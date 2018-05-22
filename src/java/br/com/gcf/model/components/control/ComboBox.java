/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gcf.model.components.control;

import eu.webtoolkit.jwt.EventSignal;
import eu.webtoolkit.jwt.WComboBox;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author Windows
 */
public class ComboBox<T> extends WComboBox {

    private List<T> lista;

    public ComboBox() {

        this.lista = new ArrayList<>();
        this.init();
    }

    public ComboBox(List<T> lista) {
        this();
        if (lista == null) {
            return;
        }
        lista.stream().forEach((template) -> {
            this.lista.add(template);
            super.addItem(template.toString());
        });
        this.init();
    }

    public ComboBox(T... lista) {
        this();
        if (lista == null) {
            return;
        }

        for (T template : lista) {

            this.lista.add(template);
            super.addItem(template.toString());
        }
        this.init();
    }

    private void init() {

        this.setAttributeValue("style", "border-radius: 2px;");
    }

    public void setCurrentObject(T template) {

        if (template == null) {
            return;
        }

        for (int i = 0; i < this.lista.size(); i++) {

            T temp = this.lista.get(i);

            if (temp.toString().equals(template.toString())) {
                this.setCurrentIndex(i);
                return;
            }
        }
    }

    public T getSelecteObject() {

        if (this.lista.isEmpty()) {
            return null;
        }

        T current = this.lista.get(super.getCurrentIndex());
        return current;
    }

    public void addItem(T object) {

        if (object == null) {
            return;
        }

        this.lista.add(object);
        this.addItem(object.toString());
    }

    public void removeItem(T object) {

        if (object == null) {
            return;
        }

        int index = -1;

        for (int i = 0; i < lista.size(); i++) {

            if (lista.get(i).toString().equals(object.toString())) {

                index = i;
                break;
            }
        }

        if (index == -1) {
            return;
        }

        this.lista.remove(object);
        this.removeItem(index);

    }

    @Override
    public void removeItem(int index) {

        if (index > this.lista.size() - 1 || index < 0) {
            return;
        }

        super.removeItem(index);
        this.lista.remove(index);
    }

    @Override
    public void clear() {
        super.clear(); //To change body of generated methods, choose Tools | Templates.
        this.lista.clear();
    }

    public boolean isEmpty() {

        return super.getCount() == 0;
    }

}
