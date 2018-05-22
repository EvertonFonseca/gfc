/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gcf.model.components.control;

import eu.webtoolkit.jwt.Side;
import eu.webtoolkit.jwt.WBorder;
import eu.webtoolkit.jwt.WColor;
import eu.webtoolkit.jwt.WLength;
import eu.webtoolkit.jwt.WLineEdit;

/**
 *
 * @author Windows
 */
public class EditLine<T> extends WLineEdit {

    public EditLine() {

        this.init();
    }

    public EditLine(String text) {
        super(text);
        this.init();
    }

    public EditLine(T text) {
        super(String.valueOf(text));
        this.init();
    }

    private void init() {

        this.setBackgroundTransparent();

        this.blurred().addListener(this, () -> {

            this.getDecorationStyle().setBorder(new WBorder(WBorder.Style.Solid, new WLength(1, WLength.Unit.Pixel), WColor.lightGray), Side.Bottom);
        });

        this.getDecorationStyle().setBorder(new WBorder(WBorder.Style.None, WLength.Auto), Side.Top, Side.Right, Side.Left);

        this.focussed().addListener(this, () -> {

            this.getDecorationStyle().setBorder(new WBorder(WBorder.Style.Solid, new WLength(2, WLength.Unit.Pixel), new WColor("#4dc3ff")), Side.Bottom);

        });

    }

    public void endSelectedText() {

        this.setSelection(this.getText().length(), this.getText().length());
    }

    public void setBackground(String color) {

        this.setAttributeValue("style", ""
                + "background-color: " + color + ";"
                + "-webkit-box-shadow: none;\n"
                + "box-shadow: none;");
    }

    public void setBackgroundTransparent() {

        this.setAttributeValue("style", ""
                + "background-color: transparent;"
                + "-webkit-box-shadow: none;\n"
                + "box-shadow: none;");
    }
}
