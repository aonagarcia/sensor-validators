/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.aonagarcia.RegsMttos;

import com.github.aonagarcia.extras.CSVLoader;
import com.github.aonagarcia.extras.Instances;
import java.io.File;

/**
 *
 * @author javier
 */
public class mttosDataSet {

    private String timeLineNameField = "Condition";
    private String eventIdField = "Event";
    private String onsetField = "Onset";
    private String DurationField = "Duration";
    private String BaseLineField = "Baseline";
    private String LatencyField = "Latency";
    private String tagData1 = "User_Data_1";
    private Thread m_IOThread = null;
    private Instances bitacora; //Objeto para almacenar los registros de los mantenimientos
    private TimeLinesManager m_timeLinesManager;

    /**
     * Carga en la estructura "bitacora" el archivo <b>selected</b> que contiene la informaci�n
     * de los registros de eventos (mantenimientos/Intervenciones)
     * @param selected Ruta y nombre del archivo a cargar.
     */
    public void loadDataFile(final File selected) {
        try {
            CSVLoader cnv = new CSVLoader();
            cnv.setSource(selected);
            bitacora = cnv.getDataSet();
        } catch (Exception e) {
            m_IOThread = null;
            e.printStackTrace();
        }
    }

    /**
     * Crea la estructura principal que almacena las l�neas de tiempo y sus respectivos eventos.
     * Esta estructura permite un manejo de los registros de mantenimientos contra las muestras
     * cargadas en el sistema de validaci�n. El m�todo recorre registro a registro el dataSet de
     * instancias leido.
     */
    public TimeLinesManager buildTimeLine() {        

        int timeLineNameAttidx = bitacora.attribute(timeLineNameField).index();
        int EventAttidx = bitacora.attribute(this.eventIdField).index();
        int OnsetAttidx = bitacora.attribute(this.onsetField).index();
        int DurationAttidx = bitacora.attribute(this.DurationField).index();
        int BaseLineAttidx = bitacora.attribute(this.BaseLineField).index();
        int LatencyAttIdx = bitacora.attribute(this.LatencyField).index();
        int TagAttIdx = bitacora.attribute(this.tagData1).index();

        m_timeLinesManager = new TimeLinesManager();

        for (int i = 0; i < this.bitacora.numInstances(); i++) {
            String timeLineName = bitacora.instance(i).stringValue(timeLineNameAttidx);
            //Si no existe la l�nea de tiempo, se crea y se agrega el evento
            if (!m_timeLinesManager.existTimeLine(timeLineName)) {
                m_timeLinesManager.addTimeLine(timeLineName);
                int start = (int) bitacora.instance(i).value(OnsetAttidx);
                int duration = (int) bitacora.instance(i).value(DurationAttidx);
                int eventCount = (int) bitacora.instance(i).value(EventAttidx);
                int baseLine = (int) bitacora.instance(i).value(BaseLineAttidx);
                int latency = (int) bitacora.instance(i).value(LatencyAttIdx);
                String tag = bitacora.instance(i).stringValue(TagAttIdx);
                m_timeLinesManager.getTimeLine(timeLineName).addEvent(start, start + duration, eventCount, tag,baseLine,latency);
            } else //Si ya existe, se agrega el nuevo evento encontrado a la l�nea de tiempo
            {
                int start = (int) bitacora.instance(i).value(OnsetAttidx);
                int duration = (int) bitacora.instance(i).value(DurationAttidx);
                int eventCount = (int) bitacora.instance(i).value(EventAttidx);
                String tag = bitacora.instance(i).stringValue(TagAttIdx);
                int baseLine = (int) bitacora.instance(i).value(BaseLineAttidx);
                int latency = (int) bitacora.instance(i).value(LatencyAttIdx);
                m_timeLinesManager.getTimeLine(timeLineName).addEvent(start, start + duration, eventCount, tag,baseLine,latency);
            }
        }
        return m_timeLinesManager;
    }


    public TimeLinesManager getTimeLineMannager() {
        return this.m_timeLinesManager;
    }


}
