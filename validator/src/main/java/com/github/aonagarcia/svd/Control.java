/*
 * Control.java
 *
 * Created on 16 de julio de 2008, 15:13
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package com.github.aonagarcia.svd;

import com.github.aonagarcia.RegsMttos.TimeLinesManager;
import com.github.aonagarcia.igu.showModel;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.util.Arrays;
import java.util.Vector;
import java.util.LinkedList;
import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.awt.Cursor;
import javax.swing.JFileChooser;
import com.github.aonagarcia.igu.IGUPanel;
import com.github.aonagarcia.igu.modelParameters;
import com.github.aonagarcia.igu.ExtensionFileFilter;

import com.github.aonagarcia.extras.Instances;
import com.github.aonagarcia.extras.Instance;
import com.github.aonagarcia.extras.CSVLoader;
import com.github.aonagarcia.extras.Discretize;
import com.github.aonagarcia.extras.FastVector;
import com.github.aonagarcia.extras.Attribute;
import com.github.aonagarcia.extras.AttributeStats;
import com.github.aonagarcia.extras.Utils;
import com.github.aonagarcia.igu.MainFrame;
import java.util.Iterator;
import javax.swing.UIManager;
import com.github.aonagarcia.RegsMttos.mttosDataSet;
import jxl.write.WriteException;

import javax.swing.JScrollPane;

/**
 *
 * @author Javier HV
 */
/**Esta clase gestiona las funciones principales del Sistema para Validaci?n de Datos
 *Las acciones solicitadas por el usuario son ejecutadas por esta clase. La IGU es el medio
 *de comunicaci?n entre el usuario y la clase Control.
 */
public class Control {

    private File openedFile;
    /*Objeto para preparar la tabla de datos para la construcci?n de la red din?mica*/
    private ConstruirTabla _construirTabla = new ConstruirTabla();
    public showModel modeloGrafico;
    public boolean flag1 = false;
    public Vector structDN = null;
    //public Instances validatedBD;
    /**Estructura para almacenar el conjunto de datos a validar o de los que se aprender? un modelo*/
    public static Instances BD;
    private Instances dymanicBD;
    /**estructura para almecenar los SetPoints cargados de un archivo*/
    public static Instances _referenceValues;
    /**Estructura para almacenar los par?metros para validacion (l?mites, umbrales,etc) en la construcci?n de un modelo*/
    public static Instances validationModel;
    /**Estructura para cargar un modelo de validaci?n existente*/
    //public static Instances ModelToValidateBD;
    /**Copia de la BD original a la que se le a?aden  durante los procesos de validaci?n las acciones ejecutadas*/
    public static Instances historial;
    /**Se mantiene en este objeto una copia de la base de datos BD para podre mostrar la gr?fica datos/tiempo
     * aunque los las variables de BD hayan sido discretizadas*/
    public static Instances BDforPlot;
    /**N?mero de atributos que contiene el modelo de validaci?n*/
    protected int numAttributesValidationModel = 15;
    /**?ndice a la instancia seleccionada del archivo de SetPoints*/
    //public static int setPointRowSelected=-1;
    /**Proporciona acceso a m?todos y variables de la Interfaz Gr?fica*/
    public static IGUPanel _igu;
    /**Tolerancia establecida cuando una variable no contiene ningun valor de referencia o s?lo est? definido el mid */
    protected int tolerancia_SPMid = 3;
    /**Tolerancia establecida cuando una vaiable tiene definido SPmax y/o SPmin*/
    protected int tolerancia_SP_MaxMin = 2;
    /**Cadena en la que se almacena el archivo de restricciones*/
    protected String constraintsFileName = null;
    /**Objeto para discretizar variables*/
    public Discretize discretizar = new Discretize();
    /**Hilo para ejecutar funciones de entrada/salida*/
    protected Thread m_IOThread;
    /**Hilo para realizar el proceso de validaci?n de una base de datos*/
    protected Thread m_ValidarThread;
    /**Archivo para cargar el model de vallidaci?n (csv)*/
    private File validationModelfile;
    /**Base de datos para crear el Historial de Acciones*/
    public historial m_historial;
    /**Par?metro para la detecci?n de fallas aparentes*/
    public double p_value = 0.01;
    /**Par?metro para la detecci?n de fallas reales*/
    public double Pf_Th = 0.7;
    /**Par?metro para la construcci?n de la estructura de la red bayesiana*/
    public double significaceLevel = 0.05;
    /**Define la tolerancia (desviaciones est?ndar aplicadas) para el c?lculo del umbral de cambios abruptos*/
    public double ToleranciaUmbral = 3;
    /**Objeto que empaqueta la informaci?n que se envia y/o recibe del servidor*/
    ValidationObjects vm;
    /**Objeto para crear la red de detecci?n*/
    public DetectionNetwork _DN;
    /**Este objeto realizar? la validaci?n de una BD*/
    public validate m_validate;
    /**Objeto que realiza la detecci?n de cambios bruscos en los datos*/
    public ChangesDetection changesDetection;
    /**Objeto que prepara la estructura de mantenimientos*/
    private TimeLinesManager mttosTimeLines;
    private String fileToSave;
    mttosDataSet mds;
    //public ArrayList<Domain> localDNArray = new ArrayList<Domain>();
    public boolean DWTComputed = false;
    public static Instances originales;

    /** Creates a new instance of Control */
    public Control() {
    }

    /**M?todo que inicializa la estructura "validationModel" que almacenar? la informaci?n
     * necesaria durante la construcci?n de un modelo y que ser? utilizada para validar.
     * La estructura almacena los nombres de cada variable de la BD caragda en el sistema y
     * una serie de valores asociados a la variable como: Rangos, Media, Desviaci?n Est?ndar,
     * SetPoints, L?mites, umbral de cambios.
     * Los atributos SPmaxTol y SPminTol creados dentro de esta funci?n corresponden a los
     * l?mites Superiro e Inferior, respectivamente, que se muestran en la IGU
     */
    public void initValidationModel() {

        int numInstancesValidationModel = BD.numAttributes();

        FastVector atts;
        atts = new FastVector(numAttributesValidationModel);

        FastVector values = new FastVector(numInstancesValidationModel);
        for (int z = 0; z < numInstancesValidationModel; z++) {
            values.addElement("dummy");
        }

        for (int i = 0; i < numInstancesValidationModel; i++) {
            values.setElementAt(BD.attribute(i).name(), i);
        }

        atts.addElement(new Attribute("varName", values));
        atts.addElement(new Attribute("RangoMax"));
        atts.addElement(new Attribute("RangoMin"));
        atts.addElement(new Attribute("Media"));
        atts.addElement(new Attribute("Mediana"));
        atts.addElement(new Attribute("DesvStndMedia"));
        atts.addElement(new Attribute("DesvStndMediana"));
        atts.addElement(new Attribute("SPmax"));
        atts.addElement(new Attribute("SPmid"));
        atts.addElement(new Attribute("SPmin"));
        atts.addElement(new Attribute("SPmaxTol"));
        atts.addElement(new Attribute("SPminTol"));
        atts.addElement(new Attribute("Escala_Cambios"));
        atts.addElement(new Attribute("Umbral_Cambios"));
        atts.addElement(new Attribute("WindowWidth_LV"));

        validationModel = new Instances("validationModel", atts, numInstancesValidationModel);

        for (int j = 0; j < numInstancesValidationModel; j++) {
            double[] vals = new double[numAttributesValidationModel];
            for (int i = 0; i < numAttributesValidationModel; i++) {
                if (i == 0) {
                    vals[i] = (double) j;
                } else {
                    vals[i] = Instance.missingValue();
                }
            }
            validationModel.add(new Instance(1.0, vals));
        }
        setValidationModelStats(true);
    }

    /**Calcula las estad?sticas de cada variable de la BD y almacena los valores correspondientes
     * en la estructura validationModel*/
    public void setValidationModelStats(boolean b) {

        AttributeStats attStats = new AttributeStats();

        for (int j = 0; j < BD.numAttributes(); j++) {
            attStats = BD.attributeStats(j);
            if (attStats.numericStats != null) {
                validationModel.instance(j).setValue(validationModel.attribute("RangoMax"), attStats.numericStats.max);
                validationModel.instance(j).setValue(validationModel.attribute("RangoMin"), attStats.numericStats.min);
                validationModel.instance(j).setValue(validationModel.attribute("Media"), attStats.numericStats.mean);
                double mediana = attStats.numericStats.calculaMediana(BD, j);
                validationModel.instance(j).setValue(validationModel.attribute("Mediana"), mediana);
                validationModel.instance(j).setValue(validationModel.attribute("DesvStndMedia"), attStats.numericStats.stdDev);
                validationModel.instance(j).setValue(validationModel.attribute("DesvStndMediana"), attStats.numericStats.calculaStdDevM(mediana, BD, j));
            }
        }
    }

    /**Almacena los valores de los SetPoints cargados en la estructura "validationModel"
     * el par?metro SP_idx corresponde a la instancia de la base de datos de SetPoints de
     * donde se leer?n los valores para cada variable.
     */
    public void setSPToValidationModel(int SP_idx) {
        String media_mediana = "Mediana";
        int SPMaxIdx = validationModel.attribute("SPmax").index();
        int SPMidIdx = validationModel.attribute("SPmid").index();
        int SPMinIdx = validationModel.attribute("SPmin").index();
        int SPMinTolIdx = validationModel.attribute("SPminTol").index();
        int SPMaxTolIdx = validationModel.attribute("SPmaxTol").index();

        for (int j = 0; j < BD.numAttributes(); j++) {
            String varName = validationModel.instance(j).stringValue(0);
            double media = validationModel.instance(j).value(validationModel.attribute(media_mediana));
            double DesvStnd = validationModel.instance(j).value(validationModel.attribute("DesvStnd" + media_mediana));

            validationModel.instance(j).setValue(SPMaxIdx, Instance.missingValue());
            validationModel.instance(j).setValue(SPMidIdx, Instance.missingValue());
            validationModel.instance(j).setValue(SPMinIdx, Instance.missingValue());

            double _spMax = _referenceValues.attribute(varName + "_sup") != null ? _referenceValues.instance(SP_idx).value(_referenceValues.attribute(varName + "_sup")) : Instance.missingValue();
            double _spMid = _referenceValues.attribute(varName + "_med") != null ? _referenceValues.instance(SP_idx).value(_referenceValues.attribute(varName + "_med")) : Instance.missingValue();
            double _spMin = _referenceValues.attribute(varName + "_inf") != null ? _referenceValues.instance(SP_idx).value(_referenceValues.attribute(varName + "_inf")) : Instance.missingValue();

            validationModel.instance(j).setValue(SPMaxIdx, _spMax);
            validationModel.instance(j).setValue(SPMidIdx, _spMid);
            validationModel.instance(j).setValue(SPMinIdx, _spMin);

            if (!BD.attribute(j).isDiscretized()) {
                /*Si no existe ningun valor se calculan las tolerancias con respecto a la media (+/-) DesvStandar */
                if (Instance.isMissingValue(_spMax) && Instance.isMissingValue(_spMin) && Instance.isMissingValue(_spMid)) {
                    validationModel.instance(j).setValue(SPMidIdx, media);
                    double LI = media - (tolerancia_SPMid * DesvStnd);
                    double LS = media + (tolerancia_SPMid * DesvStnd);
                    validationModel.instance(j).setValue(SPMinTolIdx,/*LI<0?0:*/ LI);
                    validationModel.instance(j).setValue(SPMaxTolIdx,/*LS<0?0:*/ LS);
                    //ya no aplica validationModel.instance(j).setValue(validationModel.attribute("Umbral_Cambios"), computeThreshold(j));
                    continue;
                }

                /**Si s?lo est? definido SPMid se calculan las tolerancias con respecto a ?l (+/-) DesvStandar*/
                if (Instance.isMissingValue(_spMax) && Instance.isMissingValue(_spMin) && (!Instance.isMissingValue(_spMid))) {

                    validationModel.instance(j).setValue(SPMidIdx, _spMid);
                    double LI = _spMid - (tolerancia_SPMid * DesvStnd);
                    double LS = _spMid + (tolerancia_SPMid * DesvStnd);
                    validationModel.instance(j).setValue(SPMinTolIdx,/*LI<0?0:*/ LI);
                    validationModel.instance(j).setValue(SPMaxTolIdx,/*LS<0?0:*/ LS);
                    //ya no aplica validationModel.instance(j).setValue(validationModel.attribute("Umbral_Cambios"), computeThreshold(j));
                    continue;
                }

                /**Si s?lo esta definido SPMin se establece su valor
                 * y la tolerancia superior con la media (+) DesvStandar*/
                if (Instance.isMissingValue(_spMax) && (!Instance.isMissingValue(_spMin)) && Instance.isMissingValue(_spMid)) {

                    validationModel.instance(j).setValue(SPMinIdx, _spMin);
                    //validationModel.instance(j).setValue(SPMinTolIdx,(_spMin-(tolerancia_SPMid*DesvStnd)));
                    double LI =/*_spMin<0?0:*/ _spMin;
                    double LS = media + (tolerancia_SPMid * DesvStnd);
                    validationModel.instance(j).setValue(SPMinTolIdx, LI);
                    validationModel.instance(j).setValue(SPMaxTolIdx,/*LS<0?0:*/ LS);
                    //ya no aplica validationModel.instance(j).setValue(validationModel.attribute("Umbral_Cambios"), computeThreshold(j));
                    continue;
                }

                /**Si s?lo est? definido SPMax se establece su valor
                 * y la tolerancia inferior con la media (-) DesvStandar*/
                if ((!Instance.isMissingValue(_spMax)) && Instance.isMissingValue(_spMin) && Instance.isMissingValue(_spMid)) {

                    validationModel.instance(j).setValue(SPMaxIdx, _spMax);
                    double LS =/*_spMax<0?0:*/ _spMax;
                    double LI = media - (tolerancia_SPMid * DesvStnd);
                    validationModel.instance(j).setValue(SPMaxTolIdx, _spMax);
                    validationModel.instance(j).setValue(SPMinTolIdx,/*LI<0?0:*/ LI);
                    //validationModel.instance(j).setValue(SPMaxTolIdx,(_spMax+(tolerancia_SPMid*DesvStnd)));
                    //ya no aplica validationModel.instance(j).setValue(validationModel.attribute("Umbral_Cambios"), computeThreshold(j));
                    continue;
                }

                /**Si esta definido SPMax y SPMin se establece su valor como LI y LS*/
                if ((!Instance.isMissingValue(_spMax)) && (!Instance.isMissingValue(_spMin))) {

                    validationModel.instance(j).setValue(SPMaxIdx, _spMax);
                    validationModel.instance(j).setValue(SPMinIdx, _spMin);
                    //validationModel.instance(j).setValue(SPMinTolIdx,(_spMin-(tolerancia_SPMid*DesvStnd)));
                    //validationModel.instance(j).setValue(SPMaxTolIdx,(_spMax+(tolerancia_SPMid*DesvStnd)));
                    validationModel.instance(j).setValue(SPMinTolIdx,/*_spMin<0?0:*/ _spMin);
                    validationModel.instance(j).setValue(SPMaxTolIdx,/*_spMax<0?0:*/ _spMax);
                    //ya no aplica validationModel.instance(j).setValue(validationModel.attribute("Umbral_Cambios"), computeThreshold(j));
                    continue;
                }

                /**Si los tres estan definidos se almacenan */
                if ((!Instance.isMissingValue(_spMax)) && Instance.isMissingValue(_spMin) && Instance.isMissingValue(_spMid)) {
                    validationModel.instance(j).setValue(SPMaxIdx, _spMax);
                    validationModel.instance(j).setValue(SPMidIdx, _spMid);
                    validationModel.instance(j).setValue(SPMinIdx, _spMin);
                    //validationModel.instance(j).setValue(SPMinTolIdx,(_spMin-(tolerancia_SPMid*DesvStnd)));
                    //validationModel.instance(j).setValue(SPMaxTolIdx,(_spMax+(tolerancia_SPMid*DesvStnd)));
                    validationModel.instance(j).setValue(SPMinTolIdx,/*_spMin<0?0:*/ _spMin);
                    validationModel.instance(j).setValue(SPMaxTolIdx,/*_spMax<0?0:*/ _spMax);
                    //ya no aplica validationModel.instance(j).setValue(validationModel.attribute("Umbral_Cambios"), computeThreshold(j));
                    continue;
                }
            }
        }
    }

    /**Calcula y almacena en la estructura validationModel los l?mites y el umbral
     * de cambios bruscos para cada variable. Este m?todo es llamado cuando se carga
     * una base de datos con la finalidad de proponer valores de referencia de acuerdo
     * a las tolerancias establecidas.
     *
     *L?mite Superior (SPmaxTol) = media + (tolerancia_SPMid*desviaci?n est?ndar)
     *L?mite Inferior (SPminTol) = media - (tolerancia_SPMid*desviaci?n est?ndar)
     */
    public void calacularLimites(String media_mediana) {
        //String media_mediana = "Mediana";
        for (int j = 0; j < BD.numAttributes(); j++) {
            String varName = validationModel.instance(j).stringValue(0);
            double m = validationModel.instance(j).value(validationModel.attribute(media_mediana));
            double DesvStnd = Utils.roundDouble(validationModel.instance(j).value(validationModel.attribute("DesvStnd" + media_mediana)), 6);
            validationModel.instance(j).setValue(validationModel.attribute("SPmid"), m);
            double LI = m - (tolerancia_SPMid * DesvStnd);
            double LS = m + (tolerancia_SPMid * DesvStnd);
            validationModel.instance(j).setValue(validationModel.attribute("SPminTol"),/*LI<0?0:*/ LI);
            validationModel.instance(j).setValue(validationModel.attribute("SPmaxTol"),/*LS<0?0:*/ LS);
            //validationModel.instance(j).setValue(validationModel.attribute("Umbral_Cambios"), computeThreshold(j));            
            //}
        }
        //Ejecutarlo al abrir el formulario DWTSettings --> computeThresholdWH();
    }

    public void computeThresholdWH() {
        if (changesDetection == null) {
            changesDetection = new ChangesDetection(BD);
        }
    }

    /**La IGU llama este metodo para establecer el punto de comunicaci?n
     *entre Control e IGU.
     *@param igu La interfaz grafica que se comunicar? con Control
     */
    public void setIGU(IGUPanel igu) {
        _igu = igu;
    }

    public void runValidationScript() {
        String path = "D:\\Personal\\Pemex\\Validator\\validator\\Datos\\Valid\\J1\\sinFueraRango\\";
        File directorio = new File(path);
        String[] ficheros = directorio.list();
        final File m_file;
        String line;

        for (int i = 0; i < ficheros.length; i++) {
            try {
                if (ficheros[i].compareToIgnoreCase("sinFueraRango") == 0) {
                    continue;
                }
                BufferedReader br = new BufferedReader(new FileReader(new File(path + ficheros[i])));
                String linea = br.readLine();
                while (linea != null) {
                    System.out.println(linea);
                    linea = br.readLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        for (int i = 0; i < ficheros.length; i++) {
            try {
                if (ficheros[i].compareToIgnoreCase("sinFueraRango") == 0) {
                    continue;
                }
                CSVLoader cnv = new CSVLoader();
                cnv.setSource(new File(path + ficheros[i]));
                BD = cnv.getDataSet();
                m_historial = new historial(BD);
                BDforPlot = new Instances(BD);
                initValidationModel();
                calacularLimites("Mediana");
                /*   System.out.print(ficheros[i]);
                AttributeStats attStats = new AttributeStats();
                for (int j = 0; j < BD.numAttributes(); j++) {
                attStats = BD.attributeStats(j);
                if (attStats.numericStats != null) {
                System.out.print(","+attStats.numericStats.max+","+attStats.numericStats.min+"\t");
                }
                }*/
                System.out.println();
            } catch (IOException e) {
                e.printStackTrace();
            }


            if (BD != null) {
                int numAtts = BD.numAttributes();
                int[] sel = new int[numAtts];
                for (int j = 0; j < numAtts; j++) {
                    sel[j] = j;
                }
                removeAllOutOfRangeData(sel);
                saveInstancesToFile(new File(path + "sinFueraRango\\" + ficheros[i]), BD);
                System.out.println("Archivo " + i + " :" + ficheros[i] + " hecho");
            }


        }
    }

    /**Carga en el sistema los datos del archivo ?selected?. Se utiliza un objeto CSVLoader
     * para realizar la carga de datos.
     * Los datos son almacenados en el objeto BD (variable de la clase Control). Se crea un objeto
     * de la clase historial con la informaci?n de la base de datos cargada. Se inicializa la
     * estructura validationModel y se env?a a la IGU la BD que debe ser visualizada en cada pesta?a.
     * Posterior a esto, se llama al m?todo que verifica el porcentaje de informaci?n de cada variable
     * para determinar las variables que deben ser eliminadas.Por ?ltimo, se calcula la informaci?n
     * preliminar del modelo (l?mites, umbral).*/
    public void cmdLoadData(final File selected) {
        setCursor(new Cursor(Cursor.WAIT_CURSOR));
        if (m_IOThread == null) {
            m_IOThread = new Thread() {

                @Override
                public void run() {
                    try {
                        _igu.BuildModel.flag = false;
                        _igu.getLogAndMsgs().addStringToLog("Cargando archivo: " + selected.getAbsolutePath());

                        CSVLoader cnv = new CSVLoader();
                        cnv.setSource(selected);
                        BD = cnv.getDataSet();
                        m_historial = new historial(BD);
                        initValidationModel();
                        _igu.cmdshowDataBM(BD);
                        _igu.ValidateData.initTable();
                        _igu.BuildModel.mlp.plot.setMttosFlag(false);
                        _igu.getLogAndMsgs().addStringToLog(BD.numAttributes() + " vaiables " + BD.numInstances() + " instancias");
                        _igu.getLogAndMsgs().addStringToLog("Datos cargados");
                        openedFile = selected.getAbsoluteFile();
                        /*Informa al usuario si hay variables con menos del
                         *30% de datos y si desea eliminarlos
                         */
                        cmdDeleteVarsWithMissingValues(0.30);
                        calacularLimites("Mediana");
                        _igu.BuildModel.showVarData(0);
                        _igu.BuildModel.updateGraphic(0);
                        structDN = null;
                        _igu._stateObserver.setStateBuildModel(1);
                        _igu.BuildModel.enableIGUCtrls(true);
                        _igu.m_Tabs.setEnabledAt(1, false);

                    } catch (Exception ex) {
                        m_IOThread = null;
                        _igu.getLogAndMsgs().addStringToLog("Error al cargar los datos");
                        _igu.raiseException(ex, "Error al cargar los datos", "Cargar Datos");
                    }
                    m_IOThread = null;
                }
            };

            m_IOThread.setPriority(Thread.MIN_PRIORITY);
            m_IOThread.start();
        } else {
            System.out.println("Sistema Ocupado");
        }
        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }

    public void cmdLoadDataDynamicBN(final File selected) {
        setCursor(new Cursor(Cursor.WAIT_CURSOR));
        if (m_IOThread == null) {
            m_IOThread = new Thread() {

                @Override
                public void run() {
                    try {
                        _igu.BuildModel.flag = false;
                        _igu.getLogAndMsgs().addStringToLog("Cargando archivo: " + selected.getAbsolutePath());

                        CSVLoader cnv = new CSVLoader();
                        cnv.setSource(selected);
                        dymanicBD = cnv.getDataSet();

                    } catch (Exception ex) {
                        m_IOThread = null;
                        _igu.getLogAndMsgs().addStringToLog("Error al cargar los datos");
                        _igu.raiseException(ex, "Error al cargar los datos", "Cargar Datos");
                    }
                    m_IOThread = null;
                }
            };

            m_IOThread.setPriority(Thread.MIN_PRIORITY);
            m_IOThread.start();
        } else {
            System.out.println("Sistema Ocupado");
        }
        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }

    public void cmdLoad_referenceValues(final File selected) {
        if (m_IOThread == null) {
            m_IOThread = new Thread() {

                @Override
                public void run() {
                    try {

                        CSVLoader cnv = new CSVLoader();
                        cnv.setSource(selected);
                        _referenceValues = cnv.getDataSet();

                    } catch (Exception ex) {
                        m_IOThread = null;
                        _igu.raiseException(ex, "Error al cargar el archivo.", "Cargar valores de referencia");
                    }
                    m_IOThread = null;
                    cmdChooseSP();
                }
            };
            m_IOThread.setPriority(Thread.MIN_PRIORITY);
            m_IOThread.start();
        } else {
            System.out.println("Sistema Ocupado");
        }

    }

    public void removeVarsFromValidationModel() {
        validationModel.deleteAttributeAt(0);
        int numInstancesValidationModel = BD.numAttributes();
        FastVector att = new FastVector(1);
        FastVector values = new FastVector(numInstancesValidationModel);

        for (int z = 0; z < numInstancesValidationModel; z++) {
            values.addElement("dummy");
        }

        for (int j = 0; j < numInstancesValidationModel; j++) {
            values.setElementAt(BD.attribute(j).name(), j);
        }

        validationModel.insertAttributeAt(new Attribute("varName", values), 0);

        for (int j = 0; j < numInstancesValidationModel; j++) {
            validationModel.instance(j).setValue(validationModel.attribute("varName"), BD.attribute(j).name());
        }
    }

    /**Elimina las variables contenidas en el arreglo ?ndices de las estructuras de datos:
     *BD, BDforPlot y validationModel. Despu?s de eliminarlas se le pasa a la IGU las bases
     * de datos con lo cambios realizados. En este m?todo tambien se le informa al Historial
     *sobre la acci?n realizada.
     */
    public void cmdDeleteVars(int[] indices) {
        Arrays.sort(indices);
        for (int i = indices.length - 1; i >= 0; i--) {
            m_historial.regDeleteVar(BD.attribute(indices[i]).name());
            BD.deleteAttributeAt(indices[i]);
            BDforPlot.deleteAttributeAt(indices[i]);
            if (validationModel != null) {
                validationModel.delete(indices[i]);
            }
        }

        removeVarsFromValidationModel();
        _igu.cmdshowDataBM(BD);

        //21/02/2011 _igu.cmdSetInstancesValidation(BDforPlot);
        _igu.BuildModel.setInstancesForPlot(BDforPlot);
        _igu.BuildModel.m_variables.m_List.setSelected(false);
    }

    public void cmdDeleteVarsVDP(int[] indices) {
        Arrays.sort(indices);
        for (int i = indices.length - 1; i >= 0; i--) {
            _igu.ValidateData.getInstances().deleteAttributeAt(indices[i]);
        }
        _igu.ValidateData.setInstances(_igu.ValidateData.getInstances());
        _igu.ValidateData.m_variables.m_List.setSelected(false);
    }

    /**Este m?todo es llamado por el m?todo cmdDiscretize antes de discretizar las variables
     * que el usuario haya seleccionado. Se eliminan los datos fuera de rango ya que no son
     * considerados en el proceso de discretizaci?n.*/
    public void removeAllOutOfRangeData(int[] sel) {
        //Eliminar todos los datos fuera de rango antes de discretizar

        for (int i = 0; i < sel.length; i++) {
            //2011 if (isVarOfValidGroup(sel[i])) {//verifica que se trate de una grupo de variables valido
            for (int j = 0; j < BD.numInstances(); j++) {
                double val = BD.instance(j).value(sel[i]);
                if ((val < validationModel.instance(sel[i]).value(validationModel.attribute("SPminTol")))
                        || (val > validationModel.instance(sel[i]).value(validationModel.attribute("SPmaxTol")))) {
                    BD.instance(j).setValue(sel[i], Double.NaN);
                    BDforPlot.instance(j).setValue(sel[i], Double.NaN);
                }
            }
            //2011 }
        }
        //Calcula los nuevos rangos, desviaci?n est?ndar, etc. despu?s de haber eliminado
        //los datos fuera de rango
        setValidationModelStats(false);
//mod4script        _igu.BuildModel.showVarData(0);
//mod4script        _igu.BuildModel.updateGraphic(0);
    }

    /**Realiza la discretizaci?n de las variables seleccionadas por el usuario
     * en la IGU. Este m?todo solicita a la IGU la lista de variables a discretizar
     * que analiza para saber si ya fueron discretizadas o no pertenecen a variables
     * v?lidas para discretizar (de acuerdo a los grupos de variables v?lidos
     * definidos en groupNames), depu?s de filtrar las variables que se discretizar?n
     * elimina los datos fuera de rango y ejecuta el algoritmo de discretizaci?n
     * seleccionado por el usuario en la IGU, el par?metro ?alg? define el algoritmo
     * seleccionado.
     */
    public void cmdDiscretize(final int alg) {
        if (m_IOThread == null) {
            m_IOThread = new Thread() {

                @Override
                public void run() {

                    int[] indicesSelected = _igu.BuildModel.getSelectedAttributes();
                    Arrays.sort(indicesSelected);
                    int l = indicesSelected.length;
                    String listaVars = "";
                    for (int i = 0; i < l; i++) {
                        if ((BD.attribute(indicesSelected[i]).isDiscretized()) || BD.isEmpty(indicesSelected[i])) {
                            listaVars = listaVars + BD.attribute(indicesSelected[i]).name() + "\n";
                        }
                    }
                    if (listaVars.length() > 0) {
                        JOptionPane.showConfirmDialog(_igu.BuildModel, "Se excluiran del proceso las variables nominales,  discretizadas y/o vacias", "Discretizar", JOptionPane.PLAIN_MESSAGE);
                    }

                    /*Se eliminan los ?ndices de las variables que no deben discretizarse
                     *varibles de grupos no v?lidos o variables ya discretizadas*/
                    for (int i = 0; i < l; i++) {
                        if ((BD.attribute(indicesSelected[i]).isDiscretized()) || BD.isEmpty(indicesSelected[i])) {
                            System.arraycopy(indicesSelected, i + 1, indicesSelected, i, l - i - 1);
                            l--;
                            i--;
                        }
                    }

                    int[] validIndices = new int[l];
                    System.arraycopy(indicesSelected, 0, validIndices, 0, l);

                    if (validIndices.length > 0) {
                        int response = JOptionPane.showConfirmDialog(_igu.BuildModel,
                                "Se eliminar?n los datos fuera de rango de las variables seleccionadas",
                                "Discretizar",
                                JOptionPane.WARNING_MESSAGE);

                        if (response == JOptionPane.NO_OPTION || response == JOptionPane.CANCEL_OPTION) {
                            m_IOThread = null;
                            return;
                        }
                    } else {
                        m_IOThread = null;
                        return;
                    }
                    removeAllOutOfRangeData(validIndices);
                    discretizar.setAttributeIndicesArray(validIndices);
                    discretizar.setValidationMOdel(validationModel);

                    switch (alg) {
                        case 0:
                            discretizar.setUseEqualFrequency(false);
                            discretizar.setUseEqualWidthBinning(true);
                            discretizar.setUseMixtureGaussians(false);
                            break;
                        case 1:
                            discretizar.setUseEqualFrequency(true);
                            discretizar.setUseEqualWidthBinning(false);
                            discretizar.setUseMixtureGaussians(false);
                            break;
                        case 2:
                            discretizar.setUseEqualFrequency(false);
                            discretizar.setUseEqualWidthBinning(false);
                            discretizar.setUseMixtureGaussians(true);
                            break;
                    }
                    try {
                        Instances copy = new Instances(BD);
                        discretizar.setInputFormat(copy);
                        BD = discretizar.useFilter(copy, discretizar);
                        _igu.cmdshowDataBM(BD);

                        /*13Nov2011
                        for (int i = 0; i < copy.numAttributes(); i++) {
                        BD.attribute(i).setDiscretizedFlag(copy.attribute(i).isDiscretized(),alg,discretizar.getBins());
                        }*/
                        copy.delete();

                        //establece una bandera para identificar que la variable ya fue discretizada y el
                        //algoritmo y configuraci?n usados (esto se utiliza para crear el modelo local)
                        for (int i = 0; i < validIndices.length; i++) {
                            BD.attribute(validIndices[i]).setDiscretizedFlag(true, alg, discretizar.getBins());
                        }

                        _igu.BuildModel.m_variables.m_List.setSelected(false);
                        JOptionPane.showConfirmDialog(_igu.BuildModel,
                                "Proceso terminado",
                                "Discretizar",
                                JOptionPane.PLAIN_MESSAGE);
                        if (isAllDiscretized()) {
                            _igu._stateObserver.setStateBuildModel(2);
                        }

                    } catch (Exception ex) {
                        ex.printStackTrace();
                        JOptionPane.showConfirmDialog(_igu.BuildModel,
                                "Ocurrio un error durante la discretizaci?n",
                                "Discretizar",
                                JOptionPane.PLAIN_MESSAGE);
                    }

                    m_IOThread = null;
                }
            };
            m_IOThread.setPriority(Thread.MIN_PRIORITY);
            m_IOThread.start();
        } else {
            JOptionPane.showConfirmDialog(_igu.BuildModel, "Sistema ocupado", "Discretizar", JOptionPane.PLAIN_MESSAGE);
        }
    }

    public boolean isAllDiscretized() {
        boolean AllVarsDiscretized = true;
        for (int i = 0; i < BD.numAttributes(); i++) {
            if ((BD.attribute(i).isDiscretized() == false)) {
                AllVarsDiscretized = false;
                break;
            }
        }
        return AllVarsDiscretized;
    }

    public void cmdBuildBayesNet() {
        if (isAllDiscretized()) {
            if (m_IOThread == null) {
                m_IOThread = new Thread() {

                    @Override
                    public void run() {
                        if (BD.numAttributes() > 0) {
                            try {
                                setCursor(new Cursor(Cursor.WAIT_CURSOR));
                                _igu._stateObserver.setStateBuildModel(3);
                                _DN = new DetectionNetwork(BD,significaceLevel, 0.5);
                                if (_DN.builtIt()) {

                                    _igu.getLogAndMsgs().addStringToLog("Modelo construido");
                                    JOptionPane.showConfirmDialog(_igu.BuildModel, "Modelo construido", "Construir Modelo", JOptionPane.PLAIN_MESSAGE);
                                    structDN = null;
                                    structDN = _DN.getBayesNetStructure();
                                    _igu.m_Tabs.setEnabledAt(0, true);
                                    _igu.m_Tabs.setEnabledAt(1, true);
                                    _igu._stateObserver.setStateBuildModel(4);
                                    modeloGrafico = new showModel(structDN);
                                    //drawDN();
                                } else {
                                    _igu.getLogAndMsgs().addStringToLog("No fue posible construir el modelo est?tico");
                                    JOptionPane.showConfirmDialog(_igu.BuildModel, "No fue posible construir el modelo", "Construir Modelo", JOptionPane.ERROR_MESSAGE);
                                    _igu._stateObserver.setStateBuildModel(2);
                                    _DN.freeMem();
                                    _DN = null;
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            JOptionPane.showConfirmDialog(_igu.BuildModel,
                                    "No hay variables para construir el modelo",
                                    "Construir Modelo",
                                    JOptionPane.PLAIN_MESSAGE);
                        }
                        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                        m_IOThread = null;
                    }
                };
                m_IOThread.setPriority(Thread.MIN_PRIORITY);
                m_IOThread.start();

            } else {
                System.out.println("Sistema Ocupado");
            }
        } else {
            JOptionPane.showConfirmDialog(_igu.BuildModel,
                    "Faltan variables por discretizar",
                    "Construir Modelo",
                    JOptionPane.PLAIN_MESSAGE);
        }
    }

    public void drawDN() {
        final JFrame jf = new JFrame("Modelo");
        JScrollPane scrollPane = new JScrollPane(modeloGrafico);

        modeloGrafico.graficar();
        jf.getContentPane().setLayout(new BorderLayout());
        jf.getContentPane().add(scrollPane, BorderLayout.CENTER);
        jf.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                jf.dispose();
            }
        });
        jf.setExtendedState(JFrame.NORMAL);
        jf.setPreferredSize(new Dimension(500, 500));
        jf.pack();
        jf.setVisible(true);
    }

    /**Modifica el valor de los l?mites en la estructura validationModel cuando
     * el usuario realiza alg?n cambio desde la interfaz*/
    public void SetUserLimits(int var, double max, double min, double th) {
        validationModel.instance(var).setValue(validationModel.attribute("SPmaxTol"), max);
        validationModel.instance(var).setValue(validationModel.attribute("SPminTol"), min);
        validationModel.instance(var).setValue(validationModel.attribute("Umbral_Cambios"), th);
    }

    /**Crea una objeto chooseSP el cual es una ventana que presenta al usuario las
     * familias contenidas en el archivo de setPoints que se haya cargado en la cual
     * el usuario puede elegir la familia que desea cargar.*/
    public void cmdChooseSP() {
        setSPToValidationModel(0);
        _igu.BuildModel.showVarData(0);
        _igu.BuildModel.updateGraphic(0);

        /*2011
        chooseSP m_chooseSPpanel = new chooseSP(MainFrame.jf,setPoints);
        m_chooseSPpanel.showWindow();
        if (setPointRowSelected!=-1) {
        setSPToValidationModel(setPointRowSelected);
        _igu.BuildModel.showVarData(0);
        _igu.BuildModel.updateGraphic(0);
        }*/
    }

    /**Elimina los datos que esten fuera de rango de las variables seleccionadas*/
    public void cmdRemoveDataOutOfRange() {
        int[] sel = _igu.BuildModel.getSelectedAttributes();
        if (sel.length > 0) {
            //for(int i=0; i<BD.numAttributes();i++)
            for (int i = 0; i < sel.length; i++) {
                if (BD.attribute(sel[i]).isNumeric()) {
                    for (int j = 0; j < BD.numInstances(); j++) {
                        double val = BD.instance(j).value(sel[i]);
                        if ((val < validationModel.instance(sel[i]).value(validationModel.attribute("SPminTol")))
                                || (val > validationModel.instance(sel[i]).value(validationModel.attribute("SPmaxTol")))) {
                            BD.instance(j).setValue(sel[i], Double.NaN);
                            BDforPlot.instance(j).setValue(sel[i], Double.NaN);
                        }
                    }
                }
            }

            //Calcula los nuevos rangos, desviaci?n est?ndar, etc. despu?s de haber eliminado
            //los datos fuera de rango
            setValidationModelStats(false);
            _igu.BuildModel.selectNothingAction();
            _igu.BuildModel.showVarData(sel[sel.length - 1]);
            _igu.BuildModel.updateGraphic(sel[sel.length - 1]);
            _igu.BuildModel.m_variables.m_List.setSelected(false);
        } else {
            JOptionPane.showConfirmDialog(_igu.BuildModel,
                    "No hay variables seleccionadas",
                    "Eliminar datos fuera de rango",
                    JOptionPane.PLAIN_MESSAGE);
        }
    }

    /**Analiza todas las variables y determina cuales contienen menos del pocentaje
     * dado en percent. Muestra al usuario una lista con las variables y le da la
     * opci?n a eliminarlas*/
    public void cmdDeleteVarsWithMissingValues(double percent) {
        AttributeStats attStats = new AttributeStats();
        Vector vars = new Vector();
        for (int j = 0; j < BD.numAttributes(); j++) {
            attStats = BD.attributeStats(j);
            if (attStats.numericStats != null) {
                double temp = (double) (attStats.missingCount) / (double) (BD.numInstances());
                if (temp > percent) {
                    vars.add(j);
                }
            }

        }

        int[] t = new int[vars.size()];
        for (int i = 0; i < t.length; i++) {
            t[i] = Integer.parseInt(vars.get(i).toString());
        }

        _igu.BuildModel.setVarsCheckBox(t);


        if (vars.size() > 0) {
            int opc = JOptionPane.showConfirmDialog(_igu.BuildModel, "Las variables seleccionadas en la lista de variables\n contienen poca informaci?n (<30%)\n ?Desea eliminarlas?", "", JOptionPane.WARNING_MESSAGE);
            /*String lista="";
            for (int i=0; i<t.length; i++) {
            lista=lista+BD.attribute(t[i]).name()+"\n";
            }
            JPanel m_panel=new JPanel(new BorderLayout());
            m_panel.add(new JLabel("Las siguientes variables contiene menos del "+ (percent*100) +" % de informaci?n"),BorderLayout.NORTH);
            m_panel.add(new JScrollPane(new JTextArea(lista,10,10)),BorderLayout.CENTER);
            m_panel.add(new JLabel("?Desea eliminarlas ahora?"),BorderLayout.SOUTH);
            
            int opc=_igu.getLogAndMsgs().showMessage(_igu.BuildModel,
            m_panel,
            "Remover Variables",0);
             */
            if (opc == 0) {
                cmdDeleteVars(t);
            } else {
                return;
            }
        }
    }

    /**Guarda la base de datos inst en un archivo en formato csv.*/
    public void saveBD(Instances inst) {

        UIManager.put("FileChooser.fileNameLabelText", "Nombre del archivo");
        JFileChooser m_FileChooser = new JFileChooser(new File(System.getProperty("user.dir")));

        ExtensionFileFilter ffilter = new ExtensionFileFilter(".csv", "Valores separados por coma (*.csv)");
        m_FileChooser.setFileFilter(ffilter);

        m_FileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        m_FileChooser.setDialogTitle("Guardar datos");

        int returnVal = m_FileChooser.showSaveDialog(_igu.ValidateData);
        if (returnVal == JFileChooser.APPROVE_OPTION) {

            String nameSelected = m_FileChooser.getSelectedFile().getPath();
            String nameSelectedHistorial = m_FileChooser.getSelectedFile().getPath();
            if (!nameSelected.endsWith(".csv")) {
                nameSelected += ".csv";
                nameSelectedHistorial += "_H.csv";
            }
            File sFile = new File(nameSelected);

            if (sFile.exists()) {
                int response = JOptionPane.showConfirmDialog(_igu.ValidateData, "?Sobreescribir el contenido de " + sFile.getName() + "?",
                        "Guardar datos", JOptionPane.WARNING_MESSAGE);
                if (response == JOptionPane.NO_OPTION || response == JOptionPane.CANCEL_OPTION) {
                    return;
                }
            }

            //File sFile = new File(this.fileToSave);
            saveInstancesToFile(sFile, inst);
            //String nameSelectedHistorial = fileToSave + "H";
            //saveHistorial(nameSelectedHistorial);
        }

    }

    public void saveBD2(File dir) {
        File sFile = new File(dir.getParent(), dir.getName());
        /**Guarda la estructura "BD"*/
        saveInstancesToFile(sFile, BD);
    }

    /**Realiza las operaciones de escritura sobre el archivo f, para guardar las
     * instancias inst*/
    protected void saveInstancesToFile(final File f, final Instances inst) {

        /*if (m_IOThread == null) {
        m_IOThread = new Thread() {

        @Override
        public void run() {*/
        try {
            Writer w = new BufferedWriter(new FileWriter(f));
            for (int i = 0; i < inst.numAttributes(); i++) {
                w.write(inst.attribute(i).name());
                if (i < inst.numAttributes() - 1) {
                    w.write(",");
                }
            }
            w.write("\n");

            for (int i = 0; i < inst.numInstances(); i++) {
                w.write(inst.instance(i).toString());
                w.write("\n");
            }
            w.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        /* m_IOThread = null;
        }
        };
        m_IOThread.setPriority(Thread.MIN_PRIORITY);
        m_IOThread.start();
        } else {
        JOptionPane.showMessageDialog(_igu.BuildModel,
        "No es posible guardar en este momento,\n"
        + "Sistema ocupado",
        "Guardar modelo",
        JOptionPane.WARNING_MESSAGE);
        }*/
    }

    /**Calcula el umbral de cambios abruptos que el sistema propone al usuario
     * para la variable cuyo ?ndice es idx*/
    public double computeThreshold(int idx) {
        if (BD.attribute(idx).isNumeric()) {
            Vector diferencias = new Vector();
            double dif = 0;
            int cont = 0;
            double d1 = 0, d2 = 0;
            double limInf = validationModel.instance(idx).value(validationModel.attribute("SPminTol"));
            double limSup = validationModel.instance(idx).value(validationModel.attribute("SPmaxTol"));
            double media = 0, sum = 0;
            for (int i = 1; i < BD.numInstances(); i++) {
                d1 = BD.instance(i - 1).value(idx);
                d2 = BD.instance(i).value(idx);
                if ((!Double.isNaN(d1)) && (!Double.isNaN(d2))) {
                    if ((limInf < d1) && (d1 < limSup) && (limInf < d2) && (d2 < limSup)) {
                        dif = Math.abs(d1 - d2);
                        diferencias.add(dif);
                        sum += dif;
                        cont++;
                    }
                }
            }
            //Media de las diferencias
            media = sum / cont;

            //Calcula la desviaci?n Est?ndar de los datos dentro de los l?mites
            double suma = 0;
            for (int i = 0; i < diferencias.size(); i++) {
                double d = Double.parseDouble(diferencias.get(i).toString());
                suma = suma + Math.pow((d - media), 2);
            }

            double desvStd = Math.sqrt(suma / (diferencias.size() - 1));

            return media + (desvStd * ToleranciaUmbral);
        } else {
            return 0;
        }
    }

    /**Encuentra los datos de la variable idx que sean mayores al umbral th
     * regresa un arreglo de valores booleanos en los que se establece en TRUE si
     * el dato en la instancia representa un cambio abrupto.*/
    public boolean[] validarTendencias(int idx, double th) {
        double d1, d2;
        boolean[] s = new boolean[BDforPlot.numInstances()];
        double limInf = validationModel.instance(idx).value(validationModel.attribute("SPminTol"));
        double limSup = validationModel.instance(idx).value(validationModel.attribute("SPmaxTol"));

        for (int i = 1; i < BDforPlot.numInstances(); i++) {
            d1 = BDforPlot.instance(i - 1).value(idx);
            d2 = BDforPlot.instance(i).value(idx);
            s[i] = false;
            if ((Math.abs(d1 - d2) > th) && (limInf < d1) && (d1 < limSup) && (limInf < d2) && (d2 < limSup) && th > 0) {
                s[i] = true;
            }
        }
        return s;
    }

    public boolean[] validarTendenciasHaar(int idx, int scale, double th) {
        boolean[] s = new boolean[BDforPlot.numInstances()];
        Object[] res = changesDetection.findAbruptChanges(idx, scale, th);
        for (int i = 0; i < res.length; i++) {
            int v = ((Integer) res[i]).intValue();
            if (v < BDforPlot.numInstances()) {
                s[v] = true;
            }
        }
        return s;
    }

    /**Regresa el objeto validationModel*/
    public Instances getValidationModel() {
        return validationModel;
    }

    public void setCursor(Cursor _cursor) {
        Frame browserFrame;
        Component parentComponent;
        parentComponent = _igu.getParent();
        while (parentComponent != null
                && !(parentComponent instanceof Frame)) {
            parentComponent = parentComponent.getParent();
        }
        browserFrame = (Frame) parentComponent;
        browserFrame.setCursor(_cursor);
    }

    public boolean checkVarsToValidate() {
        Iterator it = structDN.iterator();
        while (it.hasNext()) {
            String varName = (String) ((LinkedList) it.next()).get(0);
            if (_igu.ValidateData.getInstances().attribute(varName) == null) {
                return false;
            }

        }
        return true;
    }


    public void cmdValidarBD() {
        if (checkVarsToValidate()) {
            if (m_ValidarThread == null) {
                m_ValidarThread = new Thread() {

                    @Override
                    public void run() {

                        MainFrame.getFrame().setCursor(new Cursor(Cursor.WAIT_CURSOR));
                        _igu._stateObserver.setStateValidateModel(6);

                        m_validate = new validate();
                        m_validate.setInstances(_igu.ValidateData.getInstances());

                        for (int row = 0; row < m_validate.m_data.numInstances(); row++) {
                            for (int col = 0; col < m_validate.m_data.numAttributes(); col++) {
                                m_validate.m_data.instance(row).setErrorType(col, 0);
                                m_validate.m_data.instance(row).setProbableValue(col, Double.NaN);
                                m_validate.m_data.instance(row).setChangeValue(col, false);
                            }
                        }
                        _igu.ValidateData.repaintTable();

                        m_validate.setValidationModel(validationModel, _DN);
                        LinkedList varList = _DN.getVarList();
                        _DN.setP_Value(p_value, Pf_Th);
                        _igu.ValidateData.initProgressBar(m_validate.m_data.numInstances());
                        // _igu._stateObserver.setStateValidateModel(7);
                        m_validate.initValidationProcess(varList, new SigmaLimitsGlobal());
                        JOptionPane.showConfirmDialog(_igu.ValidateData, "Proceso terminado con �xito", "Validar", JOptionPane.PLAIN_MESSAGE);
                        _igu.ValidateData.buildValidationReport(varList);
                        m_ValidarThread = null;
                        MainFrame.getFrame().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                        _igu.ValidateData.finishProgressBar();
                        _igu._stateObserver.setStateValidateModel(7);
                    }
                };
                m_ValidarThread.setPriority(Thread.MIN_PRIORITY);
                m_ValidarThread.start();
            } else {
                JOptionPane.showConfirmDialog(_igu.ValidateData, "Validando datos...", "Sistema ocupado", JOptionPane.WARNING_MESSAGE);
            }
        } else {
            JOptionPane.showConfirmDialog(_igu.ValidateData, "El archivo a validar no contiene todas la variables del modelo.", "Validar", JOptionPane.PLAIN_MESSAGE);
        }
    }

    /**Cambia el dato detectado como error por el dato propuesto por el sistema
     * para todas las celdas detectadas con alg?n tipo de error*/
    public void applyChangesToAllData() {

        LinkedList varList = _DN.getVarList();
        Instances _inst = _igu.ValidateData.getInstances();

        for (int row = 0; row < _inst.numInstances(); row++) {
            for (int var = 0; var < varList.size(); var++) {
                String varName = (String) varList.get(var);
                if (_inst.attribute(varName) != null) {
                    int attIdx = _inst.attribute(varName).index();
                    double t = _inst.instance(row).getProbableValue(attIdx);
                    if (!Double.isNaN(t)) {
                        //se guarda en el historial
                        int originalIdx = _inst.instance(row).getOriginalIdx();
                        int originalAttIdx = m_historial.getInstances().attribute(varName).index();
                        m_historial.getInstances().instance(originalIdx).setProbableValue(originalAttIdx, t);
                        m_historial.getInstances().instance(originalIdx).setErrorType(originalAttIdx, _inst.instance(row).getErrorType(attIdx));
                        //m_historial.getInstances().instance(row).setChangeValue(attIdx,true);

                        _igu.ValidateData.getInstances().instance(row).setValue(attIdx, t);
                        _igu.ValidateData.getInstances().instance(row).setErrorType(attIdx, 0);
                        _igu.ValidateData.getInstances().instance(row).setProbableValue(attIdx, Double.NaN);
                        _igu.ValidateData.getInstances().instance(row).setChangeValue(attIdx, false);
                    }
                }
            }
        }
        _igu.ValidateData.repaintTable();
    }

    public void applyChangesToErrorType(int errorType) {

        LinkedList varList = _DN.getVarList();
        Instances _inst = _igu.ValidateData.getInstances();

        for (int row = 0; row < _inst.numInstances(); row++) {
            for (int var = 0; var < varList.size(); var++) {
                String varName = (String) varList.get(var);
                if (_inst.attribute(varName) != null) {
                    int attIdx = _inst.attribute(varName).index();
                    double t = _inst.instance(row).getProbableValue(attIdx);
                    if (!Double.isNaN(t)) {
                        if (_inst.instance(row).getErrorType(attIdx) == errorType) {
                            //se guarda en el historial
                            int originalIdx = _inst.instance(row).getOriginalIdx();
                            int originalAttIdx = m_historial.getInstances().attribute(varName).index();
                            m_historial.getInstances().instance(originalIdx).setProbableValue(originalAttIdx, t);
                            m_historial.getInstances().instance(originalIdx).setErrorType(originalAttIdx, _inst.instance(row).getErrorType(attIdx));
                            //m_historial.getInstances().instance(row).setChangeValue(attIdx,true);
                            _igu.ValidateData.getInstances().instance(row).setValue(attIdx, t);
                            _igu.ValidateData.getInstances().instance(row).setErrorType(attIdx, 0);
                            _igu.ValidateData.getInstances().instance(row).setProbableValue(attIdx, Double.NaN);
                            _igu.ValidateData.getInstances().instance(row).setChangeValue(attIdx, false);
                        }
                    }
                }
            }
        }
        _igu.ValidateData.repaintTable();
    }

    /**Cambia todos los datos detectados con alg?n tipo de error por el dato
     * propuesto correspondiente en cada celda para las filas seleccionadas.*/
    public void applyChangesToSelectedRow(int[] sel) {

        LinkedList varList = _DN.getVarList();
        Instances _inst = _igu.ValidateData.getInstances();
        for (int row = 0; row < sel.length; row++) {
            for (int var = 0; var < varList.size(); var++) {
                String varName = (String) varList.get(var);
                if (_inst.attribute(varName) != null) {
                    int attIdx = _inst.attribute(varName).index();
                    double t = _inst.instance(sel[row]).getProbableValue(attIdx);
                    if (!Double.isNaN(t)) {
                        int originalIdx = _inst.instance(sel[row]).getOriginalIdx();
                        int originalAttIdx = m_historial.getInstances().attribute(varName).index();
                        m_historial.getInstances().instance(originalIdx).setProbableValue(originalAttIdx, t);
                        m_historial.getInstances().instance(originalIdx).setErrorType(originalAttIdx, _inst.instance(sel[row]).getErrorType(attIdx));
                        //m_historial.getInstances().instance(sel[row]).setChangeValue(attIdx,true);

                        _inst.instance(sel[row]).setValue(attIdx, t);
                        _inst.instance(sel[row]).setErrorType(attIdx, 0);
                        _inst.instance(sel[row]).setProbableValue(attIdx, Double.NaN);
                        _inst.instance(sel[row]).setChangeValue(attIdx, false);
                    }
                }
            }
        }
        _igu.ValidateData.repaintTable();
    }

    /**Cambia los datos seleccionados detectados con alg?n tipo de error por
     * el dato propuesto*/
    public void applyChangesToSelectedData() {
        LinkedList varList = _DN.getVarList();
        Instances _inst = _igu.ValidateData.getInstances();

        for (int row = 0; row < _inst.numInstances(); row++) {
            for (int var = 0; var < varList.size(); var++) {
                String varName = (String) varList.get(var);
                if (_inst.attribute(varName) != null) {
                    int attIdx = _inst.attribute(varName).index();
                    if (_inst.instance(row).changeValue(attIdx)) {
                        double t = _inst.instance(row).getProbableValue(attIdx);
                        if (!Double.isNaN(t)) {

                            int originalIdx = _inst.instance(row).getOriginalIdx();
                            int originalAttIdx = m_historial.getInstances().attribute(varName).index();
                            m_historial.getInstances().instance(originalIdx).setProbableValue(originalAttIdx, t);
                            m_historial.getInstances().instance(originalIdx).setErrorType(originalAttIdx, _inst.instance(row).getErrorType(attIdx));
                            //m_historial.getInstances().instance(row).setChangeValue(attIdx,true);

                            _inst.instance(row).setValue(attIdx, t);
                            _inst.instance(row).setErrorType(attIdx, 0);
                            _inst.instance(row).setProbableValue(attIdx, Double.NaN);
                            _inst.instance(row).setChangeValue(attIdx, false);
                        }
                    }
                }
            }
        }
        _igu.ValidateData.repaintTable();
    }

    /**Muestra le cuadro de di?logo Abrir para seleccionar un archivo de
     * restricciones que ser? utilizado para construir un modelo*/
    public void cmdCargarRestricciones() {
        UIManager.put("FileChooser.fileNameLabelText", "Nombre del archivo");
        JFileChooser m_FileChooser = new JFileChooser(new File(System.getProperty("user.dir")));
        m_FileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        int returnVal = m_FileChooser.showOpenDialog(_igu.BuildModel);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            final File selected = m_FileChooser.getSelectedFile();
            constraintsFileName = selected.getAbsolutePath();
        }
    }

    /**Actualiza los valores: media, desviaci?n est?ndar, rangos de todas la variables
     * despu?s de eliminar registros de la base de datos*/
    public void update() {
        setValidationModelStats(false);
        _igu.BuildModel.showVarData(0);
        _igu.BuildModel.updateGraphic(0);
    }

    /**Guarda la base de datos de historial de acciones*/
    public void saveHistorial(String fileName) {
        m_historial.save(fileName);
    }

    /**Actualiza las redes bayesianas cargadas con la informaci?n de la base de datos
     * que se muestre en la pesta?a de validaci?n*/
    public void updateModel() {
        int response = JOptionPane.showConfirmDialog(_igu.BuildModel,
                "El modelo ser? actualizado con la informaci?n de la BD",
                "Actualizar Modelo",
                JOptionPane.WARNING_MESSAGE);
        if (response == JOptionPane.NO_OPTION || response == JOptionPane.CANCEL_OPTION) {
            m_IOThread = null;
            return;
        }

        //--> _DN.updateModel(m_validate.m_data);
    }

    /**Detiene el proceso de validaci?n*/
    public void stopValidationProcess() {
        if (m_ValidarThread != null) {
            m_ValidarThread.interrupt();
            m_ValidarThread.stop();
            m_ValidarThread = null;
            MainFrame.getFrame().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            _igu.ValidateData.initProgressBar(0);
            _igu.ValidateData.updateProgressBar(0);
            _igu._stateObserver.setStateValidateModel(5);
        }
    }

    public void setParams(double s, double p, double t) {
        significaceLevel = s;
        p_value = p;
        Pf_Th = t;
    }

    /**Muestra la ventana de configuraci?n de par?metros de modelos*/
    public void showModelParams() {
        new modelParameters(significaceLevel, p_value, Pf_Th, this);
    }

    /**Restaura los valores originales de las variables seleccionadas*/
    public void restoreVars(int[] indices) {
        if (indices.length > 0) {
            Arrays.sort(indices);
            for (int i = indices.length - 1; i >= 0; i--) {
                BD.restoreAttribute(m_historial.getInstances(), BD.attribute(indices[i]));
                BDforPlot.restoreAttribute(m_historial.getInstances(), BD.attribute(indices[i]));
            }
            setValidationModelStats(true);
            //calacularLimites();
            //2011 _igu.cmdshowData(BD);
            _igu.cmdshowDataBM(BD);
            //21/02/2011 _igu.cmdSetInstancesValidation(BDforPlot);
            _igu.BuildModel.m_variables.m_List.setSelected(false);
            _igu._stateObserver.setStateBuildModel(1);
        } else {
            JOptionPane.showConfirmDialog(_igu.BuildModel, "Seleccione las variables que desea restaurar", "Restaurar valores", JOptionPane.PLAIN_MESSAGE);
        }
    }

    public boolean validateNumVarsLoaded() {
        int maxAtts = 10;
        if (BD.numAttributes() > maxAtts) {
            return false;
        }
        return true;
    }

    public void cmdLoadData2Validate(final File selected) {
        fileToSave = selected.getAbsolutePath();
        fileToSave = "C" + fileToSave.substring(1);

        if (m_IOThread == null) {
            m_IOThread = new Thread() {

                public void run() {
                    try {
                        _igu.getLogAndMsgs().addStringToLog("Cargando archivo: " + selected.getAbsolutePath());
                        CSVLoader cnv = new CSVLoader();
                        cnv.setSource(selected);
                        Instances ins = cnv.getDataSet();
                        openedFile = selected.getAbsoluteFile();


                        //27/Oct/2011 m_historial = new historial(ins);
                         /*7/nov/2011*/ m_historial = new historial(ins);
                        //if (validateNumVarsLoaded()) {
                        _igu.cmdSetInstancesValidation(ins);
                        _igu._stateObserver.setStateValidateModel(5);
                        /*} else {
                        _igu.raiseException(null,"Error al cargar los datos","muchas variables");
                        }*/
                    } catch (Exception ex) {
                        m_IOThread = null;
                        _igu.getLogAndMsgs().addStringToLog("Error al cargar los datos");
                        _igu.raiseException(ex, "Error al cargar los datos", "Cargar Datos");
                    }
                    m_IOThread = null;
                }
            };
            m_IOThread.setPriority(Thread.MIN_PRIORITY);
            m_IOThread.start();
        } else {
            System.out.println("Sistema Ocupado");
        }
    }

    public void setSudenTreshold(int th) {
    }

    /**Guarda un modelo creado el cual esta compuesto por la estructura validationModel
     * (que se guarda en un archivo csv) y las redes bayesianas construidas que est?n
     * almacenadas en el vector modelos (se guardan en el formato hkb).
     * Al guardar un modelo la memoria utilizada por cada red bayesiana es liberada*/
    public void cmdsaveValidationModel() {
        UIManager.put("FileChooser.fileNameLabelText", "Nombre del directorio");
        JFileChooser m_FileChooser = new JFileChooser(new File(System.getProperty("user.dir")));
        m_FileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        m_FileChooser.setDialogTitle("Guardar modelo de validaci?n");

        int returnVal = m_FileChooser.showSaveDialog(_igu.BuildModel);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File dir = m_FileChooser.getSelectedFile();

            /**Crea el directorio donde se almacenar? el modelo*/
            dir.mkdirs();

            /**Crea el archivo CSV donde se guardara la informaci?n de lim?tes de cada, umbrales de cambios, etc.*/
            File sFile = new File(dir.getAbsolutePath(), dir.getName() + CSVLoader.FILE_EXTENSION);

            /**Guarda la estructura "validationModel"*/
            saveInstancesToFile(sFile, validationModel);

            /**Guarda la rede bayesiana creada*/
            _DN.saveNet(dir.getAbsolutePath());
            _DN.freeMem();//liberar la memoria o no??
            _DN = null;

        }
    }

    /**Llena los datos faltantes de las variables que se encuentren seleccionadas
     * en la IGU. Esta funci?n est? disponible despu?s de la construcci?n de las
     * redes bayesianas ya que se requieren para obtener los valores para los datos
     * faltante*/
    public void cmdFillMissingData2() {
        int[] sel = _igu.BuildModel.getSelectedAttributes();
        if (sel.length > 0) {
            for (int var = 0; var < sel.length; var++) {
                String varName = BD.attribute(sel[var]).name();
                /**verificar si alg?n modelo contiene a la variable*/
                DetectionNetwork currentModel = this._DN;

                for (int row = 0; row < BD.numInstances(); row++) {
                    /**Si el dato para la variable e instancia actual no es missing se
                     * continua con la siguiente instancia para la misma variable*/
                    if (!BD.instance(row).isMissing(BD.attribute(varName))) {
                        continue;
                    }
                    double value = currentModel.getProbableValue(varName, BD, row, true, 0.3);
                    String interval = currentModel.getLabelState(varName, value);

                    BDforPlot.instance(row).setValue(BDforPlot.attribute(varName), value);
                    BD.instance(row).setValue(BD.attribute(varName), interval);
                }
            }
        } else {
            JOptionPane.showConfirmDialog(_igu.BuildModel,
                    "No hay variables seleccionadas",
                    "Llenar Datos Faltantes",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    public boolean loadValidationModels() {
        UIManager.put("FileChooser.fileNameLabelText", "Nombre del directorio");
        JFileChooser m_FileChooser = new JFileChooser(new File(System.getProperty("user.dir")));
        m_FileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        m_FileChooser.setDialogTitle("Abrir modelo de validaci?n");
        int returnVal = m_FileChooser.showOpenDialog(_igu.ValidateData);
        if (returnVal == JFileChooser.APPROVE_OPTION) {

            validationModelfile = m_FileChooser.getSelectedFile();
            File sFile = new File(validationModelfile.getAbsolutePath(), validationModelfile.getName() + CSVLoader.FILE_EXTENSION);
            try {
                MainFrame.getFrame().setCursor(new Cursor(Cursor.WAIT_CURSOR));
                _igu.getLogAndMsgs().addStringToLog("Cargando modelo: " + sFile.getAbsolutePath());
                _DN = null;
                CSVLoader cnv = new CSVLoader();
                cnv.setSource(sFile);
                validationModel = null;
                validationModel = cnv.getDataSet();
                //31Mayo2012 _DN = new DetectionNetwork(sFile.getParent() + "\\" + "DetectionNetwork.hkb", p_value, Pf_Th);
                _DN = new DetectionNetwork(sFile.getParent() + "\\" + "DetectionNetwork.pgmx");     
                structDN = _DN.getBayesNetStructure();

                //_igu._stateObserver.setStateBuildModel(4);
                _igu._stateObserver.setStateValidateModel(4);
                modeloGrafico = new showModel(structDN);
                drawDN();
                _igu.getLogAndMsgs().addStringToLog("Modelo:" + sFile.getName() + " para validaci?n cargado");
                MainFrame.getFrame().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                return true;
            } catch (Exception ex) {
                MainFrame.getFrame().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                _igu.getLogAndMsgs().addStringToLog("Error al cargar el modelo global");
                return false;
            }

        } else {
            return false;
        }
    }

    public void loadMttosFile(File selected) {
        mds = new mttosDataSet();
        mds.loadDataFile(selected);
        mttosTimeLines = mds.buildTimeLine();
        _igu.BuildModel.dvp.setMttosTimeLines(mttosTimeLines);
        boolean s[] = new boolean[BD.numInstances()];
        mttosTimeLines.getArrayEvents(s);
        BD.setInstancesWithMttos(s);
        _igu.BuildModel.dvp.setInstances(BD);
        _igu.BuildModel.mlp.plot.setMttosFlag(true);
        _igu.BuildModel.mlp.plot.updateInstances(BD);

        //_igu.BuildModel.mlp.plot.setMttosFlags(s);
    }

   

    public TimeLinesManager getMttosTimeLines() {
        return mttosTimeLines;
    }

    public void DeleteAllInstancesWithMttos() {
        int[] rowsIdx = BD.getInstancesIdxWithMttos();
        if (rowsIdx.length == BD.numInstances()) {
            JOptionPane.showMessageDialog(_igu.BuildModel,
                    "No es posible eliminar todos los registros!\n",
                    "Eliminar registros",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        Arrays.sort(rowsIdx);
        for (int i = rowsIdx.length - 1; i >= 0; i--) {
            m_historial.regDeleteInstance(BD.instance(rowsIdx[i]).getOriginalIdx());
            BD.delete(rowsIdx[i]);
            BDforPlot.delete(rowsIdx[i]);
        }
    }

    public void markOutliers(int[] selectedAttributes) {
        double val;
        for (int j = 0; j < selectedAttributes.length; j++) {
            double max = validationModel.instance(selectedAttributes[j]).value(validationModel.attribute("SPmaxTol"));
            double min = validationModel.instance(selectedAttributes[j]).value(validationModel.attribute("SPminTol"));
            for (int i = 0; i < this.BD.numInstances(); i++) {
                val = BD.instance(i).value(selectedAttributes[j]);
                if ((val < min || val > max) && (this.BD.instance(i).getErrorType(selectedAttributes[j]) == 0)) {
                    this.BD.instance(i).setErrorType(selectedAttributes[j], 2);
                }
            }
        }
    }
}
