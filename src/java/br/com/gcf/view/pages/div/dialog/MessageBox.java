/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gcf.view.pages.div.dialog;

import br.com.gcf.model.components.control.Button;
import eu.webtoolkit.jwt.Icon;
import eu.webtoolkit.jwt.Signal;
import eu.webtoolkit.jwt.StandardButton;
import eu.webtoolkit.jwt.WLength;
import eu.webtoolkit.jwt.WMessageBox;
import eu.webtoolkit.jwt.WPushButton;
import java.util.EnumSet;

/**
 *
 * @author Windows
 */
public class MessageBox extends WMessageBox{

    private Signal emitConfirmar;
    private Signal emitCancelar;
    
    public MessageBox(String title, String message) {
        super(title,message, Icon.NoIcon,EnumSet.of(StandardButton.NoButton));
        getTitleBar().setMaximumSize(WLength.Auto,new WLength(50, WLength.Unit.Pixel));
        this.emitConfirmar = new Signal();
        this.emitCancelar  = new Signal();
        
        this.setTitleBarEnabled(true);
        
        resize(500,300);

        Button btOk = new Button("Confirmar",10);
        btOk.resize(110,30);
        btOk.setDefault(true);
     
        Button btCancel = new Button("Cancelar",10);
        btCancel.resize(110,30);
        btCancel.setStyleClass("btn-danger");
        
        btOk.clicked().addListener(btCancel,(mouse)->{
          
            this.emitConfirmar.trigger();
            reject();
            remove();
        
        });
        btCancel.clicked().addListener(btCancel,(mouse)->{
          
            this.emitCancelar.trigger();
            reject();
            remove();
        
        });
        
        this.addButton(btOk, StandardButton.Ok);
        this.addButton(btCancel, StandardButton.Cancel);
        
        show();
    }

    /**
     * @return the emitConfirmar
     */
    public Signal getEmitConfirmar() {
        return emitConfirmar;
    }
    /**
     * @return the emitCancelar
     */
    public Signal getEmitCancelar() {
        return emitCancelar;
    }

    
}
