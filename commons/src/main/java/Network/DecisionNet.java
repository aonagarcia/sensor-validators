/*     */ package Network;
/*     */ 
/*     */ import java.awt.Point;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.FileReader;
/*     */ import java.io.FileWriter;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintStream;
/*     */ import java.io.PrintWriter;
/*     */ import java.util.Vector;
/*     */ 
/*     */ public class DecisionNet
/*     */   extends BayesNet
/*     */ {
/*     */   public DecisionNet() {}
/*     */   
/*     */   public DecisionNet(DecisionNet paramDecisionNet)
/*     */   {
/*  19 */     this();
/*  20 */     if ((paramDecisionNet != null) && (paramDecisionNet.nd != null)) setDecisionNet(paramDecisionNet);
/*     */   }
/*     */   
/*     */   public void setDumbNet(int paramInt)
/*     */   {
/*  25 */     this.nd = new INode[paramInt];
/*     */   }
/*     */   
/*     */   public void setDumbNetPlus(int paramInt)
/*     */   {
/*  30 */     setDumbNet(paramInt);
/*  31 */     for (int i = 0; i < paramInt; i++) this.nd[i] = new INode();
/*     */   }
/*     */   
/*     */   void setDecisionNet(DecisionNet paramDecisionNet) {
/*  35 */     int i = paramDecisionNet.getNodeCount();
/*  36 */     setDumbNetPlus(i);
/*  37 */     for (int j = 0; j < i; j++) { this.nd[j] = new INode((INode)paramDecisionNet.nd[j]);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean isValueNode(int paramInt)
/*     */   {
/*  45 */     return ((INode)this.nd[paramInt]).isValueNode();
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean isDecisionNode(int paramInt)
/*     */   {
/*  51 */     return ((INode)this.nd[paramInt]).isDecisionNode();
/*     */   }
/*     */   
/*     */   public boolean isChanceNode(int paramInt)
/*     */   {
/*  56 */     return ((INode)this.nd[paramInt]).isChanceNode();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static DecisionNet addNode(DecisionNet paramDecisionNet, Point paramPoint)
/*     */   {
/*  65 */     DecisionNet localDecisionNet = new DecisionNet();
/*     */     
/*  67 */     int i = paramDecisionNet.getNodeCount();
/*  68 */     localDecisionNet.setDumbNet(i + 1);
/*     */     
/*  70 */     for (int j = 0; j < i; j++) { localDecisionNet.nd[j] = paramDecisionNet.nd[j];
/*     */     }
/*  72 */     localDecisionNet.nd[i] = new INode();
/*  73 */     localDecisionNet.setPos(i, paramPoint);
/*  74 */     localDecisionNet.setLabel(i, new String("var_" + i));
/*  75 */     return localDecisionNet;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static DecisionNet delNode(DecisionNet paramDecisionNet, int paramInt)
/*     */   {
/*  82 */     int i = paramDecisionNet.nd[paramInt].pls == null ? 0 : paramDecisionNet.nd[paramInt].pls.length;
/*  83 */     for (int j = 0; j < i; j++) {
/*  84 */       int k = paramDecisionNet.nd[paramInt].pls[j];
/*  85 */       paramDecisionNet.nd[k].delChild(paramInt);
/*     */     }
/*     */     
/*  88 */     i = paramDecisionNet.nd[paramInt].cls == null ? 0 : paramDecisionNet.nd[paramInt].cls.length;
/*  89 */     for (int j = 0; j < i; j++) {
/*  90 */       int k = paramDecisionNet.nd[paramInt].cls[j];
/*  91 */       ((BNode)paramDecisionNet.nd[k]).delBParent(paramInt);
/*     */     }
/*     */     
/*  94 */     DecisionNet localDecisionNet = new DecisionNet();
/*  95 */     int k = paramDecisionNet.getNodeCount();
/*  96 */     localDecisionNet.setDumbNet(k - 1);
/*     */     
/*  98 */     for (int m = 0; m < k - 1; m++) {
/*  99 */       if (m < paramInt) localDecisionNet.nd[m] = paramDecisionNet.nd[m]; else
/* 100 */         localDecisionNet.nd[m] = paramDecisionNet.nd[(m + 1)];
/*     */     }
/* 102 */     localDecisionNet.modifyNodeIndex(paramInt + 1, k - 1, -1);
/* 103 */     return localDecisionNet;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void addArc(int paramInt1, int paramInt2)
/*     */   {
/* 111 */     if (isValueNode(paramInt2)) {
/* 112 */       ((INode)this.nd[paramInt2]).addParentToValueNode(paramInt1, ((BNode)this.nd[paramInt1]).getStateCount());
/*     */     }
/* 114 */     else if (isDecisionNode(paramInt2)) {
/* 115 */       ((BNode)this.nd[paramInt2]).addBParent(paramInt1, ((BNode)this.nd[paramInt1]).getStateCount());
/* 116 */       toDecisionNode(paramInt2);
/*     */     }
/*     */     else {
/* 119 */       ((BNode)this.nd[paramInt2]).addBParent(paramInt1, ((BNode)this.nd[paramInt1]).getStateCount());
/*     */     }
/* 121 */     this.nd[paramInt1].addChild(paramInt2);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setState(int paramInt, String[] paramArrayOfString)
/*     */   {
/* 131 */     int i = ((BNode)this.nd[paramInt]).getStateCount();
/*     */     
/* 133 */     ((BNode)this.nd[paramInt]).setState(paramArrayOfString);
/*     */     
/* 135 */     if (i != paramArrayOfString.length) {
/* 136 */       if (isChanceNode(paramInt)) ((BNode)this.nd[paramInt]).setCondProb();
/* 137 */       int[] arrayOfInt = this.nd[paramInt].getChild();
/* 138 */       int j = arrayOfInt == null ? 0 : arrayOfInt.length;
/* 139 */       for (int k = 0; k < j; k++) {
/* 140 */         setParentStateCount(arrayOfInt[k]);
/* 141 */         if (isChanceNode(arrayOfInt[k])) {
/* 142 */           ((BNode)this.nd[arrayOfInt[k]]).setCondProb();
/* 143 */         } else if (isValueNode(arrayOfInt[k])) {
/* 144 */           ((INode)this.nd[arrayOfInt[k]]).setUtil();
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public float[] getValue(int paramInt)
/*     */   {
/* 154 */     return ((INode)this.nd[paramInt]).getValue();
/*     */   }
/*     */   
/*     */   public void setUtil(int paramInt, float[] paramArrayOfFloat)
/*     */   {
/* 159 */     ((INode)this.nd[paramInt]).setUtil(paramArrayOfFloat);
/*     */   }
/*     */   
/*     */ 
/*     */   int[] getValueNode()
/*     */   {
/* 165 */     int i = getNodeCount();
/* 166 */     int[] arrayOfInt1 = new int[i];
/* 167 */     int j = 0;
/*     */     
/* 169 */     for (int k = 0; k < i; k++) {
/* 170 */       if (((INode)this.nd[k]).isValueNode()) arrayOfInt1[(j++)] = k;
/*     */     }
/* 172 */     if (j == 0) { return null;
/*     */     }
/* 174 */     int[] arrayOfInt2 = new int[j];
/* 175 */     for (int m = 0; m < j; m++) arrayOfInt2[m] = arrayOfInt1[m];
/* 176 */     return arrayOfInt2;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void toValueNode(int paramInt)
/*     */   {
/* 183 */     ((INode)this.nd[paramInt]).toValueNode();
/*     */   }
/*     */   
/*     */   public void toDecisionNode(int paramInt)
/*     */   {
/* 188 */     ((INode)this.nd[paramInt]).toDecisionNode();
/*     */   }
/*     */   
/*     */   public void toChanceNode(int paramInt)
/*     */   {
/* 193 */     ((INode)this.nd[paramInt]).toChanceNode();
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
/*     */   int[] getDecisionNode()
/*     */   {
/* 207 */     unmarkNode();
/* 208 */     int[] arrayOfInt1 = getTopOrder();
/*     */     
/* 210 */     int i = getNodeCount();
/* 211 */     int[] arrayOfInt2 = new int[i];
/* 212 */     int j = 0;
/* 213 */     for (int k = 0; k < i; k++) {
/* 214 */       m = arrayOfInt1[k];
/* 215 */       if (((INode)this.nd[m]).isDecisionNode()) arrayOfInt2[(j++)] = m;
/*     */     }
/* 217 */     if (j == 0) { return null;
/*     */     }
/* 219 */     int[] arrayOfInt3 = new int[j];
/* 220 */     for (int m = 0; m < j; m++) arrayOfInt3[m] = arrayOfInt2[m];
/* 221 */     return arrayOfInt3;
/*     */   }
/*     */   
/*     */ 
/*     */   void removeDecParent(int[] paramArrayOfInt)
/*     */   {
/* 227 */     for (int i = 0; i < paramArrayOfInt.length; i++) {
/* 228 */       int j = paramArrayOfInt[i];
/* 229 */       int[] arrayOfInt = getParent(j);
/* 230 */       for (int k = 0; k < arrayOfInt.length; k++) delArc(arrayOfInt[k], j);
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean hasDecChild()
/*     */   {
/* 236 */     for (int i = 0; i < this.nd.length; i++) {
/* 237 */       if ((isDecisionNode(i)) && 
/* 238 */         (!((INode)this.nd[i]).hasDecChild()))
/* 239 */         return false;
/*     */     }
/* 241 */     return true;
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
/*     */   void IdToLogicBn()
/*     */   {
/* 254 */     int i = getNodeCount();
/* 255 */     for (int j = 0; j < i; j++) {
/* 256 */       if (((INode)this.nd[j]).isDecisionNode()) {
/* 257 */         ((INode)this.nd[j]).setCondProb();
/*     */       }
/* 259 */       else if (((INode)this.nd[j]).isValueNode()) {
/* 260 */         String[] arrayOfString = { "t", "f" };
/* 261 */         ((INode)this.nd[j]).setState(arrayOfString);
/* 262 */         float[] arrayOfFloat1 = ((INode)this.nd[j]).getUtil();
/* 263 */         int k = arrayOfFloat1.length;
/* 264 */         float[] arrayOfFloat2 = new float[k * 2];
/* 265 */         for (int m = 0; m < k; m++) {
/* 266 */           arrayOfFloat2[(m * 2)] = arrayOfFloat1[m];arrayOfFloat2[(m * 2 + 1)] = (1.0F - arrayOfFloat1[m]);
/*     */         }
/* 268 */         setCondProb(j, arrayOfFloat2);
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
/*     */   public void lowerNodeIndex(int paramInt)
/*     */   {
/* 285 */     int i = this.nd.length;
/* 286 */     int[][] arrayOfInt1 = new int[i][];
/* 287 */     int[][] arrayOfInt2 = new int[i][];
/* 288 */     for (int j = 0; j < i; j++) {
/* 289 */       arrayOfInt1[j] = this.nd[j].getParent();
/* 290 */       arrayOfInt2[j] = getParentStateCount(j);
/*     */     }
/*     */     
/* 293 */     BNode localBNode = (BNode)this.nd[paramInt];
/* 294 */     int[] arrayOfInt3 = arrayOfInt1[paramInt];int[] arrayOfInt4 = arrayOfInt2[paramInt];
/* 295 */     for (int k = paramInt; k > 0; k--) {
/* 296 */       this.nd[k] = this.nd[(k - 1)];arrayOfInt1[k] = arrayOfInt1[(k - 1)];arrayOfInt2[k] = arrayOfInt2[(k - 1)];
/*     */     }
/* 298 */     this.nd[0] = localBNode;arrayOfInt1[0] = arrayOfInt3;arrayOfInt2[0] = arrayOfInt4;
/*     */     
/* 300 */     modifyNodeIndex(paramInt, paramInt, i - paramInt);
/* 301 */     modifyNodeIndex(0, paramInt - 1, 1);
/* 302 */     modifyNodeIndex(i, i, -i);
/*     */     
/*     */ 
/* 305 */     for (k = 1; k < i; k++) {
/* 306 */       if (!isDecisionNode(k))
/*     */       {
/* 308 */         int[] arrayOfInt5 = arrayOfInt1[k];
/* 309 */         arrayOfInt5 = UTIL.moveToSortedAryStart(paramInt, arrayOfInt5);
/* 310 */         if (arrayOfInt5 != null) { float[] arrayOfFloat;
/* 311 */           if (isChanceNode(k)) {
/* 312 */             arrayOfFloat = ((BNode)this.nd[k]).getCondProb();
/* 313 */             int[] arrayOfInt6 = UTIL.appendToArray(arrayOfInt1[k], i);
/* 314 */             int[] arrayOfInt7 = UTIL.appendToArray(arrayOfInt5, i);
/* 315 */             int[] arrayOfInt8 = UTIL.appendToArray(arrayOfInt2[k], getStateCount(k));
/* 316 */             ((BNode)this.nd[k]).setCondProb(MATH.reorderBelief(arrayOfInt6, arrayOfInt8, arrayOfFloat, arrayOfInt7));
/*     */           }
/*     */           else {
/* 319 */             arrayOfFloat = ((INode)this.nd[k]).getValue();
/* 320 */             ((INode)this.nd[k]).setUtil(MATH.reorderBelief(arrayOfInt1[k], arrayOfInt2[k], arrayOfFloat, arrayOfInt5));
/*     */           }
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Vector makeDecision()
/*     */   {
/* 368 */     int[] arrayOfInt1 = getDecisionNode();
/* 369 */     int[] arrayOfInt2 = getValueNode();
/* 370 */     if (arrayOfInt2.length > 1) {
/* 371 */       HelpPanel.showError("More\tthan one value node.");return null;
/*     */     }
/* 373 */     float[] arrayOfFloat1 = ((INode)this.nd[arrayOfInt2[0]]).getVRange();
/*     */     
/*     */ 
/* 376 */     IdToLogicBn();
/* 377 */     BayesNet localBayesNet = new BayesNet(this);
/* 378 */     localBayesNet.addNonForgetArc(arrayOfInt1);
/* 379 */     JoinForest localJoinForest = JoinForest.bnToJt(localBayesNet);
/*     */     
/*     */ 
/* 382 */     int i = arrayOfInt1.length;
/* 383 */     Object localObject1 = localBayesNet.getFamily(arrayOfInt1[(i - 1)]);
/* 384 */     int[] arrayOfInt3 = MATH.sort((int[])localObject1);
/* 385 */     int[] arrayOfInt4 = localJoinForest.getStateCount(localJoinForest.getCqHome(arrayOfInt3), arrayOfInt3);
/*     */     
/* 387 */     float[] arrayOfFloat2 = localJoinForest.getVarMargin(arrayOfInt3);
/* 388 */     arrayOfFloat2 = MATH.reorderBelief(arrayOfInt3, arrayOfInt4, arrayOfFloat2, (int[])localObject1);
/* 389 */     float[] arrayOfFloat3 = localJoinForest.getVarMargin(arrayOfInt2[0]);
/*     */     
/*     */ 
/* 392 */     int[] arrayOfInt5 = { 1, 0 };
/* 393 */     localJoinForest.enterEvidenceToCq(arrayOfInt2[0], arrayOfInt5);
/* 394 */     localJoinForest.enterEvidence1By1(arrayOfInt2[0]);
/* 395 */     float[] arrayOfFloat4 = localJoinForest.getVarMargin(arrayOfInt3);
/* 396 */     float[] arrayOfFloat5 = MATH.reorderBelief(arrayOfInt3, arrayOfInt4, arrayOfFloat4, (int[])localObject1);
/*     */     
/*     */ 
/* 399 */     int[][] arrayOfInt = new int[i][];
/* 400 */     float[][] arrayOfFloat = new float[i][];
/*     */     
/* 402 */     for (int j = i - 1; j >= 0; j--)
/*     */     {
/* 404 */       localObject2 = localBayesNet.getParentStateCount(arrayOfInt1[j]);
/* 405 */       int k = 1;
/* 406 */       for (int m = 0; m < localObject2.length; m++) k *= localObject2[m];
/* 407 */       m = localBayesNet.getStateCount(arrayOfInt1[j]);
/* 408 */       int[] arrayOfInt7 = getPolicy(arrayOfFloat5, k, m);
/* 409 */       int[] arrayOfInt8 = localBayesNet.getParent(arrayOfInt1[j]);
/*     */       
/*     */ 
/* 412 */       float[] arrayOfFloat6 = new float[k];
/* 413 */       if (k == 1) {
/* 414 */         arrayOfFloat6[0] = (arrayOfFloat5[arrayOfInt7[0]] * arrayOfFloat3[0] / arrayOfFloat2[arrayOfInt7[0]]);
/* 415 */         if (j < i - 1) {
/* 416 */           n = localBayesNet.getStateCount(arrayOfInt1[(j + 1)]);
/* 417 */           arrayOfFloat6[0] *= n;
/*     */         }
/* 419 */         arrayOfFloat6[0] = (arrayOfFloat6[0] * arrayOfFloat1[1] + arrayOfFloat1[0]);
/*     */       }
/*     */       else {
/* 422 */         for (n = 0; n < k; n++) {
/* 423 */           float f = arrayOfFloat2[(n * m + arrayOfInt7[n])];
/* 424 */           if (f < 1.0E-7F) {
/* 425 */             arrayOfInt7[n] = -1;
/* 426 */             arrayOfFloat6[n] = (arrayOfFloat1[0] - 1.0F);
/*     */           }
/*     */           else {
/* 429 */             arrayOfFloat6[n] = (arrayOfFloat5[(n * m + arrayOfInt7[n])] * arrayOfFloat3[0] / arrayOfFloat2[(n * m + arrayOfInt7[n])]);
/* 430 */             if (j < i - 1) {
/* 431 */               int i2 = localBayesNet.getStateCount(arrayOfInt1[(j + 1)]);
/* 432 */               arrayOfFloat6[n] *= i2;
/*     */             }
/* 434 */             arrayOfFloat6[n] = (arrayOfFloat6[n] * arrayOfFloat1[1] + arrayOfFloat1[0]);
/*     */           }
/*     */         } }
/* 437 */       arrayOfInt[j] = arrayOfInt7;arrayOfFloat[j] = arrayOfFloat6;
/* 438 */       if (j == 0) {
/*     */         break;
/*     */       }
/* 441 */       for (int n = 0; n < k; n++) {
/* 442 */         for (int i1 = 0; i1 < m; i1++) {
/* 443 */           if (arrayOfInt7[n] != i1) { arrayOfFloat5[(n * m + i1)] = 0.0F;
/*     */           }
/*     */         }
/*     */       }
/* 447 */       int[] arrayOfInt9 = localBayesNet.getFamily(arrayOfInt1[(j - 1)]);
/* 448 */       int[] arrayOfInt10 = UTIL.getSubsetIndex((int[])localObject1, arrayOfInt9);
/* 449 */       int[] arrayOfInt11 = localBayesNet.getFamilyStateCount(arrayOfInt1[j]);
/* 450 */       arrayOfFloat2 = MATH.margin(arrayOfFloat2, arrayOfInt11, arrayOfInt10);
/* 451 */       arrayOfFloat5 = MATH.margin(arrayOfFloat5, arrayOfInt11, arrayOfInt10);
/* 452 */       localObject1 = arrayOfInt9;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 457 */     int[][][] arrayOfInt6 = getExePolicy(localBayesNet, arrayOfInt1, arrayOfInt);
/* 458 */     Object localObject2 = getExeValue(arrayOfInt6, arrayOfFloat);
/*     */     
/* 460 */     Vector localVector = new Vector();
/* 461 */     localVector.addElement(localBayesNet);localVector.addElement(arrayOfInt1);
/* 462 */     localVector.addElement(arrayOfInt6);localVector.addElement(localObject2);
/* 463 */     return localVector;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   static int[] getPolicy(float[] paramArrayOfFloat, int paramInt1, int paramInt2)
/*     */   {
/* 475 */     int[] arrayOfInt = new int[paramInt1];
/* 476 */     for (int i = 0; i < paramInt1; i++) {
/* 477 */       float f = 0.0F;
/* 478 */       for (int j = 0; j < paramInt2; j++) {
/* 479 */         if (f < paramArrayOfFloat[(i * paramInt2 + j)]) {
/* 480 */           f = paramArrayOfFloat[(i * paramInt2 + j)];
/* 481 */           arrayOfInt[i] = j;
/*     */         }
/*     */       }
/*     */     }
/* 485 */     return arrayOfInt;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   static int[][][] getExePolicy(BayesNet paramBayesNet, int[] paramArrayOfInt, int[][] paramArrayOfInt1)
/*     */   {
/* 495 */     int i = paramArrayOfInt.length;
/* 496 */     for (int j = 1; j < i; j++) {
/* 497 */       for (k = 0; k < paramArrayOfInt1[j].length; k++) {
/* 498 */         if (paramArrayOfInt1[j][k] >= 0)
/*     */         {
/*     */ 
/* 501 */           int[] arrayOfInt1 = paramBayesNet.getParentStateCount(paramArrayOfInt[j]);
/* 502 */           int[] arrayOfInt2 = MATH.decToMix(k, arrayOfInt1);
/* 503 */           int[] arrayOfInt3 = paramBayesNet.getParent(paramArrayOfInt[j]);
/* 504 */           int[] arrayOfInt4 = paramBayesNet.getParent(paramArrayOfInt[(j - 1)]);
/*     */           
/* 506 */           int i1 = 0;
/* 507 */           if (arrayOfInt4 != null) {
/* 508 */             int[] arrayOfInt5 = UTIL.getSubsetIndex(arrayOfInt3, arrayOfInt4);
/* 509 */             int[] arrayOfInt6 = new int[arrayOfInt5.length];
/* 510 */             for (int i4 = 0; i4 < arrayOfInt5.length; i4++) { arrayOfInt6[i4] = arrayOfInt2[arrayOfInt5[i4]];
/*     */             }
/* 512 */             int[] arrayOfInt7 = paramBayesNet.getParentStateCount(paramArrayOfInt[(j - 1)]);
/* 513 */             i1 = MATH.mixToDec(arrayOfInt6, arrayOfInt7);
/*     */           }
/*     */           
/*     */ 
/* 517 */           int i2 = 0;
/* 518 */           for (int i3 = 0; i3 < arrayOfInt3.length; i3++) {
/* 519 */             if (arrayOfInt3[i3] == paramArrayOfInt[(j - 1)]) {
/* 520 */               i2 = i3; break;
/*     */             }
/*     */           }
/* 523 */           if (paramArrayOfInt1[(j - 1)][i1] != arrayOfInt2[i2]) paramArrayOfInt1[j][k] = -1;
/*     */         }
/*     */       }
/*     */     }
/* 527 */     int[][][] arrayOfInt = new int[i][][];
/* 528 */     for (int k = 0; k < i; k++) {
/* 529 */       int m = 0;
/* 530 */       for (int n = 0; n < paramArrayOfInt1[k].length; n++) {
/* 531 */         if (paramArrayOfInt1[k][n] >= 0) {
/* 532 */           m++;
/*     */         }
/*     */       }
/* 535 */       arrayOfInt[k] = new int[m][2];
/* 536 */       m = 0;
/* 537 */       for (n = 0; n < paramArrayOfInt1[k].length; n++)
/* 538 */         if (paramArrayOfInt1[k][n] >= 0) {
/* 539 */           arrayOfInt[k][m][0] = n;
/* 540 */           arrayOfInt[k][(m++)][1] = paramArrayOfInt1[k][n];
/*     */         }
/*     */     }
/* 543 */     return arrayOfInt;
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
/*     */   static float[][] getExeValue(int[][][] paramArrayOfInt, float[][] paramArrayOfFloat)
/*     */   {
/* 556 */     int i = paramArrayOfInt.length;
/* 557 */     float[][] arrayOfFloat = new float[i][];
/* 558 */     for (int j = 0; j < i; j++) {
/* 559 */       arrayOfFloat[j] = new float[paramArrayOfInt[j].length];
/* 560 */       for (int k = 0; k < paramArrayOfInt[j].length; k++) {
/* 561 */         arrayOfFloat[j][k] = paramArrayOfFloat[j][paramArrayOfInt[j][k][0]];
/*     */       }
/*     */     }
/* 564 */     return arrayOfFloat;
/*     */   }
/*     */   
/*     */ 
/*     */   public void showExePolicy(Vector paramVector)
/*     */   {
/* 570 */     BayesNet localBayesNet = (BayesNet)paramVector.elementAt(0);
/* 571 */     int[] arrayOfInt1 = (int[])paramVector.elementAt(1);
/* 572 */     int[][][] arrayOfInt = (int[][][])paramVector.elementAt(2);
/* 573 */     float[][] arrayOfFloat = (float[][])paramVector.elementAt(3);
/*     */     
/* 575 */     int i = arrayOfInt1.length;
/* 576 */     for (int j = 0; j < i; j++) {
/* 577 */       String str1 = localBayesNet.getLabel(arrayOfInt1[j]);
/* 578 */       int[] arrayOfInt2 = localBayesNet.getParent(arrayOfInt1[j]);
/*     */       
/* 580 */       if (arrayOfInt2 == null) {
/* 581 */         HelpPanel.addHelp(str1 + "()=" + localBayesNet.getState(arrayOfInt1[j], arrayOfInt[j][0][1]) + " vlu=" + arrayOfFloat[0][0]);
/*     */ 
/*     */       }
/*     */       else
/*     */       {
/* 586 */         String[] arrayOfString = localBayesNet.getParentLabel(arrayOfInt1[j]);
/* 587 */         int[] arrayOfInt3 = localBayesNet.getParentStateCount(arrayOfInt1[j]);
/* 588 */         for (int k = 0; k < arrayOfInt[j].length; k++) {
/* 589 */           String str2 = new String(str1 + "(");
/* 590 */           int[] arrayOfInt4 = MATH.decToMix(arrayOfInt[j][k][0], arrayOfInt3);
/* 591 */           for (int m = 0; m < arrayOfInt2.length; m++) {
/* 592 */             String str4 = localBayesNet.getState(arrayOfInt2[m], arrayOfInt4[m]);
/* 593 */             str2 = new String(str2 + arrayOfString[m] + "=" + str4 + " ");
/*     */           }
/* 595 */           String str3 = localBayesNet.getState(arrayOfInt1[j], arrayOfInt[j][k][1]);
/* 596 */           str2 = new String(str2 + ")= " + str3);
/* 597 */           HelpPanel.addHelp(str2 + "\tvlu=" + arrayOfFloat[j][k]);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public static DecisionNet loadId(BufferedReader paramBufferedReader)
/*     */   {
/* 606 */     DecisionNet localDecisionNet = new DecisionNet();
/* 607 */     int i = localDecisionNet.loadNodeCount(paramBufferedReader);
/* 608 */     localDecisionNet.setDumbNet(i);
/* 609 */     localDecisionNet.loadNode(paramBufferedReader);
/* 610 */     localDecisionNet.setParentStateCount();
/* 611 */     return localDecisionNet;
/*     */   }
/*     */   
/*     */   public static DecisionNet loadId(String paramString) {
/* 615 */     DecisionNet localDecisionNet = null;
/*     */     try {
/* 617 */       BufferedReader localBufferedReader = new BufferedReader(new FileReader(paramString));
/* 618 */       HelpPanel.addHelp("Loading id from " + paramString);
/* 619 */       localDecisionNet = loadId(localBufferedReader);
/* 620 */       localBufferedReader.close();
/*     */     } catch (IOException localIOException) {
/* 622 */       HelpPanel.showError("Unable to load file!");
/*     */     }
/* 624 */     return localDecisionNet;
/*     */   }
/*     */   
/*     */   void loadNode(BufferedReader paramBufferedReader)
/*     */   {
/* 629 */     int i = this.nd.length;
/*     */     try {
/* 631 */       for (int j = 0; j < i; j++) {
/* 632 */         paramBufferedReader.readLine();
/* 633 */         this.nd[j] = INode.loadINode(paramBufferedReader);
/* 634 */         if (this.nd[j] == null) throw new IOException("Unexpected end of input!");
/*     */       }
/*     */     }
/*     */     catch (IOException localIOException) {
/* 638 */       HelpPanel.showError("Unable to complete load: " + localIOException.getMessage());
/*     */     }
/*     */   }
/*     */   
/*     */   public void save(PrintWriter paramPrintWriter)
/*     */   {
/* 644 */     int i = this.nd.length;
/* 645 */     paramPrintWriter.println(i + "  #_of_nodes");
/* 646 */     for (int j = 0; j < i; j++) {
/* 647 */       paramPrintWriter.println();
/* 648 */       ((INode)this.nd[j]).save(paramPrintWriter, j);
/*     */     }
/*     */   }
/*     */   
/*     */   public void save(String paramString)
/*     */   {
/*     */     try {
/* 655 */       PrintWriter localPrintWriter = new PrintWriter(new FileWriter(paramString));
/* 656 */       HelpPanel.addHelp("Saving\t" + paramString);
/* 657 */       save(localPrintWriter);
/* 658 */       localPrintWriter.close();
/*     */     } catch (IOException localIOException) {
/* 660 */       HelpPanel.showError("Unable to save " + paramString);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public void showDecisionNet()
/*     */   {
/* 667 */     for (int i = 0; i < this.nd.length; i++) {
/* 668 */       if (!((INode)this.nd[i]).isDecisionNode()) {
/* 669 */         System.out.print("nd[" + i + "]:");
/* 670 */         ((INode)this.nd[i]).showINode();
/* 671 */         System.out.println();
/*     */       }
/*     */     }
/* 674 */     UTIL.showList("value nodes:", getValueNode());
/* 675 */     UTIL.showList("decision nodes:", getDecisionNode());
/*     */   }
/*     */   
/*     */   static void showPolicy(int[][] paramArrayOfInt)
/*     */   {
/* 680 */     for (int i = 0; i < paramArrayOfInt.length; i++) {
/* 681 */       for (int j = 0; j < paramArrayOfInt[i].length; j++) {
/* 682 */         HelpPanel.addHelp("pol[" + i + "][" + j + "]=" + paramArrayOfInt[i][j]);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   static void showPolicy2(int[][][] paramArrayOfInt)
/*     */   {
/* 690 */     for (int i = 0; i < paramArrayOfInt.length; i++) {
/* 691 */       for (int j = 0; j < paramArrayOfInt[i].length; j++) {
/* 692 */         HelpPanel.addHelp("pol[" + i + "][" + j + "][0,1]=" + paramArrayOfInt[i][j][0] + "," + paramArrayOfInt[i][j][1]);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   void showValue(float[][] paramArrayOfFloat)
/*     */   {
/* 699 */     for (int i = 0; i < paramArrayOfFloat.length; i++) {
/* 700 */       for (int j = 0; j < paramArrayOfFloat[i].length; j++) {
/* 701 */         HelpPanel.addHelp("val[" + i + "][" + j + "]=" + paramArrayOfFloat[i][j]);
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              /home/anali/INAOE/tesis/codigos/topub/exe/ww4.jar!/Network/DecisionNet.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */