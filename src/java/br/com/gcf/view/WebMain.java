/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gcf.view;

import eu.webtoolkit.jwt.JSignal;
import eu.webtoolkit.jwt.WApplication;
import eu.webtoolkit.jwt.WEnvironment;
import eu.webtoolkit.jwt.WVBoxLayout;

/**
 *
 * @author Windows
 */
public class WebMain extends WApplication {

    private WVBoxLayout box;
    private Web web;
    private static WApplication.UpdateLock lock;
    private JSignal onClosed;

    public WebMain(WEnvironment env) {
        super(env);

        System.out.println("Locale: " + env.getLocale());
        this.setLocale(env.getLocale());
        this.checkLogout();

    }

    public void init() {

        this.getRoot().setId("body");
        this.setTitle("Solinftec - GCF");

        this.box = new WVBoxLayout(this.getRoot());
        getBox().setContentsMargins(0, 0, 0, 0);

        this.web = new Web(WApplication.getInstance().getSessionId(), this);

        getBox().addWidget(getWeb());

    }

    public static void configureFavIcon(String path, String type) {

        WEnvironment env = WApplication.getInstance().getEnvironment();
        WApplication.getInstance().doJavaScript("function change_favicon(img) {\n"
                + "    var favicon = document.querySelector('link[rel=\"shortcut icon\"]');\n"
                + "    \n"
                + "    if (!favicon) {\n"
                + "        favicon = document.createElement('link');\n"
                + "        favicon.setAttribute('rel', 'shortcut icon');\n"
                + "        var head = document.querySelector('head');\n"
                + "        head.appendChild(favicon);\n"
                + "    }\n"
                + "    \n"
                + "    \n"
                + "    favicon.setAttribute('type', 'image/" + type + "');\n"
                + "    favicon.setAttribute('href', img);\n"
                + ""
                + "}\n"
                + "\n"
                + "change_favicon('http://" + env.getHostName() + "/" + path + "');"
                + ""
                + "");

    }
    
    private void checkLogout(){
        
        this.onClosed = new JSignal(this, "Event closed window") {
        };
        
        this.onClosed.addListener(this,() -> {
        
            System.out.println("Closing session: "+getSessionId());
            this.quit();
        });

        WApplication.getInstance().doJavaScript(""
                + "        $(function () {\n"
                + "            $(window).bind(\"beforeunload\", function () {\n"
                + this.onClosed.createCall() + "\n"
                + "\n"
                + "            })\n"
                + "        });\n"
                + "window.onunload = function () {\n"
                + this.onClosed.createCall() + "\n"
                + "}");
    }

    /**
     * @return the box
     */
    public WVBoxLayout getBox() {
        return box;
    }

    /**
     * @return the web
     */
    public Web getWeb() {
        return web;
    }

    public static void updateBegin() {

        WApplication app = WApplication.getInstance();
        WebMain.lock = app.getUpdateLock();
    }

    public static void updateEnd() {
        WApplication app = WApplication.getInstance();
        app.triggerUpdate();
        WebMain.lock.release();
    }
   
}
