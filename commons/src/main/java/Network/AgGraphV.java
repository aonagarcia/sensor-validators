/*    */ package Network;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AgGraphV
/*    */   extends AgGraph
/*    */   implements IntAgtV
/*    */ {
/*    */   public int[] markRootLeaf(int[] paramArrayOfInt)
/*    */   {
/* 17 */     return this.net.markRootLeaf(paramArrayOfInt);
/*    */   }
/*    */   
/*    */   public void setMark(int paramInt)
/*    */   {
/* 22 */     this.net.setMark(paramInt);
/*    */   }
/*    */   
/*    */   public boolean isMarked(int paramInt)
/*    */   {
/* 27 */     return this.net.isMarked(paramInt);
/*    */   }
/*    */   
/*    */   public boolean isMarked() {
/* 31 */     return this.net.isMarked();
/*    */   }
/*    */   
/*    */ 
/*    */   public boolean hasUnmarkedParent(int paramInt)
/*    */   {
/* 37 */     return this.net.hasUnmarkedParent(paramInt);
/*    */   }
/*    */   
/*    */ 
/*    */   public boolean hasUnmarkedChild(int paramInt)
/*    */   {
/* 43 */     return this.net.hasUnmarkedChild(paramInt);
/*    */   }
/*    */ }


/* Location:              /home/anali/INAOE/tesis/codigos/topub/exe/ww4.jar!/Network/AgGraphV.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */