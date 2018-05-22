/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gcf.model.components.control;

import eu.webtoolkit.jwt.AbstractSignal.Connection;
import eu.webtoolkit.jwt.AlignmentFlag;
import eu.webtoolkit.jwt.JSignal1;
import eu.webtoolkit.jwt.Signal;
import eu.webtoolkit.jwt.WColor;
import eu.webtoolkit.jwt.WContainerWidget;
import eu.webtoolkit.jwt.WFont;
import eu.webtoolkit.jwt.WHBoxLayout;
import eu.webtoolkit.jwt.WLength;
import eu.webtoolkit.jwt.WText;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author Windows
 */
public class DateEditLine extends WContainerWidget {

    private EditLine dateEdit;
    private WText datePicker;
    private WHBoxLayout box;
    private JSignal1<String> signalDate;
    private Signal textChanged;

    public DateEditLine() {

        this.box = new WHBoxLayout(this);
        box.setContentsMargins(0, 0, 0, 0);
        box.setSpacing(1);

        this.dateEdit = new EditLine();
        this.dateEdit.setId(this.toString());
        this.dateEdit.setReadOnly(true);

        this.datePicker = new WText("");
        this.datePicker.getDecorationStyle().getFont().setSize(WFont.Size.Large, WLength.Auto);
        this.dateNormal();
        
        this.init();
        
        box.addWidget(dateEdit, 1,AlignmentFlag.AlignLeft);
        box.addWidget(datePicker, 0,AlignmentFlag.AlignLeft);

    }

    public DateEditLine(String text) {

        this.box = new WHBoxLayout(this);
        box.setContentsMargins(0, 0, 0, 0);
        box.setSpacing(1);

        this.box = new WHBoxLayout(this);
        box.setContentsMargins(0, 0, 0, 0);
        box.setSpacing(1);

        this.dateEdit = new EditLine(text);
        this.dateEdit.setId(this.toString());
        this.dateEdit.setReadOnly(true);

        this.datePicker = new WText("");
        this.datePicker.getDecorationStyle().getFont().setSize(WFont.Size.Large, WLength.Auto);
        this.dateNormal();
        
        this.init();
           
        box.addWidget(dateEdit, 1,AlignmentFlag.AlignLeft);
        box.addWidget(datePicker, 0,AlignmentFlag.AlignLeft);

    }

    private void init() {

        this.dateEdit.setPlaceholderText("Clique para escolher uma data");
        this.textChanged = new Signal();
        this.signalDate = new JSignal1<String>(this, "SignalDate") {
        };

        this.dateEdit.enterPressed().addListener(dateEdit, () -> {

            this.dateEdit.doJavaScript("$('#" + this.dateEdit.getId() + "').datepicker('hide');");

        });

        this.signalDate.addListener(this, (arg) -> {

            Date now = new Date(Long.valueOf(arg));

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(now);

            String day = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
            day = day.length() == 1 ? "0" + day : day;

            String month = String.valueOf(calendar.get(Calendar.MONTH) + 1);
            month = month.length() == 1 ? "0" + (month) : (month);

            String year = String.valueOf(calendar.get(Calendar.YEAR));

            dateEdit.setText(day + "/" + month + "/" + year);
            this.textChanged.trigger();

        });

        this.dateEdit.doJavaScript(""
                + "$('#" + this.dateEdit.getId() + "').datepicker({\n"
                + "		format: \"dd/mm/yyyy\",	\n"
                + "		language: \"pt-BR\",\n"
                + "	        startDate: '+0d',\n"
                + "});"
                + ""
                + "$('#" + this.dateEdit.getId() + "').datepicker()\n"
                + ".on('changeDate', function(ev){\n"
                + ""
                + this.signalDate.createCall("ev.date.valueOf()") + ""
                + "$('#" + this.dateEdit.getId() + "').datepicker('hide');"
                + "});"
                + "");
    }

    public String getText() {

        return this.dateEdit.getText();
    }

    @Override
    public String toString() {
        String string = super.toString();
        return super.toString().substring(string.indexOf("@")+1,string.length());
    }
    
    public EditLine getEditLine(){
        
        return this.dateEdit;
    }
    public Signal getTextChanged() {
        return textChanged;
    }

    public void dateWarning(String message) {

        this.datePicker.setStyleClass("lnr lnr-warning");
        this.datePicker.getDecorationStyle().setForegroundColor(WColor.red);
        this.datePicker.setToolTip(message);
    }

    public void dateNormal() {

        this.datePicker.setStyleClass("lnr lnr-calendar-full");
        this.datePicker.getDecorationStyle().setForegroundColor(WColor.darkGray);
        this.datePicker.setToolTip("Clique na entrada de texto para selecionar uma data");
    }
}
