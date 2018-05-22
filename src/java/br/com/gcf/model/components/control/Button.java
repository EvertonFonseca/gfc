/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gcf.model.components.control;

import eu.webtoolkit.jwt.WColor;
import eu.webtoolkit.jwt.WContainerWidget;
import eu.webtoolkit.jwt.WLink;
import eu.webtoolkit.jwt.WPushButton;

/**
 *
 * @author Windows
 */
public class Button extends WPushButton {

    public Button() {
    }

    public Button(String text) {
        super(text);
    }

    public Button(String text, int radius) {
        super(text);
        this.setAttributeValue("style", "border-radius: " + radius + "px;");
    }

    public Button(String text, WContainerWidget parent) {
        super(text, parent);

    }

    public Button(String text, WContainerWidget parent, int radius) {
        super(text, parent);
        this.setAttributeValue("style", "border-radius: " + radius + "px;");

    }

    public Button(int radius) {

        this.setAttributeValue("style", "border-radius: " + radius + "px;");
    }
    
    public void setIcon(String url){
        
        super.setIcon(new WLink(url));
    }
}
