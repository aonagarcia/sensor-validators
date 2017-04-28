/*      */ package Network;
/*      */ 
/*      */ import java.awt.Point;
/*      */ import java.awt.Rectangle;
/*      */ import java.io.BufferedReader;
/*      */ import java.io.PrintWriter;
/*      */ 
/*      */ public class HyperGraph
/*      */ {
/*   10 */   public HNode[] cq = null;
/*   11 */   int varCount = 0;
/*   12 */   int[] varToCq = null;
/*   13 */   int[] tor = null;
/*      */   
/*      */   public HyperGraph() {
/*   16 */     this.cq = null;
/*      */   }
/*      */   
/*   19 */   public HyperGraph(int paramInt) { setDumbNet(paramInt); }
/*      */   
/*      */   public HyperGraph(HyperGraph paramHyperGraph)
/*      */   {
/*   23 */     this();
/*   24 */     if ((paramHyperGraph != null) && (paramHyperGraph.cq != null)) {
/*   25 */       setHyperGraphNode(paramHyperGraph);
/*   26 */       setHyperGraphOther(paramHyperGraph);
/*      */     }
/*      */   }
/*      */   
/*      */   public void setDumbNet(int paramInt)
/*      */   {
/*   32 */     this.cq = new HNode[paramInt];
/*      */   }
/*      */   
/*      */   public void setDumbNetPlus(int paramInt)
/*      */   {
/*   37 */     setDumbNet(paramInt);
/*   38 */     for (int i = 0; i < paramInt; i++) this.cq[i] = new HNode();
/*      */   }
/*      */   
/*      */   void setHyperGraphNode(HyperGraph paramHyperGraph)
/*      */   {
/*   43 */     int i = paramHyperGraph.getNodeCount();
/*   44 */     setDumbNetPlus(i);
/*   45 */     for (int j = 0; j < i; j++) this.cq[j] = paramHyperGraph.getHNode(j);
/*      */   }
/*      */   
/*      */   void setHyperGraphOther(HyperGraph paramHyperGraph)
/*      */   {
/*   50 */     this.varCount = paramHyperGraph.getVarCount();
/*   51 */     this.varToCq = paramHyperGraph.getVarToCq();
/*   52 */     this.tor = paramHyperGraph.getTreeOrder();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public int[] getDomain()
/*      */   {
/*   59 */     int i = getNodeCount();
/*   60 */     if (i == 0) { return null;
/*      */     }
/*   62 */     int[] arrayOfInt = null;
/*   63 */     for (int j = 0; j < i; j++) arrayOfInt = MATH.union(arrayOfInt, getCqMember(j));
/*   64 */     return arrayOfInt;
/*      */   }
/*      */   
/*      */   int getVarCount() {
/*   68 */     return this.varCount;
/*      */   }
/*      */   
/*      */ 
/*      */   void setVarCount()
/*      */   {
/*   74 */     int[] arrayOfInt = null;
/*   75 */     for (int i = 0; i < getNodeCount(); i++)
/*   76 */       arrayOfInt = MATH.union(arrayOfInt, getCqMember(i));
/*   77 */     this.varCount = (arrayOfInt == null ? 0 : arrayOfInt.length);
/*      */   }
/*      */   
/*   80 */   public void setVarCount(int paramInt) { this.varCount = paramInt; }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public int getNodeCount()
/*      */   {
/*   87 */     if (this.cq == null) return 0;
/*   88 */     return this.cq.length;
/*      */   }
/*      */   
/*      */   HNode getHNode(int paramInt)
/*      */   {
/*   93 */     return new HNode(this.cq[paramInt]);
/*      */   }
/*      */   
/*      */   void setMark(int paramInt, boolean paramBoolean)
/*      */   {
/*   98 */     this.cq[paramInt].setMark(paramBoolean);
/*      */   }
/*      */   
/*      */   public void setMark(boolean paramBoolean) {
/*  102 */     for (int i = 0; i < getNodeCount(); i++) setMark(i, paramBoolean);
/*      */   }
/*      */   
/*      */   public void setMark(int[] paramArrayOfInt) {
/*  106 */     for (int i = 0; i < getNodeCount(); i++) setMark(i, false);
/*  107 */     if (paramArrayOfInt == null) return;
/*  108 */     for (i = 0; i < paramArrayOfInt.length; i++) setMark(paramArrayOfInt[i], true);
/*      */   }
/*      */   
/*      */   boolean isMarked(int paramInt)
/*      */   {
/*  113 */     return this.cq[paramInt].isMarked();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getLabel(int paramInt)
/*      */   {
/*  120 */     String str = new String(this.cq[paramInt].getLabel());
/*  121 */     return str;
/*      */   }
/*      */   
/*      */   public void setLabel()
/*      */   {
/*  126 */     if (this.cq == null) return;
/*  127 */     int i = this.cq.length;
/*  128 */     for (int j = 0; j < i; j++) this.cq[j].setLabel(j);
/*      */   }
/*      */   
/*      */   public void setLabel(String paramString)
/*      */   {
/*  133 */     if (this.cq == null) return;
/*  134 */     int i = this.cq.length;
/*  135 */     for (int j = 0; j < i; j++) this.cq[j].setLabel(j, paramString);
/*      */   }
/*      */   
/*      */   int getCqByLabel(String paramString)
/*      */   {
/*  140 */     for (int i = 0; i < getNodeCount(); i++)
/*  141 */       if (getLabel(i).equals(paramString)) return i;
/*  142 */     return -1;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   int getCqSize(int paramInt)
/*      */   {
/*  149 */     return this.cq[paramInt].getCqSize();
/*      */   }
/*      */   
/*      */   int[] getCqSize() {
/*  153 */     int i = getNodeCount();
/*  154 */     if (i == 0) { return null;
/*      */     }
/*  156 */     int[] arrayOfInt = new int[i];
/*  157 */     for (int j = 0; j < i; j++) arrayOfInt[j] = getCqSize(j);
/*  158 */     return arrayOfInt;
/*      */   }
/*      */   
/*      */   int getCqSizeSum()
/*      */   {
/*  163 */     int i = getNodeCount();
/*  164 */     if (i == 0) { return 0;
/*      */     }
/*  166 */     int j = 0;
/*  167 */     for (int k = 0; k < i; k++) j += getCqSize(k);
/*  168 */     return j;
/*      */   }
/*      */   
/*      */   public int[] getCqMember(int paramInt)
/*      */   {
/*  173 */     return this.cq[paramInt].getCqMember();
/*      */   }
/*      */   
/*      */   public String getCqMemberStr(int paramInt) {
/*  177 */     int[] arrayOfInt = this.cq[paramInt].getCqMember();
/*  178 */     if (arrayOfInt == null) return null;
/*  179 */     int i = arrayOfInt.length;
/*  180 */     StringBuffer localStringBuffer = new StringBuffer();
/*  181 */     for (int j = 0; j < i - 1; j++) localStringBuffer.append(arrayOfInt[j] + ",");
/*  182 */     localStringBuffer.append(arrayOfInt[(i - 1)]);
/*  183 */     return localStringBuffer.toString();
/*      */   }
/*      */   
/*      */   int getCqMember(int paramInt1, int paramInt2) {
/*  187 */     return this.cq[paramInt1].getCqMember(paramInt2);
/*      */   }
/*      */   
/*      */   public void setCqMember(int paramInt, int[] paramArrayOfInt)
/*      */   {
/*  192 */     this.cq[paramInt].setCqMember(paramArrayOfInt);
/*      */   }
/*      */   
/*      */   public boolean isCqMember(int paramInt1, int paramInt2)
/*      */   {
/*  197 */     return this.cq[paramInt2].isCqMember(paramInt1);
/*      */   }
/*      */   
/*      */   boolean isCqMember(int[] paramArrayOfInt, int paramInt) {
/*  201 */     return this.cq[paramInt].isCqMember(paramArrayOfInt);
/*      */   }
/*      */   
/*      */   public int getCqHome(int[] paramArrayOfInt)
/*      */   {
/*  206 */     if (this.cq == null) System.out.println("Err: cq[] is null!");
/*  207 */     for (int i = 0; i < this.cq.length; i++)
/*  208 */       if (isCqMember(paramArrayOfInt, i)) return i;
/*  209 */     return -1;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public int getCqIndex(int[] paramArrayOfInt)
/*      */   {
/*  216 */     for (int i = 0; i < this.cq.length; i++) {
/*  217 */       if (MATH.isEqualSet(paramArrayOfInt, this.cq[i].getCqMember()))
/*  218 */         return i;
/*      */     }
/*  220 */     return -1;
/*      */   }
/*      */   
/*      */   public void setVarToCq(int[] paramArrayOfInt) {
/*  224 */     this.varToCq = paramArrayOfInt;
/*      */   }
/*      */   
/*      */   public void setVarToCq(DirectGraph paramDirectGraph) {
/*  228 */     int i = paramDirectGraph.nd.length;
/*  229 */     int[] arrayOfInt1 = new int[i];
/*  230 */     for (int j = 0; j < i; j++) {
/*  231 */       int[] arrayOfInt2 = paramDirectGraph.getFamily(j);
/*  232 */       arrayOfInt1[j] = getCqHome(arrayOfInt2);
/*      */     }
/*  234 */     this.varToCq = arrayOfInt1;
/*      */   }
/*      */   
/*      */ 
/*      */   public void setVarToCq(DirectGraph paramDirectGraph, int[] paramArrayOfInt)
/*      */   {
/*  240 */     int i = paramDirectGraph.nd.length;
/*  241 */     int[] arrayOfInt1 = new int[i];
/*  242 */     for (int j = 0; j < i; j++) {
/*  243 */       int[] arrayOfInt2 = paramDirectGraph.getFamily(j);
/*      */       
/*  245 */       int[] arrayOfInt3 = MATH.getIntersection(arrayOfInt2, paramArrayOfInt);
/*  246 */       if (arrayOfInt3 == null) arrayOfInt1[j] = -1; else
/*  247 */         arrayOfInt1[j] = getCqHome(arrayOfInt3);
/*      */     }
/*  249 */     this.varToCq = arrayOfInt1;
/*      */   }
/*      */   
/*      */   public void setVarToCq(UndirectGraph paramUndirectGraph) {
/*  253 */     int i = paramUndirectGraph.nd.length;
/*  254 */     int[] arrayOfInt = new int[i];
/*  255 */     for (int j = 0; j < i; j++) {
/*  256 */       for (int k = 0; k < this.cq.length; k++)
/*  257 */         if (MATH.member(j, this.cq[k].getCqMember())) arrayOfInt[j] = k;
/*      */     }
/*  259 */     this.varToCq = arrayOfInt;
/*      */   }
/*      */   
/*      */   int[] getVarToCq() {
/*  263 */     return UTIL.getDuplicate(this.varToCq);
/*      */   }
/*      */   
/*      */   public int getCqOfVar(int paramInt) {
/*  267 */     return this.varToCq[paramInt];
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public Point getPos(int paramInt)
/*      */   {
/*  274 */     return this.cq[paramInt].getPos();
/*      */   }
/*      */   
/*      */   public void setPos(int paramInt, Point paramPoint)
/*      */   {
/*  279 */     this.cq[paramInt].setPos(paramPoint);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public int isClose(Point paramPoint, int paramInt)
/*      */   {
/*  287 */     int i = this.cq == null ? 0 : this.cq.length;
/*  288 */     for (int j = 0; j < i; j++)
/*  289 */       if (this.cq[j].isClose(paramPoint, paramInt) == 0) return j;
/*  290 */     for (j = 0; j < i; j++)
/*  291 */       if (this.cq[j].isClose(paramPoint, paramInt) == -1) return -1;
/*  292 */     return -2;
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
/*      */   public void assignCqPos(Rectangle paramRectangle, java.util.Random paramRandom)
/*      */   {
/*  311 */     int i = this.cq.length;
/*  312 */     if (i == 1) {
/*  313 */       this.cq[0].setPos(paramRectangle.width / 2, paramRectangle.height / 2);return;
/*      */     }
/*      */     
/*  316 */     int[] arrayOfInt1 = new int[i];
/*  317 */     for (int j = 0; j < i; j++) { arrayOfInt1[j] = 0;
/*      */     }
/*  319 */     j = 0;
/*  320 */     int[] arrayOfInt2 = new int[i];
/*  321 */     int[] arrayOfInt3 = new int[i];
/*  322 */     int[][] arrayOfInt = new int[i][i];
/*  323 */     for (int k = 0; k < i; k++) { arrayOfInt2[k] = (arrayOfInt3[k] = -1);
/*      */     }
/*  325 */     k = 0;
/*  326 */     for (int m = 0; m < i; m++) {
/*  327 */       if (!this.cq[m].hasPeer()) {
/*  328 */         arrayOfInt2[m] = k;
/*  329 */         arrayOfInt3[m] = arrayOfInt1[k];
/*  330 */         arrayOfInt[arrayOfInt2[m]][arrayOfInt3[m]] = m;
/*  331 */         arrayOfInt1[k] += 1;
/*  332 */         j++;
/*      */       }
/*      */     }
/*  335 */     k++;
/*      */     
/*  337 */     while (j < i) {
/*  338 */       for (m = 0; m < arrayOfInt1[(k - 1)]; m++) {
/*  339 */         n = arrayOfInt[(k - 1)][m];
/*  340 */         i1 = this.cq[n].getNeighborCount();
/*  341 */         for (i2 = 0; i2 < i1; i2++) {
/*  342 */           i3 = this.cq[n].getNeighbor(i2);
/*  343 */           if (arrayOfInt2[i3] < 0) {
/*  344 */             arrayOfInt2[i3] = k;
/*  345 */             arrayOfInt3[i3] = arrayOfInt1[k];
/*  346 */             arrayOfInt[arrayOfInt2[i3]][arrayOfInt3[i3]] = i3;
/*  347 */             arrayOfInt1[k] += 1;
/*  348 */             j++;
/*      */           }
/*      */         }
/*      */       }
/*  352 */       k++;
/*      */     }
/*      */     
/*  355 */     m = paramRectangle.width;
/*  356 */     int n = paramRectangle.height;
/*      */     
/*  358 */     int i1 = (int)(n / (k + 0.5D));
/*  359 */     int i2 = i1 / 2;
/*  360 */     int i3 = 9;
/*      */     
/*  362 */     for (int i4 = 0; i4 < k; i4++) {
/*  363 */       int i5 = m / arrayOfInt1[i4];
/*  364 */       int i6 = i5 / 2 + (int)(paramRandom.nextFloat() * 5.0F);
/*  365 */       for (int i7 = 0; i7 < arrayOfInt1[i4]; i7++) {
/*  366 */         int i8 = arrayOfInt[i4][i7];
/*  367 */         int i9 = (int)(paramRandom.nextFloat() * i7 * i3);
/*  368 */         Point localPoint = new Point(i6 + i5 * arrayOfInt3[i8], i2 + i1 * arrayOfInt2[i8] - i9);
/*  369 */         this.cq[i8].setPos(localPoint);
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public void assignCqPos(Rectangle paramRectangle) {
/*  375 */     java.util.Random localRandom = new java.util.Random();
/*  376 */     assignCqPos(paramRectangle, localRandom);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public int[] getTreeOrder()
/*      */   {
/*  383 */     if (this.tor == null) return null;
/*  384 */     int[] arrayOfInt = new int[this.tor.length];
/*  385 */     for (int i = 0; i < this.tor.length; i++) arrayOfInt[i] = this.tor[i];
/*  386 */     return arrayOfInt;
/*      */   }
/*      */   
/*      */   int getTreeNode(int paramInt)
/*      */   {
/*  391 */     return this.tor[paramInt];
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
/*      */   public void setPeerOrder()
/*      */   {
/*  410 */     int i = getNodeCount();
/*  411 */     if (i == 0) return;
/*  412 */     if (i == 1) {
/*  413 */       this.tor = new int[1];this.tor[0] = 0;setPeer(0, -1);return;
/*      */     }
/*      */     
/*  416 */     for (int j = 0; j < i; j++) { setPeer(j, -1);
/*      */     }
/*  418 */     int[] arrayOfInt = new int[i];
/*  419 */     boolean[][] arrayOfBoolean = new boolean[i][i];
/*  420 */     boolean[] arrayOfBoolean1 = new boolean[i];
/*  421 */     for (int k = 0; k < i; k++) {
/*  422 */       arrayOfInt[k] = this.cq[k].getNeighborCount();
/*  423 */       for (m = 0; m < arrayOfInt[k]; m++) arrayOfBoolean[k][m] = 0;
/*  424 */       arrayOfBoolean1[k] = false;
/*      */     }
/*      */     
/*  427 */     k = 0;
/*  428 */     this.tor = new int[i];
/*  429 */     int m = i - 1;
/*  430 */     while (k == 0) {
/*  431 */       k = 1;
/*  432 */       for (int n = 0; n < i; n++) {
/*  433 */         if (arrayOfInt[n] == 1) {
/*  434 */           arrayOfBoolean1[n] = true;
/*  435 */           k = 0;
/*      */         }
/*      */       }
/*      */       
/*  439 */       if (m == 0) {
/*  440 */         n = 0;
/*  441 */         while (getPeer(n) != -1) n++;
/*  442 */         this.tor[0] = n; return;
/*      */       }
/*      */       int i1;
/*      */       int i2;
/*  446 */       if (m == 1) {
/*  447 */         n = 0;
/*  448 */         while (getPeer(n) != -1) n++;
/*  449 */         i1 = 0;
/*  450 */         while (arrayOfBoolean[n][i1] != 0) i1++;
/*  451 */         i2 = this.cq[n].getNeighbor(i1);
/*  452 */         this.cq[n].setPeer(i2);
/*  453 */         this.tor[1] = n;this.tor[0] = i2;
/*  454 */         return;
/*      */       }
/*      */       
/*      */ 
/*  458 */       for (n = 0; n < i; n++)
/*  459 */         if (arrayOfBoolean1[n] != 0)
/*      */         {
/*  461 */           i1 = 0;
/*  462 */           while (arrayOfBoolean[n][i1] != 0) i1++;
/*  463 */           i2 = this.cq[n].getNeighbor(i1);
/*  464 */           this.cq[n].setPeer(i2);
/*  465 */           arrayOfBoolean[n][i1] = 1;
/*      */           
/*  467 */           int i3 = 0;
/*  468 */           while (this.cq[i2].getNeighbor(i3) != n) i3++;
/*  469 */           arrayOfBoolean[i2][i3] = 1;
/*  470 */           arrayOfInt[n] -= 1;arrayOfInt[i2] -= 1;
/*      */           
/*  472 */           this.tor[(m--)] = n;
/*      */         }
/*  474 */       for (n = 0; n < i; n++) arrayOfBoolean1[n] = false;
/*      */     }
/*      */   }
/*      */   
/*      */   boolean hasPeer(int paramInt)
/*      */   {
/*  480 */     return this.cq[paramInt].hasPeer();
/*      */   }
/*      */   
/*      */   void setPeer(int paramInt1, int paramInt2)
/*      */   {
/*  485 */     this.cq[paramInt1].setPeer(paramInt2);
/*      */   }
/*      */   
/*      */   int getPeer(int paramInt)
/*      */   {
/*  490 */     return this.cq[paramInt].getPeer();
/*      */   }
/*      */   
/*      */   int[] getSepsetToPeer(int paramInt)
/*      */   {
/*  495 */     return this.cq[paramInt].getSepsetToPeer();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public void rmLink()
/*      */   {
/*  502 */     int i = getNodeCount();
/*  503 */     for (int j = 0; j < i; j++) this.cq[j].rmLink();
/*      */   }
/*      */   
/*      */   HLink getHyperLinks(int paramInt1, int paramInt2)
/*      */   {
/*  508 */     return this.cq[paramInt1].getHyperLinks(paramInt2);
/*      */   }
/*      */   
/*      */   public int getNeighborCount(int paramInt)
/*      */   {
/*  513 */     return this.cq[paramInt].getNeighborCount();
/*      */   }
/*      */   
/*      */   int getNeighbor(int paramInt1, int paramInt2)
/*      */   {
/*  518 */     return this.cq[paramInt1].getNeighbor(paramInt2);
/*      */   }
/*      */   
/*      */   public int[] getNeighbor(int paramInt)
/*      */   {
/*  523 */     int i = this.cq[paramInt].getNeighborCount();
/*  524 */     if (i == 0) { return null;
/*      */     }
/*  526 */     int[] arrayOfInt = new int[i];
/*  527 */     for (int j = 0; j < i; j++) arrayOfInt[j] = this.cq[paramInt].getNeighbor(j);
/*  528 */     return arrayOfInt;
/*      */   }
/*      */   
/*      */   public int[] getSepset(int paramInt1, int paramInt2)
/*      */   {
/*  533 */     return this.cq[paramInt1].getSepset(paramInt2);
/*      */   }
/*      */   
/*      */   public int[] getSepset(Point paramPoint)
/*      */   {
/*  538 */     int i = paramPoint.x;
/*  539 */     int j = paramPoint.y;
/*  540 */     int k = getNeighborCount(i);
/*  541 */     for (int m = 0; m < k; m++) {
/*  542 */       int n = getNeighbor(i, m);
/*  543 */       if (n == j) return this.cq[i].getSepset(m);
/*      */     }
/*  545 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int[][] getSepset()
/*      */   {
/*  554 */     int i = getNodeCount();
/*  555 */     int j = i * 2 - 2;
/*  556 */     int[][] arrayOfInt1 = new int[j][];
/*  557 */     int k = 0;
/*      */     
/*  559 */     for (int m = 0; m < i; m++) {
/*  560 */       n = getNeighborCount(m);
/*  561 */       for (i1 = 0; i1 < n; i1++) {
/*  562 */         arrayOfInt1[(k++)] = getSepset(m, i1);
/*      */       }
/*      */     }
/*      */     
/*  566 */     boolean[] arrayOfBoolean = new boolean[j];
/*  567 */     int n = 0;
/*  568 */     for (int i1 = 0; i1 < j; i1++) {
/*  569 */       arrayOfBoolean[i1] = false;
/*  570 */       for (int i2 = 0; i2 < i1; i2++) {
/*  571 */         if (MATH.isEqualSet(arrayOfInt1[i1], arrayOfInt1[i2])) {
/*  572 */           arrayOfBoolean[i1] = true;
/*  573 */           n++;
/*  574 */           break;
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*  579 */     i1 = j - n;
/*  580 */     int[][] arrayOfInt2 = new int[i1][];
/*  581 */     int i3 = 0;
/*  582 */     for (int i4 = 0; i4 < j; i4++) {
/*  583 */       if (arrayOfBoolean[i4] == 0) arrayOfInt2[(i3++)] = arrayOfInt1[i4];
/*      */     }
/*  585 */     return arrayOfInt2;
/*      */   }
/*      */   
/*      */ 
/*      */   public boolean isSepset(int[] paramArrayOfInt)
/*      */   {
/*  591 */     for (int i = 0; i < getNodeCount() - 1; i++) {
/*  592 */       int[] arrayOfInt1 = getNeighbor(i);
/*      */       
/*  594 */       for (int j = 0; j < arrayOfInt1.length; j++) {
/*  595 */         if (arrayOfInt1[j] > i) {
/*  596 */           int[] arrayOfInt2 = getSepset(i, j);
/*  597 */           if (MATH.isEqualSet(paramArrayOfInt, arrayOfInt2)) return true;
/*      */         }
/*      */       }
/*      */     }
/*  601 */     return false;
/*      */   }
/*      */   
/*      */ 
/*      */   void delNeighbor(int paramInt1, int paramInt2)
/*      */   {
/*  607 */     this.cq[paramInt1].delNeighbor(paramInt2);
/*      */   }
/*      */   
/*      */   void delLink(int paramInt1, int paramInt2)
/*      */   {
/*  612 */     this.cq[paramInt1].delNeighbor(paramInt2);
/*  613 */     this.cq[paramInt2].delNeighbor(paramInt1);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   void modifyNodeIndex(int paramInt1, int paramInt2, int paramInt3)
/*      */   {
/*  621 */     for (int i = 0; i < this.cq.length; i++) { this.cq[i].modifyNodeIndex(paramInt1, paramInt2, paramInt3);
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
/*      */   public static HyperGraph delLeafCq(HyperGraph paramHyperGraph, int paramInt)
/*      */   {
/*  636 */     int i = paramHyperGraph.getNeighbor(paramInt, 0);
/*  637 */     paramHyperGraph.delNeighbor(i, paramInt);
/*      */     
/*  639 */     int j = paramHyperGraph.getNodeCount() - 1;
/*  640 */     HyperGraph localHyperGraph = new HyperGraph(j);
/*  641 */     for (int k = 0; k < j; k++) {
/*  642 */       if (k < paramInt) localHyperGraph.cq[k] = paramHyperGraph.cq[k]; else
/*  643 */         localHyperGraph.cq[k] = paramHyperGraph.cq[(k + 1)];
/*      */     }
/*  645 */     if (paramInt == j) return localHyperGraph;
/*  646 */     localHyperGraph.modifyNodeIndex(paramInt + 1, j, -1);
/*  647 */     return localHyperGraph;
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
/*      */   public void addNeighbor(int paramInt, HLink paramHLink)
/*      */   {
/*  678 */     this.cq[paramInt].addHyperLink(paramHLink);
/*      */   }
/*      */   
/*      */   public boolean isLink(int paramInt1, int paramInt2)
/*      */   {
/*  683 */     return this.cq[paramInt1].isNeighbor(paramInt2);
/*      */   }
/*      */   
/*      */ 
/*      */   public void addNeighbor(int paramInt1, int paramInt2, int[] paramArrayOfInt)
/*      */   {
/*  689 */     if (isLink(paramInt1, paramInt2)) return;
/*  690 */     addNeighbor(paramInt1, new HLink(paramInt2, paramArrayOfInt));
/*  691 */     addNeighbor(paramInt2, new HLink(paramInt1, paramArrayOfInt));
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
/*      */   public boolean isConnected(int paramInt1, int paramInt2)
/*      */   {
/*  710 */     int[] arrayOfInt = null;
/*  711 */     arrayOfInt = MATH.addMember(paramInt1, arrayOfInt);
/*      */     int i;
/*      */     do {
/*  714 */       i = 0;
/*  715 */       for (int j = 0; j < arrayOfInt.length; j++) {
/*  716 */         int k = arrayOfInt[j];
/*  717 */         for (int m = 0; m < this.cq[k].getNeighborCount(); m++) {
/*  718 */           int n = this.cq[k].getNeighbor(m);
/*  719 */           if (n == paramInt2) { return true;
/*      */           }
/*  721 */           if (!MATH.member(n, arrayOfInt)) {
/*  722 */             arrayOfInt = MATH.addMember(n, arrayOfInt);
/*  723 */             i = 1;
/*      */           }
/*      */         }
/*      */       }
/*  727 */     } while (i == 1);
/*  728 */     return false;
/*      */   }
/*      */   
/*      */   public boolean isJoinTree()
/*      */   {
/*  733 */     int i = 0;
/*  734 */     for (int j = 0; j < this.cq.length; j++) if (this.cq[j].isCenter()) i++;
/*  735 */     if (i > 1) return false;
/*  736 */     return true;
/*      */   }
/*      */   
/*      */   int getJoinTreeCount()
/*      */   {
/*  741 */     int i = 0;
/*  742 */     for (int j = 0; j < this.cq.length; j++) if (this.cq[j].isCenter()) i++;
/*  743 */     return i;
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
/*      */   public void assignSepset()
/*      */   {
/*  761 */     int i = this.cq.length;
/*  762 */     if (i == 1) { return;
/*      */     }
/*  764 */     int[] arrayOfInt = new int[i];
/*  765 */     boolean[][] arrayOfBoolean = new boolean[i][i];
/*  766 */     int[][] arrayOfInt1 = new int[i][i];
/*      */     int k;
/*  768 */     for (int j = 0; j < i; j++) {
/*  769 */       arrayOfInt[j] = this.cq[j].getNeighborCount();
/*  770 */       for (k = 0; k < arrayOfInt[j]; k++) {
/*  771 */         arrayOfBoolean[j][k] = 0;
/*  772 */         arrayOfInt1[j][k] = this.cq[j].getNeighbor(k);
/*      */       }
/*      */     }
/*      */     
/*      */     do
/*      */     {
/*  778 */       j = 1;
/*  779 */       for (k = 0; k < i; k++) {
/*  780 */         if (arrayOfInt[k] == 1) {
/*  781 */           j = 0;
/*  782 */           for (int m = 0; m < this.cq[k].getNeighborCount(); m++) {
/*  783 */             if (arrayOfBoolean[k][m] == 0) {
/*  784 */               this.cq[k].setPeer(arrayOfInt1[k][m]);
/*  785 */               arrayOfBoolean[k][m] = 1;
/*  786 */               int n = this.cq[k].getPeer();
/*  787 */               for (int i1 = 0; i1 < this.cq[n].getNeighborCount(); i1++) {
/*  788 */                 if (arrayOfInt1[n][i1] == k) {
/*  789 */                   arrayOfBoolean[n][i1] = 1;
/*  790 */                   break;
/*      */                 }
/*      */               }
/*  793 */               arrayOfInt[k] -= 1;
/*  794 */               arrayOfInt[n] -= 1;
/*  795 */               break;
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*  800 */     } while (j == 0);
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
/*      */   public boolean isLocalCovered()
/*      */   {
/*  821 */     HyperGraph localHyperGraph = new HyperGraph(this);
/*  822 */     int i = localHyperGraph.getNodeCount();
/*  823 */     for (int j = 1; j <= i - 2; j++) {
/*  824 */       int k = 0;
/*  825 */       while (localHyperGraph.getNeighborCount(k) != 1) k++;
/*  826 */       int[] arrayOfInt1 = localHyperGraph.getCqMember(k);
/*      */       
/*  828 */       int m = localHyperGraph.getNeighbor(k, 0);
/*  829 */       int[] arrayOfInt2 = localHyperGraph.getCqMember(m);
/*      */       
/*  831 */       int[] arrayOfInt3 = null;
/*  832 */       int n = localHyperGraph.getNodeCount();
/*  833 */       for (int i1 = 0; i1 < n; i1++) {
/*  834 */         if ((i1 != k) && (i1 != m)) { arrayOfInt3 = MATH.union(arrayOfInt3, localHyperGraph.getCqMember(i1));
/*      */         }
/*      */       }
/*  837 */       HyperGrafM localHyperGrafM = HyperGrafM.delLeafCq(new HyperGrafM(localHyperGraph), k);
/*  838 */       int[] arrayOfInt4 = MATH.getIntersection(arrayOfInt1, arrayOfInt3);
/*  839 */       if (MATH.isSubset(arrayOfInt4, arrayOfInt2)) localHyperGraph = new HyperGraph(localHyperGrafM); else
/*  840 */         return false;
/*      */     }
/*  842 */     return true;
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
/*      */   public HyperGraph[] splitJoinTree(int[][] paramArrayOfInt)
/*      */   {
/*  861 */     Object localObject = new HyperGraph[1];
/*  862 */     localObject[0] = this;
/*      */     
/*  864 */     for (int i = 0; i < paramArrayOfInt.length; i++) {
/*  865 */       int j = 0;
/*  866 */       while (!localObject[j].isSepset(paramArrayOfInt[i])) { j++;
/*      */       }
/*  868 */       HyperGraph[] arrayOfHyperGraph1 = localObject[j].splitJoinTree(paramArrayOfInt[i]);
/*  869 */       int k = localObject.length;
/*  870 */       int m = arrayOfHyperGraph1.length;
/*  871 */       int n = k - 1 + m;
/*  872 */       HyperGraph[] arrayOfHyperGraph2 = new HyperGraph[n];
/*      */       
/*  874 */       for (int i1 = 0; i1 < j; i1++) arrayOfHyperGraph2[i1] = localObject[i1];
/*  875 */       for (i1 = j + 1; i1 < k; i1++) arrayOfHyperGraph2[(i1 - 1)] = localObject[i1];
/*  876 */       for (i1 = 0; i1 < m; i1++) arrayOfHyperGraph2[(i1 + k - 1)] = arrayOfHyperGraph1[i1];
/*  877 */       localObject = arrayOfHyperGraph2;
/*      */     }
/*  879 */     return (HyperGraph[])localObject;
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
/*      */   public HyperGraph[] splitJoinTree(int[] paramArrayOfInt)
/*      */   {
/*  898 */     int i = getNodeCount();
/*  899 */     int j = 0;
/*  900 */     Point[] arrayOfPoint = new Point[i - 1];
/*      */     
/*  902 */     for (int k = 0; k < i; k++) {
/*  903 */       localObject = getNeighbor(k);
/*      */       
/*  905 */       for (m = 0; m < localObject.length; m++) {
/*  906 */         if (localObject[m] > k) {
/*  907 */           arrayOfInt = getSepset(k, m);
/*  908 */           if (MATH.isEqualSet(paramArrayOfInt, arrayOfInt)) {
/*  909 */             arrayOfPoint[(j++)] = new Point(k, localObject[m]);
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*  915 */     for (k = 0; k < j; k++) { delLink(arrayOfPoint[k].x, arrayOfPoint[k].y);
/*      */     }
/*  917 */     k = j + 1;
/*  918 */     Object localObject = new int[k][];
/*  919 */     int m = 0;
/*  920 */     int[] arrayOfInt = new int[k];
/*  921 */     for (int n = 0; n < k; n++) {
/*  922 */       localObject[n] = new int[i - j];
/*  923 */       arrayOfInt[n] = 0;
/*      */     }
/*      */     
/*  926 */     localObject[m][arrayOfInt[m]] = 0;
/*  927 */     arrayOfInt[m] += 1;m++;
/*      */     int i2;
/*  929 */     for (n = 1; n < i; n++) {
/*  930 */       i1 = 0;
/*  931 */       for (i2 = 0; i2 < m; i2++) {
/*  932 */         if (isConnected(n, localObject[i2][0])) {
/*  933 */           localObject[i2][arrayOfInt[i2]] = n;
/*  934 */           arrayOfInt[i2] += 1;
/*  935 */           i1 = 1;
/*  936 */           break;
/*      */         }
/*      */       }
/*  939 */       if (i1 == 0) {
/*  940 */         localObject[m][arrayOfInt[m]] = n;
/*  941 */         arrayOfInt[m] += 1;m++;
/*      */       }
/*      */     }
/*      */     
/*  945 */     HyperGraph[] arrayOfHyperGraph = new HyperGraph[k];
/*  946 */     for (int i1 = 0; i1 < k; i1++) {
/*  947 */       arrayOfHyperGraph[i1] = new HyperGraph(arrayOfInt[i1]);
/*  948 */       for (i2 = 0; i2 < arrayOfInt[i1]; i2++) { arrayOfHyperGraph[i1].cq[i2] = new HNode(this.cq[localObject[i1][i2]]);
/*      */       }
/*      */       
/*      */ 
/*  952 */       for (i2 = 0; i2 < arrayOfInt[i1]; i2++) {
/*  953 */         int i3 = arrayOfHyperGraph[i1].cq[i2].getNeighborCount();
/*  954 */         if (i3 == 0) break;
/*  955 */         for (int i4 = 0; i4 < i3; i4++) {
/*  956 */           int i5 = arrayOfHyperGraph[i1].getNeighbor(i2, i4);
/*  957 */           arrayOfHyperGraph[i1].cq[i2].setNeighbor(i4, UTIL.getArrayIndex(i5, localObject[i1]));
/*      */         }
/*      */       }
/*      */     }
/*  961 */     return arrayOfHyperGraph;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static HyperGraph unionCq(HyperGraph paramHyperGraph, int[][] paramArrayOfInt)
/*      */   {
/*  973 */     HyperGraph localHyperGraph = new HyperGraph(paramHyperGraph);
/*      */     
/*  975 */     int i = 1;
/*  976 */     while (i != 0) {
/*  977 */       i = 0;
/*  978 */       int j = localHyperGraph.getNodeCount();
/*  979 */       for (int k = 0; k < j - 1; k++) {
/*  980 */         for (int m = k + 1; m < j; m++)
/*  981 */           if (localHyperGraph.isLink(k, m)) {
/*  982 */             int[] arrayOfInt = localHyperGraph.getSepset(new Point(k, m));
/*  983 */             if (!MATH.member(arrayOfInt, paramArrayOfInt)) {
/*  984 */               localHyperGraph = unionCq(localHyperGraph, k, m);
/*  985 */               i = 1;
/*  986 */               break;
/*      */             }
/*      */           }
/*  989 */         if (i != 0) break;
/*      */       }
/*      */     }
/*  992 */     return localHyperGraph;
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
/*      */   static HyperGraph unionCq(HyperGraph paramHyperGraph, int paramInt1, int paramInt2)
/*      */   {
/* 1009 */     HyperGraph localHyperGraph1 = new HyperGraph(paramHyperGraph);
/*      */     
/* 1011 */     int[] arrayOfInt1 = localHyperGraph1.getCqMember(paramInt1);
/* 1012 */     int[] arrayOfInt2 = localHyperGraph1.getCqMember(paramInt2);
/* 1013 */     int[] arrayOfInt3 = MATH.union(arrayOfInt1, arrayOfInt2);
/* 1014 */     localHyperGraph1.setCqMember(paramInt2, arrayOfInt3);
/*      */     
/* 1016 */     for (int i = 0; i < localHyperGraph1.getNeighborCount(paramInt1); i++) {
/* 1017 */       int j = localHyperGraph1.getNeighbor(paramInt1, i);
/* 1018 */       localHyperGraph1.delNeighbor(j, paramInt1);
/*      */       
/* 1020 */       if (j != paramInt2) {
/* 1021 */         HLink localHLink = localHyperGraph1.getHyperLinks(paramInt1, j);
/*      */         
/* 1023 */         localHLink.setNeighbor(paramInt2);
/* 1024 */         localHyperGraph1.addNeighbor(j, localHLink);
/* 1025 */         localHLink.setNeighbor(j);
/* 1026 */         localHyperGraph1.addNeighbor(paramInt2, localHLink);
/*      */       }
/*      */     }
/*      */     
/* 1030 */     i = localHyperGraph1.getNodeCount() - 1;
/* 1031 */     HyperGraph localHyperGraph2 = new HyperGraph(i);
/* 1032 */     for (int k = 0; k < i; k++) {
/* 1033 */       if (k < paramInt1) localHyperGraph2.cq[k] = localHyperGraph1.cq[k]; else
/* 1034 */         localHyperGraph2.cq[k] = localHyperGraph1.cq[(k + 1)];
/*      */     }
/* 1036 */     if (paramInt1 == i) return localHyperGraph2;
/* 1037 */     localHyperGraph2.modifyNodeIndex(paramInt1 + 1, i, -1);
/* 1038 */     return localHyperGraph2;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   void setJoinGraph(ChordalGraph paramChordalGraph)
/*      */   {
/* 1048 */     int i = this.cq.length;
/* 1049 */     for (int j = 0; j < i; j++) { this.cq[j].setCqMember(paramChordalGraph.cqCand[j]);
/*      */     }
/* 1051 */     for (j = 0; j < i - 1; j++) {
/* 1052 */       for (int k = j + 1; k < i; k++) {
/* 1053 */         int[] arrayOfInt = MATH.getIntersection(paramChordalGraph.cqCand[j], paramChordalGraph.cqCand[k]);
/* 1054 */         if (arrayOfInt != null) {
/* 1055 */           this.cq[j].addHyperLink(k, arrayOfInt);
/* 1056 */           this.cq[k].addHyperLink(j, arrayOfInt);
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public static HyperGraph setJoinGraphAshg(ChordalGraph paramChordalGraph)
/*      */   {
/* 1066 */     HyperGraph localHyperGraph = new HyperGraph();
/* 1067 */     int i = paramChordalGraph.cqCand.length;
/* 1068 */     localHyperGraph.setDumbNetPlus(i);
/* 1069 */     localHyperGraph.setJoinGraph(paramChordalGraph);
/* 1070 */     localHyperGraph.setVarCount(paramChordalGraph.nd.length);
/* 1071 */     return localHyperGraph;
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
/*      */   public void setJoinForestGraph()
/*      */   {
/* 1098 */     int i = this.cq.length;
/* 1099 */     HyperGraph localHyperGraph = new HyperGraph();
/* 1100 */     localHyperGraph.setDumbNetPlus(i);
/* 1101 */     for (int j = 0; j < i; j++) { localHyperGraph.cq[j].setCqMember(this.cq[j].getCqMember());
/*      */     }
/* 1103 */     boolean[] arrayOfBoolean = new boolean[i];
/* 1104 */     int[] arrayOfInt1 = new int[i];
/* 1105 */     int[] arrayOfInt2 = new int[i];
/* 1106 */     for (int k = 0; k < i; k++) {
/* 1107 */       arrayOfBoolean[k] = false;arrayOfInt1[k] = 0;arrayOfInt2[k] = -1;
/*      */     }
/*      */     
/* 1110 */     arrayOfBoolean[0] = true;
/* 1111 */     k = 1;
/* 1112 */     int m = 0;
/*      */     
/* 1114 */     while (k < i) {
/* 1115 */       n = this.cq[m].getNeighborCount();
/* 1116 */       for (int i1 = 0; i1 < n; i1++) {
/* 1117 */         i2 = this.cq[m].getNeighbor(i1);
/* 1118 */         if ((arrayOfBoolean[i2] == 0) && (this.cq[m].getSepsetSize(i1) > arrayOfInt1[i2]))
/*      */         {
/* 1120 */           arrayOfInt1[i2] = this.cq[m].getSepsetSize(i1);
/* 1121 */           arrayOfInt2[i2] = m;
/*      */         }
/*      */       }
/*      */       
/* 1125 */       i1 = 1;
/* 1126 */       while (arrayOfBoolean[i1] != 0) i1++;
/* 1127 */       m = i1;
/* 1128 */       int i2 = arrayOfInt1[(i1++)];
/* 1129 */       while (i1 < i) {
/* 1130 */         if ((arrayOfBoolean[i1] == 0) && 
/* 1131 */           (i2 < arrayOfInt1[i1])) {
/* 1132 */           i2 = arrayOfInt1[i1];m = i1;
/*      */         }
/*      */         
/* 1135 */         i1++;
/*      */       }
/*      */       
/*      */ 
/* 1139 */       if (i2 > 0) {
/* 1140 */         int i3 = arrayOfInt2[m];
/* 1141 */         int[] arrayOfInt3 = MATH.getIntersection(this.cq[m].getCqMember(), this.cq[i3].getCqMember());
/*      */         
/* 1143 */         localHyperGraph.cq[m].addHyperLink(i3, arrayOfInt3);
/* 1144 */         localHyperGraph.cq[i3].addHyperLink(m, arrayOfInt3);
/*      */       }
/* 1146 */       arrayOfBoolean[m] = true;
/* 1147 */       k++;
/*      */     }
/*      */     
/* 1150 */     for (int n = 0; n < i; n++) { this.cq[n].nbc = localHyperGraph.cq[n].nbc;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   static HyperGraph setJoinForestAshg(ChordalGraph paramChordalGraph)
/*      */   {
/* 1157 */     paramChordalGraph.findClique();
/* 1158 */     HyperGraph localHyperGraph = new HyperGraph();
/* 1159 */     int i = paramChordalGraph.cqCand.length;
/* 1160 */     localHyperGraph.setDumbNetPlus(i);
/* 1161 */     localHyperGraph.setJoinGraph(paramChordalGraph);
/* 1162 */     localHyperGraph.setVarCount(paramChordalGraph.nd.length);
/* 1163 */     localHyperGraph.setJoinForestGraph();
/* 1164 */     localHyperGraph.assignSepset();
/* 1165 */     localHyperGraph.setLabel();
/* 1166 */     localHyperGraph.setVarToCq(paramChordalGraph);
/* 1167 */     return localHyperGraph;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public static HyperGraph setJoinForest(ChordalGraph paramChordalGraph, Rectangle paramRectangle)
/*      */   {
/* 1174 */     HyperGraph localHyperGraph = setJoinForestAshg(paramChordalGraph);
/* 1175 */     localHyperGraph.setPeerOrder();
/* 1176 */     localHyperGraph.assignCqPos(paramRectangle);
/* 1177 */     return localHyperGraph;
/*      */   }
/*      */   
/*      */   public void setJoinForest(Rectangle paramRectangle)
/*      */   {
/* 1182 */     setJoinForestGraph();
/* 1183 */     assignSepset();
/* 1184 */     assignCqPos(paramRectangle);
/* 1185 */     setLabel();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   void loadNode(BufferedReader paramBufferedReader)
/*      */   {
/* 1192 */     int i = this.cq.length;
/*      */     try {
/* 1194 */       for (int j = 0; j < i; j++) {
/* 1195 */         paramBufferedReader.readLine();
/* 1196 */         this.cq[j] = HNode.loadHNode(paramBufferedReader, j);
/* 1197 */         if (this.cq[j] == null) throw new java.io.IOException("Unexpected end of input!");
/*      */       }
/*      */     }
/*      */     catch (java.io.IOException localIOException) {
/* 1201 */       HelpPanel.showError("Unable to complete load: " + localIOException.getMessage());
/*      */     }
/*      */   }
/*      */   
/*      */   void load(BufferedReader paramBufferedReader) {
/* 1206 */     int i = UTIL.loadInt(paramBufferedReader);
/* 1207 */     setDumbNetPlus(i);
/* 1208 */     loadNode(paramBufferedReader);
/*      */   }
/*      */   
/*      */   public static HyperGraph loadJoinTreeTrunk(String paramString) {
/* 1212 */     HyperGraph localHyperGraph = new HyperGraph();
/*      */     try {
/* 1214 */       BufferedReader localBufferedReader = new BufferedReader(new java.io.FileReader(paramString));
/* 1215 */       HelpPanel.addHelp("Loading join tree from " + paramString);
/* 1216 */       localHyperGraph.load(localBufferedReader);
/* 1217 */       localBufferedReader.close();
/*      */     } catch (java.io.IOException localIOException) {
/* 1219 */       HelpPanel.showError("Unable to load " + paramString);
/*      */     }
/* 1221 */     return localHyperGraph;
/*      */   }
/*      */   
/*      */   public void save(PrintWriter paramPrintWriter)
/*      */   {
/* 1226 */     paramPrintWriter.println(this.cq.length + "  #_of_clusters");
/* 1227 */     for (int i = 0; i < this.cq.length; i++) {
/* 1228 */       paramPrintWriter.println();
/* 1229 */       this.cq[i].save(paramPrintWriter, i);
/*      */     }
/*      */   }
/*      */   
/*      */   public void save(String paramString) {
/*      */     try {
/* 1235 */       PrintWriter localPrintWriter = new PrintWriter(new java.io.FileWriter(paramString));
/* 1236 */       HelpPanel.addHelp("Saving " + paramString);
/* 1237 */       save(localPrintWriter);
/* 1238 */       localPrintWriter.close();
/*      */     } catch (java.io.IOException localIOException) {
/* 1240 */       HelpPanel.showError("Unable to save " + paramString);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/* 1246 */   int[][] bdr = new int[2][];
/* 1247 */   int[] bdrCq = { -1, -1 };
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void getBorderCliques(DTGraph paramDTGraph)
/*      */   {
/* 1254 */     this.bdr[0] = paramDTGraph.getBorder(2, 3);
/* 1255 */     this.bdr[1] = paramDTGraph.getBorder(2, 4);
/*      */     
/* 1257 */     for (int i = 0; i < this.cq.length; i++) {
/* 1258 */       if (MATH.isSubset(this.bdr[0], getCqMember(i))) {
/* 1259 */         this.bdrCq[0] = i; break;
/*      */       }
/*      */     }
/* 1262 */     for (i = 0; i < this.cq.length; i++) {
/* 1263 */       if (MATH.isSubset(this.bdr[1], getCqMember(i))) {
/* 1264 */         this.bdrCq[1] = i; break;
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public void saveBorderCliques(PrintWriter paramPrintWriter)
/*      */   {
/* 1271 */     paramPrintWriter.print(this.bdr[0].length + "   ");
/* 1272 */     for (int i = 0; i < this.bdr[0].length; i++) paramPrintWriter.print(this.bdr[0][i] + " ");
/* 1273 */     paramPrintWriter.println("1st_runtime_interface");
/*      */     
/* 1275 */     paramPrintWriter.print(this.bdr[1].length + "   ");
/* 1276 */     for (i = 0; i < this.bdr[1].length; i++) paramPrintWriter.print(this.bdr[1][i] + " ");
/* 1277 */     paramPrintWriter.println("2nd_runtime_interface");
/*      */     
/* 1279 */     paramPrintWriter.println(this.bdrCq[0] + " " + this.bdrCq[1] + " interface_clusters");
/*      */   }
/*      */   
/*      */   public void loadBorderCliques(BufferedReader paramBufferedReader) {
/* 1283 */     this.bdr[0] = UTIL.loadIntList(paramBufferedReader);
/* 1284 */     this.bdr[1] = UTIL.loadIntList(paramBufferedReader);
/* 1285 */     this.bdrCq = UTIL.loadIntList(paramBufferedReader, 2);
/*      */   }
/*      */   
/*      */ 
/*      */   void seeHyperGraph()
/*      */   {
/* 1291 */     HelpPanel.showList("HyperGraph: tor=", this.tor);
/* 1292 */     HelpPanel.addHelp(" varCount=" + this.varCount);
/* 1293 */     HelpPanel.appendList("  varToCq=", this.varToCq);
/*      */     
/* 1295 */     HelpPanel.addHelp("# of cqs: " + this.cq.length);
/* 1296 */     for (int i = 0; i < this.cq.length; i++) this.cq[i].seeHNode();
/*      */   }
/*      */   
/* 1299 */   public void showHyperGraph() { System.out.println("  [HyperGraph]");
/* 1300 */     UTIL.showList(" tor=", this.tor);
/* 1301 */     System.out.println(" varCount=" + this.varCount);
/* 1302 */     UTIL.showList(" varToCq=", this.varToCq);
/*      */     
/* 1304 */     System.out.println("# clusters: " + this.cq.length);
/* 1305 */     for (int i = 0; i < this.cq.length; i++) {
/* 1306 */       System.out.print("cq " + i + ": ");
/* 1307 */       this.cq[i].showHNode();
/*      */     }
/*      */   }
/*      */ }


/* Location:              /home/anali/INAOE/tesis/codigos/topub/exe/ww4.jar!/Network/HyperGraph.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */