/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gcf.control;

/**
 *
 * @author Windows
 */
public class CookieLogin {
    
    private String idSession;
    private String nameEmail;
    private String password;

    public CookieLogin() {
    }

    public CookieLogin(String idSession, String nameEmail, String password) {
        this.idSession = idSession;
        this.nameEmail = nameEmail;
        this.password = password;
    }

    /**
     * @return the idSession
     */
    public String getIdSession() {
        return idSession;
    }

    /**
     * @param idSession the idSession to set
     */
    public void setIdSession(String idSession) {
        this.idSession = idSession;
    }

    /**
     * @return the nameEmail
     */
    public String getNameEmail() {
        return nameEmail;
    }

    /**
     * @param nameEmail the nameEmail to set
     */
    public void setNameEmail(String nameEmail) {
        this.nameEmail = nameEmail;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }
    
    
}
