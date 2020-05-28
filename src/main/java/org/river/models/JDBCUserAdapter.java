package org.river.models;

import org.river.entities.Role;
import org.river.entities.User;
import org.river.exceptions.*;

public class JDBCUserAdapter implements UserAdapter {
    @Override
    public User createUser(User user) throws CreateException {
        return null;
    }

    @Override
    public User updateUser(User user) throws UpdateException {
        return null;
    }

    @Override
    public User deleteUser(User user) throws DeleteException {
        return null;
    }

    @Override
    public User queryUser(String account, String password) throws QueryException {
        return null;
    }

    @Override
    public Role createRole(Role role) throws CreateException {
        return null;
    }

    @Override
    public Role updateRole(Role role) throws UpdateException {
        return null;
    }

    @Override
    public Role deleteRole(Role role) throws DeleteException {
        return null;
    }

    @Override
    public Role queryRole(Integer id) throws QueryException {
        return null;
    }
}
