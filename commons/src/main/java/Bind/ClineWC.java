/*    */ package Bind;
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
/*    */ public class ClineWC
/*    */ {
/*    */   static MainFrameC mainFrameC;
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
/* 31 */   static final String titleText = new String("Cell");
/*    */   
/*    */   public static void main(String[] paramArrayOfString) {
/* 34 */     if (paramArrayOfString.length == 0) mainFrameC = new MainFrameC(titleText, null); else {
/* 35 */       mainFrameC = new MainFrameC(titleText, paramArrayOfString);
/*    */     }
/*    */   }
/*    */ }


/* Location:              /home/anali/INAOE/tesis/codigos/topub/exe/ww4.jar!/Bind/ClineWC.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */