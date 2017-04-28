/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.aonagarcia.svd;

import com.github.aonagarcia.extras.Instances;
import java.util.Vector;
import com.github.aonagarcia.svd.wavelets.*;
import com.github.aonagarcia.extras.Utils;
import java.util.ArrayList;

/**
 *
 * @author javier
 */
public class ChangesDetection {

    private Instances _ins;
    private double[] data;                      //Arreglo de datos a ser procesado
    private int srcDataLen;                     //Longitud de los datos originales
    private int nAtt;                           //N�mero de atributos
    private inplace_haar haarWavelet;           //Objeto que aplica el algortimo Wavelet Haar
    private double[][][] coefs;                 //matriz de coeficientes variable x escalas x datos;
    private double[][][] normCoefs;             //matriz de coeficientes normalizados escalas x datos
    private final int medianFilterWidth = 1;    //Tama�o de la ventana del filtro de la mediana
    private double T;                           //Universal Threshold (Donoho and Johnstone)

    public ChangesDetection(Instances ins) {
        byte log;
        int dstDataLen = 0;
        double[] d;
        int len;

        _ins=ins;
        nAtt = ins.numAttributes();
        srcDataLen = ins.numInstances();

        if (srcDataLen > 0) {
            log = binary.log2(srcDataLen);
            if (!(srcDataLen == Math.pow(2, log))) {
                dstDataLen = binary.power2((byte) (log + 1));
            } else {
                dstDataLen = srcDataLen;
            }

            len = binary.nearestPower2(dstDataLen);
            int windowWidth = 2;
            coefs = new double[nAtt][binary.log2(len)][dstDataLen];
            normCoefs = new double[nAtt][binary.log2(len)][dstDataLen];
            for (int att = 0; att < ins.numAttributes(); att++) {
                d = ins.attributeToDoubleArray(att);
                data = new double[dstDataLen];
                //System.arraycopy(d, 0, data, 0, srcDataLen);
                for (int i = 0; i < srcDataLen; i++) {
                    data[i] = Double.isNaN(d[i]) ? 0 : d[i];
                }
                /*    for (int i = srcDataLen; i < dstDataLen; i++) {
                data[i] = d[srcDataLen - 1];
                }
                 *
                 */
                haarWavelet = new inplace_haar();
                haarWavelet.wavelet_calc(data);
                haarWavelet.order();
                storeCoeff(att, data, len, windowWidth);
                //printCoefs(att);
                normalizaCoefs3(att);
                //printCoefs(att);
            }
        }
    }

    public void printCoefs(int idx) {
        for (int sc = 0; sc < coefs[idx].length; sc++) {
            for (int i = 0; i < coefs[idx][sc].length; i++) {
                System.out.print(coefs[idx][sc][i] + ",");
            }
            System.out.println();
        }
    }

    public Object[] findAbruptChanges(int idx, int scale, double th) {
        return findSudenChanges(idx, scale, th);
    }

    private double mediana(double[] doubles) {
        double mediana = Double.NaN;
        ArrayList d = new ArrayList();
        double[] doubles2;

        for (int i = 0; i < doubles.length; i++) {
            if (!Double.isNaN(doubles[i])) {
                d.add(doubles[i]);
            }
        }
        doubles2 = new double[d.size()];
        for (int i = 0; i < d.size(); i++) {
            doubles2[i] = Double.valueOf(d.get(i).toString()).doubleValue();
        }


        int midPoint = doubles2.length / 2;
        if ((doubles2.length % 2) == 0) {
            mediana = Utils.kthSmallestValue(doubles2, midPoint);// + Utils.kthSmallestValue(doubles2, midPoint + 1)) / 2;
        } else {
            mediana = Utils.kthSmallestValue(doubles2, midPoint + 1);
        }

        return mediana;
    }

    private double mediana2(double[] doubles) {
        double mediana = Double.NaN;
        ArrayList d = new ArrayList();
        double[] doubles2;

        for (int i = 0; i < this.srcDataLen; i++) {
            if (!Double.isNaN(doubles[i])) {
                d.add(doubles[i]);
            }
        }
        doubles2 = new double[d.size()];
        for (int i = 0; i < d.size(); i++) {
            doubles2[i] = Double.valueOf(d.get(i).toString()).doubleValue();
        }
        int midPoint = doubles2.length / 2;
        if ((doubles2.length % 2) == 0) {
            mediana = (Utils.kthSmallestValue(doubles2, midPoint) + Utils.kthSmallestValue(doubles2, midPoint + 1)) / 2;
        } else {
            mediana = Utils.kthSmallestValue(doubles2, midPoint + 1);
        }

        return mediana;
    }

    private void storeCoeff(int idx, double[] values, int end, int windowWidth) {
        if (end > 1) {
            int l = binary.log2(windowWidth);
            int windowStart = 0;
            int windowEnd = windowWidth;
            int start = end >> 1;
            for (int i = start; i < end; i++) {
                for (int j = windowStart; j < windowEnd; j++) {
                    coefs[idx][l - 1][j] = values[i];
                }
                windowStart = windowEnd;
                windowEnd = windowEnd + windowWidth;
            }
            end = start;
            windowWidth = windowWidth << 1;
            storeCoeff(idx, values, end, windowWidth);
        }
    }

    private Object[] findSudenChanges(int idx) {
        double max;
        ArrayList maxCoefs = new ArrayList();
        Object[] sc;
        for (int scale = 0; scale < normCoefs[idx].length; scale++) {
            max = Double.MIN_VALUE;
            for (int i = 0; i < normCoefs[idx][scale].length; i++) {
                if (normCoefs[idx][scale][i] > max) {
                    max = normCoefs[idx][scale][i];
                }
            }
            maxCoefs.add(max);
        }
        return maxCoefs.toArray();
    }

    public int getMaxIdx(Instances ins, int att, int idx)
    {
        double max=Double.MIN_VALUE;
        int idxMax=-1;
        for (int i=idx-1; i<idx+1;i++)
        {
            if (ins.instance(i).value(att)>max)
            {
                max=ins.instance(i).value(att);
                idxMax=i;
            }
        }
        return idxMax;
    }

    public int getMinIdx(Instances ins, int att, int idx)
    {
        double min=Double.MAX_VALUE;
        int idxMin=-1;
        for (int i=idx-1; i<idx+1;i++)
        {
            if (ins.instance(i).value(att)<min)
            {
                min=ins.instance(i).value(att);
                idxMin=i;
            }
        }
        return idxMin;
    }

    private Object[] findSudenChanges(int idx, int scale, double th) {
        Vector sudenPoint = new Vector();
        Vector idxPeaks = new Vector();
        int p0, p1;

        //double _th = this.universalThreshold(idx, scale);
        // System.out.println(th);
        for (int i = 0; i < srcDataLen; i++) {
            if (normCoefs[idx][scale][i] >= th) {
                sudenPoint.add(i);
            }
        }

        /*
        for (int j = sudenPoint.size() - 2; j >= 2; j--) {
        p0 = ((Integer) (sudenPoint.elementAt(j + 1))).intValue();
        p1 = ((Integer) (sudenPoint.elementAt(j))).intValue() + 1;
        if (p0 == p1) {
        sudenPoint.removeElementAt(j + 1);
        }
        }
         *
         */



        for (int i=0; i<sudenPoint.size()-1;i++)
        {

            
            int idx1=((Integer) sudenPoint.elementAt(i)).intValue();
            int idx2=((Integer) sudenPoint.elementAt(i+1)).intValue();
            //El pico es hacia arriba
            idxPeaks.add(getMaxIdx(_ins,idx,idx1));
          /*  idxPeaks.add(getMaxIdx(_ins,idx,idx2));
            idxPeaks.add(getMinIdx(_ins,idx,idx1));
            idxPeaks.add(getMinIdx(_ins,idx,idx2));
           * 
           */

            /*
            if((_ins.instance(idx1).value(idx)-_ins.instance(idx2).value(idx))<0)
            {
                idxPeaks.add(getMaxIdx(_ins,idx,idx2));
               /* if(_ins.instance(idx1).value(idx)>_ins.instance(idx2).value(idx))
                {
                    idxPeaks.add(idx1);
                }
                if(_ins.instance(idx1).value(idx)<_ins.instance(idx2).value(idx))
                {
                    idxPeaks.add(idx2);
                }
                *
                */
            /**}
            else
            if((_ins.instance(idx1).value(idx)-_ins.instance(idx2).value(idx))>0)
            {
                idxPeaks.add(getMinIdx(_ins,idx,idx2));
             /*   if(_ins.instance(idx1).value(idx)<_ins.instance(idx2).value(idx))
                {
                    idxPeaks.add(idx1);
                }
                if(_ins.instance(idx1).value(idx)>_ins.instance(idx2).value(idx))
                {
                    idxPeaks.add(idx2);
                }
              *
              */
            //}
     
        }

/*

        Iterator ii;
        ii = sudenPoint.iterator();
        while (ii.hasNext()) {
            int j = ((Integer) ii.next()).intValue();
            if (j == 0) {
                if (normCoefs[idx][scale][j] > normCoefs[idx][scale][j + 1]) {
                    idxPeaks.add(j);
                }
            } else {
                if (j == normCoefs[idx][scale].length - 1) {
                    if (normCoefs[idx][scale][j] > normCoefs[idx][scale][j - 1]) {
                        idxPeaks.add(j);
                    }
                } else {
                    if ((normCoefs[idx][scale][j] > normCoefs[idx][scale][j + 1])
                            || (normCoefs[idx][scale][j] > normCoefs[idx][scale][j - 1])) {
                        idxPeaks.add(j);
                    }

                }
            }
        }
        return idxPeaks.toArray();
*/
        return idxPeaks.toArray();
        //return sudenPoint.toArray();
    }

    private void printSudenChangesPoints(Object[] p) {
        for (int i = 0; i < p.length; i++) {
            System.out.println(p[i]);
        }
    }

    public int getMaxScale(int var) {
        return normCoefs[var].length == 0 ? 0 : normCoefs[var].length - 1;
    }

    public double getMaxTh(int var, int scale) {
        int t = Utils.max(normCoefs[var][scale]);
        return normCoefs[var][scale][t];
    }

    private void normalizaCoefs3(int idx) {
        double[] window = new double[medianFilterWidth];
        int winWidth = window.length / 2;

        for (int sc = 0; sc < coefs[idx].length; sc++) {
            for (int i = 0; i < coefs[idx][sc].length; i++) {
                coefs[idx][sc][i] = Math.pow(coefs[idx][sc][i], 2);
            }
        }

        for (int sc = 0; sc < coefs[idx].length; sc++) {
            for (int i = 0; i < coefs[idx][sc].length; i++) {
                if ((i - winWidth) < 0) {
                    for (int k = 0; k < winWidth - i; k++) {
                        window[k] = coefs[idx][sc][i + k - winWidth];
                    }
                } else {
                    for (int k = 0; k < window.length; k++) {
                        window[k] = coefs[idx][sc][i + k - winWidth];
                    }
                }
                normCoefs[idx][sc][i] = mediana(window);
            }
        }
    }

    public double[][] getNormCoefs(int varIdx) {
        double[][] ret = new double[normCoefs[varIdx].length][srcDataLen];
        for (int sc = 0; sc < ret.length; sc++) {
            System.arraycopy(normCoefs[varIdx][sc], 0, ret[sc], 0, srcDataLen);
        }
        return ret;
    }

    /**
     * Calcula el universal Threshold de Donoho & Johnstone. Para los datos de Pemex
     * se obtuvieron mejores resultados calculando este threshold con la desviaci�n absoluta
     * con base en la MEDIA. A diferencia del original con base en la MEDIANA.
     * @param varIdx �ndice de la variable de la cual se calculara el umbral
     * @param scale  Escala de coeficientes de los que se calcular� el Th.
     * @return Universal Threshold
     */
    public double universalThreshold(int varIdx, int scale) {
        double th;
        double sigma;
        double m;
        double[] dd = new double[this.srcDataLen];
        for (int i = 0; i < this.srcDataLen/*datos.length*/; i++) {
            dd[i] = normCoefs[varIdx][scale][i];
        }
        m = Utils.mean(dd);
        sigma = Utils.mean(absoluteDesviation(m, dd));
        //m=mediana2(normCoefs[varIdx][scale]);
        //sigma = mediana2(absoluteDesviation(m, normCoefs[varIdx][scale]));

        th = (sigma / 0.6725) * Math.sqrt(2 * Math.log(this.srcDataLen/*normCoefs[varIdx][scale].length*/));
        return th;
    }

    private double[] absoluteDesviation(double mediana, double[] datos) {
        double[] da = new double[this.srcDataLen/*datos.length*/];
        for (int i = 0; i < this.srcDataLen/*datos.length*/; i++) {
            da[i] = Math.abs(datos[i] - mediana);
        }
        return da;
    }
}
