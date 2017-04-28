/*
 * RenderTabla.java
 *
 * Created on 4 de agosto de 2008, 14:03
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.github.aonagarcia.igu;

import java.awt.Component;
import java.awt.Color;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Javier
 */
public class RenderTabla extends DefaultTableCellRenderer {
    private Color           missingColor;
    private Color           missingColorSelected;
    private Color           highlightColor;
    private Color           highlightColorSelected;
    private Color           OutOfRangeColor=new Color(255,0,0);
    private Color           OutOfRangeColorSelected=new Color(200,0,0);
    
    private double          minRange=Double.NaN;
    private double          maxRange=Double.NaN;
    /** Creates a new instance of RenderTabla */
    public RenderTabla() {
        this( new Color(223, 223, 223), new Color(192, 192, 192) );
    }
    
    public RenderTabla( Color missingColor,
            Color missingColorSelected ) {
        this( missingColor,
                missingColorSelected,
                Color.WHITE,
                Color.WHITE.darker() );
    }
    public RenderTabla( Color missingColor,
            Color missingColorSelected,
            Color highlightColor,
            Color highlightColorSelected ) {
        
        super();
        this.missingColor           = missingColor;
        this.missingColorSelected   = missingColorSelected;
        this.highlightColor         = highlightColor;
        this.highlightColorSelected = highlightColorSelected;
    }
    
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component                  result;
        result = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        
        //Si el valor es "faltante""
        if (value==null) {
            if (isSelected)
                result.setBackground(missingColorSelected);
            else
                result.setBackground(missingColor);
        }else {
            //Si el valor est� fuera de los l�mites
            if ((value instanceof Double) && (maxRange!=Double.NaN) && (minRange!=Double.NaN) ){
                    if (((Double)value > maxRange) || ((Double)value < minRange)) {
                        if (isSelected)
                            result.setBackground(OutOfRangeColorSelected);
                        else
                            result.setBackground(OutOfRangeColor);
                    }
                    else
                        if (!isSelected)
                             result.setBackground(highlightColor);
                }    
            else
                if (!isSelected)
                    result.setBackground(highlightColor);
        }
        
        return result;
    }
    /*
    public void setInstances(Instances inst)
    {
        m_data=inst;
    }
     */
    public void setOutOfRangeValues(double MaxR, double MinR) {
        this.maxRange=MaxR;
        this.minRange=MinR;
    }
}
