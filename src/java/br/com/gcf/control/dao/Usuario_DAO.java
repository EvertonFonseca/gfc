/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gcf.control.dao;

import br.com.gcf.control.Database;
import br.com.gcf.model.dto.Usuario_DTO;
import br.com.gcf.model.table.LoteTableModel;
import br.com.gcf.model.table.UsuarioTableModel;
import br.com.gcf.view.Web;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Windows
 */
public class Usuario_DAO {

    public static List<Usuario_DTO> readAllUsuarios() {

        List<Usuario_DTO> usuarios = new LinkedList<>();

        Database database = new Database();
        ResultSet rs = database.sql(UsuarioTableModel.selectAll(false));

        if (rs == null) {

            database.close();
            return null;
        }

        try {
            while (rs.next()) {

                Usuario_DTO usuario = new Usuario_DTO();
                usuario.setId(rs.getInt(Web.removeEnter(UsuarioTableModel.ID_USUARIO)));
                usuario.setNome(rs.getString(Web.removeEnter(UsuarioTableModel.NOME_USUARIO)));

                usuarios.add(usuario);

            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;

        } finally {

            database.close();
        }

        return usuarios;
    }

    public static Usuario_DTO readUsuarioByName(String nome) {

        Usuario_DTO usuario = null;

        Database database = new Database();
        ResultSet rs = database.sql(UsuarioTableModel.selectAll(true));

        if (rs == null) {

            database.close();
            return null;
        }

        try {
            while (rs.next()) {

                usuario = new Usuario_DTO();
                usuario.setId(rs.getInt(Web.removeEnter(UsuarioTableModel.ID_USUARIO)));
                usuario.setNome(rs.getString(Web.removeEnter(UsuarioTableModel.NOME_USUARIO)));

            }
        } catch (SQLException ex) {

            ex.printStackTrace();
            return null;

        } finally {

            database.close();
        }

        return usuario;
    }

    public static boolean delete(int id) {

        boolean status = true;
        Database database = new Database();

        PreparedStatement st = database.getStatement(UsuarioTableModel.delete(id));

        try {

            database.getConn().setAutoCommit(false);
            st.executeUpdate();

            database.getConn().commit();

        } catch (SQLException ex) {
            status = false;
            try {
                database.getConn().rollback();
            } catch (SQLException ex1) {
                ex1.printStackTrace();
            }
            ex.printStackTrace();

        } finally {

            database.close();
        }
        return status;
    }
}
