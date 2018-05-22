/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gcf.model.components.control;

import eu.webtoolkit.jwt.AlignmentFlag;
import eu.webtoolkit.jwt.Cursor;
import eu.webtoolkit.jwt.Side;
import eu.webtoolkit.jwt.WBorder;
import eu.webtoolkit.jwt.WColor;
import eu.webtoolkit.jwt.WContainerWidget;
import eu.webtoolkit.jwt.WFont;
import eu.webtoolkit.jwt.WLength;
import eu.webtoolkit.jwt.WText;
import eu.webtoolkit.jwt.WVBoxLayout;

/**
 *
 * @author Windows
 */
public class ButtonUtil extends WContainerWidget{
    
    private String text;
    
    public ButtonUtil() {
        
        this.text = text;
        this.init();
    }
    
    public ButtonUtil(String text) {
        
        this.text = text;
        this.init();
        
    }
    
    public ButtonUtil(String text, int radius) {
        
        this.text = text;
        this.init();
        this.setRadius(radius);
    }
    
    private void init() {
        
        this.setCanReceiveFocus(true);
        this.getDecorationStyle()
                .setBorder(new WBorder(WBorder.Style.Solid, WBorder.Width.Thin, WColor.gray), Side.All);
        
        WVBoxLayout box = new WVBoxLayout(this);
        box.setContentsMargins(0, 0, 0, 0);
        
        WText textObject = new WText(text);
        textObject.getDecorationStyle().getFont().setWeight(WFont.Weight.Bold,5);

        this.mouseWentOver().addListener(this, (mouse) -> {
            this.getDecorationStyle().setBackgroundColor(WColor.lightGray);
            this.getDecorationStyle().setCursor(Cursor.PointingHandCursor);
        });
        this.mouseWentOut().addListener(this, (mouse) -> {
            this.getDecorationStyle().setCursor(Cursor.ArrowCursor);
            this.getDecorationStyle().setBackgroundColor(WColor.white);
        });

        box.addWidget(textObject, 0, AlignmentFlag.AlignCenter, AlignmentFlag.AlignMiddle);
    }
    
    public void setRadius(int radius) {
        
        this.setAttributeValue("style", "border-radius: " + radius + "px;");
    }
}
