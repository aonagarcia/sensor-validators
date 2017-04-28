/*      */ package Network;
/*      */ 
/*      */ import java.awt.Point;
/*      */ import java.io.BufferedReader;
/*      */ import java.io.FileReader;
/*      */ import java.io.IOException;
/*      */ import java.io.PrintStream;
/*      */ import java.util.Random;
/*      */ import java.util.StringTokenizer;
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
/*      */ public class MATH
/*      */ {
/*      */   static final int ADDOUT = 0;
/*      */   static final int ADDIN = 1;
/*      */   
/*      */   public static Point[] getFillIn(int[][] paramArrayOfInt, int paramInt)
/*      */   {
/*   41 */     if (paramArrayOfInt[paramInt] == null) { return null;
/*      */     }
/*   43 */     Point[] arrayOfPoint = null;
/*   44 */     int i = paramArrayOfInt[paramInt].length;
/*   45 */     for (int j = 0; j < i - 1; j++) {
/*   46 */       int k = paramArrayOfInt[paramInt][j];
/*   47 */       for (int m = j + 1; m < i; m++) {
/*   48 */         int n = paramArrayOfInt[paramInt][m];
/*   49 */         if (!member(k, paramArrayOfInt[n])) {
/*   50 */           Point localPoint = new Point(k, n);
/*   51 */           arrayOfPoint = appendMember(localPoint, arrayOfPoint);
/*      */         }
/*      */       }
/*      */     }
/*   55 */     return arrayOfPoint;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public static int[] decToMix(int paramInt, int[] paramArrayOfInt)
/*      */   {
/*   63 */     int i = paramArrayOfInt.length;
/*   64 */     int[] arrayOfInt = new int[i];
/*      */     
/*   66 */     for (int j = 0; j < i - 1; j++) {
/*   67 */       int k = 1;
/*   68 */       for (int m = j + 1; m < i; m++) k *= paramArrayOfInt[m];
/*   69 */       m = k * paramArrayOfInt[j];
/*   70 */       arrayOfInt[j] = (paramInt % m / k);
/*   71 */       if (m <= k) HelpPanel.showError("Overflow detected!");
/*      */     }
/*   73 */     arrayOfInt[(i - 1)] = (paramInt % paramArrayOfInt[(i - 1)]);
/*   74 */     return arrayOfInt;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public static int[] chToMix(String paramString, int[] paramArrayOfInt)
/*      */   {
/*   82 */     int[] arrayOfInt = new int[paramArrayOfInt.length];
/*   83 */     for (int i = 0; i < paramArrayOfInt.length; i++) {
/*   84 */       int j = paramString.charAt(paramArrayOfInt[i]);
/*   85 */       if ((j >= 97) && (j <= 122)) { arrayOfInt[i] = (j - 97);
/*   86 */       } else if ((j >= 65) && (j <= 90)) arrayOfInt[i] = (j - 65 + 26); else
/*   87 */         HelpPanel.showError("Invalid attribute value in data!");
/*      */     }
/*   89 */     return arrayOfInt;
/*      */   }
/*      */   
/*      */ 
/*      */   public static int mixToDec(int[] paramArrayOfInt1, int[] paramArrayOfInt2)
/*      */   {
/*   95 */     int i = 1;int j = 0;
/*   96 */     for (int k = paramArrayOfInt2.length - 1; k >= 0; k--) {
/*   97 */       j += i * paramArrayOfInt1[k];
/*   98 */       i *= paramArrayOfInt2[k];
/*      */     }
/*  100 */     return j;
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
/*      */   static int incMix(int paramInt, int[][] paramArrayOfInt, int[] paramArrayOfInt1)
/*      */   {
/*  115 */     for (int i = paramArrayOfInt1.length - 1; i >= 0; i--) {
/*  116 */       if (paramArrayOfInt[0][i] == paramInt) {
/*  117 */         paramArrayOfInt[1][i] += 1;
/*  118 */         if (paramArrayOfInt[1][i] < paramArrayOfInt1[i]) { return 0;
/*      */         }
/*  120 */         paramArrayOfInt[1][i] = 0;
/*  121 */         if (i == 0) { return 1;
/*      */         }
/*      */       }
/*      */     }
/*  125 */     return 1;
/*      */   }
/*      */   
/*      */   static int incMix(int[] paramArrayOfInt1, int[] paramArrayOfInt2) {
/*  129 */     int[][] arrayOfInt = new int[0][paramArrayOfInt1.length];
/*  130 */     for (int i = 0; i < paramArrayOfInt1.length; i++) {
/*  131 */       arrayOfInt[0][i] = 0;arrayOfInt[1][i] = paramArrayOfInt1[i];
/*      */     }
/*  133 */     return incMix(0, arrayOfInt, paramArrayOfInt2);
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
/*      */   public static int[][] projectMix(int[] paramArrayOfInt1, int[] paramArrayOfInt2, int[] paramArrayOfInt3, int[] paramArrayOfInt4)
/*      */   {
/*  149 */     int i = paramArrayOfInt1.length;
/*  150 */     int j = paramArrayOfInt4.length;
/*  151 */     int[][] arrayOfInt = new int[2][j];
/*      */     
/*  153 */     for (int k = 0; k < j; k++) {
/*  154 */       for (int m = 0; m < i; m++) {
/*  155 */         if (paramArrayOfInt4[k] == paramArrayOfInt1[m]) {
/*  156 */           arrayOfInt[0][k] = paramArrayOfInt2[m];
/*  157 */           arrayOfInt[1][k] = paramArrayOfInt3[m];
/*      */         }
/*      */       }
/*      */     }
/*  161 */     return arrayOfInt;
/*      */   }
/*      */   
/*      */   public static int[][] getAllMix(int[] paramArrayOfInt)
/*      */   {
/*  166 */     int i = 1;
/*  167 */     for (int j = 0; j < paramArrayOfInt.length; j++) i *= paramArrayOfInt[j];
/*  168 */     if (i < 0) { HelpPanel.showError("Overflow detected!");
/*      */     }
/*  170 */     int[][] arrayOfInt = new int[i][paramArrayOfInt.length];
/*  171 */     for (int k = 0; k < i; k++) arrayOfInt[k] = decToMix(k, paramArrayOfInt);
/*  172 */     return arrayOfInt;
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
/*      */   public static boolean member(int paramInt, int[] paramArrayOfInt)
/*      */   {
/*  195 */     if (paramArrayOfInt == null) return false;
/*  196 */     for (int i = 0; i < paramArrayOfInt.length; i++) if (paramInt == paramArrayOfInt[i]) return true;
/*  197 */     return false;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public static boolean isSubset(int[] paramArrayOfInt1, int[] paramArrayOfInt2)
/*      */   {
/*  204 */     if (paramArrayOfInt1 == null) return true;
/*  205 */     if (paramArrayOfInt2 == null) { return false;
/*      */     }
/*  207 */     for (int i = 0; i < paramArrayOfInt1.length; i++) {
/*  208 */       if (!member(paramArrayOfInt1[i], paramArrayOfInt2)) return false;
/*      */     }
/*  210 */     return true;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public static boolean isEqualSet(int[] paramArrayOfInt1, int[] paramArrayOfInt2)
/*      */   {
/*  217 */     if ((isSubset(paramArrayOfInt1, paramArrayOfInt2)) && (isSubset(paramArrayOfInt2, paramArrayOfInt1))) return true;
/*  218 */     return false; }
/*      */   
/*      */   public static boolean isEqualSet(int[][] paramArrayOfInt1, int[][] paramArrayOfInt2) { int j;
/*  221 */     int k; for (int i = 0; i < paramArrayOfInt1.length; i++) {
/*  222 */       j = 0;
/*  223 */       for (k = 0; k < paramArrayOfInt2.length; k++) {
/*  224 */         if (isEqualSet(paramArrayOfInt1[i], paramArrayOfInt2[k])) {
/*  225 */           j = 1; break;
/*      */         }
/*      */       }
/*  228 */       if (j == 0) { return false;
/*      */       }
/*      */     }
/*  231 */     for (i = 0; i < paramArrayOfInt2.length; i++) {
/*  232 */       j = 0;
/*  233 */       for (k = 0; k < paramArrayOfInt1.length; k++) {
/*  234 */         if (isEqualSet(paramArrayOfInt2[i], paramArrayOfInt1[k])) {
/*  235 */           j = 1; break;
/*      */         }
/*      */       }
/*  238 */       if (j == 0) return false;
/*      */     }
/*  240 */     return true;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public static int[] getIntersection(int[] paramArrayOfInt1, int[] paramArrayOfInt2)
/*      */   {
/*  248 */     if ((paramArrayOfInt1 == null) || (paramArrayOfInt2 == null)) { return null;
/*      */     }
/*  250 */     int i = paramArrayOfInt1.length;
/*  251 */     int[] arrayOfInt1 = new int[i];
/*  252 */     int j = 0;
/*  253 */     for (int k = 0; k < i; k++) if (member(paramArrayOfInt1[k], paramArrayOfInt2)) arrayOfInt1[(j++)] = paramArrayOfInt1[k];
/*  254 */     if (j == 0) { return null;
/*      */     }
/*  256 */     int[] arrayOfInt2 = new int[j];
/*  257 */     for (int m = 0; m < j; m++) arrayOfInt2[m] = arrayOfInt1[m];
/*  258 */     return arrayOfInt2;
/*      */   }
/*      */   
/*  261 */   public static String[] getIntersection(String[] paramArrayOfString1, String[] paramArrayOfString2) { if ((paramArrayOfString1 == null) || (paramArrayOfString2 == null)) { return null;
/*      */     }
/*  263 */     int i = paramArrayOfString1.length;
/*  264 */     String[] arrayOfString1 = new String[i];
/*  265 */     int j = 0;
/*  266 */     for (int k = 0; k < i; k++)
/*  267 */       if (member(paramArrayOfString1[k], paramArrayOfString2)) arrayOfString1[(j++)] = new String(paramArrayOfString1[k]);
/*  268 */     if (j == 0) { return null;
/*      */     }
/*  270 */     String[] arrayOfString2 = new String[j];
/*  271 */     for (int m = 0; m < j; m++) arrayOfString2[m] = arrayOfString1[m];
/*  272 */     return arrayOfString2;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public static int[] setDifference(int[] paramArrayOfInt1, int[] paramArrayOfInt2)
/*      */   {
/*  280 */     if (paramArrayOfInt1 == null) return null;
/*  281 */     if (paramArrayOfInt2 == null) { return paramArrayOfInt1;
/*      */     }
/*  283 */     int[] arrayOfInt = null;
/*  284 */     for (int i = 0; i < paramArrayOfInt1.length; i++)
/*  285 */       if (!member(paramArrayOfInt1[i], paramArrayOfInt2)) arrayOfInt = addMember(paramArrayOfInt1[i], arrayOfInt);
/*  286 */     return arrayOfInt;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static int[] union(int[] paramArrayOfInt1, int[] paramArrayOfInt2)
/*      */   {
/*  295 */     if ((paramArrayOfInt1 == null) && (paramArrayOfInt2 == null)) { return null;
/*      */     }
/*  297 */     if ((paramArrayOfInt1 == null) && (paramArrayOfInt2 != null)) {
/*  298 */       j = paramArrayOfInt2.length;
/*  299 */       arrayOfInt1 = new int[j];
/*  300 */       for (k = 0; k < j; k++) arrayOfInt1[k] = paramArrayOfInt2[k];
/*  301 */       return arrayOfInt1;
/*      */     }
/*      */     
/*  304 */     if ((paramArrayOfInt1 != null) && (paramArrayOfInt2 == null)) {
/*  305 */       i = paramArrayOfInt1.length;
/*  306 */       arrayOfInt1 = new int[i];
/*  307 */       for (k = 0; k < i; k++) arrayOfInt1[k] = paramArrayOfInt1[k];
/*  308 */       return arrayOfInt1;
/*      */     }
/*      */     
/*  311 */     int i = paramArrayOfInt1.length;
/*  312 */     int j = paramArrayOfInt2.length;
/*      */     
/*  314 */     int[] arrayOfInt1 = new int[j];
/*  315 */     int k = 0;
/*  316 */     for (int m = 0; m < j; m++) {
/*  317 */       if (!member(paramArrayOfInt2[m], paramArrayOfInt1)) arrayOfInt1[(k++)] = paramArrayOfInt2[m];
/*      */     }
/*  319 */     int[] arrayOfInt2 = new int[i + k];
/*  320 */     for (int n = 0; n < i; n++) arrayOfInt2[n] = paramArrayOfInt1[n];
/*  321 */     for (n = 0; n < k; n++) { arrayOfInt2[(n + i)] = arrayOfInt1[n];
/*      */     }
/*  323 */     qsort(arrayOfInt2);
/*  324 */     return arrayOfInt2;
/*      */   }
/*      */   
/*      */   public static int[] unionPair(Point[] paramArrayOfPoint)
/*      */   {
/*  329 */     if (paramArrayOfPoint == null) return null;
/*  330 */     int[] arrayOfInt = null;
/*  331 */     for (int i = 0; i < paramArrayOfPoint.length; i++)
/*  332 */       arrayOfInt = addMember(paramArrayOfPoint[i].x, addMember(paramArrayOfPoint[i].y, arrayOfInt));
/*  333 */     return arrayOfInt;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static int[] addMember(int paramInt, int[] paramArrayOfInt)
/*      */   {
/*  342 */     if (member(paramInt, paramArrayOfInt)) { return paramArrayOfInt;
/*      */     }
/*      */     int i;
/*  345 */     if (paramArrayOfInt == null) i = 0; else
/*  346 */       i = paramArrayOfInt.length;
/*  347 */     int[] arrayOfInt = new int[i + 1];
/*  348 */     for (int j = 0; j < i; j++) arrayOfInt[j] = paramArrayOfInt[j];
/*  349 */     arrayOfInt[i] = paramInt;
/*      */     
/*  351 */     qsort(arrayOfInt);
/*  352 */     return arrayOfInt;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public static int[] delMember(int paramInt, int[] paramArrayOfInt)
/*      */   {
/*  359 */     if (!member(paramInt, paramArrayOfInt)) {
/*  360 */       HelpPanel.showError("Cannot delete a non-member " + paramInt + " from a set!");
/*  361 */       return paramArrayOfInt;
/*      */     }
/*      */     
/*  364 */     if (paramArrayOfInt.length == 1) { return null;
/*      */     }
/*  366 */     int[] arrayOfInt = new int[paramArrayOfInt.length - 1];
/*  367 */     int i = 0;
/*  368 */     for (int j = 0; j < paramArrayOfInt.length; j++) if (paramArrayOfInt[j] != paramInt) arrayOfInt[(i++)] = paramArrayOfInt[j];
/*  369 */     return arrayOfInt;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static int[][] delMember(int[] paramArrayOfInt, int[][] paramArrayOfInt1)
/*      */   {
/*  379 */     if (!member(paramArrayOfInt, paramArrayOfInt1)) {
/*  380 */       HelpPanel.showList("Cannot delete non-member set: ", paramArrayOfInt);
/*  381 */       return paramArrayOfInt1;
/*      */     }
/*  383 */     if (paramArrayOfInt1.length == 1) { return (int[][])null;
/*      */     }
/*  385 */     int[][] arrayOfInt = new int[paramArrayOfInt1.length - 1][];
/*  386 */     int i = 0;
/*  387 */     for (int j = 0; j < paramArrayOfInt1.length; j++) {
/*  388 */       if (!isEqualSet(paramArrayOfInt1[j], paramArrayOfInt)) arrayOfInt[(i++)] = paramArrayOfInt1[j];
/*      */     }
/*  390 */     return arrayOfInt;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static int[][] addMember(int[] paramArrayOfInt, int[][] paramArrayOfInt1)
/*      */   {
/*  399 */     int i = paramArrayOfInt.length;
/*  400 */     if (paramArrayOfInt == null) return UTIL.getDuplicate(paramArrayOfInt1);
/*  401 */     if (paramArrayOfInt1 == null) {
/*  402 */       int[][] arrayOfInt1 = new int[1][];
/*  403 */       arrayOfInt1[0] = UTIL.getDuplicate(paramArrayOfInt);
/*  404 */       return arrayOfInt1;
/*      */     }
/*      */     
/*  407 */     for (int j = 0; j < paramArrayOfInt1.length; j++) {
/*  408 */       if (isEqualSet(paramArrayOfInt, paramArrayOfInt1[j])) { return UTIL.getDuplicate(paramArrayOfInt1);
/*      */       }
/*      */     }
/*  411 */     j = paramArrayOfInt.length;
/*  412 */     int k = paramArrayOfInt1.length;
/*  413 */     int[][] arrayOfInt2 = new int[k + 1][];
/*      */     
/*  415 */     if (j <= paramArrayOfInt1[0].length) {
/*  416 */       arrayOfInt2[0] = UTIL.getDuplicate(paramArrayOfInt);
/*  417 */       for (m = 0; m < k; m++) arrayOfInt2[(m + 1)] = UTIL.getDuplicate(paramArrayOfInt1[m]);
/*  418 */       return arrayOfInt2;
/*      */     }
/*      */     
/*  421 */     if (j >= paramArrayOfInt1[(k - 1)].length) {
/*  422 */       for (m = 0; m < k; m++) arrayOfInt2[m] = UTIL.getDuplicate(paramArrayOfInt1[m]);
/*  423 */       arrayOfInt2[k] = UTIL.getDuplicate(paramArrayOfInt);
/*  424 */       return arrayOfInt2;
/*      */     }
/*      */     
/*  427 */     int m = 0;
/*  428 */     int n = 0;
/*  429 */     for (int i1 = 0; i1 < k; i1++) {
/*  430 */       if ((n == 0) && (j < paramArrayOfInt1[i1].length)) {
/*  431 */         arrayOfInt2[(m++)] = UTIL.getDuplicate(paramArrayOfInt);n = 1;
/*      */       }
/*  433 */       arrayOfInt2[(m++)] = UTIL.getDuplicate(paramArrayOfInt1[i1]);
/*      */     }
/*  435 */     return arrayOfInt2;
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
/*      */   public static int[] sort(int[] paramArrayOfInt)
/*      */   {
/*  454 */     if (paramArrayOfInt == null) return null;
/*  455 */     int[] arrayOfInt = UTIL.getDuplicate(paramArrayOfInt);
/*  456 */     qsort(arrayOfInt);
/*  457 */     return arrayOfInt;
/*      */   }
/*      */   
/*      */   public static void qsort(int[] paramArrayOfInt)
/*      */   {
/*  462 */     if (paramArrayOfInt == null) return;
/*  463 */     int i = paramArrayOfInt.length;
/*  464 */     quickSort(paramArrayOfInt, 0, i - 1);
/*      */   }
/*      */   
/*      */   private static void quickSort(int[] paramArrayOfInt, int paramInt1, int paramInt2) {
/*  468 */     if (paramInt2 > paramInt1) {
/*  469 */       int i = paramInt1 - 1;
/*  470 */       int j = paramInt2;
/*      */       for (;;) {
/*  472 */         if (paramArrayOfInt[(++i)] >= paramArrayOfInt[paramInt2]) {
/*  473 */           while (j > 0) if (paramArrayOfInt[(--j)] <= paramArrayOfInt[paramInt2]) break;
/*  474 */           if (i >= j) break;
/*  475 */           swap(paramArrayOfInt, i, j);
/*      */         } }
/*  477 */       swap(paramArrayOfInt, i, paramInt2);
/*  478 */       quickSort(paramArrayOfInt, paramInt1, i - 1);
/*  479 */       quickSort(paramArrayOfInt, i + 1, paramInt2);
/*      */     }
/*      */   }
/*      */   
/*      */   private static void swap(int[] paramArrayOfInt, int paramInt1, int paramInt2) {
/*  484 */     int i = paramArrayOfInt[paramInt1];
/*  485 */     paramArrayOfInt[paramInt1] = paramArrayOfInt[paramInt2];
/*  486 */     paramArrayOfInt[paramInt2] = i;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static int[] getSubsetDimen(int[] paramArrayOfInt1, int[] paramArrayOfInt2, int[] paramArrayOfInt3)
/*      */   {
/*  497 */     int[] arrayOfInt = new int[paramArrayOfInt3.length];
/*  498 */     for (int i = 0; i < paramArrayOfInt3.length; i++) {
/*  499 */       for (int j = 0; j < paramArrayOfInt1.length; j++) {
/*  500 */         if (paramArrayOfInt3[i] == paramArrayOfInt1[j]) {
/*  501 */           arrayOfInt[i] = paramArrayOfInt2[j];
/*  502 */           break;
/*      */         }
/*      */       }
/*      */     }
/*  506 */     return arrayOfInt;
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
/*      */   public static int[] sortAssoSet(int[] paramArrayOfInt1, int[] paramArrayOfInt2, int[] paramArrayOfInt3)
/*      */   {
/*  519 */     int[] arrayOfInt = new int[paramArrayOfInt3.length];
/*  520 */     boolean[] arrayOfBoolean = new boolean[paramArrayOfInt1.length];
/*  521 */     for (int i = 0; i < paramArrayOfInt1.length; i++) { arrayOfBoolean[i] = false;
/*      */     }
/*  523 */     for (i = 0; i < paramArrayOfInt3.length; i++) {
/*  524 */       for (int j = 0; j < paramArrayOfInt1.length; j++) {
/*  525 */         if ((paramArrayOfInt3[i] == paramArrayOfInt1[j]) && (arrayOfBoolean[j] == 0)) {
/*  526 */           arrayOfInt[i] = paramArrayOfInt2[j];arrayOfBoolean[j] = true; break;
/*      */         }
/*      */       }
/*      */     }
/*  530 */     return arrayOfInt;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static boolean member(Point paramPoint, Point[] paramArrayOfPoint)
/*      */   {
/*  540 */     if (paramArrayOfPoint == null) return false;
/*  541 */     for (int i = 0; i < paramArrayOfPoint.length; i++) if (paramPoint.equals(paramArrayOfPoint[i])) return true;
/*  542 */     return false;
/*      */   }
/*      */   
/*  545 */   public static boolean member(String paramString, String[] paramArrayOfString) { if (paramArrayOfString == null) return false;
/*  546 */     for (int i = 0; i < paramArrayOfString.length; i++) if (paramString.equals(paramArrayOfString[i])) return true;
/*  547 */     return false;
/*      */   }
/*      */   
/*      */ 
/*      */   public static boolean member(int[] paramArrayOfInt, int[][] paramArrayOfInt1)
/*      */   {
/*  553 */     if (paramArrayOfInt1 == null) return false;
/*  554 */     if (paramArrayOfInt == null) return true;
/*  555 */     for (int i = 0; i < paramArrayOfInt1.length; i++) if (isEqualSet(paramArrayOfInt, paramArrayOfInt1[i])) return true;
/*  556 */     return false;
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
/*      */   public static boolean member(int[][] paramArrayOfInt, int[][][] paramArrayOfInt1)
/*      */   {
/*  576 */     if (paramArrayOfInt1 == null) return false;
/*  577 */     if (paramArrayOfInt == null) { return true;
/*      */     }
/*  579 */     for (int i = 0; i < paramArrayOfInt1.length; i++)
/*  580 */       if (paramArrayOfInt1[i].length == paramArrayOfInt.length) {
/*  581 */         boolean[] arrayOfBoolean1 = new boolean[paramArrayOfInt.length];
/*  582 */         boolean[] arrayOfBoolean2 = new boolean[paramArrayOfInt.length];
/*  583 */         int j = 1;
/*  584 */         for (int k = 0; k < paramArrayOfInt.length; k++) {
/*  585 */           for (int m = 0; m < paramArrayOfInt1[i].length; m++) {
/*  586 */             if ((arrayOfBoolean2[m] == 0) && 
/*  587 */               (isEqualSet(paramArrayOfInt[k], paramArrayOfInt1[i][m]))) {
/*  588 */               arrayOfBoolean1[k] = true;arrayOfBoolean2[m] = true; break;
/*      */             }
/*      */           }
/*  591 */           if (arrayOfBoolean1[k] == 0) {
/*  592 */             j = 0; break;
/*      */           }
/*      */         }
/*  595 */         if (j != 0) return true;
/*      */       }
/*  597 */     return false;
/*      */   }
/*      */   
/*  600 */   public static boolean member(String[] paramArrayOfString, String[][] paramArrayOfString1) { if (paramArrayOfString1 == null) return false;
/*  601 */     if (paramArrayOfString == null) return true;
/*  602 */     for (int i = 0; i < paramArrayOfString1.length; i++) if (isEqualSet(paramArrayOfString, paramArrayOfString1[i])) return true;
/*  603 */     return false;
/*      */   }
/*      */   
/*      */   public static boolean isSubset(String[] paramArrayOfString1, String[] paramArrayOfString2)
/*      */   {
/*  608 */     if (paramArrayOfString1 == null) return true;
/*  609 */     if (paramArrayOfString2 == null) { return false;
/*      */     }
/*  611 */     for (int i = 0; i < paramArrayOfString1.length; i++) {
/*  612 */       if (!member(paramArrayOfString1[i], paramArrayOfString2)) return false;
/*      */     }
/*  614 */     return true;
/*      */   }
/*      */   
/*      */ 
/*      */   public static boolean isEqualSet(String[] paramArrayOfString1, String[] paramArrayOfString2)
/*      */   {
/*  620 */     if ((isSubset(paramArrayOfString1, paramArrayOfString2)) && (isSubset(paramArrayOfString2, paramArrayOfString1))) return true;
/*  621 */     return false;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static Point[] union(Point[] paramArrayOfPoint1, Point[] paramArrayOfPoint2)
/*      */   {
/*  631 */     if ((paramArrayOfPoint1 == null) && (paramArrayOfPoint2 == null)) { return null;
/*      */     }
/*  633 */     if ((paramArrayOfPoint1 == null) && (paramArrayOfPoint2 != null)) {
/*  634 */       j = paramArrayOfPoint2.length;
/*  635 */       arrayOfPoint1 = new Point[j];
/*  636 */       for (k = 0; k < j; k++) arrayOfPoint1[k] = paramArrayOfPoint2[k];
/*  637 */       return arrayOfPoint1;
/*      */     }
/*      */     
/*  640 */     if ((paramArrayOfPoint1 != null) && (paramArrayOfPoint2 == null)) {
/*  641 */       i = paramArrayOfPoint1.length;
/*  642 */       arrayOfPoint1 = new Point[i];
/*  643 */       for (k = 0; k < i; k++) arrayOfPoint1[k] = paramArrayOfPoint1[k];
/*  644 */       return arrayOfPoint1;
/*      */     }
/*      */     
/*  647 */     int i = paramArrayOfPoint1.length;
/*  648 */     int j = paramArrayOfPoint2.length;
/*      */     
/*  650 */     Point[] arrayOfPoint1 = new Point[j];
/*  651 */     int k = 0;
/*  652 */     for (int m = 0; m < j; m++) {
/*  653 */       if (!member(paramArrayOfPoint2[m], paramArrayOfPoint1)) arrayOfPoint1[(k++)] = paramArrayOfPoint2[m];
/*      */     }
/*  655 */     Point[] arrayOfPoint2 = new Point[i + k];
/*  656 */     for (int n = 0; n < i; n++) arrayOfPoint2[n] = paramArrayOfPoint1[n];
/*  657 */     for (n = 0; n < k; n++) { arrayOfPoint2[(n + i)] = arrayOfPoint1[n];
/*      */     }
/*  659 */     return arrayOfPoint2;
/*      */   }
/*      */   
/*      */   public static String[] union(String[] paramArrayOfString1, String[] paramArrayOfString2)
/*      */   {
/*  664 */     if ((paramArrayOfString1 == null) && (paramArrayOfString2 == null)) { return null;
/*      */     }
/*  666 */     if ((paramArrayOfString1 == null) && (paramArrayOfString2 != null)) {
/*  667 */       j = paramArrayOfString2.length;
/*  668 */       arrayOfString1 = new String[j];
/*  669 */       for (k = 0; k < j; k++) arrayOfString1[k] = new String(paramArrayOfString2[k]);
/*  670 */       return arrayOfString1;
/*      */     }
/*      */     
/*  673 */     if ((paramArrayOfString1 != null) && (paramArrayOfString2 == null)) {
/*  674 */       i = paramArrayOfString1.length;
/*  675 */       arrayOfString1 = new String[i];
/*  676 */       for (k = 0; k < i; k++) arrayOfString1[k] = new String(paramArrayOfString1[k]);
/*  677 */       return arrayOfString1;
/*      */     }
/*      */     
/*  680 */     int i = paramArrayOfString1.length;
/*  681 */     int j = paramArrayOfString2.length;
/*      */     
/*  683 */     String[] arrayOfString1 = new String[j];
/*  684 */     int k = 0;
/*  685 */     for (int m = 0; m < j; m++) {
/*  686 */       if (!member(paramArrayOfString2[m], paramArrayOfString1)) arrayOfString1[(k++)] = new String(paramArrayOfString2[m]);
/*      */     }
/*  688 */     String[] arrayOfString2 = new String[i + k];
/*  689 */     for (int n = 0; n < i; n++) arrayOfString2[n] = new String(paramArrayOfString1[n]);
/*  690 */     for (n = 0; n < k; n++) { arrayOfString2[(n + i)] = new String(arrayOfString1[n]);
/*      */     }
/*  692 */     return arrayOfString2;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static int[][] union(int[][] paramArrayOfInt1, int[][] paramArrayOfInt2)
/*      */   {
/*  704 */     if ((paramArrayOfInt1 == null) && (paramArrayOfInt2 == null)) return (int[][])null;
/*  705 */     if ((paramArrayOfInt1 == null) && (paramArrayOfInt2 != null)) return UTIL.getDuplicate(paramArrayOfInt2);
/*  706 */     if ((paramArrayOfInt2 == null) && (paramArrayOfInt1 != null)) { return UTIL.getDuplicate(paramArrayOfInt1);
/*      */     }
/*  708 */     int i = paramArrayOfInt1[0].length;
/*  709 */     int j = paramArrayOfInt2[0].length;
/*  710 */     int[][] arrayOfInt1 = new int[2][j];
/*  711 */     int k = 0;
/*  712 */     for (int m = 0; m < j; m++) {
/*  713 */       if (!member(paramArrayOfInt2[0][m], paramArrayOfInt1[0])) {
/*  714 */         arrayOfInt1[0][k] = paramArrayOfInt2[0][m];
/*  715 */         arrayOfInt1[1][(k++)] = paramArrayOfInt2[1][m];
/*      */       }
/*      */     }
/*      */     
/*  719 */     int[][] arrayOfInt2 = new int[2][i + k];
/*  720 */     for (int n = 0; n < i; n++) {
/*  721 */       arrayOfInt2[0][n] = paramArrayOfInt1[0][n];arrayOfInt2[1][n] = paramArrayOfInt1[1][n];
/*      */     }
/*  723 */     for (n = 0; n < k; n++) {
/*  724 */       arrayOfInt2[0][(n + i)] = arrayOfInt1[0][n];arrayOfInt2[1][(n + i)] = arrayOfInt1[1][n];
/*      */     }
/*  726 */     return arrayOfInt2;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static int[] appendMember(int paramInt, int[] paramArrayOfInt)
/*      */   {
/*  738 */     if (member(paramInt, paramArrayOfInt)) { return paramArrayOfInt;
/*      */     }
/*      */     int i;
/*  741 */     if (paramArrayOfInt == null) i = 0; else
/*  742 */       i = paramArrayOfInt.length;
/*  743 */     int[] arrayOfInt = new int[i + 1];
/*  744 */     for (int j = 0; j < i; j++) arrayOfInt[j] = paramArrayOfInt[j];
/*  745 */     arrayOfInt[i] = paramInt;
/*      */     
/*  747 */     return arrayOfInt;
/*      */   }
/*      */   
/*      */   public static int[][] appendMember(int[] paramArrayOfInt, int[][] paramArrayOfInt1) {
/*  751 */     if (paramArrayOfInt == null) { return UTIL.getDuplicate(paramArrayOfInt1);
/*      */     }
/*      */     int i;
/*  754 */     if (paramArrayOfInt1 == null) { i = 0;
/*      */     } else {
/*  756 */       if (member(paramArrayOfInt, paramArrayOfInt1)) return UTIL.getDuplicate(paramArrayOfInt1);
/*  757 */       i = paramArrayOfInt1.length;
/*      */     }
/*  759 */     int[][] arrayOfInt = new int[i + 1][];
/*  760 */     for (int j = 0; j < i; j++) arrayOfInt[j] = UTIL.getDuplicate(paramArrayOfInt1[j]);
/*  761 */     arrayOfInt[i] = UTIL.getDuplicate(paramArrayOfInt);
/*  762 */     return arrayOfInt;
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
/*      */   public static Point[] appendMember(Point paramPoint, Point[] paramArrayOfPoint)
/*      */   {
/*  785 */     if (member(paramPoint, paramArrayOfPoint)) { return paramArrayOfPoint;
/*      */     }
/*      */     int i;
/*  788 */     if (paramArrayOfPoint == null) i = 0; else
/*  789 */       i = paramArrayOfPoint.length;
/*  790 */     Point[] arrayOfPoint = new Point[i + 1];
/*  791 */     for (int j = 0; j < i; j++) arrayOfPoint[j] = paramArrayOfPoint[j];
/*  792 */     arrayOfPoint[i] = paramPoint;
/*      */     
/*  794 */     return arrayOfPoint;
/*      */   }
/*      */   
/*  797 */   public static String[] appendMember(String paramString, String[] paramArrayOfString) { if (member(paramString, paramArrayOfString)) { return paramArrayOfString;
/*      */     }
/*      */     int i;
/*  800 */     if (paramArrayOfString == null) i = 0; else
/*  801 */       i = paramArrayOfString.length;
/*  802 */     String[] arrayOfString = new String[i + 1];
/*  803 */     for (int j = 0; j < i; j++) arrayOfString[j] = new String(paramArrayOfString[j]);
/*  804 */     arrayOfString[i] = new String(paramString);
/*      */     
/*  806 */     return arrayOfString;
/*      */   }
/*      */   
/*      */   public static String[][] appendMember(String[] paramArrayOfString, String[][] paramArrayOfString1) {
/*  810 */     if (paramArrayOfString == null) { return UTIL.getDuplicate(paramArrayOfString1);
/*      */     }
/*      */     int i;
/*  813 */     if (paramArrayOfString1 == null) { i = 0;
/*      */     } else {
/*  815 */       if (member(paramArrayOfString, paramArrayOfString1)) return UTIL.getDuplicate(paramArrayOfString1);
/*  816 */       i = paramArrayOfString1.length;
/*      */     }
/*  818 */     String[][] arrayOfString = new String[i + 1][];
/*  819 */     for (int j = 0; j < i; j++) arrayOfString[j] = UTIL.getDuplicate(paramArrayOfString1[j]);
/*  820 */     arrayOfString[i] = UTIL.getDuplicate(paramArrayOfString);
/*  821 */     return arrayOfString;
/*      */   }
/*      */   
/*      */   public static Point[] delMember(Point paramPoint, Point[] paramArrayOfPoint)
/*      */   {
/*  826 */     if (!member(paramPoint, paramArrayOfPoint)) {
/*  827 */       HelpPanel.showError("Cannot delete (" + paramPoint.x + "," + paramPoint.y + ") from ");
/*  828 */       HelpPanel.appendList(" ", paramArrayOfPoint);
/*  829 */       return paramArrayOfPoint;
/*      */     }
/*  831 */     if (paramArrayOfPoint.length == 1) { return null;
/*      */     }
/*  833 */     Point[] arrayOfPoint = new Point[paramArrayOfPoint.length - 1];
/*  834 */     int i = 0;
/*  835 */     for (int j = 0; j < paramArrayOfPoint.length; j++)
/*  836 */       if (!paramArrayOfPoint[j].equals(paramPoint)) arrayOfPoint[(i++)] = new Point(paramArrayOfPoint[j]);
/*  837 */     return arrayOfPoint;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public static int[][] delMember(int[][] paramArrayOfInt, int[] paramArrayOfInt1)
/*      */   {
/*  845 */     if (paramArrayOfInt == null) return (int[][])null;
/*  846 */     if (paramArrayOfInt1 == null) { return paramArrayOfInt;
/*      */     }
/*  848 */     int i = paramArrayOfInt.length - paramArrayOfInt1.length;
/*  849 */     if (i == 0) { return (int[][])null;
/*      */     }
/*  851 */     int[][] arrayOfInt = new int[i][];
/*  852 */     int j = 0;
/*  853 */     for (int k = 0; k < paramArrayOfInt.length; k++)
/*  854 */       if (!member(k, paramArrayOfInt1)) arrayOfInt[(j++)] = paramArrayOfInt[k];
/*  855 */     return arrayOfInt;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public static int makePotSize(int[] paramArrayOfInt)
/*      */   {
/*  862 */     int i = 1;
/*  863 */     for (int j = 0; j < paramArrayOfInt.length; j++) i *= paramArrayOfInt[j];
/*  864 */     return i;
/*      */   }
/*      */   
/*      */   public static float[] normalize(float[] paramArrayOfFloat)
/*      */   {
/*  869 */     int i = paramArrayOfFloat.length;
/*  870 */     float[] arrayOfFloat = new float[i];
/*  871 */     for (int j = 0; j < i; j++) { arrayOfFloat[j] = paramArrayOfFloat[j];
/*      */     }
/*  873 */     j = arrayOfFloat.length;
/*  874 */     float f = 0.0F;
/*  875 */     for (int k = 0; k < j; k++) f += arrayOfFloat[k];
/*  876 */     for (k = 0; k < j; k++) arrayOfFloat[k] /= f;
/*  877 */     return arrayOfFloat;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public static float[] normalize(float[] paramArrayOfFloat, int paramInt)
/*      */   {
/*  884 */     int i = paramArrayOfFloat.length;
/*  885 */     float[] arrayOfFloat = new float[i];
/*  886 */     for (int j = 0; j < i; j++) { arrayOfFloat[j] = paramArrayOfFloat[j];
/*      */     }
/*  888 */     j = 0;
/*  889 */     while (j < arrayOfFloat.length) {
/*  890 */       float f = 0.0F;
/*  891 */       for (int k = j; k < j + paramInt; k++) f += arrayOfFloat[k];
/*  892 */       for (k = j; k < j + paramInt; k++) arrayOfFloat[k] /= f;
/*  893 */       j += paramInt;
/*      */     }
/*  895 */     return arrayOfFloat;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static float[] reorderBelief(int[] paramArrayOfInt1, int[] paramArrayOfInt2, float[] paramArrayOfFloat, int[] paramArrayOfInt3)
/*      */   {
/*  907 */     int i = paramArrayOfInt1.length;
/*  908 */     int[] arrayOfInt1 = new int[i];
/*  909 */     for (int j = 0; j < i; j++) {
/*  910 */       k = 0;
/*  911 */       while (paramArrayOfInt1[k] != paramArrayOfInt3[j]) k++;
/*  912 */       arrayOfInt1[j] = k;
/*      */     }
/*      */     
/*  915 */     int[] arrayOfInt2 = new int[i];
/*  916 */     for (int k = 0; k < i; k++) { arrayOfInt2[k] = paramArrayOfInt2[arrayOfInt1[k]];
/*      */     }
/*  918 */     k = paramArrayOfFloat.length;
/*  919 */     float[] arrayOfFloat = new float[k];
/*  920 */     int[] arrayOfInt3 = new int[i];
/*  921 */     for (int m = 0; m < k; m++) {
/*  922 */       int[] arrayOfInt4 = decToMix(m, paramArrayOfInt2);
/*  923 */       for (int n = 0; n < i; n++) arrayOfInt3[n] = arrayOfInt4[arrayOfInt1[n]];
/*  924 */       n = mixToDec(arrayOfInt3, arrayOfInt2);
/*  925 */       arrayOfFloat[n] = paramArrayOfFloat[m];
/*      */     }
/*  927 */     return arrayOfFloat;
/*      */   }
/*      */   
/*      */   public static float[] reorderPot(int[] paramArrayOfInt1, int[] paramArrayOfInt2, float[] paramArrayOfFloat, int[] paramArrayOfInt3) {
/*  931 */     return reorderBelief(paramArrayOfInt1, paramArrayOfInt2, paramArrayOfFloat, paramArrayOfInt3);
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
/*      */   public static float[] getMaxMargin(int[] paramArrayOfInt1, int[] paramArrayOfInt2, float[] paramArrayOfFloat, int[] paramArrayOfInt3)
/*      */   {
/*  961 */     int i = paramArrayOfInt1.length;
/*  962 */     int j = paramArrayOfInt3.length;
/*  963 */     if (i == j) { return reorderPot(paramArrayOfInt1, paramArrayOfInt2, paramArrayOfFloat, paramArrayOfInt3);
/*      */     }
/*  965 */     int[] arrayOfInt1 = new int[i];
/*  966 */     for (int k = 0; k < j; k++) arrayOfInt1[k] = paramArrayOfInt3[k];
/*  967 */     int[] arrayOfInt2 = setDifference(paramArrayOfInt1, paramArrayOfInt3);
/*  968 */     int m = arrayOfInt2.length;
/*  969 */     for (int n = 0; n < m; n++) { arrayOfInt1[(n + j)] = arrayOfInt2[n];
/*      */     }
/*  971 */     int[] arrayOfInt3 = new int[i];
/*  972 */     for (int i1 = 0; i1 < i; i1++) {
/*  973 */       arrayOfInt3[i1] = paramArrayOfInt2[UTIL.getArrayIndex(arrayOfInt1[i1], paramArrayOfInt1)];
/*      */     }
/*      */     
/*  976 */     float[] arrayOfFloat1 = reorderPot(paramArrayOfInt1, paramArrayOfInt2, paramArrayOfFloat, arrayOfInt1);
/*      */     
/*      */ 
/*      */ 
/*  980 */     int i2 = 1;
/*  981 */     for (int i3 = 0; i3 < j; i3++) i2 *= arrayOfInt3[i3];
/*  982 */     float[] arrayOfFloat2 = new float[i2];
/*      */     
/*  984 */     int i4 = 1;
/*  985 */     for (int i5 = 0; i5 < m; i5++) { i4 *= arrayOfInt3[(i5 + j)];
/*      */     }
/*  987 */     i5 = 0;
/*  988 */     for (int i6 = 0; i6 < i2; i6++) {
/*  989 */       arrayOfFloat2[i6] = -1.0F;
/*  990 */       for (int i7 = 0; i7 < i4; i7++) {
/*  991 */         if (arrayOfFloat1[i5] > arrayOfFloat2[i6]) arrayOfFloat2[i6] = arrayOfFloat1[i5];
/*  992 */         i5++;
/*      */       }
/*      */     }
/*  995 */     return arrayOfFloat2;
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
/*      */ 
/*      */ 
/*      */ 
/*      */   public static float[] addMargin(int[] paramArrayOfInt1, int[] paramArrayOfInt2, float[] paramArrayOfFloat1, int[] paramArrayOfInt3, float[] paramArrayOfFloat2)
/*      */   {
/* 1031 */     int i = paramArrayOfInt1.length;
/* 1032 */     int j = paramArrayOfInt3.length;
/* 1033 */     if (i == j) {
/* 1034 */       arrayOfInt1 = new int[i];
/* 1035 */       for (int k = 0; k < i; k++) arrayOfInt1[k] = paramArrayOfInt2[UTIL.getArrayIndex(paramArrayOfInt3[k], paramArrayOfInt1)];
/* 1036 */       float[] arrayOfFloat1 = reorderPot(paramArrayOfInt3, arrayOfInt1, paramArrayOfFloat2, paramArrayOfInt1);
/* 1037 */       n = paramArrayOfFloat1.length;
/* 1038 */       for (i1 = 0; i1 < n; i1++) arrayOfFloat1[i1] += paramArrayOfFloat1[i1];
/* 1039 */       return arrayOfFloat1;
/*      */     }
/*      */     
/* 1042 */     int[] arrayOfInt1 = new int[i];
/* 1043 */     for (int m = 0; m < j; m++) arrayOfInt1[m] = paramArrayOfInt3[m];
/* 1044 */     int[] arrayOfInt2 = setDifference(paramArrayOfInt1, paramArrayOfInt3);
/* 1045 */     int n = arrayOfInt2.length;
/* 1046 */     for (int i1 = 0; i1 < n; i1++) { arrayOfInt1[(i1 + j)] = arrayOfInt2[i1];
/*      */     }
/* 1048 */     float[] arrayOfFloat2 = reorderPot(paramArrayOfInt1, paramArrayOfInt2, paramArrayOfFloat1, arrayOfInt1);
/*      */     
/*      */ 
/*      */ 
/* 1052 */     int[] arrayOfInt3 = new int[i];
/* 1053 */     for (int i2 = 0; i2 < i; i2++) { arrayOfInt3[i2] = paramArrayOfInt2[UTIL.getArrayIndex(arrayOfInt1[i2], paramArrayOfInt1)];
/*      */     }
/* 1055 */     i2 = 1;
/* 1056 */     for (int i3 = 0; i3 < n; i3++) { i2 *= arrayOfInt3[(i3 + j)];
/*      */     }
/* 1058 */     i3 = paramArrayOfFloat2.length;
/* 1059 */     for (int i4 = 0; i4 < i3; i4++)
/* 1060 */       for (int i5 = 0; i5 < i2; i5++) arrayOfFloat2[(i4 * i2 + i5)] += paramArrayOfFloat2[i4];
/* 1061 */     return reorderPot(arrayOfInt1, arrayOfInt3, arrayOfFloat2, paramArrayOfInt1);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static float[] product(int[] paramArrayOfInt1, int[] paramArrayOfInt2, int[][] paramArrayOfInt, float[][] paramArrayOfFloat)
/*      */   {
/* 1127 */     int i = paramArrayOfFloat.length;
/* 1128 */     int[] arrayOfInt1 = new int[i];
/* 1129 */     for (int j = 0; j < i; j++) arrayOfInt1[j] = paramArrayOfInt[j].length;
/* 1130 */     j = paramArrayOfInt1.length;
/* 1131 */     int k = 1;
/* 1132 */     for (int m = 0; m < j; m++) k *= paramArrayOfInt2[m];
/* 1133 */     float[] arrayOfFloat = new float[k];
/*      */     
/* 1135 */     for (int n = 0; n < k; n++) {
/* 1136 */       arrayOfFloat[n] = 1.0F;
/* 1137 */       int[] arrayOfInt2 = decToMix(n, paramArrayOfInt2);
/*      */       
/* 1139 */       for (int i1 = 0; i1 < i; i1++) {
/* 1140 */         int[][] arrayOfInt = projectMix(paramArrayOfInt1, arrayOfInt2, paramArrayOfInt2, paramArrayOfInt[i1]);
/* 1141 */         int i2 = mixToDec(arrayOfInt[0], arrayOfInt[1]);
/*      */         
/*      */ 
/* 1144 */         arrayOfFloat[n] *= paramArrayOfFloat[i1][i2];
/*      */       }
/*      */     }
/* 1147 */     return arrayOfFloat;
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
/*      */   public static float[] productOfTwo(int[] paramArrayOfInt1, float[] paramArrayOfFloat1, int[] paramArrayOfInt2, float[] paramArrayOfFloat2, int[] paramArrayOfInt3, int[] paramArrayOfInt4)
/*      */   {
/* 1163 */     int[][] arrayOfInt = new int[2][];
/* 1164 */     arrayOfInt[0] = paramArrayOfInt1;
/* 1165 */     arrayOfInt[1] = paramArrayOfInt2;
/*      */     
/* 1167 */     float[][] arrayOfFloat = new float[2][];
/* 1168 */     arrayOfFloat[0] = paramArrayOfFloat1;
/* 1169 */     arrayOfFloat[1] = paramArrayOfFloat2;
/*      */     
/* 1171 */     return product(paramArrayOfInt3, paramArrayOfInt4, arrayOfInt, arrayOfFloat);
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
/*      */   public static float[] division(int[] paramArrayOfInt1, float[] paramArrayOfFloat1, int[] paramArrayOfInt2, float[] paramArrayOfFloat2, int[] paramArrayOfInt3, int[] paramArrayOfInt4)
/*      */   {
/* 1186 */     float[] arrayOfFloat = new float[paramArrayOfFloat2.length];
/* 1187 */     for (int i = 0; i < paramArrayOfFloat2.length; i++) arrayOfFloat[i] = (paramArrayOfFloat2[i] == 0.0F ? 0.0F : 1.0F / paramArrayOfFloat2[i]);
/* 1188 */     return productOfTwo(paramArrayOfInt1, paramArrayOfFloat1, paramArrayOfInt2, arrayOfFloat, paramArrayOfInt3, paramArrayOfInt4);
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
/*      */   public static float[] margin(float[] paramArrayOfFloat, int[] paramArrayOfInt1, int[] paramArrayOfInt2)
/*      */   {
/* 1205 */     int i = paramArrayOfInt1.length;
/* 1206 */     int[][] arrayOfInt = new int[2][i];
/* 1207 */     for (int j = 0; j < i; j++) {
/* 1208 */       if (member(j, paramArrayOfInt2)) arrayOfInt[0][j] = 1; else
/* 1209 */         arrayOfInt[0][j] = 0;
/* 1210 */       arrayOfInt[1][j] = 0;
/*      */     }
/*      */     
/* 1213 */     j = 1;
/* 1214 */     for (int k = 0; k < i; k++) if (member(k, paramArrayOfInt2)) j *= paramArrayOfInt1[k];
/* 1215 */     float[] arrayOfFloat = new float[j];
/*      */     
/* 1217 */     int m = 0;
/* 1218 */     float f = 0.0F;
/* 1219 */     for (int n = 0; n < paramArrayOfFloat.length; n++) {
/* 1220 */       int i1 = mixToDec(arrayOfInt[1], paramArrayOfInt1);
/* 1221 */       f += paramArrayOfFloat[i1];
/* 1222 */       int i2 = incMix(0, arrayOfInt, paramArrayOfInt1);
/* 1223 */       if (i2 == 1) {
/* 1224 */         arrayOfFloat[(m++)] = f;
/* 1225 */         incMix(1, arrayOfInt, paramArrayOfInt1);
/* 1226 */         f = 0.0F;
/*      */       }
/*      */     }
/* 1229 */     return arrayOfFloat;
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
/*      */   public static float[] fmargin(String paramString, int paramInt, int[] paramArrayOfInt1, int[] paramArrayOfInt2)
/*      */   {
/* 1257 */     int i = paramArrayOfInt2.length;
/* 1258 */     int[] arrayOfInt1 = new int[i];
/* 1259 */     for (int j = 0; j < i; j++) { arrayOfInt1[j] = paramArrayOfInt1[paramArrayOfInt2[j]];
/*      */     }
/* 1261 */     j = 1;
/* 1262 */     for (int k = 0; k < i; k++) j *= arrayOfInt1[k];
/* 1263 */     float[] arrayOfFloat = new float[j];
/*      */     
/* 1265 */     int m = 32000;
/*      */     try {
/* 1267 */       BufferedReader localBufferedReader = new BufferedReader(new FileReader(paramString), m);
/* 1268 */       String str; while ((str = localBufferedReader.readLine()) != null) {
/* 1269 */         StringTokenizer localStringTokenizer = new StringTokenizer(str);
/*      */         
/* 1271 */         int[] arrayOfInt2 = chToMix(new String(localStringTokenizer.nextToken()), paramArrayOfInt2);
/* 1272 */         int i1 = mixToDec(arrayOfInt2, arrayOfInt1);
/* 1273 */         arrayOfFloat[i1] += Integer.parseInt(localStringTokenizer.nextToken());
/*      */       }
/* 1275 */       localBufferedReader.close();
/*      */     } catch (IOException localIOException) {
/* 1277 */       HelpPanel.showError("Unable to read pre file!");
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1284 */     float f = paramInt;
/* 1285 */     for (int n = 0; n < j; n++) arrayOfFloat[n] /= f;
/* 1286 */     return arrayOfFloat;
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
/*      */   public static float[][] calibration(float[] paramArrayOfFloat1, int[] paramArrayOfInt1, int[] paramArrayOfInt2, float[] paramArrayOfFloat2, int[] paramArrayOfInt3, int[] paramArrayOfInt4, float[] paramArrayOfFloat3, int[] paramArrayOfInt5)
/*      */   {
/* 1318 */     int[][] arrayOfInt = new int[3][];
/* 1319 */     float[][] arrayOfFloat1 = new float[3][];
/*      */     
/* 1321 */     arrayOfInt[0] = paramArrayOfInt1;
/* 1322 */     arrayOfFloat1[0] = paramArrayOfFloat1;
/*      */     
/* 1324 */     int i = 0;
/* 1325 */     arrayOfInt[1] = new int[paramArrayOfInt5.length];
/* 1326 */     int[] arrayOfInt1 = new int[paramArrayOfInt5.length];
/* 1327 */     for (int j = 0; j < paramArrayOfInt3.length; j++) {
/* 1328 */       if (member(paramArrayOfInt3[j], paramArrayOfInt5)) {
/* 1329 */         arrayOfInt[1][i] = paramArrayOfInt3[j];
/* 1330 */         arrayOfInt1[(i++)] = j;
/*      */       }
/*      */     }
/*      */     
/* 1334 */     arrayOfFloat1[1] = margin(paramArrayOfFloat2, paramArrayOfInt4, arrayOfInt1);
/*      */     
/* 1336 */     arrayOfInt[2] = paramArrayOfInt5;
/* 1337 */     float[] arrayOfFloat = new float[paramArrayOfFloat3.length];
/* 1338 */     for (int k = 0; k < paramArrayOfFloat3.length; k++) {
/* 1339 */       if (paramArrayOfFloat3[k] == 0.0F) arrayOfFloat[k] = 0.0F; else
/* 1340 */         arrayOfFloat[k] = (1.0F / paramArrayOfFloat3[k]);
/*      */     }
/* 1342 */     arrayOfFloat1[2] = arrayOfFloat;
/*      */     
/* 1344 */     float[][] arrayOfFloat2 = new float[2][];
/* 1345 */     arrayOfFloat2[0] = product(paramArrayOfInt1, paramArrayOfInt2, arrayOfInt, arrayOfFloat1);
/* 1346 */     arrayOfFloat2[1] = arrayOfFloat1[1];
/* 1347 */     return arrayOfFloat2;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static float[] calibration(float[] paramArrayOfFloat1, int[] paramArrayOfInt1, int[] paramArrayOfInt2, float[] paramArrayOfFloat2, int[] paramArrayOfInt3)
/*      */   {
/* 1357 */     int[][] arrayOfInt = new int[3][];
/* 1358 */     float[][] arrayOfFloat = new float[3][];
/*      */     
/* 1360 */     arrayOfInt[0] = paramArrayOfInt1;
/* 1361 */     arrayOfFloat[0] = paramArrayOfFloat1;
/* 1362 */     arrayOfInt[1] = paramArrayOfInt3;
/* 1363 */     arrayOfFloat[1] = paramArrayOfFloat2;
/*      */     
/* 1365 */     arrayOfInt[2] = paramArrayOfInt3;
/*      */     
/* 1367 */     int i = 0;
/* 1368 */     int[] arrayOfInt1 = new int[paramArrayOfInt3.length];
/* 1369 */     for (int j = 0; j < paramArrayOfInt1.length; j++) {
/* 1370 */       if (member(paramArrayOfInt1[j], paramArrayOfInt3)) arrayOfInt1[(i++)] = j;
/*      */     }
/* 1372 */     arrayOfFloat[2] = margin(paramArrayOfFloat1, paramArrayOfInt2, arrayOfInt1);
/*      */     
/* 1374 */     float[] arrayOfFloat1 = new float[arrayOfFloat[2].length];
/* 1375 */     for (int k = 0; k < arrayOfFloat[2].length; k++) {
/* 1376 */       if (arrayOfFloat[2][k] == 0.0F) arrayOfFloat1[k] = 0.0F; else
/* 1377 */         arrayOfFloat1[k] = (1.0F / arrayOfFloat[2][k]);
/*      */     }
/* 1379 */     arrayOfFloat[2] = arrayOfFloat1;
/*      */     
/* 1381 */     return product(paramArrayOfInt1, paramArrayOfInt2, arrayOfInt, arrayOfFloat);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public static float getEntropyByJpd(float[] paramArrayOfFloat)
/*      */   {
/* 1388 */     float f = 0.0F;
/* 1389 */     for (int i = 0; i < paramArrayOfFloat.length; i++) {
/* 1390 */       if (paramArrayOfFloat[i] > 4.0E-38D) {
/* 1391 */         f -= paramArrayOfFloat[i] * (float)Math.log(paramArrayOfFloat[i]);
/*      */       }
/*      */     }
/* 1394 */     return f * 1.4427F;
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
/*      */   public static float getKLDist(float[] paramArrayOfFloat1, float[] paramArrayOfFloat2)
/*      */   {
/* 1411 */     float f = 0.0F;
/* 1412 */     for (int i = 0; i < paramArrayOfFloat1.length; i++) {
/* 1413 */       if ((paramArrayOfFloat2[i] == 0.0F) && (paramArrayOfFloat1[i] > 0.0F)) {
/* 1414 */         System.out.println("Infinite KL distance!");System.exit(0);
/*      */       }
/* 1416 */       else if (paramArrayOfFloat1[i] > 0.0F) {
/* 1417 */         f = (float)(f + paramArrayOfFloat1[i] * Math.log(paramArrayOfFloat1[i] / paramArrayOfFloat2[i]));
/*      */       }
/*      */     }
/* 1420 */     return f;
/*      */   }
/*      */   
/*      */ 
/*      */   public static float getEuclDist(float[] paramArrayOfFloat1, float[] paramArrayOfFloat2)
/*      */   {
/* 1426 */     float f1 = 0.0F;
/* 1427 */     for (int i = 0; i < paramArrayOfFloat1.length; i++) {
/* 1428 */       float f2 = paramArrayOfFloat1[i] - paramArrayOfFloat2[i];
/* 1429 */       f1 += f2 * f2;
/*      */     }
/* 1431 */     return f1;
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
/*      */   public static boolean isCondIndp(Point paramPoint, int[] paramArrayOfInt1, String paramString, int paramInt, int[] paramArrayOfInt2, float paramFloat)
/*      */   {
/* 1445 */     int[] arrayOfInt1 = appendMember(paramPoint.y, appendMember(paramPoint.x, paramArrayOfInt1));
/* 1446 */     float[] arrayOfFloat1 = fmargin(paramString, paramInt, paramArrayOfInt2, arrayOfInt1);
/* 1447 */     float f1 = getEntropyByJpd(arrayOfFloat1);
/*      */     
/* 1449 */     int[] arrayOfInt2 = new int[arrayOfInt1.length];
/* 1450 */     for (int i = 0; i < arrayOfInt1.length; i++) { arrayOfInt2[i] = paramArrayOfInt2[arrayOfInt1[i]];
/*      */     }
/* 1452 */     i = paramArrayOfInt1.length;
/* 1453 */     int[] arrayOfInt3 = new int[i + 1];
/* 1454 */     for (int j = 0; j <= i; j++) arrayOfInt3[j] = j;
/* 1455 */     float[] arrayOfFloat2 = margin(arrayOfFloat1, arrayOfInt2, arrayOfInt3);
/*      */     
/* 1457 */     int[] arrayOfInt4 = new int[i + 1];
/* 1458 */     for (int k = 0; k < i; k++) arrayOfInt4[k] = k;
/* 1459 */     arrayOfInt4[i] = (i + 1);
/* 1460 */     float[] arrayOfFloat3 = margin(arrayOfFloat1, arrayOfInt2, arrayOfInt4);
/*      */     
/* 1462 */     int[] arrayOfInt5 = new int[i];
/* 1463 */     for (int m = 0; m < i; m++) arrayOfInt5[m] = m;
/* 1464 */     float[] arrayOfFloat4 = margin(arrayOfFloat1, arrayOfInt2, arrayOfInt5);
/*      */     
/* 1466 */     float f2 = getEntropyByJpd(arrayOfFloat2);
/* 1467 */     float f3 = getEntropyByJpd(arrayOfFloat3);
/* 1468 */     float f4 = getEntropyByJpd(arrayOfFloat4);
/* 1469 */     float f5 = f2 + f3 - f4;
/* 1470 */     float f6 = f5 - f1;
/*      */     
/*      */ 
/* 1473 */     return f6 < paramFloat;
/*      */   }
/*      */   
/*      */ 
/*      */   public static boolean isCondIndp(Point paramPoint, String paramString, int paramInt, int[] paramArrayOfInt, float paramFloat)
/*      */   {
/* 1479 */     int[] arrayOfInt1 = { paramPoint.x, paramPoint.y };
/* 1480 */     float[] arrayOfFloat1 = fmargin(paramString, paramInt, paramArrayOfInt, arrayOfInt1);
/* 1481 */     float f1 = getEntropyByJpd(arrayOfFloat1);
/*      */     
/* 1483 */     int[] arrayOfInt2 = new int[2];
/* 1484 */     for (int i = 0; i < 2; i++) { arrayOfInt2[i] = paramArrayOfInt[arrayOfInt1[i]];
/*      */     }
/* 1486 */     int[] arrayOfInt3 = { 0 };
/* 1487 */     float[] arrayOfFloat2 = margin(arrayOfFloat1, arrayOfInt2, arrayOfInt3);
/*      */     
/* 1489 */     int[] arrayOfInt4 = { 1 };
/* 1490 */     float[] arrayOfFloat3 = margin(arrayOfFloat1, arrayOfInt2, arrayOfInt4);
/*      */     
/* 1492 */     float f2 = getEntropyByJpd(arrayOfFloat2);
/* 1493 */     float f3 = getEntropyByJpd(arrayOfFloat3);
/* 1494 */     float f4 = f2 + f3;
/* 1495 */     float f5 = f4 - f1;
/*      */     
/*      */ 
/* 1498 */     return f5 < paramFloat;
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
/*      */   public static long comb(long paramLong1, long paramLong2)
/*      */   {
/* 1526 */     double d = 1.0D;
/* 1527 */     if (paramLong2 > paramLong1 / 2L) paramLong2 = paramLong1 - paramLong2;
/* 1528 */     for (long l = 1L; l <= paramLong2; l += 1L) d *= (paramLong1 - l + 1L) / (paramLong2 - l + 1L);
/* 1529 */     l = Math.floor(d + 0.5D);
/* 1530 */     return l;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static int[] indexToComb(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
/*      */   {
/* 1570 */     int[] arrayOfInt1 = new int[paramInt2];
/* 1571 */     if (paramInt2 == 1) {
/* 1572 */       arrayOfInt1[0] = (paramInt4 + paramInt3);
/* 1573 */       return arrayOfInt1;
/*      */     }
/* 1575 */     int i = 0;
/* 1576 */     int j = paramInt3;
/* 1577 */     for (int k = 1; k <= paramInt1 - paramInt2 + 1; k++) {
/* 1578 */       int m = (int)comb(paramInt1 - k, paramInt2 - 1);
/* 1579 */       if (paramInt3 < i + m) {
/* 1580 */         arrayOfInt1[0] = (paramInt4 + k - 1);
/* 1581 */         int[] arrayOfInt2 = indexToComb(paramInt1 - k, paramInt2 - 1, j, paramInt4 + k);
/* 1582 */         for (int n = 1; n <= paramInt2 - 1; n++) arrayOfInt1[n] = arrayOfInt2[(n - 1)];
/* 1583 */         return arrayOfInt1;
/*      */       }
/*      */       
/* 1586 */       i += m;
/* 1587 */       j -= m;
/*      */     }
/*      */     
/* 1590 */     return null;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static Point indexToPair(int paramInt1, int paramInt2)
/*      */   {
/* 1629 */     if (paramInt2 >= paramInt1 * (paramInt1 - 1) / 2) {
/* 1630 */       HelpPanel.showError("indexTopair: Invalid index.");return null;
/*      */     }
/* 1632 */     int i = paramInt1 - 1;
/* 1633 */     int j = 0;
/* 1634 */     int k = paramInt2 - j;
/*      */     
/* 1636 */     int m = 0;int n = 0;
/* 1637 */     while (k >= i) {
/* 1638 */       m++;
/* 1639 */       j += i;
/* 1640 */       i--;
/* 1641 */       k = paramInt2 - j;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/* 1646 */     n = k + m + 1;
/* 1647 */     return new Point(m, n);
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
/*      */   public static int pairToIndex(int paramInt, Point paramPoint)
/*      */   {
/* 1662 */     if ((paramPoint.x == paramPoint.y) || (paramPoint.x >= paramInt) || (paramPoint.y >= paramInt)) {
/* 1663 */       HelpPanel.showError("pairToIndex: Invalid pair.");return -1;
/*      */     }
/* 1665 */     int i = paramPoint.x;int j = paramPoint.y;
/* 1666 */     if (i > j) {
/* 1667 */       i = paramPoint.y;j = paramPoint.x;
/*      */     }
/* 1669 */     int k = 0;
/* 1670 */     for (int m = 0; m < i; m++)
/* 1671 */       for (int n = m + 1; n < paramInt; n++) k++;
/* 1672 */     k = k - 1 + j - i;
/* 1673 */     return k;
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
/*      */   static void DrPairToIndex(String paramString1, String paramString2, String paramString3)
/*      */   {
/* 1686 */     int i = Integer.parseInt(new StringTokenizer(paramString1).nextToken());
/* 1687 */     int j = Integer.parseInt(new StringTokenizer(paramString2).nextToken());
/* 1688 */     int k = Integer.parseInt(new StringTokenizer(paramString3).nextToken());
/* 1689 */     Point localPoint = new Point(j, k);
/* 1690 */     System.out.print("n=" + i + " x=" + j + " y=" + k);
/* 1691 */     System.out.println(" Index=" + pairToIndex(i, localPoint) + " (starting from 0)");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public static int countBit1(int paramInt1, int paramInt2)
/*      */   {
/* 1699 */     int i = 1;int j = paramInt1;int k = 0;
/* 1700 */     i <<= paramInt2 - 1;
/* 1701 */     for (int m = paramInt2 - 1; m >= 0; m--) {
/* 1702 */       if (j >= i) {
/* 1703 */         k++;
/* 1704 */         j -= i;
/*      */       }
/* 1706 */       i >>= 1;
/*      */     }
/* 1708 */     return k;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static int getRandomInt(Random paramRandom, int paramInt1, int paramInt2)
/*      */   {
/* 1720 */     return (paramRandom.nextInt() & 0x7FFFFFFF) % (paramInt2 + 1 - paramInt1) + paramInt1;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static float getRandomFloat(Random paramRandom, float paramFloat1, float paramFloat2)
/*      */   {
/* 1730 */     return paramRandom.nextFloat() * (paramFloat2 - paramFloat1) + paramFloat1;
/*      */   }
/*      */   
/*      */ 
/*      */   public static Point getRandomPoint(Random paramRandom, int paramInt1, int paramInt2)
/*      */   {
/* 1736 */     return new Point(getRandomInt(paramRandom, paramInt1, paramInt2), getRandomInt(paramRandom, paramInt1, paramInt2));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static int[] getRandomSubset(int[] paramArrayOfInt, int paramInt, Random paramRandom)
/*      */   {
/* 1745 */     int i = paramArrayOfInt.length;
/* 1746 */     boolean[] arrayOfBoolean = new boolean[i];
/* 1747 */     for (int j = 0; j < i; j++) { arrayOfBoolean[j] = false;
/*      */     }
/* 1749 */     int[] arrayOfInt = new int[paramInt];
/*      */     
/* 1751 */     for (int m = 0; m < paramInt; m++) {
/*      */       int k;
/* 1753 */       do { k = (int)(paramRandom.nextFloat() * i);
/* 1754 */       } while (arrayOfBoolean[k] != 0);
/* 1755 */       arrayOfInt[m] = paramArrayOfInt[k];
/* 1756 */       arrayOfBoolean[k] = true;
/*      */     }
/* 1758 */     return arrayOfInt;
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
/*      */   public static int getBinIndex(float paramFloat, float[] paramArrayOfFloat)
/*      */   {
/* 1772 */     if ((paramFloat < 0.0D) || (paramFloat > 1.0D)) {
/* 1773 */       HelpPanel.addHelp("Invalid real.");return -1;
/*      */     }
/* 1775 */     int i = paramArrayOfFloat.length;
/* 1776 */     float f = 0.0F;
/* 1777 */     for (int j = 0; j < i; j++) {
/* 1778 */       f += paramArrayOfFloat[j];
/* 1779 */       if (paramFloat <= f) return j;
/*      */     }
/* 1781 */     HelpPanel.addHelp("Invalid prob table.");
/* 1782 */     return -1;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public static float normal(float paramFloat1, float paramFloat2, float paramFloat3)
/*      */   {
/* 1789 */     double d = paramFloat2 * Math.sqrt(6.283185307179586D);
/* 1790 */     float f1 = paramFloat3 - paramFloat1;
/* 1791 */     float f2 = f1 * f1 / (2.0F * paramFloat2 * paramFloat2);
/* 1792 */     return (float)(Math.exp(-f2) / d);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static float binomial(int paramInt1, float paramFloat, int paramInt2)
/*      */   {
/* 1802 */     long l = comb(paramInt1, paramInt2);
/* 1803 */     return (float)(l * Math.pow(paramFloat, paramInt2) * Math.pow(1.0F - paramFloat, paramInt1 - paramInt2));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public static boolean inRectangle(Point paramPoint1, Point paramPoint2, Point paramPoint3)
/*      */   {
/* 1810 */     if ((Math.abs(paramPoint2.x - paramPoint1.x) <= Math.abs(paramPoint2.x - paramPoint3.x)) && (Math.abs(paramPoint3.x - paramPoint1.x) <= Math.abs(paramPoint2.x - paramPoint3.x)) && (Math.abs(paramPoint2.y - paramPoint1.y) <= Math.abs(paramPoint2.y - paramPoint3.y)) && (Math.abs(paramPoint3.y - paramPoint1.y) <= Math.abs(paramPoint2.y - paramPoint3.y)))
/*      */     {
/*      */ 
/* 1813 */       return true; }
/* 1814 */     return false;
/*      */   }
/*      */   
/*      */   public static boolean onLine(Point paramPoint1, Point paramPoint2, Point paramPoint3)
/*      */   {
/* 1819 */     int i = Math.abs((paramPoint2.x - paramPoint1.x) * (paramPoint3.y - paramPoint1.y) - (paramPoint2.y - paramPoint1.y) * (paramPoint3.x - paramPoint1.x));
/* 1820 */     if (i < 200) return true;
/* 1821 */     return false;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public static int isClose(Point paramPoint1, Point paramPoint2, int paramInt)
/*      */   {
/* 1829 */     int i = (paramPoint1.x - paramPoint2.x) * (paramPoint1.x - paramPoint2.x) + (paramPoint1.y - paramPoint2.y) * (paramPoint1.y - paramPoint2.y);
/* 1830 */     if (i < paramInt * paramInt) return 0;
/* 1831 */     if (i < 3 * paramInt * (3 * paramInt)) return -1;
/* 1832 */     return -2;
/*      */   }
/*      */   
/*      */   public static Point getVectorSum(Point paramPoint1, Point paramPoint2)
/*      */   {
/* 1837 */     return new Point(paramPoint1.x + paramPoint2.x, paramPoint1.y + paramPoint2.y);
/*      */   }
/*      */   
/*      */   public static Point[] getVectorDiff(Point[] paramArrayOfPoint)
/*      */   {
/* 1842 */     if (paramArrayOfPoint == null) return null;
/* 1843 */     int i = paramArrayOfPoint.length - 1;
/* 1844 */     Point[] arrayOfPoint = new Point[i];
/* 1845 */     for (int j = 0; j < i; j++)
/* 1846 */       arrayOfPoint[j] = new Point(paramArrayOfPoint[(j + 1)].x - paramArrayOfPoint[j].x, paramArrayOfPoint[(j + 1)].y - paramArrayOfPoint[j].y);
/* 1847 */     return arrayOfPoint;
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
/*      */   public static int[] getRadius(Point[] paramArrayOfPoint)
/*      */   {
/* 1860 */     if (paramArrayOfPoint == null) return null;
/* 1861 */     int i = paramArrayOfPoint.length;
/* 1862 */     int[] arrayOfInt = new int[i];
/* 1863 */     for (int j = 0; j < i; j++)
/* 1864 */       arrayOfInt[j] = ((int)(0.5D + Math.sqrt(paramArrayOfPoint[j].x * paramArrayOfPoint[j].x + paramArrayOfPoint[j].y * paramArrayOfPoint[j].y)));
/* 1865 */     return arrayOfInt;
/*      */   }
/*      */   
/*      */   public static boolean isBounded(Point paramPoint, int paramInt1, int paramInt2)
/*      */   {
/* 1870 */     int i = paramPoint.x * paramPoint.x + paramPoint.y * paramPoint.y;
/* 1871 */     if ((i < paramInt1 * paramInt1) || (i > paramInt2 * paramInt2)) return false;
/* 1872 */     return true;
/*      */   }
/*      */   
/*      */ 
/*      */   public static Point getBoundedPoint(Random paramRandom, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
/*      */   {
/* 1878 */     Point localPoint = getRandomPoint(paramRandom, paramInt1, paramInt2);
/* 1879 */     while (!isBounded(localPoint, paramInt3, paramInt4)) localPoint = getRandomPoint(paramRandom, paramInt1, paramInt2);
/* 1880 */     return localPoint;
/*      */   }
/*      */   
/*      */ 
/*      */   public static int min(int[] paramArrayOfInt)
/*      */   {
/* 1886 */     if (paramArrayOfInt == null) { System.out.println("Error: min().");return -1; }
/* 1887 */     int i = paramArrayOfInt[0];
/* 1888 */     for (int j = 1; j < paramArrayOfInt.length; j++) if (paramArrayOfInt[j] < i) i = paramArrayOfInt[j];
/* 1889 */     return i;
/*      */   }
/*      */   
/* 1892 */   public static float min(float[] paramArrayOfFloat) { if (paramArrayOfFloat == null) { System.out.println("Error: min().");return -1.0F; }
/* 1893 */     float f = paramArrayOfFloat[0];
/* 1894 */     for (int i = 1; i < paramArrayOfFloat.length; i++) if (paramArrayOfFloat[i] < f) f = paramArrayOfFloat[i];
/* 1895 */     return f;
/*      */   }
/*      */   
/*      */   public static int max(int[] paramArrayOfInt) {
/* 1899 */     if (paramArrayOfInt == null) { System.out.println("Error: max().");return -1; }
/* 1900 */     int i = paramArrayOfInt[0];
/* 1901 */     for (int j = 1; j < paramArrayOfInt.length; j++) if (paramArrayOfInt[j] > i) i = paramArrayOfInt[j];
/* 1902 */     return i;
/*      */   }
/*      */   
/* 1905 */   public static float max(float[] paramArrayOfFloat) { if (paramArrayOfFloat == null) { System.out.println("Error: max().");return -1.0F; }
/* 1906 */     float f = paramArrayOfFloat[0];
/* 1907 */     for (int i = 1; i < paramArrayOfFloat.length; i++) if (paramArrayOfFloat[i] > f) f = paramArrayOfFloat[i];
/* 1908 */     return f;
/*      */   }
/*      */   
/* 1911 */   public static double max(double[] paramArrayOfDouble) { if (paramArrayOfDouble == null) { System.out.println("Error: max().");return -1.0D; }
/* 1912 */     double d = paramArrayOfDouble[0];
/* 1913 */     for (int i = 1; i < paramArrayOfDouble.length; i++) if (paramArrayOfDouble[i] > d) d = paramArrayOfDouble[i];
/* 1914 */     return d;
/*      */   }
/*      */   
/*      */   public static int minIndex(float[] paramArrayOfFloat) {
/* 1918 */     if (paramArrayOfFloat == null) { System.out.println("Error: minIndex().");return -1; }
/* 1919 */     float f = paramArrayOfFloat[0];
/* 1920 */     int i = 0;
/* 1921 */     for (int j = 1; j < paramArrayOfFloat.length; j++) if (paramArrayOfFloat[j] < f) { f = paramArrayOfFloat[j];i = j; }
/* 1922 */     return i;
/*      */   }
/*      */   
/*      */   public static int maxIndex(int[] paramArrayOfInt) {
/* 1926 */     if (paramArrayOfInt == null) { System.out.println("Error: maxIndex().");return -1; }
/* 1927 */     int i = paramArrayOfInt[0];
/* 1928 */     int j = 0;
/* 1929 */     for (int k = 1; k < paramArrayOfInt.length; k++) if (paramArrayOfInt[k] > i) { i = paramArrayOfInt[k];j = k; }
/* 1930 */     return j;
/*      */   }
/*      */   
/* 1933 */   public static int maxIndex(float[] paramArrayOfFloat) { if (paramArrayOfFloat == null) { System.out.println("Error: maxIndex().");return -1; }
/* 1934 */     float f = paramArrayOfFloat[0];
/* 1935 */     int i = 0;
/* 1936 */     for (int j = 1; j < paramArrayOfFloat.length; j++) if (paramArrayOfFloat[j] > f) { f = paramArrayOfFloat[j];i = j; }
/* 1937 */     return i;
/*      */   }
/*      */   
/* 1940 */   public static int maxIndex(double[] paramArrayOfDouble) { if (paramArrayOfDouble == null) { System.out.println("Error: maxIndex().");return -1; }
/* 1941 */     double d = paramArrayOfDouble[0];
/* 1942 */     int i = 0;
/* 1943 */     for (int j = 1; j < paramArrayOfDouble.length; j++) if (paramArrayOfDouble[j] > d) { d = paramArrayOfDouble[j];i = j; }
/* 1944 */     return i;
/*      */   }
/*      */   
/*      */   public static float sum(float[] paramArrayOfFloat) {
/* 1948 */     if (paramArrayOfFloat == null) { System.out.println("Error: sum().");return -1.0F; }
/* 1949 */     float f = 0.0F;
/* 1950 */     for (int i = 0; i < paramArrayOfFloat.length; i++) f += paramArrayOfFloat[i];
/* 1951 */     return f;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public static float[] transform(float[] paramArrayOfFloat, float paramFloat1, float paramFloat2)
/*      */   {
/* 1959 */     int i = paramArrayOfFloat.length;
/* 1960 */     float f1 = paramArrayOfFloat[0];float f2 = paramArrayOfFloat[0];
/* 1961 */     for (int j = 1; j < i; j++) {
/* 1962 */       if (paramArrayOfFloat[j] < f1) f1 = paramArrayOfFloat[j];
/* 1963 */       if (paramArrayOfFloat[j] > f2) { f2 = paramArrayOfFloat[j];
/*      */       }
/*      */     }
/* 1966 */     float f3 = f2 - f1;float f4 = paramFloat2 - paramFloat1;
/* 1967 */     float[] arrayOfFloat = new float[i];
/* 1968 */     for (int k = 0; k < i; k++) arrayOfFloat[k] = ((paramArrayOfFloat[k] - f1) / f3 * f4 + paramFloat1);
/* 1969 */     return arrayOfFloat;
/*      */   }
/*      */ }


/* Location:              /home/anali/INAOE/tesis/codigos/topub/exe/ww4.jar!/Network/MATH.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */