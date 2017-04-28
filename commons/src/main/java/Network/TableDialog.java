/*     */ package Network;
/*     */ 
/*     */ import java.awt.BorderLayout;
/*     */ import java.awt.Button;
/*     */ import java.awt.Dialog;
/*     */ import java.awt.Font;
/*     */ import java.awt.Frame;
/*     */ import java.awt.GridLayout;
/*     */ import java.awt.Panel;
/*     */ import java.awt.Point;
/*     */ import java.awt.Scrollbar;
/*     */ import java.awt.TextField;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.awt.event.AdjustmentEvent;
/*     */ import java.awt.event.AdjustmentListener;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TableDialog
/*     */   extends Dialog
/*     */   implements ActionListener, AdjustmentListener
/*     */ {
/*     */   Bridge panel;
/*     */   String singleVar;
/*     */   String[] svDomain;
/*     */   String[] otherVar;
/*     */   String[][] ovDomain;
/*     */   String[] element;
/*     */   int fwidth;
/*     */   int height;
/*     */   TextField[] tf;
/*     */   TextField[] rowLabel;
/*     */   int[][] index;
/*     */   Button doneButton;
/*     */   Scrollbar bar;
/*  38 */   int row0 = 0;
/*  39 */   int row1 = 0;
/*  40 */   int rows = 0;
/*     */   
/*  42 */   Font helvetica15 = new Font("Helvetic", 1, 15);
/*     */   
/*     */ 
/*     */   int flag;
/*     */   
/*     */ 
/*     */   public TableDialog(Frame paramFrame, Bridge paramBridge, String paramString, String[] paramArrayOfString1, String[] paramArrayOfString2, String[][] paramArrayOfString, float[] paramArrayOfFloat, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
/*     */   {
/*  50 */     super(paramFrame, "", true);
/*  51 */     init(paramFrame, paramBridge, paramString, paramArrayOfString1, paramArrayOfString2, paramArrayOfString, paramInt1, paramInt2, paramInt3, paramInt4);
/*     */     
/*  53 */     int i = paramArrayOfFloat.length;
/*  54 */     String[] arrayOfString = new String[i];
/*  55 */     for (int j = 0; j < i; j++) arrayOfString[j] = String.valueOf(paramArrayOfFloat[j]);
/*  56 */     this.element = arrayOfString;
/*  57 */     setUpDialog();
/*  58 */     this.flag = 0;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public TableDialog(Frame paramFrame, Bridge paramBridge, String paramString, String[] paramArrayOfString1, String[] paramArrayOfString2, String[][] paramArrayOfString, String[] paramArrayOfString3, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
/*     */   {
/*  65 */     super(paramFrame, "", true);
/*  66 */     init(paramFrame, paramBridge, paramString, paramArrayOfString1, paramArrayOfString2, paramArrayOfString, paramInt1, paramInt2, paramInt3, paramInt4);
/*  67 */     this.element = paramArrayOfString3;
/*  68 */     setUpDialog();
/*  69 */     this.flag = 1;
/*     */   }
/*     */   
/*     */ 
/*     */   void init(Frame paramFrame, Bridge paramBridge, String paramString, String[] paramArrayOfString1, String[] paramArrayOfString2, String[][] paramArrayOfString, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
/*     */   {
/*  75 */     setLocation(paramFrame.getLocationOnScreen().x + paramInt3, paramFrame.getLocationOnScreen().y + paramInt4);
/*     */     
/*  77 */     setFont(this.helvetica15);
/*  78 */     this.panel = paramBridge;
/*  79 */     this.singleVar = paramString;
/*  80 */     this.svDomain = paramArrayOfString1;
/*  81 */     this.otherVar = paramArrayOfString2;
/*  82 */     this.ovDomain = paramArrayOfString;
/*  83 */     this.fwidth = paramInt1;
/*  84 */     this.height = paramInt2;
/*     */   }
/*     */   
/*     */   void setUpDialog() {
/*  88 */     int i = this.svDomain.length;
/*  89 */     int j = this.otherVar.length;
/*  90 */     int k = this.element.length;
/*     */     
/*  92 */     int[] arrayOfInt = new int[j];
/*  93 */     for (int m = 0; m < j; m++) arrayOfInt[m] = this.ovDomain[m].length;
/*  94 */     this.index = MATH.getAllMix(arrayOfInt);
/*     */     
/*     */ 
/*  97 */     setLayout(new BorderLayout());
/*  98 */     Panel localPanel1 = new Panel();
/*  99 */     add("North", localPanel1);
/* 100 */     Panel localPanel2 = new Panel();
/* 101 */     add("Center", localPanel2);
/* 102 */     Panel localPanel3 = new Panel();
/* 103 */     add("South", localPanel3);
/*     */     
/*     */ 
/* 106 */     localPanel1.setLayout(new BorderLayout());
/* 107 */     Panel localPanel4 = new Panel();
/* 108 */     localPanel1.add("West", localPanel4);
/* 109 */     Panel localPanel5 = new Panel();
/* 110 */     localPanel1.add("East", localPanel5);
/*     */     
/*     */ 
/* 113 */     TextField localTextField = new TextField(this.fwidth * i);
/* 114 */     localTextField.setEditable(false);
/* 115 */     localTextField.setText(this.singleVar);
/* 116 */     localPanel4.add(localTextField);
/*     */     
/*     */ 
/* 119 */     this.doneButton = new Button("Done");
/* 120 */     this.doneButton.addActionListener(this);
/* 121 */     localPanel5.add(this.doneButton);
/*     */     
/*     */ 
/* 124 */     localPanel2.setLayout(new BorderLayout());
/* 125 */     Panel localPanel6 = new Panel();
/* 126 */     localPanel2.add("West", localPanel6);
/* 127 */     Panel localPanel7 = new Panel();
/* 128 */     localPanel2.add("Center", localPanel7);
/* 129 */     Panel localPanel8 = new Panel();
/* 130 */     localPanel2.add("East", localPanel8);
/*     */     
/*     */ 
/* 133 */     localPanel6.setLayout(new GridLayout(1, i));
/* 134 */     for (int n = 0; n < i; n++) {
/* 135 */       localTextField = new TextField(this.fwidth);
/* 136 */       localTextField.setEditable(false);
/* 137 */       localTextField.setText(this.svDomain[n]);
/* 138 */       localPanel6.add(localTextField);
/*     */     }
/*     */     
/*     */ 
/* 142 */     localPanel7.setLayout(new GridLayout(1, j));
/* 143 */     for (n = 0; n < j; n++) {
/* 144 */       localTextField = new TextField(this.fwidth);
/* 145 */       localTextField.setEditable(false);
/* 146 */       localTextField.setText(this.otherVar[n]);
/* 147 */       localPanel7.add(localTextField);
/*     */     }
/*     */     
/*     */ 
/* 151 */     localPanel3.setLayout(new BorderLayout());
/* 152 */     Panel localPanel9 = new Panel();
/* 153 */     localPanel3.add("West", localPanel9);
/* 154 */     Panel localPanel10 = new Panel();
/* 155 */     localPanel3.add("Center", localPanel10);
/*     */     
/*     */ 
/* 158 */     this.rows = (k / i);
/* 159 */     if (this.rows >= this.height) { this.rows = this.height;
/*     */     }
/* 161 */     int i1 = this.rows * i;
/* 162 */     this.tf = new TextField[i1];
/*     */     
/* 164 */     int i2 = this.rows * j;
/* 165 */     this.rowLabel = new TextField[i2];
/*     */     
/*     */ 
/* 168 */     this.row0 = 0;
/* 169 */     this.bar = new Scrollbar(1, this.row0, this.rows, 0, k / i);
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 174 */     this.bar.addAdjustmentListener(this);
/* 175 */     localPanel3.add("East", this.bar);
/*     */     
/*     */ 
/* 178 */     localPanel9.setLayout(new GridLayout(this.rows, i));
/* 179 */     for (int i3 = 0; i3 < i1; i3++) {
/* 180 */       this.tf[i3] = new TextField(this.fwidth);
/* 181 */       this.tf[i3].setText(this.element[i3]);
/* 182 */       localPanel9.add(this.tf[i3]);
/*     */     }
/*     */     
/*     */ 
/* 186 */     localPanel10.setLayout(new GridLayout(this.rows, j));
/* 187 */     for (i3 = this.row0; i3 < this.rows; i3++) {
/* 188 */       for (int i4 = 0; i4 < j; i4++) {
/* 189 */         localTextField = this.rowLabel[(i3 * j + i4)] = new TextField(this.fwidth);
/* 190 */         localTextField.setEditable(false);
/* 191 */         localTextField.setText(this.ovDomain[i4][this.index[i3][i4]]);
/* 192 */         localPanel10.add(localTextField);
/*     */       }
/*     */     }
/* 195 */     getPageLimit();
/* 196 */     pack();
/*     */   }
/*     */   
/*     */   void getPageLimit()
/*     */   {
/* 201 */     int i = this.svDomain.length;
/* 202 */     int j = this.element.length;
/* 203 */     if (this.row0 + this.rows >= j / i) {
/* 204 */       this.row1 = (j / i - 1);
/* 205 */       this.row0 = (this.row1 - this.rows + 1);
/* 206 */       if (this.row0 < 0) this.row0 = 0;
/*     */     }
/*     */     else {
/* 209 */       this.row1 = (this.row0 + this.rows - 1);
/*     */     }
/*     */   }
/*     */   
/*     */   void showOnePageElement()
/*     */   {
/* 215 */     int i = this.svDomain.length;
/* 216 */     for (int j = this.row0; j <= this.row1; j++) {
/* 217 */       for (int k = 0; k < i; k++) {
/* 218 */         this.tf[((j - this.row0) * i + k)].setText(this.element[(j * i + k)]);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   void showOnePageRowLabel()
/*     */   {
/* 225 */     int i = this.otherVar.length;
/* 226 */     for (int j = this.row0; j <= this.row1; j++) {
/* 227 */       for (int k = 0; k < i; k++) {
/* 228 */         StringBuffer localStringBuffer = new StringBuffer(this.ovDomain[k][this.index[j][k]]);
/* 229 */         localStringBuffer.setLength(this.fwidth);
/* 230 */         this.rowLabel[((j - this.row0) * i + k)].setText(localStringBuffer.toString());
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public void actionPerformed(ActionEvent paramActionEvent)
/*     */   {
/* 238 */     if (paramActionEvent.getSource() == this.doneButton) {
/* 239 */       getUserInput();
/*     */       
/* 241 */       if (this.flag == 0) {
/* 242 */         int i = this.element.length;
/* 243 */         float[] arrayOfFloat = new float[i];
/* 244 */         for (int j = 0; j < i; j++) arrayOfFloat[j] = Float.valueOf(this.element[j]).floatValue();
/* 245 */         this.panel.setTable(arrayOfFloat);
/*     */       } else {
/* 247 */         this.panel.setVector2(this.element);
/*     */       }
/* 249 */       this.panel.showNet();
/* 250 */       dispose();
/*     */     }
/*     */   }
/*     */   
/*     */   public void adjustmentValueChanged(AdjustmentEvent paramAdjustmentEvent)
/*     */   {
/* 256 */     getUserInput();
/*     */     
/* 258 */     this.row0 = ((Scrollbar)paramAdjustmentEvent.getSource()).getValue();
/* 259 */     getPageLimit();
/* 260 */     showOnePageElement();
/* 261 */     showOnePageRowLabel();
/*     */   }
/*     */   
/*     */ 
/*     */   void getUserInput()
/*     */   {
/* 267 */     int i = this.svDomain.length;
/* 268 */     for (int j = this.row0; j <= this.row1; j++) {
/* 269 */       for (int k = 0; k < i; k++) {
/* 270 */         this.element[(j * i + k)] = this.tf[((j - this.row0) * i + k)].getText();
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              /home/anali/INAOE/tesis/codigos/topub/exe/ww4.jar!/Network/TableDialog.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */