/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gcf.view;

import br.com.gcf.control.dao.Usuario_DAO;
import br.com.gcf.control.threads.ReportBean;
import br.com.gcf.control.threads.ReportTask;
import br.com.gcf.control.threads.ReportTask2;
import eu.webtoolkit.jwt.JSignal;
import eu.webtoolkit.jwt.Signal;
import eu.webtoolkit.jwt.WApplication;
import eu.webtoolkit.jwt.WEnvironment;
import eu.webtoolkit.jwt.WVBoxLayout;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Windows
 */
public class WebMain extends WApplication {

    private Logger logger = LoggerFactory.getLogger(WebMain.class);
    private WVBoxLayout box;
    private Web web;
    private static WApplication.UpdateLock lock;
    private JSignal onClosed;
    private boolean hasAppStillLive = true;
    private Index index = null;

    public WebMain(WEnvironment env, Index index) {
        super(env);

        System.out.println("Locale: " + env.getLocale());
        this.index = index;
        this.getRoot().setId("Root");
        this.setLocale(env.getLocale());
        this.checkLogout();
        this.monitorLive();

    }

    private void monitorLive() {

        JSignal signalLive = new JSignal(this, "Signal ready") {
        };
        signalLive.addListener(this, () -> {
            this.hasAppStillLive = true;
        });

        ReportBean.runReports(new ReportTask<WebMain>(WApplication.getInstance(), this) {
            @Override
            public void run() {

                while (!app.hasQuit()) {

                    this.beginLock();
                    signal.hasAppStillLive = false;
                    app.doJavaScript(signalLive.createCall());
                    this.endLock();

                    try {
                        //sleep por 1 minuto
                        Thread.sleep(60000);

                    } catch (InterruptedException ex) {
                        logger.debug("Erro monitoramento timeout", ex);
                    } finally {

                        if (!hasAppStillLive) {

                            logger.info("Application " + app.getSessionId() + " timeout expirou ....");
                            signal.killALL(signal.logger);
                        }
                    }
                }
            }
        });
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

    private void checkLogout() {

        this.onClosed = new JSignal(this, "Event closed window") {
        };

        this.onClosed.addListener(this, () -> {

            this.killALL(this.logger);
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

    public void killALL(Logger logger) {

        try {

            //logout user if it sing in
            if (this.web != null) {

                if (this.web.getUsuarioLogin() != null) {

                    Usuario_DAO.updateAtivo(this.web.getUsuarioLogin().getId(), false);
                    this.web.setUsuarioLogin(null);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Error kill all", e);
        } finally {

            logger.info("Closing session: " + this.getSessionId());
            this.index.applications.remove(this);
            this.quit();
        }
    }
}
