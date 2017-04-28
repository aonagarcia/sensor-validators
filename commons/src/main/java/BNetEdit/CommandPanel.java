/*     */ package BNetEdit;
/*     */ 
/*     */ import java.awt.Button;
/*     */ import java.awt.Cursor;
/*     */ import java.awt.Frame;
/*     */ import java.awt.event.ActionEvent;
/*     */ 
/*     */ class CommandPanel extends java.awt.Panel
/*     */ {
/*  10 */   public static final String drawLabel = new String("Draw");
/*  11 */   public static final String mvNodeLabel = new String("Move Node");
/*  12 */   public static final String delNodeLabel = new String("Del Node");
/*  13 */   public static final String delArcLabel = new String("Del Arc");
/*  14 */   public static final String delNetLabel = new String("Del Net");
/*  15 */   public static final String arrowLabel = new String("Set Arrow");
/*  16 */   public static final String mvNetLabel = new String("Arrange");
/*  17 */   public static final String noIdxLabel = new String("Show Index");
/*  18 */   public static final String loadLabel = new String("Load");
/*  19 */   public static final String saveLabel = new String("Save");
/*  20 */   public static final String varLabel = new String("Defn Var");
/*  21 */   public static final String probLabel = new String("Set Prob");
/*  22 */   public static final String indexLabel = new String("ReIndex");
/*  23 */   public static final String aboutLabel = new String("About");
/*  24 */   public static final String quitLabel = new String("Quit");
/*     */   private Button drawButton;
/*     */   private Button mvNodeButton;
/*     */   private Button delNodeButton;
/*     */   private Button delArcButton;
/*     */   private Button delNetButton;
/*     */   private Button arrowButton;
/*  31 */   private Button mvNetButton; private Button noIdxButton; private Button loadButton; private Button saveButton; private Button varButton; private Button probButton; private Button indexButton; private Button aboutButton; private Button quitButton; java.awt.Font helvetica14 = new java.awt.Font("Helvetic", 1, 14);
/*     */   
/*  33 */   Frame frame = null;
/*     */   NetworkPanel netPanel;
/*     */   
/*     */   CommandPanel(Frame paramFrame, NetworkPanel paramNetworkPanel)
/*     */   {
/*  38 */     this.frame = paramFrame;
/*  39 */     this.netPanel = paramNetworkPanel;
/*  40 */     setFont(this.helvetica14);
/*     */     
/*  42 */     int i = 2;int j = 7;int k = 6;int m = 0;
/*  43 */     setLayout(new java.awt.GridLayout(i, j, k, m));
/*  44 */     this.drawButton = new Button(drawLabel);
/*  45 */     this.mvNodeButton = new Button(mvNodeLabel);
/*  46 */     this.delNodeButton = new Button(delNodeLabel);
/*  47 */     this.delArcButton = new Button(delArcLabel);
/*  48 */     this.delNetButton = new Button(delNetLabel);
/*  49 */     this.arrowButton = new Button(arrowLabel);
/*  50 */     this.mvNetButton = new Button(mvNetLabel);
/*  51 */     this.noIdxButton = new Button(noIdxLabel);
/*  52 */     this.loadButton = new Button(loadLabel);
/*  53 */     this.saveButton = new Button(saveLabel);
/*  54 */     this.varButton = new Button(varLabel);
/*  55 */     this.probButton = new Button(probLabel);
/*  56 */     this.indexButton = new Button(indexLabel);
/*  57 */     this.aboutButton = new Button(aboutLabel);
/*  58 */     this.quitButton = new Button(quitLabel);
/*     */     
/*  60 */     add(this.drawButton);add(this.mvNodeButton);add(this.delNodeButton);
/*  61 */     add(this.delArcButton);add(this.delNetButton);add(this.arrowButton);
/*  62 */     add(this.mvNetButton);add(this.noIdxButton);add(this.loadButton);
/*  63 */     add(this.saveButton);add(this.varButton);add(this.probButton);
/*  64 */     add(this.indexButton);add(this.aboutButton);add(this.quitButton);
/*     */     
/*  66 */     ButtonHandler localButtonHandler = new ButtonHandler(null);
/*  67 */     this.drawButton.addActionListener(localButtonHandler);
/*  68 */     this.mvNodeButton.addActionListener(localButtonHandler);
/*  69 */     this.delNodeButton.addActionListener(localButtonHandler);
/*  70 */     this.delArcButton.addActionListener(localButtonHandler);
/*  71 */     this.delNetButton.addActionListener(localButtonHandler);
/*  72 */     this.arrowButton.addActionListener(localButtonHandler);
/*  73 */     this.mvNetButton.addActionListener(localButtonHandler);
/*  74 */     this.noIdxButton.addActionListener(localButtonHandler);
/*  75 */     this.loadButton.addActionListener(localButtonHandler);
/*  76 */     this.saveButton.addActionListener(localButtonHandler);
/*  77 */     this.varButton.addActionListener(localButtonHandler);
/*  78 */     this.probButton.addActionListener(localButtonHandler);
/*  79 */     this.indexButton.addActionListener(localButtonHandler);
/*  80 */     this.aboutButton.addActionListener(localButtonHandler);
/*  81 */     this.quitButton.addActionListener(localButtonHandler);
/*     */   }
/*     */   
/*     */   private String getHelpText(String paramString)
/*     */   {
/*  86 */     if (paramString.equals(drawLabel)) return drawText;
/*  87 */     if (paramString.equals(mvNodeLabel)) return mvNodeText;
/*  88 */     if (paramString.equals(delNodeLabel)) return delNodeText;
/*  89 */     if (paramString.equals(delArcLabel)) return delArcText;
/*  90 */     if (paramString.equals(delNetLabel)) return delNetText;
/*  91 */     if (paramString.equals(mvNetLabel)) return mvNetText;
/*  92 */     if (paramString.equals(noIdxLabel)) return noIdxText;
/*  93 */     if (paramString.equals(varLabel)) return varText;
/*  94 */     if (paramString.equals(probLabel)) return probText;
/*  95 */     if (paramString.equals(arrowLabel)) return arrowText;
/*  96 */     if (paramString.equals(saveLabel)) return saveText;
/*  97 */     if (paramString.equals(loadLabel)) return loadText;
/*  98 */     if (paramString.equals(indexLabel)) return indexText;
/*  99 */     if (paramString.equals(aboutLabel)) return aboutText;
/* 100 */     if (paramString.equals(quitLabel)) return quitText;
/* 101 */     return "";
/*     */   }
/*     */   
/* 104 */   static final String aboutText = new String("     WEBWEAVR-IV : Bayesian Network Editor (1997-2005) \n     Developed by : Y. Xiang      Student Contributor : J. Hu ");
/*     */   
/*     */ 
/*     */ 
/* 108 */   static final String drawText = new String("To draw an arc, click on the parent and drag to the child.");
/*     */   
/*     */ 
/* 111 */   static final String mvNodeText = new String("To move a node, click on it and drag it to the new position.");
/*     */   
/*     */ 
/* 114 */   static final String delNodeText = new String("To delete a node, click on it.");
/*     */   
/*     */ 
/* 117 */   static final String delArcText = new String("To delete an arc, click on it.");
/*     */   
/*     */ 
/* 120 */   static final String delNetText = new String("Clicking on this button deletes current network.");
/*     */   
/*     */ 
/* 123 */   static final String mvNetText = new String("Reapprange display of the current network.");
/*     */   
/*     */ 
/* 126 */   static final String noIdxText = new String("Hide node index.");
/*     */   
/*     */ 
/* 129 */   static final String varText = new String("To define the label and values of a node, click on it.");
/*     */   
/*     */ 
/* 132 */   static final String probText = new String("To define conditional probabilities of a node, click on it.");
/*     */   
/*     */ 
/* 135 */   static final String indexText = new String("To reduce the index of a node to zero, click on it.");
/*     */   
/*     */ 
/* 138 */   static final String arrowText = new String("To change the arrow size, click this button.");
/*     */   
/*     */ 
/* 141 */   static final String saveText = new String("Choose the file to save the current network.");
/*     */   
/*     */ 
/* 144 */   static final String loadText = new String("Choose a network file to load.");
/*     */   
/*     */ 
/* 147 */   static final String quitText = new String("Thank you for using WEBWEAVR-IV.");
/*     */   
/*     */   private class ButtonHandler implements java.awt.event.ActionListener {
/*     */     private ButtonHandler() {}
/*     */     
/*     */     public void actionPerformed(ActionEvent paramActionEvent) {
/* 153 */       if ((paramActionEvent.getSource() instanceof Button)) {
/* 154 */         String str = paramActionEvent.getActionCommand();
/* 155 */         CommandPanel.this.netPanel.setMode(str);
/* 156 */         Network.HelpPanel.addHelp(CommandPanel.this.getHelpText(str));
/*     */         
/* 158 */         if (paramActionEvent.getSource() != CommandPanel.this.drawButton)
/*     */         {
/* 160 */           if (paramActionEvent.getSource() == CommandPanel.this.mvNodeButton) {
/* 161 */             if (!CommandPanel.this.netPanel.hasNode()) Network.HelpPanel.showError("No node to move!");
/*     */           }
/* 163 */           else if (paramActionEvent.getSource() == CommandPanel.this.delNodeButton) {
/* 164 */             if (!CommandPanel.this.netPanel.hasNode()) Network.HelpPanel.showError("No node to delete!");
/*     */           }
/* 166 */           else if (paramActionEvent.getSource() == CommandPanel.this.delArcButton) {
/* 167 */             if (!CommandPanel.this.netPanel.hasArc()) Network.HelpPanel.showError("No arc to delete!");
/*     */           }
/* 169 */           else if (paramActionEvent.getSource() == CommandPanel.this.delNetButton) {
/* 170 */             CommandPanel.this.frame.setCursor(new Cursor(3));
/* 171 */             CommandPanel.this.netPanel.delNet();
/* 172 */             CommandPanel.this.frame.setCursor(new Cursor(0));
/*     */           }
/* 174 */           else if (paramActionEvent.getSource() == CommandPanel.this.varButton) {
/* 175 */             if (!CommandPanel.this.netPanel.hasNode()) Network.HelpPanel.showError("Please use Draw first.");
/*     */           }
/* 177 */           else if (paramActionEvent.getSource() == CommandPanel.this.indexButton) {
/* 178 */             if (!CommandPanel.this.netPanel.hasNode()) Network.HelpPanel.showError("Draw nodes first.");
/*     */           }
/* 180 */           else if (paramActionEvent.getSource() == CommandPanel.this.probButton) {
/* 181 */             if (!CommandPanel.this.netPanel.hasNode()) Network.HelpPanel.showError("Please use Draw first.");
/*     */           }
/* 183 */           else if (paramActionEvent.getSource() == CommandPanel.this.arrowButton) {
/* 184 */             CommandPanel.this.frame.setCursor(new Cursor(3));
/* 185 */             CommandPanel.this.netPanel.setArrow();
/* 186 */             CommandPanel.this.frame.setCursor(new Cursor(0));
/*     */           }
/* 188 */           else if (paramActionEvent.getSource() == CommandPanel.this.mvNetButton) {
/* 189 */             CommandPanel.this.frame.setCursor(new Cursor(3));
/* 190 */             CommandPanel.this.netPanel.moveNet();
/* 191 */             CommandPanel.this.frame.setCursor(new Cursor(0));
/*     */           }
/* 193 */           else if (paramActionEvent.getSource() == CommandPanel.this.noIdxButton) {
/* 194 */             CommandPanel.this.frame.setCursor(new Cursor(3));
/* 195 */             CommandPanel.this.netPanel.toggleIndex();
/* 196 */             CommandPanel.this.frame.setCursor(new Cursor(0));
/*     */           }
/* 198 */           else if (paramActionEvent.getSource() == CommandPanel.this.loadButton) {
/* 199 */             CommandPanel.this.frame.setCursor(new Cursor(3));
/* 200 */             CommandPanel.this.netPanel.load();
/* 201 */             CommandPanel.this.frame.setCursor(new Cursor(0));
/*     */           }
/* 203 */           else if (paramActionEvent.getSource() == CommandPanel.this.saveButton) {
/* 204 */             CommandPanel.this.frame.setCursor(new Cursor(3));
/* 205 */             CommandPanel.this.netPanel.save();
/* 206 */             CommandPanel.this.frame.setCursor(new Cursor(0));
/*     */           }
/* 208 */           else if (paramActionEvent.getSource() == CommandPanel.this.quitButton) {
/* 209 */             CommandPanel.this.frame.dispatchEvent(new java.awt.event.WindowEvent(CommandPanel.this.frame, 201));
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              /home/anali/INAOE/tesis/codigos/topub/exe/ww4.jar!/BNetEdit/CommandPanel.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */