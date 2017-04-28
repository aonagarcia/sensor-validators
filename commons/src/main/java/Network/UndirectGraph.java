/*      */ package Network;
/*      */ 
/*      */ import java.awt.Point;
/*      */ import java.io.BufferedReader;
/*      */ import java.io.PrintStream;
/*      */ import java.io.PrintWriter;
/*      */ import java.util.Random;
/*      */ 
/*      */ public class UndirectGraph
/*      */ {
/*   11 */   UNode[] nd = null;
/*   12 */   int[] tor = null;
/*   13 */   Point[] linka = null; Point[] linkb = null;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*   21 */   public UndirectGraph() { init(); }
/*      */   
/*      */   public UndirectGraph(UndirectGraph paramUndirectGraph) {
/*   24 */     this();
/*   25 */     if ((paramUndirectGraph != null) && (paramUndirectGraph.nd != null)) setUndirectGraph(paramUndirectGraph);
/*      */   }
/*      */   
/*      */   public void init()
/*      */   {
/*   30 */     this.nd = null;
/*   31 */     this.tor = null;
/*   32 */     this.linka = null;
/*   33 */     this.linkb = null;
/*      */   }
/*      */   
/*      */   public void reinit()
/*      */   {
/*   38 */     init();
/*      */   }
/*      */   
/*      */   public void setDumbNet(int paramInt)
/*      */   {
/*   43 */     this.nd = new UNode[paramInt];
/*      */   }
/*      */   
/*      */   public void setDumbNetPlus(int paramInt)
/*      */   {
/*   48 */     setDumbNet(paramInt);
/*   49 */     for (int i = 0; i < paramInt; i++) this.nd[i] = new UNode();
/*      */   }
/*      */   
/*      */   void setUndirectGraph(UndirectGraph paramUndirectGraph) {
/*   53 */     int i = paramUndirectGraph.getNodeCount();
/*   54 */     setDumbNetPlus(i);
/*   55 */     for (int j = 0; j < i; j++) this.nd[j] = new UNode(paramUndirectGraph.getUNode(j));
/*   56 */     this.tor = UTIL.getDuplicate(paramUndirectGraph.getTreeOrder());
/*   57 */     this.linka = UTIL.getDuplicate(paramUndirectGraph.getLinka());
/*   58 */     this.linkb = UTIL.getDuplicate(paramUndirectGraph.getLinkb());
/*      */   }
/*      */   
/*      */   UNode getUNode(int paramInt) {
/*   62 */     return new UNode(this.nd[paramInt]);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static UndirectGraph simuGraph(int paramInt1, int paramInt2, Random paramRandom)
/*      */   {
/*   74 */     if ((paramInt2 < paramInt1 - 1) || (paramInt2 > paramInt1 * (paramInt1 - 1) / 2)) {
/*   75 */       System.out.println("Error: Wrong number of links!");
/*   76 */       return null;
/*      */     }
/*      */     
/*   79 */     UndirectGraph localUndirectGraph = new UndirectGraph();
/*   80 */     localUndirectGraph.setDumbNetPlus(paramInt1);
/*   81 */     int j; for (int i = 0; i < paramInt1; i++) {
/*   82 */       localUndirectGraph.setLabel(i, "v" + i);
/*   83 */       localUndirectGraph.setPos(i, MATH.getRandomInt(paramRandom, 1, 100), i * 100 / paramInt1);
/*   84 */       if (i != 0) {
/*   85 */         j = MATH.getRandomInt(paramRandom, 0, i - 1);
/*   86 */         localUndirectGraph.addLink(i, j);
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*   91 */     for (int m = paramInt1; m <= paramInt2; m++) {
/*      */       int k;
/*   93 */       do { k = 1;
/*   94 */         i = MATH.getRandomInt(paramRandom, 0, paramInt1 - 1);
/*   95 */         j = MATH.getRandomInt(paramRandom, 0, paramInt1 - 1);
/*   96 */         if (i == j) k = 0;
/*   97 */         if (localUndirectGraph.isLink(i, j)) k = 0;
/*   98 */       } while (k == 0);
/*   99 */       localUndirectGraph.addLink(i, j);
/*      */     }
/*  101 */     return localUndirectGraph;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public int getNodeCount()
/*      */   {
/*  108 */     if (this.nd == null) return 0;
/*  109 */     return this.nd.length;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public static UndirectGraph addNode(UndirectGraph paramUndirectGraph, Point paramPoint)
/*      */   {
/*  116 */     UndirectGraph localUndirectGraph = new UndirectGraph();
/*      */     
/*  118 */     int i = paramUndirectGraph.getNodeCount();
/*  119 */     localUndirectGraph.setDumbNet(i + 1);
/*      */     
/*  121 */     for (int j = 0; j < i; j++) { localUndirectGraph.nd[j] = paramUndirectGraph.nd[j];
/*      */     }
/*  123 */     localUndirectGraph.nd[i] = new UNode();
/*  124 */     localUndirectGraph.setPos(i, paramPoint);
/*  125 */     return localUndirectGraph;
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
/*      */   public static UndirectGraph delNode(UndirectGraph paramUndirectGraph, int paramInt)
/*      */   {
/*  139 */     int i = paramUndirectGraph.nd[paramInt].nls == null ? 0 : paramUndirectGraph.nd[paramInt].nls.length;
/*  140 */     for (int j = 0; j < i; j++) {
/*  141 */       k = paramUndirectGraph.nd[paramInt].nls[j];
/*  142 */       paramUndirectGraph.nd[k].delNeighbor(paramInt);
/*      */     }
/*      */     
/*  145 */     UndirectGraph localUndirectGraph = new UndirectGraph();
/*  146 */     int k = paramUndirectGraph.getNodeCount();
/*  147 */     localUndirectGraph.setDumbNet(k - 1);
/*      */     
/*  149 */     for (int m = 0; m < k - 1; m++) {
/*  150 */       if (m < paramInt) localUndirectGraph.nd[m] = paramUndirectGraph.nd[m]; else
/*  151 */         localUndirectGraph.nd[m] = paramUndirectGraph.nd[(m + 1)];
/*      */     }
/*  153 */     localUndirectGraph.modifyNodeIndex(paramInt + 1, k - 1, -1);
/*  154 */     return localUndirectGraph;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public void setLabel(int paramInt, String paramString)
/*      */   {
/*  161 */     this.nd[paramInt].setLabel(paramString);
/*      */   }
/*      */   
/*      */   public String getLabel(int paramInt)
/*      */   {
/*  166 */     String str = new String(this.nd[paramInt].getLabel());
/*  167 */     return str;
/*      */   }
/*      */   
/*      */   public String[] getLabel(int[] paramArrayOfInt) {
/*  171 */     if (paramArrayOfInt == null) return null;
/*  172 */     int i = paramArrayOfInt.length;
/*  173 */     String[] arrayOfString = new String[i];
/*  174 */     for (int j = 0; j < i; j++) arrayOfString[j] = getLabel(paramArrayOfInt[j]);
/*  175 */     return arrayOfString;
/*      */   }
/*      */   
/*      */   public String[] getLabel() {
/*  179 */     int i = getNodeCount();
/*  180 */     String[] arrayOfString = new String[i];
/*  181 */     for (int j = 0; j < i; j++) arrayOfString[j] = getLabel(j);
/*  182 */     return arrayOfString;
/*      */   }
/*      */   
/*      */ 
/*      */   public int getIndex(String paramString)
/*      */   {
/*  188 */     for (int i = 0; i < this.nd.length; i++)
/*  189 */       if (this.nd[i].getLabel().equals(paramString)) return i;
/*  190 */     return -1;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public int[] getIndex(String[] paramArrayOfString)
/*      */   {
/*  197 */     int[] arrayOfInt = new int[paramArrayOfString.length];
/*  198 */     for (int i = 0; i < paramArrayOfString.length; i++) {
/*  199 */       arrayOfInt[i] = getIndex(paramArrayOfString[i]);
/*  200 */       if (arrayOfInt[i] == -1) return null;
/*      */     }
/*  202 */     return arrayOfInt;
/*      */   }
/*      */   
/*      */   void setMark(int paramInt, boolean paramBoolean)
/*      */   {
/*  207 */     this.nd[paramInt].setMark(paramBoolean);
/*      */   }
/*      */   
/*      */   public void setMark(boolean paramBoolean) {
/*  211 */     for (int i = 0; i < getNodeCount(); i++) setMark(i, paramBoolean);
/*      */   }
/*      */   
/*      */   boolean isMarked(int paramInt)
/*      */   {
/*  216 */     return this.nd[paramInt].isMarked();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public void setNeighbor(int paramInt, int[] paramArrayOfInt)
/*      */   {
/*  223 */     this.nd[paramInt].setNeighbor(paramArrayOfInt);
/*      */   }
/*      */   
/*      */   public int getNeighborCount(int paramInt)
/*      */   {
/*  228 */     return this.nd[paramInt].getNeighborCount();
/*      */   }
/*      */   
/*      */   public int[] getNeighbor(int paramInt)
/*      */   {
/*  233 */     return this.nd[paramInt].getNeighbor();
/*      */   }
/*      */   
/*      */   public int getNeighbor(int paramInt1, int paramInt2)
/*      */   {
/*  238 */     return this.nd[paramInt1].getNeighbor(paramInt2);
/*      */   }
/*      */   
/*      */   int[] getInNeighbor(int paramInt, int[] paramArrayOfInt)
/*      */   {
/*  243 */     return MATH.getIntersection(this.nd[paramInt].getNeighbor(), paramArrayOfInt);
/*      */   }
/*      */   
/*      */   int[] getOutNeighbor(int paramInt, int[] paramArrayOfInt)
/*      */   {
/*  248 */     return MATH.setDifference(this.nd[paramInt].getNeighbor(), paramArrayOfInt);
/*      */   }
/*      */   
/*      */   public int[] getPeer()
/*      */   {
/*  253 */     int[] arrayOfInt = new int[this.nd.length];
/*  254 */     for (int i = 0; i < this.nd.length; i++) arrayOfInt[i] = this.nd[i].getPeer();
/*  255 */     return arrayOfInt;
/*      */   }
/*      */   
/*      */   void setLinks(int[] paramArrayOfInt)
/*      */   {
/*  260 */     int i = this.nd.length;
/*  261 */     for (int j = 0; j < i; j++) { this.nd[j].delNeighbor();
/*      */     }
/*  263 */     for (j = 0; j < paramArrayOfInt.length; j++) {
/*  264 */       if (paramArrayOfInt[j] != 0) {
/*  265 */         Point localPoint = MATH.indexToPair(i, j);
/*  266 */         addLink(localPoint);
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   public void addLink(int paramInt1, int paramInt2)
/*      */   {
/*  274 */     this.nd[paramInt1].addNeighbor(paramInt2);
/*  275 */     this.nd[paramInt2].addNeighbor(paramInt1);
/*      */   }
/*      */   
/*  278 */   public void addLink(Point paramPoint) { addLink(paramPoint.x, paramPoint.y); }
/*      */   
/*      */   public void addLink(Point[] paramArrayOfPoint) {
/*  281 */     if (paramArrayOfPoint == null) return;
/*  282 */     for (int i = 0; i < paramArrayOfPoint.length; i++) addLink(paramArrayOfPoint[i]);
/*      */   }
/*      */   
/*  285 */   public void addLink(String[][] paramArrayOfString) { if (paramArrayOfString == null) return;
/*  286 */     for (int i = 0; i < paramArrayOfString.length; i++) {
/*  287 */       addLink(getIndex(paramArrayOfString[i][0]), getIndex(paramArrayOfString[i][1]));
/*      */     }
/*      */   }
/*      */   
/*      */   void completeNodeSet(int[] paramArrayOfInt)
/*      */   {
/*  293 */     int i = paramArrayOfInt.length;
/*  294 */     if (i == 0) return;
/*  295 */     for (int j = 0; j < i - 1; j++) {
/*  296 */       for (int k = j + 1; k < i; k++) addLink(paramArrayOfInt[j], paramArrayOfInt[k]);
/*      */     }
/*      */   }
/*      */   
/*      */   public void delLink(int paramInt1, int paramInt2) {
/*  301 */     this.nd[paramInt1].delNeighbor(paramInt2);
/*  302 */     this.nd[paramInt2].delNeighbor(paramInt1);
/*      */   }
/*      */   
/*      */   int[] linksToNode(int[] paramArrayOfInt)
/*      */   {
/*  307 */     int[] arrayOfInt = null;
/*  308 */     for (int i = 0; i < paramArrayOfInt.length; i++) {
/*  309 */       Point localPoint = MATH.indexToPair(this.nd.length, paramArrayOfInt[i]);
/*  310 */       arrayOfInt = MATH.addMember(localPoint.x, arrayOfInt);
/*  311 */       arrayOfInt = MATH.addMember(localPoint.y, arrayOfInt);
/*      */     }
/*  313 */     return arrayOfInt;
/*      */   }
/*      */   
/*      */   public boolean isLink(int paramInt1, int paramInt2)
/*      */   {
/*  318 */     return this.nd[paramInt2].isNeighbor(paramInt1);
/*      */   }
/*      */   
/*  321 */   public boolean isLink(Point paramPoint) { return isLink(paramPoint.x, paramPoint.y); }
/*      */   
/*      */ 
/*      */   Point[] nodesToLinks(int[] paramArrayOfInt)
/*      */   {
/*  326 */     if (paramArrayOfInt == null) return null;
/*  327 */     int i = paramArrayOfInt.length;
/*  328 */     Point[] arrayOfPoint = null;
/*  329 */     for (int j = 0; j < i - 1; j++) {
/*  330 */       for (int k = j + 1; k < i; k++) if (isLink(paramArrayOfInt[j], paramArrayOfInt[k]))
/*  331 */           arrayOfPoint = MATH.appendMember(new Point(paramArrayOfInt[j], paramArrayOfInt[k]), arrayOfPoint);
/*      */     }
/*  333 */     return arrayOfPoint;
/*      */   }
/*      */   
/*      */   String[][] nodesToLinksLabel(int[] paramArrayOfInt)
/*      */   {
/*  338 */     if ((paramArrayOfInt == null) || (paramArrayOfInt.length == 1)) return (String[][])null;
/*  339 */     Point[] arrayOfPoint = nodesToLinks(paramArrayOfInt);
/*  340 */     int i = arrayOfPoint.length;
/*  341 */     String[][] arrayOfString = new String[i][2];
/*  342 */     for (int j = 0; j < i; j++) {
/*  343 */       arrayOfString[j][0] = getLabel(arrayOfPoint[j].x);
/*  344 */       arrayOfString[j][1] = getLabel(arrayOfPoint[j].y);
/*      */     }
/*  346 */     return arrayOfString;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public Point getPos(int paramInt)
/*      */   {
/*  353 */     Point localPoint = new Point(this.nd[paramInt].getPos());
/*  354 */     return localPoint;
/*      */   }
/*      */   
/*      */   public void setPos(int paramInt, Point paramPoint)
/*      */   {
/*  359 */     this.nd[paramInt].setPos(paramPoint);
/*      */   }
/*      */   
/*  362 */   public void setPos(int paramInt1, int paramInt2, int paramInt3) { this.nd[paramInt1].setPos(paramInt2, paramInt3); }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setPos(int paramInt1, int paramInt2, Random paramRandom)
/*      */   {
/*  374 */     int i = this.nd.length;
/*  375 */     int[] arrayOfInt1 = new int[i];
/*  376 */     for (int j = 0; j < i; j++) { arrayOfInt1[j] = getNeighborCount(j);
/*      */     }
/*  378 */     int[][] arrayOfInt = (int[][])null;
/*  379 */     int[] arrayOfInt2 = null;
/*  380 */     int i1; while (i > 0) {
/*  381 */       int[] arrayOfInt3 = null;
/*  382 */       for (m = 0; m < this.nd.length; m++) {
/*  383 */         if ((!MATH.member(m, arrayOfInt2)) && (arrayOfInt1[m] <= 1)) {
/*  384 */           arrayOfInt3 = MATH.addMember(m, arrayOfInt3);
/*  385 */           i--;
/*      */         }
/*      */       }
/*  388 */       if (arrayOfInt3 == null) {
/*  389 */         m = 0;
/*  390 */         while (MATH.member(m, arrayOfInt2)) m++;
/*  391 */         arrayOfInt3 = MATH.addMember(m, arrayOfInt3);i--;
/*  392 */         m++;
/*  393 */         while (MATH.member(m, arrayOfInt2)) m++;
/*  394 */         arrayOfInt3 = MATH.addMember(m, arrayOfInt3);i--;
/*      */       }
/*      */       
/*  397 */       for (m = 0; m < arrayOfInt3.length; m++) {
/*  398 */         int[] arrayOfInt4 = getOutNeighbor(arrayOfInt3[m], arrayOfInt2);
/*  399 */         if (arrayOfInt4 != null) {
/*  400 */           for (i1 = 0; i1 < arrayOfInt4.length; i1++)
/*  401 */             arrayOfInt1[arrayOfInt4[i1]] -= 1;
/*      */         }
/*      */       }
/*  404 */       arrayOfInt = MATH.appendMember(arrayOfInt3, arrayOfInt);
/*  405 */       arrayOfInt2 = MATH.union(arrayOfInt2, arrayOfInt3);
/*      */     }
/*      */     
/*  408 */     int k = paramInt2 / (arrayOfInt.length + 2);
/*  409 */     if (k == 0) k++;
/*  410 */     for (int m = 0; m < arrayOfInt.length; m++) {
/*  411 */       int n = paramInt1 / arrayOfInt[m].length;
/*  412 */       for (i1 = 0; i1 < arrayOfInt[m].length; i1++) {
/*  413 */         int i2 = (int)(paramRandom.nextFloat() * n / 3.0F) - n / 6;
/*  414 */         int i3 = (int)(paramRandom.nextFloat() * k / 3.0F) - k / 6;
/*  415 */         this.nd[arrayOfInt[m][i1]].setPos(n / 2 + i1 * n + i2, (m + 1) * k + i3);
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public void setPos(int paramInt1, int paramInt2) {
/*  421 */     Random localRandom = new Random();
/*  422 */     setPos(paramInt1, paramInt2, localRandom);
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
/*      */   public void setTreePos()
/*      */   {
/*  444 */     unmarkNode();
/*  445 */     int i = 0;
/*  446 */     int j = getNodeCount();int k = j;
/*  447 */     int[] arrayOfInt = null;
/*  448 */     while (k > 2) {
/*  449 */       arrayOfInt = null;
/*  450 */       for (m = 0; m < j; m++) {
/*  451 */         if ((!isMarked(m)) && (has1UnmarkedNb(m))) {
/*  452 */           arrayOfInt = MATH.addMember(m, arrayOfInt);k--;
/*      */         }
/*      */       }
/*  455 */       for (m = 0; m < arrayOfInt.length; m++) setMarked(arrayOfInt[m]);
/*  456 */       i++;
/*      */     }
/*      */     
/*  459 */     int m = getUnmarked(1);
/*  460 */     DirectGraph localDirectGraph = DirectGraph.getDirected(this, m);
/*      */     
/*      */     int[][] arrayOfInt1;
/*  463 */     if (k == 1) {
/*  464 */       arrayOfInt1 = new int[i * 2 + 1][];
/*  465 */       arrayOfInt1[i] = new int[1];arrayOfInt1[i][0] = m;
/*      */       
/*  467 */       n = arrayOfInt.length;
/*  468 */       i1 = n / 2;
/*  469 */       arrayOfInt1[(i - 1)] = new int[i1];
/*  470 */       for (i2 = 0; i2 < i1; i2++) arrayOfInt1[(i - 1)][i2] = arrayOfInt[i2];
/*  471 */       arrayOfInt1[(i + 1)] = MATH.setDifference(getNeighbor(m), arrayOfInt1[(i - 1)]);
/*      */       
/*  473 */       for (i2 = i - 1; i2 > 0; i2--) {
/*  474 */         for (i3 = 0; i3 < arrayOfInt1[i2].length; i3++)
/*  475 */           arrayOfInt1[(i2 - 1)] = UTIL.appendToArray(arrayOfInt1[(i2 - 1)], localDirectGraph.getChild(arrayOfInt1[i2][i3]));
/*      */       }
/*  477 */       for (i2 = i + 1; i2 < i * 2; i2++) {
/*  478 */         for (i3 = 0; i3 < arrayOfInt1[i2].length; i3++) {
/*  479 */           arrayOfInt1[(i2 + 1)] = UTIL.appendToArray(arrayOfInt1[(i2 + 1)], localDirectGraph.getChild(arrayOfInt1[i2][i3]));
/*      */         }
/*      */       }
/*      */     } else {
/*  483 */       n = getUnmarked(2);
/*  484 */       arrayOfInt1 = new int[(i + 1) * 2][];
/*  485 */       arrayOfInt1[i] = new int[1];arrayOfInt1[(i + 1)] = new int[1];
/*  486 */       arrayOfInt1[i][0] = m;arrayOfInt1[(i + 1)][0] = n;
/*      */       
/*  488 */       arrayOfInt1[(i - 1)] = MATH.setDifference(getNeighbor(m), arrayOfInt1[(i + 1)]);
/*      */       
/*  490 */       for (i1 = i - 1; i1 > 0; i1--) {
/*  491 */         for (i2 = 0; i2 < arrayOfInt1[i1].length; i2++)
/*  492 */           arrayOfInt1[(i1 - 1)] = UTIL.appendToArray(arrayOfInt1[(i1 - 1)], localDirectGraph.getChild(arrayOfInt1[i1][i2]));
/*      */       }
/*  494 */       for (i1 = i + 1; i1 <= i * 2; i1++) {
/*  495 */         for (i2 = 0; i2 < arrayOfInt1[i1].length; i2++) {
/*  496 */           arrayOfInt1[(i1 + 1)] = UTIL.appendToArray(arrayOfInt1[(i1 + 1)], localDirectGraph.getChild(arrayOfInt1[i1][i2]));
/*      */         }
/*      */       }
/*      */     }
/*  500 */     int n = 20;int i1 = 10;int i2 = 0;
/*  501 */     for (int i3 = 0; i3 < arrayOfInt1.length; i3++) {
/*  502 */       if (arrayOfInt1[i3].length > i2) i2 = arrayOfInt1[i3].length;
/*      */     }
/*  504 */     i3 = (i2 - 1) * n;
/*      */     
/*  506 */     for (int i4 = 0; i4 < arrayOfInt1.length; i4++) {
/*  507 */       int i5 = arrayOfInt1[i4].length;
/*  508 */       if (i5 == 1) {
/*  509 */         setPos(arrayOfInt1[i4][0], new Point(i3 / 2, n * i4));
/*      */       }
/*      */       else {
/*  512 */         int i6 = i3 / (i5 - 1);
/*  513 */         for (int i7 = 0; i7 < i5; i7++) { setPos(arrayOfInt1[i4][i7], new Point(i6 * i7, n * i4));
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   public int isClose(Point paramPoint, int paramInt)
/*      */   {
/*  522 */     int i = this.nd.length;
/*  523 */     for (int j = 0; j < i; j++)
/*  524 */       if (this.nd[j].isClose(paramPoint, paramInt) == 0) return j;
/*  525 */     for (j = 0; j < i; j++)
/*  526 */       if (this.nd[j].isClose(paramPoint, paramInt) == -1) return -1;
/*  527 */     return -2;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   int loadNodeCount(BufferedReader paramBufferedReader)
/*      */   {
/*  534 */     return UTIL.loadInt(paramBufferedReader);
/*      */   }
/*      */   
/*      */   public void loadPos(BufferedReader paramBufferedReader)
/*      */   {
/*  539 */     for (int i = 0; i < this.nd.length; i++) this.nd[i].loadPos(paramBufferedReader);
/*      */   }
/*      */   
/*      */   public void loadPos(String paramString) {
/*  543 */     try { BufferedReader localBufferedReader = new BufferedReader(new java.io.FileReader(paramString));
/*  544 */       loadPos(localBufferedReader);
/*  545 */       localBufferedReader.close();
/*      */     } catch (java.io.IOException localIOException) {
/*  547 */       System.out.println("Unable to read .cod file!");
/*      */     }
/*      */   }
/*      */   
/*      */   void saveLabel(PrintWriter paramPrintWriter, int paramInt) {
/*  552 */     this.nd[paramInt].saveLabel(paramPrintWriter);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   boolean isComplete(int[] paramArrayOfInt)
/*      */   {
/*  559 */     int i = paramArrayOfInt.length;
/*  560 */     for (int j = 0; j <= i - 2; j++)
/*  561 */       for (int k = j + 1; k <= i - 1; k++)
/*  562 */         if (!isLink(paramArrayOfInt[j], paramArrayOfInt[k])) return false;
/*  563 */     return true;
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
/*      */   public boolean isConnected()
/*      */   {
/*  581 */     int[] arrayOfInt = null;
/*  582 */     arrayOfInt = MATH.addMember(0, arrayOfInt);
/*      */     int i;
/*      */     do {
/*  585 */       i = 0;
/*  586 */       for (int j = 0; j < arrayOfInt.length; j++) {
/*  587 */         int k = arrayOfInt[j];
/*  588 */         for (int m = 0; m < this.nd[k].getNeighborCount(); m++) {
/*  589 */           int n = this.nd[k].getNeighbor(m);
/*  590 */           if (!MATH.member(n, arrayOfInt)) {
/*  591 */             arrayOfInt = MATH.addMember(n, arrayOfInt);
/*  592 */             i = 1;
/*      */           }
/*      */         }
/*      */       }
/*  596 */     } while (i == 1);
/*  597 */     if (arrayOfInt.length == this.nd.length) return true;
/*  598 */     return false;
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
/*      */   boolean isConnected(int[] paramArrayOfInt)
/*      */   {
/*  614 */     int[] arrayOfInt = null;
/*  615 */     arrayOfInt = MATH.addMember(paramArrayOfInt[0], arrayOfInt);
/*      */     int i;
/*      */     do {
/*  618 */       i = 0;
/*  619 */       for (int j = 0; j < arrayOfInt.length; j++) {
/*  620 */         int k = arrayOfInt[j];
/*  621 */         for (int m = 0; m < this.nd[k].getNeighborCount(); m++) {
/*  622 */           int n = this.nd[k].getNeighbor(m);
/*  623 */           if (!MATH.member(n, arrayOfInt)) {
/*  624 */             arrayOfInt = MATH.addMember(n, arrayOfInt);
/*  625 */             i = 1;
/*      */           }
/*      */         }
/*      */       }
/*  629 */     } while (i == 1);
/*  630 */     if (MATH.isSubset(paramArrayOfInt, arrayOfInt)) return true;
/*  631 */     return false;
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
/*      */   boolean isConnected(int paramInt1, int paramInt2)
/*      */   {
/*  647 */     int[] arrayOfInt = null;
/*  648 */     arrayOfInt = MATH.addMember(paramInt1, arrayOfInt);
/*      */     int i;
/*      */     do {
/*  651 */       i = 0;
/*  652 */       for (int j = 0; j < arrayOfInt.length; j++) {
/*  653 */         int k = arrayOfInt[j];
/*  654 */         for (int m = 0; m < this.nd[k].getNeighborCount(); m++) {
/*  655 */           int n = this.nd[k].getNeighbor(m);
/*  656 */           if (!MATH.member(n, arrayOfInt)) {
/*  657 */             arrayOfInt = MATH.addMember(n, arrayOfInt);
/*  658 */             i = 1;
/*      */           }
/*      */         }
/*      */       }
/*  662 */     } while (i == 1);
/*  663 */     if (MATH.member(paramInt2, arrayOfInt)) return true;
/*  664 */     return false;
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
/*      */   public int[][] getConnected(int[] paramArrayOfInt1, int[] paramArrayOfInt2)
/*      */   {
/*  691 */     int[] arrayOfInt = UTIL.getDuplicate(paramArrayOfInt1);
/*      */     int i;
/*      */     do {
/*  694 */       i = 0;
/*  695 */       for (int j = 0; j < arrayOfInt.length; j++) {
/*  696 */         int k = arrayOfInt[j];
/*  697 */         for (int m = 0; m < this.nd[k].getNeighborCount(); m++) {
/*  698 */           int n = this.nd[k].getNeighbor(m);
/*  699 */           if ((MATH.member(n, paramArrayOfInt2)) && (!MATH.member(n, arrayOfInt))) {
/*  700 */             arrayOfInt = MATH.addMember(n, arrayOfInt);
/*  701 */             i = 1;
/*      */           }
/*      */         }
/*      */       }
/*  705 */       UTIL.showList("group=", arrayOfInt);
/*  706 */     } while (i == 1);
/*      */     
/*  708 */     int[][] arrayOfInt1 = new int[2][];
/*  709 */     arrayOfInt1[0] = arrayOfInt;
/*  710 */     arrayOfInt1[1] = MATH.setDifference(paramArrayOfInt2, arrayOfInt);
/*  711 */     return arrayOfInt1;
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
/*      */   public Point[] connectGraph()
/*      */   {
/*  732 */     Point[] arrayOfPoint = null;
/*  733 */     int[] arrayOfInt = null;
/*  734 */     arrayOfInt = MATH.addMember(0, arrayOfInt);
/*      */     
/*      */     for (;;)
/*      */     {
/*  738 */       int i = 0;
/*  739 */       for (int j = 0; j < arrayOfInt.length; j++) {
/*  740 */         int k = arrayOfInt[j];
/*  741 */         for (int m = 0; m < this.nd[k].getNeighborCount(); m++) {
/*  742 */           int n = this.nd[k].getNeighbor(m);
/*  743 */           if (!MATH.member(n, arrayOfInt)) {
/*  744 */             arrayOfInt = MATH.addMember(n, arrayOfInt);
/*  745 */             i = 1;
/*      */           }
/*      */         }
/*      */       }
/*  749 */       if (i != 1)
/*      */       {
/*  751 */         if (arrayOfInt.length == this.nd.length) { return arrayOfPoint;
/*      */         }
/*  753 */         j = 1;
/*  754 */         while (MATH.member(j, arrayOfInt)) j++;
/*  755 */         this.nd[j].addNeighbor(j - 1);
/*  756 */         this.nd[(j - 1)].addNeighbor(j);
/*  757 */         arrayOfPoint = MATH.appendMember(new Point(j - 1, j), arrayOfPoint);
/*  758 */         arrayOfInt = MATH.addMember(j, arrayOfInt);
/*      */       }
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
/*      */   public UndirectGraph getSpanningTree()
/*      */   {
/*  773 */     UndirectGraph localUndirectGraph1 = new UndirectGraph(this);
/*  774 */     int i = localUndirectGraph1.getNodeCount();
/*  775 */     if (i < 3) { return localUndirectGraph1;
/*      */     }
/*  777 */     localUndirectGraph1.setMark(false);
/*  778 */     UndirectGraph localUndirectGraph2 = new UndirectGraph();
/*  779 */     localUndirectGraph2.setDumbNetPlus(i);
/*  780 */     for (int j = 0; j < i; j++) {
/*  781 */       localUndirectGraph2.setPos(j, localUndirectGraph1.getPos(j));
/*  782 */       localUndirectGraph2.setLabel(j, localUndirectGraph1.getLabel(j));
/*      */     }
/*      */     
/*  785 */     localUndirectGraph1.setMark(0, true);
/*  786 */     for (j = 1; j < i; j++)
/*  787 */       for (int k = 0; k < i; k++)
/*  788 */         if (localUndirectGraph1.isMarked(k)) {
/*  789 */           int m = localUndirectGraph1.getNeighborCount(k);
/*  790 */           for (int n = 0; n < m; n++) {
/*  791 */             int i1 = localUndirectGraph1.getNeighbor(k, n);
/*  792 */             if (!localUndirectGraph1.isMarked(i1))
/*      */             {
/*  794 */               localUndirectGraph2.addLink(k, i1);
/*  795 */               localUndirectGraph1.setMark(i1, true);
/*      */             }
/*      */           }
/*      */         }
/*  799 */     return localUndirectGraph2;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   void modifyNodeIndex(int paramInt1, int paramInt2, int paramInt3)
/*      */   {
/*  807 */     for (int i = 0; i < this.nd.length; i++) { this.nd[i].modifyNodeIndex(paramInt1, paramInt2, paramInt3);
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
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setPeerOrder()
/*      */   {
/*  825 */     int i = getNodeCount();
/*  826 */     if (i == 0) return;
/*  827 */     if (i == 1) {
/*  828 */       this.tor = new int[1];this.tor[0] = 0;this.nd[0].setPeer(-1);return;
/*      */     }
/*      */     
/*  831 */     for (int j = 0; j < i; j++) { this.nd[j].setPeer(-1);
/*      */     }
/*  833 */     int[] arrayOfInt = new int[i];
/*  834 */     boolean[][] arrayOfBoolean = new boolean[i][i];
/*  835 */     boolean[] arrayOfBoolean1 = new boolean[i];
/*  836 */     for (int k = 0; k < i; k++) {
/*  837 */       arrayOfInt[k] = this.nd[k].getNeighborCount();
/*  838 */       for (m = 0; m < arrayOfInt[k]; m++) arrayOfBoolean[k][m] = 0;
/*  839 */       arrayOfBoolean1[k] = false;
/*      */     }
/*      */     
/*  842 */     k = 0;
/*  843 */     this.tor = new int[i];
/*  844 */     int m = i - 1;
/*  845 */     while (k == 0) {
/*  846 */       k = 1;
/*  847 */       for (int n = 0; n < i; n++) {
/*  848 */         if (arrayOfInt[n] == 1) {
/*  849 */           arrayOfBoolean1[n] = true;
/*  850 */           k = 0;
/*      */         }
/*      */       }
/*      */       
/*  854 */       if (m == 0) {
/*  855 */         n = 0;
/*  856 */         while (this.nd[n].getPeer() != -1) n++;
/*  857 */         this.tor[0] = n; return;
/*      */       }
/*      */       int i1;
/*      */       int i2;
/*  861 */       if (m == 1) {
/*  862 */         n = 0;
/*  863 */         while (this.nd[n].getPeer() != -1) n++;
/*  864 */         i1 = 0;
/*  865 */         while (arrayOfBoolean[n][i1] != 0) i1++;
/*  866 */         i2 = this.nd[n].getNeighbor(i1);
/*  867 */         this.nd[n].setPeer(i2);
/*  868 */         this.tor[1] = n;this.tor[0] = i2;
/*  869 */         return;
/*      */       }
/*      */       
/*      */ 
/*  873 */       for (n = 0; n < i; n++) {
/*  874 */         if (arrayOfBoolean1[n] != 0)
/*      */         {
/*  876 */           i1 = 0;
/*  877 */           while (arrayOfBoolean[n][i1] != 0) i1++;
/*  878 */           i2 = this.nd[n].getNeighbor(i1);
/*  879 */           this.nd[n].setPeer(i2);
/*  880 */           arrayOfBoolean[n][i1] = 1;
/*      */           
/*  882 */           int i3 = 0;
/*  883 */           while (this.nd[i2].getNeighbor(i3) != n) i3++;
/*  884 */           arrayOfBoolean[i2][i3] = 1;
/*  885 */           arrayOfInt[n] -= 1;arrayOfInt[i2] -= 1;
/*      */           
/*  887 */           this.tor[(m--)] = n;
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public int[] getTreeOrder() {
/*  894 */     if (this.tor == null) { return null;
/*      */     }
/*  896 */     int[] arrayOfInt = new int[this.tor.length];
/*  897 */     for (int i = 0; i < this.tor.length; i++) arrayOfInt[i] = this.tor[i];
/*  898 */     return arrayOfInt;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setBoundedTree(int paramInt1, int paramInt2, Random paramRandom)
/*      */   {
/*  910 */     setDumbNetPlus(paramInt1);
/*  911 */     for (int i = 0; i < paramInt1; i++) {
/*  912 */       setLabel(i, Integer.toString(i));
/*  913 */       this.nd[i].setPeer(paramInt2);
/*  914 */       this.nd[i].setMark(false);
/*      */     }
/*      */     
/*  917 */     i = 0;
/*  918 */     this.nd[((int)(paramRandom.nextFloat() * paramInt1))].setMark(true);
/*  919 */     for (;;) { i++; if (i >= paramInt1)
/*      */         break;
/*      */       int j;
/*  922 */       do { j = (int)(paramRandom.nextFloat() * paramInt1);
/*  923 */       } while (this.nd[j].isMarked());
/*      */       int k;
/*      */       do {
/*  926 */         k = (int)(paramRandom.nextFloat() * paramInt1);
/*  927 */       } while ((!this.nd[k].isMarked()) || (this.nd[k].getPeer() == 0));
/*      */       
/*  929 */       this.nd[j].setMark(true);
/*  930 */       addLink(j, k);
/*  931 */       this.nd[j].setPeer(this.nd[j].getPeer() - 1);
/*  932 */       this.nd[k].setPeer(this.nd[k].getPeer() - 1);
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
/*      */   public boolean isForest()
/*      */   {
/*  966 */     int i = getNodeCount();
/*  967 */     int j = 0;
/*  968 */     while (j == 0) {
/*  969 */       j = 1;
/*  970 */       for (k = 0; k < i; k++) {
/*  971 */         if ((!this.nd[k].isMarked()) && 
/*  972 */           (has1or0UnmarkedNbr(k))) {
/*  973 */           this.nd[k].setMark();
/*  974 */           j = 0;
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*  980 */     int k = 0;
/*  981 */     while ((k < i) && (this.nd[k].isMarked())) k++;
/*  982 */     unmarkNode();
/*  983 */     if (k == i) return true;
/*  984 */     return false;
/*      */   }
/*      */   
/*      */   public boolean isTree()
/*      */   {
/*  989 */     return (isConnected()) && (isForest());
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   static UndirectGraph makeSubGraph(UndirectGraph paramUndirectGraph, int[] paramArrayOfInt)
/*      */   {
/*  999 */     UndirectGraph localUndirectGraph = new UndirectGraph();
/* 1000 */     int i = paramArrayOfInt.length;
/* 1001 */     localUndirectGraph.setDumbNetPlus(i);
/* 1002 */     for (int j = 0; j < i; j++) {
/* 1003 */       int[] arrayOfInt = paramUndirectGraph.nd[paramArrayOfInt[j]].getNeighbor();
/* 1004 */       localUndirectGraph.nd[j].setNeighbor(MATH.getIntersection(arrayOfInt, paramArrayOfInt));
/* 1005 */       localUndirectGraph.nd[j].renameNeighbor(paramArrayOfInt);
/*      */     }
/* 1007 */     return localUndirectGraph;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   Point[] getNewLinks(DirectGraph paramDirectGraph)
/*      */   {
/* 1017 */     int i = getNodeCount();
/* 1018 */     Point[] arrayOfPoint = null;
/* 1019 */     for (int j = 0; j < i - 1; j++) {
/* 1020 */       for (int k = j + 1; k < i; k++) {
/* 1021 */         if ((isLink(j, k)) && 
/* 1022 */           (!paramDirectGraph.isArc(j, k)) && (!paramDirectGraph.isArc(k, j)))
/*      */         {
/* 1024 */           arrayOfPoint = MATH.appendMember(new Point(j, k), arrayOfPoint); }
/*      */       }
/*      */     }
/* 1027 */     return arrayOfPoint;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setSkeleton(DirectGraph paramDirectGraph)
/*      */   {
/* 1036 */     for (int i = 0; i < this.nd.length; i++) {
/* 1037 */       int[] arrayOfInt = MATH.union(paramDirectGraph.nd[i].getParent(), paramDirectGraph.nd[i].getChild());
/* 1038 */       this.nd[i].setNeighbor(arrayOfInt);
/* 1039 */       this.nd[i].setPos(paramDirectGraph.nd[i].getPos());
/*      */     }
/*      */     
/* 1042 */     for (i = 0; i < this.nd.length; i++) {
/* 1043 */       this.nd[i].setLabel(paramDirectGraph.getLabel(i));
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public static UndirectGraph makeSkeleton(DirectGraph paramDirectGraph)
/*      */   {
/* 1051 */     UndirectGraph localUndirectGraph = new UndirectGraph();
/* 1052 */     int i = paramDirectGraph.getNodeCount();
/* 1053 */     localUndirectGraph.setDumbNetPlus(i);
/* 1054 */     localUndirectGraph.setSkeleton(paramDirectGraph);
/* 1055 */     return localUndirectGraph;
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
/*      */   public DirectGraph directTree(HyperGraph[] paramArrayOfHyperGraph, int paramInt, Random paramRandom)
/*      */   {
/* 1083 */     int i = getNodeCount();
/* 1084 */     int[][] arrayOfInt = new int[i][];
/* 1085 */     boolean[] arrayOfBoolean = new boolean[i];
/* 1086 */     for (int j = 1; j < i; j++) { arrayOfBoolean[j] = false;
/*      */     }
/* 1088 */     setMark(false);
/* 1089 */     setMark(0, true);
/* 1090 */     int m; int n; int i1; for (j = 1; j < i; j++) {
/* 1091 */       k = 0;
/*      */       
/* 1093 */       while ((k < i - 1) && ((!isMarked(k)) || (arrayOfBoolean[k] != 0))) { k++;
/*      */       }
/* 1095 */       m = getNeighborCount(k);
/* 1096 */       for (n = 0; n < m; n++) {
/* 1097 */         i1 = getNeighbor(k, n);
/* 1098 */         if (!isMarked(i1))
/*      */         {
/* 1100 */           if (arrayOfInt[k] == null) {
/* 1101 */             if (paramRandom.nextFloat() < 0.5D) {
/* 1102 */               arrayOfInt[k] = MATH.addMember(i1, arrayOfInt[k]);
/*      */             }
/*      */             else
/*      */             {
/* 1106 */               arrayOfInt[i1] = MATH.addMember(k, arrayOfInt[i1]);
/*      */             }
/*      */             
/*      */ 
/*      */           }
/* 1111 */           else if (arrayOfInt[k].length >= paramInt) {
/* 1112 */             arrayOfInt[i1] = MATH.addMember(k, arrayOfInt[i1]);
/*      */ 
/*      */           }
/*      */           else
/*      */           {
/* 1117 */             int[] arrayOfInt1 = MATH.addMember(k, arrayOfInt[k]);
/* 1118 */             arrayOfInt1 = MATH.addMember(i1, arrayOfInt1);
/* 1119 */             int i2 = 0;
/* 1120 */             for (int i3 = 0; i3 < paramArrayOfHyperGraph.length; i3++) {
/* 1121 */               int i4 = paramArrayOfHyperGraph[i3].getNodeCount();
/* 1122 */               for (int i5 = 0; i5 < i4; i5++) {
/* 1123 */                 if (MATH.isSubset(arrayOfInt1, paramArrayOfHyperGraph[i3].getCqMember(i5))) {
/* 1124 */                   i2 = 1;
/* 1125 */                   if (paramRandom.nextFloat() < 0.5D) {
/* 1126 */                     arrayOfInt[k] = MATH.addMember(i1, arrayOfInt[k]); break;
/*      */                   }
/*      */                   
/*      */ 
/* 1130 */                   arrayOfInt[i1] = MATH.addMember(k, arrayOfInt[i1]);
/*      */                   
/*      */ 
/* 1133 */                   break;
/*      */                 }
/*      */               }
/* 1136 */               if (i2 != 0)
/*      */                 break;
/*      */             }
/* 1139 */             if (i2 == 0) {
/* 1140 */               arrayOfInt[i1] = MATH.addMember(k, arrayOfInt[i1]);
/*      */             }
/*      */           }
/*      */           
/* 1144 */           setMark(i1, true);
/*      */         } }
/* 1146 */       arrayOfBoolean[k] = true;
/*      */     }
/*      */     
/*      */ 
/* 1150 */     DirectGraph localDirectGraph = new DirectGraph();
/* 1151 */     localDirectGraph.setDumbNetPlus(i);
/* 1152 */     for (int k = 0; k < i; k++) {
/* 1153 */       m = arrayOfInt[k] == null ? 0 : arrayOfInt[k].length;
/* 1154 */       for (n = 0; n < m; n++) {
/* 1155 */         i1 = arrayOfInt[k][n];
/* 1156 */         localDirectGraph.nd[k].addParent(i1);
/* 1157 */         localDirectGraph.nd[i1].addChild(k);
/*      */       }
/*      */     }
/*      */     
/* 1161 */     for (k = 0; k < i; k++) {
/* 1162 */       localDirectGraph.setPos(k, getPos(k));
/* 1163 */       localDirectGraph.setLabel(k, getLabel(k));
/*      */     }
/* 1165 */     return localDirectGraph;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private boolean has1or0UnmarkedNbr(int paramInt)
/*      */   {
/* 1173 */     int[] arrayOfInt = getNeighbor(paramInt);
/* 1174 */     if (arrayOfInt == null) return false;
/* 1175 */     int i = 0;
/* 1176 */     for (int j = 0; j < arrayOfInt.length; j++) {
/* 1177 */       int k = arrayOfInt[j];
/* 1178 */       if (!this.nd[k].isMarked()) i++;
/*      */     }
/* 1180 */     if (i <= 1) return true;
/* 1181 */     return false;
/*      */   }
/*      */   
/*      */ 
/*      */   boolean has1UnmarkedNb(int paramInt)
/*      */   {
/* 1187 */     int[] arrayOfInt = getNeighbor(paramInt);
/* 1188 */     if (arrayOfInt == null) return false;
/* 1189 */     int i = 0;
/* 1190 */     for (int j = 0; j < arrayOfInt.length; j++) {
/* 1191 */       int k = arrayOfInt[j];
/* 1192 */       if (!this.nd[k].isMarked()) i++;
/*      */     }
/* 1194 */     if (i == 1) return true;
/* 1195 */     return false;
/*      */   }
/*      */   
/*      */   void unmarkNode()
/*      */   {
/* 1200 */     for (int i = 0; i < this.nd.length; i++) this.nd[i].setMark(false);
/*      */   }
/*      */   
/*      */   void setMarked(int paramInt)
/*      */   {
/* 1205 */     this.nd[paramInt].setMark();
/*      */   }
/*      */   
/*      */   int getUnmarked(int paramInt)
/*      */   {
/* 1210 */     int i = 0;
/* 1211 */     for (int j = 0; j < this.nd.length; j++) {
/* 1212 */       if (!this.nd[j].isMarked()) {
/* 1213 */         i++;
/* 1214 */         if (i == paramInt) return j;
/*      */       }
/*      */     }
/* 1217 */     return -1;
/*      */   }
/*      */   
/*      */ 
/*      */   Point[] getLinka()
/*      */   {
/* 1223 */     if (this.linka == null) return null;
/* 1224 */     Point[] arrayOfPoint = new Point[this.linka.length];
/* 1225 */     for (int i = 0; i < this.linka.length; i++) arrayOfPoint[i] = new Point(this.linka[i]);
/* 1226 */     return arrayOfPoint;
/*      */   }
/*      */   
/* 1229 */   Point[] getLinkb() { if (this.linkb == null) return null;
/* 1230 */     Point[] arrayOfPoint = new Point[this.linkb.length];
/* 1231 */     for (int i = 0; i < this.linkb.length; i++) arrayOfPoint[i] = new Point(this.linkb[i]);
/* 1232 */     return arrayOfPoint;
/*      */   }
/*      */   
/*      */   void setLinka(Point[] paramArrayOfPoint) {
/* 1236 */     if (paramArrayOfPoint == null) {
/* 1237 */       this.linka = null;return;
/*      */     }
/* 1239 */     this.linka = UTIL.getDuplicate(paramArrayOfPoint);
/*      */   }
/*      */   
/*      */   void addLinka(Point[] paramArrayOfPoint) {
/* 1243 */     this.linka = UTIL.appendToArray(this.linka, paramArrayOfPoint);
/*      */   }
/*      */   
/*      */   public void setLinkb(Point[] paramArrayOfPoint) {
/* 1247 */     if (paramArrayOfPoint == null) {
/* 1248 */       this.linkb = null;return;
/*      */     }
/* 1250 */     this.linkb = new Point[paramArrayOfPoint.length];
/* 1251 */     for (int i = 0; i < paramArrayOfPoint.length; i++) { this.linkb[i] = new Point(paramArrayOfPoint[i].x, paramArrayOfPoint[i].y);
/*      */     }
/*      */   }
/*      */   
/*      */   boolean isLinka(Point paramPoint)
/*      */   {
/* 1257 */     Point localPoint = new Point(paramPoint.y, paramPoint.x);
/* 1258 */     return (MATH.member(paramPoint, this.linka)) || (MATH.member(localPoint, this.linka));
/*      */   }
/*      */   
/* 1261 */   boolean isLinkb(Point paramPoint) { Point localPoint = new Point(paramPoint.y, paramPoint.x);
/* 1262 */     return (MATH.member(paramPoint, this.linkb)) || (MATH.member(localPoint, this.linkb));
/*      */   }
/*      */   
/*      */ 
/*      */   public void showUndirectGraph()
/*      */   {
/* 1268 */     System.out.println("UndirectGraph:");
/* 1269 */     for (int i = 0; i < this.nd.length; i++) {
/* 1270 */       Point localPoint = getPos(i);
/* 1271 */       System.out.print(getLabel(i) + "\t(" + localPoint.x + "," + localPoint.y + ") nd[" + i + "].nls[]=");
/*      */       
/* 1273 */       this.nd[i].showUNode();
/* 1274 */       System.out.println();
/*      */     }
/*      */   }
/*      */ }


/* Location:              /home/anali/INAOE/tesis/codigos/topub/exe/ww4.jar!/Network/UndirectGraph.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */