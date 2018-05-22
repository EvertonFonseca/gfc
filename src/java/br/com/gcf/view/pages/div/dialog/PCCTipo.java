/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gcf.view.pages.div.dialog;

import eu.webtoolkit.jwt.AlignmentFlag;
import eu.webtoolkit.jwt.WComboBox;
import eu.webtoolkit.jwt.WContainerWidget;
import eu.webtoolkit.jwt.WDialog;
import eu.webtoolkit.jwt.WGridLayout;
import eu.webtoolkit.jwt.WLength;
import eu.webtoolkit.jwt.WLineEdit;
import eu.webtoolkit.jwt.WPushButton;
import eu.webtoolkit.jwt.WText;
import eu.webtoolkit.jwt.WTextArea;

/**
 *
 * @author Windows
 */
public class PCCTipo extends WDialog{

    private WLineEdit textNome;
    private WComboBox comboCategoria;
    private WTextArea textDescricao;

    public PCCTipo() {
        super("Cadastro Tipo");

        this.resize(new WLength(450, WLength.Unit.Pixel), WLength.Auto);
        this.init();

        show();
    }

    private void init(){
        
        WText labelCategoria = new WText("Categoria:");
        WText labelNome = new WText("Nome:");
        WText labelDescricao = new WText("Descrição:");
        
        WContainerWidget divCenter = new WContainerWidget(getContents());
        
        WGridLayout grid = new WGridLayout(divCenter);
        grid.setHorizontalSpacing(20);
        grid.setVerticalSpacing(2);
        
        this.comboCategoria = new WComboBox();
        this.comboCategoria.addItem("Moveis");
        
        this.textNome = new WLineEdit();
        this.textNome.setPlaceholderText("Digite o nome do tipo");
        
        this.textDescricao = new WTextArea();
        this.textDescricao.setMinimumSize(WLength.Auto, new WLength(150, WLength.Unit.Pixel));
        this.textDescricao.setMaximumSize(WLength.Auto, new WLength(150, WLength.Unit.Pixel));
        
        grid.addWidget(labelCategoria, 0, 0, AlignmentFlag.AlignMiddle);
        grid.addWidget(comboCategoria, 0, 1, AlignmentFlag.AlignMiddle);
        grid.addWidget(labelNome, 1, 0, AlignmentFlag.AlignMiddle);
        grid.addWidget(textNome, 1, 1, AlignmentFlag.AlignMiddle);
        grid.addWidget(labelDescricao, 2, 0, AlignmentFlag.AlignTextTop);
        grid.addWidget(textDescricao,2, 1, AlignmentFlag.AlignMiddle);
        
        WPushButton btFechar = new WPushButton("Sair", getFooter());
        btFechar.setStyleClass("btn-danger");
        WPushButton btSalvar = new WPushButton("Salvar", getFooter());
        btSalvar.setDefault(true);
        
        //btresize
        btFechar.resize(100, 35);
        btSalvar.resize(100, 35);
        //listenner
        btFechar.clicked().addListener(btFechar,(mouse)->{reject();});
        
    }
    
}
