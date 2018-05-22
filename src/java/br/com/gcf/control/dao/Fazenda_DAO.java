/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gcf.control.dao;

import br.com.gcf.control.Database;
import br.com.gcf.model.dto.Fazenda_DTO;
import br.com.gcf.model.table.FazendaTableModel;
import br.com.gcf.model.table.LoteTableModel;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Windows
 */
public class Fazenda_DAO {

    public static List<Fazenda_DTO> readAllFazendas() {

        List<Fazenda_DTO> fazendas = new LinkedList<>();

        Database database = new Database();

        ResultSet rs = database.sql(FazendaTableModel.selectAllGroup());

        if (rs == null) {

            database.close();
            return null;
        }

        try {

            while (rs.next()) {

                Fazenda_DTO fazenda = new Fazenda_DTO();
                fazenda.setId(rs.getInt(1));
                fazenda.setNome(rs.getString(2));
                fazenda.setLatitude(rs.getString(3));
                fazenda.setLongitude(rs.getString(4));
                fazenda.setQuantidadeLotes(rs.getInt(5));

                fazendas.add(fazenda);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {

            database.close();
        }
        return fazendas;
    }

    public static boolean insert(Fazenda_DTO fazenda) {

        boolean status = true;
        Database database = new Database();

        PreparedStatement st = database.getStatement(FazendaTableModel.insert());

        try {

            database.getConn().setAutoCommit(false);

            st.setString(1, fazenda.getNome());
            st.setString(2, fazenda.getLatitude());
            st.setString(3, fazenda.getLongitude());
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

        PreparedStatement st = database.getStatement(FazendaTableModel.delete(id));

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
