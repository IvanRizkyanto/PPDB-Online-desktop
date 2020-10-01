/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ivan.ppdbonline.form;

/**
 *
 * @author ivanlubuntu
 */
public class C_Status {

    /**
     * @return the loggedIn
     */
    public boolean isLoggedIn() {
        return loggedIn;
    }

    /**
     * @param loggedIn the loggedIn to set
     */
    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public String getUserLogin() {
        return userLogin;
    }
    
    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }
    private String userLogin;
    private boolean loggedIn;
}
