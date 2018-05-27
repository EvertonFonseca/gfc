/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gcf.model.components.dialog;

import eu.webtoolkit.jwt.AlignmentFlag;
import eu.webtoolkit.jwt.Signal;
import eu.webtoolkit.jwt.WAnimation;
import eu.webtoolkit.jwt.WColor;
import eu.webtoolkit.jwt.WContainerWidget;
import eu.webtoolkit.jwt.WFont;
import eu.webtoolkit.jwt.WHBoxLayout;
import eu.webtoolkit.jwt.WImage;
import eu.webtoolkit.jwt.WLength;
import eu.webtoolkit.jwt.WText;
import eu.webtoolkit.jwt.WTimer;

/**
 *
 * @author Windows
 */
public class DMessagem extends WContainerWidget {

    private String message;
    private String tipo;
    public static final String IMAGE_OK = "images/ok.png";
    public static String IMAGE_WARNING = "images/warning.png";
    private WTimer timer;
    private boolean isReady;
    private Signal signalClosing;

    public DMessagem(String message, String image, WContainerWidget parent) {
        super(parent);
        this.message = message;
        this.tipo = image;
        this.init();

        WAnimation animation = new WAnimation(WAnimation.AnimationEffect.SlideInFromRight);
        animation.setDuration(100);
        this.setHidden(false, animation);

        this.isReady = false;
        this.timer = new WTimer(this);
        timer.setInterval(3000);

        timer.timeout().addListener(this, () -> {

            this.setHidden(true, animation);

            if (isReady) {

                this.timer.stop();
                this.signalClosing.trigger();
                this.signalClosing = null;
                parent.removeWidget(this);

            } else {

                timer.setInterval(500);
                isReady = true;
            }

        });
        timer.start();

    }

    public DMessagem(String message, String image, int delay, WContainerWidget parent) {
        super(parent);
        this.message = message;
        this.tipo = image;
        this.init();

        WAnimation animation = new WAnimation(WAnimation.AnimationEffect.SlideInFromRight);
        animation.setDuration(100);
        this.setHidden(false, animation);

        this.isReady = false;
        this.timer = new WTimer(this);
        timer.setInterval(delay);

        timer.timeout().addListener(this, () -> {

            this.setHidden(true, animation);

            if (isReady) {

                this.timer.stop();
                this.signalClosing.trigger();
                this.signalClosing = null;
                parent.removeWidget(this);

            } else {

                timer.setInterval(500);
                isReady = true;
            }

        });
        timer.start();

    }

    private void init() {

        this.signalClosing = new Signal(this);
        this.setStyleClass("messageDialog");
        this.setPopup(true);
        this.resize(new WLength(350, WLength.Unit.Pixel), new WLength(80, WLength.Unit.Pixel));
        this.getDecorationStyle().setBackgroundColor(WColor.white);
        // this.getDecorationStyle().setBorder(new WBorder(WBorder.Style.Solid, WBorder.Width.Thin, new WColor("#3399ff")), Side.All);
        this.setAttributeValue("style", "brackground-color: transparent;");

        WHBoxLayout box = new WHBoxLayout(this);
        box.setContentsMargins(5, 5, 0, 5);
        box.setSpacing(5);

        WImage image = new WImage(this.tipo);
        image.resize(WLength.Auto, new WLength(50, WLength.Unit.Pixel));
        image.setMinimumSize(WLength.Auto, new WLength(50, WLength.Unit.Pixel));
        image.setMaximumSize(WLength.Auto, new WLength(50, WLength.Unit.Pixel));

        WText textMessage = new WText(message);
        textMessage.setTextAlignment(AlignmentFlag.AlignCenter);
        textMessage.getDecorationStyle().getFont().setWeight(WFont.Weight.Lighter, 2);
        textMessage.getDecorationStyle().setForegroundColor(new WColor("#3399ff"));

        box.addWidget(image, 0, AlignmentFlag.AlignMiddle);
        box.addWidget(textMessage, 1, AlignmentFlag.AlignMiddle);
    }

    public void stop() {

        timer.stop();
        this.getParent().removeChild(this);
    }

    public Signal getSignalClosing() {
        return signalClosing;
    }
    
    
}
