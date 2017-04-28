/*     */ package Network;
/*     */ 
/*     */ import java.awt.Point;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.FileReader;
/*     */ import java.io.FileWriter;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintStream;
/*     */ import java.io.PrintWriter;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DesignNet
/*     */   extends BayesNet
/*     */ {
/*     */   public DesignNet() {}
/*     */   
/*     */   public DesignNet(DesignNet paramDesignNet)
/*     */   {
/*  66 */     this();
/*  67 */     if ((paramDesignNet != null) && (paramDesignNet.nd != null)) setDesignNet(paramDesignNet);
/*     */   }
/*     */   
/*     */   public void setDumbNet(int paramInt)
/*     */   {
/*  72 */     this.nd = new ENode[paramInt];
/*     */   }
/*     */   
/*     */   public void setDumbNetPlus(int paramInt)
/*     */   {
/*  77 */     setDumbNet(paramInt);
/*  78 */     for (int i = 0; i < paramInt; i++) this.nd[i] = new ENode();
/*     */   }
/*     */   
/*     */   void setDesignNet(DesignNet paramDesignNet) {
/*  82 */     int i = paramDesignNet.getNodeCount();
/*  83 */     setDumbNetPlus(i);
/*  84 */     for (int j = 0; j < i; j++) { this.nd[j] = new ENode((ENode)paramDesignNet.nd[j]);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean isUtilNode(int paramInt)
/*     */   {
/*  92 */     return ((ENode)this.nd[paramInt]).isUtilNode();
/*     */   }
/*     */   
/*     */   public boolean isDesignNode(int paramInt)
/*     */   {
/*  97 */     return ((ENode)this.nd[paramInt]).isDesignNode();
/*     */   }
/*     */   
/*     */   public boolean isPerfNode(int paramInt)
/*     */   {
/* 102 */     return ((ENode)this.nd[paramInt]).isPerfNode();
/*     */   }
/*     */   
/*     */   public boolean isEnvNode(int paramInt)
/*     */   {
/* 107 */     return ((ENode)this.nd[paramInt]).isEnvNode();
/*     */   }
/*     */   
/*     */ 
/*     */   public void setWeight(int paramInt, float paramFloat)
/*     */   {
/* 113 */     ((ENode)this.nd[paramInt]).setWeight(paramFloat);
/*     */   }
/*     */   
/*     */   public void setWeight(int[] paramArrayOfInt, float[] paramArrayOfFloat) {
/* 117 */     for (int i = 0; i < paramArrayOfInt.length; i++) setWeight(paramArrayOfInt[i], paramArrayOfFloat[i]);
/*     */   }
/*     */   
/*     */   public float getWeight(int paramInt)
/*     */   {
/* 122 */     return ((ENode)this.nd[paramInt]).getWeight();
/*     */   }
/*     */   
/*     */   public float[] getWeight(int[] paramArrayOfInt) {
/* 126 */     if (paramArrayOfInt == null) return null;
/* 127 */     int i = paramArrayOfInt.length;
/* 128 */     float[] arrayOfFloat = new float[i];
/* 129 */     for (int j = 0; j < i; j++) arrayOfFloat[j] = getWeight(paramArrayOfInt[j]);
/* 130 */     return arrayOfFloat;
/*     */   }
/*     */   
/*     */   public float[] getWeight()
/*     */   {
/* 135 */     int i = this.nd.length;
/* 136 */     float[] arrayOfFloat1 = new float[i];
/* 137 */     int j = 0;
/* 138 */     for (int k = 0; k < i; k++) {
/* 139 */       if (isUtilNode(k)) {
/* 140 */         arrayOfFloat1[(j++)] = getWeight(k);
/*     */       }
/*     */     }
/* 143 */     float[] arrayOfFloat2 = new float[j];
/* 144 */     for (int m = 0; m < j; m++) arrayOfFloat2[m] = arrayOfFloat1[m];
/* 145 */     return arrayOfFloat2;
/*     */   }
/*     */   
/*     */   public boolean utilHasRightWeight()
/*     */   {
/* 150 */     float[] arrayOfFloat = getWeight();
/* 151 */     for (int i = 0; i < arrayOfFloat.length; i++) if (arrayOfFloat[i] == 0.0F) { return false;
/*     */       }
/* 153 */     float f = 0.0F;
/* 154 */     for (int j = 0; j < arrayOfFloat.length; j++) f += arrayOfFloat[j];
/* 155 */     return (f < 1.00001F) && (f > 0.99999F);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static DesignNet addNode(DesignNet paramDesignNet, Point paramPoint)
/*     */   {
/* 165 */     DesignNet localDesignNet = new DesignNet();
/*     */     
/* 167 */     int i = paramDesignNet.getNodeCount();
/* 168 */     localDesignNet.setDumbNet(i + 1);
/*     */     
/* 170 */     for (int j = 0; j < i; j++) { localDesignNet.nd[j] = paramDesignNet.nd[j];
/*     */     }
/* 172 */     ENode localENode = new ENode();
/* 173 */     localENode.toEnvFacNode();
/* 174 */     localDesignNet.nd[i] = localENode;
/* 175 */     localDesignNet.setPos(i, paramPoint);
/* 176 */     localDesignNet.setLabel(i, new String("var_" + i));
/* 177 */     return localDesignNet;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static DesignNet delNode(DesignNet paramDesignNet, int paramInt)
/*     */   {
/* 184 */     int i = paramDesignNet.nd[paramInt].pls == null ? 0 : paramDesignNet.nd[paramInt].pls.length;
/* 185 */     for (int j = 0; j < i; j++) {
/* 186 */       k = paramDesignNet.nd[paramInt].pls[j];
/* 187 */       paramDesignNet.nd[k].delChild(paramInt);
/*     */     }
/*     */     
/* 190 */     i = paramDesignNet.nd[paramInt].cls == null ? 0 : paramDesignNet.nd[paramInt].cls.length;
/* 191 */     for (j = 0; j < i; j++) {
/* 192 */       k = paramDesignNet.nd[paramInt].cls[j];
/* 193 */       ((BNode)paramDesignNet.nd[k]).delBParent(paramInt);
/*     */     }
/*     */     
/* 196 */     DesignNet localDesignNet = new DesignNet();
/* 197 */     int k = paramDesignNet.getNodeCount();
/* 198 */     localDesignNet.setDumbNet(k - 1);
/*     */     
/* 200 */     for (int m = 0; m < k - 1; m++) {
/* 201 */       if (m < paramInt) localDesignNet.nd[m] = paramDesignNet.nd[m]; else
/* 202 */         localDesignNet.nd[m] = paramDesignNet.nd[(m + 1)];
/*     */     }
/* 204 */     localDesignNet.modifyNodeIndex(paramInt + 1, k - 1, -1);
/* 205 */     return localDesignNet;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void addArc(int paramInt1, int paramInt2)
/*     */   {
/* 213 */     if (isUtilNode(paramInt2)) {
/* 214 */       ((ENode)this.nd[paramInt2]).addParenttoUtilNode(paramInt1, ((BNode)this.nd[paramInt1]).getStateCount());
/*     */     }
/* 216 */     else if (isDesignNode(paramInt2)) {
/* 217 */       ((BNode)this.nd[paramInt2]).addBParent(paramInt1, ((BNode)this.nd[paramInt1]).getStateCount());
/* 218 */       toDesignNode(paramInt2);
/*     */     }
/*     */     else {
/* 221 */       ((BNode)this.nd[paramInt2]).addBParent(paramInt1, ((BNode)this.nd[paramInt1]).getStateCount());
/*     */     }
/* 223 */     this.nd[paramInt1].addChild(paramInt2);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setState(int paramInt, String[] paramArrayOfString)
/*     */   {
/* 232 */     int i = ((BNode)this.nd[paramInt]).getStateCount();
/* 233 */     ((BNode)this.nd[paramInt]).setState(paramArrayOfString);
/*     */     
/* 235 */     if (i != paramArrayOfString.length) {
/* 236 */       ((BNode)this.nd[paramInt]).setCondProb();
/* 237 */       int[] arrayOfInt = this.nd[paramInt].getChild();
/* 238 */       int j = arrayOfInt == null ? 0 : arrayOfInt.length;
/* 239 */       for (int k = 0; k < j; k++) {
/* 240 */         setParentStateCount(arrayOfInt[k]);
/* 241 */         if (isUtilNode(arrayOfInt[k])) {
/* 242 */           ((ENode)this.nd[arrayOfInt[k]]).setCptUtil();
/*     */         }
/*     */         else {
/* 245 */           ((BNode)this.nd[arrayOfInt[k]]).setCondProb();
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void toUtilNode(int paramInt)
/*     */   {
/* 257 */     setParentStateCount(paramInt);
/* 258 */     ((ENode)this.nd[paramInt]).toUtilNode();
/*     */   }
/*     */   
/*     */   public void toDesignNode(int paramInt)
/*     */   {
/* 263 */     ((ENode)this.nd[paramInt]).toDesignNode();
/*     */   }
/*     */   
/*     */   public void toPerfNode(int paramInt)
/*     */   {
/* 268 */     ((ENode)this.nd[paramInt]).toPerfNode();
/*     */   }
/*     */   
/*     */   public void toEnvFacNode(int paramInt)
/*     */   {
/* 273 */     ((ENode)this.nd[paramInt]).toEnvFacNode();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean hasDesignNode()
/*     */   {
/* 280 */     for (int i = 0; i < this.nd.length; i++) if (isDesignNode(i)) return true;
/* 281 */     return false;
/*     */   }
/*     */   
/*     */   public boolean designHasChild()
/*     */   {
/* 286 */     for (int i = 0; i < this.nd.length; i++) {
/* 287 */       if ((isDesignNode(i)) && 
/* 288 */         (!((ENode)this.nd[i]).designHasChild()))
/* 289 */         return false;
/*     */     }
/* 291 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean designHasRightChild()
/*     */   {
/* 297 */     for (int i = 0; i < this.nd.length; i++)
/* 298 */       if (isDesignNode(i)) {
/* 299 */         int[] arrayOfInt = this.nd[i].getChild();
/* 300 */         for (int j = 0; j < arrayOfInt.length; j++) {
/* 301 */           int k = ((ENode)this.nd[arrayOfInt[j]]).type;
/* 302 */           if ((k != 100) && (k != 109)) return false;
/*     */         }
/*     */       }
/* 305 */     return true;
/*     */   }
/*     */   
/*     */   public boolean designHasRightPa()
/*     */   {
/* 310 */     for (int i = 0; i < this.nd.length; i++)
/* 311 */       if (isDesignNode(i)) {
/* 312 */         int[] arrayOfInt = this.nd[i].getParent();
/* 313 */         if (arrayOfInt != null)
/* 314 */           for (int j = 0; j < arrayOfInt.length; j++) {
/* 315 */             int k = ((ENode)this.nd[arrayOfInt[j]]).type;
/* 316 */             if (k != 100) return false;
/*     */           }
/*     */       }
/* 319 */     return true;
/*     */   }
/*     */   
/*     */   public boolean designHasRightCpt()
/*     */   {
/* 324 */     for (int i = 0; i < this.nd.length; i++)
/* 325 */       if ((isDesignNode(i)) && 
/* 326 */         (getParentCount(i) != 0)) {
/* 327 */         float[] arrayOfFloat = getCondProb(i);
/* 328 */         int j = 0;
/* 329 */         for (int k = 0; k < arrayOfFloat.length; k++) {
/* 330 */           if (arrayOfFloat[k] < 1.0E-6F) j = 1;
/*     */         }
/* 332 */         if (j == 0) return false;
/*     */       }
/* 334 */     return true;
/*     */   }
/*     */   
/*     */   public int[] getDesignNode()
/*     */   {
/* 339 */     int i = getNodeCount();
/* 340 */     int[] arrayOfInt1 = new int[i];
/* 341 */     int j = 0;
/*     */     
/* 343 */     for (int k = 0; k < i; k++) {
/* 344 */       if (((ENode)this.nd[k]).isDesignNode()) arrayOfInt1[(j++)] = k;
/*     */     }
/* 346 */     if (j == 0) { return null;
/*     */     }
/* 348 */     int[] arrayOfInt2 = new int[j];
/* 349 */     for (int m = 0; m < j; m++) arrayOfInt2[m] = arrayOfInt1[m];
/* 350 */     return arrayOfInt2;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean perfHasChild()
/*     */   {
/* 357 */     for (int i = 0; i < this.nd.length; i++) {
/* 358 */       if ((isPerfNode(i)) && 
/* 359 */         (!((ENode)this.nd[i]).perfHasChild()))
/* 360 */         return false;
/*     */     }
/* 362 */     return true;
/*     */   }
/*     */   
/*     */   public boolean perfHasPa()
/*     */   {
/* 367 */     for (int i = 0; i < this.nd.length; i++) {
/* 368 */       if ((isPerfNode(i)) && 
/* 369 */         (!((ENode)this.nd[i]).perfHasPa()))
/* 370 */         return false;
/*     */     }
/* 372 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean perfHasRightChild()
/*     */   {
/* 378 */     for (int i = 0; i < this.nd.length; i++)
/* 379 */       if (isPerfNode(i)) {
/* 380 */         int[] arrayOfInt = this.nd[i].getChild();
/* 381 */         for (int j = 0; j < arrayOfInt.length; j++) {
/* 382 */           int k = ((ENode)this.nd[arrayOfInt[j]]).type;
/* 383 */           if ((k != 109) && (k != 117)) return false;
/*     */         }
/*     */       }
/* 386 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean perfHasRightPa()
/*     */   {
/* 392 */     for (int i = 0; i < this.nd.length; i++)
/* 393 */       if (isPerfNode(i)) {
/* 394 */         int[] arrayOfInt = this.nd[i].getParent();
/* 395 */         for (int j = 0; j < arrayOfInt.length; j++) {
/* 396 */           int k = ((ENode)this.nd[arrayOfInt[j]]).type;
/* 397 */           if (k == 117) return false;
/*     */         }
/*     */       }
/* 400 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean envNotAlone()
/*     */   {
/* 407 */     for (int i = 0; i < this.nd.length; i++) {
/* 408 */       if ((isEnvNode(i)) && 
/* 409 */         (!((ENode)this.nd[i]).envNotAlone()))
/* 410 */         return false;
/*     */     }
/* 412 */     return true;
/*     */   }
/*     */   
/*     */   public boolean envHasRightChild()
/*     */   {
/* 417 */     for (int i = 0; i < this.nd.length; i++)
/* 418 */       if (isEnvNode(i)) {
/* 419 */         int[] arrayOfInt = this.nd[i].getChild();
/* 420 */         if (arrayOfInt != null)
/* 421 */           for (int j = 0; j < arrayOfInt.length; j++) {
/* 422 */             int k = ((ENode)this.nd[arrayOfInt[j]]).type;
/* 423 */             if ((k != 116) && (k != 109)) return false;
/*     */           }
/*     */       }
/* 426 */     return true;
/*     */   }
/*     */   
/*     */   public boolean envHasRightPa()
/*     */   {
/* 431 */     for (int i = 0; i < this.nd.length; i++)
/* 432 */       if (isEnvNode(i)) {
/* 433 */         int[] arrayOfInt = this.nd[i].getParent();
/* 434 */         if (arrayOfInt != null)
/* 435 */           for (int j = 0; j < arrayOfInt.length; j++) {
/* 436 */             int k = ((ENode)this.nd[arrayOfInt[j]]).type;
/* 437 */             if (k != 116) return false;
/*     */           }
/*     */       }
/* 440 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean hasUtilNode()
/*     */   {
/* 447 */     for (int i = 0; i < this.nd.length; i++) if (isUtilNode(i)) return true;
/* 448 */     return false;
/*     */   }
/*     */   
/*     */   public boolean utilNoChild()
/*     */   {
/* 453 */     for (int i = 0; i < this.nd.length; i++) {
/* 454 */       if ((isUtilNode(i)) && 
/* 455 */         (((ENode)this.nd[i]).utilHasChild())) return false;
/*     */     }
/* 457 */     return true;
/*     */   }
/*     */   
/*     */   public boolean utilHasPa()
/*     */   {
/* 462 */     for (int i = 0; i < this.nd.length; i++) {
/* 463 */       if ((isUtilNode(i)) && 
/* 464 */         (!((ENode)this.nd[i]).utilHasPa()))
/* 465 */         return false;
/*     */     }
/* 467 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean utilHasRightPa()
/*     */   {
/* 473 */     for (int i = 0; i < this.nd.length; i++)
/* 474 */       if (isUtilNode(i)) {
/* 475 */         int[] arrayOfInt = this.nd[i].getParent();
/* 476 */         for (int j = 0; j < arrayOfInt.length; j++) {
/* 477 */           int k = ((ENode)this.nd[arrayOfInt[j]]).type;
/* 478 */           if (k != 109) return false;
/*     */         }
/*     */       }
/* 481 */     return true;
/*     */   }
/*     */   
/*     */   public boolean utilHasRightRange()
/*     */   {
/* 486 */     for (int i = 0; i < this.nd.length; i++) {
/* 487 */       if ((isUtilNode(i)) && 
/* 488 */         (!((ENode)this.nd[i]).utilHasRightRange())) return false;
/*     */     }
/* 490 */     return true;
/*     */   }
/*     */   
/*     */   public float[] getUtilFmCptUtil(int paramInt)
/*     */   {
/* 495 */     return ((ENode)this.nd[paramInt]).getUtilFmCptUtil();
/*     */   }
/*     */   
/*     */   public float getUtilFmCptUtil(int paramInt1, int paramInt2) {
/* 499 */     return ((ENode)this.nd[paramInt1]).getUtilFmCptUtil(paramInt2);
/*     */   }
/*     */   
/*     */ 
/*     */   float getUtil(int paramInt, int[] paramArrayOfInt)
/*     */   {
/* 505 */     return ((ENode)this.nd[paramInt]).getCondProb(0, paramArrayOfInt);
/*     */   }
/*     */   
/*     */ 
/*     */   public void setCptUtilFmUtil(int paramInt, float[] paramArrayOfFloat)
/*     */   {
/* 511 */     ((ENode)this.nd[paramInt]).setCptUtilFmUtil(paramArrayOfFloat);
/*     */   }
/*     */   
/*     */ 
/*     */   public int[] getUtilNode()
/*     */   {
/* 517 */     int i = getNodeCount();
/* 518 */     int[] arrayOfInt1 = new int[i];
/* 519 */     int j = 0;
/*     */     
/* 521 */     for (int k = 0; k < i; k++) {
/* 522 */       if (((ENode)this.nd[k]).isUtilNode()) arrayOfInt1[(j++)] = k;
/*     */     }
/* 524 */     if (j == 0) { return null;
/*     */     }
/* 526 */     int[] arrayOfInt2 = new int[j];
/* 527 */     for (int m = 0; m < j; m++) arrayOfInt2[m] = arrayOfInt1[m];
/* 528 */     return arrayOfInt2;
/*     */   }
/*     */   
/*     */   public int getUtilFuncSize(int paramInt)
/*     */   {
/* 533 */     return ((ENode)this.nd[paramInt]).getUtilFuncSize();
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
/*     */   public void lowerNodeIndex(int paramInt)
/*     */   {
/* 550 */     int i = this.nd.length;
/* 551 */     int[][] arrayOfInt1 = new int[i][];
/* 552 */     int[][] arrayOfInt2 = new int[i][];
/* 553 */     for (int j = 0; j < i; j++) {
/* 554 */       arrayOfInt1[j] = this.nd[j].getParent();
/* 555 */       arrayOfInt2[j] = getParentStateCount(j);
/*     */     }
/*     */     
/* 558 */     BNode localBNode = (BNode)this.nd[paramInt];
/* 559 */     int[] arrayOfInt3 = arrayOfInt1[paramInt];int[] arrayOfInt4 = arrayOfInt2[paramInt];
/* 560 */     for (int k = paramInt; k > 0; k--) {
/* 561 */       this.nd[k] = this.nd[(k - 1)];arrayOfInt1[k] = arrayOfInt1[(k - 1)];arrayOfInt2[k] = arrayOfInt2[(k - 1)];
/*     */     }
/* 563 */     this.nd[0] = localBNode;arrayOfInt1[0] = arrayOfInt3;arrayOfInt2[0] = arrayOfInt4;
/*     */     
/* 565 */     modifyNodeIndex(paramInt, paramInt, i - paramInt);
/* 566 */     modifyNodeIndex(0, paramInt - 1, 1);
/* 567 */     modifyNodeIndex(i, i, -i);
/*     */     
/*     */ 
/* 570 */     for (k = 1; k < i; k++) {
/* 571 */       if (!isDesignNode(k))
/*     */       {
/* 573 */         int[] arrayOfInt5 = arrayOfInt1[k];
/* 574 */         arrayOfInt5 = UTIL.moveToSortedAryStart(paramInt, arrayOfInt5);
/* 575 */         if (arrayOfInt5 != null) {
/* 576 */           float[] arrayOfFloat = ((BNode)this.nd[k]).getCondProb();
/* 577 */           int[] arrayOfInt6 = UTIL.appendToArray(arrayOfInt1[k], i);
/* 578 */           int[] arrayOfInt7 = UTIL.appendToArray(arrayOfInt5, i);
/* 579 */           int[] arrayOfInt8 = UTIL.appendToArray(arrayOfInt2[k], getStateCount(k));
/* 580 */           ((BNode)this.nd[k]).setCondProb(MATH.reorderBelief(arrayOfInt6, arrayOfInt8, arrayOfFloat, arrayOfInt7));
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public static DesignNet loadDn(BufferedReader paramBufferedReader)
/*     */   {
/* 589 */     DesignNet localDesignNet = new DesignNet();
/* 590 */     int i = localDesignNet.loadNodeCount(paramBufferedReader);
/* 591 */     localDesignNet.setDumbNet(i);
/* 592 */     localDesignNet.loadNode(paramBufferedReader);
/* 593 */     localDesignNet.setParentStateCount();
/* 594 */     return localDesignNet;
/*     */   }
/*     */   
/*     */   public static DesignNet loadDn(String paramString) {
/* 598 */     DesignNet localDesignNet = null;
/*     */     try {
/* 600 */       BufferedReader localBufferedReader = new BufferedReader(new FileReader(paramString));
/* 601 */       HelpPanel.addHelp("Loading dn from " + paramString);
/* 602 */       localDesignNet = loadDn(localBufferedReader);
/* 603 */       localBufferedReader.close();
/*     */     } catch (IOException localIOException) {
/* 605 */       HelpPanel.showError("Unable to load file!");
/*     */     }
/* 607 */     return localDesignNet;
/*     */   }
/*     */   
/*     */   void loadNode(BufferedReader paramBufferedReader)
/*     */   {
/* 612 */     int i = this.nd.length;
/*     */     try {
/* 614 */       for (int j = 0; j < i; j++) {
/* 615 */         paramBufferedReader.readLine();
/* 616 */         this.nd[j] = ENode.loadENode(paramBufferedReader);
/* 617 */         if (this.nd[j] == null) throw new IOException("Unexpected end of input!");
/*     */       }
/*     */     }
/*     */     catch (IOException localIOException) {
/* 621 */       HelpPanel.showError("Unable to complete load: " + localIOException.getMessage());
/*     */     }
/*     */   }
/*     */   
/*     */   public void save(PrintWriter paramPrintWriter)
/*     */   {
/* 627 */     int i = this.nd.length;
/* 628 */     paramPrintWriter.println(i + "  #_of_nodes");
/* 629 */     for (int j = 0; j < i; j++) {
/* 630 */       paramPrintWriter.println();
/* 631 */       ((ENode)this.nd[j]).save(paramPrintWriter, j);
/*     */     }
/*     */   }
/*     */   
/*     */   public void save(String paramString)
/*     */   {
/*     */     try {
/* 638 */       PrintWriter localPrintWriter = new PrintWriter(new FileWriter(paramString));
/* 639 */       HelpPanel.addHelp("Saving " + paramString);
/* 640 */       save(localPrintWriter);
/* 641 */       localPrintWriter.close();
/*     */     } catch (IOException localIOException) {
/* 643 */       HelpPanel.showError("Unable to save " + paramString);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public void showDesignNet()
/*     */   {
/* 650 */     for (int i = 0; i < this.nd.length; i++)
/*     */     {
/* 652 */       System.out.print("nd[" + i + "]:");
/* 653 */       ((ENode)this.nd[i]).showENode();
/*     */       
/* 655 */       System.out.println();
/*     */     }
/*     */   }
/*     */ }


/* Location:              /home/anali/INAOE/tesis/codigos/topub/exe/ww4.jar!/Network/DesignNet.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */