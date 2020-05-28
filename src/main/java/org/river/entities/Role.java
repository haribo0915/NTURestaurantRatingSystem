package org.river.entities;

/**
 * @author - Haribo
 */
public class Role {
    private Integer id;
    private Integer title;

    public Role() {
    }

    public Role(Integer id, Integer title) {
        this.id = id;
        this.title = title;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTitle() {
        return title;
    }

    public void setTitle(Integer title) {
        this.title = title;
    }
}
