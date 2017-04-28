/*     */ package Network;
/*     */ 
/*     */ import java.awt.Point;
/*     */ import java.awt.Rectangle;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintWriter;
/*     */ 
/*     */ public class JoinForest extends HyperGrafM
/*     */ {
/*     */   static final int SYSTEM = -1;
/*     */   
/*     */   public JoinForest() {}
/*     */   
/*     */   public JoinForest(HyperGraph paramHyperGraph)
/*     */   {
/*  17 */     this();
/*  18 */     if ((paramHyperGraph != null) && (paramHyperGraph.cq != null)) {
/*  19 */       setJoinForestNode(paramHyperGraph);
/*  20 */       setHyperGraphOther(paramHyperGraph);
/*     */     }
/*     */   }
/*     */   
/*  24 */   public JoinForest(JoinForest paramJoinForest) { this();
/*  25 */     if ((paramJoinForest != null) && (paramJoinForest.cq != null)) {
/*  26 */       setJoinForestNode(paramJoinForest);
/*  27 */       setHyperGraphOther(paramJoinForest);
/*     */     }
/*     */   }
/*     */   
/*     */   public void setDumbNet(int paramInt)
/*     */   {
/*  33 */     this.cq = new CNode[paramInt];
/*     */   }
/*     */   
/*     */   public void setDumbNetPlus(int paramInt)
/*     */   {
/*  38 */     setDumbNet(paramInt);
/*  39 */     for (int i = 0; i < paramInt; i++) this.cq[i] = new CNode();
/*     */   }
/*     */   
/*     */   void setJoinForestNode(HyperGraph paramHyperGraph)
/*     */   {
/*  44 */     int i = paramHyperGraph.getNodeCount();
/*  45 */     setDumbNetPlus(i);
/*  46 */     for (int j = 0; j < i; j++) this.cq[j] = new CNode(paramHyperGraph.getHNode(j));
/*     */   }
/*     */   
/*  49 */   void setJoinForestNode(JoinForest paramJoinForest) { int i = paramJoinForest.getNodeCount();
/*  50 */     setDumbNetPlus(i);
/*  51 */     for (int j = 0; j < i; j++) this.cq[j] = paramJoinForest.getCNode(j);
/*     */   }
/*     */   
/*     */   CNode getCNode(int paramInt) {
/*  55 */     return new CNode((CNode)this.cq[paramInt]);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setStateCount(int paramInt, int[] paramArrayOfInt)
/*     */   {
/*  62 */     ((CNode)this.cq[paramInt]).setStateCount(paramArrayOfInt);
/*     */   }
/*     */   
/*     */   void setStateCount(int[] paramArrayOfInt) {
/*  66 */     int i = this.cq == null ? 0 : this.cq.length;
/*  67 */     for (int j = 0; j < i; j++) {
/*  68 */       int[] arrayOfInt1 = this.cq[j].getCqMember();
/*  69 */       int k = arrayOfInt1.length;
/*  70 */       int[] arrayOfInt2 = new int[k];
/*  71 */       for (int m = 0; m < k; m++) arrayOfInt2[m] = paramArrayOfInt[arrayOfInt1[m]];
/*  72 */       setStateCount(j, arrayOfInt2);
/*     */     }
/*     */   }
/*     */   
/*     */   int[] getStateCount(int paramInt)
/*     */   {
/*  78 */     return ((CNode)this.cq[paramInt]).getStateCount();
/*     */   }
/*     */   
/*     */   int[] getStateCount(int paramInt, int[] paramArrayOfInt)
/*     */   {
/*  83 */     return ((CNode)this.cq[paramInt]).getStateCount(paramArrayOfInt);
/*     */   }
/*     */   
/*     */   int[] getCqSpaceSize()
/*     */   {
/*  88 */     int i = getNodeCount();
/*  89 */     int[] arrayOfInt1 = new int[i];
/*  90 */     for (int j = 0; j < i; j++) {
/*  91 */       int[] arrayOfInt2 = getStateCount(j);
/*  92 */       arrayOfInt1[j] = 1;
/*  93 */       for (int k = 0; k < arrayOfInt2.length; k++) arrayOfInt1[j] *= arrayOfInt2[k];
/*     */     }
/*  95 */     return arrayOfInt1;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static int getStateSpaceSize(HyperGraph paramHyperGraph, int[] paramArrayOfInt)
/*     */   {
/* 102 */     JoinForest localJoinForest = new JoinForest(paramHyperGraph);
/* 103 */     localJoinForest.setStateCount(paramArrayOfInt);
/* 104 */     int[] arrayOfInt = localJoinForest.getCqSpaceSize();
/* 105 */     int i = 0;
/* 106 */     for (int j = 0; j < arrayOfInt.length; j++) i += arrayOfInt[j];
/* 107 */     return i;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public int getPotSize()
/*     */   {
/* 114 */     int i = getNodeCount();
/* 115 */     int j = 0;
/* 116 */     for (int k = 0; k < i; k++) j += ((CNode)this.cq[k]).getPotSize();
/* 117 */     return j;
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
/*     */   public JoinForest[] splitJoinTree(int[][] paramArrayOfInt, DirectGraph paramDirectGraph)
/*     */   {
/* 133 */     HyperGraph[] arrayOfHyperGraph = new HyperGraph(this).splitJoinTree(paramArrayOfInt);
/*     */     
/* 135 */     int i = arrayOfHyperGraph.length;
/* 136 */     JoinForest[] arrayOfJoinForest = new JoinForest[i];
/* 137 */     for (int j = 0; j < i; j++) {
/* 138 */       arrayOfJoinForest[j] = new JoinForest(arrayOfHyperGraph[j]);
/* 139 */       arrayOfJoinForest[j].setVarCount();
/* 140 */       int[] arrayOfInt1 = arrayOfJoinForest[j].getDomain();
/* 141 */       arrayOfJoinForest[j].setVarToCq(paramDirectGraph, arrayOfInt1);
/* 142 */       arrayOfJoinForest[j].setPeerOrder();
/*     */     }
/*     */     
/* 145 */     for (j = 0; j < i; j++) {
/* 146 */       int k = arrayOfJoinForest[j].getNodeCount();
/* 147 */       for (int m = 0; m < k; m++) {
/* 148 */         int[] arrayOfInt2 = arrayOfJoinForest[j].getCqMember(m);
/* 149 */         int n = getCqIndex(arrayOfInt2);
/* 150 */         arrayOfJoinForest[j].setStateCount(m, getStateCount(n));
/* 151 */         arrayOfJoinForest[j].setBelief(m, getBelief(n));
/*     */       }
/* 153 */       arrayOfJoinForest[j].setSepsetBelief();
/*     */     }
/*     */     
/* 156 */     return arrayOfJoinForest;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static JoinForest setJoinGraphAsjf(BayesNet paramBayesNet, MarkovNet paramMarkovNet)
/*     */   {
/* 165 */     JoinForest localJoinForest = new JoinForest();
/* 166 */     int i = paramMarkovNet.cqCand.length;
/* 167 */     localJoinForest.setDumbNetPlus(i);
/* 168 */     localJoinForest.setJoinGraph(paramMarkovNet);
/* 169 */     for (int j = 0; j < i; j++) {
/* 170 */       arrayOfInt1 = localJoinForest.getCqMember(j);
/* 171 */       localJoinForest.setStateCount(j, paramMarkovNet.getStateCount(arrayOfInt1));
/*     */     }
/*     */     
/* 174 */     j = paramMarkovNet.getNodeCount();
/* 175 */     localJoinForest.setVarCount(j);
/* 176 */     int[] arrayOfInt1 = new int[j];
/* 177 */     int[] arrayOfInt2; for (int k = 0; k < j; k++) {
/* 178 */       arrayOfInt2 = paramBayesNet.getFamily(k);
/* 179 */       arrayOfInt1[k] = localJoinForest.getCqHome(arrayOfInt2);
/*     */     }
/* 181 */     localJoinForest.setVarToCq(arrayOfInt1);
/*     */     
/* 183 */     localJoinForest.setDumbBelief();
/* 184 */     for (k = 0; k < j; k++) {
/* 185 */       arrayOfInt2 = paramBayesNet.getFamily(k);
/* 186 */       float[] arrayOfFloat = paramBayesNet.getCondProb(k);
/* 187 */       int m = arrayOfInt1[k];
/* 188 */       localJoinForest.setBeliefProduct(m, arrayOfInt2, arrayOfFloat);
/*     */     }
/*     */     
/* 191 */     return localJoinForest;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static JoinForest setJtFmSkeleton(MarkovNet paramMarkovNet)
/*     */   {
/* 200 */     JoinForest localJoinForest = new JoinForest();
/* 201 */     int i = paramMarkovNet.cqCand.length;
/* 202 */     localJoinForest.setDumbNetPlus(i);
/* 203 */     localJoinForest.setJoinGraph(paramMarkovNet);
/* 204 */     for (int j = 0; j < i; j++) {
/* 205 */       int[] arrayOfInt = localJoinForest.getCqMember(j);
/* 206 */       localJoinForest.setStateCount(j, paramMarkovNet.getStateCount(arrayOfInt));
/*     */     }
/* 208 */     localJoinForest.setDumbBelief();
/* 209 */     return localJoinForest;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static JoinForest setJoinGraphAsjf(BayesNet paramBayesNet, HyperGraph paramHyperGraph)
/*     */   {
/* 217 */     JoinForest localJoinForest = new JoinForest(paramHyperGraph);
/* 218 */     int i = localJoinForest.getNodeCount();
/* 219 */     for (int j = 0; j < i; j++) {
/* 220 */       arrayOfInt1 = localJoinForest.getCqMember(j);
/* 221 */       localJoinForest.setStateCount(j, paramBayesNet.getStateCount(arrayOfInt1));
/*     */     }
/*     */     
/* 224 */     j = paramBayesNet.getNodeCount();
/* 225 */     localJoinForest.setVarCount(j);
/* 226 */     int[] arrayOfInt1 = new int[j];
/* 227 */     int[] arrayOfInt2; for (int k = 0; k < j; k++) {
/* 228 */       arrayOfInt2 = paramBayesNet.getFamily(k);
/* 229 */       arrayOfInt1[k] = localJoinForest.getCqHome(arrayOfInt2);
/*     */     }
/* 231 */     localJoinForest.setVarToCq(arrayOfInt1);
/*     */     
/* 233 */     localJoinForest.setDumbBelief();
/* 234 */     for (k = 0; k < j; k++) {
/* 235 */       arrayOfInt2 = paramBayesNet.getFamily(k);
/* 236 */       float[] arrayOfFloat = paramBayesNet.getCondProb(k);
/* 237 */       int m = arrayOfInt1[k];
/* 238 */       localJoinForest.setBeliefProduct(m, arrayOfInt2, arrayOfFloat);
/*     */     }
/*     */     
/* 241 */     return localJoinForest;
/*     */   }
/*     */   
/*     */ 
/*     */   public static JoinForest setJoinForestAsjf(MarkovNet paramMarkovNet)
/*     */   {
/* 247 */     paramMarkovNet.setChordalGraph();
/* 248 */     paramMarkovNet.findClique();
/* 249 */     JoinForest localJoinForest = new JoinForest();
/* 250 */     int i = paramMarkovNet.cqCand.length;
/* 251 */     localJoinForest.setDumbNetPlus(i);
/* 252 */     localJoinForest.setJoinGraph(paramMarkovNet);
/* 253 */     localJoinForest.setVarCount(paramMarkovNet.nd.length);
/* 254 */     localJoinForest.setJoinForestGraph();
/* 255 */     localJoinForest.assignSepset();
/* 256 */     return localJoinForest;
/*     */   }
/*     */   
/* 259 */   public static JoinForest setJoinForestAsjf(UndirectGraph paramUndirectGraph) { ChordalGraph localChordalGraph = new ChordalGraph(paramUndirectGraph);
/* 260 */     localChordalGraph.setChordalGraph();
/* 261 */     localChordalGraph.findClique();
/* 262 */     JoinForest localJoinForest = new JoinForest();
/* 263 */     int i = localChordalGraph.cqCand.length;
/* 264 */     localJoinForest.setDumbNetPlus(i);
/* 265 */     localJoinForest.setJoinGraph(localChordalGraph);
/* 266 */     localJoinForest.setVarCount(localChordalGraph.nd.length);
/* 267 */     localJoinForest.setJoinForestGraph();
/* 268 */     localJoinForest.assignSepset();
/* 269 */     return localJoinForest;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   static JoinForest setJoinForestAsjf(ChordalGraph paramChordalGraph, int[] paramArrayOfInt)
/*     */   {
/* 277 */     paramChordalGraph.setChordalGraph();
/* 278 */     paramChordalGraph.findClique();
/* 279 */     JoinForest localJoinForest = new JoinForest();
/* 280 */     int i = paramChordalGraph.cqCand.length;
/* 281 */     localJoinForest.setDumbNetPlus(i);
/* 282 */     localJoinForest.setJoinGraph(paramChordalGraph);
/* 283 */     localJoinForest.setVarCount(paramChordalGraph.nd.length);
/* 284 */     localJoinForest.setJoinForestGraph();
/* 285 */     localJoinForest.assignSepset();
/* 286 */     localJoinForest.setStateCount(paramArrayOfInt);
/* 287 */     return localJoinForest;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static JoinForest setJoinTree(MarkovNL paramMarkovNL, LnEnv paramLnEnv, Point[] paramArrayOfPoint, Rectangle paramRectangle)
/*     */   {
/* 298 */     JoinForest localJoinForest = setJoinForestAsjf(paramMarkovNL);
/* 299 */     localJoinForest.assignCqPos(paramRectangle);
/* 300 */     int i = localJoinForest.cq.length;
/* 301 */     int m; for (int j = 0; j < i; j++) {
/* 302 */       int[] arrayOfInt1 = localJoinForest.getCqMember(j);
/* 303 */       m = arrayOfInt1.length;
/*     */       
/* 305 */       int[] arrayOfInt2 = new int[m];
/* 306 */       for (int i1 = 0; i1 < m; i1++) arrayOfInt2[i1] = paramMarkovNL.getStateCount(arrayOfInt1[i1]);
/* 307 */       localJoinForest.setStateCount(j, arrayOfInt2);
/*     */     }
/*     */     
/* 310 */     String str = new String(paramLnEnv.getPath() + ".pre");
/* 311 */     for (int k = 0; k < i; k++) {
/* 312 */       ((CNode)localJoinForest.cq[k]).setBelief(MATH.fmargin(str, paramMarkovNL.getDataSize(), paramMarkovNL.getStateCount(), localJoinForest.cq[k].getCqMember()));
/*     */     }
/*     */     
/* 315 */     if (paramArrayOfPoint != null) {
/* 316 */       for (k = 0; k < paramArrayOfPoint.length; k++) {
/* 317 */         m = paramArrayOfPoint[k].x;int n = paramArrayOfPoint[k].y;int[] arrayOfInt3 = { m, n };
/* 318 */         int i2 = 0;
/* 319 */         while (!MATH.isEqualSet(arrayOfInt3, localJoinForest.getCqMember(i2))) { i2++;
/*     */         }
/* 321 */         float[] arrayOfFloat1 = localJoinForest.getBelief(i2);
/* 322 */         int[] arrayOfInt4 = localJoinForest.getStateCount(i2);
/* 323 */         int[] arrayOfInt5 = { 0 };int[] arrayOfInt6 = { 1 };
/* 324 */         float[] arrayOfFloat2 = MATH.margin(arrayOfFloat1, arrayOfInt4, arrayOfInt5);
/* 325 */         float[] arrayOfFloat3 = MATH.margin(arrayOfFloat1, arrayOfInt4, arrayOfInt6);
/* 326 */         arrayOfInt5[0] = m;arrayOfInt6[0] = n;
/* 327 */         localJoinForest.setBelief(i2, MATH.productOfTwo(arrayOfInt5, arrayOfFloat2, arrayOfInt6, arrayOfFloat3, arrayOfInt3, arrayOfInt4));
/*     */       }
/*     */     }
/* 330 */     return localJoinForest;
/*     */   }
/*     */   
/*     */ 
/*     */   public static JoinForest bnToJt(BayesNet paramBayesNet)
/*     */   {
/* 336 */     MarkovNet localMarkovNet = MarkovNet.makeMoralGraph(paramBayesNet);
/* 337 */     localMarkovNet.setChordalGraph();
/* 338 */     localMarkovNet.findClique();
/* 339 */     JoinForest localJoinForest = setJoinGraphAsjf(paramBayesNet, localMarkovNet);
/* 340 */     localJoinForest.setJoinForest(new Rectangle(500, 500));
/* 341 */     localJoinForest.initBelief();
/* 342 */     return localJoinForest;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   int getParaCount()
/*     */   {
/* 352 */     int[] arrayOfInt = getCqSpaceSize();
/* 353 */     int i = 0;
/* 354 */     for (int j = 0; j < arrayOfInt.length; j++) { i += arrayOfInt[j];
/*     */     }
/* 356 */     j = getNodeCount();
/* 357 */     int k = 0;
/* 358 */     for (int m = 0; m < j; m++) {
/* 359 */       k += ((CNode)this.cq[m]).getSepsetSpaceSize();
/*     */     }
/* 361 */     return i - k - getJoinTreeCount();
/*     */   }
/*     */   
/*     */   public void setDumbBelief() {
/* 365 */     for (int i = 0; i < this.cq.length; i++) ((CNode)this.cq[i]).setDumbBelief();
/*     */   }
/*     */   
/*     */   public float[] getBelief(int paramInt)
/*     */   {
/* 370 */     return ((CNode)this.cq[paramInt]).getBelief();
/*     */   }
/*     */   
/*     */   void setBelief(int paramInt, float[] paramArrayOfFloat)
/*     */   {
/* 375 */     ((CNode)this.cq[paramInt]).setBelief(paramArrayOfFloat);
/*     */   }
/*     */   
/*     */ 
/*     */   public void setBeliefProduct(int paramInt, int[] paramArrayOfInt, float[] paramArrayOfFloat)
/*     */   {
/* 381 */     ((CNode)this.cq[paramInt]).setBeliefProduct(paramArrayOfInt, paramArrayOfFloat);
/*     */   }
/*     */   
/*     */   public void setDumbSepsetBelief()
/*     */   {
/* 386 */     for (int i = 0; i < this.cq.length; i++) ((CNode)this.cq[i]).setDumbSepsetBelief();
/*     */   }
/*     */   
/*     */   float[] getSepsetBelief(int paramInt)
/*     */   {
/* 391 */     return ((CNode)this.cq[paramInt]).getSepsetBelief();
/*     */   }
/*     */   
/*     */   public void setSepsetBelief()
/*     */   {
/* 396 */     for (int i = 0; i < this.cq.length; i++) { ((CNode)this.cq[i]).setSepsetBelief();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/* 401 */   public float[] getVarMargin(int paramInt) { return ((CNode)this.cq[this.varToCq[paramInt]]).getVarMargin(paramInt); }
/*     */   
/*     */   public float[] getVarMargin(int[] paramArrayOfInt) {
/* 404 */     int i = getCqHome(paramArrayOfInt);
/* 405 */     return ((CNode)this.cq[i]).getVarMargin(paramArrayOfInt);
/*     */   }
/*     */   
/*     */   void normalizeBelief()
/*     */   {
/* 410 */     for (int i = 0; i < this.cq.length; i++) ((CNode)this.cq[i]).normalizeBelief();
/*     */   }
/*     */   
/*     */   void collectEvidence(int paramInt)
/*     */   {
/* 415 */     ((CNode)this.cq[paramInt]).collectEvidence(-1, paramInt, (CNode[])this.cq);
/*     */   }
/*     */   
/*     */   void distributeEvidence(int paramInt)
/*     */   {
/* 420 */     ((CNode)this.cq[paramInt]).distributeEvidence(-1, paramInt, (CNode[])this.cq);
/*     */   }
/*     */   
/*     */   void unifyBelief(int paramInt)
/*     */   {
/* 425 */     collectEvidence(paramInt);
/* 426 */     distributeEvidence(paramInt);
/*     */   }
/*     */   
/* 429 */   public void unifyBelief() { int i = 0;
/* 430 */     unifyBelief(i);
/*     */   }
/*     */   
/*     */ 
/*     */   public void initBelief()
/*     */   {
/* 436 */     setDumbSepsetBelief();
/* 437 */     unifyBelief();
/* 438 */     normalizeBelief();
/*     */   }
/*     */   
/*     */ 
/*     */   public void getSet(BayesNet paramBayesNet)
/*     */   {
/* 444 */     setSepsetBelief();
/* 445 */     setVarCount(paramBayesNet.getNodeCount());
/* 446 */     setVarToCq(paramBayesNet);
/*     */   }
/*     */   
/* 449 */   public void getSet(MarkovNet paramMarkovNet) { setSepsetBelief();
/* 450 */     setVarCount(paramMarkovNet.getNodeCount());
/* 451 */     setVarToCq(paramMarkovNet);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void enterEvidenceToCq(int paramInt, int[] paramArrayOfInt)
/*     */   {
/* 458 */     int i = paramArrayOfInt.length;
/* 459 */     float[] arrayOfFloat = new float[i];
/* 460 */     for (int j = 0; j < i; j++) { arrayOfFloat[j] = paramArrayOfInt[j];
/*     */     }
/* 462 */     int[] arrayOfInt = { paramInt };
/* 463 */     ((CNode)this.cq[this.varToCq[paramInt]]).setBeliefProduct(arrayOfInt, arrayOfFloat);
/*     */   }
/*     */   
/*     */ 
/*     */   public void enterEvidenceToCq(int paramInt1, int paramInt2)
/*     */   {
/* 469 */     ((CNode)this.cq[this.varToCq[paramInt1]]).setBeliefProduct(paramInt1, paramInt2);
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean enterVluWithCaution(int paramInt1, int paramInt2)
/*     */   {
/* 475 */     ((CNode)this.cq[this.varToCq[paramInt1]]).setBeliefProduct(paramInt1, paramInt2);
/* 476 */     if (((CNode)this.cq[this.varToCq[paramInt1]]).isImpossible()) return false;
/* 477 */     return true;
/*     */   }
/*     */   
/*     */   public void enterEvidence1By1(int paramInt)
/*     */   {
/* 482 */     int i = this.varToCq[paramInt];
/* 483 */     ((CNode)this.cq[i]).distributeEvidence(-1, i, (CNode[])this.cq);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   float getEntropyByJpd(int[] paramArrayOfInt, float[] paramArrayOfFloat)
/*     */   {
/* 491 */     float f = 0.0F;
/* 492 */     for (int i = 0; i < this.cq.length; i++) {
/* 493 */       float[] arrayOfFloat1 = MATH.margin(paramArrayOfFloat, paramArrayOfInt, this.cq[i].getCqMember());
/* 494 */       f += MATH.getEntropyByJpd(arrayOfFloat1);
/* 495 */       if (!this.cq[i].isCenter()) {
/* 496 */         float[] arrayOfFloat2 = MATH.margin(paramArrayOfFloat, paramArrayOfInt, this.cq[i].getSepsetToPeer());
/* 497 */         f -= MATH.getEntropyByJpd(arrayOfFloat2);
/*     */       }
/*     */     }
/* 500 */     return f;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   float getEntropyByData(int[] paramArrayOfInt, String paramString, int paramInt)
/*     */   {
/* 507 */     float f = 0.0F;
/* 508 */     for (int i = 0; i < this.cq.length; i++) {
/* 509 */       float[] arrayOfFloat1 = MATH.fmargin(paramString, paramInt, paramArrayOfInt, this.cq[i].getCqMember());
/*     */       
/* 511 */       f += MATH.getEntropyByJpd(arrayOfFloat1);
/* 512 */       if (!this.cq[i].isCenter())
/*     */       {
/* 514 */         float[] arrayOfFloat2 = MATH.fmargin(paramString, paramInt, paramArrayOfInt, this.cq[i].getSepsetToPeer());
/* 515 */         f -= MATH.getEntropyByJpd(arrayOfFloat2);
/*     */       }
/*     */     }
/* 518 */     return f;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   void loadNode(BufferedReader paramBufferedReader)
/*     */   {
/* 525 */     int i = this.cq.length;
/*     */     try {
/* 527 */       for (int j = 0; j < i; j++) {
/* 528 */         paramBufferedReader.readLine();
/* 529 */         this.cq[j] = CNode.load(paramBufferedReader, j);
/* 530 */         if (this.cq[j] == null) throw new IOException("Unexpected end of input!");
/*     */       }
/*     */     }
/*     */     catch (IOException localIOException) {
/* 534 */       HelpPanel.showError("Unable to complete load: " + localIOException.getMessage());
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
/*     */   public void load(BufferedReader paramBufferedReader)
/*     */   {
/* 550 */     int i = UTIL.loadInt(paramBufferedReader);
/* 551 */     setDumbNetPlus(i);
/* 552 */     loadNode(paramBufferedReader);
/*     */   }
/*     */   
/*     */   public static JoinForest load(String paramString) {
/* 556 */     JoinForest localJoinForest = new JoinForest();
/*     */     try {
/* 558 */       BufferedReader localBufferedReader = new BufferedReader(new java.io.FileReader(paramString));
/* 559 */       HelpPanel.addHelp("Loading join tree from " + paramString);
/* 560 */       localJoinForest.load(localBufferedReader);
/* 561 */       localBufferedReader.close();
/*     */     } catch (IOException localIOException) {
/* 563 */       HelpPanel.showError("Unable to load " + paramString);
/*     */     }
/* 565 */     return localJoinForest;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void saveBelief(PrintWriter paramPrintWriter, BayesNet paramBayesNet)
/*     */   {
/* 572 */     for (int i = 0; i < this.varCount; i++) {
/* 573 */       paramBayesNet.saveLabel(paramPrintWriter, i);
/* 574 */       String[] arrayOfString = paramBayesNet.getState(i);
/* 575 */       float[] arrayOfFloat = getVarMargin(i);
/* 576 */       for (int j = 0; j < arrayOfString.length; j++) paramPrintWriter.print(" " + arrayOfString[j] + ": " + arrayOfFloat[j]);
/* 577 */       paramPrintWriter.println();
/*     */     }
/*     */   }
/*     */   
/*     */   public void saveBelief(PrintWriter paramPrintWriter, MarkovNet paramMarkovNet)
/*     */   {
/* 583 */     for (int i = 0; i < this.varCount; i++) {
/* 584 */       paramMarkovNet.saveLabel(paramPrintWriter, i);
/* 585 */       String[] arrayOfString = paramMarkovNet.getState(i);
/* 586 */       float[] arrayOfFloat = getVarMargin(i);
/* 587 */       for (int j = 0; j < arrayOfString.length; j++) paramPrintWriter.print(" " + arrayOfString[j] + ": " + arrayOfFloat[j]);
/* 588 */       paramPrintWriter.println();
/*     */     }
/*     */   }
/*     */   
/*     */   public void save(PrintWriter paramPrintWriter)
/*     */   {
/* 594 */     paramPrintWriter.println(this.cq.length + "  #_of_clusters");
/* 595 */     for (int i = 0; i < this.cq.length; i++) {
/* 596 */       paramPrintWriter.println();
/* 597 */       ((CNode)this.cq[i]).save(paramPrintWriter, i);
/*     */     }
/*     */   }
/*     */   
/*     */   public void save(String paramString) {
/*     */     try {
/* 603 */       PrintWriter localPrintWriter = new PrintWriter(new java.io.FileWriter(paramString));
/* 604 */       HelpPanel.addHelp("Saving " + paramString);
/* 605 */       save(localPrintWriter);
/* 606 */       localPrintWriter.close();
/*     */     } catch (IOException localIOException) {
/* 608 */       HelpPanel.showError("Unable to save " + paramString);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/* 614 */   float[] bdrBt = null;
/* 615 */   int tick = -1;
/*     */   
/*     */   public float[] getFstBorderBelief()
/*     */   {
/* 619 */     if (this.cq[this.bdrCq[0]].equalCqMember(this.bdr[0])) {
/* 620 */       return ((CNode)this.cq[this.bdrCq[0]]).getBelief();
/*     */     }
/* 622 */     return ((CNode)this.cq[this.bdrCq[0]]).getVarMargin(this.bdr[0]);
/*     */   }
/*     */   
/*     */   public float[] getSndBorderBelief()
/*     */   {
/* 627 */     if (this.cq[this.bdrCq[1]].equalCqMember(this.bdr[1])) {
/* 628 */       return ((CNode)this.cq[this.bdrCq[1]]).getBelief();
/*     */     }
/* 630 */     return ((CNode)this.cq[this.bdrCq[1]]).getVarMargin(this.bdr[1]);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   void setFstBorderBelief()
/*     */   {
/* 637 */     if (this.cq[this.bdrCq[0]].equalCqMember(this.bdr[0])) {
/* 638 */       ((CNode)this.cq[this.bdrCq[0]]).setBelief(this.bdrBt);
/*     */     } else {
/* 640 */       ((CNode)this.cq[this.bdrCq[0]]).setBelief(MATH.calibration(((CNode)this.cq[this.bdrCq[0]]).getBelief(), this.cq[this.bdrCq[0]].getCqMember(), ((CNode)this.cq[this.bdrCq[0]]).getStateCount(), this.bdrBt, this.bdr[0]));
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   void copyBelief(JoinForest paramJoinForest)
/*     */   {
/* 648 */     for (int i = 0; i < this.cq.length; i++) {
/* 649 */       ((CNode)this.cq[i]).setBelief(((CNode)paramJoinForest.cq[i]).getBelief());
/* 650 */       if (this.cq[i].hasPeer()) {
/* 651 */         ((CNode)this.cq[i]).setSepsetBelief(((CNode)paramJoinForest.cq[i]).getSepsetBelief());
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public void saveBelief(PrintWriter paramPrintWriter, String paramString, int paramInt, char[] paramArrayOfChar, float[] paramArrayOfFloat)
/*     */   {
/* 659 */     if (paramInt == 0) paramPrintWriter.println("Time = " + this.tick);
/* 660 */     paramPrintWriter.print("[" + paramString + "," + paramInt + "]  ");
/* 661 */     for (int i = 0; i < paramArrayOfFloat.length; i++) paramPrintWriter.print(paramArrayOfChar[i] + ": " + paramArrayOfFloat[i] + "  ");
/* 662 */     paramPrintWriter.println();
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
/*     */   public void dynamicInference(java.awt.Frame paramFrame, Bridge paramBridge, JoinForest paramJoinForest, BayesNet paramBayesNet, int[][][] paramArrayOfInt, boolean paramBoolean)
/*     */   {
/* 675 */     this.bdrBt = paramJoinForest.getFstBorderBelief();
/* 676 */     int i = paramArrayOfInt.length;
/*     */     
/* 678 */     for (int j = 0; j < i; j++) {
/* 679 */       HelpPanel.addHelp("Time = " + j);
/* 680 */       copyBelief(paramJoinForest);
/* 681 */       setFstBorderBelief();
/* 682 */       for (int k = 0; k < paramArrayOfInt[j].length; k++) {
/* 683 */         enterEvidenceToCq(paramArrayOfInt[j][k][0], paramArrayOfInt[j][k][1]);
/* 684 */         paramBayesNet.setObserved(paramArrayOfInt[j][k][0]);
/*     */       }
/*     */       
/* 687 */       unifyBelief();
/* 688 */       this.bdrBt = getSndBorderBelief();
/*     */       
/* 690 */       this.tick = j;
/* 691 */       paramBridge.showNet();
/* 692 */       if (paramBoolean) {
/* 693 */         NextDialog localNextDialog = new NextDialog(paramFrame, this, "Press Ok.", "Ok", 10, 10);
/*     */         
/* 695 */         localNextDialog.setVisible(true);
/*     */       }
/* 697 */       for (int m = 0; m < paramArrayOfInt[j].length; m++) {
/* 698 */         paramBayesNet.setObserved(paramArrayOfInt[j][m][0], false);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public void showJoinForest()
/*     */   {
/* 706 */     System.out.println("  [JoinForest]");
/* 707 */     showHyperGraph();
/* 708 */     for (int i = 0; i < this.cq.length; i++) {
/* 709 */       UTIL.showList("cq" + i + " sz: ", getStateCount(i));
/* 710 */       UTIL.showList("cq" + i + " bfls: ", getBelief(i));
/* 711 */       UTIL.showList("cq" + i + " spls: ", getSepsetBelief(i));
/*     */     }
/*     */   }
/*     */ }


/* Location:              /home/anali/INAOE/tesis/codigos/topub/exe/ww4.jar!/Network/JoinForest.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */