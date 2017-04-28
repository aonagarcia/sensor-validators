/*      */ package Network;
/*      */ 
/*      */ import java.awt.Point;
/*      */ import java.io.BufferedReader;
/*      */ import java.io.FileReader;
/*      */ import java.io.IOException;
/*      */ import java.io.PrintStream;
/*      */ import java.util.StringTokenizer;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class DecNetPar
/*      */   extends DesignNet
/*      */ {
/*   72 */   static boolean debug = false;
/*      */   
/*      */ 
/*   75 */   public int pivot = 0;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*   81 */   public int[] u = null;
/*   82 */   public float[] w = null;
/*   83 */   public int[][] a = (int[][])null;
/*   84 */   int[][] asz = (int[][])null;
/*   85 */   float[][] uit = (float[][])null;
/*      */   
/*      */ 
/*      */ 
/*   89 */   int[][] beta = (int[][])null;
/*   90 */   int[][] gama = (int[][])null;
/*   91 */   int[][] vgama = (int[][])null;
/*      */   
/*      */   int[] ad;
/*      */   int[] od;
/*   95 */   float ou = 0.0F;
/*      */   
/*   97 */   float wet = 0.0F;
/*      */   
/*      */   static final int AGENT = -1;
/*      */   
/*  101 */   String path = null;
/*      */   
/*      */   public DecNetPar(DesignNet paramDesignNet) {
/*  104 */     if ((paramDesignNet != null) && (paramDesignNet.nd != null)) setDesignNet(paramDesignNet);
/*      */   }
/*      */   
/*      */   public void setPath(String paramString)
/*      */   {
/*  109 */     this.path = new String(paramString);
/*      */   }
/*      */   
/*      */   public void setLocalVar()
/*      */   {
/*  114 */     this.u = getUtilNode();
/*  115 */     int i = this.u.length;
/*  116 */     this.w = getWeight();
/*      */     
/*  118 */     if (this.wet > 0.0F) {
/*  119 */       for (j = 0; j < i; j++) { this.w[j] *= this.wet;
/*      */       }
/*      */     }
/*  122 */     this.a = new int[i][];
/*  123 */     this.asz = new int[i][];
/*      */     
/*  125 */     for (int j = 0; j < i; j++) {
/*  126 */       int[] arrayOfInt1 = getParent(this.u[j]);
/*  127 */       for (int k = 0; k < arrayOfInt1.length; k++) {
/*  128 */         int[] arrayOfInt2 = getParent(arrayOfInt1[k]);
/*  129 */         this.a[j] = MATH.union(this.a[j], arrayOfInt2);
/*      */       }
/*  131 */       this.asz[j] = getStateCount(this.a[j]);
/*      */     }
/*      */     
/*  134 */     setUit();
/*      */     
/*  136 */     this.ad = getDesignNode();
/*  137 */     this.od = new int[this.ad.length];
/*  138 */     for (j = 0; j < this.ad.length; j++) { this.od[j] = -1;
/*      */     }
/*      */   }
/*      */   
/*      */   public void showLocalVar()
/*      */   {
/*  144 */     UTIL.showList("u[]=", this.u);
/*  145 */     UTIL.showList("w[]=", this.w);
/*  146 */     UTIL.showList("a[][]=", this.a);
/*  147 */     UTIL.showList("asz[][]=", this.asz);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static DecNetPar addUtilEffe(DecNetPar paramDecNetPar, int[] paramArrayOfInt, float[] paramArrayOfFloat)
/*      */   {
/*  171 */     int i = paramArrayOfInt.length;
/*  172 */     Point localPoint = new Point(0, 0);
/*  173 */     int j = paramDecNetPar.getNodeCount();
/*  174 */     Object localObject = paramDecNetPar;
/*  175 */     for (int k = 0; k <= i; k++) { localObject = addNode((DesignNet)localObject, localPoint);
/*      */     }
/*  177 */     for (k = 0; k < i; k++) {
/*  178 */       ((DesignNet)localObject).setLabel(j + k, "e_" + ((DesignNet)localObject).getLabel(paramArrayOfInt[k]));
/*  179 */       ((DesignNet)localObject).setState(j + k, ((DesignNet)localObject).getState(paramArrayOfInt[k]));
/*      */     }
/*  181 */     ((DesignNet)localObject).setLabel(j + i, "u_" + (j + i));
/*  182 */     String[] arrayOfString = { "yes", "no" };
/*  183 */     ((DesignNet)localObject).setState(j + i, arrayOfString);
/*      */     
/*  185 */     if (paramDecNetPar.wet > 0.0F) ((DesignNet)localObject).setWeight(j + i, 1.0F / paramDecNetPar.wet); else {
/*  186 */       ((DesignNet)localObject).setWeight(j + i, 1.0F);
/*      */     }
/*  188 */     for (int m = 0; m < i; m++) {
/*  189 */       ((DesignNet)localObject).addArc(paramArrayOfInt[m], j + m);
/*  190 */       ((DesignNet)localObject).addArc(j + m, j + i);
/*      */     }
/*  192 */     ((DesignNet)localObject).toUtilNode(j + i);
/*      */     
/*  194 */     for (m = 0; m < i; m++) {
/*  195 */       int n = ((DesignNet)localObject).getStateCount(j + m);
/*  196 */       float[] arrayOfFloat = new float[n * n];
/*  197 */       for (int i1 = 0; i1 < n; i1++) {
/*  198 */         for (int i2 = 0; i2 < n; i2++) {
/*  199 */           if (i1 == i2) arrayOfFloat[(i1 * n + i2)] = 1.0F; else
/*  200 */             arrayOfFloat[(i1 * n + i2)] = 0.0F;
/*      */         }
/*      */       }
/*  203 */       ((DesignNet)localObject).setCondProb(j + m, arrayOfFloat);
/*      */     }
/*  205 */     ((DesignNet)localObject).setCptUtilFmUtil(j + i, paramArrayOfFloat);
/*      */     
/*  207 */     DecNetPar localDecNetPar = new DecNetPar((DesignNet)localObject);
/*  208 */     localDecNetPar.wet = paramDecNetPar.wet;
/*  209 */     localDecNetPar.pivot = paramDecNetPar.pivot;
/*  210 */     localDecNetPar.path = paramDecNetPar.path;
/*      */     
/*  212 */     return localDecNetPar;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void getOptDecFe()
/*      */   {
/*  301 */     if (debug) { System.out.println("Run getOptDecFe()");
/*      */     }
/*      */     
/*  304 */     setLocalVar();
/*  305 */     int i = this.u.length;
/*  306 */     int j = this.ad.length;
/*      */     
/*  308 */     int[] arrayOfInt1 = new int[j];
/*  309 */     for (int k = 0; k < j; k++) arrayOfInt1[k] = getStateCount(this.ad[k]);
/*  310 */     k = 1;
/*  311 */     for (int m = 0; m < j; m++) k *= arrayOfInt1[m];
/*  312 */     if (debug) { System.out.println("Fe: #plans= " + k);
/*      */     }
/*  314 */     for (m = 0; m < j; m++) this.od[m] = 0;
/*  315 */     int[][] arrayOfInt = new int[i][];
/*  316 */     for (int n = 0; n < i; n++) { arrayOfInt[n] = new int[this.a[n].length];
/*      */     }
/*      */     
/*  319 */     this.ou = 0.0F;
/*  320 */     for (n = 0; n < k; n++) {
/*  321 */       int[] arrayOfInt2 = MATH.decToMix(n, arrayOfInt1);
/*  322 */       float f = 0.0F;
/*  323 */       for (int i1 = 0; i1 < i; i1++) {
/*  324 */         arrayOfInt[i1] = UTIL.project(this.ad, arrayOfInt2, this.a[i1]);
/*  325 */         f += this.w[i1] * getEuFeSu(i1, arrayOfInt[i1]);
/*      */       }
/*  327 */       if (f > this.ou) {
/*  328 */         this.ou = f;
/*  329 */         for (i1 = 0; i1 < j; i1++) { this.od[i1] = arrayOfInt2[i1];
/*      */         }
/*      */       }
/*      */     }
/*  333 */     if (debug) {
/*  334 */       UTIL.showList("ad[]=", this.ad);
/*  335 */       UTIL.showList("opt util=" + this.ou + "\nopt decision=", this.od);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void getOptDec(int[] paramArrayOfInt1, int[] paramArrayOfInt2)
/*      */   {
/*  348 */     if (debug) UTIL.showList("Plan recved=", paramArrayOfInt2);
/*  349 */     this.od = UTIL.merge(paramArrayOfInt1, paramArrayOfInt2, this.ad, this.od);
/*  350 */     int i = this.beta.length;
/*  351 */     for (int j = 0; j < i; j++) { getOptDec(paramArrayOfInt1, paramArrayOfInt2, j);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   void getOptDec(int[] paramArrayOfInt1, int[] paramArrayOfInt2, int paramInt)
/*      */   {
/*  364 */     int[] arrayOfInt1 = UTIL.project(paramArrayOfInt1, paramArrayOfInt2, this.beta[paramInt]);
/*  365 */     int i = MATH.mixToDec(arrayOfInt1, getStateCount(this.beta[paramInt]));
/*  366 */     int[] arrayOfInt2 = MATH.decToMix(this.vgama[paramInt][i], getStateCount(this.gama[paramInt]));
/*  367 */     this.od = UTIL.merge(this.gama[paramInt], arrayOfInt2, this.ad, this.od);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public float[] getMeuPeUc(int[] paramArrayOfInt)
/*      */   {
/*  391 */     if (debug) { UTIL.showList("Run getMeuPeUc(pd[]), where pd[]=", paramArrayOfInt);
/*      */     }
/*  393 */     int[] arrayOfInt1 = typeUtilVar(paramArrayOfInt);
/*  394 */     if (debug) {
/*  395 */       UTIL.showList("u[]=", this.u);UTIL.showList("uty[]=", arrayOfInt1);
/*      */     }
/*  397 */     String[] arrayOfString = new String[10];
/*  398 */     int i = 0;
/*  399 */     int j = 0;int k = 0;
/*      */     
/*  401 */     int m = 0;int n = 0;int i1 = 0;
/*  402 */     for (int i2 = 0; i2 < arrayOfInt1.length; i2++) {
/*  403 */       if (arrayOfInt1[i2] == 0) { m++;
/*  404 */       } else if (arrayOfInt1[i2] == 1) n++;
/*      */     }
/*  406 */     i1 = MATH.max(arrayOfInt1) - 1;
/*  407 */     if (i1 < 0) i1 = 0;
/*  408 */     i2 = n + i1;
/*  409 */     int i3 = m + i2;
/*      */     
/*  411 */     if (i2 > 0) {
/*  412 */       this.beta = new int[i2][];
/*  413 */       this.gama = new int[i2][];this.vgama = new int[i2][];
/*      */     }
/*  415 */     int i4 = 0;
/*      */     
/*  417 */     float[][] arrayOfFloat = new float[i3][];
/*  418 */     int[][] arrayOfInt2 = new int[i3][];
/*  419 */     int[][] arrayOfInt3 = new int[i3][];
/*  420 */     int i5 = 0;
/*      */     
/*      */ 
/*  423 */     for (int i6 = 0; i6 < arrayOfInt1.length; i6++) {
/*  424 */       if (arrayOfInt1[i6] == 0) {
/*  425 */         arrayOfFloat[i5] = getEuFeSu(i6);
/*  426 */         arrayOfInt2[(i5++)] = this.a[i6];
/*  427 */         if (debug) {
/*  428 */           arrayOfString[(i++)] = UTIL.listToStr("Fe: u=" + this.u[i6] + " d_anc=", this.a[i6]);
/*  429 */           j += (int)Math.pow(5.0D, this.a[i6].length);
/*      */         }
/*      */       }
/*  432 */       else if (arrayOfInt1[i6] == 1) {
/*  433 */         arrayOfInt2[i5] = MATH.getIntersection(this.a[i6], paramArrayOfInt);
/*  434 */         arrayOfFloat[i5] = getMeuPeSu(i6, arrayOfInt2[i5], i4++);
/*  435 */         i5++;
/*  436 */         if (debug) {
/*  437 */           arrayOfString[(i++)] = UTIL.listToStr("Pe: u=" + this.u[i6] + " d_anc=", this.a[i6]);
/*  438 */           k += (int)Math.pow(5.0D, this.a[i6].length);
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*  444 */     for (i6 = 2; i6 <= i1 + 1; i6++) {
/*  445 */       int[] arrayOfInt5 = UTIL.getArrayIndex(arrayOfInt1, i6);
/*  446 */       int[] arrayOfInt6 = this.a[arrayOfInt5[0]];
/*  447 */       for (int i9 = 1; i9 < arrayOfInt5.length; i9++) arrayOfInt6 = MATH.union(arrayOfInt6, this.a[arrayOfInt5[i9]]);
/*  448 */       arrayOfInt2[i5] = MATH.getIntersection(paramArrayOfInt, arrayOfInt6);
/*  449 */       arrayOfFloat[i5] = getMeuPeMu(arrayOfInt5, arrayOfInt6, arrayOfInt2[i5], i4++);
/*  450 */       i5++;
/*  451 */       if (debug) {
/*  452 */         String str = UTIL.listToStr("Pe: u[]=", UTIL.getSubsetByIndex(this.u, arrayOfInt5));
/*  453 */         arrayOfString[(i++)] = UTIL.listToStr(str + " d_anc=", arrayOfInt6);
/*  454 */         k += (int)Math.pow(5.0D, arrayOfInt6.length);
/*      */       }
/*      */     }
/*      */     
/*  458 */     for (i6 = 0; i6 < i3; i6++) arrayOfInt3[i6] = getStateCount(arrayOfInt2[i6]);
/*  459 */     int[] arrayOfInt4 = getStateCount(paramArrayOfInt);
/*      */     
/*  461 */     int i7 = 1;
/*  462 */     for (int i8 = 0; i8 < paramArrayOfInt.length; i8++) i7 *= arrayOfInt4[i8];
/*  463 */     float[] arrayOfFloat1 = new float[i7];
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  472 */     for (int i10 = 0; i10 < i7; i10++)
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/*  477 */       arrayOfFloat1[i10] = 0.0F;
/*  478 */       int[] arrayOfInt7 = MATH.decToMix(i10, arrayOfInt4);
/*  479 */       for (int i11 = 0; i11 < i3; i11++) {
/*  480 */         int[] arrayOfInt8 = UTIL.project(paramArrayOfInt, arrayOfInt7, arrayOfInt2[i11]);
/*  481 */         arrayOfFloat1[i10] += arrayOfFloat[i11][MATH.mixToDec(arrayOfInt8, arrayOfInt3[i11])];
/*      */       }
/*      */     }
/*      */     
/*  485 */     if (debug) {
/*  486 */       for (i10 = 0; i10 < i; i10++) System.out.println(arrayOfString[i10]);
/*  487 */       System.out.println("Fe: #plan= " + j);
/*  488 */       System.out.println("Pe: #plan= " + k);
/*  489 */       UTIL.showList("beta=", this.beta);
/*  490 */       UTIL.showList("gama=", this.gama);
/*  491 */       UTIL.showList("vgama=", this.vgama);
/*      */     }
/*  493 */     return arrayOfFloat1;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public float[] getMeuPeNoUc(int[] paramArrayOfInt)
/*      */   {
/*  504 */     if (debug) { UTIL.showList("Run getMeuPeNoUc(pd[]), where pd[]=", paramArrayOfInt);
/*      */     }
/*      */     
/*  507 */     int i = 1;
/*  508 */     this.beta = new int[i][];
/*  509 */     this.gama = new int[i][];this.vgama = new int[i][];
/*      */     
/*      */ 
/*  512 */     int[] arrayOfInt = new int[this.u.length];
/*  513 */     for (int j = 0; j < this.u.length; j++) { arrayOfInt[j] = j;
/*      */     }
/*  515 */     j = 0;
/*  516 */     float[] arrayOfFloat = getMeuPeMu(arrayOfInt, this.ad, paramArrayOfInt, j);
/*  517 */     if (debug) {
/*  518 */       int k = (int)Math.pow(5.0D, this.ad.length);
/*  519 */       System.out.println("Pe: #plan= " + k);
/*  520 */       UTIL.showList("beta=", this.beta);UTIL.showList("gama=", this.gama);
/*  521 */       UTIL.showList("vgama=", this.vgama);
/*      */     }
/*  523 */     return arrayOfFloat;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public float[] getMeuFeNoUc(int[] paramArrayOfInt)
/*      */   {
/*  534 */     if (debug) { UTIL.showList("Run getMeuFeNoUc(pd[]), where pd[]=", paramArrayOfInt);
/*      */     }
/*  536 */     int i = 1;
/*  537 */     this.beta = new int[i][];
/*  538 */     this.gama = new int[i][];this.vgama = new int[i][];
/*      */     
/*      */ 
/*  541 */     int[] arrayOfInt = new int[this.u.length];
/*  542 */     for (int j = 0; j < this.u.length; j++) { arrayOfInt[j] = j;
/*      */     }
/*  544 */     j = 0;
/*  545 */     float[] arrayOfFloat = getMeuFeMu(arrayOfInt, this.ad, paramArrayOfInt, j);
/*      */     
/*  547 */     if (debug) {
/*  548 */       int k = (int)Math.pow(5.0D, this.ad.length);
/*  549 */       System.out.println("Fe: #plan= " + k);
/*  550 */       UTIL.showList("beta=", this.beta);UTIL.showList("gama=", this.gama);
/*  551 */       UTIL.showList("vgama=", this.vgama);
/*      */     }
/*  553 */     return arrayOfFloat;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public float[] getMeuFeUc(int[] paramArrayOfInt)
/*      */   {
/*  563 */     if (debug) UTIL.showList("Run getMeuFeUc(pd[]), where pd[]=", paramArrayOfInt);
/*  564 */     int[] arrayOfInt1 = typeUtilVar(paramArrayOfInt);
/*  565 */     if (debug) { UTIL.showList("u[]=", this.u);UTIL.showList("uty[]=", arrayOfInt1);
/*      */     }
/*  567 */     int i = 0;int j = 0;int k = 0;
/*  568 */     for (int m = 0; m < arrayOfInt1.length; m++) {
/*  569 */       if (arrayOfInt1[m] == 0) { i++;
/*  570 */       } else if (arrayOfInt1[m] == 1) j++;
/*      */     }
/*  572 */     k = MATH.max(arrayOfInt1) - 1;
/*  573 */     if (k < 0) k = 0;
/*  574 */     m = j + k;
/*  575 */     int n = i + m;
/*      */     
/*  577 */     if (m > 0) {
/*  578 */       this.beta = new int[m][];
/*  579 */       this.gama = new int[m][];this.vgama = new int[m][];
/*      */     }
/*  581 */     int i1 = 0;
/*      */     
/*  583 */     float[][] arrayOfFloat = new float[n][];
/*  584 */     int[][] arrayOfInt2 = new int[n][];
/*  585 */     int[][] arrayOfInt3 = new int[n][];
/*  586 */     int i2 = 0;
/*      */     
/*      */ 
/*  589 */     int i3 = 0;
/*  590 */     for (int i4 = 0; i4 < arrayOfInt1.length; i4++) {
/*  591 */       if (arrayOfInt1[i4] == 0) {
/*  592 */         arrayOfFloat[i2] = getEuFeSu(i4);
/*  593 */         arrayOfInt2[(i2++)] = this.a[i4];
/*  594 */         if (debug) i3 += (int)Math.pow(5.0D, this.a[i4].length);
/*      */       }
/*  596 */       else if (arrayOfInt1[i4] == 1) {
/*  597 */         arrayOfInt2[i2] = MATH.getIntersection(this.a[i4], paramArrayOfInt);
/*  598 */         arrayOfFloat[i2] = getMeuFeSu(i4, arrayOfInt2[i2], i1++);
/*  599 */         i2++;
/*  600 */         if (debug) { i3 += (int)Math.pow(5.0D, this.a[i4].length);
/*      */         }
/*      */       }
/*      */     }
/*  604 */     for (i4 = 2; i4 <= k + 1; i4++) {
/*  605 */       int[] arrayOfInt5 = UTIL.getArrayIndex(arrayOfInt1, i4);
/*  606 */       int[] arrayOfInt6 = this.a[arrayOfInt5[0]];
/*  607 */       for (i7 = 1; i7 < arrayOfInt5.length; i7++) arrayOfInt6 = MATH.union(arrayOfInt6, this.a[arrayOfInt5[i7]]);
/*  608 */       arrayOfInt2[i2] = MATH.getIntersection(paramArrayOfInt, arrayOfInt6);
/*  609 */       arrayOfFloat[i2] = getMeuFeMu(arrayOfInt5, arrayOfInt6, arrayOfInt2[i2], i1++);
/*  610 */       i2++;
/*  611 */       if (debug) { i3 += (int)Math.pow(5.0D, arrayOfInt6.length);
/*      */       }
/*      */     }
/*  614 */     for (i4 = 0; i4 < n; i4++) arrayOfInt3[i4] = getStateCount(arrayOfInt2[i4]);
/*  615 */     int[] arrayOfInt4 = getStateCount(paramArrayOfInt);
/*      */     
/*  617 */     int i5 = 1;
/*  618 */     for (int i6 = 0; i6 < paramArrayOfInt.length; i6++) i5 *= arrayOfInt4[i6];
/*  619 */     float[] arrayOfFloat1 = new float[i5];
/*      */     
/*      */ 
/*  622 */     for (int i7 = 0; i7 < i5; i7++) {
/*  623 */       arrayOfFloat1[i7] = 0.0F;
/*  624 */       int[] arrayOfInt7 = MATH.decToMix(i7, arrayOfInt4);
/*  625 */       for (int i8 = 0; i8 < n; i8++) {
/*  626 */         int[] arrayOfInt8 = UTIL.project(paramArrayOfInt, arrayOfInt7, arrayOfInt2[i8]);
/*  627 */         arrayOfFloat1[i7] += arrayOfFloat[i8][MATH.mixToDec(arrayOfInt8, arrayOfInt3[i8])];
/*      */       }
/*      */     }
/*      */     
/*  631 */     if (debug) {
/*  632 */       System.out.println("Fe: #plan= " + i3);
/*  633 */       UTIL.showList("beta=", this.beta);
/*  634 */       UTIL.showList("gama=", this.gama);
/*  635 */       UTIL.showList("vgama=", this.vgama);
/*      */     }
/*  637 */     return arrayOfFloat1;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int[] typeUtilVar(int[] paramArrayOfInt)
/*      */   {
/*  676 */     int i = this.u.length;
/*  677 */     int[] arrayOfInt = new int[i];
/*  678 */     for (int j = 0; j < i; j++) arrayOfInt[j] = -1;
/*  679 */     int[][] arrayOfInt1 = new int[i][];
/*      */     
/*  681 */     int k = 0;
/*  682 */     int n; int i1; for (int m = 0; m < i; m++) {
/*  683 */       if (MATH.isSubset(this.a[m], paramArrayOfInt)) {
/*  684 */         arrayOfInt[m] = 0;k++;
/*      */       }
/*      */       else {
/*  687 */         arrayOfInt1[m] = MATH.setDifference(this.a[m], paramArrayOfInt);
/*  688 */         n = 1;
/*  689 */         for (i1 = 0; i1 < i; i1++) {
/*  690 */           if ((i1 != m) && 
/*  691 */             (arrayOfInt[i1] < 0) && 
/*  692 */             (MATH.getIntersection(arrayOfInt1[m], this.a[i1]) != null)) n = 0;
/*      */         }
/*  694 */         if (n != 0) {
/*  695 */           arrayOfInt[m] = 1;k++;
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*  700 */     m = 2;
/*  701 */     while (k < i) {
/*  702 */       n = 0;
/*  703 */       while (arrayOfInt[n] >= 0) n++;
/*  704 */       arrayOfInt[n] = m;k++;
/*  705 */       i1 = 0;
/*  706 */       for (int i2 = 0; i2 < i; i2++) {
/*  707 */         if (arrayOfInt[i2] == m) {
/*  708 */           for (int i3 = 0; i3 < i; i3++)
/*  709 */             if ((arrayOfInt[i3] < 0) && 
/*  710 */               (MATH.getIntersection(arrayOfInt1[i2], this.a[i3]) != null)) {
/*  711 */               arrayOfInt[i3] = m;k++;
/*  712 */               i1 = 1;
/*      */             }
/*      */         }
/*      */       }
/*  716 */       if (i1 == 0) m++;
/*      */     }
/*  718 */     return arrayOfInt;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public float[] getEuFeSu(int paramInt)
/*      */   {
/*  726 */     int i = 1;
/*  727 */     for (int j = 0; j < this.a[paramInt].length; j++) i *= this.asz[paramInt][j];
/*  728 */     float[] arrayOfFloat = new float[i];
/*      */     
/*  730 */     for (int k = 0; k < i; k++) {
/*  731 */       int[] arrayOfInt = MATH.decToMix(k, this.asz[paramInt]);
/*  732 */       arrayOfFloat[k] = (this.w[paramInt] * getEuFeSu(paramInt, arrayOfInt));
/*      */     }
/*  734 */     return arrayOfFloat;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public float getEuFeSu(int paramInt, int[] paramArrayOfInt)
/*      */   {
/*  756 */     int i = this.u[paramInt];
/*  757 */     int[] arrayOfInt1 = getParent(i);
/*  758 */     int j = arrayOfInt1.length;
/*  759 */     int[] arrayOfInt2 = getStateCount(arrayOfInt1);
/*      */     
/*  761 */     int[] arrayOfInt3 = this.a[paramInt];
/*  762 */     int[][] arrayOfInt = new int[j][];
/*  763 */     for (int k = 0; k < j; k++) {
/*  764 */       int[] arrayOfInt4 = getParent(arrayOfInt1[k]);
/*  765 */       arrayOfInt[k] = UTIL.project(arrayOfInt3, paramArrayOfInt, arrayOfInt4);
/*      */     }
/*      */     
/*  768 */     k = 1;
/*  769 */     for (int m = 0; m < j; m++) { k *= arrayOfInt2[m];
/*      */     }
/*  771 */     float f1 = 0.0F;
/*  772 */     for (int n = 0; n < k; n++) {
/*  773 */       int[] arrayOfInt5 = MATH.decToMix(n, arrayOfInt2);
/*  774 */       float f2 = getUtilFmCptUtil(i, n);
/*  775 */       for (int i1 = 0; i1 < j; i1++) f2 *= getCondProb(arrayOfInt1[i1], arrayOfInt5[i1], arrayOfInt[i1]);
/*  776 */       f1 += f2;
/*      */     }
/*  778 */     return f1;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public float[] getMeuFeSu(int paramInt1, int[] paramArrayOfInt, int paramInt2)
/*      */   {
/*  799 */     int[] arrayOfInt1 = this.a[paramInt1];
/*  800 */     int[] arrayOfInt2 = MATH.setDifference(arrayOfInt1, paramArrayOfInt);
/*  801 */     int[] arrayOfInt3 = this.asz[paramInt1];
/*  802 */     int[] arrayOfInt4 = UTIL.project(arrayOfInt1, arrayOfInt3, paramArrayOfInt);
/*  803 */     int[] arrayOfInt5 = UTIL.project(arrayOfInt1, arrayOfInt3, arrayOfInt2);
/*      */     
/*  805 */     int i = 1;
/*  806 */     for (int j = 0; j < paramArrayOfInt.length; j++) i *= arrayOfInt4[j];
/*  807 */     float[] arrayOfFloat = new float[i];
/*      */     
/*  809 */     this.beta[paramInt2] = paramArrayOfInt;this.gama[paramInt2] = arrayOfInt2;
/*  810 */     this.vgama[paramInt2] = new int[i];
/*      */     
/*  812 */     for (int k = 0; k < i; k++) {
/*  813 */       int[] arrayOfInt6 = MATH.decToMix(k, arrayOfInt4);
/*  814 */       arrayOfFloat[k] = (this.w[paramInt1] * getMeuFeSu(paramInt1, paramArrayOfInt, arrayOfInt6, arrayOfInt2, arrayOfInt5, paramInt2, k));
/*      */     }
/*      */     
/*      */ 
/*  818 */     return arrayOfFloat;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   float getMeuFeSu(int paramInt1, int[] paramArrayOfInt1, int[] paramArrayOfInt2, int[] paramArrayOfInt3, int[] paramArrayOfInt4, int paramInt2, int paramInt3)
/*      */   {
/*  834 */     int i = 1;
/*  835 */     for (int j = 0; j < paramArrayOfInt3.length; j++) { i *= paramArrayOfInt4[j];
/*      */     }
/*  837 */     int[] arrayOfInt1 = new int[paramArrayOfInt1.length + paramArrayOfInt3.length];
/*  838 */     int[] arrayOfInt2 = UTIL.getSubsetIndex(this.a[paramInt1], paramArrayOfInt1);
/*  839 */     int[] arrayOfInt3 = UTIL.getSubsetIndex(this.a[paramInt1], paramArrayOfInt3);
/*  840 */     for (int k = 0; k < paramArrayOfInt1.length; k++) { arrayOfInt1[arrayOfInt2[k]] = paramArrayOfInt2[k];
/*      */     }
/*  842 */     for (k = 0; k < paramArrayOfInt3.length; k++) arrayOfInt1[arrayOfInt3[k]] = 0;
/*  843 */     float f1 = getEuFeSu(paramInt1, arrayOfInt1);
/*  844 */     this.vgama[paramInt2][paramInt3] = 0;
/*      */     
/*  846 */     for (int m = 1; m < i; m++) {
/*  847 */       int[] arrayOfInt4 = MATH.decToMix(m, paramArrayOfInt4);
/*  848 */       for (int n = 0; n < paramArrayOfInt3.length; n++) arrayOfInt1[arrayOfInt3[n]] = arrayOfInt4[n];
/*  849 */       float f2 = getEuFeSu(paramInt1, arrayOfInt1);
/*  850 */       if (f2 > f1) {
/*  851 */         f1 = f2;this.vgama[paramInt2][paramInt3] = m;
/*      */       }
/*      */     }
/*  854 */     return f1;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public float[] getMeuFeMu(int[] paramArrayOfInt1, int[] paramArrayOfInt2, int[] paramArrayOfInt3, int paramInt)
/*      */   {
/*  865 */     int i = paramArrayOfInt1.length;
/*  866 */     int[] arrayOfInt1 = getStateCount(paramArrayOfInt3);
/*  867 */     int[] arrayOfInt2 = MATH.setDifference(paramArrayOfInt2, paramArrayOfInt3);
/*  868 */     int[] arrayOfInt3 = getStateCount(arrayOfInt2);
/*      */     
/*  870 */     int j = 1;
/*  871 */     for (int k = 0; k < paramArrayOfInt3.length; k++) { j *= arrayOfInt1[k];
/*      */     }
/*  873 */     this.beta[paramInt] = paramArrayOfInt3;this.gama[paramInt] = arrayOfInt2;
/*  874 */     this.vgama[paramInt] = new int[j];
/*      */     
/*  876 */     float[] arrayOfFloat = new float[j];
/*  877 */     for (int m = 0; m < j; m++) {
/*  878 */       int[] arrayOfInt4 = MATH.decToMix(m, arrayOfInt1);
/*  879 */       arrayOfFloat[m] = getMeuFeMu(paramArrayOfInt1, paramArrayOfInt3, arrayOfInt4, arrayOfInt2, arrayOfInt3, paramInt, m);
/*      */     }
/*  881 */     return arrayOfFloat;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public float getMeuFeMu(int[] paramArrayOfInt1, int[] paramArrayOfInt2, int[] paramArrayOfInt3, int[] paramArrayOfInt4, int[] paramArrayOfInt5, int paramInt1, int paramInt2)
/*      */   {
/*  933 */     int i = paramArrayOfInt1.length;
/*  934 */     int j = 1;
/*  935 */     for (int k = 0; k < paramArrayOfInt4.length; k++) { j *= paramArrayOfInt5[k];
/*      */     }
/*  937 */     int[][] arrayOfInt1 = new int[i][];
/*  938 */     for (int m = 0; m < i; m++) arrayOfInt1[m] = MATH.getIntersection(this.a[paramArrayOfInt1[m]], paramArrayOfInt2);
/*  939 */     int[][] arrayOfInt2 = new int[i][];
/*  940 */     for (int n = 0; n < i; n++) arrayOfInt2[n] = MATH.setDifference(this.a[paramArrayOfInt1[n]], paramArrayOfInt2);
/*  941 */     int[][] arrayOfInt3 = new int[i][];
/*  942 */     for (int i1 = 0; i1 < i; i1++) arrayOfInt3[i1] = UTIL.getSubsetIndex(this.a[paramArrayOfInt1[i1]], arrayOfInt1[i1]);
/*  943 */     int[][] arrayOfInt4 = new int[i][];
/*  944 */     for (int i2 = 0; i2 < i; i2++) { arrayOfInt4[i2] = UTIL.getSubsetIndex(this.a[paramArrayOfInt1[i2]], arrayOfInt2[i2]);
/*      */     }
/*  946 */     int[][] arrayOfInt5 = new int[i][];
/*  947 */     for (int i3 = 0; i3 < i; i3++) { arrayOfInt5[i3] = UTIL.project(paramArrayOfInt2, paramArrayOfInt3, arrayOfInt1[i3]);
/*      */     }
/*  949 */     int[][] arrayOfInt6 = new int[i][];
/*  950 */     for (int i4 = 0; i4 < i; i4++) {
/*  951 */       arrayOfInt6[i4] = new int[this.a[i4].length];
/*  952 */       if (arrayOfInt1[i4] != null) {
/*  953 */         for (i5 = 0; i5 < arrayOfInt1[i4].length; i5++) arrayOfInt6[i4][arrayOfInt3[i4][i5]] = arrayOfInt5[i4][i5];
/*      */       }
/*      */     }
/*  956 */     float[] arrayOfFloat1 = new float[i];
/*  957 */     for (int i5 = 0; i5 < i; i5++) arrayOfFloat1[i5] = (this.w[paramArrayOfInt1[i5]] * getPivotProb(paramArrayOfInt1[i5]));
/*  958 */     float[] arrayOfFloat2 = new float[i];
/*  959 */     for (int i6 = 0; i6 < i; i6++) { arrayOfFloat2[i6] = (this.w[paramArrayOfInt1[i6]] - arrayOfFloat1[i6]);
/*      */     }
/*  961 */     float f1 = 0.0F;
/*  962 */     for (int i7 = 0; i7 < i; i7++) f1 += this.w[paramArrayOfInt1[i7]] * getEuFeSu(paramArrayOfInt1[i7], arrayOfInt6[i7]);
/*  963 */     this.vgama[paramInt1][paramInt2] = 0;
/*      */     
/*  965 */     for (i7 = 1; i7 < j; i7++) {
/*  966 */       int[] arrayOfInt7 = MATH.decToMix(i7, paramArrayOfInt5);
/*  967 */       for (int i8 = 0; i8 < i; i8++) {
/*  968 */         if (arrayOfInt2[i8] != null) {
/*  969 */           int[] arrayOfInt8 = UTIL.project(paramArrayOfInt4, arrayOfInt7, arrayOfInt2[i8]);
/*  970 */           for (int i10 = 0; i10 < arrayOfInt2[i8].length; i10++) arrayOfInt6[i8][arrayOfInt4[i8][i10]] = arrayOfInt8[i10];
/*      */         }
/*      */       }
/*  973 */       float f2 = 0.0F;
/*  974 */       for (int i9 = 0; i9 < i; i9++) f2 += this.w[paramArrayOfInt1[i9]] * getEuFeSu(paramArrayOfInt1[i9], arrayOfInt6[i9]);
/*  975 */       if (f2 > f1) {
/*  976 */         f1 = f2;this.vgama[paramInt1][paramInt2] = i7;
/*      */       }
/*      */     }
/*  979 */     return f1;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public float[] getMeuPeSu(int paramInt1, int[] paramArrayOfInt, int paramInt2)
/*      */   {
/* 1000 */     int[] arrayOfInt1 = this.a[paramInt1];
/* 1001 */     int[] arrayOfInt2 = MATH.setDifference(arrayOfInt1, paramArrayOfInt);
/* 1002 */     int[] arrayOfInt3 = this.asz[paramInt1];
/* 1003 */     int[] arrayOfInt4 = UTIL.project(arrayOfInt1, arrayOfInt3, paramArrayOfInt);
/* 1004 */     int[] arrayOfInt5 = UTIL.project(arrayOfInt1, arrayOfInt3, arrayOfInt2);
/*      */     
/* 1006 */     int i = 1;
/* 1007 */     for (int j = 0; j < paramArrayOfInt.length; j++) i *= arrayOfInt4[j];
/* 1008 */     float[] arrayOfFloat = new float[i];
/*      */     
/* 1010 */     this.beta[paramInt2] = paramArrayOfInt;this.gama[paramInt2] = arrayOfInt2;
/* 1011 */     this.vgama[paramInt2] = new int[i];
/*      */     
/* 1013 */     for (int k = 0; k < i; k++) {
/* 1014 */       int[] arrayOfInt6 = MATH.decToMix(k, arrayOfInt4);
/* 1015 */       arrayOfFloat[k] = (this.w[paramInt1] * getMeuPeSu(paramInt1, paramArrayOfInt, arrayOfInt6, arrayOfInt2, arrayOfInt5, paramInt2, k));
/*      */     }
/* 1017 */     return arrayOfFloat;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   float getMeuPeSu(int paramInt1, int[] paramArrayOfInt1, int[] paramArrayOfInt2, int[] paramArrayOfInt3, int[] paramArrayOfInt4, int paramInt2, int paramInt3)
/*      */   {
/* 1034 */     int i = 1;
/* 1035 */     for (int j = 0; j < paramArrayOfInt3.length; j++) { i *= paramArrayOfInt4[j];
/*      */     }
/* 1037 */     int[] arrayOfInt1 = new int[paramArrayOfInt1.length + paramArrayOfInt3.length];
/* 1038 */     int[] arrayOfInt2 = UTIL.getSubsetIndex(this.a[paramInt1], paramArrayOfInt1);
/* 1039 */     int[] arrayOfInt3 = UTIL.getSubsetIndex(this.a[paramInt1], paramArrayOfInt3);
/* 1040 */     for (int k = 0; k < paramArrayOfInt1.length; k++) { arrayOfInt1[arrayOfInt2[k]] = paramArrayOfInt2[k];
/*      */     }
/* 1042 */     for (k = 0; k < paramArrayOfInt3.length; k++) arrayOfInt1[arrayOfInt3[k]] = 0;
/* 1043 */     float f1 = getEuFeSu(paramInt1, arrayOfInt1);
/* 1044 */     this.vgama[paramInt2][paramInt3] = 0;
/*      */     
/* 1046 */     float f2 = getPivotProb(paramInt1);
/*      */     
/* 1048 */     float f3 = (1.0F - f2) / 2.0F;
/* 1049 */     for (int m = 1; m < i; m++) {
/* 1050 */       int[] arrayOfInt4 = MATH.decToMix(m, paramArrayOfInt4);
/* 1051 */       for (int n = 0; n < paramArrayOfInt3.length; n++) arrayOfInt1[arrayOfInt3[n]] = arrayOfInt4[n];
/* 1052 */       n = MATH.mixToDec(arrayOfInt1, this.asz[paramInt1]);
/* 1053 */       float f4 = f2 * this.uit[paramInt1][n] + f3;
/* 1054 */       if (f4 > f1) {
/* 1055 */         float f5 = getEuFeSu(paramInt1, arrayOfInt1);
/* 1056 */         if (f5 > f1) {
/* 1057 */           f1 = f5;this.vgama[paramInt2][paramInt3] = m;
/*      */         }
/*      */       }
/*      */     }
/* 1061 */     return f1;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public float[] getMeuPeMu(int[] paramArrayOfInt1, int[] paramArrayOfInt2, int[] paramArrayOfInt3, int paramInt)
/*      */   {
/* 1073 */     int i = paramArrayOfInt1.length;
/* 1074 */     int[] arrayOfInt1 = getStateCount(paramArrayOfInt3);
/* 1075 */     int[] arrayOfInt2 = MATH.setDifference(paramArrayOfInt2, paramArrayOfInt3);
/* 1076 */     int[] arrayOfInt3 = getStateCount(arrayOfInt2);
/*      */     
/* 1078 */     int j = 1;
/* 1079 */     for (int k = 0; k < paramArrayOfInt3.length; k++) { j *= arrayOfInt1[k];
/*      */     }
/* 1081 */     this.beta[paramInt] = paramArrayOfInt3;this.gama[paramInt] = arrayOfInt2;
/* 1082 */     this.vgama[paramInt] = new int[j];
/*      */     
/* 1084 */     float[] arrayOfFloat = new float[j];
/* 1085 */     for (int m = 0; m < j; m++) {
/* 1086 */       int[] arrayOfInt4 = MATH.decToMix(m, arrayOfInt1);
/* 1087 */       arrayOfFloat[m] = getMeuPeMu(paramArrayOfInt1, paramArrayOfInt3, arrayOfInt4, arrayOfInt2, arrayOfInt3, paramInt, m);
/*      */     }
/* 1089 */     return arrayOfFloat;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public float getMeuPeMu(int[] paramArrayOfInt1, int[] paramArrayOfInt2, int[] paramArrayOfInt3, int[] paramArrayOfInt4, int[] paramArrayOfInt5, int paramInt1, int paramInt2)
/*      */   {
/* 1148 */     int i = paramArrayOfInt1.length;
/* 1149 */     int j = 1;
/* 1150 */     for (int k = 0; k < paramArrayOfInt4.length; k++) { j *= paramArrayOfInt5[k];
/*      */     }
/* 1152 */     int[][] arrayOfInt1 = new int[i][];
/* 1153 */     for (int m = 0; m < i; m++) arrayOfInt1[m] = MATH.getIntersection(this.a[paramArrayOfInt1[m]], paramArrayOfInt2);
/* 1154 */     int[][] arrayOfInt2 = new int[i][];
/* 1155 */     for (int n = 0; n < i; n++) arrayOfInt2[n] = MATH.setDifference(this.a[paramArrayOfInt1[n]], paramArrayOfInt2);
/* 1156 */     int[][] arrayOfInt3 = new int[i][];
/* 1157 */     for (int i1 = 0; i1 < i; i1++) arrayOfInt3[i1] = UTIL.getSubsetIndex(this.a[paramArrayOfInt1[i1]], arrayOfInt1[i1]);
/* 1158 */     int[][] arrayOfInt4 = new int[i][];
/* 1159 */     for (int i2 = 0; i2 < i; i2++) { arrayOfInt4[i2] = UTIL.getSubsetIndex(this.a[paramArrayOfInt1[i2]], arrayOfInt2[i2]);
/*      */     }
/* 1161 */     int[][] arrayOfInt5 = new int[i][];
/* 1162 */     for (int i3 = 0; i3 < i; i3++) { arrayOfInt5[i3] = UTIL.project(paramArrayOfInt2, paramArrayOfInt3, arrayOfInt1[i3]);
/*      */     }
/* 1164 */     int[][] arrayOfInt6 = new int[i][];
/* 1165 */     for (int i4 = 0; i4 < i; i4++) {
/* 1166 */       arrayOfInt6[i4] = new int[this.a[i4].length];
/* 1167 */       if (arrayOfInt1[i4] != null) {
/* 1168 */         for (i5 = 0; i5 < arrayOfInt1[i4].length; i5++) arrayOfInt6[i4][arrayOfInt3[i4][i5]] = arrayOfInt5[i4][i5];
/*      */       }
/*      */     }
/* 1171 */     float[] arrayOfFloat1 = new float[i];
/* 1172 */     for (int i5 = 0; i5 < i; i5++) arrayOfFloat1[i5] = (this.w[paramArrayOfInt1[i5]] * getPivotProb(paramArrayOfInt1[i5]));
/* 1173 */     float[] arrayOfFloat2 = new float[i];
/*      */     
/* 1175 */     for (int i6 = 0; i6 < i; i6++) { arrayOfFloat2[i6] = ((this.w[paramArrayOfInt1[i6]] - arrayOfFloat1[i6]) / 2.0F);
/*      */     }
/*      */     
/* 1178 */     float f1 = 0.0F;
/* 1179 */     for (int i7 = 0; i7 < i; i7++) { f1 += this.w[paramArrayOfInt1[i7]] * getEuFeSu(paramArrayOfInt1[i7], arrayOfInt6[i7]);
/*      */     }
/* 1181 */     this.vgama[paramInt1][paramInt2] = 0;
/*      */     
/* 1183 */     for (i7 = 1; i7 < j; i7++) {
/* 1184 */       int[] arrayOfInt7 = MATH.decToMix(i7, paramArrayOfInt5);
/* 1185 */       int i10; for (int i8 = 0; i8 < i; i8++) {
/* 1186 */         if (arrayOfInt2[i8] != null)
/*      */         {
/* 1188 */           int[] arrayOfInt8 = UTIL.project(paramArrayOfInt4, arrayOfInt7, arrayOfInt2[i8]);
/* 1189 */           for (i10 = 0; i10 < arrayOfInt2[i8].length; i10++) arrayOfInt6[i8][arrayOfInt4[i8][i10]] = arrayOfInt8[i10];
/*      */         }
/*      */       }
/* 1192 */       float f2 = 0.0F;
/* 1193 */       for (int i9 = 0; i9 < i; i9++) {
/* 1194 */         i10 = MATH.mixToDec(arrayOfInt6[i9], this.asz[paramArrayOfInt1[i9]]);
/* 1195 */         f2 += arrayOfFloat1[i9] * this.uit[paramArrayOfInt1[i9]][i10] + arrayOfFloat2[i9];
/*      */       }
/*      */       
/*      */ 
/* 1199 */       if (f2 > f1) {
/* 1200 */         float f3 = 0.0F;
/* 1201 */         for (i10 = 0; i10 < i; i10++) f3 += this.w[paramArrayOfInt1[i10]] * getEuFeSu(paramArrayOfInt1[i10], arrayOfInt6[i10]);
/* 1202 */         if (f3 > f1) {
/* 1203 */           f1 = f3;this.vgama[paramInt1][paramInt2] = i7;
/*      */         }
/*      */       }
/*      */     }
/* 1207 */     return f1;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   float getNormPivotProb(int paramInt)
/*      */   {
/* 1238 */     float f1 = 1.0F;
/* 1239 */     int[] arrayOfInt = getParent(this.u[paramInt]);
/* 1240 */     for (int i = 0; i < arrayOfInt.length; i++) {
/* 1241 */       float[] arrayOfFloat = refCondProb(arrayOfInt[i]);
/* 1242 */       int j = getStateCount(arrayOfInt[i]);
/*      */       
/*      */ 
/*      */ 
/*      */ 
/* 1247 */       float f2 = getMax(arrayOfFloat, 0, j - 1);
/* 1248 */       f1 *= f2;
/*      */     }
/* 1250 */     if (debug) System.out.println("Normal pivot prob = " + f1);
/* 1251 */     return f1;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   float getMaxPivotProb(int paramInt)
/*      */   {
/* 1261 */     float f1 = 1.0F;
/* 1262 */     int[] arrayOfInt = getParent(this.u[paramInt]);
/* 1263 */     for (int i = 0; i < arrayOfInt.length; i++) {
/* 1264 */       float[] arrayOfFloat = refCondProb(arrayOfInt[i]);
/* 1265 */       float f2 = getMax(arrayOfFloat, 0, arrayOfFloat.length - 1);
/* 1266 */       f1 *= f2;
/*      */     }
/* 1268 */     if (debug) System.out.println("Max pivot prob = " + f1);
/* 1269 */     return f1;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   float getMinPivotProb(int paramInt)
/*      */   {
/* 1280 */     float f1 = 1.0F;
/* 1281 */     int[] arrayOfInt = getParent(this.u[paramInt]);
/* 1282 */     for (int i = 0; i < arrayOfInt.length; i++) {
/* 1283 */       int j = getStateCount(arrayOfInt[i]);
/* 1284 */       float[] arrayOfFloat1 = refCondProb(arrayOfInt[i]);
/* 1285 */       int k = arrayOfFloat1.length / j;
/* 1286 */       float[] arrayOfFloat2 = new float[k];
/*      */       
/* 1288 */       for (int m = 0; m < k; m++) arrayOfFloat2[m] = getMax(arrayOfFloat1, m * j, m * j + j - 1);
/* 1289 */       float f2 = MATH.min(arrayOfFloat2);
/* 1290 */       f1 *= f2;
/*      */     }
/* 1292 */     if (debug) System.out.println("Min pivot prob = " + f1);
/* 1293 */     return f1;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   float getMeanPivotProb(int paramInt)
/*      */   {
/* 1303 */     float f1 = 1.0F;
/* 1304 */     int[] arrayOfInt = getParent(this.u[paramInt]);
/* 1305 */     for (int i = 0; i < arrayOfInt.length; i++) {
/* 1306 */       float[] arrayOfFloat = refCondProb(arrayOfInt[i]);
/* 1307 */       int j = getStateCount(arrayOfInt[i]);
/* 1308 */       int k = arrayOfFloat.length / j;
/* 1309 */       float f2 = 0.0F;
/* 1310 */       for (int m = 1; m < k; m++) f2 += getMax(arrayOfFloat, m * j, m * j + j - 1);
/* 1311 */       f1 *= f2 / k;
/*      */     }
/* 1313 */     if (debug) System.out.println("Mean pivot prob = " + f1);
/* 1314 */     return f1;
/*      */   }
/*      */   
/*      */   static float getMax(float[] paramArrayOfFloat, int paramInt1, int paramInt2)
/*      */   {
/* 1319 */     float f = paramArrayOfFloat[paramInt1];
/* 1320 */     for (int i = paramInt1 + 1; i <= paramInt2; i++) if (paramArrayOfFloat[i] > f) f = paramArrayOfFloat[i];
/* 1321 */     return f;
/*      */   }
/*      */   
/*      */   static float getMin(float[] paramArrayOfFloat, int paramInt1, int paramInt2)
/*      */   {
/* 1326 */     float f = paramArrayOfFloat[paramInt1];
/* 1327 */     for (int i = paramInt1 + 1; i <= paramInt2; i++) if (paramArrayOfFloat[i] < f) f = paramArrayOfFloat[i];
/* 1328 */     return f;
/*      */   }
/*      */   
/*      */   float getPivotProb(int paramInt)
/*      */   {
/*      */     float f;
/* 1334 */     if (this.pivot == 0) { f = getNormPivotProb(paramInt);
/* 1335 */     } else if (this.pivot == 1) { f = getMaxPivotProb(paramInt);
/* 1336 */     } else if (this.pivot == 3) f = getMinPivotProb(paramInt); else {
/* 1337 */       f = getMeanPivotProb(paramInt);
/*      */     }
/* 1339 */     return f;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   float[] setUit(int paramInt)
/*      */   {
/* 1356 */     int i = 1;
/* 1357 */     for (int j = 0; j < this.a[paramInt].length; j++) i *= this.asz[paramInt][j];
/* 1358 */     float[] arrayOfFloat = new float[i];
/*      */     
/* 1360 */     int[] arrayOfInt1 = getParent(this.u[paramInt]);
/* 1361 */     int[] arrayOfInt2 = new int[arrayOfInt1.length];
/*      */     
/* 1363 */     for (int k = 0; k < i; k++) {
/* 1364 */       int[] arrayOfInt3 = MATH.decToMix(k, this.asz[paramInt]);
/* 1365 */       for (int m = 0; m < arrayOfInt1.length; m++) {
/* 1366 */         int[] arrayOfInt4 = getParent(arrayOfInt1[m]);
/* 1367 */         int[] arrayOfInt5 = UTIL.project(this.a[paramInt], arrayOfInt3, arrayOfInt4);
/* 1368 */         arrayOfInt2[m] = getMostProbable(arrayOfInt1[m], arrayOfInt5);
/*      */       }
/* 1370 */       arrayOfFloat[k] = getUtil(this.u[paramInt], arrayOfInt2);
/*      */     }
/* 1372 */     return arrayOfFloat;
/*      */   }
/*      */   
/*      */ 
/*      */   public void setUit()
/*      */   {
/* 1378 */     this.uit = new float[this.u.length][];
/* 1379 */     for (int i = 0; i < this.u.length; i++) { this.uit[i] = setUit(i);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   void loadWeight(String paramString1, String paramString2)
/*      */   {
/*      */     try
/*      */     {
/* 1396 */       BufferedReader localBufferedReader = new BufferedReader(new FileReader(paramString1));
/* 1397 */       HelpPanel.addHelp("Loading weight from " + paramString1);
/*      */       
/* 1399 */       String str1 = null;
/* 1400 */       while ((str1 = localBufferedReader.readLine()) != null) {
/* 1401 */         StringTokenizer localStringTokenizer = new StringTokenizer(str1);
/* 1402 */         String str2 = localStringTokenizer.nextToken();
/* 1403 */         if (str2.equals(paramString2)) {
/* 1404 */           this.wet = Float.valueOf(localStringTokenizer.nextToken()).floatValue();
/* 1405 */           break;
/*      */         }
/*      */       }
/*      */       
/* 1409 */       localBufferedReader.close();
/*      */     } catch (IOException localIOException) {
/* 1411 */       HelpPanel.showError("Unable to load weight!");
/*      */     }
/*      */   }
/*      */   
/*      */   float getAgtWeight() {
/* 1416 */     return this.wet;
/*      */   }
/*      */   
/*      */   public void setAgtWeight(float paramFloat) {
/* 1420 */     this.wet = paramFloat;
/*      */   }
/*      */ }


/* Location:              /home/anali/INAOE/tesis/codigos/topub/exe/ww4.jar!/Network/DecNetPar.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */