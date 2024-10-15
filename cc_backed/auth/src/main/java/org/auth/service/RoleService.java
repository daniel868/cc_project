package org.auth.service;

import org.auth.model.Role;

public interface RoleService {
    Role findByName(String name);
}
