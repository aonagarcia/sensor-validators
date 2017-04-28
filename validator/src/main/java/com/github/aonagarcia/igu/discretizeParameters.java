/*
 * discretizeParameters.java
 *
 * Created on 23 de septiembre de 2008, 03:53 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package com.github.aonagarcia.igu;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.BorderFactory;
import javax.swing.JCheckBox;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.github.aonagarcia.extras.Discretize;

/**
 *
 * @author Javier
 */
public class discretizeParameters extends JFrame {

    private JPanel mainPanel = new JPanel(new BorderLayout());
    private JLabel m_NumBinsLbl = new JLabel("N�mero de intervalos", JLabel.LEFT);
    private JTextField m_NumBinsTxt = new JTextField();
    private JLabel m_weightInstancesPerBinLbl = new JLabel("N�mero de instancias por intervalo", JLabel.LEFT);
    private JTextField m_weightInstancesPerBinTxt = new JTextField();
    private JCheckBox m_findNumBinsCkb = new JCheckBox("Encontrar n�mero de intervalos");
    private JButton m_aceptarBtn = new JButton("Aceptar");
    private JButton m_cancelarBtn = new JButton("Cerrar");
    private Discretize m_discretizeObjetc;

    /** Creates a new instance of discretizeParameters */
    public discretizeParameters(String nombreAlgoritmo, Discretize obj) {


        super("Configurar algoritmo");

        m_discretizeObjetc = obj;

        if (nombreAlgoritmo.equalsIgnoreCase("Equidistancia")) {
            m_NumBinsLbl.setText("N�mero de intervalos");
            m_NumBinsTxt.setEnabled(true);
            m_weightInstancesPerBinTxt.setEnabled(false);
            m_weightInstancesPerBinTxt.setVisible(false);
            m_weightInstancesPerBinLbl.setVisible(false);
            m_findNumBinsCkb.setSelected(obj.getFindNumBins()); //modificado 7/02/09
            m_findNumBinsCkb.setEnabled(true);

        } else if (nombreAlgoritmo.equalsIgnoreCase("Frecuencia")) {
            m_NumBinsLbl.setText("N�mero de intervalos");
            m_findNumBinsCkb.setSelected(false); //modificado 7/02/09
            m_NumBinsTxt.setEnabled(true);
            m_weightInstancesPerBinTxt.setEnabled(true);
            m_weightInstancesPerBinTxt.setVisible(true);
            m_weightInstancesPerBinLbl.setVisible(true);
            m_findNumBinsCkb.setEnabled(false);
            m_findNumBinsCkb.setVisible(false);
        } else if (nombreAlgoritmo.equalsIgnoreCase("Mezcla de Gaussianas")) {
            m_NumBinsLbl.setText("N�mero de Gaussianas");
            m_NumBinsTxt.setEnabled(true);
            m_findNumBinsCkb.setSelected(false);
            m_findNumBinsCkb.setEnabled(false);
            m_findNumBinsCkb.setVisible(false);
            m_weightInstancesPerBinTxt.setEnabled(false);
            m_weightInstancesPerBinTxt.setVisible(false);
            m_weightInstancesPerBinLbl.setVisible(false);
          
        }
        else{
            customDiscretizationPanel cdp=new customDiscretizationPanel();
            //mainPanel.add(cdp);

        }

        this.getContentPane().setLayout(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createTitledBorder(null, "  ", TitledBorder.CENTER, TitledBorder.DEFAULT_POSITION));

        m_NumBinsTxt.setText(Integer.toString(obj.getBins()));
        m_weightInstancesPerBinTxt.setText(Double.toString(obj.getDesiredWeightOfInstancesPerInterval()));
        //m_findNumBinsCkb.setSelected(obj.getFindNumBins());//modificado 7/02/09

        JPanel m_elemts = new JPanel(new GridLayout(6, 1, 10, 10));

        m_elemts.add(m_NumBinsLbl);
        m_elemts.add(m_NumBinsTxt);
        m_elemts.add(m_weightInstancesPerBinLbl);
        m_elemts.add(m_weightInstancesPerBinTxt);
        m_elemts.add(m_findNumBinsCkb);

        JPanel m_BttnsPanel = new JPanel(new FlowLayout());

        m_BttnsPanel.add(m_aceptarBtn);
        m_BttnsPanel.add(m_cancelarBtn);

        mainPanel.add(m_elemts, BorderLayout.CENTER);
        mainPanel.add(m_BttnsPanel, BorderLayout.SOUTH);

        this.getContentPane().add(mainPanel, BorderLayout.CENTER);
        setLocationRelativeTo(null);

        addWindowListener(
                new WindowAdapter() {

                    public void windowClosing(WindowEvent e) {
                        dispose();
                    }
                });
        this.setPreferredSize(new Dimension(280, 280));
        this.pack();
        setVisible(true);

        m_cancelarBtn.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        m_aceptarBtn.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                m_discretizeObjetc.setBins(Integer.valueOf(m_NumBinsTxt.getText()));
                m_discretizeObjetc.setDesiredWeightOfInstancesPerInterval(Double.valueOf(m_weightInstancesPerBinTxt.getText()));
                m_discretizeObjetc.setFindNumBins(m_findNumBinsCkb.getModel().isSelected());
                dispose();
            }
        });
    }
}
