/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.aonagarcia.gaussianmixturemodel;

import java.util.ArrayList;
import com.github.aonagarcia.extras.*;

/**
 *
 * @author Pablo HL
 *
 * Esta clase obtiene el modelo de Mezcla de Gausianas en 1 dimension
 * para lo cual aplica el algoritmo EM
 *
 */
public class MixtureGaussians {

    private int m_attIndex;
    private double means[];         //Medias
    private double sigmas[];        //Varianzas
    private double phis[];          //Coeficiente de Mezclas
    private int numInstances = 1;   //valores por default
    private int numGaussian = 2;
    private Instances data;         //datos
    private boolean verbose;        //Mostrar informacion en consola
    private double minStdDev=0.000001;
    private double[] minmaxOrg;

    public MixtureGaussians(int numG) {
        numGaussian = numG;
        verbose = true;
    }


    public double[] algorithmEM(Instances d, int att, double min, double max) {
        //Inicializar datos y arreglos
        data = d;
        //data=new Instances(d);
        //minmaxOrg=data.normalizaAtt(att,1,0);
        double error = 0;
        double errorAnt = Double.MIN_VALUE;
        double epsilon = 0.0000001;
        means = new double[numGaussian];
        sigmas = new double[numGaussian];
        phis = new double[numGaussian];
        m_attIndex = att;

        numInstances = data.numInstances();

        for (int i = 0; i < numInstances; i++) {
            data.instance(i).setNWeights(numGaussian);
        }

        getIniciales();

        error = getError();
        double dif = Double.MAX_VALUE;

        //Iterar algoritmo hasta que converja (de acuerdo a epsilon)
        while (dif > epsilon) {
            eStep();
            mStep();
            errorAnt = error;
            error = getError();
            dif = error - errorAnt;
        }


        if (verbose == true) {
            for (int i = 0; i < numGaussian; i++) {
                System.out.println(i + " " + means[i] + " " + sigmas[i] + " " + phis[i]);
            }
        }

        //Regresa un arreglo de intervalos
        return getCutPoints(min, max);        
    }

    public double[] getCutPoints(double min, double max) {
        double[] cutPoints = new double[numGaussian + 1];
        for (int i = 0; i < numGaussian - 1; i++) {
            cutPoints[i] = getGaussianIntersectionPoint(means[i], means[i + 1], sigmas[i], sigmas[i + 1]);
        }
        cutPoints[numGaussian - 1] = max;
        cutPoints[numGaussian] = min;

        //"Desnormaliza" los valores en los cutPoints
       /* for (int i=0; i<cutPoints.length-2;i++)
            cutPoints[i]=((cutPoints[i]-0.0D)/1.0D)*(minmaxOrg[1]-minmaxOrg[0])+minmaxOrg[0];
        *
        */
        return cutPoints;
    }

    //Obtener intervalos de acuerdo a las gausianas encontradas
    public Interval[] getIntervalos() {
        ArrayList<Interval> intervalIns = new ArrayList<Interval>();

        Interval[] intervalos = new Interval[0];
        int finultimo = 0;
        for (int i = 0; i < numGaussian; i++) {

            //Los intervalos se definen mediante la media y la desviacion estandar
            double start = means[i] - Math.sqrt(sigmas[i]);
            double end = means[i] + Math.sqrt(sigmas[i]);


            int ini = Math.round((float) start);
            int fin = Math.round((float) end);

            //Si el inicio es mas grande que el fin algo esta raro
            if (ini >= fin) {
                return intervalos;
            }

            //Si no hay suficientes intervalos como los que se piden
            //adios
            if (ini < finultimo) {
                return intervalos;
            }

            finultimo = fin;
            intervalIns.add(new Interval(ini, fin));
        }

        intervalos = new Interval[intervalIns.size()];
        for (int i = 0; i < intervalIns.size(); i++) {
            intervalos[i] = intervalIns.get(i);
        }

        return intervalos;
    }

    private void eStep() {
        for (int j = 0; j < numGaussian; j++) {
            calculaProbas(j);
        }

        for (int l = 0; l < numGaussian; l++) {
            for (int i = 0; i < numInstances; i++) {                
                Instance ins = data.instance(i);               
                double val = ins.getWeight(l) * phis[l];

                double suma = 0;
                for (int j = 0; j < numGaussian; j++) {                   
                    double newval = ins.getWeight(j)* phis[j];
                    suma += newval;
                }
                /*if (suma == 0) {
                    suma = Double.MIN_VALUE;
                }*/
                ins.setWeight(l, val / suma);              
            }
        }
    }

    private void calculaProbas(int index) {
        double weights[] = new double[numInstances];

        for (int i = 0; i < numInstances; i++) { 
            Instance ins = data.instance(i);
            if (!ins.isMissing(m_attIndex)) {
                weights[i] = GaussianDistribution.evaluate(ins.value(m_attIndex), means[index], sigmas[index]);
            }
        }

        for (int i = 0; i < numInstances; i++) {
            Instance ins = data.instance(i);
             if (!ins.isMissing(m_attIndex))
                ins.setWeight(index, weights[i]);
        }
    }

    private void mStep() {

        double meanUp = 0, meanDown = 0, sigmaUp = 0, sigmaDown = 0, phisTmp = 0;

        for (int index = 0; index < numGaussian; index++) {
            phisTmp = 0;
            for (int i = 0; i < numInstances; i++) {
                Instance ins = data.instance(i);
                if (ins.isMissing(m_attIndex)) {
                    continue;
                }
                phisTmp += ins.getWeight(index);
            }            
            phis[index] = phisTmp / numInstances;
        }

        for (int index = 0; index < numGaussian; index++) {
            meanUp = 0;
            meanDown = 0;
            for (int i = 0; i < numInstances; i++) {
                Instance ins = data.instance(i);
                if (ins.isMissing(m_attIndex)) {
                    continue;
                }
                meanUp += (ins.getWeight(index) * ins.value(m_attIndex));
                meanDown += ins.getWeight(index);
            }
            means[index] = meanUp / meanDown;
        }

        for (int index = 0; index < numGaussian; index++) {
            sigmaUp = 0;
            sigmaDown = 0;
            for (int i = 0; i < numInstances; i++) {
                Instance ins = data.instance(i);
                if (ins.isMissing(m_attIndex)) {
                    continue;
                }
                double derecha = ins.value(m_attIndex) - means[index];
                sigmaUp += (ins.getWeight(index) * Math.pow(derecha, 2));
                sigmaDown += ins.getWeight(index);
            }
            sigmas[index] = (sigmaUp / sigmaDown);//<minStdDev?minStdDev:sigmaUp / sigmaDown;
        }
    }

    public void getIniciales() {

        double max = Double.NEGATIVE_INFINITY;
        double min = Double.POSITIVE_INFINITY;

        for (int i = 0; i < numInstances; i++) {
            Instance ins = data.instance(i);
            double val = ins.value(m_attIndex);
            if (val > max) {
                max = val;
            }
            if (val < min) {
                min = val;
            }
        }
        double proba = 1.0 / numGaussian;

        double interval = max - min;
        double step = interval / (numGaussian + 1);
        for (int i = 0; i < numGaussian; i++) {
            phis[i] = proba;
            means[i] = ((i + 1) * step) + min;
            sigmas[i] = Math.sqrt(step / 2);
        }
    }

    /**<html>
     * Evalua p(<bold>X</bold>|&#955) = &#8719 p(x_t|&#955)=L(&#955|<bold>X</bold>)
     * Para obtener la m�xima verosimilitud de p(X) dado el modelo &#955={&#960,&#956,&#963}
     * </html>
     */
    public double getError() {
        double mul = 1;
        for (int i = 0; i < numInstances; i++) {
            Instance ins = data.instance(i);
            if (ins.isMissing(m_attIndex)) {
                continue;
            }
            double suma = 0;
            for (int j = 0; j < numGaussian; j++) {
                suma += phis[j] * GaussianDistribution.evaluate(ins.value(m_attIndex), means[j], sigmas[j]);
            }
            mul *= suma;
        }
        return mul;// == 0 ? Double.MIN_VALUE : mul;
    }

    public double getGaussianIntersectionPoint(double m0, double m1, double s0, double s1) {
        double a = 0, b = 0, c = 0;
        double r1=0, r2=0;
        try {
            a = s1 * s1 - s0 * s0;
            b = -2.0D * (m0 * s1 * s1 - m1 * s0 * s0);
            c = m0 * m0 * s1 * s1 - m1 * m1 * s0 * s0 - 2.0D * s0 * s0 * s1 * s1 * Math.log(s1 / s0);
            r1 = ((-b + Math.sqrt(b * b - 4.0D * a * c)) / (2.0D * a));
            r2 = ((-b - Math.sqrt(b * b - 4.0D * a * c)) / (2.0D * a));

           /* if (r2 < r1) {
                double temp = r1;
                r1 = r2;
                r2 = temp;
            }*/

        } catch (Exception ex) {
            System.out.println("GaussianIntersectionPoint Error: " + ex);
        }
        return r1;
    }
}
