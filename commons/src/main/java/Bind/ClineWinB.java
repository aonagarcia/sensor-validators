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
/*    */ public class ClineWinB
/*    */ {
/*    */   static MainFrame mainFrame;
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
/* 30 */   static final String titleText = new String("Binder");
/*    */   
/*    */   public static void main(String[] paramArrayOfString) {
/* 33 */     if (paramArrayOfString.length == 0) mainFrame = new MainFrame(titleText, null); else {
/* 34 */       mainFrame = new MainFrame(titleText, paramArrayOfString[0]);
/*    */     }
/*    */   }
/*    */ }


/* Location:              /home/anali/INAOE/tesis/codigos/topub/exe/ww4.jar!/Bind/ClineWinB.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */