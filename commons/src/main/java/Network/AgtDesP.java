/*     */ package Network;
/*     */ 
/*     */ import java.io.File;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AgtDesP
/*     */   extends AgtDes
/*     */ {
/*  11 */   int round = 0;
/*  12 */   int barcnt = 0;
/*  13 */   int jumpcnt = 0;
/*  14 */   boolean jump = false;
/*     */   public static final int ASKJUMPNEXT = 3;
/*     */   
/*     */   public AgtDesP(AgGraphDes paramAgGraphDes, String paramString, Bridge paramBridge) {
/*  18 */     super(paramAgGraphDes, paramString, paramBridge);
/*     */   }
/*     */   
/*     */   public String[] actAll(int paramInt1, int paramInt2)
/*     */   {
/*     */     String str3;
/*     */     String str4;
/*     */     String str5;
/*     */     Object localObject1;
/*     */     Object localObject2;
/*  28 */     if (paramInt2 == 1)
/*     */     {
/*  30 */       HelpPanel.addHelp("*** Col-Design No. " + this.round + " ***");
/*     */       
/*  32 */       String str1 = new String("" + this.round);
/*  33 */       str3 = UTIL.openWithZero(str1, 3);
/*  34 */       str4 = new String(this.agnet.getPath() + "-" + str3);
/*  35 */       str5 = new String(str4 + ".wrd");
/*     */       
/*  37 */       localObject1 = new File(str5);
/*  38 */       HelpPanel.addHelp("Wait for " + str5 + " ...");
/*  39 */       while (!((File)localObject1).exists()) {
/*     */         try {
/*  41 */           Thread.sleep(1000L);
/*     */         }
/*     */         catch (InterruptedException localInterruptedException) {}
/*     */       }
/*  45 */       localObject2 = new DesNetPers(this.agnet.getDn());
/*  46 */       if (this.jump) HelpPanel.addHelp("Action = jump"); else {
/*  47 */         HelpPanel.addHelp("Action = move");
/*     */       }
/*  49 */       ((DesNetPers)localObject2).update(str4, this.jump);
/*  50 */       this.agnet.setDn((DesignNet)localObject2);
/*  51 */       HelpPanel.addHelp("Subnet updated using " + str5);
/*     */       
/*     */ 
/*  54 */       HelpPanel.addHelp("Set single division ...");
/*  55 */       this.agnet.setDivTree();
/*  56 */       this.agnet.loadWeight(this.agentnm);
/*  57 */       HelpPanel.addHelp("Division set.");
/*     */       
/*  59 */       DivisionTree localDivisionTree = this.agnet.getDivTree();
/*  60 */       localDivisionTree.designByDiv();
/*  61 */       HelpPanel.addHelp("Local design by division completed.");
/*     */       
/*  63 */       HelpPanel.addHelp("Start collectUtil ...");
/*  64 */       collectUtil(paramInt1, paramInt2);
/*  65 */       if (paramInt1 == -1) {
/*  66 */         HelpPanel.addHelp("Root: collectUtil completed.");
/*     */       } else
/*  68 */         HelpPanel.addHelp("NonRoot: collectUtil completed.");
/*  69 */       return null;
/*     */     }
/*  71 */     if (paramInt2 == 2) {
/*  72 */       HelpPanel.addHelp("Start distrOptDes ...");
/*  73 */       distrOptDes(paramInt1, paramInt2);
/*  74 */       if (paramInt1 == -1) {
/*  75 */         HelpPanel.addHelp("Root: distrOptDes completed.");
/*  76 */         float f = this.agnet.getOptUtil(0);
/*  77 */         updateJumpState(f);
/*  78 */         HelpPanel.addHelp("# barren nbhd = " + this.barcnt + ", Steps to jump = " + this.jumpcnt);
/*     */         
/*  80 */         if ((this.barcnt == 3) && (this.jumpcnt >= 1) && (this.jumpcnt <= 3)) this.jump = true; else {
/*  81 */           this.jump = false;
/*     */         }
/*     */       } else {
/*  84 */         this.jump = false;
/*  85 */         HelpPanel.addHelp("NonRoot: distrOptDes completed.");
/*     */       }
/*     */       
/*     */ 
/*  89 */       String str2 = new String("" + this.round);
/*  90 */       str3 = UTIL.openWithZero(str2, 3);
/*  91 */       str4 = new String(this.agnet.getPath() + "-" + str3);
/*  92 */       str5 = new String(str4 + ".tmp");
/*  93 */       localObject1 = new String(str4 + ".des");
/*     */       
/*  95 */       this.agnet.saveOptDes(str5);
/*  96 */       localObject2 = new File(str5);
/*  97 */       ((File)localObject2).renameTo(new File((String)localObject1));
/*     */       
/*  99 */       this.round += 1;
/*     */       
/*     */ 
/* 102 */       setCtrlFlag("operation_stage", "designed");
/* 103 */       return null;
/*     */     }
/* 105 */     if (paramInt2 == 3) {
/* 106 */       HelpPanel.addHelp("Start askJumpNext ...");
/* 107 */       askJumpNext(paramInt1, paramInt2);
/* 108 */       if (paramInt1 == -1) {
/* 109 */         HelpPanel.addHelp("Root: askJumpNext completed.");
/*     */       } else
/* 111 */         HelpPanel.addHelp("NonRoot: askJumpNext completed.");
/* 112 */       return null;
/*     */     }
/* 114 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setMsgToChd(int paramInt1, int paramInt2, String paramString)
/*     */   {
/* 123 */     switch (paramInt2) {
/* 124 */     case 1:  setReqCollectUtil(paramInt1); break;
/* 125 */     case 2:  setReqDistrOptDes(paramInt1); break;
/* 126 */     case 3:  setReqAskJumpNext(paramInt1);
/*     */     }
/*     */     
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setMsgToPat(int paramInt1, int paramInt2, String[] paramArrayOfString)
/*     */   {
/* 135 */     switch (paramInt2) {
/* 136 */     case 1:  setUtilMsg(paramInt1); break;
/* 137 */     case 2:  this.msgb[paramInt1].setEmptyOutBody(); break;
/* 138 */     case 3:  this.msgb[paramInt1].setEmptyOutBody();
/*     */     }
/*     */     
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
/*     */ 
/*     */   void updateJumpState(float paramFloat)
/*     */   {
/* 178 */     if ((this.barcnt == 0) && (this.jumpcnt == 0)) {
/* 179 */       if (paramFloat <= 0.1D) this.barcnt = 1;
/*     */     }
/* 181 */     else if ((this.barcnt > 0) && (this.jumpcnt == 0)) {
/* 182 */       if (paramFloat > 0.1D) { this.barcnt = 0;
/*     */       }
/* 184 */       else if (this.barcnt == 1) { this.barcnt = 2;
/*     */       } else {
/* 186 */         this.barcnt = 3;this.jumpcnt = 3;
/*     */       }
/*     */       
/*     */     }
/* 190 */     else if ((this.barcnt == 3) && (this.jumpcnt == 3)) {
/* 191 */       this.jumpcnt = 2;
/*     */     }
/* 193 */     else if ((this.barcnt == 3) && (this.jumpcnt > 0)) {
/* 194 */       if (this.jumpcnt == 2) { this.jumpcnt = 1;
/*     */       } else {
/* 196 */         this.barcnt = 0;this.jumpcnt = 0;
/*     */       }
/*     */     }
/*     */     else {
/* 200 */       HelpPanel.showError("Unexpected jump state transition.");
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean getJumpFlag() {
/* 205 */     return this.jump;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   void setReqAskJumpNext(int paramInt)
/*     */   {
/* 212 */     int i = this.msgb.length;
/* 213 */     for (int j = 0; j < i; j++) {
/* 214 */       if (j != paramInt) {
/* 215 */         this.msgb[j].setOutMsgType(3);
/* 216 */         this.msgb[j].setEmptyOutBody();
/* 217 */         this.msgb[j].setRecved(false);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   void askJumpNext(int paramInt1, int paramInt2)
/*     */   {
/* 224 */     int i = this.msgb.length;
/*     */     
/* 226 */     if (paramInt1 != -1) {
/* 227 */       this.jump = true;
/* 228 */       if (i == 1) { return;
/*     */       }
/*     */     }
/* 231 */     setMsgToChd(paramInt1, paramInt2, null);
/* 232 */     for (int j = 0; j < i; j++) {
/* 233 */       if (j != paramInt1)
/* 234 */         new Envoy(this.msgb[j]);
/*     */     }
/* 236 */     getChdMsg(paramInt1, paramInt2);
/*     */   }
/*     */ }


/* Location:              /home/anali/INAOE/tesis/codigos/topub/exe/ww4.jar!/Network/AgtDesP.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */