/*     */ package IntMsbn;
/*     */ 
/*     */ import java.awt.Button;
/*     */ import java.awt.Cursor;
/*     */ import java.awt.Frame;
/*     */ import java.awt.event.ActionEvent;
/*     */ 
/*     */ class CommandPanel extends java.awt.Panel
/*     */ {
/*  10 */   public static final String agtOrgLabel = new String("Agt Org");
/*  11 */   public static final String agtNmLabel = new String("Name Agt");
/*  12 */   public static final String publicLabel = new String("Pub Var");
/*  13 */   public static final String mvAgtLabel = new String("Mv Agt");
/*  14 */   public static final String delAgtLabel = new String("Del Agt");
/*  15 */   public static final String delLinkLabel = new String("Del Link");
/*  16 */   public static final String delAllLabel = new String("Del All");
/*  17 */   public static final String loadLabel = new String("Load Bdr");
/*  18 */   public static final String saveLabel = new String("Save Bdr");
/*  19 */   public static final String mvOrgLabel = new String("Arrange");
/*  20 */   public static final String hyptrLabel = new String("Hyp Tree?");
/*  21 */   public static final String aboutLabel = new String("About");
/*  22 */   public static final String quitLabel = new String("Quit");
/*     */   private Button agtOrgButton;
/*     */   private Button agtNmButton;
/*     */   private Button publicButton;
/*     */   private Button mvAgtButton;
/*     */   private Button delAgtButton;
/*     */   private Button delLinkButton;
/*  29 */   private Button delAllButton; private Button loadButton; private Button saveButton; private Button mvOrgButton; private Button hyptrButton; private Button aboutButton; private Button quitButton; java.awt.Font helvetica14 = new java.awt.Font("Helvetic", 1, 14);
/*     */   
/*  31 */   Frame frame = null;
/*     */   NetworkPanel netPanel;
/*     */   
/*     */   CommandPanel(Frame paramFrame, NetworkPanel paramNetworkPanel)
/*     */   {
/*  36 */     this.frame = paramFrame;
/*  37 */     this.netPanel = paramNetworkPanel;
/*  38 */     setFont(this.helvetica14);
/*     */     
/*  40 */     int i = 2;int j = 7;int k = 6;int m = 0;
/*  41 */     setLayout(new java.awt.GridLayout(i, j, k, m));
/*  42 */     this.agtOrgButton = new Button(agtOrgLabel);
/*  43 */     this.agtNmButton = new Button(agtNmLabel);
/*  44 */     this.publicButton = new Button(publicLabel);
/*  45 */     this.mvAgtButton = new Button(mvAgtLabel);
/*  46 */     this.delAgtButton = new Button(delAgtLabel);
/*  47 */     this.delLinkButton = new Button(delLinkLabel);
/*  48 */     this.delAllButton = new Button(delAllLabel);
/*  49 */     this.loadButton = new Button(loadLabel);
/*  50 */     this.saveButton = new Button(saveLabel);
/*  51 */     this.mvOrgButton = new Button(mvOrgLabel);
/*  52 */     this.hyptrButton = new Button(hyptrLabel);
/*  53 */     this.aboutButton = new Button(aboutLabel);
/*  54 */     this.quitButton = new Button(quitLabel);
/*     */     
/*  56 */     add(this.agtOrgButton);
/*  57 */     add(this.agtNmButton);
/*  58 */     add(this.publicButton);
/*  59 */     add(this.mvAgtButton);
/*  60 */     add(this.delAgtButton);
/*  61 */     add(this.delLinkButton);
/*  62 */     add(this.delAllButton);
/*  63 */     add(this.loadButton);
/*  64 */     add(this.saveButton);
/*  65 */     add(this.mvOrgButton);
/*  66 */     add(this.hyptrButton);
/*  67 */     add(this.aboutButton);
/*  68 */     add(this.quitButton);
/*     */     
/*  70 */     ButtonHandler localButtonHandler = new ButtonHandler(null);
/*  71 */     this.agtOrgButton.addActionListener(localButtonHandler);
/*  72 */     this.agtNmButton.addActionListener(localButtonHandler);
/*  73 */     this.publicButton.addActionListener(localButtonHandler);
/*  74 */     this.mvAgtButton.addActionListener(localButtonHandler);
/*  75 */     this.delAgtButton.addActionListener(localButtonHandler);
/*  76 */     this.delLinkButton.addActionListener(localButtonHandler);
/*  77 */     this.delAllButton.addActionListener(localButtonHandler);
/*  78 */     this.loadButton.addActionListener(localButtonHandler);
/*  79 */     this.saveButton.addActionListener(localButtonHandler);
/*  80 */     this.mvOrgButton.addActionListener(localButtonHandler);
/*  81 */     this.hyptrButton.addActionListener(localButtonHandler);
/*  82 */     this.aboutButton.addActionListener(localButtonHandler);
/*  83 */     this.quitButton.addActionListener(localButtonHandler);
/*     */   }
/*     */   
/*     */ 
/*     */   private String getHelpText(String paramString)
/*     */   {
/*  89 */     if (paramString.equals(agtOrgLabel)) return agtOrgText;
/*  90 */     if (paramString.equals(agtNmLabel)) return agtNmText;
/*  91 */     if (paramString.equals(mvAgtLabel)) return mvAgtText;
/*  92 */     if (paramString.equals(delAgtLabel)) return delAgtText;
/*  93 */     if (paramString.equals(delLinkLabel)) return delArcText;
/*  94 */     if (paramString.equals(delAllLabel)) return delAllText;
/*  95 */     if (paramString.equals(mvOrgLabel)) return mvOrgText;
/*  96 */     if (paramString.equals(publicLabel)) return publicText;
/*  97 */     if (paramString.equals(saveLabel)) return saveText;
/*  98 */     if (paramString.equals(loadLabel)) return loadText;
/*  99 */     if (paramString.equals(hyptrLabel)) return hyptrText;
/* 100 */     if (paramString.equals(aboutLabel)) return aboutText;
/* 101 */     if (paramString.equals(quitLabel)) return quitText;
/* 102 */     return "";
/*     */   }
/*     */   
/* 105 */   static final String aboutText = new String("WEBWEAVR-IV : Multiagent MSBN Integrator (2005)\nCopyright by Y. Xiang");
/*     */   
/*     */ 
/*     */ 
/* 109 */   static final String agtOrgText = new String("To define agent organization, draw direct communication channel\nbetween 2 agents by clicking on one and drag to the other.");
/*     */   
/*     */ 
/*     */ 
/* 113 */   static final String agtNmText = new String("To name an agent, click on it and specify its name.");
/*     */   
/*     */ 
/* 116 */   static final String mvAgtText = new String("To move an agent, click on it and drag to the new position.");
/*     */   
/*     */ 
/* 119 */   static final String delAgtText = new String("To delete an agent, click on it.");
/*     */   
/*     */ 
/* 122 */   static final String delArcText = new String("To delete a direct communication channel, click on it.");
/*     */   
/*     */ 
/* 125 */   static final String delAllText = new String("Clicking on this button deletes current agent organization.");
/*     */   
/*     */ 
/* 128 */   static final String mvOrgText = new String("Reapprange display of the current agent organization.");
/*     */   
/*     */ 
/* 131 */   static final String publicText = new String("To define public variables for an agent, click on it.");
/*     */   
/*     */ 
/* 134 */   static final String hyptrText = new String("Test if the specified agent organization is a hypeetree.");
/*     */   
/*     */ 
/* 137 */   static final String saveText = new String("Save the binder file.");
/*     */   
/*     */ 
/* 140 */   static final String loadText = new String("Load the binder file for an MAS MSBN.");
/*     */   
/*     */ 
/* 143 */   static final String quitText = new String("Thank you for using WEBWEAVR-IV.");
/*     */   
/*     */   private class ButtonHandler implements java.awt.event.ActionListener {
/*     */     private ButtonHandler() {}
/*     */     
/*     */     public void actionPerformed(ActionEvent paramActionEvent) {
/* 149 */       if ((paramActionEvent.getSource() instanceof Button)) {
/* 150 */         String str = paramActionEvent.getActionCommand();
/* 151 */         CommandPanel.this.netPanel.setMode(str);
/* 152 */         Network.HelpPanel.addHelp(CommandPanel.this.getHelpText(str));
/*     */         
/* 154 */         if (paramActionEvent.getSource() != CommandPanel.this.agtOrgButton)
/*     */         {
/* 156 */           if (paramActionEvent.getSource() == CommandPanel.this.agtNmButton) {
/* 157 */             if (!CommandPanel.this.netPanel.hasNode()) {
/* 158 */               Network.HelpPanel.showError("Please define agent organization first.");
/*     */             }
/* 160 */           } else if (paramActionEvent.getSource() != CommandPanel.this.mvAgtButton)
/*     */           {
/* 162 */             if (paramActionEvent.getSource() != CommandPanel.this.delAgtButton)
/*     */             {
/* 164 */               if (paramActionEvent.getSource() != CommandPanel.this.delLinkButton)
/*     */               {
/* 166 */                 if (paramActionEvent.getSource() != CommandPanel.this.publicButton)
/*     */                 {
/* 168 */                   if (paramActionEvent.getSource() == CommandPanel.this.saveButton) {
/* 169 */                     CommandPanel.this.frame.setCursor(new Cursor(3));
/* 170 */                     CommandPanel.this.netPanel.save();
/* 171 */                     CommandPanel.this.frame.setCursor(new Cursor(0));
/*     */                   }
/* 173 */                   else if (paramActionEvent.getSource() == CommandPanel.this.loadButton) {
/* 174 */                     CommandPanel.this.frame.setCursor(new Cursor(3));
/* 175 */                     CommandPanel.this.netPanel.load();
/* 176 */                     CommandPanel.this.frame.setCursor(new Cursor(0));
/*     */                   }
/* 178 */                   else if (paramActionEvent.getSource() == CommandPanel.this.hyptrButton) {
/* 179 */                     CommandPanel.this.frame.setCursor(new Cursor(3));
/* 180 */                     CommandPanel.this.netPanel.isHypertree();
/* 181 */                     CommandPanel.this.frame.setCursor(new Cursor(0));
/*     */                   }
/* 183 */                   else if (paramActionEvent.getSource() == CommandPanel.this.delAllButton) {
/* 184 */                     CommandPanel.this.frame.setCursor(new Cursor(3));
/* 185 */                     CommandPanel.this.netPanel.delAll();
/* 186 */                     CommandPanel.this.frame.setCursor(new Cursor(0));
/*     */                   }
/* 188 */                   else if (paramActionEvent.getSource() == CommandPanel.this.mvOrgButton) {
/* 189 */                     CommandPanel.this.frame.setCursor(new Cursor(3));
/* 190 */                     CommandPanel.this.netPanel.moveNet();
/* 191 */                     CommandPanel.this.frame.setCursor(new Cursor(0));
/*     */                   }
/* 193 */                   else if (paramActionEvent.getSource() == CommandPanel.this.quitButton) {
/* 194 */                     CommandPanel.this.frame.dispatchEvent(new java.awt.event.WindowEvent(CommandPanel.this.frame, 201));
/*     */                   }
/*     */                 }
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              /home/anali/INAOE/tesis/codigos/topub/exe/ww4.jar!/IntMsbn/CommandPanel.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */