/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gcf.view.pages.div;

import eu.webtoolkit.jwt.AlignmentFlag;
import eu.webtoolkit.jwt.Side;
import eu.webtoolkit.jwt.WBorder;
import eu.webtoolkit.jwt.WColor;
import eu.webtoolkit.jwt.WContainerWidget;
import eu.webtoolkit.jwt.WLength;
import eu.webtoolkit.jwt.WText;
import eu.webtoolkit.jwt.WVBoxLayout;

/**
 *
 * @author Windows
 */
public class DivControl extends WContainerWidget{

    public DivControl() {
        
        setStyleClass("polaroide");
        this.init();
 
    }
    
    private void init(){
        
//        this.getDecorationStyle().setBorder(new WBorder(WBorder.Style.Solid, WBorder.Width.Thin,new WColor("#f5f5f5")),Side.All);

        WContainerWidget divCenter = new WContainerWidget(this);
        divCenter.getDecorationStyle().setBackgroundColor(new WColor("#0080ff"));
        divCenter.resize(new WLength(100, WLength.Unit.Percentage),new WLength(100, WLength.Unit.Percentage));

        WVBoxLayout box = new WVBoxLayout(divCenter);
        box.setSpacing(0);
        box.setContentsMargins(1,1,1,1);

        WContainerWidget divTitulo = new WContainerWidget();
        divTitulo.resize(new WLength(100, WLength.Unit.Percentage),new WLength(60, WLength.Unit.Pixel));
        divTitulo.setMaximumSize(new WLength(100, WLength.Unit.Percentage),new WLength(60, WLength.Unit.Pixel));
        divTitulo.getDecorationStyle().setBorder(new WBorder(WBorder.Style.Solid, WBorder.Width.Thin,new WColor("#f5f5f5")),Side.Bottom);

        WText textTitulo = new WText("Adminstração", divTitulo);
        textTitulo.setLineHeight(new WLength(40, WLength.Unit.Pixel));
        textTitulo.setStyleClass("textTitulo");
        
        WContainerWidget divMenus = new WContainerWidget();
        
        
        
        //add layout
        box.addWidget(divTitulo,0,AlignmentFlag.AlignCenter);
        box.addWidget(divMenus,1,AlignmentFlag.AlignCenter);
        
    }
}
