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
public class TipoApartacaoTableModel {

    public static String TABLE_NAME = "tipo_apartacao";
    public static String ID_TIPO_APARTACAO = "\"id_tipo_apartacao\"";
    public static String NOME_TIPO_APARTACAO = "\"nome_tipo_apartacao\"";

    public static String selectAll(boolean isOrderBy) {

        return "select * from " + TABLE_NAME + (isOrderBy ? " ORDER BY " + ID_TIPO_APARTACAO + "" : "");
    }

    public static String insert() {

        return "INSERT INTO public.tipo_apartacao(\n"
                + "    nome_tipo_apartacao)\n"
                + "    VALUES (?)";
    }

    public static String update(int idCliente) {

        return "";
    }

    public static String delete(int id) {

        return "DELETE FROM " + TABLE_NAME + " WHERE " + ID_TIPO_APARTACAO + " = " + id;
    }
}
