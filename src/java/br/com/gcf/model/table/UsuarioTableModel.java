/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gcf.model.table;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Windows
 */
public class UsuarioTableModel {

    public static String TABLE_NAME = "usuario";
    public static String ID_USUARIO = "\"id_usuario\"";
    public static String NOME_USUARIO = "\"nome_usuario\"";
    public static String SENHA_USUARIO = "\"senha_usuario\"";
    public static String ATIVO_USUARIO = "\"ativo_usuario\"";
    public static String CATEGORIA_USUARIO = "\"id_tp_usuario\"";
    public static String DATA_USUARIO = "\"data_usuario\"";
    public static String EMAIL_USUARIO = "\"email_usuario\"";

    public static String[] COLUMNS = {ID_USUARIO};

    public static String selectAll(boolean isOrderBy) {

        return "select * "
                + "from usuario us "
                + "inner join tipo_usuario tp on us.id_tp_usuario = tp.id_tp_usuario "
                + (isOrderBy ? " ORDER BY " + ID_USUARIO + "" : "");
    }

    public static String readUserByNameOrEmail(String emailOuNome) {

        if (isEmailValid(emailOuNome)) {
            return "select nome_usuario,email_usuario,senha_usuario \n"
                    + "from usuario \n"
                    + "where email_usuario = '" + emailOuNome + "'";

        } else {

            return "select nome_usuario,email_usuario,senha_usuario \n"
                    + "from usuario \n"
                    + "where nome_usuario = '" + emailOuNome + "'";

        }
    }

    public static String selectAllByName(String nome) {

        return "select * from " + TABLE_NAME + " WHERE nome_usuario = '" + nome + "'";
    }

    public static String selectAllSorting(int index) {

        return "select * from " + TABLE_NAME + " ORDER BY " + COLUMNS[index] + " DESC";
    }

    public static String insert() {

        return "INSERT INTO public.usuario(\n"
                + "nome_usuario, senha_usuario, data_usuario,id_tp_usuario, email_usuario) "
                + " VALUES (?, ?, ?, ?, ?)";
    }

    public static String updateAtivoUsuario(int idUser, boolean status) {

        return "UPDATE public.usuario SET ativo_usuario=" + status + " WHERE id_usuario = " + idUser;
    }

    public static String updateOffLineAll() {

        return "UPDATE public.usuario SET ativo_usuario=" + false;
    }

    public static String findName(String nameLote) {

        return "SELECT \n"
                + "               usuarios.id_usuario,\n"
                + "               usuarios.nome_usuario,\n"
                + "               usuarios.data_usuario,\n"
                + "               usuarios.peso_minimo_usuario,\n"
                + "               usuarios.peso_medio_usuario,\n"
                + "               usuarios.peso_maximo_usuario,\n"
                + "               usuarios.peso_total_usuario,\n"
                + "               usuarios.quantidade_usuario,\n"
                + "               usuarios.id_alimento,\n"
                + "               usuarios.arroba_usuario,\n"
                + "               usuarios.carcaca_usuario\n"
                + "               FROM\n"
                + "               public.usuarios where usuarios.nome_usuario = '" + nameLote + "'";
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

    public static String isExistEmail(String email) {

        return "select email_usuario from usuario where Upper(email_usuario) ='" + email.toUpperCase() + "'";
    }

    public static String isExistName(String nome) {

        return "select nome_usuario from usuario where Upper(nome_usuario) ='" + nome.toUpperCase() + "'";
    }

    public static String checkLoginSignIn(String emailOuNome, String senha) {

        if (isEmailValid(emailOuNome)) {
            return "select id_usuario,email_usuario,senha_usuario,ativo_usuario \n"
                    + "from usuario \n"
                    + "where email_usuario = '" + emailOuNome + "' AND senha_usuario = '" + senha + "'";

        } else {

            return "select id_usuario,nome_usuario,senha_usuario,ativo_usuario \n"
                    + "from usuario \n"
                    + "where nome_usuario = '" + emailOuNome + "' AND senha_usuario = '" + senha + "'";

        }
    }

    public static boolean isEmailValid(String email) {

        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
