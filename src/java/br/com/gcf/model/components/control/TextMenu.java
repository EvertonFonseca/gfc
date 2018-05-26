/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gcf.model.components.control;

import eu.webtoolkit.jwt.Cursor;
import eu.webtoolkit.jwt.JSignal;
import eu.webtoolkit.jwt.WColor;
import eu.webtoolkit.jwt.WContainerWidget;
import eu.webtoolkit.jwt.WLength;
import eu.webtoolkit.jwt.WPopupWidget;
import eu.webtoolkit.jwt.WText;
import eu.webtoolkit.jwt.WTimer;
import eu.webtoolkit.jwt.WVBoxLayout;

/**
 *
 * @author Windows
 */
public class TextMenu extends WText {

    private WContainerWidget contentMenu;
    private WPopupWidget popupWidget;
    private boolean isPopupVisible = false;
    private WVBoxLayout boxV;
    private JSignal signalClosePopup;
    private boolean closePoupActive;
    private double paddingChilds = 12;

    public TextMenu() {

        this.init();
    }

    public TextMenu(String text) {
        super(text);

        this.init();
    }

    public TextMenu(String text, WContainerWidget parent) {
        super(text, parent);

        this.init();
    }

    private void init() {

        this.isPopupVisible = false;
        this.closePoupActive = false;
        this.setCanReceiveFocus(true);
        this.signalClosePopup = new JSignal(this,"Signal close popup"){};
        this.signalClosePopup.addListener(this,() -> {
        
             this.isPopupVisible = false;
             this.hidePopup();
             this.closePoupActive = true;
             
             WTimer timer = new WTimer();
             timer.setSingleShot(true);
             timer.timeout().addListener(timer,() -> {  this.closePoupActive = false;});
             timer.setInterval(100);
             timer.start();
        });
        
        this.contentMenu = new WContainerWidget();
        this.contentMenu.setCanReceiveFocus(true);
        this.contentMenu.setAttributeValue("style","outline: none;");
        this.contentMenu.blurred().addListener(contentMenu,() -> {
            
            if (this.isPopupVisible) {
                this.signalClosePopup.trigger();
            }
        
        });

        this.boxV = new WVBoxLayout(this.contentMenu);
        this.boxV.setSpacing(15);
        this.boxV.setContentsMargins(5, 5, 5, 5);
        
        this.popupWidget = new WPopupWidget(this.contentMenu);
        this.popupWidget.setAnchorWidget(this);
        
        this.clicked().addListener(this,(event) -> {
           
            if (this.closePoupActive) {

                this.closePoupActive = false;
                return;
            }

            this.isPopupVisible = !isPopupVisible;

            if (this.isPopupVisible) {
                this.showPopup();
            } else {
                this.hidePopup();
            }
        });
        
        WTimer timer = new WTimer();
        timer.setInterval(100);
        timer.timeout().addListener(timer,() -> {

            this.isPopupVisible = !isPopupVisible;

            if (this.isPopupVisible) {
                this.showPopup();
            } else {
                this.hidePopup();
                timer.stop();
            }
        });
        timer.start();
    }

    public WText addMenu(String text) {

        WText menuChild = new WText(text);
        menuChild.setPadding(new WLength(this.paddingChilds, WLength.Unit.Pixel));
        menuChild.getDecorationStyle().getFont().setSize(new WLength(15, WLength.Unit.Pixel));
        
        menuChild.mouseWentOver().addListener(menuChild, (arg) -> {
            menuChild.getDecorationStyle().setForegroundColor(new WColor("#00ccff"));
            menuChild.getDecorationStyle().setCursor(Cursor.PointingHandCursor);
        });
        menuChild.mouseWentOut().addListener(menuChild, (arg) -> {
            menuChild.getDecorationStyle().setForegroundColor(WColor.darkGray);
            menuChild.getDecorationStyle().setCursor(Cursor.ArrowCursor);
        });

        this.boxV.addWidget(menuChild);

        return menuChild;
    }

    public void hidePopup() {

        this.popupWidget.hide();
        this.contentMenu.setFocus(false);
    }

    public void showPopup() {

        this.popupWidget.show();
        this.contentMenu.setFocus();
    }

    public void setPaddingChilds(double paddingChilds) {
        this.paddingChilds = paddingChilds;
    }

    public double getPaddingChilds() {
        return paddingChilds;
    }
   
}
