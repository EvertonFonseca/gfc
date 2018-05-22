/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gcf.control;

import eu.webtoolkit.jwt.WApplication;

/**
 *
 * @author Windows
 */
public class Conexao {
    
    private Client client;
    private String id;

    public Conexao(Client client, String id) {
        
        this.client = client;
        this.id = id;
        
        Sistema.clientes.add(this);
        Sistema.application.add(WApplication.getInstance());
    }

    /**
     * @return the client
     */
    public Client getClient() {
        return client;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }
    
    
}
