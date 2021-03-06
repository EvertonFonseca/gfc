/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gcf.model.components.control;

import br.com.gcf.model.components.conllection.MapaLista;
import eu.webtoolkit.jwt.AlignmentFlag;
import eu.webtoolkit.jwt.Cursor;
import eu.webtoolkit.jwt.Side;
import eu.webtoolkit.jwt.WAnimation;
import eu.webtoolkit.jwt.WApplication;
import eu.webtoolkit.jwt.WBorder;
import eu.webtoolkit.jwt.WColor;
import eu.webtoolkit.jwt.WContainerWidget;
import eu.webtoolkit.jwt.WHBoxLayout;
import eu.webtoolkit.jwt.WInteractWidget;
import eu.webtoolkit.jwt.WLength;
import eu.webtoolkit.jwt.WText;
import eu.webtoolkit.jwt.WVBoxLayout;
import eu.webtoolkit.jwt.WWidget;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Windows
 */
public class MenuDown extends WContainerWidget {

    private WText titulo;
    private WContainerWidget divDown;
    private WText seta;

    public MenuDown(String text, MapaLista<String, String> map) {

        this.titulo = new WText(text, this);
        this.seta = new WText(this);
        this.seta.setFloatSide(Side.Right);
        this.seta.setStyleClass("icon-submenu lnr  lnr-chevron-left");

        this.titulo.clicked().addListener(this, (event) -> {this.eventMouseClicked();});
        this.seta.clicked().addListener(this, (event) -> {this.eventMouseClicked();});

        this.createPageDown(map);
    }
    public MenuDown(String text) {

        this.titulo = new WText(text, this);
        this.seta = new WText(this);
        this.seta.setFloatSide(Side.Right);
        this.seta.setStyleClass("icon-submenu lnr  lnr-chevron-left");

        this.titulo.clicked().addListener(this, (event) -> {
            this.eventMouseClicked();
        });
        this.seta.clicked().addListener(this, (event) -> {
            this.eventMouseClicked();
        });
    }

    public void createPageDown(MapaLista<String, String> map) {

        this.divDown = new WContainerWidget(this);
        this.divDown.hide();

        WVBoxLayout boxLote = new WVBoxLayout(divDown);
        boxLote.setContentsMargins(5,15,0,0);
        boxLote.setSpacing(15);

        
        for (int i = 0; i < map.getLista().size(); i++) {

            MapaLista.Mapa object = map.getLista().get(i);
            String icone = (String) object.getKey();
            String texto = (String) object.getValue();

            WContainerWidget divMenu = new WContainerWidget();
            divMenu.setObjectName(String.valueOf(i));
            divMenu.setCanReceiveFocus(true);
            divMenu.getDecorationStyle().setBorder(new WBorder(WBorder.Style.Solid, WBorder.Width.Thin, new WColor("")), Side.Bottom);
            
            WHBoxLayout boxH = new WHBoxLayout(divMenu);
            boxH.setContentsMargins(0, 0, 0, 0);
            boxH.setSpacing(10);
       
            WText iconeMenu = new WText(icone);
            iconeMenu.getDecorationStyle().getFont().setSize(new WLength(14, WLength.Unit.Pixel));
            
            WInteractWidget textMenu;

            if (!object.isMenuDown()) {
                textMenu = new WText(texto);
            } else {
                textMenu = new MenuDown(texto);
            }
            
            textMenu.getDecorationStyle().getFont().setSize(new WLength(13, WLength.Unit.Pixel));

            boxH.addWidget(iconeMenu,0,AlignmentFlag.AlignMiddle);
            boxH.addWidget(textMenu, 1, AlignmentFlag.AlignMiddle);

            divMenu.focussed().addListener(divMenu, () -> {

                divMenu.getDecorationStyle().
                        setBorder(new WBorder(WBorder.Style.Solid, WBorder.Width.Thin, new WColor("#1aa3ff")), Side.Bottom);

                this.getListDivDown().forEach((item) -> {

                    if (!divMenu.getObjectName().equals(item.getObjectName())) {
                        item.getDecorationStyle().setBorder(new WBorder(WBorder.Style.Solid, WBorder.Width.Thin, new WColor("")), Side.Bottom);
                    }

                });
            });
            
            divMenu.mouseWentOver().addListener(divMenu, (event) -> {

                divMenu.getDecorationStyle().setCursor(Cursor.PointingHandCursor);
                iconeMenu.getDecorationStyle().setForegroundColor(new WColor("#1aa3ff"));
                textMenu.getDecorationStyle().setForegroundColor(WColor.white);

            });

            divMenu.mouseWentOut().addListener(divMenu, (event) -> {

                divMenu.getDecorationStyle().setCursor(Cursor.ArrowCursor);
                iconeMenu.getDecorationStyle().setForegroundColor(WColor.lightGray);
                textMenu.getDecorationStyle().setForegroundColor(WColor.lightGray);

            });

            boxLote.addWidget(divMenu);
        }

        map.clear();
        map = null;
        //fim 
    }

    public void clearChildBorder() {

        this.getListDivDown().forEach((item) -> {

            item.getDecorationStyle().setBorder(new WBorder(WBorder.Style.Solid, WBorder.Width.Thin, new WColor("")), Side.Bottom);
      
        });
    }

    public List<WContainerWidget> getListDivDown() {

        List<WContainerWidget> containers = new ArrayList<>();

        for (WWidget widget : this.divDown.getChildren()) {

            containers.add((WContainerWidget) widget);
        }
        return containers;
    }

    private void eventMouseClicked() {

        WApplication app = WApplication.getInstance();
        WApplication.UpdateLock lock = app.getUpdateLock();

        try {

            boolean isHidden = !this.divDown.isHidden();
            WAnimation animation = new WAnimation(WAnimation.AnimationEffect.SlideInFromTop);
            animation.setDuration(200);
            this.divDown.setHidden(isHidden, animation);

            if (isHidden) {
                this.seta.setStyleClass("icon-submenu lnr  lnr-chevron-left");
            } else {
                this.seta.setStyleClass("icon-submenu lnr  lnr-chevron-down");
            }

            app.triggerUpdate();
        } catch (Exception e) {

        } finally {

            lock.release();
        }
    }

}
