/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.github.aonagarcia.igu;

import com.github.aonagarcia.extras.AttributeStats;
import com.github.aonagarcia.extras.Instances;
import java.util.ArrayList;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

/**
 *
 * @author javier
 */
public class segmentedInstancesTableModel implements TableModel {

    Instances ins;
    TableModelListener _listener;
    ArrayList<AttributeStats> attStatsArray=new ArrayList<AttributeStats>();

    public segmentedInstancesTableModel(Instances _ins)
    {
        ins=_ins;
        //Calcula los valores estad�sticos de los segmentos
        for(int att=0; att<ins.numAttributes();att++)
        {
             AttributeStats attStats= new AttributeStats();
             attStats=ins.attributeStats(att);
             attStatsArray.add(attStats);
        }
    }

    public int getRowCount() {
        return ins.numAttributes();
    }

    public int getColumnCount() {
        return 9;
    }


    public Class getColumnClass(int columnIndex) {
        switch(columnIndex)
        {
            case 0: return Integer.class;
            case 1: return String.class;
            case 2: return Integer.class;
            case 3: return Double.class;
            case 4: return Double.class;
            case 5: return Double.class;
            case 6: return Double.class;
            case 7: return Double.class;
            case 8: return Double.class;
            default: return Object.class;
        }
    }

    public String getColumnName(int columnIndex) {
        switch(columnIndex)
        {
            case 0: return "No.";
            case 1: return "Descripci�n";
            case 2: return "Total datos";
            case 3: return "M�nimo";
            case 4: return "M�ximo";
            case 5: return "Media";
            case 6: return "DS";
            case 7: return "Mediana";
            case 8: return "DS(m)";
            default: return null;
        }
    }

    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        //attStats = ins.attributeStats(rowIndex);
        //double mediana=attStats.numericStats.calculaMediana(ins, rowIndex);
        switch(columnIndex)
        {
            case 0:return new Integer(rowIndex+1);
            case 1:return ins.attribute(rowIndex).name();
            case 2:return ins.numInstances();
            case 3:return attStatsArray.get(rowIndex).numericStats.min;
            case 4:return attStatsArray.get(rowIndex).numericStats.max;
            case 5:return attStatsArray.get(rowIndex).numericStats.mean;
            case 6:return attStatsArray.get(rowIndex).numericStats.stdDev;
            case 7:return null;//mediana;
            case 8:return null;//attStats.numericStats.calculaStdDevM(mediana, ins, rowIndex);
            default: return null;
        }
    }

    public void addTableModelListener(TableModelListener l) {
        _listener=l;

    }

    public void removeTableModelListener(TableModelListener l) {
        _listener=null;

    }

    public void apdateTable(TableModelEvent e)
    {
        _listener.tableChanged(null);
    }

    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        ;
    }



}
