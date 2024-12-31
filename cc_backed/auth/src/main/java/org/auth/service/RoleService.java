package org.auth.service;

import org.auth.model.Role;
import org.auth.model.RoleDto;

import java.util.List;

public interface RoleService {
    Role findByName(String name);

    List<RoleDto> findAllByUser(Long userId);
}
