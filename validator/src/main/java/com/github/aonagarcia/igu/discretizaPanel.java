/*
 * discretizaPanel.java
 *
 * Created on 11 de agosto de 2008, 05:17 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.github.aonagarcia.igu;

import java.awt.GridLayout;
import javax.swing.JComboBox;

/**
 *
 * @author Javier
 */
public class discretizaPanel extends claseIGU {
    
    private String t[]={"Equidistancia", "Frecuencia"};
    private JComboBox listaAlgoritmos=new JComboBox(t);
    //public JButton m_parametrosBtn=new JButton("Configurar...");
    //public JButton m_aplicarBtn=new JButton("Aplicar");
    //public JButton m_guardarBtn=new JButton("Guardar");
    private discretizeParameters m_discParamsDlg;
   
    /** Creates a new instance of discretizaPanel */
    public discretizaPanel() {
        setLayout(new GridLayout(2,2,20,20));
        
        //2011 m_guardarBtn.setEnabled(true);
        add(listaAlgoritmos);
        /*2011 add(m_parametrosBtn);
        add(m_aplicarBtn);
        add(m_guardarBtn);
         **/
        //setBorder(BorderFactory.createTitledBorder(null, "Discretizar variables", TitledBorder.LEFT, TitledBorder.DEFAULT_POSITION) );
        
        /*m_aplicarBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ctrl.cmdDiscretize(listaAlgoritmos.getSelectedIndex());
            }
        });
        
        m_parametrosBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                m_discParamsDlg=new discretizeParameters((String)listaAlgoritmos.getItemAt(listaAlgoritmos.getSelectedIndex()),ctrl.discretizar);
            }
        });
        
        m_guardarBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ctrl.saveBD(ctrl.BD);
            }
        });
        */
    }
    
    public void paramsDialog()
    {
        m_discParamsDlg=new discretizeParameters((String)listaAlgoritmos.getItemAt(listaAlgoritmos.getSelectedIndex()),ctrl.discretizar);    
    }
    
}
