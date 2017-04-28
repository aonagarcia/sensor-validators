/*
 * Criterio.java
 *
 * Created on 16 de julio de 2008, 16:33
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.github.aonagarcia.igu;


import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
/**
 *
 * @author Administrador
 */
public class Criterio extends JPanel
{
    private JPanel m_Panel = new JPanel();
    
    protected JTextField m_TextField = new JTextField();
    
    protected  JTextField m_TextField2 = new JTextField();
    
    private JButton m_ClearButton = new JButton("Limpiar");
    
    private JComboBox m_List = new JComboBox();
    
    /** Creates a new instance of Criterio */
    public Criterio(String crit, final int fields)
    {
        m_Panel.setLayout( new GridLayout(1, fields, 10, 10) );
        m_TextField = new JTextField();
        m_ClearButton = new JButton();
        m_ClearButton.setContentAreaFilled(false);
        m_ClearButton.setBorderPainted(false);
        m_ClearButton.setFocusPainted(false);
        m_ClearButton.setText("Limpiar");
        if(fields == 4)
            m_TextField2 = new JTextField();
        addFocusers();
        m_ClearButton.addActionListener
        (
            new ActionListener()
            {
                public void actionPerformed(ActionEvent evt)
                {
                    getM_TextField().setText("");
                    if(fields == 4)
                        m_TextField2.setText("");
                }
            }
        );
        m_TextField.setColumns(10);
        m_ClearButton.setToolTipText("Limpiar");
        m_Panel.add(m_ClearButton);
        m_Panel.add(m_TextField);
        if(fields == 4) m_Panel.add(m_TextField2);
        m_Panel.setBorder(BorderFactory.createTitledBorder(null, crit, TitledBorder.LEFT, TitledBorder.DEFAULT_POSITION) );
        add(m_Panel);
    }
    
    public Criterio(String crit, final int fields, String[] lista)
    {
        m_Panel = new JPanel();
        m_Panel.setLayout(new GridLayout(1, fields + 1, 10, 10) );
        m_TextField = new JTextField();
        addFocusers();
        m_ClearButton = new JButton();
        m_ClearButton.setContentAreaFilled(false);
        m_ClearButton.setBorderPainted(false);
        m_ClearButton.setFocusPainted(false);
        m_ClearButton.setText("Limpiar");
        m_ClearButton.addActionListener
        (
            new ActionListener()
            {
                public void actionPerformed(ActionEvent evt)
                {
                    getM_TextField().setText("");
                    m_List.setSelectedIndex(0);
                }
            }
        );
        m_TextField.setColumns(10);
        m_Panel.add(m_ClearButton);
        m_Panel.add(m_TextField);
        if(fields == 4) m_Panel.add(m_TextField2);
        m_List = new JComboBox();
        m_List.setMaximumRowCount(10);
        m_List.setModel(new DefaultComboBoxModel(lista) );
        m_List.addActionListener
        (
            new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    if(m_List.getSelectedIndex() == 0)
                    {
                        getM_TextField().setText("");
                        return;
                    }
                    getM_TextField().setText( (String) m_List.getItemAt(m_List.getSelectedIndex()  ) );
                }
            }
        );
        m_Panel.add(m_List);
        m_Panel.setBorder(BorderFactory.createTitledBorder(null, crit, TitledBorder.LEFT, TitledBorder.DEFAULT_POSITION) );
        add(m_Panel, BorderLayout.WEST);
    }
    
    private void addFocusers()
    {
        getM_TextField().addFocusListener
        (
            new FocusListener()
            {
                public void focusGained(FocusEvent e)
                {
                    getM_TextField().selectAll();
                }
                public void focusLost(FocusEvent e)
                {
                }
            }
        );
        m_TextField2.addFocusListener
        (
            new FocusListener()
            {
                public void focusGained(FocusEvent e)
                {
                    m_TextField2.selectAll();
                }
                public void focusLost(FocusEvent e)
                {
                }
            }
        );
    }
    
    public String toString()
    {
        if(m_List.getSelectedIndex() == 0)
            return "";
        return (String) m_List.getItemAt(m_List.getSelectedIndex() );
    }
    
    public JTextField getM_TextField() {
        return m_TextField;
    }

    public void setM_TextField(JTextField m_TextField) {
        this.m_TextField = m_TextField;
    }
}
