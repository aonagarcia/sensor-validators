/*     */ package DMsbToLjf;
/*     */ 
/*     */ import Network.AgGraphT;
/*     */ import Network.AgtT;
/*     */ import Network.HelpPanel;
/*     */ import Network.NetPaint;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Graphics;
/*     */ 
/*     */ public class NetworkPanel extends java.awt.Canvas implements Network.Bridge
/*     */ {
/*     */   java.awt.Frame frame;
/*  13 */   AgGraphT agtgraf = null;
/*  14 */   AgtT agent = null;
/*     */   
/*     */   Network.BayesNet bn;
/*     */   
/*     */   Network.ChordalGraph ug;
/*     */   Network.HyperGraph jt;
/*     */   Network.HyperGraph hg;
/*     */   static final int NO_SUBNET = 0;
/*     */   static final int LOADED = 1;
/*     */   static final int MORAL = 2;
/*     */   static final int CHORDAL = 3;
/*     */   static final int LINKED = 4;
/*     */   static final int ASSIGN = 5;
/*     */   static final int SAVED = 6;
/*  28 */   int stage = 0;
/*     */   
/*  30 */   boolean newflin = false;
/*     */   
/*  32 */   java.awt.Point[] usrflin = null;
/*     */   
/*     */   static final int DG_MODE = 0;
/*     */   
/*     */   static final int UG_MODE = 1;
/*     */   static final int JT_MODE = 2;
/*     */   static final int LT_MODE = 3;
/*  39 */   int pmode = 0;
/*     */   
/*     */ 
/*  42 */   boolean pause = false;
/*     */   
/*     */ 
/*  45 */   boolean showIndex = false;
/*     */   
/*     */ 
/*     */ 
/*  49 */   static boolean subnetLoaded = false;
/*  50 */   static boolean communicate = false;
/*  51 */   static boolean localDone = false;
/*  52 */   static int msgFlag = -1;
/*     */   
/*     */ 
/*  55 */   float arrowLength = 20.0F;
/*  56 */   final float maxLength = 20.0F;
/*     */   
/*     */   private java.awt.Image offScreenImage;
/*     */   
/*     */   private Graphics offScreenGraphics;
/*     */   private Dimension offScreenSize;
/*     */   
/*     */   public NetworkPanel(java.awt.Frame paramFrame)
/*     */   {
/*  65 */     this.frame = paramFrame;
/*  66 */     setBackground(NetPaint.backgroundColor);
/*     */     
/*  68 */     addMouseListener(new java.awt.event.MouseAdapter()
/*     */     {
/*     */       public void mousePressed(java.awt.event.MouseEvent paramAnonymousMouseEvent)
/*     */       {
/*  72 */         if (NetworkPanel.communicate) NetworkPanel.this.mouseDownInCommu();
/*  73 */         NetworkPanel.this.repaint();
/*     */       }
/*     */     });
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   void load()
/*     */   {
/*  83 */     localDone = false;
/*     */     
/*  85 */     java.awt.FileDialog localFileDialog = new java.awt.FileDialog(this.frame, "", 0);
/*  86 */     localFileDialog.pack();localFileDialog.setVisible(true);
/*     */     
/*  88 */     String str1 = localFileDialog.getDirectory();
/*  89 */     String str2 = localFileDialog.getFile();
/*  90 */     if (str2 == null) return;
/*  91 */     String str3 = str1 + Network.UTIL.removePostfix(str2);
/*  92 */     if (this.agent != null) {
/*  93 */       HelpPanel.addHelp("Current subnet cleared.");
/*  94 */       this.agent.stopBearerMgr();
/*  95 */       this.agtgraf = null;this.agent = null;
/*  96 */       subnetLoaded = false;
/*     */     }
/*     */     
/*  99 */     this.agtgraf = new AgGraphT();
/* 100 */     this.agtgraf.setPath(str3);
/* 101 */     HelpPanel.addHelp("path=" + this.agtgraf.getPath());
/* 102 */     this.agtgraf.loadDag();
/* 103 */     this.agtgraf.loadDsepset();
/* 104 */     this.agent = new AgtT(this.agtgraf, str3, this);
/* 105 */     this.agent.loadHostPort();
/*     */     
/* 107 */     this.bn = this.agtgraf.getNet();
/* 108 */     this.pmode = 0;
/* 109 */     NetPaint.moveNet(this, this.bn);
/* 110 */     repaint();
/* 111 */     HelpPanel.addHelp("Loading completed.");
/* 112 */     subnetLoaded = true;
/* 113 */     localDone = true;
/* 114 */     this.stage = 1;
/*     */   }
/*     */   
/*     */   void save()
/*     */   {
/* 119 */     if (this.stage < 5) {
/* 120 */       HelpPanel.showError("Please assign d-sepnodes.");return;
/*     */     }
/* 122 */     if (this.stage == 6) {
/* 123 */       HelpPanel.addHelp("Files saved.  Load new subnet or exit.");return;
/*     */     }
/* 125 */     this.agtgraf.saveJoinTree();
/* 126 */     HelpPanel.addHelp("Local junction tree saved.");
/* 127 */     this.agtgraf.saveLinkageTree();
/* 128 */     HelpPanel.addHelp("Linkage trees saved.");
/* 129 */     this.agtgraf.saveNodeAssigned();
/* 130 */     HelpPanel.addHelp("Assigned nodes saved.");
/*     */     
/* 132 */     this.stage = 6;
/* 133 */     repaint();
/*     */   }
/*     */   
/*     */   void getSet()
/*     */   {
/* 138 */     this.agent.setMsgBox();
/* 139 */     this.agent.setBearerMgr();
/* 140 */     HelpPanel.addHelp("Ready for structure compilation.");
/*     */   }
/*     */   
/*     */   void stopBearerMgr()
/*     */   {
/* 145 */     this.agent.stopBearerMgr();
/*     */   }
/*     */   
/*     */   boolean hasSubnet()
/*     */   {
/* 150 */     return subnetLoaded;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 157 */   private void mouseDownInCommu() { HelpPanel.addHelp("Communication is on going."); }
/*     */   
/*     */   public void setVector(String[] paramArrayOfString) {}
/*     */   
/*     */   public void setVector(boolean[] paramArrayOfBoolean) {}
/*     */   
/*     */   public void setVector(float[] paramArrayOfFloat) {}
/*     */   
/*     */   public void setVector(int[] paramArrayOfInt) {}
/* 166 */   public void setVector2(String[] paramArrayOfString) { if (paramArrayOfString[0].equals("communication")) {
/* 167 */       if (paramArrayOfString[1].equals("communicate")) {
/* 168 */         while (!localDone) HelpPanel.addHelp("Wait for local activity.");
/* 169 */         communicate = true;
/*     */       } else {
/* 171 */         communicate = false;
/*     */       }
/* 173 */     } else if (paramArrayOfString[0].equals("operation_stage")) {
/* 174 */       if (paramArrayOfString[1].equals("moralization")) { this.stage = 2;
/* 175 */       } else if (paramArrayOfString[1].equals("triangulation")) { this.stage = 3;
/* 176 */       } else if (paramArrayOfString[1].equals("link_join_trees")) { this.stage = 4;
/* 177 */       } else if (paramArrayOfString[1].equals("assign_dsepnode")) this.stage = 5;
/*     */     }
/* 179 */     else if (paramArrayOfString[0].equals("paint_mode")) {
/* 180 */       if (paramArrayOfString[1].equals("direct_graph")) { this.pmode = 0;
/* 181 */       } else if (paramArrayOfString[1].equals("undirect_graph")) { this.pmode = 1;
/* 182 */       } else if (paramArrayOfString[1].equals("join_tree")) { this.pmode = 2;
/* 183 */       } else if (paramArrayOfString[1].equals("linkage_tree")) this.pmode = 3;
/*     */     }
/* 185 */     else if (paramArrayOfString[0].equals("new_fillin")) {
/* 186 */       if (paramArrayOfString[1].equals("has_new_fillin")) this.newflin = true; else {
/* 187 */         this.newflin = false;
/*     */       }
/* 189 */     } else if ((paramArrayOfString[1].equals("Add Fill-In")) && (paramArrayOfString[2].equals("End1_Index End2_Index")))
/*     */     {
/* 191 */       handleAddLink(paramArrayOfString[0]);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setVector2(int[] paramArrayOfInt)
/*     */   {
/* 200 */     if (paramArrayOfInt[0] == 1) { msgFlag = 2;
/* 201 */     } else if (paramArrayOfInt[0] == 2) { msgFlag = -1;
/*     */ 
/*     */     }
/* 204 */     else if (paramArrayOfInt[0] == 3) { msgFlag = 4;
/* 205 */     } else if (paramArrayOfInt[0] == 4) { msgFlag = 5;
/* 206 */     } else if (paramArrayOfInt[0] == 5) { msgFlag = -1;
/* 207 */     } else if (paramArrayOfInt[0] == 6) { msgFlag = -1;
/*     */ 
/*     */     }
/* 210 */     else if (paramArrayOfInt[0] == 7) { msgFlag = 8;
/* 211 */     } else if (paramArrayOfInt[0] == 8) { msgFlag = 9;
/* 212 */     } else if (paramArrayOfInt[0] == 9) { msgFlag = -1;
/*     */ 
/*     */     }
/* 215 */     else if (paramArrayOfInt[0] == 10) msgFlag = -1; }
/*     */   
/*     */   public void setTable(float[] paramArrayOfFloat) {}
/*     */   
/* 219 */   public float[] getVector(int paramInt) { return null; }
/*     */   
/*     */   public String[] getVector2(int paramInt)
/*     */   {
/* 223 */     if ((paramInt == 0) && (this.usrflin != null)) {
/* 224 */       int i = this.usrflin.length;
/* 225 */       String[] arrayOfString = new String[i];
/* 226 */       for (int j = 0; j < i; j++) arrayOfString[j] = new String(this.usrflin[j].x + " " + this.usrflin[j].y);
/* 227 */       return arrayOfString;
/*     */     }
/* 229 */     return null;
/*     */   }
/*     */   
/* 232 */   public String[] getVector3(int paramInt) { return null; }
/*     */   
/*     */ 
/*     */   boolean isCommunicating()
/*     */   {
/* 237 */     return communicate;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   void setMoralGraph()
/*     */   {
/* 244 */     if (this.stage < 1) {
/* 245 */       HelpPanel.showError("Please load subnet first.");return;
/*     */     }
/* 247 */     if (this.stage >= 2) {
/* 248 */       HelpPanel.showError("Moralization has completed.");return;
/*     */     }
/*     */     
/* 251 */     while (!localDone) HelpPanel.addHelp("Wait for local activity.");
/* 252 */     communicate = true;
/* 253 */     msgFlag = 1;
/* 254 */     this.agent.setRoot(1);
/* 255 */     while (msgFlag != 2)
/* 256 */       HelpPanel.appendHelp("");
/* 257 */     this.agent.setRoot(2);
/*     */   }
/*     */   
/*     */ 
/*     */   void setChordalGraph()
/*     */   {
/* 263 */     if (this.stage < 2) {
/* 264 */       HelpPanel.showError("Please load and moralize MSBN first.");return;
/*     */     }
/* 266 */     if (this.stage >= 3) {
/* 267 */       HelpPanel.showError("Triangulation has completed.");return;
/*     */     }
/*     */     
/* 270 */     while (!localDone) HelpPanel.addHelp("Wait for local activity.");
/* 271 */     communicate = true;
/*     */     do {
/* 273 */       msgFlag = 3;
/* 274 */       this.agent.setRoot(3);
/* 275 */       while (msgFlag != 4)
/* 276 */         HelpPanel.appendHelp("");
/* 277 */       this.agent.setRoot(4);
/* 278 */       while (msgFlag != 5)
/* 279 */         HelpPanel.appendHelp("");
/* 280 */       this.agent.setRoot(5);
/* 281 */       while (msgFlag != -1)
/* 282 */         HelpPanel.appendHelp("");
/* 283 */     } while (this.newflin);
/*     */     
/*     */ 
/* 286 */     this.agent.setRoot(6);
/*     */   }
/*     */   
/*     */   void showLnkgTree() {
/* 290 */     if (this.stage < 4) {
/* 291 */       HelpPanel.showError("Please link subnets first.");return;
/*     */     }
/*     */     
/* 294 */     this.pmode = 3;
/* 295 */     int i = this.agent.getNeighborCount();
/* 296 */     for (int j = 0; j < i; j++) {
/* 297 */       this.hg = this.agtgraf.getLinkageTree(j);
/* 298 */       HelpPanel.addHelp("Host/lnkg tree of " + this.agent.getName() + " vs " + this.agent.getNbrName(j) + ".");
/*     */       
/* 300 */       moveNet();
/* 301 */       update(getGraphics());
/* 302 */       if (this.pause) {
/* 303 */         NextDialog localNextDialog = new NextDialog(this.frame, this, "Press Ok.", "Ok", 10, 10);
/*     */         
/* 305 */         localNextDialog.setVisible(true);
/*     */       }
/*     */     }
/* 308 */     this.pmode = 2;
/* 309 */     repaint();
/*     */   }
/*     */   
/*     */   void setJtLinkage()
/*     */   {
/* 314 */     if (this.stage < 3) {
/* 315 */       HelpPanel.showError("Please triangulate first.");return;
/*     */     }
/* 317 */     if (this.stage >= 4) {
/* 318 */       HelpPanel.showError("Join tree/linkage trees have been created.");
/* 319 */       return;
/*     */     }
/*     */     
/* 322 */     while (!localDone) HelpPanel.addHelp("Wait for local activity.");
/* 323 */     communicate = true;
/* 324 */     msgFlag = 7;
/* 325 */     this.agent.setRoot(7);
/* 326 */     while (msgFlag != 8)
/* 327 */       HelpPanel.appendHelp("");
/* 328 */     this.agent.setRoot(8);
/* 329 */     while (msgFlag != 9)
/* 330 */       HelpPanel.appendHelp("");
/* 331 */     this.agent.setRoot(9);
/*     */   }
/*     */   
/*     */   void assignDsepnode()
/*     */   {
/* 336 */     if (this.stage < 4) {
/* 337 */       HelpPanel.showError("Please link join trees first.");return;
/*     */     }
/* 339 */     if (this.stage >= 5) {
/* 340 */       HelpPanel.showError("Dsepnodes have been assigned.");return;
/*     */     }
/* 342 */     while (!localDone) HelpPanel.addHelp("Wait for local activity.");
/* 343 */     communicate = true;
/* 344 */     msgFlag = 10;
/* 345 */     this.agent.setRoot(10);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   void addLk()
/*     */   {
/* 353 */     if (this.stage != 1) {
/* 354 */       HelpPanel.showError("Fill-in can only be added before moralization.");
/* 355 */       return;
/*     */     }
/* 357 */     Network.LabelDialog localLabelDialog = new Network.LabelDialog(this.frame, this, "Add Fill-In", "End1_Index End2_Index", "");
/*     */     
/* 359 */     localLabelDialog.setVisible(true);
/*     */   }
/*     */   
/*     */ 
/*     */   void handleAddLink(String paramString)
/*     */   {
/* 365 */     java.util.StringTokenizer localStringTokenizer = new java.util.StringTokenizer(paramString);
/* 366 */     if (localStringTokenizer.countTokens() == 0) return;
/* 367 */     int i = Integer.parseInt(localStringTokenizer.nextToken());
/* 368 */     int j = Integer.parseInt(localStringTokenizer.nextToken());
/* 369 */     int k = this.bn.getNodeCount();
/* 370 */     if ((i >= 0) && (i < k) && (j >= 0) && (j < k)) {
/* 371 */       java.awt.Point localPoint1 = new java.awt.Point(i, j);java.awt.Point localPoint2 = new java.awt.Point(j, i);
/* 372 */       if ((!Network.MATH.member(localPoint1, this.usrflin)) && (!Network.MATH.member(localPoint2, this.usrflin))) {
/* 373 */         this.usrflin = Network.UTIL.appendToArray(this.usrflin, localPoint1);
/*     */       }
/*     */     }
/* 376 */     HelpPanel.showList("User fill-in = ", this.usrflin);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void showNet()
/*     */   {
/* 383 */     update(getGraphics());
/*     */   }
/*     */   
/*     */   public void paint(Graphics paramGraphics)
/*     */   {
/* 388 */     if (this.pmode == 1) {
/* 389 */       this.ug = this.agtgraf.getSkeleton();
/* 390 */       NetPaint.paintNet(paramGraphics, this.ug);
/*     */     }
/* 392 */     else if (this.pmode == 0) { NetPaint.paintNet(paramGraphics, this.bn, this.arrowLength, this.showIndex);
/* 393 */     } else if (this.pmode == 2) {
/* 394 */       this.jt = this.agtgraf.getJoinTreeTrunk();
/* 395 */       moveNet();
/*     */       
/*     */ 
/* 398 */       NetPaint.paintNet(paramGraphics, this.jt, Network.UndirectGraph.makeSkeleton(this.bn));
/*     */     }
/* 400 */     else if (this.pmode == 3) {
/* 401 */       NetPaint.moveNet(this, this.hg);
/*     */       
/*     */ 
/* 404 */       NetPaint.paintNet(paramGraphics, this.hg, Network.UndirectGraph.makeSkeleton(this.bn));
/*     */     }
/*     */   }
/*     */   
/*     */   void toggleIndex()
/*     */   {
/* 410 */     if (this.showIndex) this.showIndex = false; else
/* 411 */       this.showIndex = true;
/* 412 */     repaint();
/*     */   }
/*     */   
/*     */ 
/*     */   public final synchronized void update(Graphics paramGraphics)
/*     */   {
/* 418 */     Dimension localDimension = getSize();
/* 419 */     if ((this.offScreenImage == null) || (localDimension.width != this.offScreenSize.width) || (localDimension.height != this.offScreenSize.height))
/*     */     {
/* 421 */       this.offScreenImage = createImage(localDimension.width, localDimension.height);
/* 422 */       this.offScreenSize = localDimension;
/* 423 */       this.offScreenGraphics = this.offScreenImage.getGraphics();
/*     */     }
/* 425 */     this.offScreenGraphics.setColor(NetPaint.backgroundColor);
/* 426 */     this.offScreenGraphics.fillRect(0, 0, localDimension.width, localDimension.height);
/* 427 */     paint(this.offScreenGraphics);
/* 428 */     paramGraphics.drawImage(this.offScreenImage, 0, 0, null);
/*     */   }
/*     */   
/*     */   void moveNet()
/*     */   {
/* 433 */     if (this.pmode == 1) { NetPaint.moveNet(this, this.ug);
/* 434 */     } else if (this.pmode == 0) { NetPaint.moveNet(this, this.bn);
/* 435 */     } else if (this.pmode == 2) { NetPaint.moveNet(this, this.jt);
/* 436 */     } else if (this.pmode == 3) NetPaint.moveNet(this, this.hg);
/* 437 */     repaint();
/*     */   }
/*     */   
/*     */   void setArrow()
/*     */   {
/* 442 */     this.arrowLength -= 2.0F;
/* 443 */     if (this.arrowLength < 10.0F) this.arrowLength = 20.0F;
/* 444 */     repaint();
/*     */   }
/*     */   
/*     */   void switchPause()
/*     */   {
/* 449 */     this.pause = (!this.pause);
/* 450 */     HelpPanel.addHelp("Pause mode is " + this.pause + ".");
/*     */   }
/*     */ }


/* Location:              /home/anali/INAOE/tesis/codigos/topub/exe/ww4.jar!/DMsbToLjf/NetworkPanel.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */