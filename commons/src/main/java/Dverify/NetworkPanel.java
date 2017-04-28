/*     */ package Dverify;
/*     */ 
/*     */ import Network.AgGraphV;
/*     */ import Network.AgtV;
/*     */ import Network.HelpPanel;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.FileDialog;
/*     */ import java.awt.Graphics;
/*     */ 
/*     */ public class NetworkPanel extends java.awt.Canvas implements Network.Bridge
/*     */ {
/*     */   java.awt.Frame frame;
/*  13 */   AgGraphV agtgraf = null;
/*  14 */   AgtV agent = null;
/*     */   
/*     */   Network.BayesNet bn;
/*     */   
/*  18 */   static boolean isDsep = false;
/*  19 */   static boolean isAcyc = false;
/*     */   
/*     */ 
/*  22 */   static boolean subdagLoaded = false;
/*  23 */   static boolean communicate = false;
/*  24 */   static boolean localDone = false;
/*  25 */   static int msgFlag = -1;
/*     */   
/*     */ 
/*  28 */   float arrowLength = 20.0F;
/*  29 */   final float maxLength = 20.0F;
/*     */   
/*     */ 
/*  32 */   java.awt.Point downPoint = new java.awt.Point(0, 0);
/*  33 */   int downAgent = -2;
/*  34 */   int frontAgent = -2;
/*  35 */   int downNode = -2;
/*     */   
/*     */   private java.awt.Image offScreenImage;
/*     */   
/*     */   private Graphics offScreenGraphics;
/*     */   private Dimension offScreenSize;
/*     */   
/*     */   public NetworkPanel(java.awt.Frame paramFrame)
/*     */   {
/*  44 */     this.frame = paramFrame;
/*  45 */     setBackground(Network.NetPaint.backgroundColor);
/*     */     
/*  47 */     addMouseListener(new java.awt.event.MouseAdapter()
/*     */     {
/*     */       public void mousePressed(java.awt.event.MouseEvent paramAnonymousMouseEvent)
/*     */       {
/*  51 */         NetworkPanel.this.downPoint.move(paramAnonymousMouseEvent.getX(), paramAnonymousMouseEvent.getY());
/*  52 */         if (NetworkPanel.communicate) NetworkPanel.this.mouseDownInCommu();
/*  53 */         NetworkPanel.this.repaint();
/*     */       }
/*     */     });
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   void load()
/*     */   {
/*  63 */     localDone = false;
/*     */     
/*  65 */     FileDialog localFileDialog = new FileDialog(this.frame, "", 0);
/*  66 */     localFileDialog.pack();localFileDialog.setVisible(true);
/*     */     
/*  68 */     String str1 = localFileDialog.getDirectory();
/*  69 */     String str2 = localFileDialog.getFile();
/*  70 */     if (str2 == null) return;
/*  71 */     String str3 = str1 + Network.UTIL.removePostfix(str2);
/*  72 */     if (this.agent != null) {
/*  73 */       HelpPanel.addHelp("Current agent cleared.");
/*  74 */       this.agent.stopBearerMgr();
/*  75 */       this.agtgraf = null;this.agent = null;
/*  76 */       subdagLoaded = false;
/*     */     }
/*     */     
/*  79 */     this.agtgraf = new AgGraphV();
/*  80 */     this.agtgraf.setPath(str3);
/*  81 */     HelpPanel.addHelp("path=" + this.agtgraf.getPath());
/*  82 */     this.agtgraf.loadDag();
/*  83 */     this.agtgraf.loadDsepset();
/*  84 */     this.agent = new AgtV(this.agtgraf, str3, this);
/*  85 */     this.agent.loadHostPort();
/*     */     
/*  87 */     this.bn = this.agtgraf.getNet();
/*  88 */     Network.NetPaint.moveNet(this, this.bn);
/*  89 */     repaint();
/*     */     
/*  91 */     HelpPanel.addHelp("Loading complete.");
/*  92 */     subdagLoaded = true;
/*  93 */     localDone = true;
/*     */   }
/*     */   
/*     */   void getSet()
/*     */   {
/*  98 */     this.agent.setMsgBox();
/*  99 */     this.agent.setBearerMgr();
/* 100 */     HelpPanel.addHelp("Ready to verify.");
/*     */   }
/*     */   
/*     */   void stopBearerMgr()
/*     */   {
/* 105 */     this.agent.stopBearerMgr();
/*     */   }
/*     */   
/*     */   boolean hasSubdag()
/*     */   {
/* 110 */     return subdagLoaded;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 117 */   private void mouseDownInCommu() { HelpPanel.addHelp("Communication is on going."); }
/*     */   
/*     */   public void setVector(float[] paramArrayOfFloat) {}
/*     */   
/*     */   public void setVector(int[] paramArrayOfInt) {}
/*     */   public void setTable(float[] paramArrayOfFloat) {}
/* 123 */   public float[] getVector(int paramInt) { return null; }
/* 124 */   public String[] getVector2(int paramInt) { return null; }
/* 125 */   public String[] getVector3(int paramInt) { return null; }
/*     */   
/*     */ 
/*     */   public void setVector(boolean[] paramArrayOfBoolean)
/*     */   {
/* 130 */     if (paramArrayOfBoolean.length == 1) {
/* 131 */       isDsep = paramArrayOfBoolean[0];return;
/*     */     }
/*     */     
/* 134 */     isAcyc = paramArrayOfBoolean[1];
/* 135 */     if ((isDsep) && (isAcyc)) {
/* 136 */       HelpPanel.addHelp("Root: MSDAG is acyclic with d-sepsets.");
/* 137 */     } else if ((!isDsep) && (!isAcyc)) {
/* 138 */       HelpPanel.addHelp("Root: Err> cyclic MSDAG with non-d-sepset!");
/* 139 */     } else if ((isDsep) && (!isAcyc)) {
/* 140 */       HelpPanel.addHelp("Root: Err> cyclic MSDAG with d-sepset!");
/*     */     } else {
/* 142 */       HelpPanel.addHelp("Root: Err> acyclic MSDAG with non-d-sepset!");
/*     */     }
/*     */   }
/*     */   
/*     */   public void setVector2(int[] paramArrayOfInt)
/*     */   {
/* 148 */     if (paramArrayOfInt[0] == 3) { msgFlag = 4;
/* 149 */     } else if (paramArrayOfInt[0] == 4) { msgFlag = 5;
/* 150 */     } else if (paramArrayOfInt[0] == 5) msgFlag = 8;
/* 151 */     repaint();
/*     */   }
/*     */   
/*     */   public void setVector(String[] paramArrayOfString) {}
/*     */   
/*     */   public void setVector2(String[] paramArrayOfString)
/*     */   {
/* 158 */     if (paramArrayOfString[0].equals("communication"))
/* 159 */       if (paramArrayOfString[1].equals("communicate")) {
/* 160 */         while (!localDone) HelpPanel.addHelp("Wait for local activity.");
/* 161 */         communicate = true;
/*     */       } else {
/* 163 */         communicate = false;
/*     */       }
/* 165 */     repaint();
/*     */   }
/*     */   
/*     */ 
/*     */   boolean isCommunicating()
/*     */   {
/* 171 */     return communicate;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   void communicate()
/*     */   {
/* 178 */     while (!localDone) HelpPanel.addHelp("Wait for local activity.");
/* 179 */     communicate = true;
/* 180 */     msgFlag = 3;
/* 181 */     this.agent.setRoot(3);
/*     */     
/* 183 */     while (msgFlag != 4)
/* 184 */       HelpPanel.appendHelp("");
/* 185 */     this.agent.setRoot(4);
/*     */     
/* 187 */     while (msgFlag != 5)
/* 188 */       HelpPanel.appendHelp("");
/* 189 */     this.agent.setRoot(5);
/*     */     
/* 191 */     while (msgFlag != 8)
/* 192 */       HelpPanel.appendHelp("");
/* 193 */     this.agent.setRoot(8);
/* 194 */     repaint();
/*     */   }
/*     */   
/*     */   public void showNet() {
/* 198 */     update(getGraphics());
/*     */   }
/*     */   
/*     */   public void paint(Graphics paramGraphics)
/*     */   {
/* 203 */     Network.NetPaint.paintNet(paramGraphics, this.bn, this.arrowLength, false);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public final synchronized void update(Graphics paramGraphics)
/*     */   {
/* 211 */     Dimension localDimension = getSize();
/* 212 */     if ((this.offScreenImage == null) || (localDimension.width != this.offScreenSize.width) || (localDimension.height != this.offScreenSize.height))
/*     */     {
/* 214 */       this.offScreenImage = createImage(localDimension.width, localDimension.height);
/* 215 */       this.offScreenSize = localDimension;
/* 216 */       this.offScreenGraphics = this.offScreenImage.getGraphics();
/*     */     }
/* 218 */     this.offScreenGraphics.setColor(Network.NetPaint.backgroundColor);
/* 219 */     this.offScreenGraphics.fillRect(0, 0, localDimension.width, localDimension.height);
/* 220 */     paint(this.offScreenGraphics);
/* 221 */     paramGraphics.drawImage(this.offScreenImage, 0, 0, null);
/*     */   }
/*     */   
/*     */   void moveNet()
/*     */   {
/* 226 */     Network.NetPaint.moveNet(this, this.bn);
/* 227 */     repaint();
/*     */   }
/*     */   
/*     */   void setArrow()
/*     */   {
/* 232 */     this.arrowLength -= 2.0F;
/* 233 */     if (this.arrowLength < 10.0F) this.arrowLength = 20.0F;
/* 234 */     repaint();
/*     */   }
/*     */ }


/* Location:              /home/anali/INAOE/tesis/codigos/topub/exe/ww4.jar!/Dverify/NetworkPanel.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */