/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gcf.view;

import br.com.gcf.control.CookieLogin;
import br.com.gcf.control.dao.Usuario_DAO;
import br.com.gcf.control.threads.ReportBean;
import br.com.gcf.control.threads.ReportTask;
import br.com.gcf.control.threads.ReportTask2;
import br.com.gcf.model.components.control.EditLine;
import br.com.gcf.view.utils.FrmLostPassword;
import eu.webtoolkit.jwt.Cursor;
import eu.webtoolkit.jwt.WApplication;
import eu.webtoolkit.jwt.WCheckBox;
import eu.webtoolkit.jwt.WContainerWidget;
import eu.webtoolkit.jwt.WLineEdit;
import br.com.gcf.model.components.control.Button;
import br.com.gcf.model.components.dialog.DMessagem;
import br.com.gcf.model.components.dialog.Loader;
import br.com.gcf.model.dto.Usuario_DTO;
import eu.webtoolkit.jwt.Signal;
import eu.webtoolkit.jwt.Signal1;
import eu.webtoolkit.jwt.WImage;
import eu.webtoolkit.jwt.WSound;
import eu.webtoolkit.jwt.WTemplate;
import eu.webtoolkit.jwt.WText;

/**
 *
 * @author Windows
 */
public class PageLogin extends WContainerWidget {

    private EditLine textEmail, textSenha;
    private WSound sound;
    private Web web;
    private Button btLogin;
    private Loader loader;

    public PageLogin(Web web) {

        setOverflow(Overflow.OverflowHidden);

        this.web = web;
        this.init();

        // this.Sound();
    }

    private void init() {

        WTemplate template = Web.loaderFileHtml("/br/com/gcf/html/PageLogin.html", getClass());

        String key = WApplication.getInstance().getEnvironment().getCookieValue(Index.SESSION_ID_NAME);

        String cookieEmail = "Everton Fonseca";
        String cookieSenha = "ssbwarcq";

//        if (Index.mapCookies.containsKey(key)) {
//
//            CookieLogin cookie = Index.mapCookies.get(key);
//
//            if (cookie != null) {
//
//                cookieEmail = cookie.getNameEmail();
//                cookieSenha = cookie.getPassword();
//            }
//        }
        WImage imageLogo = new WImage("assets/img/logo-dark.png");

        this.textEmail = new EditLine(cookieEmail);
        textEmail.setStyleClass("form-control");
        textEmail.setPlaceholderText("Email ou Nome");
        textEmail.setSelection(textEmail.getText().length(), textEmail.getText().length());

        this.textSenha = new EditLine(cookieSenha);
        textSenha.setStyleClass("form-control");
        textSenha.setPlaceholderText("Password");
        textSenha.setEchoMode(WLineEdit.EchoMode.Password);

        WCheckBox checkBox = new WCheckBox("Remember me");
        checkBox.setChecked(true);

        this.btLogin = new Button("Login", 20);
        btLogin.setStyleClass("btn btn-primary btn-lg btn-block");

        btLogin.clicked().addListener(this, (mouse) -> {

            this.loader = new Loader(this.web);
            
            ReportBean.runReports(new ReportTask<PageLogin>(WApplication.getInstance(), this) {
                @Override
                public void run() {

                    this.beginLock();
                    if (isUserEmpty()) {

                        signal.btLogin.setEnabled(false);
                        DMessagem mesagem = web.createMessageTemp("O campo Email ou Nome não foi preenchido!", Web.Tipo_Mensagem.AVISO);
                        mesagem.getSignalClosing().addListener(mesagem, () -> {
                            signal.btLogin.setEnabled(true);
                        });
                        signal.loader.destroy();
                        this.endLock();
                        return;
                    }
                    //checa se o login é valido
                    if (!checaUserLoginOn()) {
                        signal.loader.destroy();
                        this.endLock();
                        return;
                    }

                    if (checkBox.isChecked()) {

                        if (Index.mapCookies.containsKey(key)) {

                            CookieLogin cookie = new CookieLogin(key, textEmail.getText(), textSenha.getText());
                            Index.mapCookies.replace(key, cookie);

                        } else {

                            CookieLogin cookie = new CookieLogin(key, textEmail.getText(), textSenha.getText());
                            Index.mapCookies.put(key, cookie);

                        }
                    }

                    if (signal.sound != null) {
                        signal.sound.stop();
                        signal.sound = null;
                    }

                    signal.web.getServer().postAll(textEmail.getText());
                    System.out.println("Conection Email: " + textEmail.getText());
                    app.setInternalPath("admin", true);
                    signal.loader.destroy();
                    this.endLock();
                
                }
            });
        });

        WText textForgetPass = new WText("<a>Forgot password?</a>");
        textForgetPass.mouseWentOver().addListener(textForgetPass, (mouse) -> {
            textForgetPass.getDecorationStyle().setCursor(Cursor.PointingHandCursor);
        });
        textForgetPass.mouseWentOut().addListener(textForgetPass, (mouse) -> {
            textForgetPass.getDecorationStyle().setCursor(Cursor.ArrowCursor);
        });
        textForgetPass.clicked().addListener(this, (mouse) -> {

            new FrmLostPassword(web);
        });

        template.bindWidget("image-logo", imageLogo);
        template.bindWidget("edit-email", textEmail);
        template.bindWidget("edit-password", textSenha);
        template.bindWidget("edit-check", checkBox);
        template.bindWidget("login-button", btLogin);
        template.bindWidget("forget-password", textForgetPass);

        try {
            this.addWidget(template);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean isUserEmpty() {

        boolean status = false;

        if (textEmail.getText().isEmpty() || textSenha.getText().isEmpty()) {

            this.btLogin.setEnabled(false);
            DMessagem mesagem = this.web.createMessageTemp("Usuário não encontrado!", Web.Tipo_Mensagem.AVISO);
            mesagem.getSignalClosing().addListener(mesagem, () -> {
                this.btLogin.setEnabled(true);
            });
            status = true;
        }

        return status;
    }

    void Sound() {

        this.sound = new WSound("sound/scape_alarm.webm", this);
        sound.setLoops(0);
        sound.play();

    }

    private boolean checaUserLoginOn() {

        Usuario_DTO user = Usuario_DAO.isLoginSignInValid(textEmail.getText(), textSenha.getText());
        boolean status = user != null;

        if (status) {

            if (user.isAtivo()) {
                this.btLogin.setEnabled(false);
                DMessagem mesagem = web.createMessageTemp("Usuario, já esta logado no sistema!", Web.Tipo_Mensagem.AVISO);
                mesagem.getSignalClosing().addListener(mesagem, () -> {
                    this.btLogin.setEnabled(true);
                });
                status = false;
            } else {

                Usuario_DAO.updateAtivo(user.getId(), true);
                user.setAtivo(true);
                this.web.setUsuarioLogin(user);
            }
        } else {
            this.btLogin.setEnabled(false);
            DMessagem mesagem = web.createMessageTemp("Email, Nome ou senha não foram encontradas!", Web.Tipo_Mensagem.AVISO);
            mesagem.getSignalClosing().addListener(mesagem, () -> {
                this.btLogin.setEnabled(true);
            });
        }

        return status;
    }

}
