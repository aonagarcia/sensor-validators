/*     */ package Network;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.FileReader;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintStream;
/*     */ import java.util.StringTokenizer;
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
/*     */ public class DivisionTree
/*     */   extends HyperGraph
/*     */ {
/*  28 */   int[] d = null;
/*  29 */   int[] dsz = null;
/*  30 */   float wet = 0.0F;
/*     */   static final int AGENT = -1;
/*  32 */   static boolean debug = false;
/*     */   
/*     */ 
/*     */ 
/*     */   public DivisionTree() {}
/*     */   
/*     */ 
/*     */   public DivisionTree(HyperGraph paramHyperGraph)
/*     */   {
/*  41 */     int i = paramHyperGraph.getNodeCount();
/*  42 */     this.cq = new VNode[i];
/*  43 */     for (int j = 0; j < i; j++) {
/*  44 */       this.cq[j] = new VNode(paramHyperGraph.getHNode(j));
/*  45 */       this.cq[j].setLabel("DV" + j);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static DivisionTree setDivTree(DesignNet paramDesignNet, int paramInt)
/*     */   {
/*  57 */     if (paramInt == 0) { return setDivTree(paramDesignNet);
/*     */     }
/*  59 */     int[] arrayOfInt1 = paramDesignNet.getDesignNode();
/*  60 */     if (debug) { UTIL.showList("\nDesign parameters:\n\t", arrayOfInt1);
/*     */     }
/*  62 */     int[] arrayOfInt2 = paramDesignNet.getUtilNode();
/*  63 */     if (debug) { UTIL.showList("\nUtility variables:\n\t", arrayOfInt2);
/*     */     }
/*  65 */     JoinForest localJoinForest = JoinForest.bnToJt(paramDesignNet);
/*     */     
/*  67 */     DivisionTree localDivisionTree = new DivisionTree();
/*     */     
/*  69 */     localDivisionTree.cq = new VNode[1];
/*  70 */     localDivisionTree.cq[0] = new VNode();
/*  71 */     localDivisionTree.cq[0].setLabel("DV0");
/*     */     
/*  73 */     int i = paramDesignNet.getNodeCount();
/*  74 */     int[] arrayOfInt3 = new int[i];
/*  75 */     for (int j = 0; j < i; j++) arrayOfInt3[j] = j;
/*  76 */     localDivisionTree.cq[0].clq = arrayOfInt3;
/*     */     
/*  78 */     ((VNode)localDivisionTree.cq[0]).sjt = localJoinForest;
/*     */     
/*  80 */     localDivisionTree.setDesignPar(arrayOfInt1);
/*  81 */     localDivisionTree.setDesignPar();
/*     */     
/*  83 */     localDivisionTree.setDesignParSz(paramDesignNet.getStateCount(arrayOfInt1));
/*  84 */     localDivisionTree.setDesignParSz();
/*     */     
/*  86 */     localDivisionTree.setUtilVarWeight(arrayOfInt2, paramDesignNet.getWeight(arrayOfInt2));
/*     */     
/*  88 */     if (debug) System.out.println("\nDivision tree:");
/*  89 */     if (debug) localDivisionTree.showDivisionTree();
/*  90 */     return localDivisionTree;
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
/*     */   public static DivisionTree setDivTree(DesignNet paramDesignNet)
/*     */   {
/* 122 */     JoinForest localJoinForest = JoinForest.bnToJt(paramDesignNet);
/* 123 */     return setDivTree(paramDesignNet, localJoinForest, true);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static DivisionTree setDivTree(DesignNet paramDesignNet, JoinForest paramJoinForest, boolean paramBoolean)
/*     */   {
/* 134 */     int[] arrayOfInt1 = paramDesignNet.getDesignNode();
/*     */     
/* 136 */     int[] arrayOfInt2 = null;
/* 137 */     if (!paramBoolean) {
/* 138 */       arrayOfInt2 = paramJoinForest.getDomain();
/* 139 */       arrayOfInt1 = MATH.getIntersection(arrayOfInt1, arrayOfInt2);
/*     */     }
/*     */     
/* 142 */     int[][] arrayOfInt = paramJoinForest.getSepset();
/* 143 */     int i = arrayOfInt.length;
/*     */     
/*     */ 
/* 146 */     int j = 0;
/* 147 */     boolean[] arrayOfBoolean = new boolean[i];
/* 148 */     for (int k = 0; k < i; k++) {
/* 149 */       arrayOfBoolean[k] = false;
/* 150 */       if (MATH.isSubset(arrayOfInt[k], arrayOfInt1)) {
/* 151 */         arrayOfBoolean[k] = true;
/* 152 */         j++;
/*     */       }
/*     */     }
/*     */     
/* 156 */     DivisionTree localDivisionTree = null;
/* 157 */     int m = 1;
/* 158 */     int i2; if (j == 0) {
/* 159 */       localDivisionTree = new DivisionTree();
/* 160 */       localDivisionTree.cq = new VNode[1];
/* 161 */       localDivisionTree.cq[0] = new VNode();
/* 162 */       localDivisionTree.cq[0].setLabel("DV0");
/*     */       
/* 164 */       if (paramBoolean) {
/* 165 */         int n = paramDesignNet.getNodeCount();
/* 166 */         int[] arrayOfInt3 = new int[n];
/* 167 */         for (i2 = 0; i2 < n; i2++) arrayOfInt3[i2] = i2;
/* 168 */         localDivisionTree.cq[0].clq = arrayOfInt3;
/*     */       } else {
/* 170 */         localDivisionTree.cq[0].clq = arrayOfInt2;
/*     */       }
/* 172 */       ((VNode)localDivisionTree.cq[0]).sjt = paramJoinForest;
/*     */     }
/*     */     else {
/* 175 */       localObject = new int[j][];
/* 176 */       int i1 = 0;
/* 177 */       for (i2 = 0; i2 < i; i2++) { if (arrayOfBoolean[i2] != 0) { localObject[(i1++)] = arrayOfInt[i2];
/*     */         }
/*     */       }
/* 180 */       HyperGraph localHyperGraph = HyperGraph.unionCq(paramJoinForest, (int[][])localObject);
/* 181 */       localDivisionTree = new DivisionTree(localHyperGraph);
/*     */       
/* 183 */       JoinForest[] arrayOfJoinForest = paramJoinForest.splitJoinTree((int[][])localObject, paramDesignNet);
/* 184 */       int i3 = arrayOfJoinForest.length;
/*     */       
/* 186 */       m = localDivisionTree.getNodeCount();
/* 187 */       for (int i4 = 0; i4 < m; i4++) {
/* 188 */         int[] arrayOfInt4 = localDivisionTree.getCqMember(i4);
/* 189 */         for (int i5 = 0; i5 < i3; i5++) {
/* 190 */           int[] arrayOfInt5 = arrayOfJoinForest[i5].getDomain();
/* 191 */           if (MATH.isEqualSet(arrayOfInt4, arrayOfInt5)) {
/* 192 */             localDivisionTree.setSubJt(i4, arrayOfJoinForest[i5]);
/* 193 */             break;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 199 */     localDivisionTree.setDesignPar(arrayOfInt1);
/* 200 */     localDivisionTree.setDesignPar();
/*     */     
/* 202 */     localDivisionTree.setDesignParSz(paramDesignNet.getStateCount(arrayOfInt1));
/* 203 */     localDivisionTree.setDesignParSz();
/*     */     
/* 205 */     Object localObject = paramDesignNet.getUtilNode();
/* 206 */     localDivisionTree.setUtilVarWeight((int[])localObject, paramDesignNet.getWeight((int[])localObject));
/*     */     
/* 208 */     return localDivisionTree;
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
/*     */   public static DivisionTree[] setDivTree(DesignNet paramDesignNet, HyperGraph paramHyperGraph, HyperGrafM paramHyperGrafM)
/*     */   {
/* 224 */     JoinForest localJoinForest = JoinForest.setJoinGraphAsjf(paramDesignNet, paramHyperGraph);
/* 225 */     localJoinForest.setDumbSepsetBelief();
/* 226 */     localJoinForest.unifyBelief();
/*     */     
/* 228 */     int i = paramHyperGrafM.getNodeCount();
/* 229 */     DivisionTree[] arrayOfDivisionTree = new DivisionTree[i];
/*     */     
/* 231 */     if (i == 1) {
/* 232 */       arrayOfDivisionTree[0] = setDivTree(paramDesignNet, localJoinForest, true);
/* 233 */       return arrayOfDivisionTree;
/*     */     }
/*     */     
/* 236 */     int[][] arrayOfInt = paramHyperGrafM.getSepset();
/* 237 */     JoinForest[] arrayOfJoinForest = localJoinForest.splitJoinTree(arrayOfInt, paramDesignNet);
/*     */     
/* 239 */     for (int j = 0; j < i; j++) {
/* 240 */       arrayOfDivisionTree[j] = setDivTree(paramDesignNet, arrayOfJoinForest[j], false);
/*     */     }
/* 242 */     return arrayOfDivisionTree;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   void addDivisionMate(int[][] paramArrayOfInt, float[][] paramArrayOfFloat)
/*     */   {
/* 254 */     int i = getNodeCount();
/* 255 */     int j = paramArrayOfInt.length;
/* 256 */     VNode[] arrayOfVNode = new VNode[i + j];
/* 257 */     for (int k = 0; k < i; k++) arrayOfVNode[k] = ((VNode)this.cq[k]);
/* 258 */     this.cq = arrayOfVNode;
/*     */     
/* 260 */     for (k = 0; k < j; k++) {
/* 261 */       int m = getCqHome(paramArrayOfInt[k]);
/*     */       
/* 263 */       this.cq[(i + k)] = new VNode();
/* 264 */       ((VNode)this.cq[(i + k)]).setDesignPar(paramArrayOfInt[k]);
/* 265 */       ((VNode)this.cq[(i + k)]).setDesignParSz(getDesignParSz(paramArrayOfInt[k]));
/* 266 */       ((VNode)this.cq[(i + k)]).setWeud(paramArrayOfFloat[k]);
/*     */       
/* 268 */       HLink localHLink = new HLink(m, paramArrayOfInt[k]);
/* 269 */       addNeighbor(i + k, localHLink);
/* 270 */       localHLink.setNeighbor(i + k);
/* 271 */       addNeighbor(m, localHLink);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setDesignPar(int[] paramArrayOfInt)
/*     */   {
/* 279 */     this.d = UTIL.getDuplicate(paramArrayOfInt);
/*     */   }
/*     */   
/*     */   public void setDesignPar()
/*     */   {
/* 284 */     int i = getNodeCount();
/* 285 */     for (int j = 0; j < i; j++) {
/* 286 */       int[] arrayOfInt1 = ((VNode)this.cq[j]).getDomain();
/* 287 */       int[] arrayOfInt2 = MATH.getIntersection(arrayOfInt1, this.d);
/* 288 */       ((VNode)this.cq[j]).setDesignPar(arrayOfInt2);
/*     */     }
/*     */   }
/*     */   
/*     */   public int[] getDesignPar()
/*     */   {
/* 294 */     return UTIL.getDuplicate(this.d);
/*     */   }
/*     */   
/*     */   int[] getDesignPar(int paramInt)
/*     */   {
/* 299 */     return UTIL.getDuplicate(((VNode)this.cq[paramInt]).getDesignPar());
/*     */   }
/*     */   
/*     */   public void setDesignParSz(int[] paramArrayOfInt)
/*     */   {
/* 304 */     this.dsz = UTIL.getDuplicate(paramArrayOfInt);
/*     */   }
/*     */   
/*     */   public void setDesignParSz()
/*     */   {
/* 309 */     int i = getNodeCount();
/* 310 */     for (int j = 0; j < i; j++) {
/* 311 */       int[] arrayOfInt1 = ((VNode)this.cq[j]).getDesignPar();
/* 312 */       int k = arrayOfInt1.length;
/* 313 */       int[] arrayOfInt2 = new int[k];
/* 314 */       for (int m = 0; m < k; m++) {
/* 315 */         for (int n = 0; n < this.d.length; n++) {
/* 316 */           if (arrayOfInt1[m] == this.d[n]) {
/* 317 */             arrayOfInt2[m] = this.dsz[n];
/* 318 */             break;
/*     */           }
/*     */         }
/*     */       }
/* 322 */       ((VNode)this.cq[j]).setDesignParSz(arrayOfInt2);
/*     */     }
/*     */   }
/*     */   
/*     */   int[] getDesignParSz(int paramInt)
/*     */   {
/* 328 */     return UTIL.getDuplicate(((VNode)this.cq[paramInt]).getDesignParSz());
/*     */   }
/*     */   
/*     */   int[] getDesignParSz(int[] paramArrayOfInt)
/*     */   {
/* 333 */     int i = paramArrayOfInt.length;
/* 334 */     int[] arrayOfInt = new int[i];
/* 335 */     for (int j = 0; j < i; j++) {
/* 336 */       for (int k = 0; k < this.d.length; k++) {
/* 337 */         if (paramArrayOfInt[j] == this.d[k]) {
/* 338 */           arrayOfInt[j] = this.dsz[k]; break;
/*     */         }
/*     */       }
/*     */     }
/* 342 */     return arrayOfInt;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   void enterEnvSetupToCq(int[][] paramArrayOfInt)
/*     */   {
/* 354 */     for (int i = 0; i < paramArrayOfInt.length; i++) {
/* 355 */       int[] arrayOfInt = new int[1];
/* 356 */       arrayOfInt[0] = paramArrayOfInt[i][0];
/* 357 */       int j = getCqHome(arrayOfInt);
/* 358 */       if (j != -1)
/*     */       {
/* 360 */         ((VNode)this.cq[j]).enterEnvSetupToCq(paramArrayOfInt[i][0], paramArrayOfInt[i][1]);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   int[][][] getOptDivDesign()
/*     */   {
/* 370 */     int i = getNodeCount();
/* 371 */     int[][][] arrayOfInt = new int[i][2][];
/* 372 */     for (int j = 0; j < i; j++) {
/* 373 */       arrayOfInt[j][0] = ((VNode)this.cq[j]).getDesignPar();
/* 374 */       arrayOfInt[j][1] = ((VNode)this.cq[j]).getOptd();
/*     */     }
/* 376 */     return arrayOfInt;
/*     */   }
/*     */   
/*     */ 
/*     */   int[] getOptDivDesign(int paramInt, int[] paramArrayOfInt)
/*     */   {
/* 382 */     return ((VNode)this.cq[paramInt]).getOptDesign(paramArrayOfInt);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   int[] getOptDesign(int[] paramArrayOfInt)
/*     */   {
/* 390 */     if (!MATH.isSubset(paramArrayOfInt, this.d)) return null;
/* 391 */     int i = getNodeCount();
/* 392 */     for (int j = 0; j < i; j++) {
/* 393 */       if (MATH.isSubset(paramArrayOfInt, ((VNode)this.cq[j]).d))
/* 394 */         return getOptDivDesign(j, paramArrayOfInt);
/*     */     }
/* 396 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   int[][] getOptDesign()
/*     */   {
/* 406 */     int i = getNodeCount();
/* 407 */     int j = this.d.length;
/* 408 */     int[][] arrayOfInt = new int[2][j];
/* 409 */     arrayOfInt[0] = UTIL.getDuplicate(this.d);
/*     */     
/* 411 */     for (int k = 0; k < j; k++)
/* 412 */       for (int m = 0; m < i; m++) {
/* 413 */         int[] arrayOfInt1 = ((VNode)this.cq[m]).getDesignPar();
/* 414 */         if (MATH.member(arrayOfInt[0][k], arrayOfInt1))
/*     */         {
/* 416 */           arrayOfInt[1][k] = ((VNode)this.cq[m]).getOptd(arrayOfInt[0][k]);
/* 417 */           break;
/*     */         }
/*     */       }
/* 420 */     return arrayOfInt;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setUtilVarWeight(int[] paramArrayOfInt, float[] paramArrayOfFloat)
/*     */   {
/* 427 */     int i = getNodeCount();
/* 428 */     for (int j = 0; j < i; j++) {
/* 429 */       int[] arrayOfInt1 = ((VNode)this.cq[j]).getDomain();
/* 430 */       int[] arrayOfInt2 = MATH.getIntersection(arrayOfInt1, paramArrayOfInt);
/* 431 */       ((VNode)this.cq[j]).setUtilVar(arrayOfInt2);
/* 432 */       if (arrayOfInt2 != null)
/*     */       {
/* 434 */         int k = arrayOfInt2.length;
/* 435 */         float[] arrayOfFloat = new float[k];
/* 436 */         for (int m = 0; m < k; m++) {
/* 437 */           for (int n = 0; n < paramArrayOfInt.length; n++) {
/* 438 */             if (arrayOfInt2[m] == paramArrayOfInt[n]) {
/* 439 */               arrayOfFloat[m] = paramArrayOfFloat[n];
/* 440 */               break;
/*     */             }
/*     */           }
/*     */         }
/* 444 */         ((VNode)this.cq[j]).setUtilWeight(arrayOfFloat);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   int[] getUtilVar(int paramInt) {
/* 450 */     return ((VNode)this.cq[paramInt]).getUtilVar();
/*     */   }
/*     */   
/*     */   float[] getUtilWeight(int paramInt)
/*     */   {
/* 455 */     return ((VNode)this.cq[paramInt]).getUtilWeight();
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
/*     */   float[] getWeud(int paramInt, int[] paramArrayOfInt)
/*     */   {
/* 468 */     return ((VNode)this.cq[paramInt]).getWeud(paramArrayOfInt);
/*     */   }
/*     */   
/*     */   public float getOptUtil(int paramInt)
/*     */   {
/* 473 */     return ((VNode)this.cq[paramInt]).getOptUtil();
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
/*     */   void loadWeight(String paramString1, String paramString2)
/*     */   {
/*     */     try
/*     */     {
/* 490 */       BufferedReader localBufferedReader = new BufferedReader(new FileReader(paramString1));
/* 491 */       HelpPanel.addHelp("Loading weight from " + paramString1);
/*     */       
/* 493 */       String str1 = null;
/* 494 */       while ((str1 = localBufferedReader.readLine()) != null) {
/* 495 */         StringTokenizer localStringTokenizer = new StringTokenizer(str1);
/* 496 */         String str2 = localStringTokenizer.nextToken();
/* 497 */         if (str2.equals(paramString2)) {
/* 498 */           this.wet = Float.valueOf(localStringTokenizer.nextToken()).floatValue();
/* 499 */           break;
/*     */         }
/*     */       }
/*     */       
/* 503 */       localBufferedReader.close();
/*     */     } catch (IOException localIOException) {
/* 505 */       HelpPanel.showError("Unable to load weight!");
/*     */     }
/*     */   }
/*     */   
/*     */   public void setWeight(float paramFloat) {
/* 510 */     this.wet = paramFloat;
/*     */   }
/*     */   
/*     */   float getWeight() {
/* 514 */     return this.wet;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setSubJt(int paramInt, JoinForest paramJoinForest)
/*     */   {
/* 521 */     ((VNode)this.cq[paramInt]).setSubJt(paramJoinForest);
/*     */   }
/*     */   
/*     */   public JoinForest getSubJt(int paramInt)
/*     */   {
/* 526 */     return ((VNode)this.cq[paramInt]).getSubJt();
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
/*     */   public int[] designByDiv()
/*     */   {
/* 539 */     int i = getNodeCount();
/* 540 */     int[] arrayOfInt = new int[i];
/* 541 */     for (int j = 0; j < i; j++) arrayOfInt[j] = ((VNode)this.cq[j]).designByDiv(this.wet);
/* 542 */     return arrayOfInt;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void collectDivUtil(int paramInt)
/*     */   {
/* 549 */     ((VNode)this.cq[paramInt]).collectDivUtil(-1, paramInt, (VNode[])this.cq);
/*     */   }
/*     */   
/*     */ 
/*     */   public void distributeOptDivDesign(int paramInt)
/*     */   {
/* 555 */     ((VNode)this.cq[paramInt]).distributeOptDivDesign(-1, null, null, paramInt, (VNode[])this.cq);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void distributeOptDivDesign(int paramInt, int[] paramArrayOfInt1, int[] paramArrayOfInt2)
/*     */   {
/* 562 */     ((VNode)this.cq[paramInt]).distributeOptDivDesign(-1, paramArrayOfInt1, paramArrayOfInt2, paramInt, (VNode[])this.cq);
/*     */   }
/*     */   
/*     */ 
/*     */   public int[][] collectOptDivDesign(int paramInt)
/*     */   {
/* 568 */     return ((VNode)this.cq[paramInt]).collectOptDivDesign(-1, paramInt, (VNode[])this.cq);
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
/*     */   void absorbDesignPot(int paramInt, int[] paramArrayOfInt, float[] paramArrayOfFloat)
/*     */   {
/* 581 */     ((VNode)this.cq[paramInt]).absorbDesignPot(paramArrayOfInt, paramArrayOfFloat);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   int[] setOptDesign(int paramInt, int[] paramArrayOfInt1, int[] paramArrayOfInt2)
/*     */   {
/* 591 */     return ((VNode)this.cq[paramInt]).setOptDesign(paramArrayOfInt1, paramArrayOfInt2);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void showDivisionTree()
/*     */   {
/* 598 */     UTIL.showList("  Design parameter = ", this.d);
/* 599 */     UTIL.showList("  Design par dimen = ", this.dsz);
/* 600 */     System.out.println("  wet = " + this.wet);
/*     */     
/* 602 */     System.out.println("\n  [Division organization tree]");
/* 603 */     showHyperGraph();
/* 604 */     for (int i = 0; i < this.cq.length; i++) {
/* 605 */       System.out.println("\nSubJT in division-" + i + ":");
/* 606 */       getSubJt(i).showJoinForest();
/* 607 */       UTIL.showList("\ndv_" + i + " d[] = ", getDesignPar(i));
/* 608 */       UTIL.showList("dv_" + i + " dsz[] = ", getDesignParSz(i));
/* 609 */       UTIL.showList("dv_" + i + " u[] = ", getUtilVar(i));
/* 610 */       UTIL.showList("dv_" + i + " w[] = ", getUtilWeight(i));
/*     */     }
/*     */   }
/*     */   
/*     */   public void showDesignPot()
/*     */   {
/* 616 */     int i = getNodeCount();
/* 617 */     for (int j = 0; j < i; j++) {
/* 618 */       System.out.println("\n   division " + j + ":");
/* 619 */       UTIL.showList("design par = ", ((VNode)this.cq[j]).getDesignPar());
/* 620 */       ((VNode)this.cq[j]).showDesignPot();
/*     */     }
/*     */   }
/*     */   
/*     */   public void showOptDivDesign()
/*     */   {
/* 626 */     int i = getNodeCount();
/* 627 */     for (int j = 0; j < i; j++) {
/* 628 */       System.out.println("\n   division " + j + ":");
/* 629 */       ((VNode)this.cq[j]).showOptDivDesign();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void showAllOptDesign()
/*     */   {
/* 638 */     ((VNode)this.cq[0]).showAllOptDesign();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void showAllOptDesignLab(DesignNet paramDesignNet)
/*     */   {
/* 647 */     ((VNode)this.cq[0]).showAllOptDesignLab(paramDesignNet);
/*     */   }
/*     */   
/* 650 */   public String[] getAllOptDesignLab(DesignNet paramDesignNet) { return ((VNode)this.cq[0]).getAllOptDesignLab(paramDesignNet); }
/*     */ }


/* Location:              /home/anali/INAOE/tesis/codigos/topub/exe/ww4.jar!/Network/DivisionTree.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */