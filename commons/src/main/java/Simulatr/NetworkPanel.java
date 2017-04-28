/*     */ package Simulatr;
/*     */ 
/*     */ import Network.BayesNet;
/*     */ import Network.HelpPanel;
/*     */ import Network.JoinForest;
/*     */ import Network.MarkovNet;
/*     */ import Network.NetPaint;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.FileDialog;
/*     */ 
/*     */ class NetworkPanel extends java.awt.Canvas implements Network.Bridge
/*     */ {
/*     */   java.awt.Frame frame;
/*  14 */   BayesNet bn = null;
/*  15 */   MarkovNet mn = null;
/*  16 */   JoinForest jt = null;
/*  17 */   String[] varName = null;
/*  18 */   String[][] varState = (String[][])null;
/*     */   
/*     */ 
/*     */   Network.VectorDialog eviDialog;
/*     */   
/*  23 */   java.awt.Point downPoint = new java.awt.Point(0, 0);
/*  24 */   int downNode = -2;
/*     */   
/*     */ 
/*  27 */   float arrowLength = 20.0F;
/*  28 */   final float maxLength = 20.0F;
/*     */   
/*     */   static final int START_MODE = 0;
/*     */   
/*     */   static final int GET_EVI_MODE = 1;
/*     */   
/*     */   static final int MOVE_NODE_MODE = 2;
/*     */   static final int SEE_PROB_MODE = 3;
/*  36 */   int mode = 0;
/*     */   
/*     */   static final int NO_HIST_MODE = 0;
/*     */   
/*     */   static final int HIST_MODE = 1;
/*     */   
/*     */   static final int JF_MODE = 2;
/*  43 */   int pmode = 0;
/*     */   
/*     */   private java.awt.Image offScreenImage;
/*     */   
/*     */   private java.awt.Graphics offScreenGraphics;
/*     */   
/*     */   private Dimension offScreenSize;
/*     */   
/*  51 */   int logIndex = 0;
/*     */   
/*     */ 
/*  54 */   boolean ready = false;
/*     */   
/*     */ 
/*  57 */   boolean eviLoaded = false;
/*     */   
/*     */   public NetworkPanel(java.awt.Frame paramFrame)
/*     */   {
/*  61 */     initMouse();
/*  62 */     this.frame = paramFrame;
/*  63 */     setBackground(NetPaint.backgroundColor);
/*  64 */     this.bn = null;
/*  65 */     this.mn = null;
/*  66 */     this.jt = null;
/*     */     
/*  68 */     addMouseListener(new java.awt.event.MouseAdapter()
/*     */     {
/*     */       public void mousePressed(java.awt.event.MouseEvent paramAnonymousMouseEvent)
/*     */       {
/*  72 */         if (!NetworkPanel.this.hasNode()) {
/*  73 */           NetworkPanel.this.downNode = -2;return;
/*     */         }
/*  75 */         NetworkPanel.this.downPoint.move(paramAnonymousMouseEvent.getX(), paramAnonymousMouseEvent.getY());
/*     */         
/*  77 */         if (NetworkPanel.this.mode == 1) {
/*  78 */           if (NetworkPanel.this.bn != null) NetworkPanel.this.downNode = NetworkPanel.this.bn.isClose(NetworkPanel.this.downPoint, 6); else
/*  79 */             NetworkPanel.this.downNode = NetworkPanel.this.mn.isClose(NetworkPanel.this.downPoint, 6);
/*  80 */           NetworkPanel.this.mouseDownInEvi(NetworkPanel.this.downNode);
/*     */         }
/*  82 */         else if (NetworkPanel.this.mode == 3) {
/*  83 */           if (NetworkPanel.this.jt != null) NetworkPanel.this.downNode = NetworkPanel.this.jt.isClose(NetworkPanel.this.downPoint, 6);
/*  84 */           NetworkPanel.this.mouseDownInProb(NetworkPanel.this.downNode);
/*     */         }
/*  86 */         NetworkPanel.this.repaint();
/*     */       }
/*     */     });
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private void initMouse()
/*     */   {
/*  96 */     this.downPoint = new java.awt.Point(0, 0);
/*  97 */     this.downNode = -2;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private void mouseDownInEvi(int paramInt)
/*     */   {
/* 104 */     if (paramInt >= 0) {
/*     */       boolean bool;
/* 106 */       if (this.bn != null) bool = this.bn.isObserved(paramInt); else
/* 107 */         bool = this.mn.isObserved(paramInt);
/* 108 */       if (!bool) {
/* 109 */         HelpPanel.addHelp("Enter 0 for impossible value and 1 otherwise.");
/* 110 */         this.eviDialog = new Network.VectorDialog(this.frame, this, getLabel(paramInt), getState(paramInt), getDefaultEvi(paramInt), 16, 100, 100);
/*     */         
/*     */ 
/*     */ 
/*     */ 
/* 115 */         this.eviDialog.setVisible(true);
/*     */       } else {
/* 117 */         HelpPanel.showError("Evidence on this node has been entered!");
/*     */       } }
/* 119 */     this.pmode = 0;
/* 120 */     repaint();
/*     */   }
/*     */   
/*     */   String getLabel(int paramInt) {
/* 124 */     if (this.bn != null) return this.bn.getLabel(paramInt);
/* 125 */     return this.mn.getLabel(paramInt);
/*     */   }
/*     */   
/* 128 */   String[] getState(int paramInt) { if (this.bn != null) return this.bn.getState(paramInt);
/* 129 */     return this.mn.getState(paramInt);
/*     */   }
/*     */   
/*     */   int getStateCount(int paramInt) {
/* 133 */     if (this.bn != null) return this.bn.getStateCount(paramInt);
/* 134 */     return this.mn.getStateCount(paramInt);
/*     */   }
/*     */   
/* 137 */   int[] getDefaultEvi(int paramInt) { int i = getStateCount(paramInt);
/* 138 */     int[] arrayOfInt = new int[i];
/* 139 */     for (int j = 0; j < i; j++) arrayOfInt[j] = 1;
/* 140 */     return arrayOfInt;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private void mouseDownInProb(int paramInt)
/*     */   {
/* 147 */     if (paramInt >= 0)
/*     */     {
/* 149 */       this.frame.setCursor(new java.awt.Cursor(3));
/* 150 */       Network.ProbDialog localProbDialog = new Network.ProbDialog(this.frame, this, "Cluster " + this.jt.getLabel(paramInt), getCqMemberLabel(paramInt), getCqMemberState(paramInt), this.jt.getBelief(paramInt), paramInt, 12, 10, 10, 100);
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 156 */       localProbDialog.setVisible(true);
/* 157 */       this.frame.setCursor(new java.awt.Cursor(0));
/* 158 */       repaint();
/*     */     }
/*     */   }
/*     */   
/*     */   String[] getCqMemberLabel(int paramInt)
/*     */   {
/* 164 */     int[] arrayOfInt = this.jt.getCqMember(paramInt);
/* 165 */     int i = arrayOfInt.length;
/* 166 */     String[] arrayOfString = new String[i];
/* 167 */     for (int j = 0; j < i; j++)
/* 168 */       arrayOfString[j] = (this.varName[arrayOfInt[j]] + ", " + arrayOfInt[j]);
/* 169 */     return arrayOfString;
/*     */   }
/*     */   
/*     */   String[][] getCqMemberState(int paramInt) {
/* 173 */     int[] arrayOfInt = this.jt.getCqMember(paramInt);
/* 174 */     int i = arrayOfInt.length;
/* 175 */     String[][] arrayOfString = new String[i][];
/* 176 */     for (int j = 0; j < i; j++)
/* 177 */       arrayOfString[j] = Network.UTIL.getDuplicate(this.varState[arrayOfInt[j]]);
/* 178 */     return arrayOfString;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setVector(int[] paramArrayOfInt)
/*     */   {
/* 185 */     int i = 0;
/* 186 */     for (int j = 0; j < paramArrayOfInt.length; j++) i += paramArrayOfInt[j];
/* 187 */     if ((i > 0) && (i < paramArrayOfInt.length)) {
/* 188 */       if (this.bn != null) this.bn.setObserved(this.downNode); else {
/* 189 */         this.mn.setObserved(this.downNode);
/*     */       }
/* 191 */       this.jt.enterEvidenceToCq(this.downNode, paramArrayOfInt);
/* 192 */       this.jt.enterEvidence1By1(this.downNode);
/*     */     } else {
/* 194 */       HelpPanel.showError("Invalid evidence pattern entered!"); } }
/*     */   
/*     */   public void setVector(String[] paramArrayOfString) {}
/*     */   
/*     */   public void setVector2(String[] paramArrayOfString) {}
/*     */   
/*     */   public void setVector2(int[] paramArrayOfInt) {}
/*     */   public void setVector(float[] paramArrayOfFloat) {}
/*     */   public void setVector(boolean[] paramArrayOfBoolean) {}
/*     */   public void setTable(float[] paramArrayOfFloat) {}
/* 204 */   public float[] getVector(int paramInt) { return this.jt.getBelief(paramInt); }
/*     */   
/* 206 */   public String[] getVector2(int paramInt) { return null; }
/* 207 */   public String[] getVector3(int paramInt) { return null; }
/*     */   
/*     */ 
/*     */   void logBelief()
/*     */   {
/* 212 */     String str = new String("logpb." + this.logIndex++);
/*     */     try {
/* 214 */       java.io.PrintWriter localPrintWriter = new java.io.PrintWriter(new java.io.FileWriter(str));
/* 215 */       HelpPanel.addHelp("Save log file " + str);
/* 216 */       if (this.bn != null) this.jt.saveBelief(localPrintWriter, this.bn); else
/* 217 */         this.jt.saveBelief(localPrintWriter, this.mn);
/* 218 */       localPrintWriter.close();
/*     */     } catch (java.io.IOException localIOException) {
/* 220 */       HelpPanel.showError("Unable to save " + str);
/*     */     }
/* 222 */     HelpPanel.addHelp("Saving completed.");
/* 223 */     this.pmode = 0;
/* 224 */     repaint();
/*     */   }
/*     */   
/*     */ 
/*     */   void loadInit()
/*     */   {
/* 230 */     this.bn = null;
/* 231 */     this.mn = null;
/* 232 */     this.jt = null;
/*     */   }
/*     */   
/*     */   void loadNetJt()
/*     */   {
/* 237 */     loadInit();
/* 238 */     String str = loadMnOrBn();
/* 239 */     HelpPanel.addHelp("Network loading completed.");
/* 240 */     repaint();
/* 241 */     this.jt = JoinForest.load(str + ".jt");
/* 242 */     HelpPanel.addHelp("Join tree loading completed.");
/* 243 */     if (this.bn != null) this.jt.getSet(this.bn); else
/* 244 */       this.jt.getSet(this.mn);
/* 245 */     HelpPanel.addHelp("Ready to answer queries.");
/*     */     
/* 247 */     this.eviLoaded = false;
/*     */   }
/*     */   
/*     */ 
/*     */   String loadMnOrBn()
/*     */   {
/* 253 */     FileDialog localFileDialog = new FileDialog(this.frame, "Load File", 0);
/* 254 */     localFileDialog.pack();
/* 255 */     localFileDialog.setVisible(true);
/*     */     
/* 257 */     String str = new String(localFileDialog.getDirectory() + localFileDialog.getFile());
/* 258 */     if (str == null) return null;
/* 259 */     str = new String(Network.UTIL.removePostfix(str));
/*     */     try
/*     */     {
/* 262 */       java.io.BufferedReader localBufferedReader1 = new java.io.BufferedReader(new java.io.FileReader(str + ".bn"));
/* 263 */       HelpPanel.addHelp("Loading network from " + str + ".bn");
/* 264 */       this.bn = BayesNet.loadSkipPb(localBufferedReader1);
/* 265 */       this.varName = this.bn.getLabel();
/* 266 */       this.varState = this.bn.getState();
/* 267 */       localBufferedReader1.close();return str;
/*     */     } catch (java.io.IOException localIOException1) {
/* 269 */       HelpPanel.addHelp("No .bn file found.  Try open .mn file.");
/*     */       try {
/* 271 */         java.io.BufferedReader localBufferedReader2 = new java.io.BufferedReader(new java.io.FileReader(str + ".mn"));
/* 272 */         HelpPanel.addHelp("Loading network from " + str + ".mn");
/* 273 */         this.mn = MarkovNet.load(localBufferedReader2);
/* 274 */         this.varName = this.mn.getLabel();
/* 275 */         this.varState = this.mn.getState();
/* 276 */         localBufferedReader2.close();return str;
/*     */       } catch (java.io.IOException localIOException2) {
/* 278 */         HelpPanel.showError("No .mn file found either!"); } }
/* 279 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   void loadEviStamp()
/*     */   {
/* 286 */     if (this.eviLoaded) {
/* 287 */       HelpPanel.addHelp("Evidence has been loaded.");return;
/*     */     }
/*     */     
/* 290 */     FileDialog localFileDialog = new FileDialog(this.frame, "Load File", 0);
/*     */     
/* 292 */     localFileDialog.pack();localFileDialog.setVisible(true);
/*     */     
/* 294 */     String str = new String(localFileDialog.getDirectory() + localFileDialog.getFile());
/*     */     
/* 296 */     if (str == null) return;
/* 297 */     str = new String(Network.UTIL.removePostfix(str));
/*     */     
/* 299 */     int i = this.bn.getNodeCount();
/*     */     try {
/* 301 */       java.io.BufferedReader localBufferedReader = new java.io.BufferedReader(new java.io.FileReader(str + ".evi"));
/* 302 */       HelpPanel.addHelp("Loading evidence from " + str + ".evi");
/* 303 */       this.bn.loadVarValue(localBufferedReader);
/*     */       
/* 305 */       for (int j = 0; j < i; j++) {
/* 306 */         int k = this.bn.getVarValue(j);
/* 307 */         if (k != -1)
/* 308 */           this.jt.enterEvidenceToCq(j, k);
/*     */       }
/* 310 */       this.jt.unifyBelief();
/*     */       
/* 312 */       localBufferedReader = new java.io.BufferedReader(new java.io.FileReader(str + ".stp"));
/* 313 */       HelpPanel.addHelp("Loading observability from " + str + ".stp");
/* 314 */       this.bn.loadStamp(localBufferedReader);
/* 315 */       this.eviLoaded = true;
/*     */       
/* 317 */       repaint();localBufferedReader.close();
/*     */     } catch (java.io.IOException localIOException) {
/* 319 */       HelpPanel.showError("Failure in loading .evi and .stp files!");
/* 320 */       this.eviLoaded = false;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   boolean hasNode()
/*     */   {
/* 329 */     if ((this.bn == null) && (this.mn == null)) return false;
/* 330 */     if (this.jt == null) return false;
/* 331 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void showNet()
/*     */   {
/* 338 */     update(getGraphics());
/*     */   }
/*     */   
/*     */   void showBelief() {
/* 342 */     repaint();
/*     */   }
/*     */   
/*     */   public void paint(java.awt.Graphics paramGraphics)
/*     */   {
/* 347 */     if ((this.bn != null) && (this.pmode == 0)) {
/* 348 */       NetPaint.paintNet(paramGraphics, this.bn, this.arrowLength, false);
/* 349 */     } else if ((this.bn != null) && (this.pmode == 1)) {
/* 350 */       NetPaint.paintNet(paramGraphics, this.bn, this.arrowLength, false);
/* 351 */       NetPaint.showBelief(this, paramGraphics, this.bn, this.jt);
/*     */     }
/* 353 */     else if ((this.mn != null) && (this.pmode == 0)) {
/* 354 */       NetPaint.paintNet(paramGraphics, this.mn);
/* 355 */     } else if ((this.mn != null) && (this.pmode == 1)) {
/* 356 */       NetPaint.paintNet(paramGraphics, this.mn);
/* 357 */       NetPaint.showBelief(this, paramGraphics, this.mn, this.jt);
/*     */     }
/* 359 */     else if ((this.jt != null) && (this.pmode == 2)) { NetPaint.paintNet(paramGraphics, this.jt);
/*     */     }
/*     */   }
/*     */   
/*     */   void setArrow() {
/* 364 */     this.arrowLength -= 2.0F;
/* 365 */     if (this.arrowLength < 10.0F) this.arrowLength = 20.0F;
/* 366 */     repaint();
/*     */   }
/*     */   
/*     */ 
/*     */   public final synchronized void update(java.awt.Graphics paramGraphics)
/*     */   {
/* 372 */     Dimension localDimension = getSize();
/* 373 */     if ((this.offScreenImage == null) || (localDimension.width != this.offScreenSize.width) || (localDimension.height != this.offScreenSize.height))
/*     */     {
/* 375 */       this.offScreenImage = createImage(localDimension.width, localDimension.height);
/* 376 */       this.offScreenSize = localDimension;
/* 377 */       this.offScreenGraphics = this.offScreenImage.getGraphics();
/*     */     }
/* 379 */     this.offScreenGraphics.setColor(NetPaint.backgroundColor);
/* 380 */     this.offScreenGraphics.fillRect(0, 0, localDimension.width, localDimension.height);
/* 381 */     paint(this.offScreenGraphics);
/* 382 */     paramGraphics.drawImage(this.offScreenImage, 0, 0, null);
/*     */   }
/*     */   
/*     */   void moveNet()
/*     */   {
/* 387 */     if (this.pmode != 2) {
/* 388 */       if (this.bn != null) NetPaint.moveNet(this, this.bn); else {
/* 389 */         NetPaint.moveNet(this, this.mn);
/*     */       }
/*     */     }
/* 392 */     else if (this.jt != null) { NetPaint.moveNet(this, this.jt);
/*     */     }
/* 394 */     repaint();
/*     */   }
/*     */   
/*     */   void setMode(String paramString)
/*     */   {
/* 399 */     if (paramString.equals(CommandPanel.eviLabel)) {
/* 400 */       this.mode = 1;this.pmode = 0;
/*     */     }
/* 402 */     else if (paramString.equals(CommandPanel.mvNetLabel)) {
/* 403 */       this.mode = 2;
/*     */     }
/* 405 */     else if (paramString.equals(CommandPanel.probLabel)) {
/* 406 */       this.mode = 3;this.pmode = 2;
/*     */     }
/* 408 */     else if (paramString.equals(CommandPanel.beliefLabel)) {
/* 409 */       this.mode = 0;this.pmode = 1;
/*     */     }
/*     */     else {
/* 412 */       this.mode = 0;this.pmode = 0;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   void isServerReady()
/*     */   {
/* 420 */     this.jt.unifyBelief();
/* 421 */     this.bn.setMarginalProb(this.jt);
/* 422 */     this.bn.setVarValue();
/* 423 */     if (this.bn.isVarValueSet()) {
/* 424 */       this.ready = true;
/* 425 */       HelpPanel.addHelp("Ready to serve evi request.");
/*     */     }
/*     */     else {
/* 428 */       this.ready = false;
/* 429 */       HelpPanel.addHelp("Uncertain world.  Instantiate more vars.");
/*     */     }
/*     */   }
/*     */   
/*     */   void startServer() throws java.net.UnknownHostException {
/* 434 */     HelpPanel.addHelp("Start server socket ...");
/* 435 */     String str1 = java.net.InetAddress.getLocalHost().toString();
/*     */     
/* 437 */     int i = 0;
/*     */     try {
/* 439 */       java.net.ServerSocket localServerSocket = new java.net.ServerSocket(i);
/* 440 */       i = localServerSocket.getLocalPort();
/* 441 */       HelpPanel.addHelp("Address: " + str1 + ", Port: " + i);
/*     */       for (;;)
/*     */       {
/* 444 */         java.net.Socket localSocket = localServerSocket.accept();
/* 445 */         String str2 = localSocket.getInetAddress().getHostName();
/* 446 */         HelpPanel.addHelp(str2 + " request: ");
/* 447 */         new RequestHandler(this, this.bn, localSocket).start();
/*     */       }
/*     */     } catch (java.io.IOException localIOException) {
/* 450 */       HelpPanel.addHelp("Port " + i + " may be busy. Try another...");
/*     */     }
/*     */   }
/*     */ }


/* Location:              /home/anali/INAOE/tesis/codigos/topub/exe/ww4.jar!/Simulatr/NetworkPanel.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */