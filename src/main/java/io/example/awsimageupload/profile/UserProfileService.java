package io.example.awsimageupload.profile;

import static org.apache.http.entity.ContentType.IMAGE_GIF;
import static org.apache.http.entity.ContentType.IMAGE_JPEG;
import static org.apache.http.entity.ContentType.IMAGE_PNG;

import io.example.awsimageupload.bucket.BucketName;
import io.example.awsimageupload.filestore.FileStore;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserProfileService {

  private final UserProfileDataAccessService userProfileDataAccessService;
  private final FileStore fileStore;
  private static final Logger LOG = LoggerFactory.getLogger(UserProfileService.class);

  @Autowired
  public UserProfileService(UserProfileDataAccessService userProfileDataAccessService,
      FileStore fileStore) {
    this.userProfileDataAccessService = userProfileDataAccessService;
    this.fileStore = fileStore;
  }

  List<UserProfile> getUserProfiles() {
    return userProfileDataAccessService.getUserProfiles();
  }

  public void uploadUserProfileImage(UUID userProfileId, MultipartFile file) {
    LOG.info("file received " + file.getOriginalFilename());
    if (file.isEmpty()) {
      String reason = "--File is required: file size: " + file.getSize();
      LOG.error(reason);
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
          reason);
    }
    if (!Arrays.asList(IMAGE_JPEG.getMimeType(), IMAGE_PNG.getMimeType(), IMAGE_GIF.getMimeType())
        .contains(file.getContentType())) {
      String reason =
          "File must be an image (jpeg, png, gif). FileType detected: " + file.getContentType();
      LOG.error(reason);
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, reason);
    }
    // FIXME: user exist in DB
    UserProfile userProfileFound = userProfileDataAccessService
        .getUserProfiles()
        .stream()
        .filter(userProfile -> userProfile.getUserProfileId().equals(userProfileId))
        .findFirst()
        .orElseThrow(() -> {
          String reason = "User Not found with ID: " + userProfileId;
          LOG.error(reason);
          return new ResponseStatusException(HttpStatus.NOT_FOUND, reason);
        });

    Map<String, String> metadata = new HashMap<>();
    metadata.put("Content-Type", file.getContentType());
    metadata.put("Content-Length", String.valueOf(file.getSize()));
    String path = String.format("%s/%s", BucketName.PROFILE_IMAGE.getBucketName(), userProfileId);
    String filename = String.format("%s-%s", UUID.randomUUID(), file.getOriginalFilename());
    InputStream fileInputStream;
    try {
      fileInputStream = file.getInputStream();
    } catch (Exception e) {
      String reason = "No file input stream. Error" + e.getMessage();
      LOG.error(reason);
      e.printStackTrace();
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, reason);
    }
    LOG.info(String.format("file to save: "
            + "\npath: %s, \nfilename: %s, \nMetaData: %s, \ninputStream: %s",
        path, filename, metadata, fileInputStream));
    fileStore.save(path, filename, Optional.of(metadata), fileInputStream);
    userProfileFound.setUserProfileImageLink(filename);
  }

  public byte[] downloadUserProfileImage(UUID userProfileId) {
    return new byte[0];
  }
}
