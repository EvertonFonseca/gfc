/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gcf.view.pages.div;

import br.com.gcf.view.Web;
import eu.webtoolkit.jwt.WApplication;
import eu.webtoolkit.jwt.WContainerWidget;
import eu.webtoolkit.jwt.WLength;
import eu.webtoolkit.jwt.WTemplate;
import eu.webtoolkit.jwt.WVBoxLayout;

/**
 *
 * @author Windows
 */
public class DivHome extends WContainerWidget {

    public DivHome() {

        this.init();
    }

    private void init() {

        this.resize(new WLength(100, WLength.Unit.Percentage), new WLength(100, WLength.Unit.Percentage));

        WVBoxLayout box = new WVBoxLayout(this);
        box.setContentsMargins(5, 10, 5, 0);

        WTemplate tempSidBar = Web.loaderFileHtml("/br/com/gcf/html/Home.html", getClass());

        box.addWidget(tempSidBar);

    }
    
}
