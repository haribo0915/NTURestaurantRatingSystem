package org.river.entities;

/**
 * @author - Haribo
 */
public class User {
    private Integer id;
    private Integer roleId;
    private String name;
    private String account;
    private String password;
    private String email;
    private String department;

    public User() {
    }

    public User(Integer roleId, String name, String account, String password, String email, String department) {
        this.roleId = roleId;
        this.name = name;
        this.account = account;
        this.password = password;
        this.email = email;
        this.department = department;
    }

    public User(Integer id, Integer roleId, String name, String account, String password, String email, String department) {
        this.id = id;
        this.roleId = roleId;
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

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
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
