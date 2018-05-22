/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gcf.model.components.table;

import br.com.gcf.control.Database;
import br.com.gcf.control.dao.Fazenda_DAO;
import br.com.gcf.model.dto.Fazenda_DTO;
import br.com.gcf.model.table.FazendaTableModel;
import br.com.gcf.model.table.MarcasTable;
import br.com.gcf.view.Web;
import br.com.gcf.view.pages.div.dialog.MessageBox;
import eu.webtoolkit.jwt.AlignmentFlag;
import eu.webtoolkit.jwt.Cursor;
import eu.webtoolkit.jwt.ItemDataRole;
import eu.webtoolkit.jwt.Orientation;
import eu.webtoolkit.jwt.ViewItemRenderFlag;
import eu.webtoolkit.jwt.WAbstractItemDelegate;
import eu.webtoolkit.jwt.WAbstractTableModel;
import eu.webtoolkit.jwt.WContainerWidget;
import eu.webtoolkit.jwt.WImage;
import eu.webtoolkit.jwt.WLength;
import eu.webtoolkit.jwt.WModelIndex;
import eu.webtoolkit.jwt.WString;
import eu.webtoolkit.jwt.WTableView;
import eu.webtoolkit.jwt.WVBoxLayout;
import eu.webtoolkit.jwt.WWidget;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 *
 * @author Windows
 */
public class VirtualModelFazendas<T> extends WAbstractTableModel {

    private int rows_;
    private int columns_;
    private String[] names;
    private Map<String, String> map;
    private WTableView table;
    private boolean isSorting;
    private Map<Integer,T> mapTemplate;

    public VirtualModelFazendas(int row, String[] names, WTableView parent) {
        super(parent);
        this.table = parent;
        this.rows_ = row;
        this.columns_ = names.length;
        this.names = names;
        this.map = new LinkedHashMap<>();
        this.mapTemplate = new LinkedHashMap<>();
        this.isSorting = false;

        configDelegate();

    }

    private void configDelegate() {

        this.table.setItemDelegateForColumn(5, new WAbstractItemDelegate() {
            @Override
            public WWidget update(WWidget widget, WModelIndex index, EnumSet<ViewItemRenderFlag> flags) {

                WContainerWidget divImage = new WContainerWidget();
                divImage.setToolTip("Remover a linha " + (index.getRow() + 1));
                divImage.mouseWentOver().addListener(divImage, (mouse) -> {
                    divImage.getDecorationStyle().setCursor(Cursor.PointingHandCursor);
                });
                divImage.mouseWentOut().addListener(divImage, (mouse) -> {
                    divImage.getDecorationStyle().setCursor(Cursor.ArrowCursor);
                });

                divImage.clicked().addListener(divImage, (mouse) -> {

                });

                WVBoxLayout boxV = new WVBoxLayout(divImage);
                boxV.setContentsMargins(0, 0, 0, 0);

                divImage.resize(new WLength(100, WLength.Unit.Percentage), new WLength(100, WLength.Unit.Percentage));

                WImage btRemove = new WImage("images/trash.png");
                btRemove.resize(new WLength(10, WLength.Unit.Pixel), new WLength(15, WLength.Unit.Pixel));
                btRemove.setMinimumSize(new WLength(15, WLength.Unit.Pixel), new WLength(20, WLength.Unit.Pixel));
                btRemove.setMaximumSize(new WLength(15, WLength.Unit.Pixel), new WLength(20, WLength.Unit.Pixel));
                btRemove.clicked().addListener(btRemove, (mouse) -> {

                    Fazenda_DTO fa = (Fazenda_DTO) getTemplate(index.getRow());
                    int quantLote = fa.getQuantidadeLotes();
                    String nota = quantLote > 0 ? "<p><p>Nota: Todas as seus Lotes, Apartações e Animal tambem serão apagadas!</p></p>" : "";
                    
                    MessageBox boxMessage = new MessageBox("Remover fazenda", "<p>Deseja realmente remover a fazenda " +getMap().get(index.getRow() + ":" + 1) + "?</p>"
                          +nota);

                    boxMessage.getEmitConfirmar().addListener(boxMessage, () -> {

                        String value = getMap().get(index.getRow() + ":" + 0);

                        if (Fazenda_DAO.delete(Integer.parseInt(value))) {

                        //    Web.createMessageTemp("Fazenda codigo " + value + " foi removido com sucesso!", Web.Tipo_Mensagem.SUCESSO);
                            deleteRow(index.getRow());
                        }

                    });

                });

                boxV.addWidget(btRemove, 0, AlignmentFlag.AlignCenter, AlignmentFlag.AlignMiddle);

                return divImage;
            }
        });

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
        this.reset();

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
    public Map<Integer,T> getMapTemplate() {
        return mapTemplate;
    }
    
    public void clear(){
        
        this.rows_ = 0;
        this.map.clear();
        this.mapTemplate.clear();
    }
    
}
