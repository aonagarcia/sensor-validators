/*
 * Main.java
 *
 * Created on 16 de julio de 2008, 12:48
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.github.aonagarcia.svd;

import com.github.aonagarcia.igu.MainFrame;

/**
 *
 * @author Administrador
 */

public class Main {
    
    /** Creates a new instance of Main */
    public Main() {
        
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
//        try{

//           UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName() );
//       }
//        catch (Exception ignored){}
        try{

            MainFrame app=new MainFrame();
           
        }
        catch (java.awt.HeadlessException e){
            e.printStackTrace();
            System.err.println(e.getMessage() );
        }     
    }
}
