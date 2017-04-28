/*     */ package Network;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AgtI
/*     */   extends AgtCom
/*     */   implements IntBMgr, IntRoot
/*     */ {
/*  11 */   IntAgtI agnet = null;
/*     */   
/*     */   public static final int SETPRIORBEF = 1;
/*     */   
/*     */   public static final int CLOSESETPRIOR = 2;
/*     */   
/*     */   public static final int COLLECTBELIEF = 3;
/*     */   
/*     */   static final int ASKLINKAGEINFO = 4;
/*     */   public static final int DISTRBELIEF = 5;
/*     */   static final int SYSTEM = -1;
/*     */   
/*     */   public AgtI(IntAgtI paramIntAgtI, String paramString, Bridge paramBridge)
/*     */   {
/*  25 */     super(paramString, paramBridge);
/*  26 */     this.agnet = paramIntAgtI;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setBearerMgr()
/*     */   {
/*  34 */     this.bmgr = new BearerMgr(this.msgb, this);
/*  35 */     this.bmgr.start();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setRoot(int paramInt)
/*     */   {
/*  43 */     RootCom localRootCom = new RootCom(this, paramInt);
/*  44 */     localRootCom.start();
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
/*     */   public String[] actAll(int paramInt1, int paramInt2)
/*     */   {
/*  61 */     if (paramInt2 == 1) {
/*  62 */       HelpPanel.addHelp("Start defining belief tables ...");
/*  63 */       setBelief(paramInt1, paramInt2);
/*  64 */       if (paramInt1 == -1) {
/*  65 */         HelpPanel.addHelp("Root: belief\ttable defined.");
/*     */       } else
/*  67 */         HelpPanel.addHelp("NonRoot: belief table defined.");
/*  68 */       return null;
/*     */     }
/*  70 */     if (paramInt2 == 2) {
/*  71 */       closeSetPrior(paramInt1, paramInt2);
/*  72 */       if (paramInt1 == -1) {
/*  73 */         HelpPanel.addHelp("Root: Belief\tinitialized.");
/*     */       } else
/*  75 */         HelpPanel.addHelp("NonRoot: Belief initialized.");
/*  76 */       setCtrlFlag("operation_stage", "set_priors");
/*  77 */       return null;
/*     */     }
/*     */     
/*     */ 
/*  81 */     if (paramInt2 == 3) {
/*  82 */       HelpPanel.addHelp("Start collectBelief ...");
/*  83 */       SpaceTime.showUsedMemory("In actAll()");
/*  84 */       collectBelief(paramInt1, paramInt2);
/*  85 */       if (paramInt1 == -1) {
/*  86 */         HelpPanel.addHelp("Root: collectBelief completed.");
/*     */       } else
/*  88 */         HelpPanel.addHelp("NonRoot: collectBelief completed.");
/*  89 */       SpaceTime.showUsedMemory("After collectBelief");
/*  90 */       return null;
/*     */     }
/*  92 */     if (paramInt2 == 5) {
/*  93 */       HelpPanel.addHelp("Start distributeBelief\t...");
/*  94 */       distributeBelief(paramInt1, paramInt2);
/*  95 */       if (paramInt1 == -1) {
/*  96 */         HelpPanel.addHelp("Root: distributeBelief completed.");
/*     */       } else
/*  98 */         HelpPanel.addHelp("NonRoot: distributeBelief completed.");
/*  99 */       return null;
/*     */     }
/* 101 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setMsgToChd(int paramInt1, int paramInt2, String paramString)
/*     */   {
/* 110 */     switch (paramInt2) {
/* 111 */     case 1:  setReqSetBelief(paramInt1); break;
/* 112 */     case 2:  setReqCloseSetPrior(paramInt1); break;
/*     */     case 3: 
/* 114 */       setReqCollectBef(paramInt1); break;
/* 115 */     case 4:  setReqLinkageInfo(paramInt1); break;
/* 116 */     case 5:  setReqDistrBelief(paramInt1);
/*     */     }
/*     */     
/*     */   }
/*     */   
/*     */   public void setMsgToPat(int paramInt1, int paramInt2, String[] paramArrayOfString)
/*     */   {
/* 123 */     switch (paramInt2) {
/* 124 */     case 1:  this.msgb[paramInt1].setEmptyOutBody(); break;
/* 125 */     case 2:  this.msgb[paramInt1].setEmptyOutBody(); break;
/*     */     case 3: 
/* 127 */       setLinkageBeliefMsg(paramInt1); break;
/* 128 */     case 4:  setReplyLinkageInfo(paramInt1); break;
/* 129 */     case 5:  this.msgb[paramInt1].setEmptyOutBody();
/*     */     }
/* 131 */     this.msgb[paramInt1].setRecved(false);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   void getChdMsg(int paramInt1, int paramInt2)
/*     */   {
/* 139 */     int i = this.msgb.length;
/* 140 */     int j = this.msgb.length;
/* 141 */     boolean[] arrayOfBoolean = new boolean[i];
/* 142 */     for (int k = 0; k < i; k++) arrayOfBoolean[k] = false;
/* 143 */     if (paramInt1 >= 0) {
/* 144 */       arrayOfBoolean[paramInt1] = true;j--;
/*     */     }
/* 146 */     while (j > 0) {
/* 147 */       for (k = 0; k < i; k++) {
/* 148 */         if ((arrayOfBoolean[k] == 0) && 
/* 149 */           (this.msgb[k].isRecved())) {
/* 150 */           if (paramInt2 == 3) { updateBelief(k);
/*     */           }
/* 152 */           arrayOfBoolean[k] = true;j--;
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String[] actOnPatMsg(int paramInt1, int paramInt2, String paramString)
/*     */   {
/* 164 */     switch (paramInt2) {
/* 165 */     case 5:  updateBelief(paramInt1);return null;
/*     */     }
/* 167 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   void setReqSetBelief(int paramInt)
/*     */   {
/* 174 */     for (int i = 0; i < this.msgb.length; i++) {
/* 175 */       if (i != paramInt) {
/* 176 */         this.msgb[i].setOutMsgType(1);
/* 177 */         this.msgb[i].setEmptyOutBody();
/* 178 */         this.msgb[i].setRecved(false);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   void setBelief(int paramInt1, int paramInt2) {
/* 184 */     int[] arrayOfInt = null;
/* 185 */     for (int i = 0; i < this.agnet.getNodeCount(); i++) {
/* 186 */       if (this.agnet.isMarked(i)) arrayOfInt = MATH.addMember(i, arrayOfInt);
/*     */     }
/* 188 */     this.agnet.setBelief(arrayOfInt);
/*     */     
/* 190 */     this.agnet.unifyBelief();
/*     */     
/* 192 */     i = this.msgb.length;
/* 193 */     for (int j = 0; j < i; j++) {
/* 194 */       this.agnet.setDumbLinkageBelief(j);
/*     */     }
/* 196 */     setMsgToChd(paramInt1, paramInt2, null);
/* 197 */     for (j = 0; j < i; j++) {
/* 198 */       if (j != paramInt1)
/* 199 */         new Envoy(this.msgb[j]);
/*     */     }
/* 201 */     getChdMsg(paramInt1, paramInt2);
/*     */   }
/*     */   
/*     */   void setReqCloseSetPrior(int paramInt)
/*     */   {
/* 206 */     for (int i = 0; i < this.msgb.length; i++) {
/* 207 */       if (i != paramInt) {
/* 208 */         this.msgb[i].setOutMsgType(2);
/* 209 */         this.msgb[i].setEmptyOutBody();
/* 210 */         this.msgb[i].setRecved(false);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   void closeSetPrior(int paramInt1, int paramInt2)
/*     */   {
/* 222 */     int i = this.msgb.length;
/* 223 */     if ((paramInt1 != -1) && (i == 1)) { return;
/*     */     }
/* 225 */     setMsgToChd(paramInt1, paramInt2, null);
/* 226 */     for (int j = 0; j < i; j++) {
/* 227 */       if (j != paramInt1)
/* 228 */         new Envoy(this.msgb[j]);
/*     */     }
/* 230 */     getChdMsg(paramInt1, paramInt2);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   void setReqCollectBef(int paramInt)
/*     */   {
/* 237 */     for (int i = 0; i < this.msgb.length; i++) {
/* 238 */       if (i != paramInt) {
/* 239 */         this.msgb[i].setOutMsgType(3);
/* 240 */         this.msgb[i].setOutBody(getLinkageInfo(i));
/* 241 */         this.msgb[i].setRecved(false);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   String[] getLinkageInfo(int paramInt)
/*     */   {
/* 252 */     JoinTreeM localJoinTreeM = this.agnet.getLinkageTree1(paramInt);
/* 253 */     int i = localJoinTreeM.getNodeCount();
/* 254 */     String[] arrayOfString1 = new String[i + 1];
/* 255 */     arrayOfString1[0] = new String(i + "\t#lkg");
/*     */     
/* 257 */     for (int j = 0; j < i; j++) {
/* 258 */       int[] arrayOfInt = localJoinTreeM.getCqMember(j);
/* 259 */       String[] arrayOfString2 = this.agnet.getLabel(arrayOfInt);
/* 260 */       arrayOfString1[(j + 1)] = new String(arrayOfString2[0]);
/* 261 */       for (int k = 1; k < arrayOfString2.length; k++)
/* 262 */         arrayOfString1[(j + 1)] = new String(arrayOfString1[(j + 1)] + " " + arrayOfString2[k]);
/*     */     }
/* 264 */     return arrayOfString1;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   void updateBelief(int paramInt)
/*     */   {
/* 276 */     JoinTreeM localJoinTreeM = this.agnet.getLinkageTree1(paramInt);
/* 277 */     int i = localJoinTreeM.getNodeCount();
/*     */     
/* 279 */     int j = 1;
/* 280 */     for (int k = 0; k < i; k++) {
/* 281 */       int m = UTIL.getInt(this.msgb[paramInt].getInBody(j++));
/* 282 */       int n = (m + 4) / 5;
/* 283 */       String[] arrayOfString = new String[n];
/* 284 */       for (int i1 = 0; i1 < n; i1++)
/* 285 */         arrayOfString[i1] = new String(this.msgb[paramInt].getInBody(j++));
/* 286 */       float[] arrayOfFloat1 = UTIL.getRealList(arrayOfString);
/*     */       
/* 288 */       int[] arrayOfInt = localJoinTreeM.getCqMember(k);
/* 289 */       float[] arrayOfFloat2 = localJoinTreeM.getBelief(k);
/* 290 */       int i2 = this.agnet.getCqByLabel(localJoinTreeM.getLabel(k));
/* 291 */       this.agnet.absorbThroughLinkage(i2, arrayOfInt, arrayOfFloat2, arrayOfFloat1);
/* 292 */       localJoinTreeM.setBelief(k, arrayOfFloat1);
/*     */     }
/* 294 */     this.agnet.unifyBelief();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   float[] getLinkageBelief(int paramInt, String[] paramArrayOfString)
/*     */   {
/* 304 */     int[] arrayOfInt1 = this.agnet.getIndex(paramArrayOfString);
/* 305 */     int[] arrayOfInt2 = MATH.sort(arrayOfInt1);
/*     */     
/* 307 */     JoinTreeM localJoinTreeM = this.agnet.getLinkageTree1(paramInt);
/* 308 */     int i = localJoinTreeM.getCqIndex(arrayOfInt1);
/* 309 */     int[] arrayOfInt3 = localJoinTreeM.getStateCount(i);
/* 310 */     float[] arrayOfFloat = localJoinTreeM.getBelief(i);
/* 311 */     return MATH.reorderBelief(arrayOfInt2, arrayOfInt3, arrayOfFloat, arrayOfInt1);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   void setLinkageBeliefMsg(int paramInt)
/*     */   {
/* 322 */     JoinTreeM localJoinTreeM = this.agnet.getLinkageTree1(paramInt);
/* 323 */     int i = localJoinTreeM.getNodeCount();
/* 324 */     localJoinTreeM.setLinkageBelief(this.agnet.getJoinTree());
/*     */     
/* 326 */     int j = getLkgBeliefLines(localJoinTreeM.getCqSpaceSize());
/* 327 */     this.msgb[paramInt].setDumbOutBody(j);
/* 328 */     int k = 0;int m = 0;
/* 329 */     this.msgb[paramInt].setOutBody(this.msgb[paramInt].getInBody(k++), m++);
/*     */     
/* 331 */     for (int n = 0; n < i; n++) {
/* 332 */       String[] arrayOfString = UTIL.getStringList(this.msgb[paramInt].getInBody(k++));
/* 333 */       float[] arrayOfFloat = getLinkageBelief(paramInt, arrayOfString);
/* 334 */       int i1 = arrayOfFloat.length;
/* 335 */       this.msgb[paramInt].setOutBody(new String(i1 + " #probs"), m++);
/*     */       
/* 337 */       int i2 = (i1 + 4) / 5;
/* 338 */       int i3 = 0;
/* 339 */       for (int i4 = 0; i4 < i2; i4++) {
/* 340 */         String str = new String("");
/* 341 */         for (int i5 = 0; i5 < 5; i5++) {
/* 342 */           str = new String(str + " " + arrayOfFloat[(i3++)]);
/* 343 */           if (i3 == i1) break;
/*     */         }
/* 345 */         this.msgb[paramInt].setOutBody(str, m++);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   int getLkgBeliefLines(int[] paramArrayOfInt)
/*     */   {
/* 356 */     int i = 1;
/* 357 */     for (int j = 0; j < paramArrayOfInt.length; j++) {
/* 358 */       i++;
/* 359 */       i += (paramArrayOfInt[j] + 4) / 5;
/*     */     }
/* 361 */     return i;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   void collectBelief(int paramInt1, int paramInt2)
/*     */   {
/* 373 */     int i = this.msgb.length;
/* 374 */     if ((paramInt1 != -1) && (i == 1)) { return;
/*     */     }
/* 376 */     setMsgToChd(paramInt1, paramInt2, null);
/* 377 */     for (int j = 0; j < i; j++) {
/* 378 */       if (j != paramInt1)
/* 379 */         new Envoy(this.msgb[j]);
/*     */     }
/* 381 */     getChdMsg(paramInt1, paramInt2);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   void setReqLinkageInfo(int paramInt)
/*     */   {
/* 388 */     for (int i = 0; i < this.msgb.length; i++) {
/* 389 */       if (i != paramInt) {
/* 390 */         this.msgb[i].setOutMsgType(4);
/* 391 */         this.msgb[i].setEmptyOutBody();
/* 392 */         this.msgb[i].setRecved(false);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   void setReplyLinkageInfo(int paramInt) {
/* 398 */     this.msgb[paramInt].setOutMsgType(4);
/* 399 */     this.msgb[paramInt].setOutBody(getLinkageInfo(paramInt));
/* 400 */     this.msgb[paramInt].setRecved(false);
/*     */   }
/*     */   
/*     */   void setReqDistrBelief(MsgBox paramMsgBox, int paramInt)
/*     */   {
/* 405 */     paramMsgBox.setOutMsgType(5);
/* 406 */     setLinkageBeliefMsg(paramInt);
/* 407 */     paramMsgBox.setRecved(false);
/*     */   }
/*     */   
/*     */ 
/*     */   void setReqDistrBelief(int paramInt)
/*     */   {
/* 413 */     int i = this.msgb.length;int j = i;
/* 414 */     boolean[] arrayOfBoolean = new boolean[i];
/* 415 */     for (int k = 0; k < i; k++) arrayOfBoolean[k] = false;
/* 416 */     if (paramInt >= 0) {
/* 417 */       arrayOfBoolean[paramInt] = true;j--;
/*     */     }
/* 419 */     while (j > 0) {
/* 420 */       for (k = 0; k < i; k++) {
/* 421 */         if ((arrayOfBoolean[k] == 0) && 
/* 422 */           (this.msgb[k].isRecved())) {
/* 423 */           setReqDistrBelief(this.msgb[k], k);
/* 424 */           arrayOfBoolean[k] = true;j--;
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
/*     */ 
/*     */   void distributeBelief(int paramInt1, int paramInt2)
/*     */   {
/* 438 */     if (paramInt1 != -1) { actOnPatMsg(paramInt1, paramInt2, null);
/*     */     }
/* 440 */     int i = this.msgb.length;
/* 441 */     if ((paramInt1 != -1) && (i == 1)) { return;
/*     */     }
/* 443 */     setMsgToChd(paramInt1, 4, null);
/* 444 */     for (int j = 0; j < i; j++) {
/* 445 */       if (j != paramInt1)
/* 446 */         new Envoy(this.msgb[j]);
/*     */     }
/* 448 */     getChdMsg(paramInt1, 4);
/* 449 */     setMsgToChd(paramInt1, paramInt2, null);
/* 450 */     for (j = 0; j < i; j++) {
/* 451 */       if (j != paramInt1)
/* 452 */         new Envoy(this.msgb[j]);
/*     */     }
/* 454 */     getChdMsg(paramInt1, paramInt2);
/*     */   }
/*     */ }


/* Location:              /home/anali/INAOE/tesis/codigos/topub/exe/ww4.jar!/Network/AgtI.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */