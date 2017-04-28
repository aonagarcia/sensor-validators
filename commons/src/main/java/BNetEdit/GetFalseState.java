/*     */ package BNetEdit;
/*     */ 
/*     */ import Network.BayesNet;
/*     */ import java.io.PrintStream;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class GetFalseState
/*     */ {
/*     */   public static void main(String[] paramArrayOfString)
/*     */   {
/*  35 */     int i = paramArrayOfString.length;
/*  36 */     if (i < 1) {
/*  37 */       System.out.println("Use: java BNetEdit.GetFalseState bn_file ");
/*  38 */       System.exit(0);
/*     */     }
/*     */     
/*  41 */     String str = paramArrayOfString[0];
/*  42 */     BayesNet localBayesNet = BayesNet.load(str);
/*  43 */     int[] arrayOfInt = getFalseVlu(localBayesNet);
/*  44 */     echoFalseValue(localBayesNet, arrayOfInt);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   static int[] getFalseVlu(BayesNet paramBayesNet)
/*     */   {
/*  56 */     int i = paramBayesNet.getNodeCount();
/*  57 */     int[] arrayOfInt1 = new int[i];
/*  58 */     for (int j = 0; j < i; j++) arrayOfInt1[j] = -1;
/*  59 */     j = 0;
/*     */     Object localObject;
/*  61 */     int m; for (int k = 0; k < i; k++) {
/*  62 */       if (paramBayesNet.isRoot(k)) {
/*  63 */         localObject = paramBayesNet.getCondProb(k);
/*  64 */         m = paramBayesNet.getStateCount(k);
/*  65 */         arrayOfInt1[k] = getMostProbable((float[])localObject, m);
/*  66 */         j++;
/*     */       }
/*     */     }
/*  69 */     while (j < i)
/*  70 */       for (k = 0; k < i; k++)
/*  71 */         if (arrayOfInt1[k] == -1) {
/*  72 */           localObject = paramBayesNet.getParent(k);
/*  73 */           if (isProcessed(arrayOfInt1, (int[])localObject))
/*     */           {
/*  75 */             m = localObject.length;
/*  76 */             int[] arrayOfInt2 = new int[m];
/*  77 */             for (int n = 0; n < m; n++) arrayOfInt2[n] = arrayOfInt1[localObject[n]];
/*  78 */             arrayOfInt1[k] = paramBayesNet.getMostProbable(k, arrayOfInt2);
/*  79 */             j++;
/*     */           }
/*     */         }
/*  82 */     return arrayOfInt1;
/*     */   }
/*     */   
/*     */   static void echoFalseValue(BayesNet paramBayesNet, int[] paramArrayOfInt)
/*     */   {
/*  87 */     System.out.println("\nVar-index Var-label False-state");
/*  88 */     for (int i = 0; i < paramArrayOfInt.length; i++) {
/*  89 */       String str1 = paramBayesNet.getLabel(i);
/*  90 */       String str2 = paramBayesNet.getState(i, paramArrayOfInt[i]);
/*  91 */       System.out.println(i + " " + str1 + "\t" + str2);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   static boolean isProcessed(int[] paramArrayOfInt1, int[] paramArrayOfInt2)
/*     */   {
/*  98 */     for (int i = 0; i < paramArrayOfInt2.length; i++) {
/*  99 */       if (paramArrayOfInt1[paramArrayOfInt2[i]] == -1) return false;
/*     */     }
/* 101 */     return true;
/*     */   }
/*     */   
/*     */   static int getMostProbable(float[] paramArrayOfFloat, int paramInt)
/*     */   {
/* 106 */     int i = 0;
/* 107 */     float f = paramArrayOfFloat[0];
/* 108 */     for (int j = 1; j < paramInt; j++) {
/* 109 */       if (paramArrayOfFloat[j] > f) {
/* 110 */         i = j;f = paramArrayOfFloat[j];
/*     */       }
/*     */     }
/* 113 */     return i;
/*     */   }
/*     */ }


/* Location:              /home/anali/INAOE/tesis/codigos/topub/exe/ww4.jar!/BNetEdit/GetFalseState.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */