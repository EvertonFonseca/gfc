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
import br.com.gcf.model.table.LoteTableModel;
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
public class Lote_DAO {

    public static List<Lote_DTO> readAllLotesFazendas() {

        List<Lote_DTO> lotes = new LinkedList<>();

        Database database = new Database();

        ResultSet rs = database.sql(LoteTableModel.selectLoteFazenda());

        if (rs == null) {

            database.close();
            return null;
        }

        try {

            while (rs.next()) {

                Lote_DTO lote = new Lote_DTO();
                lote.setId(rs.getInt(Web.removeEnter(LoteTableModel.ID_LOTE)));
                lote.setNome(rs.getString(Web.removeEnter(LoteTableModel.NOME_LOTE)));
                lote.setData(rs.getString(Web.removeEnter(LoteTableModel.DATA_LOTE)));
                lote.setPesoMinimo(rs.getDouble(Web.removeEnter(LoteTableModel.PESO_MINIMO_LOTE)));
                lote.setPesoMedio(rs.getDouble(Web.removeEnter(LoteTableModel.PESO_MEDIO_LOTE)));
                lote.setPesoMaximo(rs.getDouble(Web.removeEnter(LoteTableModel.PESO_MAXIMO_LOTE)));
                lote.setPesoTotal(rs.getDouble(Web.removeEnter(LoteTableModel.PESO_TOTAL_LOTE)));
                lote.setQuantidade(rs.getInt(Web.removeEnter(LoteTableModel.QUANTIDADE_LOTE)));
                lote.setQuantidadeApartacao(rs.getInt("MAX")); // quantidade de apartcaoes que o lote possui
                lote.setRacao(new Alimento_DTO(rs.getInt("id_alimento"), rs.getString("nome_alimento"))); // quantidade de apartcaoes que o lote possui
                lote.setPesoDaCarcaca(rs.getDouble(Web.removeEnter(LoteTableModel.CARCACA_LOTE)));
                lote.setArroba(rs.getDouble(Web.removeEnter(LoteTableModel.ARROBA_LOTE)));
                lotes.add(lote);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {

            database.close();
        }
        return lotes;
    }

    public static List<Lote_DTO> readAllLotesByFiltro(String campo, String condicao) {

        if (campo.equals("codigo".toUpperCase())) {
           
        } else if (campo.equals("lote".toUpperCase())) {
            campo = "nome_lote";
            condicao = "'"+condicao+"%'";
        } else if (campo.equals("data".toUpperCase())) {

        } else if (campo.equals("peso minimo".toUpperCase())) {

        } else if (campo.equals("peso medio".toUpperCase())) {

        } else if (campo.equals("peso maximo".toUpperCase())) {

        } else if (campo.equals("peso total".toUpperCase())) {

        } else if (campo.equals("ração".toUpperCase())) {

        } else if (campo.equals("peso carcaça".toUpperCase())) {

        } else if (campo.equals("arroba".toUpperCase())) {

        } else if (campo.equals("apartações".toUpperCase())) {

        } else if (campo.equals("animais".toUpperCase())) {

        }

        List<Lote_DTO> lotes = new LinkedList<>();

        Database database = new Database();

        ResultSet rs = database.sql(LoteTableModel.selectLoteFazendaByFiltro(campo, condicao));

        if (rs == null) {

            database.close();
            return null;
        }

        try {

            while (rs.next()) {

                Lote_DTO lote = new Lote_DTO();
                lote.setId(rs.getInt(Web.removeEnter(LoteTableModel.ID_LOTE)));
                lote.setNome(rs.getString(Web.removeEnter(LoteTableModel.NOME_LOTE)));
                lote.setData(rs.getString(Web.removeEnter(LoteTableModel.DATA_LOTE)));
                lote.setPesoMinimo(rs.getDouble(Web.removeEnter(LoteTableModel.PESO_MINIMO_LOTE)));
                lote.setPesoMedio(rs.getDouble(Web.removeEnter(LoteTableModel.PESO_MEDIO_LOTE)));
                lote.setPesoMaximo(rs.getDouble(Web.removeEnter(LoteTableModel.PESO_MAXIMO_LOTE)));
                lote.setPesoTotal(rs.getDouble(Web.removeEnter(LoteTableModel.PESO_TOTAL_LOTE)));
                lote.setQuantidade(rs.getInt(Web.removeEnter(LoteTableModel.QUANTIDADE_LOTE)));
                lote.setQuantidadeApartacao(rs.getInt("MAX")); // quantidade de apartcaoes que o lote possui
                lote.setRacao(new Alimento_DTO(rs.getInt("id_alimento"), rs.getString("nome_alimento"))); // quantidade de apartcaoes que o lote possui
                lote.setPesoDaCarcaca(rs.getDouble(Web.removeEnter(LoteTableModel.CARCACA_LOTE)));
                lote.setArroba(rs.getDouble(Web.removeEnter(LoteTableModel.ARROBA_LOTE)));
                lotes.add(lote);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {

            database.close();
        }
        return lotes;
    }

    public static boolean insert(Lote_DTO lote) {

        boolean status = true;
        Database database = new Database();

        PreparedStatement st = database.getStatement(LoteTableModel.insert());

        try {

            database.getConn().setAutoCommit(false);

            st.setString(1, lote.getNome());
            st.setString(2, lote.getData());
            st.setInt(3, lote.getRacao().getId());
            st.setDouble(4, lote.getPesoDaCarcaca());
            st.setDouble(5, lote.getArroba());
            st.executeUpdate();

            database.getConn().commit();

//            lote = findByName(lote.getNome());
//            
//            //insert Apartacao Padrao
//            createNewApartacao(lote);
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

    public static boolean update(Lote_DTO lote) {

        boolean status = true;
        Database database = new Database();

        PreparedStatement st = database.getStatement(LoteTableModel.update(lote.getId()));

        try {

            database.getConn().setAutoCommit(false);

            st.setString(1, lote.getNome());
            st.setInt(2, lote.getRacao().getId());
            st.setDouble(3, lote.getPesoDaCarcaca());
            st.setDouble(4, lote.getArroba());
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

    public static Lote_DTO findByName(String nameLote) {

        Database database = new Database();
        Lote_DTO lote = new Lote_DTO();
        ResultSet rs = database.sql(LoteTableModel.findName(nameLote));

        if (rs == null) {

            database.close();
            return null;
        }

        try {

            while (rs.next()) {

                lote.setId(rs.getInt(Web.removeEnter(LoteTableModel.ID_LOTE)));
                lote.setNome(rs.getString(Web.removeEnter(LoteTableModel.NOME_LOTE)));
                lote.setData(rs.getString(Web.removeEnter(LoteTableModel.DATA_LOTE)));
                lote.setPesoMinimo(rs.getDouble(Web.removeEnter(LoteTableModel.PESO_MINIMO_LOTE)));
                lote.setPesoMedio(rs.getDouble(Web.removeEnter(LoteTableModel.PESO_MEDIO_LOTE)));
                lote.setPesoMaximo(rs.getDouble(Web.removeEnter(LoteTableModel.PESO_MAXIMO_LOTE)));
                lote.setPesoTotal(rs.getDouble(Web.removeEnter(LoteTableModel.PESO_TOTAL_LOTE)));
                lote.setQuantidade(rs.getInt(Web.removeEnter(LoteTableModel.QUANTIDADE_LOTE)));

                lote.setApartacoes(Apartacao_DAO.readAllApartacoesLote(lote));
                int quantAp = lote.getApartacoes() != null ? lote.getApartacoes().size() : 0;

                lote.setQuantidadeApartacao(quantAp); // quantidade de apartcaoes que o lote possui
                lote.setRacao(Alimento_DAO.readAlimentoById(rs.getInt(Web.removeEnter(LoteTableModel.ID_ALIMENTO)))); // quantidade de apartcaoes que o lote possui
                lote.setPesoDaCarcaca(rs.getDouble(Web.removeEnter(LoteTableModel.CARCACA_LOTE)));
                lote.setArroba(rs.getDouble(Web.removeEnter(LoteTableModel.ARROBA_LOTE)));
            }

        } catch (Exception e) {

            e.printStackTrace();
            return null;
        } finally {

            database.close();
        }

        return lote;

    }

    private static void createNewApartacao(Lote_DTO lote) {

        Apartacao_DTO apartacao = new Apartacao_DTO(lote, lote.getRacao());

        try {

            boolean status = Apartacao_DAO.insert(apartacao);

            if (!status) {
                throw new Exception("Não foi possivel criar apartação default para lote!");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean delete(int id) {

        boolean status = true;
        Database database = new Database();

        PreparedStatement st = database.getStatement(LoteTableModel.delete(id));

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

    public static boolean isLoteExist(String lote) {

        boolean status = true;

        Database database = new Database();
        ResultSet rs = database.sql(LoteTableModel.isExistName(lote));

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

}
