/*     */ package Network;
/*     */ 
/*     */ import java.util.Random;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class HyperGrafS
/*     */   extends HyperGraph
/*     */ {
/*     */   public HyperGrafS() {}
/*     */   
/*     */   public HyperGrafS(int paramInt)
/*     */   {
/*  16 */     setDumbNet(paramInt);
/*     */   }
/*     */   
/*     */   public HyperGrafS(HyperGraph paramHyperGraph)
/*     */   {
/*  21 */     if ((paramHyperGraph != null) && (paramHyperGraph.cq != null)) {
/*  22 */       setHyperGraphNode(paramHyperGraph);
/*  23 */       setHyperGraphOther(paramHyperGraph);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public HyperGrafS(HyperGrafS paramHyperGrafS)
/*     */   {
/*  31 */     if ((paramHyperGrafS != null) && (paramHyperGrafS.cq != null)) {
/*  32 */       setHyperGraphNode(paramHyperGrafS);
/*  33 */       setHyperGraphOther(paramHyperGrafS);
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
/*     */   public void expandJt(int paramInt1, int paramInt2, int paramInt3, Random paramRandom)
/*     */   {
/*  77 */     int i = paramInt1;
/*  78 */     int j = paramInt2;
/*  79 */     if (this.cq == null) {
/*  80 */       setDumbNetPlus(1);
/*  81 */       k = Math.min(i, paramInt3);
/*  82 */       int m = k == 1 ? 1 : 2 + (int)(paramRandom.nextFloat() * (k - 1));
/*  83 */       this.cq[0].clq = UTIL.getSequence(j, m);
/*  84 */       i -= m;j += m;
/*     */     }
/*     */     
/*  87 */     while (i > 0) {
/*  88 */       k = 1 + (int)(paramRandom.nextFloat() * Math.min(i, paramInt3 - 1));
/*  89 */       int[] arrayOfInt1 = UTIL.getSequence(j, k);
/*  90 */       int n = this.cq.length;
/*     */       int i1;
/*  92 */       int i2; if (paramRandom.nextFloat() < 0.5D) {
/*  93 */         for (i1 = 0; i1 < n; i1++) {
/*  94 */           i2 = this.cq[i1].clq.length;
/*  95 */           if (i2 + k <= paramInt3) {
/*  96 */             int[] arrayOfInt2 = getCqMember(i1);
/*  97 */             this.cq[i1].setCqMember(MATH.union(arrayOfInt1, arrayOfInt2));
/*     */             
/*  99 */             i -= k;j += k;
/* 100 */             break;
/*     */           }
/*     */         }
/*     */       }
/*     */       else {
/* 105 */         i1 = (int)(paramRandom.nextFloat() * n);
/* 106 */         i2 = Math.min(paramInt3 - k, getCqSize(i1) - 1);
/* 107 */         int i3 = 1 + (int)(paramRandom.nextFloat() * i2);
/* 108 */         int[] arrayOfInt3 = MATH.getRandomSubset(getCqMember(i1), i3, paramRandom);
/* 109 */         int[] arrayOfInt4 = MATH.union(arrayOfInt3, arrayOfInt1);
/*     */         
/* 111 */         HNode[] arrayOfHNode = this.cq;
/* 112 */         setDumbNetPlus(n + 1);
/* 113 */         for (int i4 = 0; i4 < n; i4++) this.cq[i4] = arrayOfHNode[i4];
/* 114 */         this.cq[n].setCqMember(arrayOfInt4);
/* 115 */         this.cq[i1].addHyperLink(n, arrayOfInt3);
/* 116 */         this.cq[n].addHyperLink(i1, arrayOfInt3);
/*     */         
/* 118 */         i -= k;j += k;
/*     */       }
/*     */     }
/*     */     
/* 122 */     for (int k = 0; k < this.cq.length; k++) {
/* 123 */       MATH.qsort(this.cq[k].clq);
/* 124 */       this.cq[k].setLabel("C" + k);
/*     */     }
/* 126 */     setPeerOrder();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public HyperGrafS getRandomSubJt(Random paramRandom, float paramFloat)
/*     */   {
/* 172 */     int i = getNodeCount();
/* 173 */     int j = (int)(paramRandom.nextFloat() * paramFloat * i);
/* 174 */     if (j == 0) { j++;
/*     */     }
/* 176 */     HyperGrafS localHyperGrafS = new HyperGrafS();
/* 177 */     localHyperGrafS = new HyperGrafS(this);
/* 178 */     int k = i - j;
/*     */     
/* 180 */     for (int m = 0; m < k; m++) {
/* 181 */       int n = localHyperGrafS.getNodeCount();
/* 182 */       int i1 = -1;
/*     */       do {
/* 184 */         i1 = (int)(paramRandom.nextFloat() * n);
/* 185 */       } while (localHyperGrafS.getNeighborCount(i1) != 1);
/* 186 */       localHyperGrafS = new HyperGrafS(HyperGraph.delLeafCq(localHyperGrafS, i1));
/*     */     }
/* 188 */     localHyperGrafS.setLabel();
/* 189 */     return localHyperGrafS;
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
/*     */   static HyperGraph unionCq(HyperGraph paramHyperGraph, int paramInt1, int paramInt2)
/*     */   {
/* 224 */     HyperGraph localHyperGraph1 = new HyperGraph(paramHyperGraph);
/*     */     
/* 226 */     for (int i = 0; i < localHyperGraph1.getNeighborCount(paramInt1); i++) {
/* 227 */       int j = localHyperGraph1.getNeighbor(paramInt1, i);
/* 228 */       localHyperGraph1.delNeighbor(j, paramInt1);
/*     */       
/* 230 */       if (j != paramInt2) {
/* 231 */         HLink localHLink = localHyperGraph1.getHyperLinks(paramInt1, j);
/*     */         
/* 233 */         localHLink.setNeighbor(paramInt2);
/* 234 */         localHyperGraph1.addNeighbor(j, localHLink);
/* 235 */         localHLink.setNeighbor(j);
/* 236 */         localHyperGraph1.addNeighbor(paramInt2, localHLink);
/*     */       }
/*     */     }
/*     */     
/* 240 */     i = localHyperGraph1.getNodeCount() - 1;
/* 241 */     HyperGraph localHyperGraph2 = new HyperGraph(i);
/* 242 */     for (int k = 0; k < i; k++) {
/* 243 */       if (k < paramInt1) localHyperGraph2.cq[k] = localHyperGraph1.cq[k]; else
/* 244 */         localHyperGraph2.cq[k] = localHyperGraph1.cq[(k + 1)];
/*     */     }
/* 246 */     if (paramInt1 == i) return localHyperGraph2;
/* 247 */     localHyperGraph2.modifyNodeIndex(paramInt1 + 1, i, -1);
/* 248 */     return localHyperGraph2;
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
/*     */   public static HyperGrafS mergeJt(HyperGrafS paramHyperGrafS1, HyperGrafS paramHyperGrafS2)
/*     */   {
/* 263 */     int i = paramHyperGrafS1.getNodeCount();int j = paramHyperGrafS2.getNodeCount();
/* 264 */     int k = i + j;
/* 265 */     HyperGrafS localHyperGrafS1 = new HyperGrafS(k);
/* 266 */     HyperGrafS localHyperGrafS2 = new HyperGrafS(paramHyperGrafS1);
/* 267 */     HyperGrafS localHyperGrafS3 = new HyperGrafS(paramHyperGrafS2);
/*     */     
/* 269 */     for (int m = 0; m < i; m++) localHyperGrafS1.cq[m] = localHyperGrafS2.cq[m];
/* 270 */     for (m = i; m < k; m++) {
/* 271 */       localHyperGrafS1.cq[m] = localHyperGrafS3.cq[(m - i)];
/* 272 */       localHyperGrafS1.cq[m].modifyNodeIndex(0, j, i);
/*     */     }
/*     */     
/*     */     int n;
/* 276 */     for (m = i; m < k; m++) {
/* 277 */       if (localHyperGrafS1.isMarked(m))
/*     */       {
/* 279 */         n = localHyperGrafS1.getNeighborCount(m);
/* 280 */         int[] arrayOfInt1 = localHyperGrafS1.getNeighbor(m);
/* 281 */         for (int i2 = 0; i2 < n; i2++) {
/* 282 */           if (localHyperGrafS1.isMarked(arrayOfInt1[i2])) {
/* 283 */             localHyperGrafS1.delNeighbor(m, arrayOfInt1[i2]);
/* 284 */             localHyperGrafS1.delNeighbor(arrayOfInt1[i2], m);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 289 */     m = 0;
/* 290 */     while (m == 0) {
/* 291 */       n = 0;
/* 292 */       int i1 = localHyperGrafS1.getNodeCount();
/* 293 */       while ((n < i1) && (!localHyperGrafS1.isMarked(n))) n++;
/* 294 */       if (n == i1) { m = 1; break;
/*     */       }
/* 296 */       localHyperGrafS1.setMark(n, false);
/* 297 */       int[] arrayOfInt2 = localHyperGrafS1.getCqMember(n);
/* 298 */       for (int i3 = 0; i3 < i1; i3++) {
/* 299 */         if (i3 != n) {
/* 300 */           int[] arrayOfInt3 = localHyperGrafS1.getCqMember(i3);
/* 301 */           if (MATH.isSubset(arrayOfInt3, arrayOfInt2)) {
/* 302 */             localHyperGrafS1 = new HyperGrafS(unionCq(localHyperGrafS1, i3, n));
/* 303 */             break;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 308 */     localHyperGrafS1.setPeerOrder();localHyperGrafS1.setLabel();
/* 309 */     return localHyperGrafS1;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static UndirectGraph getGraphUnion(HyperGraph[] paramArrayOfHyperGraph, int paramInt)
/*     */   {
/* 354 */     UndirectGraph localUndirectGraph = new UndirectGraph();
/* 355 */     localUndirectGraph.setDumbNetPlus(paramInt);
/* 356 */     for (int i = 0; i < paramArrayOfHyperGraph.length; i++) {
/* 357 */       int j = paramArrayOfHyperGraph[i].getNodeCount();
/* 358 */       for (int k = 0; k < j; k++) {
/* 359 */         int[] arrayOfInt = paramArrayOfHyperGraph[i].getCqMember(k);
/* 360 */         localUndirectGraph.completeNodeSet(arrayOfInt);
/*     */       }
/*     */     }
/* 363 */     return localUndirectGraph;
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
/*     */   public static boolean isInCluster(int[] paramArrayOfInt, HyperGraph[] paramArrayOfHyperGraph)
/*     */   {
/* 415 */     int i = paramArrayOfHyperGraph.length;
/* 416 */     for (int j = 0; j < i; j++) {
/* 417 */       int k = paramArrayOfHyperGraph[j].getNodeCount();
/* 418 */       for (int m = 0; m < k; m++) {
/* 419 */         int[] arrayOfInt = paramArrayOfHyperGraph[j].getCqMember(m);
/* 420 */         if (MATH.isSubset(paramArrayOfInt, arrayOfInt)) return true;
/*     */       }
/*     */     }
/* 423 */     return false;
/*     */   }
/*     */ }


/* Location:              /home/anali/INAOE/tesis/codigos/topub/exe/ww4.jar!/Network/HyperGrafS.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */