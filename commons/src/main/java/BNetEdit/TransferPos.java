/*    */ package BNetEdit;
/*    */ 
/*    */ import java.io.BufferedReader;
/*    */ import java.io.FileReader;
/*    */ import java.io.FileWriter;
/*    */ import java.io.IOException;
/*    */ import java.io.PrintStream;
/*    */ import java.io.PrintWriter;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class TransferPos
/*    */ {
/*    */   public static void main(String[] paramArrayOfString)
/*    */   {
/* 31 */     int i = paramArrayOfString.length;
/* 32 */     if (i < 3) {
/* 33 */       System.out.println("Use: java BNetEdit.TransferPos bn1 bn2 bn3");
/* 34 */       System.exit(0);
/*    */     }
/*    */     
/* 37 */     String str1 = paramArrayOfString[0];
/* 38 */     String str2 = paramArrayOfString[1];
/* 39 */     String str3 = paramArrayOfString[2];
/* 40 */     replacePosLine(str1, str2, str3);
/* 41 */     System.out.println("Writing " + str3 + " is complete.");
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   static void replacePosLine(String paramString1, String paramString2, String paramString3)
/*    */   {
/*    */     try
/*    */     {
/* 50 */       BufferedReader localBufferedReader1 = new BufferedReader(new FileReader(paramString1));
/* 51 */       BufferedReader localBufferedReader2 = new BufferedReader(new FileReader(paramString2));
/* 52 */       PrintWriter localPrintWriter = new PrintWriter(new FileWriter(paramString3));
/*    */       
/* 54 */       String str1 = localBufferedReader1.readLine();
/* 55 */       String str2 = localBufferedReader2.readLine();
/* 56 */       while (str1 != null) {
/* 57 */         if (str1.indexOf("Coordinate") < 0) localPrintWriter.println(str2); else
/* 58 */           localPrintWriter.println(str1);
/* 59 */         str1 = localBufferedReader1.readLine();
/* 60 */         str2 = localBufferedReader2.readLine();
/*    */       }
/*    */       
/* 63 */       localBufferedReader1.close();localBufferedReader2.close();localPrintWriter.close();
/* 64 */     } catch (IOException localIOException) { System.out.println("Error file operation");
/*    */     }
/*    */   }
/*    */ }


/* Location:              /home/anali/INAOE/tesis/codigos/topub/exe/ww4.jar!/BNetEdit/TransferPos.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */