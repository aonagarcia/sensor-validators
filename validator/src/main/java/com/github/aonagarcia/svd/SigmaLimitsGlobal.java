/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.aonagarcia.svd;

import com.github.aonagarcia.extras.Instances;
import com.github.aonagarcia.extras.Utils;

/**
 *
 * @author javier
 */
public class SigmaLimitsGlobal implements OutliersDetection {

    Instances m_data;
    Instances m_validationModel;
    DetectionNetwork dn;


    public void setData(Instances _ins) {
        m_data = _ins;
    }

    public void setValidationModel(Instances _ins) {
        m_validationModel = _ins;
    }

    public void setDetectionNetwork(DetectionNetwork _dn)
    {
        dn=_dn;
    }

    public void findOutOfRangeData(int row, String var) {
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

            if ((currentData < limInf) || (currentData > limSup))// Si el dato esta fuera del rango (si limInf o limSup son NaN la condici�n es falsa)
            {
                int attIdx = m_data.attribute(var).index();
                m_data.instance(row).setErrorType(attIdx, 2); //establece el tipo de error que contiene el dato               
                m_data.instance(row).setProbableValue(attIdx, Utils.roundDouble(dn.getProbableValue(var, m_data, row, false, 0.3), 8));
            }
        }
    }
}
