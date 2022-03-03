package com.example.demo.domain.appUser;

import com.example.demo.domain.Utils.CopyNotNullProps;
import com.example.demo.domain.appMyListEntrry.MyListEntry;
import com.example.demo.domain.appMyListEntrry.MyListEntryRepository;
import com.example.demo.domain.role.Role;
import com.example.demo.domain.role.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.management.InstanceAlreadyExistsException;
import javax.management.InstanceNotFoundException;
import javax.transaction.Transactional;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
@Log4j2
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private static final String USERNOTFOUND = "User not found";

    private final MyListEntryRepository myListEntryRepository;

    private final PasswordEncoder passwordEncoder;

    private final ModelMapper modelMapper;

    @Override
//    This method is used for security authentication, use caution when changing this
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException(USERNOTFOUND);
        } else {
//          Construct a valid set of Authorities (needs to implement Granted Authorities)
            Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
            user.getRoles().forEach(role -> {
                authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
                role.getAuthorities().forEach(authority ->
                        authorities.add(new SimpleGrantedAuthority(authority.getName())));
            });
//            return a spring internal user object that contains authorities and roles
            return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
        }
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Collection<Role> roles) {
        List<GrantedAuthority> authorities
                = new ArrayList<>();
        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
            role.getAuthorities().stream()
                    .map(authority -> new SimpleGrantedAuthority(authority.getName()))
                    .forEach(authorities::add);
        }
        return authorities;
    }

    @Override
    public User saveUser(User user) throws InstanceAlreadyExistsException {
        if (userRepository.findByUsername(user.getUsername()) != null) {
            throw new InstanceAlreadyExistsException("User already exists");
        } else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            log.info("Create User with ID " + user.getId());
            return userRepository.save(user);
        }
    }

    @Override
    public Role saveRole(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public void addRoleToUser(String username, String rolename) {
        User user = userRepository.findByUsername(username);
        Role role = roleRepository.findByName(rolename);
        user.getRoles().add(role);
    }

    @Override
    public User getUser(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User findById(UUID id) throws InstanceNotFoundException {
        Optional<User> result = userRepository.findById(id);
        if (userRepository.existsById(id) && result.isPresent()) {
            return result.get();
        } else {
            throw new InstanceNotFoundException(USERNOTFOUND);
        }
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public UserUpdateDTO update(UserUpdateDTO user, UUID uuid) throws InstanceNotFoundException, InvocationTargetException, IllegalAccessException {
        if (userRepository.existsById(uuid)) {
            log.info("Overwrite User with ID " + uuid);
            User userFromDB = userRepository.findById(uuid).orElse(null);
            if (user.getPassword() != null) {
                user.setPassword(passwordEncoder.encode(user.getPassword()));
            }

            CopyNotNullProps copyNotNullProps = new CopyNotNullProps();
            copyNotNullProps.copyProperties(userFromDB, user);
            assert userFromDB != null;
            userRepository.saveAndFlush(userFromDB);

            return modelMapper.map(userFromDB, UserUpdateDTO.class);
        } else {
            throw new InstanceNotFoundException(USERNOTFOUND);
        }
    }

    @Override
    public void deleteUser(UUID id) {
        log.info("Delete User with ID " + id);
        userRepository.deleteById(id);
    }

    @Override
    public boolean checkUserAuthorityForEntry(UUID uuid) {
        User currentUser = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        try {
            return currentUser.getUsername().equals(userRepository.findById(uuid).orElseThrow(InstanceNotFoundException::new).getUsername()) ||
                    currentUser.getRoles().contains(roleRepository.findByName("ADMIN"));
        } catch (Exception e) {
            return false;
        }
    }
}
