/*
 * ValidateData.java
 *
 * Created on 16 de julio de 2008, 14:05
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package com.github.aonagarcia.igu;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.BorderFactory;
import javax.swing.border.TitledBorder;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import com.github.aonagarcia.extras.Instances;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author Administrador
 */
public class ValidateDataPanel extends claseIGU {

    public Variables m_variables = new Variables("Variables");
    public Instances m_data;
    protected showInstances m_tableInstances = new showInstances();
    protected JPanel m_buttonsPanel = new JPanel(new GridLayout(1, 6, 5, 5));
    protected JPanel m_centerPanel = new JPanel(new BorderLayout());
    protected JPanel m_dataTablePanel = new JPanel(new BorderLayout());
    protected JPanel m_rigthPanel = new JPanel(new BorderLayout());
    protected JPanel m_mensajesPanel = new JPanel(new BorderLayout());
    protected JPanel m_aplicarCambiosPanel = new JPanel(new GridLayout(1, 3, 5, 5));
    public    JTextArea m_textArea = new JTextArea(5, 20);
    protected JLabel msgNumRowValidated = new JLabel();
    private JProgressBar progressBar = new JProgressBar();
    public graphPanel mlp = new graphPanel();
    public static int currentVarSelected;

    /** Creates a new instance of ValidateData */
    public ValidateDataPanel() {
        progressBar.setForeground(new Color(0,255,0));
        setLayout(new BorderLayout());
        m_mensajesPanel.setBorder(BorderFactory.createTitledBorder(null, "Resumen de validaci�n", TitledBorder.LEFT, TitledBorder.DEFAULT_POSITION));
        m_mensajesPanel.add(new JScrollPane(m_textArea), BorderLayout.CENTER);

        JPanel graficaPanel = new JPanel(new BorderLayout());
        graficaPanel.add(mlp, BorderLayout.CENTER);
        graficaPanel.setBorder(BorderFactory.createTitledBorder(null, "Gr�fica", TitledBorder.CENTER, TitledBorder.CENTER));


        JPanel leftPanel = new JPanel(new GridLayout(2, 1));
        leftPanel.add(m_variables.getPanel());
        leftPanel.add(m_mensajesPanel);
        m_dataTablePanel.add(m_tableInstances, BorderLayout.CENTER);
        m_dataTablePanel.add(progressBar, BorderLayout.SOUTH);
        
        m_centerPanel.add(m_dataTablePanel,BorderLayout.CENTER);
        m_centerPanel.add(graficaPanel,BorderLayout.SOUTH);

        add(msgNumRowValidated, BorderLayout.NORTH);
        add(m_centerPanel, BorderLayout.CENTER);
        add(leftPanel, BorderLayout.WEST);
        setListasSelectionEvent(m_variables);
    }

    private void setListasSelectionEvent(final Variables _listaVars) {
        _listaVars.m_List.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    ListSelectionModel lm = (ListSelectionModel) e.getSource();
                    int j = 0;
                    for (int i = e.getFirstIndex(); i <= e.getLastIndex(); i++) {
                        if (lm.isSelectedIndex(i)) {
                            j = i;
                            int idx = _listaVars.getVarIndex(i);
                            currentVarSelected = idx;
                            //showVarData(idx);
                            //Establece la variable a graficar en datos/tiempo
                            updateGraphic(idx);
                            break;
                        }
                    }

                    if (!_listaVars.equals(m_variables)) {
                        m_variables.m_List.getSelectionModel().clearSelection();
                    }
                }
            }
        });
    }

    public void updateGraphic(int idx) {
        /*Grafica s�lo variables num�ricas*/
        mlp.resetControls();
        if (m_data.attribute(idx).isNominal()) {
            mlp.plot.setGraficarFlag(false);
            mlp.plot.repaint();
        } else {

            mlp.plot.setGraficarFlag(true);

            int[] arr = {idx};
            mlp.plot.setAttributestoPlot(arr);
            mlp.plot.setLimits(mlp.calcularlimites(arr));

            /*mlp.plot.setSetPoints(ctrl.validationModel.instance(idx).value(ctrl.validationModel.attribute("SPmax")),
                    ctrl.getValidationModel().instance(idx).value(ctrl.getValidationModel().attribute("SPmid")),
                    ctrl.getValidationModel().instance(idx).value(ctrl.getValidationModel().attribute("SPmin")));

                    mlp.plot.setDataRanges(ctrl.validationModel.instance(idx).value(ctrl.getValidationModel().attribute("RangoMax")),
                    ctrl.getValidationModel().instance(idx).value(ctrl.getValidationModel().attribute("RangoMin")));

            double th = ctrl.getValidationModel().instance(idx).value(ctrl.getValidationModel().attribute("Umbral_Cambios"));
            mlp.plot.setCambiosBruscosFlags(ctrl.validarTendencias(idx, th));
*/
            mlp.plot.repaint();

        }
    }

    /*Establece las instancias a validar*/
    public void setInstances(Instances _inst) {
        msgNumRowValidated.setText("");
        m_data = _inst;
        m_tableInstances.setInstances(m_data);
        mlp.setInstances(m_data);
        this.showData(m_data);        
        mlp.plot.repaint();
    }

    public void initTable() {
        m_tableInstances.initTable();
        m_variables.m_List.initTable();
    }

    public void repaintTable() {
        m_tableInstances.repaint();
    }

    /*regresa el objeto de instancias m_data*/
    public Instances getInstances() {
        return m_data;
    }

    private void reportErrorByVar(String varName) {
        m_textArea.append("Variable :" + varName);
        m_textArea.append("\nDF: " + m_data.numMissingData(varName));
        m_textArea.append("\nFR: " + m_data.numOutOfRangeData(varName));
        m_textArea.append("\nCA: " + m_data.numAbruptChanges(varName));
        m_textArea.append("\nVR: " + m_data.numRelatedVariablesErrors(varName));
        m_textArea.append("\n\n");
    }

    private void generalReport() {
        m_textArea.append("\nResumen de validaci�n:\n");
        m_textArea.append("En total se detectaron los siguientes tipos de errores:");
        m_textArea.append("\nDatos faltantes (DF):\n" + m_data.numMissingData());
        m_textArea.append("\nDatos fuera de rango (FR):\n" + m_data.numOutOfRangeData());
        m_textArea.append("\nCambios abruptos (CA):\n" + m_data.numAbruptChanges());
        m_textArea.append("\nErrores detectados de acuerdo a relaci�n de variables (VR):\n" + m_data.numRelatedVariablesErrors());
    }

    public void buildValidationReport(java.util.LinkedList l) {
        try {
            m_textArea.setText("");
            generalReport();
            m_textArea.append("\nResumen de erros por variable:\n\n");
            for (int i = 0; i < l.size(); i++) {
                reportErrorByVar((String) l.get(i));
            }
            m_textArea.setCaretPosition(0);
        } catch (Exception a) {
            System.out.println("A: ");
            a.printStackTrace();
        }
    }

    public void setNumInstances(int r) {
        msgNumRowValidated.setText("Registros validados: " + r);
    }

    public void showData(final Instances inst) {

        try {
            Runnable r = new Runnable() {

                public void run() {
                    m_variables.showVarsNames2010(inst);
                    m_variables.m_List.getSelectionModel().setSelectionInterval(0, 0);
                }
            };

            if (SwingUtilities.isEventDispatchThread()) {
                r.run();
            } else {
                SwingUtilities.invokeAndWait(r);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(ValidateDataPanel.this,
                    "Ocurrio un problema al mostrar los datos:\n"
                    + ex,
                    "Mostrando Datos",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public void deleteVars() {
        int[] s1 = m_variables.getSelectedAttributes();
        int totalElemSel = s1.length;
        if (totalElemSel == 0) {
            JOptionPane.showMessageDialog(ValidateDataPanel.this,
                    "No hay variables seleccionadas!\n",
                    "Remover Variables",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (totalElemSel == m_data.numAttributes()) {
            JOptionPane.showMessageDialog(ValidateDataPanel.this,
                    "No se pueden eliminar todas las variables!\n",
                    "Remover Variables",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        int[] totalSelected = new int[totalElemSel];
        System.arraycopy(s1, 0, totalSelected, 0, s1.length);
        ctrl.cmdDeleteVarsVDP(totalSelected);
    }

    public void initProgressBar(int max)
    {
        progressBar.setIndeterminate(false);
        progressBar.setMinimum(0);
        progressBar.setMaximum(max);
    }

    public void updateProgressBar(int n)
    {
        progressBar.setValue(n);
    }

    public void finishProgressBar()
    {
        progressBar.setValue(0);
        progressBar.setIndeterminate(false);
    }
}
