/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gcf.model.table;

import br.com.gcf.control.utils.LoadFile;

/**
 *
 * @author Windows
 */
public class AnimalTableModel {

    public static String selectAnimais() {

        return new LoadFile().loadFile("/br/com/gcf/control/dao/sql/animal/readAllAnimais.sql");
    }

    public static String selectLoteFazendaByFiltro(String campo, String condicao) {

        return "";

    }

    public static String insert() {

        return "";
    }

    public static String findName(String nameLote) {

        return "";
    }

    public static String isExistName(String nome) {

        return "";
    }

    public static String delete(int id) {

        return "";
    }

    public static String update() {
        return  "";
    }
}
