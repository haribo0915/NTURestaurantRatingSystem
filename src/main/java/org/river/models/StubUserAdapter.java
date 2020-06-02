package org.river.models;

import org.river.entities.Role;
import org.river.entities.User;
import org.river.exceptions.CreateException;
import org.river.exceptions.DeleteException;
import org.river.exceptions.QueryException;
import org.river.exceptions.UpdateException;
import java.util.Random;

/**
 * @author - Haribo
 */
public class StubUserAdapter implements UserAdapter {
    @Override
    public User createUser(User user) throws CreateException {
        Random rand = new Random();
        Integer id = rand.nextInt(50);
        id += 1;
        return new User(id, user.getRoleId(), user.getName(), user.getAccount(), user.getPassword(),
                user.getEmail(), user.getDepartment());
    }

    @Override
    public User updateUser(User user) throws UpdateException {
        return user;
    }

    @Override
    public User deleteUser(User user) throws DeleteException {
        return user;
    }

    @Override
    public User queryUser(String account, String password) throws QueryException {
        if (account.equals("admin") && password.equals("admin")) {
            return new User(1, 1, "admin", "admin", "admin",
                    "admin@gmail.com","CSIE");
        } else {
            throw new QueryException("User Not Found");
        }

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
