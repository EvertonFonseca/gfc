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
public class Apartacao_DTO {

    private int id;
    private String nome;
    private String de;
    private String ate;
    private Lote_DTO lote;
    private Alimento_DTO racao;
    private TipoApartacao_DTO tipo;

    public Apartacao_DTO(Lote_DTO lote,Alimento_DTO racao) {
        
        this.id   = -1;
        this.nome = "";
        this.de   = "";
        this.ate  = "";
        this.lote = lote;
        this.racao = racao;
        this.tipo  = new TipoApartacao_DTO(0,"","",TipoApartacao_DTO.Tipo.TEXT);
    }

    public Apartacao_DTO(int id, String nome, String de, String ate, Lote_DTO lote, Alimento_DTO racao) {
        this.id = id;
        this.nome = nome;
        this.de = de;
        this.ate = ate;
        this.lote = lote;
        this.racao = racao;
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
     * @return the de
     */
    public String getDe() {
        return de;
    }

    /**
     * @param de the de to set
     */
    public void setDe(String de) {
        this.de = de;
    }

    /**
     * @return the ate
     */
    public String getAte() {
        return ate;
    }

    /**
     * @param ate the ate to set
     */
    public void setAte(String ate) {
        this.ate = ate;
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
     * @return the racao
     */
    public Alimento_DTO getRacao() {
        return racao;
    }

    /**
     * @param racao the racao to set
     */
    public void setRacao(Alimento_DTO racao) {
        this.racao = racao;
    }

    /**
     * @return the tipo
     */
    public TipoApartacao_DTO getTipo() {
        return tipo;
    }

    /**
     * @param tipo the tipo to set
     */
    public void setTipo(TipoApartacao_DTO tipo) {
        this.tipo = tipo;
    }
 
    @Override
    public String toString() {
        return this.nome != null ? this.nome : "";
    }
    
 
    
}
