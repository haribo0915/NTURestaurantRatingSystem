package org.river.entities;

/**
 * @author - Haribo
 */
public class Role {
    private Integer id;
    private String title;

    public Role() {
    }

    public Role(Integer id, String title) {
        this.id = id;
        this.title = title;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
