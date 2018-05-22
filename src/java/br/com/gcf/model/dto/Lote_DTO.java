/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gcf.model.dto;

import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Windows
 */
public class Lote_DTO {

    private int id;
    private String nome;
    private String data;
    private double pesoTotal;
    private double pesoMinimo;
    private double pesoMaximo;
    private double pesoMedio;
    private int quantidade;
    private double precoTotal;
    private int quantidadeApartacao;
    private Alimento_DTO racao;
    private double pesoDaCarcaca;
    private double arroba;
    private List<Apartacao_DTO> apartacoes;

    public Lote_DTO() {
        
        this.id = -1;
        this.nome = "";
        this.data    = "";
        this.pesoMaximo = 0;
        this.pesoMedio  = 0;
        this.pesoMinimo = 0;
        this.pesoTotal  = 0;
        this.quantidade = 0;
        this.quantidadeApartacao = 0;
        this.racao = null;
        this.pesoDaCarcaca = 0;
        this.arroba = 0;
        this.apartacoes = new ArrayList<>();
        
    }

    public Lote_DTO(int id, String nome, String data, double pesoTotal, double pesoMinimo, double pesoMaximo, double pesoMedio, int quantidade, double precoTotal, int quantidadeApartacao, Alimento_DTO racao, double pesoDaCarcaca, double arroba, List<Apartacao_DTO> apartacoes) {
        this.id = id;
        this.nome = nome;
        this.data = data;
        this.pesoTotal = pesoTotal;
        this.pesoMinimo = pesoMinimo;
        this.pesoMaximo = pesoMaximo;
        this.pesoMedio = pesoMedio;
        this.quantidade = quantidade;
        this.precoTotal = precoTotal;
        this.quantidadeApartacao = quantidadeApartacao;
        this.racao = racao;
        this.pesoDaCarcaca = pesoDaCarcaca;
        this.arroba = arroba;
        this.apartacoes = apartacoes;
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
     * @return the pesoTotal
     */
    public double getPesoTotal() {
        return pesoTotal;
    }

    /**
     * @param pesoTotal the pesoTotal to set
     */
    public void setPesoTotal(double pesoTotal) {
        this.pesoTotal = pesoTotal;
    }

    /**
     * @return the pesoMinimo
     */
    public double getPesoMinimo() {
        return pesoMinimo;
    }

    /**
     * @param pesoMinimo the pesoMinimo to set
     */
    public void setPesoMinimo(double pesoMinimo) {
        this.pesoMinimo = pesoMinimo;
    }

    /**
     * @return the pesoMaximo
     */
    public double getPesoMaximo() {
        return pesoMaximo;
    }

    /**
     * @param pesoMaximo the pesoMaximo to set
     */
    public void setPesoMaximo(double pesoMaximo) {
        this.pesoMaximo = pesoMaximo;
    }

    /**
     * @return the pesoMedio
     */
    public double getPesoMedio() {
        return pesoMedio;
    }

    /**
     * @param pesoMedio the pesoMedio to set
     */
    public void setPesoMedio(double pesoMedio) {
        this.pesoMedio = pesoMedio;
    }

    /**
     * @return the quantidade
     */
    public int getQuantidade() {
        return quantidade;
    }

    /**
     * @param quantidade the quantidade to set
     */
    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    /**
     * @return the precoTotal
     */
    public double getPrecoTotal() {
        return precoTotal;
    }

    /**
     * @param precoTotal the precoTotal to set
     */
    public void setPrecoTotal(double precoTotal) {
        this.precoTotal = precoTotal;
    }

    /**
     * @return the quantidadeApartacao
     */
    public int getQuantidadeApartacao() {
        return quantidadeApartacao;
    }

    /**
     * @param quantidadeApartacao the quantidadeApartacao to set
     */
    public void setQuantidadeApartacao(int quantidadeApartacao) {
        this.quantidadeApartacao = quantidadeApartacao;
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
     * @return the pesoDaCarcaca
     */
    public double getPesoDaCarcaca() {
        return pesoDaCarcaca;
    }

    /**
     * @param pesoDaCarcaca the pesoDaCarcaca to set
     */
    public void setPesoDaCarcaca(double pesoDaCarcaca) {
        this.pesoDaCarcaca = pesoDaCarcaca;
    }

    /**
     * @return the arroba
     */
    public double getArroba() {
        return arroba;
    }

    /**
     * @param arroba the arroba to set
     */
    public void setArroba(double arroba) {
        this.arroba = arroba;
    }

    /**
     * @return the apartacoes
     */
    public List<Apartacao_DTO> getApartacoes() {
        return apartacoes;
    }

    /**
     * @param apartacoes the apartacoes to set
     */
    public void setApartacoes(List<Apartacao_DTO> apartacoes) {
        this.apartacoes = apartacoes;
    }

    @Override
    public String toString() {
        return this.nome != null ? this.nome : "";
    }
    
    
    
}
