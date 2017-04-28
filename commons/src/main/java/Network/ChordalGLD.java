/*    */ package Network;
/*    */ 
/*    */ import java.io.BufferedReader;
/*    */ import java.io.FileWriter;
/*    */ import java.io.IOException;
/*    */ import java.io.PrintWriter;
/*    */ 
/*    */ public class ChordalGLD extends ChordalGraph
/*    */ {
/*    */   public ChordalGLD() {}
/*    */   
/*    */   public ChordalGLD(ChordalGLD paramChordalGLD)
/*    */   {
/* 14 */     super(paramChordalGLD);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public int[][] loadLinkPat(BufferedReader paramBufferedReader)
/*    */   {
/* 22 */     int[][] arrayOfInt = new int[2][];
/*    */     try {
/* 24 */       int i = UTIL.loadInt(paramBufferedReader);
/* 25 */       arrayOfInt[0] = new int[1];
/* 26 */       arrayOfInt[0][0] = UTIL.loadInt(paramBufferedReader);
/* 27 */       arrayOfInt[1] = UTIL.loadIntListMLine(paramBufferedReader, i);
/* 28 */       paramBufferedReader.close();
/* 29 */       return arrayOfInt;
/*    */     }
/*    */     catch (IOException localIOException) {
/* 32 */       HelpPanel.showError("Unable to read pat file!");
/*    */     }
/* 34 */     return (int[][])null;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   void saveLinkPat(String paramString, int[] paramArrayOfInt, int paramInt)
/*    */   {
/* 44 */     int i = paramArrayOfInt.length;
/*    */     try {
/* 46 */       PrintWriter localPrintWriter = new PrintWriter(new FileWriter(paramString));
/* 47 */       localPrintWriter.println(i + " total_#_links");
/* 48 */       localPrintWriter.println(paramInt + " current_lookahead_links");
/* 49 */       for (int j = 0; j < i; j++) {
/* 50 */         if ((j > 0) && (j % 30 == 0)) localPrintWriter.println();
/* 51 */         localPrintWriter.print(" " + paramArrayOfInt[j]);
/*    */       }
/* 53 */       localPrintWriter.println();
/* 54 */       localPrintWriter.close();
/*    */     } catch (IOException localIOException) {
/* 56 */       HelpPanel.showError("Unable to save pat file: " + paramString);
/*    */     }
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public void completeInterface(BayesNet paramBayesNet)
/*    */   {
/* 64 */     int[] arrayOfInt1 = paramBayesNet.getBorder(2, 3);
/* 65 */     int[] arrayOfInt2 = paramBayesNet.getBorder(2, 4);
/*    */     int[] arrayOfInt3;
/* 67 */     int[] arrayOfInt4; for (int i = 0; i < arrayOfInt1.length; i++) {
/* 68 */       arrayOfInt3 = MATH.delMember(arrayOfInt1[i], arrayOfInt1);
/* 69 */       arrayOfInt4 = MATH.union(arrayOfInt3, this.nd[arrayOfInt1[i]].getNeighbor());
/* 70 */       this.nd[arrayOfInt1[i]].setNeighbor(arrayOfInt4);
/*    */     }
/*    */     
/* 73 */     for (i = 0; i < arrayOfInt2.length; i++) {
/* 74 */       arrayOfInt3 = MATH.delMember(arrayOfInt2[i], arrayOfInt2);
/* 75 */       arrayOfInt4 = MATH.union(arrayOfInt3, this.nd[arrayOfInt2[i]].getNeighbor());
/* 76 */       this.nd[arrayOfInt2[i]].setNeighbor(arrayOfInt4);
/*    */     }
/*    */   }
/*    */ }


/* Location:              /home/anali/INAOE/tesis/codigos/topub/exe/ww4.jar!/Network/ChordalGLD.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */