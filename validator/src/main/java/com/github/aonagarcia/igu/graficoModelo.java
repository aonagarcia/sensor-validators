/*
 * graficoModelo.java
 *
 * Created on 11 de marzo de 2011, 05:24 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.github.aonagarcia.igu;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.Iterator;

import java.util.LinkedList;
import java.util.Vector;



/**
 *
 * @author Javier
 */
public class graficoModelo extends Canvas implements MouseMotionListener{
    
    private LinkedList<graphicNode> listaNodos= new LinkedList<graphicNode>();
    private graphicNode nodoMoving=null;    
    private int xAnteriorRaton;
    private int yAnteriorRaton;
    private Vector estructura;
    


    /** Creates a new instance of graficoModelo */
    public graficoModelo() {
        addMouseMotionListener(this);
    }

    public void addNode(graphicNode n)
    {
        listaNodos.add(n);
    }
    
    @Override
    public  Dimension getPreferredSize()
    {
        return new Dimension(1000,1000);
    }
    
    public graphicNode findNodeInList(String nodeName)
    {
        Iterator it=listaNodos.iterator();
        while (it.hasNext())
        {
            graphicNode node=(graphicNode)it.next();
            if (node.getName().equalsIgnoreCase(nodeName))
            {               
                return node;    
            }
        }        
        return null;
    }
    
    @Override
    public void paint(Graphics g)
    {        
         BufferedImage imagen = new BufferedImage (getWidth(), getHeight(),BufferedImage.TYPE_INT_RGB);
         Graphics gImagen=imagen.getGraphics();
         gImagen.fillRect(0,0,1000,1000);
        //Primero se dibujan las relaciones
        Iterator it2=estructura.iterator();
        while (it2.hasNext())
        {
            LinkedList l =(LinkedList)it2.next();
            Iterator itList=l.iterator();               
            graphicNode nodeFrom=findNodeInList((String)itList.next());
            while (itList.hasNext())
            {
                graphicNode nodeTo=findNodeInList((String)itList.next());
                drawRelation(gImagen,nodeFrom,nodeTo);   
            }
        }        
       
        for(graphicNode node:listaNodos)
            node.drawNode(gImagen);
        
       g.drawImage(imagen, 0, 0, this);
    }
    
    @Override
    public void update (Graphics g)
   {
      paint (g);
   }

    
    public void mouseDragged(MouseEvent e) {
        // Si comienza el arrastre ...
        if (nodoMoving == null)
        {
            // Se guardan las posiciones del rat�n
            xAnteriorRaton = e.getX();
            yAnteriorRaton = e.getY();
            // y se marca que ha comenzado el arrastre.
            nodoMoving = dameFigura(e);
        }
        else
        {
            // Si ya hab�a empezado el arrastre, se calculan las nuevas
            // coordenadas del rect�ngulo
            Point p=new Point(nodoMoving.getCenterX() + (e.getX() - xAnteriorRaton),nodoMoving.getCenterY() + (e.getY() - yAnteriorRaton));
            nodoMoving.setPosition(p);

            // Se guarda la posici�n del rat�n para el siguiente c�lculo
            xAnteriorRaton = e.getX();
            yAnteriorRaton = e.getY();

            // y se manda repintar el Canvas
            repaint();
        }        
    }

   private graphicNode dameFigura(MouseEvent e)
    {
        for (graphicNode nodo : listaNodos)
        {
            if (nodo.isInsideEllipse(new Point(e.getX(), e.getY())))
            {
                return nodo;
            }
        }

        return null;
    }
    
    public void mouseMoved(MouseEvent e) {
        nodoMoving = null;
    }
    
    public void drawRelation(Graphics g, graphicNode from, graphicNode to) {
        double ang=0.0, angSep=0.0;
        double tx,ty;
        int dist=0;
        Point punto1=null,punto2=null;
        
        punto1=new Point(from.getCenterX(),from.getCenterY());        
        punto2=new Point(to.getCenterX(),to.getCenterY());
        
        dist=10;
        
/* (la coordenadas de la ventana es al revez)
calculo de la variacion de "x" y "y" para hallar el angulo
 **/
        
        ty=-(punto1.y-punto2.y)*1.0;
        tx=(punto1.x-punto2.x)*1.0;
//angulo
        ang=Math.atan(ty/tx);
        
        
        
        if(tx<0) {// si tx es negativo aumentar 180 grados
            ang+=Math.PI;
        }
        
        //calcular el punto de intersecci�n con el c�rculo        
        //int pcx=((int)(to.radio*Math.cos(ang)))+to.getCenterX(); 
        //int pcy=((-1)*(int)(to.radio*Math.sin(ang)))+to.getCenterY(); 
               
        //calcular el punto de intersecci�n con la elipse
        int pcx=((int)(50*Math.cos(ang)))+to.getCenterX(); 
        int pcy=((-1)*(int)(25*Math.sin(ang)))+to.getCenterY(); 

        Point pc=new Point(pcx,pcy);
        Point p1=new Point(),p2=new Point(),punto=pc;
        
        angSep=25.0;
        
        p1.x=(int)(punto.x+dist*Math.cos(ang-Math.toRadians(angSep)));
        p1.y=(int)(punto.y-dist*Math.sin(ang-Math.toRadians(angSep)));
        p2.x=(int)(punto.x+dist*Math.cos(ang+Math.toRadians(angSep)));
        p2.y=(int)(punto.y-dist*Math.sin(ang+Math.toRadians(angSep)));        
        
        g.setColor(Color.BLACK);
        g.drawLine(punto1.x,punto1.y,punto.x,punto.y);

        /*g.drawLine(p1.x,p1.y,punto.x,punto.y);
        g.drawLine(p2.x,p2.y,punto.x,punto.y);
        g.drawLine(p2.x,p2.y,p1.x,p1.y);
         **/
        
        int xPoints[]={p1.x,punto.x,p2.x};
        int yPoints[]={p1.y,punto.y,p2.y};
        g.fillPolygon(xPoints,yPoints,3);
        
    }
    
    
    public void setStrucDN(Vector _e)
    {
        estructura=_e;
    }
}
