/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.aonagarcia.svd.validacionLocal;

import com.github.aonagarcia.extras.Instances;
import java.util.ArrayList;

/**
 *
 * @author javier
 */
public class segmentDataSet {

    /**Almacena la fuente de datos de entrada a particionar*/
    private Instances m_dataSource;

    /**Almacena las fuentes de datos de salida (particiones)*/
    private ArrayList<Instances> m_partitionsInstances;

    /**N�mero de particiones, por defecto ser�n 2 como m�nimo*/
    private int m_nPartitions = 2;

    /**
     * Constructor de la clase
     * @param ins DataSet que ser� particionado
     * @param nP n�mero de particiones
     */
    public segmentDataSet(Instances ins, int nP) {
        m_dataSource = ins;
        m_nPartitions = nP;
        m_partitionsInstances = new ArrayList<Instances>();
    }


    /**
     * Valida que el n�mero de segmentos sea menor al
     * n�mero m�ximo de los datos
     * @param n N�mero de segmentos
     * @return Verdadero o Falso
     */
    private boolean validNumSegments(int n) {
        return n <= m_dataSource.numInstances();
    }

    /**
     * Crea las particiones y las almacena en m_partitionsInstances
     */
    public void splitDataSet() {
        int from = 0;
        int[] _segmentSizesArray = segmentSize(m_nPartitions);

        for (int i = 0; i < _segmentSizesArray.length; i++) {
            from = indiceInicial(_segmentSizesArray, i);
            Instances ins = new Instances(m_dataSource, from, _segmentSizesArray[i]);
            m_partitionsInstances.add(ins);
        }
    }

     /**
      * Crea las particiones y las almacena en m_partitionsInstances
      * @param pos Arreglo con las posiciones de corte de los datos
      */

    public void splitDataSet(int[] pos) {
        int from = 0;

        int[] _segmentSizesArray = new int[pos.length-1];
        
        for(int i=0;i<pos.length-1;i++)
        {
            /*if (i==0)
                _segmentSizesArray[i]=pos[i+1]+1;
            else*/
                _segmentSizesArray[i]=pos[i+1]-pos[i];
        }
        
        for (int i = 0; i < _segmentSizesArray.length; i++) {
            from = indiceInicial(_segmentSizesArray, i);
            Instances ins = new Instances(m_dataSource, from, _segmentSizesArray[i]);
            m_partitionsInstances.add(ins);
        }
    }

    /**
     * Regresa el �ndice (de la instacia de datos original) en el cual inicia el segmento especificado en el par�metro s
     * @param _segmentSize Arreglo que contiene el n�mero de elementos de cada segmento
     * @param s
     * @return �ndice en el que incia el segmento s
     */
    private int indiceInicial(int[] _segmentSize, int s) {
        int r = 0;
        for (int i = 0; i < s; i++) {
            r += _segmentSize[i];
        }
        return r;
    }

    /**
     * Regresa un arreglo con el n�mero de elementos que
     * debe contener cada partici�n
     *
     * @param s El n�mero de segmentos
     * @return Arreglo con el n�mero de elementos de cada segamento
     *
     * Nota: Si la partici�n no es uniforme, el primer elemento puede servir como referencia
     * para obtener el segmento de m�xima capacidad.
     */
    private int[] segmentSize(int s) {
        int[] segmentSize = new int[s];
        double _size = (m_dataSource.numInstances() / s);
        int _dif = m_dataSource.numInstances() - (s * ((int) _size));
        for (int i = 0; i < segmentSize.length; i++) {
            segmentSize[i] = (int) _size;
        }
        for (int i = 0; i < _dif; i++) {
            segmentSize[i] = segmentSize[i] + 1;
        }
        return segmentSize;
    }

    public ArrayList<Instances> getPartitions() {
        return this.m_partitionsInstances;
    }
}
