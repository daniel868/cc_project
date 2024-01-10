package com.ccprojectauth.ccauth.service;

import com.ccprojectauth.ccauth.model.Role;

public interface RoleService {
    Role findByName(String name);
}