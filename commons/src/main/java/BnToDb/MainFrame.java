/*     */ package BnToDb;
/*     */ 
/*     */ import Network.BayesNet;
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
/*     */ import java.io.PrintStream;
/*     */ import java.io.PrintWriter;
/*     */ import java.util.Random;
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
/*  33 */   boolean inReady = false;
/*     */   
/*     */   MainFrame(String paramString) {
/*  36 */     super(paramString);
/*  37 */     Dimension localDimension = calcFrameSize();
/*  38 */     setSize(localDimension);
/*  39 */     setFont(new Font("Helvetic", 1, 14));
/*     */     
/*  41 */     int i = 0;int j = 5;
/*  42 */     setLayout(new BorderLayout(i, j));
/*     */     
/*  44 */     this.winOut = new TextArea();
/*  45 */     add("Center", this.winOut);
/*     */     
/*  47 */     Panel localPanel = new Panel();
/*  48 */     localPanel.setLayout(new BorderLayout(0, 0));
/*  49 */     localPanel.add("West", new Label("Input :"));
/*  50 */     this.winIn = new TextField();
/*  51 */     this.winIn.requestFocus();
/*  52 */     localPanel.add("Center", this.winIn);
/*  53 */     this.winIn.addActionListener(this);
/*  54 */     add("South", localPanel);
/*     */     
/*  56 */     addWindowListener(new WindowAdapter()
/*     */     {
/*     */       public void windowClosing(WindowEvent paramAnonymousWindowEvent) {
/*  59 */         System.exit(0);
/*     */       }
/*     */       
/*     */ 
/*  63 */     });
/*  64 */     setVisible(true);
/*     */     
/*  66 */     clineCode();
/*     */   }
/*     */   
/*     */ 
/*     */   private static Dimension calcFrameSize()
/*     */   {
/*  72 */     Toolkit localToolkit = Toolkit.getDefaultToolkit();
/*  73 */     Dimension localDimension = localToolkit.getScreenSize();
/*  74 */     localDimension.width /= 3;
/*  75 */     localDimension.height /= 2;
/*  76 */     return localDimension;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   void print(String paramString)
/*     */   {
/*  83 */     this.winOut.append(paramString);
/*     */   }
/*     */   
/*     */   void println(String paramString) {
/*  87 */     this.winOut.append(paramString + "\n");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   String readln()
/*     */   {
/*  95 */     this.winIn.requestFocus();
/*  96 */     this.inReady = false;
/*     */     
/*  98 */     while (!this.inReady) {
/*     */       try {
/* 100 */         Thread.sleep(1000L);
/*     */       } catch (Exception localException) {}
/*     */     }
/* 103 */     String str = this.winIn.getText();
/*     */     
/* 105 */     this.winIn.setText("");
/* 106 */     println(str);
/* 107 */     this.inReady = false;
/* 108 */     return str;
/*     */   }
/*     */   
/*     */   public void actionPerformed(ActionEvent paramActionEvent)
/*     */   {
/* 113 */     this.inReady = true;
/*     */   }
/*     */   
/*     */   void clineCode()
/*     */   {
/* 118 */     BayesNet localBayesNet = null;
/* 119 */     println("Please enter .bn file path such as ../kb/fire/fire.bn : ");
/* 120 */     String str1 = readln();
/* 121 */     String str2 = System.getProperty("user.dir");
/* 122 */     if (str1.indexOf("..") != -1) { str1 = str2 + "/" + str1;
/*     */     }
/* 124 */     str1 = new String(UTIL.replacePostfix(str1, "bn"));
/* 125 */     localBayesNet = BayesNet.load(str1);
/* 126 */     if (localBayesNet != null) { println("Network loaded.");
/*     */     } else {
/* 128 */       println("Error in loading network.");System.exit(0);
/*     */     }
/* 130 */     int i = localBayesNet.getNodeCount();
/*     */     
/*     */ 
/* 133 */     println("Enter output file name such as data.raw : ");
/* 134 */     str1 = readln();
/* 135 */     if (str1.indexOf("..") != -1) { str1 = str2 + "/" + str1;
/*     */     }
/* 137 */     println("Number of cases to be generated:");
/* 138 */     String str3 = readln();
/* 139 */     int j = UTIL.getInt(str3);
/* 140 */     println("# of cases = " + j);
/*     */     
/*     */ 
/* 143 */     str1 = new String(UTIL.replacePostfix(str1, "cod"));
/*     */     try {
/* 145 */       PrintWriter localPrintWriter1 = new PrintWriter(new FileWriter(str1));
/* 146 */       for (k = 0; k < i; k++) UTIL.savePoint(localPrintWriter1, localBayesNet.getPos(k));
/* 147 */       localPrintWriter1.close();
/*     */     } catch (IOException localIOException1) {
/* 149 */       println("Unable to save " + str1 + ".");
/*     */     }
/* 151 */     println("Saving " + str1 + " completed.");
/*     */     
/*     */ 
/* 154 */     int[] arrayOfInt = localBayesNet.getTopOrder();
/* 155 */     print("Node order = ");
/* 156 */     for (int k = 0; k < i; k++) print(arrayOfInt[k] + " ");
/* 157 */     println("");
/*     */     
/*     */ 
/* 160 */     Random localRandom = new Random();
/*     */     
/* 162 */     str1 = new String(UTIL.replacePostfix(str1, "raw"));
/*     */     try {
/* 164 */       PrintWriter localPrintWriter2 = new PrintWriter(new FileWriter(str1));
/*     */       
/* 166 */       String[] arrayOfString2 = localBayesNet.getLabel();
/* 167 */       arrayOfString2 = UTIL.evenLength(arrayOfString2, 12);
/* 168 */       str3 = new String("");
/* 169 */       for (int m = 0; m < i; m++) str3 = new String(str3 + arrayOfString2[m]);
/* 170 */       localPrintWriter2.println(str3);
/*     */       
/* 172 */       for (m = 0; m < j; m++) {
/* 173 */         localBayesNet.setVarValue(arrayOfInt, localRandom);
/* 174 */         String[] arrayOfString1 = localBayesNet.getStrValue();
/* 175 */         arrayOfString1 = UTIL.evenLength(arrayOfString1, 12);
/*     */         
/* 177 */         str3 = new String("");
/* 178 */         for (int n = 0; n < i; n++) str3 = new String(str3 + arrayOfString1[n]);
/* 179 */         localPrintWriter2.println(str3);
/*     */         
/* 181 */         if ((m > 0) && (m % 100 == 0)) System.out.println(m + " cases completed.");
/*     */       }
/* 183 */       localPrintWriter2.close();
/*     */     } catch (IOException localIOException2) {
/* 185 */       println("Unable to save " + str1 + ".");
/*     */     }
/* 187 */     println("Saving " + str1 + " completed.");
/*     */     
/* 189 */     println("\nPress RETURN to quit:");
/* 190 */     String str4 = readln();System.exit(0);
/*     */   }
/*     */ }


/* Location:              /home/anali/INAOE/tesis/codigos/topub/exe/ww4.jar!/BnToDb/MainFrame.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */