/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.aonagarcia.svd;

import java.io.File;

/**
 *
 * @author javier
 */
public class validacionScript {

    String path = "D:\\Personal\\Pemex\\Validator\\validator\\Datos\\Valid\\";
    File directorio = new File(path);
    String[] ficheros = directorio.list();
    String m_file;

    validacionScript() {
    }

    public void runScript(){

    String path = "D:\\Personal\\Pemex\\Validator\\validator\\Datos\\Valid\\";
    File directorio = new File(path);
    String[] ficheros = directorio.list();
    String m_file;

        for (int i = 0; i < ficheros.length; i++) {
            m_file=path+ficheros[i];

        }
    }

    public static void main(String[] argv)
    {
        validacionScript vs=new validacionScript();
        vs.runScript();
    }
}
