/*     */ package Network;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintWriter;
/*     */ 
/*     */ public class LJoinForest extends HyperGraph
/*     */ {
/*     */   static final int SYSTEM = -1;
/*  10 */   int[] obsvar = null;
/*  11 */   int[][] obsvlu = (int[][])null;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public LJoinForest() {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public LJoinForest(HyperGraph paramHyperGraph)
/*     */   {
/*  35 */     this();
/*  36 */     if ((paramHyperGraph != null) && (paramHyperGraph.cq != null)) {
/*  37 */       setLJoinForestNode(paramHyperGraph);
/*  38 */       setHyperGraphOther(paramHyperGraph);
/*     */     }
/*     */   }
/*     */   
/*     */   public LJoinForest(LJoinForest paramLJoinForest) {
/*  43 */     this();
/*  44 */     if ((paramLJoinForest != null) && (paramLJoinForest.cq != null)) {
/*  45 */       setLJoinForestNode(paramLJoinForest);
/*  46 */       setHyperGraphOther(paramLJoinForest);
/*     */     }
/*     */   }
/*     */   
/*     */   public void setDumbNet(int paramInt)
/*     */   {
/*  52 */     this.cq = new LNode[paramInt];
/*     */   }
/*     */   
/*     */   public void setDumbNetPlus(int paramInt)
/*     */   {
/*  57 */     setDumbNet(paramInt);
/*  58 */     for (int i = 0; i < paramInt; i++) this.cq[i] = new LNode();
/*     */   }
/*     */   
/*     */   void setLJoinForestNode(HyperGraph paramHyperGraph)
/*     */   {
/*  63 */     int i = paramHyperGraph.getNodeCount();
/*  64 */     setDumbNetPlus(i);
/*  65 */     for (int j = 0; j < i; j++) this.cq[j] = new LNode(paramHyperGraph.getHNode(j));
/*     */   }
/*     */   
/*  68 */   void setLJoinForestNode(LJoinForest paramLJoinForest) { int i = paramLJoinForest.getNodeCount();
/*  69 */     setDumbNetPlus(i);
/*  70 */     for (int j = 0; j < i; j++) this.cq[j] = paramLJoinForest.getLNode(j);
/*     */   }
/*     */   
/*     */   LNode getLNode(int paramInt) {
/*  74 */     return (LNode)this.cq[paramInt];
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   int isFullyObserved(int paramInt)
/*     */   {
/*  82 */     if (this.obsvar == null) return -1;
/*  83 */     if (!MATH.member(paramInt, this.obsvar)) return -1;
/*  84 */     int i = 0;
/*  85 */     while (this.obsvar[i] != paramInt) i++;
/*  86 */     if (this.obsvlu[i].length == 1) return this.obsvlu[i][0];
/*  87 */     return -1;
/*     */   }
/*     */   
/*     */   public int[] getCqMemNotFullyObserved(int paramInt)
/*     */   {
/*  92 */     int[] arrayOfInt1 = ((LNode)this.cq[paramInt]).getCqMember();
/*  93 */     int[] arrayOfInt2 = null;
/*  94 */     for (int i = 0; i < arrayOfInt1.length; i++) {
/*  95 */       int j = arrayOfInt1[i];
/*  96 */       if (isFullyObserved(j) >= 0) arrayOfInt2 = MATH.addMember(j, arrayOfInt2);
/*     */     }
/*  98 */     return MATH.setDifference(arrayOfInt1, arrayOfInt2);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setStateCount(int paramInt, int[] paramArrayOfInt)
/*     */   {
/* 105 */     ((LNode)this.cq[paramInt]).setStateCount(paramArrayOfInt);
/*     */   }
/*     */   
/*     */   void setStateCount(int[] paramArrayOfInt) {
/* 109 */     int i = this.cq == null ? 0 : this.cq.length;
/* 110 */     for (int j = 0; j < i; j++) {
/* 111 */       int[] arrayOfInt1 = this.cq[j].getCqMember();
/* 112 */       int k = arrayOfInt1.length;
/* 113 */       int[] arrayOfInt2 = new int[k];
/* 114 */       for (int m = 0; m < k; m++) arrayOfInt2[m] = paramArrayOfInt[arrayOfInt1[m]];
/* 115 */       setStateCount(j, arrayOfInt2);
/*     */     }
/*     */   }
/*     */   
/*     */   int[] getStateCount(int paramInt)
/*     */   {
/* 121 */     return ((LNode)this.cq[paramInt]).getStateCount();
/*     */   }
/*     */   
/*     */   int[] getStateCount(int paramInt, int[] paramArrayOfInt)
/*     */   {
/* 126 */     return ((LNode)this.cq[paramInt]).getStateCount(paramArrayOfInt);
/*     */   }
/*     */   
/*     */   public int getStateSpaceSz()
/*     */   {
/* 131 */     int i = getNodeCount();
/* 132 */     int j = 0;
/* 133 */     for (int k = 0; k < i; k++) {
/* 134 */       int m = 1;
/* 135 */       int[] arrayOfInt = getStateCount(k);
/* 136 */       for (int n = 0; n < arrayOfInt.length; n++) m *= arrayOfInt[n];
/* 137 */       j += m;
/*     */     }
/* 139 */     return j;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static LJoinForest setJoinGraphAsjf(BayesNet paramBayesNet, MarkovNet paramMarkovNet)
/*     */   {
/* 147 */     LJoinForest localLJoinForest = new LJoinForest();
/* 148 */     int i = paramMarkovNet.cqCand.length;
/* 149 */     localLJoinForest.setDumbNetPlus(i);
/* 150 */     localLJoinForest.setJoinGraph(paramMarkovNet);
/*     */     
/* 152 */     for (int j = 0; j < i; j++) {
/* 153 */       arrayOfInt1 = localLJoinForest.getCqMember(j);
/* 154 */       localLJoinForest.setStateCount(j, paramMarkovNet.getStateCount(arrayOfInt1));
/*     */     }
/*     */     
/* 157 */     j = paramMarkovNet.getNodeCount();
/* 158 */     localLJoinForest.setVarCount(j);
/* 159 */     int[] arrayOfInt1 = new int[j];
/* 160 */     int[] arrayOfInt2; for (int k = 0; k < j; k++) {
/* 161 */       arrayOfInt2 = paramBayesNet.getFamily(k);
/* 162 */       arrayOfInt1[k] = localLJoinForest.getCqHome(arrayOfInt2);
/*     */     }
/* 164 */     localLJoinForest.setVarToCq(arrayOfInt1);
/*     */     
/* 166 */     localLJoinForest.setDumbPoten();
/* 167 */     for (k = 0; k < j; k++) {
/* 168 */       arrayOfInt2 = paramBayesNet.getFamily(k);
/* 169 */       float[] arrayOfFloat = paramBayesNet.getCondProb(k);
/* 170 */       int m = arrayOfInt1[k];
/* 171 */       localLJoinForest.addPoten(m, arrayOfInt2, arrayOfFloat);
/*     */     }
/* 173 */     return localLJoinForest;
/*     */   }
/*     */   
/*     */ 
/*     */   static LJoinForest setJoinForestAsjf(MarkovNet paramMarkovNet)
/*     */   {
/* 179 */     paramMarkovNet.setChordalGraph();
/* 180 */     paramMarkovNet.findClique();
/* 181 */     LJoinForest localLJoinForest = new LJoinForest();
/* 182 */     int i = paramMarkovNet.cqCand.length;
/* 183 */     localLJoinForest.setDumbNetPlus(i);
/* 184 */     localLJoinForest.setJoinGraph(paramMarkovNet);
/* 185 */     localLJoinForest.setVarCount(paramMarkovNet.nd.length);
/* 186 */     localLJoinForest.setJoinForestGraph();
/* 187 */     localLJoinForest.assignSepset();
/* 188 */     return localLJoinForest;
/*     */   }
/*     */   
/*     */   public static LJoinForest bnToLjt(BayesNet paramBayesNet)
/*     */   {
/* 193 */     MarkovNet localMarkovNet = MarkovNet.makeMoralGraph(paramBayesNet);
/* 194 */     localMarkovNet.setChordalGraph();
/* 195 */     localMarkovNet.findClique();
/* 196 */     LJoinForest localLJoinForest = setJoinGraphAsjf(paramBayesNet, localMarkovNet);
/* 197 */     localLJoinForest.setJoinForestGraph();
/* 198 */     localLJoinForest.assignSepset();
/* 199 */     return localLJoinForest;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   int getPotenCount(int paramInt)
/*     */   {
/* 206 */     return ((LNode)this.cq[paramInt]).getPotenCount();
/*     */   }
/*     */   
/*     */ 
/*     */   public int[] getPotenCount()
/*     */   {
/* 212 */     int i = getNodeCount();
/* 213 */     int[] arrayOfInt = new int[i];
/* 214 */     for (int j = 0; j < i; j++) arrayOfInt[j] = getPotenCount(j);
/* 215 */     return arrayOfInt;
/*     */   }
/*     */   
/*     */ 
/*     */   int[] getPotenSize(int paramInt)
/*     */   {
/* 221 */     return ((LNode)this.cq[paramInt]).getPotenSize();
/*     */   }
/*     */   
/*     */ 
/*     */   int[][] getPotenSize()
/*     */   {
/* 227 */     int i = getNodeCount();
/* 228 */     int[][] arrayOfInt = new int[i][];
/* 229 */     for (int j = 0; j < i; j++) arrayOfInt[j] = getPotenSize(j);
/* 230 */     return arrayOfInt;
/*     */   }
/*     */   
/*     */   public int getPotSize()
/*     */   {
/* 235 */     int i = getNodeCount();
/* 236 */     int[][] arrayOfInt = getPotenSize();
/* 237 */     int j = 0;
/* 238 */     for (int k = 0; k < i; k++) {
/* 239 */       if (arrayOfInt[k] != null) for (int m = 0; m < arrayOfInt[k].length; m++) j += arrayOfInt[k][m];
/*     */     }
/* 241 */     return j;
/*     */   }
/*     */   
/*     */   public LazyBelSet getPoten(int paramInt)
/*     */   {
/* 246 */     return ((LNode)this.cq[paramInt]).getPoten();
/*     */   }
/*     */   
/*     */   void setDumbPoten() {
/* 250 */     for (int i = 0; i < this.cq.length; i++) ((LNode)this.cq[i]).setDumbPoten();
/*     */   }
/*     */   
/*     */   void setPoten(int paramInt, LazyBelSet paramLazyBelSet)
/*     */   {
/* 255 */     ((LNode)this.cq[paramInt]).setPoten(paramLazyBelSet);
/*     */   }
/*     */   
/*     */ 
/*     */   public void addPoten(int paramInt, int[] paramArrayOfInt, float[] paramArrayOfFloat)
/*     */   {
/* 261 */     ((LNode)this.cq[paramInt]).addPoten(paramArrayOfInt, paramArrayOfFloat);
/*     */   }
/*     */   
/*     */ 
/*     */   public void addPotNoHead(int paramInt, int[] paramArrayOfInt, float[] paramArrayOfFloat)
/*     */   {
/* 267 */     ((LNode)this.cq[paramInt]).addPotNoHead(paramArrayOfInt, paramArrayOfFloat);
/*     */   }
/*     */   
/*     */ 
/*     */   LazyBelSet getBufferPoten(int paramInt1, int paramInt2)
/*     */   {
/* 273 */     return ((LNode)this.cq[paramInt1]).getBufferPoten(paramInt2);
/*     */   }
/*     */   
/*     */   void setBufferPoten()
/*     */   {
/* 278 */     for (int i = 0; i < this.cq.length; i++) { ((LNode)this.cq[i]).setBufferPoten();
/*     */     }
/*     */   }
/*     */   
/*     */   public float[] setProdPotenPlusMsg(int paramInt)
/*     */   {
/* 284 */     return ((LNode)this.cq[paramInt]).setProdPotenPlusMsg((LNode[])this.cq);
/*     */   }
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
/* 296 */     int i = this.varToCq[paramInt];
/* 297 */     if (isFullyObserved(paramInt) >= 0) {
/* 298 */       int j = ((LNode)this.cq[i]).getStateCount(paramInt);
/* 299 */       localObject = new float[j];
/* 300 */       int k = 0;
/* 301 */       while (this.obsvar[k] != paramInt) k++;
/* 302 */       for (int m = 0; m < j; m++) {
/* 303 */         if (MATH.member(m, this.obsvlu[k])) localObject[m] = 1.0F; else
/* 304 */           localObject[m] = 0.0F;
/*     */       }
/* 306 */       return (float[])localObject;
/*     */     }
/*     */     
/* 309 */     float[] arrayOfFloat = setProdPotenPlusMsg(i);
/* 310 */     Object localObject = getCqMemNotFullyObserved(i);
/* 311 */     return ((LNode)this.cq[i]).setMargin(arrayOfFloat, paramInt, (int[])localObject);
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
/*     */   public void enterEvidenceToCq(int paramInt, int[] paramArrayOfInt)
/*     */   {
/* 341 */     int i = paramArrayOfInt.length;
/* 342 */     int[] arrayOfInt1 = null;
/* 343 */     for (int j = 0; j < i; j++) {
/* 344 */       if (paramArrayOfInt[j] != 0) arrayOfInt1 = MATH.appendMember(j, arrayOfInt1);
/*     */     }
/* 346 */     this.obsvar = MATH.appendMember(paramInt, this.obsvar);
/* 347 */     this.obsvlu = UTIL.appendToArray(this.obsvlu, UTIL.getDuplicate(arrayOfInt1));
/*     */     
/* 349 */     j = this.cq.length;
/* 350 */     int i2; int[] arrayOfInt5; if (arrayOfInt1.length > 1) {
/* 351 */       float[] arrayOfFloat1 = new float[i];
/* 352 */       for (int m = 0; m < i; m++) { arrayOfFloat1[m] = paramArrayOfInt[m];
/*     */       }
/* 354 */       int[] arrayOfInt2 = { paramInt };
/* 355 */       for (int i1 = 0; i1 < j; i1++)
/* 356 */         if (isCqMember(paramInt, i1))
/*     */         {
/* 358 */           i2 = getPotenCount(i1);
/* 359 */           for (int i3 = 0; i3 < i2; i3++) {
/* 360 */             arrayOfInt5 = ((LNode)this.cq[i1]).getPotenDomain(i3);
/* 361 */             if (MATH.member(paramInt, arrayOfInt5))
/*     */             {
/* 363 */               int[] arrayOfInt6 = ((LNode)this.cq[i1]).getStateCount(arrayOfInt5);
/* 364 */               float[] arrayOfFloat2 = ((LNode)this.cq[i1]).getPoten(i3);
/* 365 */               arrayOfFloat2 = MATH.productOfTwo(arrayOfInt5, arrayOfFloat2, arrayOfInt2, arrayOfFloat1, arrayOfInt5, arrayOfInt6);
/* 366 */               ((LNode)this.cq[i1]).setPoten(arrayOfFloat2, i3);
/* 367 */               if (MATH.sum(arrayOfFloat2) < 1.0E-7D) { HelpPanel.addHelp("Err:Invalid belief!");
/*     */               }
/* 369 */               if (MATH.member(paramInt, ((LNode)this.cq[i1]).getPotenHead(i3)))
/* 370 */                 ((LNode)this.cq[i1]).belset.btlist[i3].head = null;
/*     */             }
/*     */           }
/*     */         }
/*     */     } else {
/* 375 */       for (int k = 0; k < j; k++) {
/* 376 */         if (isCqMember(paramInt, k))
/*     */         {
/* 378 */           int n = getPotenCount(k);
/* 379 */           int[] arrayOfInt3 = null;
/* 380 */           for (i2 = 0; i2 < n; i2++) {
/* 381 */             int[] arrayOfInt4 = ((LNode)this.cq[k]).getPotenDomain(i2);
/* 382 */             if (MATH.member(paramInt, arrayOfInt4))
/* 383 */               if (arrayOfInt4.length == 1) {
/* 384 */                 arrayOfInt3 = MATH.appendMember(i2, arrayOfInt3);
/*     */               }
/*     */               else {
/* 387 */                 arrayOfInt5 = ((LNode)this.cq[k]).getStateCount(arrayOfInt4);
/* 388 */                 int i4 = 0;
/* 389 */                 while (arrayOfInt4[i4] != paramInt) { i4++;
/*     */                 }
/*     */                 
/* 392 */                 int i5 = 1;
/* 393 */                 for (int i6 = 0; i6 < arrayOfInt4.length; i6++)
/* 394 */                   if (arrayOfInt4[i6] != paramInt) i5 *= arrayOfInt5[i6];
/* 395 */                 float[] arrayOfFloat3 = new float[i5];
/* 396 */                 int i7 = 0;
/* 397 */                 float[] arrayOfFloat4 = ((LNode)this.cq[k]).getPoten(i2);
/* 398 */                 for (int i8 = 0; i8 < arrayOfFloat4.length; i8++) {
/* 399 */                   int[] arrayOfInt7 = MATH.decToMix(i8, arrayOfInt5);
/* 400 */                   if (arrayOfInt7[i4] == arrayOfInt1[0]) arrayOfFloat3[(i7++)] = arrayOfFloat4[i8];
/*     */                 }
/* 402 */                 ((LNode)this.cq[k]).belset.btlist[i2].belief = arrayOfFloat3;
/*     */                 
/*     */ 
/* 405 */                 ((LNode)this.cq[k]).belset.btlist[i2].domain = MATH.delMember(paramInt, arrayOfInt4);
/* 406 */                 if (MATH.member(paramInt, ((LNode)this.cq[k]).getPotenHead(i2)))
/* 407 */                   ((LNode)this.cq[k]).belset.btlist[i2].head = null;
/*     */               } }
/* 409 */           if (arrayOfInt3 != null) { ((LNode)this.cq[k]).belset.delPotential(arrayOfInt3);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   void collectPotential(int paramInt)
/*     */   {
/* 418 */     ((LNode)this.cq[paramInt]).collectPotential(-1, paramInt, (LNode[])this.cq);
/*     */   }
/*     */   
/*     */   void distributePotential(int paramInt)
/*     */   {
/* 423 */     ((LNode)this.cq[paramInt]).distributePotential(-1, paramInt, (LNode[])this.cq);
/*     */   }
/*     */   
/*     */   void unifyPotential(int paramInt)
/*     */   {
/* 428 */     clrBufferPoten();
/* 429 */     collectPotential(paramInt);
/* 430 */     distributePotential(paramInt);
/*     */   }
/*     */   
/* 433 */   public void unifyPotential() { int i = 0;
/* 434 */     unifyPotential(i);
/*     */   }
/*     */   
/*     */ 
/*     */   public void clrBufferPoten()
/*     */   {
/* 440 */     setBufferPoten();
/*     */   }
/*     */   
/*     */ 
/*     */   public void getSet(BayesNet paramBayesNet)
/*     */   {
/* 446 */     setVarCount(paramBayesNet.getNodeCount());
/* 447 */     setVarToCq(paramBayesNet);
/*     */   }
/*     */   
/* 450 */   public void getSet(MarkovNet paramMarkovNet) { setVarCount(paramMarkovNet.getNodeCount());
/* 451 */     setVarToCq(paramMarkovNet);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   void loadNode(BufferedReader paramBufferedReader)
/*     */   {
/* 458 */     int i = this.cq.length;
/*     */     try {
/* 460 */       for (int j = 0; j < i; j++) {
/* 461 */         paramBufferedReader.readLine();
/* 462 */         this.cq[j] = new LNode();
/* 463 */         ((LNode)this.cq[j]).load(paramBufferedReader, j);
/* 464 */         if (this.cq[j] == null) {
/* 465 */           throw new IOException("Unexpected end\tof input!");
/*     */         }
/*     */       }
/*     */     } catch (IOException localIOException) {
/* 469 */       HelpPanel.showError("Unable to complete load: " + localIOException.getMessage());
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public static LJoinForest loadJt(BufferedReader paramBufferedReader)
/*     */   {
/* 476 */     LJoinForest localLJoinForest = new LJoinForest();
/* 477 */     int i = UTIL.loadInt(paramBufferedReader);
/* 478 */     localLJoinForest.setDumbNetPlus(i);
/* 479 */     localLJoinForest.loadNode(paramBufferedReader);
/*     */     
/* 481 */     return localLJoinForest;
/*     */   }
/*     */   
/*     */ 
/*     */   public static LJoinForest load(String paramString)
/*     */   {
/* 487 */     LJoinForest localLJoinForest = new LJoinForest();
/*     */     try {
/* 489 */       BufferedReader localBufferedReader = new BufferedReader(new java.io.FileReader(paramString));
/* 490 */       HelpPanel.addHelp("Loading join tree from\t" + paramString);
/* 491 */       localLJoinForest = loadJt(localBufferedReader);
/* 492 */       localBufferedReader.close();
/*     */     } catch (IOException localIOException) {
/* 494 */       HelpPanel.showError("Unable to load " + paramString);
/*     */     }
/*     */     
/* 497 */     return localLJoinForest;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void saveBelief(PrintWriter paramPrintWriter, BayesNet paramBayesNet)
/*     */   {
/* 504 */     for (int i = 0; i < this.varCount; i++) {
/* 505 */       paramBayesNet.saveLabel(paramPrintWriter, i);
/* 506 */       String[] arrayOfString = paramBayesNet.getState(i);
/* 507 */       float[] arrayOfFloat = getVarMargin(i);
/* 508 */       for (int j = 0; j < arrayOfString.length; j++)
/* 509 */         paramPrintWriter.print("  " + arrayOfString[j] + ": " + arrayOfFloat[j]);
/* 510 */       paramPrintWriter.println();
/*     */     }
/*     */   }
/*     */   
/*     */   public void saveBelief(PrintWriter paramPrintWriter, MarkovNet paramMarkovNet)
/*     */   {
/* 516 */     unifyPotential();
/* 517 */     for (int i = 0; i < this.varCount; i++) {
/* 518 */       paramMarkovNet.saveLabel(paramPrintWriter, i);
/* 519 */       String[] arrayOfString = paramMarkovNet.getState(i);
/* 520 */       float[] arrayOfFloat = getVarMargin(i);
/* 521 */       for (int j = 0; j < arrayOfString.length; j++)
/* 522 */         paramPrintWriter.print("  " + arrayOfString[j] + ": " + arrayOfFloat[j]);
/* 523 */       paramPrintWriter.println();
/*     */     }
/*     */   }
/*     */   
/*     */   public void save(PrintWriter paramPrintWriter)
/*     */   {
/* 529 */     paramPrintWriter.println(this.cq.length + "  #_of_clusters");
/* 530 */     for (int i = 0; i < this.cq.length; i++) {
/* 531 */       paramPrintWriter.println();
/* 532 */       this.cq[i].save(paramPrintWriter, i);
/*     */     }
/*     */   }
/*     */   
/*     */   public void save(String paramString) {
/*     */     try {
/* 538 */       PrintWriter localPrintWriter = new PrintWriter(new java.io.FileWriter(paramString));
/* 539 */       HelpPanel.addHelp("Saving\t" + paramString);
/* 540 */       save(localPrintWriter);
/* 541 */       localPrintWriter.close();
/*     */     } catch (IOException localIOException) {
/* 543 */       HelpPanel.showError("Unable to save " + paramString);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   void showObs()
/*     */   {
/* 550 */     UTIL.showList("obsvar[]:", this.obsvar);
/* 551 */     UTIL.showList("obsvlu[][]:", this.obsvlu);
/*     */   }
/*     */   
/* 554 */   public void showLJoinForest() { System.out.println(" varCount=" + this.varCount);
/* 555 */     UTIL.showList(" varToCq=", this.varToCq);
/* 556 */     for (int i = 0; i < this.cq.length; i++) ((LNode)this.cq[i]).showLNode();
/*     */   }
/*     */ }


/* Location:              /home/anali/INAOE/tesis/codigos/topub/exe/ww4.jar!/Network/LJoinForest.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */