/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.github.aonagarcia.gaussianmixturemodel;

/**
 *
 * @author Pablo HL
 *
 *
 * Esta clase representa un intervalo,
 * contiene un punto de inicio (start)
 * y un punto final (end)
 *
 */
public class Interval implements Comparable{


    private double start;
    private double end;


    public Interval(double start, double end) {
        this.start = start;
        this.end = end;
    }


    public double getEnd() {
        return end;
    }

    public void setEnd(double end) {
        this.end = end;
    }

    public double getStart() {
        return start;
    }

    public void setStart(double start) {
        this.start = start;
    }


    @Override
    public String toString(){
        return "\"["+start+"-"+end+"]\"";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Interval other = (Interval) obj;
        if (this.start != other.start) {
            return false;
        }
        if (this.end != other.end) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        return hash;
    }

    public int compareTo(Object t) {
        if (this.start == ((Interval) t).getStart())
            return 0;
        else if ((this.start) > ((Interval) t).getStart())
            return 1;
        else
            return -1;
    }


    public boolean valueInInterval(double value){

        if (value>=start&& value<end){
            return true;
        }
        return false;
    }


    public static boolean containedIn(Interval i, Interval j){
        if (i.getStart()>=j.getStart() &&
                i.getEnd()<=j.getEnd())
            return true;
        return false;
    }


}
