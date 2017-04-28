/*
 * showTable.java
 *
 * Created on 21 de septiembre de 2008, 04:47 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.github.aonagarcia.igu;

import com.github.aonagarcia.extras.Instances;
import com.github.aonagarcia.extras.Attribute;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.TableColumnModel;
import javax.swing.table.AbstractTableModel;
import javax.swing.ListSelectionModel;

/**
 *
 * @author Javier
 */
public class showTable extends JPanel{
    
    class InstancesTableModel extends AbstractTableModel {
        
        protected Instances  m_data;                
        
        public InstancesTableModel(Instances data) {
            
            setData(data);
        }
        
        public void setData(Instances data) {
            
            m_data = data;
        }
        
        
        public int getRowCount() {
            return m_data.numInstances();
        }
        
        
        public int getColumnCount() {            
                return m_data.numAttributes()+1;
        }
        
        public void setValueAt(Object value, int row, int col) {
           
        }
        
        public Object getValueAt(int row, int column) {
            
            
                if (column == 0)
                    return new Integer(row+1);
                if (m_data.instance(row).isMissing(column-1))
                    return null;
                
                if (m_data.attribute(column-1).isNominal())
                    return (String)m_data.instance(row).stringValue(column-1);
                
                if (m_data.attribute(column-1).isNumeric())
                    return new Double(m_data.instance(row).value(column-1));
                return null;
                
           
        }
        
        public String getColumnName(int column) {
            
                if (column==0)
                    return "No.";
                else
                    return m_data.attribute(column-1).name();
           
        }
        
        public Class getColumnClass(int col) {
            Class       result;
            result = null;
          
                if (col == 0)
                    result = Integer.class;
                else 
                    if (m_data.instance(0).attribute(col-1).type() == Attribute.NUMERIC)
                        result = Double.class;
                    else
                        result = String.class;
           
            return result;
        }
        
        public boolean isCellEditable(int row, int col) {
                return false;
        }
        
    }
    
    protected JTable m_Table = new JTable();
    protected InstancesTableModel m_Model;
    /** Creates a new instance of showTable */
    public showTable() {
        
        m_Table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        m_Table.setAutoResizeMode(m_Table.AUTO_RESIZE_OFF);
           
        
        
        setLayout(new BorderLayout());
        add(new JScrollPane(m_Table), BorderLayout.CENTER);
    }
    
    public void setInstances(Instances data) {
        //m_Model = null;
        if (m_Model == null) {
            
            m_Model = new InstancesTableModel(data);
            m_Table.setModel(m_Model);
            
            TableColumnModel tcm = m_Table.getColumnModel();
            tcm.getColumn(0).setMaxWidth(40);
        } else {
            
            m_Model.setData(data);
            m_Model.fireTableStructureChanged();
            m_Table.clearSelection();
        }
        m_Table.revalidate();
        m_Table.repaint();
        
    }
    
    /*public void setCellFlag(int x, int y,int tError)
    {
        render.setCellFlag(x,y,tError);
    }
    */
}
