/*
 * claseIGU.java
 *
 * Created on 18 de julio de 2008, 04:58 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.github.aonagarcia.igu;

import javax.swing.JPanel;
import com.github.aonagarcia.svd.Control;
/**
 *
 * @author Javier
 */

public class claseIGU extends JPanel{
    
    /*
     *Crea una instancia de "Control" que va a ser utilizada por la interfaz grafica
     *para enviarle las peticiones del usuario y estas sean procesadas por "ctrl"
     */
    public static Control ctrl; 
    
    /** Creates a new instance of claseIGU */
    public claseIGU() {
       ctrl=new Control();
    }
    
}
