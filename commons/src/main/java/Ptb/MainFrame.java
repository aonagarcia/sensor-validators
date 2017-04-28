/*     */ package Ptb;
/*     */ 
/*     */ import Network.MATH;
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
/*     */ import java.io.BufferedReader;
/*     */ import java.io.FileReader;
/*     */ import java.io.IOException;
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
/*     */   void clineCode() {
/* 117 */     println("Please enter potential file path: ");
/* 118 */     String str1 = readln();
/* 119 */     BufferedReader localBufferedReader = null;
/*     */     try {
/* 121 */       localBufferedReader = new BufferedReader(new FileReader(str1));
/*     */     } catch (IOException localIOException) {
/* 123 */       println("Unable to read file name.");
/*     */     }
/*     */     
/* 126 */     int i = UTIL.loadInt(localBufferedReader);
/* 127 */     String[] arrayOfString = UTIL.loadStringListLine(localBufferedReader, i);
/* 128 */     int[] arrayOfInt1 = UTIL.loadIntList(localBufferedReader, i);
/* 129 */     int j = 1;
/* 130 */     for (int k = 0; k < i; k++) j *= arrayOfInt1[k];
/* 131 */     float[] arrayOfFloat1 = UTIL.loadRealList(localBufferedReader, j);
/*     */     
/* 133 */     float f = 0.0F;
/* 134 */     for (int m = 0; m < j; m++) f += arrayOfFloat1[m];
/* 135 */     println("sum = " + f);
/* 136 */     for (m = 0; m < j; m++) { arrayOfFloat1[m] /= f;
/*     */     }
/* 138 */     for (m = 0; m < i; m++) print(arrayOfString[m] + ":" + arrayOfInt1[m] + "\t");
/* 139 */     print(" P(");
/* 140 */     for (m = 0; m < i; m++) print(arrayOfString[m] + ":" + arrayOfInt1[m] + " ");
/* 141 */     println(")");
/* 142 */     int[][] arrayOfInt2 = MATH.getAllMix(arrayOfInt1);
/* 143 */     for (int n = 0; n < j; n++) {
/* 144 */       for (i1 = 0; i1 < i; i1++) print(arrayOfInt2[n][i1] + "\t");
/* 145 */       println("   " + arrayOfFloat1[n]);
/*     */     }
/* 147 */     println("");
/*     */     
/* 149 */     int[][] arrayOfInt3 = new int[i][1];
/* 150 */     for (int i1 = 0; i1 < i; i1++) arrayOfInt3[i1][0] = i1;
/* 151 */     float[][] arrayOfFloat = new float[i][];
/* 152 */     int i3; for (int i2 = 0; i2 < i; i2++) {
/* 153 */       print("P(" + arrayOfString[i2] + ":" + arrayOfInt1[i2] + "): ");
/* 154 */       arrayOfFloat[i2] = MATH.margin(arrayOfFloat1, arrayOfInt1, arrayOfInt3[i2]);
/* 155 */       for (i3 = 0; i3 < arrayOfFloat[i2].length; i3++)
/* 156 */         print("  " + i3 + ": " + arrayOfFloat[i2][i3]);
/* 157 */       println("");
/*     */     }
/*     */     
/*     */ 
/* 161 */     println("\nSpecify a conditional probability(y/n)?");
/* 162 */     String str2 = readln();
/* 163 */     while (str2.equals("y")) {
/* 164 */       print("\nNumber of variables in the HEAD(>0): ");
/* 165 */       str2 = readln();
/* 166 */       i3 = UTIL.getInt(str2);
/* 167 */       print("\nNumber of variables in the TAIL(>=0): ");
/* 168 */       str2 = readln();
/* 169 */       int i4 = UTIL.getInt(str2);
/*     */       
/* 171 */       print("\nIndexes of vars in HEAD: ");
/* 172 */       str2 = readln();
/* 173 */       int[] arrayOfInt4 = UTIL.getIntList(str2, i3);
/*     */       
/*     */ 
/* 176 */       int[] arrayOfInt6 = new int[i4 + 1];
/* 177 */       int[] arrayOfInt7 = new int[i4 + i3];
/*     */       int[] arrayOfInt8;
/* 179 */       int[] arrayOfInt5; Object localObject; float[] arrayOfFloat2; int i7; if (i4 > 0) {
/* 180 */         print("\nIndexs of vars in TAIL: ");
/* 181 */         str2 = readln();
/* 182 */         arrayOfInt6 = UTIL.getIntList(str2, i4);
/*     */         
/* 184 */         arrayOfInt8 = MATH.getIntersection(arrayOfInt4, arrayOfInt6);
/* 185 */         while (arrayOfInt8 != null) {
/* 186 */           print("\nHead and tail overlap. Please reenter.");
/* 187 */           print("\nIndexes of vars in HEAD: ");
/* 188 */           str2 = readln();
/* 189 */           arrayOfInt4 = UTIL.getIntList(str2, i3);
/* 190 */           print("\nIndexs of vars in TAIL: ");
/* 191 */           str2 = readln();
/* 192 */           arrayOfInt6 = UTIL.getIntList(str2, i4);
/* 193 */           arrayOfInt8 = MATH.getIntersection(arrayOfInt4, arrayOfInt6);
/*     */         }
/*     */         
/* 196 */         arrayOfInt5 = UTIL.appendToArray(arrayOfInt4, arrayOfInt6);
/* 197 */         localObject = MATH.margin(arrayOfFloat1, arrayOfInt1, arrayOfInt5);
/*     */         
/* 199 */         int[] arrayOfInt10 = MATH.union(arrayOfInt4, arrayOfInt6);
/* 200 */         int[] arrayOfInt11 = new int[i3 + i4];
/* 201 */         for (int i8 = 0; i8 < i3 + i4; i8++) arrayOfInt11[i8] = arrayOfInt1[arrayOfInt10[i8]];
/* 202 */         localObject = MATH.reorderBelief(arrayOfInt10, arrayOfInt11, (float[])localObject, arrayOfInt5);
/*     */         
/* 204 */         int[] arrayOfInt12 = MATH.sort(arrayOfInt6);
/* 205 */         int[] arrayOfInt13 = new int[i4];
/* 206 */         for (int i9 = 0; i9 < i4; i9++) arrayOfInt13[i9] = arrayOfInt1[arrayOfInt12[i9]];
/* 207 */         float[] arrayOfFloat3 = MATH.margin(arrayOfFloat1, arrayOfInt1, arrayOfInt6);
/* 208 */         arrayOfFloat3 = MATH.reorderBelief(arrayOfInt12, arrayOfInt13, arrayOfFloat3, arrayOfInt6);
/*     */         
/* 210 */         for (int i10 = 0; i10 < i4 + i3; i10++) arrayOfInt7[i10] = arrayOfInt1[arrayOfInt5[i10]];
/* 211 */         arrayOfFloat2 = MATH.division(arrayOfInt5, (float[])localObject, arrayOfInt6, arrayOfFloat3, arrayOfInt5, arrayOfInt7);
/*     */       }
/*     */       else {
/* 214 */         arrayOfInt8 = MATH.sort(arrayOfInt4);
/* 215 */         localObject = new int[i3];
/* 216 */         for (i7 = 0; i7 < i3; i7++) localObject[i7] = arrayOfInt1[arrayOfInt8[i7]];
/* 217 */         arrayOfFloat2 = MATH.margin(arrayOfFloat1, arrayOfInt1, arrayOfInt4);
/* 218 */         arrayOfFloat2 = MATH.reorderBelief(arrayOfInt8, (int[])localObject, arrayOfFloat2, arrayOfInt4);
/*     */         
/* 220 */         arrayOfInt5 = arrayOfInt4;
/* 221 */         for (i7 = 0; i7 < i3; i7++) { arrayOfInt7[i7] = arrayOfInt1[arrayOfInt4[i7]];
/*     */         }
/*     */       }
/*     */       
/* 225 */       for (int i5 = 0; i5 < arrayOfInt5.length; i5++) {
/* 226 */         print(arrayOfString[arrayOfInt5[i5]] + ":" + arrayOfInt1[arrayOfInt5[i5]] + "\t");
/*     */       }
/* 228 */       print("  P(");
/* 229 */       for (i5 = 0; i5 < arrayOfInt4.length; i5++)
/* 230 */         print(arrayOfString[arrayOfInt4[i5]] + ":" + arrayOfInt1[arrayOfInt4[i5]] + " ");
/* 231 */       if (i4 > 0) {
/* 232 */         print("|");
/* 233 */         for (i5 = 0; i5 < i4; i5++)
/* 234 */           print(arrayOfString[arrayOfInt6[i5]] + ":" + arrayOfInt1[arrayOfInt6[i5]] + " ");
/*     */       }
/* 236 */       println(")");
/*     */       
/* 238 */       int[][] arrayOfInt9 = MATH.getAllMix(arrayOfInt7);
/* 239 */       for (int i6 = 0; i6 < arrayOfFloat2.length; i6++) {
/* 240 */         for (i7 = 0; i7 < i3 + i4; i7++) print(arrayOfInt9[i6][i7] + "\t");
/* 241 */         println("   " + arrayOfFloat2[i6]);
/*     */       }
/* 243 */       println("Specify another conditional (y/n)?");
/* 244 */       str2 = readln();
/*     */     }
/*     */     
/* 247 */     println("\nPress RETURN to quit:");
/* 248 */     String str3 = readln();System.exit(0);
/*     */   }
/*     */ }


/* Location:              /home/anali/INAOE/tesis/codigos/topub/exe/ww4.jar!/Ptb/MainFrame.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */