/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gcf.view.utils;

import br.com.gcf.control.email.M7Email;
import br.com.gcf.control.threads.ReportBean;
import br.com.gcf.control.threads.ReportTask;
import br.com.gcf.model.components.control.Button;
import br.com.gcf.model.components.control.EditLine;
import br.com.gcf.model.components.dialog.Loader;
import br.com.gcf.view.Web;
import eu.webtoolkit.jwt.Signal1;
import eu.webtoolkit.jwt.WApplication;
import eu.webtoolkit.jwt.WBreak;
import eu.webtoolkit.jwt.WContainerWidget;
import eu.webtoolkit.jwt.WDialog;
import eu.webtoolkit.jwt.WLength;
import eu.webtoolkit.jwt.WText;
import java.io.UnsupportedEncodingException;

/**
 *
 * @author Windows
 */
public class FrmLostPassword extends WDialog {

    private Web web;

    public FrmLostPassword(Web web) {
        super("Esqueci a senha");

        this.web = web;
        this.resize(new WLength(400, WLength.Unit.Pixel), WLength.Auto);
        this.init();

        this.show();
    }

    private void init() {

        WContainerWidget divCenter = new WContainerWidget(getContents());

        WText textEmail = new WText("<h3>Email:</h3>");
        EditLine editEmail = new EditLine();
        editEmail.setPlaceholderText("Digite o seu email");

        Button btFechar = new Button("Sair", getFooter(), 10);
        btFechar.setStyleClass("btn-danger");

        Button btEnviar = new Button("Enviar", getFooter(), 10);
        btEnviar.setDefault(true);

        btFechar.clicked().addListener(btFechar, (mouse) -> {
            reject();
            remove();
        });

        btEnviar.clicked().addListener(btFechar, (mouse) -> {

            web.createMessageTemp("Enviando Email para " + editEmail.getText(), Web.Tipo_Mensagem.AVISO, 5000);
            Loader loader = new Loader(web);

            Signal1 signalEmail = new Signal1();
            signalEmail.addListener(this, (status) -> {

                try {
                    boolean s = (boolean) status;
                    String message = s ? "Email foi enviado com sucesso!" : "Não foi possivel enviar o Email!";

                    web.createMessageTemp(message, s ? Web.Tipo_Mensagem.SUCESSO : Web.Tipo_Mensagem.AVISO, 5000);
                } finally {
                    loader.destroy();
                    remove();
                }

            });

            ReportBean.runReports(new ReportTask<Signal1>(WApplication.getInstance(), signalEmail) {
                @Override
                public void run() {

                    M7Email email = new M7Email();
                    boolean statusOp = false;
                    try {
                       
                        statusOp = email.sendEmail("Forget password", "Link: http://localhost:3501/GCF/", editEmail.getText());

                    }  finally {

                        this.beginLock();

                        signal.trigger(statusOp);

                        this.endLock();

                    }

                }
            });

            reject();

        });

        //add no container
        divCenter.addWidget(textEmail);
        divCenter.addWidget(editEmail);
        divCenter.addWidget(new WBreak());
    }
}
