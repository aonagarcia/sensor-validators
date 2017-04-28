/*     */ package Network;
/*     */ 
/*     */ import java.awt.Point;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AgtV
/*     */   extends AgtCom
/*     */   implements IntBMgr, IntRoot
/*     */ {
/*  11 */   IntAgtV agnet = null;
/*     */   
/*     */   static final int COLLECTPRVPINF = 0;
/*     */   
/*     */   static final int COLLECTPUBPINF = 1;
/*     */   
/*     */   static final int FDNONDSEPBYHUB = 2;
/*     */   
/*     */   public static final int FDNONDSEPNODE = 3;
/*     */   
/*     */   public static final int PREPROCESS = 4;
/*     */   public static final int MARKNODE = 5;
/*     */   static final int COLLECTFMINF = 6;
/*     */   static final int DISTRIBUTEMARK = 7;
/*     */   public static final int MARKEDALL = 8;
/*     */   static final int SYSTEM = -1;
/*     */   
/*     */   public AgtV(IntAgtV paramIntAgtV, String paramString, Bridge paramBridge)
/*     */   {
/*  30 */     super(paramString, paramBridge);
/*  31 */     this.agnet = paramIntAgtV;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setBearerMgr()
/*     */   {
/*  39 */     this.bmgr = new BearerMgr(this.msgb, this);
/*  40 */     this.bmgr.start();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setRoot(int paramInt)
/*     */   {
/*  48 */     RootCom localRootCom = new RootCom(this, paramInt);
/*  49 */     localRootCom.start();
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
/*     */   public String[] actAll(int paramInt1, int paramInt2)
/*     */   {
/*  62 */     if (paramInt2 == 3) {
/*  63 */       boolean[] arrayOfBoolean = new boolean[1];
/*  64 */       arrayOfBoolean[0] = (!findNonDsepnode(paramInt1, paramInt2) ? 1 : false);
/*  65 */       if (paramInt1 == -1) {
/*  66 */         this.panel.setVector(arrayOfBoolean);
/*  67 */         if (arrayOfBoolean[0] != 0) HelpPanel.addHelp("Root:\td-sepset verified."); else
/*  68 */           HelpPanel.addHelp("Root: Err: non-d-sepset\tfound!");
/*     */       } else {
/*  70 */         HelpPanel.addHelp("NonRoot: d-sepset\ttest done."); }
/*  71 */       return UTIL.boolToStrAry(arrayOfBoolean[0] == 0); }
/*     */     int i;
/*  73 */     if (paramInt2 == 0) {
/*  74 */       i = collectPriPaInfo(paramInt1, paramInt2, null);
/*  75 */       return UTIL.intToStrAry(i);
/*     */     }
/*  77 */     if (paramInt2 == 1) {
/*  78 */       i = collectPubPaInfo(paramInt1, paramInt2, null);
/*  79 */       return UTIL.intToStrAry(i); }
/*     */     boolean bool;
/*  81 */     if (paramInt2 == 2) {
/*  82 */       bool = findNonDsepnodeByHub(paramInt1, paramInt2, null);
/*  83 */       return UTIL.boolToStrAry(bool);
/*     */     }
/*     */     
/*     */ 
/*  87 */     if (paramInt2 == 4) {
/*  88 */       preProcess(paramInt1, paramInt2);
/*  89 */       if (paramInt1 == -1)
/*  90 */         HelpPanel.addHelp("Root: preprocess done.\nMarkNode: "); else
/*  91 */         HelpPanel.addHelp("NonRoot: preprocess done.\nMarkNode: ");
/*  92 */       return UTIL.boolToStrAry(true);
/*     */     }
/*  94 */     if (paramInt2 == 5)
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 103 */       if ((paramInt1 != -1) && (this.msgb.length == 1))
/* 104 */         return UTIL.boolToStrAry(false);
/* 105 */       if (paramInt1 == -1) {
/*     */         do {
/* 107 */           bool = markNode(paramInt1, paramInt2);
/* 108 */         } while (bool);
/* 109 */         return UTIL.boolToStrAry(false);
/*     */       }
/*     */       
/* 112 */       bool = markNode(paramInt1, paramInt2);
/* 113 */       return UTIL.boolToStrAry(bool);
/*     */     }
/*     */     Object localObject;
/* 116 */     if (paramInt2 == 6) {
/* 117 */       localObject = this.msgb[paramInt1].getInBody()[0];
/* 118 */       return UTIL.pointToStrAry(collectFamilyInfo(paramInt1, paramInt2, (String)localObject));
/*     */     }
/* 120 */     if (paramInt2 == 7) {
/* 121 */       localObject = this.msgb[paramInt1].getInBody()[0];
/* 122 */       distributeMark(paramInt1, paramInt2, (String)localObject);
/* 123 */       return UTIL.boolToStrAry(true);
/*     */     }
/* 125 */     if (paramInt2 == 8) {
/* 126 */       localObject = new boolean[2];
/* 127 */       localObject[1] = markedAll(paramInt1, paramInt2);
/* 128 */       if (paramInt1 == -1) this.panel.setVector((boolean[])localObject); else
/* 129 */         HelpPanel.addHelp("NonRoot: MarkedAll done.");
/* 130 */       return UTIL.boolToStrAry(localObject[1]);
/*     */     }
/* 132 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setMsgToChd(int paramInt1, int paramInt2, String paramString)
/*     */   {
/* 141 */     switch (paramInt2) {
/* 142 */     case 3:  setReqFindNonDSep(paramInt1); break;
/* 143 */     case 0:  setReqCollectPri(paramInt1, paramString); break;
/* 144 */     case 1:  setReqCollectPubPa(paramInt1, paramString); break;
/* 145 */     case 2:  setReqFindNonDSByHub(paramInt1, paramString); break;
/*     */     case 4: 
/* 147 */       setReqPreProc(paramInt1); break;
/* 148 */     case 5:  setReqMarkNode(paramInt1); break;
/* 149 */     case 6:  setReqCollectFInfo(paramInt1, paramString); break;
/* 150 */     case 7:  setReqDistrMark(paramInt1, paramString); break;
/* 151 */     case 8:  setReqMarkedAll(paramInt1);
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
/*     */   public void setMsgToPat(int paramInt1, int paramInt2, String[] paramArrayOfString)
/*     */   {
/* 167 */     switch (paramInt2) {
/* 168 */     case 3:  this.msgb[paramInt1].setOutBody(paramArrayOfString); break;
/* 169 */     case 0:  this.msgb[paramInt1].setOutBody(paramArrayOfString); break;
/* 170 */     case 1:  this.msgb[paramInt1].setOutBody(paramArrayOfString); break;
/* 171 */     case 2:  this.msgb[paramInt1].setOutBody(paramArrayOfString); break;
/*     */     case 6: 
/* 173 */       this.msgb[paramInt1].setOutBody(paramArrayOfString); break;
/* 174 */     case 5:  this.msgb[paramInt1].setOutBody(paramArrayOfString); break;
/* 175 */     case 4:  this.msgb[paramInt1].setOutBody(paramArrayOfString); break;
/* 176 */     case 7:  this.msgb[paramInt1].setEmptyOutBody(); break;
/* 177 */     case 8:  this.msgb[paramInt1].setOutBody(paramArrayOfString);
/*     */     }
/*     */     
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   void getChdMsg(int paramInt1, int paramInt2)
/*     */   {
/* 188 */     int i = this.msgb.length;
/* 189 */     int j = this.msgb.length;
/* 190 */     boolean[] arrayOfBoolean = new boolean[i];
/* 191 */     for (int k = 0; k < i; k++) arrayOfBoolean[k] = false;
/* 192 */     if (paramInt1 >= 0) {
/* 193 */       arrayOfBoolean[paramInt1] = true;j--;
/*     */     }
/* 195 */     while (j > 0) {
/* 196 */       for (k = 0; k < i; k++) {
/* 197 */         if ((arrayOfBoolean[k] == 0) && 
/* 198 */           (this.msgb[k].isRecved())) {
/* 199 */           arrayOfBoolean[k] = true;j--;
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
/* 211 */     switch (paramInt2) {
/* 212 */     case 0:  return UTIL.intToStrAry(getPriPaInfo(paramInt1, paramString));
/* 213 */     case 2:  return UTIL.intToStrAry(getPriPaInfo(paramInt1, paramString));
/*     */     case 4: 
/* 215 */       preProcLoc();return null;
/* 216 */     case 6:  return UTIL.pointToStrAry(getFamilyInfo(paramInt1, paramString));
/* 217 */     case 7:  distrMarkLocally(paramInt1, paramString);return null;
/*     */     }
/* 219 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */   String[] actOnCldMsg(int paramInt1, int paramInt2, Point paramPoint)
/*     */   {
/* 225 */     if (paramInt2 == 6) {
/* 226 */       Point localPoint = sumupFmlyInfo(paramInt1, paramPoint);
/* 227 */       return UTIL.pointToStrAry(localPoint);
/*     */     }
/* 229 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   void setReqCollectPri(int paramInt, String paramString)
/*     */   {
/* 236 */     int i = this.agnet.getIndex(paramString);
/* 237 */     for (int j = 0; j < this.msgb.length; j++) {
/* 238 */       if (j != paramInt) {
/* 239 */         if (MATH.member(i, this.agnet.getDsepsetLocalIndex(j))) {
/* 240 */           this.msgb[j].setOutMsgType(0);
/* 241 */           this.msgb[j].setDumbOutBody(1);
/* 242 */           this.msgb[j].setOutBody(paramString, 0);
/* 243 */           this.msgb[j].setRecved(false);
/*     */         } else {
/* 245 */           this.msgb[j].setRecved(true);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   int getPriPaInfo(int paramInt, String paramString) {
/* 252 */     int i = 0;
/* 253 */     int[] arrayOfInt = this.agnet.getDsepsetUnion();
/* 254 */     String[] arrayOfString = this.agnet.getLabel(arrayOfInt);
/* 255 */     if (this.agnet.hasOutParent(paramString, arrayOfString)) i = 1;
/* 256 */     return i;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   int collectPriPaInfo(int paramInt1, int paramInt2, String paramString)
/*     */   {
/*     */     String str;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 273 */     if (paramInt1 == -1) str = new String(paramString); else {
/* 274 */       str = this.msgb[paramInt1].getInBody(0);
/*     */     }
/* 276 */     int i = getPriPaInfo(paramInt1, str);
/* 277 */     int j = this.agnet.getIndex(str);
/*     */     
/* 279 */     setMsgToChd(paramInt1, 0, str);
/* 280 */     Envoy[] arrayOfEnvoy = new Envoy[this.msgb.length];
/* 281 */     for (int k = 0; k < this.msgb.length; k++) {
/* 282 */       if ((k != paramInt1) && 
/* 283 */         (MATH.member(j, this.agnet.getDsepsetLocalIndex(k))))
/*     */       {
/* 285 */         arrayOfEnvoy[k] = new Envoy(this.msgb[k]); }
/*     */     }
/* 287 */     getChdMsg(paramInt1, paramInt2);
/* 288 */     for (k = 0; k < this.msgb.length; k++) {
/* 289 */       if ((k != paramInt1) && 
/* 290 */         (MATH.member(j, this.agnet.getDsepsetLocalIndex(k)))) {
/* 291 */         int m = Integer.parseInt(this.msgb[k].getInBody(0));
/* 292 */         i += m;
/*     */       }
/*     */     }
/* 295 */     if (i > 1)
/* 296 */       HelpPanel.addHelp("Two or\tmore agents have private parents of " + str);
/* 297 */     return i;
/*     */   }
/*     */   
/*     */ 
/*     */   void setReqCollectPubPa(int paramInt, String paramString)
/*     */   {
/* 303 */     int i = this.agnet.getIndex(paramString);
/* 304 */     for (int j = 0; j < this.msgb.length; j++) {
/* 305 */       if (j != paramInt) {
/* 306 */         if (MATH.member(i, this.agnet.getDsepsetLocalIndex(j))) {
/* 307 */           this.msgb[j].setOutMsgType(1);
/* 308 */           this.msgb[j].setDumbOutBody(1);
/* 309 */           this.msgb[j].setOutBody(paramString, 0);
/* 310 */           this.msgb[j].setRecved(false);
/*     */         } else {
/* 312 */           this.msgb[j].setRecved(true);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   int collectPubPaInfo(int paramInt1, int paramInt2, String paramString)
/*     */   {
/*     */     String str;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 340 */     if (paramInt1 == -1) str = new String(paramString); else
/* 341 */       str = this.msgb[paramInt1].getInBody(0);
/* 342 */     int i = this.agnet.getIndex(str);
/*     */     
/* 344 */     int[] arrayOfInt1 = null;
/* 345 */     for (int j = 0; j < this.msgb.length; j++) {
/* 346 */       if (j != paramInt1)
/* 347 */         arrayOfInt1 = MATH.union(arrayOfInt1, this.agnet.getDsepsetLocalIndex(j));
/*     */     }
/* 349 */     if (!MATH.member(i, arrayOfInt1)) { return -1;
/*     */     }
/* 351 */     setMsgToChd(paramInt1, 1, str);
/* 352 */     Envoy[] arrayOfEnvoy = new Envoy[this.msgb.length];
/* 353 */     for (int k = 0; k < this.msgb.length; k++) {
/* 354 */       if ((k != paramInt1) && 
/* 355 */         (MATH.member(i, this.agnet.getDsepsetLocalIndex(k))))
/*     */       {
/* 357 */         arrayOfEnvoy[k] = new Envoy(this.msgb[k]); }
/*     */     }
/* 359 */     getChdMsg(paramInt1, 1);
/* 360 */     int[] arrayOfInt2 = null;
/* 361 */     for (int m = 0; m < this.msgb.length; m++)
/* 362 */       if ((m != paramInt1) && 
/* 363 */         (MATH.member(i, this.agnet.getDsepsetLocalIndex(m))))
/*     */       {
/* 365 */         n = Integer.parseInt(this.msgb[m].getInBody(0));
/* 366 */         if (n == 0) return 0;
/* 367 */         if (n == 1) arrayOfInt2 = MATH.addMember(m, arrayOfInt2);
/*     */       }
/*     */     String[] arrayOfString2;
/* 370 */     if (arrayOfInt2 != null) {
/* 371 */       if (arrayOfInt2.length > 1) { return 0;
/*     */       }
/* 373 */       arrayOfString1 = this.agnet.getInParentLabel(str, this.agnet.getLabel(this.agnet.getDsepsetLocalIndex(arrayOfInt2[0])));
/*     */       
/*     */ 
/* 376 */       for (n = 0; n < this.msgb.length; n++)
/* 377 */         if (n != arrayOfInt2[0]) {
/* 378 */           arrayOfString2 = this.agnet.getInParentLabel(str, this.agnet.getLabel(this.agnet.getDsepsetLocalIndex(n)));
/*     */           
/* 380 */           if (!MATH.isSubset(arrayOfString2, arrayOfString1)) return 0;
/*     */         }
/* 382 */       return 1;
/*     */     }
/*     */     
/*     */ 
/* 386 */     if (paramInt1 == -1) { return -1;
/*     */     }
/* 388 */     String[] arrayOfString1 = this.agnet.getInParentLabel(str, this.agnet.getLabel(this.agnet.getDsepsetLocalIndex(paramInt1)));
/*     */     
/*     */ 
/* 391 */     for (int n = 0; n < this.msgb.length; n++)
/* 392 */       if (n != paramInt1) {
/* 393 */         arrayOfString2 = this.agnet.getInParentLabel(str, this.agnet.getLabel(this.agnet.getDsepsetLocalIndex(n)));
/*     */         
/* 395 */         if (!MATH.isSubset(arrayOfString2, arrayOfString1)) return 1;
/*     */       }
/* 397 */     return -1;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   void setReqFindNonDSByHub(int paramInt, String paramString)
/*     */   {
/* 406 */     int i = this.agnet.getIndex(paramString);
/* 407 */     for (int j = 0; j < this.msgb.length; j++) {
/* 408 */       if (j != paramInt) {
/* 409 */         if (MATH.member(i, this.agnet.getDsepsetLocalIndex(j))) {
/* 410 */           this.msgb[j].setOutMsgType(2);
/* 411 */           this.msgb[j].setDumbOutBody(1);
/* 412 */           this.msgb[j].setOutBody(paramString, 0);
/* 413 */           this.msgb[j].setRecved(false);
/*     */         } else {
/* 415 */           this.msgb[j].setRecved(true);
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
/*     */   boolean findNonDsepnodeByHub(int paramInt1, int paramInt2, String paramString)
/*     */   {
/*     */     String str;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 435 */     if (paramInt1 == -1) str = new String(paramString); else
/* 436 */       str = this.msgb[paramInt1].getInBody(0);
/* 437 */     int i = this.agnet.getIndex(str);
/*     */     
/* 439 */     int j = getPriPaInfo(paramInt1, str);
/* 440 */     if (j == 0) {
/* 441 */       int[] arrayOfInt = null;
/* 442 */       for (int m = 0; m < this.msgb.length; m++) {
/* 443 */         if (m != paramInt1)
/* 444 */           arrayOfInt = MATH.union(arrayOfInt, this.agnet.getDsepsetLocalIndex(m));
/*     */       }
/* 446 */       if (!MATH.member(i, arrayOfInt)) { return false;
/*     */       }
/* 448 */       setMsgToChd(paramInt1, 2, str);
/* 449 */       Envoy[] arrayOfEnvoy = new Envoy[this.msgb.length];
/* 450 */       for (int n = 0; n < this.msgb.length; n++) {
/* 451 */         if ((n != paramInt1) && 
/* 452 */           (MATH.member(i, this.agnet.getDsepsetLocalIndex(n))))
/*     */         {
/* 454 */           arrayOfEnvoy[n] = new Envoy(this.msgb[n]); }
/*     */       }
/* 456 */       getChdMsg(paramInt1, 2);
/* 457 */       for (n = 0; n < this.msgb.length; n++)
/* 458 */         if ((n != paramInt1) && 
/* 459 */           (MATH.member(i, this.agnet.getDsepsetLocalIndex(n))))
/*     */         {
/* 461 */           int i1 = Integer.parseInt(this.msgb[n].getInBody(0));
/* 462 */           if (i1 == 1) return true;
/*     */         }
/* 464 */       return false;
/*     */     }
/*     */     
/* 467 */     int k = collectPubPaInfo(-1, 1, str);
/* 468 */     if (k == -1) return false;
/* 469 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */   void setReqFindNonDSep(int paramInt)
/*     */   {
/* 475 */     for (int i = 0; i < this.msgb.length; i++) {
/* 476 */       if (i != paramInt) {
/* 477 */         this.msgb[i].setOutMsgType(3);
/* 478 */         this.msgb[i].setEmptyOutBody();
/* 479 */         this.msgb[i].setRecved(false);
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
/*     */   boolean findNonDsepnode(int paramInt1, int paramInt2)
/*     */   {
/* 500 */     if ((paramInt1 != -1) && (this.msgb.length == 1)) { return false;
/*     */     }
/*     */     
/* 503 */     HelpPanel.addHelp("Check Node: ");
/* 504 */     int[] arrayOfInt = this.agnet.getDsepsetUnion();
/* 505 */     for (int i = 0; i < arrayOfInt.length; i++) {
/* 506 */       j = arrayOfInt[i];
/* 507 */       if ((paramInt1 == -1) || (!MATH.member(j, this.agnet.getDsepsetLocalIndex(paramInt1))))
/*     */       {
/* 509 */         String str = this.agnet.getLabel(j);
/* 510 */         HelpPanel.appendHelp(str + " ");
/*     */         
/*     */ 
/* 513 */         int k = collectPriPaInfo(-1, 0, str);
/* 514 */         if (k > 1) { return true;
/*     */         }
/* 516 */         if (k == 0) {
/* 517 */           int m = collectPubPaInfo(-1, 1, str);
/* 518 */           if (m == 0) { return true;
/*     */           }
/*     */         }
/* 521 */         else if ((k == 1) && 
/* 522 */           (findNonDsepnodeByHub(-1, 2, str))) {
/* 523 */           return true;
/*     */         }
/*     */       }
/*     */     }
/* 527 */     setMsgToChd(paramInt1, paramInt2, null);
/* 528 */     Envoy[] arrayOfEnvoy = new Envoy[this.msgb.length];
/* 529 */     for (int j = 0; j < this.msgb.length; j++) {
/* 530 */       if (j != paramInt1)
/* 531 */         arrayOfEnvoy[j] = new Envoy(this.msgb[j]);
/*     */     }
/* 533 */     getChdMsg(paramInt1, paramInt2);
/* 534 */     for (j = 0; j < this.msgb.length; j++) {
/* 535 */       if ((j != paramInt1) && 
/* 536 */         (Integer.parseInt(this.msgb[j].getInBody(0)) == 1))
/* 537 */         return true;
/*     */     }
/* 539 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   void setReqPreProc(int paramInt)
/*     */   {
/* 548 */     for (int i = 0; i < this.msgb.length; i++) {
/* 549 */       if (i != paramInt) {
/* 550 */         this.msgb[i].setOutMsgType(4);
/* 551 */         this.msgb[i].setEmptyOutBody();
/* 552 */         this.msgb[i].setRecved(false);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   void preProcLoc() {
/* 558 */     int[] arrayOfInt = this.agnet.getDsepsetUnion();
/* 559 */     this.agnet.markRootLeaf(arrayOfInt);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   void preProcess(int paramInt1, int paramInt2)
/*     */   {
/* 569 */     actOnPatMsg(paramInt1, paramInt2, null);
/* 570 */     setMsgToChd(paramInt1, 4, null);
/* 571 */     Envoy[] arrayOfEnvoy = new Envoy[this.msgb.length];
/* 572 */     for (int i = 0; i < this.msgb.length; i++) {
/* 573 */       if (i != paramInt1)
/* 574 */         arrayOfEnvoy[i] = new Envoy(this.msgb[i]);
/*     */     }
/* 576 */     getChdMsg(paramInt1, paramInt2);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   void setReqMarkNode(int paramInt)
/*     */   {
/* 583 */     for (int i = 0; i < this.msgb.length; i++) {
/* 584 */       if (i != paramInt) {
/* 585 */         this.msgb[i].setOutMsgType(5);
/* 586 */         this.msgb[i].setEmptyOutBody();
/* 587 */         this.msgb[i].setRecved(false);
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
/*     */   boolean markNode(int paramInt1, int paramInt2)
/*     */   {
/* 610 */     boolean bool = false;
/* 611 */     int[] arrayOfInt = this.agnet.getDsepsetUnion();
/* 612 */     for (int i = 0; i < arrayOfInt.length; i++) {
/* 613 */       j = arrayOfInt[i];
/* 614 */       if ((!this.agnet.isMarked(j)) && (
/* 615 */         (paramInt1 == -1) || (!MATH.member(j, this.agnet.getDsepsetLocalIndex(paramInt1)))))
/*     */       {
/*     */ 
/* 618 */         String str = this.agnet.getLabel(j);
/* 619 */         Point localPoint = UTIL.strAryToPoint(actOnPatMsg(paramInt1, 6, str));
/* 620 */         if ((localPoint.x == 0) || (localPoint.y == 0)) {
/* 621 */           setMsgToChd(paramInt1, 6, str);
/* 622 */           Envoy[] arrayOfEnvoy2 = new Envoy[this.msgb.length];
/* 623 */           for (int k = 0; k < this.msgb.length; k++)
/* 624 */             if (k != paramInt1)
/* 625 */               if (!MATH.member(j, this.agnet.getDsepsetLocalIndex(k))) {
/* 626 */                 this.msgb[k].setDumbInBody(2);
/* 627 */                 this.msgb[k].setInBody("-1", 0);this.msgb[k].setInBody("-1", 1);
/* 628 */                 this.msgb[k].setRecved(true);
/*     */               }
/*     */               else {
/* 631 */                 arrayOfEnvoy2[k] = new Envoy(this.msgb[k]);
/*     */               }
/* 633 */           getChdMsg(paramInt1, 6);
/* 634 */           localPoint = UTIL.strAryToPoint(actOnCldMsg(paramInt1, 6, localPoint));
/*     */           
/* 636 */           if ((localPoint.x == 0) || (localPoint.y == 0)) {
/* 637 */             bool = true;
/* 638 */             actOnPatMsg(paramInt1, 7, str);
/* 639 */             setMsgToChd(paramInt1, 7, str);
/* 640 */             for (k = 0; k < this.msgb.length; k++) {
/* 641 */               if (k != paramInt1)
/* 642 */                 if (!MATH.member(j, this.agnet.getDsepsetLocalIndex(k))) {
/* 643 */                   this.msgb[k].setRecved(true);
/*     */                 }
/*     */                 else
/* 646 */                   arrayOfEnvoy2[k] = new Envoy(this.msgb[k]);
/*     */             }
/* 648 */             getChdMsg(paramInt1, 7);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 653 */     setMsgToChd(paramInt1, 5, null);
/* 654 */     Envoy[] arrayOfEnvoy1 = new Envoy[this.msgb.length];
/* 655 */     for (int j = 0; j < this.msgb.length; j++) {
/* 656 */       if (j != paramInt1)
/* 657 */         arrayOfEnvoy1[j] = new Envoy(this.msgb[j]);
/*     */     }
/* 659 */     getChdMsg(paramInt1, 5);
/* 660 */     for (j = 0; j < this.msgb.length; j++) {
/* 661 */       if ((j != paramInt1) && 
/* 662 */         (Integer.parseInt(this.msgb[j].getInBody(0)) == 1)) bool = true;
/*     */     }
/* 664 */     return bool;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   void setReqCollectFInfo(int paramInt, String paramString)
/*     */   {
/* 671 */     for (int i = 0; i < this.msgb.length; i++) {
/* 672 */       if (i != paramInt) {
/* 673 */         this.msgb[i].setOutMsgType(6);
/* 674 */         this.msgb[i].setDumbOutBody(1);
/* 675 */         this.msgb[i].setOutBody(paramString, 0);
/* 676 */         this.msgb[i].setRecved(false);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   Point getFamilyInfo(int paramInt, String paramString)
/*     */   {
/* 683 */     HelpPanel.appendHelp(" cl:" + paramString);
/* 684 */     int i = this.agnet.getIndex(paramString);
/* 685 */     if (i == -1) return new Point(-1, -1);
/* 686 */     Point localPoint = new Point(0, 0);
/* 687 */     if (this.agnet.hasUnmarkedParent(i)) localPoint.x = 1;
/* 688 */     if (this.agnet.hasUnmarkedChild(i)) localPoint.y = 1;
/* 689 */     return localPoint;
/*     */   }
/*     */   
/*     */ 
/*     */   Point sumupFmlyInfo(int paramInt, Point paramPoint)
/*     */   {
/* 695 */     Point localPoint = paramPoint;
/* 696 */     for (int i = 0; i < this.msgb.length; i++)
/* 697 */       if (i != paramInt) {
/* 698 */         int j = Integer.parseInt(this.msgb[i].getInBody(0));
/* 699 */         int k = Integer.parseInt(this.msgb[i].getInBody(1));
/* 700 */         localPoint.x = (localPoint.x > j ? localPoint.x : j);
/* 701 */         localPoint.y = (localPoint.y > k ? localPoint.y : k);
/*     */       }
/* 703 */     return localPoint;
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
/*     */   Point collectFamilyInfo(int paramInt1, int paramInt2, String paramString)
/*     */   {
/* 718 */     int i = this.agnet.getIndex(paramString);
/* 719 */     String[] arrayOfString = actOnPatMsg(paramInt1, paramInt2, paramString);
/* 720 */     Point localPoint = UTIL.strAryToPoint(arrayOfString);
/* 721 */     if (this.msgb.length == 1) {
/* 722 */       return localPoint;
/*     */     }
/* 724 */     if ((localPoint.x == 1) && (localPoint.y == 1)) {
/* 725 */       return localPoint;
/*     */     }
/*     */     
/*     */ 
/* 729 */     setMsgToChd(paramInt1, paramInt2, paramString);
/* 730 */     Envoy[] arrayOfEnvoy = new Envoy[this.msgb.length];
/* 731 */     for (int j = 0; j < this.msgb.length; j++)
/* 732 */       if (j != paramInt1)
/* 733 */         if (!MATH.member(i, this.agnet.getDsepsetLocalIndex(j))) {
/* 734 */           this.msgb[j].setDumbInBody(2);
/* 735 */           this.msgb[j].setInBody("-1", 0);this.msgb[j].setInBody("-1", 1);
/* 736 */           this.msgb[j].setRecved(true);
/*     */         }
/*     */         else {
/* 739 */           arrayOfEnvoy[j] = new Envoy(this.msgb[j]);
/*     */         }
/* 741 */     getChdMsg(paramInt1, paramInt2);
/* 742 */     return UTIL.strAryToPoint(actOnCldMsg(paramInt1, paramInt2, localPoint));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   void setReqDistrMark(int paramInt, String paramString)
/*     */   {
/* 749 */     for (int i = 0; i < this.msgb.length; i++) {
/* 750 */       if (i != paramInt) {
/* 751 */         this.msgb[i].setOutMsgType(7);
/* 752 */         this.msgb[i].setOutBody(paramString, 0);
/* 753 */         this.msgb[i].setRecved(false);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   void distrMarkLocally(int paramInt, String paramString) {
/* 759 */     int i = this.agnet.getIndex(paramString);
/* 760 */     if (i >= 0) {
/* 761 */       HelpPanel.appendHelp(" mk:" + paramString);
/* 762 */       this.agnet.setMark(i);
/* 763 */       this.agnet.markRootLeaf(this.agnet.getDsepsetUnion());
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
/*     */   void distributeMark(int paramInt1, int paramInt2, String paramString)
/*     */   {
/* 776 */     int i = this.agnet.getIndex(paramString);
/* 777 */     actOnPatMsg(paramInt1, paramInt2, paramString);
/* 778 */     if (this.msgb.length > 1) {
/* 779 */       setMsgToChd(paramInt1, paramInt2, paramString);
/* 780 */       Envoy[] arrayOfEnvoy = new Envoy[this.msgb.length];
/* 781 */       for (int j = 0; j < this.msgb.length; j++) {
/* 782 */         if (j != paramInt1)
/* 783 */           if (!MATH.member(i, this.agnet.getDsepsetLocalIndex(j))) {
/* 784 */             this.msgb[j].setRecved(true);
/*     */           }
/*     */           else
/* 787 */             arrayOfEnvoy[j] = new Envoy(this.msgb[j]);
/*     */       }
/* 789 */       getChdMsg(paramInt1, paramInt2);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   void setReqMarkedAll(int paramInt)
/*     */   {
/* 797 */     for (int i = 0; i < this.msgb.length; i++) {
/* 798 */       if (i != paramInt) {
/* 799 */         this.msgb[i].setOutMsgType(8);
/* 800 */         this.msgb[i].setEmptyOutBody();
/* 801 */         this.msgb[i].setRecved(false);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   boolean markedAll(int paramInt1, int paramInt2)
/*     */   {
/* 813 */     if (!this.agnet.isMarked()) return false;
/* 814 */     if ((paramInt1 != -1) && (this.msgb.length == 1)) { return true;
/*     */     }
/*     */     
/* 817 */     setMsgToChd(paramInt1, 8, null);
/* 818 */     Envoy[] arrayOfEnvoy = new Envoy[this.msgb.length];
/* 819 */     for (int i = 0; i < this.msgb.length; i++) {
/* 820 */       if (i != paramInt1)
/* 821 */         arrayOfEnvoy[i] = new Envoy(this.msgb[i]);
/*     */     }
/* 823 */     getChdMsg(paramInt1, paramInt2);
/* 824 */     for (i = 0; i < this.msgb.length; i++)
/* 825 */       if (i != paramInt1) {
/* 826 */         int j = Integer.parseInt(this.msgb[i].getInBody(0));
/* 827 */         if (j == 0) return false;
/*     */       }
/* 829 */     return true;
/*     */   }
/*     */ }


/* Location:              /home/anali/INAOE/tesis/codigos/topub/exe/ww4.jar!/Network/AgtV.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */