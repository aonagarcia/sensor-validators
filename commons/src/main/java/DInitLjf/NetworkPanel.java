/*     */ package DInitLjf;
/*     */ 
/*     */ import Network.AgGraphI;
/*     */ import Network.AgtI;
/*     */ import Network.HelpPanel;
/*     */ import Network.NetPaint;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.FileDialog;
/*     */ import java.awt.Graphics;
/*     */ 
/*     */ public class NetworkPanel extends java.awt.Canvas implements Network.Bridge
/*     */ {
/*     */   java.awt.Frame frame;
/*  14 */   AgGraphI agtgraf = null;
/*  15 */   AgtI agent = null;
/*     */   
/*     */   Network.BayesNet bn;
/*     */   
/*     */   static final int NO_SUBNET = 0;
/*     */   static final int LOADED = 1;
/*     */   static final int HAS_BELIEF = 2;
/*     */   static final int SAVED = 3;
/*  23 */   int stage = 0;
/*     */   
/*     */ 
/*     */ 
/*  27 */   static boolean subnetLoaded = false;
/*  28 */   static boolean communicate = false;
/*  29 */   static boolean localDone = false;
/*  30 */   static int msgFlag = -1;
/*     */   
/*     */ 
/*  33 */   float arrowLength = 20.0F;
/*  34 */   final float maxLength = 20.0F;
/*     */   
/*     */   private java.awt.Image offScreenImage;
/*     */   
/*     */   private Graphics offScreenGraphics;
/*     */   private Dimension offScreenSize;
/*     */   
/*     */   public NetworkPanel(java.awt.Frame paramFrame)
/*     */   {
/*  43 */     this.frame = paramFrame;
/*  44 */     setBackground(NetPaint.backgroundColor);
/*     */     
/*  46 */     addMouseListener(new java.awt.event.MouseAdapter()
/*     */     {
/*     */       public void mousePressed(java.awt.event.MouseEvent paramAnonymousMouseEvent)
/*     */       {
/*  50 */         if (NetworkPanel.communicate) NetworkPanel.this.mouseDownInCommu();
/*  51 */         NetworkPanel.this.repaint();
/*     */       }
/*     */     });
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   void load()
/*     */   {
/*  61 */     localDone = false;
/*     */     
/*  63 */     FileDialog localFileDialog = new FileDialog(this.frame, "", 0);
/*  64 */     localFileDialog.pack();localFileDialog.setVisible(true);
/*     */     
/*  66 */     String str1 = localFileDialog.getDirectory();
/*  67 */     String str2 = localFileDialog.getFile();
/*  68 */     if (str2 == null) return;
/*  69 */     String str3 = str1 + Network.UTIL.removePostfix(str2);
/*  70 */     if (this.agent != null) {
/*  71 */       HelpPanel.addHelp("Current subnet cleared.");
/*  72 */       this.agent.stopBearerMgr();
/*  73 */       this.agtgraf = null;this.agent = null;
/*  74 */       subnetLoaded = false;
/*     */     }
/*     */     
/*  77 */     this.agtgraf = new AgGraphI();
/*  78 */     this.agtgraf.setPath(str3);
/*  79 */     HelpPanel.addHelp("path=" + this.agtgraf.getPath());
/*  80 */     this.agtgraf.loadBn();
/*  81 */     this.agtgraf.loadDsepset();
/*  82 */     this.agtgraf.loadJtTrunk();
/*  83 */     this.agtgraf.loadLinkageTreeTrunk();
/*  84 */     this.agtgraf.loadNodeAssigned();
/*  85 */     this.agtgraf.setStateCount();
/*     */     
/*  87 */     this.agent = new AgtI(this.agtgraf, str3, this);
/*  88 */     this.agent.loadHostPort();
/*     */     
/*  90 */     this.bn = this.agtgraf.getNet();
/*  91 */     NetPaint.moveNet(this, this.bn);
/*  92 */     repaint();
/*  93 */     HelpPanel.addHelp("Loading completed.");
/*  94 */     subnetLoaded = true;
/*  95 */     localDone = true;
/*  96 */     this.stage = 1;
/*     */   }
/*     */   
/*     */   void save()
/*     */   {
/* 101 */     if (this.stage < 2) {
/* 102 */       HelpPanel.showError("Please set prior belief.");return;
/*     */     }
/* 104 */     if (this.stage > 2) {
/* 105 */       HelpPanel.addHelp("Files saved.  Load new subnet or exit.");return;
/*     */     }
/* 107 */     this.agtgraf.saveJoinTree();
/* 108 */     HelpPanel.addHelp("Local junction tree saved.");
/* 109 */     this.agtgraf.saveLinkageTree();
/* 110 */     HelpPanel.addHelp("Linkage trees saved.");
/*     */     
/* 112 */     this.stage = 3;
/* 113 */     repaint();
/*     */   }
/*     */   
/*     */   void getSet()
/*     */   {
/* 118 */     this.agent.setMsgBox();
/* 119 */     this.agent.setBearerMgr();
/* 120 */     HelpPanel.addHelp("Ready for initializing belief.");
/*     */   }
/*     */   
/*     */   boolean hasSubnet()
/*     */   {
/* 125 */     return subnetLoaded;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 132 */   private void mouseDownInCommu() { HelpPanel.addHelp("Communication is on going."); }
/*     */   
/*     */   public void setVector(String[] paramArrayOfString) {}
/*     */   
/*     */   public void setVector(boolean[] paramArrayOfBoolean) {}
/*     */   
/*     */   public void setVector(float[] paramArrayOfFloat) {}
/*     */   
/*     */   public void setVector(int[] paramArrayOfInt) {}
/* 141 */   public void setVector2(String[] paramArrayOfString) { if (paramArrayOfString[0].equals("communication")) {
/* 142 */       if (paramArrayOfString[1].equals("communicate")) {
/* 143 */         while (!localDone) HelpPanel.addHelp("Wait for local activity.");
/* 144 */         communicate = true;
/*     */       } else {
/* 146 */         communicate = false;
/*     */       }
/* 148 */     } else if ((paramArrayOfString[0].equals("operation_stage")) && 
/* 149 */       (paramArrayOfString[1].equals("set_priors"))) { this.stage = 2;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setVector2(int[] paramArrayOfInt)
/*     */   {
/* 158 */     if (paramArrayOfInt[0] == 1) { msgFlag = 3;
/* 159 */     } else if (paramArrayOfInt[0] == 3) { msgFlag = 5;
/* 160 */     } else if (paramArrayOfInt[0] == 5) { msgFlag = 2;
/* 161 */     } else if (paramArrayOfInt[0] == 2) msgFlag = -1; }
/*     */   
/*     */   public void setTable(float[] paramArrayOfFloat) {}
/*     */   
/* 165 */   public float[] getVector(int paramInt) { return null; }
/* 166 */   public String[] getVector2(int paramInt) { return null; }
/* 167 */   public String[] getVector3(int paramInt) { return null; }
/*     */   
/*     */ 
/*     */   boolean isCommunicating()
/*     */   {
/* 172 */     return communicate;
/*     */   }
/*     */   
/*     */ 
/*     */   void initBelief()
/*     */   {
/* 178 */     if (this.stage < 1) {
/* 179 */       HelpPanel.showError("Please load agent local network first.");return;
/*     */     }
/* 181 */     if (this.stage >= 2) {
/* 182 */       HelpPanel.showError("Prior belief has been defined.");return;
/*     */     }
/*     */     
/* 185 */     while (!localDone) HelpPanel.addHelp("Wait for local activity.");
/* 186 */     communicate = true;
/* 187 */     msgFlag = 1;
/* 188 */     this.agent.setRoot(1);
/* 189 */     while (msgFlag != 3)
/* 190 */       HelpPanel.appendHelp("");
/* 191 */     this.agent.setRoot(3);
/* 192 */     while (msgFlag != 5)
/* 193 */       HelpPanel.appendHelp("");
/* 194 */     this.agent.setRoot(5);
/* 195 */     while (msgFlag != 2)
/* 196 */       HelpPanel.appendHelp("");
/* 197 */     this.agent.setRoot(2);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void showNet()
/*     */   {
/* 204 */     update(getGraphics());
/*     */   }
/*     */   
/*     */   public void paint(Graphics paramGraphics)
/*     */   {
/* 209 */     NetPaint.paintNet(paramGraphics, this.bn, this.arrowLength, false);
/*     */   }
/*     */   
/*     */ 
/*     */   public final synchronized void update(Graphics paramGraphics)
/*     */   {
/* 215 */     Dimension localDimension = getSize();
/* 216 */     if ((this.offScreenImage == null) || (localDimension.width != this.offScreenSize.width) || (localDimension.height != this.offScreenSize.height))
/*     */     {
/* 218 */       this.offScreenImage = createImage(localDimension.width, localDimension.height);
/* 219 */       this.offScreenSize = localDimension;
/* 220 */       this.offScreenGraphics = this.offScreenImage.getGraphics();
/*     */     }
/* 222 */     this.offScreenGraphics.setColor(NetPaint.backgroundColor);
/* 223 */     this.offScreenGraphics.fillRect(0, 0, localDimension.width, localDimension.height);
/* 224 */     paint(this.offScreenGraphics);
/* 225 */     paramGraphics.drawImage(this.offScreenImage, 0, 0, null);
/*     */   }
/*     */   
/*     */   void moveNet()
/*     */   {
/* 230 */     NetPaint.moveNet(this, this.bn);
/* 231 */     repaint();
/*     */   }
/*     */   
/*     */   void setArrow()
/*     */   {
/* 236 */     this.arrowLength -= 2.0F;
/* 237 */     if (this.arrowLength < 10.0F) this.arrowLength = 20.0F;
/* 238 */     repaint();
/*     */   }
/*     */ }


/* Location:              /home/anali/INAOE/tesis/codigos/topub/exe/ww4.jar!/DInitLjf/NetworkPanel.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */