/*     */ package DMasMsbn;
/*     */ 
/*     */ import Network.AgGraphI;
/*     */ import Network.BayesNet;
/*     */ import Network.HelpPanel;
/*     */ import Network.UTIL;
/*     */ import java.awt.Dimension;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintWriter;
/*     */ 
/*     */ public class NetworkPanel extends java.awt.Canvas implements Network.Bridge
/*     */ {
/*     */   java.awt.Frame frame;
/*  14 */   AgGraphI agtgraf = null;
/*  15 */   Network.AgtI agent = null;
/*     */   
/*     */   BayesNet bn;
/*     */   Network.JoinForest jt;
/*  19 */   String path = null;
/*     */   
/*     */ 
/*     */ 
/*  23 */   static boolean subnetLoaded = false;
/*  24 */   static boolean communicate = false;
/*  25 */   static boolean localDone = false;
/*  26 */   static boolean requestEvi = false;
/*  27 */   static boolean enterEvidence = false;
/*  28 */   static boolean paintBelief = false;
/*  29 */   static boolean seePotential = false;
/*  30 */   static int msgFlag = -1;
/*     */   
/*     */ 
/*  33 */   String server = null;
/*  34 */   String portNum = null;
/*     */   
/*     */ 
/*  37 */   float arrowLength = 20.0F;
/*  38 */   final float maxLength = 20.0F;
/*     */   
/*     */ 
/*  41 */   int logIndex = 0;
/*     */   
/*     */ 
/*  44 */   java.awt.Point downPoint = new java.awt.Point(0, 0);
/*  45 */   int downAgent = -2;
/*  46 */   int downNode = -2;
/*     */   
/*     */   private java.awt.Image offScreenImage;
/*     */   
/*     */   private java.awt.Graphics offScreenGraphics;
/*     */   private Dimension offScreenSize;
/*     */   
/*     */   public NetworkPanel(java.awt.Frame paramFrame)
/*     */   {
/*  55 */     this.frame = paramFrame;
/*  56 */     setBackground(Network.NetPaint.backgroundColor);
/*     */     
/*  58 */     addMouseListener(new java.awt.event.MouseAdapter()
/*     */     {
/*     */       public void mousePressed(java.awt.event.MouseEvent paramAnonymousMouseEvent)
/*     */       {
/*  62 */         NetworkPanel.this.downPoint.move(paramAnonymousMouseEvent.getX(), paramAnonymousMouseEvent.getY());
/*  63 */         if (NetworkPanel.enterEvidence) { NetworkPanel.this.mouseDownInEvi(NetworkPanel.this.downPoint);
/*  64 */         } else if (NetworkPanel.requestEvi) { NetworkPanel.this.mouseDownReqEvi(NetworkPanel.this.downPoint);
/*  65 */         } else if (NetworkPanel.communicate) { NetworkPanel.this.mouseDownInCommu();
/*  66 */         } else if (NetworkPanel.seePotential) NetworkPanel.this.mouseDownInProb(NetworkPanel.this.downPoint);
/*  67 */         NetworkPanel.this.repaint();
/*     */       }
/*     */     });
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   void load()
/*     */   {
/*  77 */     Network.SpaceTime.showUsedMemory("Before load");
/*  78 */     localDone = false;
/*     */     
/*  80 */     java.awt.FileDialog localFileDialog = new java.awt.FileDialog(this.frame, "", 0);
/*  81 */     localFileDialog.pack();localFileDialog.setVisible(true);
/*     */     
/*  83 */     String str1 = localFileDialog.getDirectory();
/*  84 */     String str2 = localFileDialog.getFile();
/*  85 */     if (str2 == null) return;
/*  86 */     this.path = (str1 + UTIL.removePostfix(str2));
/*  87 */     if (this.agent != null) {
/*  88 */       HelpPanel.addHelp("Current agent terminated.");
/*  89 */       this.agent.stopBearerMgr();
/*  90 */       this.agtgraf = null;this.agent = null;
/*  91 */       subnetLoaded = false;
/*     */     }
/*     */     
/*  94 */     this.agtgraf = new AgGraphI();
/*  95 */     this.agtgraf.setPath(this.path);
/*  96 */     HelpPanel.addHelp("path=" + this.agtgraf.getPath());
/*  97 */     this.agtgraf.loadDag();
/*  98 */     this.agtgraf.loadDsepset();
/*  99 */     this.agtgraf.loadJt();
/* 100 */     this.agtgraf.loadLinkageTree();
/*     */     
/* 102 */     this.agent = new Network.AgtI(this.agtgraf, this.path, this);
/* 103 */     this.agent.loadHostPort();
/*     */     
/* 105 */     this.bn = this.agtgraf.getNet();
/* 106 */     Network.NetPaint.moveNet(this, this.bn);
/* 107 */     repaint();
/*     */     
/* 109 */     HelpPanel.addHelp("Loading completed.");
/* 110 */     subnetLoaded = true;
/* 111 */     localDone = true;
/* 112 */     Network.SpaceTime.showUsedMemory("After load");
/*     */   }
/*     */   
/*     */   void getSet()
/*     */   {
/* 117 */     this.agtgraf.getSet();
/* 118 */     this.agtgraf.setLinkageBelief();
/* 119 */     this.jt = this.agtgraf.getJoinTree();
/* 120 */     this.agent.setMsgBox();
/* 121 */     this.agent.setBearerMgr();
/* 122 */     Network.NetPaint.moveNet(this, this.jt);
/* 123 */     HelpPanel.addHelp("Ready to answer queries.");
/*     */   }
/*     */   
/*     */   boolean hasSubnet()
/*     */   {
/* 128 */     return subnetLoaded;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private void mouseDownInCommu()
/*     */   {
/* 135 */     HelpPanel.showError("Communication on going.");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private void mouseDownReqEvi(java.awt.Point paramPoint)
/*     */   {
/* 142 */     this.downNode = this.bn.isClose(paramPoint, 6);
/* 143 */     if (this.downNode >= 0) {
/* 144 */       boolean bool = this.bn.isObserved(this.downNode);
/* 145 */       if (bool) { HelpPanel.showError("This variable has been observed.");
/*     */       }
/* 147 */       else if (this.server == null) {
/* 148 */         HelpPanel.addHelp("Please enter server address/port.");
/* 149 */         Network.VectorDialog localVectorDialog = new Network.VectorDialog(this.frame, this, "Simulator Infor", getPrompt(), getDefault(), 16, 100, 100);
/*     */         
/*     */ 
/*     */ 
/*     */ 
/* 154 */         localVectorDialog.setVisible(true);
/*     */       } else {
/* 156 */         requestAndEnterEvi(this.downNode);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   String[] getPrompt() {
/* 162 */     String[] arrayOfString = { "Server Name", "Port Number" };
/* 163 */     return arrayOfString;
/*     */   }
/*     */   
/*     */   String[] getDefault() {
/* 167 */     String[] arrayOfString = new String[2];
/* 168 */     return arrayOfString;
/*     */   }
/*     */   
/*     */   void requestAndEnterEvi(int paramInt)
/*     */   {
/* 173 */     String str1 = this.bn.getLabel(paramInt);
/* 174 */     String str2 = reqVarValue(str1);
/* 175 */     if (str2 == null) { return;
/*     */     }
/* 177 */     int i = getStateCount(paramInt);
/* 178 */     int[] arrayOfInt = new int[i];
/* 179 */     for (int j = 0; j < i; j++) { arrayOfInt[j] = 0;
/*     */     }
/* 181 */     j = this.bn.getStateIndex(paramInt, str2);
/* 182 */     arrayOfInt[j] = 1;
/* 183 */     enterEvi(paramInt, arrayOfInt);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   String reqVarValue(String paramString)
/*     */   {
/* 192 */     java.net.Socket localSocket = null;
/* 193 */     int i = Integer.parseInt(this.portNum);
/*     */     try {
/* 195 */       localSocket = new java.net.Socket(this.server, i);
/*     */     } catch (java.net.UnknownHostException localUnknownHostException) {
/* 197 */       HelpPanel.showError(localUnknownHostException.getMessage());
/* 198 */       this.server = null;this.portNum = null;return null;
/*     */     } catch (IOException localIOException1) {
/* 200 */       HelpPanel.showError(localIOException1.getMessage());
/* 201 */       this.server = null;this.portNum = null;return null;
/*     */     }
/*     */     try
/*     */     {
/* 205 */       java.io.BufferedReader localBufferedReader = new java.io.BufferedReader(new java.io.InputStreamReader(localSocket.getInputStream()));
/*     */       
/* 207 */       PrintWriter localPrintWriter = new PrintWriter(new java.io.BufferedWriter(new java.io.OutputStreamWriter(localSocket.getOutputStream())), true);
/*     */       
/*     */ 
/* 210 */       localPrintWriter.println(paramString);
/*     */       
/* 212 */       String str1 = localBufferedReader.readLine();
/* 213 */       java.util.StringTokenizer localStringTokenizer = new java.util.StringTokenizer(str1);
/* 214 */       if (!localStringTokenizer.nextToken().equals(paramString)) {
/* 215 */         HelpPanel.showError("Incorrect var name in reply.");
/* 216 */         return null;
/*     */       }
/* 218 */       String str2 = localStringTokenizer.nextToken();
/* 219 */       if (str2.equals("0")) {
/* 220 */         HelpPanel.showError("Invalid request or unobservable variable.");
/* 221 */         return null;
/*     */       }
/* 223 */       if (str2.equals("-1")) {
/* 224 */         HelpPanel.showError("Simulator not ready.");return null;
/*     */       }
/* 226 */       return localStringTokenizer.nextToken();
/*     */     } catch (IOException localIOException2) {
/* 228 */       HelpPanel.showError(localIOException2.getMessage()); }
/* 229 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private void mouseDownInEvi(java.awt.Point paramPoint)
/*     */   {
/* 237 */     this.downNode = this.bn.isClose(paramPoint, 6);
/* 238 */     if (this.downNode >= 0)
/*     */     {
/* 240 */       boolean bool = this.bn.isObserved(this.downNode);
/* 241 */       if (!bool) {
/* 242 */         HelpPanel.addHelp("Enter 0 for impossible value and 1 otherwise.");
/* 243 */         Network.VectorDialog localVectorDialog = new Network.VectorDialog(this.frame, this, getLabel(this.downNode), getState(this.downNode), getDefaultEvi(this.downNode), 16, 100, 100);
/*     */         
/*     */ 
/*     */ 
/* 247 */         localVectorDialog.setVisible(true);
/*     */       } else {
/* 249 */         HelpPanel.showError("Evidence on this node has been entered!");
/*     */       } }
/* 251 */     enterEvidence = true;
/* 252 */     paintBelief = false;
/* 253 */     repaint();
/*     */   }
/*     */   
/*     */   String getLabel(int paramInt) {
/* 257 */     return this.bn.getLabel(paramInt);
/*     */   }
/*     */   
/* 260 */   String[] getState(int paramInt) { return this.bn.getState(paramInt); }
/*     */   
/*     */ 
/* 263 */   int getStateCount(int paramInt) { return this.bn.getStateCount(paramInt); }
/*     */   
/*     */   int[] getDefaultEvi(int paramInt) {
/* 266 */     int i = getStateCount(paramInt);
/* 267 */     int[] arrayOfInt = new int[i];
/* 268 */     for (int j = 0; j < i; j++) arrayOfInt[j] = 1;
/* 269 */     return arrayOfInt;
/*     */   }
/*     */   
/*     */   void enterEvi(int paramInt, int[] paramArrayOfInt)
/*     */   {
/* 274 */     int i = 0;
/* 275 */     for (int j = 0; j < paramArrayOfInt.length; j++) i += paramArrayOfInt[j];
/* 276 */     if ((i > 0) && (i < paramArrayOfInt.length)) {
/* 277 */       this.bn.setObserved(paramInt);
/* 278 */       this.jt.enterEvidenceToCq(paramInt, paramArrayOfInt);
/* 279 */       this.jt.enterEvidence1By1(paramInt);
/*     */     } else {
/* 281 */       HelpPanel.showError("Invalid evidence pattern entered!");
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   private void mouseDownInProb(java.awt.Point paramPoint)
/*     */   {
/* 288 */     this.downNode = this.jt.isClose(paramPoint, 6);
/* 289 */     if (this.downNode >= 0) {
/* 290 */       this.frame.setCursor(new java.awt.Cursor(3));
/* 291 */       Network.ProbDialog localProbDialog = new Network.ProbDialog(this.frame, this, new String("Cluster " + this.jt.getLabel(this.downNode)), UTIL.getDuplicate(getCqMemberLabel(this.downNode)), UTIL.getDuplicate(getCqMemberState(this.downNode)), UTIL.getDuplicate(this.jt.getBelief(this.downNode)), this.downNode, 12, 10, 10, 100);
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 298 */       localProbDialog.setVisible(true);
/* 299 */       this.frame.setCursor(new java.awt.Cursor(0));
/* 300 */       repaint();
/*     */     }
/*     */   }
/*     */   
/*     */   String[] getCqMemberLabel(int paramInt)
/*     */   {
/* 306 */     int[] arrayOfInt = this.jt.getCqMember(paramInt);
/* 307 */     int i = arrayOfInt.length;
/* 308 */     String[] arrayOfString1 = new String[i];
/* 309 */     String[] arrayOfString2 = this.bn.getLabel();
/* 310 */     for (int j = 0; j < i; j++)
/* 311 */       arrayOfString1[j] = new String(arrayOfString2[arrayOfInt[j]] + ", " + arrayOfInt[j]);
/* 312 */     return arrayOfString1;
/*     */   }
/*     */   
/*     */   String[][] getCqMemberState(int paramInt) {
/* 316 */     int[] arrayOfInt = this.jt.getCqMember(paramInt);
/* 317 */     int i = arrayOfInt.length;
/* 318 */     String[][] arrayOfString1 = new String[i][];
/* 319 */     String[][] arrayOfString2 = this.bn.getState();
/* 320 */     for (int j = 0; j < i; j++)
/* 321 */       arrayOfString1[j] = UTIL.getDuplicate(arrayOfString2[arrayOfInt[j]]);
/* 322 */     return arrayOfString1;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setVector(int[] paramArrayOfInt)
/*     */   {
/* 329 */     enterEvi(this.downNode, paramArrayOfInt);
/*     */   }
/*     */   
/*     */   public void setVector2(int[] paramArrayOfInt) {
/* 333 */     if (paramArrayOfInt[0] == 3) { msgFlag = 5;
/* 334 */     } else if (paramArrayOfInt[0] == 5) msgFlag = -1; }
/*     */   
/*     */   public void setVector(float[] paramArrayOfFloat) {}
/*     */   
/*     */   public void setVector(boolean[] paramArrayOfBoolean) {}
/*     */   
/*     */   public void setTable(float[] paramArrayOfFloat) {}
/*     */   
/* 342 */   public float[] getVector(int paramInt) { return UTIL.getDuplicate(this.jt.getBelief(paramInt)); }
/*     */   
/* 344 */   public String[] getVector2(int paramInt) { return null; }
/* 345 */   public String[] getVector3(int paramInt) { return null; }
/*     */   
/*     */   public void setVector(String[] paramArrayOfString)
/*     */   {
/* 349 */     this.server = new String(paramArrayOfString[0]);
/* 350 */     this.portNum = new String(paramArrayOfString[1]);
/* 351 */     requestAndEnterEvi(this.downNode);
/*     */   }
/*     */   
/*     */   public void setVector2(String[] paramArrayOfString) {
/* 355 */     if (paramArrayOfString[0].equals("communication")) {
/* 356 */       if (paramArrayOfString[1].equals("communicate")) {
/* 357 */         while (!localDone) HelpPanel.addHelp("Wait for local activity.");
/* 358 */         communicate = true;
/* 359 */         paintBelief = false;repaint();
/*     */       } else {
/* 361 */         communicate = false;
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   void logBelief()
/*     */   {
/* 368 */     localDone = false;
/* 369 */     String str = new String("logpb." + this.logIndex++);
/*     */     try {
/* 371 */       PrintWriter localPrintWriter = new PrintWriter(new java.io.FileWriter(str));
/* 372 */       HelpPanel.addHelp("Save log file " + str);
/* 373 */       this.jt.saveBelief(localPrintWriter, this.bn);
/* 374 */       HelpPanel.addHelp("Saveing done!!");
/* 375 */       localPrintWriter.close();
/*     */     } catch (IOException localIOException) {
/* 377 */       HelpPanel.showError("Unable to save " + str);
/*     */     }
/* 379 */     HelpPanel.addHelp("Saving completed.");
/* 380 */     paintBelief = false;
/* 381 */     repaint();
/* 382 */     localDone = true;
/*     */   }
/*     */   
/*     */ 
/*     */   boolean isCommunicating()
/*     */   {
/* 388 */     return communicate;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   void communicate()
/*     */   {
/* 395 */     while (!localDone) HelpPanel.addHelp("Wait for local activity.");
/* 396 */     paintBelief = false;repaint();
/*     */     
/* 398 */     long l = Network.SpaceTime.showUsedTime("", 0L);
/* 399 */     communicate = true;
/* 400 */     msgFlag = 3;
/* 401 */     this.agent.setRoot(3);
/*     */     
/* 403 */     while (msgFlag != 5)
/* 404 */       HelpPanel.appendHelp("");
/* 405 */     if (msgFlag == 5) this.agent.setRoot(5);
/* 406 */     while (msgFlag != -1)
/* 407 */       HelpPanel.appendHelp("");
/* 408 */     Network.SpaceTime.showUsedTime("Comm Time", l);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void showNet()
/*     */   {
/* 415 */     update(getGraphics());
/*     */   }
/*     */   
/*     */   void showBelief()
/*     */   {
/* 420 */     localDone = false;
/* 421 */     paintBelief = true;
/* 422 */     repaint();
/* 423 */     localDone = true;
/*     */   }
/*     */   
/*     */   public void paint(java.awt.Graphics paramGraphics)
/*     */   {
/* 428 */     if (!seePotential) {
/* 429 */       Network.NetPaint.paintNet(paramGraphics, this.bn, this.arrowLength, false);
/* 430 */       if (paintBelief) Network.NetPaint.showBelief(this, paramGraphics, this.bn, this.jt);
/* 431 */       paintBelief = false;
/*     */ 
/*     */     }
/*     */     else
/*     */     {
/* 436 */       Network.NetPaint.paintNet(paramGraphics, this.jt, Network.UndirectGraph.makeSkeleton(this.bn));
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public final synchronized void update(java.awt.Graphics paramGraphics)
/*     */   {
/* 443 */     Dimension localDimension = getSize();
/* 444 */     if ((this.offScreenImage == null) || (localDimension.width != this.offScreenSize.width) || (localDimension.height != this.offScreenSize.height))
/*     */     {
/* 446 */       this.offScreenImage = createImage(localDimension.width, localDimension.height);
/* 447 */       this.offScreenSize = localDimension;
/* 448 */       this.offScreenGraphics = this.offScreenImage.getGraphics();
/*     */     }
/* 450 */     this.offScreenGraphics.setColor(Network.NetPaint.backgroundColor);
/* 451 */     this.offScreenGraphics.fillRect(0, 0, localDimension.width, localDimension.height);
/* 452 */     paint(this.offScreenGraphics);
/* 453 */     paramGraphics.drawImage(this.offScreenImage, 0, 0, null);
/*     */   }
/*     */   
/*     */   void moveNet()
/*     */   {
/* 458 */     if ((!seePotential) && (this.bn != null)) {
/* 459 */       Network.NetPaint.moveNet(this, this.bn);
/*     */     }
/* 461 */     else if (this.jt != null)
/*     */     {
/* 463 */       Network.NetPaint.moveNet(this, this.jt);
/*     */     }
/* 465 */     repaint();
/*     */   }
/*     */   
/*     */   void setArrow()
/*     */   {
/* 470 */     this.arrowLength -= 2.0F;
/* 471 */     if (this.arrowLength < 10.0F) this.arrowLength = 20.0F;
/* 472 */     repaint();
/*     */   }
/*     */   
/*     */   void setMode(String paramString)
/*     */   {
/* 477 */     if (paramString.equals(CommandPanel.eviLabel)) enterEvidence = true; else {
/* 478 */       enterEvidence = false;
/*     */     }
/* 480 */     if (paramString.equals(CommandPanel.probLabel)) { seePotential = true;
/* 481 */     } else if (!paramString.equals(CommandPanel.mvNetLabel)) {
/* 482 */       seePotential = false;
/*     */     }
/*     */     
/* 485 */     if (paramString.equals(CommandPanel.reqEviLabel)) requestEvi = true; else {
/* 486 */       requestEvi = false;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   int[][] loadVar()
/*     */   {
/* 497 */     int[][] arrayOfInt = new int[2][];
/* 498 */     String str = this.path + ".var";
/*     */     try {
/* 500 */       java.io.BufferedReader localBufferedReader = new java.io.BufferedReader(new java.io.FileReader(str));
/*     */       
/* 502 */       HelpPanel.addHelp("Loading a variable set from " + str);
/*     */       
/* 504 */       arrayOfInt[0] = UTIL.loadIntList(localBufferedReader);arrayOfInt[1] = UTIL.loadIntList(localBufferedReader);
/*     */       
/* 506 */       localBufferedReader.close();
/*     */     }
/*     */     catch (IOException localIOException)
/*     */     {
/* 510 */       HelpPanel.showError("Unable to load file!");
/*     */     }
/*     */     
/* 513 */     HelpPanel.showList("1st var set loaded = ", arrayOfInt[0]);
/* 514 */     HelpPanel.showList("2nd var set loaded = ", arrayOfInt[1]);
/*     */     
/* 516 */     return arrayOfInt;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   void savePot(int[][] paramArrayOfInt)
/*     */   {
/* 523 */     float[] arrayOfFloat = this.agtgraf.getPot(paramArrayOfInt[0]);
/* 524 */     if (arrayOfFloat == null) return;
/* 525 */     String str = this.path + ".ptn";
/*     */     try {
/* 527 */       PrintWriter localPrintWriter = new PrintWriter(new java.io.FileWriter(str));
/* 528 */       HelpPanel.addHelp("Saving " + str);
/* 529 */       UTIL.saveIntList(localPrintWriter, paramArrayOfInt[1], "#var/var_idx");
/* 530 */       localPrintWriter.println(arrayOfFloat.length + "  #_of_belief");
/* 531 */       UTIL.saveRealList(localPrintWriter, arrayOfFloat);
/* 532 */       localPrintWriter.close();
/*     */     } catch (IOException localIOException) {
/* 534 */       HelpPanel.showError("Unable to save " + str);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   void loadPot()
/*     */   {
/* 541 */     int[] arrayOfInt = null;
/* 542 */     float[] arrayOfFloat = null;
/* 543 */     String str = this.path + ".ptn";
/*     */     try {
/* 545 */       java.io.BufferedReader localBufferedReader = new java.io.BufferedReader(new java.io.FileReader(str));
/*     */       
/* 547 */       HelpPanel.addHelp("Loading potential from " + str);
/*     */       
/* 549 */       arrayOfInt = UTIL.loadIntList(localBufferedReader);
/* 550 */       int i = UTIL.loadInt(localBufferedReader);
/* 551 */       arrayOfFloat = UTIL.loadRealList(localBufferedReader, i);
/*     */       
/* 553 */       localBufferedReader.close();
/*     */     }
/*     */     catch (IOException localIOException)
/*     */     {
/* 557 */       HelpPanel.showError("Unable to load file!");
/*     */     }
/*     */     
/* 560 */     HelpPanel.showList("Var set loaded = ", arrayOfInt);
/* 561 */     HelpPanel.showList("Potential loaded = ", arrayOfFloat);
/*     */     
/* 563 */     this.agtgraf.multiplyPot(arrayOfInt, arrayOfFloat);
/*     */   }
/*     */ }


/* Location:              /home/anali/INAOE/tesis/codigos/topub/exe/ww4.jar!/DMasMsbn/NetworkPanel.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */