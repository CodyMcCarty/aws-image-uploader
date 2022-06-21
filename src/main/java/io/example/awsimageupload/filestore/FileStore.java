package io.example.awsimageupload.filestore;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.util.IOUtils;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class FileStore {

  private final AmazonS3 s3;
  private static final Logger LOG = LoggerFactory.getLogger(FileStore.class);

  @Autowired
  public FileStore(AmazonS3 s3) {
    this.s3 = s3;
  }

  public void save(String path, String fileName, Optional<Map<String, String>> optionalMetadata,
      InputStream inputStream) {
    LOG.info("saving..." + fileName);
    ObjectMetadata metadata = new ObjectMetadata();
    optionalMetadata.ifPresent(map -> {
      if (!map.isEmpty()) {
        map.forEach(metadata::addUserMetadata);
      }
    });
    try {
      s3.putObject(path, fileName, inputStream, metadata);
      LOG.info("saved");
    } catch (AmazonServiceException e) {
      String reason = "Failed to store file to s3";
      LOG.error(reason);
      e.printStackTrace();
      throw new IllegalStateException(reason, e);
    }

  }

  public byte[] download(String path, String fileName) {
    LOG.info("fetching pic" + path + fileName);
    try {
      S3Object object = s3.getObject(path, fileName);
      return IOUtils.toByteArray(object.getObjectContent());
    } catch (AmazonServiceException | IOException e) {
      String reason = "Failed to fetch file from s3 " + path + " fileName: " + fileName;
      LOG.error(reason);
      e.printStackTrace();
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, reason);
    }
  }
}
