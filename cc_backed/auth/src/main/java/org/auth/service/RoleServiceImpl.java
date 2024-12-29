package org.auth.service;

import org.auth.model.Role;
import org.auth.model.RoleEnum;
import org.auth.repository.RoleRepository;
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

    public List<Role> findAll() {
        return roleRepository.findAll();
    }

}

