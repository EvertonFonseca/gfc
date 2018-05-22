/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gcf.control.dao;

import br.com.gcf.control.Database;
import br.com.gcf.model.dto.TipoApartacao_DTO;
import br.com.gcf.model.table.ApartacaoTableModel;
import br.com.gcf.model.table.TipoApartacaoTableModel;
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
public class TipoApartacao_DAO {
    
    public static List<TipoApartacao_DTO> readAllTipoApartacao() {

        List<TipoApartacao_DTO> apartacaos = new LinkedList<>();

        Database database = new Database();

        ResultSet rs = database.sql(TipoApartacaoTableModel.selectAll(true));

        if (rs == null) {
            
            database.close();
            return null;
        }

        try {

            while(rs.next()){
                
                TipoApartacao_DTO ap = new TipoApartacao_DTO();
                ap.setId(rs.getInt(Web.removeEnter(TipoApartacaoTableModel.ID_TIPO_APARTACAO)));
                ap.setNome(rs.getString(Web.removeEnter(TipoApartacaoTableModel.NOME_TIPO_APARTACAO)));
                
                apartacaos.add(ap);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            database.close();
            return null;
        }
        finally{
            
            database.close();
        }
        return apartacaos;
    }
    
     public static boolean insert(TipoApartacao_DTO apartacao) {

        boolean status = true;
        Database database = new Database();

        PreparedStatement st = database.getStatement(ApartacaoTableModel.insert());

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
