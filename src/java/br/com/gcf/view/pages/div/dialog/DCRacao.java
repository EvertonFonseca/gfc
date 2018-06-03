/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gcf.view.pages.div.dialog;

import br.com.gcf.control.dao.Alimento_DAO;
import br.com.gcf.control.dao.Lote_DAO;
import br.com.gcf.control.dao.TpUsuario_DAO;
import br.com.gcf.control.dao.Usuario_DAO;
import br.com.gcf.model.components.control.Button;
import br.com.gcf.model.components.control.ButtonUtil;
import br.com.gcf.model.components.control.ComboBox;
import br.com.gcf.model.components.control.EditLine;
import br.com.gcf.model.components.dialog.DialogAbstracao;
import br.com.gcf.model.dto.Alimento_DTO;
import br.com.gcf.model.dto.TpUsuario_DTO;
import br.com.gcf.model.dto.TpUsuario_DTO;
import br.com.gcf.model.dto.Usuario_DTO;
import br.com.gcf.model.table.UsuarioTableModel;
import br.com.gcf.view.Web;
import br.com.gcf.view.pages.div.DivLotes;
import eu.webtoolkit.jwt.AlignmentFlag;
import eu.webtoolkit.jwt.Orientation;
import eu.webtoolkit.jwt.Side;
import eu.webtoolkit.jwt.Signal2;
import eu.webtoolkit.jwt.WAnimation;
import eu.webtoolkit.jwt.WBorder;
import eu.webtoolkit.jwt.WBreak;
import eu.webtoolkit.jwt.WColor;
import eu.webtoolkit.jwt.WContainerWidget;
import eu.webtoolkit.jwt.WGridLayout;
import eu.webtoolkit.jwt.WLength;
import eu.webtoolkit.jwt.WLineEdit;
import eu.webtoolkit.jwt.WString;
import eu.webtoolkit.jwt.WTable;
import eu.webtoolkit.jwt.WTemplate;
import eu.webtoolkit.jwt.WText;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

/**
 *
 * @author Windows
 */
public class DCRacao extends DialogAbstracao {

    private EditLine textNome, textAtivo;
    private ComboBox<String> comboAtivo;
    private Web web;
    private WTable table;

    public DCRacao(Web web) {
        super("Cadastro Ração");

        this.web = web;

        this.resize(new WLength(600, WLength.Unit.Pixel), new WLength(350, WLength.Unit.Pixel));
        this.setResizable(false);
        this.init();

        WAnimation animation = new WAnimation(WAnimation.AnimationEffect.SlideInFromTop);
        animation.setDuration(500);
        this.setHidden(false, animation);
    }

    private void init() {

        //dados empresa
        WContainerWidget divDadosLote = createDados();
        this.getContents().setOverflow(WContainerWidget.Overflow.OverflowAuto, Orientation.Vertical);

        getTitleBar().setMaximumSize(WLength.Auto, new WLength(50, WLength.Unit.Pixel));

        WContainerWidget divCenter = new WContainerWidget(getContents());

        WGridLayout grid = new WGridLayout(divCenter);
        grid.setHorizontalSpacing(20);
        grid.setVerticalSpacing(2);

        grid.addWidget(divDadosLote, 0, 0);

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

            if (textNome.getText().isEmpty()) {

                web.createMessageTemp("Alguns campos não foram preenchidos", Web.Tipo_Mensagem.AVISO);
                return;
            }

            //check se a ração já possui nos registros
            if (isRacaoExist()) {

                web.createMessageTemp("Já existe ração com este nome " + textNome.getText() + " nos registros!", Web.Tipo_Mensagem.AVISO);
                textNome.setFocus();
                textNome.endSelectedText();
                return;
            }

            Alimento_DTO alimento = new Alimento_DTO();
            alimento.setAtivo(comboAtivo.getSelecteObject().equals("Ativo"));
            alimento.setNome(Web.UTF8toISO(textNome.getText()));

            StringBuffer buffer = new StringBuffer("");
            //check se possui receitas
            for (int i = 1; i < table.getRowCount(); i++) {

                String nome = ((WText) table.getElementAt(i, 0).getChildren().get(0)).getText().toString();
                String atributo = ((WText) table.getElementAt(i, 1).getChildren().get(0)).getText().toString();

                buffer.append(Web.UTF8toISO(nome)).append(":").append(Web.UTF8toISO(atributo));

                if (i < (table.getRowCount() - 1)) {
                    buffer.append(",");
                }

            }// end for    
            alimento.setMistura(buffer.toString());

            if (Alimento_DAO.insert(alimento)) {

                getSignalInsert().trigger("Ração inserido com sucesso!");
            }
            reject();
        });

    }

    private WContainerWidget createDados() {

        WContainerWidget container = new WContainerWidget();
        container.resize(new WLength(90, WLength.Unit.Percentage), WLength.Auto);

        WContainerWidget containerSub = new WContainerWidget(container);
        containerSub.resize(new WLength(100, WLength.Unit.Percentage), WLength.Auto);

        WGridLayout grid = new WGridLayout(containerSub);
        grid.setContentsMargins(0, 25, 0, 0);
        grid.setHorizontalSpacing(20);
        grid.setVerticalSpacing(20);

        WText labelNome = new WText("Nome:");
        WText labelAtivo = new WText("Status:");

        container.addWidget(new WBreak());

        table = new WTable(container);
        table.addStyleClass("table form-inline");
        table.hide();
        table.resize(new WLength(100, WLength.Unit.Percentage), WLength.Auto);
        table.setHeaderCount(1);

        table.getElementAt(0, 0).addWidget(new WText("Nome"));
        table.getElementAt(0, 1).addWidget(new WText("Preparação"));
        table.getElementAt(0, 2).addWidget(new WText("Remover"));
        table.getElementAt(0, 0).getDecorationStyle().getFont().setSize(new WLength("13px"));
        table.getElementAt(0, 1).getDecorationStyle().getFont().setSize(new WLength("13px"));
        table.getElementAt(0, 2).getDecorationStyle().getFont().setSize(new WLength("13px"));
        table.getColumnAt(0).setWidth(new WLength(45, WLength.Unit.Percentage));
        table.getColumnAt(1).setWidth(new WLength(45, WLength.Unit.Percentage));
        table.getColumnAt(2).setWidth(new WLength(10, WLength.Unit.Percentage));

        WTemplate btAddReceita = new WTemplate();
        btAddReceita.setTemplateText("<button type=\"button\" style=\"\" class=\"btn btn-default\">+</button>");

        btAddReceita.clicked().addListener(btAddReceita, (event) -> {

            DialogAtributo dc = new DialogAtributo();
            dc.signalInsert.addListener(dc, (nome, valor) -> {

                int size = table.getRowCount();

                WText varNome = new WText(Web.UTF8toISO(nome).toUpperCase());
                varNome.getDecorationStyle().getFont().setSize(new WLength("14px"));
                varNome.setTextAlignment(AlignmentFlag.AlignCenter);

                WText varValor = new WText(Web.UTF8toISO(valor).toUpperCase());
                varValor.getDecorationStyle().getFont().setSize(new WLength("14px"));
                varValor.setTextAlignment(AlignmentFlag.AlignCenter);

                WText btOpcao = new WText("<button style=\"\" class=\"btn btn-default btn-sm\">X</button>");
                btOpcao.getDecorationStyle().getFont().setSize(new WLength("13px"));
                btOpcao.setTextAlignment(AlignmentFlag.AlignCenter);

                btOpcao.clicked().addListener(btOpcao, (arg) -> {

                    table.deleteRow(size);

                    if (table.getRowCount() == 0) {
                        table.hide();
                    }
                });

                table.getElementAt(size, 0).addWidget(varNome);
                table.getElementAt(size, 1).addWidget(varValor);
                table.getElementAt(size, 2).addWidget(btOpcao);
                table.getRowAt(size).setHeight(new WLength("35px"));

                if (table.isHidden()) {
                    table.show();
                }

            });
        });

        this.comboAtivo = new ComboBox<String>(new String[]{"Ativo", "Desativo"});
        this.textNome = new EditLine();
        this.textNome.setPlaceholderText("Ex: Ração Lote 1");
        this.textNome.setAutoComplete(false);

        textNome.setFocus();

        //add grid
        grid.addWidget(labelAtivo, 0, 0, AlignmentFlag.AlignMiddle);
        grid.addWidget(this.comboAtivo, 0, 1, AlignmentFlag.AlignMiddle);
        grid.addWidget(labelNome, 1, 0, AlignmentFlag.AlignMiddle);
        grid.addWidget(textNome, 1, 1, AlignmentFlag.AlignMiddle);
        grid.addWidget(btAddReceita, 1, 3, AlignmentFlag.AlignMiddle);

        return container;
    }

    private class DialogAtributo extends DialogAbstracao {

        private Signal2<String, String> signalInsert;

        public DialogAtributo() {
            super("Receita");
            this.resize(new WLength(350, WLength.Unit.Pixel), new WLength(250, WLength.Unit.Pixel));
            this.setResizable(false);
            this.init();

            this.show();
        }

        private void init() {

            this.signalInsert = new Signal2<>();
            WContainerWidget container = new WContainerWidget(getContents());
            container.resize(new WLength(90, WLength.Unit.Percentage), WLength.Auto);

            WGridLayout grid = new WGridLayout(container);
            grid.setContentsMargins(0, 10, 0, 0);
            grid.setHorizontalSpacing(20);
            grid.setVerticalSpacing(15);

            WText labelNome = new WText("Nome:");
            EditLine editNome = new EditLine();
            editNome.setPlaceholderText("Ex: Milho");

            WText labelValor = new WText("Valor:");
            EditLine editValor = new EditLine();
            editValor.setPlaceholderText("Ex: 250 kg");

            editNome.setFocus();

            Button btSalvar = new Button("Adicionar", getFooter(), 10);
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

                if (editNome.getText().isEmpty() || editValor.getText().isEmpty()) {

                    web.createMessageTemp("Alguns campos não foram preenchidos", Web.Tipo_Mensagem.AVISO);
                    return;
                }

                //envia o signal para o seu slot
                signalInsert.trigger(editNome.getText(), editValor.getText());

                reject();
            });

            grid.addWidget(labelNome, 0, 0, AlignmentFlag.AlignMiddle);
            grid.addWidget(editNome, 0, 1, AlignmentFlag.AlignMiddle);
            grid.addWidget(labelValor, 1, 0, AlignmentFlag.AlignMiddle);
            grid.addWidget(editValor, 1, 1, AlignmentFlag.AlignMiddle);
        }
    }

    /**
     * Esse metedo checa se o nome que será inserido se ja existe nos registro
     * return true or false
     */
    private boolean isRacaoExist() {

        return Alimento_DAO.isNameExist(this.textNome.getText());
    }
}
