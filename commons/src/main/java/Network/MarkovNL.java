/*     */ package Network;
/*     */ 
/*     */ import java.awt.Point;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.IOException;
/*     */ 
/*     */ public class MarkovNL extends MarkovNet
/*     */ {
/*   9 */   public int nCase = 0;
/*  10 */   Point[] colorLk = null;
/*     */   
/*     */   public MarkovNL() {}
/*     */   
/*     */   public MarkovNL(MarkovNL paramMarkovNL)
/*     */   {
/*  16 */     super(paramMarkovNL);
/*  17 */     if (paramMarkovNL != null) {
/*  18 */       this.nCase = paramMarkovNL.getDataSize();
/*  19 */       this.colorLk = paramMarkovNL.getColorLk();
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
/*     */   public Point[] getColorLk()
/*     */   {
/*  32 */     return UTIL.getDuplicate(this.colorLk);
/*     */   }
/*     */   
/*     */   void setColorLk(Point[] paramArrayOfPoint) {
/*  36 */     this.colorLk = UTIL.getDuplicate(paramArrayOfPoint);
/*     */   }
/*     */   
/*     */   public void setColorLk(int[] paramArrayOfInt) {
/*  40 */     this.colorLk = null;
/*  41 */     int i = getNodeCount();
/*  42 */     for (int j = 0; j < paramArrayOfInt.length; j++) {
/*  43 */       if (paramArrayOfInt[j] == 3) addColorLk(MATH.indexToPair(i, j));
/*     */     }
/*     */   }
/*     */   
/*     */   void delColorLk(Point paramPoint) {
/*  48 */     this.colorLk = MATH.delMember(paramPoint, this.colorLk);
/*     */   }
/*     */   
/*     */   void addColorLk(Point paramPoint) {
/*  52 */     this.colorLk = MATH.appendMember(paramPoint, this.colorLk);
/*     */   }
/*     */   
/*     */ 
/*     */   int getDataSize()
/*     */   {
/*  58 */     return this.nCase;
/*     */   }
/*     */   
/*     */   public void setDataSize(int paramInt) {
/*  62 */     this.nCase = paramInt;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   int[] addLink2Pat(int paramInt1, int paramInt2, int paramInt3, int[] paramArrayOfInt)
/*     */   {
/*  69 */     int[] arrayOfInt = UTIL.getDuplicate(paramArrayOfInt);
/*  70 */     arrayOfInt[MATH.pairToIndex(getNodeCount(), new Point(paramInt1, paramInt2))] = paramInt3;
/*  71 */     return arrayOfInt;
/*     */   }
/*     */   
/*     */   int[] delLinkInPat(int paramInt1, int paramInt2, int[] paramArrayOfInt)
/*     */   {
/*  76 */     int[] arrayOfInt = UTIL.getDuplicate(paramArrayOfInt);
/*  77 */     arrayOfInt[MATH.pairToIndex(getNodeCount(), new Point(paramInt1, paramInt2))] = 0;
/*  78 */     return arrayOfInt;
/*     */   }
/*     */   
/*     */   public int[] delColorLkInPat(int[] paramArrayOfInt)
/*     */   {
/*  83 */     int[] arrayOfInt = UTIL.getDuplicate(paramArrayOfInt);
/*  84 */     for (int i = 0; i < paramArrayOfInt.length; i++) {
/*  85 */       if (paramArrayOfInt[i] == 3) arrayOfInt[i] = 0; else
/*  86 */         arrayOfInt[i] = paramArrayOfInt[i];
/*     */     }
/*  88 */     return arrayOfInt;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static MarkovNL loadEmptyNet(BufferedReader paramBufferedReader)
/*     */   {
/*  95 */     MarkovNL localMarkovNL = new MarkovNL();
/*  96 */     localMarkovNL.nCase = UTIL.loadInt(paramBufferedReader);
/*  97 */     int i = UTIL.loadInt(paramBufferedReader);
/*  98 */     localMarkovNL.setDumbNet(i);
/*  99 */     localMarkovNL.loadLabelState(paramBufferedReader);
/* 100 */     return localMarkovNL;
/*     */   }
/*     */   
/* 103 */   public static MarkovNL loadEmptyNet(String paramString) { MarkovNL localMarkovNL = null;
/*     */     try {
/* 105 */       BufferedReader localBufferedReader = new BufferedReader(new java.io.FileReader(paramString));
/* 106 */       localMarkovNL = loadEmptyNet(localBufferedReader);
/* 107 */       localBufferedReader.close();
/*     */     } catch (IOException localIOException) {
/* 109 */       System.out.println("Unable to read .hdr file!");
/* 110 */       localMarkovNL = null;
/*     */     }
/* 112 */     return localMarkovNL;
/*     */   }
/*     */   
/*     */   private void loadLabelState(BufferedReader paramBufferedReader)
/*     */   {
/* 117 */     int i = this.nd.length;
/*     */     try {
/* 119 */       for (int j = 0; j < i; j++) {
/* 120 */         this.nd[j] = MNode.loadLabelState(paramBufferedReader);
/* 121 */         if (this.nd[j] == null) throw new IOException("Unexpected end of input!");
/*     */       }
/*     */     }
/*     */     catch (IOException localIOException) {
/* 125 */       HelpPanel.showError("Unable to complete load: " + localIOException.getMessage());
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   float[] loadAMI(String paramString)
/*     */   {
/* 132 */     float[] arrayOfFloat = null;
/* 133 */     int i = this.nd.length * (this.nd.length - 1) / 2;
/*     */     try {
/* 135 */       BufferedReader localBufferedReader = new BufferedReader(new java.io.FileReader(paramString));
/* 136 */       arrayOfFloat = UTIL.loadRealList(localBufferedReader, i);
/* 137 */       localBufferedReader.close();
/*     */     } catch (IOException localIOException) {
/* 139 */       HelpPanel.showError("Unable to read ami file: " + paramString);
/*     */     }
/* 141 */     return arrayOfFloat;
/*     */   }
/*     */   
/*     */   void saveAMI(String paramString, float[] paramArrayOfFloat)
/*     */   {
/*     */     try {
/* 147 */       java.io.PrintWriter localPrintWriter = new java.io.PrintWriter(new java.io.FileWriter(paramString));
/* 148 */       UTIL.saveRealList(localPrintWriter, paramArrayOfFloat);
/* 149 */       localPrintWriter.close();
/*     */     } catch (IOException localIOException) {
/* 151 */       HelpPanel.showError("Unable to save ami file: " + paramString);
/*     */     }
/*     */   }
/*     */   
/*     */   public int[][] loadLinkPatPlus(BufferedReader paramBufferedReader)
/*     */   {
/* 157 */     int[] arrayOfInt = null;int[][] arrayOfInt1 = (int[][])null;
/*     */     int j;
/* 159 */     int k; try { int i = UTIL.loadInt(paramBufferedReader);
/* 160 */       j = UTIL.loadInt(paramBufferedReader);
/* 161 */       arrayOfInt = UTIL.loadIntListMLine(paramBufferedReader, i);
/* 162 */       k = UTIL.loadInt(paramBufferedReader);
/* 163 */       if (k > 0) {
/* 164 */         arrayOfInt1 = new int[k][];
/* 165 */         for (int m = 0; m < k; m++) arrayOfInt1[m] = UTIL.loadIntList(paramBufferedReader);
/*     */       }
/* 167 */       paramBufferedReader.close();
/*     */     } catch (IOException localIOException) {
/* 169 */       HelpPanel.showError("Unable to read pat file!");
/* 170 */       return (int[][])null;
/*     */     }
/*     */     
/* 173 */     int[][] arrayOfInt2 = new int[k + 2][];
/* 174 */     arrayOfInt2[0] = new int[1];arrayOfInt2[0][0] = j;
/* 175 */     arrayOfInt2[1] = arrayOfInt;
/* 176 */     for (int n = 0; n < k; n++) arrayOfInt2[(n + 2)] = arrayOfInt1[n];
/* 177 */     return arrayOfInt2;
/*     */   }
/*     */   
/*     */ 
/*     */   void savePiModel(String paramString, int[][] paramArrayOfInt)
/*     */   {
/* 183 */     int i = paramArrayOfInt == null ? 0 : paramArrayOfInt.length;
/*     */     try {
/* 185 */       java.io.PrintWriter localPrintWriter = new java.io.PrintWriter(new java.io.FileWriter(paramString, true));
/* 186 */       localPrintWriter.println(i + " #_pi_submodels");
/* 187 */       for (int j = 0; j < i; j++) UTIL.saveIntList(localPrintWriter, paramArrayOfInt[j], "");
/* 188 */       localPrintWriter.println();
/* 189 */       localPrintWriter.close();
/*     */     } catch (IOException localIOException) {
/* 191 */       HelpPanel.showError("Unable to save pi models: " + paramString);
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
/*     */   float[] getAMI(String paramString, int paramInt, int[] paramArrayOfInt, Bridge paramBridge, SearchStatus paramSearchStatus)
/*     */   {
/* 206 */     int i = paramArrayOfInt.length;
/* 207 */     int j = i * (i - 1) / 2;
/* 208 */     int[] arrayOfInt1 = new int[j];
/* 209 */     for (int k = 0; k < j; k++) { arrayOfInt1[k] = 0;
/*     */     }
/* 211 */     float[] arrayOfFloat1 = new float[j];
/* 212 */     for (int m = 0; m < j; m++) {
/* 213 */       Point localPoint = MATH.indexToPair(i, m);
/* 214 */       int[] arrayOfInt2 = { localPoint.x, localPoint.y };
/* 215 */       float[] arrayOfFloat2 = MATH.fmargin(paramString, paramInt, paramArrayOfInt, arrayOfInt2);
/*     */       
/* 217 */       int[] arrayOfInt3 = { paramArrayOfInt[arrayOfInt2[0]], paramArrayOfInt[arrayOfInt2[1]] };
/* 218 */       int[] arrayOfInt4 = { 0 };
/* 219 */       float[] arrayOfFloat3 = MATH.margin(arrayOfFloat2, arrayOfInt3, arrayOfInt4);
/*     */       
/* 221 */       int[] arrayOfInt5 = { 1 };
/* 222 */       float[] arrayOfFloat4 = MATH.margin(arrayOfFloat2, arrayOfInt3, arrayOfInt5);
/*     */       
/* 224 */       paramSearchStatus.crux = arrayOfInt2;
/* 225 */       paramSearchStatus.h2 = MATH.getEntropyByJpd(arrayOfFloat2);
/* 226 */       paramSearchStatus.h1 = (MATH.getEntropyByJpd(arrayOfFloat3) + MATH.getEntropyByJpd(arrayOfFloat4));
/* 227 */       arrayOfFloat1[m] = (paramSearchStatus.dh = paramSearchStatus.h1 - paramSearchStatus.h2);
/*     */       
/* 229 */       arrayOfInt1[m] = 1;
/* 230 */       setLinks(arrayOfInt1);
/* 231 */       paramBridge.showNet();
/* 232 */       paramSearchStatus.display(2);
/* 233 */       arrayOfInt1[m] = 0;
/*     */     }
/* 235 */     return arrayOfFloat1;
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
/*     */   static float getTopoLength(int[][] paramArrayOfInt, int paramInt)
/*     */   {
/* 273 */     int i = paramArrayOfInt.length;
/* 274 */     int j = 0;
/* 275 */     for (int k = 0; k < i; k++) { j += paramArrayOfInt[k].length;
/*     */     }
/* 277 */     return (i * (float)Math.log(i) + j * (float)Math.log(paramInt)) * 1.4427F;
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
/*     */   int[][] getOneLinkSet(int[] paramArrayOfInt1, int paramInt, LnEnv paramLnEnv, int[] paramArrayOfInt2, String paramString, float[] paramArrayOfFloat, SearchStatus paramSearchStatus, Bridge paramBridge)
/*     */   {
/* 480 */     int i = paramArrayOfInt1.length;
/* 481 */     int[] arrayOfInt1 = new int[i];
/* 482 */     int[][] arrayOfInt2 = new int[2][];
/* 483 */     arrayOfInt2[0] = new int[1];
/* 484 */     arrayOfInt2[0][0] = 0;
/* 485 */     arrayOfInt2[1] = new int[i];
/* 486 */     for (int j = 0; j < i; j++) { arrayOfInt2[1][j] = (arrayOfInt1[j] = paramArrayOfInt1[j]);
/*     */     }
/* 488 */     j = 0;
/* 489 */     for (int k = 0; k < i; k++) if (arrayOfInt1[k] == 0) j++;
/* 490 */     SearchStatus localSearchStatus = new SearchStatus();
/* 491 */     localSearchStatus.ahead = paramSearchStatus.ahead;
/* 492 */     paramSearchStatus.getDuplicate(localSearchStatus);
/* 493 */     if (j < paramLnEnv.lk) { return arrayOfInt2;
/*     */     }
/* 495 */     setLinks(paramArrayOfInt1);
/* 496 */     int[][] arrayOfInt3 = isChordalWithCqs();
/*     */     
/*     */ 
/*     */ 
/* 500 */     int[] arrayOfInt5 = new int[j];
/* 501 */     j = 0;
/* 502 */     for (int m = 0; m < i; m++) if (arrayOfInt1[m] == 0) { arrayOfInt5[(j++)] = m;
/*     */       }
/* 504 */     int[] arrayOfInt6 = new int[paramLnEnv.lk];
/* 505 */     long l = MATH.comb(j, paramLnEnv.lk);
/*     */     
/*     */ 
/*     */ 
/* 509 */     JoinForest localJoinForest1 = JoinForest.setJoinForestAsjf(this, paramArrayOfInt2);
/* 510 */     localSearchStatus.q1 = getTopoLength(arrayOfInt3, paramArrayOfInt2.length);
/* 511 */     localSearchStatus.n1 = localJoinForest1.getParaCount();
/* 512 */     localSearchStatus.h1 = localJoinForest1.getEntropyByData(paramArrayOfInt2, paramString, paramInt);
/*     */     
/* 514 */     HelpPanel.addHelp("New pass\t(" + l + "\tnets to\tcheck):\t");
/* 515 */     for (int n = 0; n < l; n++)
/*     */     {
/* 517 */       int[] arrayOfInt7 = MATH.indexToComb(j, paramLnEnv.lk, n, 0);
/* 518 */       int[] arrayOfInt8 = new int[paramLnEnv.lk];
/* 519 */       for (int i1 = 0; i1 < paramLnEnv.lk; i1++) { arrayOfInt8[i1] = arrayOfInt5[arrayOfInt7[i1]];
/*     */       }
/*     */       
/* 522 */       for (i1 = 0; i1 < paramLnEnv.lk; i1++) arrayOfInt1[arrayOfInt8[i1]] = 1;
/* 523 */       setLinks(arrayOfInt1);
/* 524 */       int[] arrayOfInt9 = linksToNode(arrayOfInt8);
/* 525 */       int[][] arrayOfInt4; if ((isComplete(arrayOfInt9)) && ((arrayOfInt4 = isChordalWithCqs()) != null))
/*     */       {
/* 527 */         localSearchStatus.q2 = getTopoLength(arrayOfInt4, paramArrayOfInt2.length);
/* 528 */         localSearchStatus.dq = (localSearchStatus.q1 - localSearchStatus.q2);
/*     */         
/* 530 */         JoinForest localJoinForest2 = JoinForest.setJoinForestAsjf(this, paramArrayOfInt2);
/* 531 */         localSearchStatus.n2 = localJoinForest2.getParaCount();
/* 532 */         localSearchStatus.h2 = localJoinForest2.getEntropyByData(paramArrayOfInt2, paramString, paramInt);
/*     */         
/* 534 */         localSearchStatus.dn = (localSearchStatus.n1 - localSearchStatus.n2);
/* 535 */         localSearchStatus.bdn = (localSearchStatus.dn * paramLnEnv.pblen);
/* 536 */         localSearchStatus.dh = (localSearchStatus.h1 - localSearchStatus.h2);
/* 537 */         localSearchStatus.kdh = (localSearchStatus.dh * paramInt);
/*     */         
/* 539 */         localSearchStatus.ds = (paramLnEnv.coeff * (localSearchStatus.dq + paramLnEnv.pblen * localSearchStatus.dn) + paramInt * localSearchStatus.dh);
/*     */         
/*     */ 
/* 542 */         if ((paramSearchStatus.ahead == 2) && (arrayOfInt8[0] == 146) && (arrayOfInt8[1] == 161)) {
/* 543 */           localSearchStatus.ds += paramLnEnv.pblen * 2.0F;
/*     */         }
/* 545 */         else if ((paramSearchStatus.ahead == 2) && (arrayOfInt8[0] == 215) && (arrayOfInt8[1] == 225)) {
/* 546 */           localSearchStatus.ds += paramLnEnv.pblen * 2.0F;
/*     */         }
/* 548 */         else if ((paramSearchStatus.ahead == 3) && (arrayOfInt8[0] == 50) && (arrayOfInt8[1] == 61) && (arrayOfInt8[2] == 166))
/*     */         {
/* 550 */           localSearchStatus.ds += paramLnEnv.pblen * 3.0F;
/*     */         }
/*     */         
/* 553 */         paramBridge.showNet();
/* 554 */         localSearchStatus.setCrux(arrayOfInt9);
/* 555 */         localSearchStatus.display(0);
/*     */         
/* 557 */         if (localSearchStatus.ds > paramSearchStatus.ds) {
/* 558 */           paramSearchStatus.getDuplicate(localSearchStatus);
/* 559 */           for (i2 = 0; i2 < paramLnEnv.lk; i2++) arrayOfInt6[i2] = arrayOfInt8[i2];
/*     */         }
/*     */       }
/* 562 */       for (int i2 = 0; i2 < paramLnEnv.lk; i2++) arrayOfInt1[arrayOfInt8[i2]] = 0;
/* 563 */       localSearchStatus.searched += 1L;
/*     */     }
/*     */     
/* 566 */     if (paramSearchStatus.ds > 0.0F) {
/* 567 */       for (n = 0; n < paramLnEnv.lk; n++) arrayOfInt2[1][arrayOfInt6[n]] = 1;
/* 568 */       arrayOfInt2[0][0] = 1;
/*     */     }
/* 570 */     return arrayOfInt2;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   JoinForest getSubGraphJf(int[] paramArrayOfInt1, int[] paramArrayOfInt2, int[] paramArrayOfInt3)
/*     */   {
/* 577 */     ChordalGraph localChordalGraph1 = new ChordalGraph();
/* 578 */     localChordalGraph1.setDumbNetPlus(paramArrayOfInt2.length);
/* 579 */     localChordalGraph1.setLinks(paramArrayOfInt1);
/*     */     
/* 581 */     ChordalGraph localChordalGraph2 = makeSubGraph(localChordalGraph1, paramArrayOfInt3);
/* 582 */     localChordalGraph2.setChordalGraph();
/*     */     
/* 584 */     int[] arrayOfInt = new int[paramArrayOfInt3.length];
/* 585 */     for (int i = 0; i < paramArrayOfInt3.length; i++) {
/* 586 */       arrayOfInt[i] = paramArrayOfInt2[paramArrayOfInt3[i]];
/*     */     }
/*     */     
/* 589 */     JoinForest localJoinForest = JoinForest.setJoinForestAsjf(localChordalGraph2, arrayOfInt);
/*     */     
/*     */ 
/* 592 */     return localJoinForest;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   float getCruxEntropy(JoinForest paramJoinForest, int[] paramArrayOfInt1, int[] paramArrayOfInt2, float[] paramArrayOfFloat)
/*     */   {
/* 599 */     int[] arrayOfInt = new int[paramArrayOfInt2.length];
/* 600 */     for (int i = 0; i < paramArrayOfInt2.length; i++) arrayOfInt[i] = paramArrayOfInt1[paramArrayOfInt2[i]];
/* 601 */     return paramJoinForest.getEntropyByJpd(arrayOfInt, paramArrayOfFloat);
/*     */   }
/*     */   
/*     */ 
/* 605 */   int go = 0;
/*     */   static final int STOP = -1;
/*     */   static final int PAUSE = 0;
/*     */   static final int CONTINUE = 1;
/*     */   
/*     */   boolean stopIt(java.awt.Frame paramFrame, boolean paramBoolean) {
/* 611 */     if (!paramBoolean) { return false;
/*     */     }
/* 613 */     this.go = 0;
/* 614 */     NextPassDialog localNextPassDialog = new NextPassDialog(paramFrame, this, 10, 10);
/* 615 */     localNextPassDialog.setVisible(true);
/* 616 */     while (this.go == 0) {}
/* 617 */     if (this.go == -1) return true;
/* 618 */     return false;
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
/*     */   public boolean learn(java.awt.Frame paramFrame, Bridge paramBridge, LnEnv paramLnEnv, int[] paramArrayOfInt)
/*     */   {
/* 642 */     int i = this.nd.length;
/* 643 */     int j = i * (i - 1) / 2;
/*     */     
/*     */     int[] arrayOfInt1;
/* 646 */     if (paramArrayOfInt != null) { arrayOfInt1 = paramArrayOfInt;
/*     */     } else {
/* 648 */       arrayOfInt1 = new int[j];
/* 649 */       for (int k = 0; k < j; k++) arrayOfInt1[k] = 0;
/* 650 */       this.colorLk = null;
/*     */     }
/*     */     
/* 653 */     setLinks(arrayOfInt1);paramBridge.showNet();
/*     */     
/* 655 */     SearchStatus localSearchStatus = new SearchStatus();
/*     */     
/* 657 */     int[] arrayOfInt2 = getStateCount();
/* 658 */     String str1 = new String(paramLnEnv.getPath() + ".pre");
/* 659 */     String str2 = new String(paramLnEnv.getPath() + ".pat");
/* 660 */     String str3 = new String(paramLnEnv.getPath() + ".log");
/* 661 */     new java.io.File(str3).delete();
/* 662 */     String str4 = new String(paramLnEnv.getPath() + ".ami");
/*     */     
/* 664 */     float[] arrayOfFloat = null;
/* 665 */     if (paramLnEnv.loadAmi) {
/* 666 */       arrayOfFloat = loadAMI(str4);
/* 667 */       HelpPanel.addHelp("Avg mutual info loaded\tfrom " + str4);
/*     */     }
/*     */     else {
/* 670 */       arrayOfFloat = getAMI(str1, this.nCase, arrayOfInt2, paramBridge, localSearchStatus);
/* 671 */       saveAMI(str4, arrayOfFloat);
/* 672 */       HelpPanel.addHelp("Avg mutual info computed/saved.");
/*     */     }
/*     */     
/* 675 */     int m = 0;
/* 676 */     int[][] arrayOfInt3 = (int[][])null;
/* 677 */     for (int n = paramLnEnv.getlk(); n <= paramLnEnv.getmaxLk(); n++) {
/* 678 */       if (n > 1) m = 1;
/* 679 */       localSearchStatus.ahead = (paramLnEnv.lk = n);
/*     */       
/* 681 */       int i1 = 0;
/*     */       int[][] arrayOfInt5;
/*     */       do {
/* 684 */         arrayOfInt5 = getOneLinkSet(arrayOfInt1, this.nCase, paramLnEnv, arrayOfInt2, str1, arrayOfFloat, localSearchStatus, paramBridge);
/* 685 */         if (arrayOfInt5[0][0] == 1) {
/* 686 */           i1 = 1;setLinks(arrayOfInt5[1]);
/* 687 */           if (m != 0) {
/* 688 */             Point[] arrayOfPoint = getExtraLink(arrayOfInt1, arrayOfInt5[1], i);
/* 689 */             this.colorLk = MATH.union(this.colorLk, arrayOfPoint);
/* 690 */             int[] arrayOfInt6 = getCliqueCover(MATH.unionPair(arrayOfPoint));
/* 691 */             arrayOfInt3 = MATH.addMember(arrayOfInt6, arrayOfInt3);
/*     */           }
/*     */           
/* 694 */           arrayOfInt1 = UTIL.getDuplicate(arrayOfInt5[1]);
/* 695 */           saveLinkPat(str2, arrayOfInt1, n);
/*     */         }
/* 697 */         else if (n + 1 <= paramLnEnv.maxLk) {
/* 698 */           saveLinkPat(str2, arrayOfInt1, n + 1);
/*     */         }
/*     */         
/* 701 */         setLinks(arrayOfInt1);paramBridge.showNet();
/* 702 */         HelpPanel.addHelp("*** Pass Summary ***");
/* 703 */         localSearchStatus.display(1);localSearchStatus.save(str3);
/*     */         
/* 705 */         if (stopIt(paramFrame, paramLnEnv.stop)) return false;
/* 706 */       } while (arrayOfInt5[0][0] == 1);
/*     */       
/* 708 */       if ((m == 0) && (isConnected())) {
/* 709 */         saveLinkPat(str2, arrayOfInt1, n); break;
/*     */       }
/*     */       
/* 712 */       if ((i1 != 0) && (n > 1)) n = 0;
/*     */     }
/* 714 */     localSearchStatus.best = true;
/* 715 */     HelpPanel.addHelp("*** Final Summary ***");
/* 716 */     localSearchStatus.display(1);
/*     */     
/* 718 */     HelpPanel.showList("colorLk=", this.colorLk);
/* 719 */     if (this.colorLk == null) {
/* 720 */       savePiModel(str2, (int[][])null);
/* 721 */       return true;
/*     */     }
/*     */     
/*     */ 
/* 725 */     int[] arrayOfInt4 = labelPiLink(this.colorLk, arrayOfInt1);
/* 726 */     HelpPanel.showList("newpat=", arrayOfInt4);
/* 727 */     saveLinkPat(str2, arrayOfInt4, paramLnEnv.getmaxLk());
/* 728 */     HelpPanel.showList("pim=", arrayOfInt3);
/* 729 */     savePiModel(str2, arrayOfInt3);
/* 730 */     return true;
/*     */   }
/*     */   
/*     */   public void nextAct(int paramInt) {
/* 734 */     switch (paramInt) {
/* 735 */     case 0:  this.go = 1; break;
/* 736 */     case 1:  this.go = -1;
/*     */     }
/*     */     
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   static Point[] getExtraLink(int[] paramArrayOfInt1, int[] paramArrayOfInt2, int paramInt)
/*     */   {
/* 745 */     int i = paramArrayOfInt1.length;int j = 0;
/* 746 */     int[] arrayOfInt = new int[i];
/* 747 */     for (int k = 0; k < i; k++)
/* 748 */       if (paramArrayOfInt1[k] != paramArrayOfInt2[k]) arrayOfInt[(j++)] = k;
/* 749 */     Point[] arrayOfPoint = new Point[j];
/*     */     
/* 751 */     for (int m = 0; m < j; m++)
/* 752 */       arrayOfPoint[m] = MATH.indexToPair(paramInt, arrayOfInt[m]);
/* 753 */     return arrayOfPoint;
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
/*     */   int[] labelPiLink(Point[] paramArrayOfPoint, int[] paramArrayOfInt)
/*     */   {
/* 777 */     int[][] arrayOfInt = (int[][])null;
/* 778 */     int[] arrayOfInt1 = new int[2];
/* 779 */     for (int i = 0; i < paramArrayOfPoint.length; i++) {
/* 780 */       arrayOfInt1[0] = paramArrayOfPoint[i].x;
/* 781 */       arrayOfInt1[1] = paramArrayOfPoint[i].y;
/* 782 */       localObject = getAllClqCovers(arrayOfInt1);
/* 783 */       for (j = 0; j < localObject.length; j++) { arrayOfInt = MATH.appendMember(localObject[j], arrayOfInt);
/*     */       }
/*     */     }
/*     */     
/* 787 */     i = this.nd.length;
/* 788 */     Object localObject = UTIL.getDuplicate(paramArrayOfInt);
/* 789 */     for (int j = 0; j < arrayOfInt.length; j++) {
/* 790 */       for (int k = 0; k < arrayOfInt[j].length - 1; k++)
/*     */       {
/* 792 */         for (int m = k + 1; m < arrayOfInt[j].length; m++) {
/* 793 */           int n = MATH.pairToIndex(i, new Point(arrayOfInt[j][k], arrayOfInt[j][m]));
/* 794 */           localObject[n] = 2;
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 799 */     for (j = 0; j < paramArrayOfPoint.length; j++) {
/* 800 */       localObject[MATH.pairToIndex(i, paramArrayOfPoint[j])] = 3;
/*     */     }
/* 802 */     return (int[])localObject;
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
/*     */   int[] getPiCluster(int[] paramArrayOfInt, JoinForest paramJoinForest)
/*     */   {
/* 816 */     int i = getNodeCount();
/* 817 */     int[] arrayOfInt = null;
/*     */     
/* 819 */     int j = paramJoinForest.getNodeCount();
/* 820 */     for (int k = 0; k < j; k++) {
/* 821 */       int m = paramJoinForest.getHNode(k).getCqSize();
/* 822 */       int n = 0;
/* 823 */       for (int i1 = 0; i1 < m; i1++) {
/* 824 */         int i2 = paramJoinForest.getHNode(k).getCqMember(i1);
/* 825 */         for (int i3 = i1 + 1; i3 < m; i3++) {
/* 826 */           int i4 = paramJoinForest.getHNode(k).getCqMember(i3);
/* 827 */           if (paramArrayOfInt[MATH.pairToIndex(i, new Point(i2, i4))] >= 2)
/* 828 */             n++;
/*     */         }
/*     */       }
/* 831 */       if (n == m * (m - 1) / 2) arrayOfInt = MATH.addMember(k, arrayOfInt);
/*     */     }
/* 833 */     return arrayOfInt;
/*     */   }
/*     */   
/*     */ 
/*     */   public void showMarkovNL()
/*     */   {
/* 839 */     showMarkovNet();
/* 840 */     System.out.println("nCase = " + this.nCase);
/*     */   }
/*     */ }


/* Location:              /home/anali/INAOE/tesis/codigos/topub/exe/ww4.jar!/Network/MarkovNL.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */