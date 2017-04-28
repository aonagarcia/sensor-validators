package com.github.aonagarcia.igu;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.BorderFactory;
import javax.swing.border.TitledBorder;

import com.github.aonagarcia.extras.Instances;
import com.github.aonagarcia.extras.AttributeVisualizationPanel;


public class BuildModelPanel extends claseIGU {

    public Variables m_variables = new Variables("Variables");
    protected panelRangos m_panelRangos = new panelRangos(this);
    public datosVariablePanel dvp = new datosVariablePanel();
    public intervalosPanel idp = new intervalosPanel();
    public graphPanel mlp = new graphPanel();
    public boolean flag = false;
    public static int currentVarSelected;
    public AttributeVisualizationPanel m_histo = new AttributeVisualizationPanel();

    public BuildModelPanel() {
        
        setLayout(new GridLayout(2, 1, 0, 0));
        JPanel panelSuperior = new JPanel(new GridLayout(1, 3, 10, 10));

        GridBagLayout gridbag = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
        JPanel panelInferior = new JPanel(gridbag);
        c.fill = GridBagConstraints.BOTH;
        c.weighty = 1.0;

        panelSuperior.add(m_variables.getPanel());
        panelSuperior.add(m_panelRangos);
        panelSuperior.add(dvp);

        JPanel HistoPanel = new JPanel(new BorderLayout());
        HistoPanel.add(m_histo, BorderLayout.CENTER);
        HistoPanel.setBorder(BorderFactory.createTitledBorder(null, "Histograma", TitledBorder.CENTER, TitledBorder.CENTER));
        HistoPanel.setPreferredSize(new Dimension(100, 200));
        HistoPanel.add(idp, BorderLayout.WEST);

        JPanel graficaPanel = new JPanel(new BorderLayout());
        graficaPanel.add(mlp, BorderLayout.CENTER);
        graficaPanel.setBorder(BorderFactory.createTitledBorder(null, "Gr�fica", TitledBorder.CENTER, TitledBorder.CENTER));

        c.weightx = 0.85;
        c.gridwidth = GridBagConstraints.RELATIVE;
        gridbag.setConstraints(HistoPanel, c);
        panelInferior.add(HistoPanel);

        c.weightx = 1.15;
        c.gridwidth = GridBagConstraints.REMAINDER;
        gridbag.setConstraints(graficaPanel, c);
        panelInferior.add(graficaPanel);

        setListasSelectionEvent(m_variables);
        setSelectionRowEvent();

        add(panelSuperior);
        add(panelInferior);

        enableIGUCtrls(false);

        m_panelRangos.setObservers(mlp.plot, dvp);
    }

    public void enableIGUCtrls(boolean b) {
        m_panelRangos.setEnableComponents(b);
        mlp.setEnableComponents(b);

    }

    public void setVarsCheckBox(int[] idxs) {
        m_variables.setSelectedAttributes(idxs);
    }

    public void showData(final Instances inst) {
        if (flag == false) {
            setInstancesForPlot(ctrl.BD);
            flag = true;
        }
        try {
            Runnable r = new Runnable() {

                public void run() {
                    m_variables.showVarsNames2010(inst);
                    m_histo.setInstances(inst);
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
            JOptionPane.showMessageDialog(this,
                    "Ocurrio un problema al mostrar los datos:\n"
                    + ex,
                    "Mostrando Datos",
                    JOptionPane.ERROR_MESSAGE);
        }
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
                            showVarData(idx);
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


    private void setSelectionRowEvent() {
        dvp.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {                
                if (!e.getValueIsAdjusting()) {
                    ListSelectionModel lm = (ListSelectionModel) e.getSource();
                    int j = 0;
                    for (int i = e.getFirstIndex(); i <= e.getLastIndex(); i++) {
                        if (lm.isSelectedIndex(i)) {
                                dvp.showMttosInfo(i + 1);
                                highlightSelectedPoint(i);
                            break;
                        }
                    }
                }

            }
        });
    }

    private void highlightSelectedPoint(int i) {
            mlp.plot.highlightSelectedPoint(i);
            mlp.plot.repaint();
    }

    public void fillMissingData() {
        ctrl.cmdFillMissingData2();
        showVarData(currentVarSelected);
        updateGraphic(currentVarSelected);
        dvp.repaintTable();
    }

    public void deleteVars() {
        int[] s1 = m_variables.getSelectedAttributes();
        int totalElemSel = s1.length;
        if (totalElemSel == 0) {
            JOptionPane.showMessageDialog(BuildModelPanel.this,
                    "No hay variables seleccionadas!\n",
                    "Remover Variables",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (totalElemSel == ctrl.BD.numAttributes()) {
            JOptionPane.showMessageDialog(BuildModelPanel.this,
                    "No se pueden eliminar todas las variables!\n",
                    "Remover Variables",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        int[] totalSelected = new int[totalElemSel];
        System.arraycopy(s1, 0, totalSelected, 0, s1.length);
        ctrl.cmdDeleteVars(totalSelected);
    }

    public void selectAllAction() {
        m_variables.m_List.m_Model.includeAll();
    }

    public void selectNothingAction() {
        m_variables.m_List.m_Model.removeAll();
    }

    public void selectInvertAction() {
        m_variables.m_List.m_Model.invert();
    }

    public void setInstancesForPlot(Instances inst) {
        ctrl.BDforPlot = null;
        ctrl.BDforPlot = new Instances(inst);
        mlp.setInstances(ctrl.BDforPlot);
        mlp.plot.setCambiosBruscosFlags(new boolean[inst.numInstances()]);
        mlp.plot.repaint();
    }

    public int[] getSelectedAttributes() {
        int[] s1 = m_variables.getSelectedAttributes();
        int totalElemSel = s1.length;
        int[] totalSelected = new int[totalElemSel];
        System.arraycopy(s1, 0, totalSelected, 0, s1.length);
        return totalSelected;
    }

    public Object[] AttToArray(int idx) {
        Object[] data = new Object[ctrl.BDforPlot.numInstances()];

        for (int k = 0; k < ctrl.BDforPlot.numInstances(); k++) {
            if (ctrl.BDforPlot.instance(k).isMissing(idx)) {
                data[k] = (Object) null;
            } else if (ctrl.BDforPlot.attribute(idx).isNominal()) {
                data[k] = (Object) ctrl.BDforPlot.instance(k).stringValue(idx);
            } else if (ctrl.BDforPlot.attribute(idx).isNumeric()) {
                data[k] = (Object) ctrl.BDforPlot.instance(k).value(idx);
            }
        }
        return data;
    }

    public void showVarData(int idx) {

        Object[] data = AttToArray(idx);

        dvp.setAttributeData(data);


        m_panelRangos.setDataRanges(idx, ctrl.validationModel.instance(idx).value(ctrl.validationModel.attribute("RangoMax")),
                ctrl.validationModel.instance(idx).value(ctrl.validationModel.attribute("RangoMin")),
                ctrl.validationModel.instance(idx).value(ctrl.validationModel.attribute("Media")),
                ctrl.validationModel.instance(idx).value(ctrl.validationModel.attribute("Mediana")),
                ctrl.validationModel.instance(idx).value(ctrl.validationModel.attribute("DesvStndMedia")),
                ctrl.validationModel.instance(idx).value(ctrl.validationModel.attribute("DesvStndMediana")));

        double spmin = ctrl.validationModel.instance(idx).value(ctrl.validationModel.attribute("SPmin"));
        double spmid = ctrl.validationModel.instance(idx).value(ctrl.validationModel.attribute("SPmid"));
        double spmax = ctrl.validationModel.instance(idx).value(ctrl.validationModel.attribute("SPmax"));
        m_panelRangos.setSetPoints(idx, spmin, spmid, spmax);

        mlp.plot.setSetPoints(spmax, spmid, spmin);

        m_panelRangos.setSPTol(idx, ctrl.validationModel.instance(idx).value(ctrl.validationModel.attribute("SPmaxTol")), ctrl.validationModel.instance(idx).value(ctrl.validationModel.attribute("SPminTol")));
        //if (ctrl.BD.attribute(idx).isDiscretized() || (!ctrl.isVarOfValidGroup(idx)))
        if (ctrl.BD.attribute(idx).isDiscretized()) {

            m_panelRangos.setEnableComponents(false);
            ctrl._igu.enableDiscretizeCtrls(false);
            Object[] m_intervalos = new Object[ctrl.BD.attributeStats(idx).nominalCounts.length];
            for (int intervalo = 0; intervalo < m_intervalos.length; intervalo++) {
                m_intervalos[intervalo] = ctrl.BD.attribute(idx).value(intervalo);
            }
            idp.setAttributeData(m_intervalos);

        } else {
            m_panelRangos.setEnableComponents(true);
            ctrl._igu.enableDiscretizeCtrls(true);
            idp.clear();
        }
        m_panelRangos.setUmbralCambios(ctrl.getValidationModel().instance(idx).value(ctrl.getValidationModel().attribute("Umbral_Cambios")));
        //Establece la variable a graficar en el histograma
        m_histo.setColoringIndex(-1);
        //if (ctrl.BD.attribute(idx).isDiscretized())
        m_histo.setAttribute(idx);

    }

    public void updateGraphic(int idx) {
        /*Grafica s�lo variables num�ricas*/
        mlp.resetControls();
        if (ctrl.BDforPlot.attribute(idx).isNominal()) {
            mlp.plot.setGraficarFlag(false);
            mlp.plot.repaint();
        } else {

            mlp.plot.setGraficarFlag(true);

            int[] arr = {idx};
            mlp.plot.setAttributestoPlot(arr);
            mlp.plot.setLimits(mlp.calcularlimites(arr));

            mlp.plot.setSetPoints(ctrl.validationModel.instance(idx).value(ctrl.validationModel.attribute("SPmax")),
                    ctrl.getValidationModel().instance(idx).value(ctrl.getValidationModel().attribute("SPmid")),
                    ctrl.getValidationModel().instance(idx).value(ctrl.getValidationModel().attribute("SPmin")));

                    mlp.plot.setDataRanges(ctrl.validationModel.instance(idx).value(ctrl.getValidationModel().attribute("RangoMax")),
                    ctrl.getValidationModel().instance(idx).value(ctrl.getValidationModel().attribute("RangoMin")));

            //double th = ctrl.getValidationModel().instance(idx).value(ctrl.getValidationModel().attribute("Umbral_Cambios"));
            //mlp.plot.setCambiosBruscosFlags(ctrl.validarTendencias(idx, th));

            mlp.plot.repaint();

        }
    }

    void DWTSettings() {
        ctrl.computeThresholdWH();
        int maxSc = ctrl.changesDetection.getMaxScale(currentVarSelected);
        double maxTh = ctrl.changesDetection.getMaxTh(currentVarSelected, 0);
        DWTSettings dwts = new DWTSettings(currentVarSelected, maxSc, (int)maxTh, ctrl, mlp.plot);
        dwts.setVisible(true);
    }
}
