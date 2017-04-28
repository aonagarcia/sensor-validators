/*
 * showModel.java
 *
 * Created on 8 de marzo de 2011, 11:48 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.github.aonagarcia.igu;

import java.awt.Point;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Vector;
import javax.swing.JPanel;

/**
 *
 * @author Javier
 */
public class showModel extends JPanel{
    
    private Vector estructura;
    int i=0;
    private int viewportMaxX=1000;
    private int viewportMaxY=1000;
    private Vector graphicNodesVector=new Vector();
    graficoModelo gm=null;
   

    /** Creates a new instance of showModel */
    
    public showModel(){
        graficar();
    }
     
    public showModel(Vector v) {
        estructura=v;
        inicializarRed();
    }
   
        
    public Point getRandomPoint()
    {
        Point coord=new Point();        
        double randomX=Math.random();
        double randomY=Math.random();        
        coord.x=(int)(randomX*(viewportMaxX));
        coord.y=(int)(randomY*(viewportMaxY));        
        if (coord.x<50) coord.x=50;
        if (coord.x>(viewportMaxX-50)) coord.x=viewportMaxX-50;
        if (coord.y<25) coord.y=25;
        if (coord.y>(viewportMaxY-25)) coord.y=viewportMaxY-25;
        return coord;
    }
    
    
    
    public void inicializarRed()
    {
       Iterator it=estructura.iterator();
        while (it.hasNext())
        {
            String nodeName =(String)((LinkedList)it.next()).get(0);
            Point at=getRandomPoint();
            graphicNodesVector.add(new graphicNode(at.x,at.y,nodeName));           
        }
        
               
    }
    
    public void graficar()
    {
        if (gm==null)
            gm=new graficoModelo();
        
        gm.setStrucDN(estructura);
           
        Iterator itVector=graphicNodesVector.iterator();
        while (itVector.hasNext())
        {
            gm.addNode((graphicNode)itVector.next());
        }
        this.add(gm);        
    }
}

