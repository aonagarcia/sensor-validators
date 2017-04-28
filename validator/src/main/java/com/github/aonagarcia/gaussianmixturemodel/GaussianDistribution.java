/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.github.aonagarcia.gaussianmixturemodel;

/**
 *
 * @author pablohl
 *
 * Esta clase evalua la distribucion normal con los paremetros dados
 * de elemento, media y desviacion estándar
 *
 */
public class GaussianDistribution {

private static double m_normConst = Math.log(Math.sqrt(6.283185307179586D));

    public static double evaluate(double x, double mean, double sd){
        double variance=sd*sd;
        double exp=Math.exp((-1)*(Math.pow(x-mean,2)/(2 * variance ))  );
        return (1/Math.sqrt(2*Math.PI* (variance))) * exp;
    }
}
