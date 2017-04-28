/*     */ package Bind;
/*     */ 
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
/*     */ 
/*     */ 
/*     */ class MainFrameC
/*     */   extends Frame
/*     */   implements ActionListener
/*     */ {
/*     */   TextArea winOut;
/*     */   TextField winIn;
/*  42 */   boolean inReady = false;
/*     */   
/*     */   MainFrameC(String paramString, String[] paramArrayOfString) {
/*  45 */     super(paramString);
/*  46 */     Dimension localDimension = calcFrameSize();
/*  47 */     setSize(localDimension);
/*  48 */     setFont(new Font("Helvetic", 1, 14));
/*     */     
/*  50 */     int i = 0;int j = 5;
/*  51 */     setLayout(new BorderLayout(i, j));
/*     */     
/*  53 */     this.winOut = new TextArea();
/*  54 */     add("Center", this.winOut);
/*     */     
/*  56 */     Panel localPanel = new Panel();
/*  57 */     localPanel.setLayout(new BorderLayout(0, 0));
/*  58 */     localPanel.add("West", new Label("Input :"));
/*  59 */     this.winIn = new TextField();
/*  60 */     this.winIn.requestFocus();
/*  61 */     localPanel.add("Center", this.winIn);
/*  62 */     this.winIn.addActionListener(this);
/*  63 */     add("South", localPanel);
/*     */     
/*  65 */     addWindowListener(new WindowAdapter()
/*     */     {
/*     */       public void windowClosing(WindowEvent paramAnonymousWindowEvent) {
/*  68 */         System.exit(0);
/*     */       }
/*     */       
/*     */ 
/*  72 */     });
/*  73 */     setVisible(true);
/*     */     
/*  75 */     if (paramArrayOfString == null) clineCode(); else {
/*  76 */       clineCode(paramArrayOfString);
/*     */     }
/*     */   }
/*     */   
/*     */   private static Dimension calcFrameSize()
/*     */   {
/*  82 */     Toolkit localToolkit = Toolkit.getDefaultToolkit();
/*  83 */     Dimension localDimension = localToolkit.getScreenSize();
/*  84 */     localDimension.width /= 3;
/*  85 */     localDimension.height /= 2;
/*  86 */     return localDimension;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   void print(String paramString)
/*     */   {
/*  93 */     this.winOut.append(paramString);
/*     */   }
/*     */   
/*     */   void println(String paramString) {
/*  97 */     this.winOut.append(paramString + "\n");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   String readln()
/*     */   {
/* 105 */     this.winIn.requestFocus();
/* 106 */     this.inReady = false;
/*     */     
/* 108 */     while (!this.inReady) {
/*     */       try {
/* 110 */         Thread.sleep(1000L);
/*     */       } catch (Exception localException) {}
/*     */     }
/* 113 */     String str = this.winIn.getText();
/*     */     
/* 115 */     this.winIn.setText("");
/* 116 */     println(str);
/* 117 */     this.inReady = false;
/* 118 */     return str;
/*     */   }
/*     */   
/*     */   public void actionPerformed(ActionEvent paramActionEvent)
/*     */   {
/* 123 */     this.inReady = true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   void clineCode()
/*     */   {
/* 130 */     println("Enter agent name, path, binder host and port");
/* 131 */     println("(Ex 3cn0 ../kb/3cns/ bayes.cs.uoguelph.ca 33333):");
/* 132 */     StringTokenizer localStringTokenizer = new StringTokenizer(readln());
/* 133 */     String str1 = localStringTokenizer.nextToken();
/* 134 */     String str2 = new String(localStringTokenizer.nextToken() + str1);
/* 135 */     String str3 = localStringTokenizer.nextToken();
/* 136 */     int i = Integer.parseInt(localStringTokenizer.nextToken());
/*     */     
/* 138 */     Cell localCell = new Cell(str1, str2, str3, i, this);
/* 139 */     println("\nPress RETURN to quit:");
/* 140 */     String str4 = readln();System.exit(0);
/*     */   }
/*     */   
/* 143 */   void clineCode(String[] paramArrayOfString) { String str1 = paramArrayOfString[0];
/* 144 */     String str2 = new String(paramArrayOfString[1] + str1);
/* 145 */     String str3 = paramArrayOfString[2];
/* 146 */     int i = Integer.parseInt(paramArrayOfString[3]);
/*     */     
/* 148 */     Cell localCell = new Cell(str1, str2, str3, i, this);
/* 149 */     System.exit(0);
/*     */   }
/*     */ }


/* Location:              /home/anali/INAOE/tesis/codigos/topub/exe/ww4.jar!/Bind/MainFrameC.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */