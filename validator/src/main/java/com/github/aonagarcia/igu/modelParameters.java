
package com.github.aonagarcia.igu;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.BorderFactory;
import javax.swing.JPasswordField;



import java.awt.Dimension;

import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

import com.github.aonagarcia.svd.Control;


/**
 *
 * @author Javier
 */
public class modelParameters extends JFrame{
    
    private JPanel mainPanel=new JPanel(new BorderLayout());
    
    private JLabel m_SignificanceLevel=new JLabel("Significance Level",JLabel.RIGHT);
    private JTextField m_SignificanceLevelTxt=new JTextField();
    
    private JLabel m_pvalue=new JLabel("p_value",JLabel.RIGHT);
    private JTextField m_pvalueTxt=new JTextField();
    
    
    private JLabel m_Th_Pf=new JLabel("Th_Pf",JLabel.RIGHT);
    private JTextField m_Th_PfTxt=new JTextField();
    
    private JButton m_aceptarBtn=new JButton("Aceptar");
    private JButton m_cancelarBtn=new JButton("Cerrar");
    
    private String password="123456";
    
    /** Creates a new instance of modelParameters */
    public modelParameters(double sl, double pv,double pfTh,  final Control ctrl) {
        
        
        super("Par�metros del modelo");
        
        //if (passwdMessagesDlg()==false)
        //   return;
        
        this.getContentPane().setLayout(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createTitledBorder(null, "  ", TitledBorder.CENTER,TitledBorder.DEFAULT_POSITION) );
        
        m_SignificanceLevelTxt.setText(Double.toString(sl));
        m_pvalueTxt.setText(Double.toString(pv));
        m_Th_PfTxt.setText(Double.toString(pfTh));
        
        JPanel m_elemts=new JPanel(new GridLayout(3,2,20,10));
        
        m_elemts.add(m_SignificanceLevel);
        m_elemts.add(m_SignificanceLevelTxt);
        m_elemts.add(m_pvalue);
        m_elemts.add(m_pvalueTxt);
        m_elemts.add(m_Th_Pf);
        m_elemts.add(m_Th_PfTxt);
        
        
        JPanel m_BttnsPanel=new JPanel(new FlowLayout());
        
        m_BttnsPanel.add(m_aceptarBtn);
        m_BttnsPanel.add(m_cancelarBtn);
        
        mainPanel.add(m_elemts,BorderLayout.CENTER);
        mainPanel.add(m_BttnsPanel,BorderLayout.SOUTH);
        
        this.getContentPane().add(mainPanel,BorderLayout.CENTER);
        setLocationRelativeTo(null);
        
        addWindowListener
                (
                new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        }
        );
        this.setPreferredSize(new Dimension(280,180));
        this.pack();
        setVisible(true);
        
        m_cancelarBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        
        m_aceptarBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                ctrl.setParams(new Double(m_SignificanceLevelTxt.getText()),new Double(m_pvalueTxt.getText()),new Double(m_Th_PfTxt.getText()));
                /*ctrl.significaceLevel=new Double(m_SignificanceLevelTxt.getText());
                ctrl.p_value=new Double(m_pvalueTxt.getText());
                ctrl.Pf_Th=new Double(m_Th_PfTxt.getText());*/
                dispose();
            }
        });
    }
    
    public boolean passwdMessagesDlg() {
        
        JPanel connectionPanel;
        JLabel passwordLabel = new JLabel("Contrase�a:   ", JLabel.LEFT);
        JTextField passwordField = new JPasswordField("");
        
        connectionPanel = new JPanel(new GridLayout(1,2,5,5));
        connectionPanel.add(passwordLabel);
        connectionPanel.add(passwordField);
        
        boolean goOn = false;
        do{
            if (JOptionPane.showOptionDialog(null, connectionPanel,
                    "Acceder a par�metros",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null,null,null) != 0) {
                return false;
            }
                        
            try{
                if (password.equals(passwordField.getText()))
                    return true;
                else
                    passwordField.setText("");
                
            }catch(Exception ex){
               
            }
        }while(!goOn);
        
        return false;
    }
}
