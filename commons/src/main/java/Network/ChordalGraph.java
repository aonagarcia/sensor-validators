/*     */ package Network;
/*     */ 
/*     */ import java.awt.Point;
/*     */ import java.io.PrintStream;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ChordalGraph
/*     */   extends UndirectGraph
/*     */ {
/*  12 */   int[] eOrder = null;
/*  13 */   Point[] fillIn = null;
/*  14 */   Point[] mfillIn = null;
/*  15 */   int[][] cqCand = (int[][])null;
/*     */   
/*     */ 
/*  18 */   public ChordalGraph() { init(); }
/*     */   
/*     */   public ChordalGraph(ChordalGraph paramChordalGraph) {
/*  21 */     super(paramChordalGraph);
/*  22 */     if ((paramChordalGraph != null) && (paramChordalGraph.nd != null)) setChordalGraph(paramChordalGraph);
/*     */   }
/*     */   
/*  25 */   public ChordalGraph(UndirectGraph paramUndirectGraph) { super(paramUndirectGraph); }
/*     */   
/*     */ 
/*     */   public void init()
/*     */   {
/*  30 */     this.nd = null;
/*  31 */     reinit();
/*     */   }
/*     */   
/*     */   public void reinit() {
/*  35 */     this.eOrder = null;
/*  36 */     this.fillIn = null;
/*  37 */     this.cqCand = ((int[][])null);
/*     */   }
/*     */   
/*     */   void setChordalGraph(ChordalGraph paramChordalGraph) {
/*  41 */     this.eOrder = UTIL.getDuplicate(paramChordalGraph.getIntOrder());
/*  42 */     this.fillIn = UTIL.getDuplicate(paramChordalGraph.getIntFillIn());
/*  43 */     this.mfillIn = UTIL.getDuplicate(paramChordalGraph.getMfillIn());
/*  44 */     this.cqCand = UTIL.getDuplicate(paramChordalGraph.cqCand);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   static ChordalGraph makeSubGraph(ChordalGraph paramChordalGraph, int[] paramArrayOfInt)
/*     */   {
/*  54 */     return new ChordalGraph(UndirectGraph.makeSubGraph(paramChordalGraph, paramArrayOfInt));
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
/*     */   public void setChordalGraph()
/*     */   {
/*  84 */     int i = this.nd.length;
/*  85 */     int[][] arrayOfInt = new int[i][];
/*  86 */     for (int j = 0; j < i; j++) arrayOfInt[j] = this.nd[j].getNeighbor();
/*  87 */     Point[][] arrayOfPoint = new Point[i][];
/*  88 */     boolean[] arrayOfBoolean = new boolean[i];
/*  89 */     for (int k = 0; k < i; k++) arrayOfBoolean[k] = true;
/*  90 */     this.eOrder = new int[i];
/*  91 */     this.cqCand = new int[i][];
/*  92 */     for (k = 0; k < i; k++) { this.cqCand[k] = null;
/*     */     }
/*  94 */     k = i;
/*  95 */     while (k > 1) {
/*  96 */       m = 0;
/*  97 */       int n = -1;
/*  98 */       for (int i1 = 0; i1 < i; i1++) arrayOfPoint[i1] = null;
/*  99 */       for (i1 = 0; i1 < i; i1++) {
/* 100 */         if (arrayOfBoolean[i1] != 0) {
/* 101 */           arrayOfPoint[i1] = MATH.getFillIn(arrayOfInt, i1);
/* 102 */           if (arrayOfPoint[i1] == null) {
/* 103 */             n = i1;
/* 104 */             m = 1;
/* 105 */             break;
/*     */           }
/*     */         }
/*     */       }
/*     */       int i3;
/* 110 */       if (m == 0) {
/* 111 */         i1 = i;
/* 112 */         for (i2 = 0; i2 < i; i2++) {
/* 113 */           if ((arrayOfBoolean[i2] != 0) && (arrayOfPoint[i2].length < i1))
/*     */           {
/* 115 */             i1 = arrayOfPoint[i2].length;
/* 116 */             n = i2;
/*     */           }
/*     */         }
/*     */         
/* 120 */         for (i2 = 0; i2 < arrayOfPoint[n].length; i2++) {
/* 121 */           i3 = arrayOfPoint[n][i2].x;
/* 122 */           int i4 = arrayOfPoint[n][i2].y;
/* 123 */           arrayOfInt[i3] = MATH.addMember(i4, arrayOfInt[i3]);
/* 124 */           arrayOfInt[i4] = MATH.addMember(i3, arrayOfInt[i4]);
/*     */         }
/*     */       }
/*     */       
/* 128 */       i1 = 0;
/* 129 */       if (arrayOfInt[n] != null) i1 = arrayOfInt[n].length;
/* 130 */       this.cqCand[(i - k)] = new int[i1 + 1];
/* 131 */       for (int i2 = 0; i2 < i1; i2++)
/* 132 */         this.cqCand[(i - k)][i2] = arrayOfInt[n][i2];
/* 133 */       this.cqCand[(i - k)][i1] = n;
/*     */       
/* 135 */       arrayOfBoolean[n] = false;
/* 136 */       for (i2 = 0; i2 < i1; i2++) {
/* 137 */         i3 = arrayOfInt[n][i2];
/* 138 */         arrayOfInt[i3] = MATH.delMember(n, arrayOfInt[i3]);
/*     */       }
/*     */       
/* 141 */       this.eOrder[(i - k--)] = n;
/* 142 */       if (arrayOfPoint[n] != null) {
/* 143 */         if (this.fillIn == null) this.fillIn = arrayOfPoint[n]; else {
/* 144 */           this.fillIn = MATH.union(arrayOfPoint[n], this.fillIn);
/*     */         }
/*     */       }
/*     */     }
/* 148 */     this.cqCand[(i - 1)] = new int[1];
/* 149 */     for (int m = 0; m < i; m++) if (arrayOfBoolean[m] != 0) {
/* 150 */         this.eOrder[(i - 1)] = m;
/* 151 */         this.cqCand[(i - 1)][0] = m;
/*     */       }
/* 153 */     addLink(this.fillIn);
/* 154 */     setLinkb(this.fillIn);
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
/*     */   public void setChordalGraph(int[] paramArrayOfInt)
/*     */   {
/* 184 */     int i = this.nd.length;
/* 185 */     int[][] arrayOfInt = new int[i][];
/* 186 */     for (int j = 0; j < i; j++) arrayOfInt[j] = this.nd[j].getNeighbor();
/* 187 */     Point[][] arrayOfPoint = new Point[i][];
/* 188 */     boolean[] arrayOfBoolean = new boolean[i];
/* 189 */     for (int k = 0; k < i; k++) arrayOfBoolean[k] = true;
/* 190 */     this.eOrder = new int[i];
/* 191 */     this.cqCand = new int[i][];
/* 192 */     for (k = 0; k < i; k++) this.cqCand[k] = null;
/* 193 */     int[] arrayOfInt1 = new int[i];
/* 194 */     for (int m = 0; m < i; m++) { arrayOfInt1[m] = (MATH.member(m, paramArrayOfInt) ? 1 : 0);
/*     */     }
/* 196 */     m = i;
/* 197 */     while (m > 1) {
/* 198 */       n = 0;
/* 199 */       int i1 = -1;
/* 200 */       for (int i2 = 0; i2 < i; i2++) arrayOfPoint[i2] = null;
/* 201 */       for (i2 = 0; i2 < i; i2++)
/* 202 */         if ((arrayOfBoolean[i2] != 0) && (
/* 203 */           (m <= paramArrayOfInt.length) || (arrayOfInt1[i2] != 1)))
/*     */         {
/* 205 */           arrayOfPoint[i2] = MATH.getFillIn(arrayOfInt, i2);
/* 206 */           if (arrayOfPoint[i2] == null) {
/* 207 */             i1 = i2;
/* 208 */             n = 1;
/* 209 */             break;
/*     */           }
/*     */         }
/*     */       int i4;
/* 213 */       if (n == 0) {
/* 214 */         i2 = i * i;
/* 215 */         for (i3 = 0; i3 < i; i3++) {
/* 216 */           if ((arrayOfBoolean[i3] != 0) && (
/* 217 */             (m <= paramArrayOfInt.length) || (arrayOfInt1[i3] != 1)))
/*     */           {
/* 219 */             if (arrayOfPoint[i3].length < i2) {
/* 220 */               i2 = arrayOfPoint[i3].length;
/* 221 */               i1 = i3;
/*     */             }
/*     */           }
/*     */         }
/* 225 */         if (arrayOfPoint[i1] != null) {
/* 226 */           for (i3 = 0; i3 < arrayOfPoint[i1].length; i3++) {
/* 227 */             i4 = arrayOfPoint[i1][i3].x;
/* 228 */             int i5 = arrayOfPoint[i1][i3].y;
/* 229 */             arrayOfInt[i4] = MATH.addMember(i5, arrayOfInt[i4]);
/* 230 */             arrayOfInt[i5] = MATH.addMember(i4, arrayOfInt[i5]);
/*     */           }
/*     */         }
/*     */       }
/*     */       
/* 235 */       i2 = 0;
/* 236 */       if (arrayOfInt[i1] != null) i2 = arrayOfInt[i1].length;
/* 237 */       this.cqCand[(i - m)] = new int[i2 + 1];
/* 238 */       for (int i3 = 0; i3 < i2; i3++)
/* 239 */         this.cqCand[(i - m)][i3] = arrayOfInt[i1][i3];
/* 240 */       this.cqCand[(i - m)][i2] = i1;
/*     */       
/* 242 */       arrayOfBoolean[i1] = false;
/* 243 */       for (i3 = 0; i3 < i2; i3++) {
/* 244 */         i4 = arrayOfInt[i1][i3];
/* 245 */         arrayOfInt[i4] = MATH.delMember(i1, arrayOfInt[i4]);
/*     */       }
/*     */       
/* 248 */       this.eOrder[(i - m--)] = i1;
/* 249 */       if (arrayOfPoint[i1] != null) {
/* 250 */         if (this.fillIn == null) this.fillIn = arrayOfPoint[i1]; else
/* 251 */           this.fillIn = MATH.union(arrayOfPoint[i1], this.fillIn);
/*     */       }
/*     */     }
/* 254 */     this.cqCand[(i - 1)] = new int[1];
/* 255 */     for (int n = 0; n < i; n++) if (arrayOfBoolean[n] != 0) {
/* 256 */         this.eOrder[(i - 1)] = n;
/* 257 */         this.cqCand[(i - 1)][0] = n;
/*     */       }
/* 259 */     addLink(this.fillIn);
/* 260 */     setLinkb(this.fillIn);
/*     */   }
/*     */   
/*     */   public int[] getIntOrder()
/*     */   {
/* 265 */     return UTIL.getDuplicate(this.eOrder);
/*     */   }
/*     */   
/*     */   public String getStrOrder() {
/* 269 */     StringBuffer localStringBuffer = new StringBuffer();
/* 270 */     int i = this.eOrder.length;
/* 271 */     localStringBuffer.append("(");
/* 272 */     for (int j = 0; j < i - 1; j++) localStringBuffer.append(this.eOrder[j] + ",");
/* 273 */     localStringBuffer.append(this.eOrder[(i - 1)] + ")");
/*     */     
/* 275 */     return localStringBuffer.toString();
/*     */   }
/*     */   
/*     */   public boolean isTriangulated()
/*     */   {
/* 280 */     if (this.eOrder != null) return true;
/* 281 */     return false;
/*     */   }
/*     */   
/*     */   public boolean isChordal()
/*     */   {
/* 286 */     setChordalGraph();
/*     */     boolean bool;
/* 288 */     if (this.fillIn == null) bool = true; else
/* 289 */       bool = false;
/* 290 */     reinit();
/* 291 */     return bool;
/*     */   }
/*     */   
/*     */ 
/*     */   int[][] isChordalWithCqs()
/*     */   {
/* 297 */     setChordalGraph();
/* 298 */     if (this.fillIn == null) {
/* 299 */       findClique();
/* 300 */       int[][] arrayOfInt = this.cqCand;
/* 301 */       reinit();
/* 302 */       return arrayOfInt;
/*     */     }
/*     */     
/* 305 */     reinit();
/* 306 */     return (int[][])null;
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
/*     */   public int[][] findClique()
/*     */   {
/* 319 */     int i = this.nd.length;
/* 320 */     int j = i;
/*     */     
/* 322 */     for (int k = i - 1; k > 0; k--) {
/* 323 */       for (m = k - 1; m >= 0; m--) {
/* 324 */         if (MATH.isSubset(this.cqCand[k], this.cqCand[m])) {
/* 325 */           this.cqCand[k] = null;
/* 326 */           j--;
/* 327 */           break;
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 332 */     int[][] arrayOfInt = new int[j][];
/* 333 */     int m = 0;
/* 334 */     for (int n = 0; n < i; n++) {
/* 335 */       if (this.cqCand[n] != null) {
/* 336 */         arrayOfInt[m] = this.cqCand[n];
/* 337 */         MATH.qsort(arrayOfInt[m]);
/* 338 */         m++;
/*     */       }
/*     */     }
/* 341 */     this.cqCand = arrayOfInt;
/* 342 */     return UTIL.getDuplicate(arrayOfInt);
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
/*     */   int[] getCliqueCover(int[] paramArrayOfInt)
/*     */   {
/* 356 */     if (paramArrayOfInt == null) {
/* 357 */       HelpPanel.showError("Cannot get clique cover for empty set!");
/* 358 */       return null;
/*     */     }
/*     */     
/*     */ 
/* 362 */     Object localObject = UTIL.getDuplicate(paramArrayOfInt);
/*     */     int i;
/* 364 */     do { i = 0;
/* 365 */       for (int j = 0; j < localObject.length; j++) {
/* 366 */         int[] arrayOfInt2 = this.nd[localObject[j]].getOtherNeighbor((int[])localObject);
/* 367 */         int[] arrayOfInt3 = this.nd[localObject[j]].getNeighbor();
/* 368 */         if (arrayOfInt2 != null)
/* 369 */           for (int k = 0; k < arrayOfInt2.length; k++) {
/* 370 */             int[] arrayOfInt1 = MATH.addMember(arrayOfInt2[k], (int[])localObject);
/* 371 */             if (isComplete(arrayOfInt1)) {
/* 372 */               localObject = arrayOfInt1;i = 1;
/*     */             }
/*     */           }
/*     */       }
/* 376 */     } while (i != 0);
/* 377 */     return (int[])localObject;
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
/*     */   int[][] getAllClqCovers(int[] paramArrayOfInt)
/*     */   {
/* 390 */     if (paramArrayOfInt == null) {
/* 391 */       HelpPanel.showError("Cannot get clique cover for empty set!");
/* 392 */       return (int[][])null;
/*     */     }
/*     */     
/* 395 */     int[] arrayOfInt1 = this.nd[paramArrayOfInt[0]].getOtherNeighbor(paramArrayOfInt);
/* 396 */     int[][] arrayOfInt = (int[][])null;
/* 397 */     if (arrayOfInt1 == null) {
/* 398 */       arrayOfInt = new int[1][];
/* 399 */       arrayOfInt[0] = UTIL.getDuplicate(paramArrayOfInt);
/* 400 */       return arrayOfInt;
/*     */     }
/*     */     
/* 403 */     int i = arrayOfInt1.length;
/*     */     
/* 405 */     for (int j = 0; j < i; j++) {
/* 406 */       int[] arrayOfInt2 = MATH.addMember(arrayOfInt1[j], paramArrayOfInt);
/* 407 */       int[] arrayOfInt3; if (isComplete(arrayOfInt2)) arrayOfInt3 = arrayOfInt2; else
/* 408 */         arrayOfInt3 = UTIL.getDuplicate(paramArrayOfInt);
/* 409 */       for (int k = 0; k < arrayOfInt1.length; k++)
/* 410 */         if (k != j) {
/* 411 */           arrayOfInt2 = MATH.addMember(arrayOfInt1[k], arrayOfInt3);
/* 412 */           if (isComplete(arrayOfInt2)) arrayOfInt3 = arrayOfInt2;
/*     */         }
/* 414 */       arrayOfInt = MATH.appendMember(arrayOfInt3, arrayOfInt);
/*     */     }
/* 416 */     return arrayOfInt;
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
/*     */   int[][] getCrux(int[] paramArrayOfInt, int[][] paramArrayOfInt1, int[][] paramArrayOfInt2)
/*     */   {
/* 434 */     if (paramArrayOfInt == null) {
/* 435 */       HelpPanel.showError("Invalid input nodes set for crux!");return (int[][])null;
/*     */     }
/* 437 */     int i = paramArrayOfInt1.length;int j = paramArrayOfInt2.length;
/*     */     
/* 439 */     int[] arrayOfInt1 = null;
/* 440 */     for (int k = 0; k < j; k++) {
/* 441 */       if (MATH.isSubset(paramArrayOfInt, paramArrayOfInt2[k])) {
/* 442 */         arrayOfInt1 = paramArrayOfInt2[k]; break;
/*     */       }
/*     */     }
/*     */     
/* 446 */     int[] arrayOfInt2 = null;
/* 447 */     for (int m = 0; m < j; m++) {
/* 448 */       int[] arrayOfInt3 = MATH.getIntersection(arrayOfInt1, paramArrayOfInt2[m]);
/* 449 */       if (arrayOfInt3 != null) {
/* 450 */         int n = 0;
/* 451 */         for (int i1 = 0; i1 < i; i1++) {
/* 452 */           if (MATH.isEqualSet(paramArrayOfInt2[m], paramArrayOfInt1[i1])) {
/* 453 */             n = 1; break;
/*     */           }
/*     */         }
/* 456 */         if (n == 0) { arrayOfInt2 = MATH.union(arrayOfInt2, paramArrayOfInt2[m]);
/*     */         }
/*     */       }
/*     */     }
/* 460 */     int[][] arrayOfInt = new int[2][];
/* 461 */     arrayOfInt[0] = arrayOfInt1;arrayOfInt[1] = arrayOfInt2;
/* 462 */     return arrayOfInt;
/*     */   }
/*     */   
/*     */ 
/*     */   public int[][] getIntClique()
/*     */   {
/* 468 */     int i = this.cqCand.length;
/* 469 */     int[][] arrayOfInt = new int[i][];
/* 470 */     for (int j = 0; j < i; j++) {
/* 471 */       arrayOfInt[j] = new int[this.cqCand[j].length];
/* 472 */       for (int k = 0; k < this.cqCand[j].length; k++) arrayOfInt[j][k] = this.cqCand[j][k];
/*     */     }
/* 474 */     return arrayOfInt;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   void setMoralGraph(DirectGraph paramDirectGraph)
/*     */   {
/* 481 */     int i = this.nd.length;
/*     */     
/* 483 */     for (int j = 0; j < i; j++) {
/* 484 */       this.nd[j].setLabel(paramDirectGraph.nd[j].getLabel());
/* 485 */       this.nd[j].setPos(paramDirectGraph.nd[j].getPos());
/*     */     }
/*     */     int[] arrayOfInt1;
/* 488 */     for (j = 0; j < i; j++) {
/* 489 */       arrayOfInt1 = MATH.union(paramDirectGraph.nd[j].getParent(), paramDirectGraph.nd[j].getChild());
/* 490 */       this.nd[j].setNeighbor(arrayOfInt1);
/*     */     }
/*     */     
/* 493 */     for (j = 0; j < i; j++) {
/* 494 */       arrayOfInt1 = paramDirectGraph.nd[j].getParent();
/* 495 */       int k = arrayOfInt1 == null ? 0 : arrayOfInt1.length;
/* 496 */       for (int m = 0; m < k; m++) {
/* 497 */         int n = arrayOfInt1[m];
/* 498 */         int[] arrayOfInt2 = MATH.union(arrayOfInt1, this.nd[n].getNeighbor());
/* 499 */         this.nd[n].setNeighbor(MATH.delMember(n, arrayOfInt2));
/*     */       }
/*     */     }
/*     */     
/* 503 */     this.mfillIn = getNewLinks(paramDirectGraph);
/* 504 */     setLinka(this.mfillIn);
/*     */   }
/*     */   
/*     */   public static ChordalGraph makeMoralGraph(DirectGraph paramDirectGraph)
/*     */   {
/* 509 */     ChordalGraph localChordalGraph = new ChordalGraph();
/* 510 */     int i = paramDirectGraph.getNodeCount();
/* 511 */     localChordalGraph.setDumbNetPlus(i);
/* 512 */     localChordalGraph.setMoralGraph(paramDirectGraph);
/* 513 */     return localChordalGraph;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static ChordalGraph makeMoralGraph(DirectGraph paramDirectGraph, Point[] paramArrayOfPoint)
/*     */   {
/* 523 */     ChordalGraph localChordalGraph = makeMoralGraph(paramDirectGraph);
/* 524 */     if (paramArrayOfPoint == null) { return localChordalGraph;
/*     */     }
/* 526 */     for (int i = 0; i < paramArrayOfPoint.length; i++) localChordalGraph.addMfillIn(paramArrayOfPoint[i]);
/* 527 */     return localChordalGraph;
/*     */   }
/*     */   
/*     */ 
/*     */   void delFillIn()
/*     */   {
/* 533 */     this.fillIn = null;
/*     */   }
/*     */   
/*     */   public Point[] getIntFillIn() {
/* 537 */     return UTIL.getDuplicate(this.fillIn);
/*     */   }
/*     */   
/*     */   void setFillIn(Point[] paramArrayOfPoint) {
/* 541 */     this.fillIn = UTIL.getDuplicate(paramArrayOfPoint);
/* 542 */     setLinkb(this.fillIn);
/* 543 */     addLink(this.fillIn);
/*     */   }
/*     */   
/*     */   public void pickAddFillIn(Point[] paramArrayOfPoint)
/*     */   {
/* 548 */     if (paramArrayOfPoint == null) return;
/* 549 */     for (int i = 0; i < paramArrayOfPoint.length; i++)
/* 550 */       if (!isLink(paramArrayOfPoint[i])) this.fillIn = MATH.appendMember(paramArrayOfPoint[i], this.fillIn);
/* 551 */     setLinkb(this.fillIn);
/* 552 */     addLink(this.fillIn);
/*     */   }
/*     */   
/* 555 */   void pickAddFillIn(String[][] paramArrayOfString) { if (paramArrayOfString == null) return;
/* 556 */     Point[] arrayOfPoint = new Point[paramArrayOfString.length];
/* 557 */     for (int i = 0; i < paramArrayOfString.length; i++)
/* 558 */       arrayOfPoint[i] = new Point(getIndex(paramArrayOfString[i][0]), getIndex(paramArrayOfString[i][1]));
/* 559 */     pickAddFillIn(arrayOfPoint);
/*     */   }
/*     */   
/*     */   Point[] getMfillIn()
/*     */   {
/* 564 */     return UTIL.getDuplicate(this.mfillIn);
/*     */   }
/*     */   
/*     */   boolean isMfillIn(Point paramPoint)
/*     */   {
/* 569 */     return (MATH.member(paramPoint, this.mfillIn)) || (MATH.member(new Point(paramPoint.y, paramPoint.x), this.mfillIn));
/*     */   }
/*     */   
/*     */ 
/*     */   void addMfillIn(Point paramPoint)
/*     */   {
/* 575 */     this.mfillIn = MATH.appendMember(paramPoint, this.mfillIn);
/*     */     
/* 577 */     setLinka(this.mfillIn);
/* 578 */     addLink(this.mfillIn);
/*     */   }
/*     */   
/*     */ 
/*     */   public void showChordalGraph()
/*     */   {
/* 584 */     System.out.println("ChordalGraph:");
/* 585 */     showUndirectGraph();
/* 586 */     UTIL.showList("cqCand[][]:", this.cqCand);
/*     */   }
/*     */ }


/* Location:              /home/anali/INAOE/tesis/codigos/topub/exe/ww4.jar!/Network/ChordalGraph.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */