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
import eu.webtoolkit.jwt.WAnimation;
import eu.webtoolkit.jwt.WContainerWidget;
import eu.webtoolkit.jwt.WGridLayout;
import eu.webtoolkit.jwt.WLength;
import eu.webtoolkit.jwt.WLineEdit;
import eu.webtoolkit.jwt.WTemplate;
import eu.webtoolkit.jwt.WText;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

/**
 *
 * @author Windows
 */
public class DCUsuarios extends DialogAbstracao {

    private EditLine textNome,textEmail,textSenha;
    private ComboBox<TpUsuario_DTO> comboCategorias;
    private Web web;
    
    public DCUsuarios(Web web) {
        super("Cadastro Usuário");

        this.web = web;
        
        this.resize(new WLength(500, WLength.Unit.Pixel),new WLength(360, WLength.Unit.Pixel));
        this.setResizable(false);
        this.init();
      
        WAnimation animation = new WAnimation(WAnimation.AnimationEffect.SlideInFromTop);
        animation.setDuration(500);
        this.setHidden(false, animation);
    }

    private void init() {

        //dados empresa
        WContainerWidget divDadosLote = createDados();
      
        this.getContents().setOverflow(WContainerWidget.Overflow.OverflowHidden);
           
        getTitleBar().setMaximumSize(WLength.Auto,new WLength(50, WLength.Unit.Pixel));

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

            if (textNome.getText().isEmpty() || textSenha.getText().isEmpty() || textEmail.getText().isEmpty()) {

                web.createMessageTemp("Alguns campos não foram preenchidos", Web.Tipo_Mensagem.AVISO);
                return;
            }

            //check se o nome ja existe no banco
            if (Usuario_DAO.isNameExist(textNome.getText())) {

                web.createMessageTemp("O Nome " + textNome.getText()+" já existe nos registros", Web.Tipo_Mensagem.AVISO);
                return;
            }

            //check se o email é valido
            if (!isValidEmailAddress(textEmail.getText())) {

                //email isn't valid
                web.createMessageTemp("Email " + textEmail.getText() + " inválido", Web.Tipo_Mensagem.AVISO);
                textEmail.endSelectedText();
                textEmail.setFocus();
                return;
            }

            //check se o email ja existe no banco
            if (Usuario_DAO.isEmailExist(textNome.getText())) {

                web.createMessageTemp("O Email " + textEmail.getText()+" já existe nos registros", Web.Tipo_Mensagem.AVISO);
                return;
            }

            Usuario_DTO user = new Usuario_DTO();
            user.setNome(Web.UTF8toISO(textNome.getText()));
            user.setPassword(textSenha.getText());
            user.setAtivo(false);
            user.setEmail(textEmail.getText());
            user.setData(Web.formatDateToString("dd/MM/yyyy"));
            user.setTipoCategoria(comboCategorias.getSelecteObject());

            if (Usuario_DAO.insert(user)) {

                getSignalInsert().trigger("Usuário inserido com sucesso!");
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
  
        WText labelNome = new WText("Nome:");
        WText labelEmail = new WText("Email:");
        WText labelSenha = new WText("Senha:");
        WText labelTipo = new WText("Categoria:");
        
        WTemplate btAddCategoria = new WTemplate();
        btAddCategoria.setTemplateText("<button type=\"button\" style=\"\" class=\"btn btn-default\">+</button>");

        btAddCategoria.clicked().addListener(this, (mouse) -> {
            
            DCCategoria dc = new DCCategoria(web);
            dc.getSignalInsert().addListener(dc, (arg) -> {

                //cleat table
                this.comboCategorias.setListItens(TpUsuario_DAO.readAllCategorias());
                this.web.createMessageTemp(arg, Web.Tipo_Mensagem.SUCESSO);

            });

        });

        this.comboCategorias   = new ComboBox(TpUsuario_DAO.readAllCategorias());
        this.textNome = new EditLine();
        this.textNome.setAutoComplete(false);
        
        this.textEmail = new EditLine();
        this.textEmail.setAutoComplete(false);
        
        this.textSenha = new EditLine();
        this.textSenha.setAutoComplete(false);
        this.textSenha.setEchoMode(WLineEdit.EchoMode.Password);
       
        textNome.setFocus();
        
        //add grid
        grid.addWidget(labelNome, 1, 0, AlignmentFlag.AlignMiddle);
        grid.addWidget(textNome, 1, 1, AlignmentFlag.AlignMiddle);
        grid.addWidget(labelEmail, 2, 0, AlignmentFlag.AlignMiddle);
        grid.addWidget(textEmail, 2, 1, AlignmentFlag.AlignMiddle);
        grid.addWidget(labelSenha, 3, 0, AlignmentFlag.AlignMiddle);
        grid.addWidget(textSenha, 3, 1, AlignmentFlag.AlignMiddle);
        grid.addWidget(labelTipo, 4, 0, AlignmentFlag.AlignMiddle);
        grid.addWidget(comboCategorias, 4, 1, AlignmentFlag.AlignMiddle);
        grid.addWidget(btAddCategoria, 4, 2, AlignmentFlag.AlignMiddle);
        
        return container;
    }
    
    public static boolean isValidEmailAddress(String email) {
        return UsuarioTableModel.isEmailValid(email);
    }
}
