/*
 * graphicNode.java
 *
 * Created on 9 de marzo de 2011, 12:43 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.github.aonagarcia.igu;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

/**
 *
 * @author Javier
 */
public class graphicNode {
    
    private int nodeWidth=100;
    private int nodeHeigth=50;
    private Point center=new Point();
    private int nodeCenter_x;
    private int nodeCenter_y;
    private Color  nodeColor=new Color(255, 255, 192);
    private String name="";
    private String labelNode="";
    private int posX;
    private int posY;
    public int radio;
    
    
   
    /** Creates a new instance of graphicNode */
    public graphicNode(int x, int y, String n) {
        center.x=x;
        center.y=y;
        name=n;
        radio=(nodeWidth)/2;
       /* if (n.length()>7)
            labelNode=n.substring(0,7)+"..."; 
        else*/
            labelNode=n;
    }
    
    public boolean isInside(Point p)
    {
        if (Math.sqrt(((center.x - p.x) * (center.x - p.x)) + ((center.y - p.y) * (center.y - p.y))) < nodeHeigth)
        {
            return true;
        }

        return false;
    }
    
    public boolean isInsideEllipse(Point p)
    {
        double ang=0.0;
        double tx,ty;
        double r_center_p=0,r_center_elipse=0;
        
        r_center_p=Math.sqrt(((center.x-p.x)*(center.x-p.x))+((center.y-p.y)*(center.y-p.y)));
        
        ty=-(center.y-p.y)*1.0;
        tx=(center.x-p.x)*1.0;
        ang=Math.atan(ty/tx);
                
        if(tx<0) {
            ang+=Math.PI;
        }
                       
        int pcx=((int)(50*Math.cos(ang)))+center.x; 
        int pcy=((-1)*(int)(25*Math.sin(ang)))+center.y;     
        
        r_center_elipse=Math.sqrt(((center.x-pcx)*(center.x-pcx))+((center.y-pcy)*(center.y-pcy)));
        
        if (r_center_p<=r_center_elipse)
            return true;
        else
            return false;
    }
    
    
    
    public void drawNode(Graphics g)
    {
        int anchoNombre=g.getFontMetrics().stringWidth(labelNode);
        
        g.setColor(nodeColor);
        //g.fillOval(center.x-radio,center.y-radio,nodeWidth,2*radio);
        g.fillOval(center.x-(nodeWidth/2),center.y-(nodeHeigth/2),nodeWidth,nodeHeigth);
        g.setColor(Color.BLACK);
        //g.drawOval(center.x-radio,center.y-radio,nodeWidth,2*radio);
        g.drawOval(center.x-(nodeWidth/2),center.y-(nodeHeigth/2),nodeWidth,nodeHeigth);
        g.drawString(labelNode,center.x-(int)(anchoNombre/2),center.y+3);           
    }
    
    public void setPosition(Point p)
    {
        this.center.x=p.x;
        this.center.y=p.y;
    }
    
    public int getCenterX()
    {
        return this.center.x;
    }
    
     public int getCenterY()
    {
        return this.center.y;
    }
    
     public String getName()
     {
        return name;
     }
}
