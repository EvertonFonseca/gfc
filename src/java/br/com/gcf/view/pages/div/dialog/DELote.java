/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gcf.view.pages.div.dialog;

import br.com.gcf.control.dao.Alimento_DAO;
import br.com.gcf.control.dao.Fazenda_DAO;
import br.com.gcf.control.dao.Lote_DAO;
import br.com.gcf.model.dto.Fazenda_DTO;
import br.com.gcf.model.components.control.Button;
import br.com.gcf.model.components.control.ButtonUtil;
import br.com.gcf.model.components.control.ComboBox;
import br.com.gcf.model.components.control.EditLine;
import br.com.gcf.model.components.dialog.DialogAbstracao;
import br.com.gcf.model.dto.Alimento_DTO;
import br.com.gcf.model.dto.Lote_DTO;
import br.com.gcf.view.Web;
import br.com.gcf.view.pages.div.DivLotes;
import eu.webtoolkit.jwt.AlignmentFlag;
import eu.webtoolkit.jwt.WAnimation;
import eu.webtoolkit.jwt.WContainerWidget;
import eu.webtoolkit.jwt.WDialog;
import eu.webtoolkit.jwt.WGridLayout;
import eu.webtoolkit.jwt.WLength;
import eu.webtoolkit.jwt.WTemplate;
import eu.webtoolkit.jwt.WText;

/**
 *
 * @author Windows
 */
public class DELote extends DialogAbstracao {

    private EditLine textNome;
    private EditLine textArroba,textCarcaca;
    private ComboBox<Alimento_DTO> comboRacao;
    private Web web;
    private Lote_DTO lote;
    
    public DELote(Web web,Lote_DTO lote) {
        super("Editor Lote");

        this.lote = lote;
        this.web = web;
        
        this.resize(new WLength(500, WLength.Unit.Pixel),new WLength(350, WLength.Unit.Pixel));
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
          
            this.lote.setNome(Web.UTF8toISO(textNome.getText()));
            this.lote.setRacao(comboRacao.getSelecteObject());
            this.lote.setArroba(Double.parseDouble(textArroba.getText()));
            this.lote.setPesoDaCarcaca(Double.parseDouble(textCarcaca.getText()));
            
            if (Lote_DAO.update(lote)) {
                
                DivLotes.signalLotes.trigger("Lote atualziado com sucesso!");
            }
            reject();
        });

    }

    private WContainerWidget createDadosLote() {

        WContainerWidget container = new WContainerWidget();
        container.resize(new WLength(90, WLength.Unit.Percentage), WLength.Auto);
        
        WGridLayout grid = new WGridLayout(container);
        grid.setContentsMargins(0, 0, 0, 0);
        grid.setHorizontalSpacing(20);
        grid.setVerticalSpacing(15);
  
        WText labelNome = new WText("Lote:");
        WText labelCarcaca = new WText("Carcaça:");
        WText labelArroba = new WText("Arroba:");
        WText labelRacao = new WText("Ração:");
        
        WTemplate btAddRacao = new WTemplate();
        btAddRacao.setTemplateText("<button type=\"button\" style=\"\" class=\"btn btn-default\">+</button>");

        btAddRacao.clicked().addListener(this, (mouse) -> {

          //  DCFazenda diagloFazenda = new DCFazenda(web);

        });

        this.comboRacao   = new ComboBox(Alimento_DAO.readAllAlimentos());
        this.comboRacao.setCurrentObject(lote.getRacao());
        
        this.textNome = new EditLine(lote.getNome());
        this.textCarcaca = new EditLine(lote.getPesoDaCarcaca());
        this.textArroba = new EditLine(lote.getArroba());
       
        textNome.endSelectedText();
        textNome.setFocus();
        
        //add grid
        grid.addWidget(labelNome, 1, 0, AlignmentFlag.AlignMiddle);
        grid.addWidget(textNome, 1, 1, AlignmentFlag.AlignMiddle);
        grid.addWidget(labelCarcaca, 2, 0, AlignmentFlag.AlignMiddle);
        grid.addWidget(textCarcaca, 2, 1, AlignmentFlag.AlignMiddle);
        grid.addWidget(new WText("%"), 2, 2, AlignmentFlag.AlignMiddle);
        grid.addWidget(labelArroba, 3, 0, AlignmentFlag.AlignMiddle);
        grid.addWidget(textArroba, 3, 1, AlignmentFlag.AlignMiddle);
        grid.addWidget(new WText("@"), 3, 2, AlignmentFlag.AlignMiddle);
        grid.addWidget(labelRacao, 4, 0, AlignmentFlag.AlignMiddle);
        grid.addWidget(comboRacao, 4, 1, AlignmentFlag.AlignMiddle);
        grid.addWidget(btAddRacao,4, 2, AlignmentFlag.AlignMiddle);
        
        
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

}
