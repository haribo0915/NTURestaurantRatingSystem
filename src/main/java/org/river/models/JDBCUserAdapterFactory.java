package org.river.models;

/**
 * @author - Haribo
 */
public class JDBCUserAdapterFactory implements UserAdapterFactory {
    @Override
    public UserAdapter create() {
        return new JDBCUserAdapter();
    }
}
