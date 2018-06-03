/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gcf.view.pages.div;

import br.com.gcf.view.Web;
import eu.webtoolkit.jwt.JSignal1;
import eu.webtoolkit.jwt.WApplication;
import eu.webtoolkit.jwt.WContainerWidget;
import eu.webtoolkit.jwt.WLength;
import eu.webtoolkit.jwt.WVBoxLayout;

/**
 *
 * @author Windows
 */
public class DivEstatistica extends WContainerWidget {

    private Web web;
    private JSignal1<String> signalMap; 
    private String timeout;
    
    public DivEstatistica(Web web) {

        this.resize(new WLength(100, WLength.Unit.Percentage), new WLength(100, WLength.Unit.Percentage));
        this.web = web;
        this.init();
    }

    @Override
    public void remove() {
        
        WApplication.getInstance().doJavaScript(""
                + ""
                + "clearTimeout("+timeout+");");
        super.remove(); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    private void init() {

        this.signalMap = new JSignal1<String>(this,"") {};
        this.signalMap.addListener(this,(arg) -> {
            
            this.timeout = arg;
        });
        
        WVBoxLayout box = new WVBoxLayout(this);
        box.setContentsMargins(5, 10, 5, 0);
        box.setSpacing(0);

        WContainerWidget divMap = new WContainerWidget();
        divMap.resize(new WLength(100, WLength.Unit.Percentage), new WLength(100, WLength.Unit.Percentage));
        divMap.setId("map");
        WApplication.getInstance().doJavaScript("try{\n"
                + "mapboxgl.accessToken = 'pk.eyJ1IjoiZXZlcnRvbmZvbnNlY2EiLCJhIjoiY2o2eGlmeDB6MjY5bTJxbGE1bHd0MGloMyJ9.4NP9z61Z_S8wyiebqwvBJA';\n"
                + "var map = new mapboxgl.Map({\n"
                + "container: 'map',\n"
                + "style: 'mapbox://styles/mapbox/satellite-v9',"
                + "center: [-50.4654162,-21.2055624],\n"
                + "zoom: 9 // starting zoom\n,"
                + "dragRotate: false, \n"
                + "attributionControl: false"
                + "});"
                + ""
                + "var timeout = setInterval(function() {\n"
                + "map.resize();"
                + "},1000);"
                + this.signalMap.createCall("timeout")
                + "map.resize();\n"
                + "} catch (e) {\n"
                + "console.log(e)\n"
                + "}"
        );
         
        box.addWidget(divMap);
        
    }
}
