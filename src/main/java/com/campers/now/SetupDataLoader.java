package com.campers.now;


import com.campers.now.models.Role;
import com.campers.now.models.User;
import com.campers.now.models.enums.RoleType;
import com.campers.now.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;

@Component
@RequiredArgsConstructor
public class SetupDataLoader implements
        ApplicationListener<ContextRefreshedEvent> {

    boolean alreadySetup;

    private final UserRepository userRepository;

    private final ActivityRepository activityRepository;

    private final PostRepository postRepository;

    private final CommentRepository commentRepository;

    private final PasswordEncoder passwordEncoder;

    private final RoleRepository roleRepository;

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {

        if (alreadySetup)
            return;

        createRoleIfNotFound(RoleType.ROLE_SUPER_ADMIN);
        createRoleIfNotFound(RoleType.ROLE_ADMIN);
        createRoleIfNotFound(RoleType.ROLE_CAMPER);

        /*
         * Uncomment to add random posts
         *
         for (int i = 1; i < 21; i++) {
            Post post = new Post();
            post.setDetails("Lorem " + i * 1523);
            var savedPost = postRepository.save(post);
            var comment = new Comment();
            comment.setDetails("GG");
            comment.setPost(post);
            commentRepository.save(comment);
        }*/

        User user = new User();

        user.setEmail("admin@test.com");
        if (userRepository.countByEmail(user.getEmail()) == 0) {
            Role adminRole = roleRepository.findByName(RoleType.ROLE_SUPER_ADMIN);
            user.setNom("admin");
            user.setPrenom("admin");
            user.setPassword(passwordEncoder.encode("admin"));
            user.setRoles(List.of(adminRole));
            user.setActive(true);
            user.setEmailValide(true);
            userRepository.save(user);
        }

        alreadySetup = true;
    }

    @Transactional
    public void createRoleIfNotFound(RoleType name) {

        if (roleRepository.countByName(name) == 0) {
            Role role = Role.builder().name(name).build();
            roleRepository.save(role);
        }
    }
}