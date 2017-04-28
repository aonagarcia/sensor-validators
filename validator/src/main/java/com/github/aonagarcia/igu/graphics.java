package com.github.aonagarcia.igu;

import com.github.aonagarcia.extras.Instances;
import com.github.aonagarcia.extras.Utils;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;

public class graphics extends JPanel {

    /** Almacenar los datos para cada eje en una matriz */
    final int ox = 0, oy = 0;
    private double[][] matriz;
    private double[][] x_values;
    private double[][] y_values;
    private String[] class_values;
    private boolean[] cambioBrusco;
    private boolean[] mttos;
    private boolean[] outLiersLocales = null;
    private boolean showGrid = true;
    private boolean showLimits = true;
    private boolean showRef = true;
    private boolean showRangos = true;
    private boolean showThDWT = false;
    private boolean showOutOfRange = true;
    private boolean mttosFlag = false;
    /**Bandera para saber si se debe o no graficar los datos en caso de que estos sean nominales*/
    private boolean graficar = false;
    /** Margen para que los datos se distingan de los ejes */
    private final double margen = 0.1;
    /** Vector para almacenar los l�mites para el plano cartesiano */
    private double[] limits;
    //private Color   missingColor= new Color(223,223,223);
    private Color outOfRangeColor = new Color(255, 0, 0);
    private Color correctColor = new Color(0, 0, 255);
    private Color userRangeLinesColor = new Color(255, 128, 0);
    private Color dataRangeLinesColor = new Color(128, 128, 0);
    private Color SetPointMaxColor = new Color(100, 80, 50);
    private Color SetPointMidColor = new Color(100, 80, 50);
    private Color SetPointMinColor = new Color(100, 80, 50);
    private Color ThDWTColor = new Color(100, 0, 0);
    private Color windowColor = new Color(100, 100, 0);
    private Color outLiersLocalesColor = new Color(255, 50, 50);
    /** Valores m�ximo y minimo en cada eje */
    private double minimox, maximox, minimoy, maximoy;
    /** N�mero total de columnas que ser�n graficadas*/
    private int columnas;
    /** La matriz de datos en formato de Weka */
    private Instances m_data2 = null;
    private double[][] y_values2;
    /** Los �ndices para saber cu�les son las columnas que se graficar�n */
    private int[] idx;
    private double UserMaxRange = Double.NaN;
    private double UserMinRange = Double.NaN;
    private double SetPointMax = Double.NaN;
    private double SetPointMid = Double.NaN;
    private double SetPointMin = Double.NaN;
    private double ThDWT = Double.NaN;
    private double DataRangeMax = Double.NaN;
    private double DataRangeMin = Double.NaN;
    private boolean m_graphLocalWindow = false;
    private int m_localValidationWindowIni;
    private int m_localValidationWindowFin;
    double cxI;
    double cxF;
    private int selectedPoint;
    private boolean showSegmentsFlag=false;
    private int[] segmentsPos;

    public graphics() {
        setLayout(new BorderLayout());
    }

    public void setM_graphLocalWindow(boolean m_graphLocalWindow) {
        this.m_graphLocalWindow = m_graphLocalWindow;
    }

    public void setM_localValidationWindowFin(int m_localValidationWindowFin) {
        this.m_localValidationWindowFin = m_localValidationWindowFin;
    }

    public void setM_localValidationWindowIni(int m_localValidationWindowIni) {
        this.m_localValidationWindowIni = m_localValidationWindowIni;
    }

    /*Establece la bandera graficar en verdadero o falso, dependiendo de su valor se grafican o no los vlaores de una variable*/
    public void setGraficarFlag(boolean b) {
        graficar = b;
    }

    public void setMttosFlag(boolean b) {
        mttosFlag = b;
    }
    /*Establece el valor del limite superior para graficarlo*/

    public void setUserMaxRange(double r) {
        this.UserMaxRange = r;
    }

    /*Establece el valor del limite inferior para graficarlo*/
    public void setUserMinRange(double r) {
        this.UserMinRange = r;
    }

    /*Establece los valores de los Setpoints para graficarlos*/
    public void setSetPoints(double spMax, double spMid, double spMin) {
        this.SetPointMax = spMax;
        this.SetPointMid = spMid;
        this.SetPointMin = spMin;
    }

    /*Establece los valores de los rangos para graficarlos*/
    public void setDataRanges(double spMax, double spMin) {
        this.DataRangeMax = spMax;
        this.DataRangeMin = spMin;
    }

    public void setThDWT(double Th) {
        this.ThDWT = Th;
    }
    /*Aigna un arreglo de banderas que contiene los datos donde se presentaron cambios bruscos en los datos*/

    public void setCambiosBruscosFlags(boolean[] s) {
        cambioBrusco = s;
    }

    public void setMttosFlags(boolean[] s) {
        mttos = s;
    }

    public void setOutLiersFlags(boolean[] s) {
        outLiersLocales = s;
    }

    /**
     * M�todo para especificar cuales ser�an los atributos o columnas
     * para graficar.
     * @param indexes Vector con los indices de los atributos deseados.
     */
    public void setAttributestoPlot(int[] indexes) {
        columnas = indexes.length;
        idx = indexes;

        x_values = new double[columnas][m_data2.numInstances()];
        //if(m_data != null) y_values = new double[columnas][m_data.numInstances()];

        y_values2 = new double[columnas][m_data2.numInstances()];
        /** Almacenar la informaci�n en las matrices correspondientes */
        for (int i = 0; i < columnas; i++) {
            for (int j = 0; j < m_data2.numInstances(); j++) {
                x_values[i][j] = j + 1;
                y_values2[i][j] = m_data2.instance(j).value(idx[i]);
            }
        }
        //en otro array colocamos la clase a la cual corresponde cada instancia
        class_values = new String[m_data2.numInstances()];
        int clase = m_data2.attribute("Clase") != null ? m_data2.attribute("Clase").index() : -1;
        if (clase > 0) {
            for (int j = 0; j < m_data2.numInstances(); j++) {
                class_values[j] = m_data2.instance(j).attribute(clase).value((int) m_data2.instance(j).value(clase));
            }
        }
    }

    public void setFlagGrid(boolean b) {
        this.showGrid = b;
    }

    public void setFlagLimits(boolean b) {
        this.showLimits = b;
    }

    public void setFlagRef(boolean b) {
        this.showRef = b;
    }

    void setFlagRangos(boolean b) {
        this.showRangos = b;
    }

    /**
     * M�todo para indicar el conjunto de datos.
     *
     * @param newins El conjunto de datos (instancias).
     */
    public void setInstances(Instances newins) {
        m_data2 = newins;
    }

    public void setInstancesArray(double[][] doubles) {
        matriz = doubles;
    }

    public void setScaletoPlot(int[] indexes) {
        int scales = indexes.length;

        x_values = new double[scales][matriz[0].length];
        y_values2 = new double[scales][matriz[0].length];
        for (int i = 0; i < scales; i++) {
            for (int j = 0; j < matriz[i].length; j++) {
                x_values[i][j] = j + 1;
                y_values2[i][j] = matriz[indexes[i]][j];
            }
        }
    }

    public void drawCircle(Graphics g, double pt1_x, double pt1_y, double pt2_x, double pt2_y) {
        g.setColor(Color.YELLOW);
        g.drawOval((int) pt1_x, (int) (pt1_y < pt2_y ? pt1_y : pt2_y), (int) Math.abs(pt1_x - pt2_x), (int) Math.abs(pt1_y - pt2_y));
    }

    /**
     * M�todo para graficar los datos.
     * @param g El contexto de Gr�ficos.
     */
    @Override
    public void paintComponent(Graphics g) {
        final int ancho = this.getWidth(), alto = this.getHeight();
        // limpiar la pantalla
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, ancho, alto);
        try {
            if (graficar) {

                final int ox = ancho / 12, oy = alto / 12;
                final int margenplano = 20;
                int diam = 10;
                int diamBad = diam * 2;
                int diamAux = diam;
                g.setFont(new Font("Serif", Font.PLAIN, 9));

                // Ser�n 10 grid lines en cada eje
                int grids = 10;
                double lima = (minimox - minimox) * ((ancho - margenplano - (ox + margenplano)) / (maximox - minimox)) + ox + margenplano;
                double limb = (maximox - minimox) * ((ancho - margenplano - (ox + margenplano)) / (maximox - minimox)) + ox + margenplano;

                double limc = (minimoy - minimoy) * ((alto - margenplano - (oy + margenplano)) / (maximoy - minimoy)) + oy + margenplano;
                double limd = (maximoy - minimoy) * ((alto - margenplano - (oy + margenplano)) / (maximoy - minimoy)) + oy + margenplano;
                double heap1 = (limb - lima) / grids;
                double heap2 = (limd - limc) / grids;
                double tickx = minimox, ticky = minimoy;
                double heapreal_x = (maximox - minimox) / grids;
                double heapreal_y = (maximoy - minimoy) / grids;

                double UserMaxRangeLine = Double.NaN,
                        UserMinRangeLine = Double.NaN,
                        SetPointMaxLine = Double.NaN,
                        SetPointMidLine = Double.NaN,
                        SetPointMinLine = Double.NaN,
                        DataRangeMaxLine = Double.NaN,
                        DataRangeMinLine = Double.NaN,
                        ThDWTLine = Double.NaN;



                for (int i = 0; i <= grids; i++) {
                    /*Dibuja el grid*/
                    if (this.showGrid) {
                        g.setColor(Color.LIGHT_GRAY);
                        int cont = 0;
                        for (int j = ox; j < ancho; j += 1) {
                            cont++;
                            if (cont % 2 == 0) {
                                continue;
                            }
                            g.drawLine(j, alto - (int) limc, j, alto - (int) limc);
                        }

                        cont = 0;
                        for (int j = 0; j < alto - oy + 1; j += 1) {
                            cont++;
                            if (cont % 2 == 0) {
                                continue;
                            }
                            g.drawLine((int) lima, j, (int) lima, j);
                        }
                    }

                    g.setColor(Color.BLACK);
                    //valores del eje X
                    g.drawString(String.valueOf((int) Utils.roundDouble(tickx, 0)), (int) lima, alto - (int) ((double) oy * 0.4));
                    g.drawLine((int) lima, alto - oy + 1, (int) lima, (alto - oy) - 4);
                    //valores de eje Y
                    String s;
                    if (showThDWT)                    
                        s=String.format("%4.2g",Utils.roundDouble(ticky, 0));
                    else
                        s=String.valueOf(Utils.roundDouble(ticky, 0));
                    
                    g.drawString(s, (int) (0.1 * ox / 1), alto - (int) limc + 3);
                    g.drawLine((int) ox - 1, alto - (int) limc, (int) ox + 3, alto - (int) limc);

                    lima += heap1;
                    limc += heap2;
                    tickx += heapreal_x;
                    ticky += heapreal_y;
                }

                if (!Double.isNaN(UserMaxRange)) {
                    UserMaxRangeLine = (UserMaxRange - minimoy) * ((alto - margenplano - (oy + margenplano)) / (maximoy - minimoy)) + oy + margenplano;
                }
                if (!Double.isNaN(UserMinRange)) {
                    UserMinRangeLine = (UserMinRange - minimoy) * ((alto - margenplano - (oy + margenplano)) / (maximoy - minimoy)) + oy + margenplano;
                }

                if (this.showSegmentsFlag) {
                    g.setColor(Color.GREEN);
                    for(int ii=0; ii<this.segmentsPos.length;ii++)
                    {
                        cxI = (this.segmentsPos[ii] - minimox) * ((ancho - margenplano - (ox + margenplano)) / (maximox - minimox)) + ox + margenplano;
                        g.drawLine((int) cxI, 0, (int) cxI, alto - 10);
                    }
                }

                if (this.m_graphLocalWindow) {
                    g.setColor(windowColor);
                    cxI = (this.m_localValidationWindowIni - minimox) * ((ancho - margenplano - (ox + margenplano)) / (maximox - minimox)) + ox + margenplano;
                    cxF = (this.m_localValidationWindowFin - minimox) * ((ancho - margenplano - (ox + margenplano)) / (maximox - minimox)) + ox + margenplano;
                    g.drawLine((int) cxI, 0, (int) cxI, alto - 10);
                    g.drawLine((int) cxF, 0, (int) cxF, alto - 10);
                    g.setColor(userRangeLinesColor);
                    g.drawString("LS", (int) cxI, alto - (int) UserMaxRangeLine);
                    g.drawLine((int) cxI, alto - (int) UserMaxRangeLine, (int) cxF, alto - (int) UserMaxRangeLine);
                    g.drawString("LI", (int) cxI, alto - (int) UserMinRangeLine);
                    g.drawLine((int) cxI, alto - (int) UserMinRangeLine, (int) cxF, alto - (int) UserMinRangeLine);
                }

                if (this.showLimits) {
                    g.setColor(userRangeLinesColor);
                    g.drawString("LS", (int) ox + 5, alto - (int) UserMaxRangeLine);
                    g.drawLine((int) ox, alto - (int) UserMaxRangeLine, (int) ancho, alto - (int) UserMaxRangeLine);

                    g.setColor(userRangeLinesColor);
                    g.drawString("LI", (int) ox + 5, alto - (int) UserMinRangeLine);
                    g.drawLine((int) ox, alto - (int) UserMinRangeLine, (int) ancho, alto - (int) UserMinRangeLine);
                }

                if (this.showThDWT) {
                    if (!Double.isNaN(ThDWT)) {
                        ThDWTLine = (ThDWT - minimoy) * ((alto - margenplano - (oy + margenplano)) / (maximoy - minimoy)) + oy + margenplano;
                        g.setColor(ThDWTColor);
                        g.drawString("Th", (int) ox + 5, alto - (int) ThDWTLine);
                        g.drawLine((int) ox, alto - (int) ThDWTLine, (int) ancho, alto - (int) ThDWTLine);
                    }
                }
                // graficar los valores
                for (int i = 0; i < x_values.length; i++) {
                    double coordx = 0, coordy2 = 0, xb4 = 0, yb4 = 0, y2b4 = 0;
                    int start = 0, end = x_values[0].length;

                    /**
                     * La siguiente condici�n sirve para optimizar ligeramente el despliegue de los valores de
                     * acuerdo a los intervalos que se eligen en la clase MultipleListPlot.
                     */
                    if (limits != null) {
                        if (limits[0] >= 0) {
                            start = (int) limits[0];
                        }
                        if (limits[1] <= end) {
                            end = (int) limits[1];
                        }
                    }


                    for (int j = start; j < end; j++) {
                        /**
                         * F�rmula para remapear un vector a un cierto intervalo [a, b]
                         * nuevo valor = (valor actual - min) * ( (b - a) / (max - min) ) + a
                         */
                        coordx = (x_values[i][j] - minimox) * ((ancho - margenplano - (ox + margenplano))
                                / (maximox - minimox)) + ox + margenplano;

                        coordy2 = (y_values2[i][j] - minimoy) * ((alto - margenplano - (oy + margenplano))
                                / (maximoy - minimoy)) + oy + margenplano;



                        // para unir los puntos con rectas
                    /*2011*/ if (xb4 >= ox) {
                            if (j > 0) {
                                if (!Double.isNaN(coordy2)) {
                                    if (!Double.isNaN(y2b4)) {
                                        /*if ((cambioBrusco!=null) &&(cambioBrusco[j]==true)) {
                                        g.setColor(Color.RED);
                                        g.drawLine( (int)coordx, alto - (int)coordy2, (int)xb4, alto - (int)y2b4 );
                                        }else*/ {
                                            g.setColor(Color.BLUE);
                                            g.drawLine((int) coordx, alto - (int) coordy2, (int) xb4, alto - (int) y2b4);
                                        }
                                    }
                                }
                            }
                        }

                        xb4 = coordx;
                        y2b4 = coordy2;

                        /*2011*/
                        if (xb4 >= ox) {
                            if (!Double.isNaN(coordy2)) {
                                int x1=selectedPoint==j?(int) (coordx - 0.5 * diam / 1):(int) (coordx - 0.5 * diam / 2);
                                int y1=selectedPoint==j?(int) (alto - (int) (coordy2) - 0.5 * diam / 1):(int) (alto - (int) (coordy2) - 0.5 * diam / 2);
                                int x2=selectedPoint==j?diam / 1:diam / 2;
                                int y2=selectedPoint==j?diam / 1:diam / 2;
                                if ((showOutOfRange) && ((coordy2 > UserMaxRangeLine) || (coordy2 < UserMinRangeLine))) {
                                    g.setColor(outOfRangeColor);
                                    g.fillOval(x1, y1, x2, y2);
                                } else {
                                    if ((cambioBrusco != null) && (cambioBrusco[j] == true)) {
                                        g.setColor(Color.RED);
                                        g.drawOval((int) (coordx - 0.5 * (diam / 2) - 1), (int) (alto - (int) (coordy2) - 0.5 * (diam / 2) - 1), (diam / 2) + 1, (diam / 2) + 1);
                                        g.setColor(Color.RED);
                                        g.fillOval((int) (coordx - 0.5 * diam / 3), (int) (alto - (int) (coordy2) - 0.5 * diam / 3), diam / 3, diam / 3);
                                    } else {
                                        if ((outLiersLocales != null) && (outLiersLocales[j] == true)) {
                                            g.setColor(outLiersLocalesColor);
                                            //g.fillOval((int) (coordx - 0.5 * diam / 2), (int) (alto - (int) (coordy2) - 0.5 * diam / 2), diam / 2, diam / 2);
                                            g.fillOval(x1, y1, x2, y2);
                                        } else {
                                            g.setColor(correctColor);
                                            //g.fillOval((int) (coordx - 0.5 * diam / 2), (int) (alto - (int) (coordy2) - 0.5 * diam / 2), diam / 2, diam / 2);
                                            g.fillOval(x1, y1, x2, y2);
                                        }
                                    }
                                    //if (this.mttosFlag && mttos[j] == true) {
                                    if (this.mttosFlag &&  m_data2.instance(j).isM_HasMtto() == true) {

                                        g.setColor(new Color(255, 0,0, 50));
                                        /*int[] xs={(int)coordx-4,(int)coordx,(int)coordx+4};
                                        int[] ys={alto - (int) coordy2+7,alto - (int) coordy2+2,alto - (int) coordy2+7};
                                        g.fillPolygon(xs, ys, 3);
                                         * g.drawLine((int) coordx,alto-oy , (int) coordx, alto - (int) coordy2);
                                         */
                                        g.fillRect((int) (coordx - 0.5 * diam / 2), 0, diam / 2, alto-oy);
                                        //g.drawLine((int) coordx, alto - oy, (int) coordx, 0);
                                    }
                                }
                            }
                        }
                    }
                }

                // graficar los ejes
                g.setColor(Color.BLACK);
                for (int i = 0; i < 1; i++) {
                    g.drawLine(ox + (i - 1), alto - oy + (i - 1), ancho, alto - oy + (i - 1));// Eje x
                    g.drawLine(ox + (i - 1), 0, ox + (i - 1), alto - 10);// Eje y
                }

                //Para dibujar las lineas de los rangos definidos por el usuario
                if (this.showRef) {
                    if (!Double.isNaN(SetPointMax)) {
                        SetPointMaxLine = (SetPointMax - minimoy) * ((alto - margenplano - (oy + margenplano)) / (maximoy - minimoy)) + oy + margenplano;
                        g.setColor(SetPointMaxColor);
                        g.drawString("VRMax", (int) ox + 5, alto - (int) SetPointMaxLine);
                        g.drawLine((int) ox, alto - (int) SetPointMaxLine, (int) ancho, alto - (int) SetPointMaxLine);
                    }
                    if (!Double.isNaN(SetPointMid)) {
                        SetPointMidLine = (SetPointMid - minimoy) * ((alto - margenplano - (oy + margenplano)) / (maximoy - minimoy)) + oy + margenplano;
                        g.setColor(SetPointMidColor);
                        g.drawString("VRMedio", (int) ox + 5, alto - (int) SetPointMidLine);
                        g.drawLine((int) ox, alto - (int) SetPointMidLine, (int) ancho, alto - (int) SetPointMidLine);
                    }
                    if (!Double.isNaN(SetPointMin)) {
                        SetPointMinLine = (SetPointMin - minimoy) * ((alto - margenplano - (oy + margenplano)) / (maximoy - minimoy)) + oy + margenplano;
                        g.setColor(SetPointMinColor);
                        g.drawString("VRMin", (int) ox + 5, alto - (int) SetPointMinLine);
                        g.drawLine((int) ox, alto - (int) SetPointMinLine, (int) ancho, alto - (int) SetPointMinLine);
                    }
                }
                if (!Double.isNaN(DataRangeMax)) {
                    DataRangeMaxLine = (DataRangeMax - minimoy) * ((alto - margenplano - (oy + margenplano)) / (maximoy - minimoy)) + oy + margenplano;
                }
                if (!Double.isNaN(DataRangeMin)) {
                    DataRangeMinLine = (DataRangeMin - minimoy) * ((alto - margenplano - (oy + margenplano)) / (maximoy - minimoy)) + oy + margenplano;
                }

                if (this.showRangos) {
                    g.setColor(dataRangeLinesColor);
                    g.drawString("Max", (int) ox + 5, alto - (int) DataRangeMaxLine);
                    g.drawLine((int) ox, alto - (int) DataRangeMaxLine, (int) ancho, alto - (int) DataRangeMaxLine);

                    g.setColor(dataRangeLinesColor);
                    g.drawString("Min", (int) ox + 5, alto - (int) DataRangeMinLine);
                    g.drawLine((int) ox, alto - (int) DataRangeMinLine, (int) ancho, alto - (int) DataRangeMinLine);

                }

            }
        } catch (Exception e) {
            e.getMessage();
        }
    }

    /**
     * M�todo para establecer los l�mites para el plano cartesiano.
     * @param lims El vector que contiene los l�mites.
     */
    public void setLimits(double[] lims) {
        double minx = lims[0] - (lims[1] * margen), maxx = lims[1] * (1 + margen);
        double miny = lims[2] - (lims[3] * margen), maxy = lims[3] * (1 + margen);
        minimox = lims[0];
        maximox = lims[1];
        if (Double.isNaN(UserMinRange) && Double.isNaN(UserMaxRange)) {
            minimoy = lims[2];
            maximoy = lims[3];
        } else {
            minimoy = lims[2] < UserMinRange ? lims[2] : UserMinRange;
            maximoy = lims[3] > UserMaxRange ? lims[3] : UserMaxRange;
        }
        limits = new double[]{minx, maxx, miny, maxy};
    }

    public void setFlagThDWT(boolean b) {
        this.showThDWT = b;
    }

    public void setFlagOutOfRange(boolean b) {
        this.showOutOfRange = b;
    }

    public void updateInstances(Instances _ins)
    {
        this.m_data2=_ins;
    }

    void highlightSelectedPoint(int i) {
        selectedPoint=i;
    }

    public void showSegments(boolean f)
    {
        showSegmentsFlag=f;
    }

    public void setSegmentsPos(int[] pos) {
        segmentsPos=pos;
    }
}
