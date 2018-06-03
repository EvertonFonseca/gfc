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
public class Familia_DTO {
    
    private int id;
    private String nome;
    private Animal_DTO pai;
    private Animal_DTO mae;
    
    public Familia_DTO() {
   
    }

    public Familia_DTO(int id, String nome, Animal_DTO pai, Animal_DTO mae) {
        this.id = id;
        this.nome = nome;
        this.pai = pai;
        this.mae = mae;
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

    @Override
    public String toString() {
        return this.nome;
    }

}
