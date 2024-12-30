package org.auth.service;

import org.auth.model.Role;
import org.auth.model.RoleEnum;
import org.auth.repository.RoleRepository;
import org.auth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "roleService")
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository;


    @Override
    public Role findByName(String name) {
        return roleRepository.findRoleByRole(RoleEnum.valueOf(name));
    }

    @Override
    public List<Role> findAllByUser(Long userId) {
        List<Role> allRoleByUser = roleRepository.findAllRoleByUser(userId);
        return List.of();
    }
}

