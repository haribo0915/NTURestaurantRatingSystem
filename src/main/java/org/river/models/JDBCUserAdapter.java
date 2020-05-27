package org.river.models;

import org.river.entities.User;
import org.river.exceptions.*;

/**
 * @author - Haribo
 */
public class JDBCUserAdapter implements UserAdapter {
    @Override
    public User createUser(User user) throws CreateException {
        return null;
    }

    @Override
    public User queryUser(User user) throws QueryException {
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
}
