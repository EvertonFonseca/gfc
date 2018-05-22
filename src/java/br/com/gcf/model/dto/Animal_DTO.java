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
public class Animal_DTO {
    
    private int id;
    private String identificador;
    private String tag;
    private String sisbov;
    private String peso;
    private Animal_DTO pai;
    private Animal_DTO mae;
    private String dataNascimento;
    private String apartacao;

    public Animal_DTO() {
    }

    public Animal_DTO(int id, String identificador, String tag, String sisbov, String peso, Animal_DTO pai, Animal_DTO mae, String dataNascimento, String apartacao) {
        this.id = id;
        this.identificador = identificador;
        this.tag = tag;
        this.sisbov = sisbov;
        this.peso = peso;
        this.pai = pai;
        this.mae = mae;
        this.dataNascimento = dataNascimento;
        this.apartacao = apartacao;
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
     * @return the identificador
     */
    public String getIdentificador() {
        return identificador;
    }

    /**
     * @param identificador the identificador to set
     */
    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    /**
     * @return the tag
     */
    public String getTag() {
        return tag;
    }

    /**
     * @param tag the tag to set
     */
    public void setTag(String tag) {
        this.tag = tag;
    }

    /**
     * @return the sisbov
     */
    public String getSisbov() {
        return sisbov;
    }

    /**
     * @param sisbov the sisbov to set
     */
    public void setSisbov(String sisbov) {
        this.sisbov = sisbov;
    }

    /**
     * @return the peso
     */
    public String getPeso() {
        return peso;
    }

    /**
     * @param peso the peso to set
     */
    public void setPeso(String peso) {
        this.peso = peso;
    }

    /**
     * @return the pai
     */
    public Animal_DTO getPai() {
        return pai;
    }

    /**
     * @param pai the pai to set
     */
    public void setPai(Animal_DTO pai) {
        this.pai = pai;
    }

    /**
     * @return the mae
     */
    public Animal_DTO getMae() {
        return mae;
    }

    /**
     * @param mae the mae to set
     */
    public void setMae(Animal_DTO mae) {
        this.mae = mae;
    }

    /**
     * @return the dataNascimento
     */
    public String getDataNascimento() {
        return dataNascimento;
    }

    /**
     * @param dataNascimento the dataNascimento to set
     */
    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    /**
     * @return the apartacao
     */
    public String getApartacao() {
        return apartacao;
    }

    /**
     * @param apartacao the apartacao to set
     */
    public void setApartacao(String apartacao) {
        this.apartacao = apartacao;
    }
    
    
}
