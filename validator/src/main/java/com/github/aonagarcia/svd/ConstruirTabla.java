/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.github.aonagarcia.svd;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Alex
 */
public class ConstruirTabla {
    private List<String> cabecera;
    private List<String[]> registroEntrenar;
    private Double[][] matrizInicial;
    private final Double Null= null;
    //private final Double nAn= Double.valueOf("NaN");
    private final Double infinity= Double.valueOf("Infinity");
    private int numColAgregada;//Número total de columnas agregadas.
    private int numColumna;//Número de columnas del arreglo original.
    private int numRegistro;//Número de registros(filas) acumulados en la matriz dinamica de datos.
    private final int numCAPC=2;//Número de Columanas Agregadas Por Columna (columna-1;columna+2).
    private final int numAntSig = 1;//Número de retraso y adelanto (x-1) (x+1).
//    private final int numAnt = 1;//Número de retraso (x-1).
//    private final int numSig = 1;//Número de adelanto (x+1).
    
    public ConstruirTabla(){
        cabecera = null;
        registroEntrenar = null;
        matrizInicial = null;
    }


    public void setCabeceras(String[] _cabeceras){
        cabecera = new ArrayList<String>();
        numColumna = _cabeceras.length;
        int numTotalColumna = (1+numCAPC)*numColumna;
        int c=0;
        boolean cambio = true;//Indica el cambio de variable (columna; columna-1; columna+1;......)
        int numBloques=0;//Indica el número total de bloques recorridos ==> (1+numCAPC)=numBloques

        for(int cA=0; cA<numTotalColumna; cA++){
            c=cA % numColumna;
            if(c==0){
                if(!cambio)
                    cambio=true;
                else
                    cambio=false;
                numBloques++;
            }

            if(cA>=numColumna){
                if(cambio)
                    cabecera.add(_cabeceras[c] + "_ant");
                else
                    cabecera.add(_cabeceras[c] + "_sig");
            }
            else
                cabecera.add(_cabeceras[c]);
        }
        numColAgregada = cabecera.size();
    }

    public void setRegistro (List<Double[]> _registro){
        ArrayList<String[]> dRegistro = new ArrayList<String[]>();
        List<String> fRegistro = new ArrayList<String>();
         for(Double[] vReg: _registro){
            for(Double reg: vReg ){
                fRegistro.add(doubleToString(reg));
            }
            dRegistro.add(Arrays.copyOf(fRegistro.toArray(), fRegistro.size(), String[].class) );
            fRegistro.clear();
        }
         setRegistros(dRegistro);
    }

    public void setRegistros(List<String[]> _registro){
        if(_registro.size() >= (1 + numAntSig)){
            numRegistro = _registro.size();
        String[][] matrizRegistro = new String[numRegistro][numColAgregada];
        int c=0;
        boolean cambio = true;//Indica el cambio de variable (columna; columna-1; columna+1;......)
        for(int cA=0; cA<numColAgregada; cA++){
            c = cA % numColumna;
            if(c==0){
                if(!cambio)
                    cambio=true;
                else
                    cambio=false;
            }
            //Rellenar Columnas
            for(int f=0; f<numRegistro; f++){
                if(cA>=numColumna){//Entra a calcular las variables retrasadas y adelantadas y las agrega a la matriz.
                    if(cambio){
                        if((f-numAntSig)>=0)
                            matrizRegistro[f][cA] = _registro.get(f-numAntSig)[c];
                        else
                            matrizRegistro[f][cA] = null;
                    }
                    else{
                        if((f+numAntSig)<numRegistro)
                            matrizRegistro[f][cA] = _registro.get(f+numAntSig)[c];
                        else
                            matrizRegistro[f][cA] = null;
                    }
                }
                else
                     matrizRegistro[f][cA] = _registro.get(f)[cA];
           }
        }
        arrayToListRemove(matrizRegistro); //Copia el arreglo bidimensional a una lista bidimencional y elimina las filas involucradas en el retraso y/o adelanto de valores.
        copiarMatrizInicial();
        }
    }

    private void copiarMatrizInicial(){
        matrizInicial = new Double[numRegistro][numColumna];//Crear un respaldo de la matriz original.
        for(int f=0; f<numRegistro; f++){
            for(int col=0; col<numColumna; col++)
                matrizInicial[f][col] = stringToDouble(registroEntrenar.get(f)[col]);
        }
    }

    private Double stringToDouble(String cadena){
        Double valor = null;
        try {
            valor = Double.parseDouble(cadena);
            if(valor.isNaN())
                valor = Double.NaN;
        } catch (NumberFormatException ex) {
            return Double.NaN;
        }
        return valor;
   }

    private String doubleToString(Double valor){
        String cadena = null;
        try {
            cadena = Double.toString(valor);
        } catch (Exception ex) {
            return "NaN";
        }
        return cadena;
   }

    private void arrayToListRemove(String[][] mRegistro){
        registroEntrenar = new ArrayList<String[]>(Arrays.asList(mRegistro));
        if(numCAPC>=2){//La matriz debe ser mayor o igual a 2 bloques.
            for(int y=0; y<numAntSig; y++){//Elimina filas de forma ascendente, por efecto del proceso del número anterior.
                registroEntrenar.remove(0);
            }
            numRegistro = registroEntrenar.size();//Actualiza el número de fila después del recorte.
            for(int s=0; s<numAntSig; s++){//Elimina filas de forma descendente, por efecto del proceso del número siguiente.
                registroEntrenar.remove(numRegistro-1);
                numRegistro = registroEntrenar.size();//Actualiza el número de fila después de cada recorte.
            }
        } 
        else{
            if(numCAPC==1){//Por defaul Eliminara de forma ascendente porque tiene programado que obtenga el valor anterior de cada valor cuando la matriz sea menor a 2 boques.
                for(int y=0; y<numAntSig; y++){
                    registroEntrenar.remove(0);
                }
                    numRegistro = registroEntrenar.size();//Actualiza el número de fila después del recorte.
            }
        }
        numRegistro = registroEntrenar.size();
    }

    public List<String> getCabeceraEntrenar(){
        return cabecera;
    }

    public List<String[]> getRegistroEntrenar(){
        return registroEntrenar;
    }

    public List<Double[]> getRegistrosEntrenar(){
        ArrayList<Double[]> dRegistro = new ArrayList<Double[]>();
        List<Double> fRegistro = new ArrayList<Double>();
        for(String[] vReg: registroEntrenar){
           for(String reg: vReg ){
               fRegistro.add(stringToDouble(reg));
           }
           dRegistro.add(Arrays.copyOf(fRegistro.toArray(), fRegistro.size(), Double[].class) );
           fRegistro.clear();
        }
        return dRegistro;
    }

    public int NumFilaRegistro(){
        return numRegistro;
    }

    public void insertarValor(Double valor, int fila, int column){
        matrizInicial[fila][column] = valor;
    }
    
    public List<String> getCabeceraInicial(){
        List sList = cabecera.subList(0, numColumna);
        return sList;
    }

    public List<Double[]> getRegistroInicial(){
        List<Double[]> rCompleto = new ArrayList<Double[]>(Arrays.asList(matrizInicial));
        return rCompleto;
    }

    public List<String[]> getRegistrosInicial(){
        List<Double[]> rCompleto = new ArrayList<Double[]>(Arrays.asList(matrizInicial));
        ArrayList<String[]> dRegistro = new ArrayList<String[]>();
        List<String> fRegistro = new ArrayList<String>();
         for(Double[] vReg: rCompleto){
            for(Double reg: vReg ){
                fRegistro.add(doubleToString(reg));
            }
            dRegistro.add(Arrays.copyOf(fRegistro.toArray(), fRegistro.size(), String[].class) );
            fRegistro.clear();
        }
         return dRegistro;
    }

    public int NumColumnaInicial(){ //Regresa el total de columnas que tiene la matriz original de datos.
        return numColumna;
    }

    public void eliminarNumInvalido(){
        //Busca un número invalido en cada uno de los registros y si almenos encuentra un número inavlido el registro (fila) sera eliminada.       
        List    sList = new ArrayList();
        int from = 0, to = 1;
        while(from < numRegistro){
            for(String[] vRegistro : registroEntrenar.subList(from, to))
                sList = Arrays.asList(vRegistro); //Obtenemos el vector.

            if (sList.indexOf(Null)!= -1) {
                registroEntrenar.remove(from);
                numRegistro = registroEntrenar.size();//Actualiza el número de fila después de cada recorte.
            } else {
                if (sList.indexOf(Double.NaN)!= -1) {
                    registroEntrenar.remove(from);
                    numRegistro = registroEntrenar.size();//Actualiza el número de fila después de cada recorte.
                } else {
                    if (sList.indexOf(infinity)!= -1) {
                        registroEntrenar.remove(from);
                        numRegistro = registroEntrenar.size();//Actualiza el número de fila después de cada recorte.
                    } else {
                        from++; //No se encontro ningun registro con valor invalido, se procede a buscar en el siguiente registro.
                        to++;
                   }
               }
           }

           }
    }
}
