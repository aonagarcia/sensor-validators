/*     */ package LThinker;
/*     */ 
/*     */ import Network.BayesNet;
/*     */ import Network.HelpPanel;
/*     */ import Network.LJoinForest;
/*     */ import Network.NetPaint;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Graphics;
/*     */ import java.io.IOException;
/*     */ 
/*     */ class NetworkPanel extends java.awt.Canvas implements Network.Bridge
/*     */ {
/*     */   java.awt.Frame frame;
/*  14 */   BayesNet bn = null;
/*  15 */   LJoinForest jt = null;
/*  16 */   String[] varName = null;
/*  17 */   String[][] varState = (String[][])null;
/*     */   
/*     */ 
/*  20 */   java.awt.Point downPoint = new java.awt.Point(0, 0);
/*  21 */   int downNode = -2;
/*     */   
/*     */ 
/*  24 */   float arrowLength = 20.0F;
/*  25 */   final float maxLength = 20.0F;
/*     */   
/*     */   static final int START_MODE = 0;
/*     */   
/*     */   static final int GET_EVI_MODE = 1;
/*     */   
/*     */   static final int MOVE_NODE_MODE = 2;
/*     */   static final int SEE_PROB_MODE = 3;
/*     */   static final int REQ_EVI_MODE = 4;
/*  34 */   int mode = 0;
/*     */   
/*     */   static final int NO_HIST_MODE = 0;
/*     */   
/*     */   static final int HIST_MODE = 1;
/*     */   
/*     */   static final int JF_MODE = 2;
/*  41 */   int pmode = 0;
/*     */   
/*     */   private java.awt.Image offScreenImage;
/*     */   
/*     */   private Graphics offScreenGraphics;
/*     */   
/*     */   private Dimension offScreenSize;
/*     */   
/*  49 */   int logIndex = 0;
/*     */   
/*     */ 
/*  52 */   String server = null;
/*  53 */   String portNum = null;
/*     */   
/*     */   public NetworkPanel(java.awt.Frame paramFrame)
/*     */   {
/*  57 */     initMouse();
/*  58 */     this.frame = paramFrame;
/*  59 */     setBackground(NetPaint.backgroundColor);
/*  60 */     this.bn = null;
/*  61 */     this.jt = null;
/*     */     
/*     */ 
/*  64 */     addMouseListener(new java.awt.event.MouseAdapter()
/*     */     {
/*     */       public void mousePressed(java.awt.event.MouseEvent paramAnonymousMouseEvent) {
/*  67 */         if (!NetworkPanel.this.hasNode()) NetworkPanel.this.downNode = -2;
/*  68 */         NetworkPanel.this.downPoint.move(paramAnonymousMouseEvent.getX(), paramAnonymousMouseEvent.getY());
/*  69 */         if (NetworkPanel.this.mode == 1) {
/*  70 */           if (NetworkPanel.this.bn != null) { NetworkPanel.this.downNode = NetworkPanel.this.bn.isClose(NetworkPanel.this.downPoint, 6);
/*     */           }
/*  72 */           NetworkPanel.this.mouseDownInEvi(NetworkPanel.this.downNode);
/*     */         }
/*  74 */         else if (NetworkPanel.this.mode == 3) {
/*  75 */           if (NetworkPanel.this.jt != null) { NetworkPanel.this.downNode = NetworkPanel.this.jt.isClose(NetworkPanel.this.downPoint, 6);
/*     */           }
/*  77 */           NetworkPanel.this.mouseDownInProb(NetworkPanel.this.downNode);
/*     */         }
/*  79 */         else if (NetworkPanel.this.mode == 4) {
/*  80 */           if (NetworkPanel.this.bn != null) { NetworkPanel.this.downNode = NetworkPanel.this.bn.isClose(NetworkPanel.this.downPoint, 6);
/*     */           }
/*  82 */           NetworkPanel.this.mouseDownReqEvi(NetworkPanel.this.downNode);
/*     */         }
/*  84 */         NetworkPanel.this.repaint();
/*     */       }
/*     */     });
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private void initMouse()
/*     */   {
/*  94 */     this.downPoint = new java.awt.Point(0, 0);
/*  95 */     this.downNode = -2;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private void mouseDownReqEvi(int paramInt)
/*     */   {
/* 102 */     if (paramInt >= 0) {
/* 103 */       boolean bool = false;
/* 104 */       if (this.bn != null) bool = this.bn.isObserved(paramInt);
/* 105 */       if (bool) { HelpPanel.showError("This variable has been observed.");
/*     */       }
/* 107 */       else if (this.server == null) {
/* 108 */         HelpPanel.addHelp("Please enter server address/port.");
/* 109 */         Network.VectorDialog localVectorDialog = new Network.VectorDialog(this.frame, this, "Simulator Infor", getPrompt(), getDefault(), 16, 100, 100);
/*     */         
/*     */ 
/* 112 */         localVectorDialog.setVisible(true);
/*     */       } else {
/* 114 */         requestAndEnterEvi(paramInt);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   String[] getPrompt() {
/* 120 */     String[] arrayOfString = { "Server Name", "Port Number" };
/* 121 */     return arrayOfString;
/*     */   }
/*     */   
/*     */   String[] getDefault() {
/* 125 */     String[] arrayOfString = new String[2];
/* 126 */     return arrayOfString;
/*     */   }
/*     */   
/*     */   int getStateCount(int paramInt) {
/* 130 */     return this.bn.getStateCount(paramInt);
/*     */   }
/*     */   
/*     */   void requestAndEnterEvi(int paramInt)
/*     */   {
/* 135 */     String str1 = this.bn.getLabel(paramInt);
/*     */     
/* 137 */     String str2 = reqVarValue(str1);
/* 138 */     if (str2 == null) { return;
/*     */     }
/* 140 */     int i = getStateCount(paramInt);
/* 141 */     int[] arrayOfInt = new int[i];
/* 142 */     for (int j = 0; j < i; j++) { arrayOfInt[j] = 0;
/*     */     }
/* 144 */     j = this.bn.getStateIndex(paramInt, str2);
/* 145 */     arrayOfInt[j] = 1;
/* 146 */     enterEvi(paramInt, arrayOfInt);
/*     */   }
/*     */   
/*     */   void enterEvi(int paramInt, int[] paramArrayOfInt)
/*     */   {
/* 151 */     int i = 0;
/* 152 */     for (int j = 0; j < paramArrayOfInt.length; j++) i += paramArrayOfInt[j];
/* 153 */     if ((i > 0) && (i < paramArrayOfInt.length)) {
/* 154 */       if (this.bn != null) this.bn.setObserved(paramInt);
/* 155 */       this.jt.enterEvidenceToCq(paramInt, paramArrayOfInt);
/* 156 */       this.jt.unifyPotential();
/*     */     } else {
/* 158 */       HelpPanel.showError("Invalid evidence pattern entered!");
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   String reqVarValue(String paramString)
/*     */   {
/* 167 */     java.net.Socket localSocket = null;
/* 168 */     int i = Integer.parseInt(this.portNum);
/*     */     try {
/* 170 */       localSocket = new java.net.Socket(this.server, i);
/*     */     } catch (java.net.UnknownHostException localUnknownHostException) {
/* 172 */       HelpPanel.showError(localUnknownHostException.getMessage());
/* 173 */       this.server = null;this.portNum = null;return null;
/*     */     } catch (IOException localIOException1) {
/* 175 */       HelpPanel.showError(localIOException1.getMessage());
/* 176 */       this.server = null;this.portNum = null;return null;
/*     */     }
/*     */     try
/*     */     {
/* 180 */       java.io.BufferedReader localBufferedReader = new java.io.BufferedReader(new java.io.InputStreamReader(localSocket.getInputStream()));
/*     */       
/* 182 */       java.io.PrintWriter localPrintWriter = new java.io.PrintWriter(new java.io.BufferedWriter(new java.io.OutputStreamWriter(localSocket.getOutputStream())), true);
/*     */       
/*     */ 
/* 185 */       localPrintWriter.println(paramString);
/*     */       
/* 187 */       String str1 = localBufferedReader.readLine();
/* 188 */       java.util.StringTokenizer localStringTokenizer = new java.util.StringTokenizer(str1);
/* 189 */       if (!localStringTokenizer.nextToken().equals(paramString)) {
/* 190 */         HelpPanel.showError("Incorrect var name in reply.");
/* 191 */         return null;
/*     */       }
/* 193 */       String str2 = localStringTokenizer.nextToken();
/* 194 */       if (str2.equals("0")) {
/* 195 */         HelpPanel.showError("Invalid request or unobservable variable.");
/* 196 */         return null;
/*     */       }
/* 198 */       if (str2.equals("-1")) {
/* 199 */         HelpPanel.showError("Simulator not ready.");return null;
/*     */       }
/* 201 */       return localStringTokenizer.nextToken();
/*     */     } catch (IOException localIOException2) {
/* 203 */       HelpPanel.showError(localIOException2.getMessage()); }
/* 204 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void mouseDownInEvi(int paramInt)
/*     */   {
/* 213 */     if (paramInt >= 0) {
/* 214 */       boolean bool = this.bn.isObserved(paramInt);
/* 215 */       if (!bool) {
/* 216 */         HelpPanel.addHelp("Enter 0 for impossible value and 1 otherwise.");
/* 217 */         Network.VectorDialog localVectorDialog = new Network.VectorDialog(this.frame, this, getLabel(paramInt), getState(paramInt), getDefaultEvi(paramInt), 16, 100, 100);
/*     */         
/*     */ 
/* 220 */         localVectorDialog.setVisible(true);
/*     */       }
/*     */       else {
/* 223 */         HelpPanel.showError("Evidence on this node has been entered!");
/*     */       } }
/* 225 */     this.pmode = 0;
/* 226 */     repaint();
/*     */   }
/*     */   
/*     */   String getLabel(int paramInt) {
/* 230 */     return this.bn.getLabel(paramInt);
/*     */   }
/*     */   
/*     */   String[] getState(int paramInt) {
/* 234 */     return this.bn.getState(paramInt);
/*     */   }
/*     */   
/*     */   int[] getDefaultEvi(int paramInt) {
/* 238 */     int i = getStateCount(paramInt);
/* 239 */     int[] arrayOfInt = new int[i];
/* 240 */     for (int j = 0; j < i; j++) arrayOfInt[j] = 1;
/* 241 */     return arrayOfInt;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private void mouseDownInProb(int paramInt)
/*     */   {
/* 248 */     if (paramInt >= 0) {
/* 249 */       this.frame.setCursor(new java.awt.Cursor(3));
/* 250 */       Network.ProbDialog localProbDialog = new Network.ProbDialog(this.frame, this, "Cluster " + this.jt.getLabel(paramInt), getCqMemNFOLabel(paramInt), getCqMemNFOState(paramInt), this.jt.setProdPotenPlusMsg(paramInt), paramInt, 12, 10, 10, 100);
/*     */       
/*     */ 
/*     */ 
/* 254 */       localProbDialog.setVisible(true);
/* 255 */       this.frame.setCursor(new java.awt.Cursor(0));
/* 256 */       repaint();
/*     */     }
/*     */   }
/*     */   
/*     */   String[] getCqMemNFOLabel(int paramInt)
/*     */   {
/* 262 */     int[] arrayOfInt = this.jt.getCqMemNotFullyObserved(paramInt);
/* 263 */     int i = arrayOfInt.length;
/* 264 */     String[] arrayOfString = new String[i];
/* 265 */     for (int j = 0; j < i; j++)
/* 266 */       arrayOfString[j] = (this.varName[arrayOfInt[j]] + ", " + arrayOfInt[j]);
/* 267 */     return arrayOfString;
/*     */   }
/*     */   
/*     */   String[][] getCqMemNFOState(int paramInt)
/*     */   {
/* 272 */     int[] arrayOfInt = this.jt.getCqMemNotFullyObserved(paramInt);
/* 273 */     int i = arrayOfInt.length;
/* 274 */     String[][] arrayOfString = new String[i][];
/* 275 */     for (int j = 0; j < i; j++)
/* 276 */       arrayOfString[j] = Network.UTIL.getDuplicate(this.varState[arrayOfInt[j]]);
/* 277 */     return arrayOfString;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setVector(int[] paramArrayOfInt)
/*     */   {
/* 284 */     enterEvi(this.downNode, paramArrayOfInt);
/*     */   }
/*     */   
/*     */   public void setVector(String[] paramArrayOfString) {
/* 288 */     this.server = new String(paramArrayOfString[0]);
/* 289 */     this.portNum = new String(paramArrayOfString[1]);
/* 290 */     requestAndEnterEvi(this.downNode); }
/*     */   
/*     */   public void setVector2(String[] paramArrayOfString) {}
/*     */   
/*     */   public void setVector2(int[] paramArrayOfInt) {}
/*     */   
/*     */   public void setVector(float[] paramArrayOfFloat) {}
/*     */   public void setVector(boolean[] paramArrayOfBoolean) {}
/*     */   public void setTable(float[] paramArrayOfFloat) {}
/* 299 */   public float[] getVector(int paramInt) { return this.jt.setProdPotenPlusMsg(paramInt); }
/*     */   
/*     */ 
/* 302 */   public String[] getVector2(int paramInt) { return null; }
/* 303 */   public String[] getVector3(int paramInt) { return null; }
/*     */   
/*     */ 
/*     */ 
/*     */   void logBelief()
/*     */   {
/* 309 */     String str = new String("logpb." + this.logIndex++);
/*     */     try {
/* 311 */       java.io.PrintWriter localPrintWriter = new java.io.PrintWriter(new java.io.FileWriter(str));
/* 312 */       HelpPanel.addHelp("Save log file " + str);
/* 313 */       this.jt.unifyPotential();
/* 314 */       this.jt.saveBelief(localPrintWriter, this.bn);
/* 315 */       localPrintWriter.close();
/*     */     } catch (IOException localIOException) {
/* 317 */       HelpPanel.showError("Unable to save " + str);
/*     */     }
/* 319 */     HelpPanel.addHelp("Saving completed.");
/* 320 */     this.pmode = 0;
/* 321 */     repaint();
/*     */   }
/*     */   
/*     */ 
/*     */   void loadInit()
/*     */   {
/* 327 */     this.bn = null;
/* 328 */     this.jt = null;
/* 329 */     this.server = null;
/* 330 */     this.portNum = null;
/*     */   }
/*     */   
/*     */   void loadNetJt()
/*     */   {
/* 335 */     loadInit();
/* 336 */     String str = loadMnOrBn();
/* 337 */     if (str == null) return;
/* 338 */     HelpPanel.addHelp("Network loading completed.");
/* 339 */     repaint();
/* 340 */     this.jt = LJoinForest.load(str + ".ljt");
/* 341 */     HelpPanel.addHelp("Join tree loading completed.");
/* 342 */     if (this.bn != null) this.jt.getSet(this.bn);
/* 343 */     HelpPanel.addHelp("Ready to answer queries.");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   String loadMnOrBn()
/*     */   {
/* 350 */     java.awt.FileDialog localFileDialog = new java.awt.FileDialog(this.frame, "Load File", 0);
/*     */     
/* 352 */     localFileDialog.pack();
/* 353 */     localFileDialog.setVisible(true);
/*     */     
/* 355 */     String str1 = localFileDialog.getDirectory();
/* 356 */     String str2 = localFileDialog.getFile();
/* 357 */     String str3 = new String(str1 + str2);
/* 358 */     if ((str1 == null) || (str2 == null)) return null;
/* 359 */     str3 = new String(Network.UTIL.removePostfix(str3));
/*     */     try
/*     */     {
/* 362 */       java.io.BufferedReader localBufferedReader = new java.io.BufferedReader(new java.io.FileReader(str3 + ".bn"));
/* 363 */       HelpPanel.addHelp("Loading network from " + str3 + ".bn");
/* 364 */       this.bn = BayesNet.loadSkipPb(localBufferedReader);
/* 365 */       this.varName = this.bn.getLabel();
/* 366 */       this.varState = this.bn.getState();
/* 367 */       localBufferedReader.close();return str3;
/*     */     } catch (IOException localIOException) {
/* 369 */       HelpPanel.addHelp("No .bn file found."); }
/* 370 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   boolean hasNode()
/*     */   {
/* 379 */     if ((this.bn == null) || (this.jt == null)) return false;
/* 380 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void showNet()
/*     */   {
/* 387 */     update(getGraphics());
/*     */   }
/*     */   
/*     */   void showBelief() {
/* 391 */     repaint();
/*     */   }
/*     */   
/*     */   public void paint(Graphics paramGraphics)
/*     */   {
/* 396 */     if ((this.bn != null) && (this.pmode == 0)) {
/* 397 */       NetPaint.paintNet(paramGraphics, this.bn, this.arrowLength, false);
/* 398 */     } else if ((this.bn != null) && (this.pmode == 1)) {
/* 399 */       NetPaint.paintNet(paramGraphics, this.bn, this.arrowLength, false);
/* 400 */       this.jt.unifyPotential();
/* 401 */       NetPaint.showBelief(this, paramGraphics, this.bn, this.jt);
/*     */     }
/* 403 */     else if ((this.jt != null) && (this.pmode == 2)) { NetPaint.paintNet(paramGraphics, this.jt);
/*     */     }
/*     */   }
/*     */   
/*     */   void setArrow() {
/* 408 */     this.arrowLength -= 2.0F;
/* 409 */     if (this.arrowLength < 10.0F) this.arrowLength = 20.0F;
/* 410 */     repaint();
/*     */   }
/*     */   
/*     */ 
/*     */   public final synchronized void update(Graphics paramGraphics)
/*     */   {
/* 416 */     Dimension localDimension = getSize();
/* 417 */     if ((this.offScreenImage == null) || (localDimension.width != this.offScreenSize.width) || (localDimension.height != this.offScreenSize.height))
/*     */     {
/* 419 */       this.offScreenImage = createImage(localDimension.width, localDimension.height);
/*     */       
/* 421 */       this.offScreenSize = localDimension;
/* 422 */       this.offScreenGraphics = this.offScreenImage.getGraphics();
/*     */     }
/*     */     
/* 425 */     this.offScreenGraphics.setColor(NetPaint.backgroundColor);
/* 426 */     this.offScreenGraphics.fillRect(0, 0, localDimension.width, localDimension.height);
/*     */     
/* 428 */     paint(this.offScreenGraphics);
/* 429 */     paramGraphics.drawImage(this.offScreenImage, 0, 0, null);
/*     */   }
/*     */   
/*     */   void moveNet()
/*     */   {
/* 434 */     if (this.pmode != 2) {
/* 435 */       if (this.bn != null) { NetPaint.moveNet(this, this.bn);
/*     */       }
/*     */     }
/* 438 */     else if (this.jt != null) { NetPaint.moveNet(this, this.jt);
/*     */     }
/* 440 */     repaint();
/*     */   }
/*     */   
/*     */   void setMode(String paramString)
/*     */   {
/* 445 */     if (paramString.equals(CommandPanel.eviLabel)) {
/* 446 */       this.mode = 1;this.pmode = 0;
/*     */     }
/* 448 */     else if (paramString.equals(CommandPanel.reqEviLabel)) {
/* 449 */       this.mode = 4;this.pmode = 0;
/*     */     }
/* 451 */     else if (paramString.equals(CommandPanel.mvNodeLabel)) {
/* 452 */       this.mode = 2;
/*     */     }
/* 454 */     else if (paramString.equals(CommandPanel.mvNetLabel)) {
/* 455 */       this.mode = 2;
/*     */     }
/* 457 */     else if (paramString.equals(CommandPanel.probLabel)) {
/* 458 */       this.mode = 3;this.pmode = 2;
/*     */     }
/* 460 */     else if (paramString.equals(CommandPanel.beliefLabel)) {
/* 461 */       this.mode = 0;this.pmode = 1;
/*     */     }
/*     */     else {
/* 464 */       this.mode = 0;this.pmode = 0;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /home/anali/INAOE/tesis/codigos/topub/exe/ww4.jar!/LThinker/NetworkPanel.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */