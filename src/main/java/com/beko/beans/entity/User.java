package com.beko.beans.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class User extends BaseEntity{
    private static final long serialVersionUID = 7181971624801380059L;

    private boolean newUser;
    private String login;

    public User(){
        newUser = true;
    }

    @Column(name="is_new_user")
    public boolean isNewUser() {
        return newUser;
    }

    public void setNewUser(boolean newUser) {
        this.newUser = newUser;
    }

    @Column(name = "login")
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
    @Override
    public void lazyInit() {
    }

}
