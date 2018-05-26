/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gcf.control.dao;

import br.com.gcf.control.Database;
import br.com.gcf.model.dto.Alimento_DTO;
import br.com.gcf.model.table.AlimentoTableModel;
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
public class Alimento_DAO {

    public static List<Alimento_DTO> readAllAlimentos() {

        List<Alimento_DTO> alimentos = new LinkedList<>();

        Database database = new Database();
        ResultSet rs = database.sql(AlimentoTableModel.selectAll(false));

        if (rs == null) {

            database.close();
            return null;
        }

        try {
            while (rs.next()) {

                Alimento_DTO alimento = new Alimento_DTO();
                alimento.setId(rs.getInt(Web.removeEnter(AlimentoTableModel.ID_ALIMENTO)));
                alimento.setNome(rs.getString(Web.removeEnter(AlimentoTableModel.NOME_ALIMENTO)));
                alimento.setMistura(rs.getString(Web.removeEnter(AlimentoTableModel.PROCESSO_ALIMENTO)));
                alimento.setAtivo(rs.getBoolean(Web.removeEnter(AlimentoTableModel.ATIVO_ALIMENTO)));

                alimentos.add(alimento);

            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;

        } finally {

            database.close();
        }

        return alimentos;
    }

    public static Alimento_DTO readAlimentoById(int id) {

        Alimento_DTO alimento = null;

        Database database = new Database();
        ResultSet rs = database.sql(AlimentoTableModel.selectById(id));

        if (rs == null) {

            database.close();
            return null;
        }

        try {
            while (rs.next()) {

                alimento = new Alimento_DTO();
                alimento.setNome(rs.getString(Web.removeEnter(AlimentoTableModel.NOME_ALIMENTO)));
                alimento.setMistura(rs.getString(Web.removeEnter(AlimentoTableModel.PROCESSO_ALIMENTO)));
                alimento.setAtivo(rs.getBoolean(Web.removeEnter(AlimentoTableModel.ATIVO_ALIMENTO)));

            }
        } catch (SQLException ex) {

            ex.printStackTrace();
            return null;

        } finally {

            database.close();
        }

        return alimento;
    }

    public static boolean isNameExist(String nome) {

        boolean status = true;

        Database database = new Database();
        ResultSet rs = database.sql(AlimentoTableModel.isExistName(nome));

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
    
     public static boolean insert(Alimento_DTO alimento) {

        boolean status = true;
        Database database = new Database();

        PreparedStatement st = database.getStatement(AlimentoTableModel.insert());

        try {

            database.getConn().setAutoCommit(false);

            st.setString(1, alimento.getNome());
            st.setString(2, alimento.getMistura());
            st.setBoolean(3, alimento.isAtivo());
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
     
     public static boolean delete(int id) {

        boolean status = true;
        Database database = new Database();

         PreparedStatement st = database.getStatement(AlimentoTableModel.delete(id));

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
