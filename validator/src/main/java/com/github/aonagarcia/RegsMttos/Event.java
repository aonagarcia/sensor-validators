/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.github.aonagarcia.RegsMttos;

/**
 *
 * @author javier
 */
public class Event {

    private String tag;
    private int idObjeto;
    private int start;
    private int end;
    private int onset;
    private int offset;
    private int lifespan;
    private int eventCount;
    private int baseLine;
    private int Latency;

   /**
    * Constructor para crear un evento en la l�nea de tiempo, 
    * @param c Comentario a almacenar en el evento
    * @param s N�mero muestra en la que incia el evento
    * @param e N�mero de muestra en que finaliza el evento
    * @param count Contador de evento en la l�nea de tiempo, p.e. Condicion1----evento1----evento2----eventoN
    */

    Event(int s, int e, int count,String c,int bl, int l)
    {
        
        this.start=s;
        this.end=e;
        this.eventCount=count;
        this.tag=c;
        this.baseLine=bl;
        this.Latency=l;
    }

    /**
     * Constructor para crear un evento definiendo los valores onset y offset en lugar de
     * start y end.
     * @param c Comentario a almacenar en el evento
     * @param os par�metro Onset
     * @param offs par�metro offset
     * @param count contador de evento en la l�nea de tiempo
     */
    /*
    Event( String c,int os, int offs,int count)
    {
        this.tag=c;
        if (!(os<offs))
        {
            System.out.println("Event: Onset > Offset!!!");
            return;
        }
        this.onset=os;
        this.offset=offs;
        this.lifespan=offs-os;
        this.eventCount=count;
    }
     *
     */


    public int getEnd() {
        return end;
    }

    public int getEventCount() {
        return eventCount;
    }

    public int getStart() {
        return start;
    }

    public String getTag() {
        return tag;
    }

    int getBaseLine() {
       return this.baseLine;
    }

    int getLatency() {
       return this.Latency;
    }


}
