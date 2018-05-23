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
public class CategoriaTableModel {

    public static String TABLE_NAME = "tipo_usuario";
    public static String ID_USUARIO = "\"id_tp_usuario\"";
    public static String NOME_USUARIO = "\"nome_tp_usuario\"";

    public static String[] COLUMNS = {ID_USUARIO};

    public static String selectAll(boolean isOrderBy) {

        return "select * from " + TABLE_NAME + (isOrderBy ? " ORDER BY " + ID_USUARIO + "" : "");
    }
    
     public static String selectAllByName(String nome) {

        return "select * from " + TABLE_NAME +" WHERE nome_usuario = '"+nome+"'";
    }

    public static String selectAllSorting(int index) {

        return "select * from " + TABLE_NAME + " ORDER BY " + COLUMNS[index] + " DESC";
    }

    public static String insert() {

        return "INSERT INTO public.tipo_usuario(\n"
                + "            nome_tp_usuario)\n"
                + "    VALUES (?)";
    }

    public static String findName(String nameLote){
        
        return "SELECT \n" +
"               usuarios.id_usuario,\n" +
"               usuarios.nome_usuario,\n" +
"               usuarios.data_usuario,\n" +
"               usuarios.peso_minimo_usuario,\n" +
"               usuarios.peso_medio_usuario,\n" +
"               usuarios.peso_maximo_usuario,\n" +
"               usuarios.peso_total_usuario,\n" +
"               usuarios.quantidade_usuario,\n" +
"               usuarios.id_alimento,\n" +
"               usuarios.arroba_usuario,\n" +
"               usuarios.carcaca_usuario\n" +
"               FROM\n" +
"               public.usuarios where usuarios.nome_usuario = '"+nameLote+"'";
    }
    
    public static String update(int id) {

        return "UPDATE "
                + "public.usuarios"
                + " SET nome_usuario=?,"
                + " id_alimento=?, \n"
                + " arroba_usuario=?,"
                + " carcaca_usuario=?\n"
                + " WHERE " + ID_USUARIO + " = " + id;
    }

    public static String delete(int id) {

        return "DELETE FROM " + TABLE_NAME + " WHERE " + ID_USUARIO + " = " + id;
    }
}
