package io.example.awsimageupload.datastore;

import io.example.awsimageupload.profile.UserProfile;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Repository;

@Repository
public class FakeUserProfileDataStore {

  private static final List<UserProfile> USER_PROFILES = new ArrayList<>();

  static {
    USER_PROFILES.add(new UserProfile(UUID.fromString("e4231e88-5800-4e0b-ad51-8c57ea613f73"),
        "Ragnar Lothbrok", null));
    USER_PROFILES.add(new UserProfile(UUID.fromString("b2100bea-6058-45e1-b92a-7dacf5be4c61"),
        "Lagertha", null));
  }

  public List<UserProfile> getUserProfiles() {
    return USER_PROFILES;
  }
}
