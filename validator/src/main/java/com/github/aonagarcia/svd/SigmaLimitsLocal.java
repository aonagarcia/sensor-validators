/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.aonagarcia.svd;

import com.github.aonagarcia.extras.Instances;
import com.github.aonagarcia.svd.validacionLocal.localValidation;

/**
 *
 * @author javier
 */
public class SigmaLimitsLocal implements OutliersDetection {

    Instances ins;
    Instances m_validationModel;
    DetectionNetwork dn;
    private localValidation m_localValidation;

    public SigmaLimitsLocal(Instances ins)
    {
        m_localValidation = new localValidation(ins, 5);
        m_localValidation.setDelta(1);
    }
    
    public void setData(Instances _ins) {
        ins = _ins;
    }

    public void setValidationModel(Instances _ins) {
        m_validationModel = _ins;
    }

    public void setDetectionNetwork(DetectionNetwork _dn) {
        dn = _dn;
    }

    public void findOutOfRangeData(int row, String var) {
        //System.out.println("Esta implementaci�n no est� terminada");        
        if (row < ins.numInstances() - m_localValidation.getWindowWidthSamples())
            m_localValidation.performStepByStep(ins.attribute(var).index(), row);
    }
}
