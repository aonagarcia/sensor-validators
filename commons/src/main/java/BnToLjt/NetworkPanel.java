/*     */ package BnToLjt;
/*     */ 
/*     */ import Network.HelpPanel;
/*     */ import Network.MarkovNet;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.FileDialog;
/*     */ import java.awt.Point;
/*     */ 
/*     */ class NetworkPanel extends java.awt.Canvas implements Network.Bridge
/*     */ {
/*     */   java.awt.Frame frame;
/*  12 */   Network.BayesNet bn = null;
/*  13 */   MarkovNet mn = null;
/*  14 */   Network.JoinForest jf = null;
/*  15 */   Network.LJoinForest ljf = null;
/*  16 */   boolean hasBelief = false;
/*     */   
/*     */ 
/*  19 */   Point downPoint = new Point(0, 0);
/*  20 */   Point thisPoint = new Point(0, 0);
/*  21 */   Point upPoint = new Point(0, 0);
/*     */   
/*  23 */   int downNode = -2;
/*  24 */   int downNode2 = -2;
/*  25 */   int upNode = -2;
/*  26 */   Point oldPos = new Point(0, 0);
/*     */   
/*     */   static final int START_MODE = 0;
/*     */   
/*     */   static final int ADD_LINK_MODE = 1;
/*     */   
/*     */   static final int MOVE_NODE_MODE = 2;
/*     */   static final int DEL_LINK_MODE = 3;
/*     */   static final int SEE_PROB_MODE = 4;
/*  35 */   int mode = 0;
/*     */   
/*     */   static final int LAZY_MODE = 0;
/*     */   
/*     */   static final int NORM_MODE = 1;
/*     */   
/*  41 */   int imode = 0;
/*     */   
/*     */   static final int NOBN = 0;
/*     */   
/*     */   static final int MORAL = 1;
/*     */   
/*     */   static final int CHORDAL = 2;
/*     */   static final int JOIN = 3;
/*     */   static final int BELIEF = 4;
/*     */   static final int SAVED = 5;
/*  51 */   int stage = 0;
/*     */   
/*     */   static final int MN_MODE = 0;
/*     */   
/*     */   static final int JF_MODE = 1;
/*     */   
/*  57 */   int pmode = 0;
/*     */   
/*     */   private java.awt.Image offScreenImage;
/*     */   
/*     */   private java.awt.Graphics offScreenGraphics;
/*     */   private Dimension offScreenSize;
/*     */   
/*     */   public NetworkPanel(java.awt.Frame paramFrame)
/*     */   {
/*  66 */     initMouse();
/*  67 */     this.frame = paramFrame;
/*  68 */     setBackground(Network.NetPaint.backgroundColor);
/*  69 */     initNet();
/*     */   }
/*     */   
/*     */ 
/*     */   void initNet()
/*     */   {
/*  75 */     this.bn = null;
/*  76 */     this.mn = null;
/*  77 */     this.jf = null;
/*  78 */     this.ljf = null;
/*  79 */     int i = 0;
/*     */   }
/*     */   
/*     */   private void initMouse()
/*     */   {
/*  84 */     this.downPoint = new Point(0, 0);
/*  85 */     this.thisPoint = new Point(0, 0);
/*  86 */     this.upPoint = new Point(0, 0);
/*     */     
/*  88 */     this.downNode = -2;
/*  89 */     this.upNode = -2;
/*  90 */     this.oldPos = new Point(0, 0);
/*     */   }
/*     */   
/*     */ 
/*     */   void loadInit()
/*     */   {
/*  96 */     initNet();
/*  97 */     this.pmode = 0;
/*     */   }
/*     */   
/*     */   void load()
/*     */   {
/* 102 */     FileDialog localFileDialog = new FileDialog(this.frame, "Load File", 0);
/* 103 */     localFileDialog.pack();
/* 104 */     localFileDialog.setVisible(true);
/*     */     
/* 106 */     String str1 = localFileDialog.getDirectory();
/* 107 */     String str2 = localFileDialog.getFile();
/* 108 */     if (str2 == null) return;
/* 109 */     str2 = new String(Network.UTIL.removePostfix(str2) + ".bn");
/*     */     try {
/* 111 */       java.io.BufferedReader localBufferedReader = new java.io.BufferedReader(new java.io.FileReader(str1 + str2));
/* 112 */       HelpPanel.addHelp("Loading network from " + str1 + str2);
/* 113 */       this.bn = Network.BayesNet.load(localBufferedReader);
/* 114 */       localBufferedReader.close();
/*     */     } catch (java.io.IOException localIOException) {
/* 116 */       HelpPanel.showError("Unable to load file!");
/*     */     }
/* 118 */     HelpPanel.addHelp("Loading completed.");
/*     */   }
/*     */   
/*     */   void save()
/*     */   {
/* 123 */     if (this.stage < 4) {
/* 124 */       HelpPanel.showError("Please initialize belief.");return;
/*     */     }
/* 126 */     if (this.stage > 4) {
/* 127 */       HelpPanel.addHelp("Files saved.  Load next BN or exit.");return;
/*     */     }
/*     */     
/* 130 */     FileDialog localFileDialog = new FileDialog(this.frame, "Save File", 1);
/* 131 */     localFileDialog.pack();
/* 132 */     localFileDialog.setVisible(true);
/*     */     
/* 134 */     String str1 = localFileDialog.getDirectory();
/* 135 */     String str2 = localFileDialog.getFile();
/* 136 */     if (str2 == null) {
/* 137 */       str1 = "";str2 = "network.mn";
/*     */     } else {
/* 139 */       str2 = new String(Network.UTIL.replacePostfix(str2, "mn"));
/*     */     }
/*     */     java.io.FileWriter localFileWriter;
/*     */     java.io.PrintWriter localPrintWriter;
/*     */     try {
/* 144 */       localFileWriter = new java.io.FileWriter(str1 + str2);
/* 145 */       localPrintWriter = new java.io.PrintWriter(localFileWriter);
/* 146 */       HelpPanel.addHelp("Save decomposable Markov network to " + str1 + str2);
/* 147 */       this.mn.save(localPrintWriter);
/* 148 */       localFileWriter.close();localPrintWriter.close();
/*     */     } catch (java.io.IOException localIOException1) {
/* 150 */       HelpPanel.showError("Unable to save " + str1 + str2);
/*     */     }
/* 152 */     HelpPanel.addHelp("Saving completed.");
/*     */     try
/*     */     {
/* 155 */       if (this.imode == 0) {
/* 156 */         str2 = new String(Network.UTIL.replacePostfix(str2, "ljt"));
/* 157 */         localFileWriter = new java.io.FileWriter(str1 + str2);
/* 158 */         localPrintWriter = new java.io.PrintWriter(localFileWriter);
/* 159 */         HelpPanel.addHelp("Save join tree to " + str1 + str2);
/* 160 */         this.ljf.save(localPrintWriter);
/*     */       }
/*     */       else {
/* 163 */         str2 = new String(Network.UTIL.replacePostfix(str2, "jt"));
/* 164 */         localFileWriter = new java.io.FileWriter(str1 + str2);
/* 165 */         localPrintWriter = new java.io.PrintWriter(localFileWriter);
/* 166 */         HelpPanel.addHelp("Save join tree to " + str1 + str2);
/* 167 */         this.jf.save(localPrintWriter);
/*     */       }
/* 169 */       localFileWriter.close();localPrintWriter.close();
/*     */     } catch (java.io.IOException localIOException2) {
/* 171 */       HelpPanel.showError("Unable to save " + str1 + str2);
/*     */     }
/*     */     
/* 174 */     repaint();
/* 175 */     HelpPanel.addHelp("Saving completed.");
/*     */     
/* 177 */     this.stage = 5;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   void setMoralGraph()
/*     */   {
/* 184 */     if (this.bn == null) return;
/* 185 */     this.mn = MarkovNet.makeMoralGraph(this.bn);
/* 186 */     HelpPanel.addHelp("Moral graph displayed.");
/* 187 */     repaint();
/* 188 */     this.stage = 1;
/*     */   }
/*     */   
/*     */   void chordalInit() {
/* 192 */     this.jf = null;
/* 193 */     this.pmode = 0;
/*     */   }
/*     */   
/*     */   void setChordalGraph()
/*     */   {
/* 198 */     if (this.stage < 1) {
/* 199 */       HelpPanel.showError("Please load and moralize BN first.");return;
/*     */     }
/* 201 */     if (this.stage > 1) {
/* 202 */       HelpPanel.showError("Triangulation has completed.");return;
/*     */     }
/* 204 */     this.mn.setChordalGraph();
/* 205 */     HelpPanel.addHelp("Chordal graph displayed.  Fill-ins are colored.");
/* 206 */     HelpPanel.addHelp("Order = " + this.mn.getStrOrder());
/* 207 */     HelpPanel.showList("Fill-in = ", this.mn.getIntFillIn());
/*     */     
/* 209 */     this.mn.findClique();
/* 210 */     HelpPanel.showList("Cliques = ", this.mn.getIntClique());
/* 211 */     repaint();
/* 212 */     this.stage = 2;
/*     */   }
/*     */   
/*     */   boolean isTriangulated()
/*     */   {
/* 217 */     return this.mn.isTriangulated();
/*     */   }
/*     */   
/*     */   void jForestInit() {
/* 221 */     this.pmode = 1;
/*     */   }
/*     */   
/*     */   void setJF()
/*     */   {
/* 226 */     if (this.stage < 2) {
/* 227 */       HelpPanel.showError("Please triangulate first.");return;
/*     */     }
/* 229 */     if (this.stage > 2) {
/* 230 */       HelpPanel.showError("Join Forest already created.");return;
/*     */     }
/*     */     
/* 233 */     String[] arrayOfString = new String[2];
/* 234 */     arrayOfString[0] = new String("Lazy inference");
/* 235 */     arrayOfString[1] = new String("Normal inference");
/* 236 */     Network.RadioDialog localRadioDialog = new Network.RadioDialog(this.frame, this, "Select Inference Mode:", arrayOfString, 10, 10);
/*     */     
/* 238 */     localRadioDialog.setVisible(true);
/*     */     
/* 240 */     jForestInit();
/* 241 */     setJoinForest();
/*     */     
/* 243 */     this.stage = 3;
/*     */   }
/*     */   
/*     */   void setJoinForest()
/*     */   {
/* 248 */     if (this.imode == 0) {
/* 249 */       this.ljf = Network.LJoinForest.setJoinGraphAsjf(this.bn, this.mn);
/* 250 */       HelpPanel.addHelp("Junction graph created.");
/* 251 */       this.ljf.setJoinForest(getBounds());
/*     */     }
/*     */     else {
/* 254 */       this.jf = Network.JoinForest.setJoinGraphAsjf(this.bn, this.mn);
/* 255 */       HelpPanel.addHelp("Junction graph created.");
/* 256 */       this.jf.setJoinForest(getBounds());
/*     */     }
/* 258 */     repaint();
/* 259 */     HelpPanel.addHelp("Join forest displayed.");
/*     */   }
/*     */   
/*     */   void initBelief()
/*     */   {
/* 264 */     if (this.stage < 3) {
/* 265 */       HelpPanel.showError("Please build Join Forest first.");return;
/*     */     }
/* 267 */     if (this.stage > 3) {
/* 268 */       HelpPanel.showError("Belief has been initialized.");return;
/*     */     }
/*     */     
/* 271 */     if (this.imode == 0) {
/* 272 */       if (this.ljf == null) {
/* 273 */         HelpPanel.showError("No join tree constructed yet!");return;
/*     */       }
/* 275 */       if (!this.ljf.isJoinTree()) {
/* 276 */         HelpPanel.showError("It is not a join tree!");return;
/*     */       }
/* 278 */       this.ljf.clrBufferPoten();
/*     */     }
/*     */     else {
/* 281 */       if (this.jf == null) {
/* 282 */         HelpPanel.showError("No join tree constructed yet!");return;
/*     */       }
/* 284 */       if (!this.jf.isJoinTree()) {
/* 285 */         HelpPanel.showError("It is not a join tree!");return;
/*     */       }
/* 287 */       this.jf.initBelief();
/*     */     }
/* 289 */     this.hasBelief = true;
/* 290 */     HelpPanel.addHelp("Belief initialized.");
/*     */     
/* 292 */     this.stage = 4;
/*     */   }
/*     */   
/*     */ 
/*     */   public void setVector(String[] paramArrayOfString) {}
/*     */   
/*     */ 
/* 299 */   public void setVector(int[] paramArrayOfInt) { this.imode = paramArrayOfInt[0]; }
/*     */   
/*     */   public void setVector2(int[] paramArrayOfInt) {}
/*     */   public void setVector2(String[] paramArrayOfString) {}
/*     */   public void setVector(boolean[] paramArrayOfBoolean) {}
/*     */   public void setVector(float[] paramArrayOfFloat) {}
/*     */   public void setTable(float[] paramArrayOfFloat) {}
/*     */   public void showNet() {}
/* 307 */   public float[] getVector(int paramInt) { return null; }
/* 308 */   public String[] getVector2(int paramInt) { return null; }
/* 309 */   public String[] getVector3(int paramInt) { return null; }
/*     */   
/*     */ 
/*     */ 
/*     */   boolean hasNode()
/*     */   {
/* 315 */     if (this.mn == null) return false;
/* 316 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void paint(java.awt.Graphics paramGraphics)
/*     */   {
/* 323 */     if (this.pmode == 0) { Network.NetPaint.paintNet(paramGraphics, this.mn);
/* 324 */     } else if (this.pmode == 1) {
/* 325 */       if (this.imode == 0) Network.NetPaint.paintNet(paramGraphics, this.ljf); else {
/* 326 */         Network.NetPaint.paintNet(paramGraphics, this.jf);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   void moveNet() {
/* 332 */     if (this.pmode == 0) { Network.NetPaint.moveNet(this, this.mn);
/* 333 */     } else if (this.pmode == 1) {
/* 334 */       if (this.imode == 0) Network.NetPaint.moveNet(this, this.ljf); else
/* 335 */         Network.NetPaint.moveNet(this, this.jf);
/*     */     }
/* 337 */     repaint();
/*     */   }
/*     */   
/*     */ 
/*     */   public final synchronized void update(java.awt.Graphics paramGraphics)
/*     */   {
/* 343 */     Dimension localDimension = getSize();
/* 344 */     if ((this.offScreenImage == null) || (localDimension.width != this.offScreenSize.width) || (localDimension.height != this.offScreenSize.height))
/*     */     {
/* 346 */       this.offScreenImage = createImage(localDimension.width, localDimension.height);
/* 347 */       this.offScreenSize = localDimension;
/* 348 */       this.offScreenGraphics = this.offScreenImage.getGraphics();
/*     */     }
/* 350 */     this.offScreenGraphics.setColor(Network.NetPaint.backgroundColor);
/* 351 */     this.offScreenGraphics.fillRect(0, 0, localDimension.width, localDimension.height);
/* 352 */     paint(this.offScreenGraphics);
/* 353 */     paramGraphics.drawImage(this.offScreenImage, 0, 0, null);
/*     */   }
/*     */   
/*     */   void setMode(String paramString)
/*     */   {
/* 358 */     if (paramString.equals(CommandPanel.addLinkLabel)) { this.mode = 1;
/* 359 */     } else if (paramString.equals(CommandPanel.mvNodeLabel)) { this.mode = 2;
/* 360 */     } else if (paramString.equals(CommandPanel.delLinkLabel)) { this.mode = 3;
/* 361 */     } else if (paramString.equals(CommandPanel.probLabel)) this.mode = 4; else {
/* 362 */       this.mode = 0;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /home/anali/INAOE/tesis/codigos/topub/exe/ww4.jar!/BnToLjt/NetworkPanel.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */