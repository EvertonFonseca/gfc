/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gcf.model.dto;

/**
 *
 * @author Windows
 */
public class Usuario_DTO {
    
    private int id;
    private String nome;
    private String password;
    private String data;
    private String email;
    private TpUsuario_DTO tipoCategoria;
    private boolean ativo;

    public Usuario_DTO() {

        this.id = -1;
        this.nome = "";
        this.password = "";
        this.data = "";
        this.email = "";
        this.tipoCategoria = null;
        this.ativo = false;
    }

    public Usuario_DTO(int id, String nome, String password, String data, String email, TpUsuario_DTO tipoCategoria, boolean ativo) {
        this.id = id;
        this.nome = nome;
        this.password = password;
        this.data = data;
        this.email = email;
        this.tipoCategoria = tipoCategoria;
        this.ativo = ativo;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * @param nome the nome to set
     */
    public void setNome(String nome) {
        this.nome = nome;
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

    /**
     * @return the data
     */
    public String getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(String data) {
        this.data = data;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the tipoCategoria
     */
    public TpUsuario_DTO getTipoCategoria() {
        return tipoCategoria;
    }

    /**
     * @param tipoCategoria the tipoCategoria to set
     */
    public void setTipoCategoria(TpUsuario_DTO tipoCategoria) {
        this.tipoCategoria = tipoCategoria;
    }

    /**
     * @return the ativo
     */
    public boolean isAtivo() {
        return ativo;
    }

    /**
     * @param ativo the ativo to set
     */
    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }
    

    
}
