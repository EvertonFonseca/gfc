/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gcf.control.dao;

import br.com.gcf.control.Database;
import br.com.gcf.model.dto.TpUsuario_DTO;
import br.com.gcf.model.dto.Usuario_DTO;
import br.com.gcf.model.table.ApartacaoTableModel;
import br.com.gcf.model.table.CategoriaTableModel;
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
        ResultSet rs = database.sql(UsuarioTableModel.selectAll(true));

        if (rs == null) {

            database.close();
            return null;
        }

        try {
            while (rs.next()) {

                Usuario_DTO usuario = new Usuario_DTO();
                usuario.setId(rs.getInt(Web.removeEnter(UsuarioTableModel.ID_USUARIO)));
                usuario.setNome(rs.getString(Web.removeEnter(UsuarioTableModel.NOME_USUARIO)));
                usuario.setPassword(rs.getString(Web.removeEnter(UsuarioTableModel.SENHA_USUARIO)));
                usuario.setAtivo(rs.getBoolean(Web.removeEnter(UsuarioTableModel.ATIVO_USUARIO)));
                usuario.setData(rs.getString(Web.removeEnter(UsuarioTableModel.DATA_USUARIO)));
                usuario.setEmail(rs.getString(Web.removeEnter(UsuarioTableModel.EMAIL_USUARIO)));

                //creia o obj tipo usuario
                TpUsuario_DTO tpUser = new TpUsuario_DTO();
                tpUser.setId(rs.getInt(Web.removeEnter(CategoriaTableModel.ID_USUARIO)));
                tpUser.setNome(rs.getString(Web.removeEnter(CategoriaTableModel.NOME_USUARIO)));

                usuario.setTipoCategoria(tpUser);

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
        ResultSet rs = database.sql(UsuarioTableModel.readUserByNameOrEmail(nome));

        if (rs == null) {

            database.close();
            return null;
        }

        try {
            while (rs.next()) {

                usuario = new Usuario_DTO();
                usuario.setNome(rs.getString(Web.removeEnter(UsuarioTableModel.NOME_USUARIO)));
                usuario.setEmail(rs.getString(Web.removeEnter(UsuarioTableModel.EMAIL_USUARIO)));
                usuario.setPassword(rs.getString(Web.removeEnter(UsuarioTableModel.SENHA_USUARIO)));

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

    public static boolean insert(Usuario_DTO user) {

        boolean status = true;
        Database database = new Database();

        PreparedStatement st = database.getStatement(UsuarioTableModel.insert());

        try {

            database.getConn().setAutoCommit(false);
            st.setString(1, user.getNome());
            st.setString(2, user.getPassword());
            st.setString(3, user.getData());
            st.setInt(4, user.getTipoCategoria().getId());
            st.setString(5, user.getEmail());
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

    public static boolean isEmailExist(String email) {

        boolean status = true;

        Database database = new Database();
        ResultSet rs = database.sql(UsuarioTableModel.isExistEmail(email));

        if (rs == null) {

            database.close();
            return status;
        }

        try {

            rs.last();
            status = rs.getRow() > 0;
            rs.beforeFirst();

        } catch (SQLException ex) {

            ex.printStackTrace();
            return status;

        } finally {

            database.close();
        }

        return status;
    }

    public static boolean isNameExist(String nome) {

        boolean status = true;

        Database database = new Database();
        ResultSet rs = database.sql(UsuarioTableModel.isExistName(nome));

        if (rs == null) {

            database.close();
            return status;
        }

        try {

            rs.last();
            status = rs.getRow() > 0;
            rs.beforeFirst();

        } catch (SQLException ex) {

            ex.printStackTrace();
            return status;

        } finally {

            database.close();
        }

        return status;
    }

    public static Usuario_DTO isLoginSignInValid(String nomeOuEmail, String senha) {

        Database database = new Database();
        ResultSet rs = database.sql(UsuarioTableModel.checkLoginSignIn(nomeOuEmail, senha));
        Usuario_DTO usuario = null;

        if (rs == null) {

            database.close();
            return null;
        }

        try {
            while (rs.next()) {

                usuario = new Usuario_DTO();
                usuario.setId(rs.getInt(Web.removeEnter(UsuarioTableModel.ID_USUARIO)));
                usuario.setNome(rs.getString(Web.removeEnter(UsuarioTableModel.NOME_USUARIO)));
                usuario.setAtivo(rs.getBoolean(Web.removeEnter(UsuarioTableModel.ATIVO_USUARIO)));
                usuario.setPassword(rs.getString(Web.removeEnter(UsuarioTableModel.SENHA_USUARIO)));

            }
        } catch (SQLException ex) {

            ex.printStackTrace();
            return null;

        } finally {

            database.close();
        }

        return usuario;
    }

    public static boolean updateAtivo(int idUser, boolean isAtivo) {

        boolean status = true;
        Database database = new Database();

        PreparedStatement st = database.getStatement(UsuarioTableModel.updateAtivoUsuario(idUser, isAtivo));

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

    public static void updateAllOffline() {

        Database database = new Database();
        PreparedStatement st = database.getStatement(UsuarioTableModel.updateOffLineAll());

        try {

            database.getConn().setAutoCommit(false);
            st.executeUpdate();

            database.getConn().commit();

        } catch (SQLException ex) {
            try {
                database.getConn().rollback();
            } catch (SQLException ex1) {
                ex1.printStackTrace();
            }
            ex.printStackTrace();

        } finally {

            database.close();
        }
    }
}
