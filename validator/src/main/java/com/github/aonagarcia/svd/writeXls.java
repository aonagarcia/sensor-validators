/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.aonagarcia.svd;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import jxl.*;
import jxl.write.*;
import com.github.aonagarcia.extras.*;
import jxl.format.Colour;
import jxl.format.CellFormat;

/**
 * Escribir el contenido de los datos validados (con los datos marcados) en un archivo de excel.
 * Se debe pasar como par�metro el objeto Instances con los datos validados.
 * @author javier
 */
public class writeXls {

    private Instances ins;
    private WritableWorkbook workbook;
    private WritableSheet sheet;
    private WritableCellFormat[] backgroundsColors;
    /*Normal,Faltante,Outlier,Abrupt,Rouge,LocalOutLier*/
    private Colour[] colors = {Colour.WHITE, Colour.GRAY_50, Colour.RED, Colour.YELLOW, Colour.GREEN,Colour.ORANGE};
    private CellFormat selectedBackground;

    public writeXls(String name) {
        try {
            workbook = Workbook.createWorkbook(new File(name + ".xls"));
            sheet = workbook.createSheet("Hoja1", 0);
            backgroundsColors = new WritableCellFormat[colors.length];
            for (int i = 0; i < backgroundsColors.length; i++) {
                backgroundsColors[i] = new WritableCellFormat();
                backgroundsColors[i].setWrap(true);
                backgroundsColors[i].setBackground(colors[i]);
            }
        } catch (IOException ex) {
            Logger.getLogger(writeXls.class.getName()).log(Level.SEVERE, null, ex);
        } catch (WriteException ex2) {
            Logger.getLogger(writeXls.class.getName()).log(Level.SEVERE, null, ex2);
        }
    }

    /**
     * Establece el objeto de instancias
     * @param ins Objeto que contiene todos los datos a escribir en el excel
     */
    public void setIns(Instances ins) {
        this.ins = ins;
    }

    public boolean write() throws WriteException {
        /*Cabecera de las columnas*/
        for (int att = 0; att < ins.numAttributes(); att++) {
            Label label = new Label(att, 0, ins.attribute(att).name());
            try {
                sheet.addCell(label);
            } catch (WriteException ex) {
                Logger.getLogger(writeXls.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
        }
        /*Datos*/
        for (int instance = 0; instance < ins.numInstances(); instance++) {
            for (int att = 0; att < ins.numAttributes(); att++) {
                //escribir el dato y dar formato a la celda de acuerdo al error que contenga el dato
                try {
                    int errorType = ins.instance(instance).getErrorType(att);
                    double val = Double.NaN;
                    if (!ins.instance(instance).isMissing(att)) {
                        val = ins.instance(instance).value(att);
                        jxl.write.Number number;
                        if (errorType==0)
                        {
                            number = new jxl.write.Number(att, instance + 1, val);
                        }
                        else
                        {
                            number = new jxl.write.Number(att, instance + 1, val, backgroundsColors[errorType]);
                        }
                        sheet.addCell(number);
                    } else {
                        val = ins.instance(instance).value(att);
                        jxl.write.Label label = new jxl.write.Label(att, instance+1, "");
                        sheet.addCell(label);
                    }
                } catch (WriteException ex) {
                    Logger.getLogger(writeXls.class.getName()).log(Level.SEVERE, null, ex);
                    return false;
                }
            }
        }
        try {
            workbook.write();
            workbook.close();
        } catch (IOException ex) {
            Logger.getLogger(writeXls.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } catch (WriteException ex) {
            Logger.getLogger(writeXls.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }
}
