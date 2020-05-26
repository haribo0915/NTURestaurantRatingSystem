package org.river.models;

import org.river.entities.User;

/**
 * @author - Haribo
 */
public interface UserAdapter {
    User createUser(User user);
    User queryUser(User user);
}
