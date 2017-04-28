/*
 * LogAndMsgs.java
 *
 * Created on 4 de diciembre de 2008, 07:50 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.github.aonagarcia.igu;

import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JOptionPane;
import javax.swing.JLabel;
import java.awt.Component;
import java.awt.BorderLayout;
import java.awt.Font;
/**
 *
 * @author Javier
 */
public class LogAndMsgs extends JPanel{
    
    private JTextArea   m_log=new JTextArea(1,50);
    private int scrollMax=0;
    private Component [] c;
    /** Creates a new instance of LogAndMsgs */
    public LogAndMsgs() {
        
        setLayout(new BorderLayout());
        m_log.setEditable(false);
        m_log.setFont(new Font("Arial", Font.PLAIN, 16));
        add(new JLabel(" Log: ",JLabel.RIGHT),BorderLayout.WEST);
        add(new JScrollPane(m_log),BorderLayout.CENTER);
   
        
    }
    
    
    public void addStringToLog(String s)
    {
            m_log.append("\n"+s);            
            m_log.setCaretPosition(m_log.getDocument().getLength()-1);
    }
    
    public int showMessage(Component pC,Object msg, String dialogName, int t)
    {
        int opc=JOptionPane.showConfirmDialog(pC,msg,dialogName,t);    
        return opc;
    }
    
    public void clearLog()
    {
        m_log.removeAll();
    }
}
