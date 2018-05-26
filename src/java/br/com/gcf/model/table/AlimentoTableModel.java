/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gcf.model.table;

/**
 *
 * @author Windows
 */
public class AlimentoTableModel {

    public static String TABLE_NAME = "alimento";
    public static String ID_ALIMENTO = "\"id_alimento\"";
    public static String NOME_ALIMENTO = "\"nome_alimento\"";
    public static String PROCESSO_ALIMENTO = "\"processo_alimento\"";
    public static String ATIVO_ALIMENTO = "\"status_alimento\"";

    public static String selectAll(boolean isOrderBy) {

        return "select * from " + TABLE_NAME + (isOrderBy ? " ORDER BY " + ID_ALIMENTO + "" : "");
    }
    
       public static String selectById(int id) {

        return "select * from " + TABLE_NAME +" where "+ID_ALIMENTO+" = "+id;
    }

    public static String isExistName(String name) {

        return "select * from " + TABLE_NAME + " where " + NOME_ALIMENTO + " = '"+name+"'";
    }
    public static String delete(int id) {

        return "DELETE FROM " + TABLE_NAME + " WHERE " + ID_ALIMENTO + " = " + id;
    }

    public static String insert() {

        return "INSERT INTO public.alimento(\n"
                + "             nome_alimento, processo_alimento, status_alimento)\n"
                + "    VALUES (?, ?, ?)";
    }
}
