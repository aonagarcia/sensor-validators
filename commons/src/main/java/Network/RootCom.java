/*    */ package Network;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class RootCom
/*    */   extends Thread
/*    */ {
/* 10 */   IntRoot agent = null;
/* 11 */   int msgType = -2;
/*    */   
/*    */   RootCom(IntRoot paramIntRoot, int paramInt) {
/* 14 */     this.agent = paramIntRoot;this.msgType = paramInt;
/*    */   }
/*    */   
/*    */   public void run() {
/* 18 */     HelpPanel.addHelp("Communication root thread started.");
/* 19 */     this.agent.setCtrlFlag("communication", "communicate");
/* 20 */     this.agent.actAll(-1, this.msgType);
/*    */     
/* 22 */     this.agent.setCtrlFlag("communication", "end_of_communicate");
/* 23 */     this.agent.setMsgFlag(this.msgType);
/*    */   }
/*    */ }


/* Location:              /home/anali/INAOE/tesis/codigos/topub/exe/ww4.jar!/Network/RootCom.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */