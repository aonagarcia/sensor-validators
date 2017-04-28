/*     */ package RandBn;
/*     */ 
/*     */ import Network.BayesNet;
/*     */ import Network.DirectGraph;
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
/*     */ import java.io.FileWriter;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintWriter;
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
/*  44 */   boolean inReady = false;
/*     */   
/*     */   MainFrame(String paramString) {
/*  47 */     super(paramString);
/*  48 */     Dimension localDimension = calcFrameSize();
/*  49 */     setSize(localDimension);
/*  50 */     setFont(new Font("Helvetic", 1, 14));
/*     */     
/*  52 */     int i = 0;int j = 5;
/*  53 */     setLayout(new BorderLayout(i, j));
/*     */     
/*  55 */     this.winOut = new TextArea();
/*  56 */     add("Center", this.winOut);
/*     */     
/*  58 */     Panel localPanel = new Panel();
/*  59 */     localPanel.setLayout(new BorderLayout(0, 0));
/*  60 */     localPanel.add("West", new Label("Input :"));
/*  61 */     this.winIn = new TextField();
/*  62 */     this.winIn.requestFocus();
/*  63 */     localPanel.add("Center", this.winIn);
/*  64 */     this.winIn.addActionListener(this);
/*  65 */     add("South", localPanel);
/*     */     
/*  67 */     addWindowListener(new WindowAdapter()
/*     */     {
/*     */       public void windowClosing(WindowEvent paramAnonymousWindowEvent) {
/*  70 */         System.exit(0);
/*     */       }
/*     */       
/*     */ 
/*  74 */     });
/*  75 */     setVisible(true);
/*  76 */     clineCode();
/*     */   }
/*     */   
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
/* 130 */     println("Enter # nodes, # roots, max indegree and full bn file path:");
/* 131 */     String[] arrayOfString = UTIL.getStringList(readln());
/*     */     
/* 133 */     int i = Integer.parseInt(arrayOfString[0]);
/* 134 */     int j = Integer.parseInt(arrayOfString[1]);
/* 135 */     int k = Integer.parseInt(arrayOfString[2]);
/* 136 */     String str1 = new String(arrayOfString[3]);
/*     */     
/* 138 */     println("n=" + i + " r=" + j + " m=" + k + " file=" + str1);
/*     */     
/* 140 */     BayesNet localBayesNet = null;
/* 141 */     localBayesNet = new BayesNet(new DirectGraph(i, k, j));
/* 142 */     int m = localBayesNet.getNodeCount();
/* 143 */     if (m > 0) {
/* 144 */       localBayesNet.setPos(450, 400);
/* 145 */       println("Positions set.");
/* 146 */       localBayesNet.initExpn();
/* 147 */       println("Expressions set.");
/* 148 */       localBayesNet.setRandCondProb();
/* 149 */       println("Rand probs set. All vars are binary.");
/* 150 */       for (int n = 0; n < m; n++) localBayesNet.setLabel(n, new String("v" + n));
/* 151 */       println("Labels set.");
/*     */       try {
/* 153 */         PrintWriter localPrintWriter = new PrintWriter(new FileWriter(str1));
/* 154 */         println("Saving the current network into " + str1);
/* 155 */         localBayesNet.save(localPrintWriter);
/* 156 */         localPrintWriter.close();
/*     */       } catch (IOException localIOException) {
/* 158 */         println("Unable to save " + str1);
/*     */       }
/* 160 */       println("Saving completed.");
/*     */     } else {
/* 162 */       println("Invalid input entered.");
/*     */     }
/* 164 */     println("\nPress RETURN to quit:");
/* 165 */     String str2 = readln();System.exit(0);
/*     */   }
/*     */ }


/* Location:              /home/anali/INAOE/tesis/codigos/topub/exe/ww4.jar!/RandBn/MainFrame.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */