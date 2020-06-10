package org.river.models;

import org.river.entities.Role;
import org.river.entities.User;
import org.river.exceptions.ResourceNotFoundException;
import java.util.Random;

/**
 * @author - Haribo
 */
public class StubUserAdapter implements UserAdapter {
    @Override
    public User createUser(User user){
        Random rand = new Random();
        Integer id = rand.nextInt(50);
        id += 1;
        return new User(id, user.getRoleId(), user.getName(), user.getAccount(), user.getPassword(),
                user.getEmail(), user.getDepartment());
    }

    @Override
    public User updateUser(User user){
        return user;
    }

    @Override
    public User deleteUser(User user){
        return user;
    }

    @Override
    public User queryUser(String account, String password)  {
        if (account.equals("admin") && password.equals("admin")) {
            return new User(1, 1, "admin", "admin", "admin",
                    "admin@gmail.com","CSIE");
        } else {
            throw new ResourceNotFoundException("User Not Found");
        }

    }

    @Override
    public Role createRole(Role role) {
        return null;
    }

    @Override
    public Role updateRole(Role role) {
        return null;
    }

    @Override
    public Role deleteRole(Role role) {
        return null;
    }

    @Override
    public Role queryRole(Integer id) {
        return null;
    }
}
