/*     */ package BnToJt;
/*     */ 
/*     */ import Network.ChordalGraph;
/*     */ import Network.MATH;
/*     */ import Network.UTIL;
/*     */ import Network.UndirectGraph;
/*     */ import java.awt.Frame;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.FileWriter;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.PrintStream;
/*     */ import java.io.PrintWriter;
/*     */ import java.util.Random;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SimuBoundSet
/*     */ {
/*  60 */   static long seed = 0L;
/*  61 */   static boolean debug = false;
/*     */   
/*     */   public static void main(String[] paramArrayOfString) throws IOException
/*     */   {
/*  65 */     if (paramArrayOfString.length != 4) {
/*  66 */       System.out.println("Use: java BnToJt.SimuBoundSet n e type boundary_set_file");
/*  67 */       System.exit(0);
/*     */     }
/*  69 */     int i = Integer.parseInt(paramArrayOfString[0]);
/*  70 */     int j = Integer.parseInt(paramArrayOfString[1]);
/*  71 */     if (j < i) {
/*  72 */       System.out.println("Error: e < n (graph is disconnected or singly connected)");
/*  73 */       System.exit(0);
/*     */     }
/*  75 */     else if (j >= i * (i - 1) / 2) {
/*  76 */       System.out.println("Error: e >= n*(n-1)/2 (graph is too dense or impossible)");
/*  77 */       System.exit(0);
/*     */     }
/*  79 */     int k = Integer.parseInt(paramArrayOfString[2]);
/*  80 */     String str = paramArrayOfString[3];
/*     */     
/*  82 */     Random localRandom = new Random(seed);
/*  83 */     if (seed == 0L) { localRandom = new Random();
/*     */     }
/*  85 */     ChordalGraph localChordalGraph = simuChordalGraph(i, j, localRandom);
/*     */     
/*  87 */     Frame localFrame = null;
/*  88 */     if (debug) {
/*  89 */       localFrame = new Frame();localFrame.setSize(600, 600);localFrame.setLocation(0, 0);
/*  90 */       localFrame.add(new UgCan2(new UndirectGraph(localChordalGraph)));
/*  91 */       localFrame.setTitle("Simulated Chordal Graph");localFrame.setVisible(true);
/*     */     }
/*     */     
/*  94 */     int[][] arrayOfInt1 = localChordalGraph.findClique();
/*  95 */     UTIL.showList("Chordal graph cliques = ", arrayOfInt1);
/*     */     
/*  97 */     int[][] arrayOfInt2 = rmUnique(arrayOfInt1);
/*  98 */     UTIL.showList("  Type 1 Boundary set = ", arrayOfInt2);
/*     */     
/* 100 */     int[][] arrayOfInt3 = arrayOfInt2;
/* 101 */     if (k == 3) {
/* 102 */       arrayOfInt3 = splitBoundary(arrayOfInt2, localRandom);
/* 103 */       UTIL.showList("  Type 3 Boundary set = ", arrayOfInt3);
/*     */     }
/* 105 */     else if (k == 2) {
/* 106 */       arrayOfInt3 = nonChordalize(arrayOfInt2, i, localRandom);
/* 107 */       while (arrayOfInt3 == null) {
/* 108 */         localChordalGraph = simuChordalGraph(i, j, localRandom);
/*     */         
/* 110 */         arrayOfInt1 = localChordalGraph.findClique();
/* 111 */         arrayOfInt2 = rmUnique(arrayOfInt1);
/* 112 */         arrayOfInt3 = nonChordalize(arrayOfInt2, i, localRandom);
/*     */       }
/*     */       
/* 115 */       if (debug) {
/* 116 */         localFrame = new Frame();localFrame.setSize(600, 600);localFrame.setLocation(0, 0);
/* 117 */         localFrame.add(new UgCan2(new UndirectGraph(localChordalGraph)));
/* 118 */         localFrame.setTitle("Re-simulated Chordal Graph");localFrame.setVisible(true);
/*     */       }
/*     */       
/* 121 */       UTIL.showList("Re-simulated chordal graph cliques = ", arrayOfInt1);
/* 122 */       UTIL.showList("               Type 1 Boundary set = ", arrayOfInt2);
/* 123 */       UTIL.showList("               Type 2 Boundary set = ", arrayOfInt3);
/*     */     }
/*     */     
/* 126 */     saveSetFile(str, arrayOfInt3);
/*     */     
/*     */ 
/* 129 */     if (debug) {
/* 130 */       System.out.print("Enter to exit: ");
/* 131 */       BufferedReader localBufferedReader = new BufferedReader(new InputStreamReader(System.in));
/*     */       
/* 133 */       localBufferedReader.readLine();
/*     */     }
/* 135 */     System.exit(0);
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
/*     */   static int[][] rmUnique(int[][] paramArrayOfInt)
/*     */   {
/* 152 */     int[][] arrayOfInt = new int[paramArrayOfInt.length][];
/* 153 */     int j; for (int i = 0; i < paramArrayOfInt.length; i++) {
/* 154 */       j = paramArrayOfInt[i].length;
/* 155 */       arrayOfInt[i] = new int[j];
/* 156 */       System.arraycopy(paramArrayOfInt[i], 0, arrayOfInt[i], 0, j);
/*     */     }
/*     */     
/* 159 */     for (i = 0; i < paramArrayOfInt.length; i++) {
/* 160 */       for (j = 0; j < paramArrayOfInt[i].length; j++) {
/* 161 */         int k = 1;
/* 162 */         for (int m = 0; m < paramArrayOfInt.length; m++) {
/* 163 */           if ((m != i) && 
/* 164 */             (MATH.member(paramArrayOfInt[i][j], paramArrayOfInt[m]))) {
/* 165 */             k = 0; break;
/*     */           }
/*     */         }
/* 168 */         if (k != 0) { arrayOfInt[i][j] = -1;
/*     */         }
/*     */       }
/*     */     }
/* 172 */     for (i = 0; i < paramArrayOfInt.length; i++) arrayOfInt[i] = UTIL.delValue(-1, arrayOfInt[i]);
/* 173 */     return arrayOfInt;
/*     */   }
/*     */   
/*     */   static ChordalGraph simuChordalGraph(int paramInt1, int paramInt2, Random paramRandom)
/*     */   {
/* 178 */     UndirectGraph localUndirectGraph = UndirectGraph.simuGraph(paramInt1, paramInt2, paramRandom);
/* 179 */     ChordalGraph localChordalGraph = new ChordalGraph(localUndirectGraph);
/* 180 */     localChordalGraph.setChordalGraph();
/* 181 */     return localChordalGraph;
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
/*     */   static int[][] nonChordalize(int[][] paramArrayOfInt, int paramInt, Random paramRandom)
/*     */   {
/* 202 */     Object localObject = new int[paramArrayOfInt.length][];
/* 203 */     for (int i = 0; i < paramArrayOfInt.length; i++) {
/* 204 */       j = paramArrayOfInt[i].length;
/* 205 */       localObject[i] = new int[j];
/* 206 */       System.arraycopy(paramArrayOfInt[i], 0, localObject[i], 0, j);
/*     */     }
/*     */     
/*     */ 
/* 210 */     int j = 0;
/*     */     do {
/* 212 */       j++;
/* 213 */       if (j > 10) {
/* 214 */         System.out.println("Fail to non-chordalize after 10 trials: resimulate.");
/* 215 */         return (int[][])null;
/*     */       }
/*     */       
/* 218 */       i = 1;
/* 219 */       int k = MATH.getRandomInt(paramRandom, 0, localObject.length - 1);
/* 220 */       if (localObject[k].length < 3) i = 0;
/* 221 */       if (i != 0)
/*     */       {
/*     */ 
/* 224 */         if (isComparable(k, (int[][])localObject)) i = 0;
/* 225 */         if (i != 0)
/*     */         {
/*     */ 
/* 228 */           int m = MATH.getRandomInt(paramRandom, 0, localObject[k].length - 2);
/* 229 */           int[][] arrayOfInt = new int[localObject.length + 1][];
/* 230 */           for (int n = 0; n < k; n++) arrayOfInt[n] = localObject[n];
/* 231 */           arrayOfInt[k] = MATH.delMember(localObject[k][m], localObject[k]);
/* 232 */           for (n = k + 1; n < localObject.length; n++) arrayOfInt[n] = localObject[n];
/* 233 */           arrayOfInt[localObject.length] = MATH.delMember(localObject[k][(m + 1)], localObject[k]);
/* 234 */           localObject = arrayOfInt;
/*     */           
/* 236 */           UndirectGraph localUndirectGraph = setGraphFmCluster((int[][])localObject, paramInt);
/* 237 */           ChordalGraph localChordalGraph = new ChordalGraph(localUndirectGraph);
/* 238 */           if (localChordalGraph.isChordal()) i = 0;
/* 239 */         } } } while (i == 0);
/* 240 */     return (int[][])localObject;
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
/*     */   static int[][] splitBoundary(int[][] paramArrayOfInt, Random paramRandom)
/*     */   {
/* 261 */     int[][] arrayOfInt = (int[][])null;
/* 262 */     int i = -1;
/*     */     int j;
/*     */     do {
/* 265 */       j = 1;
/* 266 */       i = MATH.getRandomInt(paramRandom, 0, paramArrayOfInt.length - 1);
/* 267 */       int k = paramArrayOfInt[i].length;
/* 268 */       if (k < 3) j = 0;
/* 269 */       if (j != 0)
/*     */       {
/*     */ 
/* 272 */         if (isComparable(i, paramArrayOfInt)) j = 0;
/* 273 */         if (j != 0)
/*     */         {
/*     */ 
/* 276 */           int m = k * (k - 1) / 2;
/* 277 */           arrayOfInt = new int[m][2];
/* 278 */           int n = 0;
/* 279 */           int i2; for (int i1 = 0; i1 < k - 1; i1++) {
/* 280 */             for (i2 = i1 + 1; i2 < k; i2++) {
/* 281 */               arrayOfInt[n][0] = paramArrayOfInt[i][i1];
/* 282 */               arrayOfInt[n][1] = paramArrayOfInt[i][i2];n++;
/*     */             }
/*     */           }
/*     */           
/* 286 */           if (k >= 4) {
/* 287 */             i1 = MATH.getRandomInt(paramRandom, 1, k - 3);
/* 288 */             for (i2 = 1; i2 <= i1; i2++) {
/* 289 */               int i3 = MATH.getRandomInt(paramRandom, 0, arrayOfInt.length - 2);
/* 290 */               arrayOfInt = replaceNbByUnion(arrayOfInt, i3);
/*     */             }
/*     */           }
/* 293 */         } } } while (j == 0);
/* 294 */     return cutOneAddMore(paramArrayOfInt, i, arrayOfInt);
/*     */   }
/*     */   
/*     */   static boolean isComparable(int paramInt, int[][] paramArrayOfInt)
/*     */   {
/* 299 */     for (int i = 0; i < paramArrayOfInt.length; i++) {
/* 300 */       if ((i != paramInt) && 
/* 301 */         (MATH.isSubset(paramArrayOfInt[paramInt], paramArrayOfInt[i]))) return true;
/*     */     }
/* 303 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   static int[][] replaceNbByUnion(int[][] paramArrayOfInt, int paramInt)
/*     */   {
/* 312 */     int i = paramArrayOfInt.length;
/* 313 */     int[][] arrayOfInt = new int[i - 1][];
/* 314 */     for (int j = 0; j < paramInt; j++) arrayOfInt[j] = paramArrayOfInt[j];
/* 315 */     for (j = paramInt; j < i - 2; j++) arrayOfInt[j] = paramArrayOfInt[(j + 2)];
/* 316 */     arrayOfInt[(i - 2)] = MATH.union(paramArrayOfInt[paramInt], paramArrayOfInt[(paramInt + 1)]);
/* 317 */     return arrayOfInt;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   static int[][] cutOneAddMore(int[][] paramArrayOfInt1, int paramInt, int[][] paramArrayOfInt2)
/*     */   {
/* 324 */     int i = paramArrayOfInt1.length;
/* 325 */     int j = paramArrayOfInt2.length;
/* 326 */     int k = i + j - 1;
/* 327 */     int[][] arrayOfInt = new int[k][];
/* 328 */     for (int m = 0; m < paramInt; m++) arrayOfInt[m] = paramArrayOfInt1[m];
/* 329 */     for (m = paramInt; m < i - 1; m++) arrayOfInt[m] = paramArrayOfInt1[(m + 1)];
/* 330 */     for (m = i - 1; m < k; m++) arrayOfInt[m] = paramArrayOfInt2[(m - i + 1)];
/* 331 */     return arrayOfInt;
/*     */   }
/*     */   
/*     */ 
/*     */   static UndirectGraph setGraphFmCluster(int[][] paramArrayOfInt, int paramInt)
/*     */   {
/* 337 */     UndirectGraph localUndirectGraph = new UndirectGraph();
/* 338 */     localUndirectGraph.setDumbNetPlus(paramInt);
/*     */     
/* 340 */     for (int i = 0; i < paramArrayOfInt.length; i++) {
/* 341 */       for (int j = 0; j < paramArrayOfInt[i].length - 1; j++) {
/* 342 */         for (int k = j + 1; k < paramArrayOfInt[i].length; k++) {
/* 343 */           localUndirectGraph.addLink(paramArrayOfInt[i][j], paramArrayOfInt[i][k]);
/*     */         }
/*     */       }
/*     */     }
/* 347 */     return localUndirectGraph;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   static void saveSetFile(String paramString, int[][] paramArrayOfInt)
/*     */   {
/* 357 */     int i = paramArrayOfInt.length;
/*     */     try {
/* 359 */       PrintWriter localPrintWriter = new PrintWriter(new FileWriter(paramString));
/* 360 */       System.out.println("Saving file " + paramString);
/*     */       
/* 362 */       localPrintWriter.println(i + "  #_of_sets");
/* 363 */       for (int j = 0; j < i; j++) {
/* 364 */         for (int k = 0; k < paramArrayOfInt[j].length; k++) localPrintWriter.print(paramArrayOfInt[j][k] + " ");
/* 365 */         localPrintWriter.println();
/*     */       }
/*     */       
/* 368 */       localPrintWriter.close();
/* 369 */     } catch (IOException localIOException) { System.out.println("Unable to save " + paramString);
/*     */     }
/*     */   }
/*     */ }


/* Location:              /home/anali/INAOE/tesis/codigos/topub/exe/ww4.jar!/BnToJt/SimuBoundSet.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */