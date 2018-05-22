/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gcf.model.components.table;

import br.com.gcf.control.dao.Usuario_DAO;
import br.com.gcf.model.dto.Usuario_DTO;
import br.com.gcf.view.Web;
import br.com.gcf.view.pages.div.dialog.DEUsuario;
import br.com.gcf.view.pages.div.dialog.MessageBox;
import eu.webtoolkit.jwt.AlignmentFlag;
import eu.webtoolkit.jwt.Cursor;
import eu.webtoolkit.jwt.ViewItemRenderFlag;
import eu.webtoolkit.jwt.WAbstractItemDelegate;
import eu.webtoolkit.jwt.WContainerWidget;
import eu.webtoolkit.jwt.WFont;
import eu.webtoolkit.jwt.WImage;
import eu.webtoolkit.jwt.WLength;
import eu.webtoolkit.jwt.WModelIndex;
import eu.webtoolkit.jwt.WTableView;
import eu.webtoolkit.jwt.WText;
import eu.webtoolkit.jwt.WVBoxLayout;
import eu.webtoolkit.jwt.WWidget;
import java.util.EnumSet;

/**
 *
 * @author Windows
 */
public class VirtualModelUsuario<T> extends VirtualAbstractTableModel<T> {

    private DEUsuario dialogDEUsuario;
    
    public VirtualModelUsuario(Web web, int row, String[] names, WTableView parent) {
        super(web, row, names, parent);
        this.configDelegate();
    }

    private void configDelegate() {

        getTable().setItemDelegateForColumn(getIndexColuna("Editar"), new WAbstractItemDelegate() {
            @Override
            public WWidget update(WWidget widget, WModelIndex index, EnumSet<ViewItemRenderFlag> flags) {

                WContainerWidget divImage = new WContainerWidget();
                divImage.setToolTip("Editar a linha " + (index.getRow() + 1));
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

                WText btEdit = new WText("");
                btEdit.setStyleClass("lnr lnr-pencil");
                btEdit.getDecorationStyle().getFont().setSize(WFont.Size.Large, WLength.Auto);
                btEdit.getDecorationStyle().getFont().setWeight(WFont.Weight.Bolder, 5);
                btEdit.resize(new WLength(10, WLength.Unit.Pixel), new WLength(15, WLength.Unit.Pixel));
                btEdit.setMinimumSize(new WLength(15, WLength.Unit.Pixel), new WLength(20, WLength.Unit.Pixel));
                btEdit.setMaximumSize(new WLength(15, WLength.Unit.Pixel), new WLength(20, WLength.Unit.Pixel));
                btEdit.clicked().addListener(btEdit, (mouse) -> {

                    Usuario_DTO usuario = (Usuario_DTO) getTemplate(index.getRow());
                    dialogDEUsuario = new DEUsuario(getWeb(), usuario);

                    dialogDEUsuario.getSignalClose().addListener(dialogDEUsuario, () -> {

                        dialogDEUsuario = null;

                    });

                });

                boxV.addWidget(btEdit, 0, AlignmentFlag.AlignCenter, AlignmentFlag.AlignMiddle);

                return divImage;
            }
        });

        getTable().setItemDelegateForColumn(getIndexColuna("Remover"), new WAbstractItemDelegate() {
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

                    MessageBox boxMessage = new MessageBox("Remover usuario", "<p><p>Deseja realmente remover o usuario " + getMap().get(index.getRow() + ":" + 1) + "?</p></p>");

                    boxMessage.getEmitConfirmar().addListener(boxMessage, () -> {

                        String value = getMap().get(index.getRow() + ":" + 0);

                        if (Usuario_DAO.delete(Integer.parseInt(value))) {

                            getWeb().createMessageTemp("Usuario codigo " + value + " foi removido com sucesso!", Web.Tipo_Mensagem.SUCESSO);
                            deleteRow(index.getRow());
                        }

                    });

                });

                boxV.addWidget(btRemove, 0, AlignmentFlag.AlignCenter, AlignmentFlag.AlignMiddle);

                return divImage;
            }
        });

    }

}
