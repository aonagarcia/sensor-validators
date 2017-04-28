/*
 * ValidationObjects.java
 *
 * Created on 17 de febrero de 2011, 10:30 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.github.aonagarcia.svd;

import java.io.*;
import com.github.aonagarcia.extras.Instances;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 *
 * @author Javier
 */

public class ValidationObjects implements Serializable{
    
    private Instances varsParams;
    private Instances BD;
    private Instances BD4Plot;
    private String cmd;
    private double significanceLevel;
    private double p_value;
    private double Pf_Th;
    private LinkedList varList;
    private ArrayList selectedVars;
    
    public ValidationObjects()
    {
        this.varsParams=null;
        this.BD=null;
        this.BD4Plot=null;
        this.cmd=null;
        this.p_value=0;
        this.Pf_Th=0;
        this.significanceLevel=0;
        this.varList=null;       
    }
    
    /** Creates a new instance of ValidationObjects */
    public ValidationObjects(Instances a, Instances b) {
        this.varsParams=a;  //Estructura con los par�metros de rangos definidos para cada variable
        this.BD=b;          //Instancia con las variables y datos de la cual se construir� o se validar� un modelo
        cmd=null;           //Comando que deber� ejecutar el servidor ("build", "validate","update","fill")
    }

    public void setCmd(String _cmd){
        this.cmd=_cmd;
    }
    
    public String getCmd(){
        return this.cmd;
    }
    
    public void setBD(Instances _BD){
        this.BD=_BD;
    }
    
    public Instances getBD() {
        return BD;
    }    
    
    public Instances setVarsMoldel(Instances _varsModels){
        return this.varsParams=_varsModels;
    }
    
    public Instances getVarsMoldel(){
        return this.varsParams;
    }       

    public void setDNparams(double sl, double p_value, double Pf_Th) {
       this.significanceLevel=sl;
       this.p_value= p_value;
       this.Pf_Th=Pf_Th; 
    }

    public double getSignificance()
    {
        return this.significanceLevel;
    }
    
    public double getP_value(){
        return this.p_value;
    }
    
    public double getPf_Th(){
        return this.Pf_Th;
    }
    
    public void setVarModelList(LinkedList l){
        this.varList=l;
    }
    
    public LinkedList getVarModelList(){
        return this.varList;
    }

    public void setSelectedVarsArray(ArrayList sel) {
       this.selectedVars=sel;
    }
    
    public ArrayList getSelectedVarsArray(){
        return this.selectedVars;
    }    
    
    public void setBD4Plot(Instances inst){
        this.BD4Plot=inst;
    }    
    
    public Instances getBD4Plot(){
        return this.BD4Plot;
    }
}
