/*     */ package Thinker;
/*     */ 
/*     */ import Network.BayesNet;
/*     */ import Network.HelpPanel;
/*     */ import Network.JoinForest;
/*     */ import Network.MarkovNet;
/*     */ import Network.NetPaint;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Point;
/*     */ import java.awt.event.MouseEvent;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.IOException;
/*     */ 
/*     */ class NetworkPanel extends java.awt.Canvas implements Network.Bridge
/*     */ {
/*     */   java.awt.Frame frame;
/*  18 */   BayesNet bn = null;
/*  19 */   MarkovNet mn = null;
/*  20 */   JoinForest jt = null;
/*  21 */   String[] varName = null;
/*  22 */   String[][] varState = (String[][])null;
/*     */   
/*     */ 
/*  25 */   Point downPoint = new Point(0, 0);
/*  26 */   int downNode = -2;
/*  27 */   Point upPoint = new Point(0, 0);
/*  28 */   Point oldPos = new Point(0, 0);
/*     */   
/*     */ 
/*  31 */   float arrowLength = 20.0F;
/*  32 */   final float maxLength = 20.0F;
/*     */   
/*     */   static final int START_MODE = 0;
/*     */   
/*     */   static final int GET_EVI_MODE = 1;
/*     */   
/*     */   static final int MOVE_NODE_MODE = 2;
/*     */   static final int SEE_PROB_MODE = 3;
/*     */   static final int REQ_EVI_MODE = 4;
/*     */   static final int MOVE_NET_MODE = 5;
/*  42 */   int mode = 0;
/*     */   
/*     */   static final int NO_HIST_MODE = 0;
/*     */   
/*     */   static final int HIST_MODE = 1;
/*     */   
/*     */   static final int JF_MODE = 2;
/*  49 */   int pmode = 0;
/*     */   
/*     */ 
/*     */   private java.awt.Image offScreenImage;
/*     */   
/*     */ 
/*     */   private Graphics offScreenGraphics;
/*     */   
/*     */   private Dimension offScreenSize;
/*     */   
/*  59 */   int logIndex = 0;
/*  60 */   String logpath = null;
/*     */   
/*     */ 
/*  63 */   String server = null;
/*  64 */   String portNum = null;
/*     */   
/*     */ 
/*  67 */   boolean allone = true;
/*     */   
/*     */   public NetworkPanel(java.awt.Frame paramFrame)
/*     */   {
/*  71 */     initMouse();
/*  72 */     this.frame = paramFrame;
/*  73 */     setBackground(NetPaint.backgroundColor);
/*  74 */     this.bn = null;
/*  75 */     this.mn = null;
/*  76 */     this.jt = null;
/*     */     
/*  78 */     addMouseListener(new java.awt.event.MouseAdapter()
/*     */     {
/*     */       public void mousePressed(MouseEvent paramAnonymousMouseEvent)
/*     */       {
/*  82 */         if (!NetworkPanel.this.hasNode()) {
/*  83 */           NetworkPanel.this.downNode = -2;return;
/*     */         }
/*  85 */         NetworkPanel.this.downPoint.move(paramAnonymousMouseEvent.getX(), paramAnonymousMouseEvent.getY());
/*     */         
/*  87 */         if (NetworkPanel.this.mode == 1) {
/*  88 */           if (NetworkPanel.this.bn != null) NetworkPanel.this.downNode = NetworkPanel.this.bn.isClose(NetworkPanel.this.downPoint, 6); else
/*  89 */             NetworkPanel.this.downNode = NetworkPanel.this.mn.isClose(NetworkPanel.this.downPoint, 6);
/*  90 */           NetworkPanel.this.mouseDownInEvi(NetworkPanel.this.downNode);
/*     */         }
/*  92 */         else if (NetworkPanel.this.mode == 4) {
/*  93 */           if (NetworkPanel.this.bn != null) NetworkPanel.this.downNode = NetworkPanel.this.bn.isClose(NetworkPanel.this.downPoint, 6); else
/*  94 */             NetworkPanel.this.downNode = NetworkPanel.this.mn.isClose(NetworkPanel.this.downPoint, 6);
/*  95 */           NetworkPanel.this.mouseDownReqEvi();
/*     */         }
/*  97 */         else if (NetworkPanel.this.mode == 2) {
/*  98 */           if (NetworkPanel.this.bn != null) NetworkPanel.this.downNode = NetworkPanel.this.bn.isClose(NetworkPanel.this.downPoint, 6); else
/*  99 */             NetworkPanel.this.downNode = NetworkPanel.this.mn.isClose(NetworkPanel.this.downPoint, 6);
/* 100 */           NetworkPanel.this.mouseDownInMove();
/*     */         }
/* 102 */         else if (NetworkPanel.this.mode == 3) {
/* 103 */           if (NetworkPanel.this.jt != null) NetworkPanel.this.downNode = NetworkPanel.this.jt.isClose(NetworkPanel.this.downPoint, 6);
/* 104 */           NetworkPanel.this.mouseDownInProb(NetworkPanel.this.downNode);
/*     */         }
/* 106 */         NetworkPanel.this.repaint();
/*     */       }
/*     */       
/* 109 */       public void mouseReleased(MouseEvent paramAnonymousMouseEvent) { NetworkPanel.this.upPoint.move(paramAnonymousMouseEvent.getX(), paramAnonymousMouseEvent.getY());
/* 110 */         if (NetworkPanel.this.mode == 2) { NetworkPanel.this.mouseUpInMove();
/*     */         }
/* 112 */         NetworkPanel.this.initMouse();
/* 113 */         NetworkPanel.this.repaint();
/*     */       }
/*     */     });
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private void mouseDownInMove()
/*     */   {
/* 122 */     if (this.downNode < 0) return;
/* 123 */     if (this.bn != null) this.oldPos.setLocation(this.bn.getPos(this.downNode)); else {
/* 124 */       this.oldPos.setLocation(this.mn.getPos(this.downNode));
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void mouseUpInMove()
/*     */   {
/* 134 */     if (this.downNode < 0) { return;
/*     */     }
/* 136 */     this.bn.setPos(this.downNode, 0, 0);
/* 137 */     int i = this.bn.isClose(this.upPoint, 6);
/* 138 */     if ((i != -2) || (this.upPoint.x < 0) || (this.upPoint.x > getSize().width) || (this.upPoint.y < 0) || (this.upPoint.y > getSize().height))
/*     */     {
/*     */ 
/* 141 */       this.bn.setPos(this.downNode, this.oldPos);
/* 142 */       HelpPanel.showError("Too close to another node or out of border!");
/*     */     } else {
/* 144 */       this.bn.setPos(this.downNode, this.upPoint);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   private void initMouse()
/*     */   {
/* 151 */     this.downPoint = new Point(0, 0);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private void mouseDownReqEvi()
/*     */   {
/* 159 */     if (this.downNode >= 0) {
/*     */       boolean bool;
/* 161 */       if (this.bn != null) bool = this.bn.isObserved(this.downNode); else
/* 162 */         bool = this.mn.isObserved(this.downNode);
/* 163 */       if (bool) { HelpPanel.showError("This variable has been observed.");
/*     */       }
/* 165 */       else if (this.server == null) {
/* 166 */         HelpPanel.addHelp("Please enter server address/port.");
/* 167 */         Network.VectorDialog localVectorDialog = new Network.VectorDialog(this.frame, this, "Simulator Infor", getPrompt(), getDefault(), 16, 100, 100);
/*     */         
/*     */ 
/*     */ 
/*     */ 
/* 172 */         localVectorDialog.setVisible(true);
/*     */       } else {
/* 174 */         requestAndEnterEvi(this.downNode);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   String[] getPrompt() {
/* 180 */     String[] arrayOfString = { "Server Name", "Port Number" };
/* 181 */     return arrayOfString;
/*     */   }
/*     */   
/*     */   String[] getDefault() {
/* 185 */     String[] arrayOfString = new String[2];
/* 186 */     return arrayOfString;
/*     */   }
/*     */   
/*     */   int getStateCount(int paramInt) {
/* 190 */     if (this.bn != null) return this.bn.getStateCount(paramInt);
/* 191 */     return this.mn.getStateCount(paramInt);
/*     */   }
/*     */   
/*     */   void requestAndEnterEvi(int paramInt)
/*     */   {
/* 196 */     String str1 = null;
/* 197 */     if (this.bn != null) str1 = this.bn.getLabel(paramInt); else {
/* 198 */       str1 = this.mn.getLabel(paramInt);
/*     */     }
/* 200 */     String str2 = reqVarValue(str1);
/* 201 */     if (str2 == null) { return;
/*     */     }
/* 203 */     int i = getStateCount(paramInt);
/* 204 */     int[] arrayOfInt = new int[i];
/* 205 */     for (int j = 0; j < i; j++) { arrayOfInt[j] = 0;
/*     */     }
/*     */     
/* 208 */     if (this.bn != null) j = this.bn.getStateIndex(paramInt, str2); else
/* 209 */       j = this.mn.getStateIndex(paramInt, str2);
/* 210 */     arrayOfInt[j] = 1;
/* 211 */     enterEvi(paramInt, arrayOfInt);
/*     */   }
/*     */   
/*     */   void enterEvi(int paramInt, int[] paramArrayOfInt)
/*     */   {
/* 216 */     int i = 0;
/* 217 */     for (int j = 0; j < paramArrayOfInt.length; j++) i += paramArrayOfInt[j];
/* 218 */     if ((i > 0) && (i < paramArrayOfInt.length)) {
/* 219 */       if (this.bn != null) this.bn.setObserved(paramInt); else {
/* 220 */         this.mn.setObserved(paramInt);
/*     */       }
/* 222 */       this.jt.enterEvidenceToCq(paramInt, paramArrayOfInt);
/* 223 */       this.jt.enterEvidence1By1(paramInt);
/*     */     } else {
/* 225 */       HelpPanel.showError("Invalid evidence pattern entered!");
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   String reqVarValue(String paramString)
/*     */   {
/* 234 */     java.net.Socket localSocket = null;
/* 235 */     int i = Integer.parseInt(this.portNum);
/*     */     try {
/* 237 */       localSocket = new java.net.Socket(this.server, i);
/*     */     } catch (java.net.UnknownHostException localUnknownHostException) {
/* 239 */       HelpPanel.showError(localUnknownHostException.getMessage());
/* 240 */       this.server = null;this.portNum = null;return null;
/*     */     } catch (IOException localIOException1) {
/* 242 */       HelpPanel.showError(localIOException1.getMessage());
/* 243 */       this.server = null;this.portNum = null;return null;
/*     */     }
/*     */     try
/*     */     {
/* 247 */       BufferedReader localBufferedReader = new BufferedReader(new java.io.InputStreamReader(localSocket.getInputStream()));
/*     */       
/* 249 */       java.io.PrintWriter localPrintWriter = new java.io.PrintWriter(new java.io.BufferedWriter(new java.io.OutputStreamWriter(localSocket.getOutputStream())), true);
/*     */       
/*     */ 
/* 252 */       localPrintWriter.println(paramString);
/*     */       
/* 254 */       String str1 = localBufferedReader.readLine();
/* 255 */       java.util.StringTokenizer localStringTokenizer = new java.util.StringTokenizer(str1);
/* 256 */       if (!localStringTokenizer.nextToken().equals(paramString)) {
/* 257 */         HelpPanel.showError("Incorrect var name in reply.");
/* 258 */         return null;
/*     */       }
/* 260 */       String str2 = localStringTokenizer.nextToken();
/* 261 */       if (str2.equals("0")) {
/* 262 */         HelpPanel.showError("Invalid request or unobservable variable.");
/* 263 */         return null;
/*     */       }
/* 265 */       if (str2.equals("-1")) {
/* 266 */         HelpPanel.showError("Simulator not ready.");return null;
/*     */       }
/* 268 */       return localStringTokenizer.nextToken();
/*     */     } catch (IOException localIOException2) {
/* 270 */       HelpPanel.showError(localIOException2.getMessage()); }
/* 271 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private void mouseDownInEvi(int paramInt)
/*     */   {
/* 279 */     if (paramInt >= 0) {
/*     */       boolean bool;
/* 281 */       if (this.bn != null) bool = this.bn.isObserved(paramInt); else
/* 282 */         bool = this.mn.isObserved(paramInt);
/* 283 */       if (!bool) {
/* 284 */         HelpPanel.addHelp("Enter 0 for impossible value and 1 otherwise.");
/* 285 */         Network.VectorDialog localVectorDialog = new Network.VectorDialog(this.frame, this, getLabel(paramInt), getState(paramInt), getDefaultEvi(paramInt), 16, 100, 100);
/*     */         
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 291 */         localVectorDialog.setVisible(true);
/*     */       } else {
/* 293 */         HelpPanel.showError("Evidence on this node has been entered!");
/*     */       } }
/* 295 */     this.pmode = 0;
/* 296 */     repaint();
/*     */   }
/*     */   
/*     */   String getLabel(int paramInt) {
/* 300 */     if (this.bn != null) return this.bn.getLabel(paramInt);
/* 301 */     return this.mn.getLabel(paramInt);
/*     */   }
/*     */   
/* 304 */   String[] getState(int paramInt) { if (this.bn != null) return this.bn.getState(paramInt);
/* 305 */     return this.mn.getState(paramInt);
/*     */   }
/*     */   
/*     */   int[] getDefaultEvi(int paramInt) {
/* 309 */     int i = getStateCount(paramInt);
/* 310 */     int[] arrayOfInt = new int[i];
/* 311 */     int j; if (this.allone) for (j = 0; j < i; j++) arrayOfInt[j] = 1; else
/* 312 */       for (j = 0; j < i; j++) arrayOfInt[j] = 0;
/* 313 */     return arrayOfInt;
/*     */   }
/*     */   
/*     */   void switchEviForm()
/*     */   {
/* 318 */     if (this.allone) {
/* 319 */       this.allone = false;
/* 320 */       HelpPanel.addHelp("Initial evi function is set to all 0.");
/*     */     }
/*     */     else {
/* 323 */       this.allone = true;
/* 324 */       HelpPanel.addHelp("Initial evi function is set to all 1.");
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private void mouseDownInProb(int paramInt)
/*     */   {
/* 332 */     if (paramInt >= 0) {
/* 333 */       this.frame.setCursor(new java.awt.Cursor(3));
/* 334 */       Network.ProbDialog localProbDialog = new Network.ProbDialog(this.frame, this, "Cluster " + this.jt.getLabel(paramInt), getCqMemberLabel(paramInt), getCqMemberState(paramInt), this.jt.getBelief(paramInt), paramInt, 12, 10, 10, 100);
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 341 */       localProbDialog.setVisible(true);
/* 342 */       this.frame.setCursor(new java.awt.Cursor(0));
/* 343 */       repaint();
/*     */     }
/*     */   }
/*     */   
/*     */   String[] getCqMemberLabel(int paramInt)
/*     */   {
/* 349 */     int[] arrayOfInt = this.jt.getCqMember(paramInt);
/* 350 */     int i = arrayOfInt.length;
/* 351 */     String[] arrayOfString = new String[i];
/* 352 */     for (int j = 0; j < i; j++)
/* 353 */       arrayOfString[j] = (this.varName[arrayOfInt[j]] + ", " + arrayOfInt[j]);
/* 354 */     return arrayOfString;
/*     */   }
/*     */   
/*     */   String[][] getCqMemberState(int paramInt) {
/* 358 */     int[] arrayOfInt = this.jt.getCqMember(paramInt);
/* 359 */     int i = arrayOfInt.length;
/* 360 */     String[][] arrayOfString = new String[i][];
/* 361 */     for (int j = 0; j < i; j++)
/* 362 */       arrayOfString[j] = Network.UTIL.getDuplicate(this.varState[arrayOfInt[j]]);
/* 363 */     return arrayOfString;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setVector(int[] paramArrayOfInt)
/*     */   {
/* 370 */     enterEvi(this.downNode, paramArrayOfInt);
/*     */   }
/*     */   
/*     */   public void setVector(String[] paramArrayOfString) {
/* 374 */     this.server = new String(paramArrayOfString[0]);
/* 375 */     this.portNum = new String(paramArrayOfString[1]);
/* 376 */     requestAndEnterEvi(this.downNode); }
/*     */   
/*     */   public void setVector2(String[] paramArrayOfString) {}
/*     */   
/*     */   public void setVector2(int[] paramArrayOfInt) {}
/*     */   
/*     */   public void setVector(float[] paramArrayOfFloat) {}
/*     */   public void setVector(boolean[] paramArrayOfBoolean) {}
/*     */   public void setTable(float[] paramArrayOfFloat) {}
/* 385 */   public float[] getVector(int paramInt) { return this.jt.getBelief(paramInt); }
/*     */   
/* 387 */   public String[] getVector2(int paramInt) { return null; }
/* 388 */   public String[] getVector3(int paramInt) { return null; }
/*     */   
/*     */ 
/*     */   void logBelief()
/*     */   {
/* 393 */     String str = new String("logpb." + this.logIndex++);
/*     */     try {
/* 395 */       java.io.PrintWriter localPrintWriter = new java.io.PrintWriter(new java.io.FileWriter(str));
/* 396 */       HelpPanel.addHelp("Save log file " + str);
/* 397 */       if (this.bn != null) this.jt.saveBelief(localPrintWriter, this.bn); else
/* 398 */         this.jt.saveBelief(localPrintWriter, this.mn);
/* 399 */       localPrintWriter.close();
/*     */     } catch (IOException localIOException) {
/* 401 */       HelpPanel.showError("Unable to save " + str);
/*     */     }
/* 403 */     HelpPanel.addHelp("Saving completed.");
/* 404 */     this.pmode = 0;
/* 405 */     repaint();
/*     */   }
/*     */   
/*     */ 
/*     */   void loadInit()
/*     */   {
/* 411 */     this.bn = null;
/* 412 */     this.mn = null;
/* 413 */     this.jt = null;
/* 414 */     this.server = null;
/* 415 */     this.portNum = null;
/*     */   }
/*     */   
/*     */   void loadNetJt()
/*     */   {
/* 420 */     loadInit();
/* 421 */     String str = loadMnOrBn();
/* 422 */     if (str == null) return;
/* 423 */     HelpPanel.addHelp("Network loading completed.");
/* 424 */     repaint();
/* 425 */     this.jt = JoinForest.load(str + ".jt");
/* 426 */     HelpPanel.addHelp("Join tree loading completed.");
/* 427 */     if (this.bn != null) this.jt.getSet(this.bn); else
/* 428 */       this.jt.getSet(this.mn);
/* 429 */     HelpPanel.addHelp("Ready to answer queries.");
/*     */   }
/*     */   
/*     */ 
/*     */   String loadMnOrBn()
/*     */   {
/* 435 */     java.awt.FileDialog localFileDialog = new java.awt.FileDialog(this.frame, "Load File", 0);
/* 436 */     localFileDialog.pack();
/* 437 */     localFileDialog.setVisible(true);
/*     */     
/* 439 */     String str1 = localFileDialog.getDirectory();
/* 440 */     String str2 = localFileDialog.getFile();
/* 441 */     String str3 = new String(str1 + str2);
/* 442 */     if ((str1 == null) || (str2 == null)) return null;
/* 443 */     str3 = new String(Network.UTIL.removePostfix(str3));
/*     */     try
/*     */     {
/* 446 */       BufferedReader localBufferedReader1 = new BufferedReader(new java.io.FileReader(str3 + ".bn"));
/* 447 */       HelpPanel.addHelp("Loading network from " + str3 + ".bn");
/* 448 */       this.bn = BayesNet.loadSkipPb(localBufferedReader1);
/* 449 */       this.varName = this.bn.getLabel();
/* 450 */       this.varState = this.bn.getState();
/* 451 */       localBufferedReader1.close();return str3;
/*     */     } catch (IOException localIOException1) {
/* 453 */       HelpPanel.addHelp("No .bn file found.  Try open .mn file.");
/*     */       try {
/* 455 */         BufferedReader localBufferedReader2 = new BufferedReader(new java.io.FileReader(str3 + ".mn"));
/* 456 */         HelpPanel.addHelp("Loading network from " + str3 + ".mn");
/* 457 */         this.mn = MarkovNet.load(localBufferedReader2);
/* 458 */         this.varName = this.mn.getLabel();
/* 459 */         this.varState = this.mn.getState();
/* 460 */         localBufferedReader2.close();return str3;
/*     */       } catch (IOException localIOException2) {
/* 462 */         HelpPanel.showError("No .mn file found either!"); } }
/* 463 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   boolean hasNode()
/*     */   {
/* 473 */     if ((this.bn == null) && (this.mn == null)) return false;
/* 474 */     if (this.jt == null) return false;
/* 475 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void showNet()
/*     */   {
/* 482 */     update(getGraphics());
/*     */   }
/*     */   
/*     */   void showBelief() {
/* 486 */     repaint();
/*     */   }
/*     */   
/*     */   public void paint(Graphics paramGraphics)
/*     */   {
/* 491 */     if ((this.bn != null) && (this.pmode == 0)) {
/* 492 */       NetPaint.paintNet(paramGraphics, this.bn, this.arrowLength, false);
/* 493 */     } else if ((this.bn != null) && (this.pmode == 1)) {
/* 494 */       NetPaint.paintNet(paramGraphics, this.bn, this.arrowLength, false);
/* 495 */       NetPaint.showBelief(this, paramGraphics, this.bn, this.jt);
/*     */     }
/* 497 */     else if ((this.mn != null) && (this.pmode == 0)) {
/* 498 */       NetPaint.paintNet(paramGraphics, this.mn);
/* 499 */     } else if ((this.mn != null) && (this.pmode == 1)) {
/* 500 */       NetPaint.paintNet(paramGraphics, this.mn);
/* 501 */       NetPaint.showBelief(this, paramGraphics, this.mn, this.jt);
/*     */     }
/* 503 */     else if ((this.jt != null) && (this.pmode == 2)) { NetPaint.paintNet(paramGraphics, this.jt);
/*     */     }
/*     */   }
/*     */   
/*     */   void setArrow() {
/* 508 */     this.arrowLength -= 2.0F;
/* 509 */     if (this.arrowLength < 10.0F) this.arrowLength = 20.0F;
/* 510 */     repaint();
/*     */   }
/*     */   
/*     */ 
/*     */   public final synchronized void update(Graphics paramGraphics)
/*     */   {
/* 516 */     Dimension localDimension = getSize();
/* 517 */     if ((this.offScreenImage == null) || (localDimension.width != this.offScreenSize.width) || (localDimension.height != this.offScreenSize.height))
/*     */     {
/* 519 */       this.offScreenImage = createImage(localDimension.width, localDimension.height);
/* 520 */       this.offScreenSize = localDimension;
/* 521 */       this.offScreenGraphics = this.offScreenImage.getGraphics();
/*     */     }
/* 523 */     this.offScreenGraphics.setColor(NetPaint.backgroundColor);
/* 524 */     this.offScreenGraphics.fillRect(0, 0, localDimension.width, localDimension.height);
/* 525 */     paint(this.offScreenGraphics);
/* 526 */     paramGraphics.drawImage(this.offScreenImage, 0, 0, null);
/*     */   }
/*     */   
/*     */   void moveNet()
/*     */   {
/* 531 */     if (this.pmode != 2) {
/* 532 */       if (this.bn != null) NetPaint.moveNet(this, this.bn); else {
/* 533 */         NetPaint.moveNet(this, this.mn);
/*     */       }
/*     */     }
/* 536 */     else if (this.jt != null) { NetPaint.moveNet(this, this.jt);
/*     */     }
/* 538 */     repaint();
/*     */   }
/*     */   
/*     */   void setMode(String paramString)
/*     */   {
/* 543 */     if (paramString.equals(CommandPanel.eviLabel)) {
/* 544 */       this.mode = 1;this.pmode = 0;
/*     */     }
/* 546 */     else if (paramString.equals(CommandPanel.reqEviLabel)) {
/* 547 */       this.mode = 4;this.pmode = 0;
/*     */     }
/* 549 */     else if (paramString.equals(CommandPanel.mvNodeLabel)) {
/* 550 */       this.mode = 2;
/*     */     }
/* 552 */     else if (paramString.equals(CommandPanel.mvNetLabel)) {
/* 553 */       this.mode = 5;
/*     */     }
/* 555 */     else if (paramString.equals(CommandPanel.probLabel)) {
/* 556 */       this.mode = 3;this.pmode = 2;
/*     */     }
/* 558 */     else if (paramString.equals(CommandPanel.beliefLabel)) {
/* 559 */       this.mode = 0;this.pmode = 1;
/*     */     }
/*     */     else {
/* 562 */       this.mode = 0;this.pmode = 0;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /home/anali/INAOE/tesis/codigos/topub/exe/ww4.jar!/Thinker/NetworkPanel.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */