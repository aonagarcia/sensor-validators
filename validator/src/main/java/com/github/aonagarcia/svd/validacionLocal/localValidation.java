/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.aonagarcia.svd.validacionLocal;

import com.github.aonagarcia.extras.AttributeStats;
import com.github.aonagarcia.extras.Instance;
import com.github.aonagarcia.extras.Instances;

/**
 *Clase que realiza la detecci�n de outliers locales sobre el conjunto de datos de una variable
 * @author Javier
 */
public class localValidation {

    private Instances data;
    private int windowWidthSamples;
    private final int N = 3;
    private double mediana;
    private double desvStdM;
    private int delta;
    private AttributeStats attStats;

    /**
     * Este constructor asigna el conjunto de instancias y establece el tama�o
     * de la ventana
     * @param ins Conjunto de instancias
     * @param windowWidth Tama�o de la ventana (porcentaje de los datos)
     */
    public localValidation(Instances ins, int windowWidth) {
        this.data = ins;
        delta = 1;
        windowWidthSamples = (int) (data.numInstances() * (windowWidth / 100.0));
        attStats = new AttributeStats();
    }

    /**
     * Ejecuta la validaci�n local sobre el atributo espcificado
     * 
     * @param attIdx
     */
    public void performLocalValidation(int attIdx) {
        Instance ins;
        attStats = data.attributeStats(attIdx);
        for (int i = 0; i < data.numInstances() - windowWidthSamples; i = i + delta) {
            mediana = attStats.numericStats.calculaMediana(data, attIdx, i, i + windowWidthSamples);
            desvStdM = attStats.numericStats.calculaStdDevM(mediana, data, attIdx, i, i + windowWidthSamples);
            double highTh = mediana + (N * desvStdM);
            double lowTh = mediana - (N * desvStdM);
            for (int j = i; j < i + windowWidthSamples; j++) {
                ins = data.instance(j);
                if (ins.value(attIdx) >= highTh || ins.value(attIdx) <= lowTh) {
                    ins.setErrorType(attIdx, 5);//El tipo de error 5 ser� "outlier local"
                }
            }
        }
    }

    public void setDelta(int d) {
        delta = d;
    }

    public double getDesvStdM() {
        return desvStdM;
    }

    public double getMediana() {
        return mediana;
    }

    public int getDelta() {
        return delta;
    }

    public double[] performStepByStep(int attIdx, int position) {
        Instance ins;
        double[] lims = new double[2];
        lims[0] = Double.NaN;
        lims[1] = Double.NaN;
        attStats = data.attributeStats(attIdx);
        mediana = attStats.numericStats.calculaMediana(data, attIdx, position, position + windowWidthSamples);
        desvStdM = attStats.numericStats.calculaStdDevM(mediana, data, attIdx, position, position + windowWidthSamples);
        if (desvStdM > 0) {
            double highTh = mediana + (N * desvStdM);
            double lowTh = mediana - (N * desvStdM);
            lims[0] = highTh;
            lims[1] = lowTh;
            for (int j = position; j < position + windowWidthSamples; j++) {
                ins = data.instance(j);
                //ins = data.instance(position);
                if ((ins.value(attIdx) <= lowTh || ins.value(attIdx) >= highTh)
                        && (ins.getErrorType(attIdx) == 0)) {
                    ins.setErrorType(attIdx, 5);//El tipo de error 5 ser� "outlier local"
                }
            }
        }
        return lims;
    }

    public int getWindowWidthSamples() {
        return windowWidthSamples;
    }
}
