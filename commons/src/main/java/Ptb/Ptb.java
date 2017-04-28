/*     */ package Ptb;
/*     */ 
/*     */ import Network.MATH;
/*     */ import Network.UTIL;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.FileReader;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.PrintStream;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Ptb
/*     */ {
/*     */   public static void main(String[] paramArrayOfString)
/*     */     throws IOException
/*     */   {
/*  19 */     BufferedReader localBufferedReader1 = new BufferedReader(new InputStreamReader(System.in));
/*     */     
/*     */     BufferedReader localBufferedReader2;
/*  22 */     if (paramArrayOfString.length < 1) {
/*  23 */       System.out.println("Please enter potential file path: ");
/*  24 */       String str1 = localBufferedReader1.readLine();
/*  25 */       localBufferedReader2 = new BufferedReader(new FileReader(str1));
/*     */     } else {
/*  27 */       localBufferedReader2 = new BufferedReader(new FileReader(paramArrayOfString[0]));
/*     */     }
/*  29 */     int i = UTIL.loadInt(localBufferedReader2);
/*  30 */     String[] arrayOfString = UTIL.loadStringListLine(localBufferedReader2, i);
/*  31 */     int[] arrayOfInt1 = UTIL.loadIntList(localBufferedReader2, i);
/*  32 */     int j = 1;
/*  33 */     for (int k = 0; k < i; k++) j *= arrayOfInt1[k];
/*  34 */     float[] arrayOfFloat1 = UTIL.loadRealList(localBufferedReader2, j);
/*     */     
/*  36 */     float f = 0.0F;
/*  37 */     for (int m = 0; m < j; m++) f += arrayOfFloat1[m];
/*  38 */     System.out.println("sum = " + f);
/*  39 */     for (int n = 0; n < j; n++) { arrayOfFloat1[n] /= f;
/*     */     }
/*  41 */     for (int i1 = 0; i1 < i; i1++) System.out.print(arrayOfString[i1] + ":" + arrayOfInt1[i1] + "\t");
/*  42 */     System.out.print(" P(");
/*  43 */     for (int i2 = 0; i2 < i; i2++) System.out.print(arrayOfString[i2] + ":" + arrayOfInt1[i2] + " ");
/*  44 */     System.out.println(")");
/*  45 */     int[][] arrayOfInt2 = MATH.getAllMix(arrayOfInt1);
/*  46 */     for (int i3 = 0; i3 < j; i3++) {
/*  47 */       for (int i4 = 0; i4 < i; i4++) System.out.print(arrayOfInt2[i3][i4] + "\t");
/*  48 */       System.out.println("   " + arrayOfFloat1[i3]);
/*     */     }
/*  50 */     System.out.println();
/*     */     
/*  52 */     int[][] arrayOfInt3 = new int[i][1];
/*  53 */     for (int i5 = 0; i5 < i; i5++) arrayOfInt3[i5][0] = i5;
/*  54 */     float[][] arrayOfFloat = new float[i][];
/*  55 */     for (int i6 = 0; i6 < i; i6++) {
/*  56 */       System.out.print("P(" + arrayOfString[i6] + ":" + arrayOfInt1[i6] + "): ");
/*  57 */       arrayOfFloat[i6] = MATH.margin(arrayOfFloat1, arrayOfInt1, arrayOfInt3[i6]);
/*  58 */       for (int i7 = 0; i7 < arrayOfFloat[i6].length; i7++)
/*  59 */         System.out.print("  " + i7 + ": " + arrayOfFloat[i6][i7]);
/*  60 */       System.out.println();
/*     */     }
/*     */     
/*     */ 
/*  64 */     System.out.println("\nSpecify a conditional probability(y/n)?");
/*  65 */     String str2 = localBufferedReader1.readLine();
/*  66 */     while (str2.equals("y")) {
/*  67 */       System.out.print("\nNumber of variables in the HEAD(>0): ");
/*  68 */       str2 = localBufferedReader1.readLine();
/*  69 */       int i8 = UTIL.getInt(str2);
/*  70 */       System.out.print("\nNumber of variables in the TAIL(>=0): ");
/*  71 */       str2 = localBufferedReader1.readLine();
/*  72 */       int i9 = UTIL.getInt(str2);
/*     */       
/*  74 */       System.out.print("\nIndexes of vars in HEAD: ");
/*  75 */       str2 = localBufferedReader1.readLine();
/*  76 */       int[] arrayOfInt4 = UTIL.getIntList(str2, i8);
/*     */       
/*     */ 
/*  79 */       int[] arrayOfInt6 = new int[i9 + 1];
/*  80 */       int[] arrayOfInt7 = new int[i9 + i8];
/*     */       int[] arrayOfInt8;
/*  82 */       int[] arrayOfInt5; Object localObject; int i14; float[] arrayOfFloat2; int i12; if (i9 > 0) {
/*  83 */         System.out.print("\nIndexs of vars in TAIL: ");
/*  84 */         str2 = localBufferedReader1.readLine();
/*  85 */         arrayOfInt6 = UTIL.getIntList(str2, i9);
/*     */         
/*  87 */         arrayOfInt8 = MATH.getIntersection(arrayOfInt4, arrayOfInt6);
/*  88 */         while (arrayOfInt8 != null) {
/*  89 */           System.out.print("\nHead and tail overlap. Please reenter.");
/*  90 */           System.out.print("\nIndexes of vars in HEAD: ");
/*  91 */           str2 = localBufferedReader1.readLine();
/*  92 */           arrayOfInt4 = UTIL.getIntList(str2, i8);
/*  93 */           System.out.print("\nIndexs of vars in TAIL: ");
/*  94 */           str2 = localBufferedReader1.readLine();
/*  95 */           arrayOfInt6 = UTIL.getIntList(str2, i9);
/*  96 */           arrayOfInt8 = MATH.getIntersection(arrayOfInt4, arrayOfInt6);
/*     */         }
/*     */         
/*  99 */         arrayOfInt5 = UTIL.appendToArray(arrayOfInt4, arrayOfInt6);
/* 100 */         localObject = MATH.margin(arrayOfFloat1, arrayOfInt1, arrayOfInt5);
/*     */         
/* 102 */         int[] arrayOfInt9 = MATH.union(arrayOfInt4, arrayOfInt6);
/* 103 */         int[] arrayOfInt11 = new int[i8 + i9];
/* 104 */         for (i14 = 0; i14 < i8 + i9; i14++) arrayOfInt11[i14] = arrayOfInt1[arrayOfInt9[i14]];
/* 105 */         localObject = MATH.reorderBelief(arrayOfInt9, arrayOfInt11, (float[])localObject, arrayOfInt5);
/*     */         
/* 107 */         int[] arrayOfInt12 = MATH.sort(arrayOfInt6);
/* 108 */         int[] arrayOfInt13 = new int[i9];
/* 109 */         for (int i15 = 0; i15 < i9; i15++) arrayOfInt13[i15] = arrayOfInt1[arrayOfInt12[i15]];
/* 110 */         float[] arrayOfFloat3 = MATH.margin(arrayOfFloat1, arrayOfInt1, arrayOfInt6);
/* 111 */         arrayOfFloat3 = MATH.reorderBelief(arrayOfInt12, arrayOfInt13, arrayOfFloat3, arrayOfInt6);
/*     */         
/* 113 */         for (int i16 = 0; i16 < i9 + i8; i16++) arrayOfInt7[i16] = arrayOfInt1[arrayOfInt5[i16]];
/* 114 */         arrayOfFloat2 = MATH.division(arrayOfInt5, (float[])localObject, arrayOfInt6, arrayOfFloat3, arrayOfInt5, arrayOfInt7);
/*     */       }
/*     */       else {
/* 117 */         arrayOfInt8 = MATH.sort(arrayOfInt4);
/* 118 */         localObject = new int[i8];
/* 119 */         for (i12 = 0; i12 < i8; i12++) localObject[i12] = arrayOfInt1[arrayOfInt8[i12]];
/* 120 */         arrayOfFloat2 = MATH.margin(arrayOfFloat1, arrayOfInt1, arrayOfInt4);
/* 121 */         arrayOfFloat2 = MATH.reorderBelief(arrayOfInt8, (int[])localObject, arrayOfFloat2, arrayOfInt4);
/*     */         
/* 123 */         arrayOfInt5 = arrayOfInt4;
/* 124 */         for (i13 = 0; i13 < i8; i13++) { arrayOfInt7[i13] = arrayOfInt1[arrayOfInt4[i13]];
/*     */         }
/*     */       }
/*     */       
/* 128 */       for (int i10 = 0; i10 < arrayOfInt5.length; i10++) {
/* 129 */         System.out.print(arrayOfString[arrayOfInt5[i10]] + ":" + arrayOfInt1[arrayOfInt5[i10]] + "\t");
/*     */       }
/* 131 */       System.out.print("  P(");
/* 132 */       for (int i11 = 0; i11 < arrayOfInt4.length; i11++)
/* 133 */         System.out.print(arrayOfString[arrayOfInt4[i11]] + ":" + arrayOfInt1[arrayOfInt4[i11]] + " ");
/* 134 */       if (i9 != 0) {
/* 135 */         System.out.print("|");
/* 136 */         for (i12 = 0; i12 < i9; i12++)
/* 137 */           System.out.print(arrayOfString[arrayOfInt6[i12]] + ":" + arrayOfInt1[arrayOfInt6[i12]] + " ");
/*     */       }
/* 139 */       System.out.println(")");
/*     */       
/* 141 */       int[][] arrayOfInt10 = MATH.getAllMix(arrayOfInt7);
/* 142 */       for (int i13 = 0; i13 < arrayOfFloat2.length; i13++) {
/* 143 */         for (i14 = 0; i14 < i8 + i9; i14++) System.out.print(arrayOfInt10[i13][i14] + "\t");
/* 144 */         System.out.println("   " + arrayOfFloat2[i13]);
/*     */       }
/* 146 */       System.out.println("Specify another conditional (y/n)?");
/* 147 */       str2 = localBufferedReader1.readLine();
/*     */     }
/*     */   }
/*     */ }


/* Location:              /home/anali/INAOE/tesis/codigos/topub/exe/ww4.jar!/Ptb/Ptb.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */