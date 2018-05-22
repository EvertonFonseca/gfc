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
                alimento.setId(rs.getInt(Web.removeEnter(AlimentoTableModel.ID_ALIMENTO)));
                alimento.setNome(rs.getString(Web.removeEnter(AlimentoTableModel.NOME_ALIMENTO)));

            }
        } catch (SQLException ex) {
          
            ex.printStackTrace();
            return null;

        } finally {

            database.close();
        }

        return alimento;
    }
}
