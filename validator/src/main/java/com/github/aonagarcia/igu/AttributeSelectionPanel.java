/*
 * AttributeSelectionPanel.java
 *
 * Created on 16 de julio de 2008, 16:36
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.github.aonagarcia.igu;


import javax.swing.JPanel;
import javax.swing.JTable;
import java.awt.Dimension;
import javax.swing.JButton;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import javax.swing.JScrollPane;
import javax.swing.BorderFactory;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.AbstractTableModel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.github.aonagarcia.extras.Instances;

import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Administrador
 */
public class AttributeSelectionPanel extends JPanel
{
    /**
     * A table model that looks at the names of attributes and maintains
     * a list of attributes that have been "selected".
     */
    class AttributeTableModel extends AbstractTableModel
    {
        /** The instances who's attribute structure we are reporting */
        //protected Instances m_Instances;
        
        /** The flag for whether the instance will be included */
        protected boolean [] m_Selected;
        protected boolean habilitarCheckBox;
        
        /**
         * Creates the tablemodel with the given set of instances.
         *
         * @param instances the initial set of Instances
         */
        public AttributeTableModel(Instances instances)
        {
            
            setInstances(instances);
        }
        
        public void setHabilitarCheckBox(boolean b)
        {
            habilitarCheckBox=b;
        }
        /**
         * Sets the tablemodel to look at a new set of instances.
         *
         * @param instances the new set of Instances.
         */
    public void setInstances(Instances instances) {

      m_Instances = instances;
      m_Selected = new boolean [m_Instances.numAttributes()];
    }
  
        /**
         * Gets the number of attributes.
         *
         * @return the number of attributes.
         */
        public int getRowCount()
        {
            return m_Selected.length;
        }
        
        /**
         * Gets the number of columns: 3
         *
         * @return 3
         */
        public int getColumnCount()
        {
            return 3;
        }
        
        /**
         * Gets a table cell
         *
         * @param row the row index
         * @param column the column index
         * @return the value at row, column
         */
        public Object getValueAt(int row, int column)
        {
            switch (column)
            {
                case 0:
                    return new Integer(row + 1);
                case 1:
                    return new Boolean(m_Selected[row]);
                case 2:
                    return m_Instances.attribute(row).name();

                default:
                    return null;
            }
        }
        
        /**
         * Gets the name for a column.
         *
         * @param column the column index.
         * @return the name of the column.
         */
        @Override
        public String getColumnName(int column)
        {
            
            switch (column)
            {
                case 0:
                    return "No.";
                case 1:
                    return "";
                case 2:
                    return "Nombre";
                default:
                    return null;
            }
        }
        
        /**
         * Sets the value at a cell.
         *
         * @param value the new value.
         * @param row the row index.
         * @param col the column index.
         */
        public void setValueAt(Object value, int row, int col)
        {
            
            if (col == 1)
            {
                m_Selected[row] = ((Boolean) value).booleanValue();
            }
        }
        
        /**
         * Gets the class of elements in a column.
         *
         * @param col the column index.
         * @return the class of elements in the column.
         */
        public Class getColumnClass(int col)
        {
            return getValueAt(0, col).getClass();
        }
        
        /**
         * Returns true if the column is the "selected" column.
         *
         * @param row ignored
         * @param col the column index.
         * @return true if col == 1.
         */
        public boolean isCellEditable(int row, int col)
        {
            
            if (col == 1)
            {
                if (this.habilitarCheckBox==true)
                    return true;
                else
                    return false;
            }
            return false;
        }
        
   
        public int [] getSelectedAttributes()
        {
            
            int [] r1 = new int[getRowCount()];
            int selCount = 0;
            for (int i = 0; i < getRowCount(); i++)
            {
                if (m_Selected[i])
                {
                    r1[selCount++] = i;
                }
            }
            int [] result = new int[selCount];
            System.arraycopy(r1, 0, result, 0, selCount);
            return result;
        }
        
        public void includeAll()
        {
            
            for (int i = 0; i < m_Selected.length; i++)
            {
                m_Selected[i] = true;
            }
            fireTableRowsUpdated(0, m_Selected.length);
        }
        
        /**
         * Deselects all attributes.
         */
        public void removeAll()
        {
            
            for (int i = 0; i < m_Selected.length; i++)
            {
                m_Selected[i] = false;
            }
            fireTableRowsUpdated(0, m_Selected.length);
        }
        
        /**
         * Inverts the selected status of each attribute.
         */
        public void invert()
        {
            
            for (int i = 0; i < m_Selected.length; i++)
            {
                m_Selected[i] = !m_Selected[i];
            }
            fireTableRowsUpdated(0, m_Selected.length);
        }

        
        public void include(int [] idx)
        {
            
            for (int i = 0; i < idx.length; i++)
            {
                m_Selected[idx[i]] =true;
            }
            fireTableRowsUpdated(0, m_Selected.length);
        }
            
    }
    
    /** Press to select all attributes */
    protected JButton m_IncludeAll = new JButton("Todo");
    
    /** Press to deselect all attributes */
    protected JButton m_RemoveAll = new JButton("Nada");
    
    /** Press to invert the current selection */
    protected JButton m_Invert = new JButton("Invertir");
    
    protected JButton m_Select_Deselect = new JButton("Seleccionar / Deseleccionar");
    
    
    /** The table displaying attribute names and selection status */
    protected JTable m_Table = new JTable();
    
    /** The table model containingn attribute names and selection status */
    protected AttributeTableModel m_Model;
    
    protected Instances m_Instances;
    
    protected boolean hcb;
    
    protected boolean selected=false;
    /**
     * Creates the attribute selection panel with no initial instances.
     */
    public AttributeSelectionPanel(boolean hcb)
    {
        this.hcb=hcb;
        m_IncludeAll.setToolTipText("Seleccionar todo");
        m_IncludeAll.setEnabled(false);
        m_IncludeAll.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {                
                m_Model.includeAll();
            }
        });
        
        m_Select_Deselect.setToolTipText("Seleccionar/Deseleccionar variables.");
        m_Select_Deselect.setEnabled(false);
        m_Select_Deselect.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                if (selected)
                {
                    m_Model.removeAll();                    
                }
                else{
                    m_Model.includeAll();                    
                }
                selected=!selected;
            }
        });
                
        m_RemoveAll.setToolTipText("Deseleccionar todo");
        m_RemoveAll.setEnabled(false);
        m_RemoveAll.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                m_Model.removeAll();
            }
        });
        m_Invert.setToolTipText("Invertir la selecci�n actual");
        m_Invert.setEnabled(false);
        m_Invert.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                m_Model.invert();
            }
        });
        m_Table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        m_Table.setColumnSelectionAllowed(false);
        m_Table.setPreferredScrollableViewportSize(new Dimension(250,180));
        
        // Set up the layout
        JPanel p1 = new JPanel();
        p1.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));
        p1.setLayout(new GridLayout(1, 3, 5, 5));
        //p1.add(m_IncludeAll);
        //p1.add(m_RemoveAll);
        //p1.add(m_Invert);
        p1.add(m_Select_Deselect);
        
        setLayout(new BorderLayout());
        add(p1, BorderLayout.NORTH);
        add(new JScrollPane(m_Table), BorderLayout.CENTER);
    }
    
   
    /**
     * Sets the instances who's attribute names will be displayed.
     *
     * @param newInstances the new set of instances
     */
    public void setInstances(Instances newInstances)
    {
      if (m_Model == null) 
      {
        m_Model = new AttributeTableModel(newInstances);
        m_Model.setHabilitarCheckBox(hcb);
        m_Table.setModel(m_Model);
        TableColumnModel tcm = m_Table.getColumnModel();
        tcm.getColumn(0).setMaxWidth(60);
        tcm.getColumn(1).setMaxWidth(tcm.getColumn(1).getMinWidth());
        tcm.getColumn(2).setMinWidth(100);
    }
    else 
    {
        m_Model.setInstances(newInstances);
        m_Table.clearSelection();
    }
    
    m_Select_Deselect.setEnabled(false);
    m_IncludeAll.setEnabled(false);
    m_RemoveAll.setEnabled(false);
    m_Invert.setEnabled(false);
    if (newInstances.numAttributes()>0)
    {
        m_Select_Deselect.setEnabled(true);
        m_IncludeAll.setEnabled(true);
        m_RemoveAll.setEnabled(true);
        m_Invert.setEnabled(true);
    }
    m_Table.sizeColumnsToFit(2);
    m_Table.revalidate();
    m_Table.repaint();
    }
    
    /**
     * Gets an array containing the indices of all selected attributes.
     *
     * @return the array of selected indices.
     */
    public int [] getSelectedAttributes()
    {
        
        return m_Model.getSelectedAttributes();
    }
    
     public void getSelectedAttributes(int [] idx)
    { 
         m_Model.include(idx);
    }
    
    /**
     * Gets the selection model used by the table.
     *
     * @return a value of type 'ListSelectionModel'
     */
    public ListSelectionModel getSelectionModel()
    {
        
        return m_Table.getSelectionModel();
    }
    
   public void updateTable()
   {
       m_Table.revalidate();
       m_Table.repaint();
   }
   
   public void setSelected(boolean f){
    this.selected=f;
   }

 public void initTable()
    {
        m_Table.setModel(new DefaultTableModel());
        m_Model = null;
        m_Instances=null;
    }
} 
