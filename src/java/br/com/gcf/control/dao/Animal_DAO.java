/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gcf.control.dao;

import br.com.gcf.control.Database;
import br.com.gcf.model.dto.Animal_DTO;
import br.com.gcf.model.dto.Apartacao_DTO;
import br.com.gcf.model.dto.Familia_DTO;
import br.com.gcf.model.dto.Lote_DTO;
import br.com.gcf.model.table.AnimalTableModel;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Windows
 */
public class Animal_DAO {

    public static List<Animal_DTO> readAllAnimais() {

        List<Animal_DTO> animais = new LinkedList<>();

        Database database = new Database();

        ResultSet rs = database.sql(AnimalTableModel.selectAnimais());
        
        if (rs == null) {

            database.close();
            return null;
        }

        try {

            while (rs.next()) {

                Animal_DTO animal = new Animal_DTO();
                animal.setId(rs.getInt("id_animal"));
                animal.setTag(rs.getString("tag_animal"));
                animal.setSisbov(rs.getString("sisbov_animal"));
                animal.setNome(rs.getString("nome_animal"));
                animal.setPeso(rs.getString("peso_atual_animal"));
                animal.setDataNascimento(rs.getString("data_nascimento_animal"));

                //Pai
                Animal_DTO father = new Animal_DTO();
                father.setId(rs.getInt("id_pai"));
                father.setNome(rs.getString("nome_pai"));

                //Mãe
                Animal_DTO mother = new Animal_DTO();
                mother.setId(rs.getInt("id_mae"));
                mother.setNome(rs.getString("nome_mae"));

                //Lote
                Lote_DTO lote = new Lote_DTO();
                lote.setId(rs.getInt("id_lote"));
                lote.setNome(rs.getString("nome_lote"));
                
                //Apartacao
                Apartacao_DTO apartacao = new Apartacao_DTO(lote,null);
                apartacao.setId(rs.getInt("id_apartacao"));
                apartacao.setNome(rs.getString("nome_apartacao"));
                
                //Familia 
                Familia_DTO familia = new Familia_DTO();
                familia.setId(rs.getInt("id_familia"));
                familia.setNome(rs.getString("nome_familia"));
                familia.setPai(father);
                familia.setMae(mother);
                
                //add as depedencias
                animal.setLote(lote);
                animal.setFamilia(familia);
                animal.setApartacao(apartacao);
                
                animais.add(animal);
            }
        } catch (Exception e) {

            e.printStackTrace();
            return null;
        } finally {

            database.close();
        }
        return animais;
    }

    public static List<Animal_DTO> readAllLotesByFiltro(String campo, String condicao) {

        if (campo.equals("codigo".toUpperCase())) {
            campo = "L.id_animal::text";
        } else if (campo.equals("animal".toUpperCase())) {
            campo = "nome_animal::text";
        } else if (campo.equals("data".toUpperCase())) {
            campo = "data_animal::text";
        } else if (campo.equals("peso minimo".toUpperCase())) {
            campo = "peso_minimo_animal::text";
        } else if (campo.equals("peso medio".toUpperCase())) {
            campo = "peso_medio_animal::text";
        } else if (campo.equals("peso maximo".toUpperCase())) {
            campo = "peso_maximo_animal::text";
        } else if (campo.equals("peso total".toUpperCase())) {
            campo = "peso_total_animal::text";
        } else if (campo.equals("ração".toUpperCase())) {
            campo = "nome_alimento::text";
        } else if (campo.equals("peso carcaça".toUpperCase())) {
            campo = "carcaca_animal::text";
        } else if (campo.equals("arroba".toUpperCase())) {
            campo = "arroba_animal::text";
        } else if (campo.equals("apartações".toUpperCase())) {
            campo = "MAX::text";
        } else if (campo.equals("animais".toUpperCase())) {
            campo = "quantidade_animal::text";
        }
        condicao = "'" + condicao.toUpperCase() + "%'";

        List<Animal_DTO> animais = new LinkedList<>();

        Database database = new Database();

        ResultSet rs = database.sql(AnimalTableModel.selectLoteFazendaByFiltro(campo, condicao));

        if (rs == null) {

            database.close();
            return null;
        }

        try {

            while (rs.next()) {

                Animal_DTO animal= new Animal_DTO();
                animais.add(animal);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {

            database.close();
        }
        return animais;
    }

    public static boolean insert(Animal_DTO animal) {

        boolean status = true;
        Database database = new Database();

        PreparedStatement st = database.getStatement(AnimalTableModel.insert());

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

    public static boolean update(Animal_DTO animal) {

        boolean status = true;
        Database database = new Database();

        PreparedStatement st = database.getStatement(AnimalTableModel.update());

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

    public static Animal_DTO findByName(String nameLote) {

        Database database = new Database();
        Animal_DTO animal= new Animal_DTO();
        ResultSet rs = database.sql(AnimalTableModel.findName(nameLote));

        if (rs == null) {

            database.close();
            return null;
        }

        try {

            while (rs.next()) {

            }

        } catch (Exception e) {

            e.printStackTrace();
            return null;
        } finally {

            database.close();
        }

        return animal;

    }

    public static boolean delete(int id) {

        boolean status = true;
        Database database = new Database();

        PreparedStatement st = database.getStatement(AnimalTableModel.delete(id));

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

    public static boolean isAnimalExist(String animal) {

        boolean status = true;

        Database database = new Database();
        ResultSet rs = database.sql(AnimalTableModel.isExistName(animal));

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
