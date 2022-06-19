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
    USER_PROFILES.add(new UserProfile(UUID.randomUUID(), "bobbymcdula", null));
    USER_PROFILES.add(new UserProfile(UUID.randomUUID(), "helenvanderholfen", null));
  }

  public List<UserProfile> getUserProfiles() {
    return USER_PROFILES;
  }
}
