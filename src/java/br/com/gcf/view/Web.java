/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gcf.view;

import br.com.gcf.control.Client;
import br.com.gcf.control.Server;
import br.com.gcf.model.dto.Usuario_DTO;
import br.com.gcf.model.components.dialog.DMessagem;
import br.com.gcf.model.components.dialog.Loader;
import br.com.gcf.view.pages.PAdmin;
import eu.webtoolkit.jwt.*;
import eu.webtoolkit.jwt.WVBoxLayout;
import eu.webtoolkit.jwt.servlet.UploadedFile;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Windows
 */
public class Web extends WContainerWidget implements Client {

    private Server server;
    private String client;
    private WVBoxLayout box;
    public static Signal2<Integer, Integer> signalSize = new Signal2<>();
    private static Usuario_DTO usuarioLogin = new Usuario_DTO();
    private WContainerWidget rootContainer;
    private String nome;
    public static WebMain webApp;

    public Web(String sessionId, WebMain webApp) {

        setId("element");
        resize(new WLength(100, WLength.Unit.Percentage), new WLength(100, WLength.Unit.Percentage));
        this.server = new Server();
        this.client = sessionId;
        this.webApp = webApp;
        server.connect(this);
        setLayoutSizeAware(true);
        WApplication.getInstance().enableUpdates(true);
        
        interPathChanged();
        this.init();
    }

    public interface Tipo_Mensagem {

        public static String SUCESSO = DMessagem.IMAGE_OK;
        public static String AVISO = DMessagem.IMAGE_WARNING;
    }

    private void init() {

        this.rootContainer = new WContainerWidget(this);
        this.rootContainer.resize(new WLength(100, WLength.Unit.Percentage), new WLength(100, WLength.Unit.Percentage));

        box = new WVBoxLayout(this.rootContainer);
        box.setContentsMargins(0, 0, 0, 0);

        PageLogin pAdmin = new PageLogin(this);
        box.addWidget(pAdmin);

    }

    public Web getInstancia() {

        return this;
    }

    void interPathChanged() {

        WApplication.getInstance().internalPathChanged().addListener(WApplication.getInstance(), new Signal1.Listener<String>() {
            @Override
            public void trigger(String arg) {

                box.clear();
                System.out.println("PathInterno: " + arg);
                System.out.println("Path 2: " + WApplication.getInstance().getBookmarkUrl(WApplication.getInstance().getInternalPath()));
                switch (arg) {

                    case Index.Path_Response.SISTEMA:

                        break;
                    case "/":
                       
                        box.addWidget(new PageLogin(getInstancia()));
                        System.err.println("Connecting: "+webApp.hasQuit());
                        break;
                    case Index.Path_Response.ADMIN:
                       
                        box.addWidget(new PAdmin(getInstancia()));
                        createMessageTemp("Login conectado com sucesso!", Tipo_Mensagem.SUCESSO);
                        
                        break;

                    default:
                        System.out.println("Erro page not found");
                        break;

                }// end switch
            }
        });
    }

    @Override
    public void run() {

        System.out.println("Login detected: " + "ID: " + WApplication.getInstance().getSessionId() + " User: " + getNome());
        WApplication.getInstance().triggerUpdate();
    }

    /**
     * @return the server
     */
    public Server getServer() {
        return server;
    }

    /**
     * @param server the server to set
     */
    public void setServer(Server server) {
        this.server = server;
    }

    /**
     * @return the client
     */
    public String getClient() {
        return client;
    }

    /**
     * @param client the client to set
     */
    public void setClient(String client) {
        this.client = client;
    }

    @Override
    protected void layoutSizeChanged(int width, int height) {
        super.layoutSizeChanged(width, height);

        Web.consoleJS("width: "+width+" , height: "+height);
        signalSize.trigger(width, height);
        WApplication.getInstance().refresh();

    }

    public void createMessageTemp(String message, String tipo) {

        new DMessagem(message, tipo, this);
    }

    public void createMessageTemp(String message, String tipo, int delay) {

        new DMessagem(message, tipo, delay, this);
    }

    private void importFile(WVBoxLayout boxV) {

        final WFileUpload fu = new WFileUpload();
        fu.setId("btinput");
        fu.resize(100, 80);
        fu.setFileTextSize(50);
        fu.setProgressBar(new WProgressBar());

        fu.changed().addListener(this, new Signal.Listener() {
            public void trigger() {
                fu.upload();

            }
        });

        fu.uploaded().addListener(this, new Signal.Listener() {
            public void trigger() {

                List<UploadedFile> load = fu.getUploadedFiles();

                for (UploadedFile uploadedFile : load) {

                    System.out.println(uploadedFile.getClientFileName() + " : " + uploadedFile.getSpoolFileName());
                }

            }
        });
        fu.fileTooLarge().addListener(this, new Signal.Listener() {
            public void trigger() {
            }
        });

        boxV.addWidget(fu);

        for (int i = 0; i < fu.getChildren().size(); i++) {

            System.out.println("Children: " + fu.getChildren().get(i).getId());
        }

    }

    public static WTemplate loaderFileHtml(String pathHtml, Class<?> classe) {

        InputStream inputStream = classe.getResourceAsStream(pathHtml);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        String data = null;
        StringBuffer buffer = new StringBuffer("");

        try {
            while ((data = reader.readLine()) != null) {

                buffer.append(data);
                buffer.append("\n");
            }

            WTemplate template = new WTemplate();
            template.setTemplateText(buffer.toString(), TextFormat.XHTMLUnsafeText);

            return template;

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return null;
    }

    public static void loaderScripts(String... path) {

        for (String url : path) {

            WApplication.getInstance().require(url);
        }
    }

    public static void callJavaScriptFunction(String name) {

        WApplication.getInstance()
                .doJavaScript(WApplication.getInstance()
                        .getJavaScriptClass().concat("." + name + "();"));

    }

    public static void loaderFile(String[] paths, String[] nomes, Object object) {

        int index = 0;
        for (int i = 0; i < paths.length; i++) {

            String path = paths[i];

            InputStream inputStream = object.getClass().getResourceAsStream(path);

            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            String data = null;
            StringBuffer buffer = new StringBuffer("");

            try {
                while ((data = reader.readLine()) != null) {

                    buffer.append(data);
                    buffer.append("\n");
                }

                System.out.println(buffer.toString());
                WApplication.getInstance().declareJavaScriptFunction(nomes[i], buffer.toString());

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

    }

    public static String formatDateToString(String format) {

        Calendar calendar = Calendar.getInstance();

        DateFormat dformat = new SimpleDateFormat(format);
        Date date = calendar.getTime();

        dformat.setTimeZone(calendar.getTimeZone());

        return dformat.format(calendar.getTime());

    }

    public static String UTF8toISO(String str) {

        Charset utf8charset = Charset.forName("UTF-8");
        Charset iso88591charset = Charset.forName("ISO-8859-1");

        ByteBuffer inputBuffer = ByteBuffer.wrap(str.getBytes());

        // decode UTF-8
        CharBuffer data = utf8charset.decode(inputBuffer);

        // encode ISO-8559-1
        ByteBuffer outputBuffer = iso88591charset.encode(data);
        byte[] outputData = outputBuffer.array();

        return new String(outputData);
    }

    /**
     * @return the usuarioLogin
     */
    public static Usuario_DTO getUsuarioLogin() {
        return usuarioLogin;
    }

    /**
     * @return the nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * @param nome the nome to set
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    public static String removeEnter(String text) {

        return text.replace("\"", "");
    }
    
    public static void consoleJS(String text){
        
        WApplication.getInstance().doJavaScript("console.log('"+text+"')");
    }

}
