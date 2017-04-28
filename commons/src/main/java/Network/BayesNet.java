/*     */ package Network;
/*     */ 
/*     */ import java.awt.Point;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.FileReader;
/*     */ import java.io.FileWriter;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintStream;
/*     */ import java.io.PrintWriter;
/*     */ import java.util.Random;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BayesNet
/*     */   extends DTGraph
/*     */ {
/*     */   public static void main(String[] paramArrayOfString)
/*     */   {
/*  19 */     setRandomBn(paramArrayOfString);
/*     */   }
/*     */   
/*     */   static void setRandomBn(String[] paramArrayOfString) {
/*  23 */     System.out.println("java Network.BayesNet #nodes maxParent #root path\n\tuser_defined_node_label\n\t#_1st_few_nodes_whose_label/dimen_to_be_set\n\tlabels... dimens...");
/*     */     
/*     */ 
/*     */ 
/*  27 */     if (paramArrayOfString.length < 4) { System.exit(1);
/*     */     }
/*  29 */     int i = Integer.parseInt(paramArrayOfString[0]);
/*  30 */     int j = Integer.parseInt(paramArrayOfString[1]);
/*  31 */     int k = Integer.parseInt(paramArrayOfString[2]);
/*  32 */     String str1 = paramArrayOfString[4];
/*  33 */     int m = 0;
/*  34 */     if (paramArrayOfString.length >= 8) { m = Integer.parseInt(paramArrayOfString[5]);
/*     */     }
/*     */     
/*  37 */     BayesNet localBayesNet = new BayesNet(new DirectGraph(i, j, k));
/*  38 */     if (localBayesNet.nd != null) {
/*  39 */       localBayesNet.setPos(450, 400);
/*  40 */       for (int n = 0; n < localBayesNet.getNodeCount(); n++) {
/*  41 */         if ((m > 0) && (n < m)) localBayesNet.setLabel(n, paramArrayOfString[(n + 6)]); else {
/*  42 */           localBayesNet.setLabel(n, str1 + n);
/*     */         }
/*  44 */         int i1 = 0;
/*  45 */         if ((m > 0) && (n < m)) { i1 = Integer.parseInt(paramArrayOfString[(n + 6 + m)]);
/*     */         } else {
/*  47 */           localObject = new Random();
/*  48 */           i1 = MATH.getRandomInt((Random)localObject, 2, 4);
/*     */         }
/*  50 */         Object localObject = new String[i1];
/*  51 */         for (int i2 = 0; i2 < i1; i2++) localObject[i2] = new String("" + i2);
/*  52 */         localBayesNet.setState(n, (String[])localObject);
/*     */       }
/*     */       
/*  55 */       localBayesNet.initExpn();
/*  56 */       localBayesNet.setRandCondProb();
/*     */       
/*  58 */       String str2 = new String(paramArrayOfString[3]);
/*     */       try {
/*  60 */         PrintWriter localPrintWriter = new PrintWriter(new FileWriter(str2));
/*  61 */         System.out.println("Saving the current network into " + str2);
/*  62 */         localBayesNet.save(localPrintWriter);
/*  63 */         localPrintWriter.close();
/*  64 */       } catch (IOException localIOException) { System.out.println(localIOException.getMessage()); }
/*  65 */       System.out.println("Saving completed.");
/*     */     } else {
/*  67 */       System.out.println("Could not generate graph");
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public BayesNet() {}
/*     */   
/*     */   public BayesNet(BayesNet paramBayesNet)
/*     */   {
/*  76 */     this();
/*  77 */     if ((paramBayesNet != null) && (paramBayesNet.nd != null)) setBayesNet(paramBayesNet);
/*     */   }
/*     */   
/*     */   BayesNet(MixNet paramMixNet, String paramString) {
/*  81 */     this();
/*  82 */     if ((paramMixNet != null) && (paramMixNet.nd != null)) setBayesNet(paramMixNet, paramString);
/*     */   }
/*     */   
/*     */   public BayesNet(DirectGraph paramDirectGraph) {
/*  86 */     this();
/*  87 */     int i = paramDirectGraph.getNodeCount();
/*  88 */     if (i > 0) {
/*  89 */       setDumbNet(i);
/*  90 */       for (int j = 0; j < i; j++) this.nd[j] = new BNode(paramDirectGraph.nd[j]);
/*     */     } else {
/*  92 */       this.nd = null;
/*     */     }
/*     */   }
/*     */   
/*     */   public void setDumbNet(int paramInt) {
/*  97 */     this.nd = new BNode[paramInt];
/*     */   }
/*     */   
/*     */   public void setDumbNetPlus(int paramInt)
/*     */   {
/* 102 */     setDumbNet(paramInt);
/* 103 */     for (int i = 0; i < paramInt; i++) this.nd[i] = new BNode();
/*     */   }
/*     */   
/*     */   void setBayesNet(BayesNet paramBayesNet) {
/* 107 */     int i = paramBayesNet.getNodeCount();
/* 108 */     setDumbNetPlus(i);
/*     */     
/* 110 */     for (int j = 0; j < i; j++) this.nd[j] = new BNode(paramBayesNet.getBNode(j));
/*     */   }
/*     */   
/*     */   void setBayesNet(MixNet paramMixNet, String paramString)
/*     */   {
/* 115 */     int i = paramMixNet.getNodeCount();
/* 116 */     setDumbNetPlus(i);
/* 117 */     for (int j = 0; j < i; j++) {
/* 118 */       this.nd[j] = new BNode(paramMixNet.getXNode(j));
/*     */     }
/* 120 */     setParentStateCount();
/*     */     
/* 122 */     int[] arrayOfInt1 = paramMixNet.getStateCount();
/* 123 */     for (int k = 0; k < i; k++) {
/* 124 */       int[] arrayOfInt2 = getParent(k);
/* 125 */       int[] arrayOfInt3 = MATH.appendMember(k, arrayOfInt2);
/* 126 */       float[] arrayOfFloat1 = MATH.fmargin(paramString, paramMixNet.getDataSize(), arrayOfInt1, arrayOfInt3);
/*     */       
/* 128 */       if (arrayOfInt2 == null) {
/* 129 */         setCondProb(k, arrayOfFloat1);
/*     */       }
/*     */       else {
/* 132 */         int[] arrayOfInt4 = getFamilyStateCount(k);
/* 133 */         int[] arrayOfInt5 = new int[arrayOfInt4.length - 1];
/* 134 */         for (int m = 0; m < arrayOfInt5.length; m++) arrayOfInt5[m] = m;
/* 135 */         float[] arrayOfFloat2 = MATH.margin(arrayOfFloat1, arrayOfInt4, arrayOfInt5);
/*     */         
/* 137 */         float[] arrayOfFloat3 = MATH.division(arrayOfInt3, arrayOfFloat1, arrayOfInt2, arrayOfFloat2, arrayOfInt3, arrayOfInt4);
/* 138 */         setCondProb(k, arrayOfFloat3);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/* 143 */   public BNode getBNode(int paramInt) { return new BNode((BNode)this.nd[paramInt]); }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setState(int paramInt, String[] paramArrayOfString)
/*     */   {
/* 152 */     int i = ((BNode)this.nd[paramInt]).getStateCount();
/*     */     
/* 154 */     ((BNode)this.nd[paramInt]).setState(paramArrayOfString);
/*     */     
/* 156 */     if (i != paramArrayOfString.length) {
/* 157 */       ((BNode)this.nd[paramInt]).initExpn();
/* 158 */       ((BNode)this.nd[paramInt]).setCondProb();
/*     */       
/* 160 */       int[] arrayOfInt = this.nd[paramInt].getChild();
/* 161 */       int j = arrayOfInt == null ? 0 : arrayOfInt.length;
/* 162 */       for (int k = 0; k < j; k++) {
/* 163 */         setParentStateCount(arrayOfInt[k]);
/* 164 */         ((BNode)this.nd[arrayOfInt[k]]).initExpn();
/* 165 */         ((BNode)this.nd[arrayOfInt[k]]).setCondProb();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   void setParentStateCount()
/*     */   {
/* 172 */     for (int i = 0; i < this.nd.length; i++) setParentStateCount(i);
/*     */   }
/*     */   
/*     */   void setParentStateCount(int paramInt) {
/* 176 */     int i = this.nd[paramInt].getParentCount();
/* 177 */     if (i == 0) { return;
/*     */     }
/* 179 */     int[] arrayOfInt = new int[i];
/* 180 */     for (int j = 0; j < i; j++) {
/* 181 */       int k = this.nd[paramInt].getParent(j);
/* 182 */       arrayOfInt[j] = ((BNode)this.nd[k]).getStateCount();
/*     */     }
/* 184 */     ((BNode)this.nd[paramInt]).setParentStateCount(arrayOfInt);
/*     */   }
/*     */   
/*     */   public int getStateCount(int paramInt)
/*     */   {
/* 189 */     return ((BNode)this.nd[paramInt]).getStateCount();
/*     */   }
/*     */   
/*     */   public int getStateCount(String paramString) {
/* 193 */     return ((BNode)this.nd[getIndex(paramString)]).getStateCount();
/*     */   }
/*     */   
/*     */   public int[] getStateCount(int[] paramArrayOfInt) {
/* 197 */     if (paramArrayOfInt == null) return null;
/* 198 */     int i = paramArrayOfInt.length;
/* 199 */     int[] arrayOfInt = new int[i];
/* 200 */     for (int j = 0; j < i; j++) arrayOfInt[j] = getStateCount(paramArrayOfInt[j]);
/* 201 */     return arrayOfInt;
/*     */   }
/*     */   
/*     */   public int[] getStateCount() {
/* 205 */     int i = this.nd.length;
/* 206 */     int[] arrayOfInt = new int[i];
/* 207 */     for (int j = 0; j < i; j++) arrayOfInt[j] = getStateCount(j);
/* 208 */     return arrayOfInt;
/*     */   }
/*     */   
/*     */   public int[] getFamilyStateCount(int paramInt)
/*     */   {
/* 213 */     int[] arrayOfInt1 = this.nd[paramInt].getParent();
/* 214 */     int i = arrayOfInt1 == null ? 1 : arrayOfInt1.length + 1;
/* 215 */     int[] arrayOfInt2 = new int[i];
/* 216 */     for (int j = 0; j < i - 1; j++) arrayOfInt2[j] = getStateCount(arrayOfInt1[j]);
/* 217 */     arrayOfInt2[(i - 1)] = getStateCount(paramInt);
/* 218 */     return arrayOfInt2;
/*     */   }
/*     */   
/*     */   public int[] getParentIndex(int paramInt)
/*     */   {
/* 223 */     return this.nd[paramInt].getParent();
/*     */   }
/*     */   
/*     */   int[] getParentStateCount(int paramInt)
/*     */   {
/* 228 */     int[] arrayOfInt1 = this.nd[paramInt].getParent();
/* 229 */     int i = arrayOfInt1 == null ? 0 : arrayOfInt1.length;
/* 230 */     int[] arrayOfInt2 = new int[i];
/* 231 */     for (int j = 0; j < i; j++) arrayOfInt2[j] = getStateCount(arrayOfInt1[j]);
/* 232 */     return arrayOfInt2;
/*     */   }
/*     */   
/*     */   public String[] getState(int paramInt)
/*     */   {
/* 237 */     return ((BNode)this.nd[paramInt]).getState();
/*     */   }
/*     */   
/*     */   public String getState(int paramInt1, int paramInt2) {
/* 241 */     return ((BNode)this.nd[paramInt1]).getState(paramInt2);
/*     */   }
/*     */   
/*     */   public String[] getState(String paramString) {
/* 245 */     return ((BNode)this.nd[getIndex(paramString)]).getState();
/*     */   }
/*     */   
/*     */   public String[][] getState() {
/* 249 */     int i = getNodeCount();
/* 250 */     String[][] arrayOfString = new String[i][];
/* 251 */     for (int j = 0; j < i; j++) arrayOfString[j] = getState(j);
/* 252 */     return arrayOfString;
/*     */   }
/*     */   
/*     */   public String[][] getState(int[] paramArrayOfInt) {
/* 256 */     int i = paramArrayOfInt.length;
/* 257 */     String[][] arrayOfString = new String[i][];
/* 258 */     for (int j = 0; j < i; j++) {
/* 259 */       arrayOfString[j] = getState(paramArrayOfInt[j]);
/*     */     }
/* 261 */     return arrayOfString;
/*     */   }
/*     */   
/*     */   public char[] getStateInit(int paramInt)
/*     */   {
/* 266 */     return ((BNode)this.nd[paramInt]).getStateInit();
/*     */   }
/*     */   
/*     */   public String[][] getParentState(int paramInt)
/*     */   {
/* 271 */     int[] arrayOfInt = this.nd[paramInt].getParent();
/* 272 */     return getState(arrayOfInt);
/*     */   }
/*     */   
/*     */   public int getStateIndex(int paramInt, String paramString)
/*     */   {
/* 277 */     return ((BNode)this.nd[paramInt]).getStateIndex(paramString);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   void delVarValue(int paramInt)
/*     */   {
/* 284 */     ((BNode)this.nd[paramInt]).delVarValue();
/*     */   }
/*     */   
/*     */   void delVarValue() {
/* 288 */     for (int i = 0; i < getNodeCount(); i++) { ((BNode)this.nd[i]).delVarValue();
/*     */     }
/*     */   }
/*     */   
/*     */   int[] getParentValue(int paramInt)
/*     */   {
/* 294 */     int i = getParentCount(paramInt);
/* 295 */     if (i == 0) { return null;
/*     */     }
/* 297 */     int[] arrayOfInt1 = getParent(paramInt);
/* 298 */     int[] arrayOfInt2 = new int[i];
/* 299 */     for (int j = 0; j < i; j++) arrayOfInt2[j] = getVarValue(arrayOfInt1[j]);
/* 300 */     return arrayOfInt2;
/*     */   }
/*     */   
/*     */   public int getVarValue(int paramInt)
/*     */   {
/* 305 */     return ((BNode)this.nd[paramInt]).getVarValue();
/*     */   }
/*     */   
/*     */   public int[] getVarValue() {
/* 309 */     int i = getNodeCount();
/* 310 */     int[] arrayOfInt = new int[i];
/* 311 */     for (int j = 0; j < i; j++) arrayOfInt[j] = ((BNode)this.nd[j]).getVarValue();
/* 312 */     return arrayOfInt;
/*     */   }
/*     */   
/*     */   String getStrValue(int paramInt)
/*     */   {
/* 317 */     return ((BNode)this.nd[paramInt]).getStrValue();
/*     */   }
/*     */   
/*     */   public String[] getStrValue() {
/* 321 */     int i = getNodeCount();
/* 322 */     String[] arrayOfString = new String[i];
/* 323 */     for (int j = 0; j < i; j++) arrayOfString[j] = ((BNode)this.nd[j]).getStrValue();
/* 324 */     return arrayOfString;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getStampedStrValue(String paramString)
/*     */   {
/* 332 */     int i = getIndex(paramString);
/* 333 */     if (i == -1) { return null;
/*     */     }
/* 335 */     if (((BNode)this.nd[i]).isStamped())
/* 336 */       return getStrValue(i);
/* 337 */     return null;
/*     */   }
/*     */   
/*     */   void setVarValue(int paramInt1, int paramInt2)
/*     */   {
/* 342 */     ((BNode)this.nd[paramInt1]).setVarValue(paramInt2);
/*     */   }
/*     */   
/*     */   public void setVarValue() {
/* 346 */     int i = getNodeCount();
/* 347 */     for (int j = 0; j < i; j++) { ((BNode)this.nd[j]).setVarValue();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setVarValue(int[] paramArrayOfInt, Random paramRandom)
/*     */   {
/* 359 */     delVarValue();
/* 360 */     int i = getNodeCount();
/* 361 */     for (int j = 0; j < i; j++) {
/* 362 */       int k = paramArrayOfInt[j];
/* 363 */       int[] arrayOfInt = getParentValue(k);
/* 364 */       float[] arrayOfFloat = ((BNode)this.nd[k]).getCondProb(arrayOfInt);
/* 365 */       float f = paramRandom.nextFloat();
/* 366 */       int m = MATH.getBinIndex(f, arrayOfFloat);
/* 367 */       setVarValue(k, m);
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean isVarValueSet()
/*     */   {
/* 373 */     int i = getNodeCount();
/* 374 */     for (int j = 0; j < i; j++) {
/* 375 */       if (getVarValue(j) == -1) return false;
/*     */     }
/* 377 */     return true;
/*     */   }
/*     */   
/*     */   public void setMarginalProb(JoinForest paramJoinForest)
/*     */   {
/* 382 */     int i = getNodeCount();
/* 383 */     for (int j = 0; j < i; j++) {
/* 384 */       ((BNode)this.nd[j]).setMarginalProb(paramJoinForest.getVarMargin(j));
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static BayesNet addNode(BayesNet paramBayesNet, Point paramPoint)
/*     */   {
/* 393 */     BayesNet localBayesNet = new BayesNet();
/*     */     
/* 395 */     int i = paramBayesNet.getNodeCount();
/* 396 */     localBayesNet.setDumbNet(i + 1);
/*     */     
/* 398 */     for (int j = 0; j < i; j++) { localBayesNet.nd[j] = paramBayesNet.nd[j];
/*     */     }
/* 400 */     localBayesNet.nd[i] = new BNode();
/* 401 */     localBayesNet.setPos(i, paramPoint);
/* 402 */     localBayesNet.setLabel(i, new String("var_" + i));
/* 403 */     return localBayesNet;
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
/*     */   public static BayesNet delNode(BayesNet paramBayesNet, int paramInt)
/*     */   {
/* 418 */     int i = paramBayesNet.nd[paramInt].pls == null ? 0 : paramBayesNet.nd[paramInt].pls.length;
/* 419 */     for (int j = 0; j < i; j++) {
/* 420 */       k = paramBayesNet.nd[paramInt].pls[j];
/* 421 */       paramBayesNet.nd[k].delChild(paramInt);
/*     */     }
/*     */     
/* 424 */     i = paramBayesNet.nd[paramInt].cls == null ? 0 : paramBayesNet.nd[paramInt].cls.length;
/* 425 */     for (j = 0; j < i; j++) {
/* 426 */       k = paramBayesNet.nd[paramInt].cls[j];
/* 427 */       ((BNode)paramBayesNet.nd[k]).delBParent(paramInt);
/*     */     }
/*     */     
/* 430 */     BayesNet localBayesNet = new BayesNet();
/* 431 */     int k = paramBayesNet.getNodeCount();
/* 432 */     localBayesNet.setDumbNet(k - 1);
/*     */     
/* 434 */     for (int m = 0; m < k - 1; m++) {
/* 435 */       if (m < paramInt) localBayesNet.nd[m] = paramBayesNet.nd[m]; else
/* 436 */         localBayesNet.nd[m] = paramBayesNet.nd[(m + 1)];
/*     */     }
/* 438 */     localBayesNet.modifyNodeIndex(paramInt + 1, k - 1, -1);
/* 439 */     return localBayesNet;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void addArc(int paramInt1, int paramInt2)
/*     */   {
/* 448 */     ((BNode)this.nd[paramInt2]).addBParent(paramInt1, ((BNode)this.nd[paramInt1]).getStateCount());
/* 449 */     this.nd[paramInt1].addChild(paramInt2);
/*     */   }
/*     */   
/*     */   public void delArc(int paramInt1, int paramInt2)
/*     */   {
/* 454 */     this.nd[paramInt1].delChild(paramInt2);
/* 455 */     ((BNode)this.nd[paramInt2]).delBParent(paramInt1);
/*     */   }
/*     */   
/*     */   public boolean hasArc()
/*     */   {
/* 460 */     for (int i = 0; i < this.nd.length; i++)
/* 461 */       if (this.nd[i].getParentCount() > 0) return true;
/* 462 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   void addNonForgetArc(int[] paramArrayOfInt)
/*     */   {
/* 471 */     int i = paramArrayOfInt.length;
/* 472 */     if (i < 2) { return;
/*     */     }
/*     */     
/* 475 */     for (int k = 0; k < i - 1; k++) {
/* 476 */       int[] arrayOfInt = getParentIndex(paramArrayOfInt[k]);
/* 477 */       if (arrayOfInt != null) {
/* 478 */         int j = arrayOfInt.length;
/* 479 */         for (int m = k + 1; m < i; m++) {
/* 480 */           for (int n = 0; n < j; n++)
/* 481 */             if (!isArc(arrayOfInt[n], paramArrayOfInt[m])) addArc(arrayOfInt[n], paramArrayOfInt[m]);
/* 482 */           if (!isArc(paramArrayOfInt[k], paramArrayOfInt[m])) { addArc(paramArrayOfInt[k], paramArrayOfInt[m]);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public static BayesNet unionNet(BayesNet paramBayesNet1, BayesNet paramBayesNet2)
/*     */   {
/* 492 */     int i = paramBayesNet1.getNodeCount();
/* 493 */     int j = paramBayesNet2.getNodeCount();
/* 494 */     BayesNet localBayesNet1 = new BayesNet();
/* 495 */     localBayesNet1.setDumbNet(i + j);
/* 496 */     for (int k = 0; k < i; k++) { localBayesNet1.nd[k] = paramBayesNet1.nd[k];
/*     */     }
/* 498 */     BayesNet localBayesNet2 = new BayesNet(paramBayesNet2);
/* 499 */     localBayesNet2.modifyNodeIndex(0, j - 1, i);
/* 500 */     for (int m = 0; m < j; m++) { localBayesNet1.nd[(i + m)] = localBayesNet2.nd[m];
/*     */     }
/* 502 */     m = 0;
/* 503 */     Point localPoint; for (int n = 0; n < i; n++) {
/* 504 */       localPoint = paramBayesNet1.getPos(n);
/* 505 */       if (localPoint.x > m) m = localPoint.x;
/*     */     }
/* 507 */     for (n = 0; n < j; n++) {
/* 508 */       localPoint = localBayesNet1.getPos(i + n);
/* 509 */       localPoint.x += m + 10;
/* 510 */       localBayesNet1.setPos(i + n, localPoint);
/*     */     }
/* 512 */     return localBayesNet1;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void initExpn()
/*     */   {
/* 519 */     for (int i = 0; i < getNodeCount(); i++) initExpn(i);
/*     */   }
/*     */   
/*     */   public void initExpn(int paramInt) {
/* 523 */     ((BNode)this.nd[paramInt]).initExpn();
/*     */   }
/*     */   
/*     */   public String[] getExpn(int paramInt) {
/* 527 */     return ((BNode)this.nd[paramInt]).getExpn();
/*     */   }
/*     */   
/*     */   public void setExpn(String[] paramArrayOfString, int paramInt) {
/* 531 */     ((BNode)this.nd[paramInt]).setExpn(paramArrayOfString);
/*     */   }
/*     */   
/*     */   public void setCptByExpn(int paramInt)
/*     */   {
/* 536 */     ((BNode)this.nd[paramInt]).setCptByExpn();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public float[] getCondProb(int paramInt)
/*     */   {
/* 543 */     return ((BNode)this.nd[paramInt]).getCondProb();
/*     */   }
/*     */   
/*     */   public float[] refCondProb(int paramInt) {
/* 547 */     return ((BNode)this.nd[paramInt]).refCondProb();
/*     */   }
/*     */   
/*     */   public float[] getCondProb(String paramString) {
/* 551 */     return ((BNode)this.nd[getIndex(paramString)]).getCondProb();
/*     */   }
/*     */   
/*     */   public float getCondProb(int paramInt1, int paramInt2) {
/* 555 */     return ((BNode)this.nd[paramInt1]).getCondProb(paramInt2);
/*     */   }
/*     */   
/*     */   public float getCondProb(String paramString1, String paramString2) {
/* 559 */     int i = getIndex(paramString1);
/* 560 */     int j = ((BNode)this.nd[i]).getStateIndex(paramString2);
/* 561 */     return ((BNode)this.nd[i]).getCondProb(j);
/*     */   }
/*     */   
/*     */   public float getCondProb(int paramInt1, int paramInt2, int[] paramArrayOfInt)
/*     */   {
/* 566 */     return ((BNode)this.nd[paramInt1]).getCondProb(paramInt2, paramArrayOfInt);
/*     */   }
/*     */   
/*     */ 
/*     */   public float getCondProb(int paramInt1, int paramInt2, int[] paramArrayOfInt1, int[] paramArrayOfInt2)
/*     */   {
/* 572 */     return ((BNode)this.nd[paramInt1]).getCondProb(paramInt2, paramArrayOfInt1, paramArrayOfInt2);
/*     */   }
/*     */   
/*     */ 
/*     */   public float getCondProb(String paramString1, String paramString2, String[] paramArrayOfString1, String[] paramArrayOfString2)
/*     */   {
/* 578 */     int i = getIndex(paramString1);
/* 579 */     int j = ((BNode)this.nd[i]).getStateIndex(paramString2);
/* 580 */     int k = paramArrayOfString1.length;
/* 581 */     int[] arrayOfInt1 = new int[k];
/* 582 */     int[] arrayOfInt2 = new int[k];
/* 583 */     for (int m = 0; m < k; m++) {
/* 584 */       arrayOfInt1[m] = getIndex(paramArrayOfString1[m]);
/* 585 */       arrayOfInt2[m] = ((BNode)this.nd[arrayOfInt1[m]]).getStateIndex(paramArrayOfString2[m]);
/*     */     }
/* 587 */     return ((BNode)this.nd[i]).getCondProb(j, arrayOfInt1, arrayOfInt2);
/*     */   }
/*     */   
/*     */   public int getCptSize(int paramInt) {
/* 591 */     return ((BNode)this.nd[paramInt]).getCptSize();
/*     */   }
/*     */   
/*     */   public int getCptSize() {
/* 595 */     int i = getNodeCount();
/* 596 */     int j = 0;
/* 597 */     for (int k = 0; k < i; k++) j += ((BNode)this.nd[k]).getCptSize();
/* 598 */     return j;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public float getCondProbLeak(String paramString1, String paramString2, String[] paramArrayOfString1, String[] paramArrayOfString2)
/*     */   {
/* 606 */     int i = getIndex(paramString1);
/* 607 */     int j = ((BNode)this.nd[i]).getStateIndex(paramString2);
/* 608 */     if (j < 0) j = getStateCount(i) - 1;
/* 609 */     int k = paramArrayOfString1.length;
/* 610 */     int[] arrayOfInt1 = new int[k];
/* 611 */     int[] arrayOfInt2 = new int[k];
/* 612 */     for (int m = 0; m < k; m++) {
/* 613 */       arrayOfInt1[m] = getIndex(paramArrayOfString1[m]);
/* 614 */       arrayOfInt2[m] = ((BNode)this.nd[arrayOfInt1[m]]).getStateIndex(paramArrayOfString2[m]);
/*     */     }
/* 616 */     return ((BNode)this.nd[i]).getCondProb(j, arrayOfInt1, arrayOfInt2);
/*     */   }
/*     */   
/*     */ 
/*     */   public int getMostProbable(int paramInt, int[] paramArrayOfInt)
/*     */   {
/* 622 */     return ((BNode)this.nd[paramInt]).getMostProbable(paramArrayOfInt);
/*     */   }
/*     */   
/*     */   public void setUniformCpt(int paramInt)
/*     */   {
/* 627 */     ((BNode)this.nd[paramInt]).setUniformCpt();
/*     */   }
/*     */   
/*     */   public void setCondProb(int paramInt, float[] paramArrayOfFloat)
/*     */   {
/* 632 */     ((BNode)this.nd[paramInt]).setCondProb(paramArrayOfFloat);
/*     */   }
/*     */   
/*     */   public void setCondProb()
/*     */   {
/* 637 */     int i = getNodeCount();
/* 638 */     for (int j = 0; j < i; j++) { ((BNode)this.nd[j]).setCondProb();
/*     */     }
/*     */   }
/*     */   
/*     */   public void setRandCondProb(Random paramRandom)
/*     */   {
/* 644 */     int i = getNodeCount();
/* 645 */     for (int j = 0; j < i; j++) ((BNode)this.nd[j]).setRandCondProb(paramRandom);
/*     */   }
/*     */   
/*     */   public void setRandCondProb()
/*     */   {
/* 650 */     int i = getNodeCount();
/* 651 */     for (int j = 0; j < i; j++) { ((BNode)this.nd[j]).setRandCondProb();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   void loadNode(BufferedReader paramBufferedReader)
/*     */   {
/* 658 */     int i = this.nd.length;
/*     */     try {
/* 660 */       for (int j = 0; j < i; j++) {
/* 661 */         paramBufferedReader.readLine();
/* 662 */         this.nd[j] = BNode.load(paramBufferedReader);
/* 663 */         if (this.nd[j] == null) throw new IOException("Unexpected end of input!");
/*     */       }
/*     */     }
/*     */     catch (IOException localIOException) {
/* 667 */       HelpPanel.showError("Unable to complete load: " + localIOException.getMessage());
/*     */     }
/*     */   }
/*     */   
/*     */   void loadNodeSkipPb(BufferedReader paramBufferedReader) {
/* 672 */     int i = this.nd.length;
/*     */     try {
/* 674 */       for (int j = 0; j < i; j++) {
/* 675 */         paramBufferedReader.readLine();
/* 676 */         this.nd[j] = BNode.loadSkipPb(paramBufferedReader);
/* 677 */         if (this.nd[j] == null) throw new IOException("Unexpected end of input!");
/*     */       }
/*     */     }
/*     */     catch (IOException localIOException) {
/* 681 */       HelpPanel.showError("Unable to complete load: " + localIOException.getMessage());
/*     */     }
/*     */   }
/*     */   
/*     */   public static BayesNet load(BufferedReader paramBufferedReader)
/*     */   {
/* 687 */     BayesNet localBayesNet = new BayesNet();
/* 688 */     int i = localBayesNet.loadNodeCount(paramBufferedReader);
/* 689 */     localBayesNet.setDumbNet(i);
/* 690 */     localBayesNet.loadNode(paramBufferedReader);
/* 691 */     localBayesNet.setParentStateCount();
/* 692 */     return localBayesNet;
/*     */   }
/*     */   
/*     */   public static BayesNet load(String paramString) {
/* 696 */     BayesNet localBayesNet = null;
/*     */     try {
/* 698 */       BufferedReader localBufferedReader = new BufferedReader(new FileReader(paramString));
/* 699 */       HelpPanel.addHelp("Loading net from " + paramString);
/* 700 */       localBayesNet = load(localBufferedReader);
/* 701 */       localBufferedReader.close();
/*     */     } catch (IOException localIOException) {
/* 703 */       HelpPanel.showError("Unable to load file!");
/*     */     }
/* 705 */     return localBayesNet;
/*     */   }
/*     */   
/*     */   public static BayesNet loadSkipPb(BufferedReader paramBufferedReader)
/*     */   {
/* 710 */     BayesNet localBayesNet = new BayesNet();
/* 711 */     int i = localBayesNet.loadNodeCount(paramBufferedReader);
/* 712 */     localBayesNet.setDumbNet(i);
/* 713 */     localBayesNet.loadNodeSkipPb(paramBufferedReader);
/* 714 */     localBayesNet.setParentStateCount();
/* 715 */     return localBayesNet;
/*     */   }
/*     */   
/*     */   public static BayesNet loadSkipPb(String paramString) {
/* 719 */     BayesNet localBayesNet = null;
/*     */     try {
/* 721 */       BufferedReader localBufferedReader = new BufferedReader(new FileReader(paramString));
/* 722 */       HelpPanel.addHelp("Loading net from " + paramString);
/* 723 */       localBayesNet = loadSkipPb(localBufferedReader);
/* 724 */       localBufferedReader.close();
/*     */     } catch (IOException localIOException) {
/* 726 */       HelpPanel.showError("Unable to load file!");
/*     */     }
/* 728 */     return localBayesNet;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void loadVarValue(BufferedReader paramBufferedReader)
/*     */   {
/* 735 */     int i = UTIL.loadInt(paramBufferedReader);
/* 736 */     Point[] arrayOfPoint = new Point[i];
/* 737 */     int j = -1;int k = -1;
/* 738 */     int m = 0;
/* 739 */     for (int n = 0; n < i; n++) {
/* 740 */       String[] arrayOfString = UTIL.loadStringListLine(paramBufferedReader, 2);
/* 741 */       j = getIndex(arrayOfString[0]);
/* 742 */       if (j == -1) { HelpPanel.showError("Invalid var name: " + arrayOfString[0]);
/*     */       } else {
/* 744 */         k = getStateIndex(j, arrayOfString[1]);
/* 745 */         if (k == -1)
/* 746 */           HelpPanel.showError("Var " + arrayOfString[0] + " has invalid value: " + arrayOfString[1]);
/*     */       }
/* 748 */       arrayOfPoint[n] = new Point(j, k);
/* 749 */       if ((j == -1) || (k == -1)) { m = 1;
/*     */       }
/*     */     }
/* 752 */     if (m != 0) return;
/* 753 */     for (n = 0; n < i; n++) {
/* 754 */       setVarValue(arrayOfPoint[n].x, arrayOfPoint[n].y);
/* 755 */       setObserved(arrayOfPoint[n].x);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int[][] loadEvi(String paramString)
/*     */   {
/* 766 */     int[][] arrayOfInt = (int[][])null;
/*     */     try {
/* 768 */       BufferedReader localBufferedReader = new BufferedReader(new FileReader(paramString));
/* 769 */       int i = UTIL.loadInt(localBufferedReader);
/* 770 */       if (i == 0) return (int[][])null;
/* 771 */       arrayOfInt = new int[i][2];
/* 772 */       int j = -1;int k = -1;
/* 773 */       for (int m = 0; m < i; m++) {
/* 774 */         String[] arrayOfString = UTIL.loadStringListLine(localBufferedReader, 2);
/* 775 */         j = getIndex(arrayOfString[0]);
/* 776 */         if (j == -1) { System.out.println("Invalid var name: " + arrayOfString[0]);
/*     */         } else {
/* 778 */           k = getStateIndex(j, arrayOfString[1]);
/* 779 */           if (k == -1)
/* 780 */             System.out.println("Var " + arrayOfString[0] + " value invalid: " + arrayOfString[1]);
/*     */         }
/* 782 */         arrayOfInt[m][0] = j;arrayOfInt[m][1] = k;
/* 783 */         if ((j == -1) || (k == -1)) return (int[][])null;
/*     */       }
/* 785 */       return arrayOfInt;
/*     */     } catch (IOException localIOException) {
/* 787 */       System.out.println("Unable to load " + paramString);
/*     */     }
/* 789 */     return arrayOfInt;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void loadStamp(BufferedReader paramBufferedReader)
/*     */   {
/* 796 */     int i = UTIL.loadInt(paramBufferedReader);
/* 797 */     int[] arrayOfInt = new int[i];
/* 798 */     for (int j = 0; j < i; j++) {
/* 799 */       String str = UTIL.loadString(paramBufferedReader);
/* 800 */       arrayOfInt[j] = getIndex(str);
/* 801 */       if (arrayOfInt[j] == -1)
/* 802 */         HelpPanel.showError("Invalid variable name: " + str);
/*     */     }
/* 804 */     for (j = 0; j < i; j++) this.nd[arrayOfInt[j]].setStamp();
/* 805 */     HelpPanel.addHelp("Obervable variables:");
/* 806 */     for (j = 0; j < getNodeCount(); j++) {
/* 807 */       if (this.nd[j].isStamped()) { HelpPanel.addHelp(" " + this.nd[j].getLabel());
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public void save(PrintWriter paramPrintWriter)
/*     */   {
/* 815 */     int i = this.nd.length;
/* 816 */     paramPrintWriter.println(i + " #_of_nodes");
/* 817 */     for (int j = 0; j < i; j++) {
/* 818 */       paramPrintWriter.println();
/* 819 */       ((BNode)this.nd[j]).save(paramPrintWriter, j);
/*     */     }
/*     */   }
/*     */   
/*     */   public void save(String paramString)
/*     */   {
/*     */     try {
/* 826 */       PrintWriter localPrintWriter = new PrintWriter(new FileWriter(paramString));
/* 827 */       HelpPanel.addHelp("Saving " + paramString);
/* 828 */       save(localPrintWriter);
/* 829 */       localPrintWriter.close();
/*     */     } catch (IOException localIOException) {
/* 831 */       HelpPanel.showError("Unable to save " + paramString);
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
/*     */   public void lowerNodeIndex(int paramInt)
/*     */   {
/* 848 */     int i = this.nd.length;
/* 849 */     int[][] arrayOfInt1 = new int[i][];
/* 850 */     int[][] arrayOfInt2 = new int[i][];
/* 851 */     for (int j = 0; j < i; j++) {
/* 852 */       arrayOfInt1[j] = this.nd[j].getParent();
/* 853 */       arrayOfInt2[j] = getParentStateCount(j);
/*     */     }
/*     */     
/* 856 */     BNode localBNode = (BNode)this.nd[paramInt];
/* 857 */     int[] arrayOfInt3 = arrayOfInt1[paramInt];int[] arrayOfInt4 = arrayOfInt2[paramInt];
/* 858 */     for (int k = paramInt; k > 0; k--) {
/* 859 */       this.nd[k] = this.nd[(k - 1)];arrayOfInt1[k] = arrayOfInt1[(k - 1)];arrayOfInt2[k] = arrayOfInt2[(k - 1)];
/*     */     }
/* 861 */     this.nd[0] = localBNode;arrayOfInt1[0] = arrayOfInt3;arrayOfInt2[0] = arrayOfInt4;
/*     */     
/* 863 */     modifyNodeIndex(paramInt, paramInt, i - paramInt);
/* 864 */     modifyNodeIndex(0, paramInt - 1, 1);
/* 865 */     modifyNodeIndex(i, i, -i);
/*     */     
/*     */ 
/* 868 */     for (k = 1; k < i; k++) {
/* 869 */       int[] arrayOfInt5 = arrayOfInt1[k];
/* 870 */       arrayOfInt5 = UTIL.moveToSortedAryStart(paramInt, arrayOfInt5);
/* 871 */       if (arrayOfInt5 != null) {
/* 872 */         float[] arrayOfFloat = ((BNode)this.nd[k]).getCondProb();
/* 873 */         int[] arrayOfInt6 = UTIL.appendToArray(arrayOfInt1[k], i);
/* 874 */         int[] arrayOfInt7 = UTIL.appendToArray(arrayOfInt5, i);
/* 875 */         int[] arrayOfInt8 = UTIL.appendToArray(arrayOfInt2[k], getStateCount(k));
/* 876 */         ((BNode)this.nd[k]).setCondProb(MATH.reorderBelief(arrayOfInt6, arrayOfInt8, arrayOfFloat, arrayOfInt7));
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void showBayesNet()
/*     */   {
/* 885 */     System.out.println(" [BayesNet]");
/* 886 */     for (int i = 0; i < this.nd.length; i++) {
/* 887 */       ((BNode)this.nd[i]).showBNode(i);
/*     */     }
/*     */   }
/*     */ }


/* Location:              /home/anali/INAOE/tesis/codigos/topub/exe/ww4.jar!/Network/BayesNet.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */