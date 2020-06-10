package org.river.models;

import org.river.entities.Role;
import org.river.entities.User;
import org.river.exceptions.ResourceNotFoundException;

/**
 * @author - Haribo
 */
public interface UserAdapter {
    User createUser(User user);
    User updateUser(User user);
    User deleteUser(User user);
    User queryUser(String account, String password) throws ResourceNotFoundException;

    Role createRole(Role role);
    Role updateRole(Role role);
    Role deleteRole(Role role);
    Role queryRole(Integer id) throws ResourceNotFoundException;
}
