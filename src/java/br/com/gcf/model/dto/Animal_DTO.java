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
    private String nome;
    private String tag;
    private String sisbov;
    private String peso;
    private String dataNascimento;
    private Lote_DTO lote;
    private Apartacao_DTO apartacao;
    private Familia_DTO familia;

    public Animal_DTO() {
    }

    public Animal_DTO(int id, String nome, String tag, String sisbov, String peso, String dataNascimento, Lote_DTO lote, Apartacao_DTO apartacao, Familia_DTO familia) {
        this.id = id;
        this.nome = nome;
        this.tag = tag;
        this.sisbov = sisbov;
        this.peso = peso;
        this.dataNascimento = dataNascimento;
        this.lote = lote;
        this.apartacao = apartacao;
        this.familia = familia;
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
     * @return the lote
     */
    public Lote_DTO getLote() {
        return lote;
    }

    /**
     * @param lote the lote to set
     */
    public void setLote(Lote_DTO lote) {
        this.lote = lote;
    }

    /**
     * @return the apartacao
     */
    public Apartacao_DTO getApartacao() {
        return apartacao;
    }

    /**
     * @param apartacao the apartacao to set
     */
    public void setApartacao(Apartacao_DTO apartacao) {
        this.apartacao = apartacao;
    }

    /**
     * @return the familia
     */
    public Familia_DTO getFamilia() {
        return familia;
    }

    /**
     * @param familia the familia to set
     */
    public void setFamilia(Familia_DTO familia) {
        this.familia = familia;
    }

    @Override
    public String toString() {
        return this.nome;
    }
    
}
