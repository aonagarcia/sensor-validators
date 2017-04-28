/*     */ package DMsbToLjf;
/*     */ 
/*     */ import java.awt.Button;
/*     */ import java.awt.Cursor;
/*     */ import java.awt.Frame;
/*     */ import java.awt.event.ActionEvent;
/*     */ 
/*     */ class CommandPanel extends java.awt.Panel
/*     */ {
/*  10 */   public static final String loadLabel = new String("Load");
/*  11 */   public static final String addlkLabel = new String("Add Link");
/*  12 */   public static final String moralLabel = new String("Moral");
/*  13 */   public static final String chordalLabel = new String("Chordal");
/*  14 */   public static final String linkLabel = new String("Link JT");
/*  15 */   public static final String assignLabel = new String("Assign");
/*  16 */   public static final String saveLabel = new String("Save");
/*     */   
/*  18 */   public static final String mvNetLabel = new String("Arrange");
/*  19 */   public static final String noIdxLabel = new String("Show Idx");
/*  20 */   public static final String arrowLabel = new String("Arrow");
/*  21 */   public static final String pauseLabel = new String("Pause");
/*  22 */   public static final String linkageLabel = new String("Show LT");
/*  23 */   public static final String aboutLabel = new String("About");
/*  24 */   public static final String quitLabel = new String("Quit");
/*     */   private Button loadButton;
/*     */   private Button addlkButton;
/*     */   private Button moralButton;
/*     */   private Button chordalButton;
/*     */   private Button linkButton;
/*     */   private Button assignButton;
/*  31 */   private Button saveButton; private Button mvNetButton; private Button noIdxButton; private Button arrowButton; private Button pauseButton; private Button linkageButton; private Button aboutButton; private Button quitButton; java.awt.Font helvetica14 = new java.awt.Font("Helvetic", 1, 14);
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
/*  42 */     int i = 2;int j = 6;int k = 6;int m = 0;
/*  43 */     setLayout(new java.awt.GridLayout(i, j, k, m));
/*  44 */     this.loadButton = new Button(loadLabel);
/*  45 */     this.addlkButton = new Button(addlkLabel);
/*  46 */     this.moralButton = new Button(moralLabel);
/*  47 */     this.chordalButton = new Button(chordalLabel);
/*  48 */     this.linkButton = new Button(linkLabel);
/*  49 */     this.assignButton = new Button(assignLabel);
/*  50 */     this.saveButton = new Button(saveLabel);
/*     */     
/*  52 */     this.mvNetButton = new Button(mvNetLabel);
/*  53 */     this.noIdxButton = new Button(noIdxLabel);
/*  54 */     this.arrowButton = new Button(arrowLabel);
/*  55 */     this.pauseButton = new Button(pauseLabel);
/*  56 */     this.linkageButton = new Button(linkageLabel);
/*  57 */     this.aboutButton = new Button(aboutLabel);
/*  58 */     this.quitButton = new Button(quitLabel);
/*     */     
/*  60 */     add(this.loadButton);
/*  61 */     add(this.addlkButton);
/*  62 */     add(this.moralButton);
/*  63 */     add(this.chordalButton);
/*  64 */     add(this.linkButton);
/*  65 */     add(this.assignButton);
/*  66 */     add(this.saveButton);
/*  67 */     add(this.mvNetButton);
/*  68 */     add(this.noIdxButton);
/*  69 */     add(this.arrowButton);
/*  70 */     add(this.pauseButton);
/*  71 */     add(this.linkageButton);
/*  72 */     add(this.aboutButton);
/*  73 */     add(this.quitButton);
/*     */     
/*  75 */     ButtonHandler localButtonHandler = new ButtonHandler(null);
/*  76 */     this.loadButton.addActionListener(localButtonHandler);
/*  77 */     this.addlkButton.addActionListener(localButtonHandler);
/*  78 */     this.moralButton.addActionListener(localButtonHandler);
/*  79 */     this.chordalButton.addActionListener(localButtonHandler);
/*  80 */     this.linkButton.addActionListener(localButtonHandler);
/*  81 */     this.assignButton.addActionListener(localButtonHandler);
/*  82 */     this.saveButton.addActionListener(localButtonHandler);
/*  83 */     this.mvNetButton.addActionListener(localButtonHandler);
/*  84 */     this.noIdxButton.addActionListener(localButtonHandler);
/*  85 */     this.arrowButton.addActionListener(localButtonHandler);
/*  86 */     this.pauseButton.addActionListener(localButtonHandler);
/*  87 */     this.linkageButton.addActionListener(localButtonHandler);
/*  88 */     this.aboutButton.addActionListener(localButtonHandler);
/*  89 */     this.quitButton.addActionListener(localButtonHandler);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private String getHelpText(String paramString)
/*     */   {
/*  96 */     if (paramString.equals(loadLabel)) return loadText;
/*  97 */     if (paramString.equals(addlkLabel)) return addlkText;
/*  98 */     if (paramString.equals(moralLabel)) return moralText;
/*  99 */     if (paramString.equals(chordalLabel)) return chordalText;
/* 100 */     if (paramString.equals(linkLabel)) return linkText;
/* 101 */     if (paramString.equals(assignLabel)) return assignText;
/* 102 */     if (paramString.equals(saveLabel)) { return saveText;
/*     */     }
/* 104 */     if (paramString.equals(mvNetLabel)) return mvNetText;
/* 105 */     if (paramString.equals(noIdxLabel)) return noIdxText;
/* 106 */     if (paramString.equals(arrowLabel)) return arrowText;
/* 107 */     if (paramString.equals(pauseLabel)) return pauseText;
/* 108 */     if (paramString.equals(linkageLabel)) return linkageText;
/* 109 */     if (paramString.equals(aboutLabel)) return aboutText;
/* 110 */     if (paramString.equals(quitLabel)) return quitText;
/* 111 */     return "";
/*     */   }
/*     */   
/* 114 */   static final String aboutText = new String("WEBWEAVR-IV : Distributed Multi-agent MSBN Compiler (2001-04)\nDeveloped by: Y. Xiang    Student Contributor: X. Chen ");
/*     */   
/*     */ 
/*     */ 
/*     */ 
/* 119 */   static final String loadText = new String("Load the subnet of this agent.");
/*     */   
/*     */ 
/* 122 */   static final String addlkText = new String("To add a fill-in, specify: end_1_index end_2_index.");
/*     */   
/*     */ 
/* 125 */   static final String moralText = new String("Moralize MSDAG.  Communication starts.");
/*     */   
/*     */ 
/* 128 */   static final String chordalText = new String("Triangulate moral graphs. Communication starts.");
/*     */   
/*     */ 
/* 131 */   static final String linkText = new String("Construct linked junction forest. Communication starts.");
/*     */   
/*     */ 
/* 134 */   static final String assignText = new String("Assign d-sepnodes. Communication starts.");
/*     */   
/*     */ 
/* 137 */   static final String saveText = new String("Save junction tree, linkage trees and d-sepnode assignment.");
/*     */   
/*     */ 
/* 140 */   static final String noIdxText = new String("Hide or show node index.");
/*     */   
/*     */ 
/* 143 */   static final String linkageText = new String("Show linkage trees of this agent.");
/*     */   
/*     */ 
/* 146 */   static final String pauseText = new String("Switch pause mode for linkage tree display.");
/*     */   
/*     */ 
/* 149 */   static final String mvNetText = new String("Reapprange display for the subnet of this agent.");
/*     */   
/*     */ 
/* 152 */   static final String arrowText = new String("To change the size of arrow, keep clicking this button.");
/*     */   
/*     */ 
/* 155 */   static final String quitText = new String("Thank you for using WEBWEAVR-IV.");
/*     */   
/*     */   private class ButtonHandler implements java.awt.event.ActionListener {
/*     */     private ButtonHandler() {}
/*     */     
/*     */     public void actionPerformed(ActionEvent paramActionEvent) {
/* 161 */       if ((paramActionEvent.getSource() instanceof Button)) {
/* 162 */         String str = paramActionEvent.getActionCommand();
/*     */         
/* 164 */         if (CommandPanel.this.netPanel.isCommunicating()) {
/* 165 */           Network.HelpPanel.showError("Communication ongoing.");return;
/*     */         }
/* 167 */         if ((paramActionEvent.getSource() != CommandPanel.this.quitButton) && (paramActionEvent.getSource() != CommandPanel.this.loadButton) && (paramActionEvent.getSource() != CommandPanel.this.aboutButton) && (!CommandPanel.this.netPanel.hasSubnet()))
/*     */         {
/*     */ 
/*     */ 
/* 171 */           Network.HelpPanel.showError("Load subnet first.");return;
/*     */         }
/*     */         
/* 174 */         Network.HelpPanel.addHelp(CommandPanel.this.getHelpText(str));
/*     */         
/* 176 */         if (paramActionEvent.getSource() == CommandPanel.this.loadButton) {
/* 177 */           CommandPanel.this.frame.setCursor(new Cursor(3));
/* 178 */           CommandPanel.this.netPanel.load();
/* 179 */           CommandPanel.this.netPanel.getSet();
/* 180 */           CommandPanel.this.frame.setCursor(new Cursor(0));
/*     */         }
/* 182 */         else if (paramActionEvent.getSource() == CommandPanel.this.addlkButton) {
/* 183 */           CommandPanel.this.frame.setCursor(new Cursor(3));
/* 184 */           CommandPanel.this.netPanel.addLk();
/* 185 */           CommandPanel.this.frame.setCursor(new Cursor(0));
/*     */         }
/* 187 */         else if (paramActionEvent.getSource() == CommandPanel.this.moralButton) {
/* 188 */           CommandPanel.this.frame.setCursor(new Cursor(3));
/* 189 */           CommandPanel.this.netPanel.setMoralGraph();
/* 190 */           CommandPanel.this.frame.setCursor(new Cursor(0));
/*     */         }
/* 192 */         else if (paramActionEvent.getSource() == CommandPanel.this.chordalButton) {
/* 193 */           CommandPanel.this.frame.setCursor(new Cursor(3));
/* 194 */           CommandPanel.this.netPanel.setChordalGraph();
/* 195 */           CommandPanel.this.frame.setCursor(new Cursor(0));
/*     */         }
/* 197 */         else if (paramActionEvent.getSource() == CommandPanel.this.linkButton) {
/* 198 */           CommandPanel.this.frame.setCursor(new Cursor(3));
/* 199 */           CommandPanel.this.netPanel.setJtLinkage();
/* 200 */           CommandPanel.this.frame.setCursor(new Cursor(0));
/*     */         }
/* 202 */         else if (paramActionEvent.getSource() == CommandPanel.this.assignButton) {
/* 203 */           CommandPanel.this.frame.setCursor(new Cursor(3));
/* 204 */           CommandPanel.this.netPanel.assignDsepnode();
/* 205 */           CommandPanel.this.frame.setCursor(new Cursor(0));
/*     */         }
/* 207 */         else if (paramActionEvent.getSource() == CommandPanel.this.saveButton) {
/* 208 */           CommandPanel.this.frame.setCursor(new Cursor(3));
/* 209 */           CommandPanel.this.netPanel.save();
/* 210 */           CommandPanel.this.frame.setCursor(new Cursor(0));
/*     */         }
/* 212 */         else if (paramActionEvent.getSource() == CommandPanel.this.mvNetButton) {
/* 213 */           CommandPanel.this.frame.setCursor(new Cursor(3));
/* 214 */           CommandPanel.this.netPanel.moveNet();
/* 215 */           CommandPanel.this.frame.setCursor(new Cursor(0));
/*     */         }
/* 217 */         else if (paramActionEvent.getSource() == CommandPanel.this.noIdxButton) {
/* 218 */           CommandPanel.this.frame.setCursor(new Cursor(3));
/* 219 */           CommandPanel.this.netPanel.toggleIndex();
/* 220 */           CommandPanel.this.frame.setCursor(new Cursor(0));
/*     */         }
/* 222 */         else if (paramActionEvent.getSource() == CommandPanel.this.linkageButton) {
/* 223 */           CommandPanel.this.frame.setCursor(new Cursor(3));
/* 224 */           CommandPanel.this.netPanel.showLnkgTree();
/* 225 */           CommandPanel.this.frame.setCursor(new Cursor(0));
/*     */         }
/* 227 */         else if (paramActionEvent.getSource() == CommandPanel.this.pauseButton) {
/* 228 */           CommandPanel.this.frame.setCursor(new Cursor(3));
/* 229 */           CommandPanel.this.netPanel.switchPause();
/* 230 */           CommandPanel.this.frame.setCursor(new Cursor(0));
/*     */         }
/* 232 */         else if (paramActionEvent.getSource() == CommandPanel.this.arrowButton) {
/* 233 */           CommandPanel.this.frame.setCursor(new Cursor(3));
/* 234 */           CommandPanel.this.netPanel.setArrow();
/* 235 */           CommandPanel.this.frame.setCursor(new Cursor(0));
/*     */         }
/* 237 */         else if (paramActionEvent.getSource() == CommandPanel.this.quitButton) {
/* 238 */           CommandPanel.this.frame.dispatchEvent(new java.awt.event.WindowEvent(CommandPanel.this.frame, 201));
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              /home/anali/INAOE/tesis/codigos/topub/exe/ww4.jar!/DMsbToLjf/CommandPanel.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */