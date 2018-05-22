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
public class TipoApartacao_DTO {
    
    private int id;
    private String nome;
    private Tipo flag;
    private String referencia;
    
    public enum Tipo {
        
        TEXT,NUMBER,REAL,DATE
    }

    public TipoApartacao_DTO(int id, String nome,String referencia,Tipo flag) {
        this.id = id;
        this.nome = nome;
        this.flag = flag;
        this.referencia = referencia;
    }

    public TipoApartacao_DTO() {
        
        this.id = -1;
        this.nome = "";
        this.referencia = "";
        this.flag = Tipo.TEXT;
    }

    
        @Override
    public String toString() {
        return this.getNome() != null ? this.getNome() : "";
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
     * @return the flag
     */
    public Tipo getFlag() {
        return flag;
    }

    /**
     * @param flag the flag to set
     */
    public void setFlag(Tipo flag) {
        this.flag = flag;
    }

    /**
     * @return the referencia
     */
    public String getReferencia() {
        return referencia;
    }

    /**
     * @param referencia the referencia to set
     */
    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }
    
    
}
