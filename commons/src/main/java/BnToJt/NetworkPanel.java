/*     */ package BnToJt;
/*     */ 
/*     */ import Network.HelpPanel;
/*     */ import Network.JoinForest;
/*     */ import Network.LabelDialog;
/*     */ import Network.MarkovNet;
/*     */ import Network.NetPaint;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.FileDialog;
/*     */ import java.awt.Graphics;
/*     */ import java.io.FileWriter;
/*     */ import java.io.IOException;
/*     */ import java.util.StringTokenizer;
/*     */ 
/*     */ class NetworkPanel extends java.awt.Canvas implements Network.Bridge
/*     */ {
/*  17 */   boolean moralize = true;
/*     */   
/*     */   java.awt.Frame frame;
/*     */   
/*  21 */   Network.BayesNet bn = null;
/*  22 */   MarkovNet mn = null;
/*  23 */   JoinForest jf = null;
/*  24 */   boolean hasBelief = false;
/*  25 */   int[] last = null;
/*     */   
/*     */   LabelDialog triConDialog;
/*     */   
/*     */   LabelDialog mvNodeDialog;
/*     */   
/*     */   LabelDialog addLkDialog;
/*     */   
/*     */   static final int MN_MODE = 0;
/*     */   static final int JF_MODE = 1;
/*  35 */   int pmode = 0;
/*     */   
/*     */   static final int MORAL_MODE = 0;
/*     */   
/*     */   static final int TRI_MODE = 1;
/*     */   
/*     */   static final int JTPLUS_MODE = 2;
/*  42 */   int cmode = 0;
/*     */   
/*     */   private java.awt.Image offScreenImage;
/*     */   
/*     */   private Graphics offScreenGraphics;
/*     */   private Dimension offScreenSize;
/*     */   
/*     */   public NetworkPanel(java.awt.Frame paramFrame)
/*     */   {
/*  51 */     this.frame = paramFrame;
/*  52 */     setBackground(NetPaint.backgroundColor);
/*  53 */     initNet();
/*     */   }
/*     */   
/*     */ 
/*     */   void initNet()
/*     */   {
/*  59 */     this.bn = null;
/*  60 */     this.mn = null;
/*  61 */     this.jf = null;
/*  62 */     int i = 0;
/*  63 */     this.last = null;
/*     */   }
/*     */   
/*     */ 
/*     */   void loadInit()
/*     */   {
/*  69 */     initNet();
/*  70 */     this.pmode = 0;
/*     */   }
/*     */   
/*     */   void load()
/*     */   {
/*  75 */     if (MainFrame.isApplet) {
/*  76 */       HelpPanel.addHelp("No load from applet!");
/*  77 */       return;
/*     */     }
/*     */     
/*  80 */     FileDialog localFileDialog = new FileDialog(this.frame, "Load File", 0);
/*  81 */     localFileDialog.pack();
/*  82 */     localFileDialog.setVisible(true);
/*     */     
/*  84 */     String str1 = localFileDialog.getDirectory();
/*  85 */     String str2 = localFileDialog.getFile();
/*  86 */     if (str2 == null) return;
/*  87 */     str2 = new String(Network.UTIL.removePostfix(str2) + ".bn");
/*     */     try {
/*  89 */       java.io.BufferedReader localBufferedReader = new java.io.BufferedReader(new java.io.FileReader(str1 + str2));
/*  90 */       HelpPanel.addHelp("Loading network from " + str1 + str2);
/*  91 */       this.bn = Network.BayesNet.load(localBufferedReader);
/*  92 */       localBufferedReader.close();
/*     */     } catch (IOException localIOException) {
/*  94 */       HelpPanel.showError("Unable to load file!");
/*     */     }
/*  96 */     HelpPanel.addHelp("Loading completed.");
/*     */   }
/*     */   
/*     */   void save()
/*     */   {
/* 101 */     if ((this.mn == null) || (this.jf == null)) {
/* 102 */       HelpPanel.showError("Conversion incomplete!");return;
/*     */     }
/* 104 */     if (!this.hasBelief) {
/* 105 */       HelpPanel.showError("No belief defined!");return;
/*     */     }
/*     */     
/* 108 */     FileDialog localFileDialog = new FileDialog(this.frame, "Save File", 1);
/* 109 */     localFileDialog.pack();
/* 110 */     localFileDialog.setVisible(true);
/*     */     
/* 112 */     String str1 = localFileDialog.getDirectory();
/* 113 */     String str2 = localFileDialog.getFile();
/* 114 */     if (str2 == null) {
/* 115 */       str1 = "";str2 = "network.mn";
/*     */     } else {
/* 117 */       str2 = new String(Network.UTIL.replacePostfix(str2, "mn"));
/*     */     }
/*     */     java.io.PrintWriter localPrintWriter;
/* 120 */     try { FileWriter localFileWriter1 = new FileWriter(str1 + str2);
/* 121 */       localPrintWriter = new java.io.PrintWriter(localFileWriter1);
/* 122 */       HelpPanel.addHelp("Save decomposable Markov network to " + str1 + str2);
/* 123 */       this.mn.save(localPrintWriter);
/* 124 */       localFileWriter1.close();localPrintWriter.close();
/*     */     } catch (IOException localIOException1) {
/* 126 */       HelpPanel.showError("Unable to save " + str1 + str2);
/*     */     }
/* 128 */     HelpPanel.addHelp("Saving completed.");
/*     */     
/* 130 */     str2 = new String(Network.UTIL.replacePostfix(str2, "jt"));
/*     */     try {
/* 132 */       FileWriter localFileWriter2 = new FileWriter(str1 + str2);
/* 133 */       localPrintWriter = new java.io.PrintWriter(localFileWriter2);
/* 134 */       HelpPanel.addHelp("Save join tree to " + str1 + str2);
/* 135 */       this.jf.save(localPrintWriter);
/* 136 */       localFileWriter2.close();localPrintWriter.close();
/*     */     } catch (IOException localIOException2) {
/* 138 */       HelpPanel.showError("Unable to save " + str1 + str2);
/*     */     }
/*     */     
/* 141 */     repaint();
/* 142 */     HelpPanel.addHelp("Saving completed.");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   void setMoralGraph()
/*     */   {
/* 150 */     if (this.moralize) {
/* 151 */       if (this.bn == null) return;
/* 152 */       this.mn = MarkovNet.makeMoralGraph(this.bn);
/* 153 */       HelpPanel.addHelp("Moral graph displayed.");
/*     */     }
/*     */     else {
/* 156 */       setSkeleton();
/* 157 */       HelpPanel.addHelp("Skeleton displayed.");
/*     */     }
/*     */     
/* 160 */     repaint();
/* 161 */     this.cmode = 0;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   void setSkeleton()
/*     */   {
/* 168 */     if (this.bn == null) {
/* 169 */       HelpPanel.showError("Load from .bn file first.");return;
/*     */     }
/* 171 */     this.mn = new MarkovNet();
/* 172 */     this.mn.setSkeleton(this.bn);
/*     */   }
/*     */   
/*     */   void chordalInit() {
/* 176 */     this.jf = null;
/* 177 */     this.pmode = 0;
/*     */   }
/*     */   
/*     */ 
/*     */   void setChordalGraph()
/*     */   {
/* 183 */     if (this.last == null) { this.mn.setChordalGraph();
/*     */     } else {
/* 185 */       this.mn.setChordalGraph(this.last);
/* 186 */       this.last = null;
/*     */     }
/* 188 */     HelpPanel.addHelp("Chordal graph displayed.  Fill-ins are colored.");
/* 189 */     HelpPanel.addHelp("Order = " + this.mn.getStrOrder());
/* 190 */     HelpPanel.showList("Fill-in = ", this.mn.getIntFillIn());
/*     */     
/* 192 */     this.mn.findClique();
/* 193 */     HelpPanel.showList("Cliques = ", this.mn.getIntClique());
/* 194 */     repaint();
/* 195 */     this.cmode = 1;
/*     */   }
/*     */   
/*     */   boolean isTriangulated()
/*     */   {
/* 200 */     return this.mn.isTriangulated();
/*     */   }
/*     */   
/*     */   void jForestInit() {
/* 204 */     this.pmode = 1;
/*     */   }
/*     */   
/*     */ 
/*     */   void setJoinForest()
/*     */   {
/* 210 */     if (this.moralize) this.jf = JoinForest.setJoinGraphAsjf(this.bn, this.mn); else {
/* 211 */       this.jf = JoinForest.setJtFmSkeleton(this.mn);
/*     */     }
/* 213 */     HelpPanel.addHelp("Junction graph created.");
/* 214 */     this.jf.setJoinForest(getBounds());
/* 215 */     repaint();
/* 216 */     HelpPanel.addHelp("Join forest displayed.");
/* 217 */     this.cmode = 2;
/*     */   }
/*     */   
/*     */   void initBelief()
/*     */   {
/* 222 */     if (this.jf == null) {
/* 223 */       HelpPanel.showError("No join tree constructed yet!");return;
/*     */     }
/* 225 */     if (!this.jf.isJoinTree()) {
/* 226 */       HelpPanel.showError("It is not a join tree!");return;
/*     */     }
/* 228 */     this.jf.initBelief();
/* 229 */     this.hasBelief = true;
/* 230 */     HelpPanel.addHelp("Belief initialized.");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   boolean hasNode()
/*     */   {
/* 237 */     if (this.mn == null) return false;
/* 238 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   void setTriCon()
/*     */   {
/* 246 */     this.triConDialog = new LabelDialog(this.frame, this, "Nodes To Eliminate Last", "Node Indexes", "");
/*     */     
/* 248 */     this.triConDialog.setVisible(true);
/*     */   }
/*     */   
/*     */ 
/*     */   void mvNode()
/*     */   {
/* 254 */     this.mvNodeDialog = new LabelDialog(this.frame, this, "Moving Node To New Location", "Node_Index NewX NewY", "");
/*     */     
/* 256 */     this.mvNodeDialog.setVisible(true);
/*     */   }
/*     */   
/*     */ 
/*     */   void addLk()
/*     */   {
/* 262 */     this.addLkDialog = new LabelDialog(this.frame, this, "Add Fill-In", "End1_Index End2_Index", "");
/*     */     
/* 264 */     this.addLkDialog.setVisible(true);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setVector(String[] paramArrayOfString) {}
/*     */   
/*     */ 
/*     */   public void setVector2(String[] paramArrayOfString)
/*     */   {
/* 274 */     StringTokenizer localStringTokenizer = new StringTokenizer(paramArrayOfString[0]);
/*     */     int i;
/* 276 */     int j; if ((paramArrayOfString[1].equals("Nodes To Eliminate Last")) && (paramArrayOfString[2].equals("Node Indexes")))
/*     */     {
/* 278 */       i = localStringTokenizer.countTokens();
/* 279 */       if (i == 0) return;
/* 280 */       this.last = new int[i];
/* 281 */       for (j = 0; j < i; j++) {
/* 282 */         this.last[j] = Integer.parseInt(localStringTokenizer.nextToken());
/* 283 */         if ((this.last[j] < 0) || (this.last[j] >= this.mn.getNodeCount())) {
/* 284 */           HelpPanel.showError("Invalid node index.");
/* 285 */           this.last = null;
/* 286 */           break;
/*     */         }
/*     */       }
/* 289 */       HelpPanel.showList("Nodes to eliminate last = ", this.last);
/*     */ 
/*     */     }
/* 292 */     else if ((paramArrayOfString[1].equals("Moving Node To New Location")) && (paramArrayOfString[2].equals("Node_Index NewX NewY")))
/*     */     {
/* 294 */       i = Integer.parseInt(localStringTokenizer.nextToken());
/* 295 */       j = Integer.parseInt(localStringTokenizer.nextToken());
/* 296 */       int k = Integer.parseInt(localStringTokenizer.nextToken());
/* 297 */       if ((i >= 0) && (i < this.mn.getNodeCount())) this.mn.setPos(i, j, k); else {
/* 298 */         HelpPanel.showError("Invalid node index.");
/*     */       }
/*     */     }
/* 301 */     else if ((paramArrayOfString[1].equals("Add Fill-In")) && (paramArrayOfString[2].equals("End1_Index End2_Index")))
/*     */     {
/* 303 */       if (localStringTokenizer.countTokens() == 0) return;
/* 304 */       i = Integer.parseInt(localStringTokenizer.nextToken());
/* 305 */       j = Integer.parseInt(localStringTokenizer.nextToken());
/* 306 */       if ((i >= 0) && (i < this.mn.getNodeCount()) && (j >= 0) && (j < this.mn.getNodeCount())) {
/* 307 */         java.awt.Point[] arrayOfPoint = new java.awt.Point[1];
/* 308 */         arrayOfPoint[0] = new java.awt.Point(i, j);
/* 309 */         this.mn.pickAddFillIn(arrayOfPoint); } } }
/*     */   
/*     */   public void setVector(int[] paramArrayOfInt) {}
/*     */   
/*     */   public void setVector2(int[] paramArrayOfInt) {}
/*     */   
/*     */   public void setVector(float[] paramArrayOfFloat) {}
/*     */   
/*     */   public void setVector(boolean[] paramArrayOfBoolean) {}
/*     */   public void setTable(float[] paramArrayOfFloat) {}
/* 319 */   public float[] getVector(int paramInt) { return null; }
/* 320 */   public String[] getVector2(int paramInt) { return null; }
/* 321 */   public String[] getVector3(int paramInt) { return null; }
/*     */   
/* 323 */   public void showNet() { update(getGraphics()); }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void paint(Graphics paramGraphics)
/*     */   {
/* 330 */     if (this.pmode == 0) { NetPaint.paintNet(paramGraphics, this.mn);
/* 331 */     } else if (this.pmode == 1) NetPaint.paintNet(paramGraphics, this.jf);
/*     */   }
/*     */   
/*     */   void moveNet()
/*     */   {
/* 336 */     if (this.pmode == 0) NetPaint.moveNet(this, this.mn);
/* 337 */     repaint();
/*     */   }
/*     */   
/*     */ 
/*     */   public final synchronized void update(Graphics paramGraphics)
/*     */   {
/* 343 */     Dimension localDimension = getSize();
/* 344 */     if ((this.offScreenImage == null) || (localDimension.width != this.offScreenSize.width) || (localDimension.height != this.offScreenSize.height))
/*     */     {
/* 346 */       this.offScreenImage = createImage(localDimension.width, localDimension.height);
/* 347 */       this.offScreenSize = localDimension;
/* 348 */       this.offScreenGraphics = this.offScreenImage.getGraphics();
/*     */     }
/* 350 */     this.offScreenGraphics.setColor(NetPaint.backgroundColor);
/* 351 */     this.offScreenGraphics.fillRect(0, 0, localDimension.width, localDimension.height);
/* 352 */     paint(this.offScreenGraphics);
/* 353 */     paramGraphics.drawImage(this.offScreenImage, 0, 0, null);
/*     */   }
/*     */ }


/* Location:              /home/anali/INAOE/tesis/codigos/topub/exe/ww4.jar!/BnToJt/NetworkPanel.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */