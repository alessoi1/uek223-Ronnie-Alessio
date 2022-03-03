package com.example.demo.domain.role;

import javax.management.InstanceNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public interface RoleService {
    void addAuthorityToRole( String rolename, String authorityname);
    List<Role> findAll();
    Role findById(UUID id) throws InstanceNotFoundException;
    Role createRole(Role role);
    Role updateRole(UUID id, Role role) throws InvocationTargetException, IllegalAccessException, InstanceNotFoundException;
    void deleteRole(UUID id) throws InstanceNotFoundException, SQLException;
}
