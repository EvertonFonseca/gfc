/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gcf.model.components.conllection;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Windows
 */
public class MapaLista<K, V> {

    private List<Mapa> lista;

    public MapaLista() {

        this.lista = new LinkedList<>();
    }

    public void put(K key, V value) {

        Mapa<K,V> map = new Mapa<>(key,value);
        this.lista.add(map);
    }

    public void clear() {

        this.lista.clear();
    }

    public List<Mapa> getLista() {
        return lista;
    }

    public class Mapa<K, V>{

        private K key;
        private V value;

        public Mapa(K key, V value) {
            this.key = key;
            this.value = value;
        }

        /**
         * @return the key
         */
        public K getKey() {
            return key;
        }

        /**
         * @param key the key to set
         */
        public void setKey(K key) {
            this.key = key;
        }

        /**
         * @return the value
         */
        public V getValue() {
            return value;
        }

        /**
         * @param value the value to set
         */
        public void setValue(V value) {
            this.value = value;
        }

    }
}