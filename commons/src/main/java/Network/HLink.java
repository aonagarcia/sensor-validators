/*    */ package Network;
/*    */ 
/*    */ import java.io.BufferedReader;
/*    */ import java.io.PrintStream;
/*    */ import java.io.PrintWriter;
/*    */ 
/*    */ public class HLink
/*    */ {
/*    */   private int nb;
/* 10 */   private int[] its = null;
/*    */   
/*    */ 
/* 13 */   HLink() { this(-1, null); }
/*    */   
/*    */   public HLink(int paramInt, int[] paramArrayOfInt) {
/* 16 */     this.nb = paramInt;
/* 17 */     this.its = UTIL.getDuplicate(paramArrayOfInt);
/*    */   }
/*    */   
/*    */   HLink(HLink paramHLink) {
/* 21 */     setHlink(paramHLink);
/*    */   }
/*    */   
/*    */   void setHlink(HLink paramHLink)
/*    */   {
/* 26 */     if (paramHLink == null) {
/* 27 */       this.nb = -1;this.its = null;return;
/*    */     }
/* 29 */     this.nb = paramHLink.nb;
/* 30 */     this.its = UTIL.getDuplicate(paramHLink.its);
/*    */   }
/*    */   
/*    */   boolean isNeighbor(int paramInt)
/*    */   {
/* 35 */     return paramInt == this.nb;
/*    */   }
/*    */   
/*    */   int getNeighbor()
/*    */   {
/* 40 */     return this.nb;
/*    */   }
/*    */   
/*    */   void setNeighbor(int paramInt)
/*    */   {
/* 45 */     this.nb = paramInt;
/*    */   }
/*    */   
/*    */   int[] getSepset()
/*    */   {
/* 50 */     return UTIL.getDuplicate(this.its);
/*    */   }
/*    */   
/*    */   int getSepset(int paramInt) {
/* 54 */     return this.its[paramInt];
/*    */   }
/*    */   
/*    */   int getSepsetSize()
/*    */   {
/* 59 */     return this.its == null ? 0 : this.its.length;
/*    */   }
/*    */   
/*    */   void saveHyperLink(PrintWriter paramPrintWriter)
/*    */   {
/* 64 */     paramPrintWriter.println(this.nb + " neighbor");
/* 65 */     UTIL.saveIntList(paramPrintWriter, this.its, "sepset");
/*    */   }
/*    */   
/*    */   static HLink loadHyperLink(BufferedReader paramBufferedReader)
/*    */   {
/* 70 */     int i = UTIL.loadInt(paramBufferedReader);
/* 71 */     int[] arrayOfInt = UTIL.loadIntList(paramBufferedReader);
/* 72 */     return new HLink(i, arrayOfInt);
/*    */   }
/*    */   
/*    */   void showHLink()
/*    */   {
/* 77 */     HelpPanel.addHelp("  HLink nb = " + this.nb);
/* 78 */     HelpPanel.appendList(" its = ", this.its);
/*    */   }
/*    */   
/* 81 */   void printHLink() { System.out.print(" HLink nb = " + this.nb);
/* 82 */     UTIL.showList(" its = ", this.its);
/*    */   }
/*    */ }


/* Location:              /home/anali/INAOE/tesis/codigos/topub/exe/ww4.jar!/Network/HLink.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */