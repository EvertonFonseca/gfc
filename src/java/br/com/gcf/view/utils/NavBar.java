/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gcf.view.utils;

import br.com.gcf.model.components.control.TextMenu;
import br.com.gcf.view.Web;
import br.com.gcf.view.WebMain;
import br.com.gcf.view.pages.PAdmin;
import eu.webtoolkit.jwt.AlignmentFlag;
import eu.webtoolkit.jwt.Cursor;
import eu.webtoolkit.jwt.Side;
import eu.webtoolkit.jwt.Signal1;
import eu.webtoolkit.jwt.WAnimation;
import eu.webtoolkit.jwt.WBorder;
import eu.webtoolkit.jwt.WBreak;
import eu.webtoolkit.jwt.WColor;
import eu.webtoolkit.jwt.WContainerWidget;
import eu.webtoolkit.jwt.WFont;
import eu.webtoolkit.jwt.WHBoxLayout;
import eu.webtoolkit.jwt.WImage;
import eu.webtoolkit.jwt.WLength;
import eu.webtoolkit.jwt.WPopupWidget;
import eu.webtoolkit.jwt.WTemplate;
import eu.webtoolkit.jwt.WText;
import eu.webtoolkit.jwt.WWidget;

/**
 *
 * @author Windows
 */
public class NavBar extends WContainerWidget {

    private WTemplate template;
    private Signal1<Boolean> signalHideMenuBar;
    public static int NAVBAR_HEIGHT = 84;
    private PAdmin pgAdmin;
    private WContainerWidget divMenuBar;
    private WText textHome;
    private TextMenu textLotes;
    private WText textApartacao;
    private WText textAnimais;
    private WText textEstatistica;
    private WText textNotificacao;
    private WText textConfig;
    private TextMenu textUser;

    public NavBar(PAdmin pgAdmin) {
        this.pgAdmin = pgAdmin;
        this.init();
        
    }

    private void init() {

        this.signalHideMenuBar = new Signal1<>();

        //config
        this.resize(new WLength(100, WLength.Unit.Percentage), new WLength(84, WLength.Unit.Pixel));
        this.setMinimumSize(new WLength(100, WLength.Unit.Percentage), new WLength(84, WLength.Unit.Pixel));
        this.setMaximumSize(new WLength(100, WLength.Unit.Percentage), new WLength(84, WLength.Unit.Pixel));

        WTemplate template = Web.loaderFileHtml("/br/com/gcf/html/NavBar.html", getClass());

        //Icone Logo
        WContainerWidget divLogo = new WContainerWidget();
        divLogo.setContentAlignment(AlignmentFlag.AlignCenter);
        divLogo.resize(new WLength(180, WLength.Unit.Pixel), WLength.Auto);

        WImage logo = new WImage("assets/img/logo-dark.png");
        logo.setMaximumSize(new WLength(64, WLength.Unit.Pixel), new WLength(21, WLength.Unit.Pixel));

        divLogo.addWidget(logo);

        WContainerWidget divMenu = new WContainerWidget();
        divMenu.setStyleClass("btMenu");

        divMenu.clicked().addListener(divMenu, (mouse) -> {

            boolean isHidden = !this.pgAdmin.getMenuBar().isHidden();

            WAnimation animation = new WAnimation(WAnimation.AnimationEffect.SlideInFromRight);
            animation.setDuration(200);

            this.divMenuBar.setHidden(!isHidden, animation);

            if (isHidden) {

                divMenu.setToolTip("Mostrar o painel do Menu");
                divMenu.toggleStyleClass("change", true);

            } else {

                divMenu.setToolTip("Esconder o painel do Menu");
                divMenu.toggleStyleClass("change", false);
            }

            this.pgAdmin.getMenuBar().hiddenAnimation(isHidden);

        });

        WContainerWidget divBar1 = new WContainerWidget(divMenu);
        divBar1.setStyleClass("bar1");
        WContainerWidget divBar2 = new WContainerWidget(divMenu);
        divBar2.setStyleClass("bar2");
        WContainerWidget divBar3 = new WContainerWidget(divMenu);
        divBar3.setStyleClass("bar3");

        //create divmenubar
        this.divMenuBar = createMenuBar();

        WText imgUser = new WText("");
        imgUser.setStyleClass("img-circle fa fa-user-o");
        imgUser.getDecorationStyle().getFont().setSize(WFont.Size.Larger, WLength.Auto);

        WText nameUser = new WText(tratarNomeLength(pgAdmin.getWeb().getUsuarioLogin().getNome()));
        nameUser.setToolTip("Usuário logado: " + pgAdmin.getWeb().getUsuarioLogin().getNome());

        //menu user
        WText userLogout = new WText("<i class=\"lnr lnr-exit\"></i> <span>Logout</span>");
        userLogout.clicked().addListener(userLogout, ((mouse) -> {

            this.pgAdmin.logout();

        }));

        template.bindWidget("icone-logo", divLogo);
        template.bindWidget("div-menu", divMenu);
        template.bindWidget("div-menubar", this.divMenuBar);
        template.bindWidget("image-user", imgUser);
        template.bindWidget("name-user", nameUser);
        template.bindWidget("user-logout", userLogout);

        try {
            this.addWidget(template);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String tratarNomeLength(String nome) {

        int limite = 15;
        boolean status = nome.length() >= limite;

        String[] space = nome.split(" ");

        if (space.length > 1) {
            nome = space[0] + " " + space[1].substring(0, 1) + ".";
        } else if (status) {
            nome = nome.substring(0, limite - 3) + " ...";
        }

        return nome;
    }

    private WContainerWidget createMenuBar() {

        WContainerWidget div = new WContainerWidget();
        div.hide();

        WHBoxLayout boxH = new WHBoxLayout(div);
        boxH.setContentsMargins(0,0,10,0);
        boxH.setSpacing(20);

        this.textHome = new WText("<i class=\"fa fa-server\"></i>");
        this.textHome.setId("home");

        this.textLotes = new TextMenu("<i class=\"lnr lnr-home\"></i>");
        this.textLotes.setId("lotes");
        this.textLotes.addMenu("<i class=\"lnr lnr-book\"></i> <span>Histórico</span>");
        
        this.textApartacao = new WText("<i class=\"fa fa-sitemap\"></i>");
        this.textApartacao.setId("apartacao");

        this.textAnimais = new WText("<i class=\"fa fa-paw\"></i>");
        this.textAnimais.setId("animais");

        this.textEstatistica = new WText("<i class=\"lnr lnr-chart-bars\"></i>");
        this.textEstatistica.setId("estatistica");

        this.textNotificacao = new WText(" <i class=\"lnr lnr-alarm\"></i>"
                + "<span style=\"position: relative; margin-top: -25px;margin-left: -10px;\" class=\"badge bg-danger\">5</span>");
        this.textNotificacao.setId("notificacao");

        this.textConfig = new WText("<i class=\"lnr lnr-cog\"></i>");
        this.textConfig.setId("configuracao");

        this.textConfig = new WText("<i class=\"lnr lnr-cog\"></i>");
        this.textConfig.setId("configuracao");

        this.textUser = new TextMenu("<i class=\"lnr lnr-user\"></i>");
        this.textUser.setId("usuario");
        this.textUser.addMenu("<i class=\"lnr lnr-user\"></i> <span>My Profile</span>");
        this.textUser.addMenu("<i class=\"lnr lnr-envelope\"></i> <span>Message</span>");
        this.textUser.addMenu("<i class=\"lnr lnr-cog\"></i> <span>Settings</span>");

        boxH.addWidget(new WBreak(), 0, AlignmentFlag.AlignMiddle);
        boxH.addWidget(getTextHome(), 0, AlignmentFlag.AlignMiddle);
        boxH.addWidget(getTextLotes(), 0, AlignmentFlag.AlignMiddle);
        boxH.addWidget(getTextApartacao(), 0, AlignmentFlag.AlignMiddle);
        boxH.addWidget(getTextAnimais(), 0, AlignmentFlag.AlignMiddle);
        boxH.addWidget(getTextEstatistica(), 0, AlignmentFlag.AlignMiddle);
        boxH.addWidget(getTextNotificacao(), 0, AlignmentFlag.AlignMiddle);
        boxH.addWidget(getTextConfig(), 0, AlignmentFlag.AlignMiddle);
        boxH.addWidget(textUser, 0, AlignmentFlag.AlignMiddle);
        
        String[] toolTips = new String[] {
            "Abrir menu de Controle",
            "Abrir menu de Lotes",
            "Abrir menu de Apartações",
            "Abrir menu de Animais",
            "Abrir menu de Estatística",
            "Abrir menu de Notificações",
            "Abrir menu de Configuração",
            "Abrir menu de Usuario"
        }; 
      
        int countOut = 0;
        for (int i = 0; i < div.getChildren().size(); i++) {

            WWidget child = div.getChildren().get(i);

            if (child instanceof WText) {

                WText text = ((WText) child);
                text.setToolTip(toolTips[i- countOut]);
                text.setCanReceiveFocus(true);
                text.getDecorationStyle().setBorder(new WBorder(WBorder.Style.Solid, new WLength(1, WLength.Unit.Pixel), WColor.lightGray), Side.Bottom);
                text.getDecorationStyle().getFont().setSize(new WLength(24, WLength.Unit.Pixel));

                text.mouseWentOver().addListener(text, () -> {

                    text.getDecorationStyle().setForegroundColor(new WColor("#1E90FF"));
                    text.getDecorationStyle().setCursor(Cursor.PointingHandCursor);
                });
                text.mouseWentOut().addListener(text, () -> {

                    text.getDecorationStyle().setForegroundColor(WColor.darkGray);
                    text.getDecorationStyle().setCursor(Cursor.ArrowCursor);

                });

                text.focussed().addListener(this, () -> {

                    switch (text.getId()) {

                        case "home":

                            this.pgAdmin.getMenuBar().getTextHome().clicked().trigger(null);
                       
                            break;
                        case "lotes":

                            this.pgAdmin.getMenuBar().getTextLotes().clicked().trigger(null);
                            break;
                        case "apartacao":

                            this.pgAdmin.getMenuBar().getTextApartacao().clicked().trigger(null);
                            break;
                        case "animais":

                            this.pgAdmin.getMenuBar().getTextAnimais().clicked().trigger(null);
                            break;
                        case "estatistica":

                            this.pgAdmin.getMenuBar().getTextEstatistica().clicked().trigger(null);
                            break;
                        case "notificaco":

                            this.pgAdmin.getMenuBar().getTextNotificacao().clicked().trigger(null);
                            break;
                        case "configuracao":

                            this.pgAdmin.getMenuBar().getTextConfig().clicked().trigger(null);
                            break;
                        case "usuario":

                            this.pgAdmin.getMenuBar().getTextUser().clicked().trigger(null);
                           
                            break;
                    }
                });

            }
            else
                countOut++;
        }
        textHome.getDecorationStyle().setBorder(new WBorder(WBorder.Style.Solid, new WLength(2, WLength.Unit.Pixel), new WColor("#4dc3ff")), Side.Bottom);
        return div;
    }

    private void menuBarHidden(boolean status) {

        WAnimation animation = new WAnimation(WAnimation.AnimationEffect.Fade);
        animation.setDuration(800);

        this.divMenuBar.setHidden(status, animation);
    }

    /**
     * @return the textHome
     */
    public WText getTextHome() {
        return textHome;
    }

    /**
     * @return the textLotes
     */
    public WText getTextLotes() {
        return textLotes;
    }

    /**
     * @return the textApartacao
     */
    public WText getTextApartacao() {
        return textApartacao;
    }

    /**
     * @return the textAnimais
     */
    public WText getTextAnimais() {
        return textAnimais;
    }

    /**
     * @return the textEstatistica
     */
    public WText getTextEstatistica() {
        return textEstatistica;
    }

    /**
     * @return the textNotificacao
     */
    public WText getTextNotificacao() {
        return textNotificacao;
    }

    /**
     * @return the textConfig
     */
    public WText getTextConfig() {
        return textConfig;
    }

    public TextMenu getTextUser() {
        return textUser;
    }
    

    public void focusNavMenuBar(WText text) {

        for (int i = 0; i < divMenuBar.getChildren().size(); i++) {

            WWidget child = divMenuBar.getChildren().get(i);

            if (child instanceof WText) {

                if (text.getId().equals(((WText) child).getId())) {

                    child.getDecorationStyle().setBorder(new WBorder(WBorder.Style.Solid, new WLength(2, WLength.Unit.Pixel), new WColor("#4dc3ff")), Side.Bottom);

                } else {

                    child.getDecorationStyle().setBorder(new WBorder(WBorder.Style.Solid, new WLength(1, WLength.Unit.Pixel), WColor.lightGray), Side.Bottom);
                }
            }

        }

    }

}
