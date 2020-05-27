package org.river.models;

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
    User queryUser(User user) throws QueryException;
    User updateUser(User user) throws UpdateException;
    User deleteUser(User user) throws DeleteException;
}
