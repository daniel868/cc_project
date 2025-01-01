package org.auth.service;

import org.auth.model.Role;
import org.auth.model.RoleDto;
import org.auth.model.RoleEnum;
import org.auth.repository.RoleRepository;
import org.auth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;


    @Override
    public Role findByName(String name) {
        return roleRepository.findRoleByRole(RoleEnum.valueOf(name));
    }

    @Override
    public List<RoleDto> findAllByUser(Long userId) {
        return userRepository.loadUserWithRole(userId)
                .getRoles()
                .stream()
                .map(role -> RoleDto.builder()
                        .roleName(role.getRole().getName())
                        .build())
                .collect(Collectors.toList());
    }
}

