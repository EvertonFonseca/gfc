/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gcf.view.pages.div.dialog;

import br.com.gcf.control.Database;
import br.com.gcf.control.dao.Fazenda_DAO;
import br.com.gcf.model.dto.Fazenda_DTO;
import br.com.gcf.model.components.control.Button;
import br.com.gcf.model.components.control.EditLine;
import br.com.gcf.model.components.dialog.DialogAbstracao;
import br.com.gcf.model.table.FazendaTableModel;
import br.com.gcf.view.Web;
import br.com.gcf.view.pages.div.DivFazendas;
import eu.webtoolkit.jwt.AlignmentFlag;
import eu.webtoolkit.jwt.WColor;
import eu.webtoolkit.jwt.WContainerWidget;
import eu.webtoolkit.jwt.WDialog;
import eu.webtoolkit.jwt.WGridLayout;
import eu.webtoolkit.jwt.WLength;
import eu.webtoolkit.jwt.WText;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author Windows
 */
public class DCFazenda extends DialogAbstracao {

    private EditLine textNome,textLatitude,textLongetude;
    private Web web;
    
    public DCFazenda(Web web) {
        super("Cadastro Fazenda");

        this.resize(new WLength(600, WLength.Unit.Pixel),new WLength(380, WLength.Unit.Pixel));
        this.setResizable(true);
        this.web = web;
        this.init();

        show();
    }

    private void init() {

        //dados empresa
        WContainerWidget divDadosFazenda = createDadosFazenda();
      
        this.getContents().setOverflow(WContainerWidget.Overflow.OverflowHidden);
        getTitleBar().setMaximumSize(WLength.Auto,new WLength(50, WLength.Unit.Pixel));
        
        WContainerWidget divCenter = new WContainerWidget(getContents());
        
        WGridLayout grid = new WGridLayout(divCenter);
        grid.setHorizontalSpacing(20);
        grid.setVerticalSpacing(15);
 
        grid.addWidget(divDadosFazenda, 0, 0);

        Button btFechar = new Button("Sair", getFooter(),20);
        btFechar.setStyleClass("btn-danger");
       
        Button btSalvar = new Button("Salvar", getFooter(),20);
        btSalvar.setDefault(true);

        //btresize
        btFechar.resize(100, 35);
        btSalvar.resize(100, 35);
        //listenner
        btFechar.clicked().addListener(btFechar, (mouse) -> {
            reject();
        });
        btSalvar.clicked().addListener(btSalvar, (mouse) -> {
          
            Fazenda_DTO fazenda = new Fazenda_DTO();
            fazenda.setNome(Web.UTF8toISO(textNome.getText()));
            fazenda.setLatitude(textLatitude.getText());
            fazenda.setLongitude(textLongetude.getText());
            
            if (Fazenda_DAO.insert(fazenda)) {
                DivFazendas.signalFazendas.trigger(fazenda);
            }
            else{
                
               this.web.createMessageTemp("Não foi possivel criar a fazenda error!",Web.Tipo_Mensagem.AVISO);
            }
            
            reject();
        });

    }

    private WContainerWidget createDadosFazenda() {

        WContainerWidget container = new WContainerWidget();
        container.resize(new WLength(90, WLength.Unit.Percentage), WLength.Auto);
        
        WGridLayout grid = new WGridLayout(container);
        grid.setContentsMargins(0, 0, 0, 0);
        grid.setHorizontalSpacing(20);
        grid.setVerticalSpacing(5);
        
        WText labelNome = new WText("Fazenda:");
        WText labelLatitude = new WText("Latitude:");
        labelLatitude.setMinimumSize(new WLength(100, WLength.Unit.Pixel), WLength.Auto);
        
        WText labelLongetude = new WText("Longetude:");
        
        this.textNome = new EditLine();
        this.textLatitude = new EditLine();
        this.textLongetude = new EditLine();
       
        textNome.setFocus();
        
        //add grid
        grid.addWidget(labelNome, 0, 0,AlignmentFlag.AlignMiddle);
        grid.addWidget(textNome, 0, 1,AlignmentFlag.AlignMiddle);
        grid.addWidget(labelLatitude,1, 0,AlignmentFlag.AlignMiddle);
        grid.addWidget(textLatitude, 1, 1,AlignmentFlag.AlignMiddle);
        grid.addWidget(labelLongetude, 2, 0,AlignmentFlag.AlignMiddle);
        grid.addWidget(textLongetude, 2, 1,AlignmentFlag.AlignMiddle);
        
        return container;
     
    }

}
