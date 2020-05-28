package org.river.entities;

/**
 * @author - Haribo
 */
public class User {
    private Integer id;
    private Integer role_id;
    private String name;
    private String account;
    private String password;
    private String email;
    private String department;

    public User() {
    }

    public User(Integer id, Integer role_id, String name, String account, String password, String email, String department) {
        this.id = id;
        this.role_id = role_id;
        this.name = name;
        this.account = account;
        this.password = password;
        this.email = email;
        this.department = department;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRole_id() {
        return role_id;
    }

    public void setRole_id(Integer role_id) {
        this.role_id = role_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}
