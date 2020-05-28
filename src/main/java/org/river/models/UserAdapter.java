package org.river.models;

import org.river.entities.Role;
import org.river.entities.User;
import org.river.exceptions.CreateException;
import org.river.exceptions.DeleteException;
import org.river.exceptions.QueryException;
import org.river.exceptions.UpdateException;

/**
 * @author - Haribo
 */
public interface UserAdapter {
    User createUser(User user) throws CreateException;
    User updateUser(User user) throws UpdateException;
    User deleteUser(User user) throws DeleteException;
    User queryUser(String account, String password) throws QueryException;

    Role createRole(Role role) throws CreateException;
    Role updateRole(Role role) throws UpdateException;
    Role deleteRole(Role role) throws DeleteException;
    Role queryRole(Integer id) throws QueryException;
}
