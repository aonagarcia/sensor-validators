
package com.github.aonagarcia.igu;

import com.github.aonagarcia.extras.AttributeStats;
import com.github.aonagarcia.extras.Instances;
import com.github.aonagarcia.extras.Utils;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.JScrollBar;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import javax.swing.JCheckBox;
import javax.swing.JSlider;

public class graphPanel extends JPanel {
    
    Instances m_data;
    int attribIndex;
    AttributeStats as;
    public graphics plot = new graphics();
    double _xmin=Double.NaN,_xmax=Double.NaN,_ymin=Double.NaN,_ymax=Double.NaN;

    /**ScrollBar Horizontal para mover la gr�fica en el eje X*/
    private JScrollBar hsb=new JScrollBar(JScrollBar.HORIZONTAL);
    /**ScrollBar Vertical para mover la gr�fica en el eje Y*/
    private JScrollBar vsb=new JScrollBar(JScrollBar.VERTICAL);
    /**ticks para el zoom*/
    private int ticks=500;
    /**Slider para el zoom en X*/
    private JSlider zoomX =new JSlider( SwingConstants.HORIZONTAL,0,ticks,0);
    /**Slider para el zoom en Y*/
    private JSlider zoomY =new JSlider( SwingConstants.VERTICAL,0,ticks,0);
    /**Almacena la posici�n actual de la barra horizontal*/
    private int hsbActPos=0;
    /**Almacena el desplazamiento de la barra horizontal*/
    private int hsbDesp=0;
    /**Almacena la posici�n actual de la barra vertical*/
    private int vsbActPos=0;
    /**Almacena el desplazamiento de la barra horizontal*/
    private int vsbDesp=0;
    /**Variable de control para la posici�n del zoom en Y*/
    private int sliderYAntPos=0;
    /**Variable de control para la posici�n del zoom en X*/
    private int sliderXAntPos=0;
    
    private double originalMaxX=0;
    private double originalMinX=0;
    
    private double originalMaxY=0;
    private double originalMinY=0;

    private int vsbMin=0;
    private int vsbMax=0;

    private JCheckBox showGrid=new JCheckBox("Grid",true);
    private JCheckBox showLimits=new JCheckBox("L�mites",true);
    private JCheckBox showRef=new JCheckBox("Referencia",true);
    private JCheckBox showRangos=new JCheckBox("Rangos",true);
    
    
    /** Constructor para esta clase. */
    public graphPanel() {
        setLayout(new BorderLayout() );
        
        zoomX.setValue(0);
        zoomX.setPreferredSize(new Dimension(100,20));
        zoomX.setMajorTickSpacing( 1 );
        zoomX.setPaintTicks( false );
        
        zoomY.setValue(0);
        zoomY.setMajorTickSpacing( 1 );
        zoomY.setPaintTicks( false );
        
        hsb.setValue(0);
        vsb.setValue(0);
        
        showGrid.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                int estado=evt.getStateChange();
                if (estado==java.awt.event.ItemEvent.SELECTED)
                    plot.setFlagGrid(true);
                else 
                    plot.setFlagGrid(false);                
                plot.repaint();
            }
        });
        
        showLimits.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                int estado=evt.getStateChange();
                if (estado==java.awt.event.ItemEvent.SELECTED)
                    plot.setFlagLimits(true);
                else
                    plot.setFlagLimits(false);               
                plot.repaint();
            }
        });

         showRef.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                int estado=evt.getStateChange();
                if (estado==java.awt.event.ItemEvent.SELECTED)
                    plot.setFlagRef(true);
                else
                    plot.setFlagRef(false);                
                plot.repaint();
            }
        });
        
         showRangos.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                int estado=evt.getStateChange();
                if (estado==java.awt.event.ItemEvent.SELECTED) 
                    plot.setFlagRangos(true);
                else
                    plot.setFlagRangos(false);                
                plot.repaint();
            }
        });        

        zoomX.addChangeListener(new ChangeListener() {
            public void stateChanged( ChangeEvent e ) {                
                if (((zoomX.getValue()-sliderXAntPos)>0) && (zoomX.getValue()<zoomX.getMaximum())) {
                    _xmax=_xmax-(((double)(originalMaxX-originalMinX)/(double)(ticks*1.0))*(Math.abs(zoomX.getValue()-sliderXAntPos)));
                    sliderXAntPos=zoomX.getValue();
                }
                if ((zoomX.getValue()-sliderXAntPos)<0) {
                    _xmax=_xmax+(((double)(originalMaxX-originalMinX)/(double)(ticks*1.0))*(Math.abs(zoomX.getValue()-sliderXAntPos)));
                    sliderXAntPos=zoomX.getValue();
                }
                introLimites();
            }
        }
        );
        
        zoomY.addChangeListener(new ChangeListener() {
            public void stateChanged( ChangeEvent e ) {
                Double yMax=_ymax;
                if (((zoomY.getValue()-sliderYAntPos)>0) && (zoomY.getValue()<zoomY.getMaximum())) {
                    _ymax=yMax-(((double)(originalMaxY-originalMinY)/(double)(ticks*1.0))*(Math.abs(zoomY.getValue()-sliderYAntPos)));
                    sliderYAntPos=zoomY.getValue();
                }
                if ((zoomY.getValue()-sliderYAntPos)<0) {
                    _ymax=yMax+(((double)(originalMaxY-originalMinY)/(double)(ticks*1.0))*(Math.abs(zoomY.getValue()-sliderYAntPos)));
                    sliderYAntPos=zoomY.getValue();
                }
                introLimites();
            }
        }
        );
        
        /***/

        JPanel escalaX = new JPanel();
        JPanel escalaY = new JPanel();
        JPanel opciones=new JPanel(new GridLayout(4,1,1,1));
        opciones.setBorder(BorderFactory.createTitledBorder("Indicadores"));
        opciones.add(showGrid);
        opciones.add(showLimits);
        opciones.add(showRef);
        opciones.add(showRangos);
        
        escalaX.add(new JLabel("Zoom X"));
        escalaX.add(zoomX);
        
        JLabel e=new JLabel("Zoom Y",SwingConstants.LEFT );
        e.setUI(new com.github.aonagarcia.extras.rotateLabel(false));
        e.setVerticalTextPosition(SwingConstants.TOP);
        e.setVerticalAlignment(SwingConstants.CENTER);
        escalaY.add(e);
        escalaY.add(zoomY);

        plot.setBorder(BorderFactory.createBevelBorder(1));
        plot.setSize(new Dimension(400, 300));
        //JScrollPane grafica = new JScrollPane(plot);
        //grafica.setPreferredSize(new Dimension(800, 600));
        JPanel panel=new JPanel(new BorderLayout());
        panel.add(plot,BorderLayout.CENTER);
        panel.add(escalaY,BorderLayout.WEST);
        panel.add(opciones,BorderLayout.EAST);
        
        
        AdjustmentListener adjustmentListenerHSB = new AdjustmentListener() {
            public void adjustmentValueChanged(AdjustmentEvent adjustmentEvent) {
                
                Double xmin=_xmin;
                Double xmax=_xmax;
                
                hsbDesp=adjustmentEvent.getValue()-hsbActPos;
                _xmin=xmin+hsbDesp;
                _xmax=xmax+hsbDesp;
                hsbActPos=adjustmentEvent.getValue();                
                introLimites();
            }
        };

        AdjustmentListener adjustmentListenerVSB = new AdjustmentListener() {
            public void adjustmentValueChanged(AdjustmentEvent adjustmentEvent) {
                
                Double ymin=_ymin;
                Double ymax=_ymax;
                
                vsbDesp=adjustmentEvent.getValue()-vsbActPos;
                _ymin=ymin+vsbDesp;
                _ymax=ymax+vsbDesp;
                vsbActPos=adjustmentEvent.getValue();
                
                introLimites();
            }
        };
        
        
        hsb.setValues(0,1,0,(int)originalMaxX-1);
        hsb.addAdjustmentListener(adjustmentListenerHSB);
        hsb.setValue(0);
        
        vsb.setValues(0,1,0,(int)originalMaxY-1);
        vsb.addAdjustmentListener(adjustmentListenerVSB);

        add(escalaX, BorderLayout.NORTH);
        add(hsb, BorderLayout.SOUTH);
        add(vsb, BorderLayout.EAST);
        add(panel,  BorderLayout.CENTER);
    }
    
    private void introLimites() {
        plot.setLimits(new double[] {_xmin,_xmax, _ymin, _ymax});
        plot.repaint();
    }
    
    public double[] calcularlimites(int[] columnas) {
        double minx = 1, maxx = m_data.numInstances();
        double margen = 0.1;
        double[] a_min = new double[columnas.length], a_max= new double[columnas.length];
        double[] mins_atts = new double[columnas.length], maxs_atts = new double[columnas.length];
        double minimo = 1000;
        double maximo = -1000;
        for (int i = 0; i < columnas.length; i++) {
            as = m_data.attributeStats(columnas[i]);
            if(as.numericStats == null)
                continue;
            a_min[i] = as.numericStats.min;
            a_max[i] = as.numericStats.max;
            if(Utils.sm(a_min[i], minimo) )
                minimo = a_min[i];
            if(Utils.gr(a_max[i], maximo) )
                maximo = a_max[i];
            mins_atts[i] = a_min[i] - margen * (a_max[i] - a_min[i]);
            maxs_atts[i] = a_max[i] + margen * (a_max[i] - a_min[i]);
        }
        _xmin=(int)minx;
        _xmax=(int)maxx;
        _ymin=Utils.roundDouble(minimo, 3);
        _ymax=Utils.roundDouble(maximo, 3);
        
        originalMaxX=maxx;
        originalMinX=minx;
        originalMaxY=maximo;
        originalMinY=minimo;

        vsbMin=0;
        vsbMax=(int)Math.abs(originalMaxY-originalMinY);
        vsb.setMaximum(vsbMax);        
        double[] limites = {minx, maxx, minimo, maximo};        
        return limites;
    }
    
    public void setInstances(Instances newins) {
        attribIndex = 0;
        m_data = newins;
        as = null;
        
        plot.setInstances(newins);
        
        int[] aux = {0};
        plot.setAttributestoPlot(aux);
        _xmin=0;
        _xmax=newins.numInstances();
        originalMaxX=_xmax;

        if(newins.attribute(0).isNumeric() ) {
            _ymin=m_data.attributeStats(0).numericStats.min;
            _ymax=m_data.attributeStats(0).numericStats.max;                     
            zoomX.setMinimum(0);            
            zoomX.setValue(0);            
            vsb.setMaximum(vsbMax);
            hsb.setMaximum((int)originalMaxX);
            plot.setLimits(new double[] {_xmin, _xmax, _ymin,_ymax});
        }
    }
    
    public void resetControls() {
        zoomX.setValue(0);
        zoomY.setValue(0);
        hsb.setValue(0);
        vsb.setValue(0);
    }
    
    public void setEnableComponents(boolean b)
    {
        this.hsb.setEnabled(b);
        this.zoomX.setEnabled(b);  
        this.vsb.setEnabled(b);
        this.zoomY.setEnabled(b);
        this.showGrid.setEnabled(b);
        this.showLimits.setEnabled(b);
        this.showRangos.setEnabled(b);
        this.showRef.setEnabled(b);
    }

    public void setSize(int width, int height)
    {
        //grafica.setPreferredSize(new Dimension(800, 600));
    }
}
