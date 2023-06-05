package com.campers.now;


import com.campers.now.models.Activity;
import com.campers.now.models.Role;
import com.campers.now.models.User;
import com.campers.now.models.enums.RoleType;
import com.campers.now.repositories.ActivityRepository;
import com.campers.now.repositories.RoleRepository;
import com.campers.now.repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Component
@AllArgsConstructor
@NoArgsConstructor
public class SetupDataLoader implements
        ApplicationListener<ContextRefreshedEvent> {

    boolean alreadySetup;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {

        if (alreadySetup)
            return;

        createRoleIfNotFound(RoleType.ROLE_ADMIN);
        createRoleIfNotFound(RoleType.ROLE_VENDOR);
        createRoleIfNotFound(RoleType.ROLE_CAMPER);

        for (int i = 1; i < 21; i++) {
            Activity activity = new Activity()/* activityRepository.findById(i).get()*/;
            activity.setPrice(i+4);
            activity.setDuration(2);
            activityRepository.save(activity);
        }
        User user = new User();

        user.setEmail("admin@test.com");
        if (userRepository.countByEmail(user.getEmail()) == 0) {
            Role adminRole = roleRepository.findByName(RoleType.ROLE_ADMIN);
            user.setNom("admin");
            user.setPrenom("admin");
            user.setPassword("admin");
            user.setRoles(List.of(adminRole));
            user.setActive(true);
            user.setEmailValide(true);
            user.setModifiedAt(new Date());
            userRepository.save(user);
        }

        alreadySetup = true;
    }

    @Transactional
    void createRoleIfNotFound(RoleType name) {

        if (roleRepository.countByName(name) == 0) {
            Role role = Role.builder().name(name).build();
            roleRepository.save(role);
        }
    }
}