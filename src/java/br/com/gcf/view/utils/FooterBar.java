/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gcf.view.utils;
 
import br.com.gcf.view.Web;
import eu.webtoolkit.jwt.AlignmentFlag;
import eu.webtoolkit.jwt.WApplication;
import eu.webtoolkit.jwt.WColor;
import eu.webtoolkit.jwt.WContainerWidget;
import eu.webtoolkit.jwt.WFont;
import eu.webtoolkit.jwt.WImage;
import eu.webtoolkit.jwt.WLength;
import eu.webtoolkit.jwt.WTemplate;
import eu.webtoolkit.jwt.WText;
import eu.webtoolkit.jwt.WVBoxLayout;

/**
 *
 * @author Windows
 */
public class FooterBar extends WContainerWidget{
    
    private WTemplate template;
    public static int FOOTERBAR_HEIGHT = 30;

    public FooterBar() {
        
        this.init();
    }

    private void init() {

        //config
        resize(new WLength(100, WLength.Unit.Percentage),new WLength(30, WLength.Unit.Pixel));
        setMinimumSize(new WLength(100, WLength.Unit.Percentage),new WLength(30, WLength.Unit.Pixel));
      
        WVBoxLayout box = new WVBoxLayout(this);
        box.setContentsMargins(0, 0, 0, 1);
        
        WText textRodape = new WText("<p>&copy; 2018 <a href=\"http://solinftec.com/\" target=\"_blank\">Solinftec</a>. All Rights Reserved.</p>");
        textRodape.setLineHeight(new WLength(30, WLength.Unit.Pixel));
        textRodape.getDecorationStyle().getFont().setSize(WFont.Size.Small);

        box.addWidget(textRodape,0,AlignmentFlag.AlignRight,AlignmentFlag.AlignMiddle);
    }
}
