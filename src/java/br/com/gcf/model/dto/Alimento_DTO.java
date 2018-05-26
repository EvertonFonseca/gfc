/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gcf.model.dto;

import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 *
 * @author Windows
 */
public class Alimento_DTO {

    private int id;
    private String nome;
    private String mistura;
    private boolean ativo;
    private HashMap<String,String> receitas;
    
    public Alimento_DTO() {
        
        this.id = -1;
        this.nome = "";
        this.receitas = new LinkedHashMap<>();
    }

    public Alimento_DTO(int id, String nome) {
        this.id = id;
        this.nome = nome;
        this.receitas = new LinkedHashMap<>();
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
        return this.getNome() != null ? this.getNome() : "";
    }

    /**
     * @return the mistura
     */
    public String getMistura() {
        return mistura;
    }

    /**
     * @param mistura the mistura to set
     */
    public void setMistura(String mistura) {
        this.mistura = mistura;
        
        //create receitas
        this.receitas.clear();
        
        //split
        String[] separator = this.mistura.split(",");
        
        for (String alimento : separator) {
             
             String[] mix = alimento.split(":");
             String nome  = mix[0];
             String valor = mix[1];
             
             this.receitas.put(nome, valor);
        }
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

    public HashMap<String, String> getReceitas() {
        return receitas;
    }
    
    
}
