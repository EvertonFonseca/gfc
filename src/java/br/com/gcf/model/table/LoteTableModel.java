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
public class LoteTableModel {

    public static String TABLE_NAME = "lotes";
    public static String ID_LOTE = "\"id_lote\"";
    public static String NOME_LOTE = "\"nome_lote\"";
    public static String DATA_LOTE = "\"data_lote\"";
    public static String PESO_MINIMO_LOTE = "\"peso_minimo_lote\"";
    public static String PESO_MEDIO_LOTE = "\"peso_medio_lote\"";
    public static String PESO_MAXIMO_LOTE = "\"peso_maximo_lote\"";
    public static String PESO_TOTAL_LOTE = "\"peso_total_lote\"";
    public static String QUANTIDADE_LOTE= "\"quantidade_lote\"";
    public static String ID_ALIMENTO = "\"id_alimento\"";
    public static String ARROBA_LOTE = "\"arroba_lote\"";
    public static String CARCACA_LOTE  = "\"carcaca_lote\"";
    public static String[] COLUMNS = {ID_LOTE};

    public static String selectAll(boolean isOrderBy) {

        return "select * from " + TABLE_NAME + (isOrderBy ? " ORDER BY " + ID_LOTE + "" : "");
    }

    public static String selectLoteFazenda() {

        return "select * from lotes L \n"
                + "left join (select COUNT(id_apartacao) as MAX, id_lote \n"
                + "from apartacoes GROUP BY id_lote) A \n"
                + "on L.id_lote = A.id_lote left join alimento R on L.id_alimento = R.id_alimento order by L.id_lote";


    }
    public static String selectLoteFazendaByFiltro(String campo,String condicao) {

        return "select * from lotes L \n"
                + "left join (select COUNT(id_apartacao) as MAX, id_lote \n"
                + "from apartacoes GROUP BY id_lote) A \n"
                + "on L.id_lote = A.id_lote left join alimento R on L.id_alimento = R.id_alimento where Upper("+campo+") LIKE "+condicao+" order by L.id_lote";

    }

    public static String selectAllSorting(int index) {

        return "select * from " + TABLE_NAME + " ORDER BY " + COLUMNS[index] + " DESC";
    }

    public static String insert() {

        return "INSERT INTO public.lotes(\n"
                + "nome_lote, "
                + "data_lote, "
                + "id_alimento, "
                 + "arroba_lote, "
                 + "carcaca_lote"
                + ") "
                + "VALUES (?,?,?,?,?)";
    }

    public static String findName(String nameLote){
        
        return "SELECT \n" +
"               lotes.id_lote,\n" +
"               lotes.nome_lote,\n" +
"               lotes.data_lote,\n" +
"               lotes.peso_minimo_lote,\n" +
"               lotes.peso_medio_lote,\n" +
"               lotes.peso_maximo_lote,\n" +
"               lotes.peso_total_lote,\n" +
"               lotes.quantidade_lote,\n" +
"               lotes.id_alimento,\n" +
"               lotes.arroba_lote,\n" +
"               lotes.carcaca_lote\n" +
"               FROM\n" +
"               public.lotes where lotes.nome_lote = '"+nameLote+"'";
    }
    
    public static String update(int id) {

        return "UPDATE "
                + "public.lotes"
                + " SET nome_lote=?,"
                + " id_alimento=?, \n"
                + " arroba_lote=?,"
                + " carcaca_lote=?\n"
                + " WHERE " + ID_LOTE + " = " + id;
    }

    public static String isExistName(String nome){
        
        return "select " + ID_LOTE + " from public.lotes where lotes.nome_lote = '"+nome+"'";
    }
    
    public static String delete(int id) {

        return "DELETE FROM " + TABLE_NAME + " WHERE " + ID_LOTE + " = " + id;
    }
}
