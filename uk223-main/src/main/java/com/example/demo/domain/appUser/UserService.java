package com.example.demo.domain.appUser;

import com.example.demo.domain.role.Role;

import javax.management.InstanceAlreadyExistsException;
import javax.management.InstanceNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.UUID;

public interface UserService {
    User saveUser(User user) throws InstanceAlreadyExistsException;
    Role saveRole(Role role);
    void addRoleToUser(String username, String rolename);
    User getUser(String username);
    User findById(UUID id) throws InstanceNotFoundException;
    List<User> findAll();
    UserUpdateDTO update(UserUpdateDTO user, UUID uuid) throws InstanceNotFoundException, InvocationTargetException, IllegalAccessException;
    void deleteUser(UUID id);
    boolean checkUserAuthorityForEntry(UUID uuid);
}
