/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gcf.control;

import eu.webtoolkit.jwt.JSignal;
import eu.webtoolkit.jwt.JSignal1;
import eu.webtoolkit.jwt.JSlot;
import eu.webtoolkit.jwt.WApplication;
import eu.webtoolkit.jwt.WContainerWidget;
import eu.webtoolkit.jwt.WLength;
import eu.webtoolkit.jwt.WObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 *
 * @author Windows
 */
public class JavaScript extends WContainerWidget{
    
    private JSignal1<String> signal;
    private JSlot slot = new JSlot();

    public JavaScript() {
        resize(WLength.Auto, WLength.Auto);
        this.signal = new JSignal1<String>(this, "Sinal Latitude") {
        };
        
        slot.setJavaScript("function()" + "{"
                +"alert(\"Eu sou um alert!\");"
                +"}");
    }

    public void script(){
        
        WApplication.getInstance().declareJavaScriptFunction("foo", "function()"
                + "{"
                + "alert(\"Eu sou um alert!\");"
                + "}");
        String javaClass = WApplication.getInstance().getJavaScriptClass();
        WApplication.getInstance().doJavaScript(javaClass.concat(".foo();"));
        
    }
        
    public void googleMap(String script){
              
//        try{
//            
//            InputStream in = getClass().getResourceAsStream("/br/com/estoque/javascript/googlemaps/script.txt");
//            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
//            String buffer = null;
//            StringBuffer dados = new StringBuffer();
//            while((buffer = reader.readLine()) != null){
//                dados.append(buffer);
//            }
//            googleMap(dados.toString());
//            
//        } catch (IOException ex) {
//
//        }
        
        
        WContainerWidget mapCanvas = new WContainerWidget(this);
        mapCanvas.setId("map_canvas");
        mapCanvas.resize(new WLength(400, WLength.Unit.Pixel),new WLength(400, WLength.Unit.Pixel));
                
        WApplication.getInstance().require("http://maps.googleapis.com/maps/api/js?key=AIzaSyCse9kMY-wgS14Gcg2ed_I6ximvKf5hhII&sensor=false");
        
        final String mapGoogle = "\n"
                + "\n"
                + "                function(){\n"
                + "\n"
                + "                    var map;\n"
                + "                    var poligono;\n"
                + "                    var marker;\n"
                + "\n"
                + "                    var myLatLng = new google.maps.LatLng(-21.2055624,-50.4566614);\n"
                + "                    var mapOptions = {\n"
                + "                        zoom: 8,\n"
                + "                        center: myLatLng,\n"
                + "                        disableDoubleClickZoom: true,\n"
                + "                        mapTypeId: google.maps.MapTypeId.ROADMAP\n"
                + "                    };\n"
                + "                    map = new google.maps.Map(document.getElementById('map_canvas'), mapOptions);\n"
                + "                    poligono = new google.maps.Polygon({\n"
                + "                        strokeColor: '#FF0000',\n"
                + "                        strokeOpacity: 0.8,\n"
                + "                        strokeWeight: 3,\n"
                + "                        fillColor: '#FF0000',\n"
                + "                        fillOpacity: 0.35\n"
                + "                    });\n"
                + "                    poligono.setMap(map);\n"
                + "\n"
                + "                    google.maps.event.addListener(map, 'click', addLatLng);\n"
                + "                    google.maps.event.addListener(map, 'rightclick', verificaPonto);\n"
                + "                    google.maps.event.addListener(poligono, 'rightclick', verificaPonto);\n"
                + "\n"
                + "                    function verificaPonto(event){\n"
                + "\n"
                + "                        if ( !marker ) {\n"
                + "                            marker = new google.maps.Marker({ map: map });\n"
                + "                        }\n"
                + "                        try{\n"
                + "                            if(google.maps.geometry.poly.containsLocation(event.latLng,poligono)==true){\n"
                + "                                marker.setIcon('http://www.google.com/intl/en_us/mapfiles/ms/micons/green-dot.png');\n"
                + "                            }else{\n"
                + "                                marker.setIcon('http://www.google.com/intl/en_us/mapfiles/ms/micons/red-dot.png');\n"
                + "                            }\n"
                + "                        }catch(err){\n"
                + "\n"
                + "                            alert('erro');\n"
                + "                        }\n"
                + "                        marker.setPosition(event.latLng);\n"
                + "                    }\n"
                + "\n"
                + "                    function addLatLng(event) {\n"
                + "                        var vertices = poligono.getPath();\n"
                + "                        vertices.push(event.latLng);\n"
                + "                        var marker2 = new google.maps.Marker({ map: map });\n"
                + "                        marker2.setPosition(event.latLng);\n"
                + "                       "
                + signal.createCall("event.latLng")
                + " google.maps.event.addListener(marker2,'dblclick', function() {\n"
                + "                            var vertices=poligono.getPath();\n"
                + "                            vertices.removeAt(vertices.indexOf(marker2.position));\n"
                + "                            marker2.setMap(null);\n"
                + "                        });\n"
                + "\n"
                + "                    }\n"
                + "\n"
                + "\n"
                + "                }";
        
        WApplication.getInstance().declareJavaScriptFunction("mapGoogle",mapGoogle);
        WApplication.getInstance().doJavaScript(WApplication.getInstance().getJavaScriptClass().concat(".mapGoogle();"));
    }
    
    /**
     * @return the signal
     */
    public JSignal1<String> getSignal() {
        return signal;
    }

    /**
     * @param signal the signal to set
     */
    public void setSignal(JSignal1<String> signal) {
        this.signal = signal;
    }

    /**
     * @return the slot
     */
    public JSlot getSlot() {
        return slot;
    }

    /**
     * @param slot the slot to set
     */
    public void setSlot(JSlot slot) {
        this.slot = slot;
    }
    
    
    
}
