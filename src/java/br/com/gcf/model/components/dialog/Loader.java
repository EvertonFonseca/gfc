/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gcf.model.components.dialog;

import br.com.gcf.view.Index;
import br.com.gcf.view.Web;
import br.com.gcf.view.WebMain;
import eu.webtoolkit.jwt.AlignmentFlag;
import eu.webtoolkit.jwt.WAnimation;
import eu.webtoolkit.jwt.WContainerWidget;
import eu.webtoolkit.jwt.WDialog;
import eu.webtoolkit.jwt.WLength;
import eu.webtoolkit.jwt.WTimer;
import eu.webtoolkit.jwt.WVBoxLayout;

/**
 *
 * @author Windows
 */
public class Loader extends WContainerWidget {

    public Loader(Web web) {
        super(web);
        
        resize(new WLength(100, WLength.Unit.Percentage), new WLength(100, WLength.Unit.Percentage));
        setStyleClass("modalLoader");

        WAnimation animation = new WAnimation(WAnimation.AnimationEffect.Fade);
        animation.setDuration(800);
        this.setHidden(false, animation);

        WVBoxLayout box = new WVBoxLayout(this);
        box.setContentsMargins(0, 0, 0, 0);

        WContainerWidget loaderDiv = new WContainerWidget();
        loaderDiv.setStyleClass("loader");

        box.addWidget(loaderDiv, 0, AlignmentFlag.AlignCenter, AlignmentFlag.AlignMiddle);

    }

    public void destroy() {
        this.remove();
    }

}
