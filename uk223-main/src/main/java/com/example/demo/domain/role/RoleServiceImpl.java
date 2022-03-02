package com.example.demo.domain.role;

import com.example.demo.domain.authority.Authority;
import com.example.demo.domain.authority.AuthorityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import javax.management.InstanceNotFoundException;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

@Service @RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

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
        if (roleRepository.existsById(id)) {
            return roleRepository.findById(id).orElseThrow(InstanceNotFoundException::new);
        }
        else {
            throw new InstanceNotFoundException("Role doesn't exist");
        }
    }

    @Override
    public Role createRole(Role role) {
        return roleRepository.save(role);
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
            throw new InstanceNotFoundException("Role doesn't exist");
        }
    }
}
