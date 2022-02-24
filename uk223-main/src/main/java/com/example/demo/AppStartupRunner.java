package com.example.demo;

import com.example.demo.domain.appMyListEntrry.MyListEntry;
import com.example.demo.domain.appMyListEntrry.MyListEntryService;
import com.example.demo.domain.appUser.User;
import com.example.demo.domain.appUser.UserService;
import com.example.demo.domain.authority.Authority;
import com.example.demo.domain.authority.AuthorityRepository;
import com.example.demo.domain.role.Role;
import com.example.demo.domain.role.RoleRepository;
import com.example.demo.domain.role.RoleServiceImpl;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Set;


@Component
@RequiredArgsConstructor
//ApplicationListener used to run commands after startup
class AppStartupRunner implements ApplicationRunner {
    @Autowired
    private final UserService userService;
    @Autowired
    private final MyListEntryService myListEntryService;
    @Autowired
    private final RoleRepository roleRepository;
    @Autowired
    private final AuthorityRepository authorityRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
//        RUN YOUR STARTUP CODE HERE
//        e.g. to add a user or role to the DB (only for testing)

        //Authorities
        Authority read_auth = new Authority(null, "READ");
        authorityRepository.save(read_auth);
        Authority create_mylistentry_auth = new Authority(null, "CREATE_MYLISTENTRY");
        authorityRepository.save(create_mylistentry_auth);
        Authority read_Write_auth = new Authority(null, "READ_WRITE");
        authorityRepository.save(read_Write_auth);

        //Roles
        Role CAN_CREATE_MYLISTENTRY_ROLE = new Role(null, "CAN_CREATE_MYLISTENTRY", Arrays.asList(create_mylistentry_auth));
        roleRepository.save(CAN_CREATE_MYLISTENTRY_ROLE);
        Role ADMIN_ROLE = new Role(null, "ADMIN", Arrays.asList(read_Write_auth));
        roleRepository.save(ADMIN_ROLE);
        Role CAN_READ_MYLISTENTRY_ROLE = new Role(null, "CAN_READ_MYLISTENTRY", Arrays.asList(read_auth));
        roleRepository.save(CAN_READ_MYLISTENTRY_ROLE);

        // Users
        User james = new User(null, "james", "james.bond@mi6.com", "bond", Set.of(ADMIN_ROLE));
        userService.saveUser(james);
        User silvan = new User(null, "silvan", "silvan.egger@mi6.com", "egger", Set.of(CAN_READ_MYLISTENTRY_ROLE));
        userService.saveUser(silvan);
        User markus = new User(null, "markus", "markus.stein@mi6.com", "stein", Set.of(ADMIN_ROLE));
        userService.saveUser(markus);
        User ruediger = new User(null, "ruediger", "ruediger.meier@mi6.com", "meier", Set.of(CAN_CREATE_MYLISTENTRY_ROLE));
        userService.saveUser(ruediger);

        //MyListEntry
        MyListEntry myListEntry1 = new MyListEntry(null, "Titel1", "Text1", new Date(), 2, james);
        myListEntryService.saveMyListEntry(myListEntry1);
        MyListEntry myListEntry2 = new MyListEntry(null, "Titel2", "Text2", new Date(), 8, silvan);
        myListEntryService.saveMyListEntry(myListEntry2);
        MyListEntry myListEntry3 = new MyListEntry(null, "Titel3", "Text3", new Date(), 3, markus);
        myListEntryService.saveMyListEntry(myListEntry3);
        MyListEntry myListEntry4 = new MyListEntry(null, "Titel4", "Text4", new Date(), 5, ruediger);
        myListEntryService.saveMyListEntry(myListEntry4);

        userService.addRoleToUser(james.getUsername(), ADMIN_ROLE.getName());
        userService.addRoleToUser(silvan.getUsername(), CAN_READ_MYLISTENTRY_ROLE.getName());
        userService.addRoleToUser(markus.getUsername(), ADMIN_ROLE.getName());
        userService.addRoleToUser(ruediger.getUsername(), CAN_CREATE_MYLISTENTRY_ROLE.getName());
    }
}

