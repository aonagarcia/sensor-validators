/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.aonagarcia.RegsMttos;

import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author javier
 */
public class TimeLinesManager {

    private ArrayList<timeLine> timeLinesList;
    private int timeLineId = 0;

    TimeLinesManager() {
        timeLinesList = new ArrayList<timeLine>();
    }

    public void addTimeLine(String timeLineName) {
        timeLine tl = new timeLine(timeLineId++, timeLineName);
        timeLinesList.add(tl);
    }

    /**
     * Regresa verdadero si la condici�n (l�nea de tiempo) ya existe en la lista de l�neas de tiempo
     * @param conditionName Nombre de la condici�n
     * @return verdadero si existe, falso en otro caso
     */
    public boolean existTimeLine(String timeLineName) {
        Iterator it = this.timeLinesList.iterator();
        while (it.hasNext()) {
            timeLine t = (timeLine) it.next();
            if (t.getTimeLineName().compareTo(timeLineName) == 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * Regresa la l�nea de tiempo que corresponde con el nombre timeLineName
     * @param timeLineName nombre de la linea de tiempo que se desea buscar
     * @return l�nea de tiempo encontrada. Null en caso de que no se encontrara ninguna
     */
    public timeLine getTimeLine(String timeLineName) {
        Iterator it = this.timeLinesList.iterator();
        while (it.hasNext()) {
            timeLine t = (timeLine) it.next();
            if (t.getTimeLineName().compareTo(timeLineName) == 0) {
                return t;
            }
        }
        return null;
    }

    /**
     * Comprueba si existe un evento para la instancia row
     * @param row instancia en la que se busca la ocurrencia de un evento
     * @return
     */
    public boolean existEvent(int row) {
        Iterator it = this.timeLinesList.iterator();
        while (it.hasNext()) {
            timeLine t = (timeLine) it.next();
            if (t.existeEventAt(row)) {
                return true;
            }
        }
        return false;
    }

    public String getMttosInfo(int row) {
        String data = "";
        Iterator it = this.timeLinesList.iterator();
        while (it.hasNext()) {
            timeLine t = (timeLine) it.next();
            if (t.existeEventAt(row)) {
                data += t.getTimeLineName() + "\n" + t.getEventsTags(row);
            }
        }
        return data;
    }

    public void getArrayEvents(boolean[] s) {
        for (int i = 1; i < s.length+1; i++) {
            Iterator it = this.timeLinesList.iterator();
            while (it.hasNext()) {
                timeLine t = (timeLine) it.next();
                if (t.existeEventAt(i)) {
                   s[i-1]=true;
                }
            }
        }
    }
}
