/*     */ package BnToLjt;
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
/*  14 */   public static final String addLinkLabel = new String("Add Link");
/*  15 */   public static final String mvNodeLabel = new String("Move Node");
/*  16 */   public static final String delLinkLabel = new String("Del Link");
/*  17 */   public static final String mvNetLabel = new String("Arrange");
/*  18 */   public static final String saveLabel = new String("Save JT");
/*  19 */   public static final String probLabel = new String("See Prob");
/*  20 */   public static final String aboutLabel = new String("About");
/*  21 */   public static final String quitLabel = new String("Quit");
/*     */   Button loadButton;
/*     */   Button chordalButton;
/*     */   Button jFButton;
/*     */   Button beliefButton;
/*     */   Button saveButton;
/*  27 */   Button aboutButton; Button mvNodeButton; Button addLinkButton; Button delLinkButton; Button mvNetButton; Button probButton; Button quitButton; java.awt.Font helvetica14 = new java.awt.Font("Helvetic", 1, 14);
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
/*  41 */     this.chordalButton = new Button(chordalLabel);
/*  42 */     this.jFButton = new Button(jFLabel);
/*  43 */     this.beliefButton = new Button(beliefLabel);
/*  44 */     this.saveButton = new Button(saveLabel);
/*  45 */     this.aboutButton = new Button(aboutLabel);
/*  46 */     this.mvNodeButton = new Button(mvNodeLabel);
/*  47 */     this.addLinkButton = new Button(addLinkLabel);
/*  48 */     this.delLinkButton = new Button(delLinkLabel);
/*  49 */     this.mvNetButton = new Button(mvNetLabel);
/*  50 */     this.probButton = new Button(probLabel);
/*  51 */     this.quitButton = new Button(quitLabel);
/*     */     
/*  53 */     add(this.loadButton);
/*  54 */     add(this.chordalButton);
/*  55 */     add(this.jFButton);
/*  56 */     add(this.beliefButton);
/*  57 */     add(this.saveButton);
/*  58 */     add(this.aboutButton);
/*  59 */     add(this.mvNodeButton);
/*  60 */     add(this.addLinkButton);
/*  61 */     add(this.delLinkButton);
/*  62 */     add(this.mvNetButton);
/*  63 */     add(this.probButton);
/*  64 */     add(this.quitButton);
/*     */     
/*  66 */     ButtonHandler localButtonHandler = new ButtonHandler(null);
/*  67 */     this.loadButton.addActionListener(localButtonHandler);
/*  68 */     this.chordalButton.addActionListener(localButtonHandler);
/*  69 */     this.jFButton.addActionListener(localButtonHandler);
/*  70 */     this.beliefButton.addActionListener(localButtonHandler);
/*  71 */     this.saveButton.addActionListener(localButtonHandler);
/*  72 */     this.aboutButton.addActionListener(localButtonHandler);
/*  73 */     this.mvNodeButton.addActionListener(localButtonHandler);
/*  74 */     this.addLinkButton.addActionListener(localButtonHandler);
/*  75 */     this.delLinkButton.addActionListener(localButtonHandler);
/*  76 */     this.mvNetButton.addActionListener(localButtonHandler);
/*  77 */     this.probButton.addActionListener(localButtonHandler);
/*  78 */     this.quitButton.addActionListener(localButtonHandler);
/*     */   }
/*     */   
/*     */ 
/*     */   private String getHelpText(String paramString)
/*     */   {
/*  84 */     if (paramString.equals(loadLabel)) return loadText;
/*  85 */     if (paramString.equals(chordalLabel)) return chordalText;
/*  86 */     if (paramString.equals(jFLabel)) return jFText;
/*  87 */     if (paramString.equals(saveLabel)) return saveText;
/*  88 */     if (paramString.equals(beliefLabel)) return beliefText;
/*  89 */     if (paramString.equals(aboutLabel)) return aboutText;
/*  90 */     if (paramString.equals(quitLabel)) return quitText;
/*  91 */     if (paramString.equals(mvNodeLabel)) return noneText;
/*  92 */     if (paramString.equals(delLinkLabel)) return noneText;
/*  93 */     if (paramString.equals(addLinkLabel)) return noneText;
/*  94 */     if (paramString.equals(mvNetLabel)) return mvNetText;
/*  95 */     if (paramString.equals(probLabel)) return noneText;
/*  96 */     return "";
/*     */   }
/*     */   
/*  99 */   static final String mvNodeText = new String("To move a node, click on it and drag it to the new position.");
/*     */   
/*     */ 
/* 102 */   static final String chordalText = new String("Triangulate the moral graph.");
/*     */   
/*     */ 
/* 105 */   static final String jFText = new String("Construct junction forest representation.");
/*     */   
/*     */ 
/* 108 */   static final String beliefText = new String("Initialize belief.");
/*     */   
/*     */ 
/* 111 */   static final String delLinkText = new String("To delete a link, click on it.");
/*     */   
/*     */ 
/* 114 */   static final String addLinkText = new String("To add a link, press mouse at one node and drag to the other.");
/*     */   
/*     */ 
/* 117 */   static final String mvNetText = new String("Reapprange display of the current network.");
/*     */   
/*     */ 
/* 120 */   static final String probText = new String("To see probability distribution of a clique, click on it.");
/*     */   
/*     */ 
/* 123 */   static final String saveText = new String("Choose the file to save the current join tree.");
/*     */   
/*     */ 
/* 126 */   static final String loadText = new String("Choose a Bayesian network file to load.");
/*     */   
/*     */ 
/* 129 */   static final String aboutText = new String("WEBWEAVR-IV : Bayesian Network Compilor For Dual Inference (2003-05) \n     Developed by: Y. Xiang   Student Contributor: X. Chen");
/*     */   
/*     */ 
/*     */ 
/* 133 */   static final String quitText = new String("Thank you for using WEBWEAVR-IV.");
/*     */   
/*     */ 
/* 136 */   static final String noneText = new String("This function is under development.");
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
/* 147 */         if (paramActionEvent.getSource() == CommandPanel.this.loadButton) {
/* 148 */           CommandPanel.this.frame.setCursor(new Cursor(3));
/* 149 */           CommandPanel.this.netPanel.loadInit();
/* 150 */           CommandPanel.this.netPanel.load();
/* 151 */           CommandPanel.this.netPanel.setMoralGraph();
/* 152 */           CommandPanel.this.frame.setCursor(new Cursor(0));
/*     */         }
/* 154 */         else if (paramActionEvent.getSource() == CommandPanel.this.chordalButton) {
/* 155 */           CommandPanel.this.frame.setCursor(new Cursor(3));
/* 156 */           CommandPanel.this.netPanel.chordalInit();
/* 157 */           CommandPanel.this.netPanel.setChordalGraph();
/* 158 */           CommandPanel.this.frame.setCursor(new Cursor(0));
/*     */         }
/* 160 */         else if (paramActionEvent.getSource() == CommandPanel.this.jFButton) {
/* 161 */           CommandPanel.this.frame.setCursor(new Cursor(3));
/* 162 */           CommandPanel.this.netPanel.setJF();
/* 163 */           CommandPanel.this.frame.setCursor(new Cursor(0));
/*     */         }
/* 165 */         else if (paramActionEvent.getSource() == CommandPanel.this.beliefButton) {
/* 166 */           CommandPanel.this.frame.setCursor(new Cursor(3));
/* 167 */           CommandPanel.this.netPanel.initBelief();
/* 168 */           CommandPanel.this.frame.setCursor(new Cursor(0));
/*     */         }
/* 170 */         else if (paramActionEvent.getSource() != CommandPanel.this.mvNodeButton)
/*     */         {
/*     */ 
/* 173 */           if (paramActionEvent.getSource() != CommandPanel.this.delLinkButton)
/*     */           {
/*     */ 
/* 176 */             if (paramActionEvent.getSource() != CommandPanel.this.probButton)
/*     */             {
/*     */ 
/* 179 */               if (paramActionEvent.getSource() == CommandPanel.this.mvNetButton) {
/* 180 */                 CommandPanel.this.frame.setCursor(new Cursor(3));
/* 181 */                 CommandPanel.this.netPanel.moveNet();
/* 182 */                 CommandPanel.this.frame.setCursor(new Cursor(0));
/*     */               }
/* 184 */               else if (paramActionEvent.getSource() == CommandPanel.this.saveButton) {
/* 185 */                 CommandPanel.this.frame.setCursor(new Cursor(3));
/* 186 */                 CommandPanel.this.netPanel.save();
/* 187 */                 CommandPanel.this.frame.setCursor(new Cursor(0));
/*     */               }
/* 189 */               else if (paramActionEvent.getSource() == CommandPanel.this.quitButton) {
/* 190 */                 CommandPanel.this.frame.dispatchEvent(new java.awt.event.WindowEvent(CommandPanel.this.frame, 201));
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              /home/anali/INAOE/tesis/codigos/topub/exe/ww4.jar!/BnToLjt/CommandPanel.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */