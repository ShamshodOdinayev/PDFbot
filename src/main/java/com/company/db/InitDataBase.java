package com.company.db;

import com.company.dto.Profile;
import com.company.enums.ProfileRole;
import com.company.repository.Repository;

public class InitDataBase {
    public static void adminInit() {
        Repository repository = new Repository();
        Profile profile = new Profile();
        profile.setFullName("Odinayev Shamshod");
        profile.setPhone("91 082 66 96");
        profile.setChatId("5001957106");
        profile.setRole(ProfileRole.ADMIN);
        Profile profile1 = repository.getProfileByChatId(profile.getChatId());
        if (profile1 != null) {
            return;
        }
        repository.saveProfile(profile);
    }
}
