/*
 * InstancesTableModel.java
 *
 * Created on 4 de enero de 2009, 03:42 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.github.aonagarcia.igu;

//import com.sun.org.apache.bcel.internal.verifier.statics.DOUBLE_Upper;
import com.github.aonagarcia.extras.Instances;

import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Javier
 */
 class InstancesTableModel extends AbstractTableModel {
        
        /**Datos que maneja el modelo de datos*/
        protected Instances  m_data;
        
        /**Arreglo para saber las instancias selecciondas*/
        protected boolean [] m_Selected; 
        
        /**arreglo para saber si debe ser mostrada o no una columna*/
        public boolean[] columnsVisible;
        
        /**Constructor de la clase*/
        public InstancesTableModel(Instances data) {            
            setData(data);
        }
        
        /**Establece los datos que manejar� el modelo de datos*/
        public void setData(Instances data) {           
            m_data = data;
            m_Selected = new boolean [m_data.numInstances()];
            columnsVisible= new boolean [m_data.numAttributes()];
            for (int i=0; i<columnsVisible.length; i++)
                columnsVisible[i]=true;
        }
        
        /**Obtiene el �ndice correcto de la columna col cuando hay variables ocultas*/        
        protected int getNumber(int col) {
            int n = col;
            /*2011 int i = 0;
            do {
                if (!(columnsVisible[i])) n++;
                i++;
            } while (i < n);
            while (!(columnsVisible[n])) n++;
             * */           
            return n;
        }
      
        /**Regresa el numero de instancias*/
        public int getRowCount() {
            
            return m_data.numInstances();
        }


        /**Regresa el n�mero de columnas de acuerdo a las que deben estar visibles
         * como lo indica el arreglo columnsVisible
         */
        public int getColumnCount() {
            int n = 0;
            for (int i = 0; i < m_data.numAttributes(); i++)
                if (columnsVisible[i]) n++;
            return n+2;                                    
        }
        
        /**Establece la selecci�n de la instancia row*/
    @Override
        public void setValueAt(Object value, int row, int col) {
            
            if (col == 0) {
                m_Selected[row] = ((Boolean) value).booleanValue();
            }
        }
        
        /**Regresa el objeto (cadena o numero) que se mostrar� en la celda definida por row y column*/
        public Object getValueAt(int row, int column) {
            
            if (column == 0)
                return new Boolean(m_Selected[row]);
            if (column == 1)
                return new Integer(row+1);
            
            if (m_data.instance(row).isMissing(getNumber(column-2)))
            {
                double vProb=m_data.instance(row).getProbableValue(getNumber(column-2)); //obtiene el "valor m�s probable" asignado al dato (row,col)
                if (!Double.isNaN(vProb))// si el dato esta definido (es distinto de NaN)
                {
                    return  (String)"("+vProb+")";
                    
                }else
                    return null;
            }
                
            
            if (m_data.attribute(getNumber(column-2)).isNominal())
               
                return (String)m_data.instance(row).stringValue(getNumber(column-2));
            
            if (m_data.attribute(getNumber(column-2)).isNumeric())
            {
                double vProb=m_data.instance(row).getProbableValue(getNumber(column-2)); //obtiene el "valor m�s probable" asignado al dato (row,col)
                if (!Double.isNaN(vProb))// si el dato esta definido (es distinto de NaN)
                {
                    double v=new Double(m_data.instance(row).value(getNumber(column-2)));
                    return  (String)Double.toString(v)+ " ("+vProb+")";
                    
                }else
                {
                    double v=new Double(m_data.instance(row).value(getNumber(column-2)));
                    return    (String)Double.toString(v);
                }
            }
            return null;
            
        }
        
        /**Regresa el nombre de la columan cuyo �ndice es column*/
    @Override
        public String getColumnName(int column) {
            
            if (column==0)
                return null;
            if (column==1)
                return "No.";
            else
                //return columnsName[getNumber(col)];
                return m_data.attribute(getNumber(column-2)).name();
        }
        
        /**Regresa la clase a la que pertenece el tipo de dato de la columna col */
    @Override
        public Class getColumnClass(int col) {
            Class       result;
            result = null;
            
            if (col == 0)
                return Boolean.class;
            else
            
            return String.class;
        }
        /*
        public Class getColumnClass(int col) {
            Class       result;
            result = null;
            
            if (col == 0)
                result= Boolean.class;
            else
                if (col == 1)
                    result = Integer.class;
                else
                    if (m_data.instance(0).attribute(getNumber(col-2)).type() == Attribute.NUMERIC)
                        result = Double.class;
                    else
                        result = String.class;
            
            return result;
        }
         **/
        
        /**Determina que la columna 0 es editable para permitir activar/desactivar los checkBox*/
        public boolean isCellEditable(int row, int col) {
            
            if (col==0)
                return true;
            else
                return false;
        }
 
        /**Regresa un arreglo con los �ndices de las instancias seleccionadas*/
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
 }
    