/*     */ package Network;
/*     */ 
/*     */ import java.awt.Point;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.PrintStream;
/*     */ import java.io.PrintWriter;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LJoinTreeM
/*     */   extends LJoinForest
/*     */ {
/*     */   static final int SYSTEM = -1;
/*     */   
/*     */   LJoinTreeM() {}
/*     */   
/*     */   LJoinTreeM(HyperGraph paramHyperGraph)
/*     */   {
/*  22 */     this();
/*  23 */     if ((paramHyperGraph != null) && (paramHyperGraph.cq != null)) {
/*  24 */       setLJoinTreeMNode(paramHyperGraph);
/*  25 */       setHyperGraphOther(paramHyperGraph);
/*     */     }
/*     */   }
/*     */   
/*  29 */   LJoinTreeM(LJoinForest paramLJoinForest) { this();
/*  30 */     if ((paramLJoinForest != null) && (paramLJoinForest.cq != null)) {
/*  31 */       setLJoinTreeMNode(paramLJoinForest);
/*  32 */       setHyperGraphOther(paramLJoinForest);
/*     */     }
/*     */   }
/*     */   
/*  36 */   LJoinTreeM(LJoinTreeM paramLJoinTreeM) { this();
/*  37 */     if ((paramLJoinTreeM != null) && (paramLJoinTreeM.cq != null)) {
/*  38 */       setLJoinTreeMNode(paramLJoinTreeM);
/*  39 */       setHyperGraphOther(paramLJoinTreeM);
/*     */     }
/*     */   }
/*     */   
/*     */   public void setDumbNet(int paramInt)
/*     */   {
/*  45 */     this.cq = new WNode[paramInt];
/*     */   }
/*     */   
/*     */   public void setDumbNetPlus(int paramInt)
/*     */   {
/*  50 */     setDumbNet(paramInt);
/*  51 */     for (int i = 0; i < paramInt; i++) this.cq[i] = new WNode();
/*     */   }
/*     */   
/*     */   void setLJoinTreeMNode(HyperGraph paramHyperGraph)
/*     */   {
/*  56 */     int i = paramHyperGraph.getNodeCount();
/*  57 */     setDumbNetPlus(i);
/*  58 */     for (int j = 0; j < i; j++) this.cq[j] = new WNode(paramHyperGraph.getHNode(j));
/*     */   }
/*     */   
/*  61 */   void setLJoinTreeMNode(LJoinForest paramLJoinForest) { int i = paramLJoinForest.getNodeCount();
/*  62 */     setDumbNetPlus(i);
/*  63 */     for (int j = 0; j < i; j++) this.cq[j] = new WNode(paramLJoinForest.getLNode(j));
/*     */   }
/*     */   
/*  66 */   void setLJoinTreeMNode(LJoinTreeM paramLJoinTreeM) { int i = paramLJoinTreeM.getNodeCount();
/*  67 */     setDumbNetPlus(i);
/*  68 */     for (int j = 0; j < i; j++) this.cq[j] = new WNode(paramLJoinTreeM.getWNode(j));
/*     */   }
/*     */   
/*     */   WNode getWNode(int paramInt) {
/*  72 */     return (WNode)this.cq[paramInt];
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   int getMarked()
/*     */   {
/*  82 */     for (int i = 0; i < getNodeCount(); i++) {
/*  83 */       if (isMarked(i)) return i;
/*     */     }
/*  85 */     return -1;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   LazyBelSet getLkgBufPoten(int paramInt)
/*     */   {
/*  93 */     return ((WNode)this.cq[paramInt]).getLkgBufPoten();
/*     */   }
/*     */   
/*     */ 
/*     */   public void setLkgBufPoten()
/*     */   {
/*  99 */     for (int i = 0; i < this.cq.length; i++) ((WNode)this.cq[i]).setLkgBufPoten();
/*     */   }
/*     */   
/*     */   void addLkgBufPoten(int paramInt, LazyBelief paramLazyBelief)
/*     */   {
/* 104 */     ((WNode)this.cq[paramInt]).addLkgBufPoten(paramLazyBelief);
/*     */   }
/*     */   
/*     */   public void addLkgBufPoten(int paramInt, LazyBelSet paramLazyBelSet)
/*     */   {
/* 109 */     ((WNode)this.cq[paramInt]).addLkgBufPoten(paramLazyBelSet);
/*     */   }
/*     */   
/*     */   void addLkgBufPoten(LJoinTreeM paramLJoinTreeM)
/*     */   {
/* 114 */     int i = paramLJoinTreeM.getNodeCount();
/* 115 */     for (int j = 0; j < i; j++) {
/* 116 */       LazyBelSet localLazyBelSet = paramLJoinTreeM.getPoten(j);
/* 117 */       int k = getCqByLabel(paramLJoinTreeM.getLabel(j));
/* 118 */       addLkgBufPoten(k, localLazyBelSet);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setPoten(BayesNet paramBayesNet, int[] paramArrayOfInt)
/*     */   {
/* 130 */     for (int i = 0; i < getNodeCount(); i++)
/* 131 */       setStateCount(i, paramBayesNet.getStateCount(getCqMember(i)));
/* 132 */     this.varCount = paramBayesNet.getNodeCount();
/* 133 */     this.varToCq = new int[this.varCount];
/* 134 */     for (i = 0; i < this.varCount; i++) { this.varToCq[i] = getCqHome(paramBayesNet.getFamily(i));
/*     */     }
/* 136 */     setDumbPoten();
/* 137 */     for (i = 0; i < this.varCount; i++) {
/* 138 */       if (MATH.member(i, paramArrayOfInt)) {
/* 139 */         int[] arrayOfInt = paramBayesNet.getFamily(i);
/* 140 */         float[] arrayOfFloat = paramBayesNet.getCondProb(i);
/* 141 */         int j = this.varToCq[i];
/* 142 */         if (j != -1) { addPoten(j, arrayOfInt, arrayOfFloat);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int[] setPotenRest(BayesNet paramBayesNet, int[] paramArrayOfInt)
/*     */   {
/* 154 */     for (int i = 0; i < getNodeCount(); i++)
/* 155 */       setStateCount(i, paramBayesNet.getStateCount(getCqMember(i)));
/* 156 */     this.varCount = paramBayesNet.getNodeCount();
/* 157 */     this.varToCq = new int[this.varCount];
/* 158 */     for (i = 0; i < this.varCount; i++) { this.varToCq[i] = getCqHome(paramBayesNet.getFamily(i));
/*     */     }
/*     */     
/* 161 */     setDumbPoten();
/* 162 */     int[] arrayOfInt1 = null;
/* 163 */     for (int j = 0; j < this.varCount; j++)
/* 164 */       if (MATH.member(j, paramArrayOfInt)) {
/* 165 */         int k = this.varToCq[j];
/* 166 */         if (k != -1) {
/* 167 */           int[] arrayOfInt3 = paramBayesNet.getFamily(j);
/* 168 */           float[] arrayOfFloat = paramBayesNet.refCondProb(j);
/* 169 */           addPoten(k, arrayOfInt3, arrayOfFloat);
/*     */           
/* 171 */           arrayOfInt1 = MATH.addMember(j, arrayOfInt1);
/*     */         } }
/* 173 */     int[] arrayOfInt2 = MATH.setDifference(paramArrayOfInt, arrayOfInt1);
/* 174 */     return arrayOfInt2;
/*     */   }
/*     */   
/*     */ 
/*     */   public float[] setProdPotenPlusAllMsg(int paramInt)
/*     */   {
/* 180 */     return ((WNode)this.cq[paramInt]).setProdPotenPlusAllMsg((WNode[])this.cq);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   LazyBelSet getPotenPlusAllMsg(int paramInt)
/*     */   {
/* 189 */     return ((WNode)this.cq[paramInt]).getPotenPlusAllMsg((WNode[])this.cq);
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
/*     */   public float[] getVarMargin(int paramInt)
/*     */   {
/* 204 */     int i = this.varToCq[paramInt];
/* 205 */     if (isFullyObserved(paramInt) >= 0) {
/* 206 */       int j = ((LNode)this.cq[i]).getStateCount(paramInt);
/* 207 */       localObject = new float[j];
/* 208 */       int k = 0;
/* 209 */       while (this.obsvar[k] != paramInt) k++;
/* 210 */       for (int m = 0; m < j; m++) {
/* 211 */         if (MATH.member(m, this.obsvlu[k])) localObject[m] = 1.0F; else
/* 212 */           localObject[m] = 0.0F;
/*     */       }
/* 214 */       return (float[])localObject;
/*     */     }
/*     */     
/* 217 */     float[] arrayOfFloat = setProdPotenPlusAllMsg(i);
/* 218 */     Object localObject = getCqMemNotFullyObserved(i);
/* 219 */     return ((LNode)this.cq[i]).setMargin(arrayOfFloat, paramInt, (int[])localObject);
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
/*     */   void setLkgPot(LJoinTreeM paramLJoinTreeM)
/*     */   {
/* 240 */     for (int i = 0; i < getNodeCount(); i++) {
/* 241 */       int[] arrayOfInt1 = getCqMember(i);
/* 242 */       int j = paramLJoinTreeM.getCqByLabel(getLabel(i));
/*     */       
/* 244 */       WNode localWNode = (WNode)paramLJoinTreeM.cq[j];
/* 245 */       LazyBelSet localLazyBelSet = new LazyBelSet(localWNode.belset);
/* 246 */       for (int k = 0; k < localWNode.getNeighborCount(); k++) {
/* 247 */         int m = localWNode.getNeighbor(k);
/* 248 */         if (localWNode.isPeer(m)) localLazyBelSet.unionPotential(localWNode.spinls); else
/* 249 */           localLazyBelSet.unionPotential(((LNode)paramLJoinTreeM.cq[m]).spoutls);
/*     */       }
/* 251 */       localLazyBelSet.unionPotential(localWNode.lkginls);
/*     */       
/*     */ 
/* 254 */       localLazyBelSet.setMargin(localWNode, arrayOfInt1);
/* 255 */       if (!hasPeer(i)) {
/* 256 */         setPoten(i, localLazyBelSet);
/*     */       } else {
/* 258 */         int[] arrayOfInt2 = getSepsetToPeer(i);
/* 259 */         int[] arrayOfInt3 = getStateCount(i);
/* 260 */         localLazyBelSet.divByMargin(arrayOfInt1, arrayOfInt3, arrayOfInt2);
/* 261 */         setPoten(i, localLazyBelSet);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   Point[] getFullyObserved(int[] paramArrayOfInt)
/*     */   {
/* 271 */     int i = paramArrayOfInt.length;
/* 272 */     int[] arrayOfInt = new int[i];
/* 273 */     int j = 0;
/* 274 */     for (int k = 0; k < i; k++) {
/* 275 */       arrayOfInt[k] = isFullyObserved(paramArrayOfInt[k]);
/* 276 */       if (arrayOfInt[k] >= 0) j++;
/*     */     }
/* 278 */     if (j == 0) { return null;
/*     */     }
/* 280 */     Point[] arrayOfPoint = new Point[j];
/* 281 */     int m = 0;
/* 282 */     for (int n = 0; n < i; n++) {
/* 283 */       if (arrayOfInt[n] >= 0) arrayOfPoint[(m++)] = new Point(paramArrayOfInt[n], arrayOfInt[n]);
/*     */     }
/* 285 */     return arrayOfPoint;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   void collectPotential1(int paramInt)
/*     */   {
/* 293 */     ((WNode)this.cq[paramInt]).collectPotential1(-1, paramInt, (WNode[])this.cq);
/*     */   }
/*     */   
/*     */ 
/*     */   void distributePotential1(int paramInt)
/*     */   {
/* 299 */     ((WNode)this.cq[paramInt]).distributePotential1(-1, paramInt, (WNode[])this.cq);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   void unifyPotential1(int paramInt)
/*     */   {
/* 306 */     clrBufferPoten();
/* 307 */     collectPotential1(paramInt);
/* 308 */     distributePotential1(paramInt);
/*     */   }
/*     */   
/*     */   public void unifyPotential1() {
/* 312 */     int i = 0;
/* 313 */     unifyPotential1(i);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static LJoinTreeM load1(String paramString)
/*     */   {
/* 321 */     LJoinForest localLJoinForest = LJoinForest.load(paramString);
/* 322 */     return new LJoinTreeM(localLJoinForest);
/*     */   }
/*     */   
/*     */   void loadLkgTreeWithPoten(BufferedReader paramBufferedReader, int paramInt)
/*     */   {
/* 327 */     ((LNode)this.cq[paramInt]).loadLkgWithPoten(paramBufferedReader);
/*     */   }
/*     */   
/*     */   static LJoinTreeM loadLkgTreeWithPoten(BufferedReader paramBufferedReader) {
/* 331 */     int i = UTIL.loadInt(paramBufferedReader);
/* 332 */     LJoinTreeM localLJoinTreeM = new LJoinTreeM();
/* 333 */     localLJoinTreeM.setDumbNetPlus(i);
/*     */     
/* 335 */     for (int j = 0; j < i; j++) {
/* 336 */       UTIL.skipLine(paramBufferedReader);
/* 337 */       localLJoinTreeM.loadLkgTreeWithPoten(paramBufferedReader, j);
/*     */     }
/* 339 */     return localLJoinTreeM;
/*     */   }
/*     */   
/*     */   void loadInferJT(BufferedReader paramBufferedReader, BayesNet paramBayesNet)
/*     */   {
/* 344 */     int i = UTIL.loadInt(paramBufferedReader);
/* 345 */     setDumbNetPlus(i);
/* 346 */     for (int j = 0; j < i; j++) {
/* 347 */       UTIL.skipLine(paramBufferedReader);
/* 348 */       ((WNode)this.cq[j]).loadInferJTNode(paramBufferedReader, j, paramBayesNet);
/*     */     }
/*     */   }
/*     */   
/*     */   void loadMsgJT(BufferedReader paramBufferedReader, BayesNet paramBayesNet)
/*     */   {
/* 354 */     int i = UTIL.loadInt(paramBufferedReader);
/* 355 */     setDumbNetPlus(i);
/* 356 */     for (int j = 0; j < i; j++) {
/* 357 */       UTIL.skipLine(paramBufferedReader);
/* 358 */       ((WNode)this.cq[j]).loadMsgJTNode(paramBufferedReader, j, paramBayesNet);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   void saveLinkageTree(PrintWriter paramPrintWriter, int paramInt)
/*     */   {
/* 366 */     int i = getNodeCount();
/* 367 */     paramPrintWriter.println(i + "  #_of_lkgs_in_tree_" + paramInt);
/* 368 */     for (int j = 0; j < i; j++) {
/* 369 */       paramPrintWriter.println();
/* 370 */       ((LNode)this.cq[j]).saveLkgWithPoten(paramPrintWriter, j);
/*     */     }
/*     */   }
/*     */   
/*     */   public void saveInferJT(PrintWriter paramPrintWriter)
/*     */   {
/* 376 */     paramPrintWriter.println(this.cq.length + "  #_of_clusters");
/* 377 */     for (int i = 0; i < this.cq.length; i++) {
/* 378 */       paramPrintWriter.println();
/* 379 */       ((WNode)this.cq[i]).saveInferJTNode(paramPrintWriter, i);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   void saveMsgJT(PrintWriter paramPrintWriter, int paramInt1, int paramInt2)
/*     */   {
/* 387 */     int i = getNodeCount();
/* 388 */     paramPrintWriter.println(i + "  #cqs_in_JT_" + paramInt2 + "_of_msg_JF_" + paramInt1);
/* 389 */     for (int j = 0; j < i; j++) {
/* 390 */       paramPrintWriter.println();
/* 391 */       ((WNode)this.cq[j]).saveMsgJTNode(paramPrintWriter, j);
/*     */     }
/*     */     
/* 394 */     for (j = 0; j < i; j++) {
/* 395 */       if (isMarked(j)) {
/* 396 */         paramPrintWriter.println();
/* 397 */         paramPrintWriter.println(j + "  lkg_host");
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   void showLJoinTreeM()
/*     */   {
/* 405 */     System.out.println("  [LJoinTreeM]");
/* 406 */     UTIL.showList(" varToCq=", this.varToCq);
/* 407 */     for (int i = 0; i < this.cq.length; i++) ((WNode)this.cq[i]).showWNode();
/*     */   }
/*     */ }


/* Location:              /home/anali/INAOE/tesis/codigos/topub/exe/ww4.jar!/Network/LJoinTreeM.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */