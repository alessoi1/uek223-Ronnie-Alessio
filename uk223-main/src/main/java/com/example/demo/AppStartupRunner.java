package com.example.demo;

import com.example.demo.domain.appMyListEntrry.MyListEntry;
import com.example.demo.domain.appMyListEntrry.MyListEntryService;
import com.example.demo.domain.appUser.User;
import com.example.demo.domain.appUser.UserService;
import com.example.demo.domain.authority.Authority;
import com.example.demo.domain.authority.AuthorityRepository;
import com.example.demo.domain.role.Role;
import com.example.demo.domain.role.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import java.util.Arrays;
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
        Authority READ_MYLISTENTRY_AUTH = new Authority(null, "CAN_READ_MYLISTENTRY");
        authorityRepository.save(READ_MYLISTENTRY_AUTH);
        Authority EDIT_MYLISTENTRY_AUTH = new Authority(null, "CAN_EDIT_MYLISTENTRY");
        authorityRepository.save(EDIT_MYLISTENTRY_AUTH);

        //Roles
        Role ADMIN_ROLE = new Role(null, "ADMIN", Arrays.asList(EDIT_MYLISTENTRY_AUTH));
        roleRepository.save(ADMIN_ROLE);
        Role USER_ROLE = new Role(null, "USER", Arrays.asList(READ_MYLISTENTRY_AUTH));
        roleRepository.save(USER_ROLE);
        Role GUEST_ROLE = new Role(null, "GUEST", Arrays.asList());
        roleRepository.save(GUEST_ROLE);

        // Users
        User admin = new User(null, "admin", "admin@admin.admin", "Admin123", Set.of(ADMIN_ROLE));
        userService.saveUser(admin);
        User silvan = new User(null, "silvan", "silvan.egger@mi6.com", "Passwort", Set.of(USER_ROLE));
        userService.saveUser(silvan);
        User markus = new User(null, "markus", "markus.stein@mi6.com", "Passwort", Set.of(USER_ROLE));
        userService.saveUser(markus);
        User ruediger = new User(null, "ruediger", "ruediger.meier@mi6.com", "Passwort", Set.of(USER_ROLE));
        userService.saveUser(ruediger);

        //MyListEntry
        MyListEntry myListEntry1 = new MyListEntry(null, "Admins Blog", "Ich bin ein Admin", new Date(), 2, admin);
        myListEntryService.saveMyListEntry(myListEntry1);
        MyListEntry myListEntry2 = new MyListEntry(null, "Mein 1. Blog", "Hallo. Ich bin Silvan.", new Date(), 8, silvan);
        myListEntryService.saveMyListEntry(myListEntry2);
        MyListEntry myListEntry3 = new MyListEntry(null, "Mein zweiter Blog", "Ich bin immer noch Silvan", new Date(), 3, silvan);
        myListEntryService.saveMyListEntry(myListEntry3);
        MyListEntry myListEntry4 = new MyListEntry(null, "Hello World!", "blablablabla blabla blabla", new Date(), 3, silvan);
        myListEntryService.saveMyListEntry(myListEntry4);
        MyListEntry myListEntry5 = new MyListEntry(null, "Mein Titel", "Mein Text", new Date(), 5, ruediger);
        myListEntryService.saveMyListEntry(myListEntry5);
        MyListEntry myListEntry6 = new MyListEntry(null, "Markus Blog", "Markus Text", new Date(), 5, markus);
        myListEntryService.saveMyListEntry(myListEntry6);

        userService.addRoleToUser(admin.getUsername(), ADMIN_ROLE.getName());
        userService.addRoleToUser(silvan.getUsername(), USER_ROLE.getName());
        userService.addRoleToUser(markus.getUsername(), USER_ROLE.getName());
        userService.addRoleToUser(ruediger.getUsername(), USER_ROLE.getName());
    }
}

