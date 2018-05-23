/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gcf.view.pages.div.dialog;

import br.com.gcf.control.dao.Alimento_DAO;
import br.com.gcf.control.dao.TpUsuario_DAO;
import br.com.gcf.model.components.control.Button;
import br.com.gcf.model.components.control.ComboBox;
import br.com.gcf.model.components.control.EditLine;
import br.com.gcf.model.components.dialog.DialogAbstracao;
import br.com.gcf.model.dto.Alimento_DTO;
import br.com.gcf.model.dto.TpUsuario_DTO;
import br.com.gcf.view.Web;
import br.com.gcf.view.pages.div.DivCategorias;
import eu.webtoolkit.jwt.AlignmentFlag;
import eu.webtoolkit.jwt.WAnimation;
import eu.webtoolkit.jwt.WContainerWidget;
import eu.webtoolkit.jwt.WGridLayout;
import eu.webtoolkit.jwt.WLength;
import eu.webtoolkit.jwt.WText;

/**
 *
 * @author Windows
 */
public class DCCategoria extends DialogAbstracao {

    private EditLine textNome,textArroba,textCarcaca;
    private ComboBox<Alimento_DTO> comboRacao;
    private Web web;
    
    public DCCategoria(Web web) {
        super("Cadastro Tipo Usuários");

        this.web = web;
        
        this.resize(new WLength(500, WLength.Unit.Pixel),new WLength(250, WLength.Unit.Pixel));
        this.setResizable(true);
        this.init();
      
        WAnimation animation = new WAnimation(WAnimation.AnimationEffect.SlideInFromTop);
        animation.setDuration(500);
        this.setHidden(false, animation);
    }

    private void init() {

        //dados empresa
        WContainerWidget divDadosLote = createDadosLote();
      
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
          
            TpUsuario_DTO tpUser = new TpUsuario_DTO();
            tpUser.setNome(Web.UTF8toISO(textNome.getText()));
            
            if (TpUsuario_DAO.insert(tpUser)) {
                
                getSignalInsert().trigger("Tipo de usuário inserido com sucesso!");
                
            }
            reject();
        });

    }

    private WContainerWidget createDadosLote() {

        WContainerWidget container = new WContainerWidget();
        container.resize(new WLength(90, WLength.Unit.Percentage), WLength.Auto);
        
        WGridLayout grid = new WGridLayout(container);
        grid.setContentsMargins(0,20, 0, 0);
        grid.setHorizontalSpacing(20);
        grid.setVerticalSpacing(15);
  
        WText labelNome = new WText("Nome:");
        
        this.comboRacao   = new ComboBox(Alimento_DAO.readAllAlimentos());
        this.textNome = new EditLine();
        this.textNome.setPlaceholderText("Digite o nome da categoria");
       
        textNome.setFocus();
        
        //add grid
        grid.addWidget(labelNome, 1, 0, AlignmentFlag.AlignMiddle);
        grid.addWidget(textNome, 1, 1, AlignmentFlag.AlignMiddle);
        
        return container;
     
    }
}
