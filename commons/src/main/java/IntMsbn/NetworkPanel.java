/*     */ package IntMsbn;
/*     */ 
/*     */ import Network.HelpPanel;
/*     */ import Network.UTIL;
/*     */ import Network.UndirectGraph;
/*     */ import java.awt.FileDialog;
/*     */ import java.awt.Point;
/*     */ import java.io.PrintWriter;
/*     */ 
/*     */ public class NetworkPanel extends java.awt.Canvas implements Network.Bridge
/*     */ {
/*  12 */   UndirectGraph org = null;
/*     */   
/*     */ 
/*     */ 
/*     */   StringList2D pubvar;
/*     */   
/*     */ 
/*     */ 
/*  20 */   Network.HyperGraph ct = null;
/*     */   
/*     */ 
/*     */   java.awt.Frame frame;
/*     */   
/*     */   Network.LabelDialog agtNameDialog;
/*     */   
/*     */   Network.LabelDialog pubVarDialog;
/*     */   
/*  29 */   Point downPoint = new Point(0, 0);
/*  30 */   Point thisPoint = new Point(0, 0);
/*  31 */   Point upPoint = new Point(0, 0);
/*     */   
/*  33 */   int downAgt = -2;
/*  34 */   int downAgt2 = -2;
/*  35 */   int upAgt = -2;
/*  36 */   Point oldPos = new Point(0, 0);
/*     */   
/*     */   static final int START_MODE = 0;
/*     */   
/*     */   static final int DRAW_MODE = 1;
/*     */   
/*     */   static final int DEF_AGT_MODE = 2;
/*     */   static final int MOVE_AGT_MODE = 3;
/*     */   static final int DEL_AGT_MODE = 4;
/*     */   static final int DEL_LINK_MODE = 5;
/*     */   static final int DEF_PUB_MODE = 6;
/*  47 */   int mode = 0;
/*     */   
/*     */ 
/*  50 */   boolean isHyperTree = false;
/*     */   
/*     */   private java.awt.Image offScreenImage;
/*     */   
/*     */   private java.awt.Graphics offScreenGraphics;
/*     */   private java.awt.Dimension offScreenSize;
/*     */   
/*     */   public NetworkPanel(java.awt.Frame paramFrame)
/*     */   {
/*  59 */     initMouse();
/*  60 */     this.frame = paramFrame;
/*  61 */     setBackground(Network.NetPaint.backgroundColor);
/*  62 */     this.org = new UndirectGraph();
/*  63 */     this.pubvar = new StringList2D();
/*     */     
/*  65 */     addMouseListener(new java.awt.event.MouseAdapter()
/*     */     {
/*     */       public void mousePressed(java.awt.event.MouseEvent paramAnonymousMouseEvent)
/*     */       {
/*  69 */         NetworkPanel.this.downPoint.move(paramAnonymousMouseEvent.getX(), paramAnonymousMouseEvent.getY());
/*  70 */         if (NetworkPanel.this.org.getNodeCount() > 0)
/*  71 */           NetworkPanel.this.downAgt = NetworkPanel.this.org.isClose(NetworkPanel.this.downPoint, 6); else {
/*  72 */           NetworkPanel.this.downAgt = -2;
/*     */         }
/*  74 */         if (NetworkPanel.this.mode == 1) { NetworkPanel.this.mouseDownInDraw();
/*  75 */         } else if (NetworkPanel.this.mode == 2) { NetworkPanel.this.mouseDownInDefAgt();
/*  76 */         } else if (NetworkPanel.this.mode == 3) { NetworkPanel.this.mouseDownInMove();
/*  77 */         } else if (NetworkPanel.this.mode == 4) { NetworkPanel.this.mouseDownInDelNode();
/*  78 */         } else if (NetworkPanel.this.mode == 5) { NetworkPanel.this.mouseDownInDelLink();
/*  79 */         } else if (NetworkPanel.this.mode == 6) NetworkPanel.this.mouseDownInDefPub();
/*  80 */         NetworkPanel.this.repaint();
/*     */       }
/*     */       
/*  83 */       public void mouseReleased(java.awt.event.MouseEvent paramAnonymousMouseEvent) { NetworkPanel.this.upPoint.move(paramAnonymousMouseEvent.getX(), paramAnonymousMouseEvent.getY());
/*  84 */         if (NetworkPanel.this.mode == 1) { NetworkPanel.this.mouseUpInDraw();
/*  85 */         } else if (NetworkPanel.this.mode == 3) { NetworkPanel.this.mouseUpInMove();
/*     */         }
/*  87 */         NetworkPanel.this.initMouse();
/*  88 */         NetworkPanel.this.repaint();
/*     */       }
/*     */       
/*     */ 
/*  92 */     });
/*  93 */     addMouseMotionListener(new java.awt.event.MouseMotionAdapter()
/*     */     {
/*     */       public void mouseDragged(java.awt.event.MouseEvent paramAnonymousMouseEvent) {
/*  96 */         NetworkPanel.this.thisPoint.move(paramAnonymousMouseEvent.getX(), paramAnonymousMouseEvent.getY());
/*  97 */         if (NetworkPanel.this.mode == 3) NetworkPanel.this.mouseDragInMove();
/*  98 */         NetworkPanel.this.repaint();
/*     */       }
/*     */     });
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private void initMouse()
/*     */   {
/* 108 */     this.downPoint = new Point(0, 0);
/* 109 */     this.thisPoint = new Point(0, 0);
/* 110 */     this.upPoint = new Point(0, 0);
/*     */     
/* 112 */     this.downAgt = -2;
/* 113 */     this.upAgt = -2;
/* 114 */     this.oldPos = new Point(0, 0);
/*     */   }
/*     */   
/*     */   void delAll()
/*     */   {
/* 119 */     if (this.org.getNodeCount() == 0) {
/* 120 */       HelpPanel.addHelp("No agent organization to delete.");return;
/*     */     }
/* 122 */     this.org = new UndirectGraph();
/* 123 */     this.pubvar = new StringList2D();
/* 124 */     repaint();
/* 125 */     HelpPanel.addHelp("Current agent organization deleted.");
/*     */   }
/*     */   
/*     */ 
/*     */   boolean hasNode()
/*     */   {
/* 131 */     if (this.org.getNodeCount() == 0) return false;
/* 132 */     return true;
/*     */   }
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
/*     */   void isHypertree()
/*     */   {
/* 147 */     int i = this.org.getNodeCount();
/* 148 */     if (i < 2) {
/* 149 */       HelpPanel.showError("No agent organization to check.");
/* 150 */       this.isHyperTree = false; return; }
/*     */     Object localObject1;
/*     */     int k;
/* 153 */     Object localObject2; for (int j = 0; j < i - 1; j++) {
/* 154 */       localObject1 = this.org.getLabel(j);
/* 155 */       for (k = j + 1; k < i; k++) {
/* 156 */         localObject2 = this.org.getLabel(k);
/* 157 */         if (((String)localObject1).equals(localObject2)) {
/* 158 */           HelpPanel.showError("Identical name for agents " + j + " and " + k);
/* 159 */           this.isHyperTree = false;return;
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 164 */     if (!this.org.isTree()) {
/* 165 */       HelpPanel.showError("Agent organization is not a tree.");
/* 166 */       this.isHyperTree = false;return;
/*     */     }
/*     */     
/* 169 */     if (!this.pubvar.isDefined()) {
/* 170 */       HelpPanel.showError("Public variables undefined for some agent.");
/* 171 */       this.isHyperTree = false; return;
/*     */     }
/*     */     String[] arrayOfString2;
/* 174 */     for (j = 0; j < i - 1; j++) {
/* 175 */       localObject1 = this.pubvar.getRow(j);
/* 176 */       for (k = j + 1; k < i; k++) {
/* 177 */         if (this.org.isLink(j, k)) {
/* 178 */           localObject2 = this.pubvar.getRow(k);
/* 179 */           arrayOfString2 = Network.MATH.getIntersection((String[])localObject1, (String[])localObject2);
/* 180 */           if (arrayOfString2 == null) {
/* 181 */             HelpPanel.showError("Public vars in agents " + j + " & " + k + " are inconsistent with agent organization.");
/*     */             
/* 183 */             this.isHyperTree = false;return;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 189 */     for (j = 0; j < i; j++) {
/* 190 */       localObject1 = this.pubvar.getRow(j);
/* 191 */       String[] arrayOfString1 = null;
/* 192 */       for (int m = 0; m < i; m++) {
/* 193 */         if ((m != j) && 
/* 194 */           (this.org.isLink(j, m))) {
/* 195 */           arrayOfString2 = this.pubvar.getRow(m);
/* 196 */           arrayOfString1 = Network.MATH.union(arrayOfString1, arrayOfString2);
/*     */         }
/*     */       }
/* 199 */       if (!Network.MATH.isSubset((String[])localObject1, arrayOfString1)) {
/* 200 */         UTIL.showList("pub1=", (String[])localObject1);
/* 201 */         UTIL.showList("pubu=", arrayOfString1);
/* 202 */         HelpPanel.showError("Some public vars in agent " + j + " are not shared by adjacent agents.");
/*     */         
/* 204 */         this.isHyperTree = false;return;
/*     */       }
/*     */     }
/*     */     
/* 208 */     setClusterTree();
/* 209 */     if (!this.ct.isLocalCovered()) {
/* 210 */       HelpPanel.showError("Public var cluster tree not locally covered.");
/* 211 */       this.isHyperTree = false;return;
/*     */     }
/* 213 */     this.isHyperTree = true;
/* 214 */     HelpPanel.addHelp("Hypertree condition verified.");
/*     */   }
/*     */   
/*     */ 
/*     */   void setClusterTree()
/*     */   {
/* 220 */     this.ct = new Network.HyperGraph();
/* 221 */     int i = this.org.getNodeCount();
/* 222 */     this.ct.setDumbNetPlus(i);
/* 223 */     String[] arrayOfString = this.pubvar.getStrList();
/*     */     int[] arrayOfInt1;
/* 225 */     for (int j = 0; j < i; j++) {
/* 226 */       arrayOfInt1 = UTIL.getSubsetIndex(arrayOfString, this.pubvar.getRow(j));
/* 227 */       this.ct.setCqMember(j, arrayOfInt1);
/*     */     }
/*     */     
/* 230 */     for (j = 0; j < i - 1; j++) {
/* 231 */       arrayOfInt1 = this.ct.getCqMember(j);
/* 232 */       for (int k = j + 1; k < i; k++) {
/* 233 */         if (this.org.isLink(j, k)) {
/* 234 */           int[] arrayOfInt2 = this.ct.getCqMember(k);
/* 235 */           int[] arrayOfInt3 = Network.MATH.getIntersection(arrayOfInt1, arrayOfInt2);
/* 236 */           this.ct.addNeighbor(j, new Network.HLink(k, arrayOfInt3));
/* 237 */           this.ct.addNeighbor(k, new Network.HLink(j, arrayOfInt3));
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   void load()
/*     */   {
/* 249 */     FileDialog localFileDialog = new FileDialog(this.frame, "", 0);
/* 250 */     localFileDialog.pack();localFileDialog.setVisible(true);
/*     */     
/* 252 */     String str1 = localFileDialog.getDirectory();
/* 253 */     String str2 = localFileDialog.getFile();
/* 254 */     if (str2 == null) { return;
/*     */     }
/* 256 */     this.org = new UndirectGraph();
/* 257 */     this.pubvar = new StringList2D();
/* 258 */     HelpPanel.addHelp("Current MSBN hypertree cleared.");
/* 259 */     String str3 = new String(str1 + UTIL.removePostfix(str2) + ".bdr");
/*     */     try
/*     */     {
/* 262 */       java.io.BufferedReader localBufferedReader = new java.io.BufferedReader(new java.io.FileReader(str3));
/* 263 */       HelpPanel.addHelp("Loading hypertree from " + str3 + ".");
/*     */       
/* 265 */       int i = UTIL.loadInt(localBufferedReader);
/* 266 */       this.org.setDumbNetPlus(i);
/* 267 */       String[][] arrayOfString1 = new String[i][];
/* 268 */       for (int j = 0; j < i; j++) {
/* 269 */         UTIL.skipLine(localBufferedReader);
/* 270 */         this.org.setLabel(j, UTIL.loadString(localBufferedReader));
/* 271 */         arrayOfString1[j] = UTIL.loadStringListLine(localBufferedReader);
/* 272 */         this.org.setPos(j, UTIL.loadPoint(localBufferedReader));
/*     */       }
/*     */       
/* 275 */       for (j = 0; j < i - 1; j++) {
/* 276 */         String str4 = this.org.getLabel(j);
/* 277 */         for (int m = j + 1; m < i; m++) {
/* 278 */           String str5 = this.org.getLabel(m);
/* 279 */           for (int i1 = 0; i1 < arrayOfString1[j].length; i1++) {
/* 280 */             if (str5.equals(arrayOfString1[j][i1])) { this.org.addLink(j, m);
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/* 285 */       UTIL.skipLine(localBufferedReader);UTIL.skipLine(localBufferedReader);
/*     */       
/* 287 */       String[][] arrayOfString2 = new String[i][];
/* 288 */       for (int k = 0; k < i - 1; k++) {
/* 289 */         UTIL.skipLine(localBufferedReader);
/* 290 */         String[] arrayOfString3 = UTIL.loadStringListLine(localBufferedReader, 2);
/* 291 */         int n = UTIL.loadInt(localBufferedReader);
/* 292 */         String[] arrayOfString4 = UTIL.loadStringListMLine(localBufferedReader, n);
/*     */         
/* 294 */         for (int i2 = 0; i2 < i; i2++) {
/* 295 */           if ((arrayOfString3[0].equals(this.org.getLabel(i2))) || (arrayOfString3[1].equals(this.org.getLabel(i2))))
/*     */           {
/* 297 */             arrayOfString2[i2] = Network.MATH.union(arrayOfString2[i2], arrayOfString4);
/*     */           }
/*     */         }
/*     */       }
/*     */       
/* 302 */       this.pubvar = new StringList2D(i);
/* 303 */       for (k = 0; k < i; k++) { this.pubvar.setRow(k, arrayOfString2[k]);
/*     */       }
/* 305 */       localBufferedReader.close();
/*     */     } catch (java.io.IOException localIOException) {
/* 307 */       HelpPanel.showError("Unable to load " + str3);
/*     */     }
/*     */     
/* 310 */     moveNet();
/* 311 */     repaint();
/* 312 */     HelpPanel.addHelp("Loading completed.");
/*     */   }
/*     */   
/*     */   void save()
/*     */   {
/* 317 */     if (!this.isHyperTree) {
/* 318 */       HelpPanel.showError("Please define/verify hypertree first.");
/* 319 */       return;
/*     */     }
/*     */     
/* 322 */     String str1 = null;
/* 323 */     String str2 = null;
/* 324 */     while (str2 == null) {
/* 325 */       localObject1 = new FileDialog(this.frame, "", 1);
/* 326 */       ((FileDialog)localObject1).pack();((FileDialog)localObject1).setVisible(true);
/* 327 */       str1 = ((FileDialog)localObject1).getDirectory();
/* 328 */       str2 = ((FileDialog)localObject1).getFile();
/* 329 */       if (str2 == null) HelpPanel.showError("Invalid file name.");
/*     */     }
/* 331 */     Object localObject1 = new String(str1 + UTIL.removePostfix(str2) + ".bdr");
/*     */     try
/*     */     {
/* 334 */       PrintWriter localPrintWriter = new PrintWriter(new java.io.FileWriter((String)localObject1));
/*     */       
/* 336 */       int i = this.org.getNodeCount();
/* 337 */       localPrintWriter.println(i + "  #_of_agents");
/*     */       int n;
/* 339 */       Object localObject2; for (int j = 0; j < i; j++) {
/* 340 */         localPrintWriter.println();
/* 341 */         localPrintWriter.println(this.org.getLabel(j) + "  agent_" + j);
/* 342 */         localPrintWriter.print("  ");
/* 343 */         int k = this.org.getNeighborCount(j);
/* 344 */         for (int m = 0; m < k; m++) {
/* 345 */           n = this.org.getNeighbor(j, m);
/* 346 */           localPrintWriter.print(this.org.getLabel(n) + " ");
/*     */         }
/* 348 */         localObject2 = this.org.getPos(j);
/* 349 */         localPrintWriter.println("\n" + ((Point)localObject2).x + " " + ((Point)localObject2).y + "  coordinate");
/*     */       }
/*     */       
/* 352 */       localPrintWriter.println("\n#_of_agent_interfaces=#_of_agents-1");
/*     */       
/* 354 */       for (j = 0; j < i - 1; j++) {
/* 355 */         String str3 = this.org.getLabel(j);
/* 356 */         localObject2 = this.pubvar.getRow(j);
/* 357 */         for (n = j + 1; n < i; n++) {
/* 358 */           if (this.org.isLink(j, n)) {
/* 359 */             String str4 = this.org.getLabel(n);
/* 360 */             String[] arrayOfString1 = this.pubvar.getRow(n);
/* 361 */             String[] arrayOfString2 = Network.MATH.getIntersection((String[])localObject2, arrayOfString1);
/* 362 */             int i1 = arrayOfString2.length;
/*     */             
/* 364 */             localPrintWriter.println("\n" + str3 + " " + str4);
/* 365 */             localPrintWriter.println(i1 + "  #_d-sepnodes");
/* 366 */             for (int i2 = 0; i2 < i1 - 1; i2++) localPrintWriter.print(arrayOfString2[i2] + " ");
/* 367 */             localPrintWriter.println(arrayOfString2[(i1 - 1)]);
/*     */           }
/*     */         }
/*     */       }
/*     */       
/* 372 */       localPrintWriter.close();
/*     */     } catch (java.io.IOException localIOException) {
/* 374 */       HelpPanel.showError("Unable to save " + (String)localObject1);
/*     */     }
/* 376 */     HelpPanel.addHelp("Binder file saved to " + (String)localObject1);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private void mouseDownInDraw()
/*     */   {
/* 383 */     this.thisPoint = new Point(this.downPoint);
/* 384 */     if (this.downAgt >= 0) return;
/* 385 */     if (this.downAgt == -2) {
/* 386 */       this.org = UndirectGraph.addNode(this.org, this.downPoint);
/* 387 */       this.pubvar.addRow();
/* 388 */       this.downAgt = this.org.isClose(this.downPoint, 6);
/*     */     }
/*     */     else {
/* 391 */       HelpPanel.showError("Too close to an existing agent!");
/*     */     }
/*     */   }
/*     */   
/*     */   private void mouseDownInDefAgt() {
/* 396 */     if (this.downAgt >= 0) {
/* 397 */       this.downAgt2 = this.downAgt;
/* 398 */       HelpPanel.addHelp("Agent name should contain no spaces.");
/* 399 */       this.agtNameDialog = new Network.LabelDialog(this.frame, this, "Name Agent", "Agent Name", "");
/*     */       
/* 401 */       this.agtNameDialog.setVisible(true);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   private void mouseDownInMove()
/*     */   {
/* 408 */     if (this.org.getNodeCount() == 0) {
/* 409 */       HelpPanel.showError("No agent to move.");return;
/*     */     }
/* 411 */     if (this.downAgt >= 0) this.oldPos.setLocation(this.org.getPos(this.downAgt));
/*     */   }
/*     */   
/*     */   private void mouseDownInDelNode()
/*     */   {
/* 416 */     if (this.org.getNodeCount() == 0) {
/* 417 */       HelpPanel.showError("No agent to delete.");return;
/*     */     }
/* 419 */     if (this.downAgt < 0) {
/* 420 */       HelpPanel.addHelp("Click mouse at the agent to be deleted.");return;
/*     */     }
/*     */     
/* 423 */     this.org = UndirectGraph.delNode(this.org, this.downAgt);
/* 424 */     this.pubvar.delRow(this.downAgt);
/*     */   }
/*     */   
/*     */ 
/*     */   private void mouseDownInDelLink()
/*     */   {
/* 430 */     if (this.org.getNodeCount() == 0) {
/* 431 */       HelpPanel.showError("No link to delete.");return;
/*     */     }
/* 433 */     if (this.downAgt >= 0) return;
/* 434 */     Point localPoint = new Point(this.downPoint);
/* 435 */     int i = this.org.getNodeCount();
/* 436 */     for (int j = 0; j < i; j++) {
/* 437 */       for (int k = 0; k < i; k++) {
/* 438 */         if ((this.org.isLink(j, k)) && (Network.MATH.inRectangle(this.downPoint, this.org.getPos(j), this.org.getPos(k))) && (Network.MATH.onLine(this.downPoint, this.org.getPos(j), this.org.getPos(k))))
/*     */         {
/*     */ 
/* 441 */           this.org.delLink(j, k);
/* 442 */           return;
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private void mouseDownInDefPub()
/*     */   {
/* 450 */     if (this.org.getNodeCount() == 0) {
/* 451 */       HelpPanel.showError("Please define agent organization first.");return;
/*     */     }
/* 453 */     if (this.downAgt < 0) { return;
/*     */     }
/* 455 */     this.downAgt2 = this.downAgt;
/* 456 */     HelpPanel.addHelp("Specify public variables separated by space.");
/* 457 */     this.pubVarDialog = new Network.LabelDialog(this.frame, this, "Public Variable", "Var Labels", this.pubvar.getRowStr(this.downAgt2));
/*     */     
/* 459 */     this.pubVarDialog.setVisible(true);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   void setAgtPara(String[] paramArrayOfString)
/*     */   {
/* 466 */     if (paramArrayOfString[1].equals("Name Agent")) { this.org.setLabel(this.downAgt2, paramArrayOfString[0]);
/* 467 */     } else if (paramArrayOfString[1].equals("Public Variable")) {
/* 468 */       this.pubvar.defRow(this.downAgt2, UTIL.getStringList(paramArrayOfString[0]));
/* 469 */       this.pubvar.show();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private void mouseDragInMove()
/*     */   {
/* 477 */     if (this.downAgt >= 0) { this.org.setPos(this.downAgt, this.thisPoint);
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
/*     */ 
/*     */ 
/*     */   private void mouseUpInDraw()
/*     */   {
/* 492 */     if (this.downAgt < 0) { return;
/*     */     }
/* 494 */     this.upAgt = this.org.isClose(this.upPoint, 6);
/* 495 */     if (this.upAgt >= 0) {
/* 496 */       if (this.upAgt == this.downAgt) {
/* 497 */         HelpPanel.showError("Press and release mouse at the same agent!");
/*     */       } else {
/* 499 */         this.org.addLink(this.downAgt, this.upAgt);
/*     */       }
/* 501 */     } else if (this.upAgt == -2) {
/* 502 */       this.org = UndirectGraph.addNode(this.org, this.upPoint);
/* 503 */       this.pubvar.addRow();
/* 504 */       this.upAgt = this.org.isClose(this.upPoint, 6);
/* 505 */       this.org.addLink(this.downAgt, this.upAgt);
/*     */     }
/*     */     else {
/* 508 */       HelpPanel.addHelp("Please release mouse at an agent or far from any.");
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void mouseUpInMove()
/*     */   {
/* 518 */     if (this.downAgt < 0) { return;
/*     */     }
/* 520 */     this.org.setPos(this.downAgt, 0, 0);
/* 521 */     this.upAgt = this.org.isClose(this.upPoint, 6);
/* 522 */     if ((this.upAgt != -2) || (this.upPoint.x < 0) || (this.upPoint.x > getSize().width) || (this.upPoint.y < 0) || (this.upPoint.y > getSize().height))
/*     */     {
/*     */ 
/* 525 */       this.org.setPos(this.downAgt, this.oldPos);
/* 526 */       HelpPanel.showError("Too close to another agent or out of border!");
/*     */     } else {
/* 528 */       this.org.setPos(this.downAgt, this.upPoint);
/*     */     }
/*     */   }
/*     */   
/*     */   public void setVector(String[] paramArrayOfString) {}
/*     */   
/*     */   public void setVector2(String[] paramArrayOfString) {
/* 535 */     setAgtPara(paramArrayOfString);
/* 536 */     repaint(); }
/*     */   
/*     */   public void setVector(boolean[] paramArrayOfBoolean) {}
/*     */   public void setVector(float[] paramArrayOfFloat) {}
/*     */   public void setVector(int[] paramArrayOfInt) {}
/*     */   public void setVector2(int[] paramArrayOfInt) {}
/*     */   public void setTable(float[] paramArrayOfFloat) {}
/*     */   public void showNet() {}
/* 544 */   public float[] getVector(int paramInt) { return null; }
/* 545 */   public String[] getVector2(int paramInt) { return null; }
/* 546 */   public String[] getVector3(int paramInt) { return null; }
/*     */   
/*     */ 
/*     */ 
/*     */   public void paint(java.awt.Graphics paramGraphics)
/*     */   {
/* 552 */     if ((this.mode == 1) && (this.downAgt >= 0)) {
/* 553 */       Network.NetPaint.drawLink(paramGraphics, this.org.getPos(this.downAgt), this.thisPoint);
/*     */     }
/* 555 */     Network.NetPaint.paintNet(paramGraphics, this.org);
/*     */   }
/*     */   
/*     */   void moveNet()
/*     */   {
/* 560 */     Network.NetPaint.moveNet(this, this.org);
/* 561 */     repaint();
/*     */   }
/*     */   
/*     */ 
/*     */   public final synchronized void update(java.awt.Graphics paramGraphics)
/*     */   {
/* 567 */     java.awt.Dimension localDimension = getSize();
/* 568 */     if ((this.offScreenImage == null) || (localDimension.width != this.offScreenSize.width) || (localDimension.height != this.offScreenSize.height))
/*     */     {
/* 570 */       this.offScreenImage = createImage(localDimension.width, localDimension.height);
/* 571 */       this.offScreenSize = localDimension;
/* 572 */       this.offScreenGraphics = this.offScreenImage.getGraphics();
/*     */     }
/* 574 */     this.offScreenGraphics.setColor(Network.NetPaint.backgroundColor);
/* 575 */     this.offScreenGraphics.fillRect(0, 0, localDimension.width, localDimension.height);
/* 576 */     paint(this.offScreenGraphics);
/* 577 */     paramGraphics.drawImage(this.offScreenImage, 0, 0, null);
/*     */   }
/*     */   
/*     */   void setMode(String paramString)
/*     */   {
/* 582 */     if (paramString.equals(CommandPanel.agtOrgLabel)) { this.mode = 1;
/* 583 */     } else if (paramString.equals(CommandPanel.agtNmLabel)) { this.mode = 2;
/* 584 */     } else if (paramString.equals(CommandPanel.mvAgtLabel)) { this.mode = 3;
/* 585 */     } else if (paramString.equals(CommandPanel.delAgtLabel)) { this.mode = 4;
/* 586 */     } else if (paramString.equals(CommandPanel.delLinkLabel)) { this.mode = 5;
/* 587 */     } else if (paramString.equals(CommandPanel.publicLabel)) this.mode = 6; else {
/* 588 */       this.mode = 0;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /home/anali/INAOE/tesis/codigos/topub/exe/ww4.jar!/IntMsbn/NetworkPanel.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */