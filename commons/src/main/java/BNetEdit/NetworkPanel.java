/*     */ package BNetEdit;
/*     */ 
/*     */ import Network.BayesNet;
/*     */ import Network.Bridge;
/*     */ import Network.HelpPanel;
/*     */ import Network.MATH;
/*     */ import Network.NetPaint;
/*     */ import Network.TableDialog;
/*     */ import Network.UTIL;
/*     */ import Network.VariableDialog;
/*     */ import Network.VectorDialog;
/*     */ import java.awt.Canvas;
/*     */ import java.awt.Cursor;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.FileDialog;
/*     */ import java.awt.Frame;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Image;
/*     */ import java.awt.Point;
/*     */ import java.awt.event.MouseAdapter;
/*     */ import java.awt.event.MouseEvent;
/*     */ import java.awt.event.MouseMotionAdapter;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.FileReader;
/*     */ import java.io.FileWriter;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintWriter;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class NetworkPanel
/*     */   extends Canvas
/*     */   implements Bridge
/*     */ {
/*     */   Frame frame;
/*     */   VariableDialog varDialog;
/*     */   VectorDialog priorDialog;
/*     */   TableDialog cptDialog;
/*     */   VectorDialog priExpnDialog;
/*     */   TableDialog cptExpnDialog;
/*     */   BayesNet bn;
/*  52 */   Point downPoint = new Point(0, 0);
/*  53 */   Point thisPoint = new Point(0, 0);
/*  54 */   Point upPoint = new Point(0, 0);
/*     */   
/*  56 */   int downNode = -2;
/*  57 */   int downNode2 = -2;
/*  58 */   int upNode = -2;
/*  59 */   Point oldPos = new Point(0, 0);
/*     */   
/*     */   static final int START_MODE = 0;
/*     */   
/*     */   static final int DRAW_MODE = 1;
/*     */   
/*     */   static final int MOVE_NODE_MODE = 2;
/*     */   static final int DEL_NODE_MODE = 3;
/*     */   static final int DEL_ARC_MODE = 4;
/*     */   static final int DEF_VAR_MODE = 5;
/*     */   static final int SET_PROB_MODE = 6;
/*     */   static final int SET_INDEX_MODE = 7;
/*  71 */   int mode = 0;
/*     */   
/*     */ 
/*     */ 
/*  75 */   boolean showIndex = false;
/*     */   
/*     */ 
/*  78 */   float arrowLength = 20.0F;
/*  79 */   final float maxLength = 20.0F;
/*     */   
/*     */   private Image offScreenImage;
/*     */   
/*     */   private Graphics offScreenGraphics;
/*     */   private Dimension offScreenSize;
/*     */   
/*     */   public NetworkPanel(Frame paramFrame)
/*     */   {
/*  88 */     initMouse();
/*  89 */     this.frame = paramFrame;
/*  90 */     setBackground(NetPaint.backgroundColor);
/*  91 */     this.bn = new BayesNet();
/*     */     
/*  93 */     addMouseListener(new MouseAdapter()
/*     */     {
/*     */       public void mousePressed(MouseEvent paramAnonymousMouseEvent)
/*     */       {
/*  97 */         NetworkPanel.this.downPoint.move(paramAnonymousMouseEvent.getX(), paramAnonymousMouseEvent.getY());
/*  98 */         if (NetworkPanel.this.hasNode())
/*  99 */           NetworkPanel.this.downNode = NetworkPanel.this.bn.isClose(NetworkPanel.this.downPoint, 6); else {
/* 100 */           NetworkPanel.this.downNode = -2;
/*     */         }
/* 102 */         if (NetworkPanel.this.mode == 2) { NetworkPanel.this.mouseDownInMove();
/* 103 */         } else if (NetworkPanel.this.mode == 1) { NetworkPanel.this.mouseDownInDraw();
/* 104 */         } else if (NetworkPanel.this.mode == 3) { NetworkPanel.this.mouseDownInDelNode();
/* 105 */         } else if (NetworkPanel.this.mode == 4) { NetworkPanel.this.mouseDownInDelArc();
/* 106 */         } else if (NetworkPanel.this.mode == 5) { NetworkPanel.this.mouseDownInDefVar();
/* 107 */         } else if (NetworkPanel.this.mode == 6) { NetworkPanel.this.mouseDownInSetProb();
/* 108 */         } else if (NetworkPanel.this.mode == 7) NetworkPanel.this.mouseDownInSetIndex();
/* 109 */         NetworkPanel.this.repaint();
/*     */       }
/*     */       
/* 112 */       public void mouseReleased(MouseEvent paramAnonymousMouseEvent) { NetworkPanel.this.upPoint.move(paramAnonymousMouseEvent.getX(), paramAnonymousMouseEvent.getY());
/* 113 */         if (NetworkPanel.this.mode == 2) { NetworkPanel.this.mouseUpInMove();
/* 114 */         } else if (NetworkPanel.this.mode == 1) { NetworkPanel.this.mouseUpInDraw();
/*     */         }
/* 116 */         NetworkPanel.this.initMouse();
/* 117 */         NetworkPanel.this.repaint();
/*     */       }
/*     */       
/*     */ 
/* 121 */     });
/* 122 */     addMouseMotionListener(new MouseMotionAdapter()
/*     */     {
/*     */       public void mouseDragged(MouseEvent paramAnonymousMouseEvent) {
/* 125 */         NetworkPanel.this.thisPoint.move(paramAnonymousMouseEvent.getX(), paramAnonymousMouseEvent.getY());
/* 126 */         if (NetworkPanel.this.mode == 2) NetworkPanel.this.mouseDragInMove();
/* 127 */         NetworkPanel.this.repaint();
/*     */       }
/*     */     });
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private void initMouse()
/*     */   {
/* 137 */     this.downPoint = new Point(0, 0);
/* 138 */     this.thisPoint = new Point(0, 0);
/* 139 */     this.upPoint = new Point(0, 0);
/*     */     
/* 141 */     this.downNode = -2;
/* 142 */     this.upNode = -2;
/* 143 */     this.oldPos = new Point(0, 0);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   void delNet()
/*     */   {
/* 150 */     if (this.bn.getNodeCount() == 0) {
/* 151 */       HelpPanel.addHelp("No bn to delete.");return;
/*     */     }
/* 153 */     this.bn.reinit();
/* 154 */     repaint();
/* 155 */     HelpPanel.addHelp("Current bn cleared.");
/*     */   }
/*     */   
/*     */   boolean hasNode()
/*     */   {
/* 160 */     if (this.bn.getNodeCount() > 0) return true;
/* 161 */     return false;
/*     */   }
/*     */   
/*     */   boolean hasArc()
/*     */   {
/* 166 */     return this.bn.hasArc();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   void load()
/*     */   {
/* 173 */     if (MainFrame.isApplet) {
/* 174 */       HelpPanel.addHelp("No load from applet!");
/* 175 */       return;
/*     */     }
/*     */     
/* 178 */     FileDialog localFileDialog = new FileDialog(this.frame, "Load File", 0);
/* 179 */     localFileDialog.pack();
/* 180 */     localFileDialog.setVisible(true);
/*     */     
/* 182 */     String str1 = localFileDialog.getDirectory();
/* 183 */     String str2 = localFileDialog.getFile();
/* 184 */     if (str2 == null) return;
/* 185 */     str2 = new String(UTIL.removePostfix(str2) + ".bn");
/*     */     try {
/* 187 */       BufferedReader localBufferedReader = new BufferedReader(new FileReader(str1 + str2));
/* 188 */       HelpPanel.addHelp("Loading bn from " + str1 + str2);
/* 189 */       this.bn = BayesNet.load(localBufferedReader);
/* 190 */       localBufferedReader.close();
/*     */     } catch (IOException localIOException) {
/* 192 */       HelpPanel.showError("Unable to load file!");
/*     */     }
/*     */     
/* 195 */     repaint();
/* 196 */     HelpPanel.addHelp("Loading completed.");
/*     */   }
/*     */   
/*     */   void save()
/*     */   {
/* 201 */     if (this.bn.getNodeCount() == 0) {
/* 202 */       HelpPanel.showError("No bn to save.");return;
/*     */     }
/* 204 */     if (!this.bn.isDag()) {
/* 205 */       HelpPanel.showError("Graph is not DAG!");return;
/*     */     }
/* 207 */     if (MainFrame.isApplet) {
/* 208 */       HelpPanel.addHelp("No saving from applet!");return;
/*     */     }
/*     */     
/* 211 */     FileDialog localFileDialog = new FileDialog(this.frame, "Save File", 1);
/* 212 */     localFileDialog.pack();
/* 213 */     localFileDialog.setVisible(true);
/*     */     
/* 215 */     String str = new String(localFileDialog.getDirectory() + localFileDialog.getFile());
/* 216 */     if (str == null) new String("jk.bn"); else
/* 217 */       str = new String(UTIL.removePostfix(str) + ".bn");
/*     */     try {
/* 219 */       PrintWriter localPrintWriter = new PrintWriter(new FileWriter(str));
/* 220 */       HelpPanel.addHelp("Saving the current bn into " + str);
/* 221 */       this.bn.save(localPrintWriter);
/* 222 */       localPrintWriter.close();
/*     */     } catch (IOException localIOException) {
/* 224 */       HelpPanel.showError("Unable to save " + str);
/*     */     }
/*     */     
/* 227 */     repaint();
/* 228 */     HelpPanel.addHelp("Saving completed.");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private void mouseDownInSetIndex()
/*     */   {
/* 236 */     if (this.downNode > 0) { this.bn.lowerNodeIndex(this.downNode);
/*     */     }
/*     */   }
/*     */   
/*     */   private void mouseDownInMove()
/*     */   {
/* 242 */     if (this.downNode >= 0) { this.oldPos.setLocation(this.bn.getPos(this.downNode));
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private void mouseDownInDraw()
/*     */   {
/* 250 */     this.thisPoint = new Point(this.downPoint);
/* 251 */     if (this.downNode >= 0) return;
/* 252 */     if (this.downNode == -2) {
/* 253 */       this.bn = BayesNet.addNode(this.bn, this.downPoint);
/* 254 */       this.downNode = this.bn.isClose(this.downPoint, 6);
/*     */     }
/*     */     else {
/* 257 */       HelpPanel.showError("This is too close to an existing node!");
/*     */     }
/*     */   }
/*     */   
/*     */   private void mouseDownInDelNode() {
/* 262 */     if (this.downNode < 0) {
/* 263 */       HelpPanel.addHelp("Click mouse at the node to delete.");return;
/*     */     }
/* 265 */     this.bn = BayesNet.delNode(this.bn, this.downNode);
/*     */   }
/*     */   
/*     */   private void mouseDownInDelArc()
/*     */   {
/* 270 */     if (this.downNode >= 0) return;
/* 271 */     Point localPoint = new Point(this.downPoint);
/* 272 */     int i = this.bn.getNodeCount();
/* 273 */     for (int j = 0; j < i; j++) {
/* 274 */       for (int k = 0; k < i; k++) {
/* 275 */         if ((this.bn.isArc(j, k)) && (MATH.inRectangle(this.downPoint, this.bn.getPos(j), this.bn.getPos(k))) && (MATH.onLine(this.downPoint, this.bn.getPos(j), this.bn.getPos(k))))
/*     */         {
/*     */ 
/* 278 */           this.bn.delArc(j, k);
/* 279 */           return;
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private void mouseDownInDefVar()
/*     */   {
/* 287 */     if (this.downNode >= 0) {
/* 288 */       this.downNode2 = this.downNode;
/* 289 */       HelpPanel.addHelp("Variable label cannot contain spaces.");
/* 290 */       this.varDialog = new VariableDialog(this.frame, "Define Variable", this);
/* 291 */       this.varDialog.setVisible(true);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   String[] setIntString(String[] paramArrayOfString)
/*     */   {
/* 298 */     if (!isNumber(paramArrayOfString)) { return paramArrayOfString;
/*     */     }
/* 300 */     int i = paramArrayOfString.length;
/* 301 */     int[] arrayOfInt = new int[i];
/* 302 */     for (int j = 0; j < i; j++) {
/* 303 */       float f = Float.valueOf(paramArrayOfString[j]).floatValue();
/* 304 */       arrayOfInt[j] = ((int)f);
/* 305 */       if ((arrayOfInt[j] - f > 0.0F) || (f - arrayOfInt[j] > 0.0F)) { return paramArrayOfString;
/*     */       }
/*     */     }
/* 308 */     String[] arrayOfString = new String[i];
/* 309 */     for (int k = 0; k < i; k++) arrayOfString[k] = new String("" + arrayOfInt[k]);
/* 310 */     return arrayOfString;
/*     */   }
/*     */   
/*     */   boolean isNumber(String[] paramArrayOfString)
/*     */   {
/* 315 */     for (int i = 0; i < paramArrayOfString.length; i++) {
/* 316 */       if (!isNumber(paramArrayOfString[i])) return false;
/*     */     }
/* 318 */     return true;
/*     */   }
/*     */   
/*     */   boolean isNumber(String paramString)
/*     */   {
/*     */     try {
/* 324 */       Float.valueOf(paramString);
/* 325 */       return true; } catch (NumberFormatException localNumberFormatException) {}
/* 326 */     return false;
/*     */   }
/*     */   
/*     */   void setLabel(String paramString) {
/* 330 */     this.bn.setLabel(this.downNode2, paramString);
/*     */   }
/*     */   
/* 333 */   String getLabel() { return this.bn.getLabel(this.downNode2); }
/*     */   
/*     */   String[] getState()
/*     */   {
/* 337 */     return this.bn.getState(this.downNode2);
/*     */   }
/*     */   
/* 340 */   int getStateCount() { return this.bn.getStateCount(this.downNode2); }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void mouseDownInSetProb()
/*     */   {
/* 358 */     if (this.downNode < 0) { HelpPanel.addHelp("Click on a node.");return; }
/* 359 */     this.downNode2 = this.downNode;
/* 360 */     this.frame.setCursor(new Cursor(3));
/*     */     String[] arrayOfString1;
/* 362 */     String[] arrayOfString2; if (this.bn.getParentCount(this.downNode2) == 0) {
/* 363 */       arrayOfString1 = new String[] { "Generating expn" };
/* 364 */       arrayOfString2 = this.bn.getExpn(this.downNode2);
/* 365 */       this.priExpnDialog = new VectorDialog(this.frame, this, getLabel(), arrayOfString1, arrayOfString2, 16, 100, 100);
/*     */       
/* 367 */       this.priExpnDialog.setVisible(true);
/*     */       
/* 369 */       this.priorDialog = new VectorDialog(this.frame, this, getLabel(), getState(), getCondProb(), 16, 100, 100);
/*     */       
/* 371 */       this.priorDialog.setVisible(true);
/*     */     }
/*     */     else {
/* 374 */       arrayOfString1 = new String[] { "Expressions" };
/*     */       
/* 376 */       arrayOfString2 = this.bn.getExpn(this.downNode2);
/*     */       
/*     */ 
/* 379 */       this.cptExpnDialog = new TableDialog(this.frame, this, getLabel(), arrayOfString1, getParentLabel(), getParentState(), arrayOfString2, 30, 10, 10, 100);
/*     */       
/* 381 */       this.cptExpnDialog.setVisible(true);
/*     */       
/* 383 */       int i = getState().length;
/* 384 */       int j = getParentLabel().length;
/* 385 */       int k = 10;
/* 386 */       if (i + j > 10) { k = 7;
/* 387 */       } else if (i + j > 15) { k = 5;
/*     */       }
/* 389 */       this.cptDialog = new TableDialog(this.frame, this, getLabel(), getState(), getParentLabel(), getParentState(), getCondProb(), k, 10, 10, 100);
/*     */       
/*     */ 
/* 392 */       this.cptDialog.setVisible(true);
/*     */     }
/* 394 */     this.frame.setCursor(new Cursor(0));
/*     */   }
/*     */   
/* 397 */   float[] getCondProb() { return this.bn.getCondProb(this.downNode2); }
/*     */   
/*     */   String[] getParentLabel() {
/* 400 */     return this.bn.getParentLabel(this.downNode2);
/*     */   }
/*     */   
/* 403 */   String[][] getParentState() { return this.bn.getParentState(this.downNode2); }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setVector(String[] paramArrayOfString)
/*     */   {
/* 410 */     if (this.mode == 5) {
/* 411 */       int i = paramArrayOfString.length;
/* 412 */       for (int j = 0; j < i; j++) {
/* 413 */         if (paramArrayOfString[j].equals("")) { paramArrayOfString[j] = new String("s" + j);
/*     */         }
/*     */       }
/* 416 */       this.bn.setState(this.downNode2, setIntString(paramArrayOfString));
/*     */     }
/* 418 */     else if (this.mode == 6) {
/* 419 */       this.bn.setExpn(paramArrayOfString, this.downNode2);
/* 420 */       this.bn.setCptByExpn(this.downNode2);
/*     */     } }
/*     */   
/*     */   public void setVector(int[] paramArrayOfInt) {}
/*     */   
/*     */   public void setVector2(int[] paramArrayOfInt) {}
/*     */   
/* 427 */   public void setVector2(String[] paramArrayOfString) { if (this.mode == 5) { setLabel(paramArrayOfString[0]);
/* 428 */     } else if (this.mode == 6) {
/* 429 */       this.bn.setExpn(paramArrayOfString, this.downNode2);
/* 430 */       this.bn.setCptByExpn(this.downNode2);
/*     */     }
/*     */   }
/*     */   
/*     */   public void setVector(boolean[] paramArrayOfBoolean) {}
/*     */   
/* 436 */   public void setVector(float[] paramArrayOfFloat) { this.bn.setCondProb(this.downNode2, paramArrayOfFloat); }
/*     */   
/*     */ 
/*     */ 
/* 440 */   public void setTable(float[] paramArrayOfFloat) { setVector(paramArrayOfFloat); }
/*     */   
/* 442 */   public float[] getVector(int paramInt) { return null; }
/*     */   
/* 444 */   public String[] getVector2(int paramInt) { return getState(); }
/*     */   
/*     */   public String[] getVector3(int paramInt) {
/* 447 */     String[] arrayOfString = new String[1];
/* 448 */     arrayOfString[0] = getLabel();
/* 449 */     return arrayOfString;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private void mouseDragInMove()
/*     */   {
/* 456 */     if (this.downNode >= 0) { this.bn.setPos(this.downNode, this.thisPoint);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void mouseUpInMove()
/*     */   {
/* 468 */     if (this.downNode < 0) { return;
/*     */     }
/* 470 */     this.bn.setPos(this.downNode, 0, 0);
/* 471 */     this.upNode = this.bn.isClose(this.upPoint, 6);
/* 472 */     if ((this.upNode != -2) || (this.upPoint.x < 0) || (this.upPoint.x > getSize().width) || (this.upPoint.y < 0) || (this.upPoint.y > getSize().height))
/*     */     {
/*     */ 
/* 475 */       this.bn.setPos(this.downNode, this.oldPos);
/* 476 */       HelpPanel.showError("Too close to another node or out of border!");
/*     */     } else {
/* 478 */       this.bn.setPos(this.downNode, this.upPoint);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void mouseUpInDraw()
/*     */   {
/* 491 */     if (this.downNode < 0) { return;
/*     */     }
/* 493 */     this.upNode = this.bn.isClose(this.upPoint, 6);
/* 494 */     if (this.upNode >= 0) {
/* 495 */       if (this.upNode == this.downNode) {
/* 496 */         HelpPanel.showError("Press and release mouse at the same node!");
/*     */       } else {
/* 498 */         this.bn.addArc(this.downNode, this.upNode);
/*     */       }
/* 500 */     } else if (this.upNode == -2) {
/* 501 */       this.bn = BayesNet.addNode(this.bn, this.upPoint);
/* 502 */       this.upNode = this.bn.isClose(this.upPoint, 6);
/* 503 */       this.bn.addArc(this.downNode, this.upNode);
/*     */     }
/*     */     else {
/* 506 */       HelpPanel.addHelp("Please release mouse at a node or far from any node.");
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public void showNet()
/*     */   {
/* 513 */     repaint();
/*     */   }
/*     */   
/*     */   public void paint(Graphics paramGraphics)
/*     */   {
/* 518 */     if ((this.mode == 1) && (this.downNode >= 0)) {
/* 519 */       NetPaint.drawArc(paramGraphics, this.bn.getPos(this.downNode), this.thisPoint, this.arrowLength);
/*     */     }
/* 521 */     NetPaint.paintNet(paramGraphics, this.bn, this.arrowLength, this.showIndex);
/*     */   }
/*     */   
/*     */   void setArrow()
/*     */   {
/* 526 */     this.arrowLength -= 2.0F;
/* 527 */     if (this.arrowLength < 2.0F) this.arrowLength = 20.0F;
/* 528 */     repaint();
/*     */   }
/*     */   
/*     */   void moveNet()
/*     */   {
/* 533 */     NetPaint.moveNet(this, this.bn);
/* 534 */     repaint();
/*     */   }
/*     */   
/*     */   void toggleIndex()
/*     */   {
/* 539 */     if (this.showIndex) this.showIndex = false; else
/* 540 */       this.showIndex = true;
/* 541 */     repaint();
/*     */   }
/*     */   
/*     */ 
/*     */   public final synchronized void update(Graphics paramGraphics)
/*     */   {
/* 547 */     Dimension localDimension = getSize();
/* 548 */     if ((this.offScreenImage == null) || (localDimension.width != this.offScreenSize.width) || (localDimension.height != this.offScreenSize.height))
/*     */     {
/* 550 */       this.offScreenImage = createImage(localDimension.width, localDimension.height);
/* 551 */       this.offScreenSize = localDimension;
/* 552 */       this.offScreenGraphics = this.offScreenImage.getGraphics();
/*     */     }
/* 554 */     this.offScreenGraphics.setColor(NetPaint.backgroundColor);
/* 555 */     this.offScreenGraphics.fillRect(0, 0, localDimension.width, localDimension.height);
/* 556 */     paint(this.offScreenGraphics);
/* 557 */     paramGraphics.drawImage(this.offScreenImage, 0, 0, null);
/*     */   }
/*     */   
/*     */   void setMode(String paramString)
/*     */   {
/* 562 */     if (paramString.equals(CommandPanel.drawLabel)) { this.mode = 1;
/* 563 */     } else if (paramString.equals(CommandPanel.mvNodeLabel)) { this.mode = 2;
/* 564 */     } else if (paramString.equals(CommandPanel.delNodeLabel)) { this.mode = 3;
/* 565 */     } else if (paramString.equals(CommandPanel.delArcLabel)) { this.mode = 4;
/* 566 */     } else if (paramString.equals(CommandPanel.varLabel)) { this.mode = 5;
/* 567 */     } else if (paramString.equals(CommandPanel.probLabel)) { this.mode = 6;
/* 568 */     } else if (paramString.equals(CommandPanel.indexLabel)) this.mode = 7; else {
/* 569 */       this.mode = 0;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /home/anali/INAOE/tesis/codigos/topub/exe/ww4.jar!/BNetEdit/NetworkPanel.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */