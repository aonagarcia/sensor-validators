/*     */ package Network;
/*     */ 
/*     */ import java.awt.Rectangle;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.PrintStream;
/*     */ import java.io.PrintWriter;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class HyperGrafM
/*     */   extends HyperGraph
/*     */ {
/*     */   public HyperGrafM() {}
/*     */   
/*     */   public HyperGrafM(int paramInt)
/*     */   {
/*  19 */     setDumbNet(paramInt);
/*     */   }
/*     */   
/*     */   public HyperGrafM(HyperGraph paramHyperGraph) {
/*  23 */     this();
/*  24 */     if ((paramHyperGraph != null) && (paramHyperGraph.cq != null)) {
/*  25 */       setHyperGraphNode(paramHyperGraph);
/*  26 */       setHyperGraphOther(paramHyperGraph);
/*     */     }
/*     */   }
/*     */   
/*     */   public HyperGrafM(HyperGrafM paramHyperGrafM) {
/*  31 */     this();
/*  32 */     if ((paramHyperGrafM != null) && (paramHyperGrafM.cq != null)) {
/*  33 */       setHyperGraphNode(paramHyperGrafM);
/*  34 */       setHyperGraphOther(paramHyperGrafM);
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
/*     */   static HyperGrafM delLeafCq(HyperGrafM paramHyperGrafM, int paramInt)
/*     */   {
/*  52 */     int i = paramHyperGrafM.getNeighbor(paramInt, 0);
/*  53 */     paramHyperGrafM.delNeighbor(i, paramInt);
/*     */     
/*  55 */     int j = paramHyperGrafM.getNodeCount() - 1;
/*  56 */     HyperGrafM localHyperGrafM = new HyperGrafM(j);
/*  57 */     for (int k = 0; k < j; k++) {
/*  58 */       if (k < paramInt) localHyperGrafM.cq[k] = paramHyperGrafM.cq[k]; else
/*  59 */         localHyperGrafM.cq[k] = paramHyperGrafM.cq[(k + 1)];
/*     */     }
/*  61 */     if (paramInt == j) return localHyperGrafM;
/*  62 */     localHyperGrafM.modifyNodeIndex(paramInt + 1, j, -1);
/*  63 */     return localHyperGrafM;
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
/*     */   static HyperGrafM unionCq(HyperGrafM paramHyperGrafM, int paramInt1, int paramInt2)
/*     */   {
/*  78 */     HyperGrafM localHyperGrafM1 = new HyperGrafM(paramHyperGrafM);
/*     */     
/*  80 */     int[] arrayOfInt = paramHyperGrafM.getNeighbor(paramInt1);
/*  81 */     int i = arrayOfInt.length;
/*  82 */     for (int j = 0; j < i; j++) {
/*  83 */       int k = arrayOfInt[j];
/*  84 */       localHyperGrafM1.delNeighbor(k, paramInt1);
/*     */       
/*  86 */       if (k != paramInt2)
/*     */       {
/*  88 */         HLink localHLink1 = paramHyperGrafM.getHyperLinks(paramInt1, k);
/*     */         
/*  90 */         localHLink1.setNeighbor(paramInt2);
/*  91 */         localHyperGrafM1.addNeighbor(k, localHLink1);
/*     */         
/*  93 */         HLink localHLink2 = paramHyperGrafM.getHyperLinks(paramInt1, k);
/*  94 */         localHLink2.setNeighbor(k);
/*  95 */         localHyperGrafM1.addNeighbor(paramInt2, localHLink2);
/*     */       }
/*     */     }
/*     */     
/*  99 */     j = localHyperGrafM1.getNodeCount() - 1;
/* 100 */     HyperGrafM localHyperGrafM2 = new HyperGrafM(j);
/* 101 */     for (int m = 0; m < j; m++) {
/* 102 */       if (m < paramInt1) localHyperGrafM2.cq[m] = localHyperGrafM1.cq[m]; else
/* 103 */         localHyperGrafM2.cq[m] = localHyperGrafM1.cq[(m + 1)];
/*     */     }
/* 105 */     if (paramInt1 == j) return localHyperGrafM2;
/* 106 */     localHyperGrafM2.modifyNodeIndex(paramInt1 + 1, j, -1);
/* 107 */     return localHyperGrafM2;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   void delVarFromCq(int paramInt1, int paramInt2)
/*     */   {
/* 114 */     this.cq[paramInt1].delVarFromCq(paramInt2);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   int delVarFromCq(int[] paramArrayOfInt)
/*     */   {
/* 121 */     for (int i = 0; i < getNodeCount(); i++)
/* 122 */       for (int j = 0; j < getCqSize(i); j++) {
/* 123 */         int k = getCqMember(i, j);
/* 124 */         if (!MATH.member(k, paramArrayOfInt))
/*     */         {
/* 126 */           int m = 1;
/* 127 */           for (int n = 0; n < getNeighborCount(i); n++) {
/* 128 */             int i1 = getNeighbor(i, n);
/* 129 */             if (MATH.member(k, getCqMember(i1))) {
/* 130 */               m = 0; break;
/*     */             }
/*     */           }
/*     */           
/* 134 */           if (m != 0) {
/* 135 */             delVarFromCq(i, k);
/* 136 */             return i;
/*     */           }
/*     */         }
/*     */       }
/* 140 */     return -1;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   int getSupcq(int paramInt)
/*     */   {
/* 148 */     for (int i = 0; i < getNeighborCount(paramInt); i++) {
/* 149 */       int j = getNeighbor(paramInt, i);
/* 150 */       if (MATH.isSubset(getCqMember(paramInt), getCqMember(j))) return j;
/*     */     }
/* 152 */     return -1;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   static HyperGrafM setHostTree(HyperGraph paramHyperGraph, int[] paramArrayOfInt)
/*     */   {
/* 162 */     HyperGrafM localHyperGrafM = new HyperGrafM(paramHyperGraph);
/*     */     int i;
/*     */     do {
/* 165 */       i = 1;
/* 166 */       for (int j = 0; j < localHyperGrafM.getNodeCount(); j++) {
/* 167 */         if (localHyperGrafM.getNeighborCount(j) == 1) {
/* 168 */           int[] arrayOfInt = localHyperGrafM.getSepset(j, 0);
/* 169 */           if (MATH.isSubset(MATH.getIntersection(paramArrayOfInt, localHyperGrafM.getCqMember(j)), arrayOfInt))
/*     */           {
/* 171 */             localHyperGrafM = delLeafCq(localHyperGrafM, j);
/* 172 */             i = 0;
/* 173 */             break;
/*     */           }
/*     */         }
/*     */       }
/* 177 */     } while (i == 0);
/* 178 */     return localHyperGrafM;
/*     */   }
/*     */   
/*     */   public static HyperGrafM setHostTree(HyperGraph paramHyperGraph, int[] paramArrayOfInt, Rectangle paramRectangle)
/*     */   {
/* 183 */     HyperGrafM localHyperGrafM = setHostTree(paramHyperGraph, paramArrayOfInt);
/* 184 */     localHyperGrafM.setPeerOrder();
/* 185 */     localHyperGrafM.assignCqPos(paramRectangle);
/* 186 */     return localHyperGrafM;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   static HyperGrafM setLinkageTree(HyperGrafM paramHyperGrafM, int[] paramArrayOfInt)
/*     */   {
/* 196 */     HyperGrafM localHyperGrafM = new HyperGrafM(paramHyperGrafM);
/*     */     int i;
/*     */     do {
/* 199 */       i = 1;
/* 200 */       int j = localHyperGrafM.delVarFromCq(paramArrayOfInt);
/* 201 */       if (j >= 0) {
/* 202 */         i = 0;
/* 203 */         if (localHyperGrafM.getNodeCount() != 1) {
/* 204 */           int k = localHyperGrafM.getSupcq(j);
/* 205 */           if (k >= 0) localHyperGrafM = unionCq(localHyperGrafM, j, k);
/*     */         }
/* 207 */       } } while (i == 0);
/* 208 */     return localHyperGrafM;
/*     */   }
/*     */   
/*     */   public static HyperGrafM setLinkageTree(HyperGrafM paramHyperGrafM, int[] paramArrayOfInt, Rectangle paramRectangle)
/*     */   {
/* 213 */     HyperGrafM localHyperGrafM = setLinkageTree(paramHyperGrafM, paramArrayOfInt);
/* 214 */     localHyperGrafM.setPeerOrder();
/* 215 */     localHyperGrafM.assignCqPos(paramRectangle);
/*     */     
/*     */ 
/* 218 */     localHyperGrafM.tor = null;
/* 219 */     localHyperGrafM.varCount = 0;
/* 220 */     localHyperGrafM.varToCq = null;
/* 221 */     localHyperGrafM.setMark(false);
/*     */     
/* 223 */     return localHyperGrafM;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   static HyperGrafM loadLinkageTreeTrunk(BufferedReader paramBufferedReader)
/*     */   {
/* 230 */     int i = UTIL.loadInt(paramBufferedReader);
/* 231 */     HyperGrafM localHyperGrafM = new HyperGrafM();
/* 232 */     localHyperGrafM.setDumbNetPlus(i);
/* 233 */     for (int j = 0; j < i; j++) {
/* 234 */       UTIL.skipLine(paramBufferedReader);
/* 235 */       localHyperGrafM.cq[j].loadLinkage(paramBufferedReader);
/*     */     }
/* 237 */     return localHyperGrafM;
/*     */   }
/*     */   
/*     */ 
/*     */   void saveLinkageTree(PrintWriter paramPrintWriter, int paramInt)
/*     */   {
/* 243 */     int i = getNodeCount();
/* 244 */     paramPrintWriter.println(i + "  #_of_lkgs_in_tree_" + paramInt);
/* 245 */     for (int j = 0; j < i; j++) {
/* 246 */       paramPrintWriter.println();
/* 247 */       this.cq[j].saveLinkage(paramPrintWriter, j);
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
/*     */   int[] getSubGraph(int paramInt1, int paramInt2)
/*     */   {
/* 265 */     int[] arrayOfInt1 = { paramInt2 };
/* 266 */     int i = 1;
/* 267 */     int j = 0;
/* 268 */     while (j == 0) {
/* 269 */       for (int k = 0; k < i; k++) {
/* 270 */         int[] arrayOfInt2 = getNeighbor(arrayOfInt1[k]);
/* 271 */         arrayOfInt1 = MATH.union(arrayOfInt1, arrayOfInt2);
/* 272 */         if (MATH.member(paramInt1, arrayOfInt1)) arrayOfInt1 = MATH.delMember(paramInt1, arrayOfInt1);
/*     */       }
/* 274 */       if (arrayOfInt1.length == i) j = 1;
/* 275 */       i = arrayOfInt1.length;
/*     */     }
/* 277 */     return arrayOfInt1;
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
/*     */   static HyperGrafM getSubGraph(HyperGrafM paramHyperGrafM, int paramInt1, int paramInt2)
/*     */   {
/* 309 */     int[] arrayOfInt1 = paramHyperGrafM.getCqMember(paramInt2);
/* 310 */     int[] arrayOfInt2 = paramHyperGrafM.getSubGraph(paramInt1, paramInt2);
/* 311 */     int i = arrayOfInt2.length;
/* 312 */     HyperGrafM localHyperGrafM = new HyperGrafM(i);
/* 313 */     for (int j = 0; j < i; j++) {
/* 314 */       localHyperGrafM.cq[j] = paramHyperGrafM.getHNode(arrayOfInt2[j]);
/* 315 */       int[] arrayOfInt3 = localHyperGrafM.getCqMember(j);
/* 316 */       if (MATH.isEqualSet(arrayOfInt3, arrayOfInt1)) localHyperGrafM.delNeighbor(j, paramInt1);
/*     */     }
/* 318 */     for (j = 0; j < i; j++) {
/* 319 */       for (int k = 0; k < i; k++) localHyperGrafM.modifyNodeIndex(arrayOfInt2[j], arrayOfInt2[j], j - arrayOfInt2[j]);
/*     */     }
/* 321 */     return localHyperGrafM;
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
/*     */   static HyperGrafM addLeafCq(HyperGrafM paramHyperGrafM, int paramInt, int[] paramArrayOfInt)
/*     */   {
/* 354 */     int i = paramHyperGrafM.getNodeCount();
/* 355 */     HyperGrafM localHyperGrafM = new HyperGrafM(i + 1);
/* 356 */     for (int j = 0; j < i; j++) { localHyperGrafM.cq[j] = paramHyperGrafM.cq[j];
/*     */     }
/* 358 */     HNode localHNode = new HNode();
/* 359 */     localHNode.setCqMember(paramArrayOfInt);
/* 360 */     localHyperGrafM.cq[i] = localHNode;
/*     */     
/* 362 */     int[] arrayOfInt = MATH.getIntersection(paramHyperGrafM.getCqMember(paramInt), paramArrayOfInt);
/* 363 */     if (arrayOfInt == null) {
/* 364 */       System.out.println("Err: HyperGrafM.addLeafCq().");return null;
/*     */     }
/* 366 */     localHyperGrafM.cq[paramInt].addHyperLink(i, arrayOfInt);
/* 367 */     localHyperGrafM.cq[i].addHyperLink(paramInt, arrayOfInt);
/* 368 */     return localHyperGrafM;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   static HyperGrafM mergeJT(HyperGrafM paramHyperGrafM1, HyperGrafM paramHyperGrafM2, int[] paramArrayOfInt)
/*     */   {
/* 379 */     int i = paramHyperGrafM1.getNodeCount();int j = paramHyperGrafM2.getNodeCount();
/* 380 */     HyperGrafM localHyperGrafM1 = new HyperGrafM(i + j);
/* 381 */     for (int k = 0; k < i; k++) { localHyperGrafM1.cq[k] = paramHyperGrafM1.cq[k];
/*     */     }
/* 383 */     HyperGrafM localHyperGrafM2 = new HyperGrafM(paramHyperGrafM2);
/* 384 */     localHyperGrafM2.modifyNodeIndex(0, j - 1, i);
/* 385 */     for (int m = i; m < i + j; m++) localHyperGrafM1.cq[m] = localHyperGrafM2.cq[(m - i)];
/* 386 */     m = paramHyperGrafM1.getCqIndex(paramArrayOfInt);
/* 387 */     int n = i + paramHyperGrafM2.getCqIndex(paramArrayOfInt);
/* 388 */     return unionCq(localHyperGrafM1, m, n);
/*     */   }
/*     */   
/*     */ 
/*     */   void saveMsgJT(PrintWriter paramPrintWriter, int paramInt1, int paramInt2)
/*     */   {
/* 394 */     int i = getNodeCount();
/* 395 */     paramPrintWriter.println(i + "  #cqs_in_JT_" + paramInt2 + "_of_msg_JF_" + paramInt1);
/* 396 */     for (int j = 0; j < i; j++) {
/* 397 */       paramPrintWriter.println();
/* 398 */       this.cq[j].saveLinkage(paramPrintWriter, j);
/*     */     }
/*     */     
/* 401 */     for (j = 0; j < i; j++) {
/* 402 */       if (isMarked(j)) {
/* 403 */         paramPrintWriter.println();
/* 404 */         paramPrintWriter.println(j + "  lkg_host");
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              /home/anali/INAOE/tesis/codigos/topub/exe/ww4.jar!/Network/HyperGrafM.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */