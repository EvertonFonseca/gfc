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
public class FazendaTableModel {

    public static String TABLE_NAME = "fazendas";
    public static String ID_FAZENDA = "\"id_fazenda\"";
    public static String NOME_FAZENDA = "\"nome_fazenda\"";
    public static String LATITUDE_FAZENDA = "\"latitude_fazenda\"";
    public static String LONGETUDE_FAZENDA = "\"longitude_fazenda\"";
    public static String[] COLUMNS = {ID_FAZENDA,NOME_FAZENDA,LATITUDE_FAZENDA,LONGETUDE_FAZENDA    };

    public static String selectAll(boolean isOrderBy) {

        return "select * from " + TABLE_NAME + (isOrderBy ? " ORDER BY " + ID_FAZENDA + "" : "");
    }

    public static String selectAllGroup() {

        return "select \n"
                + "  fazendas.id_fazenda, \n"
                + "  fazendas.nome_fazenda, \n"
                + "  fazendas.latitude_fazenda, \n"
                + "  fazendas.longitude_fazenda, \n"
                + "  count(lotes.id_lote)as Total\n"
                + "  from fazendas,lotes where lotes.id_fazenda = fazendas.id_fazenda GROUP BY fazendas.id_fazenda ORDER BY id_fazenda";

    }

    public static String selectAllSorting(int index) {

        return "select * from " + TABLE_NAME + " ORDER BY " + COLUMNS[index] + " DESC";
    }

    public static String insert() {

        return "INSERT INTO " + TABLE_NAME + "(\n"
                + "  " + NOME_FAZENDA
                + "," + LATITUDE_FAZENDA
                + "," + LONGETUDE_FAZENDA+ ")\n"
                + "    VALUES (?, ?, ?);";
    }

    public static String update(int idCliente) {

        return "UPDATE " + TABLE_NAME + "\n"
                + "   SET " + NOME_FAZENDA + "=?," + LATITUDE_FAZENDA + "=?," + LONGETUDE_FAZENDA + "=? \n"
                + " WHERE " + ID_FAZENDA + " = " + idCliente;
    }

    public static String delete(int id) {

        return "DELETE FROM " + TABLE_NAME + " WHERE " + ID_FAZENDA + " = " + id;
    }
}
