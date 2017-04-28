/*     */ package BNetEdit;
/*     */ 
/*     */ import Network.BayesNet;
/*     */ import Network.MATH;
/*     */ import Network.UTIL;
/*     */ import java.awt.Point;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.FileReader;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintStream;
/*     */ import java.util.Random;
/*     */ import java.util.StringTokenizer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class BifToBn
/*     */ {
/*  27 */   static boolean debug = false;
/*     */   
/*     */   public static void main(String[] paramArrayOfString) {
/*  30 */     int i = paramArrayOfString.length;
/*  31 */     if (i < 2) {
/*  32 */       System.out.println("Use: java BNetEdit.BifToBn bif_file bn_file");
/*  33 */       System.exit(0);
/*     */     }
/*     */     
/*  36 */     String str1 = paramArrayOfString[0];
/*  37 */     String str2 = paramArrayOfString[1];
/*  38 */     BayesNet localBayesNet = loadBifBn(str1);
/*  39 */     System.out.println("\nSave " + str2);
/*  40 */     localBayesNet.save(str2);
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
/*     */   static BayesNet loadBifBn(String paramString)
/*     */   {
/*  59 */     int i = 2000;
/*  60 */     int j = 0;
/*  61 */     String[] arrayOfString1 = new String[i];
/*  62 */     String[][] arrayOfString2 = new String[i][];
/*  63 */     int[][] arrayOfInt = new int[i][];
/*  64 */     float[][] arrayOfFloat = new float[i][];
/*     */     try
/*     */     {
/*  67 */       BufferedReader localBufferedReader = new BufferedReader(new FileReader(paramString));
/*  68 */       localBufferedReader.readLine();localBufferedReader.readLine();
/*     */       
/*  70 */       String str = localBufferedReader.readLine();
/*  71 */       while (str.startsWith("variable")) {
/*  72 */         String[][] arrayOfString3 = loadVarInfo(str, localBufferedReader.readLine());
/*  73 */         arrayOfString1[j] = arrayOfString3[0][0];
/*  74 */         arrayOfString2[(j++)] = arrayOfString3[1];
/*  75 */         localBufferedReader.readLine();
/*  76 */         str = localBufferedReader.readLine();
/*     */       }
/*     */       
/*  79 */       if (debug) {
/*  80 */         for (k = 0; k < j; k++) {
/*  81 */           System.out.print(k + " " + arrayOfString1[k] + ": ");
/*  82 */           for (int m = 0; m < arrayOfString2[k].length; m++) System.out.print(arrayOfString2[k][m] + " ");
/*  83 */           System.out.println();
/*     */         }
/*     */       }
/*     */       
/*  87 */       for (int k = 0; k < j; k++) {
/*  88 */         String[] arrayOfString4 = loadFamily(str);
/*  89 */         if (debug) UTIL.showList(k + " ", arrayOfString4);
/*  90 */         arrayOfInt[k] = getParent(arrayOfString4, arrayOfString1, j, arrayOfString2);
/*  91 */         int n = arrayOfInt[k] == null ? 0 : arrayOfInt[k].length;
/*  92 */         if (debug) UTIL.showList("Parent: ", arrayOfInt[k]);
/*  93 */         int i1 = getProbLineCount(arrayOfInt[k], arrayOfString2);
/*  94 */         String[] arrayOfString5 = new String[i1];
/*  95 */         for (int i2 = 0; i2 < i1; i2++) arrayOfString5[i2] = localBufferedReader.readLine();
/*  96 */         arrayOfFloat[k] = getCpt(arrayOfString5, n);
/*  97 */         localBufferedReader.readLine();
/*  98 */         str = localBufferedReader.readLine();
/*     */       }
/*     */       
/* 101 */       localBufferedReader.close();
/* 102 */     } catch (IOException localIOException) { System.out.println("Err loading " + paramString);
/*     */     }
/* 104 */     BayesNet localBayesNet = setBn(j, arrayOfString1, arrayOfString2, arrayOfInt, arrayOfFloat);
/* 105 */     return localBayesNet;
/*     */   }
/*     */   
/*     */ 
/*     */   static BayesNet setBn(int paramInt, String[] paramArrayOfString, String[][] paramArrayOfString1, int[][] paramArrayOfInt, float[][] paramArrayOfFloat)
/*     */   {
/* 111 */     BayesNet localBayesNet = new BayesNet();
/* 112 */     localBayesNet.setDumbNetPlus(paramInt);
/* 113 */     Random localRandom = new Random();
/*     */     
/* 115 */     int[][] arrayOfInt = getChildFmParent(paramArrayOfInt);
/*     */     
/* 117 */     for (int i = 0; i < paramInt; i++) {
/* 118 */       localBayesNet.setLabel(i, paramArrayOfString[i]);
/* 119 */       localBayesNet.setState(i, paramArrayOfString1[i]);
/*     */       
/* 121 */       int j = arrayOfInt[i] == null ? 0 : arrayOfInt[i].length;
/* 122 */       if (j == 0) { localBayesNet.setChild(i, null);
/*     */       } else {
/* 124 */         int[] arrayOfInt1 = new int[j];
/* 125 */         for (int m = 0; m < j; m++) arrayOfInt1[m] = arrayOfInt[i][m];
/* 126 */         localBayesNet.setChild(i, arrayOfInt1);
/*     */       }
/*     */       
/* 129 */       int k = paramArrayOfInt[i] == null ? 0 : paramArrayOfInt[i].length;
/* 130 */       if (k == 0) { localBayesNet.setParent(i, null);
/*     */       } else {
/* 132 */         localObject = new int[k];
/* 133 */         for (int n = 0; n < k; n++) localObject[(k - n - 1)] = paramArrayOfInt[i][n];
/* 134 */         localBayesNet.setParent(i, (int[])localObject);
/*     */       }
/*     */       
/* 137 */       localBayesNet.setCondProb(i, paramArrayOfFloat[i]);
/*     */       
/* 139 */       Object localObject = MATH.getRandomPoint(localRandom, 10, 200);
/* 140 */       localBayesNet.setPos(i, (Point)localObject);
/*     */     }
/* 142 */     return localBayesNet;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   static int[][] getChildFmParent(int[][] paramArrayOfInt)
/*     */   {
/* 151 */     int i = paramArrayOfInt.length;
/* 152 */     int[] arrayOfInt = new int[i];
/* 153 */     for (int j = 0; j < i; j++) {
/* 154 */       for (k = 0; k < i; k++) {
/* 155 */         if (MATH.member(j, paramArrayOfInt[k])) { arrayOfInt[j] += 1;
/*     */         }
/*     */       }
/*     */     }
/* 159 */     int[][] arrayOfInt1 = new int[i][];
/* 160 */     for (int k = 0; k < i; k++) {
/* 161 */       if (arrayOfInt[k] == 0) {
/* 162 */         arrayOfInt1[k] = null;
/*     */       } else {
/* 164 */         arrayOfInt1[k] = new int[arrayOfInt[k]];
/* 165 */         int m = 0;
/* 166 */         for (int n = 0; n < i; n++)
/* 167 */           if (MATH.member(k, paramArrayOfInt[n])) arrayOfInt1[k][(m++)] = n;
/*     */       }
/*     */     }
/* 170 */     return arrayOfInt1;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   static String[][] loadVarInfo(String paramString1, String paramString2)
/*     */   {
/* 180 */     String[][] arrayOfString = new String[2][];
/* 181 */     arrayOfString[0] = new String[1];
/* 182 */     StringTokenizer localStringTokenizer = new StringTokenizer(paramString1);
/* 183 */     localStringTokenizer.nextToken();
/* 184 */     arrayOfString[0][0] = localStringTokenizer.nextToken();
/*     */     
/* 186 */     String str = paramString2.replace(',', ' ');
/* 187 */     localStringTokenizer = new StringTokenizer(str);
/* 188 */     int i = localStringTokenizer.countTokens() - 7;
/* 189 */     arrayOfString[1] = new String[i];
/* 190 */     for (int j = 0; j < 6; j++) localStringTokenizer.nextToken();
/* 191 */     for (j = 0; j < i; j++) arrayOfString[1][j] = localStringTokenizer.nextToken();
/* 192 */     return arrayOfString;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   static String[] loadFamily(String paramString)
/*     */   {
/* 200 */     String str1 = paramString.replace('|', ' ');
/* 201 */     String str2 = str1.replace(',', ' ');
/* 202 */     StringTokenizer localStringTokenizer = new StringTokenizer(str2);
/* 203 */     int i = localStringTokenizer.countTokens() - 4;
/* 204 */     String[] arrayOfString = new String[i];
/*     */     
/* 206 */     localStringTokenizer.nextToken();localStringTokenizer.nextToken();
/* 207 */     for (int j = 0; j < i; j++) arrayOfString[j] = localStringTokenizer.nextToken();
/* 208 */     return arrayOfString;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   static int[] getParent(String[] paramArrayOfString1, String[] paramArrayOfString2, int paramInt, String[][] paramArrayOfString)
/*     */   {
/* 217 */     int i = paramArrayOfString1.length - 1;
/* 218 */     if (i == 0) { return null;
/*     */     }
/* 220 */     int[] arrayOfInt = new int[i];
/* 221 */     for (int j = 0; j < i; j++) {
/* 222 */       for (int k = 0; k < paramInt; k++) {
/* 223 */         if (paramArrayOfString1[(j + 1)].equals(paramArrayOfString2[k])) {
/* 224 */           arrayOfInt[j] = k; break;
/*     */         }
/*     */       }
/*     */     }
/* 228 */     return arrayOfInt;
/*     */   }
/*     */   
/*     */   static int getProbLineCount(int[] paramArrayOfInt, String[][] paramArrayOfString)
/*     */   {
/* 233 */     if (paramArrayOfInt == null) return 1;
/* 234 */     int i = paramArrayOfInt.length;
/*     */     
/* 236 */     int j = 1;
/* 237 */     for (int k = 0; k < i; k++) j *= paramArrayOfString[paramArrayOfInt[k]].length;
/* 238 */     return j;
/*     */   }
/*     */   
/*     */ 
/*     */   static float[] getCpt(String[] paramArrayOfString, int paramInt)
/*     */   {
/* 244 */     int i = paramArrayOfString.length;
/* 245 */     StringTokenizer localStringTokenizer = new StringTokenizer(paramArrayOfString[0]);
/* 246 */     int j = localStringTokenizer.countTokens() - paramInt;
/* 247 */     if (paramInt == 0) { j--;
/*     */     }
/* 249 */     float[] arrayOfFloat = new float[i * j];
/* 250 */     int k = 0;
/*     */     
/* 252 */     for (int m = 0; m < i; m++) {
/* 253 */       String str1 = paramArrayOfString[m].replace(',', ' ');
/* 254 */       String str2 = str1.replace(';', ' ');
/* 255 */       localStringTokenizer = new StringTokenizer(str2);
/* 256 */       if (paramInt == 0) localStringTokenizer.nextToken(); else
/* 257 */         for (n = 0; n < paramInt; n++) localStringTokenizer.nextToken();
/* 258 */       for (int n = 0; n < j; n++) arrayOfFloat[(k++)] = Float.parseFloat(localStringTokenizer.nextToken());
/*     */     }
/* 260 */     return arrayOfFloat;
/*     */   }
/*     */ }


/* Location:              /home/anali/INAOE/tesis/codigos/topub/exe/ww4.jar!/BNetEdit/BifToBn.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */