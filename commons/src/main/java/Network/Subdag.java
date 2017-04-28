/*     */ package Network;
/*     */ 
/*     */ import java.awt.Point;
/*     */ import java.awt.Rectangle;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintWriter;
/*     */ 
/*     */ public class Subdag extends UNode
/*     */ {
/*  11 */   BayesNet net = null;
/*  12 */   ChordalGraph cdg = null;
/*  13 */   JoinTreeM jt = null;
/*  14 */   JoinTreeM[] lt = null;
/*  15 */   String path = null;
/*     */   
/*  17 */   String[] nbr = null;
/*  18 */   Dsepset[] sep = null;
/*     */   
/*     */   static final int SYSTEM = -1;
/*     */   
/*     */ 
/*     */   Subdag() {}
/*     */   
/*     */ 
/*     */   Subdag(Subdag paramSubdag)
/*     */   {
/*  28 */     super(paramSubdag);
/*  29 */     setSubdag(paramSubdag);
/*     */   }
/*     */   
/*     */   void setSubdag(Subdag paramSubdag) {
/*  33 */     this.net = new BayesNet(paramSubdag.getNet());
/*  34 */     this.cdg = new ChordalGraph(paramSubdag.getSkeleton());
/*  35 */     this.jt = new JoinTreeM(paramSubdag.getJoinTreeTrunk());
/*  36 */     this.lt = paramSubdag.getHostTree();
/*  37 */     this.path = new String(paramSubdag.getPath());
/*  38 */     this.nbr = UTIL.getDuplicate(paramSubdag.getNbrPath());
/*  39 */     this.sep = paramSubdag.getSepset();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getNeighborCount()
/*     */   {
/*  50 */     int i = super.getNeighborCount();
/*  51 */     if (this.nbr != null) i = i < this.nbr.length ? this.nbr.length : i;
/*  52 */     if (this.sep != null) i = i < this.sep.length ? this.sep.length : i;
/*  53 */     if (this.lt != null) i = i < this.lt.length ? this.lt.length : i;
/*  54 */     return i;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setPath(String paramString)
/*     */   {
/*  61 */     if (paramString == null) return;
/*  62 */     this.path = new String(paramString);
/*     */   }
/*     */   
/*     */   public String getPath()
/*     */   {
/*  67 */     if (this.path == null) return null;
/*  68 */     return new String(this.path);
/*     */   }
/*     */   
/*     */   String getName()
/*     */   {
/*  73 */     if (this.path == null) return null;
/*  74 */     return new String(UTIL.removePath(this.path));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   void setNbrPath(String[] paramArrayOfString)
/*     */   {
/*  81 */     if (paramArrayOfString == null) return;
/*  82 */     int i = paramArrayOfString.length;
/*  83 */     this.nbr = new String[i];
/*  84 */     for (int j = 0; j < i; j++) {
/*  85 */       if (paramArrayOfString[j] == null) this.nbr[j] = null; else {
/*  86 */         this.nbr[j] = new String(paramArrayOfString[j]);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   String getNbrPath(int paramInt) {
/*  92 */     if (this.nbr == null) {
/*  93 */       HelpPanel.showError("No neighbor found!");
/*  94 */       return null;
/*     */     }
/*  96 */     return new String(this.nbr[paramInt]);
/*     */   }
/*     */   
/*  99 */   String[] getNbrPath() { if (this.nbr == null) return null;
/* 100 */     int i = this.nbr.length;
/* 101 */     String[] arrayOfString = new String[i];
/* 102 */     for (int j = 0; j < i; j++) arrayOfString[j] = new String(this.nbr[j]);
/* 103 */     return arrayOfString;
/*     */   }
/*     */   
/*     */   String getNbrName(int paramInt)
/*     */   {
/* 108 */     if (this.nbr == null) {
/* 109 */       HelpPanel.showError("No neighbor found!");
/* 110 */       return null;
/*     */     }
/* 112 */     return new String(UTIL.removePath(this.nbr[paramInt]));
/*     */   }
/*     */   
/*     */   void setSepset(Dsepset[] paramArrayOfDsepset)
/*     */   {
/* 117 */     this.sep = paramArrayOfDsepset;
/*     */   }
/*     */   
/*     */   Dsepset getSepset(int paramInt)
/*     */   {
/* 122 */     int i = getNbrIndex(paramInt);
/* 123 */     return this.sep[i];
/*     */   }
/*     */   
/* 126 */   Dsepset[] getSepset() { if (this.sep == null) return null;
/* 127 */     Dsepset[] arrayOfDsepset = new Dsepset[this.sep.length];
/* 128 */     for (int i = 0; i < this.sep.length; i++) arrayOfDsepset[i] = new Dsepset(this.sep[i]);
/* 129 */     return arrayOfDsepset;
/*     */   }
/*     */   
/*     */   int[] getSepsetUnion()
/*     */   {
/* 134 */     int[] arrayOfInt = null;
/* 135 */     for (int i = 0; i < this.sep.length; i++)
/* 136 */       arrayOfInt = MATH.union(arrayOfInt, this.sep[i].getLocalIndex());
/* 137 */     return arrayOfInt;
/*     */   }
/*     */   
/*     */ 
/*     */   DirectGraph getDag()
/*     */   {
/* 143 */     return this.net;
/*     */   }
/*     */   
/*     */   public BayesNet getNet() {
/* 147 */     return this.net;
/*     */   }
/*     */   
/*     */   ChordalGraph getSkeleton() {
/* 151 */     return this.cdg;
/*     */   }
/*     */   
/*     */   HyperGraph getJoinTreeTrunk() {
/* 155 */     return this.jt;
/*     */   }
/*     */   
/*     */   HyperGrafM getHostTreeTrunk(int paramInt)
/*     */   {
/* 160 */     return this.lt[paramInt];
/*     */   }
/*     */   
/*     */   JoinTreeM[] getHostTree()
/*     */   {
/* 165 */     if (this.lt == null) return null;
/* 166 */     JoinTreeM[] arrayOfJoinTreeM = new JoinTreeM[this.lt.length];
/* 167 */     for (int i = 0; i < this.lt.length; i++) arrayOfJoinTreeM[i] = new JoinTreeM(this.lt[i]);
/* 168 */     return arrayOfJoinTreeM;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   String[][] getInParentLabel(String[] paramArrayOfString)
/*     */   {
/* 175 */     return this.net.getInParentLabel(paramArrayOfString);
/*     */   }
/*     */   
/*     */ 
/*     */   String[] getInParentLabel(String paramString, String[] paramArrayOfString)
/*     */   {
/* 181 */     return this.net.getInParentLabel(paramString, paramArrayOfString);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private boolean isIdenConnected(int paramInt1, int paramInt2, Subdag paramSubdag)
/*     */   {
/* 189 */     String[] arrayOfString = getSepset(paramInt2).getLabel();
/* 190 */     String[][] arrayOfString1 = getInParentLabel(arrayOfString);
/* 191 */     String[][] arrayOfString2 = paramSubdag.getInParentLabel(arrayOfString);
/* 192 */     for (int i = 0; i < arrayOfString.length; i++) {
/* 193 */       if (!MATH.isEqualSet(arrayOfString1[i], arrayOfString2[i])) {
/* 194 */         HelpPanel.showError("sep(" + paramInt1 + "," + paramInt2 + ") not identically connected.");
/* 195 */         HelpPanel.appendList(" inPa=", arrayOfString1[i]);
/* 196 */         HelpPanel.appendList(" inPb=", arrayOfString2[i]);
/* 197 */         return false;
/*     */       }
/*     */     }
/* 200 */     HelpPanel.addHelp("sep(" + paramInt1 + "," + paramInt2 + ")\tidentically connected.");
/* 201 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   boolean isIdenConnected(int paramInt1, int paramInt2, Subdag[] paramArrayOfSubdag)
/*     */   {
/* 211 */     if ((this.nbr.length == 1) && (getNeighbor(0) == paramInt1)) {
/* 212 */       return isIdenConnected(paramInt2, paramInt1, paramArrayOfSubdag[paramInt1]);
/*     */     }
/* 214 */     for (int i = 0; i < this.nbr.length; i++) {
/* 215 */       int j = getNeighbor(i);
/* 216 */       if ((j != paramInt1) && 
/* 217 */         (!paramArrayOfSubdag[j].isIdenConnected(paramInt2, j, paramArrayOfSubdag))) { return false;
/*     */       }
/*     */     }
/* 220 */     if (paramInt1 == -1) return true;
/* 221 */     return isIdenConnected(paramInt2, paramInt1, paramArrayOfSubdag[paramInt1]);
/*     */   }
/*     */   
/*     */ 
/*     */   boolean hasOutParent(String paramString, String[] paramArrayOfString)
/*     */   {
/* 227 */     return this.net.hasOutParent(paramString, paramArrayOfString);
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
/*     */   int collectOutParentInfo(String paramString, int paramInt1, int paramInt2, Subdag[] paramArrayOfSubdag)
/*     */   {
/* 244 */     int i = this.net.getIndex(paramString);
/* 245 */     if (i == -1) { return 0;
/*     */     }
/* 247 */     int j = 0;
/* 248 */     int m; for (int k = 0; k < this.nbr.length; k++) {
/* 249 */       m = getNeighbor(k);
/* 250 */       if ((m != paramInt1) || (paramInt1 != paramInt2)) {
/* 251 */         String[] arrayOfString = getSepset(m).getLabel();
/* 252 */         if ((MATH.member(paramString, arrayOfString)) && 
/* 253 */           (hasOutParent(paramString, arrayOfString))) j = 1;
/*     */       }
/*     */     }
/* 256 */     for (k = 0; k < this.nbr.length; k++) {
/* 257 */       m = getNeighbor(k);
/* 258 */       if (m != paramInt1) {
/* 259 */         j += paramArrayOfSubdag[m].collectOutParentInfo(paramString, paramInt2, m, paramArrayOfSubdag);
/* 260 */         if (j > 1) {
/* 261 */           HelpPanel.showError("Split parents found: " + paramInt2 + " vs " + m);
/* 262 */           HelpPanel.appendHelp(" (sepnode=" + paramString + " outcnt=" + j + ")");
/* 263 */           return j;
/*     */         }
/*     */       } }
/* 266 */     return j;
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
/*     */   boolean noSplitParent(int paramInt1, int paramInt2, Subdag[] paramArrayOfSubdag)
/*     */   {
/* 282 */     HelpPanel.addHelp("Test split parents at subDAG " + paramInt2 + " called\tby " + paramInt1);
/*     */     
/* 284 */     int[] arrayOfInt = getDsepNode();
/* 285 */     int j; for (int i = 0; i < arrayOfInt.length; i++) {
/* 286 */       j = arrayOfInt[i];
/* 287 */       if ((paramInt1 == -1) || (!MATH.member(j, this.sep[getNbrIndex(paramInt1)].getLocalIndex())))
/*     */       {
/*     */ 
/* 290 */         String str = this.net.getLabel(j);
/* 291 */         int k = collectOutParentInfo(str, paramInt2, paramInt2, paramArrayOfSubdag);
/* 292 */         if (k > 1) return false;
/*     */       }
/*     */     }
/* 295 */     for (i = 0; i < this.nbr.length; i++) {
/* 296 */       j = getNeighbor(i);
/* 297 */       if ((j != paramInt1) && 
/* 298 */         (!paramArrayOfSubdag[j].noSplitParent(paramInt2, j, paramArrayOfSubdag))) return false;
/*     */     }
/* 300 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   boolean isConnected()
/*     */   {
/* 307 */     return this.net.isConnected();
/*     */   }
/*     */   
/*     */   boolean isAcyclic()
/*     */   {
/* 312 */     return this.net.isDag();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   void preProcess(int paramInt1, int paramInt2, Subdag[] paramArrayOfSubdag)
/*     */   {
/* 324 */     int[] arrayOfInt = getDsepNode();
/*     */     
/* 326 */     HelpPanel.appendList("  [" + paramInt2 + "]:", this.net.markRootLeaf(arrayOfInt));
/*     */     
/* 328 */     for (int i = 0; i < this.nbr.length; i++) {
/* 329 */       int j = getNeighbor(i);
/* 330 */       if (j != paramInt1) paramArrayOfSubdag[j].preProcess(paramInt2, j, paramArrayOfSubdag);
/*     */     }
/*     */   }
/*     */   
/*     */   private int[] getDsepNode()
/*     */   {
/* 336 */     int[] arrayOfInt = null;
/* 337 */     for (int i = 0; i < this.nbr.length; i++)
/* 338 */       arrayOfInt = MATH.union(arrayOfInt, this.sep[i].getLocalIndex());
/* 339 */     return arrayOfInt;
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
/*     */   boolean markNode(int paramInt1, int paramInt2, Subdag[] paramArrayOfSubdag)
/*     */   {
/* 353 */     HelpPanel.addHelp("markNode(" + paramInt1 + ",\t" + paramInt2 + "):");
/*     */     
/* 355 */     if ((this.nbr.length == 1) && (getNeighbor(0) == paramInt1)) {
/* 356 */       return false;
/*     */     }
/* 358 */     boolean bool1 = false;
/*     */     
/* 360 */     int[] arrayOfInt = getDsepNode();
/* 361 */     int j; for (int i = 0; i < arrayOfInt.length; i++) {
/* 362 */       j = arrayOfInt[i];
/* 363 */       HelpPanel.appendHelp(" Ck\t" + j);
/* 364 */       if (!this.net.isMarked(j))
/*     */       {
/* 366 */         if ((paramInt1 == -1) || (!MATH.member(j, this.sep[getNbrIndex(paramInt1)].getLocalIndex())))
/*     */         {
/*     */ 
/* 369 */           String str = this.net.getLabel(j);
/* 370 */           Point localPoint = collectFamilyInfo(str, paramInt2, paramInt2, paramArrayOfSubdag);
/* 371 */           HelpPanel.appendHelp(" (" + localPoint.x + "," + localPoint.y + ")");
/* 372 */           if ((localPoint.x == 0) || (localPoint.y == 0)) {
/* 373 */             HelpPanel.appendHelp(" Mk " + j);
/* 374 */             distributeMark(str, paramInt2, paramInt2, paramArrayOfSubdag);
/* 375 */             bool1 = true;
/*     */           }
/*     */         } }
/*     */     }
/* 379 */     for (i = 0; i < this.nbr.length; i++) {
/* 380 */       j = getNeighbor(i);
/* 381 */       if (j != paramInt1) {
/* 382 */         boolean bool2 = paramArrayOfSubdag[j].markNode(paramInt2, j, paramArrayOfSubdag);
/* 383 */         bool1 = (bool1) || (bool2);
/*     */       } }
/* 385 */     return bool1;
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
/*     */   Point collectFamilyInfo(String paramString, int paramInt1, int paramInt2, Subdag[] paramArrayOfSubdag)
/*     */   {
/* 399 */     int i = this.net.getIndex(paramString);
/* 400 */     if (i == -1) { return new Point(-1, -1);
/*     */     }
/* 402 */     Point localPoint1 = new Point(0, 0);
/* 403 */     if (this.net.hasUnmarkedParent(i)) localPoint1.x = 1;
/* 404 */     if (this.net.hasUnmarkedChild(i)) localPoint1.y = 1;
/* 405 */     if ((localPoint1.x == 1) && (localPoint1.y == 1)) { return localPoint1;
/*     */     }
/* 407 */     for (int j = 0; j < this.nbr.length; j++) {
/* 408 */       int k = getNeighbor(j);
/* 409 */       if ((k != paramInt1) && 
/* 410 */         (MATH.member(i, this.sep[j].getLocalIndex()))) {
/* 411 */         Point localPoint2 = paramArrayOfSubdag[k].collectFamilyInfo(paramString, paramInt2, k, paramArrayOfSubdag);
/* 412 */         localPoint1.x = (localPoint1.x > localPoint2.x ? localPoint1.x : localPoint2.x);
/* 413 */         localPoint1.y = (localPoint1.y > localPoint2.y ? localPoint1.y : localPoint2.y);
/*     */       }
/*     */     }
/* 416 */     return localPoint1;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   void distributeMark(String paramString, int paramInt1, int paramInt2, Subdag[] paramArrayOfSubdag)
/*     */   {
/* 424 */     int i = this.net.getIndex(paramString);
/* 425 */     this.net.setMark(i);
/* 426 */     this.net.markRootLeaf(getDsepNode());
/*     */     
/* 428 */     for (int j = 0; j < this.nbr.length; j++) {
/* 429 */       int k = getNeighbor(j);
/* 430 */       if ((k != paramInt1) && 
/* 431 */         (MATH.member(i, this.sep[j].getLocalIndex()))) {
/* 432 */         paramArrayOfSubdag[k].distributeMark(paramString, paramInt2, k, paramArrayOfSubdag);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   boolean markedAll(int paramInt1, int paramInt2, Subdag[] paramArrayOfSubdag) {
/* 438 */     if (!this.net.isMarked()) { return false;
/*     */     }
/* 440 */     if ((this.nbr.length == 1) && (getNeighbor(0) == paramInt1)) {
/* 441 */       return true;
/*     */     }
/* 443 */     boolean bool1 = true;
/* 444 */     for (int i = 0; i < this.nbr.length; i++) {
/* 445 */       int j = getNeighbor(i);
/* 446 */       if (j != paramInt1) {
/* 447 */         boolean bool2 = paramArrayOfSubdag[j].markedAll(paramInt2, j, paramArrayOfSubdag);
/* 448 */         HelpPanel.appendHelp(" (" + paramInt2 + "," + j + ") " + bool2);
/* 449 */         bool1 = (bool1) && (bool2);
/*     */       } }
/* 451 */     return bool1;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   void localMoralize()
/*     */   {
/* 458 */     this.cdg = ChordalGraph.makeMoralGraph(this.net);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   boolean shipMoralLink(int paramInt, Subdag[] paramArrayOfSubdag)
/*     */   {
/* 469 */     boolean bool = false;
/* 470 */     for (int i = 0; i < this.nbr.length; i++) {
/* 471 */       int j = getNeighbor(i);
/* 472 */       String[][] arrayOfString = paramArrayOfSubdag[j].getNewMoralLink(paramInt);
/* 473 */       if (arrayOfString != null)
/*     */       {
/* 475 */         for (int k = 0; k < arrayOfString.length; k++) {
/* 476 */           Point localPoint = new Point(this.net.getIndex(arrayOfString[k][0]), this.net.getIndex(arrayOfString[k][1]));
/*     */           
/* 478 */           if (!this.cdg.isMfillIn(localPoint)) {
/* 479 */             this.cdg.addMfillIn(localPoint);
/* 480 */             bool = true;
/*     */           }
/*     */         } }
/*     */     }
/* 484 */     return bool;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   String[][] getNewMoralLink(int paramInt)
/*     */   {
/* 492 */     Dsepset localDsepset = getSepset(paramInt);
/* 493 */     return localDsepset.getDlinks(this.cdg.getMfillIn());
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
/*     */   String[][] forwardTriangulate(int paramInt1, int paramInt2, Subdag[] paramArrayOfSubdag, String[][] paramArrayOfString)
/*     */   {
/* 512 */     if (paramArrayOfString != null) { this.cdg.pickAddFillIn(paramArrayOfString);
/*     */     }
/* 514 */     for (int i = 0; i < this.nbr.length; i++) {
/* 515 */       int j = getNeighbor(i);
/* 516 */       if (j != paramInt1)
/*     */       {
/* 518 */         HelpPanel.addHelp(paramInt2 + ":" + j + " ");
/* 519 */         int[] arrayOfInt2 = getSepset(j).getLocalIndex();
/* 520 */         this.cdg.setChordalGraph(arrayOfInt2);
/* 521 */         HelpPanel.appendList("eod=", this.cdg.getIntOrder());
/* 522 */         HelpPanel.showList("fln=", this.cdg.getIntFillIn());
/* 523 */         String[][] arrayOfString2 = this.cdg.nodesToLinksLabel(arrayOfInt2);
/* 524 */         HelpPanel.appendList("dlk=", arrayOfString2);
/* 525 */         String[][] arrayOfString3 = paramArrayOfSubdag[j].forwardTriangulate(paramInt2, j, paramArrayOfSubdag, arrayOfString2);
/* 526 */         this.cdg.pickAddFillIn(arrayOfString3);
/*     */       }
/*     */     }
/* 529 */     if (paramInt1 != -1) {
/* 530 */       HelpPanel.addHelp(paramInt2 + ":" + paramInt1 + "\t");
/* 531 */       int[] arrayOfInt1 = getSepset(paramInt1).getLocalIndex();
/* 532 */       this.cdg.setChordalGraph(arrayOfInt1);
/* 533 */       HelpPanel.appendList("eod=", this.cdg.getIntOrder());
/* 534 */       HelpPanel.showList("fln=", this.cdg.getIntFillIn());
/* 535 */       String[][] arrayOfString1 = this.cdg.nodesToLinksLabel(arrayOfInt1);
/* 536 */       HelpPanel.appendList("dlk=", arrayOfString1);
/* 537 */       return arrayOfString1;
/*     */     }
/* 539 */     return (String[][])null;
/*     */   }
/*     */   
/*     */   void distributeDlink(int paramInt1, int paramInt2, Subdag[] paramArrayOfSubdag, String[][] paramArrayOfString) {
/* 543 */     if (paramArrayOfString != null) this.cdg.pickAddFillIn(paramArrayOfString);
/* 544 */     for (int i = 0; i < this.nbr.length; i++) {
/* 545 */       int j = getNeighbor(i);
/* 546 */       if (j != paramInt1)
/*     */       {
/* 548 */         int[] arrayOfInt = getSepset(j).getLocalIndex();
/* 549 */         String[][] arrayOfString = this.cdg.nodesToLinksLabel(arrayOfInt);
/* 550 */         paramArrayOfSubdag[j].distributeDlink(paramInt2, j, paramArrayOfSubdag, arrayOfString);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   boolean checkEliminationOdr(int paramInt1, int paramInt2, Subdag[] paramArrayOfSubdag)
/*     */   {
/*     */     int j;
/*     */     
/*     */ 
/*     */ 
/* 564 */     for (int i = 0; i < this.nbr.length; i++) {
/* 565 */       j = getNeighbor(i);
/* 566 */       if (j != paramInt1)
/*     */       {
/* 568 */         if (!paramArrayOfSubdag[j].checkEliminationOdr(paramInt2, j, paramArrayOfSubdag)) return false;
/*     */       }
/*     */     }
/* 571 */     for (i = 0; i < this.nbr.length; i++) {
/* 572 */       j = getNeighbor(i);
/* 573 */       int[] arrayOfInt = getSepset(j).getLocalIndex();
/* 574 */       this.cdg.delFillIn();
/* 575 */       this.cdg.setChordalGraph(arrayOfInt);
/* 576 */       Point[] arrayOfPoint = this.cdg.getIntFillIn();
/* 577 */       if (arrayOfPoint != null) {
/* 578 */         HelpPanel.addHelp(" Err:" + paramInt2 + "vs" + j);
/* 579 */         HelpPanel.appendList(" dsep=", arrayOfInt);
/* 580 */         HelpPanel.appendList(" fln=", arrayOfPoint);
/* 581 */         return false;
/*     */       }
/*     */     }
/* 584 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   void setJoinTree(Rectangle paramRectangle)
/*     */   {
/* 591 */     this.jt = new JoinTreeM(HyperGraph.setJoinForest(this.cdg, paramRectangle));
/*     */   }
/*     */   
/*     */ 
/*     */   void setHostTree(Rectangle paramRectangle)
/*     */   {
/* 597 */     int i = getNeighborCount();
/* 598 */     this.lt = new JoinTreeM[i];
/*     */     
/* 600 */     for (int j = 0; j < i; j++) {
/* 601 */       int k = getNeighbor(j);
/* 602 */       HelpPanel.appendHelp(k + " ");
/* 603 */       int[] arrayOfInt = getSepset(k).getLocalIndex();
/* 604 */       this.lt[j] = new JoinTreeM(HyperGrafM.setHostTree(this.jt, arrayOfInt, paramRectangle));
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   void setLinkageTree(Rectangle paramRectangle)
/*     */   {
/* 611 */     int i = getNeighborCount();
/* 612 */     for (int j = 0; j < i; j++) {
/* 613 */       int k = getNeighbor(j);
/* 614 */       int[] arrayOfInt = getSepset(k).getLocalIndex();
/* 615 */       this.lt[j] = new JoinTreeM(HyperGrafM.setLinkageTree(this.lt[j], arrayOfInt, paramRectangle));
/*     */       
/* 617 */       HelpPanel.appendHelp("  sdag " + k + " ");
/* 618 */       HelpPanel.appendList("tor=", this.lt[j].getTreeOrder());
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
/*     */   boolean assignSepnode(String paramString, int paramInt1, int paramInt2, Subdag[] paramArrayOfSubdag)
/*     */   {
/* 635 */     int i = this.net.getIndex(paramString);
/* 636 */     if (i == -1) return false;
/*     */     int k;
/* 638 */     for (int j = 0; j < this.nbr.length; j++) {
/* 639 */       k = getNeighbor(j);
/* 640 */       if ((k != paramInt1) || (paramInt1 != paramInt2)) {
/* 641 */         String[] arrayOfString = getSepset(k).getLabel();
/* 642 */         if ((MATH.member(paramString, arrayOfString)) && 
/* 643 */           (hasOutParent(paramString, arrayOfString))) {
/* 644 */           this.net.setMark(i);
/* 645 */           HelpPanel.addHelp("Assign " + paramString + " to\tsubDAG " + paramInt2);
/* 646 */           return true;
/*     */         }
/*     */       }
/*     */     }
/* 650 */     for (j = 0; j < this.nbr.length; j++) {
/* 651 */       k = getNeighbor(j);
/* 652 */       if ((k != paramInt1) && 
/* 653 */         (paramArrayOfSubdag[k].assignSepnode(paramString, paramInt2, k, paramArrayOfSubdag)))
/* 654 */         return true;
/*     */     }
/* 656 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   void assignSepnode(int paramInt1, int paramInt2, Subdag[] paramArrayOfSubdag)
/*     */   {
/* 667 */     if ((this.nbr.length == 1) && (getNeighbor(0) == paramInt1)) { return;
/*     */     }
/* 669 */     int[] arrayOfInt = getDsepNode();
/* 670 */     int j; for (int i = 0; i < arrayOfInt.length; i++) {
/* 671 */       j = arrayOfInt[i];
/* 672 */       if ((paramInt1 == -1) || (!MATH.member(j, this.sep[getNbrIndex(paramInt1)].getLocalIndex())))
/*     */       {
/*     */ 
/* 675 */         String str = this.net.getLabel(j);
/* 676 */         if (!assignSepnode(str, paramInt2, paramInt2, paramArrayOfSubdag)) {
/* 677 */           this.net.setMark(j);
/* 678 */           HelpPanel.addHelp("Assign " + str + " to subDAG " + paramInt2);
/*     */         }
/*     */       }
/*     */     }
/* 682 */     for (i = 0; i < this.nbr.length; i++) {
/* 683 */       j = getNeighbor(i);
/* 684 */       if (j != paramInt1) {
/* 685 */         paramArrayOfSubdag[j].assignSepnode(paramInt2, j, paramArrayOfSubdag);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   int[] getBeliefVars() {
/* 691 */     int[] arrayOfInt1 = getDsepNode();
/* 692 */     int[] arrayOfInt2 = null;
/* 693 */     for (int i = 0; i < this.net.getNodeCount(); i++) {
/* 694 */       if (!MATH.member(i, arrayOfInt1)) { arrayOfInt2 = MATH.addMember(i, arrayOfInt2);
/* 695 */       } else if (this.net.isMarked(i)) arrayOfInt2 = MATH.addMember(i, arrayOfInt2);
/*     */     }
/* 697 */     return arrayOfInt2;
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
/*     */   String[] collectObserved(int paramInt1, int paramInt2, Subdag[] paramArrayOfSubdag)
/*     */   {
/* 710 */     HelpPanel.appendHelp(paramInt1 + "<" + paramInt2 + ",");
/* 711 */     for (int i = 0; i < getNeighborCount(); i++) {
/* 712 */       int j = getNeighbor(i);
/* 713 */       if (j != paramInt1) {
/* 714 */         String[] arrayOfString = paramArrayOfSubdag[j].collectObserved(paramInt2, j, paramArrayOfSubdag);
/* 715 */         this.net.setObserved(arrayOfString);
/*     */       } }
/* 717 */     if (paramInt1 != -1) return getObserved(paramInt1);
/* 718 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   void distributeObserved(String[] paramArrayOfString, int paramInt1, int paramInt2, Subdag[] paramArrayOfSubdag)
/*     */   {
/* 728 */     HelpPanel.appendHelp(paramInt1 + ">" + paramInt2 + ",");
/* 729 */     if (paramArrayOfString != null) this.net.setObserved(paramArrayOfString);
/* 730 */     if ((getNeighborCount() == 1) && (getNeighbor(0) == paramInt1)) { return;
/*     */     }
/* 732 */     for (int i = 0; i < getNeighborCount(); i++) {
/* 733 */       int j = getNeighbor(i);
/* 734 */       if (j != paramInt1) {
/* 735 */         String[] arrayOfString = getObserved(j);
/* 736 */         paramArrayOfSubdag[j].distributeObserved(arrayOfString, paramInt2, j, paramArrayOfSubdag);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   String[] getObserved(int paramInt) {
/* 742 */     int[] arrayOfInt1 = getSepset(paramInt).getLocalIndex();
/* 743 */     int[] arrayOfInt2 = this.net.getObserved(arrayOfInt1);
/* 744 */     return this.net.getLabel(arrayOfInt2);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   static Subdag load(BufferedReader paramBufferedReader, String paramString)
/*     */   {
/* 752 */     Subdag localSubdag = new Subdag();
/*     */     
/* 754 */     String str = UTIL.loadString(paramBufferedReader);
/* 755 */     if (paramString.equals("\\")) str = str.replace('/', '\\'); else
/* 756 */       str = str.replace('\\', '/');
/* 757 */     localSubdag.setPath(str);
/*     */     
/* 759 */     localSubdag.setLabel(UTIL.removePath(UTIL.removePostfix(str)));
/* 760 */     localSubdag.setNeighbor(UTIL.loadIntList(paramBufferedReader));
/* 761 */     localSubdag.setPos(UTIL.loadPoint(paramBufferedReader));
/* 762 */     return localSubdag;
/*     */   }
/*     */   
/*     */   public void loadDag()
/*     */   {
/* 767 */     this.net = BayesNet.loadSkipPb(this.path + ".bn");
/*     */   }
/*     */   
/*     */   void loadDsepset(BufferedReader paramBufferedReader)
/*     */   {
/* 772 */     int i = UTIL.loadInt(paramBufferedReader);
/* 773 */     this.sep = new Dsepset[i];
/*     */     
/* 775 */     for (int j = 0; j < i; j++) {
/* 776 */       UTIL.skipLine(paramBufferedReader, 2);
/* 777 */       int[] arrayOfInt = UTIL.loadIntList(paramBufferedReader);
/* 778 */       String[] arrayOfString = UTIL.loadStringListMLine(paramBufferedReader, arrayOfInt.length);
/* 779 */       this.sep[j] = new Dsepset(arrayOfString, arrayOfInt);
/*     */     }
/*     */   }
/*     */   
/*     */   public void loadDsepset() {
/* 784 */     loadDsepset(".nbr");
/*     */   }
/*     */   
/*     */   public void loadDsepset(String paramString) {
/*     */     try {
/* 789 */       BufferedReader localBufferedReader = new BufferedReader(new java.io.FileReader(this.path + paramString));
/* 790 */       HelpPanel.addHelp("Loading d-sepset from " + this.path + paramString + ".");
/* 791 */       loadDsepset(localBufferedReader);
/* 792 */       localBufferedReader.close();
/*     */     } catch (IOException localIOException) {
/* 794 */       HelpPanel.showError("Unable to load " + this.path + paramString + ".");
/*     */     }
/* 796 */     HelpPanel.appendHelp(" Completed.");
/*     */   }
/*     */   
/*     */   void savePath(PrintWriter paramPrintWriter, int paramInt)
/*     */   {
/* 801 */     paramPrintWriter.println(this.path + "  subnet_" + paramInt);
/*     */   }
/*     */   
/*     */   void save(PrintWriter paramPrintWriter, int paramInt)
/*     */   {
/* 806 */     savePath(paramPrintWriter, paramInt);
/* 807 */     saveNeighbor(paramPrintWriter);
/* 808 */     UTIL.savePoint(paramPrintWriter, getPos());
/*     */   }
/*     */   
/*     */   void saveSepset(PrintWriter paramPrintWriter, int paramInt)
/*     */   {
/* 813 */     UTIL.saveIntList(paramPrintWriter, this.sep[paramInt].getLocalIndex(), "dsepnode_index");
/* 814 */     UTIL.saveStringListMLine(paramPrintWriter, this.sep[paramInt].getLabel());
/*     */   }
/*     */   
/*     */ 
/*     */   public void showSubdag()
/*     */   {
/* 820 */     HelpPanel.addHelp("SubDAG: " + getLabel());
/* 821 */     HelpPanel.addHelp("path: " + getPath());
/* 822 */     HelpPanel.showList("nls[]: ", getNeighbor());
/* 823 */     HelpPanel.showList("nbr[]: ", getNbrPath());
/* 824 */     for (int i = 0; i < this.sep.length; i++) this.sep[i].show();
/*     */   }
/*     */ }


/* Location:              /home/anali/INAOE/tesis/codigos/topub/exe/ww4.jar!/Network/Subdag.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */