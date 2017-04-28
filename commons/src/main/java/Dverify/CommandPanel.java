/*     */ package Dverify;
/*     */ 
/*     */ import java.awt.Button;
/*     */ import java.awt.Cursor;
/*     */ import java.awt.Frame;
/*     */ import java.awt.event.ActionEvent;
/*     */ 
/*     */ class CommandPanel extends java.awt.Panel
/*     */ {
/*  10 */   public static final String loadLabel = new String("Load");
/*  11 */   public static final String commuLabel = new String("Verify");
/*  12 */   public static final String mvNetLabel = new String("Arrange");
/*  13 */   public static final String arrowLabel = new String("Arrow");
/*  14 */   public static final String aboutLabel = new String("About");
/*  15 */   public static final String quitLabel = new String("Quit");
/*     */   private Button loadButton;
/*     */   private Button commuButton;
/*     */   private Button mvNetButton;
/*     */   private Button arrowButton;
/*  20 */   private Button aboutButton; private Button quitButton; java.awt.Font helvetica14 = new java.awt.Font("Helvetic", 1, 14);
/*     */   
/*  22 */   Frame frame = null;
/*     */   NetworkPanel netPanel;
/*     */   
/*     */   CommandPanel(Frame paramFrame, NetworkPanel paramNetworkPanel)
/*     */   {
/*  27 */     this.frame = paramFrame;
/*  28 */     this.netPanel = paramNetworkPanel;
/*  29 */     setFont(this.helvetica14);
/*     */     
/*  31 */     int i = 1;int j = 6;int k = 6;int m = 0;
/*  32 */     setLayout(new java.awt.GridLayout(i, j, k, m));
/*  33 */     this.loadButton = new Button(loadLabel);
/*  34 */     this.commuButton = new Button(commuLabel);
/*  35 */     this.mvNetButton = new Button(mvNetLabel);
/*  36 */     this.arrowButton = new Button(arrowLabel);
/*  37 */     this.aboutButton = new Button(aboutLabel);
/*  38 */     this.quitButton = new Button(quitLabel);
/*     */     
/*  40 */     add(this.loadButton);
/*  41 */     add(this.commuButton);
/*  42 */     add(this.mvNetButton);
/*  43 */     add(this.arrowButton);
/*  44 */     add(this.aboutButton);
/*  45 */     add(this.quitButton);
/*     */     
/*  47 */     ButtonHandler localButtonHandler = new ButtonHandler(null);
/*  48 */     this.loadButton.addActionListener(localButtonHandler);
/*  49 */     this.commuButton.addActionListener(localButtonHandler);
/*  50 */     this.mvNetButton.addActionListener(localButtonHandler);
/*  51 */     this.arrowButton.addActionListener(localButtonHandler);
/*  52 */     this.aboutButton.addActionListener(localButtonHandler);
/*  53 */     this.quitButton.addActionListener(localButtonHandler);
/*     */   }
/*     */   
/*     */ 
/*     */   private String getHelpText(String paramString)
/*     */   {
/*  59 */     if (paramString.equals(commuLabel)) return commuText;
/*  60 */     if (paramString.equals(loadLabel)) return loadText;
/*  61 */     if (paramString.equals(mvNetLabel)) return mvNetText;
/*  62 */     if (paramString.equals(arrowLabel)) return arrowText;
/*  63 */     if (paramString.equals(aboutLabel)) return aboutText;
/*  64 */     if (paramString.equals(quitLabel)) return quitText;
/*  65 */     return "";
/*     */   }
/*     */   
/*  68 */   static final String aboutText = new String("WEBWEAVR-IV : Distributed Multi-agent MSBN verifier (2001-03)\nDeveloped by: Y. Xiang    Student Contributors: H. Geng and X. Chen ");
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*  73 */   static final String loadText = new String("Choose a subnet file to load.");
/*     */   
/*     */ 
/*  76 */   static final String commuText = new String("Communication starts.");
/*     */   
/*     */ 
/*  79 */   static final String mvNetText = new String("Reapprange display of the current network.");
/*     */   
/*     */ 
/*  82 */   static final String arrowText = new String("To change the size of arrow, keep clicking this button.");
/*     */   
/*     */ 
/*  85 */   static final String quitText = new String("Thank you for using WEBWEAVR-IV.");
/*     */   
/*     */   private class ButtonHandler implements java.awt.event.ActionListener {
/*     */     private ButtonHandler() {}
/*     */     
/*     */     public void actionPerformed(ActionEvent paramActionEvent) {
/*  91 */       if ((paramActionEvent.getSource() instanceof Button)) {
/*  92 */         String str = paramActionEvent.getActionCommand();
/*     */         
/*  94 */         if (CommandPanel.this.netPanel.isCommunicating()) {
/*  95 */           Network.HelpPanel.showError("Communication ongoing.");return;
/*     */         }
/*  97 */         if ((paramActionEvent.getSource() != CommandPanel.this.quitButton) && (paramActionEvent.getSource() != CommandPanel.this.loadButton) && (paramActionEvent.getSource() != CommandPanel.this.aboutButton) && (!CommandPanel.this.netPanel.hasSubdag()))
/*     */         {
/*     */ 
/*     */ 
/* 101 */           Network.HelpPanel.showError("Load subdag first.");return;
/*     */         }
/* 103 */         Network.HelpPanel.addHelp(CommandPanel.this.getHelpText(str));
/*     */         
/* 105 */         if (paramActionEvent.getSource() == CommandPanel.this.commuButton) {
/* 106 */           CommandPanel.this.frame.setCursor(new Cursor(3));
/* 107 */           CommandPanel.this.netPanel.communicate();
/* 108 */           CommandPanel.this.frame.setCursor(new Cursor(0));
/*     */         }
/* 110 */         else if (paramActionEvent.getSource() == CommandPanel.this.loadButton) {
/* 111 */           CommandPanel.this.frame.setCursor(new Cursor(3));
/* 112 */           CommandPanel.this.netPanel.load();
/* 113 */           CommandPanel.this.netPanel.getSet();
/* 114 */           CommandPanel.this.frame.setCursor(new Cursor(0));
/*     */         }
/* 116 */         else if (paramActionEvent.getSource() == CommandPanel.this.mvNetButton) {
/* 117 */           CommandPanel.this.frame.setCursor(new Cursor(3));
/* 118 */           CommandPanel.this.netPanel.moveNet();
/* 119 */           CommandPanel.this.frame.setCursor(new Cursor(0));
/*     */         }
/* 121 */         else if (paramActionEvent.getSource() == CommandPanel.this.arrowButton) {
/* 122 */           CommandPanel.this.frame.setCursor(new Cursor(3));
/* 123 */           CommandPanel.this.netPanel.setArrow();
/* 124 */           CommandPanel.this.frame.setCursor(new Cursor(0));
/*     */         }
/* 126 */         else if (paramActionEvent.getSource() == CommandPanel.this.quitButton) {
/* 127 */           CommandPanel.this.frame.dispatchEvent(new java.awt.event.WindowEvent(CommandPanel.this.frame, 201));
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              /home/anali/INAOE/tesis/codigos/topub/exe/ww4.jar!/Dverify/CommandPanel.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */