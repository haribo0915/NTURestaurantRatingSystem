package org.river.models;

/**
 * Factory used to create StubUserAdapter dynamically
 *
 * @author - Haribo
 */
public class StubUserAdapterFactory implements UserAdapterFactory {
    @Override
    public UserAdapter create() {
        return new StubUserAdapter();
    }
}
