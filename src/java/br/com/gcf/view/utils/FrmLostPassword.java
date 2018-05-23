/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gcf.view.utils;

import br.com.gcf.control.dao.Usuario_DAO;
import br.com.gcf.control.email.M7Email;
import br.com.gcf.control.threads.ReportBean;
import br.com.gcf.control.threads.ReportTask;
import br.com.gcf.model.components.control.Button;
import br.com.gcf.model.components.control.EditLine;
import br.com.gcf.model.components.dialog.Loader;
import br.com.gcf.model.dto.Usuario_DTO;
import br.com.gcf.model.table.UsuarioTableModel;
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

        String hostName = WApplication.getInstance().getEnvironment().getHostName()+"/GCF/login";
        WText textEmail = new WText("<h4>Email:</h4>");
        EditLine editEmail = new EditLine();
        editEmail.setPlaceholderText("Digite o seu email");

        Button btEnviar = new Button("Enviar", getFooter(), 10);
        btEnviar.setDefault(true);

        Button btFechar = new Button("Sair", getFooter(), 10);
        btFechar.setStyleClass("btn-danger");

        btFechar.clicked().addListener(btFechar, (mouse) -> {
            reject();
            remove();
        });

        btEnviar.clicked().addListener(btFechar, (mouse) -> {

            //check se o email é valido
            if (!UsuarioTableModel.isEmailValid(editEmail.getText())) {

                web.createMessageTemp("Email " + editEmail.getText() + " é invalido!", Web.Tipo_Mensagem.AVISO);
                return;
            }
            //check se o email exist nos registros
            if (!Usuario_DAO.isEmailExist(editEmail.getText())) {

                web.createMessageTemp("Email " + editEmail.getText() + " é invalido!", Web.Tipo_Mensagem.AVISO);
                return;
            }

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
                        Usuario_DTO user = Usuario_DAO.readUsuarioByName(editEmail.getText());

                        if (user != null) {
                        
                            StringBuffer buffer = new StringBuffer();
                            buffer.append("Nome: " + user.getNome() + "\nEmail: " + user.getEmail() + "\nSenha: " + user.getPassword() + "\n");
                            buffer.append("Link: http://"+hostName);
                            statusOp = email.sendEmail("Esqueceu a Senha",buffer.toString(), editEmail.getText());
                        }
                        
                    } finally {

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
