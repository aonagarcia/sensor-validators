/*
 * validate.java
 *
 * Created on 19 de octubre de 2008, 11:06 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package com.github.aonagarcia.svd;

import com.github.aonagarcia.extras.Instances;

import java.util.Vector;
import com.github.aonagarcia.extras.Utils;
import java.util.ArrayList;
import java.util.LinkedList;
//import org.apache.commons.lang.ArrayUtils;
/**
 *
 * @author Javier
 */
public class validate {

    protected Vector models;
    protected DetectionNetwork m_dn;
    public Instances m_data;
    public Instances originales;
    protected Instances m_validationModel;

    long[] time;
    int[]  times;
    int variable=0;
    /** Creates a new instance of validate */
    public validate() {
    }


    //Ciclo que realiza la validaci�n
    public void initValidationProcess(LinkedList varList,OutliersDetection od )  {       
        od.setData(m_data);
        od.setValidationModel(m_validationModel);
        od.setDetectionNetwork(m_dn);
        for (int row = 0; row < m_data.numInstances(); row++) {
            for (int var = 0; var < varList.size(); var++) {
                String varName = (String) varList.get(var);
                if (m_data.attribute(varName) != null) {
                    findMissingData(row, varName, getModelDN());
                    //findOutOfRangeData(row, varName, getModelDN());
                    od.findOutOfRangeData(row, varName);
                    findAbruptChanges(row, varName, getModelDN());
                }
            }            
            findRelationalVariablesErrors(row);
            Control._igu.ValidateData.setNumInstances(row + 1);
            Control._igu.ValidateData.updateProgressBar(row);
            Control._igu.ValidateData.repaintTable();
        }
    }

    /***Establece el modelo para validaci�n
     * @param _inst Estructura que contiene los datos para validar
     */
    public void setvalidationModel(Instances _inst, Vector m) {
        m_validationModel = _inst;
        models = m;
    }

    public void setValidationModel(Instances _inst, DetectionNetwork _dn) {
        m_validationModel = _inst;
        m_dn = _dn;
    }

    /**Encuentra los datos faltantes en la base de datos y asigna el tipo de error "1"
     *a los datos faltantes
     */
    public void findMissingData(int row, String var, DetectionNetwork dn) {

        if (m_data.instance(row).isMissing(m_data.attribute(var))) {
            int attIdx = m_data.attribute(var).index();
            m_data.instance(row).setErrorType(attIdx, 1); //Establece el tipo de error que tiene el dato                                       
                m_data.instance(row).setProbableValue(attIdx, Utils.roundDouble(dn.getProbableValue(var, m_data, row, false, 100), 8));               
                variable=m_data.attribute(var).index();
            }
        }
    

    public double[][] buildVectors(int var)
    {
        ArrayList<Double> arrayListE=new ArrayList();
        ArrayList<Double> arrayListR=new ArrayList();
        for(int i=0; i< m_data.numInstances()-1;i++)
        {
            if (m_data.instance(i).getErrorType(var)==1)
            {
                arrayListE.add(m_data.instance(i).getProbableValue(var));               
                arrayListR.add(originales.instance(i).value(var));
            }
        }            
        
        double[][] out=new double[2][arrayListE.size()];
        for(int k=0;k<arrayListE.size();k++)
        {
            out[0][k]=arrayListE.get(k);
            out[1][k]=arrayListR.get(k);
        }
        
        return out;
    }
    
    /**Encuentra los datos que estan fuera de rango de acuerdo al modelo de validaci?n
     *que fue establecido. A cada dato detectado como fuera de rango se le asigna el tipo de
     *error "2".
     */
    public void findOutOfRangeData(int row, String var, DetectionNetwork dn) {

        double limInf = -1;
        double limSup = -1;


        int idx = m_validationModel.attribute(0).indexOfValue(var);
        if (m_data.attribute(var) != null) {

            //Obtiene los limites inferior y superior de la variable
            limInf = m_validationModel.instance(idx).value(m_validationModel.attribute("SPminTol"));
            limSup = m_validationModel.instance(idx).value(m_validationModel.attribute("SPmaxTol"));

            double currentData;
            //se obtiene el valor de actual de la (instancia,variable)
            currentData = m_data.instance(row).value(m_data.attribute(var));

            if ((currentData < limInf) || (currentData > limSup))// Si el dato esta fuera del rango (si limInf o limSup son NaN la condici?n es falsa)
            {
                int attIdx = m_data.attribute(var).index();
                m_data.instance(row).setErrorType(attIdx, 2); //establece el tipo de error que contiene el dato
                m_data.instance(row).setProbableValue(attIdx, Utils.roundDouble(dn.getProbableValue(var, m_data, row, false, 0.3), 8));
            }
        }
    }

    public void findAbruptChanges(int row, String var, DetectionNetwork dn) {
        double d1, d2;
        double th;
        if (row > 0) {
            int idx = m_validationModel.attribute(0).indexOfValue(var);
            int attIdx = m_data.attribute(var).index();

            if ((m_data.instance(row).getErrorType(attIdx) == 0) && (m_data.instance(row - 1).getErrorType(attIdx) == 0))//solo se detecta si no ha sido detectado como error de otro tipo
            {
                th = m_validationModel.instance(idx).value(m_validationModel.attribute("Umbral_Cambios"));
                if (th > 0) //si el valor es negativo (-1) no realiza validaci?n
                {
                    d1 = m_data.instance(row - 1).value(m_data.attribute(var).index());
                    d2 = m_data.instance(row).value(m_data.attribute(var).index());
                    if (Math.abs(d1 - d2) > th) {

                        m_data.instance(row).setErrorType(attIdx, 3); //establece el tipo de error que contiene el dato
                        m_data.instance(row).setProbableValue(attIdx, Utils.roundDouble(dn.getProbableValue(var, m_data, row, false, 0.3), 8));
                    }
                }
            }
        }
    }

    
    public void findRelationalVariablesErrors(int row) {
        m_dn.validar(row, m_data);
    }

    public void setInstances(Instances inst) {
        m_data = inst;
    }

    public DetectionNetwork getModelDN() {
        return this.m_dn;
    }

    public Instances getInstances() {
       return m_data;
    }

    void setInstancesOriginales(Instances originalData) {
        this.originales=originalData;
    }
}
