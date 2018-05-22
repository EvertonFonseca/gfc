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
public class MarcasTable {

    public static String TABLE_NAME = "public.marcas";
    public static String ID_MARCA = "\"id_marca\"";
    public static String NOME_MARCA = "\"nome_marca\"";
    public static String DESCRICAO_MARCA = "\"descricao_marca\"";
    public static String IMAGE_MARCA = "\"image_marca\"";
    public static String TIPO_IMAGE_MARCA = "\"tipo_image_marca\"";

    public static String selectAll(boolean isOrderBy) {

        return "select * from " + TABLE_NAME + (isOrderBy ? " ORDER BY " +ID_MARCA+"" : "");
    }

    public static String insert() {

        return "INSERT INTO "+TABLE_NAME+"(\n"
                + "  "+ NOME_MARCA + "," + DESCRICAO_MARCA + "," + IMAGE_MARCA + "," + TIPO_IMAGE_MARCA + ")\n"
                + "    VALUES (?, ?, ?, ?);";
    }

    public static String update(int idMarca) {

        return "UPDATE "+TABLE_NAME+"\n"
                + "   SET " + NOME_MARCA + "=?," + DESCRICAO_MARCA + "=?," + IMAGE_MARCA + "=?," + TIPO_IMAGE_MARCA + "=? \n"
                + " WHERE " + ID_MARCA + " = " + idMarca;
    }
    
    public static String delete(int id){
        
        return "DELETE FROM "+TABLE_NAME+" WHERE "+ID_MARCA+" = "+id;
    }
}
