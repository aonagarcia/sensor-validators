/*
 * showInstances.java
 *
 * Created on 21 de septiembre de 2008, 04:47 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.github.aonagarcia.igu;

import com.github.aonagarcia.extras.Instances;

import java.awt.*;
import javax.swing.table.*;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


/**
 *
 * @author Javier
 */

public class showInstances extends JPanel{
    
    /*Tabla para mostrar los datos*/
    protected JTable m_Table= new JTable();
    
    /*Modelo de datos*/
    protected InstancesTableModel m_Model;    
    
    /*Objeto render para manejar la visualizaci�n de errores con el c�digo de colores*/
    public RenderTablaValidate render;
    
    /*datos*/
    protected Instances m_data;
    
    
    /** Creates a new instance of showInstances */
    public showInstances() {
        
        /*m_Table = new JTable( ) {
            protected JTableHeader createDefaultTableHeader() {
                return new GroupableTableHeader(columnModel);
            }
        };*/
        
        
        //21/02/2011 m_Table.setTableHeader(new GroupableTableHeader(m_Table.getColumnModel()));
        
          /*Enumeration enumeration = m_Table.getColumnModel().getColumns();
           while (enumeration.hasMoreElements()) {
                TableColumn aColumn = (TableColumn)enumeration.nextElement();
                aColumn.setHeaderRenderer(new CheckBoxHeader(myItemListener));
            }
           **/
        render = new RenderTablaValidate();        
        m_Table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        m_Table.setAutoResizeMode(m_Table.AUTO_RESIZE_OFF);
        m_Table.getTableHeader().setReorderingAllowed(false);
        
        //m_Table.setDefaultRenderer(Boolean.class, render);
        m_Table.setDefaultRenderer(Integer.class, render);
        m_Table.setDefaultRenderer(String.class, render);
        m_Table.setDefaultRenderer(Double.class, render);
        
        setLayout(new BorderLayout());
        add(new JScrollPane(m_Table), BorderLayout.CENTER);
    }
    public void initTable()
    {
        m_Table.setModel(new DefaultTableModel());
        m_Model = null;
        m_data=null;
    }

    public void setInstances(Instances data) {
        m_data=data;
        try
        {
        if (m_Model == null) {
            m_Model = new InstancesTableModel(data);            
            render.setInstances(data);
            render.setTableModel(m_Model);
            m_Table.setModel(m_Model);
            //21/02/2011 fillVectorIdxs();
            //21/02/2011 makeHeaders();
        } else {   
            m_Model.setData(data);
            //m_Model.fireTableStructureChanged();
            m_Table.clearSelection();
            m_Table.setModel(m_Model);
            render.setInstances(data);
            render.setTableModel(m_Model);
            m_Model.fireTableStructureChanged();
            //21/02/2011 fillVectorIdxs();
            //21/02/2011 makeHeaders();
            
        }
        }
        catch (Exception e)
        {
        
        }
        
        m_Table.addMouseListener(new MouseAdapter() {
            private void ShowPopup(MouseEvent e) {
                if (e.isPopupTrigger() && m_Table.isEnabled()) {
                    Point p = new Point(e.getX(), e.getY());
                    int col = m_Table.columnAtPoint(p);
                    int row = m_Table.rowAtPoint(p);
                    int mcol = m_Table.getColumn(m_Table.getColumnName(col)).getModelIndex();
                    int rcol=m_Model.getNumber(mcol-2);
                    /**Crea el men� contextual s�lo para variables maracadas con error*/
                    if (m_data.instance(row).getErrorType(rcol)!=0) {
                        JPopupMenu contextMenu = createContextMenu(row,rcol);
                        if (contextMenu != null && contextMenu.getComponentCount() > 0) {
                            contextMenu.show(m_Table, p.x, p.y);
                        }
                    }
                }
            }
            
            public void mousePressed(MouseEvent e) {
                ShowPopup(e);
            }
            
            public void mouseReleased(MouseEvent e) {
                ShowPopup(e);
            }
        });
        
        m_Table.revalidate();
        m_Table.repaint();
    }
    
    /**Almacena en el vector idxGroup los �ndices de m_data de las variables
     *cuyo nombre est� en el arreglo vars
     */
/*    public void fillIdxVectors(String[] vars,Vector idxGroup) {
        idxGroup.clear();
        for (int j=0; j<vars.length; j++) {
            for (int i=0; i<m_data.numAttributes(); i++) {
                if (vars[j].equalsIgnoreCase(m_data.attribute(i).name())) {
                    idxGroup.add(i);
                    break;
                }
            }
        }
    }
  */

    /**Regresa el �ndice de la columna cname de acuerdo al �ndice en el objeto JTable*/
    /*2011 public int getColumnIndex(String cname) {
        TableColumnModel cm = m_Table.getColumnModel();
        Enumeration e=cm.getColumns();
        int i=0;
        while (e.hasMoreElements()) {
            TableColumn aColumn = (TableColumn)e.nextElement();
            if (aColumn.getHeaderValue()!=null)
                if (cname.equalsIgnoreCase(aColumn.getHeaderValue().toString()))
                    return i;
            i++;
        }
        return -1;
    }*/
            
     /**Regresa un objeto ColumnGroup con las columnas definidas en vars agrupadas 
     *bajo el nombre groupName
     */
    /*2011 public ColumnGroup setHeaders(String groupName, String[] vars) {
        TableColumnModel cm = m_Table.getColumnModel();
        ColumnGroup group = new ColumnGroup(groupName);        
        for (int j=0; j<vars.length; j++) {
            for (int i=0; i<m_data.numAttributes(); i++) {
                if (vars[j].equalsIgnoreCase(m_data.attribute(i).name())) {
                    int k=getColumnIndex(m_data.attribute(i).name());
                    if (k>=0) {                        
                        group.add(cm.getColumn(k));
                    }
                    break;
                }
            }
        }
        return group;
    }*/
        
    /**Oculta en la tabla las variables cuyos indices estan en el vector idx*/
 /*2011   public void hideColumn(Vector idx) {
        for (int i=0; i<idx.size(); i++) {
            int k=Integer.valueOf(idx.elementAt(i).toString());
            m_Model.columnsVisible[k]=false;
        }
        m_Model.fireTableStructureChanged();
        //makeHeaders();
        
        m_Table.revalidate();
        m_Table.repaint();
    }
  *
  */
    
    /**Muestra en la tabla las variables cuyos �ndices estan en el vector idx*/
   /*2011  public void showColumn(Vector idx) {
        for (int i=0; i<idx.size(); i++) {
            int k=Integer.valueOf(idx.elementAt(i).toString());
            m_Model.columnsVisible[k]=true;
        }
        
        m_Model.fireTableStructureChanged();
        //makeHeaders();
        m_Table.revalidate();
        m_Table.repaint();
        
    }
    * 
    */
    
    
    /**Crea el men� contextual que se meustra al prsionar el bot�n secundario del mouse 
     sobre alguna celda de la tabla marcada con algun tipo de error */
    private JPopupMenu createContextMenu(final int rowIndex,final int columnIndex) {       
        
        boolean b=m_data.instance(rowIndex).changeValue(columnIndex);
        JPopupMenu contextMenu = new JPopupMenu();
        
        JMenuItem MenuItem = new JMenuItem();
        JMenuItem MenuItem2 = new JMenuItem();
        if (!b)
        {
            MenuItem.setText("Marcar para corregir");
            MenuItem2.setText("No corregir");
        }
        else
        {
            
            MenuItem.setText("Desmarcar para corregir");
        }
        
        MenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                m_data.instance(rowIndex).setChangeValue(columnIndex,!m_data.instance(rowIndex).changeValue(columnIndex));
                m_Table.revalidate();
                m_Table.repaint();
            }
        });
        
        MenuItem2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                m_data.instance(rowIndex).setChangeValue(columnIndex,!m_data.instance(rowIndex).changeValue(columnIndex)); 
                m_data.instance(rowIndex).setProbableValue(columnIndex,Double.NaN);
                m_data.instance(rowIndex).setErrorType(columnIndex,0);
                m_Table.revalidate();
                m_Table.repaint();
            }
        });
        
        contextMenu.add(MenuItem);
        contextMenu.add(MenuItem2);
        return contextMenu;
    }
    
    /**Regresa los �ndices de las instancias seleccionadas en la tabla
     *(el nombre del metodo esta mal deberia ser getSelectedInstances)
     */
    public int [] getSelectedAttributes() {
        return m_Model.getSelectedAttributes();
    }
}
