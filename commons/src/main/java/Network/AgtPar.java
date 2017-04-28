/*     */ package Network;
/*     */ 
/*     */ import java.io.PrintStream;
/*     */ 
/*     */ public class AgtPar
/*     */   extends AgtCom implements IntBMgr, IntRoot
/*     */ {
/*   8 */   AgGraphPar agnet = null;
/*     */   
/*     */   public static final int COLLECTUTIL = 1;
/*     */   
/*     */   public static final int DISTROPTDES = 2;
/*     */   
/*     */   public static final int HALT = 3;
/*     */   
/*  16 */   static boolean debug = false;
/*     */   
/*     */   public AgtPar(AgGraphPar paramAgGraphPar, String paramString, Bridge paramBridge)
/*     */   {
/*  20 */     super(paramString, paramBridge);
/*  21 */     this.agnet = paramAgGraphPar;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setBearerMgr()
/*     */   {
/*  29 */     this.bmgr = new BearerMgr(this.msgb, this);
/*  30 */     this.bmgr.start();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setRoot(int paramInt)
/*     */   {
/*  38 */     RootCom localRootCom = new RootCom(this, paramInt);
/*  39 */     localRootCom.start();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String[] actAll(int paramInt1, int paramInt2)
/*     */   {
/*  51 */     if (paramInt2 == 1) {
/*  52 */       HelpPanel.addHelp("Start collectUtil ...");
/*  53 */       collectUtil(paramInt1, paramInt2);
/*  54 */       if (paramInt1 == -1) {
/*  55 */         HelpPanel.addHelp("Root: collectUtil completed.");
/*     */       } else
/*  57 */         HelpPanel.addHelp("NonRoot: collectUtil completed.");
/*  58 */       return null;
/*     */     }
/*  60 */     if (paramInt2 == 2) {
/*  61 */       HelpPanel.addHelp("Start distrOptDes ...");
/*  62 */       distrOptDes(paramInt1, paramInt2);
/*  63 */       if (paramInt1 == -1) {
/*  64 */         HelpPanel.addHelp("Root: distrOptDes completed.");
/*     */         
/*     */ 
/*  67 */         float[] arrayOfFloat = new float[1];
/*  68 */         arrayOfFloat[0] = this.agnet.getOptUtil();
/*  69 */         this.panel.setVector(arrayOfFloat);
/*     */       }
/*     */       else {
/*  72 */         HelpPanel.addHelp("NonRoot: distrOptDes completed.");
/*     */       }
/*     */       
/*  75 */       return null;
/*     */     }
/*  77 */     if (paramInt2 == 3) {
/*  78 */       HelpPanel.addHelp("Get ready to halt ...");
/*  79 */       distrHalt(paramInt1, paramInt2);
/*     */     }
/*     */     
/*  82 */     return null;
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
/*  93 */     switch (paramInt2) {
/*  94 */     case 1:  setReqCollectUtil(paramInt1); break;
/*  95 */     case 2:  setReqDistrOptDes(paramInt1); break;
/*  96 */     case 3:  setReqHalt(paramInt1);
/*     */     }
/*     */     
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   void getChdMsg(int paramInt1, int paramInt2)
/*     */   {
/* 105 */     int i = this.msgb.length;
/* 106 */     int j = this.msgb.length;
/* 107 */     boolean[] arrayOfBoolean = new boolean[i];
/* 108 */     for (int k = 0; k < i; k++) arrayOfBoolean[k] = false;
/* 109 */     if (paramInt1 >= 0) {
/* 110 */       arrayOfBoolean[paramInt1] = true;j--;
/*     */     }
/* 112 */     while (j > 0) {
/* 113 */       for (k = 0; k < i; k++) {
/* 114 */         if ((arrayOfBoolean[k] == 0) && 
/* 115 */           (this.msgb[k].isRecved())) {
/* 116 */           if (paramInt2 == 1) { updateUtil(k);
/*     */           }
/* 118 */           arrayOfBoolean[k] = true;j--;
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setMsgToPat(int paramInt1, int paramInt2, String[] paramArrayOfString)
/*     */   {
/* 131 */     switch (paramInt2) {
/* 132 */     case 1:  setUtilMsg(paramInt1); break;
/* 133 */     case 2:  this.msgb[paramInt1].setEmptyOutBody();
/*     */     }
/*     */     
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   void setReqCollectUtil(int paramInt)
/*     */   {
/* 142 */     for (int i = 0; i < this.msgb.length; i++) {
/* 143 */       if (i != paramInt) {
/* 144 */         this.msgb[i].setOutMsgType(1);
/* 145 */         this.msgb[i].setEmptyOutBody();
/* 146 */         this.msgb[i].setRecved(false);
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
/*     */   void updateUtil(int paramInt)
/*     */   {
/* 161 */     int[] arrayOfInt = this.agnet.getDsepsetLocalIndex(paramInt);
/* 162 */     float[] arrayOfFloat = getInBodyRealList(paramInt);
/* 163 */     if (debug) { UTIL.showList("Pot recved=", arrayOfFloat);
/*     */     }
/* 165 */     this.agnet.absorbDesignPot(arrayOfInt, arrayOfFloat);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   void setUtilMsg(int paramInt)
/*     */   {
/* 173 */     float[] arrayOfFloat = this.agnet.getDsepsetUtil(paramInt);
/* 174 */     setListOutBody(paramInt, arrayOfFloat);
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
/*     */   void collectUtil(int paramInt1, int paramInt2)
/*     */   {
/* 187 */     int i = this.msgb.length;
/* 188 */     if ((paramInt1 != -1) && (i == 1)) { return;
/*     */     }
/* 190 */     setMsgToChd(paramInt1, paramInt2, null);
/* 191 */     for (int j = 0; j < i; j++)
/* 192 */       if (j != paramInt1) {
/* 193 */         if (debug) {
/* 194 */           System.out.println("\nMsg to nb agent " + j + ">");
/* 195 */           this.msgb[j].showMsgBox();
/*     */         }
/* 197 */         new Envoy(this.msgb[j]);
/*     */       }
/* 199 */     getChdMsg(paramInt1, paramInt2);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   void setReqDistrOptDes(int paramInt, int[] paramArrayOfInt)
/*     */   {
/* 207 */     this.msgb[paramInt].setOutMsgType(2);
/* 208 */     setListOutBody(paramInt, paramArrayOfInt);
/* 209 */     this.msgb[paramInt].setRecved(false);
/*     */   }
/*     */   
/*     */   void setReqDistrOptDes(int paramInt)
/*     */   {
/* 214 */     int i = this.msgb.length;
/* 215 */     for (int j = 0; j < i; j++) {
/* 216 */       if (j != paramInt)
/*     */       {
/* 218 */         int[] arrayOfInt1 = this.agnet.getDsepsetLocalIndex(j);
/* 219 */         int[] arrayOfInt2 = this.agnet.getOptDec(arrayOfInt1);
/* 220 */         setReqDistrOptDes(j, arrayOfInt2);
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
/*     */   void distrOptDes(int paramInt1, int paramInt2)
/*     */   {
/* 242 */     int[] arrayOfInt1 = null;
/* 243 */     int[] arrayOfInt2 = null;
/*     */     
/* 245 */     if (paramInt1 != -1) {
/* 246 */       arrayOfInt1 = this.agnet.getDsepsetLocalIndex(paramInt1);
/* 247 */       arrayOfInt2 = getInBodyIntList(paramInt1);
/* 248 */       this.agnet.getOptDec(arrayOfInt1, arrayOfInt2);
/*     */     }
/*     */     else {
/* 251 */       this.agnet.getOptDec();
/* 252 */       String str = "Opt exp util = " + this.agnet.getOptUtil();
/* 253 */       if (debug) System.out.println("\n" + str);
/* 254 */       HelpPanel.addHelp(str);
/*     */     }
/* 256 */     this.agnet.seeOptDec();
/*     */     
/* 258 */     setMsgToChd(paramInt1, paramInt2, null);
/*     */     
/* 260 */     int i = this.msgb.length;
/* 261 */     for (int j = 0; j < i; j++) {
/* 262 */       if (j != paramInt1)
/* 263 */         new Envoy(this.msgb[j]);
/*     */     }
/* 265 */     getChdMsg(paramInt1, paramInt2);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   void distrHalt(int paramInt1, int paramInt2)
/*     */   {
/* 272 */     setMsgToChd(paramInt1, paramInt2, null);
/* 273 */     int i = this.msgb.length;
/* 274 */     for (int j = 0; j < i; j++) {
/* 275 */       if (j != paramInt1)
/* 276 */         new Envoy(this.msgb[j]);
/*     */     }
/* 278 */     getChdMsg(paramInt1, paramInt2);
/* 279 */     BearerMgr.halt = true;
/*     */   }
/*     */   
/*     */   void setReqHalt(int paramInt)
/*     */   {
/* 284 */     for (int i = 0; i < this.msgb.length; i++) {
/* 285 */       if (i != paramInt) {
/* 286 */         this.msgb[i].setOutMsgType(3);
/* 287 */         this.msgb[i].setEmptyOutBody();
/* 288 */         this.msgb[i].setRecved(false);
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              /home/anali/INAOE/tesis/codigos/topub/exe/ww4.jar!/Network/AgtPar.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */