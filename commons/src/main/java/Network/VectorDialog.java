/*     */ package Network;
/*     */ 
/*     */ import java.awt.Button;
/*     */ import java.awt.Dialog;
/*     */ import java.awt.Font;
/*     */ import java.awt.Frame;
/*     */ import java.awt.GridLayout;
/*     */ import java.awt.Panel;
/*     */ import java.awt.Point;
/*     */ import java.awt.TextField;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
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
/*     */ public class VectorDialog
/*     */   extends Dialog
/*     */   implements ActionListener
/*     */ {
/*     */   Bridge panel;
/*     */   String title;
/*     */   String[] label;
/*     */   String[] element;
/*     */   int width;
/*  32 */   int flag = 0;
/*     */   
/*     */   Button doneButton;
/*     */   TextField[] f;
/*     */   int count;
/*     */   String[] vector;
/*  38 */   Font helvetica15 = new Font("Helvetic", 1, 15);
/*     */   
/*     */ 
/*     */   public VectorDialog(Frame paramFrame, Bridge paramBridge, String paramString, String[] paramArrayOfString1, String[] paramArrayOfString2, int paramInt1, int paramInt2, int paramInt3)
/*     */   {
/*  43 */     super(paramFrame, "", true);
/*  44 */     setLocation(paramFrame.getLocationOnScreen().x + paramInt2, paramFrame.getLocationOnScreen().y + paramInt3);
/*     */     
/*  46 */     setFont(this.helvetica15);
/*     */     
/*  48 */     this.panel = paramBridge;
/*  49 */     this.title = paramString;
/*  50 */     this.label = UTIL.getDuplicate(paramArrayOfString1);
/*  51 */     this.width = paramInt1;
/*  52 */     this.count = paramArrayOfString1.length;
/*     */     
/*  54 */     this.element = UTIL.getDuplicate(paramArrayOfString2);
/*  55 */     this.flag = 0;
/*  56 */     setUpDialog();
/*     */   }
/*     */   
/*     */ 
/*     */   public VectorDialog(Frame paramFrame, Bridge paramBridge, String paramString, String[] paramArrayOfString, float[] paramArrayOfFloat, int paramInt1, int paramInt2, int paramInt3)
/*     */   {
/*  62 */     super(paramFrame, "", false);
/*  63 */     setLocation(paramFrame.getLocationOnScreen().x + paramInt2, paramFrame.getLocationOnScreen().y + paramInt3);
/*     */     
/*  65 */     setFont(this.helvetica15);
/*     */     
/*  67 */     this.panel = paramBridge;
/*  68 */     this.title = paramString;
/*  69 */     this.label = paramArrayOfString;
/*  70 */     this.width = paramInt1;
/*  71 */     this.count = paramArrayOfString.length;
/*     */     
/*  73 */     this.element = new String[this.count];
/*  74 */     for (int i = 0; i < this.count; i++)
/*  75 */       this.element[i] = new String(String.valueOf(paramArrayOfFloat[i]));
/*  76 */     this.flag = 1;
/*  77 */     setUpDialog();
/*     */   }
/*     */   
/*     */ 
/*     */   public VectorDialog(Frame paramFrame, Bridge paramBridge, String paramString, String[] paramArrayOfString, int[] paramArrayOfInt, int paramInt1, int paramInt2, int paramInt3)
/*     */   {
/*  83 */     super(paramFrame, "", false);
/*  84 */     setLocation(paramFrame.getLocationOnScreen().x + paramInt2, paramFrame.getLocationOnScreen().y + paramInt3);
/*     */     
/*  86 */     setFont(this.helvetica15);
/*     */     
/*  88 */     this.panel = paramBridge;
/*  89 */     this.title = paramString;
/*  90 */     this.label = UTIL.getDuplicate(paramArrayOfString);
/*  91 */     this.width = paramInt1;
/*  92 */     this.count = paramArrayOfString.length;
/*     */     
/*  94 */     this.element = new String[this.count];
/*  95 */     for (int i = 0; i < this.count; i++)
/*  96 */       this.element[i] = new String(String.valueOf(paramArrayOfInt[i]));
/*  97 */     this.flag = 2;
/*  98 */     setUpDialog();
/*     */   }
/*     */   
/*     */   void setUpDialog() {
/* 102 */     setLayout(new GridLayout(this.count + 1, 1));
/* 103 */     Panel[] arrayOfPanel = new Panel[this.count + 1];
/* 104 */     for (int i = 0; i <= this.count; i++) {
/* 105 */       arrayOfPanel[i] = new Panel();
/* 106 */       add(arrayOfPanel[i]);
/*     */     }
/*     */     
/* 109 */     TextField localTextField = new TextField(this.width);
/* 110 */     localTextField.setEditable(false);
/* 111 */     localTextField.setText(this.title);
/* 112 */     arrayOfPanel[0].add(localTextField);
/* 113 */     this.doneButton = new Button("Done");
/* 114 */     this.doneButton.addActionListener(this);
/* 115 */     arrayOfPanel[0].add(this.doneButton);
/*     */     
/* 117 */     String[] arrayOfString = UTIL.evenLength(this.label);
/*     */     
/* 119 */     this.f = new TextField[this.count];
/* 120 */     for (int j = 0; j < this.count; j++) {
/* 121 */       localTextField = new TextField(this.width);
/* 122 */       localTextField.setEditable(false);
/* 123 */       localTextField.setText(arrayOfString[j]);
/* 124 */       arrayOfPanel[(j + 1)].add(localTextField);
/*     */       
/* 126 */       this.f[j] = new TextField(this.width);
/* 127 */       this.f[j].setText(this.element[j]);
/* 128 */       arrayOfPanel[(j + 1)].add(this.f[j]);
/*     */     }
/*     */     
/* 131 */     pack();
/*     */   }
/*     */   
/*     */   public void actionPerformed(ActionEvent paramActionEvent)
/*     */   {
/* 136 */     if (paramActionEvent.getSource() == this.doneButton) {
/* 137 */       getUserInput();
/* 138 */       this.panel.showNet();
/* 139 */       dispose();
/*     */     }
/*     */   }
/*     */   
/*     */   void getUserInput() {
/* 144 */     String[] arrayOfString = new String[this.count];
/* 145 */     for (int i = 0; i < this.count; i++) { arrayOfString[i] = new String(this.f[i].getText());
/*     */     }
/* 147 */     if (this.flag == 0) { this.panel.setVector(arrayOfString); } else { Object localObject;
/* 148 */       int j; StringTokenizer localStringTokenizer; if (this.flag == 1) {
/* 149 */         localObject = new float[this.count];
/* 150 */         for (j = 0; j < this.count; j++) {
/* 151 */           localStringTokenizer = new StringTokenizer(arrayOfString[j]);
/* 152 */           localObject[j] = Float.valueOf(localStringTokenizer.nextToken()).floatValue();
/*     */         }
/* 154 */         this.panel.setVector((float[])localObject);
/*     */       }
/*     */       else {
/* 157 */         localObject = new int[this.count];
/* 158 */         for (j = 0; j < this.count; j++) {
/* 159 */           localStringTokenizer = new StringTokenizer(arrayOfString[j]);
/* 160 */           localObject[j] = Integer.parseInt(localStringTokenizer.nextToken());
/*     */         }
/* 162 */         this.panel.setVector((int[])localObject);
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              /home/anali/INAOE/tesis/codigos/topub/exe/ww4.jar!/Network/VectorDialog.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */