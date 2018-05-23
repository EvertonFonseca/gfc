/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gcf.view.utils;

import br.com.gcf.view.Web;
import br.com.gcf.view.pages.PAdmin;
import br.com.gcf.view.pages.div.DivAnimais;
import br.com.gcf.view.pages.div.DivApartacao;
import br.com.gcf.view.pages.div.DivCategorias;
import br.com.gcf.view.pages.div.DivEstatistica;
import br.com.gcf.view.pages.div.DivHome;
import br.com.gcf.view.pages.div.DivFazendas;
import br.com.gcf.view.pages.div.DivLotes;
import br.com.gcf.view.pages.div.DivUsuarios;
import br.com.gcf.view.utils.MenuBar.Page;
import eu.webtoolkit.jwt.Orientation;
import eu.webtoolkit.jwt.Signal1;
import eu.webtoolkit.jwt.WContainerWidget;
import eu.webtoolkit.jwt.WVBoxLayout;

/**
 *
 * @author Windows
 */
public class ContentMain extends WContainerWidget implements Signal1.Listener<Integer> {

    private FooterBar footerBar;
    private WContainerWidget divMain;
    private WContainerWidget widgetSelected = null;
    private PAdmin pgAdmin;
    private Web web;

    public ContentMain(Web web, PAdmin pgAdmin) {

        this.web = web;
        this.pgAdmin = pgAdmin;
        this.init();
    }

    private void init() {

        //config
        WVBoxLayout box = new WVBoxLayout(this);
        box.setContentsMargins(0, 0, 0, 0);
        box.setSpacing(0);

        this.divMain = new WContainerWidget();
        this.divMain.setOverflow(Overflow.OverflowAuto, Orientation.Vertical);
        this.divMain.setId("divMain");
        this.divMain.setOverflow(Overflow.OverflowHidden);

        this.pgAdmin.getMenuBar().signalMenuEvent.addListener(this, this);

        //footerBar
        this.footerBar = new FooterBar();

        //inicia home widget
        this.pgAdmin.getMenuBar().signalMenuEvent.trigger(Page.HOME);

        box.addWidget(divMain, 1);
        box.addWidget(footerBar, 0);

    }

    @Override
    public void trigger(Integer page) {

        //reseta o scroll vertical e horizontanl para position 0 da div
        this.divMain.doJavaScript(""
                + ""
                + "    var elmnt = document.getElementById(\"divMain\");\n"
                + "    elmnt.scrollLeft = 0;\n"
                + "    elmnt.scrollTop = 0;"
                + "");

        switch (page) {

            case Page.HOME:

                if (this.widgetSelected instanceof DivHome) {
                    break;
                }
                this.divMain.clear();
                this.divMain.setOverflow(Overflow.OverflowAuto, Orientation.Vertical);
                this.widgetSelected = new DivHome();
                this.divMain.addWidget(this.widgetSelected);

                break;

            case Page.FAZENDAS:

                if (this.widgetSelected instanceof DivFazendas) {
                    break;
                }
                this.divMain.clear();
                this.divMain.setOverflow(Overflow.OverflowHidden);
                this.widgetSelected = new DivFazendas(web);
                this.divMain.addWidget(this.widgetSelected);

                break;

            case Page.LOTES:

                if (this.widgetSelected instanceof DivLotes) {
                    break;
                }
                this.divMain.clear();
                this.divMain.setOverflow(Overflow.OverflowHidden);
                this.widgetSelected = new DivLotes(web);
                this.divMain.addWidget(this.widgetSelected);

                break;
            case Page.APARTACAO:

                if (this.widgetSelected instanceof DivApartacao) {
                    break;
                }
                this.divMain.clear();
                this.divMain.setOverflow(Overflow.OverflowHidden);
                this.widgetSelected = new DivApartacao(web);
                this.divMain.addWidget(this.widgetSelected);

                break;

            case Page.ANIMAIS:

                if (this.widgetSelected instanceof DivAnimais) {
                    break;
                }
                this.divMain.clear();
                this.divMain.setOverflow(Overflow.OverflowHidden);
                this.widgetSelected = new DivAnimais(web);
                this.divMain.addWidget(this.widgetSelected);

                break;
            case Page.ESTATISTICA:

                if (this.widgetSelected instanceof DivEstatistica) {
                    break;
                }
                this.divMain.clear();
                this.divMain.setOverflow(Overflow.OverflowHidden);
                this.widgetSelected = new DivEstatistica(this.web);
                this.divMain.addWidget(this.widgetSelected);

                break;
            case Page.NOTIFICACAO:
                break;

            case Page.USUARIO_USUARIOS:

                if (this.widgetSelected instanceof DivUsuarios) {
                    break;
                }
                this.divMain.clear();
                this.divMain.setOverflow(Overflow.OverflowHidden);
                this.widgetSelected = new DivUsuarios(this.web);
                this.divMain.addWidget(this.widgetSelected);

                break;
            case Page.USUARIO_PERFIL:

                if (this.widgetSelected instanceof DivUsuarios) {
                    break;
                }
                this.divMain.clear();
                this.divMain.setOverflow(Overflow.OverflowHidden);
                this.widgetSelected = new DivUsuarios(this.web);
                this.divMain.addWidget(this.widgetSelected);

                break;
            case Page.USUARIO_CATETORIA:

                if (this.widgetSelected instanceof DivCategorias) {
                    break;
                }
                this.divMain.clear();
                this.divMain.setOverflow(Overflow.OverflowHidden);
                this.widgetSelected = new DivCategorias(this.web);
                this.divMain.addWidget(this.widgetSelected);

                break;
        }

        Runtime.getRuntime().runFinalization();
        Runtime.getRuntime().gc();
    }
}
