/*     */ package LThinker;
/*     */ 
/*     */ import java.awt.Button;
/*     */ import java.awt.Cursor;
/*     */ import java.awt.Frame;
/*     */ import java.awt.event.ActionEvent;
/*     */ 
/*     */ class CommandPanel extends java.awt.Panel
/*     */ {
/*  10 */   public static final String loadLabel = new String("Load");
/*  11 */   public static final String reqEviLabel = new String("Req Evi");
/*  12 */   public static final String eviLabel = new String("Enter Evi");
/*  13 */   public static final String beliefLabel = new String("Belief");
/*  14 */   public static final String probLabel = new String("Potential");
/*  15 */   public static final String mvNetLabel = new String("Arrange");
/*  16 */   public static final String arrowLabel = new String("Set Arrow");
/*  17 */   public static final String logLabel = new String("Log");
/*  18 */   public static final String mvNodeLabel = new String("Move Node");
/*  19 */   public static final String aboutLabel = new String("About");
/*  20 */   public static final String quitLabel = new String("Quit");
/*     */   private Button loadButton;
/*     */   private Button reqEviButton;
/*     */   private Button eviButton;
/*     */   private Button beliefButton;
/*  25 */   private Button probButton; private Button mvNetButton; private Button arrowButton; private Button logButton; private Button mvNodeButton; private Button aboutButton; private Button quitButton; java.awt.Font helvetica14 = new java.awt.Font("Helvetic", 1, 14);
/*     */   
/*  27 */   Frame frame = null;
/*     */   NetworkPanel netPanel;
/*     */   
/*     */   CommandPanel(Frame paramFrame, NetworkPanel paramNetworkPanel)
/*     */   {
/*  32 */     this.frame = paramFrame;
/*  33 */     this.netPanel = paramNetworkPanel;
/*  34 */     setFont(this.helvetica14);
/*     */     
/*  36 */     int i = 2;int j = 6;int k = 6;int m = 0;
/*  37 */     setLayout(new java.awt.GridLayout(i, j, k, m));
/*     */     
/*  39 */     this.loadButton = new Button(loadLabel);
/*  40 */     this.reqEviButton = new Button(reqEviLabel);
/*  41 */     this.eviButton = new Button(eviLabel);
/*  42 */     this.beliefButton = new Button(beliefLabel);
/*  43 */     this.probButton = new Button(probLabel);
/*  44 */     this.mvNetButton = new Button(mvNetLabel);
/*  45 */     this.arrowButton = new Button(arrowLabel);
/*  46 */     this.logButton = new Button(logLabel);
/*  47 */     this.mvNodeButton = new Button(mvNodeLabel);
/*  48 */     this.aboutButton = new Button(aboutLabel);
/*  49 */     this.quitButton = new Button(quitLabel);
/*     */     
/*  51 */     add(this.loadButton);
/*  52 */     add(this.reqEviButton);
/*  53 */     add(this.eviButton);
/*  54 */     add(this.beliefButton);
/*  55 */     add(this.probButton);
/*  56 */     add(this.mvNetButton);
/*  57 */     add(this.arrowButton);
/*  58 */     add(this.logButton);
/*  59 */     add(this.mvNodeButton);
/*  60 */     add(this.aboutButton);
/*  61 */     add(this.quitButton);
/*     */     
/*  63 */     ButtonHandler localButtonHandler = new ButtonHandler(null);
/*  64 */     this.loadButton.addActionListener(localButtonHandler);
/*  65 */     this.reqEviButton.addActionListener(localButtonHandler);
/*  66 */     this.eviButton.addActionListener(localButtonHandler);
/*  67 */     this.beliefButton.addActionListener(localButtonHandler);
/*  68 */     this.probButton.addActionListener(localButtonHandler);
/*  69 */     this.mvNetButton.addActionListener(localButtonHandler);
/*  70 */     this.arrowButton.addActionListener(localButtonHandler);
/*  71 */     this.logButton.addActionListener(localButtonHandler);
/*  72 */     this.mvNodeButton.addActionListener(localButtonHandler);
/*  73 */     this.aboutButton.addActionListener(localButtonHandler);
/*  74 */     this.quitButton.addActionListener(localButtonHandler);
/*     */   }
/*     */   
/*     */ 
/*     */   private String getHelpText(String paramString)
/*     */   {
/*  80 */     if (paramString.equals(loadLabel)) return loadText;
/*  81 */     if (paramString.equals(reqEviLabel)) return reqEviText;
/*  82 */     if (paramString.equals(eviLabel)) return eviText;
/*  83 */     if (paramString.equals(beliefLabel)) return beliefText;
/*  84 */     if (paramString.equals(logLabel)) return logText;
/*  85 */     if (paramString.equals(arrowLabel)) return arrowText;
/*  86 */     if (paramString.equals(aboutLabel)) return aboutText;
/*  87 */     if (paramString.equals(quitLabel)) return quitText;
/*  88 */     if (paramString.equals(mvNodeLabel)) return noneText;
/*  89 */     if (paramString.equals(mvNetLabel)) return mvNetText;
/*  90 */     if (paramString.equals(probLabel)) return probText;
/*  91 */     return "";
/*     */   }
/*     */   
/*  94 */   static final String mvNodeText = new String("To move a node, click on it and drag it to the new position.");
/*     */   
/*     */ 
/*  97 */   static final String reqEviText = new String("Click a variable to request observation.");
/*     */   
/*     */ 
/* 100 */   static final String eviText = new String("Click a variable to enter evidence.");
/*     */   
/*     */ 
/* 103 */   static final String logText = new String("Current belief is saved in a LOG file.");
/*     */   
/*     */ 
/* 106 */   static final String beliefText = new String("Show the current belief.");
/*     */   
/*     */ 
/* 109 */   static final String mvNetText = new String("Reapprange display of the current network.");
/*     */   
/*     */ 
/* 112 */   static final String probText = new String("To see potential of a cluster, click on its center.");
/*     */   
/*     */ 
/* 115 */   static final String loadText = new String("Choose a Bayesian/ Markov network file to load.");
/*     */   
/*     */ 
/* 118 */   static final String arrowText = new String("To change the size of arrow, keep clicking this button.");
/*     */   
/*     */ 
/* 121 */   static final String aboutText = new String("   WEBWEAVR-IV : Belief Network Lazy-Inference Engine (2002-03) \n   Developed by : Y. Xiang      Student Contributor:  X. Chen");
/*     */   
/*     */ 
/*     */ 
/* 125 */   static final String quitText = new String("Thank you for using WEBWEAVR-IV.");
/*     */   
/*     */ 
/* 128 */   static final String noneText = new String("This function is under development.");
/*     */   
/*     */   private class ButtonHandler implements java.awt.event.ActionListener {
/*     */     private ButtonHandler() {}
/*     */     
/*     */     public void actionPerformed(ActionEvent paramActionEvent) {
/* 134 */       if ((paramActionEvent.getSource() instanceof Button)) {
/* 135 */         String str = paramActionEvent.getActionCommand();
/* 136 */         CommandPanel.this.netPanel.setMode(str);
/* 137 */         Network.HelpPanel.addHelp(CommandPanel.this.getHelpText(str));
/*     */         
/*     */ 
/* 140 */         if (paramActionEvent.getSource() == CommandPanel.this.loadButton) {
/* 141 */           CommandPanel.this.frame.setCursor(new Cursor(3));
/* 142 */           CommandPanel.this.netPanel.loadNetJt();
/* 143 */           CommandPanel.this.frame.setCursor(new Cursor(0));
/*     */         }
/* 145 */         else if (paramActionEvent.getSource() == CommandPanel.this.reqEviButton) {
/* 146 */           if (!CommandPanel.this.netPanel.hasNode()) Network.HelpPanel.showError("Load BN first!");
/*     */         }
/* 148 */         else if (paramActionEvent.getSource() == CommandPanel.this.eviButton) {
/* 149 */           if (!CommandPanel.this.netPanel.hasNode()) Network.HelpPanel.showError("Load BN first!"); else {
/* 150 */             CommandPanel.this.netPanel.repaint();
/*     */           }
/* 152 */         } else if (paramActionEvent.getSource() == CommandPanel.this.beliefButton) {
/* 153 */           if (!CommandPanel.this.netPanel.hasNode()) { Network.HelpPanel.showError("Load BN first!");
/*     */           } else {
/* 155 */             CommandPanel.this.frame.setCursor(new Cursor(3));
/* 156 */             CommandPanel.this.netPanel.showBelief();
/* 157 */             CommandPanel.this.frame.setCursor(new Cursor(0));
/*     */           }
/*     */         }
/* 160 */         else if (paramActionEvent.getSource() == CommandPanel.this.logButton) {
/* 161 */           if (!CommandPanel.this.netPanel.hasNode()) { Network.HelpPanel.showError("Load BN first!");
/*     */           } else {
/* 163 */             CommandPanel.this.frame.setCursor(new Cursor(3));
/* 164 */             CommandPanel.this.netPanel.logBelief();
/* 165 */             CommandPanel.this.frame.setCursor(new Cursor(0));
/*     */           }
/*     */         }
/* 168 */         else if (paramActionEvent.getSource() == CommandPanel.this.arrowButton) {
/* 169 */           if (!CommandPanel.this.netPanel.hasNode()) { Network.HelpPanel.showError("Load BN first!");
/*     */           } else {
/* 171 */             CommandPanel.this.frame.setCursor(new Cursor(3));
/* 172 */             CommandPanel.this.netPanel.setArrow();
/* 173 */             CommandPanel.this.frame.setCursor(new Cursor(0));
/*     */           }
/*     */         }
/* 176 */         else if (paramActionEvent.getSource() == CommandPanel.this.mvNodeButton) {
/* 177 */           if (!CommandPanel.this.netPanel.hasNode()) Network.HelpPanel.showError("Load BN first!");
/*     */         }
/* 179 */         else if (paramActionEvent.getSource() == CommandPanel.this.probButton) {
/* 180 */           if (!CommandPanel.this.netPanel.hasNode()) Network.HelpPanel.showError("Load BN first!"); else {
/* 181 */             CommandPanel.this.netPanel.repaint();
/*     */           }
/* 183 */         } else if (paramActionEvent.getSource() == CommandPanel.this.mvNetButton) {
/* 184 */           if (!CommandPanel.this.netPanel.hasNode()) { Network.HelpPanel.showError("Load BN first!");
/*     */           } else {
/* 186 */             CommandPanel.this.frame.setCursor(new Cursor(3));
/* 187 */             CommandPanel.this.netPanel.moveNet();
/* 188 */             CommandPanel.this.frame.setCursor(new Cursor(0));
/*     */           }
/*     */         }
/* 191 */         else if (paramActionEvent.getSource() == CommandPanel.this.quitButton) {
/* 192 */           CommandPanel.this.frame.dispatchEvent(new java.awt.event.WindowEvent(CommandPanel.this.frame, 201));
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              /home/anali/INAOE/tesis/codigos/topub/exe/ww4.jar!/LThinker/CommandPanel.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */