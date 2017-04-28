/*     */ package BnToJt;
/*     */ 
/*     */ import java.awt.Button;
/*     */ import java.awt.Cursor;
/*     */ import java.awt.Frame;
/*     */ import java.awt.event.ActionEvent;
/*     */ 
/*     */ class CommandPanel extends java.awt.Panel
/*     */ {
/*  10 */   public static final String loadLabel = new String("Load");
/*  11 */   public static final String chordalLabel = new String("Chordal");
/*  12 */   public static final String jFLabel = new String("Join Forest");
/*  13 */   public static final String beliefLabel = new String("Init Belief");
/*  14 */   public static final String saveLabel = new String("Save JT");
/*  15 */   public static final String aboutLabel = new String("About");
/*  16 */   public static final String triConLabel = new String("Constraint");
/*  17 */   public static final String mvNodeLabel = new String("Move Node");
/*  18 */   public static final String addLkLabel = new String("Add Link");
/*  19 */   public static final String mvNetLabel = new String("Arrange");
/*  20 */   public static final String quitLabel = new String("Quit");
/*     */   Button loadButton;
/*     */   Button chordalButton;
/*     */   Button jFButton;
/*     */   Button beliefButton;
/*     */   Button saveButton;
/*  26 */   Button aboutButton; Button triConButton; Button mvNodeButton; Button addLkButton; Button mvNetButton; Button quitButton; java.awt.Font helvetica14 = new java.awt.Font("Helvetic", 1, 14);
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
/*  40 */     this.chordalButton = new Button(chordalLabel);
/*  41 */     this.jFButton = new Button(jFLabel);
/*  42 */     this.beliefButton = new Button(beliefLabel);
/*  43 */     this.saveButton = new Button(saveLabel);
/*  44 */     this.aboutButton = new Button(aboutLabel);
/*  45 */     this.triConButton = new Button(triConLabel);
/*  46 */     this.mvNodeButton = new Button(mvNodeLabel);
/*  47 */     this.addLkButton = new Button(addLkLabel);
/*  48 */     this.mvNetButton = new Button(mvNetLabel);
/*  49 */     this.quitButton = new Button(quitLabel);
/*     */     
/*  51 */     add(this.loadButton);
/*  52 */     add(this.chordalButton);
/*  53 */     add(this.jFButton);
/*  54 */     add(this.beliefButton);
/*  55 */     add(this.saveButton);
/*  56 */     add(this.aboutButton);
/*  57 */     add(this.triConButton);
/*  58 */     add(this.mvNodeButton);
/*  59 */     add(this.addLkButton);
/*  60 */     add(this.mvNetButton);
/*  61 */     add(this.quitButton);
/*     */     
/*  63 */     ButtonHandler localButtonHandler = new ButtonHandler(null);
/*  64 */     this.loadButton.addActionListener(localButtonHandler);
/*  65 */     this.chordalButton.addActionListener(localButtonHandler);
/*  66 */     this.jFButton.addActionListener(localButtonHandler);
/*  67 */     this.beliefButton.addActionListener(localButtonHandler);
/*  68 */     this.saveButton.addActionListener(localButtonHandler);
/*  69 */     this.aboutButton.addActionListener(localButtonHandler);
/*  70 */     this.triConButton.addActionListener(localButtonHandler);
/*  71 */     this.mvNodeButton.addActionListener(localButtonHandler);
/*  72 */     this.addLkButton.addActionListener(localButtonHandler);
/*  73 */     this.mvNetButton.addActionListener(localButtonHandler);
/*  74 */     this.quitButton.addActionListener(localButtonHandler);
/*     */   }
/*     */   
/*     */ 
/*     */   private String getHelpText(String paramString)
/*     */   {
/*  80 */     if (paramString.equals(loadLabel)) return loadText;
/*  81 */     if (paramString.equals(chordalLabel)) return chordalText;
/*  82 */     if (paramString.equals(jFLabel)) return jFText;
/*  83 */     if (paramString.equals(beliefLabel)) return beliefText;
/*  84 */     if (paramString.equals(saveLabel)) return saveText;
/*  85 */     if (paramString.equals(aboutLabel)) return aboutText;
/*  86 */     if (paramString.equals(triConLabel)) return triConText;
/*  87 */     if (paramString.equals(mvNodeLabel)) return mvNodeText;
/*  88 */     if (paramString.equals(addLkLabel)) return addLkText;
/*  89 */     if (paramString.equals(mvNetLabel)) return mvNetText;
/*  90 */     if (paramString.equals(quitLabel)) return quitText;
/*  91 */     return "";
/*     */   }
/*     */   
/*  94 */   static final String loadText = new String("Choose a Bayesian network file to load.");
/*     */   
/*     */ 
/*  97 */   static final String chordalText = new String("Triangulate the moral graph.");
/*     */   
/*     */ 
/* 100 */   static final String jFText = new String("Construct junction forest.");
/*     */   
/*     */ 
/* 103 */   static final String beliefText = new String("Initialize belief.");
/*     */   
/*     */ 
/* 106 */   static final String saveText = new String("Choose the file to save the current join tree.");
/*     */   
/*     */ 
/* 109 */   static final String aboutText = new String("   WEBWEAVR-IV : Bayesian Network Compilor (1997-2005) \n   Developed by :    Y. Xiang ");
/*     */   
/*     */ 
/*     */ 
/* 113 */   static final String triConText = new String("To specify last nodes to eliminate in constrained triangulation.");
/*     */   
/*     */ 
/* 116 */   static final String mvNodeText = new String("To move a node, specify: index new_X new_Y");
/*     */   
/*     */ 
/* 119 */   static final String addLkText = new String("To add a link, specify: end_1_index end_2_index");
/*     */   
/*     */ 
/* 122 */   static final String mvNetText = new String("Reapprange display of the current network.");
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
/* 136 */         Network.HelpPanel.addHelp(CommandPanel.this.getHelpText(str));
/*     */         
/* 138 */         if (paramActionEvent.getSource() == CommandPanel.this.loadButton) {
/* 139 */           CommandPanel.this.frame.setCursor(new Cursor(3));
/* 140 */           CommandPanel.this.netPanel.loadInit();
/* 141 */           CommandPanel.this.netPanel.load();
/* 142 */           CommandPanel.this.netPanel.setMoralGraph();
/* 143 */           CommandPanel.this.frame.setCursor(new Cursor(0));
/*     */         }
/* 145 */         else if (paramActionEvent.getSource() == CommandPanel.this.chordalButton) {
/* 146 */           if (!CommandPanel.this.netPanel.hasNode()) { Network.HelpPanel.showError("Load a BN first!");
/*     */           } else {
/* 148 */             CommandPanel.this.frame.setCursor(new Cursor(3));
/* 149 */             CommandPanel.this.netPanel.chordalInit();
/* 150 */             CommandPanel.this.netPanel.setChordalGraph();
/* 151 */             CommandPanel.this.frame.setCursor(new Cursor(0));
/*     */           }
/*     */         }
/* 154 */         else if (paramActionEvent.getSource() == CommandPanel.this.jFButton) {
/* 155 */           if ((!CommandPanel.this.netPanel.hasNode()) || (!CommandPanel.this.netPanel.isTriangulated())) {
/* 156 */             Network.HelpPanel.showError("Load and triangulate first!");
/*     */           } else {
/* 158 */             CommandPanel.this.frame.setCursor(new Cursor(3));
/* 159 */             CommandPanel.this.netPanel.jForestInit();
/* 160 */             CommandPanel.this.netPanel.setJoinForest();
/* 161 */             CommandPanel.this.frame.setCursor(new Cursor(0));
/*     */           }
/*     */         }
/* 164 */         else if (paramActionEvent.getSource() == CommandPanel.this.beliefButton) {
/* 165 */           CommandPanel.this.frame.setCursor(new Cursor(3));
/* 166 */           CommandPanel.this.netPanel.initBelief();
/* 167 */           CommandPanel.this.frame.setCursor(new Cursor(0));
/*     */         }
/* 169 */         else if (paramActionEvent.getSource() == CommandPanel.this.saveButton) {
/* 170 */           CommandPanel.this.frame.setCursor(new Cursor(3));
/* 171 */           CommandPanel.this.netPanel.save();
/* 172 */           CommandPanel.this.frame.setCursor(new Cursor(0));
/*     */         }
/* 174 */         else if (paramActionEvent.getSource() == CommandPanel.this.triConButton) {
/* 175 */           if (!CommandPanel.this.netPanel.hasNode()) { Network.HelpPanel.showError("Load first.");
/* 176 */           } else if (CommandPanel.this.netPanel.cmode == 2) {
/* 177 */             Network.HelpPanel.showError("Constraint must be set before JT building.");
/*     */           } else {
/* 179 */             CommandPanel.this.frame.setCursor(new Cursor(3));
/* 180 */             CommandPanel.this.netPanel.setTriCon();
/* 181 */             CommandPanel.this.frame.setCursor(new Cursor(0));
/*     */           }
/*     */           
/*     */         }
/* 185 */         else if (paramActionEvent.getSource() == CommandPanel.this.mvNodeButton) {
/* 186 */           if (!CommandPanel.this.netPanel.hasNode()) { Network.HelpPanel.showError("Load first.");
/* 187 */           } else if (CommandPanel.this.netPanel.pmode != 0) {
/* 188 */             Network.HelpPanel.showError("Not in undirected graph mode.");
/*     */           } else {
/* 190 */             CommandPanel.this.frame.setCursor(new Cursor(3));
/* 191 */             CommandPanel.this.netPanel.mvNode();
/* 192 */             CommandPanel.this.frame.setCursor(new Cursor(0));
/*     */           }
/*     */         }
/* 195 */         else if (paramActionEvent.getSource() == CommandPanel.this.addLkButton) {
/* 196 */           if (!CommandPanel.this.netPanel.hasNode()) { Network.HelpPanel.showError("Load first.");
/* 197 */           } else if (CommandPanel.this.netPanel.cmode != 0) {
/* 198 */             Network.HelpPanel.showError("Adding Links must be before triangulation.");
/*     */           } else {
/* 200 */             CommandPanel.this.frame.setCursor(new Cursor(3));
/* 201 */             CommandPanel.this.netPanel.addLk();
/* 202 */             CommandPanel.this.frame.setCursor(new Cursor(0));
/*     */           }
/*     */         }
/* 205 */         else if (paramActionEvent.getSource() == CommandPanel.this.mvNetButton) {
/* 206 */           CommandPanel.this.frame.setCursor(new Cursor(3));
/* 207 */           CommandPanel.this.netPanel.moveNet();
/* 208 */           CommandPanel.this.frame.setCursor(new Cursor(0));
/*     */         }
/* 210 */         else if (paramActionEvent.getSource() == CommandPanel.this.quitButton) {
/* 211 */           CommandPanel.this.frame.dispatchEvent(new java.awt.event.WindowEvent(CommandPanel.this.frame, 201));
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              /home/anali/INAOE/tesis/codigos/topub/exe/ww4.jar!/BnToJt/CommandPanel.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */