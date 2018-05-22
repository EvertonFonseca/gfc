/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gcf.model.components.control;

import eu.webtoolkit.jwt.AlignmentFlag;
import eu.webtoolkit.jwt.Side;
import eu.webtoolkit.jwt.WBorder;
import eu.webtoolkit.jwt.WColor;
import eu.webtoolkit.jwt.WContainerWidget;
import eu.webtoolkit.jwt.WFont;
import eu.webtoolkit.jwt.WInteractWidget;
import eu.webtoolkit.jwt.WLength;
import eu.webtoolkit.jwt.WText;
import eu.webtoolkit.jwt.WVBoxLayout;

/**
 *
 * @author Windows
 */
public class Panel extends WContainerWidget {

    private WVBoxLayout box;
    private WText labelTitle;

    public Panel(String title) {

        this.setAttributeValue("style", "border-radius: " +1 + "px;");
        this.getDecorationStyle().setBorder(new WBorder(WBorder.Style.Solid, WBorder.Width.Thin, WColor.gray), Side.All);
        this.box = new WVBoxLayout(this);
        this.box.setContentsMargins(0, 0, 0, 5);
        this.box.setSpacing(0);

        this.labelTitle = new WText(title);
        labelTitle.setTextAlignment(AlignmentFlag.AlignCenter);
        labelTitle.setMinimumSize(new WLength(100, WLength.Unit.Percentage), WLength.Auto);
        labelTitle.getDecorationStyle().setForegroundColor(WColor.white);
        labelTitle.getDecorationStyle().getFont().setSize(WFont.Size.Small, WLength.Auto);
        labelTitle.getDecorationStyle().setBackgroundColor(new WColor("#595959"));
        labelTitle.getDecorationStyle().setBorder(new WBorder(WBorder.Style.Solid, WBorder.Width.Thin, WColor.gray), Side.Bottom);

        this.box.addWidget(labelTitle, 0, AlignmentFlag.AlignCenter, AlignmentFlag.AlignMiddle);

        box.setResizable(0, true, new WLength(25, WLength.Unit.Pixel));
    }

    public Panel(String title, WContainerWidget parent) {
        super(parent);

        this.setAttributeValue("style", "border-radius: " +1 + "px;");
        this.getDecorationStyle().setBorder(new WBorder(WBorder.Style.Solid, WBorder.Width.Thin, WColor.gray), Side.All);
        this.box = new WVBoxLayout(this);
        this.box.setContentsMargins(0, 0, 0, 5);
        this.box.setSpacing(0);

        labelTitle = new WText(title);
        labelTitle.setTextAlignment(AlignmentFlag.AlignCenter);
        labelTitle.setMinimumSize(new WLength(100, WLength.Unit.Percentage), WLength.Auto);
        labelTitle.getDecorationStyle().setForegroundColor(WColor.white);
        labelTitle.getDecorationStyle().getFont().setSize(WFont.Size.Small, WLength.Auto);
        labelTitle.getDecorationStyle().setBackgroundColor(new WColor("#595959"));
        labelTitle.getDecorationStyle().setBorder(new WBorder(WBorder.Style.Solid, WBorder.Width.Thin, WColor.gray), Side.Bottom);

        this.box.addWidget(labelTitle, 0, AlignmentFlag.AlignCenter, AlignmentFlag.AlignMiddle);

        box.setResizable(0, true, new WLength(25, WLength.Unit.Pixel));
    }
    
    public void setTitle(String title){
        
        labelTitle.setText(title);
    }

    public void setCentralWidget(WInteractWidget widget) {

        this.box.addWidget(widget,1, AlignmentFlag.AlignCenter, AlignmentFlag.AlignMiddle);
    }
}
