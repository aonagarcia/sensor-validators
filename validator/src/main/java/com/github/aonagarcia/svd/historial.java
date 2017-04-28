/*
 * historial.java
 *
 * Created on 7 de diciembre de 2008, 12:39 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.github.aonagarcia.svd;

import com.github.aonagarcia.extras.Instances;
import java.io.File;
import java.io.*;
import javax.swing.UIManager;
import javax.swing.JFileChooser;
import com.github.aonagarcia.igu.ExtensionFileFilter;
import javax.swing.JOptionPane;

/**
 *
 * @author Javier
 */
public class historial {
    
    private Instances   m_historial;    
    protected final String ETQ_DATO_FALTANTE="DF";
    protected final String ETQ_FUERA_RANGO="OL";
    protected final String ETQ_CAMBIO_ABRUPTO="CA";
    protected final String ETQ_RELACION_VARS="RV";
    
    protected Thread m_IOThread;
    
    
    /** Creates a new instance of historial */
    public historial(Instances inst) {
        m_historial=new Instances(inst);
    }
    
    public void regDeleteVar(String varName) {
        m_historial.attribute(varName).setAsDeleted();
    }
    
    public void regDeleteInstance(int row) {
        
        m_historial.instance(row).setAsDeleted();
    }
    
    
    public void save(String fileName) {
        if (fileName == null) {
            UIManager.put("FileChooser.fileNameLabelText", "Nombre del archivo");
            JFileChooser m_FileChooser = new JFileChooser(new File(System.getProperty("user.dir")));

            ExtensionFileFilter ffilter = new ExtensionFileFilter(".csv", "Valores separados por coma (*.csv)");
            m_FileChooser.setFileFilter(ffilter);

            m_FileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            m_FileChooser.setDialogTitle("Guardar historial");

            int returnVal = m_FileChooser.showSaveDialog(Control._igu.ValidateData);
            if (returnVal == JFileChooser.APPROVE_OPTION) {

                String nameSelected = m_FileChooser.getSelectedFile().getPath();
                if (!nameSelected.endsWith(".csv")) {
                    nameSelected += ".csv";
                }
                File sFile = new File(nameSelected);


                if (sFile.exists()) {
                    int response = JOptionPane.showConfirmDialog(Control._igu.ValidateData, "�Sobreescribir el contenido de " + sFile.getName() + "?",
                            "Guardar Historial", JOptionPane.WARNING_MESSAGE);
                    if (response == JOptionPane.NO_OPTION || response == JOptionPane.CANCEL_OPTION) {
                        return;
                    }
                }
                saveInstancesToFile(sFile, m_historial);
            }
        } else {
            File sFile = new File(fileName);
            saveInstancesToFile(sFile, m_historial);
        }
    }
    
    protected void saveInstancesToFile(final File f, final Instances inst) {
        
        if (m_IOThread == null) {
            m_IOThread = new Thread() {
                public void run() {
                    try {
                        Writer w = new BufferedWriter(new FileWriter(f));
                        for (int i = 0; i < inst.numAttributes(); i++) {
                            String varName=inst.attribute(i).name();
                            if (inst.attribute(i).wasDeleted())
                                w.write(varName+" / Eliminada");
                            else
                                w.write(varName);
                            if (i < inst.numAttributes()-1) {
                                w.write(",");
                            }
                        }
                        w.write("\n");
                        
                        for (int i = 0; i < inst.numInstances(); i++) {
                            for (int j = 0; j < inst.numAttributes(); j++) {
                                if ((inst.attribute(j).wasDeleted()) || (inst.instance(i).wasDeleted())) {                                
                                    writeToFile(w,inst, i,  j,  "/ Eliminado /NA");
                                }else
                                {
                                  
                                        int errType=inst.instance(i).getErrorType(j);
                                        switch(errType)
                                        {
                                            case 0:{
                                                    writeToFile(w,inst, i,  j,  ""); 
                                                    break;
                                                    }
                                            case 1:{
                                                    writeToFile(w,inst, i,  j, "/"+ETQ_DATO_FALTANTE+"/"+inst.instance(i).getProbableValue(j)); 
                                                    break;
                                                    }
                                            case 2:{
                                                    //06/03/2012 writeToFile(w,inst, i,  j, "/"+ETQ_FUERA_RANGO+"/"+inst.instance(i).getProbableValue(j));
                                                    writeToFile(w,inst, i,  j, "/"+ETQ_FUERA_RANGO);
                                                    break;
                                                    }
                                            case 3:{
                                                    writeToFile(w,inst, i,  j, "/"+ETQ_CAMBIO_ABRUPTO+"/"+inst.instance(i).getProbableValue(j)); 
                                                    break;
                                                    }
                                            case 4:{
                                                    writeToFile(w,inst, i,  j, "/"+ETQ_RELACION_VARS+"/"+inst.instance(i).getProbableValue(j)); 
                                                    break;
                                                    }                                            
                                            default:{ break;}
                                        }
                                    }                                
                                
                                if (j < inst.numAttributes()-1) {
                                    w.write(",");
                                }
                            }
                            w.write("\n");
                        }
                        w.close();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    m_IOThread = null;
                }
            };
            m_IOThread.setPriority(Thread.MIN_PRIORITY);
            m_IOThread.start();
        } else {
            JOptionPane.showMessageDialog(Control._igu.ValidateData,
                    "No es posible guardar en este momento,\n"
                    + "Sistema ocupado",
                    "Guardar Historial",
                    JOptionPane.WARNING_MESSAGE);
        }
    }
    
    public Instances getInstances() {
        return m_historial;
    }
    
    
    public void writeToFile(Writer w,Instances Inst, int i, int j, String m) {        
        try {
            if (Inst.attribute(j).isNumeric())
                w.write(Inst.instance(i).value(j)+m);
            else
                w.write(Inst.instance(i).stringValue(j)+m);
        }catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
