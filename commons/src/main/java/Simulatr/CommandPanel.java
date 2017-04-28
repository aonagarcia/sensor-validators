/*     */ package Simulatr;
/*     */ 
/*     */ import java.awt.Button;
/*     */ import java.awt.Cursor;
/*     */ import java.awt.Frame;
/*     */ import java.awt.event.ActionEvent;
/*     */ 
/*     */ class CommandPanel extends java.awt.Panel
/*     */ {
/*  10 */   public static final String loadLabel = new String("Load BN");
/*  11 */   public static final String loadEviLabel = new String("Load Evi");
/*  12 */   public static final String eviLabel = new String("Enter Evi");
/*  13 */   public static final String beliefLabel = new String("Belief");
/*  14 */   public static final String probLabel = new String("Potential");
/*  15 */   public static final String readyLabel = new String("Ready");
/*  16 */   public static final String mvNetLabel = new String("Arrange");
/*  17 */   public static final String arrowLabel = new String("Set Arrow");
/*  18 */   public static final String logLabel = new String("Log");
/*  19 */   public static final String aboutLabel = new String("About");
/*  20 */   public static final String quitLabel = new String("Quit");
/*     */   private Button loadButton;
/*     */   private Button loadEviButton;
/*     */   private Button eviButton;
/*     */   private Button beliefButton;
/*     */   private Button probButton;
/*  26 */   private Button readyButton; private Button mvNetButton; private Button arrowButton; private Button logButton; private Button aboutButton; private Button quitButton; java.awt.Font helvetica14 = new java.awt.Font("Helvetic", 1, 14);
/*     */   
/*  28 */   Frame frame = null;
/*     */   NetworkPanel netPanel;
/*     */   
/*     */   CommandPanel(Frame paramFrame, NetworkPanel paramNetworkPanel)
/*     */   {
/*  33 */     this.frame = paramFrame;
/*  34 */     this.netPanel = paramNetworkPanel;
/*  35 */     setFont(this.helvetica14);
/*     */     
/*  37 */     int i = 2;int j = 6;int k = 6;int m = 0;
/*  38 */     setLayout(new java.awt.GridLayout(i, j, k, m));
/*  39 */     this.loadButton = new Button(loadLabel);
/*  40 */     this.loadEviButton = new Button(loadEviLabel);
/*  41 */     this.eviButton = new Button(eviLabel);
/*  42 */     this.beliefButton = new Button(beliefLabel);
/*  43 */     this.probButton = new Button(probLabel);
/*  44 */     this.readyButton = new Button(readyLabel);
/*  45 */     this.mvNetButton = new Button(mvNetLabel);
/*  46 */     this.arrowButton = new Button(arrowLabel);
/*  47 */     this.logButton = new Button(logLabel);
/*  48 */     this.aboutButton = new Button(aboutLabel);
/*  49 */     this.quitButton = new Button(quitLabel);
/*     */     
/*  51 */     add(this.loadButton);
/*  52 */     add(this.loadEviButton);
/*  53 */     add(this.eviButton);
/*  54 */     add(this.beliefButton);
/*  55 */     add(this.probButton);
/*  56 */     add(this.readyButton);
/*  57 */     add(this.mvNetButton);
/*  58 */     add(this.arrowButton);
/*  59 */     add(this.logButton);
/*  60 */     add(this.aboutButton);
/*  61 */     add(this.quitButton);
/*     */     
/*  63 */     ButtonHandler localButtonHandler = new ButtonHandler(null);
/*  64 */     this.loadButton.addActionListener(localButtonHandler);
/*  65 */     this.loadEviButton.addActionListener(localButtonHandler);
/*  66 */     this.eviButton.addActionListener(localButtonHandler);
/*  67 */     this.beliefButton.addActionListener(localButtonHandler);
/*  68 */     this.probButton.addActionListener(localButtonHandler);
/*  69 */     this.readyButton.addActionListener(localButtonHandler);
/*  70 */     this.mvNetButton.addActionListener(localButtonHandler);
/*  71 */     this.arrowButton.addActionListener(localButtonHandler);
/*  72 */     this.logButton.addActionListener(localButtonHandler);
/*  73 */     this.aboutButton.addActionListener(localButtonHandler);
/*  74 */     this.quitButton.addActionListener(localButtonHandler);
/*     */   }
/*     */   
/*     */ 
/*     */   private String getHelpText(String paramString)
/*     */   {
/*  80 */     if (paramString.equals(loadLabel)) return loadText;
/*  81 */     if (paramString.equals(loadEviLabel)) return loadEviText;
/*  82 */     if (paramString.equals(eviLabel)) return eviText;
/*  83 */     if (paramString.equals(beliefLabel)) return beliefText;
/*  84 */     if (paramString.equals(logLabel)) return logText;
/*  85 */     if (paramString.equals(arrowLabel)) return arrowText;
/*  86 */     if (paramString.equals(aboutLabel)) return aboutText;
/*  87 */     if (paramString.equals(quitLabel)) return quitText;
/*  88 */     if (paramString.equals(readyLabel)) return readyText;
/*  89 */     if (paramString.equals(mvNetLabel)) return mvNetText;
/*  90 */     if (paramString.equals(probLabel)) return probText;
/*  91 */     return "";
/*     */   }
/*     */   
/*  94 */   static final String readyText = new String("Test if simulator is ready to serve observation request.");
/*     */   
/*     */ 
/*  97 */   static final String loadEviText = new String("Choose an evidence (.evi) file to load. ");
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
/* 121 */   static final String aboutText = new String("   WEBWEAVR-IV : Scenario Simulator (1999-2003) \n   Developed by : Y. Xiang   Student Contributor: T. Wan ");
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
/* 145 */         else if (paramActionEvent.getSource() == CommandPanel.this.loadEviButton) {
/* 146 */           if (!CommandPanel.this.netPanel.hasNode()) { Network.HelpPanel.showError("Load a BN first!");
/*     */           } else {
/* 148 */             CommandPanel.this.frame.setCursor(new Cursor(3));
/* 149 */             CommandPanel.this.netPanel.loadEviStamp();
/* 150 */             CommandPanel.this.frame.setCursor(new Cursor(0));
/*     */           }
/*     */         }
/* 153 */         else if (paramActionEvent.getSource() == CommandPanel.this.eviButton) {
/* 154 */           if (!CommandPanel.this.netPanel.hasNode()) Network.HelpPanel.showError("Load a BN first!"); else {
/* 155 */             CommandPanel.this.netPanel.repaint();
/*     */           }
/* 157 */         } else if (paramActionEvent.getSource() == CommandPanel.this.beliefButton) {
/* 158 */           if (!CommandPanel.this.netPanel.hasNode()) {
/* 159 */             Network.HelpPanel.showError("Load first!");
/*     */           } else {
/* 161 */             CommandPanel.this.frame.setCursor(new Cursor(3));
/* 162 */             CommandPanel.this.netPanel.showBelief();
/* 163 */             CommandPanel.this.frame.setCursor(new Cursor(0));
/*     */           }
/*     */         }
/* 166 */         else if (paramActionEvent.getSource() == CommandPanel.this.logButton) {
/* 167 */           if (!CommandPanel.this.netPanel.hasNode()) { Network.HelpPanel.showError("No belief to log!");
/*     */           } else {
/* 169 */             CommandPanel.this.frame.setCursor(new Cursor(3));
/* 170 */             CommandPanel.this.netPanel.logBelief();
/* 171 */             CommandPanel.this.frame.setCursor(new Cursor(0));
/*     */           }
/*     */         }
/* 174 */         else if (paramActionEvent.getSource() == CommandPanel.this.arrowButton) {
/* 175 */           CommandPanel.this.frame.setCursor(new Cursor(3));
/* 176 */           CommandPanel.this.netPanel.setArrow();
/* 177 */           CommandPanel.this.frame.setCursor(new Cursor(0));
/*     */         }
/* 179 */         else if (paramActionEvent.getSource() == CommandPanel.this.readyButton) {
/* 180 */           if (!CommandPanel.this.netPanel.hasNode()) Network.HelpPanel.showError("Load BN first!"); else {
/* 181 */             CommandPanel.this.netPanel.isServerReady();
/*     */           }
/* 183 */         } else if (paramActionEvent.getSource() == CommandPanel.this.probButton) {
/* 184 */           if (!CommandPanel.this.netPanel.hasNode()) Network.HelpPanel.showError("Load BN first."); else {
/* 185 */             CommandPanel.this.netPanel.repaint();
/*     */           }
/* 187 */         } else if (paramActionEvent.getSource() == CommandPanel.this.mvNetButton) {
/* 188 */           CommandPanel.this.frame.setCursor(new Cursor(3));
/* 189 */           CommandPanel.this.netPanel.moveNet();
/* 190 */           CommandPanel.this.frame.setCursor(new Cursor(0));
/*     */         }
/* 192 */         else if (paramActionEvent.getSource() == CommandPanel.this.quitButton) {
/* 193 */           CommandPanel.this.frame.dispatchEvent(new java.awt.event.WindowEvent(CommandPanel.this.frame, 201));
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              /home/anali/INAOE/tesis/codigos/topub/exe/ww4.jar!/Simulatr/CommandPanel.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */