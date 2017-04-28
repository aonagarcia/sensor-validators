/*    */ package BnToJt;
/*    */ 
/*    */ import Network.HyperGraph;
/*    */ import Network.NetPaint;
/*    */ import java.awt.Canvas;
/*    */ import java.awt.Graphics;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class HgCan
/*    */   extends Canvas
/*    */ {
/*    */   HyperGraph hg;
/*    */   
/* 59 */   public HgCan(HyperGraph paramHyperGraph) { this.hg = paramHyperGraph; }
/*    */   
/* 61 */   public void paint(Graphics paramGraphics) { NetPaint.moveNet(this, this.hg);
/* 62 */     NetPaint.paintNet(paramGraphics, this.hg);
/*    */   }
/*    */ }


/* Location:              /home/anali/INAOE/tesis/codigos/topub/exe/ww4.jar!/BnToJt/HgCan.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */