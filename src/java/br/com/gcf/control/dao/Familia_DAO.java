/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gcf.control.dao;

import br.com.gcf.control.Database;
import br.com.gcf.control.utils.LoadFile;
import br.com.gcf.model.dto.Animal_DTO;
import br.com.gcf.model.dto.Familia_DTO;
import br.com.gcf.model.dto.Usuario_DTO;
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
public class Familia_DAO {

    public static List<Familia_DTO> readAllFamilia() {

        List<Familia_DTO> familias = new LinkedList<>();

        Database database = new Database();
        ResultSet rs = database.sql(new LoadFile().loadFile("/br/com/gcf/control/dao/sql/familia/readAllFamilia.sql"));

        if (rs == null) {

            database.close();
            return null;
        }

        try {
            while (rs.next()) {

                Familia_DTO familia = new Familia_DTO();
                familia.setId(rs.getInt("id_familia"));
                familia.setNome(rs.getString("nome_familia"));
                
                //Pai
                Animal_DTO pai = new Animal_DTO();
                pai.setId(rs.getInt("pai_id"));
                pai.setNome(rs.getString("pai_nome"));
                pai.setTag(rs.getString("pai_tag"));
                pai.setSisbov(rs.getString("pai_sisbov"));

                //Mae
                Animal_DTO mae = new Animal_DTO();
                mae.setId(rs.getInt("mae_id"));
                mae.setNome(rs.getString("mae_nome"));
                mae.setTag(rs.getString("mae_tag"));
                mae.setSisbov(rs.getString("mae_sisbov"));
                
                familia.setPai(pai);
                familia.setMae(mae);

                familias.add(familia);

            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;

        } finally {

            database.close();
        }

        return familias;
    }

    public static Familia_DTO readUsuarioByName(String nome) {

        Familia_DTO familia = null;

        Database database = new Database();
        ResultSet rs = database.sql(UsuarioTableModel.selectAll(true));

        if (rs == null) {

            database.close();
            return null;
        }

        try {
            while (rs.next()) {

                familia = new Familia_DTO();
                familia.setId(rs.getInt(Web.removeEnter(CategoriaTableModel.ID_USUARIO)));
                familia.setNome(rs.getString(Web.removeEnter(CategoriaTableModel.NOME_USUARIO)));

            }
        } catch (SQLException ex) {

            ex.printStackTrace();
            return null;

        } finally {

            database.close();
        }

        return familia;
    }

    public static boolean delete(int id) {

        boolean status = true;
        Database database = new Database();

        PreparedStatement st = database.getStatement(CategoriaTableModel.delete(id));

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

   public static boolean insert(Familia_DTO tpUsuario) {

        boolean status = true;
        Database database = new Database();

        PreparedStatement st = database.getStatement(CategoriaTableModel.insert());

        try {

            database.getConn().setAutoCommit(false);

            st.setString(1, tpUsuario.getNome());
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
