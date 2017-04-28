/*    */ package BnToDb;
/*    */ 
/*    */ import Network.BayesNet;
/*    */ import Network.UTIL;
/*    */ import java.io.FileWriter;
/*    */ import java.io.IOException;
/*    */ import java.io.PrintStream;
/*    */ import java.io.PrintWriter;
/*    */ import java.util.Random;
/*    */ 
/*    */ 
/*    */ public class BnToDb
/*    */ {
/*    */   public static void main(String[] paramArrayOfString)
/*    */   {
/* 16 */     BayesNet localBayesNet = null;
/*    */     
/* 18 */     if (paramArrayOfString.length < 1) {
/* 19 */       str1 = UTIL.readString("Please enter .bn file path: ");
/*    */     } else
/* 21 */       str1 = new String(paramArrayOfString[0]);
/* 22 */     String str1 = new String(UTIL.replacePostfix(str1, "bn"));
/* 23 */     localBayesNet = BayesNet.load(str1);
/*    */     
/* 25 */     if (localBayesNet != null) { System.out.println("Network loaded.");
/*    */     } else {
/* 27 */       System.out.println("Error in loading network.");System.exit(0);
/*    */     }
/* 29 */     int i = localBayesNet.getNodeCount();
/*    */     
/*    */ 
/* 32 */     str1 = UTIL.readString("Enter output file name:");
/*    */     
/* 34 */     String str2 = UTIL.readString("Number of cases to be generated:");
/* 35 */     int j = UTIL.getInt(str2);
/* 36 */     System.out.println("# of cases = " + j);
/*    */     
/*    */ 
/* 39 */     str1 = new String(UTIL.replacePostfix(str1, "cod"));
/*    */     try {
/* 41 */       PrintWriter localPrintWriter1 = new PrintWriter(new FileWriter(str1));
/* 42 */       for (k = 0; k < i; k++) UTIL.savePoint(localPrintWriter1, localBayesNet.getPos(k));
/* 43 */       localPrintWriter1.close();
/*    */     } catch (IOException localIOException1) {
/* 45 */       System.out.println("Unable to save " + str1 + ".");
/*    */     }
/* 47 */     System.out.println("Saving " + str1 + " completed.");
/*    */     
/*    */ 
/* 50 */     int[] arrayOfInt = localBayesNet.getTopOrder();
/* 51 */     System.out.print("Node order = ");
/* 52 */     for (int k = 0; k < i; k++) System.out.print(arrayOfInt[k] + " ");
/* 53 */     System.out.println("");
/*    */     
/*    */ 
/* 56 */     Random localRandom = new Random();
/*    */     
/* 58 */     str1 = new String(UTIL.replacePostfix(str1, "raw"));
/*    */     try {
/* 60 */       PrintWriter localPrintWriter2 = new PrintWriter(new FileWriter(str1));
/*    */       
/* 62 */       String[] arrayOfString2 = localBayesNet.getLabel();
/* 63 */       arrayOfString2 = UTIL.evenLength(arrayOfString2, 12);
/* 64 */       str2 = new String("");
/* 65 */       for (int m = 0; m < i; m++) str2 = new String(str2 + arrayOfString2[m]);
/* 66 */       localPrintWriter2.println(str2);
/*    */       
/* 68 */       for (int n = 0; n < j; n++) {
/* 69 */         localBayesNet.setVarValue(arrayOfInt, localRandom);
/* 70 */         String[] arrayOfString1 = localBayesNet.getStrValue();
/* 71 */         arrayOfString1 = UTIL.evenLength(arrayOfString1, 12);
/*    */         
/* 73 */         str2 = new String("");
/* 74 */         for (int i1 = 0; i1 < i; i1++) str2 = new String(str2 + arrayOfString1[i1]);
/* 75 */         localPrintWriter2.println(str2);
/*    */         
/* 77 */         if ((n > 0) && (n % 100 == 0)) System.out.println(n + " cases completed.");
/*    */       }
/* 79 */       localPrintWriter2.close();
/*    */     } catch (IOException localIOException2) {
/* 81 */       System.out.println("Unable to save " + str1 + ".");
/*    */     }
/* 83 */     System.out.println("Saving " + str1 + " completed.");
/*    */   }
/*    */ }


/* Location:              /home/anali/INAOE/tesis/codigos/topub/exe/ww4.jar!/BnToDb/BnToDb.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */