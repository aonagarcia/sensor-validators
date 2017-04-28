/*    */ package Network;
/*    */ 
/*    */ import java.awt.Button;
/*    */ 
/*    */ class NextPassDialog extends java.awt.Dialog implements java.awt.event.ActionListener
/*    */ {
/*    */   java.awt.Frame win;
/*    */   MarkovNL caller;
/*    */   Button okayButton;
/*    */   Button helpButton;
/*    */   Button stopButton;
/*    */   
/*    */   NextPassDialog(java.awt.Frame paramFrame, MarkovNL paramMarkovNL, int paramInt1, int paramInt2)
/*    */   {
/* 15 */     super(paramFrame, "", true);
/* 16 */     setLocation(paramFrame.getLocationOnScreen().x + paramInt1, paramFrame.getLocationOnScreen().y + paramInt2);
/*    */     
/* 18 */     setFont(NetPaint.helvetica15);
/* 19 */     this.win = paramFrame;
/* 20 */     this.caller = paramMarkovNL;
/*    */     
/* 22 */     java.awt.Panel localPanel = new java.awt.Panel();add(localPanel);
/* 23 */     localPanel.add(new java.awt.Label("Continue?"));
/* 24 */     this.okayButton = new Button("Okay");
/* 25 */     this.okayButton.addActionListener(this);
/* 26 */     localPanel.add(this.okayButton);
/* 27 */     this.helpButton = new Button("HelpPanel");
/* 28 */     this.helpButton.addActionListener(this);
/* 29 */     localPanel.add(this.helpButton);
/* 30 */     this.stopButton = new Button("Stop");
/* 31 */     this.stopButton.addActionListener(this);
/* 32 */     localPanel.add(this.stopButton);
/*    */     
/* 34 */     pack();
/*    */   }
/*    */   
/*    */   public void actionPerformed(java.awt.event.ActionEvent paramActionEvent)
/*    */   {
/* 39 */     if (paramActionEvent.getSource() == this.helpButton) {
/* 40 */       HelpDialog localHelpDialog = new HelpDialog(this.win, HelpPanel.getText(), 0, 0);
/* 41 */       localHelpDialog.setVisible(true);
/*    */     }
/* 43 */     else if (paramActionEvent.getSource() == this.okayButton) {
/* 44 */       this.caller.nextAct(0);
/* 45 */       dispose();
/*    */     }
/* 47 */     else if (paramActionEvent.getSource() == this.stopButton) {
/* 48 */       this.caller.nextAct(1);
/* 49 */       dispose();
/*    */     }
/*    */   }
/*    */ }


/* Location:              /home/anali/INAOE/tesis/codigos/topub/exe/ww4.jar!/Network/NextPassDialog.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */