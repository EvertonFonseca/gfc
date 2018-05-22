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
public class Fazenda_DTO {

    private int id;
    private String nome;
    private String latitude;
    private String longitude;
    private int quantidadeLotes;
    
    public Fazenda_DTO() {
        
        this.id = -1;
        this.nome = "";
        this.latitude = "0";
        this.longitude = "0";
        this.quantidadeLotes = 0;
    }

    public Fazenda_DTO(int id, String nome, String latitude, String longitude, int quantidadeLotes) {
        this.id = id;
        this.nome = nome;
        this.latitude = latitude;
        this.longitude = longitude;
        this.quantidadeLotes = quantidadeLotes;
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
     * @return the latitude
     */
    public String getLatitude() {
        return latitude;
    }

    /**
     * @param latitude the latitude to set
     */
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    /**
     * @return the longitude
     */
    public String getLongitude() {
        return longitude;
    }

    /**
     * @param longitude the longitude to set
     */
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    /**
     * @return the quantidadeLotes
     */
    public int getQuantidadeLotes() {
        return quantidadeLotes;
    }

    /**
     * @param quantidadeLotes the quantidadeLotes to set
     */
    public void setQuantidadeLotes(int quantidadeLotes) {
        this.quantidadeLotes = quantidadeLotes;
    }

    @Override
    public String toString() {
        return this.nome;
    }
    
}
