package org.auth.repository;

import org.auth.model.Role;
import org.auth.model.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findRoleByRole(RoleEnum role);

    @Query("select u.roles from User u left join fetch u.roles where u.userId =:userId")
    List<Role> findAllRoleByUser(@Param("userId") Long userId);
}