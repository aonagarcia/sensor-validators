/*      */ package Network;
/*      */ 
/*      */ import java.awt.Point;
/*      */ import java.io.BufferedReader;
/*      */ import java.util.Random;
/*      */ 
/*      */ public class DirectGraph
/*      */ {
/*      */   public DNode[] nd;
/*      */   
/*      */   public static void main(String[] paramArrayOfString)
/*      */   {
/*   13 */     DirectGraph localDirectGraph = new DirectGraph(3, 2);
/*   14 */     localDirectGraph.showDirectGraph();
/*      */     
/*   16 */     BayesNet localBayesNet = new BayesNet(localDirectGraph);
/*   17 */     localBayesNet.save("jk.bn");
/*      */   }
/*      */   
/*      */ 
/*   21 */   public DirectGraph() { this.nd = null; }
/*      */   
/*      */   public DirectGraph(DirectGraph paramDirectGraph) {
/*   24 */     this();
/*   25 */     if ((paramDirectGraph != null) && (paramDirectGraph.nd != null)) setDirectGraph(paramDirectGraph);
/*      */   }
/*      */   
/*   28 */   public DirectGraph(MixNet paramMixNet) { this();
/*   29 */     if ((paramMixNet != null) && (paramMixNet.nd != null)) { setDirectGraph(paramMixNet);
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
/*      */   public DirectGraph(int paramInt1, int paramInt2)
/*      */   {
/*   45 */     int i = ((int)Math.pow(paramInt1, paramInt2 + 1) - 1) / (paramInt1 - 1);
/*   46 */     setDumbNetPlus(i);
/*   47 */     int j = 0;int k = 0;int m = 0;int n = 1;int i1 = 20;int i2 = 10;
/*   48 */     setPos(0, new Point(i2, i2 + i1 * (paramInt2 + 1)));
/*      */     
/*   50 */     while (j < paramInt2) {
/*   51 */       i3 = m + 1;
/*   52 */       for (int i4 = k; i4 <= m; i4++) {
/*   53 */         for (int i5 = 0; i5 < paramInt1; i5++) {
/*   54 */           this.nd[i4].addParent(i3);
/*   55 */           this.nd[i3].addChild(i4);
/*      */           
/*   57 */           int i6 = i2 + (i3 - m - 1) * i1;
/*   58 */           int i7 = i2 + (paramInt2 - j) * i1;
/*   59 */           setPos(i3++, new Point(i6, i7));
/*      */         }
/*      */       }
/*   62 */       k = m + 1;
/*   63 */       m = i3 - 1;
/*   64 */       j++;
/*      */     }
/*      */     
/*   67 */     for (int i3 = 0; i3 < i; i3++) { setLabel(i3, "v" + i3);
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
/*      */   public DirectGraph(int paramInt1, int paramInt2, int paramInt3)
/*      */   {
/*   85 */     if ((paramInt1 < 2) || (paramInt3 >= paramInt1)) {
/*   86 */       this.nd = null; return;
/*      */     }
/*      */     
/*      */     int i;
/*      */     
/*   91 */     if (paramInt3 >= paramInt2) i = paramInt2 * (paramInt1 - paramInt3); else {
/*   92 */       i = paramInt2 * (paramInt1 - paramInt2) + (paramInt2 * (paramInt2 - 1) - paramInt3 * (paramInt3 - 1)) / 2;
/*      */     }
/*   94 */     if (i < paramInt1 - 1) {
/*   95 */       this.nd = null;return;
/*      */     }
/*      */     
/*   98 */     this.nd = new DNode[paramInt1];
/*   99 */     for (int j = 0; j < paramInt1; j++) { this.nd[j] = new DNode();
/*      */     }
/*  101 */     Random localRandom = new Random();
/*  102 */     int k = MATH.getRandomInt(localRandom, paramInt1 - 1, i);
/*      */     
/*      */ 
/*  105 */     int[] arrayOfInt1 = null;
/*  106 */     int[] arrayOfInt2 = new int[paramInt1];
/*      */     
/*  108 */     for (int m = paramInt3; m < paramInt1; m++) {
/*  109 */       arrayOfInt2[m] = 1;
/*  110 */       if (arrayOfInt2[m] < paramInt2) { arrayOfInt1 = MATH.addMember(m, arrayOfInt1);
/*      */       }
/*      */     }
/*  113 */     for (m = paramInt1 - paramInt3; m < k; m++) {
/*  114 */       n = arrayOfInt1[MATH.getRandomInt(localRandom, 0, arrayOfInt1.length - 1)];
/*  115 */       arrayOfInt2[n] += 1;
/*  116 */       if ((arrayOfInt2[n] == paramInt2) || (arrayOfInt2[n] == n)) {
/*  117 */         arrayOfInt1 = MATH.delMember(n, arrayOfInt1);
/*      */       }
/*      */     }
/*      */     
/*  121 */     for (m = paramInt3; m < paramInt1; m++) {
/*  122 */       n = MATH.getRandomInt(localRandom, paramInt3 - 1, m - 1);
/*  123 */       this.nd[m].addParent(n);
/*  124 */       this.nd[n].addChild(m);
/*  125 */       arrayOfInt2[m] -= 1;
/*      */     }
/*      */     
/*  128 */     int[] arrayOfInt3 = null;
/*  129 */     for (int n = paramInt3; n < paramInt1; n++) {
/*  130 */       if (arrayOfInt2[n] > 0) arrayOfInt3 = MATH.addMember(n, arrayOfInt3);
/*      */     }
/*  132 */     for (n = 0; n < paramInt3 - 1; n++) {
/*  133 */       int i1 = arrayOfInt3[MATH.getRandomInt(localRandom, 0, arrayOfInt3.length - 1)];
/*  134 */       this.nd[n].addChild(i1);
/*  135 */       this.nd[i1].addParent(n);
/*  136 */       if (arrayOfInt2[i1] -= 1 == 0) { arrayOfInt3 = MATH.delMember(i1, arrayOfInt3);
/*      */       }
/*      */     }
/*  139 */     for (n = paramInt3; n < paramInt1; n++) {
/*  140 */       int[] arrayOfInt4 = null;
/*  141 */       for (int i2 = 0; i2 < n; i2++)
/*  142 */         if (!this.nd[n].isParent(i2)) arrayOfInt4 = MATH.addMember(i2, arrayOfInt4);
/*  143 */       while (arrayOfInt2[n] > 0) {
/*  144 */         i2 = arrayOfInt4[MATH.getRandomInt(localRandom, 0, arrayOfInt4.length - 1)];
/*  145 */         this.nd[n].addParent(i2);
/*  146 */         this.nd[i2].addChild(n);
/*  147 */         arrayOfInt2[n] -= 1;
/*  148 */         arrayOfInt4 = MATH.delMember(i2, arrayOfInt4);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setTreePlus(int paramInt1, int paramInt2, int paramInt3)
/*      */   {
/*  171 */     setDumbNetPlus(paramInt1);
/*  172 */     Random localRandom = new Random();
/*      */     int j;
/*  174 */     int k; int m; int n; for (int i = 0; i < paramInt1 - 1; i++) {
/*  175 */       j = 0;
/*  176 */       while (j == 0) {
/*  177 */         k = MATH.getRandomInt(localRandom, 0, paramInt1 - 1);
/*  178 */         m = MATH.getRandomInt(localRandom, paramInt1 * 3 / 4, paramInt1 - 1);
/*  179 */         if ((k != m) && 
/*  180 */           (!isConnected(k, m))) {
/*  181 */           if (k > m) {
/*  182 */             n = k;k = m;m = n;
/*      */           }
/*  184 */           if (getParentCount(m) != paramInt2) {
/*  185 */             addArc(k, m);
/*  186 */             j = 1;
/*      */           }
/*      */         }
/*      */       } }
/*  190 */     for (i = 0; i < paramInt1; i++) setLabel(i, "v" + i);
/*  191 */     setTreePos();
/*      */     
/*  193 */     for (i = 0; i < paramInt3; i++) {
/*  194 */       j = 0;
/*  195 */       while (j == 0) {
/*  196 */         k = MATH.getRandomInt(localRandom, 0, paramInt1 - 1);
/*  197 */         m = MATH.getRandomInt(localRandom, 0, paramInt1 - 1);
/*  198 */         if ((k != m) && 
/*  199 */           (!isArc(k, m)) && (!isArc(m, k))) {
/*  200 */           if (k > m) {
/*  201 */             n = k;k = m;m = n;
/*      */           }
/*  203 */           if (getParentCount(m) != paramInt2) {
/*  204 */             addArc(k, m);
/*  205 */             j = 1;
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*  212 */   public void reinit() { this.nd = null; }
/*      */   
/*      */ 
/*      */   public void setDumbNet(int paramInt)
/*      */   {
/*  217 */     this.nd = new DNode[paramInt];
/*      */   }
/*      */   
/*      */   public void setDumbNetPlus(int paramInt)
/*      */   {
/*  222 */     setDumbNet(paramInt);
/*  223 */     for (int i = 0; i < paramInt; i++) this.nd[i] = new DNode();
/*      */   }
/*      */   
/*      */   void setDirectGraph(DirectGraph paramDirectGraph) {
/*  227 */     int i = paramDirectGraph.getNodeCount();
/*  228 */     setDumbNetPlus(i);
/*  229 */     for (int j = 0; j < i; j++) this.nd[j] = new DNode(paramDirectGraph.getDNode(j));
/*      */   }
/*      */   
/*      */   void setDirectGraph(MixNet paramMixNet) {
/*  233 */     int i = paramMixNet.getNodeCount();
/*  234 */     setDumbNetPlus(i);
/*  235 */     for (int j = 0; j < i; j++) { this.nd[j] = new DNode(paramMixNet.getXNode(j));
/*      */     }
/*      */   }
/*      */   
/*      */   DNode getDNode(int paramInt)
/*      */   {
/*  241 */     return new DNode(this.nd[paramInt]);
/*      */   }
/*      */   
/*      */   public int getNodeCount()
/*      */   {
/*  246 */     if (this.nd == null) return 0;
/*  247 */     return this.nd.length;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public void setLabel(int paramInt, String paramString)
/*      */   {
/*  254 */     this.nd[paramInt].setLabel(paramString);
/*      */   }
/*      */   
/*      */   public String getLabel(int paramInt)
/*      */   {
/*  259 */     return new String(this.nd[paramInt].getLabel());
/*      */   }
/*      */   
/*      */   String[] getLabel(int[] paramArrayOfInt) {
/*  263 */     if (paramArrayOfInt == null) return null;
/*  264 */     int i = paramArrayOfInt.length;
/*  265 */     String[] arrayOfString = new String[i];
/*  266 */     for (int j = 0; j < i; j++) arrayOfString[j] = getLabel(paramArrayOfInt[j]);
/*  267 */     return arrayOfString;
/*      */   }
/*      */   
/*      */   public String[] getLabel() {
/*  271 */     int i = getNodeCount();
/*  272 */     String[] arrayOfString = new String[i];
/*  273 */     for (int j = 0; j < i; j++) arrayOfString[j] = getLabel(j);
/*  274 */     return arrayOfString;
/*      */   }
/*      */   
/*      */ 
/*      */   public int getIndex(String paramString)
/*      */   {
/*  280 */     for (int i = 0; i < this.nd.length; i++) {
/*  281 */       String str = this.nd[i].getLabel();
/*  282 */       if (str.equals(paramString)) return i;
/*      */     }
/*  284 */     return -1;
/*      */   }
/*      */   
/*      */ 
/*      */   int[] getIndex(String[] paramArrayOfString)
/*      */   {
/*  290 */     if (paramArrayOfString == null) return null;
/*  291 */     int[] arrayOfInt = new int[paramArrayOfString.length];
/*  292 */     for (int i = 0; i < paramArrayOfString.length; i++) arrayOfInt[i] = getIndex(paramArrayOfString[i]);
/*  293 */     return arrayOfInt;
/*      */   }
/*      */   
/*      */ 
/*      */   int getNodeByLabelFrwd(String paramString)
/*      */   {
/*  299 */     int i = getIndex(paramString);
/*  300 */     if (i == -1) HelpPanel.showError("Invalid var label!");
/*  301 */     return i;
/*      */   }
/*      */   
/*      */   int getNodeByLabelBkwd(String paramString)
/*      */   {
/*  306 */     for (int i = this.nd.length - 1; i >= 0; i--)
/*  307 */       if (this.nd[i].getLabel().equals(paramString)) return i;
/*  308 */     HelpPanel.showError("Invalid var label!");
/*  309 */     return -1;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public Point getPos(int paramInt)
/*      */   {
/*  316 */     return this.nd[paramInt].getPos();
/*      */   }
/*      */   
/*      */   public void setPos(int paramInt, Point paramPoint)
/*      */   {
/*  321 */     this.nd[paramInt].setPos(paramPoint);
/*      */   }
/*      */   
/*  324 */   public void setPos(int paramInt1, int paramInt2, int paramInt3) { this.nd[paramInt1].setPos(paramInt2, paramInt3); }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setPos(int paramInt1, int paramInt2)
/*      */   {
/*  335 */     int i = this.nd.length;
/*  336 */     int[] arrayOfInt1 = new int[i];
/*  337 */     for (int j = 0; j < i; j++) { arrayOfInt1[j] = getParentCount(j);
/*      */     }
/*  339 */     int[][] arrayOfInt = (int[][])null;
/*  340 */     int[] arrayOfInt2 = null;
/*  341 */     int i1; while (i > 0) {
/*  342 */       int[] arrayOfInt3 = null;
/*  343 */       for (m = 0; m < this.nd.length; m++) {
/*  344 */         if ((!MATH.member(m, arrayOfInt2)) && (arrayOfInt1[m] == 0)) {
/*  345 */           arrayOfInt3 = MATH.addMember(m, arrayOfInt3);
/*  346 */           i--;
/*      */         }
/*      */       }
/*  349 */       for (m = 0; m < arrayOfInt3.length; m++) {
/*  350 */         int[] arrayOfInt4 = getChild(arrayOfInt3[m]);
/*  351 */         if (arrayOfInt4 != null) {
/*  352 */           for (i1 = 0; i1 < arrayOfInt4.length; i1++)
/*  353 */             arrayOfInt1[arrayOfInt4[i1]] -= 1;
/*      */         }
/*      */       }
/*  356 */       arrayOfInt = MATH.appendMember(arrayOfInt3, arrayOfInt);
/*  357 */       arrayOfInt2 = MATH.union(arrayOfInt2, arrayOfInt3);
/*      */     }
/*      */     
/*  360 */     int k = (int)(paramInt2 / (arrayOfInt.length + 1));
/*  361 */     for (int m = 0; m < arrayOfInt.length; m++) {
/*  362 */       int n = (int)(paramInt1 / (arrayOfInt[m].length + 1));
/*  363 */       for (i1 = 0; i1 < arrayOfInt[m].length; i1++) {
/*  364 */         this.nd[arrayOfInt[m][i1]].setPos((i1 + 1) * n, (m + 1) * k);
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
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setTreePos()
/*      */   {
/*  382 */     UndirectGraph localUndirectGraph = UndirectGraph.makeSkeleton(this);
/*  383 */     localUndirectGraph.setTreePos();
/*  384 */     for (int i = 0; i < getNodeCount(); i++) { setPos(i, localUndirectGraph.getPos(i));
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public int isClose(Point paramPoint, int paramInt)
/*      */   {
/*  392 */     int i = this.nd == null ? 0 : this.nd.length;
/*  393 */     for (int j = 0; j < i; j++)
/*  394 */       if (this.nd[j].isClose(paramPoint, paramInt) == 0) return j;
/*  395 */     for (j = 0; j < i; j++)
/*  396 */       if (this.nd[j].isClose(paramPoint, paramInt) == -1) return -1;
/*  397 */     return -2;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public void setParent(int paramInt, int[] paramArrayOfInt)
/*      */   {
/*  404 */     this.nd[paramInt].setParent(paramArrayOfInt);
/*      */   }
/*      */   
/*      */   public int[] getParent(int paramInt)
/*      */   {
/*  409 */     return this.nd[paramInt].getParent();
/*      */   }
/*      */   
/*      */   public int getParent(int paramInt1, int paramInt2) {
/*  413 */     return this.nd[paramInt1].getParent(paramInt2);
/*      */   }
/*      */   
/*      */   public int getParentCount(int paramInt) {
/*  417 */     return this.nd[paramInt].getParentCount();
/*      */   }
/*      */   
/*      */   public String[] getParentLabel(int paramInt)
/*      */   {
/*  422 */     int[] arrayOfInt = this.nd[paramInt].getParent();
/*  423 */     if (arrayOfInt == null) return null;
/*  424 */     String[] arrayOfString = new String[arrayOfInt.length];
/*  425 */     for (int i = 0; i < arrayOfInt.length; i++) arrayOfString[i] = this.nd[arrayOfInt[i]].getLabel();
/*  426 */     return arrayOfString;
/*      */   }
/*      */   
/*      */   String[] getInParentLabel(String paramString, String[] paramArrayOfString)
/*      */   {
/*  431 */     int i = getIndex(paramString);
/*  432 */     String[] arrayOfString = getParentLabel(i);
/*  433 */     return MATH.getIntersection(arrayOfString, paramArrayOfString);
/*      */   }
/*      */   
/*      */   String[][] getInParentLabel(String[] paramArrayOfString) {
/*  437 */     String[][] arrayOfString = new String[paramArrayOfString.length][];
/*  438 */     for (int i = 0; i < paramArrayOfString.length; i++) {
/*  439 */       arrayOfString[i] = getInParentLabel(paramArrayOfString[i], paramArrayOfString);
/*      */     }
/*      */     
/*  442 */     return arrayOfString;
/*      */   }
/*      */   
/*      */   boolean hasOutParent(String paramString, String[] paramArrayOfString)
/*      */   {
/*  447 */     int i = getIndex(paramString);
/*      */     
/*  449 */     return !MATH.isSubset(getParentLabel(i), paramArrayOfString);
/*      */   }
/*      */   
/*      */ 
/*      */   boolean[] hasOutParent(String[] paramArrayOfString)
/*      */   {
/*  455 */     boolean[] arrayOfBoolean = new boolean[paramArrayOfString.length];
/*  456 */     for (int i = 0; i < paramArrayOfString.length; i++) arrayOfBoolean[i] = hasOutParent(paramArrayOfString[i], paramArrayOfString);
/*  457 */     return arrayOfBoolean;
/*      */   }
/*      */   
/*      */   public boolean isRoot(int paramInt)
/*      */   {
/*  462 */     return getParentCount(paramInt) == 0;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public void setChild(int paramInt, int[] paramArrayOfInt)
/*      */   {
/*  469 */     this.nd[paramInt].setChild(paramArrayOfInt);
/*      */   }
/*      */   
/*      */   public boolean hasChild(int paramInt)
/*      */   {
/*  474 */     return this.nd[paramInt].hasChild();
/*      */   }
/*      */   
/*      */   public int getChildCount(int paramInt) {
/*  478 */     return this.nd[paramInt].getChildCount();
/*      */   }
/*      */   
/*      */   public int[] getChild(int paramInt)
/*      */   {
/*  483 */     return this.nd[paramInt].getChild();
/*      */   }
/*      */   
/*      */   public int getChild(int paramInt1, int paramInt2) {
/*  487 */     return this.nd[paramInt1].getChild()[paramInt2];
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public boolean isArc(int paramInt1, int paramInt2)
/*      */   {
/*  494 */     if (this.nd[paramInt2].isParent(paramInt1)) return true;
/*  495 */     return false;
/*      */   }
/*      */   
/*      */ 
/*      */   public int[] getFamily(int paramInt)
/*      */   {
/*  501 */     return UTIL.appendToArray(this.nd[paramInt].getParent(), paramInt);
/*      */   }
/*      */   
/*      */   public boolean isConnected()
/*      */   {
/*  506 */     UndirectGraph localUndirectGraph = UndirectGraph.makeSkeleton(this);
/*  507 */     return localUndirectGraph.isConnected();
/*      */   }
/*      */   
/*      */   public boolean isConnected(int paramInt1, int paramInt2) {
/*  511 */     UndirectGraph localUndirectGraph = UndirectGraph.makeSkeleton(this);
/*  512 */     return localUndirectGraph.isConnected(paramInt1, paramInt2);
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
/*      */   public int[][] getConnected(int[] paramArrayOfInt1, int[] paramArrayOfInt2)
/*      */   {
/*  527 */     UndirectGraph localUndirectGraph = UndirectGraph.makeSkeleton(this);
/*  528 */     return localUndirectGraph.getConnected(paramArrayOfInt1, paramArrayOfInt2);
/*      */   }
/*      */   
/*      */ 
/*      */   public void addArc(int paramInt1, int paramInt2)
/*      */   {
/*  534 */     this.nd[paramInt2].addParent(paramInt1);
/*  535 */     this.nd[paramInt1].addChild(paramInt2);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   boolean hasUnmarkedParent(int paramInt)
/*      */   {
/*  543 */     int[] arrayOfInt = getParent(paramInt);
/*  544 */     if (arrayOfInt == null) return false;
/*  545 */     for (int i = 0; i < arrayOfInt.length; i++) {
/*  546 */       int j = arrayOfInt[i];
/*  547 */       if (!this.nd[j].isMarked()) return true;
/*      */     }
/*  549 */     return false;
/*      */   }
/*      */   
/*      */ 
/*      */   boolean hasUnmarkedChild(int paramInt)
/*      */   {
/*  555 */     int[] arrayOfInt = getChild(paramInt);
/*  556 */     if (arrayOfInt == null) return false;
/*  557 */     for (int i = 0; i < arrayOfInt.length; i++) {
/*  558 */       int j = arrayOfInt[i];
/*  559 */       if (!this.nd[j].isMarked()) return true;
/*      */     }
/*  561 */     return false;
/*      */   }
/*      */   
/*      */   public void unmarkNode()
/*      */   {
/*  566 */     for (int i = 0; i < this.nd.length; i++) { this.nd[i].setMark(false);
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
/*      */   public boolean isDag()
/*      */   {
/*  583 */     int i = getNodeCount();
/*  584 */     int j = 0;
/*  585 */     while (j == 0) {
/*  586 */       j = 1;
/*  587 */       for (k = 0; k < i; k++) {
/*  588 */         if ((!this.nd[k].isMarked()) && (
/*  589 */           (!hasUnmarkedParent(k)) || (!hasUnmarkedChild(k)))) {
/*  590 */           this.nd[k].setMark();
/*  591 */           j = 0;
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*  597 */     int k = 0;
/*  598 */     while ((k < i) && (this.nd[k].isMarked())) k++;
/*  599 */     unmarkNode();
/*  600 */     if (k == i) return true;
/*  601 */     return false;
/*      */   }
/*      */   
/*      */ 
/*      */   int[] markRootLeaf(int[] paramArrayOfInt)
/*      */   {
/*  607 */     int[] arrayOfInt = null;
/*  608 */     int i = 0;
/*  609 */     while (i == 0) {
/*  610 */       i = 1;
/*  611 */       for (int j = 0; j < getNodeCount(); j++) {
/*  612 */         if ((!this.nd[j].isMarked()) && (!MATH.member(j, paramArrayOfInt)))
/*      */         {
/*  614 */           if ((!hasUnmarkedParent(j)) || (!hasUnmarkedChild(j))) {
/*  615 */             this.nd[j].setMark();
/*  616 */             arrayOfInt = MATH.appendMember(j, arrayOfInt);
/*  617 */             i = 0;
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*  622 */     return arrayOfInt;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public int[] getTopOrder()
/*      */   {
/*  630 */     int[] arrayOfInt = null;
/*  631 */     int i = 0;
/*  632 */     while (i == 0) {
/*  633 */       i = 1;
/*  634 */       for (int j = 0; j < getNodeCount(); j++) {
/*  635 */         if ((!this.nd[j].isMarked()) && (!hasUnmarkedParent(j)))
/*      */         {
/*  637 */           this.nd[j].setMark();
/*  638 */           arrayOfInt = MATH.appendMember(j, arrayOfInt);
/*  639 */           i = 0;
/*      */         }
/*      */       }
/*      */     }
/*  643 */     return arrayOfInt;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setMark(int paramInt)
/*      */   {
/*  651 */     this.nd[paramInt].setMark();
/*      */   }
/*      */   
/*      */   public void setMark(boolean paramBoolean)
/*      */   {
/*  656 */     for (int i = 0; i < getNodeCount(); i++) this.nd[i].setMark(paramBoolean);
/*      */   }
/*      */   
/*      */   public void setMark(boolean paramBoolean, int paramInt)
/*      */   {
/*  661 */     this.nd[paramInt].setMark(paramBoolean);
/*      */   }
/*      */   
/*      */ 
/*      */   public boolean isMarked(int paramInt)
/*      */   {
/*  667 */     return this.nd[paramInt].isMarked();
/*      */   }
/*      */   
/*      */   boolean isMarked() {
/*  671 */     for (int i = 0; i < getNodeCount(); i++) {
/*  672 */       if (!this.nd[i].isMarked()) return false;
/*      */     }
/*  674 */     return true;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setObserved(int paramInt)
/*      */   {
/*  683 */     this.nd[paramInt].setMark();
/*      */   }
/*      */   
/*  686 */   void setMarked(int paramInt) { this.nd[paramInt].setMark(); }
/*      */   
/*      */   public void setObserved(int paramInt, boolean paramBoolean)
/*      */   {
/*  690 */     this.nd[paramInt].setMark(paramBoolean);
/*      */   }
/*      */   
/*      */   void setObserved(String[] paramArrayOfString) {
/*  694 */     if (paramArrayOfString == null) return;
/*  695 */     for (int i = 0; i < paramArrayOfString.length; i++) this.nd[getIndex(paramArrayOfString[i])].setMark();
/*      */   }
/*      */   
/*      */   public boolean isObserved(int paramInt)
/*      */   {
/*  700 */     return this.nd[paramInt].isMarked();
/*      */   }
/*      */   
/*      */ 
/*      */   int[] getObserved(int[] paramArrayOfInt)
/*      */   {
/*  706 */     if (paramArrayOfInt == null) return null;
/*  707 */     int[] arrayOfInt = null;
/*  708 */     for (int i = 0; i < paramArrayOfInt.length; i++)
/*  709 */       if (isObserved(paramArrayOfInt[i])) arrayOfInt = MATH.appendMember(paramArrayOfInt[i], arrayOfInt);
/*  710 */     return arrayOfInt;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setStamp(int paramInt)
/*      */   {
/*  719 */     this.nd[paramInt].setStamp();
/*      */   }
/*      */   
/*      */   public void setStamp(boolean paramBoolean)
/*      */   {
/*  724 */     for (int i = 0; i < getNodeCount(); i++) this.nd[i].setStamp(paramBoolean);
/*      */   }
/*      */   
/*      */   public void setStamp(boolean paramBoolean, int paramInt)
/*      */   {
/*  729 */     this.nd[paramInt].setStamp(paramBoolean);
/*      */   }
/*      */   
/*      */ 
/*      */   public boolean isStamped(int paramInt)
/*      */   {
/*  735 */     return this.nd[paramInt].isStamped();
/*      */   }
/*      */   
/*      */ 
/*      */   public int[] getStamped()
/*      */   {
/*  741 */     int i = getNodeCount();
/*  742 */     int[] arrayOfInt1 = new int[i];
/*  743 */     int j = 0;
/*  744 */     for (int k = 0; k < i; k++)
/*  745 */       if (this.nd[k].isStamped()) arrayOfInt1[(j++)] = k;
/*  746 */     int[] arrayOfInt2 = new int[j];
/*  747 */     for (int m = 0; m < j; m++) arrayOfInt2[m] = arrayOfInt1[m];
/*  748 */     return arrayOfInt2;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setCode(int paramInt)
/*      */   {
/*  757 */     for (int i = 0; i < getNodeCount(); i++) this.nd[i].setPeer(paramInt);
/*      */   }
/*      */   
/*      */   public void setCode(int paramInt1, int paramInt2)
/*      */   {
/*  762 */     this.nd[paramInt2].setPeer(paramInt1);
/*      */   }
/*      */   
/*      */ 
/*      */   public int getCode(int paramInt)
/*      */   {
/*  768 */     return this.nd[paramInt].getPeer();
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
/*      */   public boolean hasObsDes(int paramInt)
/*      */   {
/*  791 */     if (!hasChild(paramInt)) return false;
/*  792 */     int[] arrayOfInt = getChild(paramInt);
/*  793 */     for (int i = 0; i < arrayOfInt.length; i++) {
/*  794 */       if (isMarked(arrayOfInt[i])) return true;
/*  795 */       if (hasObsDes(arrayOfInt[i])) return true;
/*      */     }
/*  797 */     return false;
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
/*      */   public int[][] getOGBD()
/*      */   {
/*  820 */     int[] arrayOfInt1 = getStamped();
/*  821 */     if (arrayOfInt1 == null) { return (int[][])null;
/*      */     }
/*  823 */     int[] arrayOfInt2 = null;int[] arrayOfInt3 = null;
/*  824 */     boolean[] arrayOfBoolean = new boolean[getNodeCount()];
/*  825 */     while (arrayOfInt1 != null) {
/*  826 */       int i = arrayOfInt1[0];
/*  827 */       arrayOfInt1 = MATH.delMember(i, arrayOfInt1);
/*  828 */       arrayOfBoolean[i] = true;
/*  829 */       if (!isStamped(i)) { arrayOfInt3 = MATH.addMember(i, arrayOfInt3);
/*      */       }
/*  831 */       int[] arrayOfInt4 = getParent(i);
/*  832 */       if (arrayOfInt4 != null) {
/*  833 */         for (int j = 0; j < arrayOfInt4.length; j++) {
/*  834 */           if (arrayOfBoolean[arrayOfInt4[j]] == 0)
/*  835 */             if (isMarked(arrayOfInt4[j])) {
/*  836 */               arrayOfInt2 = MATH.addMember(arrayOfInt4[j], arrayOfInt2);
/*  837 */               arrayOfBoolean[arrayOfInt4[j]] = true;
/*      */             } else {
/*  839 */               arrayOfInt1 = MATH.addMember(arrayOfInt4[j], arrayOfInt1);
/*      */             }
/*      */         }
/*      */       }
/*  843 */       int[] arrayOfInt5 = getChild(i);
/*  844 */       if (arrayOfInt5 != null) {
/*  845 */         for (int k = 0; k < arrayOfInt5.length; k++) {
/*  846 */           if (arrayOfBoolean[arrayOfInt5[k]] == 0)
/*  847 */             if (isMarked(arrayOfInt5[k])) {
/*  848 */               arrayOfInt2 = MATH.addMember(arrayOfInt5[k], arrayOfInt2);
/*  849 */               arrayOfBoolean[arrayOfInt5[k]] = true;
/*      */               
/*  851 */               int[] arrayOfInt6 = getParent(arrayOfInt5[k]);
/*  852 */               int m = arrayOfInt6.length;
/*  853 */               for (int n = 0; n < m; n++) {
/*  854 */                 if (arrayOfBoolean[arrayOfInt6[n]] == 0) {
/*  855 */                   arrayOfInt1 = MATH.addMember(arrayOfInt6[n], arrayOfInt1);
/*      */                 }
/*      */               }
/*  858 */             } else if (hasObsDes(arrayOfInt5[k])) {
/*  859 */               arrayOfInt1 = MATH.addMember(arrayOfInt5[k], arrayOfInt1);
/*      */             } else {
/*  861 */               arrayOfBoolean[arrayOfInt5[k]] = true;
/*      */             }
/*      */         }
/*      */       }
/*      */     }
/*  866 */     int[][] arrayOfInt = new int[2][];
/*  867 */     arrayOfInt[0] = arrayOfInt2;arrayOfInt[1] = arrayOfInt3;
/*  868 */     return arrayOfInt;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void modifyNodeIndex(int paramInt1, int paramInt2, int paramInt3)
/*      */   {
/*  879 */     for (int i = 0; i < this.nd.length; i++) { this.nd[i].modifyNodeIndex(paramInt1, paramInt2, paramInt3);
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
/*      */   public static DirectGraph delNode(DirectGraph paramDirectGraph, int paramInt)
/*      */   {
/*  895 */     int i = paramDirectGraph.nd[paramInt].pls == null ? 0 : paramDirectGraph.nd[paramInt].pls.length;
/*  896 */     for (int j = 0; j < i; j++) {
/*  897 */       k = paramDirectGraph.nd[paramInt].pls[j];
/*  898 */       paramDirectGraph.nd[k].delChild(paramInt);
/*      */     }
/*      */     
/*  901 */     i = paramDirectGraph.nd[paramInt].cls == null ? 0 : paramDirectGraph.nd[paramInt].cls.length;
/*  902 */     for (j = 0; j < i; j++) {
/*  903 */       k = paramDirectGraph.nd[paramInt].cls[j];
/*  904 */       paramDirectGraph.nd[k].delParent(paramInt);
/*      */     }
/*      */     
/*  907 */     DirectGraph localDirectGraph = new DirectGraph();
/*  908 */     int k = paramDirectGraph.getNodeCount();
/*  909 */     localDirectGraph.setDumbNet(k - 1);
/*      */     
/*  911 */     for (int m = 0; m < k - 1; m++) {
/*  912 */       if (m < paramInt) localDirectGraph.nd[m] = paramDirectGraph.nd[m]; else
/*  913 */         localDirectGraph.nd[m] = paramDirectGraph.nd[(m + 1)];
/*      */     }
/*  915 */     localDirectGraph.modifyNodeIndex(paramInt + 1, k - 1, -1);
/*  916 */     return localDirectGraph;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public DirectGraph getSubdag(DirectGraph paramDirectGraph, int[] paramArrayOfInt)
/*      */   {
/*  925 */     int i = getNodeCount();
/*  926 */     DirectGraph localDirectGraph = new DirectGraph(paramDirectGraph);
/*  927 */     for (int j = 0; j < i; j++) { localDirectGraph.setLabel(j, "v" + j);
/*      */     }
/*  929 */     j = paramArrayOfInt.length;
/*      */     
/*      */ 
/*  932 */     for (int k = 0; k < i; k++) {
/*  933 */       int m = i - 1 - k;
/*  934 */       if (!MATH.member(m, paramArrayOfInt))
/*      */       {
/*  936 */         localDirectGraph = delNode(localDirectGraph, m); }
/*      */     }
/*  938 */     return localDirectGraph;
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
/*      */   public DirectGraph getAncestralGraph(String[] paramArrayOfString)
/*      */   {
/*  958 */     DirectGraph localDirectGraph = this;
/*  959 */     int i = 0;
/*  960 */     while (i == 0) {
/*  961 */       i = 1;
/*  962 */       int j = localDirectGraph.getNodeCount();
/*  963 */       for (int k = 0; k < j; k++)
/*  964 */         if (!localDirectGraph.hasChild(k)) {
/*  965 */           String str = localDirectGraph.getLabel(k);
/*  966 */           if (!MATH.member(str, paramArrayOfString)) {
/*  967 */             localDirectGraph = delNode(localDirectGraph, k);
/*  968 */             i = 0;
/*  969 */             break;
/*      */           }
/*      */         } }
/*  972 */     return localDirectGraph;
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
/*      */   static DirectGraph getDirected(UndirectGraph paramUndirectGraph, int paramInt)
/*      */   {
/*  988 */     int i = paramUndirectGraph.getNodeCount();
/*  989 */     DirectGraph localDirectGraph = new DirectGraph();
/*  990 */     localDirectGraph.setDumbNetPlus(i);
/*      */     
/*  992 */     Object localObject1 = { paramInt };Object localObject2 = null;int[] arrayOfInt1 = null;
/*  993 */     int j = 0;
/*  994 */     for (; j == 0; 
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
/* 1007 */         arrayOfInt1 = null)
/*      */     {
/*  995 */       j = 1;
/*  996 */       for (int k = 0; k < localObject1.length; k++) {
/*  997 */         int m = localObject1[k];
/*  998 */         int[] arrayOfInt2 = paramUndirectGraph.getNeighbor(m);
/*  999 */         int[] arrayOfInt3 = MATH.setDifference(arrayOfInt2, (int[])localObject2);
/* 1000 */         if (arrayOfInt3 != null) {
/* 1001 */           j = 0;
/* 1002 */           for (int n = 0; n < arrayOfInt3.length; n++) {
/* 1003 */             localDirectGraph.addArc(m, arrayOfInt3[n]);
/* 1004 */             arrayOfInt1 = MATH.addMember(arrayOfInt3[n], arrayOfInt1);
/*      */           }
/*      */         } }
/* 1007 */       localObject2 = localObject1;localObject1 = arrayOfInt1;
/*      */     }
/* 1009 */     return localDirectGraph;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   int loadNodeCount(BufferedReader paramBufferedReader)
/*      */   {
/* 1016 */     return UTIL.loadInt(paramBufferedReader);
/*      */   }
/*      */   
/*      */   void saveLabel(java.io.PrintWriter paramPrintWriter, int paramInt)
/*      */   {
/* 1021 */     this.nd[paramInt].saveLabel(paramPrintWriter);
/*      */   }
/*      */   
/*      */ 
/*      */   public void showDirectGraph()
/*      */   {
/* 1027 */     for (int i = 0; i < this.nd.length; i++) {
/* 1028 */       System.out.print("nd " + i + " :");
/* 1029 */       this.nd[i].showDNode();
/*      */     }
/*      */   }
/*      */ }


/* Location:              /home/anali/INAOE/tesis/codigos/topub/exe/ww4.jar!/Network/DirectGraph.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */