/*
 * datosVariablePanel.java
 *
 * Created on 23 de julio de 2008, 17:17
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package com.github.aonagarcia.igu;

import com.github.aonagarcia.RegsMttos.TimeLinesManager;
import com.github.aonagarcia.extras.Instances;
import javax.swing.JPanel;
import javax.swing.JTable;
import java.awt.Dimension;
import java.awt.BorderLayout;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.AbstractTableModel;
import javax.swing.BorderFactory;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Javier
 */
public class datosVariablePanel extends JPanel {

    public static TimeLinesManager m_mttos = null;
    public Instances ins;

    class InstancesTableModel extends AbstractTableModel {

        protected Object[] m_data;

        public InstancesTableModel(Object[] data) {
            setData(data);

        }

        public void setData(Object[] data) {
            m_data = data;
        }

        public int getRowCount() {
            return m_data.length;
        }

        public int getColumnCount() {
            return 3;
        }

        public Object getValueAt(int row, int column) {
            switch (column) {
                case 0:
                    return new Integer(row + 1);
                case 1:
                    if (m_data[row] == null) {
                        return null;
                    } else if (m_data[row] instanceof String) {
                        return (String) m_data[row];
                    } else if (m_data[row] instanceof Double) {
                        double valor = ((Double) m_data[row]).doubleValue();
                        return valor;
                    }
                case 2:
                    if (ins != null) {
                        return ins.instance(row).isM_HasMtto();
                    } else {
                        return false;
                    }
                /*
                if (m_mttos != null) {
                return m_mttos.existEvent(row + 1);
                } else {
                return false;
                }*/
                default:
                    return null;
            }
        }

        public String getColumnName(int column) {
            switch (column) {
                case 0:
                    return new String("No.");
                case 1:
                    return new String("Valor");
                case 2:
                    return new String("Mtto.");
                default:
                    return null;
            }
        }

        public void setValueAt(Object valor, int row, int col) {
            //m_data[row] = valor;
            if (col == 2) {
                ins.instance(row).setM_HasMtto(((Boolean) valor).booleanValue());
            }
            // Indica que se ha cambiado
            fireTableDataChanged();
        }

        public Class getColumnClass(int col) {
            // return getValueAt(0, col).getClass();

            Class result;
            result = null;

            switch (col) {
                case 0:
                    result = Integer.class;
                    break;
                case 1:
                    if (m_data[0] instanceof Double) {
                        result = Double.class;
                    } else {
                        result = String.class;
                    }
                    break;
                case 2:
                    result = Boolean.class;
                    break;
                default:
                    break;
            }
            /*if (col == 0)
            result = Integer.class;
            else
            if (m_data[0] instanceof Double)
            result = Double.class;
            else
            result = String.class;
             *
             */
            return result;
        }

        public boolean isCellEditable(int row, int col) {
            if (col == 2) {
                return true;
            } else {
                return false;
            }
        }

        public void update() {
            fireTableRowsUpdated(0, m_data.length);
        }
    }
    protected JTable m_Table = new JTable();
    protected InstancesTableModel m_Model;
    protected JTextArea m_MttosInfo;
    RenderTabla render = null;

    public datosVariablePanel() {
        render = new RenderTabla();
        m_MttosInfo = new JTextArea(5, 20);
        m_MttosInfo.setEditable(false);

        setBorder(BorderFactory.createTitledBorder(null, "Valores de la variable", TitledBorder.CENTER, TitledBorder.DEFAULT_POSITION));
        m_Table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        m_Table.setPreferredScrollableViewportSize(new Dimension(200, 100));

        m_Table.setDefaultRenderer(String.class, render);
        m_Table.setDefaultRenderer(Double.class, render);

        setLayout(new BorderLayout());
        add(new JScrollPane(m_Table), BorderLayout.CENTER);
        JPanel mmtosInfo = new JPanel(new BorderLayout());
        mmtosInfo.setBorder(BorderFactory.createTitledBorder(null, "Informaci�n de mantenimientos", TitledBorder.CENTER, TitledBorder.DEFAULT_POSITION));
        mmtosInfo.add(new JScrollPane(m_MttosInfo), BorderLayout.CENTER);
        add(mmtosInfo, BorderLayout.SOUTH);

        //setSelectionRowEvent();
    }

    public void setAttributeData(Object[] data) {
        if (m_Model == null) {
            m_Model = new InstancesTableModel(data);
            m_Table.setModel(m_Model);

            TableColumnModel tcm = m_Table.getColumnModel();
            tcm.getColumn(0).setMaxWidth(60);
            tcm.getColumn(1).setMinWidth(100);
            tcm.getColumn(2).setMaxWidth(30);
        } else {
            m_Model.setData(data);
            m_Table.clearSelection();
        }

        m_Table.sizeColumnsToFit(1);
        m_Table.revalidate();
        m_Table.repaint();
    }

    public ListSelectionModel getSelectionModel() {

        return m_Table.getSelectionModel();
    }

    public RenderTabla getRender() {
        return render;
    }

    public void update() {
        m_Table.revalidate();
        m_Table.repaint();
        m_Model.update();
    }

    public void repaintTable() {
        m_Table.repaint();
    }

    public void setMttosTimeLines(TimeLinesManager mttosTimeLines) {
        m_mttos = mttosTimeLines;
    }

    public void setInstances(Instances _ins) {
        ins = _ins;
    }

    public void initTable() {
        m_Table.setModel(new DefaultTableModel());
        m_Model = null;
        ins = null;
    }
    /*
    private void setSelectionRowEvent() {
    m_Table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
    public void valueChanged(ListSelectionEvent e) {
    if (e.getValueIsAdjusting()) {
    System.out.println("seleccion� un registro");
    ListSelectionModel lm = (ListSelectionModel) e.getSource();
    int j = 0;
    for (int i = e.getFirstIndex(); i <= e.getLastIndex(); i++) {
    if (lm.isSelectedIndex(i)) {
    showMttosInfo(i + 1);
    System.out.println("row selected: "+i);
    break;
    }
    }
    }

    }
    });
    }
     */

    /**NOTA: Si se eliminan instancias esto podr�a traer informaci�n incorrecta
    debido al desplazamiento de los �ndices (ARREGLARLO)*/
    public void showMttosInfo(int i) {
        String mttoStringInfo;
        this.m_MttosInfo.setCaretPosition(0);
        this.m_MttosInfo.setText("");
        this.m_MttosInfo.append("");
        if (m_mttos != null) {
            this.m_MttosInfo.append(m_mttos.getMttosInfo(i));
        }
    }
}
