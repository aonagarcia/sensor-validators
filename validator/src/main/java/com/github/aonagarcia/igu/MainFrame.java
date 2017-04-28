/*
 * Main.java
 *
 * Created on 16 de julio de 2008, 13:23
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.github.aonagarcia.igu;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JFrame;

/**
 *
 * @author Administrador
 */
public class MainFrame {
    
    static public IGUPanel myIGU;
    static public JFrame jf ;
    static public JDialog _startdlg;
    /** Creates a new instance of Main */
    public MainFrame() {

            myIGU=new IGUPanel();
            jf = new JFrame("Sistema para Validaci�n de Datos");
            jf.getContentPane().setLayout(new BorderLayout() );
            jf.getContentPane().add(myIGU, BorderLayout.CENTER);
            jf.addWindowListener
            (
                new WindowAdapter()
                {
                    public void windowClosing(WindowEvent e)
                    {
                        jf.dispose();
                        System.exit(0);
                    }
                }
            );
            showStartDlg();
        try {
            Thread.sleep(0);
        } catch (InterruptedException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
            jf.setExtendedState(JFrame.MAXIMIZED_BOTH);
            _startdlg.setVisible(false);
            jf.setVisible(true);
    }
    
    public static JFrame getFrame()
    {
        return jf;
    }

    public void showStartDlg() {
        if (_startdlg == null) {
            _startdlg = new startDlg(MainFrame.getFrame(), true);
            _startdlg.setLocationRelativeTo(MainFrame.getFrame());
            _startdlg.setVisible(true);

        }
    }
}
