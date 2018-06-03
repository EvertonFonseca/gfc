/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gcf.control.dao;

import br.com.gcf.control.Database;
import br.com.gcf.model.dto.Alimento_DTO;
import br.com.gcf.model.dto.Apartacao_DTO;
import br.com.gcf.model.dto.Lote_DTO;
import br.com.gcf.model.dto.TipoApartacao_DTO;
import br.com.gcf.model.table.ApartacaoTableModel;
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
public class Apartacao_DAO {
    
    public static List<Apartacao_DTO> readAllApartacoesLote(Lote_DTO lote) {

        List<Apartacao_DTO> apartacaos = new LinkedList<>();

        Database database = new Database();

        ResultSet rs = database.sql(ApartacaoTableModel.selectAll(lote, true));

        if (rs == null) {
            
            database.close();
            return null;
        }

        try {

            while(rs.next()){
                
                Apartacao_DTO ap = new Apartacao_DTO(lote,null);
                ap.setId(rs.getInt(Web.removeEnter(ApartacaoTableModel.ID_APARTACAO)));
                
                apartacaos.add(ap);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        finally{
            
            database.close();
        }
        return apartacaos;
    }
    
    public static List<Apartacao_DTO> readAllApartacoes() {

        List<Apartacao_DTO> apartacaos = new LinkedList<>();

        Database database = new Database();

        ResultSet rs = database.sql(ApartacaoTableModel.selectAllApartacao(true));

        if (rs == null) {
            
            database.close();
            return null;
        }

        try {

            while(rs.next()){
                
                int idLote = rs.getInt("id_lote");
                String nomeLote = rs.getString("nome_lote");
                Lote_DTO lote = new Lote_DTO();
                lote.setId(idLote);
                lote.setNome(nomeLote);
                
                Alimento_DTO racao = new Alimento_DTO(rs.getInt("id_alimento"),rs.getString("nome_alimento"));
                
                TipoApartacao_DTO tipoAp = new TipoApartacao_DTO();
                tipoAp.setId(rs.getInt("id_tipo_apartacao"));
                tipoAp.setNome(rs.getString("nome_tipo_apartacao"));
                tipoAp.setReferencia(rs.getString("referencia_tipo_apartacao"));
                
                Apartacao_DTO ap = new Apartacao_DTO(lote,racao);
                ap.setTipo(tipoAp);
                ap.setId(rs.getInt(Web.removeEnter(ApartacaoTableModel.ID_APARTACAO)));
                ap.setNome(rs.getString(Web.removeEnter(ApartacaoTableModel.NOME_APARTACAO)));
                ap.setDe(rs.getString(Web.removeEnter(ApartacaoTableModel.DE)));
                ap.setAte(rs.getString(Web.removeEnter(ApartacaoTableModel.ATE)));
                
                apartacaos.add(ap);
                
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        finally{
            
            database.close();
        }
        return apartacaos;
    }
    
     public static boolean insert(Apartacao_DTO apartacao) {

        boolean status = true;
        Database database = new Database();

        PreparedStatement st = database.getStatement(ApartacaoTableModel.insert());

        try {

            database.getConn().setAutoCommit(false);

            st.setString(1, apartacao.getNome());
            st.setInt(2, apartacao.getLote().getId());
            st.setInt(3, apartacao.getRacao().getId());
            st.setString(4, apartacao.getDe());
            st.setString(5, apartacao.getAte());
            st.setInt(6, apartacao.getTipo().getId());
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

        PreparedStatement st = database.getStatement(ApartacaoTableModel.delete(id));

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

    public static List<Apartacao_DTO> readAllLotesByFiltro(String campo, String condicao) {
  
        return null;
    }
}
