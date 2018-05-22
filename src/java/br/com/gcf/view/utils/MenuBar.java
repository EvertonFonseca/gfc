/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gcf.view.utils;

import br.com.gcf.model.components.conllection.MapaLista;
import br.com.gcf.model.components.conllection.MapaLista.Mapa;
import br.com.gcf.model.components.control.MenuDown;
import br.com.gcf.view.Web;
import br.com.gcf.view.pages.PAdmin;
import eu.webtoolkit.jwt.Cursor;
import eu.webtoolkit.jwt.Orientation;
import eu.webtoolkit.jwt.PositionScheme;
import eu.webtoolkit.jwt.Side;
import eu.webtoolkit.jwt.Signal1;
import eu.webtoolkit.jwt.WAnimation;
import eu.webtoolkit.jwt.WColor;
import eu.webtoolkit.jwt.WContainerWidget;
import eu.webtoolkit.jwt.WLength;
import eu.webtoolkit.jwt.WTemplate;
import eu.webtoolkit.jwt.WText;
import eu.webtoolkit.jwt.WVBoxLayout;
import java.util.List;

/**
 *
 * @author Windows
 */
public class MenuBar extends WContainerWidget {

    private WTemplate template;
    private List<WText> textBar;
    public Signal1<Integer> signalMenuEvent;
    private PAdmin pgAdmin;
    private WText textHome;
    private MenuDown textLotes;
    private WText textApartacao;
    private WText textAnimais;
    private WText textEstatistica;
    private WText textNotificacao;
    private WText textConfig;
    private MenuDown textUser;

    public interface Page {

        public static int HOME = 0x00;
        public static int FAZENDAS = 0x01;
        public static int LOTES = 0x02;
        public static int ANIMAIS = 0x03;
        public static int ESTATISTICA = 0x04;
        public static int CONFIGURACAO = 0x05;
        public static int NOTIFICACAO = 0x06;
        public static int APARTACAO = 0x07;
        public static int USUARIO_CATETORIA = 0x08;
        public static int USUARIO_USUARIOS = 0x09;
        public static int USUARIO_PERFIL = 0x0A;
    }

    public MenuBar(PAdmin pgAdmin) {
        this.pgAdmin = pgAdmin;
        this.init();
    }

    private void init() {

        signalMenuEvent = new Signal1<>();

        //config
        getDecorationStyle().setBackgroundColor(new WColor("#494848"));
        this.resize(new WLength(250, WLength.Unit.Pixel), new WLength(100, WLength.Unit.Percentage));
        this.setOverflow(Overflow.OverflowAuto, Orientation.Vertical);

        this.template = Web.loaderFileHtml("/br/com/gcf/html/Sidebar.html", getClass());

        //configurando menus
        this.textHome = new WText("<i class=\"fa fa-server\"></i> <span>Controle</span>");

        MapaLista<String, String> map = new MapaLista<>();
        map.put("<span class=\"lnr lnr-home\"></span> ", "<span>Lotes</span>");
        map.put("<span class=\"lnr lnr-book\"></span> ", "<span>Histórico</span>");
        this.textLotes = new MenuDown("<i class=\"lnr lnr-laptop\"></i><span>Fazenda</span>", map);

        List<WContainerWidget> loteDown = this.textLotes.getListDivDown();

        this.textApartacao = new WText("<i class=\"fa fa-sitemap\"></i> <span>Apartações</span>");
        this.textAnimais = new WText("<i class=\"fa fa-paw\"></i> <span>Animais</span>");
        this.textEstatistica = new WText("<i class=\"lnr lnr-chart-bars\"></i> <span>Estatística</span>");
        this.textNotificacao = new WText("<i class=\"lnr lnr-alarm\"></i> <span>Notitficações</span>"
                + "<span style=\"position: relative; margin-top: -25px;margin-left: -110px;\" class=\"badge bg-danger\">5</span>");
        this.textConfig = new WText("<i class=\"lnr lnr-cog\"></i> <span>Configuração</span>");

        MapaLista<String, String> mapUser = new MapaLista<>();
        mapUser.put("<span class=\"lnr lnr-user\"></span> ", "<span>Meu Perfil</span>");
        mapUser.put("<span class=\"lnr lnr-users\"></span> ", "<span>Usuários</span>");
        mapUser.put("<span class=\"lnr lnr-users\"></span> ", "<span>Categorias</span>");
        mapUser.put("<span class=\"lnr lnr-envelope\"></span> ", "<span>Mensagens</span>");
        this.textUser = new MenuDown("<i class=\"lnr lnr-user\"></i> <span>Perfil</span>", mapUser);
        List<WContainerWidget> userDown = this.textUser.getListDivDown();

        //tooltip
        this.textHome.setToolTip("Abrir menu de Controle");
        this.textLotes.setToolTip("Abrir menu de Lotes");
        this.textApartacao.setToolTip("Abrir menu de Apartações");
        this.textAnimais.setToolTip("Abrir menu de Animais");
        this.textEstatistica.setToolTip("Abrir menu de Estatística");
        this.textNotificacao.setToolTip("Abrir menu de Notificações");
        this.textConfig.setToolTip("Abrir menu de Configuração");
        this.textUser.setToolTip("Abrir menu de Perfil");

        //fim lotes
        this.template.bindWidget("text-home", getTextHome());
        this.template.bindWidget("text-lotes", getTextLotes());
        this.template.bindWidget("text-apartacao", getTextApartacao());
        this.template.bindWidget("text-animais", getTextAnimais());
        this.template.bindWidget("text-charts", getTextEstatistica());
        this.template.bindWidget("text-alarm", getTextNotificacao());
        this.template.bindWidget("text-config", getTextConfig());
        this.template.bindWidget("text-user", this.textUser);

        pgAdmin.createImportFile(getTextHome(), pgAdmin);

        //listenner
        getTextHome().clicked().addListener(this, (mouse) -> {
            changeSelectedColor("idHome");
            this.pgAdmin.getNavBar().focusNavMenuBar(this.pgAdmin.getNavBar().getTextHome());
            signalMenuEvent.trigger(Page.HOME);

        });

        getTextLotes().clicked().addListener(this, (mouse) -> {
            changeSelectedColor("idLote");
        });
        loteDown.get(0).clicked().addListener(this, (mouse) -> {
            this.pgAdmin.getNavBar().focusNavMenuBar(this.pgAdmin.getNavBar().getTextLotes());
            signalMenuEvent.trigger(Page.LOTES);
        });

        getTextApartacao().clicked().addListener(this, (mouse) -> {
            changeSelectedColor("idApartacao");
            this.pgAdmin.getNavBar().focusNavMenuBar(this.pgAdmin.getNavBar().getTextApartacao());
            signalMenuEvent.trigger(Page.APARTACAO);
        });
        getTextAnimais().clicked().addListener(this, (mouse) -> {
            changeSelectedColor("idAnimais");
            this.pgAdmin.getNavBar().focusNavMenuBar(this.pgAdmin.getNavBar().getTextAnimais());
            signalMenuEvent.trigger(Page.ANIMAIS);
        });
        getTextEstatistica().clicked().addListener(this, (mouse) -> {
            changeSelectedColor("idChart");
            this.pgAdmin.getNavBar().focusNavMenuBar(this.pgAdmin.getNavBar().getTextEstatistica());
            signalMenuEvent.trigger(Page.ESTATISTICA);
        });
        getTextNotificacao().clicked().addListener(this, (mouse) -> {
            changeSelectedColor("idAlarm");
            this.pgAdmin.getNavBar().focusNavMenuBar(this.pgAdmin.getNavBar().getTextNotificacao());
            signalMenuEvent.trigger(Page.NOTIFICACAO);
        });
        getTextConfig().clicked().addListener(this, (mouse) -> {
            changeSelectedColor("idConfig");
            this.pgAdmin.getNavBar().focusNavMenuBar(this.pgAdmin.getNavBar().getTextConfig());
            signalMenuEvent.trigger(Page.CONFIGURACAO);
        });
        this.textUser.clicked().addListener(this, (mouse) -> {
            changeSelectedColor("idUser");
        });
       
        userDown.get(0).clicked().addListener(this, (mouse) -> {
            this.pgAdmin.getNavBar().focusNavMenuBar(this.pgAdmin.getNavBar().getTextUser());
            signalMenuEvent.trigger(Page.USUARIO_PERFIL);
        });
        userDown.get(1).clicked().addListener(this, (mouse) -> {
            this.pgAdmin.getNavBar().focusNavMenuBar(this.pgAdmin.getNavBar().getTextUser());
            signalMenuEvent.trigger(Page.USUARIO_USUARIOS);
        });
        userDown.get(2).clicked().addListener(this, (mouse) -> {
            this.pgAdmin.getNavBar().focusNavMenuBar(this.pgAdmin.getNavBar().getTextUser());
            signalMenuEvent.trigger(Page.USUARIO_CATETORIA);
        });
                
        //init a selection at home
        changeSelectedColor("idHome");
        this.addWidget(this.template);

    }

    //Metedo é disparado quando o menu for presseiado para ver se vai esconder essa widget
    public void hiddenAnimation(boolean status) {

        WAnimation animation = new WAnimation(WAnimation.AnimationEffect.SlideInFromLeft);
        animation.setDuration(200);

        this.setHidden(status, animation);

    }

    private void changeSelectedColor(String id) {

        this.doJavaScript(""
                + ""
                + "\n"
                + "    var vetor = ['idHome','idLote','idApartacao','idAnimais','idChart','idAlarm','idConfig','idUser'];\n"
                + "    for (var i = 0; i < vetor.length; i++){\n"
                + "        \n"
                + "         var dado = vetor[i];\n"
                + "    var elmnt = document.getElementById(dado);"
                + "         if(dado == '" + id + "'){\n"
                + "    elmnt.style.background = '#252c35';\n"
                + "    elmnt.style.borderLeftColor = '#00AAFF';"
                + "         }else{\n"
                + "    elmnt.style.background = '';\n"
                + "    elmnt.style.borderLeftColor = 'transparent';"
                + "         }\n"
                + "    }"
                + "");
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
    public MenuDown getTextLotes() {
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

    public MenuDown getTextUser() {
        return textUser;
    }

    private WContainerWidget createPageDown(MapaLista<String, String> map) {

        //page down 
        WContainerWidget pageDown = new WContainerWidget();
        pageDown.setStyleClass("collapse");

        WTemplate tempPageLote = new WTemplate(pageDown);
        tempPageLote.setTemplateText("<ul class=\"sublista\">\n"
                + "<li><div>${page-down}</div></li>\n"
                + "</ul>");
        //template produtos page down
        WContainerWidget divDown = new WContainerWidget();
        WVBoxLayout boxLote = new WVBoxLayout(divDown);
        boxLote.setContentsMargins(5, 0, 0, 5);
        boxLote.setSpacing(8);

        for (Mapa object : map.getLista()) {

            String icone = (String) object.getKey();
            String texto = (String) object.getValue();

            WContainerWidget divMenu = new WContainerWidget();
            WText iconeMenu = new WText(icone, divMenu);
            iconeMenu.setFloatSide(Side.Left);

            WText textMenu = new WText(texto, divMenu);
            textMenu.setFloatSide(Side.Left);

            divMenu.mouseWentOver().addListener(divMenu, (event) -> {

                divMenu.getDecorationStyle().setCursor(Cursor.PointingHandCursor);
                iconeMenu.getDecorationStyle().setForegroundColor(new WColor("#1aa3ff"));
                textMenu.getDecorationStyle().setForegroundColor(WColor.white);

            });

            divMenu.mouseWentOut().addListener(divMenu, (event) -> {

                divMenu.getDecorationStyle().setCursor(Cursor.ArrowCursor);
                iconeMenu.getDecorationStyle().setForegroundColor(WColor.lightGray);
                textMenu.getDecorationStyle().setForegroundColor(WColor.lightGray);

            });

            boxLote.addWidget(divMenu);
        }

        tempPageLote.bindWidget("page-down", divDown);
        map.clear();
        map = null;
        //fim 

        return pageDown;
    }

}
