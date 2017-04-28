/*     */ package Network;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.File;
/*     */ import java.io.FileReader;
/*     */ import java.io.FileWriter;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintStream;
/*     */ import java.io.PrintWriter;
/*     */ import java.util.StringTokenizer;
/*     */ 
/*     */ public class AgGraphDt extends AgGraph
/*     */ {
/*  14 */   DesignNet net = null;
/*  15 */   HyperGraph jtt = null;
/*  16 */   DivisionTree[] dts = null;
/*     */   
/*  18 */   int[] d = null;
/*  19 */   int[] dsz = null;
/*     */   
/*  21 */   float wet = 0.0F;
/*     */   
/*  23 */   int[][] envset = (int[][])null;
/*     */   
/*     */ 
/*  26 */   static boolean debug = false;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setDesignPar()
/*     */   {
/*  36 */     this.d = this.net.getDesignNode();
/*     */   }
/*     */   
/*     */   public void setDesignParSz()
/*     */   {
/*  41 */     this.dsz = this.net.getStateCount(this.d);
/*     */   }
/*     */   
/*     */   public int[] getDesignParSz(int[] paramArrayOfInt)
/*     */   {
/*  46 */     int i = paramArrayOfInt.length;
/*  47 */     int[] arrayOfInt = new int[i];
/*  48 */     for (int j = 0; j < i; j++) {
/*  49 */       for (int k = 0; k < this.d.length; k++) {
/*  50 */         if (paramArrayOfInt[j] == this.d[k]) {
/*  51 */           arrayOfInt[j] = this.dsz[k]; break;
/*     */         }
/*     */       }
/*     */     }
/*  55 */     return arrayOfInt;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setDivTree(int paramInt)
/*     */   {
/*  63 */     this.dts = DivisionTree.setDivTree(this.net, this.jtt, this.lt[paramInt]);
/*  64 */     for (int i = 0; i < this.dts.length; i++) this.dts[i].setWeight(this.wet);
/*     */   }
/*     */   
/*     */   public void setDivTree()
/*     */   {
/*  69 */     this.dts = new DivisionTree[1];
/*  70 */     JoinForest localJoinForest = JoinForest.setJoinGraphAsjf(this.net, this.jtt);
/*  71 */     localJoinForest.setDumbSepsetBelief();
/*  72 */     localJoinForest.unifyBelief();
/*  73 */     this.dts[0] = DivisionTree.setDivTree(this.net, localJoinForest, true);
/*  74 */     this.dts[0].setWeight(this.wet);
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
/*     */   void addDivisionMate(int paramInt, float[][] paramArrayOfFloat)
/*     */   {
/*  88 */     int i = getLkgCount(paramInt);
/*  89 */     int[][] arrayOfInt1 = new int[i][];
/*  90 */     for (int j = 0; j < i; j++) { arrayOfInt1[j] = this.lt[paramInt].getCqMember(j);
/*     */     }
/*  92 */     j = this.dts.length;
/*     */     
/*  94 */     int[] arrayOfInt2 = new int[i];
/*     */     
/*     */ 
/*  97 */     for (int m = 0; m < j; m++) {
/*  98 */       int[] arrayOfInt3 = this.dts[m].getDomain();
/*  99 */       int k = 0;
/* 100 */       for (int n = 0; n < i; n++) {
/* 101 */         if (MATH.isSubset(arrayOfInt1[n], arrayOfInt3))
/* 102 */           arrayOfInt2[(k++)] = n;
/*     */       }
/* 104 */       if (k != 0)
/*     */       {
/* 106 */         int[][] arrayOfInt4 = new int[k][];
/* 107 */         float[][] arrayOfFloat = new float[k][];
/* 108 */         for (int i1 = 0; i1 < k; i1++) {
/* 109 */           arrayOfInt4[i1] = arrayOfInt1[arrayOfInt2[i1]];
/* 110 */           arrayOfFloat[i1] = paramArrayOfFloat[arrayOfInt2[i1]];
/*     */         }
/* 112 */         this.dts[m].addDivisionMate(arrayOfInt4, arrayOfFloat);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public DesignNet getDn()
/*     */   {
/* 119 */     return this.net;
/*     */   }
/*     */   
/*     */   void setDn(DesignNet paramDesignNet) {
/* 123 */     this.net = paramDesignNet;
/*     */   }
/*     */   
/*     */   public DivisionTree[] getDivTree() {
/* 127 */     return this.dts;
/*     */   }
/*     */   
/*     */   public float getWeight() {
/* 131 */     return this.wet;
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
/*     */   String[] getLkgInfo(int paramInt)
/*     */   {
/* 146 */     int i = this.lt[paramInt].getNodeCount();
/* 147 */     String[] arrayOfString1 = new String[i];
/*     */     
/* 149 */     for (int j = 0; j < i; j++) {
/* 150 */       int[] arrayOfInt = this.lt[paramInt].getCqMember(j);
/* 151 */       String[] arrayOfString2 = this.net.getLabel(arrayOfInt);
/* 152 */       arrayOfString1[j] = new String(arrayOfString2[0]);
/* 153 */       for (int k = 1; k < arrayOfString2.length; k++)
/* 154 */         arrayOfString1[j] = new String(arrayOfString1[j] + " " + arrayOfString2[k]);
/*     */     }
/* 156 */     return arrayOfString1;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   float[][] reorderWeud(int paramInt, float[][] paramArrayOfFloat, String[] paramArrayOfString)
/*     */   {
/* 166 */     int i = this.lt[paramInt].getNodeCount();
/* 167 */     float[][] arrayOfFloat = new float[i][];
/* 168 */     for (int j = 0; j < i; j++) {
/* 169 */       int[] arrayOfInt1 = this.net.getIndex(UTIL.getStringList(paramArrayOfString[j]));
/* 170 */       for (int k = 0; k < i; k++) {
/* 171 */         int[] arrayOfInt2 = this.lt[paramInt].getCqMember(k);
/* 172 */         if (MATH.isEqualSet(arrayOfInt1, arrayOfInt2))
/*     */         {
/* 174 */           int[] arrayOfInt3 = this.net.getStateCount(arrayOfInt2);
/* 175 */           arrayOfFloat[j] = MATH.reorderBelief(arrayOfInt2, arrayOfInt3, paramArrayOfFloat[k], arrayOfInt1);
/* 176 */           break;
/*     */         }
/*     */       } }
/* 179 */     return arrayOfFloat;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   float[][] convertWedu(int paramInt, float[] paramArrayOfFloat)
/*     */   {
/* 186 */     int i = this.lt[paramInt].getNodeCount();
/* 187 */     float[][] arrayOfFloat = new float[i][];
/* 188 */     int j = 0;
/* 189 */     for (int k = 0; k < i; k++) {
/* 190 */       int[] arrayOfInt1 = this.lt[paramInt].getCqMember(k);
/* 191 */       int[] arrayOfInt2 = this.net.getStateCount(arrayOfInt1);
/* 192 */       int m = MATH.makePotSize(arrayOfInt2);
/* 193 */       arrayOfFloat[k] = new float[m];
/*     */       
/* 195 */       for (int n = 0; n < m; n++) arrayOfFloat[k][n] = paramArrayOfFloat[(j + n)];
/* 196 */       j += m;
/*     */     }
/* 198 */     return arrayOfFloat;
/*     */   }
/*     */   
/*     */ 
/*     */   float getOptUtil(int paramInt)
/*     */   {
/* 204 */     return this.dts[0].getOptUtil(paramInt);
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
/*     */   int[] getOptDesign()
/*     */   {
/* 219 */     int i = this.dts.length;
/* 220 */     int[][][] arrayOfInt = new int[i][2][];
/* 221 */     for (int j = 0; j < i; j++) { arrayOfInt[j] = this.dts[j].getOptDesign();
/*     */     }
/* 223 */     j = this.d.length;
/* 224 */     int[] arrayOfInt1 = new int[j];
/* 225 */     for (int k = 0; k < j; k++) {
/* 226 */       for (int m = 0; m < i; m++) {
/* 227 */         int n = arrayOfInt[m][0].length;
/* 228 */         for (int i1 = 0; i1 < n; i1++)
/* 229 */           if (arrayOfInt[m][0][i1] == this.d[k]) {
/* 230 */             arrayOfInt1[k] = arrayOfInt[m][1][i1];
/* 231 */             break;
/*     */           }
/*     */       }
/*     */     }
/* 235 */     return arrayOfInt1;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   void designByDiv()
/*     */   {
/* 245 */     for (int i = 0; i < this.dts.length; i++) { this.dts[i].designByDiv();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   void collectDivUtil()
/*     */   {
/* 254 */     this.dts[0].collectDivUtil(0);
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
/*     */   float[][] collectDivUtil(int paramInt)
/*     */   {
/* 270 */     int i = this.lt[paramInt].getNodeCount();
/* 271 */     float[][] arrayOfFloat = new float[i][];
/*     */     
/* 273 */     for (int j = 0; j < i; j++) {
/* 274 */       int[] arrayOfInt1 = this.lt[paramInt].getCqMember(j);
/*     */       
/* 276 */       for (int k = 0; k < this.dts.length; k++) {
/* 277 */         int[] arrayOfInt2 = this.dts[k].getDesignPar();
/* 278 */         if (MATH.isSubset(arrayOfInt1, arrayOfInt2))
/*     */         {
/* 280 */           int m = this.dts[k].getCqHome(arrayOfInt1);
/* 281 */           this.dts[k].collectDivUtil(m);
/* 282 */           arrayOfFloat[j] = this.dts[k].getWeud(m, arrayOfInt1);
/* 283 */           break;
/*     */         }
/*     */       } }
/* 286 */     return arrayOfFloat;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   void distributeOptDivDesign()
/*     */   {
/* 295 */     this.dts[0].distributeOptDivDesign(0);
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
/*     */   void distributeOptDivDesign(int[][][] paramArrayOfInt)
/*     */   {
/* 308 */     int i = paramArrayOfInt[0].length;
/* 309 */     for (int j = 0; j < i; j++) {
/* 310 */       for (int k = 0; k < this.dts.length; k++) {
/* 311 */         int[] arrayOfInt = this.dts[k].getDesignPar();
/* 312 */         if (MATH.isSubset(paramArrayOfInt[0][j], arrayOfInt))
/*     */         {
/* 314 */           int m = this.dts[k].getCqHome(paramArrayOfInt[0][j]);
/* 315 */           this.dts[k].distributeOptDivDesign(m, paramArrayOfInt[0][j], paramArrayOfInt[1][j]);
/* 316 */           break;
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   String[] getOptLkgDesign(int paramInt)
/*     */   {
/* 328 */     int i = this.lt[paramInt].getNodeCount();
/* 329 */     String[] arrayOfString1 = new String[i * 2];
/*     */     
/* 331 */     for (int j = 0; j < i; j++) {
/* 332 */       int[] arrayOfInt1 = this.lt[paramInt].getCqMember(j);
/* 333 */       String[] arrayOfString2 = this.net.getLabel(arrayOfInt1);
/*     */       
/* 335 */       arrayOfString1[(j * 2)] = new String("");
/* 336 */       for (int k = 0; k < arrayOfInt1.length; k++) { int tmp88_87 = (j * 2); String[] tmp88_83 = arrayOfString1;tmp88_83[tmp88_87] = (tmp88_83[tmp88_87] + arrayOfString2[k] + " ");
/*     */       }
/* 338 */       for (k = 0; k < this.dts.length; k++) {
/* 339 */         int[] arrayOfInt2 = this.dts[k].getOptDesign(arrayOfInt1);
/* 340 */         if (arrayOfInt2 != null)
/*     */         {
/* 342 */           arrayOfString1[(j * 2 + 1)] = new String("");
/* 343 */           for (int m = 0; m < arrayOfInt1.length; m++) { int tmp193_192 = (j * 2 + 1); String[] tmp193_186 = arrayOfString1;tmp193_186[tmp193_192] = (tmp193_186[tmp193_192] + arrayOfInt2[m] + " "); }
/* 344 */           break;
/*     */         }
/*     */       } }
/* 347 */     return arrayOfString1;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   int[][][] convertOptLkgDesign(int paramInt, String[] paramArrayOfString)
/*     */   {
/* 354 */     int i = this.lt[paramInt].getNodeCount();
/* 355 */     int[][][] arrayOfInt = new int[2][i][];
/*     */     
/* 357 */     for (int j = 0; j < i; j++) {
/* 358 */       String[] arrayOfString = UTIL.getStringList(paramArrayOfString[(j * 2)]);
/* 359 */       arrayOfInt[0][j] = this.net.getIndex(arrayOfString);
/* 360 */       arrayOfInt[1][j] = UTIL.getIntListPure(paramArrayOfString[(j * 2 + 1)]);
/*     */     }
/* 362 */     return arrayOfInt;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void addEnvSetup(int[][] paramArrayOfInt)
/*     */   {
/* 369 */     this.envset = UTIL.appendToArray(this.envset, paramArrayOfInt);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   void enterEnvSetupToCq()
/*     */   {
/* 377 */     if (this.envset == null) return;
/* 378 */     int i = this.dts.length;
/* 379 */     for (int j = 0; j < i; j++) this.dts[j].enterEnvSetupToCq(this.envset);
/*     */   }
/*     */   
/*     */   void initEnvset()
/*     */   {
/* 384 */     this.envset = ((int[][])null);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void loadDn()
/*     */   {
/* 391 */     this.net = DesignNet.loadDn(this.path + ".dn");
/*     */   }
/*     */   
/* 394 */   public void loadDn(String paramString) { this.net = DesignNet.loadDn(paramString); }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void loadWeight(String paramString)
/*     */   {
/* 408 */     String str1 = new String(this.path + ".wet");
/*     */     try {
/* 410 */       BufferedReader localBufferedReader = new BufferedReader(new FileReader(str1));
/* 411 */       HelpPanel.addHelp("Loading weight from " + str1);
/*     */       
/* 413 */       String str2 = null;
/* 414 */       while ((str2 = localBufferedReader.readLine()) != null) {
/* 415 */         StringTokenizer localStringTokenizer = new StringTokenizer(str2);
/* 416 */         String str3 = localStringTokenizer.nextToken();
/* 417 */         if (str3.equals(paramString)) {
/* 418 */           this.wet = Float.valueOf(localStringTokenizer.nextToken()).floatValue();
/* 419 */           break;
/*     */         }
/*     */       }
/* 422 */       localBufferedReader.close();
/*     */     } catch (IOException localIOException) {
/* 424 */       HelpPanel.showError("Unable to load weight from " + str1 + "!");
/*     */     }
/*     */   }
/*     */   
/*     */   public void loadJtTrunk()
/*     */   {
/* 430 */     this.jtt = HyperGraph.loadJoinTreeTrunk(this.path + ".jtt");
/*     */   }
/*     */   
/*     */   void loadLinkageTreeTrunk(BufferedReader paramBufferedReader)
/*     */   {
/* 435 */     int i = UTIL.loadInt(paramBufferedReader);
/* 436 */     this.lt = new HyperGrafM[i];
/* 437 */     for (int j = 0; j < i; j++) {
/* 438 */       UTIL.skipLine(paramBufferedReader);
/* 439 */       this.lt[j] = HyperGrafM.loadLinkageTreeTrunk(paramBufferedReader);
/*     */     }
/*     */   }
/*     */   
/*     */   public void loadLinkageTreeTrunk() {
/*     */     try {
/* 445 */       BufferedReader localBufferedReader = new BufferedReader(new FileReader(this.path + ".ltt"));
/* 446 */       HelpPanel.addHelp("Loading linkage tree from " + this.path + ".ltt");
/* 447 */       loadLinkageTreeTrunk(localBufferedReader);
/* 448 */       localBufferedReader.close();
/*     */     } catch (IOException localIOException) {
/* 450 */       HelpPanel.showError("Unable to load " + this.path + ".ltt");
/*     */     }
/*     */   }
/*     */   
/*     */   public void loadEnvFacVal()
/*     */   {
/* 456 */     String str = new String(this.path + ".efv");
/* 457 */     loadEnvFacVal(str);
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
/*     */   public void loadEnvFacVal(String paramString)
/*     */   {
/* 472 */     File localFile = new File(paramString);
/* 473 */     if (!localFile.exists()) {
/* 474 */       HelpPanel.addHelp(paramString + " not found.");
/* 475 */       this.envset = ((int[][])null);
/* 476 */       return;
/*     */     }
/*     */     
/* 479 */     String[][] arrayOfString = (String[][])null;
/*     */     try {
/* 481 */       BufferedReader localBufferedReader = new BufferedReader(new FileReader(paramString));
/* 482 */       HelpPanel.addHelp("Loading env factor values from " + paramString);
/* 483 */       arrayOfString = UTIL.loadStringArray(localBufferedReader);
/* 484 */       localBufferedReader.close();
/*     */     } catch (IOException localIOException) {
/* 486 */       HelpPanel.showError("Unable to load " + paramString);
/*     */     }
/* 488 */     if (arrayOfString == null) {
/* 489 */       HelpPanel.showError("Unable to load " + paramString);
/* 490 */       return;
/*     */     }
/*     */     
/* 493 */     int[][] arrayOfInt = new int[arrayOfString.length][2];
/* 494 */     for (int i = 0; i < arrayOfString.length; i++) {
/* 495 */       int j = this.net.getIndex(arrayOfString[i][0]);
/*     */       
/* 497 */       if (j == -1) {
/* 498 */         HelpPanel.showError("Invalid var name: " + arrayOfString[i][0]);
/* 499 */         return;
/*     */       }
/*     */       
/*     */ 
/* 503 */       if (!this.net.isEnvNode(j)) {
/* 504 */         HelpPanel.showError("Not env factor: " + arrayOfString[i][0]);
/* 505 */         return;
/*     */       }
/*     */       
/* 508 */       int k = this.net.getStateCount(j);
/* 509 */       int m = this.net.getStateIndex(j, arrayOfString[i][1]);
/* 510 */       if ((m < 0) || (m >= k)) {
/* 511 */         HelpPanel.showError("Invalid value " + arrayOfString[i][1] + " for var " + arrayOfString[i][0]);
/*     */         
/* 513 */         return;
/*     */       }
/*     */       
/*     */ 
/* 517 */       if (this.net.isObserved(j)) {
/* 518 */         HelpPanel.showError("Value has been entered on var " + arrayOfString[i][0]);
/* 519 */         return;
/*     */       }
/*     */       
/* 522 */       arrayOfInt[i][0] = j;arrayOfInt[i][1] = m;
/*     */     }
/*     */     
/* 525 */     HelpPanel.addHelp("Entering:");
/* 526 */     for (i = 0; i < arrayOfString.length; i++) {
/* 527 */       HelpPanel.addHelp(arrayOfString[i][0] + " = " + arrayOfString[i][1]);
/* 528 */       this.net.setObserved(arrayOfInt[i][0]);
/*     */     }
/* 530 */     this.envset = arrayOfInt;
/*     */   }
/*     */   
/*     */   public void saveOptDes(String paramString)
/*     */   {
/*     */     try {
/* 536 */       PrintWriter localPrintWriter = new PrintWriter(new FileWriter(paramString));
/*     */       
/* 538 */       int[] arrayOfInt = getOptDesign();
/* 539 */       localPrintWriter.println("Globally Optimal Local Design:");
/* 540 */       int i = this.d.length;
/* 541 */       localPrintWriter.println(i + "  #_of_Design_Pars");
/* 542 */       for (int j = 0; j < i; j++) {
/* 543 */         String str1 = this.net.getLabel(this.d[j]);
/* 544 */         String str2 = this.net.getState(this.d[j], arrayOfInt[j]);
/* 545 */         localPrintWriter.println(str1 + " = " + str2);
/*     */       }
/*     */       
/* 548 */       localPrintWriter.println();
/* 549 */       localPrintWriter.println("Opt Util at Div 0 of Div Tree 0 (global opt util if root agent):");
/*     */       
/* 551 */       localPrintWriter.println("" + getOptUtil(0));
/*     */       
/* 553 */       localPrintWriter.close();
/*     */     } catch (IOException localIOException) {
/* 555 */       HelpPanel.showError("Unable to save " + paramString);
/*     */     }
/* 557 */     HelpPanel.addHelp("Optimal design saved in " + paramString);
/*     */   }
/*     */   
/* 560 */   public void saveOptDes() { saveOptDes(this.path + ".des"); }
/*     */   
/*     */ 
/*     */ 
/*     */   void showDivTree()
/*     */   {
/* 566 */     for (int i = 0; i < this.dts.length; i++) {
/* 567 */       System.out.println("\nDiv tree " + i + " :");
/* 568 */       this.dts[i].showDivisionTree();
/*     */     }
/*     */   }
/*     */   
/*     */   public void showDesignPot() {
/* 573 */     for (int i = 0; i < this.dts.length; i++) {
/* 574 */       System.out.println("\nDiv tree " + i + " :");
/* 575 */       this.dts[i].showDesignPot();
/*     */     }
/*     */   }
/*     */   
/*     */   public void showOptDivDesign()
/*     */   {
/* 581 */     System.out.println("\n\t<Opt design by div tree>");
/* 582 */     for (int i = 0; i < this.dts.length; i++) {
/* 583 */       System.out.println("\nDiv tree " + i + " :");
/* 584 */       this.dts[i].showOptDivDesign();
/*     */     }
/*     */   }
/*     */   
/*     */   public void seeOptDesign()
/*     */   {
/* 590 */     int[] arrayOfInt = getOptDesign();
/* 591 */     String str1 = new String("Opt Design (");
/* 592 */     for (int i = 0; i < this.d.length; i++) {
/* 593 */       String str2 = this.net.getLabel(this.d[i]);
/* 594 */       String str3 = this.net.getState(this.d[i], arrayOfInt[i]);
/* 595 */       str1 = str1 + str2 + "=" + str3 + " ";
/*     */     }
/* 597 */     str1 = str1 + ")";
/* 598 */     HelpPanel.addHelp(str1);
/*     */   }
/*     */ }


/* Location:              /home/anali/INAOE/tesis/codigos/topub/exe/ww4.jar!/Network/AgGraphDt.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */