/*     */ package FrontEnd;
/*     */ 
/*     */ import java.awt.Button;
/*     */ import java.awt.Cursor;
/*     */ import java.awt.Frame;
/*     */ import java.awt.event.ActionEvent;
/*     */ 
/*     */ class CommandPanel extends java.awt.Panel
/*     */ {
/*  10 */   public static final String loadLabel = new String("Load");
/*  11 */   public static final String addLabel = new String("Add");
/*  12 */   public static final String runLabel = new String("Run");
/*  13 */   public static final String cutLabel = new String("Cut");
/*  14 */   public static final String pasteLabel = new String("Paste");
/*  15 */   public static final String sortLabel = new String("Sort");
/*  16 */   public static final String helpLabel = new String("Help");
/*  17 */   public static final String aboutLabel = new String("About");
/*  18 */   public static final String quitLabel = new String("Quit");
/*     */   Button loadButton;
/*     */   Button addButton;
/*     */   Button runButton;
/*     */   Button cutButton;
/*  23 */   Button pasteButton; Button sortButton; Button helpButton; Button aboutButton; Button quitButton; java.awt.Font helvetica14 = new java.awt.Font("Helvetic", 1, 14);
/*  24 */   Frame frame = null;
/*     */   MenuPanel menuPanel;
/*     */   
/*     */   CommandPanel(Frame paramFrame, MenuPanel paramMenuPanel)
/*     */   {
/*  29 */     this.frame = paramFrame;
/*  30 */     this.menuPanel = paramMenuPanel;
/*  31 */     setFont(this.helvetica14);
/*     */     
/*  33 */     int i = 2;int j = 5;int k = 0;int m = 0;
/*  34 */     setLayout(new java.awt.GridLayout(i, j, k, m));
/*  35 */     this.loadButton = new Button(loadLabel);
/*  36 */     this.runButton = new Button(runLabel);
/*  37 */     this.helpButton = new Button(helpLabel);
/*  38 */     this.aboutButton = new Button(aboutLabel);
/*  39 */     this.quitButton = new Button(quitLabel);
/*  40 */     this.addButton = new Button(addLabel);
/*  41 */     this.cutButton = new Button(cutLabel);
/*  42 */     this.pasteButton = new Button(pasteLabel);
/*  43 */     this.sortButton = new Button(sortLabel);
/*     */     
/*  45 */     add(this.loadButton);add(this.runButton);add(this.helpButton);
/*  46 */     add(this.aboutButton);add(this.quitButton);add(this.addButton);
/*  47 */     add(this.cutButton);add(this.pasteButton);add(this.sortButton);
/*     */     
/*  49 */     ButtonHandler localButtonHandler = new ButtonHandler(null);
/*  50 */     this.loadButton.addActionListener(localButtonHandler);
/*  51 */     this.runButton.addActionListener(localButtonHandler);
/*  52 */     this.helpButton.addActionListener(localButtonHandler);
/*  53 */     this.aboutButton.addActionListener(localButtonHandler);
/*  54 */     this.quitButton.addActionListener(localButtonHandler);
/*  55 */     this.addButton.addActionListener(localButtonHandler);
/*  56 */     this.cutButton.addActionListener(localButtonHandler);
/*  57 */     this.pasteButton.addActionListener(localButtonHandler);
/*  58 */     this.sortButton.addActionListener(localButtonHandler);
/*     */   }
/*     */   
/*     */ 
/*     */   private String getHelpText(String paramString)
/*     */   {
/*  64 */     if (paramString.equals(addLabel)) return addText;
/*  65 */     if (paramString.equals(cutLabel)) return cutText;
/*  66 */     if (paramString.equals(pasteLabel)) return pasteText;
/*  67 */     if (paramString.equals(sortLabel)) return sortText;
/*  68 */     if (paramString.equals(loadLabel)) return loadText;
/*  69 */     if (paramString.equals(runLabel)) return runText;
/*  70 */     if (paramString.equals(helpLabel)) return helpText;
/*  71 */     if (paramString.equals(aboutLabel)) return aboutText;
/*  72 */     return "";
/*     */   }
/*     */   
/*  75 */   static final String loadText = new String("Load menu from file.");
/*  76 */   static final String addText = new String("To add a menu item, select its\nclass file.");
/*     */   
/*     */ 
/*  79 */   static final String cutText = new String("To cut a menu item, click its\nbutton.");
/*     */   
/*     */ 
/*  82 */   static final String pasteText = new String("To paste an item, click\ntarget location.");
/*     */   
/*     */ 
/*  85 */   static final String sortText = new String("Sort menu items by index.");
/*     */   
/*  87 */   static final String runText = new String("To run a menu item, click its\nbutton and wait for it to\nstart (takes a few seconds).");
/*     */   
/*     */ 
/*     */ 
/*  91 */   static final String helpText = new String("To load tool menu, click Load. For\nhelp on a tool, click Help and then\nthe tool button. To run a tool, click\nRun and then the tool botton.");
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*  96 */   static final String aboutText = new String("WEBWEAVR-IV Front End (1998-2005)\nDeveloped by: Y.Xiang   Student Contributor: Y.Huang");
/*     */   
/*     */   private class ButtonHandler implements java.awt.event.ActionListener
/*     */   {
/*     */     private ButtonHandler() {}
/*     */     
/*     */     public void actionPerformed(ActionEvent paramActionEvent) {
/* 103 */       if ((paramActionEvent.getSource() instanceof Button)) {
/* 104 */         String str = paramActionEvent.getActionCommand();
/* 105 */         CommandPanel.this.menuPanel.setMode(str);
/* 106 */         Network.HelpPanel.addHelp(CommandPanel.this.getHelpText(str));
/*     */         
/* 108 */         if (paramActionEvent.getSource() == CommandPanel.this.addButton) {
/* 109 */           CommandPanel.this.frame.setCursor(new Cursor(3));
/* 110 */           CommandPanel.this.menuPanel.add();
/* 111 */           CommandPanel.this.frame.setCursor(new Cursor(0));
/*     */         }
/* 113 */         else if (paramActionEvent.getSource() == CommandPanel.this.loadButton)
/*     */         {
/* 115 */           CommandPanel.this.frame.setCursor(new Cursor(0));
/* 116 */           CommandPanel.this.menuPanel.load();
/* 117 */           CommandPanel.this.frame.setCursor(new Cursor(0));
/*     */         }
/* 119 */         else if (paramActionEvent.getSource() == CommandPanel.this.sortButton) {
/* 120 */           CommandPanel.this.frame.setCursor(new Cursor(3));
/* 121 */           CommandPanel.this.menuPanel.sort();
/* 122 */           CommandPanel.this.frame.setCursor(new Cursor(0));
/*     */         }
/* 124 */         else if (paramActionEvent.getSource() == CommandPanel.this.quitButton) {
/* 125 */           CommandPanel.this.menuPanel.save();
/* 126 */           CommandPanel.this.frame.dispatchEvent(new java.awt.event.WindowEvent(CommandPanel.this.frame, 201));
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              /home/anali/INAOE/tesis/codigos/topub/exe/ww4.jar!/FrontEnd/CommandPanel.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */