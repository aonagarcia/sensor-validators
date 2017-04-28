/*     */ package Network;
/*     */ 
/*     */ import java.io.PrintStream;
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
/*     */ 
/*     */ public class VNode
/*     */   extends HNode
/*     */ {
/*  42 */   static boolean debug = false;
/*  43 */   static int DESECHOFREQ = 50;
/*     */   
/*  45 */   public JoinForest sjt = null;
/*  46 */   int[] d = null;
/*  47 */   int[] dsz = null;
/*  48 */   int[] optd = null;
/*  49 */   float optu = 0.0F;
/*  50 */   int[] u = null;
/*  51 */   float[] w = null;
/*  52 */   float[] weud = null;
/*     */   
/*     */ 
/*     */   static final int AGENT = -1;
/*     */   
/*     */ 
/*     */ 
/*     */   public VNode() {}
/*     */   
/*     */ 
/*     */ 
/*     */   VNode(HNode paramHNode)
/*     */   {
/*  65 */     super(paramHNode);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   void setSubJt(JoinForest paramJoinForest)
/*     */   {
/*  72 */     this.sjt = paramJoinForest;
/*     */   }
/*     */   
/*     */   JoinForest getSubJt()
/*     */   {
/*  77 */     return this.sjt;
/*     */   }
/*     */   
/*     */ 
/*     */   int[] getDomain()
/*     */   {
/*  83 */     return this.sjt.getDomain();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   void setDesignPar(int[] paramArrayOfInt)
/*     */   {
/*  90 */     this.d = UTIL.getDuplicate(paramArrayOfInt);
/*     */   }
/*     */   
/*     */   int[] getDesignPar()
/*     */   {
/*  95 */     return UTIL.getDuplicate(this.d);
/*     */   }
/*     */   
/*     */   void setDesignParSz(int[] paramArrayOfInt)
/*     */   {
/* 100 */     this.dsz = UTIL.getDuplicate(paramArrayOfInt);
/*     */   }
/*     */   
/*     */   int[] getDesignParSz()
/*     */   {
/* 105 */     return UTIL.getDuplicate(this.dsz);
/*     */   }
/*     */   
/*     */ 
/*     */   int[] getDesignParSz(int[] paramArrayOfInt)
/*     */   {
/* 111 */     if (paramArrayOfInt == null) return null;
/* 112 */     int[] arrayOfInt = new int[paramArrayOfInt.length];
/* 113 */     for (int i = 0; i < paramArrayOfInt.length; i++) arrayOfInt[i] = getDesignParSz(paramArrayOfInt[i]);
/* 114 */     return arrayOfInt;
/*     */   }
/*     */   
/*     */   int getDesignParSz(int paramInt)
/*     */   {
/* 119 */     for (int i = 0; i < this.d.length; i++) {
/* 120 */       if (paramInt == this.d[i]) return this.dsz[i];
/*     */     }
/* 122 */     return -1;
/*     */   }
/*     */   
/*     */   void setOptd(int[] paramArrayOfInt)
/*     */   {
/* 127 */     this.optd = paramArrayOfInt;
/*     */   }
/*     */   
/*     */   int[] getOptd()
/*     */   {
/* 132 */     return UTIL.getDuplicate(this.optd);
/*     */   }
/*     */   
/*     */   int getOptd(int paramInt)
/*     */   {
/* 137 */     for (int i = 0; i < this.d.length; i++) if (this.d[i] == paramInt) return this.optd[i];
/* 138 */     return -1;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   void enterEnvSetupToCq(int paramInt1, int paramInt2)
/*     */   {
/* 146 */     int[] arrayOfInt = new int[1];
/* 147 */     arrayOfInt[0] = paramInt1;
/* 148 */     int i = this.sjt.getCqHome(arrayOfInt);
/* 149 */     this.sjt.enterEvidenceToCq(paramInt1, paramInt2);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   void setUtilVar(int[] paramArrayOfInt)
/*     */   {
/* 156 */     this.u = UTIL.getDuplicate(paramArrayOfInt);
/*     */   }
/*     */   
/*     */   int[] getUtilVar()
/*     */   {
/* 161 */     return UTIL.getDuplicate(this.u);
/*     */   }
/*     */   
/*     */   void setUtilWeight(float[] paramArrayOfFloat)
/*     */   {
/* 166 */     this.w = UTIL.getDuplicate(paramArrayOfFloat);
/*     */   }
/*     */   
/*     */   float[] getUtilWeight()
/*     */   {
/* 171 */     return UTIL.getDuplicate(this.w);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   float[] getWeud()
/*     */   {
/* 178 */     return UTIL.getDuplicate(this.weud);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   float[] getWeud(int[] paramArrayOfInt)
/*     */   {
/* 188 */     return MATH.getMaxMargin(this.d, this.dsz, this.weud, paramArrayOfInt);
/*     */   }
/*     */   
/*     */   void setWeud(float[] paramArrayOfFloat)
/*     */   {
/* 193 */     this.weud = UTIL.getDuplicate(paramArrayOfFloat);
/*     */   }
/*     */   
/*     */   float getOptUtil()
/*     */   {
/* 198 */     return this.optu;
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
/*     */   int designByDiv(float paramFloat)
/*     */   {
/* 220 */     int i = this.d.length;
/* 221 */     int j = 1;
/* 222 */     for (int k = 0; k < i; k++) j *= this.dsz[k];
/* 223 */     this.weud = new float[j];
/* 224 */     for (k = 0; k < j; k++) { this.weud[k] = 0.0F;
/*     */     }
/*     */     
/* 227 */     if (this.u == null) return -1;
/* 228 */     k = this.u.length;
/*     */     
/* 230 */     float[] arrayOfFloat = new float[k];
/* 231 */     for (int m = 0; m < k; m++) { arrayOfFloat[m] = (this.w[m] * paramFloat);
/*     */     }
/* 233 */     HelpPanel.addHelp("# division designs = " + j);
/* 234 */     for (m = 0; m < j; m++)
/*     */     {
/*     */ 
/*     */ 
/* 238 */       new EvaOneDesign(this.sjt, this.d, this.dsz, m, arrayOfFloat, this.u, this.weud).start();
/*     */     }
/* 240 */     return j;
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
/*     */   void evaluateOneDesign(int paramInt, int[] paramArrayOfInt, float[] paramArrayOfFloat)
/*     */   {
/* 256 */     JoinForest localJoinForest = new JoinForest(this.sjt);
/* 257 */     int[] arrayOfInt = MATH.decToMix(paramInt, paramArrayOfInt);
/*     */     
/* 259 */     for (int i = 0; i < this.d.length; i++) {
/* 260 */       if (!localJoinForest.enterVluWithCaution(this.d[i], arrayOfInt[i])) {
/* 261 */         this.weud[paramInt] = NaN.0F; break;
/*     */       }
/*     */     }
/* 264 */     if (this.weud[paramInt] < 0.0F) { return;
/*     */     }
/* 266 */     if (debug) localJoinForest.showJoinForest();
/* 267 */     localJoinForest.unifyBelief();
/* 268 */     for (i = 0; i < this.u.length; i++) {
/* 269 */       float[] arrayOfFloat = localJoinForest.getVarMargin(this.u[i]);
/* 270 */       this.weud[paramInt] += arrayOfFloat[0] * paramArrayOfFloat[i];
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   int[] setOptDesign(int[] paramArrayOfInt1, int[] paramArrayOfInt2)
/*     */   {
/* 281 */     float f = -1.0F;
/* 282 */     int i = -1;
/* 283 */     int j = this.weud.length;
/*     */     int k;
/* 285 */     if (paramArrayOfInt1 == null) {
/* 286 */       for (k = 0; k < j; k++) {
/* 287 */         if (this.weud[k] > f) {
/* 288 */           f = this.weud[k];i = k;
/*     */         }
/*     */         
/*     */       }
/*     */     } else {
/* 293 */       for (k = 0; k < j; k++) {
/* 294 */         int[] arrayOfInt = MATH.decToMix(k, this.dsz);
/* 295 */         int m = 1;
/* 296 */         for (int n = 0; n < paramArrayOfInt1.length; n++) {
/* 297 */           int i1 = UTIL.getArrayIndex(paramArrayOfInt1[n], this.d);
/* 298 */           if (arrayOfInt[i1] != paramArrayOfInt2[n]) {
/* 299 */             m = 0; break;
/*     */           }
/*     */         }
/* 302 */         if ((m != 0) && 
/* 303 */           (this.weud[k] > f)) {
/* 304 */           f = this.weud[k];i = k;
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 309 */     this.optd = MATH.decToMix(i, this.dsz);
/* 310 */     this.optu = f;
/* 311 */     return getOptd();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   int[] getOptDesign(int[] paramArrayOfInt)
/*     */   {
/* 320 */     int[] arrayOfInt = new int[paramArrayOfInt.length];
/* 321 */     for (int i = 0; i < paramArrayOfInt.length; i++) {
/* 322 */       int j = paramArrayOfInt[i];
/* 323 */       for (int k = 0; k < this.d.length; k++) {
/* 324 */         if (j == this.d[k]) {
/* 325 */           arrayOfInt[i] = this.optd[k]; break;
/*     */         }
/*     */       }
/*     */     }
/* 329 */     return arrayOfInt;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   void absorbDesignPot(int[] paramArrayOfInt, float[] paramArrayOfFloat)
/*     */   {
/* 337 */     float[] arrayOfFloat = MATH.addMargin(this.d, this.dsz, this.weud, paramArrayOfInt, paramArrayOfFloat);
/* 338 */     this.weud = arrayOfFloat;
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
/*     */   float[] collectDivUtil(int paramInt1, int paramInt2, VNode[] paramArrayOfVNode)
/*     */   {
/* 352 */     if (paramArrayOfVNode.length == 1) { return null;
/*     */     }
/* 354 */     int i = getNeighborCount();
/* 355 */     for (int j = 0; j < i; j++) {
/* 356 */       int k = getNeighbor(j);
/* 357 */       if (k != paramInt1) {
/* 358 */         float[] arrayOfFloat = paramArrayOfVNode[k].collectDivUtil(paramInt2, k, paramArrayOfVNode);
/* 359 */         int[] arrayOfInt3 = paramArrayOfVNode[k].getDesignPar();
/* 360 */         int[] arrayOfInt4 = MATH.getIntersection(this.d, arrayOfInt3);
/* 361 */         absorbDesignPot(arrayOfInt4, arrayOfFloat);
/*     */       }
/*     */     }
/* 364 */     if (paramInt1 != -1) {
/* 365 */       int[] arrayOfInt1 = paramArrayOfVNode[paramInt1].getDesignPar();
/* 366 */       int[] arrayOfInt2 = MATH.getIntersection(this.d, arrayOfInt1);
/* 367 */       return getWeud(arrayOfInt2);
/*     */     }
/* 369 */     return null;
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
/*     */   void distributeOptDivDesign(int paramInt1, int[] paramArrayOfInt1, int[] paramArrayOfInt2, int paramInt2, VNode[] paramArrayOfVNode)
/*     */   {
/* 385 */     this.optd = setOptDesign(paramArrayOfInt1, paramArrayOfInt2);
/* 386 */     if (paramArrayOfVNode.length == 1) { return;
/*     */     }
/* 388 */     int i = getNeighborCount();
/* 389 */     for (int j = 0; j < i; j++) {
/* 390 */       int k = getNeighbor(j);
/* 391 */       if (k != paramInt1)
/*     */       {
/* 393 */         int[] arrayOfInt1 = paramArrayOfVNode[k].getDesignPar();
/* 394 */         int[] arrayOfInt2 = MATH.getIntersection(this.d, arrayOfInt1);
/* 395 */         int[] arrayOfInt3 = new int[arrayOfInt2.length];
/* 396 */         for (int m = 0; m < arrayOfInt2.length; m++)
/* 397 */           arrayOfInt3[m] = this.optd[UTIL.getArrayIndex(arrayOfInt2[m], this.d)];
/* 398 */         paramArrayOfVNode[k].distributeOptDivDesign(paramInt2, arrayOfInt2, arrayOfInt3, k, paramArrayOfVNode);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   int[][] integrateDesign(int[][] paramArrayOfInt)
/*     */   {
/* 409 */     int i = this.d.length;
/* 410 */     int[][] arrayOfInt = new int[2][];
/* 411 */     arrayOfInt[0] = this.d;
/* 412 */     arrayOfInt[1] = this.optd;
/* 413 */     return MATH.union(paramArrayOfInt, arrayOfInt);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   int[][] collectOptDivDesign(int paramInt1, int paramInt2, VNode[] paramArrayOfVNode)
/*     */   {
/* 425 */     if (debug)
/* 426 */       System.out.println("\t Div " + paramInt1 + " col opt div des fm Div " + paramInt2 + ">>");
/* 427 */     int i = getNeighborCount();
/* 428 */     int[][] arrayOfInt1; if ((paramArrayOfVNode.length == 1) || ((i == 1) && (paramInt1 == getNeighbor(0))))
/*     */     {
/* 430 */       arrayOfInt1 = new int[2][];
/* 431 */       arrayOfInt1[0] = UTIL.getDuplicate(this.d);
/* 432 */       arrayOfInt1[1] = UTIL.getDuplicate(this.optd);
/* 433 */       return arrayOfInt1;
/*     */     }
/*     */     
/*     */ 
/* 437 */     int[][] arrayOfInt2 = (int[][])null;
/* 438 */     for (int j = 0; j < i; j++) {
/* 439 */       int k = getNeighbor(j);
/* 440 */       if (k != paramInt1) {
/* 441 */         arrayOfInt1 = paramArrayOfVNode[k].collectOptDivDesign(paramInt2, k, paramArrayOfVNode);
/* 442 */         if (debug) UTIL.showList("des=", arrayOfInt1);
/* 443 */         int[][] arrayOfInt3 = integrateDesign(arrayOfInt1);
/* 444 */         arrayOfInt2 = MATH.union(arrayOfInt2, arrayOfInt3);
/* 445 */         if (debug) UTIL.showList("opd=", arrayOfInt2);
/*     */       } }
/* 447 */     return arrayOfInt2;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   void showDesignPot()
/*     */   {
/* 457 */     int i = this.weud.length;
/* 458 */     for (int j = 0; j < i; j++) {
/* 459 */       int[] arrayOfInt = MATH.decToMix(j, this.dsz);
/* 460 */       String str = UTIL.listToStr("div design " + j + " = ", arrayOfInt);
/* 461 */       if (this.weud[j] <= -2.0F) {
/* 462 */         System.out.println(str + " illegal design ");
/*     */       }
/*     */       else {
/* 465 */         System.out.println(str + " WEU = " + this.weud[j]);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   void showOptDivDesign() {
/* 471 */     UTIL.showList("\nDiv design Par ", this.d);
/* 472 */     UTIL.showList("Opt div design ", this.optd);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   void showAllOptDesign()
/*     */   {
/* 482 */     float f = -1.0F;
/* 483 */     int i = this.weud.length;
/* 484 */     for (int j = 0; j < i; j++) if (this.weud[j] > f) { f = this.weud[j];
/*     */       }
/* 486 */     System.out.println("\nFinal design>>");
/* 487 */     System.out.println("Opt ext util = " + f);
/* 488 */     UTIL.showList("\nDesign Pars = ", this.d);
/*     */     
/* 490 */     j = 1;
/* 491 */     for (int k = 0; k < i; k++) {
/* 492 */       if (this.weud[k] == f) {
/* 493 */         int[] arrayOfInt = MATH.decToMix(k, this.dsz);
/* 494 */         UTIL.showList("Opt des " + j + " = ", arrayOfInt);
/* 495 */         j++;
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   void showAllOptDesignLab(DesignNet paramDesignNet)
/*     */   {
/* 507 */     String[] arrayOfString = getAllOptDesignLab(paramDesignNet);
/* 508 */     for (int i = 0; i < arrayOfString.length; i++) System.out.println(arrayOfString[i]);
/*     */   }
/*     */   
/* 511 */   String[] getAllOptDesignLab(DesignNet paramDesignNet) { float f = -1.0F;
/* 512 */     int i = this.weud.length;
/* 513 */     for (int j = 0; j < i; j++) if (this.weud[j] > f) { f = this.weud[j];
/*     */       }
/* 515 */     String[] arrayOfString1 = new String[10];
/* 516 */     arrayOfString1[0] = new String("\nOptimal design>>");
/* 517 */     arrayOfString1[1] = new String("Opt ext util = " + f);
/*     */     
/* 519 */     int k = 1;
/* 520 */     for (int m = 0; m < i; m++) {
/* 521 */       if (this.weud[m] == f) {
/* 522 */         int[] arrayOfInt = MATH.decToMix(m, this.dsz);
/*     */         
/* 524 */         arrayOfString1[(k + 1)] = new String("Opt Des-" + k + " = ( ");
/* 525 */         for (int i1 = 0; i1 < this.d.length; i1++) {
/* 526 */           String str1 = paramDesignNet.getLabel(this.d[i1]);
/* 527 */           String str2 = paramDesignNet.getState(this.d[i1], arrayOfInt[i1]); int 
/* 528 */             tmp228_227 = (k + 1); String[] tmp228_222 = arrayOfString1;tmp228_222[tmp228_227] = (tmp228_222[tmp228_227] + str1 + "=" + str2 + " ");
/*     */         }
/* 530 */         int tmp276_275 = (k + 1); String[] tmp276_270 = arrayOfString1;tmp276_270[tmp276_275] = (tmp276_270[tmp276_275] + ")");
/*     */         
/* 532 */         k++;
/* 533 */         if (k == 9) {
/* 534 */           arrayOfString1[9] = new String("** there may be more opt design !!! **");
/* 535 */           return arrayOfString1;
/*     */         }
/*     */       }
/*     */     }
/* 539 */     String[] arrayOfString2 = new String[k + 1];
/* 540 */     for (int n = 0; n < k + 1; n++) arrayOfString2[n] = arrayOfString1[n];
/* 541 */     return arrayOfString2;
/*     */   }
/*     */ }


/* Location:              /home/anali/INAOE/tesis/codigos/topub/exe/ww4.jar!/Network/VNode.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */