/*
 * datosVariablePanel.java
 *
 * Created on 23 de julio de 2008, 17:17
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.github.aonagarcia.igu;

import javax.swing.JPanel;
import javax.swing.JTable;
import java.awt.Dimension;
import java.awt.BorderLayout;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;



/**
 *
 * @author Javier
 */


public class intervalosPanel extends JPanel {

    class InstancesTableModel extends AbstractTableModel {

        protected Object [] m_data;

        public InstancesTableModel(Object [] data) {

            setData(data);
        }

        public void setData(Object [] data) {

            m_data = data;            
            //m_as = m_data.attributeStats(Idx);
        }


        public int getRowCount() {
            return m_data.length;
            //return m_as.nominalCounts.length;
        }


        public int getColumnCount() {
            return 2;
        }

        public Object getValueAt(int row, int column) {
            switch (column) {
                case 0:
                    return new Integer(row + 1);
                case 1:
                    if (m_data[row] == null)
                        return null;
                    else
                        if (m_data[row] instanceof String)
                            return (String)m_data[row];

                default:
                    return null;
            }
        }

        public String getColumnName(int column) {
            switch (column) {
                case 0:
                    return new String("No.");
                case 1:
                    return new String("Intervalo");
                default:
                    return null;
            }
        }

       
        public Class getColumnClass(int col) {
            // return getValueAt(0, col).getClass();

            Class       result;
            result = null;

            if (col == 0)
                result = Integer.class;
            else
                result = String.class;

            return result;
        }

        public boolean isCellEditable(int row, int col) {
            if (col==0)
                return false;
            else
                return false;
        }

    }

    protected JTable m_Table = new JTable();
    protected InstancesTableModel m_Model;

    RenderTabla render = null;

    public intervalosPanel() {

        //setBorder(BorderFactory.createTitledBorder(null, "Intervalos", TitledBorder.CENTER, TitledBorder.DEFAULT_POSITION) );
        m_Table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        m_Table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        m_Table.setPreferredScrollableViewportSize(new Dimension(150,200));
        m_Table.setAutoscrolls(true);

        setLayout(new BorderLayout());
        JScrollPane jsp=new JScrollPane(m_Table);        
        add(jsp, BorderLayout.CENTER);
    }

    public void setAttributeData(Object[] data) {
        if (m_Model == null) {
            m_Model = new InstancesTableModel(data);
            m_Table.setModel(m_Model);

            TableColumnModel tcm = m_Table.getColumnModel();
            tcm.getColumn(0).setMaxWidth(20);
            tcm.getColumn(1).setMinWidth(200);
        } else {
            m_Model.setData(data);
            m_Table.clearSelection();
        }

//        m_Table.sizeColumnsToFit(1);
        m_Table.revalidate();
        m_Table.repaint();
    }

    public ListSelectionModel getSelectionModel() {

        return m_Table.getSelectionModel();
    }

    public RenderTabla getRender() {
        return render;
    }


    public void repaintTable()
    {
       m_Table.repaint();
    }

    public void clear()
    {
        m_Model=null;
        m_Table.setModel(new DefaultTableModel());
    }
   
}
