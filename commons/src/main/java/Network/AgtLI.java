/*     */ package Network;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AgtLI
/*     */   extends AgtI
/*     */   implements IntBMgr, IntRoot
/*     */ {
/*  15 */   IntAgtLI agnet = null;
/*     */   
/*     */   public static final int COLLECTOBS = 6;
/*     */   
/*     */   public static final int DISTROBS = 7;
/*     */   
/*     */ 
/*     */   public AgtLI(IntAgtLI paramIntAgtLI, String paramString, Bridge paramBridge)
/*     */   {
/*  24 */     super(null, paramString, paramBridge);
/*  25 */     this.agnet = paramIntAgtLI;
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
/*     */   public String[] actAll(int paramInt1, int paramInt2)
/*     */   {
/*  39 */     if (paramInt2 == 6) {
/*  40 */       HelpPanel.addHelp("Start collecting observations ...");
/*  41 */       collectObs(paramInt1, paramInt2);
/*  42 */       if (paramInt1 == -1) {
/*  43 */         HelpPanel.addHelp("Root: observation collected.");
/*     */       } else
/*  45 */         HelpPanel.addHelp("NonRoot: observation\tcollected.");
/*  46 */       return null;
/*     */     }
/*  48 */     if (paramInt2 == 7) {
/*  49 */       HelpPanel.addHelp("Start distributing observations ...");
/*  50 */       distributeObs(paramInt1, paramInt2);
/*  51 */       if (paramInt1 == -1) {
/*  52 */         HelpPanel.addHelp("Root: observation distributed.");
/*     */       } else
/*  54 */         HelpPanel.addHelp("NonRoot: observation\tdistributed.");
/*  55 */       return null;
/*     */     }
/*  57 */     return super.actAll(paramInt1, paramInt2);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setMsgToChd(int paramInt1, int paramInt2, String paramString)
/*     */   {
/*  67 */     switch (paramInt2) {
/*  68 */     case 6:  setReqCollectObs(paramInt1); break;
/*  69 */     case 7:  setReqDistrObs(paramInt1); break;
/*  70 */     default:  super.setMsgToChd(paramInt1, paramInt2, paramString);
/*     */     }
/*     */     
/*     */   }
/*     */   
/*     */ 
/*     */   public void setMsgToPat(int paramInt1, int paramInt2, String[] paramArrayOfString)
/*     */   {
/*  78 */     switch (paramInt2) {
/*  79 */     case 6:  this.msgb[paramInt1].setOutBody(this.agnet.getFullyObserved(paramInt1));
/*  80 */       break;
/*  81 */     case 7:  this.msgb[paramInt1].setEmptyOutBody(); break;
/*  82 */     default:  super.setMsgToPat(paramInt1, paramInt2, paramArrayOfString);
/*     */     }
/*  84 */     this.msgb[paramInt1].setRecved(false);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   void getChdMsg(int paramInt1, int paramInt2)
/*     */   {
/*  92 */     int i = this.msgb.length;
/*  93 */     int j = this.msgb.length;
/*  94 */     boolean[] arrayOfBoolean = new boolean[i];
/*  95 */     for (int k = 0; k < i; k++) arrayOfBoolean[k] = false;
/*  96 */     if (paramInt1 >= 0) {
/*  97 */       arrayOfBoolean[paramInt1] = true;j--;
/*     */     }
/*  99 */     while (j > 0) {
/* 100 */       for (k = 0; k < i; k++) {
/* 101 */         if ((arrayOfBoolean[k] == 0) && 
/* 102 */           (this.msgb[k].isRecved())) {
/* 103 */           if (paramInt2 == 3) { setInLkgBufPoten(k);
/*     */           }
/* 105 */           arrayOfBoolean[k] = true;j--;
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
/* 117 */     switch (paramInt2) {
/* 118 */     case 5:  setInLkgBufPoten(paramInt1);
/* 119 */       return null;
/* 120 */     case 7:  this.agnet.enterFullyObserved(this.msgb[paramInt1].getInBody());
/* 121 */       return null; }
/* 122 */     return super.actOnPatMsg(paramInt1, paramInt2, paramString);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   void setBelief(int paramInt1, int paramInt2)
/*     */   {
/* 131 */     int[] arrayOfInt = null;
/* 132 */     for (int i = 0; i < this.agnet.getNodeCount(); i++) {
/* 133 */       if (this.agnet.isMarked(i)) { arrayOfInt = MATH.addMember(i, arrayOfInt);
/*     */       }
/*     */     }
/* 136 */     this.agnet.setPoten(arrayOfInt);
/* 137 */     this.agnet.clrBufferPoten();
/* 138 */     this.agnet.clrLkgBufPoten();
/*     */     
/*     */ 
/* 141 */     this.agnet.clrLkgPoten();
/*     */     
/* 143 */     setMsgToChd(paramInt1, paramInt2, null);
/* 144 */     for (i = 0; i < this.msgb.length; i++) {
/* 145 */       if (i != paramInt1)
/* 146 */         new Envoy(this.msgb[i]);
/*     */     }
/* 148 */     getChdMsg(paramInt1, paramInt2);
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
/*     */   String[] getLinkageInfo(int paramInt)
/*     */   {
/* 162 */     LJoinTreeM localLJoinTreeM = this.agnet.getLinkageTree2(paramInt);
/* 163 */     int i = localLJoinTreeM.getNodeCount();
/* 164 */     String[] arrayOfString1 = new String[i + 1];
/* 165 */     arrayOfString1[0] = new String(i + "\t#lkg");
/*     */     
/* 167 */     for (int j = 0; j < i; j++) {
/* 168 */       int[] arrayOfInt = localLJoinTreeM.getCqMember(j);
/* 169 */       String[] arrayOfString2 = this.agnet.getLabel(arrayOfInt);
/* 170 */       arrayOfString1[(j + 1)] = new String(arrayOfString2[0]);
/* 171 */       for (int k = 1; k < arrayOfString2.length; k++)
/* 172 */         arrayOfString1[(j + 1)] = new String(arrayOfString1[(j + 1)] + " " + arrayOfString2[k]);
/*     */     }
/* 174 */     return arrayOfString1;
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
/*     */   int getLkgBufPotenLines(int[][] paramArrayOfInt)
/*     */   {
/* 191 */     int i = 1;
/* 192 */     for (int j = 0; j < paramArrayOfInt.length; j++) {
/* 193 */       i += 2;
/* 194 */       if (paramArrayOfInt[j] != null)
/* 195 */         for (int k = 0; k < paramArrayOfInt[j].length; k++) {
/* 196 */           i += 4;
/* 197 */           i += (paramArrayOfInt[j][k] + 4) / 5;
/*     */         }
/*     */     }
/* 200 */     return i;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   LazyBelSet getLkgPot(LJoinTreeM paramLJoinTreeM, String[] paramArrayOfString)
/*     */   {
/* 207 */     int[] arrayOfInt = this.agnet.getIndex(paramArrayOfString);
/* 208 */     int i = paramLJoinTreeM.getCqIndex(arrayOfInt);
/* 209 */     return paramLJoinTreeM.getPoten(i);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   void setLinkageBeliefMsg(int paramInt)
/*     */   {
/* 218 */     setOutLkgBufPoten(paramInt);
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
/*     */   void setOutLkgBufPoten(int paramInt)
/*     */   {
/* 239 */     LJoinTreeM localLJoinTreeM1 = this.agnet.getLinkageTree2(paramInt);
/* 240 */     LJoinTreeM localLJoinTreeM2 = new LJoinTreeM(localLJoinTreeM1);
/* 241 */     localLJoinTreeM2.setLkgPot(this.agnet.getJoinTree1());
/*     */     
/* 243 */     int i = getLkgBufPotenLines(localLJoinTreeM2.getPotenSize());
/* 244 */     this.msgb[paramInt].setDumbOutBody(i);
/*     */     
/* 246 */     int j = 0;int k = 0;
/* 247 */     this.msgb[paramInt].setOutBody(this.msgb[paramInt].getInBody(j++), k++);
/*     */     
/* 249 */     int m = localLJoinTreeM2.getNodeCount();
/* 250 */     for (int n = 0; n < m; n++) {
/* 251 */       this.msgb[paramInt].setOutBody(this.msgb[paramInt].getInBody(j), k++);
/* 252 */       String[] arrayOfString = UTIL.getStringList(this.msgb[paramInt].getInBody(j++));
/*     */       
/* 254 */       LazyBelSet localLazyBelSet = getLkgPot(localLJoinTreeM2, arrayOfString);
/* 255 */       BayesNet localBayesNet = this.agnet.getNet();
/* 256 */       k = setOutLkgBufPoten(localLazyBelSet, localBayesNet, paramInt, k);
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
/*     */   int setOutLkgBufPoten(LazyBelSet paramLazyBelSet, BayesNet paramBayesNet, int paramInt1, int paramInt2)
/*     */   {
/* 274 */     if (paramLazyBelSet.btlist == null) {
/* 275 */       this.msgb[paramInt1].setOutBody(new String("0 #poten"), paramInt2++);
/* 276 */       return paramInt2;
/*     */     }
/* 278 */     int i = paramLazyBelSet.getPotenCount();
/* 279 */     this.msgb[paramInt1].setOutBody(new String(i + " #poten"), paramInt2++);
/*     */     
/* 281 */     for (int j = 0; j < i; j++) {
/* 282 */       int[] arrayOfInt1 = paramLazyBelSet.getDomain(j);
/* 283 */       String[] arrayOfString = paramBayesNet.getLabel(arrayOfInt1);
/* 284 */       String str1 = new String(arrayOfString[0]);
/* 285 */       for (int k = 1; k < arrayOfString.length; k++) str1 = new String(str1 + " " + arrayOfString[k]);
/* 286 */       this.msgb[paramInt1].setOutBody(str1, paramInt2++);
/*     */       
/* 288 */       int[] arrayOfInt2 = paramLazyBelSet.getHead(j);
/* 289 */       if (arrayOfInt2 == null) {
/* 290 */         this.msgb[paramInt1].setOutBody(new String("0 #head"), paramInt2++);
/* 291 */         this.msgb[paramInt1].setOutBody(" ", paramInt2++);
/*     */       }
/*     */       else {
/* 294 */         this.msgb[paramInt1].setOutBody(new String(arrayOfInt2.length + " #head"), paramInt2++);
/* 295 */         localObject = paramBayesNet.getLabel(arrayOfInt2);
/* 296 */         String str2 = new String(localObject[0]);
/* 297 */         for (n = 1; n < localObject.length; n++) str2 = new String(str2 + " " + localObject[n]);
/* 298 */         this.msgb[paramInt1].setOutBody(str2, paramInt2++);
/*     */       }
/*     */       
/* 301 */       Object localObject = paramLazyBelSet.getBelief(j);
/* 302 */       int m = localObject.length;
/* 303 */       this.msgb[paramInt1].setOutBody(new String(m + " #value"), paramInt2++);
/* 304 */       int n = (m + 4) / 5;
/* 305 */       int i1 = 0;
/* 306 */       for (int i2 = 0; i2 < n; i2++) {
/* 307 */         String str3 = new String("");
/* 308 */         for (int i3 = 0; i3 < 5; i3++) {
/* 309 */           str3 = new String(str3 + " " + localObject[(i1++)]);
/* 310 */           if (i1 == m) break;
/*     */         }
/* 312 */         this.msgb[paramInt1].setOutBody(str3, paramInt2++);
/*     */       }
/*     */     }
/* 315 */     return paramInt2;
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
/*     */   ArrayList getInLkgBufPoten(BayesNet paramBayesNet, int paramInt)
/*     */   {
/* 328 */     int i = 0;
/* 329 */     int j = UTIL.getInt(this.msgb[paramInt].getInBody(i++));
/* 330 */     int[][] arrayOfInt = new int[j][];
/* 331 */     LazyBelSet[] arrayOfLazyBelSet = new LazyBelSet[j];
/*     */     
/* 333 */     for (int k = 0; k < j; k++) {
/* 334 */       String[] arrayOfString1 = UTIL.getStringList(this.msgb[paramInt].getInBody(i++));
/* 335 */       arrayOfInt[k] = paramBayesNet.getIndex(arrayOfString1);
/*     */       
/* 337 */       arrayOfLazyBelSet[k] = new LazyBelSet();
/* 338 */       int m = UTIL.getInt(this.msgb[paramInt].getInBody(i++));
/* 339 */       if (m != 0)
/*     */       {
/* 341 */         for (int n = 0; n < m; n++) {
/* 342 */           String str1 = new String(this.msgb[paramInt].getInBody(i++));
/* 343 */           String[] arrayOfString2 = UTIL.getStringList(str1);
/* 344 */           int[] arrayOfInt1 = paramBayesNet.getIndex(arrayOfString2);
/*     */           
/* 346 */           int i1 = UTIL.getInt(this.msgb[paramInt].getInBody(i++));
/* 347 */           int[] arrayOfInt2 = null;
/* 348 */           if (i1 != 0) {
/* 349 */             String str2 = new String(this.msgb[paramInt].getInBody(i));
/* 350 */             String[] arrayOfString3 = UTIL.getStringList(str2);
/* 351 */             arrayOfInt2 = paramBayesNet.getIndex(arrayOfString3);
/*     */           }
/* 353 */           i++;
/*     */           
/* 355 */           int i2 = UTIL.getInt(this.msgb[paramInt].getInBody(i++));
/* 356 */           int i3 = (i2 + 4) / 5;
/* 357 */           String[] arrayOfString4 = new String[i3];
/* 358 */           for (int i4 = 0; i4 < i3; i4++)
/* 359 */             arrayOfString4[i4] = new String(this.msgb[paramInt].getInBody(i++));
/* 360 */           float[] arrayOfFloat = UTIL.getRealList(arrayOfString4);
/* 361 */           LazyBelief localLazyBelief = new LazyBelief(arrayOfInt1, arrayOfInt2, arrayOfFloat);
/* 362 */           arrayOfLazyBelSet[k].addPotential(localLazyBelief);
/*     */         }
/*     */       }
/*     */     }
/* 366 */     ArrayList localArrayList = new ArrayList(2);
/* 367 */     localArrayList.add(0, arrayOfInt);localArrayList.add(1, arrayOfLazyBelSet);
/* 368 */     return localArrayList;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   void setInLkgBufPoten(int paramInt)
/*     */   {
/* 376 */     BayesNet localBayesNet = this.agnet.getNet();
/* 377 */     ArrayList localArrayList = getInLkgBufPoten(localBayesNet, paramInt);
/* 378 */     int[][] arrayOfInt = (int[][])localArrayList.get(0);
/* 379 */     int i = arrayOfInt.length;
/* 380 */     LazyBelSet[] arrayOfLazyBelSet = (LazyBelSet[])localArrayList.get(1);
/*     */     
/* 382 */     LJoinTreeM localLJoinTreeM1 = this.agnet.getJoinTree1();
/* 383 */     LJoinTreeM localLJoinTreeM2 = this.agnet.getLinkageTree2(paramInt);
/*     */     
/* 385 */     for (int j = 0; j < i; j++) {
/* 386 */       if (arrayOfLazyBelSet[j].getPotenCount() != 0) {
/* 387 */         int k = localLJoinTreeM2.getCqIndex(arrayOfInt[j]);
/* 388 */         localLJoinTreeM2.setPoten(k, arrayOfLazyBelSet[j]);
/* 389 */         int m = localLJoinTreeM1.getCqByLabel(localLJoinTreeM2.getLabel(k));
/* 390 */         localLJoinTreeM1.addLkgBufPoten(m, arrayOfLazyBelSet[j]);
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
/*     */   void collectBelief(int paramInt1, int paramInt2)
/*     */   {
/* 407 */     this.agnet.clrBufferPoten();
/* 408 */     this.agnet.clrLkgBufPoten();
/* 409 */     this.agnet.clrLkgPoten();
/*     */     
/* 411 */     int i = this.msgb.length;
/* 412 */     if ((paramInt1 != -1) && (i == 1)) {
/* 413 */       this.agnet.unifyPotential();
/* 414 */       return;
/*     */     }
/*     */     
/* 417 */     setMsgToChd(paramInt1, paramInt2, null);
/* 418 */     for (int j = 0; j < i; j++) {
/* 419 */       if (j != paramInt1)
/* 420 */         new Envoy(this.msgb[j]);
/*     */     }
/* 422 */     getChdMsg(paramInt1, paramInt2);
/* 423 */     this.agnet.unifyPotential1();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   void setReqDistrBelief(MsgBox paramMsgBox, int paramInt)
/*     */   {
/* 430 */     paramMsgBox.setOutMsgType(5);
/* 431 */     this.agnet.addLkgBufPoten(paramInt);
/*     */     
/* 433 */     this.agnet.unifyPotential1();
/* 434 */     setOutLkgBufPoten(paramInt);
/* 435 */     paramMsgBox.setRecved(false);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   void distributeBelief(int paramInt1, int paramInt2)
/*     */   {
/* 445 */     int i = this.msgb.length;
/* 446 */     if (paramInt1 != -1) {
/* 447 */       actOnPatMsg(paramInt1, paramInt2, null);
/*     */     }
/* 449 */     if ((paramInt1 != -1) && (i == 1)) { return;
/*     */     }
/* 451 */     setMsgToChd(paramInt1, 4, null);
/* 452 */     for (int j = 0; j < i; j++) {
/* 453 */       if (j != paramInt1)
/* 454 */         new Envoy(this.msgb[j]);
/*     */     }
/* 456 */     getChdMsg(paramInt1, 4);
/* 457 */     setMsgToChd(paramInt1, paramInt2, null);
/* 458 */     for (j = 0; j < i; j++) {
/* 459 */       if (j != paramInt1)
/* 460 */         new Envoy(this.msgb[j]);
/*     */     }
/* 462 */     getChdMsg(paramInt1, paramInt2);
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
/*     */   void setReqCollectObs(int paramInt)
/*     */   {
/* 478 */     for (int i = 0; i < this.msgb.length; i++) {
/* 479 */       if (i != paramInt) {
/* 480 */         this.msgb[i].setOutMsgType(6);
/* 481 */         this.msgb[i].setEmptyOutBody();
/* 482 */         this.msgb[i].setRecved(false);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   void collectObs(int paramInt1, int paramInt2)
/*     */   {
/* 492 */     int i = this.msgb.length;
/* 493 */     if ((paramInt1 != -1) && (i == 1)) { return;
/*     */     }
/* 495 */     setMsgToChd(paramInt1, paramInt2, null);
/* 496 */     for (int j = 0; j < i; j++) {
/* 497 */       if (j != paramInt1)
/* 498 */         new Envoy(this.msgb[j]);
/*     */     }
/* 500 */     getChdMsg(paramInt1, paramInt2);
/* 501 */     for (j = 0; j < this.msgb.length; j++) {
/* 502 */       if (j != paramInt1) {
/* 503 */         this.agnet.enterFullyObserved(this.msgb[j].getInBody());
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   void setReqDistrObs(int paramInt) {
/* 509 */     for (int i = 0; i < this.msgb.length; i++) {
/* 510 */       if (i != paramInt) {
/* 511 */         this.msgb[i].setOutMsgType(7);
/* 512 */         this.msgb[i].setOutBody(this.agnet.getFullyObserved(i));
/* 513 */         this.msgb[i].setRecved(false);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   void distributeObs(int paramInt1, int paramInt2)
/*     */   {
/* 525 */     int i = this.msgb.length;
/* 526 */     if (paramInt1 != -1) actOnPatMsg(paramInt1, paramInt2, null);
/* 527 */     if ((paramInt1 != -1) && (i == 1)) { return;
/*     */     }
/* 529 */     setMsgToChd(paramInt1, paramInt2, null);
/* 530 */     for (int j = 0; j < i; j++) {
/* 531 */       if (j != paramInt1)
/* 532 */         new Envoy(this.msgb[j]);
/*     */     }
/* 534 */     getChdMsg(paramInt1, paramInt2);
/*     */   }
/*     */ }


/* Location:              /home/anali/INAOE/tesis/codigos/topub/exe/ww4.jar!/Network/AgtLI.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */