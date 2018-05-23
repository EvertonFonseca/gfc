/*
 * To change this license navBarer, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gcf.view.pages;

import br.com.gcf.view.Web;
import br.com.gcf.view.utils.ContentMain;
import br.com.gcf.view.utils.MenuBar;
import br.com.gcf.view.utils.NavBar;
import eu.webtoolkit.jwt.Signal;
import eu.webtoolkit.jwt.WApplication;
import eu.webtoolkit.jwt.WBorderLayout;
import eu.webtoolkit.jwt.WContainerWidget;
import eu.webtoolkit.jwt.WFileUpload;
import eu.webtoolkit.jwt.WInteractWidget;
import eu.webtoolkit.jwt.WLength;
import eu.webtoolkit.jwt.WProgressBar;
import eu.webtoolkit.jwt.servlet.UploadedFile;
import java.util.List;

/**
 *
 * @author Windows
 */
public class PAdmin extends WContainerWidget {

    private WBorderLayout borderLayout;
    private NavBar navBar;
    private MenuBar menuBar;
    private ContentMain contentMain;
    private Web web;

    public PAdmin(Web web) {
    
        this.web = web;
        this.init();
                   
    }

    private void init() {

        //create as instancia
        this.borderLayout = new WBorderLayout(this);
        getBorderLayout().setContentsMargins(0, 0, 0, 0);
        getBorderLayout().setSpacing(0);

        this.navBar = new NavBar(this);
        this.menuBar = new MenuBar(this);
        this.contentMain = new ContentMain(web,this);

        getBorderLayout().addWidget(getNavBar(), WBorderLayout.Position.North);
        getBorderLayout().addWidget(getMenuBar(), WBorderLayout.Position.West);
        getBorderLayout().addWidget(getContentMain(), WBorderLayout.Position.Center);

    }

    public WFileUpload createImportFile(WInteractWidget container, WContainerWidget parent) {

        WFileUpload fu = new WFileUpload(parent);
        fu.setFileTextSize(50);
        fu.resize(new WLength(100, WLength.Unit.Percentage), new WLength(100, WLength.Unit.Percentage));

        WProgressBar progresso = new WProgressBar();
        progresso.valueChanged().addListener(progresso, (value) -> {

        });

        fu.setProgressBar(progresso);
        fu.setDisplayWidget(container);

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
        
        return fu;
    }
    
    public void logout(){
        
         this.remove();
        // WApplication.getInstance().redirect("/GCF");
         WApplication.getInstance().setInternalPath("/", true);
        
    }
    
    /**
     * @return the borderLayout
     */
    public WBorderLayout getBorderLayout() {
        return borderLayout;
    }

    /**
     * @return the navBar
     */
    public NavBar getNavBar() {
        return navBar;
    }

    /**
     * @return the menuBar
     */
    public MenuBar getMenuBar() {
        return menuBar;
    }

    /**
     * @return the contentMain
     */
    public ContentMain getContentMain() {
        return contentMain;
    }

    public Web getWeb() {
        return web;
    }
    
    
}
