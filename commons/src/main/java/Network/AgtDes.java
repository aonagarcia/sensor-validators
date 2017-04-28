/*     */ package Network;
/*     */ 
/*     */ import java.io.PrintStream;
/*     */ 
/*     */ public class AgtDes
/*     */   extends AgtCom
/*     */   implements IntBMgr, IntRoot
/*     */ {
/*   9 */   AgGraphDes agnet = null;
/*     */   
/*     */   public static final int COLLECTUTIL = 1;
/*     */   
/*     */   public static final int DISTROPTDES = 2;
/*     */   
/*     */   public static final int HALT = 3;
/*     */   
/*  17 */   static boolean debug = false;
/*     */   
/*     */   public AgtDes(AgGraphDes paramAgGraphDes, String paramString, Bridge paramBridge) {
/*  20 */     super(paramString, paramBridge);
/*  21 */     this.agnet = paramAgGraphDes;
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
/*     */   public String[] actAll(int paramInt1, int paramInt2)
/*     */   {
/*  49 */     if (paramInt2 == 1) {
/*  50 */       HelpPanel.addHelp("Start collectUtil ...");
/*  51 */       collectUtil(paramInt1, paramInt2);
/*  52 */       if (paramInt1 == -1) {
/*  53 */         HelpPanel.addHelp("Root: collectUtil completed.");
/*     */       } else
/*  55 */         HelpPanel.addHelp("NonRoot: collectUtil completed.");
/*  56 */       return null;
/*     */     }
/*  58 */     if (paramInt2 == 2) {
/*  59 */       HelpPanel.addHelp("Start distrOptDes ...");
/*  60 */       distrOptDes(paramInt1, paramInt2);
/*  61 */       if (paramInt1 == -1) {
/*  62 */         HelpPanel.addHelp("Root: distrOptDes completed.");
/*     */         
/*     */ 
/*  65 */         float[] arrayOfFloat = new float[1];
/*  66 */         arrayOfFloat[0] = this.agnet.getOptUtil(0);
/*  67 */         this.panel.setVector(arrayOfFloat);
/*     */       }
/*     */       else {
/*  70 */         HelpPanel.addHelp("NonRoot: distrOptDes completed.");
/*     */       }
/*     */       
/*  73 */       return null;
/*     */     }
/*  75 */     if (paramInt2 == 3) {
/*  76 */       HelpPanel.addHelp("Get ready to halt ...");
/*  77 */       distrHalt(paramInt1, paramInt2);
/*     */     }
/*     */     
/*  80 */     return null;
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
/*  91 */     switch (paramInt2) {
/*  92 */     case 1:  setReqCollectUtil(paramInt1); break;
/*  93 */     case 2:  setReqDistrOptDes(paramInt1); break;
/*  94 */     case 3:  setReqHalt(paramInt1);
/*     */     }
/*     */     
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   void getChdMsg(int paramInt1, int paramInt2)
/*     */   {
/* 103 */     int i = this.msgb.length;
/* 104 */     int j = this.msgb.length;
/* 105 */     boolean[] arrayOfBoolean = new boolean[i];
/* 106 */     for (int k = 0; k < i; k++) arrayOfBoolean[k] = false;
/* 107 */     if (paramInt1 >= 0) {
/* 108 */       arrayOfBoolean[paramInt1] = true;j--;
/*     */     }
/* 110 */     while (j > 0) {
/* 111 */       for (k = 0; k < i; k++) {
/* 112 */         if ((arrayOfBoolean[k] == 0) && 
/* 113 */           (this.msgb[k].isRecved())) {
/* 114 */           if (paramInt2 == 1) { updateUtil(k);
/*     */           }
/* 116 */           arrayOfBoolean[k] = true;j--;
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setMsgToPat(int paramInt1, int paramInt2, String[] paramArrayOfString)
/*     */   {
/* 127 */     switch (paramInt2) {
/* 128 */     case 1:  setUtilMsg(paramInt1); break;
/* 129 */     case 2:  this.msgb[paramInt1].setEmptyOutBody();
/*     */     }
/*     */     
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   void setReqCollectUtil(int paramInt)
/*     */   {
/* 138 */     for (int i = 0; i < this.msgb.length; i++) {
/* 139 */       if (i != paramInt) {
/* 140 */         this.msgb[i].setOutMsgType(1);
/* 141 */         this.msgb[i].setEmptyOutBody();
/* 142 */         this.msgb[i].setRecved(false);
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
/*     */   void updateUtil(int paramInt)
/*     */   {
/* 156 */     int[] arrayOfInt = this.agnet.getDsepsetLocalIndex(paramInt);
/* 157 */     float[] arrayOfFloat = getInBodyRealList(paramInt);
/* 158 */     if (debug) { UTIL.showList("Pot recved=", arrayOfFloat);
/*     */     }
/* 160 */     this.agnet.absorbDesignPot(arrayOfInt, arrayOfFloat);
/*     */     
/* 162 */     if (debug) {
/* 163 */       System.out.print("\nUpdated local utility>");
/* 164 */       this.agnet.showDesignPot();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   void setUtilMsg(int paramInt)
/*     */   {
/* 172 */     float[] arrayOfFloat = this.agnet.getDsepsetUtil(paramInt);
/* 173 */     setListOutBody(paramInt, arrayOfFloat);
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
/* 186 */     int i = this.msgb.length;
/* 187 */     if ((paramInt1 != -1) && (i == 1)) { return;
/*     */     }
/* 189 */     setMsgToChd(paramInt1, paramInt2, null);
/* 190 */     for (int j = 0; j < i; j++)
/* 191 */       if (j != paramInt1) {
/* 192 */         if (debug) {
/* 193 */           System.out.println("\nMsg to nb agent " + j + ">");
/* 194 */           this.msgb[j].showMsgBox();
/*     */         }
/* 196 */         new Envoy(this.msgb[j]);
/*     */       }
/* 198 */     getChdMsg(paramInt1, paramInt2);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   void setReqDistrOptDes(int paramInt, int[] paramArrayOfInt)
/*     */   {
/* 206 */     this.msgb[paramInt].setOutMsgType(2);
/* 207 */     setListOutBody(paramInt, paramArrayOfInt);
/* 208 */     this.msgb[paramInt].setRecved(false);
/*     */   }
/*     */   
/*     */   void setReqDistrOptDes(int paramInt)
/*     */   {
/* 213 */     int i = this.msgb.length;
/* 214 */     for (int j = 0; j < i; j++) {
/* 215 */       if (j != paramInt)
/*     */       {
/* 217 */         int[] arrayOfInt1 = this.agnet.getDsepsetLocalIndex(j);
/* 218 */         int[] arrayOfInt2 = this.agnet.getOptDesign(arrayOfInt1);
/* 219 */         setReqDistrOptDes(j, arrayOfInt2);
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
/* 241 */     int[] arrayOfInt1 = null;
/* 242 */     int[] arrayOfInt2 = null;
/*     */     
/* 244 */     if (paramInt1 != -1) {
/* 245 */       arrayOfInt1 = this.agnet.getDsepsetLocalIndex(paramInt1);
/* 246 */       arrayOfInt2 = getInBodyIntList(paramInt1);
/* 247 */       this.agnet.setOptDesign(arrayOfInt1, arrayOfInt2);
/*     */     }
/*     */     else {
/* 250 */       this.agnet.setOptDesign(null, null);
/* 251 */       String str = "Opt ex util = " + this.agnet.getOptUtil(0);
/* 252 */       if (debug) System.out.println("\n" + str);
/* 253 */       HelpPanel.addHelp(str);
/*     */     }
/* 255 */     if (debug) this.agnet.showOptDivDesign();
/* 256 */     this.agnet.seeOptDivDesign();
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


/* Location:              /home/anali/INAOE/tesis/codigos/topub/exe/ww4.jar!/Network/AgtDes.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */