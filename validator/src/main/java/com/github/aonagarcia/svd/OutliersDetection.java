/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.aonagarcia.svd;

import com.github.aonagarcia.extras.Instances;

/**
 *
 * @author javier
 */
public interface OutliersDetection {

    public void setData(Instances ins);

    public void setValidationModel(Instances _ins);

    public void setDetectionNetwork(DetectionNetwork _dn);

    public void findOutOfRangeData(int row, String var);

}
