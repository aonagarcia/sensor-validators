/*     */ package DMasMsbn;
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
/*  15 */   public static final String commuLabel = new String("Commu");
/*  16 */   public static final String quitLabel = new String("Quit");
/*     */   
/*  18 */   public static final String mvNetLabel = new String("Arrange");
/*  19 */   public static final String arrowLabel = new String("Arrow");
/*  20 */   public static final String logLabel = new String("Log");
/*  21 */   public static final String aboutLabel = new String("About");
/*  22 */   public static final String svPotLabel = new String("Save Pot");
/*  23 */   public static final String ldPotLabel = new String("Load Pot");
/*     */   private Button loadButton;
/*     */   private Button reqEviButton;
/*     */   private Button eviButton;
/*     */   private Button beliefButton;
/*     */   private Button probButton;
/*     */   private Button commuButton;
/*  30 */   private Button quitButton; private Button mvNetButton; private Button arrowButton; private Button logButton; private Button aboutButton; private Button svPotButton; private Button ldPotButton; java.awt.Font helvetica14 = new java.awt.Font("Helvetic", 1, 14);
/*     */   
/*  32 */   Frame frame = null;
/*     */   NetworkPanel netPanel;
/*     */   
/*     */   CommandPanel(Frame paramFrame, NetworkPanel paramNetworkPanel)
/*     */   {
/*  37 */     this.frame = paramFrame;
/*  38 */     this.netPanel = paramNetworkPanel;
/*  39 */     setFont(this.helvetica14);
/*     */     
/*  41 */     int i = 2;int j = 5;int k = 6;int m = 0;
/*  42 */     setLayout(new java.awt.GridLayout(i, j, k, m));
/*     */     
/*  44 */     this.loadButton = new Button(loadLabel);
/*  45 */     this.reqEviButton = new Button(reqEviLabel);
/*  46 */     this.eviButton = new Button(eviLabel);
/*  47 */     this.beliefButton = new Button(beliefLabel);
/*  48 */     this.probButton = new Button(probLabel);
/*  49 */     this.commuButton = new Button(commuLabel);
/*  50 */     this.quitButton = new Button(quitLabel);
/*  51 */     this.mvNetButton = new Button(mvNetLabel);
/*  52 */     this.arrowButton = new Button(arrowLabel);
/*  53 */     this.logButton = new Button(logLabel);
/*  54 */     this.aboutButton = new Button(aboutLabel);
/*  55 */     this.svPotButton = new Button(svPotLabel);
/*  56 */     this.ldPotButton = new Button(ldPotLabel);
/*     */     
/*  58 */     add(this.loadButton);
/*  59 */     add(this.reqEviButton);
/*  60 */     add(this.eviButton);
/*  61 */     add(this.beliefButton);
/*  62 */     add(this.probButton);
/*  63 */     add(this.commuButton);
/*  64 */     add(this.quitButton);
/*  65 */     add(this.mvNetButton);
/*  66 */     add(this.arrowButton);
/*  67 */     add(this.logButton);
/*  68 */     add(this.aboutButton);
/*  69 */     add(this.svPotButton);
/*  70 */     add(this.ldPotButton);
/*     */     
/*  72 */     ButtonHandler localButtonHandler = new ButtonHandler(null);
/*  73 */     this.loadButton.addActionListener(localButtonHandler);
/*  74 */     this.reqEviButton.addActionListener(localButtonHandler);
/*  75 */     this.eviButton.addActionListener(localButtonHandler);
/*  76 */     this.beliefButton.addActionListener(localButtonHandler);
/*  77 */     this.probButton.addActionListener(localButtonHandler);
/*  78 */     this.commuButton.addActionListener(localButtonHandler);
/*  79 */     this.quitButton.addActionListener(localButtonHandler);
/*  80 */     this.mvNetButton.addActionListener(localButtonHandler);
/*  81 */     this.arrowButton.addActionListener(localButtonHandler);
/*  82 */     this.logButton.addActionListener(localButtonHandler);
/*  83 */     this.aboutButton.addActionListener(localButtonHandler);
/*  84 */     this.svPotButton.addActionListener(localButtonHandler);
/*  85 */     this.ldPotButton.addActionListener(localButtonHandler);
/*     */   }
/*     */   
/*     */ 
/*     */   private String getHelpText(String paramString)
/*     */   {
/*  91 */     if (paramString.equals(loadLabel)) return loadText;
/*  92 */     if (paramString.equals(reqEviLabel)) return reqEviText;
/*  93 */     if (paramString.equals(eviLabel)) return eviText;
/*  94 */     if (paramString.equals(beliefLabel)) return beliefText;
/*  95 */     if (paramString.equals(probLabel)) return probText;
/*  96 */     if (paramString.equals(commuLabel)) return commuText;
/*  97 */     if (paramString.equals(quitLabel)) return quitText;
/*  98 */     if (paramString.equals(mvNetLabel)) return mvNetText;
/*  99 */     if (paramString.equals(arrowLabel)) return arrowText;
/* 100 */     if (paramString.equals(logLabel)) return logText;
/* 101 */     if (paramString.equals(aboutLabel)) return aboutText;
/* 102 */     if (paramString.equals(svPotLabel)) return svPotText;
/* 103 */     if (paramString.equals(ldPotLabel)) return ldPotText;
/* 104 */     return "";
/*     */   }
/*     */   
/* 107 */   static final String aboutText = new String("WEBWEAVR-IV : Distributed Multi-agent MSBN Inf. Engine (1998-2003)\nDeveloped by: Y. Xiang    Student Contributor: H. Geng ");
/*     */   
/*     */ 
/*     */ 
/*     */ 
/* 112 */   static final String loadText = new String("Choose a subnet file to load.");
/*     */   
/*     */ 
/* 115 */   static final String reqEviText = new String("Click a variable to request observation.");
/*     */   
/*     */ 
/* 118 */   static final String eviText = new String("Click a variable to enter evidence.");
/*     */   
/*     */ 
/* 121 */   static final String beliefText = new String("Show current belief of each variable.");
/*     */   
/*     */ 
/* 124 */   static final String probText = new String("To see potential of a cluster, click on its center.");
/*     */   
/*     */ 
/* 127 */   static final String commuText = new String("Start communication.");
/*     */   
/*     */ 
/* 130 */   static final String logText = new String("Save current belief on a log file.");
/*     */   
/*     */ 
/* 133 */   static final String mvNetText = new String("Reapprange display of the current network.");
/*     */   
/*     */ 
/* 136 */   static final String arrowText = new String("To change the size of arrow, keep clicking this button.");
/*     */   
/*     */ 
/* 139 */   static final String quitText = new String("Thank you for using WEBWEAVR-IV.");
/*     */   
/*     */ 
/* 142 */   static final String svPotText = new String("Save potential over a subset of variables in a cluster.");
/*     */   
/*     */ 
/* 145 */   static final String ldPotText = new String("Multiply a cluster potential by a given potential over \na subset of variables in the cluster.");
/*     */   
/*     */   private class ButtonHandler implements java.awt.event.ActionListener
/*     */   {
/*     */     private ButtonHandler() {}
/*     */     
/*     */     public void actionPerformed(ActionEvent paramActionEvent) {
/* 152 */       if ((paramActionEvent.getSource() instanceof Button)) {
/* 153 */         String str = paramActionEvent.getActionCommand();
/*     */         
/* 155 */         if (CommandPanel.this.netPanel.isCommunicating()) {
/* 156 */           Network.HelpPanel.showError("Communication ongoing.");return;
/*     */         }
/* 158 */         if ((paramActionEvent.getSource() != CommandPanel.this.quitButton) && (paramActionEvent.getSource() != CommandPanel.this.loadButton) && (paramActionEvent.getSource() != CommandPanel.this.aboutButton) && (!CommandPanel.this.netPanel.hasSubnet()))
/*     */         {
/*     */ 
/*     */ 
/* 162 */           Network.HelpPanel.showError("Load subnet first.");return;
/*     */         }
/*     */         
/* 165 */         CommandPanel.this.netPanel.setMode(str);
/* 166 */         Network.HelpPanel.addHelp(CommandPanel.this.getHelpText(str));
/*     */         
/* 168 */         if (paramActionEvent.getSource() == CommandPanel.this.eviButton) {
/* 169 */           CommandPanel.this.netPanel.repaint();
/*     */         }
/* 171 */         else if (paramActionEvent.getSource() != CommandPanel.this.reqEviButton)
/*     */         {
/* 173 */           if (paramActionEvent.getSource() == CommandPanel.this.beliefButton) {
/* 174 */             CommandPanel.this.frame.setCursor(new Cursor(3));
/* 175 */             CommandPanel.this.netPanel.showBelief();
/* 176 */             CommandPanel.this.frame.setCursor(new Cursor(0));
/*     */           }
/* 178 */           else if (paramActionEvent.getSource() == CommandPanel.this.probButton) {
/* 179 */             CommandPanel.this.netPanel.repaint();
/*     */           }
/* 181 */           else if (paramActionEvent.getSource() == CommandPanel.this.commuButton) {
/* 182 */             CommandPanel.this.frame.setCursor(new Cursor(3));
/* 183 */             CommandPanel.this.netPanel.communicate();
/* 184 */             CommandPanel.this.frame.setCursor(new Cursor(0));
/*     */           }
/* 186 */           else if (paramActionEvent.getSource() == CommandPanel.this.logButton) {
/* 187 */             CommandPanel.this.frame.setCursor(new Cursor(3));
/* 188 */             CommandPanel.this.netPanel.logBelief();
/* 189 */             CommandPanel.this.frame.setCursor(new Cursor(0));
/*     */           }
/* 191 */           else if (paramActionEvent.getSource() == CommandPanel.this.loadButton) {
/* 192 */             CommandPanel.this.frame.setCursor(new Cursor(3));
/* 193 */             CommandPanel.this.netPanel.load();
/* 194 */             CommandPanel.this.netPanel.getSet();
/* 195 */             CommandPanel.this.frame.setCursor(new Cursor(0));
/*     */           }
/* 197 */           else if (paramActionEvent.getSource() == CommandPanel.this.mvNetButton) {
/* 198 */             CommandPanel.this.frame.setCursor(new Cursor(3));
/* 199 */             CommandPanel.this.netPanel.moveNet();
/* 200 */             CommandPanel.this.frame.setCursor(new Cursor(0));
/*     */           }
/* 202 */           else if (paramActionEvent.getSource() == CommandPanel.this.arrowButton) {
/* 203 */             CommandPanel.this.frame.setCursor(new Cursor(3));
/* 204 */             CommandPanel.this.netPanel.setArrow();
/* 205 */             CommandPanel.this.frame.setCursor(new Cursor(0));
/*     */           }
/* 207 */           else if (paramActionEvent.getSource() == CommandPanel.this.svPotButton) {
/* 208 */             CommandPanel.this.frame.setCursor(new Cursor(3));
/* 209 */             CommandPanel.this.netPanel.savePot(CommandPanel.this.netPanel.loadVar());
/* 210 */             CommandPanel.this.frame.setCursor(new Cursor(0));
/*     */           }
/* 212 */           else if (paramActionEvent.getSource() == CommandPanel.this.ldPotButton) {
/* 213 */             CommandPanel.this.frame.setCursor(new Cursor(3));
/* 214 */             CommandPanel.this.netPanel.loadPot();
/* 215 */             CommandPanel.this.frame.setCursor(new Cursor(0));
/*     */           }
/* 217 */           else if (paramActionEvent.getSource() == CommandPanel.this.quitButton) {
/* 218 */             CommandPanel.this.frame.dispatchEvent(new java.awt.event.WindowEvent(CommandPanel.this.frame, 201));
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              /home/anali/INAOE/tesis/codigos/topub/exe/ww4.jar!/DMasMsbn/CommandPanel.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */