/*
 * panelRangos.java
 *
 * Created on 9 de julio de 2008, 09:11 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */
package com.github.aonagarcia.igu;

import java.awt.*;
import javax.swing.*;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JSpinner;
import java.awt.BorderLayout;
import javax.swing.JTextField;
import java.awt.event.ActionEvent;
import javax.swing.event.ChangeEvent;
import java.awt.event.ActionListener;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeListener;
import com.github.aonagarcia.extras.Utils;

/**
 *
 * @author Javier
 */
public class panelRangos extends claseIGU {

    private JPanel panelRangoDatos = new JPanel();
    private JPanel panelSetPoints = new JPanel();
    private JPanel panelRangoDatosUser = new JPanel();
    private JPanel panelWaveletsHaarParams = new JPanel(new GridLayout(3, 2));
    private JTextField maxEditR = new JTextField();
    private JTextField minEditR = new JTextField();
    private JTextField MediaEditR = new JTextField();
    private JTextField MedianaEditR = new JTextField();
    private JTextField DesvStndEditR = new JTextField();
    private JTextField DesvStnd_MEditR = new JTextField();
    private JTextField maxEditSP = new JTextField();
    private JTextField minEditSP = new JTextField();
    private JTextField midEditSP = new JTextField();
    private JSpinner maxSpinnerR = new JSpinner();
    private JSpinner minSpinnerR = new JSpinner();
    private JSpinner ThresholdSpinner = new JSpinner();
    private JButton m_saveBttn = new JButton();
    private JButton m_equalLimits2Range = new JButton("<html> Rangos &rarr Lims</html> ");
    private JButton m_equalLimits2RangeAllVars = new JButton("<html> Rangos &rarr Lims*</html> ");
    private JSlider sudenChangesThCtrl = new JSlider(SwingConstants.VERTICAL, 0, 100, 0);
    private JSlider scaleSliderCtrl = new JSlider(SwingConstants.VERTICAL, 0, 10, 0);
    private JTextField scaleVal = new JTextField();
    private JTextField thVal = new JTextField();
    //private JButton m_calcularValsRef=new JButton("Calcular valores de referencia y l�mites");
    private JRadioButton mediaRadioButton = new JRadioButton("Media");
    private JRadioButton medianaRadioButton = new JRadioButton("Mediana");
    private ButtonGroup buttonGroup = new ButtonGroup();
    private JPanel radioButtonsPanel = new JPanel(new GridLayout(1, 2));
    //Objetos que deben ser acutulizados cuando los valores de los rangos son modificados
    private graphics plot;
    private datosVariablePanel dvp;
    private int var = 0;
    private BuildModelPanel bmp;

    /** Creates a new instance of panelRangos */
    public panelRangos(BuildModelPanel _BMP) {
        bmp = _BMP;

        this.setRadioButtons();
        setLayout(new GridLayout(3, 1, 5, 5));

        maxEditR.setEditable(false);
        minEditR.setEditable(false);
        MediaEditR.setEditable(false);
        MedianaEditR.setEditable(false);
        DesvStndEditR.setEditable(false);
        maxEditSP.setEditable(false);
        midEditSP.setEditable(false);
        minEditSP.setEditable(false);
        DesvStnd_MEditR.setEditable(false);


        m_saveBttn.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("guardar2.gif"))));
        panelRangoDatos.setLayout(new GridLayout(3, 4, 0, 5));
        panelRangoDatos.setBorder(BorderFactory.createTitledBorder(null, "Estad�sticas", TitledBorder.CENTER, TitledBorder.CENTER));
        panelRangoDatos.add(new JLabel("M�ximo ", JLabel.RIGHT));
        panelRangoDatos.add(maxEditR);
        panelRangoDatos.add(new JLabel("M�nimo ", JLabel.RIGHT));
        panelRangoDatos.add(minEditR);
        panelRangoDatos.add(new JLabel("Media ", JLabel.RIGHT));
        panelRangoDatos.add(MediaEditR);
        panelRangoDatos.add(new JLabel("\u03c3 ", JLabel.RIGHT));
        panelRangoDatos.add(DesvStndEditR);
        panelRangoDatos.add(new JLabel("Mediana ", JLabel.RIGHT));
        panelRangoDatos.add(MedianaEditR);
        panelRangoDatos.add(new JLabel("\u03c3_m", JLabel.RIGHT));
        panelRangoDatos.add(DesvStnd_MEditR);


        panelSetPoints.setLayout(new GridLayout(3, 3, 10, 1));
        panelSetPoints.setBorder(BorderFactory.createTitledBorder(null, "Valores de referencia", TitledBorder.CENTER, TitledBorder.CENTER));
        panelSetPoints.add(new JLabel("M�nimo ", JLabel.CENTER));
        panelSetPoints.add(new JLabel("Medio ", JLabel.CENTER));
        panelSetPoints.add(new JLabel("M�ximo ", JLabel.CENTER));
        panelSetPoints.add(minEditSP);
        panelSetPoints.add(midEditSP);
        panelSetPoints.add(maxEditSP);
        panelSetPoints.add(new JPanel());
        //panelSetPoints.add(m_calcularValsRef);

        //panelRangoDatosUser.setLayout(new GridLayout(3,3,1,1));
        panelRangoDatosUser.setLayout(new GridLayout(1, 3, 5, 5));



        minSpinnerR.setModel(new SpinnerNumberModel(0.0, null, null, 0.0001));
        minSpinnerR.setEditor(new JSpinner.NumberEditor(minSpinnerR, "###.########"));

        maxSpinnerR.setModel(new SpinnerNumberModel(0.0, null, null, 0.0001));
        maxSpinnerR.setEditor(new JSpinner.NumberEditor(maxSpinnerR, "###.########"));

        ThresholdSpinner.setModel(new SpinnerNumberModel(0.0, null, null, 0.0001));
        ThresholdSpinner.setEditor(new JSpinner.NumberEditor(ThresholdSpinner, "###.########"));

        JPanel labelsRangosUser = new JPanel(new GridLayout(3, 1));

        labelsRangosUser.add(new JLabel(" Superior ", JLabel.RIGHT));
        labelsRangosUser.add(new JLabel(" Inferior ", JLabel.RIGHT));
        //labelsRangosUser.add(new JLabel(" Umbral de cambios",JLabel.RIGHT));
        //labelsRangosUser.add(m_equalLimits2Range);
        labelsRangosUser.add(m_equalLimits2RangeAllVars);


        JPanel ctrlsRangosUser = new JPanel(new GridLayout(3, 1, 10, 10));
        ctrlsRangosUser.add(maxSpinnerR);
        ctrlsRangosUser.add(minSpinnerR);
        //ctrlsRangosUser.add(ThresholdSpinner);
        sudenChangesThCtrl.setMajorTickSpacing(10);
        sudenChangesThCtrl.setPaintTicks(true);

        panelWaveletsHaarParams.add(new JLabel("Escala"));
        panelWaveletsHaarParams.add(new JLabel("Threshold"));
        panelWaveletsHaarParams.add(scaleVal);
        panelWaveletsHaarParams.add(thVal);
        panelWaveletsHaarParams.add(scaleSliderCtrl);
        panelWaveletsHaarParams.add(sudenChangesThCtrl);

        panelRangoDatosUser.add(labelsRangosUser);
        panelRangoDatosUser.add(ctrlsRangosUser);
        m_saveBttn.setPreferredSize(new Dimension(10, 30));
        JPanel dummy1=new JPanel(new BorderLayout());
        dummy1.add(m_saveBttn,BorderLayout.NORTH);
        dummy1.add(new JPanel(),BorderLayout.CENTER);
        dummy1.add(new JPanel(),BorderLayout.SOUTH);
        panelRangoDatosUser.add(dummy1);

        JPanel aux = new JPanel(new BorderLayout());
        aux.setBorder(BorderFactory.createTitledBorder(null, "Configuraci�n de l�mites", TitledBorder.CENTER, TitledBorder.CENTER));
        aux.add(panelRangoDatosUser, BorderLayout.CENTER);
        aux.add(radioButtonsPanel, BorderLayout.NORTH);

        add(panelRangoDatos);
        add(aux);
        add(panelSetPoints);



        ChangeListener listenerMaxSpinner = new ChangeListener() {

            public void stateChanged(ChangeEvent e) {

                Object max = maxSpinnerR.getValue();

                //if (!Double.isNaN((Double)maxSpinnerR.getValue()) && !Double.isNaN((Double)minSpinnerR.getValue()))
                {
                    plot.setUserMaxRange((Double) maxSpinnerR.getValue());
                    dvp.getRender().setOutOfRangeValues((Double) maxSpinnerR.getValue(), (Double) minSpinnerR.getValue());
                    plot.repaint();
                    dvp.repaint();
                }
            }
        };
        maxSpinnerR.addChangeListener(listenerMaxSpinner);

        ChangeListener listenerMinSpinner = new ChangeListener() {

            public void stateChanged(ChangeEvent e) {
                Object min = minSpinnerR.getValue();
                //if (!Double.isNaN((Double)maxSpinnerR.getValue()) && !Double.isNaN((Double)minSpinnerR.getValue()))
                {
                    plot.setUserMinRange((Double) minSpinnerR.getValue());
                    dvp.getRender().setOutOfRangeValues((Double) maxSpinnerR.getValue(), (Double) minSpinnerR.getValue());
                    plot.repaint();
                    dvp.repaint();
                }
            }
        };
        minSpinnerR.addChangeListener(listenerMinSpinner);

        ChangeListener listenerUmbralSpinner = new ChangeListener() {

            public void stateChanged(ChangeEvent e) {
                if (!Double.isNaN((Double) ThresholdSpinner.getValue())) {
                   plot.setCambiosBruscosFlags(ctrl.validarTendencias(var, (Double) ThresholdSpinner.getValue()));
                    plot.repaint();
                }
            }
        };
        ThresholdSpinner.addChangeListener(listenerUmbralSpinner);

        m_saveBttn.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                ctrl.SetUserLimits(var, (Double) maxSpinnerR.getValue(), (Double) minSpinnerR.getValue(), (Double) ThresholdSpinner.getValue());
                bmp.updateGraphic(var);
            }
        });

        m_equalLimits2Range.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                maxSpinnerR.setValue(Double.valueOf(maxEditR.getText()));
                minSpinnerR.setValue(Double.valueOf(minEditR.getText()));
                //ctrl.SetUserLimits(var,Double.valueOf(maxEditR.getText()),Double.valueOf(minEditR.getText()),(Double)ThresholdSpinner.getValue());
            }
        });

        m_equalLimits2RangeAllVars.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                for(int i=0;i<ctrl.validationModel.numInstances();i++)
                {
                    ctrl.SetUserLimits(i,ctrl.validationModel.instance(i).value(ctrl.validationModel.attribute("RangoMax")) ,ctrl.validationModel.instance(i).value(ctrl.validationModel.attribute("RangoMin")) , -1);
                }
                //ctrl.SetUserLimits(var,Double.valueOf(maxEditR.getText()),Double.valueOf(minEditR.getText()),(Double)ThresholdSpinner.getValue());
            }
        });

        /*m_calcularValsRef.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent e) {
        ctrl.calacularLimites();
        ctrl._igu.BuildModel.showVarData(0);
        }
        });*/

        scaleSliderCtrl.addChangeListener(new ChangeListener() {

            public void stateChanged(ChangeEvent e) {
                scaleVal.setText("" + scaleSliderCtrl.getValue());
                plot.setCambiosBruscosFlags(ctrl.validarTendenciasHaar(var, scaleSliderCtrl.getValue(), sudenChangesThCtrl.getValue()));
                plot.repaint();

            }
        });

        sudenChangesThCtrl.addChangeListener(new ChangeListener() {

            public void stateChanged(ChangeEvent e) {
                thVal.setText("" + sudenChangesThCtrl.getValue());
                plot.setCambiosBruscosFlags(ctrl.validarTendenciasHaar(var, scaleSliderCtrl.getValue(), sudenChangesThCtrl.getValue()));
                plot.repaint();

            }
        });


    }

    public void setRadioButtons() {
        medianaRadioButton.setSelected(true);
        ActionListener actionListener = new ActionListener() {

            public void actionPerformed(ActionEvent actionEvent) {
                AbstractButton aButton = (AbstractButton) actionEvent.getSource();
                boolean selected = aButton.getModel().isSelected();
                ctrl.calacularLimites(actionEvent.getActionCommand());
                ctrl._igu.BuildModel.showVarData(BuildModelPanel.currentVarSelected);

                plot.setUserMinRange((Double) minSpinnerR.getValue());
                plot.setUserMaxRange((Double) maxSpinnerR.getValue());
                dvp.getRender().setOutOfRangeValues((Double) maxSpinnerR.getValue(), (Double) minSpinnerR.getValue());

                plot.repaint();
                dvp.repaint();

                //ctrl._igu.BuildModel.updateGraphic(BuildModelPanel.currentVarSelected);
            }
        };
        mediaRadioButton.addActionListener(actionListener);
        medianaRadioButton.addActionListener(actionListener);

        buttonGroup.add(mediaRadioButton);
        buttonGroup.add(medianaRadioButton);
        radioButtonsPanel.add(mediaRadioButton);
        radioButtonsPanel.add(medianaRadioButton);

    }

    public void setDataRanges(int var, double max, double min, double media, double mediana, double ds, double ds_m) {
        this.var = var;

        maxEditR.setText(Double.isNaN(max) ? " " : Utils.doubleToString(max, 5));
        minEditR.setText(Double.isNaN(min) ? " " : Utils.doubleToString(min, 5));
        MediaEditR.setText(Double.isNaN(media) ? " " : Utils.doubleToString(media, 5));
        MedianaEditR.setText(Double.isNaN(mediana) ? " " : Utils.doubleToString(mediana, 5));
        DesvStndEditR.setText(Double.isNaN(ds) ? " " : Utils.doubleToString(ds, 5));
        DesvStnd_MEditR.setText(Double.isNaN(ds_m) ? " " : Utils.doubleToString(ds_m, 5));
    }

    public void setSPTol(int var, double max, double min) {
        this.var = var;
        if (!Double.isNaN(max)) {
            maxSpinnerR.setValue(max);
        } else {
            maxSpinnerR.setValue(Double.NaN);
        }
        if (!Double.isNaN(min)) {
            minSpinnerR.setValue(min);
        } else {
            minSpinnerR.setValue(Double.NaN);
        }
    }

    public void setUmbralCambios(double th) {
        ThresholdSpinner.setValue(th);
    }

    public void setSetPoints(int var, double min, double mid, double max) {
        minEditSP.setText(Double.isNaN(min) ? " " : Utils.doubleToString(min, 5));
        midEditSP.setText(Double.isNaN(mid) ? " " : Utils.doubleToString(mid, 5));
        maxEditSP.setText(Double.isNaN(max) ? " " : Utils.doubleToString(max, 5));
    }

    public void setObservers(final graphics plot, final datosVariablePanel dvp) {
        this.plot = plot;
        this.dvp = dvp;
    }

    public void setEnableComponents(boolean b) {
        maxSpinnerR.setEnabled(b);
        minSpinnerR.setEnabled(b);
        ThresholdSpinner.setEnabled(b);
        m_saveBttn.setEnabled(b);
        medianaRadioButton.setEnabled(b);
        mediaRadioButton.setEnabled(b);
        //m_calcularValsRef.setEnabled(b);
    }
}
