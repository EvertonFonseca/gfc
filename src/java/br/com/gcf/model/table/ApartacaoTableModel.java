/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gcf.model.table;

import br.com.gcf.model.dto.Lote_DTO;

/**
 *
 * @author Windows
 */
public class ApartacaoTableModel {

    public static String TABLE_NAME = "apartacoes";
    public static String ID_APARTACAO = "\"id_apartacao\"";
    public static String NOME_APARTACAO = "\"nome_apartacao\"";
    public static String ID_LOTE = "\"id_lote\"";
    public static String ID_ALIMENTO = "\"id_alimento\"";
    public static String DE = "\"de_apartacao\"";
    public static String ATE = "\"ate_apartacao\"";
    public static String TIPO = "\"tipo_apartacao\"";
    public static String[] COLUMNS = {ID_LOTE};

    public static String selectAll(Lote_DTO lote, boolean isOrderBy) {

        return "select * from " + TABLE_NAME + " where " + ID_LOTE + "=" + lote.getId() + (isOrderBy ? " ORDER BY " + ID_LOTE + "" : "");
    }

    public static String selectAllApartacao(boolean isOrderBy) {

        return "SELECT \n"
                + "apartacoes.id_apartacao, \n"
                + "apartacoes.nome_apartacao, \n"
                + "apartacoes.de_apartacao, \n"
                + "apartacoes.ate_apartacao, \n"
                + "lotes.id_lote, \n"
                + "lotes.nome_lote, \n"
                + "tipo_apartacao.id_tipo_apartacao, \n"
                + "tipo_apartacao.nome_tipo_apartacao, \n"
                + "tipo_apartacao.referencia_tipo_apartacao, \n"
                + "alimento.id_alimento,\n"
                + "alimento.nome_alimento\n"
                + "FROM \n"
                + "public.lotes, \n"
                + "public.apartacoes, \n"
                + "public.tipo_apartacao, \n"
                + "public.alimento\n"
                + "WHERE \n"
                + "apartacoes.id_lote = lotes.id_lote AND\n"
                + "apartacoes.id_tipo_apartacao = tipo_apartacao.id_tipo_apartacao AND\n"
                + "apartacoes.id_alimento = alimento.id_alimento" + (isOrderBy ? " ORDER BY " + ID_APARTACAO + "" : "");
    }

    public static String selectAllSorting(int index) {

        return "select * from " + TABLE_NAME + " ORDER BY " + COLUMNS[index] + " DESC";
    }

    public static String insert() {

        return "INSERT INTO public.apartacoes(\n"
                + "            nome_apartacao, id_lote, id_alimento, de_apartacao, \n"
                + "            ate_apartacao, id_tipo_apartacao)\n"
                + "    VALUES (?, ?, ?, ?, \n"
                + "            ?, ?)";
    }

    public static String update(int id) {

        return "UPDATE public.apartacoes\n"
                + "   SET nome_apartacao=?, id_lote=?, id_alimento=?, de_apartacao=?, \n"
                + "       ate_apartacao=?, id_tipo_apartacao=?, status_apartacao=?\n"
                + " WHERE " + ID_APARTACAO + " = " + id;
    }

    public static String delete(int id) {

        return "DELETE FROM " + TABLE_NAME + " WHERE " + ID_APARTACAO + " = " + id;
    }
}
