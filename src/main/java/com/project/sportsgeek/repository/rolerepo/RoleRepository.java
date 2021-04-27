package com.project.sportsgeek.repository.rolerepo;

import com.project.sportsgeek.model.profile.Role;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository(value = "roleRepo")
public interface RoleRepository {

    public List<Role> findAllRole();

    public List<Role> findRoleById(int id) throws Exception;

    public int addRole(Role role) throws Exception;

    public boolean updateRole(int id, Role role) throws Exception;

    public int deleteRole(int id) throws Exception;
}
