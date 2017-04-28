/*     */ package Network;
/*     */ 
/*     */ import java.awt.Canvas;
/*     */ import java.awt.Color;
/*     */ import java.awt.Font;
/*     */ import java.awt.FontMetrics;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Point;
/*     */ import java.awt.Rectangle;
/*     */ import java.io.PrintWriter;
/*     */ 
/*     */ public class NetPaint
/*     */ {
/*     */   public static final int NODESIZE = 12;
/*     */   public static final int NODERADIX = 6;
/*  16 */   public static Font helvetica14 = new Font("Helvetica", 1, 14);
/*  17 */   public static Font helvetica15 = new Font("Helvetica", 1, 15);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  23 */   public static final Color histColor = Color.red;
/*  24 */   public static final Color nodeColor = Color.green;
/*  25 */   public static final Color obsNodeColor = Color.yellow;
/*  26 */   public static final Color nodeBorderColor = Color.black;
/*  27 */   public static final Color nodeLabelColor = Color.blue;
/*  28 */   public static final Color markedLabelColor = Color.red;
/*  29 */   public static final Color blackLinkColor = Color.black;
/*  30 */   public static final Color colorLinkColor1 = Color.green;
/*  31 */   public static final Color colorLinkColor2 = Color.red;
/*  32 */   public static final Color backgroundColor = Color.lightGray;
/*     */   
/*     */ 
/*     */ 
/*  36 */   public static final Color fw1NodeColor = Color.pink;
/*  37 */   public static final Color fw2NodeColor = Color.red;
/*  38 */   public static final Color runBdrNodeColor = Color.gray;
/*  39 */   public static final Color fBdrNodeColor = Color.lightGray;
/*  40 */   public static final Color sBdrNodeColor = Color.darkGray;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static Color getNodeColor(Graphics paramGraphics, BayesNet paramBayesNet, int paramInt)
/*     */   {
/*  48 */     int i = paramBayesNet.getCode(paramInt);
/*  49 */     switch (i) {
/*  50 */     case 0:  return Color.green;
/*  51 */     case 1:  return Color.blue;
/*  52 */     case 2:  return Color.orange;
/*  53 */     case 3:  return Color.red;
/*  54 */     case 4:  return Color.yellow;
/*     */     }
/*     */     
/*  57 */     if (paramBayesNet.isObserved(paramInt)) {
/*  58 */       return obsNodeColor;
/*     */     }
/*  60 */     if (paramBayesNet.isOnBdr(paramInt, 2, 3))
/*  61 */       return fBdrNodeColor;
/*  62 */     if (paramBayesNet.isOnBdr(paramInt, 2, 4)) {
/*  63 */       return sBdrNodeColor;
/*     */     }
/*  65 */     if (paramBayesNet.isOnBdr(paramInt, 1, 2)) {
/*  66 */       return runBdrNodeColor;
/*     */     }
/*  68 */     if (paramBayesNet.isOnBdr(paramInt, 0, 0))
/*  69 */       return fw1NodeColor;
/*  70 */     if (paramBayesNet.isOnBdr(paramInt, 0, 1)) {
/*  71 */       return fw2NodeColor;
/*     */     }
/*  73 */     return nodeColor;
/*     */   }
/*     */   
/*     */ 
/*     */   public static void paintNet(Graphics paramGraphics, BayesNet paramBayesNet, float paramFloat, boolean paramBoolean)
/*     */   {
/*  79 */     if (paramBayesNet == null) return;
/*  80 */     int i = paramBayesNet.getNodeCount();
/*     */     
/*  82 */     paramGraphics.setColor(blackLinkColor);
/*  83 */     for (int j = 0; j < i; j++) {
/*  84 */       for (int k = 0; k < i; k++) {
/*  85 */         if (paramBayesNet.isArc(j, k)) {
/*  86 */           paramGraphics.drawLine(paramBayesNet.getPos(j).x, paramBayesNet.getPos(j).y, paramBayesNet.getPos(k).x, paramBayesNet.getPos(k).y);
/*     */           
/*  88 */           drawArrow(paramGraphics, paramBayesNet.getPos(j), paramBayesNet.getPos(k), paramFloat);
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*  93 */     for (j = 0; j < i; j++) { if (paramBayesNet.getPos(j).x > 0) {
/*  94 */         paramGraphics.setColor(getNodeColor(paramGraphics, paramBayesNet, j));
/*  95 */         paramGraphics.fillOval(paramBayesNet.getPos(j).x - 6, paramBayesNet.getPos(j).y - 6, 12, 12);
/*     */       }
/*     */     }
/*     */     
/*  99 */     paramGraphics.setColor(nodeBorderColor);
/* 100 */     for (j = 0; j < i; j++) { if (paramBayesNet.getPos(j).x > 0) {
/* 101 */         paramGraphics.drawOval(paramBayesNet.getPos(j).x - 6, paramBayesNet.getPos(j).y - 6, 12, 12);
/*     */       }
/*     */     }
/* 104 */     paramGraphics.setColor(nodeLabelColor);
/* 105 */     paramGraphics.setFont(helvetica14);
/* 106 */     for (j = 0; j < i; j++) { if (paramBayesNet.getPos(j).x > 0) {
/* 107 */         if (paramBoolean) { paramGraphics.drawString(paramBayesNet.getLabel(j) + "," + j, paramBayesNet.getPos(j).x - 14, paramBayesNet.getPos(j).y - 14);
/*     */         } else {
/* 109 */           paramGraphics.drawString(paramBayesNet.getLabel(j), paramBayesNet.getPos(j).x - 14, paramBayesNet.getPos(j).y - 14);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void moveNet(Canvas paramCanvas, BayesNet paramBayesNet)
/*     */   {
/* 125 */     Rectangle localRectangle = paramCanvas.getBounds();
/* 126 */     int i = localRectangle.width;
/* 127 */     int j = localRectangle.height - 15;
/*     */     
/* 129 */     int k = (int)(i * 0.05F);
/* 130 */     int m = (int)(j * 0.1F);
/* 131 */     int n = (int)(i * 0.85F);
/* 132 */     int i1 = (int)(j * 0.8F);
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 140 */     int i2 = i;
/* 141 */     int i3 = j;
/* 142 */     int i4 = 0;
/* 143 */     int i5 = 0;
/* 144 */     int i8; for (int i6 = 0; i6 < paramBayesNet.getNodeCount(); i6++) {
/* 145 */       int i7 = paramBayesNet.getPos(i6).x;
/* 146 */       i8 = paramBayesNet.getPos(i6).y;
/* 147 */       if (i7 < i2) i2 = i7;
/* 148 */       if (i8 < i3) i3 = i8;
/* 149 */       if (i7 > i4) i4 = i7;
/* 150 */       if (i8 > i5) { i5 = i8;
/*     */       }
/*     */     }
/* 153 */     for (i6 = 0; i6 < paramBayesNet.getNodeCount(); i6++) {
/* 154 */       Point localPoint = paramBayesNet.getPos(i6);
/*     */       
/* 156 */       if (i4 == i2) i8 = n / 2 + k; else
/* 157 */         i8 = (localPoint.x - i2) * n / (i4 - i2) + k;
/* 158 */       int i9; if (i5 == i3) i9 = i1 / 2 + m + 15; else
/* 159 */         i9 = (localPoint.y - i3) * i1 / (i5 - i3) + m + 15;
/* 160 */       localPoint.move(i8, i9);
/* 161 */       paramBayesNet.setPos(i6, localPoint);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public static void showBelief(Canvas paramCanvas, Graphics paramGraphics, BayesNet paramBayesNet, JoinForest paramJoinForest)
/*     */   {
/* 168 */     for (int i = 0; i < paramBayesNet.getNodeCount(); i++) {
/* 169 */       Point localPoint = paramBayesNet.getPos(i);
/* 170 */       float[] arrayOfFloat = paramJoinForest.getVarMargin(i);
/* 171 */       String[] arrayOfString = paramBayesNet.getState(i);
/* 172 */       showHist(paramCanvas, paramGraphics, localPoint, arrayOfFloat, arrayOfString);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static void showBelief(Canvas paramCanvas, Graphics paramGraphics, BayesNet paramBayesNet, JoinForest paramJoinForest, PrintWriter paramPrintWriter)
/*     */   {
/* 180 */     for (int i = 0; i < paramBayesNet.getNodeCount(); i++) {
/* 181 */       Point localPoint = paramBayesNet.getPos(i);
/* 182 */       String str = paramBayesNet.getLabel(i);
/* 183 */       float[] arrayOfFloat = paramJoinForest.getVarMargin(i);
/* 184 */       char[] arrayOfChar = paramBayesNet.getStateInit(i);
/* 185 */       paramJoinForest.saveBelief(paramPrintWriter, str, i, arrayOfChar, arrayOfFloat);
/* 186 */       String[] arrayOfString = paramBayesNet.getState(i);
/* 187 */       showHist(paramCanvas, paramGraphics, localPoint, arrayOfFloat, arrayOfString);
/*     */     }
/*     */   }
/*     */   
/*     */   public static void showBelief(Canvas paramCanvas, Graphics paramGraphics, BayesNet paramBayesNet, LJoinForest paramLJoinForest)
/*     */   {
/* 193 */     for (int i = 0; i < paramBayesNet.getNodeCount(); i++) {
/* 194 */       Point localPoint = paramBayesNet.getPos(i);
/* 195 */       float[] arrayOfFloat = paramLJoinForest.getVarMargin(i);
/* 196 */       String[] arrayOfString = paramBayesNet.getState(i);
/* 197 */       showHist(paramCanvas, paramGraphics, localPoint, arrayOfFloat, arrayOfString);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public static void showBelief(Canvas paramCanvas, Graphics paramGraphics, BayesNet paramBayesNet, LJoinTreeM paramLJoinTreeM)
/*     */   {
/* 204 */     for (int i = 0; i < paramBayesNet.getNodeCount(); i++) {
/* 205 */       Point localPoint = paramBayesNet.getPos(i);
/* 206 */       float[] arrayOfFloat = paramLJoinTreeM.getVarMargin(i);
/* 207 */       String[] arrayOfString = paramBayesNet.getState(i);
/* 208 */       showHist(paramCanvas, paramGraphics, localPoint, arrayOfFloat, arrayOfString);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   static void showHist(Canvas paramCanvas, Graphics paramGraphics, Point paramPoint, float[] paramArrayOfFloat, String[] paramArrayOfString)
/*     */   {
/* 219 */     int i = paramArrayOfFloat.length;
/* 220 */     if (i > 5) showHistVert(paramCanvas, paramGraphics, paramPoint, paramArrayOfFloat, paramArrayOfString); else {
/* 221 */       showHistHori(paramCanvas, paramGraphics, paramPoint, paramArrayOfFloat, paramArrayOfString);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   static void showHistHori(Canvas paramCanvas, Graphics paramGraphics, Point paramPoint, float[] paramArrayOfFloat, String[] paramArrayOfString)
/*     */   {
/* 235 */     FontMetrics localFontMetrics = paramGraphics.getFontMetrics();
/* 236 */     paramGraphics.setColor(histColor);
/*     */     
/* 238 */     int i = localFontMetrics.getHeight();
/* 239 */     int j = i - 4;
/* 240 */     int k = 2;
/* 241 */     int m = 1;
/* 242 */     int n = i * 4;
/* 243 */     int i1 = 2;
/* 244 */     int i2 = localFontMetrics.charWidth('A');
/*     */     
/* 246 */     int i3 = 0;
/* 247 */     for (int i4 = 0; i4 < paramArrayOfString.length; i4++) {
/* 248 */       if (paramArrayOfString[i4].length() > i3) i3 = paramArrayOfString[i4].length();
/*     */     }
/* 250 */     if (i3 > 10) { i3 = 10;
/*     */     }
/* 252 */     i4 = paramArrayOfFloat.length;
/* 253 */     int i5 = n + m + i2 * i3 + 2;
/* 254 */     int i6 = i4 * i + i1 * 2;
/* 255 */     int i7 = paramPoint.x;
/* 256 */     int i8 = paramPoint.y - i6 / 2;
/*     */     
/* 258 */     Rectangle localRectangle = paramCanvas.getBounds();
/* 259 */     if (i7 < 1) i7 = 1;
/* 260 */     if (i7 + i5 > localRectangle.width) i7 = localRectangle.width - i5 - 1;
/* 261 */     if (i8 < 1) i8 = 1;
/* 262 */     if (i8 + i6 > localRectangle.height) i8 = localRectangle.height - i6 - 1;
/* 263 */     paramGraphics.drawRect(i7, i8, i5, i6);
/*     */     
/* 265 */     int i9 = i5 - n;
/* 266 */     int i10 = 5;
/* 267 */     int i11 = n / i10;
/* 268 */     for (int i12 = 0; i12 < i10; i12++) {
/* 269 */       paramGraphics.drawLine(i7 + i9 + i11 * i12, i8, i7 + i9 + i11 * i12, i8 + i1);
/* 270 */       paramGraphics.drawLine(i7 + i9 + i11 * i12, i8 + i6, i7 + i9 + i11 * i12, i8 + i6 - i1);
/*     */     }
/*     */     int i14;
/* 273 */     for (i12 = 0; i12 < i4; i12++) {
/* 274 */       int i13 = i7 + i9;
/* 275 */       i14 = i8 + i1 + 2 + i * i12;
/* 276 */       if (paramArrayOfFloat[i12] > 0.99F) paramArrayOfFloat[i12] = 1.0F;
/* 277 */       int i15 = (int)(paramArrayOfFloat[i12] * n + 0.5F);
/*     */       
/* 279 */       paramGraphics.fillRect(i13, i14, i15, j);
/*     */     }
/*     */     
/* 282 */     for (i12 = 0; i12 < i4; i12++) {
/* 283 */       char[] arrayOfChar = paramArrayOfString[i12].toCharArray();
/* 284 */       i14 = i8 + i * (i12 + 1) - 4;
/* 285 */       if (arrayOfChar.length < i3) paramGraphics.drawChars(arrayOfChar, 0, arrayOfChar.length, i7 + 2, i14); else {
/* 286 */         paramGraphics.drawChars(arrayOfChar, 0, i3, i7 + 2, i14);
/*     */       }
/*     */     }
/*     */   }
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   static void showHistVert(Canvas paramCanvas, Graphics paramGraphics, Point paramPoint, float[] paramArrayOfFloat, String[] paramArrayOfString)
/*     */   {
/* 319 */     FontMetrics localFontMetrics = paramGraphics.getFontMetrics();
/* 320 */     paramGraphics.setColor(histColor);
/*     */     
/* 322 */     int i = (int)(localFontMetrics.charWidth('A') * 1.5F);
/* 323 */     int j = 1;
/* 324 */     int k = 1;
/* 325 */     int m = localFontMetrics.getHeight() - 7;
/* 326 */     int n = (int)(3.0F * localFontMetrics.getHeight());
/* 327 */     int i1 = 2;
/*     */     
/* 329 */     int i2 = 0;
/* 330 */     for (int i3 = 0; i3 < paramArrayOfString.length; i3++) {
/* 331 */       if (paramArrayOfString[i3].length() > i2) i2 = paramArrayOfString[i3].length();
/*     */     }
/* 333 */     if (i2 > 4) { i2 = 4;
/*     */     }
/* 335 */     i3 = paramArrayOfFloat.length;
/* 336 */     int i4 = (i3 + 3) * (i + j);
/* 337 */     int i5 = n + k + m + 3;
/* 338 */     int i6 = paramPoint.x;
/* 339 */     int i7 = paramPoint.y - i5 / 2;
/*     */     
/* 341 */     Rectangle localRectangle = paramCanvas.getBounds();
/* 342 */     if (i6 < 1) i6 = 1;
/* 343 */     if (i6 + i4 > localRectangle.width) i6 = localRectangle.width - i4 - 1;
/* 344 */     if (i7 < 1) i7 = 1;
/* 345 */     if (i7 + i5 > localRectangle.height) i7 = localRectangle.height - i5 - 1;
/* 346 */     paramGraphics.drawRect(i6, i7, i4, i5);
/* 347 */     int i8 = i6 + i * 3;
/* 348 */     paramGraphics.drawLine(i8, i7, i8, i7 + i5);
/*     */     
/* 350 */     int i9 = 5;
/* 351 */     float f1 = MATH.max(paramArrayOfFloat);
/* 352 */     float f2 = f1 * i3 / i9;
/* 353 */     if (f2 < 0.5F) { f2 = 0.5F;
/* 354 */     } else if (f2 < 1.0F) f2 = 1.0F;
/* 355 */     float f3 = f2 * i9;
/*     */     
/*     */ 
/*     */ 
/* 359 */     int i10 = n;
/* 360 */     int i12; for (int i11 = 0; i11 < i9; i11++) {
/* 361 */       i12 = i7 + i10 - n * i11 / i9 - 1;
/* 362 */       paramGraphics.drawLine(i8, i12, i8 - i1, i12);
/* 363 */       paramGraphics.drawLine(i6 + i4, i12, i6 + i4 - i1, i12);
/*     */       
/* 365 */       if (i11 % 2 != 1) {
/* 366 */         String str = new String("" + f2 * i11);
/* 367 */         char[] arrayOfChar1 = str.toCharArray();
/* 368 */         paramGraphics.drawChars(arrayOfChar1, 0, arrayOfChar1.length, i6 + 3, i12 + m / 2);
/*     */       }
/*     */     }
/* 371 */     for (i11 = 0; i11 < i3; i11++) {
/* 372 */       i12 = (i + j) * (i11 + 3) - 1;
/* 373 */       if (paramArrayOfFloat[i11] > 0.99F) paramArrayOfFloat[i11] = 1.0F;
/* 374 */       int i13 = (int)(n * paramArrayOfFloat[i11] * i3 / f3 + 0.5F);
/* 375 */       int i14 = n - i13;
/* 376 */       paramGraphics.fillRect(i6 + i12, i7 + i14, i, i13);
/*     */       
/* 378 */       if (i11 % 2 != 0) {
/* 379 */         char[] arrayOfChar2 = paramArrayOfString[i11].toCharArray();
/* 380 */         int i15 = i6 + i12;
/* 381 */         int i16 = i7 + i10 + m + 2;
/* 382 */         if (arrayOfChar2.length < i2) paramGraphics.drawChars(arrayOfChar2, 0, arrayOfChar2.length, i15, i16); else {
/* 383 */           paramGraphics.drawChars(arrayOfChar2, 0, i2, i15, i16);
/*     */         }
/* 385 */         paramGraphics.drawLine(i15, i16 - m - 2, i15, i16 - m - 2 + i1);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public static void paintNet(Graphics paramGraphics, DesignNet paramDesignNet, float paramFloat)
/*     */   {
/* 393 */     if (paramDesignNet == null) return;
/* 394 */     int i = paramDesignNet.getNodeCount();
/*     */     
/* 396 */     paramGraphics.setColor(blackLinkColor);
/* 397 */     for (int j = 0; j < i; j++)
/* 398 */       for (int k = 0; k < i; k++)
/* 399 */         if (paramDesignNet.isArc(j, k)) {
/* 400 */           paramGraphics.drawLine(paramDesignNet.getPos(j).x, paramDesignNet.getPos(j).y, paramDesignNet.getPos(k).x, paramDesignNet.getPos(k).y);
/*     */           
/* 402 */           drawArrow(paramGraphics, paramDesignNet.getPos(j), paramDesignNet.getPos(k), paramFloat); }
/*     */     int[] arrayOfInt1;
/*     */     int[] arrayOfInt2;
/*     */     int m;
/*     */     int n;
/* 407 */     for (j = 0; j < i; j++) { if (paramDesignNet.getPos(j).x > 0) {
/* 408 */         paramGraphics.setColor(getNodeColor(paramGraphics, paramDesignNet, j));
/*     */         
/* 410 */         if (paramDesignNet.isUtilNode(j)) {
/* 411 */           arrayOfInt1 = new int[4];arrayOfInt2 = new int[4];
/* 412 */           m = paramDesignNet.getPos(j).x;n = paramDesignNet.getPos(j).y;
/* 413 */           arrayOfInt1[0] = (m - 6 - 2);arrayOfInt2[0] = (n - 6 + 6);
/* 414 */           arrayOfInt1[1] = (m - 6 + 6);arrayOfInt2[1] = (n - 6 - 2);
/* 415 */           arrayOfInt1[2] = (m - 6 + 14);arrayOfInt2[2] = (n - 6 + 6);
/* 416 */           arrayOfInt1[3] = (m - 6 + 6);arrayOfInt2[3] = (n - 6 + 14);
/* 417 */           paramGraphics.fillPolygon(arrayOfInt1, arrayOfInt2, 4);
/*     */         }
/* 419 */         else if (paramDesignNet.isDesignNode(j)) {
/* 420 */           paramGraphics.fillRect(paramDesignNet.getPos(j).x - 6, paramDesignNet.getPos(j).y - 6, 12, 12);
/*     */ 
/*     */         }
/* 423 */         else if (paramDesignNet.isPerfNode(j)) {
/* 424 */           paramGraphics.fillOval(paramDesignNet.getPos(j).x - 6 - 6, paramDesignNet.getPos(j).y - 6, 24, 12);
/*     */         }
/*     */         else {
/* 427 */           paramGraphics.fillOval(paramDesignNet.getPos(j).x - 6, paramDesignNet.getPos(j).y - 6, 12, 12);
/*     */         }
/*     */       }
/*     */     }
/* 431 */     paramGraphics.setColor(nodeBorderColor);
/* 432 */     for (j = 0; j < i; j++) { if (paramDesignNet.getPos(j).x > 0) {
/* 433 */         if (paramDesignNet.isUtilNode(j)) {
/* 434 */           arrayOfInt1 = new int[4];arrayOfInt2 = new int[4];
/* 435 */           m = paramDesignNet.getPos(j).x;n = paramDesignNet.getPos(j).y;
/* 436 */           arrayOfInt1[0] = (m - 6 - 2);arrayOfInt2[0] = (n - 6 + 6);
/* 437 */           arrayOfInt1[1] = (m - 6 + 6);arrayOfInt2[1] = (n - 6 - 2);
/* 438 */           arrayOfInt1[2] = (m - 6 + 14);arrayOfInt2[2] = (n - 6 + 6);
/* 439 */           arrayOfInt1[3] = (m - 6 + 6);arrayOfInt2[3] = (n - 6 + 14);
/* 440 */           paramGraphics.drawPolygon(arrayOfInt1, arrayOfInt2, 4);
/*     */         }
/* 442 */         else if (paramDesignNet.isDesignNode(j)) {
/* 443 */           paramGraphics.drawRect(paramDesignNet.getPos(j).x - 6, paramDesignNet.getPos(j).y - 6, 12, 12);
/*     */ 
/*     */         }
/* 446 */         else if (paramDesignNet.isPerfNode(j)) {
/* 447 */           paramGraphics.drawOval(paramDesignNet.getPos(j).x - 6 - 6, paramDesignNet.getPos(j).y - 6, 24, 12);
/*     */         }
/*     */         else {
/* 450 */           paramGraphics.drawOval(paramDesignNet.getPos(j).x - 6, paramDesignNet.getPos(j).y - 6, 12, 12);
/*     */         }
/*     */       }
/*     */     }
/* 454 */     paramGraphics.setColor(nodeLabelColor);
/* 455 */     paramGraphics.setFont(helvetica14);
/* 456 */     for (j = 0; j < i; j++) { if (paramDesignNet.getPos(j).x > 0) {
/* 457 */         paramGraphics.drawString(paramDesignNet.getLabel(j) + "," + j, paramDesignNet.getPos(j).x - 14, paramDesignNet.getPos(j).y - 14);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public static void paintNet(Graphics paramGraphics, DecisionNet paramDecisionNet, float paramFloat)
/*     */   {
/* 465 */     if (paramDecisionNet == null) return;
/* 466 */     int i = paramDecisionNet.getNodeCount();
/*     */     
/* 468 */     paramGraphics.setColor(blackLinkColor);
/* 469 */     for (int j = 0; j < i; j++)
/* 470 */       for (int k = 0; k < i; k++)
/* 471 */         if (paramDecisionNet.isArc(j, k)) {
/* 472 */           paramGraphics.drawLine(paramDecisionNet.getPos(j).x, paramDecisionNet.getPos(j).y, paramDecisionNet.getPos(k).x, paramDecisionNet.getPos(k).y);
/*     */           
/* 474 */           drawArrow(paramGraphics, paramDecisionNet.getPos(j), paramDecisionNet.getPos(k), paramFloat); }
/*     */     int[] arrayOfInt1;
/*     */     int[] arrayOfInt2;
/*     */     int m;
/*     */     int n;
/* 479 */     for (j = 0; j < i; j++) { if (paramDecisionNet.getPos(j).x > 0) {
/* 480 */         paramGraphics.setColor(getNodeColor(paramGraphics, paramDecisionNet, j));
/*     */         
/* 482 */         if (paramDecisionNet.isValueNode(j)) {
/* 483 */           arrayOfInt1 = new int[4];arrayOfInt2 = new int[4];
/* 484 */           m = paramDecisionNet.getPos(j).x;n = paramDecisionNet.getPos(j).y;
/* 485 */           arrayOfInt1[0] = (m - 6 - 2);arrayOfInt2[0] = (n - 6 + 6);
/* 486 */           arrayOfInt1[1] = (m - 6 + 6);arrayOfInt2[1] = (n - 6 - 2);
/* 487 */           arrayOfInt1[2] = (m - 6 + 14);arrayOfInt2[2] = (n - 6 + 6);
/* 488 */           arrayOfInt1[3] = (m - 6 + 6);arrayOfInt2[3] = (n - 6 + 14);
/* 489 */           paramGraphics.fillPolygon(arrayOfInt1, arrayOfInt2, 4);
/*     */         }
/* 491 */         else if (paramDecisionNet.isDecisionNode(j)) {
/* 492 */           paramGraphics.fillRect(paramDecisionNet.getPos(j).x - 6, paramDecisionNet.getPos(j).y - 6, 12, 12);
/*     */         }
/*     */         else {
/* 495 */           paramGraphics.fillOval(paramDecisionNet.getPos(j).x - 6, paramDecisionNet.getPos(j).y - 6, 12, 12);
/*     */         }
/*     */       }
/*     */     }
/* 499 */     paramGraphics.setColor(nodeBorderColor);
/* 500 */     for (j = 0; j < i; j++) { if (paramDecisionNet.getPos(j).x > 0) {
/* 501 */         if (paramDecisionNet.isValueNode(j)) {
/* 502 */           arrayOfInt1 = new int[4];arrayOfInt2 = new int[4];
/* 503 */           m = paramDecisionNet.getPos(j).x;n = paramDecisionNet.getPos(j).y;
/* 504 */           arrayOfInt1[0] = (m - 6 - 2);arrayOfInt2[0] = (n - 6 + 6);
/* 505 */           arrayOfInt1[1] = (m - 6 + 6);arrayOfInt2[1] = (n - 6 - 2);
/* 506 */           arrayOfInt1[2] = (m - 6 + 14);arrayOfInt2[2] = (n - 6 + 6);
/* 507 */           arrayOfInt1[3] = (m - 6 + 6);arrayOfInt2[3] = (n - 6 + 14);
/* 508 */           paramGraphics.drawPolygon(arrayOfInt1, arrayOfInt2, 4);
/*     */         }
/* 510 */         else if (paramDecisionNet.isDecisionNode(j)) {
/* 511 */           paramGraphics.drawRect(paramDecisionNet.getPos(j).x - 6, paramDecisionNet.getPos(j).y - 6, 12, 12);
/*     */         }
/*     */         else {
/* 514 */           paramGraphics.drawOval(paramDecisionNet.getPos(j).x - 6, paramDecisionNet.getPos(j).y - 6, 12, 12);
/*     */         }
/*     */       }
/*     */     }
/* 518 */     paramGraphics.setColor(nodeLabelColor);
/* 519 */     paramGraphics.setFont(helvetica14);
/* 520 */     for (j = 0; j < i; j++) { if (paramDecisionNet.getPos(j).x > 0) {
/* 521 */         paramGraphics.drawString(paramDecisionNet.getLabel(j) + "," + j, paramDecisionNet.getPos(j).x - 14, paramDecisionNet.getPos(j).y - 14);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private static Color getLinkColor(Graphics paramGraphics, UndirectGraph paramUndirectGraph, Point paramPoint)
/*     */   {
/* 531 */     if (paramUndirectGraph.isLinka(paramPoint)) return colorLinkColor1;
/* 532 */     if (paramUndirectGraph.isLinkb(paramPoint)) return colorLinkColor2;
/* 533 */     return blackLinkColor;
/*     */   }
/*     */   
/*     */   public static void paintNet(Graphics paramGraphics, UndirectGraph paramUndirectGraph)
/*     */   {
/* 538 */     if (paramUndirectGraph == null) return;
/* 539 */     int i = paramUndirectGraph.getNodeCount();
/*     */     
/* 541 */     for (int j = 0; j < i; j++) {
/* 542 */       for (int k = 0; k < i; k++) {
/* 543 */         if (paramUndirectGraph.isLink(j, k)) {
/* 544 */           paramGraphics.setColor(getLinkColor(paramGraphics, paramUndirectGraph, new Point(j, k)));
/* 545 */           paramGraphics.drawLine(paramUndirectGraph.getPos(j).x, paramUndirectGraph.getPos(j).y, paramUndirectGraph.getPos(k).x, paramUndirectGraph.getPos(k).y);
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 551 */     for (j = 0; j < i; j++) { if (paramUndirectGraph.getPos(j).x > 0) {
/* 552 */         paramGraphics.setColor(nodeColor);
/* 553 */         paramGraphics.fillOval(paramUndirectGraph.getPos(j).x - 6, paramUndirectGraph.getPos(j).y - 6, 12, 12);
/*     */       }
/*     */     }
/*     */     
/* 557 */     paramGraphics.setColor(nodeBorderColor);
/* 558 */     for (j = 0; j < i; j++) { if (paramUndirectGraph.getPos(j).x > 0) {
/* 559 */         paramGraphics.drawOval(paramUndirectGraph.getPos(j).x - 6, paramUndirectGraph.getPos(j).y - 6, 12, 12);
/*     */       }
/*     */     }
/* 562 */     paramGraphics.setColor(nodeLabelColor);
/* 563 */     paramGraphics.setFont(helvetica14);
/* 564 */     for (j = 0; j < i; j++) { if (paramUndirectGraph.getPos(j).x > 0) {
/* 565 */         paramGraphics.drawString(paramUndirectGraph.getLabel(j) + "," + j, paramUndirectGraph.getPos(j).x - 14, paramUndirectGraph.getPos(j).y - 14);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void moveNet(Canvas paramCanvas, UndirectGraph paramUndirectGraph)
/*     */   {
/* 580 */     Rectangle localRectangle = paramCanvas.getBounds();
/* 581 */     int i = localRectangle.width;
/* 582 */     int j = localRectangle.height - 15;
/* 583 */     int k = (int)(i * 0.05F);
/* 584 */     int m = (int)(j * 0.1F);
/* 585 */     int n = (int)(i * 0.85F);
/* 586 */     int i1 = (int)(j * 0.8F);
/*     */     
/* 588 */     int i2 = i;
/* 589 */     int i3 = j;
/* 590 */     int i4 = 0;
/* 591 */     int i5 = 0;
/* 592 */     int i8; for (int i6 = 0; i6 < paramUndirectGraph.getNodeCount(); i6++) {
/* 593 */       int i7 = paramUndirectGraph.getPos(i6).x;
/* 594 */       i8 = paramUndirectGraph.getPos(i6).y;
/* 595 */       if (i7 < i2) i2 = i7;
/* 596 */       if (i8 < i3) i3 = i8;
/* 597 */       if (i7 > i4) i4 = i7;
/* 598 */       if (i8 > i5) { i5 = i8;
/*     */       }
/*     */     }
/* 601 */     for (i6 = 0; i6 < paramUndirectGraph.getNodeCount(); i6++) {
/* 602 */       Point localPoint = paramUndirectGraph.getPos(i6);
/*     */       
/* 604 */       if (i4 == i2) i8 = n / 2 + k; else
/* 605 */         i8 = (localPoint.x - i2) * n / (i4 - i2) + k;
/* 606 */       int i9; if (i5 == i3) i9 = i1 / 2 + m + 15; else
/* 607 */         i9 = (localPoint.y - i3) * i1 / (i5 - i3) + m + 15;
/* 608 */       localPoint.move(i8, i9);
/* 609 */       paramUndirectGraph.setPos(i6, localPoint);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static void paintNet(Graphics paramGraphics, UndirectGraph paramUndirectGraph, int[][] paramArrayOfInt)
/*     */   {
/* 617 */     paintNet(paramGraphics, paramUndirectGraph);
/*     */     
/* 619 */     paramGraphics.setColor(nodeLabelColor);
/* 620 */     int i = paramUndirectGraph.getNodeCount();
/* 621 */     for (int j = 0; j < i; j++) {
/* 622 */       for (int k = 0; k < paramArrayOfInt[j].length; k++) {
/* 623 */         int m = paramUndirectGraph.getNeighbor(j, k);
/* 624 */         if (m > j) {
/* 625 */           paramGraphics.drawString("" + paramArrayOfInt[j][k], (paramUndirectGraph.getPos(j).x + paramUndirectGraph.getPos(m).x) / 2, (paramUndirectGraph.getPos(j).y + paramUndirectGraph.getPos(m).y) / 2);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void paintNet(Graphics paramGraphics, MarkovNL paramMarkovNL)
/*     */   {
/* 637 */     if (paramMarkovNL == null) return;
/* 638 */     Point[] arrayOfPoint = paramMarkovNL.getColorLk();
/* 639 */     paintNet(paramGraphics, paramMarkovNL);
/* 640 */     int i = paramMarkovNL.getNodeCount();
/* 641 */     if (arrayOfPoint != null) {
/* 642 */       for (j = 0; j < arrayOfPoint.length; j++) {
/* 643 */         Point localPoint1 = paramMarkovNL.getPos(arrayOfPoint[j].x);
/* 644 */         Point localPoint2 = paramMarkovNL.getPos(arrayOfPoint[j].y);
/* 645 */         paramGraphics.setColor(backgroundColor);
/* 646 */         paramGraphics.drawLine(localPoint1.x, localPoint1.y, localPoint2.x, localPoint2.y);
/* 647 */         drawDashLink(paramGraphics, localPoint1, localPoint2, 10);
/*     */       }
/* 649 */       for (j = 0; j < arrayOfPoint.length; j++) {
/* 650 */         for (int k = 0; k < i; k++) {
/* 651 */           if (paramMarkovNL.getPos(k).x > 0) {
/* 652 */             paramGraphics.setColor(nodeColor);
/* 653 */             paramGraphics.fillOval(paramMarkovNL.getPos(k).x - 6, paramMarkovNL.getPos(k).y - 6, 12, 12);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 660 */     paramGraphics.setColor(nodeBorderColor);
/* 661 */     for (int j = 0; j < i; j++) { if (paramMarkovNL.getPos(j).x > 0) {
/* 662 */         paramGraphics.drawOval(paramMarkovNL.getPos(j).x - 6, paramMarkovNL.getPos(j).y - 6, 12, 12);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public static void showBelief(Canvas paramCanvas, Graphics paramGraphics, MarkovNet paramMarkovNet, JoinForest paramJoinForest)
/*     */   {
/* 670 */     for (int i = 0; i < paramMarkovNet.getNodeCount(); i++) {
/* 671 */       Point localPoint = paramMarkovNet.getPos(i);
/* 672 */       float[] arrayOfFloat = paramJoinForest.getVarMargin(i);
/* 673 */       String[] arrayOfString = paramMarkovNet.getState(i);
/* 674 */       showHist(paramCanvas, paramGraphics, localPoint, arrayOfFloat, arrayOfString);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static void paintNet(Graphics paramGraphics, MixNet paramMixNet)
/*     */   {
/* 682 */     if (paramMixNet == null) return;
/* 683 */     Point[] arrayOfPoint = paramMixNet.getColorLk();
/* 684 */     paintNet(paramGraphics, paramMixNet);
/* 685 */     int i = paramMixNet.getNodeCount();
/* 686 */     int k; if (arrayOfPoint != null) {
/* 687 */       for (j = 0; j < arrayOfPoint.length; j++) {
/* 688 */         Point localPoint1 = paramMixNet.getPos(arrayOfPoint[j].x);
/* 689 */         Point localPoint2 = paramMixNet.getPos(arrayOfPoint[j].y);
/* 690 */         paramGraphics.setColor(backgroundColor);
/* 691 */         paramGraphics.drawLine(localPoint1.x, localPoint1.y, localPoint2.x, localPoint2.y);
/* 692 */         drawDashLink(paramGraphics, localPoint1, localPoint2, 10);
/*     */       }
/* 694 */       for (j = 0; j < arrayOfPoint.length; j++) {
/* 695 */         for (k = 0; k < i; k++) {
/* 696 */           if (paramMixNet.getPos(k).x > 0) {
/* 697 */             paramGraphics.setColor(nodeColor);
/* 698 */             paramGraphics.fillOval(paramMixNet.getPos(k).x - 6, paramMixNet.getPos(k).y - 6, 12, 12);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 704 */     paramGraphics.setColor(blackLinkColor);
/* 705 */     for (int j = 0; j < i; j++) {
/* 706 */       for (k = 0; k < i; k++) {
/* 707 */         if (paramMixNet.isArc(j, k)) {
/* 708 */           drawArrow(paramGraphics, paramMixNet.getPos(j), paramMixNet.getPos(k), 20.0F);
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 713 */     paramGraphics.setColor(nodeBorderColor);
/* 714 */     for (j = 0; j < i; j++) { if (paramMixNet.getPos(j).x > 0) {
/* 715 */         paramGraphics.drawOval(paramMixNet.getPos(j).x - 6, paramMixNet.getPos(j).y - 6, 12, 12);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public static void paintNet(Graphics paramGraphics, HyperGraph paramHyperGraph)
/*     */   {
/* 723 */     if (paramHyperGraph == null) return;
/* 724 */     int i = paramHyperGraph.getNodeCount();
/*     */     
/* 726 */     paramGraphics.setColor(blackLinkColor);
/* 727 */     for (int j = 0; j < i; j++) {
/* 728 */       for (k = 0; k < i; k++) { if (paramHyperGraph.isLink(j, k)) {
/* 729 */           paramGraphics.drawLine(paramHyperGraph.getPos(j).x, paramHyperGraph.getPos(j).y, paramHyperGraph.getPos(k).x, paramHyperGraph.getPos(k).y);
/*     */         }
/*     */       }
/*     */     }
/* 733 */     FontMetrics localFontMetrics = paramGraphics.getFontMetrics();
/* 734 */     for (int k = 0; k < i; k++) { if (paramHyperGraph.getPos(k).x > 0) {
/* 735 */         String str = paramHyperGraph.getCqMemberStr(k);
/* 736 */         int m = localFontMetrics.stringWidth(str);
/* 737 */         int n = localFontMetrics.getHeight();
/*     */         
/* 739 */         paramGraphics.setColor(nodeColor);
/* 740 */         paramGraphics.fillOval(paramHyperGraph.getPos(k).x - m * 3 / 4, paramHyperGraph.getPos(k).y - n * 2 / 3, m * 3 / 2, n * 4 / 3);
/*     */         
/*     */ 
/* 743 */         paramGraphics.setColor(nodeBorderColor);
/* 744 */         paramGraphics.drawOval(paramHyperGraph.getPos(k).x - m * 3 / 4, paramHyperGraph.getPos(k).y - n * 2 / 3, m * 3 / 2, n * 4 / 3);
/*     */         
/*     */ 
/* 747 */         paramGraphics.setColor(nodeLabelColor);
/* 748 */         paramGraphics.setFont(helvetica14);
/* 749 */         paramGraphics.drawString(str, paramHyperGraph.getPos(k).x - m / 2, paramHyperGraph.getPos(k).y + n / 3);
/*     */         
/* 751 */         if (paramHyperGraph.isMarked(k)) paramGraphics.setColor(markedLabelColor);
/* 752 */         paramGraphics.drawString(paramHyperGraph.getLabel(k), paramHyperGraph.getPos(k).x - 10, paramHyperGraph.getPos(k).y - n * 3 / 4);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static void paintNet(Graphics paramGraphics, HyperGraph paramHyperGraph, UndirectGraph paramUndirectGraph)
/*     */   {
/* 761 */     if (paramHyperGraph == null) return;
/* 762 */     int i = paramHyperGraph.getNodeCount();
/*     */     
/* 764 */     paramGraphics.setColor(blackLinkColor);
/* 765 */     for (int j = 0; j < i; j++) {
/* 766 */       for (k = 0; k < i; k++) { if (paramHyperGraph.isLink(j, k)) {
/* 767 */           paramGraphics.drawLine(paramHyperGraph.getPos(j).x, paramHyperGraph.getPos(j).y, paramHyperGraph.getPos(k).x, paramHyperGraph.getPos(k).y);
/*     */         }
/*     */       }
/*     */     }
/* 771 */     FontMetrics localFontMetrics = paramGraphics.getFontMetrics();
/* 772 */     for (int k = 0; k < i; k++) { if (paramHyperGraph.getPos(k).x > 0) {
/* 773 */         String[] arrayOfString = paramUndirectGraph.getLabel(paramHyperGraph.getCqMember(k));
/* 774 */         String str = UTIL.removeBrace(UTIL.listToStr("", arrayOfString));
/* 775 */         int m = localFontMetrics.stringWidth(str);
/* 776 */         int n = localFontMetrics.getHeight();
/*     */         
/* 778 */         paramGraphics.setColor(nodeColor);
/* 779 */         paramGraphics.fillOval(paramHyperGraph.getPos(k).x - m * 3 / 4, paramHyperGraph.getPos(k).y - n * 2 / 3, m * 8 / 5, n * 4 / 3);
/*     */         
/*     */ 
/* 782 */         paramGraphics.setColor(nodeBorderColor);
/* 783 */         paramGraphics.drawOval(paramHyperGraph.getPos(k).x - m * 3 / 4, paramHyperGraph.getPos(k).y - n * 2 / 3, m * 8 / 5, n * 4 / 3);
/*     */         
/*     */ 
/* 786 */         paramGraphics.setColor(nodeLabelColor);
/* 787 */         paramGraphics.setFont(helvetica14);
/* 788 */         paramGraphics.drawString(str, paramHyperGraph.getPos(k).x - m / 2, paramHyperGraph.getPos(k).y + n / 3);
/*     */         
/* 790 */         paramGraphics.drawString(paramHyperGraph.getLabel(k), paramHyperGraph.getPos(k).x - 10, paramHyperGraph.getPos(k).y - n * 3 / 4);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void moveNet(Canvas paramCanvas, HyperGraph paramHyperGraph)
/*     */   {
/* 804 */     Rectangle localRectangle = paramCanvas.getBounds();
/* 805 */     float f1 = localRectangle.width;
/* 806 */     float f2 = localRectangle.height;
/*     */     
/* 808 */     float f3 = f1 * 0.08F;
/* 809 */     float f4 = f2 * 0.1F;
/* 810 */     float f5 = f1 * 0.84F;
/* 811 */     float f6 = f2 * 0.8F;
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
/* 823 */     float f7 = f1;
/* 824 */     float f8 = f2;
/* 825 */     float f9 = 0.0F;
/* 826 */     float f10 = 0.0F;
/*     */     
/* 828 */     for (int i = 0; i < paramHyperGraph.getNodeCount(); i++) {
/* 829 */       float f11 = paramHyperGraph.getPos(i).x;
/* 830 */       float f12 = paramHyperGraph.getPos(i).y;
/* 831 */       if (f11 < f7) f7 = f11;
/* 832 */       if (f12 < f8) f8 = f12;
/* 833 */       if (f11 > f9) f9 = f11;
/* 834 */       if (f12 > f10) f10 = f12;
/*     */     }
/* 836 */     float f13 = f9 == f7 ? 1.0F : f9 - f7;
/* 837 */     float f14 = f10 == f8 ? 1.0F : f10 - f8;
/*     */     
/*     */ 
/*     */ 
/* 841 */     for (int j = 0; j < paramHyperGraph.getNodeCount(); j++) {
/* 842 */       Point localPoint = paramHyperGraph.getPos(j);
/* 843 */       float f15 = (localPoint.x - f7) * f5 / f13 + f3;
/* 844 */       float f16 = (localPoint.y - f8) * f6 / f14 + f4;
/* 845 */       paramHyperGraph.setPos(j, new Point((int)f15, (int)f16));
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static void drawLink(Graphics paramGraphics, Point paramPoint1, Point paramPoint2)
/*     */   {
/* 853 */     paramGraphics.setColor(blackLinkColor);
/* 854 */     paramGraphics.drawLine(paramPoint1.x, paramPoint1.y, paramPoint2.x, paramPoint2.y);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void drawDashLink(Graphics paramGraphics, Point paramPoint1, Point paramPoint2, int paramInt)
/*     */   {
/* 862 */     paramGraphics.setColor(colorLinkColor2);
/*     */     
/* 864 */     double d1 = paramPoint2.x - paramPoint1.x;double d2 = paramPoint2.y - paramPoint1.y;
/* 865 */     double d3 = Math.sqrt(d1 * d1 + d2 * d2) * 2.0D / paramInt;
/* 866 */     if (d3 < 1.0D) return;
/* 867 */     double d4 = d1 / d3;double d5 = d2 / d3;
/*     */     
/*     */ 
/* 870 */     double d6 = paramPoint1.x;double d7 = paramPoint1.y;
/* 871 */     for (int i = 0; i < d3; i += 2) {
/* 872 */       double d8 = d6 + d4;double d9 = d7 + d5;
/* 873 */       paramGraphics.drawLine((int)d6, (int)d7, (int)d8, (int)d9);
/* 874 */       d6 = d8 + d4;d7 = d9 + d5;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void drawArc(Graphics paramGraphics, Point paramPoint1, Point paramPoint2, float paramFloat)
/*     */   {
/* 883 */     paramGraphics.setColor(blackLinkColor);
/* 884 */     paramGraphics.drawLine(paramPoint1.x, paramPoint1.y, paramPoint2.x, paramPoint2.y);
/* 885 */     drawArrow(paramGraphics, paramPoint1, paramPoint2, paramFloat);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void drawArrow(Graphics paramGraphics, Point paramPoint1, Point paramPoint2, float paramFloat)
/*     */   {
/* 893 */     int[][] arrayOfInt = getArrow(paramPoint1, paramPoint2, paramFloat / 2.0F);
/* 894 */     paramGraphics.fillPolygon(arrayOfInt[0], arrayOfInt[1], 3);
/*     */   }
/*     */   
/*     */   static int[][] getArrow(Point paramPoint1, Point paramPoint2, float paramFloat)
/*     */   {
/* 899 */     int[][] arrayOfInt = new int[2][3];
/*     */     
/*     */ 
/*     */ 
/* 903 */     float f1 = (paramPoint1.x + paramPoint2.x) / 2.0F;float f2 = (paramPoint1.y + paramPoint2.y) / 2.0F;
/*     */     
/* 905 */     float f3 = paramPoint1.x - paramPoint2.x;float f4 = paramPoint1.y - paramPoint2.y;
/* 906 */     float f5 = (float)Math.sqrt(f3 * f3 + f4 * f4);
/*     */     
/* 908 */     arrayOfInt[0][0] = ((int)(f1 - paramFloat * f3 / f5));
/* 909 */     arrayOfInt[1][0] = ((int)(f2 - paramFloat * f4 / f5));
/*     */     
/* 911 */     f1 += paramFloat * f3 / f5;
/* 912 */     f2 += paramFloat * f4 / f5;
/*     */     
/* 914 */     arrayOfInt[0][1] = ((int)(f1 - 0.7F * paramFloat * f4 / f5));
/* 915 */     arrayOfInt[1][1] = ((int)(f2 + 0.7F * paramFloat * f3 / f5));
/*     */     
/* 917 */     arrayOfInt[0][2] = ((int)(f1 + 0.7F * paramFloat * f4 / f5));
/* 918 */     arrayOfInt[1][2] = ((int)(f2 - 0.7F * paramFloat * f3 / f5));
/* 919 */     return arrayOfInt;
/*     */   }
/*     */ }


/* Location:              /home/anali/INAOE/tesis/codigos/topub/exe/ww4.jar!/Network/NetPaint.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */