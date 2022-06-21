package io.example.awsimageupload.filestore;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import java.io.InputStream;
import java.util.Map;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
