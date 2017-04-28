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
import java.awt.Font;

import com.github.aonagarcia.extras.Instances;
/**
 *
 * @author Javier
 */
public class RenderTablaValidate extends DefaultTableCellRenderer {
    
    private Color           usingCM = Color.black;//Color.BLUE.darker();
    private Color           usingMODA =Color.black; //Color.GREEN;
    
    private Color           datoMarcado = Color.WHITE;
    
    private Color           missingDataColor=Color.GRAY;
    private Color           missingDataColorSelected=Color.GRAY.darker();
    
    private Color           OutOfRangeDataColor=Color.RED;
    private Color           OutOfRangeDataColorSelected=Color.RED.darker();
    
    private Color           abruptChangeDataColor=Color.YELLOW;
    private Color           abruptChangeDataColorSelected=Color.YELLOW.darker();
    
    private Color           errorDataColor=Color.GREEN;
    private Color           errorDataColorSelected=Color.GREEN.darker();

    private Color           LocalOutlierDataColor=Color.ORANGE;
    private Color           LocalOutlierDataColorSelected=Color.ORANGE.darker();

    
    private Color           highlightColor=Color.WHITE;
    private Color           highlightColorSelected=new Color(184,207,229);
    
    private Font font=new Font( "Helvetica",Font.BOLD,12 );
    
    /**Estructura de datos en la cual se consulta el tipo de error de un dato*/
    private Instances m_data;
    /**Modelo de datos utilizado para tener acceso al metodo getNumber(int col)*/
    private InstancesTableModel m_Model;
    
    
    
    /** Creates a new instance of RenderTabla */
    public RenderTablaValidate() {
        //super();
    }
    
    /**Regresa la celda con el color de fondo como debe ser mostrada dependiendo del tipo de error detectado
     *o si fue marcada para corregir el dato por el valor propuesto el color del texto es establecido a az�l
     */
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component                  result;
        
        result = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        
        if (column>=2) {
            //if (m_data.attribute(m_Model.getNumber(column-2)).isVisible()) {
            int n=m_Model.getNumber(column-2);
            if (m_Model.columnsVisible[n]) {
                
                int tE=m_data.instance(row).getErrorType(n);
                switch(tE) {
                    /**Dato normal*/
                    case 0:{
                        this.setToolTipText(null);
                        if (isSelected) {
                            result.setBackground(highlightColorSelected);
                            result.setForeground(Color.BLACK);
                        } else {
                            result.setBackground(highlightColor);
                            result.setForeground(Color.BLACK);
                        }
                        break;
                    }
                    /**Dato Falatante*/
                    case 1:{
                        this.setToolTipText("Dato Faltante");
                        
                        if (isSelected) {
                            result.setBackground(missingDataColorSelected);
                            setTextColors(result,n,row);
                        } else {
                            result.setBackground(missingDataColor);
                            setTextColors(result,n,row);
                        }
                        break;
                    }
                    /**Dato fuera de Rango*/
                    case 2:{
                        this.setToolTipText("Dato Fuera de Rango");
                        if (isSelected) {
                            result.setBackground(OutOfRangeDataColorSelected);
                            setTextColors(result,n,row);
                        } else {
                            result.setBackground(OutOfRangeDataColor);
                            setTextColors(result,n,row);
                        }
                        break;
                    }
                    /**Cambio Abrupto*/
                    case 3:{
                        this.setToolTipText("Cambio Abrupto");
                        if (isSelected){
                            result.setBackground(abruptChangeDataColorSelected);
                            setTextColors(result,n,row);
                        } else {
                            result.setBackground(abruptChangeDataColor);
                            setTextColors(result,n,row);
                        }
                        
                        break;
                    }
                    
                    /**Dato erroneo de acuerdo a variables relacionadas*/
                    case 4:{
                        this.setToolTipText("Error de variable relacionada");
                        if (isSelected) {
                            result.setBackground(errorDataColorSelected);
                            setTextColors(result,n,row);
                        } else {
                            result.setBackground(errorDataColor);
                            setTextColors(result,n,row);
                        }
                        break;
                    }

                    /**Outlier Local*/
                    case 5:{
                        this.setToolTipText("Dato Fuera de Rango (Local)");
                        if (isSelected) {
                            result.setBackground(LocalOutlierDataColorSelected);
                            setTextColors(result,n,row);
                        } else {
                            result.setBackground(LocalOutlierDataColor);
                            setTextColors(result,n,row);
                        }
                        break;
                    }
                    
                    default:{
                        this.setToolTipText(null);
                        if (isSelected) {
                            result.setBackground(highlightColorSelected);
                        } else {
                            result.setBackground(highlightColor);
                        }
                        break;
                    }
                }
                
            }
        }else {
            this.setToolTipText(null);
            if (isSelected)
                result.setBackground(highlightColorSelected);
            else
                result.setBackground(highlightColor);
        }
        
        return result;
    }
    
    /**Asigna el los datos*/
    public void setInstances(Instances _inst) {
        m_data=_inst;
    }
    
    /**Asigna el modelo de datos*/
    public void setTableModel(InstancesTableModel model) {
        this.m_Model=model;
    }
    
    public void setTextColors(Component result, int n, int row) {
        if (m_data.instance(row).getMetodoUtilizado(n)==1) {
            result.setForeground(usingCM);
            result.setFont(font);            
            if (m_data.instance(row).changeValue(n)==true) {
                    result.setForeground(datoMarcado);
                    result.setFont(font);
                }
        }else
            if (m_data.instance(row).getMetodoUtilizado(n)==2) {
            result.setForeground(usingMODA);
            result.setFont(font);
            if (m_data.instance(row).changeValue(n)==true) {
                    result.setForeground(datoMarcado);
                    result.setFont(font);
            }
            }else                            
               result.setForeground(Color.BLACK);
    }
}

