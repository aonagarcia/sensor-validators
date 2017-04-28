/*
 * principal.java
 *
 * Created on 16 de julio de 2008, 12:58
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 *
 *Esta clase integra las tres pesta?as del sistema, la barra de men? y en general los elementos
 *de interfaz comunes de la aplicaci?n.
 *
 *Las peticiones hechas por el usuario a traves de la intrefaz son dirigidas al com.github.aonagarcia.svd para ser procesadas.
 *Es decir, la interfaz no realiza ningun procesamiento de los datos, ?nicamente recibe ordenes del usuario y
 *estas son procesadas por el sistema de control com.github.aonagarcia.svd, de igual forma el com.github.aonagarcia.svd envia instrucciones a la interfaz
 *para mostrar informaci?n.
 */
package com.github.aonagarcia.igu;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Iterator;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JToolBar;
import javax.swing.UIManager;
import javax.swing.border.Border;
import com.github.aonagarcia.extras.Instances;

import java.util.Vector;
import javax.swing.JDialog;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author Administrador
 */
public class IGUPanel extends claseIGU {

    public JTabbedPane m_Tabs = new JTabbedPane();
    private JPanel menuYlogos = new JPanel();
    private javax.swing.JMenu ActionsMenu;
    private javax.swing.JMenu DiscretizarMenu;
    private javax.swing.JMenuItem DiscretizeConf;
    private javax.swing.JMenuItem DiscretizeEqDist;
    private javax.swing.JMenuItem DiscretizeEqFrec;
    private javax.swing.JMenuItem AbruptChangeDetection;
    private javax.swing.JMenuItem LocalThresholdValidation;
    

    private javax.swing.JMenuItem DeleteAllInstancesWithMttos;
    private javax.swing.JMenuItem DeleteSelectedInstancesWithMttos;
    private javax.swing.JMenuItem DeleteDataWithMttos;
    private javax.swing.JMenuItem DiscretizeMixGauss;
    private javax.swing.JMenuItem EditDeleteRegs;
    private javax.swing.JMenuItem EditDeleteVars;
    private javax.swing.JMenu EditMenu;
    private javax.swing.JMenuItem EditRestoreVars;
    private javax.swing.JMenuItem FileOpenCsv;
    private javax.swing.JMenuItem FileLoadMttosFile;
    private javax.swing.JMenuItem FileOpenRefsVals;
    private javax.swing.JMenuItem FileSaveCsv;
    private javax.swing.JMenuItem FileSaveDiscretized;
    private javax.swing.JMenuItem ModelBuild;
    private javax.swing.JMenu ModelMenu;
    private javax.swing.JMenuItem ModelShow;
    private javax.swing.JMenuItem ModelSave;
    private javax.swing.JMenuItem ModelLoadRestrictionFile;
    private javax.swing.JMenuItem ValidateConf;
    private javax.swing.JMenuItem ValidateLoadModel;
    private javax.swing.JMenu ValidateMenu;
    private javax.swing.JMenuItem ValidateStart;
    private javax.swing.JMenuItem ValidateStop;
    private javax.swing.JMenu ValidateCorregirMenu;
    private javax.swing.JMenuItem ApplyAll;
    private javax.swing.JMenuItem ApplyRows;
    private javax.swing.JMenuItem ApplyCells;
    private javax.swing.JMenuItem ApplyMissingType;
    private javax.swing.JMenuItem ApplyOutOfRangeType;
    private javax.swing.JMenuItem ApplyAbruptChangesType;
    private javax.swing.JMenuItem ApplyRelatedErrorType;   
    private javax.swing.JMenu HelpMenu;
    private javax.swing.JMenuItem HelpAbout;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JPopupMenu.Separator jSeparator4;
    private javax.swing.JPopupMenu.Separator jSeparator6;
    private javax.swing.JPopupMenu.Separator jSeparator8;
    private javax.swing.JPopupMenu.Separator jSeparator9;
    private javax.swing.JMenuBar menuBar;
     /*Panel de la pesta?a Construir Modelo*/
    public BuildModelPanel BuildModel = new BuildModelPanel();
    /*Panel de la pesta?a Validar Datos*/
    public ValidateDataPanel ValidateData = new ValidateDataPanel();
    /*Objeto para mostrar mensaje en la parte inferior de la interfaz*/
    private LogAndMsgs m_logAndMsgs = new LogAndMsgs();
    private JToolBar m_ToolBar = new JToolBar();
    private JButton m_openCSV = new JButton();
    private JButton m_openCVSRefVals = new JButton();
    private JButton m_removeVars = new JButton();
    public JButton m_restoreVars = new JButton();
    private JButton m_eliminarRegsBttn = new JButton();
    private JButton m_deleteDataOutRange = new JButton();
    private JButton m_fillMissingData = new JButton();
    private JButton m_loadRestrictionsFile = new JButton();
    private JButton m_buildModel = new JButton();
    private JButton m_viewModel = new JButton();
    private JButton m_discretize = new JButton();
    private JButton m_saveDiscretizeData = new JButton();
    private JButton m_DiscretizeConf = new JButton();
    private JButton m_loadData2Validate = new JButton();
    private JButton m_showConfParams = new JButton();
    private JButton m_validate = new JButton();
    private JButton m_stopProcess = new JButton();
    private JButton m_saveValidatedData = new JButton();
    private JButton m_applyAll = new JButton();
    private JButton m_applyRows = new JButton();
    private JButton m_applyCells = new JButton();
    private JButton m_delRows = new JButton();
    private String opciones[] = {"Equidistancia", "Frecuencia", "Mezcla de Gaussianas", "Personalizada..."};
    private JComboBox listaAlgoritmos = new JComboBox(opciones);
    private discretizeParameters m_discParamsDlg;
    Vector botones = new Vector();
    public stateObserver _stateObserver;
    public int tabPaneFocus = 0;
    public PanelUmbralesLocales UL;
    private JDialog aboutBox;
    

    /** Creates a new instance of principal */
    public IGUPanel() {
        Border compound;
        Border raisedbevel, loweredbevel;
        raisedbevel = BorderFactory.createRaisedBevelBorder();
        loweredbevel = BorderFactory.createLoweredBevelBorder();
        compound = BorderFactory.createCompoundBorder(raisedbevel, loweredbevel);
        this.setBorder(compound);


        makeToolBar();
        setMenu();

        //Asigna al miembro "ctrl" heredado de claseIGU la interfaz a la cual enviar? mensajes
        ctrl.setIGU(this);
        this.setLayout(new BorderLayout());
        //dp.setBackground(new Color(0,0,0));
        menuYlogos.setLayout(new BorderLayout());
        menuYlogos.add(menuBar, BorderLayout.NORTH);
        menuYlogos.add(m_ToolBar, BorderLayout.SOUTH);

        //2011 m_Tabs.add("Cargar Datos",LoadData);
        m_Tabs.add("Construcci�n de modelo", BuildModel);
        m_Tabs.add("Validaci�n de datos", ValidateData);

        m_Tabs.setEnabledAt(0, true);
        m_Tabs.setEnabledAt(1, true);

        m_Tabs.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent evt) {
                JTabbedPane pane = (JTabbedPane) evt.getSource();
                tabPaneFocus = pane.getSelectedIndex();
                _stateObserver.setStateTab(tabPaneFocus);
            }
        });


        //disableBttns();
        add(menuYlogos, BorderLayout.NORTH);
        //add(m_ToolBar,BorderLayout.NORTH);
        add(m_Tabs, BorderLayout.CENTER);
        //add(dp, BorderLayout.CENTER);
        add(this.m_logAndMsgs, BorderLayout.SOUTH);
        _stateObserver = new stateObserver(botones);
        _stateObserver.setStateBuildModel(0);
    }

    private void makeToolBar() {
        m_ToolBar.setFloatable(false);

        botones.add(m_openCSV);
        m_openCSV.setName("m_openCSV");
        m_openCSV.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("loadData.gif"))));
        m_openCSV.setToolTipText("Cargar archivo de datos (csv).");
        m_openCSV.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                m_openCSV_ActionPerformed(evt);

            }
        });

        /*botones.add(m_openCSVOriginal);
        m_openCSVOriginal.setName("m_openCSVOriginal");
        m_openCSVOriginal.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("resources/loadData.gif"))));
        m_openCSVOriginal.setToolTipText("Abrir archivo de datos Original(csv).");
        m_openCSVOriginal.addActionListener(new java.awt.event.ActionListener() {

        public void actionPerformed(java.awt.event.ActionEvent evt) {
        m_openCSVOriginal_ActionPerformed(evt);

        }
        });
         * 
         */

        botones.add(m_openCVSRefVals);
        m_openCVSRefVals.setName("m_openCVSRefVals");
        m_openCVSRefVals.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("valRef2.gif"))));
        //m_openCVSRefVals.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("resources/icono.JPG"))));
        m_openCVSRefVals.setToolTipText("Abrir archivo de valores de referencia (csv).");
        m_openCVSRefVals.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                openCSVRefVals_ActionPerformed(evt);
                //BuildModel.DWTSettings();
                //openPUL();

            }
        });

        botones.add(m_removeVars);
        m_removeVars.setName("m_removeVars");
        m_removeVars.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("deleteVar2.gif"))));
        //m_removeVars.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("resources/icono.JPG"))));
        m_removeVars.setToolTipText("Eliminar las variables seleccionadas.");
        m_removeVars.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                if (m_Tabs.getSelectedIndex() == 0) {
                    //if (dp.getSelectedFrame().getName().equalsIgnoreCase("buildModel")) {
                    BuildModel.deleteVars();

                }
                if (m_Tabs.getSelectedIndex() == 1) {
                    //if (dp.getSelectedFrame().getName().equalsIgnoreCase("validateData")) {
                    ValidateData.deleteVars();


                }
            }
        });

        /*botones.add(m_markOutliers);
        m_markOutliers.setName("m_markOutliers");
        m_markOutliers.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("resources/restoreVar.gif"))));
        //m_restoreVars.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("resources/icono.JPG"))));
        m_markOutliers.setToolTipText("Marcar en la estructura de datos los outliers.");
        m_markOutliers.addActionListener(new java.awt.event.ActionListener() {

        public void actionPerformed(java.awt.event.ActionEvent evt) {
        ctrl.markOutliers(BuildModel.getSelectedAttributes());
        }
        });
         * ?
         */

        botones.add(m_restoreVars);
        m_restoreVars.setName("m_restoreVars");
        m_restoreVars.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("restoreVar2.gif"))));
        //m_restoreVars.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("resources/icono.JPG"))));
        m_restoreVars.setToolTipText("Restaurar los datos de las variables seleccionadas.");
        m_restoreVars.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                ctrl.restoreVars(BuildModel.getSelectedAttributes());
            }
        });


        botones.add(m_eliminarRegsBttn);
        m_eliminarRegsBttn.setName("m_eliminarRegsBttn");
        m_eliminarRegsBttn.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("deleteRows2.gif"))));
        m_eliminarRegsBttn.setToolTipText("Eliminar registros");
        m_eliminarRegsBttn.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                deleteRows(e);
            }
        });

        botones.add(m_deleteDataOutRange);
        m_deleteDataOutRange.setName("m_deleteDataOutRange");
        m_deleteDataOutRange.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("outRange.gif"))));
        m_deleteDataOutRange.setToolTipText("Eliminar los valores fuera de rango de las variables seleccionadas.");
        m_deleteDataOutRange.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                ctrl.cmdRemoveDataOutOfRange();
                //ctrl.discretizaPablo();

            }
        });

        botones.add(m_fillMissingData);
        m_fillMissingData.setName("m_fillMissingData");
        m_fillMissingData.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("m_fillMissingData.gif"))));
        //m_fillMissingData.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("resources/icono.JPG"))));
        m_fillMissingData.setToolTipText("Llenar los datos faltantes de las variables seleccionadas.");
        m_fillMissingData.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                BuildModel.fillMissingData();
            }
        });

        //botones.add(this.listaAlgoritmos);

        botones.add(m_loadRestrictionsFile);
        m_loadRestrictionsFile.setName("m_loadRestrictionsFile");
        m_loadRestrictionsFile.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("restricciones.gif"))));
        m_loadRestrictionsFile.setToolTipText("Cargar archivo de reestricciones.");
        m_loadRestrictionsFile.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                ctrl.cmdCargarRestricciones();
            }
        });

        botones.add(m_buildModel);
        m_buildModel.setName("m_buildModel");
        m_buildModel.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("buildModel.gif"))));
        m_buildModel.setToolTipText("Construir modelo.");
        m_buildModel.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                ctrl.cmdBuildBayesNet();
            }
        });

        botones.add(m_viewModel);
        m_viewModel.setName("m_viewModel");
        m_viewModel.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("viewModel.gif"))));
        m_viewModel.setToolTipText("Ver modelo.");
        m_viewModel.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                /*if (ctrl.structDN==null)
                ctrl.enviarPeticionStructDN();
                else*/
                ctrl.drawDN();
            }
        });

        botones.add(m_discretize);
        m_discretize.setName("m_discretize");
        m_discretize.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("discretizarVar.gif"))));
        m_discretize.setToolTipText("Discretizar las variables seleccionadas.");
        m_discretize.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                ctrl.cmdDiscretize(listaAlgoritmos.getSelectedIndex());
            }
        });

        botones.add(m_DiscretizeConf);
        m_DiscretizeConf.setName("m_DiscretizeConf");
        m_DiscretizeConf.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("DiscretizeConf.gif"))));
        m_DiscretizeConf.setToolTipText("Configurar par?metros de discretizaci?n.");
        m_DiscretizeConf.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                m_discParamsDlg = new discretizeParameters((String) listaAlgoritmos.getItemAt(listaAlgoritmos.getSelectedIndex()), ctrl.discretizar);
            }
        });

        botones.add(m_saveDiscretizeData);
        m_saveDiscretizeData.setName("m_saveDiscretizeData");
        m_saveDiscretizeData.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("saveDiscretizeData.gif"))));
        m_saveDiscretizeData.setToolTipText("Guardar datos discretizados.");
        m_saveDiscretizeData.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                ctrl.saveBD(ctrl.BD);
            }
        });

        botones.add(m_loadData2Validate);
        m_loadData2Validate.setName("m_loadData2Validate");
        m_loadData2Validate.setBorderPainted(false);
        m_loadData2Validate.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("icono.JPG"))));
        m_loadData2Validate.setToolTipText("Cargar datos a validar (csv).");
        m_loadData2Validate.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                openCSV2Validate_ActionPerformed(evt);
            }
        });



        botones.add(m_showConfParams);
        m_showConfParams.setName("m_showConfParams");
        m_showConfParams.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("modelConf.gif"))));
        //m_validate.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("resources/icono.JPG"))));
        m_showConfParams.setToolTipText("Configuraci?n de par?metros.");
        m_showConfParams.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                ctrl.showModelParams();
            }
        });

        botones.add(m_validate);
        m_validate.setName("m_validate");
        m_validate.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("validar.gif"))));
        //m_validate.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("resources/icono.JPG"))));
        m_validate.setToolTipText("Iniciar proceso de validaci?n.");
        m_validate.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                ctrl.cmdValidarBD();
            }
        });

        botones.add(m_stopProcess);
        m_stopProcess.setName("m_stopProcess");
        m_stopProcess.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("stopProcess.gif"))));
        //m_stopProcess.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("resources/icono.JPG"))));
        m_stopProcess.setToolTipText("Detener proceso de validaci?n.");
        m_stopProcess.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                ctrl.stopValidationProcess();
            }
        });

        botones.add(m_saveValidatedData);
        m_saveValidatedData.setName("m_saveValidatedData");
        m_saveValidatedData.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("saveValidateData.gif"))));
        m_saveValidatedData.setToolTipText("Guardar datos validados.");
        m_saveValidatedData.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                ctrl.saveBD(ValidateData.m_data);
            }
        });

        botones.add(m_applyAll);
        m_applyAll.setName("m_applyAll");
        m_applyAll.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("applyAll.gif"))));
        m_applyAll.setToolTipText("Aplicar todos los cambios.");
        m_applyAll.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                ctrl.applyChangesToAllData();
            }
        });

        botones.add(m_applyRows);
        m_applyRows.setName("m_applyRows");
        m_applyRows.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("applyRows.gif"))));
        m_applyRows.setToolTipText("Aplicar a filas seleccionadas.");
        m_applyRows.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                int[] sel = ValidateData.m_tableInstances.getSelectedAttributes();
                ctrl.applyChangesToSelectedRow(sel);
            }
        });

        botones.add(m_applyCells);
        m_applyCells.setName("m_applyCells");
        m_applyCells.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("applyCells.gif"))));
        m_applyCells.setToolTipText("Aplicar a celdas marcadas.");
        m_applyCells.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                ctrl.applyChangesToSelectedData();
            }
        });

        /*botones.add(m_applyErrorType);
        m_applyErrorType.setName("m_applyErrorType");
        m_applyErrorType.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("resources/icono.JPG"))));
        m_applyErrorType.setToolTipText("Aplicar a tipo de error.");
        m_applyErrorType.addActionListener(new java.awt.event.ActionListener() {

        public void actionPerformed(java.awt.event.ActionEvent evt) {
        //1 Datos faltantes
        //2 Datos fuera de rango
        //3 cambios abruptos
        //4 Redes Bayesianas
        ctrl.applyChangesToErrorType(2);
        ctrl.saveHistorial("D:\\Personal\\Pemex\\Validator\\validator\\validadosMod4Hist.csv");
        //ctrl.stopValidationProcess();
        }
        });
         * 
         */

        //botones.add(m_delRows);
        m_delRows.setName("m_delRows");
        m_delRows.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("icono.JPG"))));
        m_delRows.setToolTipText("Eliminar filas seleccionadas.");
        m_delRows.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
            }
        });

        Iterator it = botones.iterator();
        while (it.hasNext()) {
            try{
            JButton temp = (JButton) it.next();
            temp.setBorderPainted(false);
            }
            catch (ClassCastException e)
            {
                ;
            }
            
        }

        //m_openCSV.setEnabled(true);

        m_ToolBar.add(m_openCSV);
        //m_ToolBar.add(m_openCSVOriginal);
        m_ToolBar.add(m_openCVSRefVals);
        m_ToolBar.add(new JToolBar.Separator());
        m_ToolBar.add(m_removeVars);
        //m_ToolBar.add(m_markOutliers);
        m_ToolBar.add(m_restoreVars);
        m_ToolBar.add(m_eliminarRegsBttn);
        m_ToolBar.add(new JToolBar.Separator());
        m_ToolBar.add(m_deleteDataOutRange);
        m_ToolBar.add(m_fillMissingData);
        m_ToolBar.add(new JToolBar.Separator());
        
        m_ToolBar.add(listaAlgoritmos);
        m_ToolBar.add(m_discretize);
        m_ToolBar.add(m_DiscretizeConf);
        m_ToolBar.add(m_saveDiscretizeData);
        m_ToolBar.add(new JToolBar.Separator());
        
        m_ToolBar.add(m_buildModel);
        m_ToolBar.add(m_viewModel);
        m_ToolBar.add(new JToolBar.Separator());

        m_ToolBar.add(m_showConfParams);
        m_ToolBar.add(m_validate);
        m_ToolBar.add(m_stopProcess);
        m_ToolBar.add(m_saveValidatedData);
        m_ToolBar.add(new JToolBar.Separator());
        m_ToolBar.add(m_applyAll);
        m_ToolBar.add(m_applyRows);
        m_ToolBar.add(m_applyCells);
        //m_ToolBar.add(m_applyErrorType);
        //m_ToolBar.add(m_delRows);
    }

    private void m_openCSV_ActionPerformed(ActionEvent evt) {
        UIManager.put("FileChooser.fileNameLabelText", "Nombre del archivo");
        JFileChooser m_FileChooser = new JFileChooser(new File(System.getProperty("user.dir")));
        int returnVal = m_FileChooser.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            final File selected = m_FileChooser.getSelectedFile();

            if (m_Tabs.getSelectedIndex() == 0) {
                //if (dp.getSelectedFrame().getName().equalsIgnoreCase("buildModel")) {
                ctrl.cmdLoadData(selected);
                //buildModelinternalFrame.setTitle("Construir modelo de validaci?n: " + selected);
            }
            if (m_Tabs.getSelectedIndex() == 1) {
                //if (dp.getSelectedFrame().getName().equalsIgnoreCase("validateData")) {
                ctrl.cmdLoadData2Validate(selected);
                //validateDataInternalFrame.setTitle("Validar Datos: " + selected);


            }
        }
    }

    //06-Sep
    /*
    private void m_openCSVOriginal_ActionPerformed(java.awt.event.ActionEvent evt) {
    UIManager.put("FileChooser.fileNameLabelText", "Nombre del archivo");
    JFileChooser m_FileChooser = new JFileChooser(new File(System.getProperty("user.dir")));
    int returnVal = m_FileChooser.showOpenDialog(this);
    if (returnVal == JFileChooser.APPROVE_OPTION) {
    final File selected = m_FileChooser.getSelectedFile();

    if (dp.getSelectedFrame().getName().equalsIgnoreCase("validateData")) {
    ctrl.cmdLoadDataOriginal(selected);
    }
    }
    }*/
    private void m_openCSV_ActionPerformed2(ActionEvent evt) {
        UIManager.put("FileChooser.fileNameLabelText", "Nombre del archivo");
        JFileChooser m_FileChooser = new JFileChooser(new File(System.getProperty("user.dir")));
        int returnVal = m_FileChooser.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            final File selected = m_FileChooser.getSelectedFile();

            ctrl.cmdLoadDataDynamicBN(selected);

        }
    }

    private void loadMttosFile(ActionEvent evt) {
        UIManager.put("FileChooser.fileNameLabelText", "Nombre del archivo");
        JFileChooser m_FileChooser = new JFileChooser(new File(System.getProperty("user.dir")));
        int returnVal = m_FileChooser.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            final File selected = m_FileChooser.getSelectedFile();
            ctrl.loadMttosFile(selected);
        }
    }

    private void openCSVRefVals_ActionPerformed(ActionEvent evt) {
        UIManager.put("FileChooser.fileNameLabelText", "Nombre del archivo");
        JFileChooser m_FileChooser = new JFileChooser(new File(System.getProperty("user.dir")));
        int returnVal = m_FileChooser.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            final File selected = m_FileChooser.getSelectedFile();
            ctrl.cmdLoad_referenceValues(selected);
        }
    }

    private void openCSV2Validate_ActionPerformed(ActionEvent evt) {
        UIManager.put("FileChooser.fileNameLabelText", "Nombre del archivo");
        JFileChooser m_FileChooser = new JFileChooser(new File(System.getProperty("user.dir")));
        int returnVal = m_FileChooser.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            final File selected = m_FileChooser.getSelectedFile();
            ctrl.cmdLoadData2Validate(selected);
        }
    }

    public void openPUL(Instances ins) {
        //decidir qu? objeto debe pasar aqui c.BD o validateDataPanel. m_data
        UL = new PanelUmbralesLocales(ctrl, ins);
        final JFrame umbralesLocales = new JFrame("Validaci?n x Ubrales Locales");
        umbralesLocales.getContentPane().setLayout(new BorderLayout());
        umbralesLocales.getContentPane().add(UL, BorderLayout.CENTER);
        umbralesLocales.addWindowListener(new WindowAdapter() {

            public void windowClosing(WindowEvent e) {
                umbralesLocales.dispose();
            }
        });
        umbralesLocales.setExtendedState(JFrame.NORMAL);
        umbralesLocales.setPreferredSize(new Dimension(800, 650));
        umbralesLocales.pack();
        umbralesLocales.setVisible(true);
    }

    private void deleteRows(ActionEvent evt) {
        final showTable m_tableInstances = new showTable();
        //m_tableInstances.setInstances(ctrl.BD);
        m_tableInstances.setInstances(ctrl.BDforPlot);
        final JFrame frameInstances = new JFrame("Tabla de datos");
        frameInstances.getContentPane().setLayout(new BorderLayout());
        frameInstances.getContentPane().add(m_tableInstances, BorderLayout.CENTER);
        JPanel m_bttns = new JPanel();

        JButton m_eliminarBtn = new JButton("Eliminar registros");
        JButton m_salirBtn = new JButton("Salir");
        m_bttns.setLayout(new GridLayout(1, 2, 20, 20));
        m_bttns.add(m_eliminarBtn);
        m_bttns.add(m_salirBtn);

        m_eliminarBtn.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                int[] rowsIdx = m_tableInstances.m_Table.getSelectedRows();
                if (rowsIdx.length == 0) {
                    JOptionPane.showMessageDialog(frameInstances,
                            "No hay registros seleccionados!\n",
                            "Eliminar registros",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (rowsIdx.length == ctrl.BD.numInstances()) {
                    JOptionPane.showMessageDialog(frameInstances,
                            "No es posible eliminar todos los registros!\n",
                            "Eliminar registros",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                Arrays.sort(rowsIdx);
                for (int i = rowsIdx.length - 1; i >= 0; i--) {
                    ctrl.m_historial.regDeleteInstance(ctrl.BD.instance(rowsIdx[i]).getOriginalIdx());
                    ctrl.BD.delete(rowsIdx[i]);
                    ctrl.BDforPlot.delete(rowsIdx[i]);
                }
                //m_tableInstances.setInstances(ctrl.BD);
                m_tableInstances.setInstances(ctrl.BDforPlot); //new line
                ctrl.changesDetection = null;
                //2011 ctrl._igu.cmdSetInstancesValidation(ctrl.BDforPlot); //new line
            }
        });

        m_salirBtn.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                ctrl.update();
                frameInstances.dispose();
            }
        });

        frameInstances.getContentPane().add(m_bttns, BorderLayout.SOUTH);

        frameInstances.addWindowListener(new WindowAdapter() {

            public void windowClosing(WindowEvent e) {
                frameInstances.dispose();
            }
        });
        frameInstances.setExtendedState(JFrame.NORMAL);
        frameInstances.setPreferredSize(new Dimension(500, 400));
        frameInstances.pack();
        frameInstances.setVisible(true);

    }

    public LogAndMsgs getLogAndMsgs() {
        return m_logAndMsgs;
    }

    /*Crea y configura los elementos del men?*/
    public void setMenu() {
        menuBar = new javax.swing.JMenuBar();
        javax.swing.JMenu fileMenu = new javax.swing.JMenu("Archivo");
        FileOpenCsv = new javax.swing.JMenuItem();
        FileLoadMttosFile = new javax.swing.JMenuItem();
        FileOpenRefsVals = new javax.swing.JMenuItem();
        jSeparator6 = new javax.swing.JPopupMenu.Separator();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        FileSaveCsv = new javax.swing.JMenuItem();
        FileSaveDiscretized = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        javax.swing.JMenuItem FileExit = new javax.swing.JMenuItem();
        EditMenu = new javax.swing.JMenu();
        EditDeleteVars = new javax.swing.JMenuItem();
        EditDeleteRegs = new javax.swing.JMenuItem();
        jSeparator4 = new javax.swing.JPopupMenu.Separator();
        EditRestoreVars = new javax.swing.JMenuItem();
        ActionsMenu = new javax.swing.JMenu();
        DiscretizarMenu = new javax.swing.JMenu();
        DiscretizeEqDist = new javax.swing.JMenuItem();
        DiscretizeEqFrec = new javax.swing.JMenuItem();
        DiscretizeMixGauss = new javax.swing.JMenuItem();
        AbruptChangeDetection = new javax.swing.JMenuItem();
        DeleteAllInstancesWithMttos = new javax.swing.JMenuItem();
        DeleteSelectedInstancesWithMttos = new javax.swing.JMenuItem();
        DeleteDataWithMttos = new javax.swing.JMenuItem();
        LocalThresholdValidation = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        DiscretizeConf = new javax.swing.JMenuItem();
        ModelMenu = new javax.swing.JMenu();
        ValidateLoadModel = new javax.swing.JMenuItem();
        ModelBuild = new javax.swing.JMenuItem();
        ModelShow = new javax.swing.JMenuItem();
        ModelSave = new javax.swing.JMenuItem();
        ModelLoadRestrictionFile = new javax.swing.JMenuItem();
        ValidateMenu = new javax.swing.JMenu();
        ValidateStart = new javax.swing.JMenuItem();
        ValidateStop = new javax.swing.JMenuItem();
        ValidateCorregirMenu = new javax.swing.JMenu();
        ApplyAll = new javax.swing.JMenuItem();
        ApplyRows = new javax.swing.JMenuItem();
        ApplyCells = new javax.swing.JMenuItem();
        ApplyMissingType = new javax.swing.JMenuItem();
        ApplyOutOfRangeType = new javax.swing.JMenuItem();
        ApplyAbruptChangesType = new javax.swing.JMenuItem();
        ApplyRelatedErrorType = new javax.swing.JMenuItem();
        //saveExcel = new javax.swing.JMenuItem();
        jSeparator8 = new javax.swing.JPopupMenu.Separator();
        ValidateConf = new javax.swing.JMenuItem();
        HelpMenu = new javax.swing.JMenu();
        HelpAbout = new javax.swing.JMenuItem();
        jSeparator9 = new javax.swing.JPopupMenu.Separator();

        menuBar.setName("menuBar");
        fileMenu.setMnemonic(java.awt.event.KeyEvent.VK_A);
        fileMenu.setText("Archivo");
        fileMenu.setName("fileMenu");

        FileOpenCsv.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.CTRL_MASK));
        FileOpenCsv.setText("Abrir (csv)...");
        FileOpenCsv.setName("FileOpenCsv");
        FileOpenCsv.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                m_openCSV_ActionPerformed(evt);
            }
        });
        fileMenu.add(FileOpenCsv);

        /*
        FileNewBuildModel.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.CTRL_MASK));
        FileNewBuildModel.setText("Cronstruir nuevo modelo...");
        FileNewBuildModel.setName("FileNewBuildModel");
        FileNewBuildModel.addActionListener(new java.awt.event.ActionListener() {

        public void actionPerformed(java.awt.event.ActionEvent evt) {
        buildModelinternalFrame = new JInternalFrame();
        buildModelinternalFrame.setClosable(true);
        buildModelinternalFrame.setIconifiable(true);
        buildModelinternalFrame.setMaximizable(true);
        buildModelinternalFrame.setResizable(true);
        buildModelinternalFrame.setName("buildModel");
        buildModelinternalFrame.setTitle("Construir modelo de validaci?n");
        buildModelinternalFrame.setVisible(true);
        buildModelinternalFrame.setBounds(120, 80, 270, 150);
        buildModelinternalFrame.getContentPane().setLayout(new BorderLayout());
        buildModelinternalFrame.getContentPane().add(BuildModel);
        buildModelinternalFrame.addInternalFrameListener(new CInternalFrameListener(_stateObserver, "buildModel"));
        dp.add(buildModelinternalFrame, javax.swing.JLayeredPane.DEFAULT_LAYER);
        dp.setSelectedFrame(buildModelinternalFrame);
        buildModelinternalFrame.pack();
        buildModelinternalFrame.setVisible(true);

        }
        });
        fileMenu.add(FileNewBuildModel);
         *
         */

        /*
        FileNewValidateData.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.CTRL_MASK));
        FileNewValidateData.setText("Abrir formulario de validacion...");
        FileNewValidateData.setName("FileNewValidateData");
        FileNewValidateData.addActionListener(new java.awt.event.ActionListener() {

        public void actionPerformed(java.awt.event.ActionEvent evt) {
        validateDataInternalFrame = new JInternalFrame();
        validateDataInternalFrame.setClosable(true);
        validateDataInternalFrame.setIconifiable(true);
        validateDataInternalFrame.setMaximizable(true);
        validateDataInternalFrame.setResizable(true);
        validateDataInternalFrame.setName("validateData");
        validateDataInternalFrame.setTitle("Validar Datos");
        validateDataInternalFrame.setVisible(true);
        validateDataInternalFrame.setBounds(150, 100, 300, 170);
        validateDataInternalFrame.getContentPane().setLayout(new BorderLayout());
        validateDataInternalFrame.getContentPane().add(ValidateData);
        validateDataInternalFrame.addInternalFrameListener(new CInternalFrameListener(_stateObserver, "validateData"));
        dp.add(validateDataInternalFrame, javax.swing.JLayeredPane.DEFAULT_LAYER);
        dp.setSelectedFrame(validateDataInternalFrame);
        validateDataInternalFrame.pack();
        validateDataInternalFrame.setVisible(true);

        }
        });
        fileMenu.add(FileNewValidateData);
         * 
         */

        FileLoadMttosFile.setText("Cargar registro de eventos...");
        FileLoadMttosFile.setName("FileLoadMttosFile");
        FileLoadMttosFile.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                loadMttosFile(evt);
            }
        });
        fileMenu.add(FileLoadMttosFile);


        FileOpenRefsVals.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_R, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        FileOpenRefsVals.setText("Abrir valores de referencia (csv)...");
        FileOpenRefsVals.setName("FileOpenRefsVals");
        FileOpenRefsVals.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                openCSVRefVals_ActionPerformed(evt);
            }
        });
        fileMenu.add(FileOpenRefsVals);

        jSeparator1.setName("jSeparator1");
        fileMenu.add(jSeparator1);

        FileSaveCsv.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_G, java.awt.event.InputEvent.CTRL_MASK));
        FileSaveCsv.setText("Guardar (csv)...");
        FileSaveCsv.setName("FileSaveCsv");
        fileMenu.add(FileSaveCsv);

        FileSaveDiscretized.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_G, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        FileSaveDiscretized.setText("Guardar discretizados (csv)...");
        FileSaveDiscretized.setName("FileSaveDiscretized");
        FileSaveDiscretized.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                ctrl.saveBD(ctrl.BD);
            }
        });
        fileMenu.add(FileSaveDiscretized);

        jSeparator2.setName("jSeparator2");
        fileMenu.add(jSeparator2);



        FileExit.setText("Salir");
        FileExit.setToolTipText("Salir de la aplicaci?n");
        FileExit.setName("FileExit");
        FileExit.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                System.exit(0);
            }
        });
        fileMenu.add(FileExit);

        menuBar.add(fileMenu);

        EditMenu.setText("Edici?n");

        EditDeleteVars.setText("Eliminar variables...");
        EditDeleteVars.setName("EditDeleteVars");
        EditMenu.add(EditDeleteVars);

        EditDeleteRegs.setText("Eliminar registros");
        EditDeleteRegs.setName("EditDeleteRegs");
        EditMenu.add(EditDeleteRegs);

        jSeparator4.setName("jSeparator4");
        EditMenu.add(jSeparator4);

        EditRestoreVars.setText("Restaurar variables");
        EditRestoreVars.setName("EditRestoreVars");
        EditMenu.add(EditRestoreVars);

        menuBar.add(EditMenu);

        ActionsMenu.setText("Acciones");
        ActionsMenu.setName("ActionsMenu");

        DiscretizarMenu.setText("Discretizar");
        DiscretizarMenu.setName("DiscretizarMenu");

        DiscretizeEqDist.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_D, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        DiscretizeEqDist.setText("Equidistancia");
        DiscretizeEqDist.setName("DiscretizeEqDist");
        DiscretizarMenu.add(DiscretizeEqDist);

        DiscretizeEqFrec.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        DiscretizeEqFrec.setText("Equifrecuencia");
        DiscretizeEqFrec.setName("DiscretizeEqFrec");
        DiscretizarMenu.add(DiscretizeEqFrec);

        DiscretizeMixGauss.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_G, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        DiscretizeMixGauss.setText("Mezclas Gaussianas");
        DiscretizeMixGauss.setName("DiscretizeMixGauss");
        DiscretizarMenu.add(DiscretizeMixGauss);



        jSeparator3.setName("jSeparator3");
        DiscretizarMenu.add(jSeparator3);

        DiscretizeConf.setText("Configurarci?n...");
        DiscretizeConf.setName("DiscretizeConf");
        DiscretizarMenu.add(DiscretizeConf);

        ActionsMenu.add(DiscretizarMenu);

        jSeparator9.setName("jSeparator9");
        ActionsMenu.add(jSeparator9);

        AbruptChangeDetection.setText("Detecci�n de cambios bruscos...");
        AbruptChangeDetection.setName("AbruptChangeDetection");
        AbruptChangeDetection.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                BuildModel.DWTSettings();//DiscreteWaveletTransformSettings
            }
        });
        ActionsMenu.add(AbruptChangeDetection);

        DeleteAllInstancesWithMttos.setText("Eliminar Instancias con Mantenimientos");
        DeleteAllInstancesWithMttos.setName("DeleteAllInstancesWithMttos");
        DeleteAllInstancesWithMttos.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                ctrl.DeleteAllInstancesWithMttos();
            }
        });
        //ActionsMenu.add(DeleteAllInstancesWithMttos);

        DeleteSelectedInstancesWithMttos.setText("Eliminar Instancias con Mantenimientos");
        DeleteSelectedInstancesWithMttos.setName("DeleteSelectedInstancesWithMttos");
        DeleteSelectedInstancesWithMttos.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                ctrl.DeleteAllInstancesWithMttos();
            }
        });
        EditMenu.add(DeleteSelectedInstancesWithMttos);

        DeleteSelectedInstancesWithMttos.setText("Eliminar registros con mantenimientos seleccionados");
        DeleteSelectedInstancesWithMttos.setName("DeleteSelectedInstancesWithMttos");
        DeleteSelectedInstancesWithMttos.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
            }
        });
        //ActionsMenu.add(DeleteSelectedInstancesWithMttos);

        DeleteDataWithMttos.setText("Eliminar datos con Mantenimientos Seleccionados");
        DeleteDataWithMttos.setName("DeleteDataWithMttos");
        DeleteDataWithMttos.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
            }
        });
        //ActionsMenu.add(DeleteDataWithMttos);


        LocalThresholdValidation.setText("Validaci�n por Umbrales Locales");
        LocalThresholdValidation.setName("LocalThresholdValidation");
        LocalThresholdValidation.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                if (m_Tabs.getSelectedIndex() == 0) {
                    openPUL(ctrl.BD);//OpenPanelUmbralesLocales
                }
                if (m_Tabs.getSelectedIndex() == 1) {
                    openPUL(ValidateData.m_data);//OpenPanelUmbralesLocales
                }
            }
        });

        ActionsMenu.add(LocalThresholdValidation);
        menuBar.add(ActionsMenu);

        ModelMenu.setText("Modelo");
        ModelMenu.setName("ModelMenu");

        ValidateLoadModel.setText("Cargar modelo...");
        ValidateLoadModel.setName("ValidateLoadModel");
        ValidateLoadModel.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                ctrl.loadValidationModels();
            }
        });
        ModelMenu.add(ValidateLoadModel);

        ModelLoadRestrictionFile.setText("Cargar archivo de restricciones...");
        ModelLoadRestrictionFile.setName("ModelLoadRestrictionFile");
        ModelLoadRestrictionFile.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                ctrl.cmdCargarRestricciones();
            }
        });
        ModelMenu.add(ModelLoadRestrictionFile);

        ModelBuild.setText("Crear modelo");
        ModelBuild.setName("ModelBuild");
        ModelMenu.add(ModelBuild);

        ModelShow.setText("Mostrar");
        ModelShow.setName("ModelShow");
        ModelMenu.add(ModelShow);


        ModelSave.setText("Guardar...");
        ModelSave.setName("ModelSave");
        ModelSave.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                ctrl.cmdsaveValidationModel();
            }
        });
        ModelMenu.add(ModelSave);
        menuBar.add(ModelMenu);

        ValidateMenu.setText("Validar");
        ValidateMenu.setName("ValidateMenu");

        ValidateStart.setText("Iniciar");
        ValidateStart.setName("ValidateStart");
        ValidateMenu.add(ValidateStart);

        ValidateStop.setText("Detener");
        ValidateStop.setName("ValidateStop");
        ValidateStop.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                ctrl.stopValidationProcess();
            }
        });
        ValidateMenu.add(ValidateStop);

        ValidateCorregirMenu.setText("Corregir");
        ValidateCorregirMenu.setName("ValidateCorregirMenu");

        ApplyAll.setText("Todo");
        ApplyAll.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                ctrl.applyChangesToAllData();
            }
        });
        ValidateCorregirMenu.add(ApplyAll);

        ApplyRows.setText("Filas seleccionadas");
        ApplyRows.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                int[] sel = ValidateData.m_tableInstances.getSelectedAttributes();
                ctrl.applyChangesToSelectedRow(sel);
            }
        });
        ValidateCorregirMenu.add(ApplyRows);

        ApplyCells.setText("Celdas seleccionadas");
        ApplyCells.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                ctrl.applyChangesToSelectedData();
            }
        });
        ValidateCorregirMenu.add(ApplyCells);

        ApplyMissingType.setText("1-Valores faltantes");
        ApplyMissingType.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                //1- Missing Data
                ctrl.applyChangesToErrorType(1);
            }
        });
        ValidateCorregirMenu.add(ApplyMissingType);

        ApplyOutOfRangeType.setText("2-Fuera de rango");
        ApplyOutOfRangeType.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                //2- Out of Range
                ctrl.applyChangesToErrorType(2);
            }
        });
        ValidateCorregirMenu.add(ApplyOutOfRangeType);

        ApplyAbruptChangesType.setText("3-Cambios abruptos");
        ApplyAbruptChangesType.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                //3- Abrupt Changes
                ctrl.applyChangesToErrorType(3);
            }
        });
        ValidateCorregirMenu.add(ApplyAbruptChangesType);

        ApplyRelatedErrorType.setText("4-Valores relacionados");
        ApplyRelatedErrorType.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                //4- Abrupt Changes
                ctrl.applyChangesToErrorType(4);
            }
        });
        ValidateCorregirMenu.add(ApplyRelatedErrorType);
       ValidateMenu.add(ValidateCorregirMenu);

       

        ValidateConf.setText("Configuraci�n...");
        ValidateConf.setName("ValidateConf");
        ValidateMenu.add(ValidateConf);

        menuBar.add(ValidateMenu);

        HelpMenu.setText("Ayuda");
        HelpMenu.setName("Ayuda");

        HelpAbout.setText("Acerca de...");
        HelpAbout.setName("HelpAbout");
        HelpAbout.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                showAboutBox();
            }
        });

        HelpMenu.add(HelpAbout);
        menuBar.add(HelpMenu);
    }

    public void raiseException(Exception ex, String s1, String s2) {

        if (JOptionPane.showOptionDialog(IGUPanel.this,
                s1 + "\n"
                + "Motivo:\n" + ex.getMessage(),
                s2,
                0,
                JOptionPane.ERROR_MESSAGE,
                null,
                new String[]{"Aceptar"},
                null) == 1) {
        }
    }

    /*Informa a BuildModel que debe mostrar los datos contenidos en el objeto
     *inst
     *@param inst Objeto Instances que contiene las datos a mostrar
     */
    public void cmdshowDataBM(Instances inst) {
        BuildModel.showData(inst);
    }

    /*Informa a ValidateData que debe mostrar los datos contenidos en el objeto
     *inst
     *@param inst Objeto Instances que contiene las datos a mostrar
     */
    public void cmdSetInstancesValidation(Instances inst) {

        ValidateData.setInstances(inst);

    }

    public void enableDiscretizeCtrls(boolean b) {
        this.listaAlgoritmos.setEnabled(b);
        this.m_DiscretizeConf.setEnabled(b);
        this.m_discretize.setEnabled(b);
    }

    public void showAboutBox() {
        if (aboutBox == null) {
            aboutBox = new aboutBoxDlg(MainFrame.getFrame(), true);
            aboutBox.setLocationRelativeTo(MainFrame.getFrame());
            aboutBox.setVisible(true);
        }
    }
}
