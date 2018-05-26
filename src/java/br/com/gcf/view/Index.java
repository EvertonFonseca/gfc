/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gcf.view;

import br.com.gcf.control.CookieLogin;
import br.com.gcf.control.dao.Usuario_DAO;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import eu.webtoolkit.jwt.WApplication;
import eu.webtoolkit.jwt.WBootstrapTheme;
import eu.webtoolkit.jwt.WContainerWidget;
import eu.webtoolkit.jwt.WEnvironment;
import eu.webtoolkit.jwt.WLink;
import eu.webtoolkit.jwt.WResource;
import eu.webtoolkit.jwt.WTimer;
import eu.webtoolkit.jwt.WtServlet;
import eu.webtoolkit.jwt.servlet.WebRequest;
import eu.webtoolkit.jwt.servlet.WebResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Windows
 */
public class Index extends WtServlet {

    public static String texto = "";
    public static String SESSION_ID_NAME = "JSESSIONID";
    public static Map<String, CookieLogin> mapCookies = new LinkedHashMap<>();
    public static List<WApplication> applications;

    public interface Path_Response {

        public static String INDEX = "/index";
        public static String SISTEMA = "/sistema";
        public static String CONTATOS = "/constatos";
        public static String SOBRE = "/sobre";
        public static String LOGIN = "/login";
        public static String ADMIN = "/admin";
        public static String OPERADOR = "/operador";
    }

    public Index() {
        super();

        this.applications = new ArrayList<>();
        //define icone para web
        // getConfiguration().setWebSocketsEnabled(true);
        getConfiguration().setFavicon("assets/img/favicon.png");
        
        addResource(new WResource() {
            @Override
            protected void handleRequest(WebRequest request, WebResponse response) throws IOException {
                
                BufferedReader reader = request.getReader();

                String dado = null;
                StringBuffer buffer = new StringBuffer();
                while ((dado = reader.readLine()) != null) {

                    buffer.append(dado);
                }

                JsonParser parser = new JsonParser();
                JsonElement elment = parser.parse(buffer.toString());
                JsonObject json = elment.getAsJsonObject();
                
                System.out.println("Nome: "+json.get("Nome").toString());
                System.out.println("Idade: "+json.get("Idade").toString());
                System.out.println("Casado: "+json.get("Casado").toString());
                
                response.getOutputStream().write(
                        new String("Seja bem vindo "+json.get("Nome").toString().replace("/","")).getBytes());
                response.getOutputStream().flush();
            }
        },"/GCF/login/admin/input");
        //desable all user to offline
        Usuario_DAO.updateAllOffline(); 
    }

    public WApplication createApplication(WEnvironment env) {

        getConfiguration().setFavicon("http://" +env.getHostName() + "/GCF/assets/img/favicon.png");
        WebMain app = new WebMain(env);
        configCss();
        
        if (!checkPageFound(app)) {
            return app;
        }

        this.applications.add(app);
        app.init();
     
        return app;
    }

    public Index getIndex() {

        return this;
    }

    void configCss() {

        WBootstrapTheme p_wtTheme = new WBootstrapTheme();
        p_wtTheme.setVersion(WBootstrapTheme.Version.Version3);

        WApplication.getInstance().setTheme(p_wtTheme);
        
        WApplication.getInstance().useStyleSheet(new WLink("style/main.css"));
        WApplication.getInstance().useStyleSheet(new WLink("style/menu.css"));
        WApplication.getInstance().useStyleSheet(new WLink("style/gridflex.css"));
        WApplication.getInstance().useStyleSheet(new WLink("style/tableview.css"));
        WApplication.getInstance().useStyleSheet(new WLink("style/loader.css"));
        WApplication.getInstance().useStyleSheet(new WLink("style/fullscreen.css"));

        WApplication.getInstance().useStyleSheet(new WLink("assets/css/bootstrap.css"));
        WApplication.getInstance().useStyleSheet(new WLink("assets/css/demo.css"));
        WApplication.getInstance().useStyleSheet(new WLink("assets/css/main.css"));
        WApplication.getInstance().useStyleSheet(new WLink("assets/vendor/font-awesome/css/font-awesome.min.css"));
        WApplication.getInstance().useStyleSheet(new WLink("assets/vendor/linearicons/style.css"));
        WApplication.getInstance().useStyleSheet(new WLink("assets/vendor/chartist/css/chartist-custom.css"));

        //Fullscreen
        WApplication.getInstance().require("js/fullscreem.js");
        
        //Datepicker
        WApplication.getInstance().require("assets/datepicker/js/jquery.js");
        WApplication.getInstance().useStyleSheet(new WLink("assets/datepicker/css/bootstrap-datepicker3.css"));
        WApplication.getInstance().require("assets/datepicker/js/bootstrap-datepicker.min.js");
        WApplication.getInstance().require("assets/datepicker/js/bootstrap-datepicker.pt-BR.min.js");
        
        //Location bar
        WApplication.getInstance().require("js/location-bar.js");
        
        //MapBox
//        WApplication.getInstance().require("https://api.mapbox.com/mapbox-gl-js/v0.44.2/mapbox-gl.js");
//        WApplication.getInstance().useStyleSheet(new WLink("https://api.mapbox.com/mapbox-gl-js/v0.44.2/mapbox-gl.css"));
//        WApplication.getInstance().useStyleSheet(new WLink("style/mapbox.css"));
        
        //js
        WApplication.getInstance().require("assets/vendor/bootstrap/js/bootstrap.js");
        WApplication.getInstance().require("assets/vendor/bootstrap/js/bootstrap.min.js");
        WApplication.getInstance().require("assets/vendor/bootstrap/js/npm.js");
        
        //charlist
        WApplication.getInstance().useStyleSheet(new WLink("js/chart/chartist.min.css"));
        WApplication.getInstance().useStyleSheet(new WLink("js/chart/chartist.css"));
        WApplication.getInstance().require("js/chart/chartist.min.js");

    }

    public WContainerWidget getRoot() {

        return WApplication.getInstance().getRoot();
    }

    public static double convertePixelPorcentoWidth(double pixel) {

        double width = WApplication.getInstance().getEnvironment().getScreenWidth();
        double x = (100 - (pixel * 100 / width));

        return x;
    }

    public static double convertePixelPorcentoHeight(double pixel) {

        double height = WApplication.getInstance().getEnvironment().getScreenHeight();
        double x = (100 - (pixel * 100 / height));

        return x;
    }

    private boolean checkPageFound(WApplication app) {

        System.out.println("Path: " + app.getBookmarkUrl(app.getInternalPath()));

        if (app.getBookmarkUrl(app.getInternalPath()).equals("login")) {

            return true;
        } else {
            app.redirect("/GCF");
            app.quit();
            return false;
        }
    }

}
