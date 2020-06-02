package org.river.models;

/**
 * @author - Haribo
 */
public class StubUserAdapterFactory implements UserAdapterFactory {
    @Override
    public UserAdapter create() {
        return new StubUserAdapter();
    }
}
