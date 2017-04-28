/*
 * Variables.java
 *
 * Created on 16 de julio de 2008, 16:35
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.github.aonagarcia.igu;


import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import com.github.aonagarcia.extras.Instances;
import com.github.aonagarcia.extras.FastVector;
import com.github.aonagarcia.extras.Attribute;
/**
 *
 * @author Administrador
 */
public class Variables {
    /*Panel que contiene una tabla con la lista de variables del grupo definido*/
    public AttributeSelectionPanel m_List;
    /*Nombre que recibir� el panel de acuerdo al grupo de variables*/
    private String panelName;
    /*Nombre de las variables como ser�n mostrados en la interfaz*/
    private String[] varNames;
    /*Nombres de las variables en la estructura de datos*/
    private String[] vars;
    
    /*vector para almacenar los �ndices tal como aparecen en la estructura de datos principal*/
    private Vector indicesAtributos;
    
    private JPanel m_Panel = new JPanel();
    
    public Variables(String nombre)
    {
        m_List = new AttributeSelectionPanel(true);
        indicesAtributos=new Vector();
        m_Panel.setBorder(BorderFactory.createTitledBorder(null, nombre, TitledBorder.CENTER, TitledBorder.DEFAULT_POSITION) );
        m_Panel.add(m_List);       
    }
    /*El constructor de la clase crea una estructura Instances con los nombres de las variabes que contendr� el grupo.
     *Recibe los par�metros:
     *@param nombre Nombre que tendra el contenedor
     *@param Nombres de las variables que se mostrar�n en la IGU
     *@param Nombre de las variables como aparecen en la estructura de datos
     *@param hcb (HideCheckBox) bandera para habilitar o deshabilitar el checkBox que aparece al lado del nombre de las variables
     */
    public Variables(String nombre, String[] content,String[] vars,boolean hcb) {
        m_List = new AttributeSelectionPanel(hcb);
        this.vars=vars;
        varNames=content;
        panelName=nombre;
        indicesAtributos=new Vector();
        
        FastVector fv;
        Instances inst;
        m_Panel.setBorder(BorderFactory.createTitledBorder(null, nombre, TitledBorder.CENTER, TitledBorder.DEFAULT_POSITION) );
        
        fv = new FastVector(content.length);
        for(int i = 0; i < content.length; i++)
            fv.addElement(new Attribute(content[i]) );
        
        inst = new Instances(nombre, fv, 1);
        m_List.setInstances(inst);
        m_List.m_Model.includeAll();
        m_Panel.add(m_List);
    }
    
    /*M�todo para mostrar los nombres de las variables del grupo en la interfaz.
     *Este m�todo realiza el mapeo entre los nombres de las varibles a mostrar y el
     *nombre de las variables usado en la estructura de datos.
     *Durante este proceso, almacena el �ndice de cada varible para el mapeo posterior
     *entre los �ndices locales y los �ndices de la estructura de datos Instances.
     */
    public void showVarsNames(Instances newInstances) {
        
        int cont=0;
        indicesAtributos.clear();
        String [] nameVars=new String[varNames.length];
        
        for (int j=0; j<varNames.length;j++) {
            for (int i=0;i<newInstances.numAttributes();i++) {
                if (vars[j].equals(newInstances.attribute(i).name())) {
                    newInstances.attribute(i).setVarGroup(panelName);
                    //se almacena el �ndice original que tiene la  variable en la estructura "Instances"
                    indicesAtributos.add(i);
                    nameVars[cont]=varNames[j];
                    cont++;
                }
            }
        }
        
        FastVector fv;
        Instances inst;
        fv = new FastVector(cont);
        for (int i=0; i<cont; i++) {
            //Muestra en la IGU el nombre de las columnas como estan en el archivo CSV
            //fv.addElement(new Attribute(newInstances.attribute(indicesAtributos[i]).name()) );            
            //Muestra en la IGU el nombre asignado a las columnas del CSV
            fv.addElement(new Attribute(nameVars[i]));
        }
        inst = new Instances(panelName, fv, 1);
        m_List.setInstances(inst);
        
    }
    
public void showVarsNames2010(Instances newInstances) {
        
        int cont=0;
        indicesAtributos.clear();
        String [] nameVars=new String[newInstances.numAttributes()];
        
        //for (int j=0; j<varNames.length;j++) {
            for (int i=0;i<newInstances.numAttributes();i++) {
                //if (vars[j].equals(newInstances.attribute(i).name())) {
                    newInstances.attribute(i).setVarGroup(panelName);
                    //se almacena el �ndice original que tiene la  variable en la estructura "Instances"
                    indicesAtributos.add(i);
                    //nameVars[cont]=varNames[j];
                    cont++;
                //}
            }
        //}
        
        FastVector fv;
        Instances inst;
        fv = new FastVector(cont);
        for (int i=0; i<cont; i++) {
            //Muestra en la IGU el nombre de las columnas como estan en el archivo CSV
            //fv.addElement(new Attribute(newInstances.attribute(indicesAtributos[i]).name()) );
            fv.addElement(new Attribute(newInstances.attribute(i).name()) );
            //Muestra en la IGU el nombre asignado a las columnas del CSV
            //fv.addElement(new Attribute(nameVars[i]));
        }
        inst = new Instances(panelName, fv, 1);
        m_List.setInstances(inst);
        
    }    
    
    /*Regresa un arreglo con los �ndices "originales" (con respecto a la estructura de datos original)*/
    public int[] getSelectedAttributes() {
        int [] r = m_List.m_Model.getSelectedAttributes();
        int [] r2=new int[r.length];
        for(int i=0; i<r.length;i++)
            r2[i]=getVarIndex(r[i]);
        return r2;
        
    }
    
    public int[] getSelectedAttributes2() {
        int [] r = m_List.m_Model.getSelectedAttributes();
        int [] r2=new int[r.length];
        for(int i=0; i<r.length;i++)
            r2[i]=getVarIndex(r[i]);
        return m_List.m_Model.getSelectedAttributes();
    }
    
    /*Selecciona los checkBox de las variables cuyos �ndices estan en el arreglo idxs*/
    public void setSelectedAttributes(int[] idxs) {
        Vector vars=new Vector();
        for (int i=0; i<idxs.length ;i++)
            //for(int j=0;j<indicesAtributos.length; j++)
            for(int j=0;j<indicesAtributos.size() ;j++) {
            //if (indicesAtributos[j]==idxs[i])
            if (((Integer)indicesAtributos.elementAt(j)).intValue()==idxs[i]) {
                vars.add(j);
            }
            }
        
        int[] t=new int [vars.size()];
        for (int i=0; i<t.length;i++) {
            t[i]=Integer.parseInt(vars.get(i).toString());
        }
        m_List.m_Model.include(t);
    }
    
    /*Regresa el �ndice original del �ndice local idx*/
    public int getVarIndex(int idx) {
        // return indicesAtributos[idx];
        if (indicesAtributos.size()>0)
            return ((Integer)indicesAtributos.get(idx)).intValue();
        else
            return 0;
    }
    
    public JPanel getPanel() {
        return m_Panel;
    }
    
    /*Regresa un arreglo con los �nidices originales de todas las variables presentes en el contenedor*/
    public int [] getIndexArray() {
        int[] t=new int [indicesAtributos.size()];
        for (int i=0; i<t.length;i++) {
            t[i]=Integer.parseInt(indicesAtributos.get(i).toString());
        }
        return t;
    }

 
}
