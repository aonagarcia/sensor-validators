/*     */ package Network;
/*     */ 
/*     */ import java.awt.Frame;
/*     */ import java.awt.Point;
/*     */ import java.io.BufferedReader;
/*     */ import java.util.Random;
/*     */ 
/*     */ public class MixNet
/*     */   extends MarkovNL
/*     */ {
/*     */   public MixNet() {}
/*     */   
/*     */   public MixNet(MarkovNet paramMarkovNet)
/*     */   {
/*  15 */     this();
/*  16 */     if ((paramMarkovNet != null) && (paramMarkovNet.nd != null)) setMixNet(paramMarkovNet);
/*     */   }
/*     */   
/*     */   public MixNet(MixNet paramMixNet) {
/*  20 */     this();
/*  21 */     if ((paramMixNet != null) && (paramMixNet.nd != null)) setMixNet(paramMixNet);
/*     */   }
/*     */   
/*     */   public void setDumbNet(int paramInt)
/*     */   {
/*  26 */     this.nd = new XNode[paramInt];
/*     */   }
/*     */   
/*     */   public void setDumbNetPlus(int paramInt)
/*     */   {
/*  31 */     setDumbNet(paramInt);
/*  32 */     for (int i = 0; i < paramInt; i++) this.nd[i] = new XNode();
/*     */   }
/*     */   
/*     */   void setMixNet(MarkovNet paramMarkovNet)
/*     */   {
/*  37 */     int i = paramMarkovNet.getNodeCount();
/*  38 */     setDumbNetPlus(i);
/*  39 */     for (int j = 0; j < i; j++) this.nd[j] = new XNode(paramMarkovNet.getMNode(j));
/*     */   }
/*     */   
/*     */   void setMixNet(MixNet paramMixNet)
/*     */   {
/*  44 */     int i = paramMixNet.getNodeCount();
/*  45 */     setDumbNetPlus(i);
/*  46 */     for (int j = 0; j < i; j++) this.nd[j] = new XNode(paramMixNet.getXNode(j));
/*     */   }
/*     */   
/*     */   XNode getXNode(int paramInt)
/*     */   {
/*  51 */     return new XNode((XNode)this.nd[paramInt]);
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
/*     */   int[] getChildOfMaxParent(int[] paramArrayOfInt1, int[] paramArrayOfInt2)
/*     */   {
/*  66 */     int i = -1;
/*  67 */     int j = paramArrayOfInt1.length;
/*  68 */     int[] arrayOfInt1 = new int[j];
/*  69 */     for (int k = 0; k < j; k++) {
/*  70 */       int[] arrayOfInt3 = ((XNode)this.nd[paramArrayOfInt1[k]]).getInParent(paramArrayOfInt2);
/*  71 */       arrayOfInt1[k] = (arrayOfInt3 == null ? 0 : arrayOfInt3.length);
/*  72 */       if (arrayOfInt1[k] > i) { i = arrayOfInt1[k];
/*     */       }
/*     */     }
/*  75 */     int[] arrayOfInt2 = null;
/*  76 */     for (int m = 0; m < j; m++)
/*  77 */       if (arrayOfInt1[m] == i) arrayOfInt2 = MATH.addMember(paramArrayOfInt1[m], arrayOfInt2);
/*  78 */     return arrayOfInt2;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   void setParentCand(JoinForest paramJoinForest)
/*     */   {
/*  90 */     int i = paramJoinForest.getNodeCount();
/*  91 */     for (int j = 0; j < i; j++) {
/*  92 */       int[] arrayOfInt = paramJoinForest.getCqMember(j);
/*  93 */       for (int k = 0; k < arrayOfInt.length; k++) {
/*  94 */         ((XNode)this.nd[arrayOfInt[k]]).addParentCand(arrayOfInt);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   void delParentCand(int[] paramArrayOfInt, Point paramPoint)
/*     */   {
/* 101 */     for (int i = 0; i < paramArrayOfInt.length; i++) {
/* 102 */       ((XNode)this.nd[paramArrayOfInt[i]]).delParentCand(paramPoint);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean isArc(int paramInt1, int paramInt2)
/*     */   {
/* 109 */     if (((XNode)this.nd[paramInt2]).isParent(paramInt1)) return true;
/* 110 */     return false;
/*     */   }
/*     */   
/*     */   void addArc(int paramInt1, int paramInt2)
/*     */   {
/* 115 */     ((XNode)this.nd[paramInt1]).addChild(paramInt2);
/* 116 */     ((XNode)this.nd[paramInt2]).addParent(paramInt1);
/* 117 */     addLink(paramInt1, paramInt2);
/*     */   }
/*     */   
/*     */   void delArc(int paramInt1, int paramInt2)
/*     */   {
/* 122 */     if (!isArc(paramInt1, paramInt2)) return;
/* 123 */     ((XNode)this.nd[paramInt1]).delChild(paramInt2);
/* 124 */     ((XNode)this.nd[paramInt2]).delParent(paramInt1);
/* 125 */     delLink(paramInt1, paramInt2);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   boolean isBlackLink(int paramInt1, int paramInt2, int[] paramArrayOfInt)
/*     */   {
/* 132 */     int i = MATH.pairToIndex(getNodeCount(), new Point(paramInt1, paramInt2));
/* 133 */     if ((paramArrayOfInt[i] == 1) || (paramArrayOfInt[i] == 2)) return true;
/* 134 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   int[] getNdOfMaxBlackInNb(int[] paramArrayOfInt1, int[] paramArrayOfInt2, int[] paramArrayOfInt3)
/*     */   {
/* 142 */     int i = -1;
/* 143 */     int j = paramArrayOfInt1.length;
/* 144 */     int[] arrayOfInt1 = new int[j];
/* 145 */     for (int k = 0; k < j; k++) {
/* 146 */       int[] arrayOfInt3 = getInNeighbor(paramArrayOfInt1[k], paramArrayOfInt2);
/*     */       
/*     */ 
/* 149 */       int[] arrayOfInt4 = null;
/* 150 */       for (int n = 0; n < arrayOfInt3.length; n++) {
/* 151 */         if (isBlackLink(paramArrayOfInt1[k], arrayOfInt3[n], paramArrayOfInt3)) {
/* 152 */           arrayOfInt4 = MATH.addMember(arrayOfInt3[n], arrayOfInt4);
/*     */         }
/*     */       }
/* 155 */       arrayOfInt1[k] = (arrayOfInt4 == null ? 0 : arrayOfInt4.length);
/* 156 */       if (arrayOfInt1[k] > i) { i = arrayOfInt1[k];
/*     */       }
/*     */     }
/* 159 */     int[] arrayOfInt2 = null;
/* 160 */     for (int m = 0; m < j; m++)
/* 161 */       if (arrayOfInt1[m] == i) arrayOfInt2 = MATH.addMember(paramArrayOfInt1[m], arrayOfInt2);
/* 162 */     return arrayOfInt2;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   boolean isDag()
/*     */   {
/* 169 */     DirectGraph localDirectGraph = new DirectGraph(this);
/* 170 */     if (localDirectGraph.isDag()) return true;
/* 171 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static MixNet loadMixNetFromMn(BufferedReader paramBufferedReader)
/*     */   {
/* 178 */     MarkovNet localMarkovNet = load(paramBufferedReader);
/* 179 */     MixNet localMixNet = new MixNet(localMarkovNet);
/* 180 */     return localMixNet;
/*     */   }
/*     */   
/*     */   public void loadnCase(BufferedReader paramBufferedReader)
/*     */   {
/* 185 */     this.nCase = UTIL.loadInt(paramBufferedReader);
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
/*     */   int[] setClusterOdr(JoinForest paramJoinForest)
/*     */   {
/* 205 */     int i = paramJoinForest.getNodeCount();
/* 206 */     int[] arrayOfInt1 = new int[i];
/* 207 */     for (int j = 0; j < i; j++)
/* 208 */       arrayOfInt1[j] = (paramJoinForest.getCqSize(j) * i - paramJoinForest.getNeighborCount(j));
/* 209 */     int[] arrayOfInt2 = UTIL.getDuplicate(arrayOfInt1);
/*     */     
/* 211 */     int[] arrayOfInt3 = new int[i];
/* 212 */     MATH.qsort(arrayOfInt1);
/* 213 */     for (int k = 0; k < i; k++) {
/* 214 */       for (int m = 0; m < i; m++) {
/* 215 */         if (arrayOfInt1[k] == arrayOfInt2[m]) {
/* 216 */           arrayOfInt3[k] = m;
/* 217 */           arrayOfInt2[m] = -1;
/* 218 */           break;
/*     */         }
/*     */       }
/*     */     }
/* 222 */     return arrayOfInt3;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   int[] getIndSepset(int[] paramArrayOfInt, Point paramPoint, int paramInt, String paramString, float paramFloat)
/*     */   {
/* 232 */     int[] arrayOfInt1 = MATH.delMember(paramPoint.x, MATH.delMember(paramPoint.y, paramArrayOfInt));
/* 233 */     int[] arrayOfInt2 = new int[paramInt];
/* 234 */     int[] arrayOfInt3 = getStateCount();
/* 235 */     int i = arrayOfInt1.length;
/* 236 */     long l = MATH.comb(i, paramInt);
/* 237 */     boolean bool = false;
/* 238 */     int[] arrayOfInt4 = null;
/*     */     
/* 240 */     for (int j = 0; ((j < l ? 1 : 0) & (!bool ? 1 : 0)) != 0; j++) {
/* 241 */       int[] arrayOfInt5 = MATH.indexToComb(i, paramInt, j, 0);
/*     */       
/*     */ 
/* 244 */       for (int k = 0; k < arrayOfInt5.length; k++)
/* 245 */         arrayOfInt2[k] = arrayOfInt1[arrayOfInt5[k]];
/* 246 */       bool = MATH.isCondIndp(paramPoint, arrayOfInt2, paramString, this.nCase, arrayOfInt3, paramFloat);
/*     */       
/* 248 */       if (bool)
/*     */       {
/* 250 */         arrayOfInt4 = MATH.union(arrayOfInt2, arrayOfInt4);
/*     */       }
/*     */     }
/* 253 */     return arrayOfInt4;
/*     */   }
/*     */   
/*     */   int[] delLink(Bridge paramBridge, int[] paramArrayOfInt, float[] paramArrayOfFloat, float paramFloat)
/*     */   {
/* 258 */     int[] arrayOfInt = UTIL.getDuplicate(paramArrayOfInt);
/* 259 */     int i = paramArrayOfInt.length;
/* 260 */     for (int j = 0; j < i; j++) {
/* 261 */       if (((paramArrayOfInt[j] == 1) || (paramArrayOfInt[j] == 2)) && 
/* 262 */         (paramArrayOfFloat[j] < paramFloat)) {
/* 263 */         arrayOfInt[j] = 0;
/* 264 */         setLinks(arrayOfInt);
/* 265 */         paramBridge.showNet();
/*     */       }
/*     */     }
/*     */     
/* 269 */     return arrayOfInt;
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
/*     */   int[] delLink(Bridge paramBridge, int[] paramArrayOfInt1, int[] paramArrayOfInt2, float[] paramArrayOfFloat, float paramFloat, String paramString)
/*     */   {
/* 292 */     int i = paramArrayOfInt1.length;
/* 293 */     int[] arrayOfInt1 = UTIL.getDuplicate(paramArrayOfInt2);
/* 294 */     int j = i - 2;
/*     */     
/*     */ 
/* 297 */     for (int k = 1; k <= j; k++) {
/* 298 */       for (int m = 0; m < i - 1; m++) {
/* 299 */         for (int n = m + 1; n < i; n++) {
/* 300 */           Point localPoint = new Point(paramArrayOfInt1[m], paramArrayOfInt1[n]);
/* 301 */           int i1 = MATH.pairToIndex(getNodeCount(), localPoint);
/* 302 */           if (arrayOfInt1[i1] < 3) {
/* 303 */             int[] arrayOfInt2 = getIndSepset(paramArrayOfInt1, localPoint, k, paramString, paramFloat);
/* 304 */             if (arrayOfInt2 != null) {
/* 305 */               arrayOfInt1[i1] = 0;
/* 306 */               setLinks(arrayOfInt1);
/* 307 */               paramBridge.showNet();
/*     */               
/* 309 */               ((XNode)this.nd[paramArrayOfInt1[m]]).delParentCand(paramArrayOfInt1[n]);
/* 310 */               ((XNode)this.nd[paramArrayOfInt1[n]]).delParentCand(paramArrayOfInt1[m]);
/* 311 */               delParentCand(arrayOfInt2, localPoint);
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 317 */     return arrayOfInt1;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void orient(JoinForest paramJoinForest, int[] paramArrayOfInt)
/*     */   {
/* 324 */     for (int i = 0; i < paramJoinForest.getNodeCount(); i++) {
/* 325 */       setHdToHd(paramJoinForest.getCqMember(i), paramArrayOfInt);
/*     */     }
/* 327 */     setChild(paramArrayOfInt);
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
/*     */   void setHdToHd(int[] paramArrayOfInt1, int[] paramArrayOfInt2)
/*     */   {
/* 344 */     int i = this.nd.length;
/*     */     
/* 346 */     for (int j = 0; j < paramArrayOfInt1.length; j++) {
/* 347 */       int k = paramArrayOfInt1[j];
/* 348 */       int m = ((XNode)this.nd[k]).getParentCandCount();
/* 349 */       for (int n = 0; n < m; n++) {
/* 350 */         int i1 = ((XNode)this.nd[k]).pCand[n].x;
/* 351 */         int i2 = ((XNode)this.nd[k]).pCand[n].y;
/* 352 */         int i3 = paramArrayOfInt2[MATH.pairToIndex(i, new Point(k, i1))];
/* 353 */         int i4 = paramArrayOfInt2[MATH.pairToIndex(i, new Point(k, i2))];
/* 354 */         if ((i3 != 3) && (i4 != 3) && (!isLink(i1, i2)))
/*     */         {
/*     */ 
/* 357 */           addArc(i1, k);addArc(i2, k);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   void setChild(int[] paramArrayOfInt)
/*     */   {
/* 365 */     for (int i = 0; i < this.nd.length; i++) { setChild(i, paramArrayOfInt);
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
/*     */   void setChild(int paramInt, int[] paramArrayOfInt)
/*     */   {
/* 381 */     int[] arrayOfInt1 = ((XNode)this.nd[paramInt]).getParent();
/* 382 */     if (arrayOfInt1 == null) { return;
/*     */     }
/* 384 */     int i = 0;
/* 385 */     for (int j = 0; j < arrayOfInt1.length; j++) {
/* 386 */       if (isBlackLink(arrayOfInt1[j], paramInt, paramArrayOfInt)) {
/* 387 */         i = 1; break;
/*     */       }
/*     */     }
/* 390 */     if (i == 0) { return;
/*     */     }
/* 392 */     int[] arrayOfInt2 = MATH.setDifference(((XNode)this.nd[paramInt]).getNeighbor(), ((XNode)this.nd[paramInt]).getParent());
/*     */     
/* 394 */     int[] arrayOfInt3 = MATH.setDifference(arrayOfInt2, ((XNode)this.nd[paramInt]).getChild());
/*     */     
/* 396 */     if (arrayOfInt3 == null) { return;
/*     */     }
/* 398 */     for (int k = 0; k < arrayOfInt3.length; k++) {
/* 399 */       for (int m = 0; m < arrayOfInt1.length; m++) {
/* 400 */         if ((isBlackLink(arrayOfInt1[m], paramInt, paramArrayOfInt)) && (!isLink(arrayOfInt3[k], arrayOfInt1[m])))
/*     */         {
/* 402 */           addArc(paramInt, arrayOfInt3[k]);
/* 403 */           break;
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
/*     */   void setChildRand(int[] paramArrayOfInt)
/*     */   {
/* 418 */     int i = getNodeCount();
/* 419 */     for (int j = 0; j < paramArrayOfInt.length; j++) {
/* 420 */       if (paramArrayOfInt[j] != 0)
/*     */       {
/* 422 */         Point localPoint = MATH.indexToPair(i, j);
/* 423 */         if ((!isArc(localPoint.x, localPoint.y)) && (!isArc(localPoint.y, localPoint.x)))
/*     */         {
/* 425 */           int[] arrayOfInt1 = ((XNode)this.nd[localPoint.x]).getParent();
/* 426 */           int[] arrayOfInt2 = ((XNode)this.nd[localPoint.y]).getParent();
/* 427 */           if ((arrayOfInt1 != null) && (arrayOfInt2 == null)) {
/* 428 */             addArc(localPoint.x, localPoint.y);
/* 429 */           } else if ((arrayOfInt2 != null) && (arrayOfInt1 == null)) {
/* 430 */             addArc(localPoint.y, localPoint.x);
/* 431 */           } else if ((arrayOfInt2 == null) && (arrayOfInt1 == null)) {
/* 432 */             int k = MATH.getRandomInt(new Random(), 0, 10);
/* 433 */             if (k <= 5) addArc(localPoint.x, localPoint.y); else
/* 434 */               addArc(localPoint.y, localPoint.x);
/*     */           } else {
/* 436 */             HelpPanel.showError("Link orientation conflict.");
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
/*     */   int[] orientPiModel(int[] paramArrayOfInt1, int[] paramArrayOfInt2)
/*     */   {
/* 454 */     int i = paramArrayOfInt1.length;
/* 455 */     int[] arrayOfInt1 = null;
/* 456 */     for (int j = 0; j < i; j++) {
/* 457 */       if (((XNode)this.nd[paramArrayOfInt1[j]]).getOutParent(paramArrayOfInt1) == null) {
/* 458 */         arrayOfInt1 = MATH.addMember(paramArrayOfInt1[j], arrayOfInt1);
/*     */       }
/*     */     }
/* 461 */     int[] arrayOfInt2 = getChildOfMaxParent(arrayOfInt1, paramArrayOfInt1);
/*     */     
/*     */ 
/* 464 */     arrayOfInt2 = getNdOfMaxBlackInNb(arrayOfInt2, paramArrayOfInt1, paramArrayOfInt2);
/* 465 */     int k = arrayOfInt2[MATH.getRandomInt(new Random(), 0, arrayOfInt2.length - 1)];
/*     */     
/*     */ 
/* 468 */     int m = getNodeCount();
/* 469 */     int[] arrayOfInt3 = UTIL.getDuplicate(paramArrayOfInt2);
/* 470 */     for (int n = 0; n < i - 1; n++) {
/* 471 */       for (int i1 = n + 1; i1 < i; i1++) {
/* 472 */         if (paramArrayOfInt2[MATH.pairToIndex(m, new Point(paramArrayOfInt1[n], paramArrayOfInt1[i1]))] == 3) {
/* 473 */           arrayOfInt3 = delLinkInPat(paramArrayOfInt1[n], paramArrayOfInt1[i1], arrayOfInt3);
/* 474 */           if (((XNode)this.nd[paramArrayOfInt1[n]]).isParent(paramArrayOfInt1[i1])) {
/* 475 */             delArc(paramArrayOfInt1[i1], paramArrayOfInt1[n]);
/* 476 */           } else if (((XNode)this.nd[paramArrayOfInt1[i1]]).isParent(paramArrayOfInt1[n])) {
/* 477 */             delArc(paramArrayOfInt1[n], paramArrayOfInt1[i1]);
/* 478 */           } else if (isLink(paramArrayOfInt1[n], paramArrayOfInt1[i1])) {
/* 479 */             delLink(paramArrayOfInt1[n], paramArrayOfInt1[i1]);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 484 */     for (n = 0; n < i; n++) {
/* 485 */       if (paramArrayOfInt1[n] != k) {
/* 486 */         addArc(paramArrayOfInt1[n], k);
/* 487 */         addLink(paramArrayOfInt1[n], k);
/* 488 */         arrayOfInt3 = addLink2Pat(paramArrayOfInt1[n], k, 3, arrayOfInt3);
/*     */       }
/*     */     }
/* 491 */     return arrayOfInt3;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   int[] orientPiModel(int[][] paramArrayOfInt, int[] paramArrayOfInt1)
/*     */   {
/* 498 */     int[] arrayOfInt = UTIL.getDuplicate(paramArrayOfInt1);
/* 499 */     for (int i = 0; i < paramArrayOfInt.length; i++) {
/* 500 */       arrayOfInt = orientPiModel(paramArrayOfInt[i], arrayOfInt);
/*     */     }
/* 502 */     for (i = 0; i < paramArrayOfInt1.length; i++)
/* 503 */       if (arrayOfInt[i] > 0) arrayOfInt[i] = paramArrayOfInt1[i];
/* 504 */     setColorLk(arrayOfInt);
/* 505 */     return arrayOfInt;
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
/*     */   public boolean learnBN(Frame paramFrame, Bridge paramBridge, BnLnEnv paramBnLnEnv, int[] paramArrayOfInt, int[][] paramArrayOfInt1)
/*     */   {
/* 530 */     int[] arrayOfInt1 = delColorLkInPat(paramArrayOfInt);
/* 531 */     this.colorLk = null;
/* 532 */     setLinks(arrayOfInt1);
/* 533 */     paramBridge.showNet();
/* 534 */     JoinForest localJoinForest = JoinForest.setJoinForestAsjf(this);
/*     */     
/* 536 */     int[] arrayOfInt2 = setClusterOdr(localJoinForest);
/* 537 */     HelpPanel.showList("Cluster\tOrder=", arrayOfInt2);
/*     */     
/* 539 */     float[] arrayOfFloat = loadAMI(paramBnLnEnv.getPath() + ".ami");
/* 540 */     float f = paramBnLnEnv.getdhFlr();
/* 541 */     arrayOfInt1 = delLink(paramBridge, arrayOfInt1, arrayOfFloat, f);
/* 542 */     HelpPanel.addHelp("Marginal\tindependent links deleted.");
/* 543 */     if (stopIt(paramFrame, paramBnLnEnv.stop)) { return false;
/*     */     }
/* 545 */     setParentCand(localJoinForest);
/* 546 */     String str1 = new String(paramBnLnEnv.getPath() + ".pre");
/* 547 */     for (int i = 0; i < arrayOfInt2.length; i++) {
/* 548 */       int[] arrayOfInt4 = localJoinForest.getCqMember(arrayOfInt2[i]);
/* 549 */       HelpPanel.showList("Deleting link\tin black cluster ", arrayOfInt4);
/* 550 */       arrayOfInt1 = delLink(paramBridge, arrayOfInt4, arrayOfInt1, arrayOfFloat, f, str1);
/* 551 */       setLinks(arrayOfInt1);
/* 552 */       paramBridge.showNet();
/*     */     }
/* 554 */     HelpPanel.addHelp("Non-PI link deletion finished.");
/* 555 */     if (stopIt(paramFrame, paramBnLnEnv.stop)) { return false;
/*     */     }
/* 557 */     orient(localJoinForest, arrayOfInt1);
/* 558 */     paramBridge.showNet();
/* 559 */     HelpPanel.addHelp("All compelled arcs have been oriented!");
/* 560 */     if (stopIt(paramFrame, paramBnLnEnv.stop)) { return false;
/*     */     }
/* 562 */     int[] arrayOfInt3 = UTIL.getDuplicate(arrayOfInt1);
/* 563 */     for (int j = 0; j < paramArrayOfInt.length; j++) {
/* 564 */       if (paramArrayOfInt[j] == 3) arrayOfInt3[j] = 3; else
/* 565 */         arrayOfInt3[j] = arrayOfInt1[j];
/*     */     }
/* 567 */     setColorLk(arrayOfInt3);setLinks(arrayOfInt3);
/* 568 */     paramBridge.showNet();
/*     */     
/* 570 */     HelpPanel.showList("pim=", paramArrayOfInt1);
/* 571 */     if (paramArrayOfInt1 != null) {
/* 572 */       arrayOfInt3 = orientPiModel(paramArrayOfInt1, arrayOfInt3);
/* 573 */       paramBridge.showNet();
/* 574 */       HelpPanel.addHelp("Non-compelled PI arcs oriented.");
/* 575 */       if (stopIt(paramFrame, paramBnLnEnv.stop)) { return false;
/*     */       }
/*     */     }
/* 578 */     setChildRand(arrayOfInt3);
/* 579 */     paramBridge.showNet();
/* 580 */     HelpPanel.addHelp("All non-compelled arcs oriented.");
/* 581 */     if (stopIt(paramFrame, paramBnLnEnv.stop)) { return false;
/*     */     }
/* 583 */     if (!isDag()) {
/* 584 */       HelpPanel.showError("Cyclic!");return false;
/*     */     }
/* 586 */     HelpPanel.addHelp("It is a DAG.");
/*     */     
/*     */ 
/* 589 */     BayesNet localBayesNet = new BayesNet(this, str1);
/* 590 */     String str2 = new String(paramBnLnEnv.getPath() + ".bn");
/* 591 */     localBayesNet.save(str2);
/*     */     
/* 593 */     return true;
/*     */   }
/*     */   
/*     */   public void showMixNet() {
/* 597 */     HelpPanel.addHelp("Content of MixNet:");
/* 598 */     HelpPanel.showList("colorLk=", getColorLk());
/* 599 */     for (int i = 0; i < this.nd.length; i++) {
/* 600 */       HelpPanel.addHelp("nd[" + i + "]:");
/* 601 */       ((XNode)this.nd[i]).showXNode();
/*     */     }
/*     */   }
/*     */ }


/* Location:              /home/anali/INAOE/tesis/codigos/topub/exe/ww4.jar!/Network/MixNet.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */