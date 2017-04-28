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
public class timeLine {
    private int timeLineId;
    private String timeLineName;

    ArrayList<Event> timeLineStruct;

    timeLine(int t, String n)
    {
        this.timeLineId=t;
        this.timeLineName=n;
        this.timeLineStruct=new ArrayList<Event>();
    }

    /**
     * Agrega un evento a la l�nea de tiempo
     * @param start n�mero de muestra de inicio
     * @param end n�mero de muestra final
     * @param bl n�mero de muestras del BaseLine
     * @param l n�mero de muestras de la Latencia
     */
    public boolean addEvent(int start, int end, int eventCount,String tag, int bl, int l)
    {
        if (!this.existEvent(eventCount))
        {
            Event event=new Event(start,end,eventCount,tag,bl,l);
            timeLineStruct.add(event);
            return true;
        }
        else
        {
            System.out.println("Evento duplicado. L�nea de tiempo: "+timeLineName+" evento: "+eventCount);
            return false;
        }
    }

    public int getTimeLineId()
    {
        return this.timeLineId;
    }

    public String getTimeLineName()
    {
        return this.timeLineName;
    }

    /**
     * Devuelve verdadero si el evento con identificador "EventCount" ya existe en la l�nea de tiempo
     * @param EventCount identificador del evento en la l�nea de tiempo
     * @return verdadero si el evento existe, falso en otro caso
     */
    public boolean existEvent(int EventCount)
    {
        Iterator it=timeLineStruct.iterator();
        while(it.hasNext())
        {
            Event e=(Event)it.next();
            if (e.getEventCount()==EventCount)
                return true;
        }
        return false;
    }

    boolean existeEventAt(int row) {
        Iterator it=timeLineStruct.iterator();
        while(it.hasNext())
        {
            Event e=(Event)it.next();
            if ((row>=e.getStart()-e.getBaseLine()) && (row<=e.getEnd()+e.getLatency()))
                return true;
        }
        return false;
    }

    public String getEventsTags(int atSample)
    {
        String evtTags="";
        Iterator it=timeLineStruct.iterator();
        while(it.hasNext())
        {
            Event e=(Event)it.next();
            if ((atSample>=e.getStart()) && (atSample<=e.getEnd()))
                evtTags+="\t-"+e.getTag()+"\n";
        }
        return evtTags;
    }
}
