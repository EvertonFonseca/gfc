/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gcf.view.pages.div.dialog;

import br.com.gcf.control.dao.Alimento_DAO;
import br.com.gcf.control.dao.Apartacao_DAO;
import br.com.gcf.control.dao.Lote_DAO;
import br.com.gcf.control.dao.TipoApartacao_DAO;
import br.com.gcf.model.components.control.Button;
import br.com.gcf.model.components.control.ButtonUtil;
import br.com.gcf.model.components.control.ComboBox;
import br.com.gcf.model.components.control.DateEditLine;
import br.com.gcf.model.components.control.EditLine;
import br.com.gcf.model.components.control.Panel;
import br.com.gcf.model.components.dialog.DialogAbstracao;
import br.com.gcf.model.dto.Alimento_DTO;
import br.com.gcf.model.dto.Apartacao_DTO;
import br.com.gcf.model.dto.Lote_DTO;
import br.com.gcf.model.dto.TipoApartacao_DTO;
import br.com.gcf.view.Web;
import br.com.gcf.view.WebMain;
import eu.webtoolkit.jwt.AlignmentFlag;
import eu.webtoolkit.jwt.WAnimation;
import eu.webtoolkit.jwt.WContainerWidget;
import eu.webtoolkit.jwt.WGridLayout;
import eu.webtoolkit.jwt.WLength;
import eu.webtoolkit.jwt.WTemplate;
import eu.webtoolkit.jwt.WText;
import eu.webtoolkit.jwt.WVBoxLayout;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Windows
 */
public class DCApartacao extends DialogAbstracao {

    private EditLine textNome, textDe, textAte;
    private ComboBox<Lote_DTO> comboLote;
    private ComboBox<TipoApartacao_DTO> comboTipoAp;
    private ComboBox<Alimento_DTO> comboRacao;
    private Web web;
    private Panel panelCondicao;
    private WText labelDe;
    private WText labelAte;
    private DateEditLine dateEditDe;
    private DateEditLine dateEditAte;
    private WGridLayout gridPanel;
    private WContainerWidget divDadosCondicao;
    private WContainerWidget divDadosCondicaoData;
    private boolean statusGrid = false;

    public DCApartacao(Web web) {
        super("Cadastro Apartacao");

        this.web = web;

        this.resize(new WLength(650, WLength.Unit.Pixel), new WLength(460, WLength.Unit.Pixel));
        this.setResizable(false);
        this.init();

        WAnimation animation = new WAnimation(WAnimation.AnimationEffect.SlideInFromTop);
        animation.setDuration(500);
        this.setHidden(false, animation);
    }

    private void init() {

        //dados empresa
        WContainerWidget divDadosLote = createDados();
        this.divDadosCondicao = createPanelCondicao();
        this.divDadosCondicaoData = createPanelCondicaoData();

        this.getContents().setOverflow(WContainerWidget.Overflow.OverflowHidden);

        getTitleBar().setMaximumSize(WLength.Auto, new WLength(50, WLength.Unit.Pixel));

        WContainerWidget divCenter = new WContainerWidget(getContents());

        WVBoxLayout grid = new WVBoxLayout(divCenter);
        grid.setSpacing(10);

        grid.addWidget(divDadosLote);
        grid.addWidget(divDadosCondicao);
        grid.addWidget(divDadosCondicaoData);

        Button btSalvar = new Button("Salvar", getFooter(), 10);
        btSalvar.setDefault(true);

        Button btFechar = new Button("Sair", getFooter(), 10);
        btFechar.setStyleClass("btn-danger");

        //btresize
        btFechar.resize(100, 30);
        btSalvar.resize(100, 30);
        //listenner
        btFechar.clicked().addListener(btFechar, (mouse) -> {
            reject();
        });
        btSalvar.clicked().addListener(btSalvar, (mouse) -> {

            Lote_DTO lote = this.comboLote.getSelecteObject();
            Alimento_DTO racao = this.comboRacao.getSelecteObject();
            TipoApartacao_DTO tipo = this.comboTipoAp.getSelecteObject();

            Apartacao_DTO apartacao = new Apartacao_DTO(lote, racao);
            apartacao.setNome(Web.UTF8toISO(textNome.getText()));
            apartacao.setRacao(racao);
            apartacao.setTipo(tipo);

            if (comboTipoAp.getCurrentText().equals("DATA")) {

                apartacao.setDe(dateEditDe.getText());
                apartacao.setAte(dateEditAte.getText());

            } else {
                apartacao.setDe(textDe.getText());
                apartacao.setAte(textAte.getText());
            }

            if (Apartacao_DAO.insert(apartacao)) {

                getSignalInsert().trigger("");
            }
            reject();
        });

    }

    private WContainerWidget createDados() {

        WContainerWidget container = new WContainerWidget();
        container.resize(new WLength(90, WLength.Unit.Percentage), WLength.Auto);

        WGridLayout grid = new WGridLayout(container);
        grid.setContentsMargins(0, 0, 0, 0);
        grid.setHorizontalSpacing(20);
        grid.setVerticalSpacing(15);

        WText labelLote = new WText("Lote:");
        WText labelTipo = new WText("Tipo:");
        WText labelRacao = new WText("Ração:");
        WText labelApartacao = new WText("Apartacao:");

        WTemplate btAddLote = new WTemplate();
        btAddLote.setTemplateText("<button type=\"button\" style=\"\" class=\"btn btn-default\">+</button>");

        btAddLote.clicked().addListener(this, (mouse) -> {

            DCLote dc = new DCLote(web);
            dc.getSignalInsert().addListener(dc, (arg) -> {

                //cleat table
                this.comboLote.setListItens(Lote_DAO.readAllLotesFazendas());
                this.web.createMessageTemp(arg, Web.Tipo_Mensagem.SUCESSO);

            });
        });
        
        
        WTemplate btAddRacao = new WTemplate();
        btAddRacao.setTemplateText("<button type=\"button\" style=\"\" class=\"btn btn-default\">+</button>");

        btAddRacao.clicked().addListener(this, (mouse) -> {

            DCRacao dc = new DCRacao(web);
            dc.getSignalInsert().addListener(dc, (arg) -> {

                //cleat table
                this.comboRacao.setListItens(Alimento_DAO.readAllAlimentos());
                this.web.createMessageTemp(arg, Web.Tipo_Mensagem.SUCESSO);

            });
        });
 
        this.comboLote = new ComboBox(Lote_DAO.readAllLotesFazendas());
        this.comboRacao = new ComboBox(Alimento_DAO.readAllAlimentos());
        this.comboTipoAp = new ComboBox(TipoApartacao_DAO.readAllTipoApartacao());

        this.textNome = new EditLine();
        this.textNome.setPlaceholderText("Digite o nome da apartação");

        textNome.setFocus();

        this.comboTipoAp.changed().addListener(comboTipoAp, () -> {

            switch (comboTipoAp.getCurrentText().toString()) {

                case "PESO":
                    this.labelAte.setHidden(false);
                    this.textAte.setHidden(false);
                    this.labelDe.setText("De:");
                    this.labelAte.setText("Até:");
                    this.textDe.setPlaceholderText("Digite a condição menor");
                    this.textAte.setPlaceholderText("Digite a condição maior");
                    this.panelCondicao.setTitle("PESO kg");

                    if (divDadosCondicao.isHidden()) {

                        divDadosCondicao.setHidden(false);
                        divDadosCondicaoData.setHidden(true);
                    }

                    break;
                case "DATA":

                    this.labelAte.setHidden(false);
                    this.textAte.setHidden(false);
                    this.labelDe.setText("De:");
                    this.labelAte.setText("Até:");
                    this.panelCondicao.setTitle("DATA");
                    this.textDe.setPlaceholderText("Digite a condição dd/mm/yyyy");
                    this.textAte.setPlaceholderText("Digite a condição dd/mm/yyyy");

                    if (divDadosCondicaoData.isHidden()) {

                        WebMain.updateBegin();

                        divDadosCondicao.setHidden(true);
                        divDadosCondicaoData.setHidden(false);

                        divDadosCondicaoData.resize(new WLength(100, WLength.Unit.Percentage), new WLength(100, WLength.Unit.Percentage));

                        WebMain.updateEnd();
                    }

                    //format
                    break;
                case "NUMERO":
                    this.labelAte.setHidden(false);
                    this.textAte.setHidden(false);
                    this.textDe.setPlaceholderText("Digite a condição menor");
                    this.textAte.setPlaceholderText("Digite a condição maior");
                    this.labelDe.setText("De:");
                    this.labelAte.setText("Até:");
                    this.panelCondicao.setTitle("NUMERO");

                    if (divDadosCondicao.isHidden()) {

                        divDadosCondicao.setHidden(false);
                        divDadosCondicaoData.setHidden(true);
                    }

                    break;
                default:
                    this.panelCondicao.setTitle("COMPARADOR");
                    this.textDe.setPlaceholderText("Digite a condição texto");
                    this.labelAte.setHidden(true);
                    this.textAte.setHidden(true);
                    this.labelDe.setText("Texto:");

                    if (divDadosCondicao.isHidden()) {

                        divDadosCondicao.setHidden(false);
                        divDadosCondicaoData.setHidden(true);
                    }

                    break;

            }

        });

        //add grid
        grid.addWidget(labelLote, 0, 0, AlignmentFlag.AlignMiddle);
        grid.addWidget(comboLote, 0, 1, AlignmentFlag.AlignMiddle);
        grid.addWidget(btAddLote, 0, 2, AlignmentFlag.AlignMiddle);

        grid.addWidget(labelTipo, 1, 0, AlignmentFlag.AlignMiddle);
        grid.addWidget(comboTipoAp, 1, 1, AlignmentFlag.AlignMiddle);

        grid.addWidget(labelRacao, 2, 0, AlignmentFlag.AlignMiddle);
        grid.addWidget(comboRacao, 2, 1, AlignmentFlag.AlignMiddle);
        grid.addWidget(btAddRacao, 2, 2, AlignmentFlag.AlignMiddle);

        grid.addWidget(labelApartacao, 3, 0, AlignmentFlag.AlignMiddle);
        grid.addWidget(textNome, 3, 1, AlignmentFlag.AlignMiddle);

        return container;

    }

    private WContainerWidget createPanelCondicao() {

        WContainerWidget container = new WContainerWidget();
        container.resize(new WLength(90, WLength.Unit.Percentage), WLength.Auto);

        WContainerWidget divPanel = new WContainerWidget();
        divPanel.resize(new WLength(100, WLength.Unit.Percentage), new WLength(100, WLength.Unit.Percentage));

        this.panelCondicao = new Panel("PESO kg", container);
        this.panelCondicao.resize(new WLength(100, WLength.Unit.Percentage), new WLength(100, WLength.Unit.Percentage));

        this.panelCondicao.setCentralWidget(divPanel);

        this.gridPanel = new WGridLayout(divPanel);
        this.gridPanel.setContentsMargins(5, 0, 5, 2);
        this.gridPanel.setHorizontalSpacing(20);
        this.gridPanel.setVerticalSpacing(15);

        labelDe = new WText("De:");
        labelAte = new WText("Até:");
        this.textDe = new EditLine();
        textDe.setPlaceholderText("Digite a condição menor");

        this.textAte = new EditLine();
        textAte.setPlaceholderText("Digite a condição maior");

        labelDe.setMinimumSize(WLength.Auto, new WLength(25, WLength.Unit.Pixel));
        labelAte.setMinimumSize(WLength.Auto, new WLength(25, WLength.Unit.Pixel));
        textDe.setMinimumSize(WLength.Auto, new WLength(25, WLength.Unit.Pixel));
        textAte.setMinimumSize(WLength.Auto, new WLength(25, WLength.Unit.Pixel));

        this.gridPanel.addWidget(labelDe, 0, 0, AlignmentFlag.AlignMiddle);
        this.gridPanel.addWidget(textDe, 0, 1, AlignmentFlag.AlignMiddle);

        this.gridPanel.addWidget(labelAte, 1, 0, AlignmentFlag.AlignMiddle);
        this.gridPanel.addWidget(textAte, 1, 1, AlignmentFlag.AlignMiddle);

        return container;
    }

    private WContainerWidget createPanelCondicaoData() {

        WContainerWidget container = new WContainerWidget();
        container.hide();
        container.resize(new WLength(90, WLength.Unit.Percentage), WLength.Auto);

        WContainerWidget divPanel = new WContainerWidget();
        divPanel.resize(new WLength(100, WLength.Unit.Percentage), new WLength(100, WLength.Unit.Percentage));

        Panel panelCondicaoData = new Panel("DATA", container);
        panelCondicaoData.resize(new WLength(100, WLength.Unit.Percentage), new WLength(100, WLength.Unit.Percentage));

        WGridLayout gridPanelData = new WGridLayout(divPanel);
        gridPanelData.setContentsMargins(5, 0, 5, 2);
        gridPanelData.setHorizontalSpacing(20);
        gridPanelData.setVerticalSpacing(15);

        WText labelDeData = new WText("De:");
        WText labelAteData = new WText("Até:");

        this.dateEditDe = new DateEditLine();
        this.dateEditAte = new DateEditLine();

        this.analiseDataMenorQue();

        labelDeData.setMinimumSize(WLength.Auto, new WLength(25, WLength.Unit.Pixel));
        labelDeData.setMinimumSize(WLength.Auto, new WLength(25, WLength.Unit.Pixel));

        gridPanelData.addWidget(labelDeData, 0, 0, AlignmentFlag.AlignMiddle);
        gridPanelData.addWidget(dateEditDe, 0, 1, AlignmentFlag.AlignMiddle);

        gridPanelData.addWidget(labelAteData, 1, 0, AlignmentFlag.AlignMiddle);
        gridPanelData.addWidget(dateEditAte, 1, 1, AlignmentFlag.AlignMiddle);

        panelCondicaoData.setCentralWidget(divPanel);

        return container;
    }

    private ButtonUtil createBtUtil(String toolTip) {

        ButtonUtil btAdd = new ButtonUtil("+", 10);
        btAdd.setToolTip(toolTip);
        btAdd.resize(new WLength(35, WLength.Unit.Pixel), new WLength(30, WLength.Unit.Pixel));
        btAdd.setMinimumSize(new WLength(35, WLength.Unit.Pixel), new WLength(30, WLength.Unit.Pixel));
        btAdd.setMinimumSize(new WLength(35, WLength.Unit.Pixel), new WLength(30, WLength.Unit.Pixel));

        return btAdd;
    }

    private void analiseDataMenorQue() {

        EditLine editLineDe = this.dateEditDe.getEditLine();
        EditLine editLineAte = this.dateEditAte.getEditLine();

        this.dateEditDe.getTextChanged().addListener(editLineDe, () -> {

            if (!editLineAte.getText().isEmpty()) {

                DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

                try {
                    Date dateDe = df.parse(editLineDe.getText());
                    Date dateAte = df.parse(editLineAte.getText());

                    if (dateDe.after(dateAte)) {
                        this.dateEditDe.dateWarning("Aviso a data "+editLineDe.getText()+" não pode ser maior que "+editLineAte.getText()+"!");
                        this.dateEditAte.dateNormal();
                    } else {
                        this.dateEditDe.dateNormal();
                    }

                } catch (ParseException ex) {
                    ex.printStackTrace();
                }
            }
        });

        this.dateEditAte.getTextChanged().addListener(editLineAte, () -> {

            if (!editLineDe.getText().isEmpty()) {

                DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

                try {
                    Date dateDe = df.parse(editLineDe.getText());
                    Date dateAte = df.parse(editLineAte.getText());

                    if (dateAte.before(dateDe)) {
                        this.dateEditAte.dateWarning("Aviso a data "+editLineAte.getText()+" não pode ser menor que "+editLineDe.getText()+"!");
                        this.dateEditDe.dateNormal();
                    } else {
                        this.dateEditAte.dateNormal();
                    }

                } catch (ParseException ex) {
                    ex.printStackTrace();
                }
            }

        });
    }

}
