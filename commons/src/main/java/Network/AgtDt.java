/*     */ package Network;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AgtDt
/*     */   extends AgtCom
/*     */   implements IntBMgr, IntRoot
/*     */ {
/*  13 */   AgGraphDt agnet = null;
/*     */   
/*     */   public static final int SETDIVTREE = 1;
/*     */   
/*     */   public static final int COLLECTUTIL = 2;
/*     */   
/*     */   public static final int DISTROPTDES = 3;
/*  20 */   static boolean debug = false;
/*     */   
/*     */   public AgtDt(AgGraphDt paramAgGraphDt, String paramString, Bridge paramBridge) {
/*  23 */     super(paramString, paramBridge);
/*  24 */     this.agnet = paramAgGraphDt;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setBearerMgr()
/*     */   {
/*  32 */     this.bmgr = new BearerMgr(this.msgb, this);
/*  33 */     this.bmgr.start();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setRoot(int paramInt)
/*     */   {
/*  41 */     RootCom localRootCom = new RootCom(this, paramInt);
/*  42 */     localRootCom.start();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String[] actAll(int paramInt1, int paramInt2)
/*     */   {
/*  52 */     if (paramInt2 == 1) {
/*  53 */       if (paramInt1 == -1) { HelpPanel.addHelp("Set one div tree ...");
/*     */       } else {
/*  55 */         int i = this.agnet.getLkgCount(paramInt1);
/*  56 */         HelpPanel.addHelp("Set " + i + " div tree(s) ...");
/*     */       }
/*  58 */       setDivTree(paramInt1, paramInt2);
/*     */       
/*  60 */       if (paramInt1 == -1) {
/*  61 */         HelpPanel.addHelp("Root: setDivTree completed.");
/*     */       } else {
/*  63 */         HelpPanel.addHelp("NonRoot: setDivTree completed.");
/*     */       }
/*  65 */       this.agnet.enterEnvSetupToCq();
/*  66 */       this.agnet.designByDiv();
/*  67 */       HelpPanel.addHelp("Local design by division completed.");
/*     */       
/*  69 */       return null;
/*     */     }
/*  71 */     if (paramInt2 == 2) {
/*  72 */       HelpPanel.addHelp("Start collectUtil ...");
/*  73 */       collectUtil(paramInt1, paramInt2);
/*  74 */       if (paramInt1 == -1) {
/*  75 */         HelpPanel.addHelp("Root: collectUtil completed.");
/*     */       } else
/*  77 */         HelpPanel.addHelp("NonRoot: collectUtil completed.");
/*  78 */       return null;
/*     */     }
/*  80 */     if (paramInt2 == 3) {
/*  81 */       HelpPanel.addHelp("Start distrOptDes ...");
/*  82 */       distrOptDes(paramInt1, paramInt2);
/*  83 */       if (paramInt1 == -1) {
/*  84 */         HelpPanel.addHelp("Root: distrOptDes completed.");
/*     */       } else {
/*  86 */         HelpPanel.addHelp("NonRoot: distrOptDes completed.");
/*     */       }
/*  88 */       setCtrlFlag("operation_stage", "designed");
/*  89 */       return null;
/*     */     }
/*     */     
/*  92 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setMsgToChd(int paramInt1, int paramInt2, String paramString)
/*     */   {
/* 103 */     switch (paramInt2) {
/* 104 */     case 1:  setReqSetDivTree(paramInt1); break;
/* 105 */     case 2:  setReqCollectUtil(paramInt1); break;
/* 106 */     case 3:  setReqDistrOptDes(paramInt1);
/*     */     }
/*     */     
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   void getChdMsg(int paramInt1, int paramInt2)
/*     */   {
/* 115 */     int i = this.msgb.length;
/* 116 */     int j = this.msgb.length;
/* 117 */     boolean[] arrayOfBoolean = new boolean[i];
/* 118 */     for (int k = 0; k < i; k++) arrayOfBoolean[k] = false;
/* 119 */     if (paramInt1 >= 0) {
/* 120 */       arrayOfBoolean[paramInt1] = true;j--;
/*     */     }
/* 122 */     while (j > 0) {
/* 123 */       for (k = 0; k < i; k++) {
/* 124 */         if ((arrayOfBoolean[k] == 0) && 
/* 125 */           (this.msgb[k].isRecved())) {
/* 126 */           if (paramInt2 == 2) { addDivisionMate(k);
/*     */           }
/* 128 */           arrayOfBoolean[k] = true;j--;
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setMsgToPat(int paramInt1, int paramInt2, String[] paramArrayOfString)
/*     */   {
/* 140 */     switch (paramInt2) {
/* 141 */     case 2:  setUtilMsg(paramInt1); break;
/* 142 */     case 3:  this.msgb[paramInt1].setEmptyOutBody();
/*     */     }
/*     */     
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   void setReqSetDivTree(int paramInt)
/*     */   {
/* 151 */     for (int i = 0; i < this.msgb.length; i++) {
/* 152 */       if (i != paramInt) {
/* 153 */         this.msgb[i].setOutMsgType(1);
/* 154 */         this.msgb[i].setEmptyOutBody();
/* 155 */         this.msgb[i].setRecved(false);
/*     */       }
/*     */     }
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
/*     */   void setDivTree(int paramInt1, int paramInt2)
/*     */   {
/* 180 */     setMsgToChd(paramInt1, paramInt2, null);
/* 181 */     int i = this.msgb.length;
/* 182 */     for (int j = 0; j < i; j++) {
/* 183 */       if (j != paramInt1) {
/* 184 */         new Envoy(this.msgb[j]);
/*     */       }
/*     */     }
/* 187 */     if (paramInt1 == -1) this.agnet.setDivTree(); else {
/* 188 */       this.agnet.setDivTree(paramInt1);
/*     */     }
/* 190 */     getChdMsg(paramInt1, paramInt2);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   void setReqCollectUtil(int paramInt)
/*     */   {
/* 199 */     for (int i = 0; i < this.msgb.length; i++) {
/* 200 */       if (i != paramInt) {
/* 201 */         this.msgb[i].setOutMsgType(2);
/* 202 */         this.msgb[i].setOutBody(this.agnet.getLkgInfo(i));
/* 203 */         this.msgb[i].setRecved(false);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   void addDivisionMate(int paramInt)
/*     */   {
/* 212 */     float[] arrayOfFloat = getInBodyRealList(paramInt);
/* 213 */     float[][] arrayOfFloat1 = this.agnet.convertWedu(paramInt, arrayOfFloat);
/* 214 */     this.agnet.addDivisionMate(paramInt, arrayOfFloat1);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   void setUtilMsg(int paramInt)
/*     */   {
/* 222 */     String[] arrayOfString = this.msgb[paramInt].getInBody();
/* 223 */     float[][] arrayOfFloat1 = this.agnet.collectDivUtil(paramInt);
/* 224 */     float[][] arrayOfFloat2 = this.agnet.reorderWeud(paramInt, arrayOfFloat1, arrayOfString);
/* 225 */     setListOutBody(paramInt, arrayOfFloat2);
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
/*     */   void collectUtil(int paramInt1, int paramInt2)
/*     */   {
/* 249 */     int i = this.msgb.length;
/* 250 */     if ((paramInt1 == -1) || (i > 1)) {
/* 251 */       setMsgToChd(paramInt1, paramInt2, null);
/* 252 */       for (int j = 0; j < i; j++) {
/* 253 */         if (j != paramInt1)
/* 254 */           new Envoy(this.msgb[j]);
/*     */       }
/* 256 */       getChdMsg(paramInt1, paramInt2);
/*     */     }
/*     */     
/* 259 */     if (paramInt1 == -1) {
/* 260 */       this.agnet.collectDivUtil();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   void setReqDistrOptDes(int paramInt, String[] paramArrayOfString)
/*     */   {
/* 268 */     this.msgb[paramInt].setOutMsgType(3);
/* 269 */     this.msgb[paramInt].setOutBody(paramArrayOfString);
/* 270 */     this.msgb[paramInt].setRecved(false);
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
/*     */   void setReqDistrOptDes(int paramInt)
/*     */   {
/* 283 */     int i = this.msgb.length;
/* 284 */     for (int j = 0; j < i; j++) {
/* 285 */       if (j != paramInt)
/*     */       {
/* 287 */         String[] arrayOfString = this.agnet.getOptLkgDesign(j);
/* 288 */         setReqDistrOptDes(j, arrayOfString);
/*     */       }
/*     */     }
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
/*     */   void distrOptDes(int paramInt1, int paramInt2)
/*     */   {
/* 312 */     int i = this.msgb.length;
/* 313 */     Object localObject; if (paramInt1 == -1) {
/* 314 */       this.agnet.distributeOptDivDesign();
/* 315 */       localObject = "Opt ex util = " + this.agnet.getOptUtil(0);
/* 316 */       HelpPanel.addHelp((String)localObject);
/*     */     }
/*     */     else {
/* 319 */       localObject = this.msgb[paramInt1].getInBody();
/* 320 */       int[][][] arrayOfInt = this.agnet.convertOptLkgDesign(paramInt1, (String[])localObject);
/* 321 */       this.agnet.distributeOptDivDesign(arrayOfInt);
/*     */     }
/* 323 */     this.agnet.seeOptDesign();
/*     */     
/* 325 */     if ((paramInt1 != -1) && (i == 1)) return;
/* 326 */     setMsgToChd(paramInt1, paramInt2, null);
/*     */     
/* 328 */     for (int j = 0; j < i; j++) {
/* 329 */       if (j != paramInt1)
/* 330 */         new Envoy(this.msgb[j]);
/*     */     }
/* 332 */     getChdMsg(paramInt1, paramInt2);
/*     */   }
/*     */ }


/* Location:              /home/anali/INAOE/tesis/codigos/topub/exe/ww4.jar!/Network/AgtDt.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */