/*     */ package Thinker;
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
/*  13 */   public static final String evFormLabel = new String("Evi Form");
/*  14 */   public static final String beliefLabel = new String("Belief");
/*  15 */   public static final String probLabel = new String("Potential");
/*  16 */   public static final String mvNetLabel = new String("Arrange");
/*  17 */   public static final String arrowLabel = new String("Set Arrow");
/*  18 */   public static final String logLabel = new String("Log");
/*  19 */   public static final String mvNodeLabel = new String("Move Node");
/*  20 */   public static final String aboutLabel = new String("About");
/*  21 */   public static final String quitLabel = new String("Quit");
/*     */   Button loadButton;
/*     */   Button reqEviButton;
/*     */   Button eviButton;
/*     */   Button beliefButton;
/*     */   Button probButton;
/*  27 */   Button mvNetButton; Button arrowButton; Button logButton; Button evFormButton; Button mvNodeButton; Button aboutButton; Button quitButton; java.awt.Font helvetica14 = new java.awt.Font("Helvetic", 1, 14);
/*     */   
/*  29 */   Frame frame = null;
/*     */   NetworkPanel netPanel;
/*     */   
/*     */   CommandPanel(Frame paramFrame, NetworkPanel paramNetworkPanel)
/*     */   {
/*  34 */     this.frame = paramFrame;
/*  35 */     this.netPanel = paramNetworkPanel;
/*  36 */     setFont(this.helvetica14);
/*     */     
/*  38 */     int i = 2;int j = 6;int k = 6;int m = 0;
/*  39 */     setLayout(new java.awt.GridLayout(i, j, k, m));
/*  40 */     this.loadButton = new Button(loadLabel);
/*  41 */     this.reqEviButton = new Button(reqEviLabel);
/*  42 */     this.eviButton = new Button(eviLabel);
/*  43 */     this.evFormButton = new Button(evFormLabel);
/*  44 */     this.beliefButton = new Button(beliefLabel);
/*  45 */     this.probButton = new Button(probLabel);
/*  46 */     this.mvNetButton = new Button(mvNetLabel);
/*  47 */     this.arrowButton = new Button(arrowLabel);
/*  48 */     this.logButton = new Button(logLabel);
/*  49 */     this.mvNodeButton = new Button(mvNodeLabel);
/*  50 */     this.aboutButton = new Button(aboutLabel);
/*  51 */     this.quitButton = new Button(quitLabel);
/*     */     
/*  53 */     add(this.loadButton);
/*  54 */     add(this.reqEviButton);
/*  55 */     add(this.eviButton);
/*  56 */     add(this.beliefButton);
/*  57 */     add(this.probButton);
/*  58 */     add(this.mvNetButton);
/*  59 */     add(this.arrowButton);
/*  60 */     add(this.logButton);
/*  61 */     add(this.evFormButton);
/*  62 */     add(this.mvNodeButton);
/*  63 */     add(this.aboutButton);
/*  64 */     add(this.quitButton);
/*     */     
/*  66 */     ButtonHandler localButtonHandler = new ButtonHandler(null);
/*  67 */     this.loadButton.addActionListener(localButtonHandler);
/*  68 */     this.reqEviButton.addActionListener(localButtonHandler);
/*  69 */     this.eviButton.addActionListener(localButtonHandler);
/*  70 */     this.beliefButton.addActionListener(localButtonHandler);
/*  71 */     this.probButton.addActionListener(localButtonHandler);
/*  72 */     this.mvNetButton.addActionListener(localButtonHandler);
/*  73 */     this.arrowButton.addActionListener(localButtonHandler);
/*  74 */     this.logButton.addActionListener(localButtonHandler);
/*  75 */     this.evFormButton.addActionListener(localButtonHandler);
/*  76 */     this.mvNodeButton.addActionListener(localButtonHandler);
/*  77 */     this.aboutButton.addActionListener(localButtonHandler);
/*  78 */     this.quitButton.addActionListener(localButtonHandler);
/*     */   }
/*     */   
/*     */ 
/*     */   private String getHelpText(String paramString)
/*     */   {
/*  84 */     if (paramString.equals(loadLabel)) return loadText;
/*  85 */     if (paramString.equals(reqEviLabel)) return reqEviText;
/*  86 */     if (paramString.equals(eviLabel)) return eviText;
/*  87 */     if (paramString.equals(evFormLabel)) return evFormText;
/*  88 */     if (paramString.equals(beliefLabel)) return beliefText;
/*  89 */     if (paramString.equals(logLabel)) return logText;
/*  90 */     if (paramString.equals(arrowLabel)) return arrowText;
/*  91 */     if (paramString.equals(aboutLabel)) return aboutText;
/*  92 */     if (paramString.equals(quitLabel)) return quitText;
/*  93 */     if (paramString.equals(mvNodeLabel)) return noneText;
/*  94 */     if (paramString.equals(mvNetLabel)) return mvNetText;
/*  95 */     if (paramString.equals(probLabel)) return probText;
/*  96 */     return "";
/*     */   }
/*     */   
/*  99 */   static final String mvNodeText = new String("To move a node, click on it and drag it to the new position.");
/*     */   
/*     */ 
/* 102 */   static final String reqEviText = new String("Click a variable to request observation.");
/*     */   
/*     */ 
/* 105 */   static final String eviText = new String("Click a variable to enter evidence.");
/*     */   
/*     */ 
/* 108 */   static final String evFormText = new String("Toggle initial evidence function form (all 1 or all 0).");
/*     */   
/*     */ 
/* 111 */   static final String logText = new String("Current belief is saved in a LOG file.");
/*     */   
/*     */ 
/* 114 */   static final String beliefText = new String("Show the current belief.");
/*     */   
/*     */ 
/* 117 */   static final String mvNetText = new String("Reapprange display of the current network.");
/*     */   
/*     */ 
/* 120 */   static final String probText = new String("To see potential of a cluster, click on its center.");
/*     */   
/*     */ 
/* 123 */   static final String loadText = new String("Choose a Bayesian/ Markov network file to load.");
/*     */   
/*     */ 
/* 126 */   static final String arrowText = new String("To change the size of arrow, keep clicking this button.");
/*     */   
/*     */ 
/* 129 */   static final String aboutText = new String("   WEBWEAVR-IV : Belief Network Inference Engine (1997-2014) \n   Developed by : Y. Xiang   Student Contributors: J. Lee, T. Wan");
/*     */   
/*     */ 
/*     */ 
/* 133 */   static final String quitText = new String("Thank you for using WEBWEAVR-IV.");
/*     */   
/*     */ 
/* 136 */   static final String noneText = new String("Change position of a node.");
/*     */   
/*     */   private class ButtonHandler implements java.awt.event.ActionListener {
/*     */     private ButtonHandler() {}
/*     */     
/*     */     public void actionPerformed(ActionEvent paramActionEvent) {
/* 142 */       if ((paramActionEvent.getSource() instanceof Button)) {
/* 143 */         String str = paramActionEvent.getActionCommand();
/* 144 */         CommandPanel.this.netPanel.setMode(str);
/* 145 */         Network.HelpPanel.addHelp(CommandPanel.this.getHelpText(str));
/*     */         
/*     */ 
/* 148 */         if (paramActionEvent.getSource() == CommandPanel.this.loadButton) {
/* 149 */           CommandPanel.this.frame.setCursor(new Cursor(3));
/* 150 */           CommandPanel.this.netPanel.loadNetJt();
/* 151 */           CommandPanel.this.frame.setCursor(new Cursor(0));
/*     */         }
/* 153 */         else if (paramActionEvent.getSource() == CommandPanel.this.reqEviButton) {
/* 154 */           if (!CommandPanel.this.netPanel.hasNode()) Network.HelpPanel.showError("Load a BN first!");
/*     */         }
/* 156 */         else if (paramActionEvent.getSource() == CommandPanel.this.eviButton) {
/* 157 */           if (!CommandPanel.this.netPanel.hasNode()) Network.HelpPanel.showError("Load a BN first!"); else {
/* 158 */             CommandPanel.this.netPanel.repaint();
/*     */           }
/* 160 */         } else if (paramActionEvent.getSource() == CommandPanel.this.evFormButton) {
/* 161 */           CommandPanel.this.netPanel.switchEviForm();
/*     */         }
/* 163 */         else if (paramActionEvent.getSource() == CommandPanel.this.beliefButton) {
/* 164 */           if (!CommandPanel.this.netPanel.hasNode()) {
/* 165 */             Network.HelpPanel.showError("Load first!");
/*     */           } else {
/* 167 */             CommandPanel.this.frame.setCursor(new Cursor(3));
/* 168 */             CommandPanel.this.netPanel.showBelief();
/* 169 */             CommandPanel.this.frame.setCursor(new Cursor(0));
/*     */           }
/*     */         }
/* 172 */         else if (paramActionEvent.getSource() == CommandPanel.this.logButton) {
/* 173 */           if (!CommandPanel.this.netPanel.hasNode()) { Network.HelpPanel.showError("No belief to log!");
/*     */           } else {
/* 175 */             CommandPanel.this.frame.setCursor(new Cursor(3));
/* 176 */             CommandPanel.this.netPanel.logBelief();
/* 177 */             CommandPanel.this.frame.setCursor(new Cursor(0));
/*     */           }
/*     */         }
/* 180 */         else if (paramActionEvent.getSource() == CommandPanel.this.arrowButton) {
/* 181 */           CommandPanel.this.frame.setCursor(new Cursor(3));
/* 182 */           CommandPanel.this.netPanel.setArrow();
/* 183 */           CommandPanel.this.frame.setCursor(new Cursor(0));
/*     */         }
/* 185 */         else if (paramActionEvent.getSource() != CommandPanel.this.mvNodeButton)
/*     */         {
/* 187 */           if (paramActionEvent.getSource() == CommandPanel.this.probButton) {
/* 188 */             if (!CommandPanel.this.netPanel.hasNode()) Network.HelpPanel.showError("Please load first."); else {
/* 189 */               CommandPanel.this.netPanel.repaint();
/*     */             }
/* 191 */           } else if (paramActionEvent.getSource() == CommandPanel.this.mvNetButton) {
/* 192 */             CommandPanel.this.frame.setCursor(new Cursor(3));
/* 193 */             CommandPanel.this.netPanel.moveNet();
/* 194 */             CommandPanel.this.frame.setCursor(new Cursor(0));
/*     */           }
/* 196 */           else if (paramActionEvent.getSource() == CommandPanel.this.quitButton) {
/* 197 */             CommandPanel.this.frame.dispatchEvent(new java.awt.event.WindowEvent(CommandPanel.this.frame, 201));
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              /home/anali/INAOE/tesis/codigos/topub/exe/ww4.jar!/Thinker/CommandPanel.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */