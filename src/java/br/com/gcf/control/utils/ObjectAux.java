/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gcf.control.utils;

import eu.webtoolkit.jwt.AbstractSignal;
import eu.webtoolkit.jwt.Signal1;
import eu.webtoolkit.jwt.WApplication;
import eu.webtoolkit.jwt.WObject;

public class ObjectAux<T> implements Signal1.Listener<T> {

    private Signal1<T> signalSend;

    public ObjectAux() {

        this.signalSend = new Signal1<>();

    }

    @Override
    public void trigger(T template) {

        this.signalSend.trigger(template);
       

    }

    public void removeOnListener(Signal1.Listener<T> object) {

        this.signalSend.removeListener(object);
    }

    public AbstractSignal.Connection addListener(Signal1.Listener<T> listener){
        
        return this.signalSend.addListener(WApplication.getInstance().getRoot(),listener);
    }
    
}
