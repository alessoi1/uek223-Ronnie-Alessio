package com.example.demo;

import com.example.demo.domain.appUser.User;
import com.example.demo.domain.appUser.UserService;
import com.example.demo.domain.authority.Authority;
import com.example.demo.domain.authority.AuthorityRepository;
import com.example.demo.domain.role.Role;
import com.example.demo.domain.role.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import java.util.Arrays;
import java.util.Set;

@Component
@RequiredArgsConstructor
@Log4j2
//ApplicationListener used to run commands after startup
class AppStartupRunner implements ApplicationRunner {
    @Autowired
    private final UserService userService;
    @Autowired
    private final RoleRepository roleRepository;
    @Autowired
    private final AuthorityRepository authorityRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
//        RUN YOUR STARTUP CODE HERE
//        e.g. to add a user or role to the DB (only for testing)

//        Authorities
        Authority read_auth=new Authority(null,"READ");
        authorityRepository.save(read_auth);

//        Roles
        Role default_role = new Role(null, "DEFAULT",Arrays.asList(read_auth));
        roleRepository.save(default_role);

        User default_user = new User(null, "james","james.bond@mi6.com","bond", Set.of(default_role));
        userService.saveUser(default_user);

        userService.addRoleToUser(default_user.getUsername(), default_role.getName());
    }
}

