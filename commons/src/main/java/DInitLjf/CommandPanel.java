/*     */ package DInitLjf;
/*     */ 
/*     */ import java.awt.Button;
/*     */ import java.awt.Cursor;
/*     */ import java.awt.Frame;
/*     */ import java.awt.event.ActionEvent;
/*     */ 
/*     */ class CommandPanel extends java.awt.Panel
/*     */ {
/*  10 */   public static final String loadLabel = new String("Load");
/*  11 */   public static final String beliefLabel = new String("Prior");
/*  12 */   public static final String saveLabel = new String("Save");
/*  13 */   public static final String mvNetLabel = new String("Arrange");
/*  14 */   public static final String arrowLabel = new String("Arrow");
/*  15 */   public static final String aboutLabel = new String("About");
/*  16 */   public static final String quitLabel = new String("Quit");
/*     */   private Button loadButton;
/*     */   private Button beliefButton;
/*     */   private Button saveButton;
/*     */   private Button mvNetButton;
/*     */   private Button arrowButton;
/*  22 */   private Button aboutButton; private Button quitButton; java.awt.Font helvetica14 = new java.awt.Font("Helvetic", 1, 14);
/*     */   
/*  24 */   Frame frame = null;
/*     */   NetworkPanel netPanel;
/*     */   
/*     */   CommandPanel(Frame paramFrame, NetworkPanel paramNetworkPanel)
/*     */   {
/*  29 */     this.frame = paramFrame;
/*  30 */     this.netPanel = paramNetworkPanel;
/*  31 */     setFont(this.helvetica14);
/*     */     
/*  33 */     int i = 1;int j = 7;int k = 6;int m = 0;
/*  34 */     setLayout(new java.awt.GridLayout(i, j, k, m));
/*  35 */     this.loadButton = new Button(loadLabel);
/*  36 */     this.beliefButton = new Button(beliefLabel);
/*  37 */     this.saveButton = new Button(saveLabel);
/*  38 */     this.mvNetButton = new Button(mvNetLabel);
/*  39 */     this.arrowButton = new Button(arrowLabel);
/*  40 */     this.aboutButton = new Button(aboutLabel);
/*  41 */     this.quitButton = new Button(quitLabel);
/*     */     
/*  43 */     add(this.loadButton);
/*  44 */     add(this.beliefButton);
/*  45 */     add(this.saveButton);
/*  46 */     add(this.mvNetButton);
/*  47 */     add(this.arrowButton);
/*  48 */     add(this.aboutButton);
/*  49 */     add(this.quitButton);
/*     */     
/*  51 */     ButtonHandler localButtonHandler = new ButtonHandler(null);
/*  52 */     this.loadButton.addActionListener(localButtonHandler);
/*  53 */     this.beliefButton.addActionListener(localButtonHandler);
/*  54 */     this.saveButton.addActionListener(localButtonHandler);
/*  55 */     this.mvNetButton.addActionListener(localButtonHandler);
/*  56 */     this.arrowButton.addActionListener(localButtonHandler);
/*  57 */     this.aboutButton.addActionListener(localButtonHandler);
/*  58 */     this.quitButton.addActionListener(localButtonHandler);
/*     */   }
/*     */   
/*     */ 
/*     */   private String getHelpText(String paramString)
/*     */   {
/*  64 */     if (paramString.equals(loadLabel)) return loadText;
/*  65 */     if (paramString.equals(beliefLabel)) return beliefText;
/*  66 */     if (paramString.equals(saveLabel)) return saveText;
/*  67 */     if (paramString.equals(mvNetLabel)) return mvNetText;
/*  68 */     if (paramString.equals(arrowLabel)) return arrowText;
/*  69 */     if (paramString.equals(aboutLabel)) return aboutText;
/*  70 */     if (paramString.equals(quitLabel)) return quitText;
/*  71 */     return "";
/*     */   }
/*     */   
/*  74 */   static final String aboutText = new String("WEBWEAVR-IV : Distributed Multi-agent MSBN Compiler\n     For Belief Initialization (2004)\nDeveloped by: Y. Xiang");
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*  79 */   static final String loadText = new String("Load the subnet of this agent.");
/*     */   
/*     */ 
/*  82 */   static final String beliefText = new String("Set prior belief. Communication starts.");
/*     */   
/*     */ 
/*  85 */   static final String saveText = new String("Save the junction tree and linkage trees of this agent.");
/*     */   
/*     */ 
/*  88 */   static final String mvNetText = new String("Reapprange display for the subnet of this agent.");
/*     */   
/*     */ 
/*  91 */   static final String arrowText = new String("To change the size of arrow, keep clicking this button.");
/*     */   
/*     */ 
/*  94 */   static final String quitText = new String("Thank you for using WEBWEAVR-IV.");
/*     */   
/*     */   private class ButtonHandler implements java.awt.event.ActionListener {
/*     */     private ButtonHandler() {}
/*     */     
/*     */     public void actionPerformed(ActionEvent paramActionEvent) {
/* 100 */       if ((paramActionEvent.getSource() instanceof Button)) {
/* 101 */         String str = paramActionEvent.getActionCommand();
/*     */         
/* 103 */         if (CommandPanel.this.netPanel.isCommunicating()) {
/* 104 */           Network.HelpPanel.showError("Communication ongoing.");return;
/*     */         }
/* 106 */         if ((paramActionEvent.getSource() != CommandPanel.this.quitButton) && (paramActionEvent.getSource() != CommandPanel.this.loadButton) && (paramActionEvent.getSource() != CommandPanel.this.aboutButton) && (!CommandPanel.this.netPanel.hasSubnet()))
/*     */         {
/*     */ 
/*     */ 
/* 110 */           Network.HelpPanel.showError("Load subnet first.");return;
/*     */         }
/*     */         
/* 113 */         Network.HelpPanel.addHelp(CommandPanel.this.getHelpText(str));
/*     */         
/* 115 */         if (paramActionEvent.getSource() == CommandPanel.this.loadButton) {
/* 116 */           CommandPanel.this.frame.setCursor(new Cursor(3));
/* 117 */           CommandPanel.this.netPanel.load();
/* 118 */           CommandPanel.this.netPanel.getSet();
/* 119 */           CommandPanel.this.frame.setCursor(new Cursor(0));
/*     */         }
/* 121 */         else if (paramActionEvent.getSource() == CommandPanel.this.beliefButton) {
/* 122 */           CommandPanel.this.frame.setCursor(new Cursor(3));
/* 123 */           CommandPanel.this.netPanel.initBelief();
/* 124 */           CommandPanel.this.frame.setCursor(new Cursor(0));
/*     */         }
/* 126 */         else if (paramActionEvent.getSource() == CommandPanel.this.saveButton) {
/* 127 */           CommandPanel.this.frame.setCursor(new Cursor(3));
/* 128 */           CommandPanel.this.netPanel.save();
/* 129 */           CommandPanel.this.frame.setCursor(new Cursor(0));
/*     */         }
/* 131 */         else if (paramActionEvent.getSource() == CommandPanel.this.mvNetButton) {
/* 132 */           CommandPanel.this.frame.setCursor(new Cursor(3));
/* 133 */           CommandPanel.this.netPanel.moveNet();
/* 134 */           CommandPanel.this.frame.setCursor(new Cursor(0));
/*     */         }
/* 136 */         else if (paramActionEvent.getSource() == CommandPanel.this.arrowButton) {
/* 137 */           CommandPanel.this.frame.setCursor(new Cursor(3));
/* 138 */           CommandPanel.this.netPanel.setArrow();
/* 139 */           CommandPanel.this.frame.setCursor(new Cursor(0));
/*     */         }
/* 141 */         else if (paramActionEvent.getSource() == CommandPanel.this.quitButton) {
/* 142 */           CommandPanel.this.frame.dispatchEvent(new java.awt.event.WindowEvent(CommandPanel.this.frame, 201));
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              /home/anali/INAOE/tesis/codigos/topub/exe/ww4.jar!/DInitLjf/CommandPanel.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */