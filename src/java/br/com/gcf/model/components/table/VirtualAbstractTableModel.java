/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gcf.model.components.table;

import br.com.gcf.view.Web;
import eu.webtoolkit.jwt.ItemDataRole;
import eu.webtoolkit.jwt.Orientation;
import eu.webtoolkit.jwt.WAbstractTableModel;
import eu.webtoolkit.jwt.WLength;
import eu.webtoolkit.jwt.WModelIndex;
import eu.webtoolkit.jwt.WString;
import eu.webtoolkit.jwt.WTableView;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 *
 * @author Windows
 */
public abstract class VirtualAbstractTableModel<T> extends WAbstractTableModel {

    private int rows_;
    private int columns_;
    private String[] names;
    private Map<String, String> map;
    private WTableView table;
    private boolean isSorting;
    private Map<Integer, T> mapTemplate;
    private Web web;

    public VirtualAbstractTableModel(Web web,int row, String[] names, WTableView parent) {
        super(parent);
        this.web = web;
        this.table = parent;
        this.rows_ = row;
        this.columns_ = names.length;
        this.names = names;
        this.map = new LinkedHashMap<>();
        this.mapTemplate = new LinkedHashMap<>();
        this.isSorting = false;
        
        //remove sortinh para tabela
        int indexEditor  = getIndexColuna("Editar");
        int indexRemover = getIndexColuna("Remover");
        this.table.setSortingEnabled(indexEditor, false);
        this.table.setSortingEnabled(indexRemover, false);
        this.table.setColumnWidth(indexEditor,new WLength(60, WLength.Unit.Pixel));
        this.table.setColumnWidth(indexRemover,new WLength(80, WLength.Unit.Pixel));

    }

    public void addTemplate(int row, T template) {

        this.getMapTemplate().put(row, template);
    }

    public void updateTemplate(int row, T template) {

        this.getMapTemplate().replace(row, template);
    }

    public T getTemplate(int row) {

        T temp = null;

        if (this.mapTemplate.containsKey(row)) {
            temp = this.mapTemplate.get(row);
        }

        return temp;
    }

    public void insert(int row, int column, String data) {

        this.getMap().put(row + ":" + column, data);
        this.setRows(this.getMap().size() / columns_);

    }

    public void insert(int row, int column, String data, boolean update) {

        this.getMap().put(row + ":" + column, data);
        this.setRows(this.getMap().size() / columns_);
        if (update) {
            this.reset();
        }

    }

    public void updateData(int row, int column, String data) {

        if (this.getMap().containsKey(row + ":" + column)) {

            this.getMap().replace(row + ":" + column, data);
            this.reset();
            table.select(getIndex(row, column));
        }
    }

    public void deleteRow(int row) {

        if (this.getMap().containsKey(row + ":" + 0)) {

            for (int i = row; i < rows_; i++) {

                for (int j = 0; j < columns_; j++) {

                    int next = i + 1;
                    this.getMap().put(i + ":" + j, getMap().get(next + ":" + j));
                    getMap().remove(getMap().get(next + ":" + j));
                }

            }
            this.getMapTemplate().remove(row);
            this.setRows(--rows_);
            this.reset();
        }

    }

    public Map<Class<?>, Class<?>> iteretorDesc(Map<Class<?>, Class<?>> map) {

        Map<Class<?>, Class<?>> result = new LinkedHashMap<>();
        LinkedList<Class<?>> keys = new LinkedList<Class<?>>((Collection<? extends Class<?>>) map.keySet());

        for (int i = keys.size() - 1; i >= 0; i--) {

            Class<?> key = keys.get(i);
            Class<?> value = map.get(key);

            result.put(key, value);

        }
        return result;
    }

    public int getRowCount(final WModelIndex parent) {
        if (!(parent != null)) {
            return this.rows_;
        } else {
            return 0;
        }
    }

    public int getColumnCount(final WModelIndex parent) {
        if (!(parent != null)) {
            return this.columns_;
        } else {
            return 0;
        }
    }

    public Object getData(final WModelIndex index, int role) {
        switch (role) {
            case ItemDataRole.DisplayRole:

                String key = index.getRow() + ":" + index.getColumn();
                String value = this.getMap().get(key);

                if (value == null) {
                    return null;
                }
                return new WString(this.getMap().get(key));

            default:
                return null;
        }
    }

    public Object getHeaderData(int section, Orientation orientation, int role) {
        if (orientation == Orientation.Horizontal) {
            switch (role) {
                case ItemDataRole.DisplayRole:
                    return new WString(this.names[section]);
                default:
                    return null;
            }
        } else {
            return null;
        }
    }

    /**
     * @param rows_ the rows_ to set
     */
    public void setRows(int rows_) {
        this.rows_ = rows_;
    }

    /**
     * @return the isSorting
     */
    public boolean isIsSorting() {
        return isSorting;
    }

    /**
     * @param isSorting the isSorting to set
     */
    public void setIsSorting(boolean isSorting) {
        this.isSorting = isSorting;
    }

    /**
     * @return the map
     */
    public Map<String, String> getMap() {
        return map;
    }

    /**
     * @param map the map to set
     */
    public void setMap(Map<String, String> map) {
        this.map = map;
        this.reset();
    }

    /**
     * @return the mapTemplate
     */
    public Map<Integer, T> getMapTemplate() {
        return mapTemplate;
    }

    public void clear() {

        this.rows_ = 0;
        this.map.clear();
        this.mapTemplate.clear();
    }

    public WTableView getTable() {

        return this.table;
    }
    public void updateTable(){
        
        this.reset();
    }

    public String[] getHeaderNamesColumns(){
        
        return this.names;
    }
    
    public int getIndexColuna(String name) {

        String[] colunas = getHeaderNamesColumns();
        String coluna = null;
        int index = -1;

        for (int i = 0; i < colunas.length; i++) {

            coluna = colunas[i].toUpperCase();

            if (coluna.equals(name.toUpperCase())) {
                index = i;
                break;
            }
        }
        return index;
    }

    /**
     * @return the web
     */
    public Web getWeb() {
        return web;
    }

}
