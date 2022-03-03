package com.example.demo.domain.role;

import com.example.demo.domain.Utils.CopyNotNullProps;
import com.example.demo.domain.appUser.User;
import com.example.demo.domain.appUser.UserUpdateDTO;
import com.example.demo.domain.authority.Authority;
import com.example.demo.domain.authority.AuthorityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import javax.management.InstanceNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service @RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    private static final String ROLEDOESNTEXIST = "Role doesn't exist";

    private final AuthorityRepository authorityRepository;

    @Override
    public void addAuthorityToRole( String rolename, String authorityname) {
        Authority authority = authorityRepository.findByName(authorityname);
        Role role = roleRepository.findByName(rolename);
        role.getAuthorities().add(authority);
    }

    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    @Override
    public Role findById(@PathVariable UUID id) throws InstanceNotFoundException {
        Optional<Role> result = roleRepository.findById(id);
        if (roleRepository.existsById(id) && result.isPresent()) {
            return result.get();
        }
        else {
            throw new InstanceNotFoundException(ROLEDOESNTEXIST);
        }
    }

    @Override
    public Role createRole(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public Role updateRole(UUID id, Role role) throws InvocationTargetException, IllegalAccessException, InstanceNotFoundException {
        if (roleRepository.existsById(id)) {
            Role roleFromDB = roleRepository.findById(id).orElse(null);

            CopyNotNullProps copyNotNullProps = new CopyNotNullProps();
            copyNotNullProps.copyProperties(roleFromDB, role);

            assert roleFromDB != null;
            roleRepository.saveAndFlush(roleFromDB);

            return roleFromDB;
        }
        else {
            throw new InstanceNotFoundException(ROLEDOESNTEXIST);
        }
    }

    @Override
    public void deleteRole(UUID id) throws InstanceNotFoundException, SQLException {
        if (roleRepository.existsById(id)) {
            try {
                roleRepository.deleteById(id);
            } catch (Exception e) {
                throw new SQLException("Some users still have this role!");
            }
        }
        else {
            throw new InstanceNotFoundException(ROLEDOESNTEXIST);
        }
    }
}
