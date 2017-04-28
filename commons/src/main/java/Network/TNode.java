/*    */ package Network;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class TNode
/*    */   extends DNode
/*    */ {
/* 13 */   int[] onBorder = { -1, -1, -1, -1 };
/*    */   
/*    */   static final int FORWARD = 0;
/*    */   
/*    */   static final int RUNBDR = 1;
/*    */   static final int RUNFTSD = 2;
/*    */   static final int NOT_ON = -1;
/*    */   static final int ON_FST_FW = 0;
/*    */   static final int ON_SND_FW = 1;
/*    */   static final int ON_RUN_BDR = 2;
/*    */   static final int ON_FST_RB = 3;
/*    */   static final int ON_SND_RB = 4;
/*    */   
/*    */   TNode() {}
/*    */   
/*    */   TNode(TNode paramTNode)
/*    */   {
/* 30 */     super(paramTNode);
/* 31 */     this.onBorder = UTIL.getDuplicate(paramTNode.getOnBdr());
/*    */   }
/*    */   
/*    */   boolean isOnBdr(int paramInt1, int paramInt2)
/*    */   {
/* 36 */     if (this.onBorder[paramInt1] == paramInt2) return true;
/* 37 */     return false;
/*    */   }
/*    */   
/*    */   void setOnBdr(int paramInt1, int paramInt2)
/*    */   {
/* 42 */     this.onBorder[paramInt1] = paramInt2;
/*    */   }
/*    */   
/*    */   int[] getOnBdr()
/*    */   {
/* 47 */     return UTIL.getDuplicate(this.onBorder);
/*    */   }
/*    */ }


/* Location:              /home/anali/INAOE/tesis/codigos/topub/exe/ww4.jar!/Network/TNode.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */