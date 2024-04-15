package com.ekdev.jdbc.entity;

public class Users {

    private Long id;
    private Long roleId;
    private String login;
    private String password;
    private String firstName;
    private String lastName;
    private Long ordersAmount;
    private Boolean isBlocked;
    private Long blockedBy;

    public Users(Long id, Long roleId, String login, String password, String firstName, String lastName,
                 Long ordersAmount, Boolean isBlocked, Long blockedBy) {
        this.id = id;
        this.roleId = roleId;
        this.login = login;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.ordersAmount = ordersAmount;
        this.isBlocked = isBlocked;
        this.blockedBy = blockedBy;
    }

    public Users() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Long getOrdersAmount() {
        return ordersAmount;
    }

    public void setOrdersAmount(Long ordersAmount) {
        this.ordersAmount = ordersAmount;
    }

    public Boolean getBlocked() {
        return isBlocked;
    }

    public void setBlocked(Boolean blocked) {
        isBlocked = blocked;
    }

    public Long getBlockedBy() {
        return blockedBy;
    }

    public void setBlockedBy(Long blockedBy) {
        this.blockedBy = blockedBy;
    }

    @Override
    public String toString() {
        return "Users{" +
                "id=" + id +
                ", roleId=" + roleId +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", ordersAmount=" + ordersAmount +
                ", isBlocked=" + isBlocked +
                ", blockedBy=" + blockedBy +
                '}';
    }
}
