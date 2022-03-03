package com.example.demo;

import com.example.demo.domain.appMyListEntrry.MyListEntryService;
import com.example.demo.domain.appUser.UserService;
import com.example.demo.domain.authority.AuthorityRepository;
import com.example.demo.domain.role.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

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

    }
    
}

