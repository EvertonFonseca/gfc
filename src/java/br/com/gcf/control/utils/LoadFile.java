/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gcf.control.utils;

import eu.webtoolkit.jwt.WApplication;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 *
 * @author Windows
 */
public class LoadFile {

    public String loadFile(String path) {

        ClassLoader classLoader = this.getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(path);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        String data = null;
        StringBuffer buffer = new StringBuffer("");

        try {
            while ((data = reader.readLine()) != null) {

                buffer.append(data);
                buffer.append("\n");
            }

            return buffer.toString();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return buffer.toString();
    }
}
