/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.aonagarcia.svd;

/**
 *This class implements several error measures as described in:
 *
 * E.A. Osman, O.A. Abdel-Wahhab, M.A. Al-Marhoun,
 * Prediction of Oil PVT Properties Using Neural Networks
 * Paper SPE 68233
 *
 * 
 * @author javier
 */
public class errorMesurmentsUtils {

    /*Number of samples*/
    private int n;

    /*Estimated values vector*/
    private double[] Est;

    /*Real values vector*/
    private double[] Exp;

    /*Vector to store the Relative deviation of every pair (Estimated, Real) values
     *  E=((Exp-Est)/Exp)*100
     */
    private double[] relativeDeviation;

    /**
     * Class constructor
     * @param _Estimated array of estimated values
     * @param _Real array of real values
     */
    public errorMesurmentsUtils(double[] _Estimated, double[] _Real) {
        this.Est = _Estimated;
        this.Exp = _Real;
        this.n = Est.length;
        relativeDeviation=new double[Est.length];
        computeRelativeDeviation();
    }

    /**
     * Compute and store the relative deviation of an estimated value from
     * an experimental value.
     */
    private void computeRelativeDeviation() {
        for (int i = 0; i < n; i++) {
            relativeDeviation[i] = ((Exp[i] - Est[i]) / Exp[i]) * 100;
        }
    }

    /**
     * Computes the Average Percent Relative Error
     * @return Average Percent Relative Error
     */
    public double AveragePercentRelativeError() {
        double sum = 0;
        for (int i = 0; i < n; i++) {
            sum = sum + (relativeDeviation[i]);
        }
        return sum / n;
    }

    /**
     * Computes the Average Absolute Percent Relative Error
     * @return Average Absolute Percent Relative Error
     */

    public double AverageAbsolutePercentRelativeError() {
        double sum = 0;
        for (int i = 0; i < n; i++) {
            sum = sum + (Math.abs(relativeDeviation[i]));
        }
        return sum / n;
    }

     /**
     * Computes the Minimum Absolute Percent Relative Error
     * @return Minimum Absolute Percent Relative Error
     */

    public double MinimumAbsolutePercentRelativeError()
    {
        double min=Double.MAX_VALUE;
        for (int i=0; i<n; i++)
                min=Math.abs(relativeDeviation[i])<min?Math.abs(relativeDeviation[i]):min;
        return min;
    }

    /**
     * Computes the Maximum Absolute Percent Relative Error
     * @return Maximum Absolute Percent Relative Error
     */
     public double MaximumAbsolutePercentRelativeError()
    {
        double max=Double.MIN_VALUE;
        for (int i=0; i<n; i++)
                max=Math.abs(relativeDeviation[i])>max?Math.abs(relativeDeviation[i]):max;
        return max;
    }

    /**
     * Compute the Root Mean Square Error
     * @return Root Mean Square Error
     */
    public double RMS() {
        double sum = 0;
        for (int i = 0; i < n; i++) {
            sum = sum + (Math.pow(relativeDeviation[i], 2));
        }

        return Math.sqrt(sum / n);
    }
}
