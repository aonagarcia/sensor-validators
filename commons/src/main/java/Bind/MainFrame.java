/*     */ package Bind;
/*     */ 
/*     */ import Network.UTIL;
/*     */ import java.awt.BorderLayout;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Font;
/*     */ import java.awt.Frame;
/*     */ import java.awt.Label;
/*     */ import java.awt.Panel;
/*     */ import java.awt.TextArea;
/*     */ import java.awt.TextField;
/*     */ import java.awt.Toolkit;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.awt.event.WindowAdapter;
/*     */ import java.awt.event.WindowEvent;
/*     */ import java.util.StringTokenizer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class MainFrame
/*     */   extends Frame
/*     */   implements ActionListener
/*     */ {
/*     */   TextArea winOut;
/*     */   TextField winIn;
/*  41 */   boolean inReady = false;
/*     */   
/*     */   MainFrame(String paramString1, String paramString2) {
/*  44 */     super(paramString1);
/*  45 */     Dimension localDimension = calcFrameSize();
/*  46 */     setSize(localDimension);
/*  47 */     setFont(new Font("Helvetic", 1, 14));
/*     */     
/*  49 */     int i = 0;int j = 5;
/*  50 */     setLayout(new BorderLayout(i, j));
/*     */     
/*  52 */     this.winOut = new TextArea();
/*  53 */     add("Center", this.winOut);
/*     */     
/*  55 */     Panel localPanel = new Panel();
/*  56 */     localPanel.setLayout(new BorderLayout(0, 0));
/*  57 */     localPanel.add("West", new Label("Input :"));
/*  58 */     this.winIn = new TextField();
/*  59 */     this.winIn.requestFocus();
/*  60 */     localPanel.add("Center", this.winIn);
/*  61 */     this.winIn.addActionListener(this);
/*  62 */     add("South", localPanel);
/*     */     
/*  64 */     addWindowListener(new WindowAdapter()
/*     */     {
/*     */       public void windowClosing(WindowEvent paramAnonymousWindowEvent) {
/*  67 */         System.exit(0);
/*     */       }
/*     */       
/*     */ 
/*  71 */     });
/*  72 */     setVisible(true);
/*     */     
/*  74 */     if (paramString2 == null) clineCode(); else {
/*  75 */       clineCode(paramString2);
/*     */     }
/*     */   }
/*     */   
/*     */   private static Dimension calcFrameSize()
/*     */   {
/*  81 */     Toolkit localToolkit = Toolkit.getDefaultToolkit();
/*  82 */     Dimension localDimension = localToolkit.getScreenSize();
/*  83 */     localDimension.width /= 3;
/*  84 */     localDimension.height /= 2;
/*  85 */     return localDimension;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   void print(String paramString)
/*     */   {
/*  92 */     this.winOut.append(paramString);
/*     */   }
/*     */   
/*     */   void println(String paramString) {
/*  96 */     this.winOut.append(paramString + "\n");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   String readln()
/*     */   {
/* 104 */     this.winIn.requestFocus();
/* 105 */     this.inReady = false;
/*     */     
/* 107 */     while (!this.inReady) {
/*     */       try {
/* 109 */         Thread.sleep(1000L);
/*     */       } catch (Exception localException) {}
/*     */     }
/* 112 */     String str = this.winIn.getText();
/*     */     
/* 114 */     this.winIn.setText("");
/* 115 */     println(str);
/* 116 */     this.inReady = false;
/* 117 */     return str;
/*     */   }
/*     */   
/*     */   public void actionPerformed(ActionEvent paramActionEvent)
/*     */   {
/* 122 */     this.inReady = true;
/*     */   }
/*     */   
/*     */   void clineCode()
/*     */   {
/* 127 */     println("Enter .bdr file path: (Ex ../kb/3cns/3cns.bdr)");
/* 128 */     String str1 = new StringTokenizer(readln()).nextToken();
/* 129 */     println("Path entered: " + str1);
/* 130 */     String str2 = new String(UTIL.removePostfix(str1) + ".bdr");
/* 131 */     new Binder(str2, this);
/*     */     
/* 133 */     println("\nPress RETURN to quit:");
/* 134 */     readln();System.exit(0);
/*     */   }
/*     */   
/* 137 */   void clineCode(String paramString) { println("Path entered: " + paramString);
/* 138 */     String str = new String(UTIL.removePostfix(paramString) + ".bdr");
/* 139 */     new Binder(str, this);
/* 140 */     System.exit(0);
/*     */   }
/*     */ }


/* Location:              /home/anali/INAOE/tesis/codigos/topub/exe/ww4.jar!/Bind/MainFrame.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */