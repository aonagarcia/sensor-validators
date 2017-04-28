/*     */ package Network;
/*     */ 
/*     */ import java.awt.GridLayout;
/*     */ import java.awt.Panel;
/*     */ import java.awt.Point;
/*     */ import java.awt.TextArea;
/*     */ 
/*     */ 
/*     */ public class HelpPanel
/*     */   extends Panel
/*     */ {
/*  12 */   static int rows = 4; static int columns = 1;
/*  13 */   static TextArea textArea = new TextArea(rows, columns);
/*     */   
/*     */ 
/*     */   public HelpPanel()
/*     */   {
/*  18 */     int i = 1;int j = 1;
/*  19 */     setLayout(new GridLayout(i, j));
/*     */     
/*     */ 
/*  22 */     setFont(NetPaint.helvetica15);
/*     */     
/*  24 */     textArea.setEditable(false);
/*  25 */     add(textArea);
/*     */   }
/*     */   
/*     */ 
/*     */   public static void showHelp(String paramString, boolean paramBoolean)
/*     */   {
/*  31 */     if (paramBoolean) textArea.setText(paramString); else {
/*  32 */       textArea.append(paramString);
/*     */     }
/*     */   }
/*     */   
/*     */   public static void showHelp(String paramString) {
/*  37 */     showHelp(paramString, true);
/*     */   }
/*     */   
/*     */   public static void addHelp(String paramString)
/*     */   {
/*  42 */     showHelp("\n" + paramString, false);
/*     */   }
/*     */   
/*     */   public static void appendHelp(String paramString)
/*     */   {
/*  47 */     showHelp(paramString, false);
/*     */   }
/*     */   
/*     */   public static void showError(String paramString)
/*     */   {
/*  52 */     showHelp("\nError: " + paramString, false);
/*     */   }
/*     */   
/*     */   public static void clearHelp()
/*     */   {
/*  57 */     textArea.setText("");
/*     */   }
/*     */   
/*     */   public static String getText()
/*     */   {
/*  62 */     return textArea.getText();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static void showList(String paramString, int[] paramArrayOfInt)
/*     */   {
/*  69 */     addHelp(UTIL.listToStr(paramString, paramArrayOfInt));
/*     */   }
/*     */   
/*     */   public static void showList(String paramString, int[][] paramArrayOfInt)
/*     */   {
/*  74 */     addHelp(UTIL.listToStr(paramString, paramArrayOfInt));
/*     */   }
/*     */   
/*     */   public static void showList(String paramString, float[] paramArrayOfFloat)
/*     */   {
/*  79 */     addHelp(UTIL.listToStr(paramString, paramArrayOfFloat));
/*     */   }
/*     */   
/*  82 */   public static void showList(String paramString, float[][] paramArrayOfFloat) { addHelp(UTIL.listToStr(paramString, paramArrayOfFloat)); }
/*     */   
/*     */ 
/*     */   public static void showList(String paramString, double[] paramArrayOfDouble)
/*     */   {
/*  87 */     addHelp(UTIL.listToStr(paramString, paramArrayOfDouble));
/*     */   }
/*     */   
/*     */   public static void showList(String paramString, String[] paramArrayOfString)
/*     */   {
/*  92 */     addHelp(UTIL.listToStr(paramString, paramArrayOfString));
/*     */   }
/*     */   
/*     */   public static void showList(String paramString, boolean[] paramArrayOfBoolean)
/*     */   {
/*  97 */     addHelp(UTIL.listToStr(paramString, paramArrayOfBoolean));
/*     */   }
/*     */   
/*     */   public static void showList(String paramString, Point[] paramArrayOfPoint)
/*     */   {
/* 102 */     addHelp(UTIL.listToStr(paramString, paramArrayOfPoint));
/*     */   }
/*     */   
/*     */   public static void appendList(String paramString, int[] paramArrayOfInt)
/*     */   {
/* 107 */     appendHelp(UTIL.listToStr(paramString, paramArrayOfInt));
/*     */   }
/*     */   
/*     */   public static void appendList(String paramString, Point[] paramArrayOfPoint) {
/* 111 */     appendHelp(UTIL.listToStr(paramString, paramArrayOfPoint));
/*     */   }
/*     */   
/*     */   public static void appendList(String paramString, float[] paramArrayOfFloat) {
/* 115 */     appendHelp(UTIL.listToStr(paramString, paramArrayOfFloat));
/*     */   }
/*     */   
/*     */   public static void appendList(String paramString, String[] paramArrayOfString) {
/* 119 */     appendHelp(UTIL.listToStr(paramString, paramArrayOfString));
/*     */   }
/*     */   
/*     */   public static void appendList(String paramString, String[][] paramArrayOfString)
/*     */   {
/* 124 */     StringBuffer localStringBuffer = new StringBuffer(paramString);
/* 125 */     if (paramArrayOfString == null) { localStringBuffer.append("[] ");
/*     */     } else {
/* 127 */       localStringBuffer.append("[");
/* 128 */       for (int i = 0; i < paramArrayOfString.length - 1; i++)
/* 129 */         localStringBuffer.append("(" + paramArrayOfString[i][0] + "," + paramArrayOfString[i][1] + "),");
/* 130 */       localStringBuffer.append("(" + paramArrayOfString[(paramArrayOfString.length - 1)][0] + "," + paramArrayOfString[(paramArrayOfString.length - 1)][1] + ")] ");
/*     */     }
/* 132 */     appendHelp(localStringBuffer.toString());
/*     */   }
/*     */ }


/* Location:              /home/anali/INAOE/tesis/codigos/topub/exe/ww4.jar!/Network/HelpPanel.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */