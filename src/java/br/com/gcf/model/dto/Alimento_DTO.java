/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gcf.model.dto;

import eu.webtoolkit.jwt.WFileResource;
import eu.webtoolkit.jwt.WResource;
import java.io.InputStream;

/**
 *
 * @author Windows
 */
public class Alimento_DTO {

    private int id;
    private String nome;
    
    public Alimento_DTO() {
        
        this.id = -1;
        this.nome = "";
    }

    public Alimento_DTO(int id, String nome) {
        this.id = id;
        this.nome = nome;
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

    @Override
    public String toString() {
        return this.nome != null ? this.nome : "";
    }
    
}
