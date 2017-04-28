/*      */ package Network;
/*      */ 
/*      */ import java.awt.Point;
/*      */ import java.io.BufferedReader;
/*      */ import java.io.FileWriter;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStreamReader;
/*      */ import java.io.PrintStream;
/*      */ import java.io.PrintWriter;
/*      */ import java.util.StringTokenizer;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class UTIL
/*      */ {
/*      */   public static void skipLine(BufferedReader paramBufferedReader)
/*      */   {
/*      */     try
/*      */     {
/*   28 */       paramBufferedReader.readLine();
/*      */     }
/*      */     catch (IOException localIOException) {
/*   31 */       HelpPanel.showError("Unable to skip a line!");
/*      */     }
/*      */   }
/*      */   
/*      */   public static void skipLine(BufferedReader paramBufferedReader, int paramInt) {
/*   36 */     for (int i = 0; i < paramInt; i++) skipLine(paramBufferedReader);
/*      */   }
/*      */   
/*      */   public static int countToken(String paramString)
/*      */   {
/*   41 */     if (paramString == null) return 0;
/*   42 */     StringTokenizer localStringTokenizer = new StringTokenizer(paramString);
/*   43 */     return localStringTokenizer.countTokens();
/*      */   }
/*      */   
/*      */ 
/*      */   public static int loadInt(BufferedReader paramBufferedReader)
/*      */   {
/*      */     try
/*      */     {
/*   51 */       StringTokenizer localStringTokenizer = new StringTokenizer(paramBufferedReader.readLine());
/*   52 */       return Integer.parseInt(localStringTokenizer.nextToken());
/*      */     }
/*      */     catch (IOException localIOException) {
/*   55 */       HelpPanel.showError("Unable to load integer!");
/*      */     }
/*   57 */     return -1;
/*      */   }
/*      */   
/*      */ 
/*      */   public static int getInt(String paramString)
/*      */   {
/*   63 */     if (paramString == null) return -1;
/*   64 */     StringTokenizer localStringTokenizer = new StringTokenizer(paramString);
/*   65 */     return Integer.parseInt(localStringTokenizer.nextToken());
/*      */   }
/*      */   
/*      */ 
/*      */   public static long getLong(String paramString)
/*      */   {
/*   71 */     if (paramString == null) return -1L;
/*   72 */     StringTokenizer localStringTokenizer = new StringTokenizer(paramString);
/*   73 */     return Long.parseLong(localStringTokenizer.nextToken());
/*      */   }
/*      */   
/*      */   public static int[] loadIntList(BufferedReader paramBufferedReader)
/*      */   {
/*      */     try {
/*   79 */       StringTokenizer localStringTokenizer = new StringTokenizer(paramBufferedReader.readLine());
/*   80 */       int i = Integer.parseInt(localStringTokenizer.nextToken());
/*   81 */       if (i < 0) {
/*   82 */         HelpPanel.showError("Negative count!");return null;
/*      */       }
/*   84 */       if (i == 0) { return null;
/*      */       }
/*   86 */       int[] arrayOfInt = new int[i];
/*   87 */       for (int j = 0; j < i; j++) arrayOfInt[j] = Integer.parseInt(localStringTokenizer.nextToken());
/*   88 */       return arrayOfInt;
/*      */     }
/*      */     catch (IOException localIOException) {
/*   91 */       HelpPanel.showError("Unable to load integer list!");
/*      */     }
/*   93 */     return null;
/*      */   }
/*      */   
/*      */   public static int[] getIntList(String paramString)
/*      */   {
/*   98 */     if (paramString == null) return null;
/*   99 */     StringTokenizer localStringTokenizer = new StringTokenizer(paramString);
/*  100 */     int i = Integer.parseInt(localStringTokenizer.nextToken());
/*  101 */     if (i == 0) { return null;
/*      */     }
/*  103 */     int[] arrayOfInt = new int[i];
/*  104 */     for (int j = 0; j < i; j++) arrayOfInt[j] = Integer.parseInt(localStringTokenizer.nextToken());
/*  105 */     return arrayOfInt;
/*      */   }
/*      */   
/*      */   public static int[] getIntListPure(String paramString)
/*      */   {
/*  110 */     if (paramString == null) return null;
/*  111 */     StringTokenizer localStringTokenizer = new StringTokenizer(paramString);
/*  112 */     int i = localStringTokenizer.countTokens();
/*      */     
/*  114 */     int[] arrayOfInt = new int[i];
/*  115 */     for (int j = 0; j < i; j++) arrayOfInt[j] = Integer.parseInt(localStringTokenizer.nextToken());
/*  116 */     return arrayOfInt;
/*      */   }
/*      */   
/*      */ 
/*      */   public static int[][] getIntPairListPure(String paramString)
/*      */   {
/*  122 */     if (paramString == null) return (int[][])null;
/*  123 */     StringTokenizer localStringTokenizer = new StringTokenizer(paramString);
/*  124 */     int i = localStringTokenizer.countTokens();
/*      */     
/*  126 */     int[][] arrayOfInt = new int[i][2];
/*  127 */     for (int j = 0; j < i; j++) {
/*  128 */       String str1 = new String(localStringTokenizer.nextToken());
/*  129 */       int k = str1.indexOf('=');
/*  130 */       if (k == -1) return (int[][])null;
/*  131 */       String str2 = str1.substring(0, k);
/*  132 */       String str3 = str1.substring(k + 1);
/*      */       
/*  134 */       arrayOfInt[j][0] = Integer.parseInt(str2);
/*  135 */       arrayOfInt[j][1] = Integer.parseInt(str3);
/*      */     }
/*  137 */     return arrayOfInt;
/*      */   }
/*      */   
/*      */   public static int[] loadIntList(BufferedReader paramBufferedReader, int paramInt)
/*      */   {
/*  142 */     if (paramInt < 0) {
/*  143 */       HelpPanel.showError("Negative count!");return null;
/*      */     }
/*  145 */     if (paramInt == 0) return null;
/*      */     try
/*      */     {
/*  148 */       StringTokenizer localStringTokenizer = new StringTokenizer(paramBufferedReader.readLine());
/*  149 */       int[] arrayOfInt = new int[paramInt];
/*  150 */       for (int i = 0; i < paramInt; i++) arrayOfInt[i] = Integer.parseInt(localStringTokenizer.nextToken());
/*  151 */       return arrayOfInt;
/*      */     }
/*      */     catch (IOException localIOException) {
/*  154 */       HelpPanel.showError("Unable to load integer list!");
/*      */     }
/*  156 */     return null;
/*      */   }
/*      */   
/*      */   public static int[] getIntList(String paramString, int paramInt)
/*      */   {
/*  161 */     if (paramInt < 0) {
/*  162 */       HelpPanel.showError("Negative count!");return null;
/*      */     }
/*  164 */     if (paramInt == 0) { return null;
/*      */     }
/*  166 */     StringTokenizer localStringTokenizer = new StringTokenizer(paramString);
/*  167 */     int[] arrayOfInt = new int[paramInt];
/*  168 */     for (int i = 0; i < paramInt; i++) arrayOfInt[i] = Integer.parseInt(localStringTokenizer.nextToken());
/*  169 */     return arrayOfInt;
/*      */   }
/*      */   
/*      */   public static int[] loadIntListLine(BufferedReader paramBufferedReader)
/*      */   {
/*      */     try {
/*  175 */       StringTokenizer localStringTokenizer = new StringTokenizer(paramBufferedReader.readLine());
/*  176 */       int i = localStringTokenizer.countTokens();
/*  177 */       if (i == 0) { return null;
/*      */       }
/*  179 */       int[] arrayOfInt = new int[i];
/*  180 */       for (int j = 0; j < i; j++) arrayOfInt[j] = Integer.parseInt(localStringTokenizer.nextToken());
/*  181 */       return arrayOfInt;
/*      */     }
/*      */     catch (IOException localIOException) {
/*  184 */       HelpPanel.showError("Unable to load integer list!");
/*      */     }
/*  186 */     return null;
/*      */   }
/*      */   
/*      */   public static int[] loadIntListMLine(BufferedReader paramBufferedReader, int paramInt)
/*      */   {
/*  191 */     if (paramInt < 0) {
/*  192 */       HelpPanel.showError("Negative count!");return null;
/*      */     }
/*  194 */     if (paramInt == 0) { return null;
/*      */     }
/*  196 */     int[] arrayOfInt = new int[paramInt];
/*      */     try {
/*  198 */       int i = 0;
/*  199 */       while (i < paramInt) {
/*  200 */         StringTokenizer localStringTokenizer = new StringTokenizer(paramBufferedReader.readLine());
/*  201 */         int j = localStringTokenizer.countTokens();
/*  202 */         for (int k = 0; k < j; k++) {
/*  203 */           arrayOfInt[(i++)] = Integer.parseInt(localStringTokenizer.nextToken());
/*      */         }
/*      */       }
/*      */     } catch (IOException localIOException) {
/*  207 */       HelpPanel.showError("Unable to load multiline integer list!");
/*      */     }
/*  209 */     return arrayOfInt;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public static void saveIntList(PrintWriter paramPrintWriter, int[] paramArrayOfInt, String paramString)
/*      */   {
/*  216 */     if (paramArrayOfInt == null) {
/*  217 */       paramPrintWriter.println("0  " + paramString);return;
/*      */     }
/*  219 */     paramPrintWriter.print(paramArrayOfInt.length + "  ");
/*  220 */     for (int i = 0; i < paramArrayOfInt.length; i++) paramPrintWriter.print(paramArrayOfInt[i] + " ");
/*  221 */     paramPrintWriter.print(" " + paramString);
/*  222 */     paramPrintWriter.println();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static float[] getRealList(String paramString, int paramInt1, int paramInt2)
/*      */   {
/*  232 */     StringTokenizer localStringTokenizer = new StringTokenizer(paramString);
/*  233 */     for (int i = 0; i < paramInt2; i++) localStringTokenizer.nextToken();
/*  234 */     float[] arrayOfFloat = new float[paramInt1];
/*  235 */     for (int j = 0; j < paramInt1; j++)
/*  236 */       arrayOfFloat[j] = Float.valueOf(localStringTokenizer.nextToken()).floatValue();
/*  237 */     return arrayOfFloat;
/*      */   }
/*      */   
/*      */   public static float[] getRealList(String paramString)
/*      */   {
/*  242 */     if (paramString == null) return null;
/*  243 */     float[] arrayOfFloat = null;
/*  244 */     StringTokenizer localStringTokenizer = new StringTokenizer(paramString);
/*  245 */     int i = localStringTokenizer.countTokens();
/*  246 */     arrayOfFloat = new float[i];
/*  247 */     for (int j = 0; j < i; j++)
/*  248 */       arrayOfFloat[j] = Float.valueOf(localStringTokenizer.nextToken()).floatValue();
/*  249 */     return arrayOfFloat;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static float[] getRealList(String paramString, int paramInt)
/*      */   {
/*  258 */     if (paramString == null) return null;
/*  259 */     float[] arrayOfFloat = null;
/*  260 */     StringTokenizer localStringTokenizer = new StringTokenizer(paramString);
/*  261 */     int i = localStringTokenizer.countTokens();
/*  262 */     if (i < paramInt) return null;
/*  263 */     arrayOfFloat = new float[paramInt];
/*  264 */     for (int j = 0; j < paramInt; j++)
/*  265 */       arrayOfFloat[j] = Float.valueOf(localStringTokenizer.nextToken()).floatValue();
/*  266 */     return arrayOfFloat;
/*      */   }
/*      */   
/*      */   public static float[] loadRealList(BufferedReader paramBufferedReader, int paramInt)
/*      */   {
/*  271 */     if (paramInt < 0) {
/*  272 */       HelpPanel.showError("Negative count!");return null;
/*      */     }
/*  274 */     if (paramInt == 0) { return null;
/*      */     }
/*  276 */     float[] arrayOfFloat = new float[paramInt];
/*      */     try {
/*  278 */       int i = 0;
/*  279 */       while (i < paramInt) {
/*  280 */         StringTokenizer localStringTokenizer = new StringTokenizer(paramBufferedReader.readLine());
/*  281 */         int j = localStringTokenizer.countTokens();
/*  282 */         for (int k = 0; k < j; k++) {
/*  283 */           arrayOfFloat[(i++)] = Float.valueOf(localStringTokenizer.nextToken()).floatValue();
/*      */         }
/*      */       }
/*      */     } catch (IOException localIOException) {
/*  287 */       HelpPanel.showError("Unable to load real list!");
/*      */     }
/*  289 */     return arrayOfFloat;
/*      */   }
/*      */   
/*      */   public static float[] getRealList(String[] paramArrayOfString)
/*      */   {
/*  294 */     if (paramArrayOfString == null) return null;
/*  295 */     int i = 0;
/*  296 */     for (int j = 0; j < paramArrayOfString.length; j++) {
/*  297 */       if (paramArrayOfString[j] == null) { HelpPanel.showError("Null string");return null; }
/*  298 */       StringTokenizer localStringTokenizer = new StringTokenizer(paramArrayOfString[j]);
/*  299 */       i += localStringTokenizer.countTokens();
/*      */     }
/*  301 */     float[] arrayOfFloat1 = new float[i];
/*  302 */     int k = 0;
/*  303 */     for (int m = 0; m < paramArrayOfString.length; m++) {
/*  304 */       float[] arrayOfFloat2 = getRealList(paramArrayOfString[m]);
/*  305 */       for (int n = 0; n < arrayOfFloat2.length; n++) arrayOfFloat1[(k++)] = arrayOfFloat2[n];
/*      */     }
/*  307 */     return arrayOfFloat1;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static void skipRealList(BufferedReader paramBufferedReader, int paramInt)
/*      */   {
/*  319 */     if (paramInt < 0) {
/*  320 */       HelpPanel.showError("Negative count!");return;
/*      */     }
/*  322 */     if (paramInt == 0) return;
/*      */     try
/*      */     {
/*  325 */       int i = 0;
/*  326 */       while (i < paramInt) {
/*  327 */         StringTokenizer localStringTokenizer = new StringTokenizer(paramBufferedReader.readLine());
/*  328 */         int j = localStringTokenizer.countTokens();
/*  329 */         for (int k = 0; k < j; k++) {
/*  330 */           float f = Float.valueOf(localStringTokenizer.nextToken()).floatValue();i++;
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (IOException localIOException) {
/*  335 */       HelpPanel.showError("Unable to skip real list!");
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   public static void saveRealList(PrintWriter paramPrintWriter, float[] paramArrayOfFloat)
/*      */   {
/*  342 */     if (paramArrayOfFloat == null) return;
/*  343 */     for (int i = 0; i < paramArrayOfFloat.length; i++) {
/*  344 */       if ((i % 5 == 0) && (i != 0)) paramPrintWriter.println();
/*  345 */       paramPrintWriter.print(paramArrayOfFloat[i] + " ");
/*      */     }
/*  347 */     paramPrintWriter.println();
/*      */   }
/*      */   
/*      */ 
/*      */   public static Point loadPoint(BufferedReader paramBufferedReader)
/*      */   {
/*      */     try
/*      */     {
/*  355 */       StringTokenizer localStringTokenizer = new StringTokenizer(paramBufferedReader.readLine());
/*  356 */       int i = Integer.parseInt(localStringTokenizer.nextToken());
/*  357 */       int j = Integer.parseInt(localStringTokenizer.nextToken());
/*  358 */       return new Point(i, j);
/*      */     }
/*      */     catch (IOException localIOException) {
/*  361 */       HelpPanel.showError("Unable to load a point!");
/*      */     }
/*  363 */     return null;
/*      */   }
/*      */   
/*      */   public static void savePoint(PrintWriter paramPrintWriter, Point paramPoint)
/*      */   {
/*  368 */     paramPrintWriter.println(paramPoint.x + " " + paramPoint.y + "  Coordinate");
/*      */   }
/*      */   
/*      */ 
/*      */   static Point[] strToPoint(String[] paramArrayOfString)
/*      */   {
/*  374 */     if (paramArrayOfString == null) return null;
/*  375 */     Point[] arrayOfPoint = new Point[paramArrayOfString.length];
/*  376 */     for (int i = 0; i < paramArrayOfString.length; i++) {
/*  377 */       String[] arrayOfString = getStringList(paramArrayOfString[i]);
/*  378 */       arrayOfPoint[i] = new Point(Integer.parseInt(arrayOfString[0]), Integer.parseInt(arrayOfString[1]));
/*      */     }
/*  380 */     return arrayOfPoint;
/*      */   }
/*      */   
/*      */ 
/*      */   public static String loadString(BufferedReader paramBufferedReader)
/*      */   {
/*      */     try
/*      */     {
/*  388 */       StringTokenizer localStringTokenizer = new StringTokenizer(paramBufferedReader.readLine());
/*  389 */       return new String(localStringTokenizer.nextToken());
/*      */     }
/*      */     catch (IOException localIOException) {
/*  392 */       HelpPanel.showError("Unable to load a string!");
/*      */     }
/*  394 */     return null;
/*      */   }
/*      */   
/*      */   public static String readString(String paramString)
/*      */   {
/*  399 */     BufferedReader localBufferedReader = new BufferedReader(new InputStreamReader(System.in));
/*      */     
/*  401 */     System.out.println(paramString);
/*  402 */     String str = null;
/*      */     try {
/*  404 */       str = localBufferedReader.readLine();
/*      */     } catch (IOException localIOException) {
/*  406 */       System.out.println("Unable to read.");
/*      */     }
/*  408 */     return str;
/*      */   }
/*      */   
/*      */   public static String loadStringLine(BufferedReader paramBufferedReader)
/*      */   {
/*      */     try {
/*  414 */       return paramBufferedReader.readLine();
/*      */     } catch (IOException localIOException) {
/*  416 */       HelpPanel.showError("Unable to load a string!");
/*      */     }
/*  418 */     return null;
/*      */   }
/*      */   
/*      */   public static String[] loadStringList(BufferedReader paramBufferedReader, int paramInt)
/*      */   {
/*  423 */     if (paramInt < 0) {
/*  424 */       HelpPanel.showError("Negative count!");return null;
/*      */     }
/*  426 */     if (paramInt == 0) return null;
/*  427 */     String[] arrayOfString = new String[paramInt];
/*  428 */     for (int i = 0; i < paramInt; i++) arrayOfString[i] = loadString(paramBufferedReader);
/*  429 */     return arrayOfString;
/*      */   }
/*      */   
/*      */   public static String[] loadStringListLine(BufferedReader paramBufferedReader, int paramInt)
/*      */   {
/*  434 */     if (paramInt < 0) {
/*  435 */       HelpPanel.showError("Negative count!");return null;
/*      */     }
/*  437 */     if (paramInt == 0) return null;
/*  438 */     String[] arrayOfString = new String[paramInt];
/*      */     try {
/*  440 */       StringTokenizer localStringTokenizer = new StringTokenizer(paramBufferedReader.readLine());
/*  441 */       for (int i = 0; i < paramInt; i++) arrayOfString[i] = new String(localStringTokenizer.nextToken());
/*      */     }
/*      */     catch (IOException localIOException) {
/*  444 */       HelpPanel.showError("Cannot load " + paramInt + " strings from one line!");
/*      */     }
/*  446 */     return arrayOfString;
/*      */   }
/*      */   
/*      */   public static String[] loadStringListLine(BufferedReader paramBufferedReader) {
/*  450 */     String[] arrayOfString = null;
/*      */     try {
/*  452 */       StringTokenizer localStringTokenizer = new StringTokenizer(paramBufferedReader.readLine());
/*  453 */       int i = localStringTokenizer.countTokens();
/*  454 */       if (i == 0) return null;
/*  455 */       arrayOfString = new String[i];
/*  456 */       for (int j = 0; j < i; j++) arrayOfString[j] = new String(localStringTokenizer.nextToken());
/*      */     }
/*      */     catch (IOException localIOException) {
/*  459 */       HelpPanel.showError("Cannot load strings from file line!");
/*      */     }
/*  461 */     return arrayOfString;
/*      */   }
/*      */   
/*      */   public static String[] loadStringListMLine(BufferedReader paramBufferedReader, int paramInt)
/*      */   {
/*  466 */     if (paramInt < 0) {
/*  467 */       HelpPanel.showError("Negative count!");return null;
/*      */     }
/*  469 */     if (paramInt == 0) return null;
/*  470 */     String[] arrayOfString = new String[paramInt];
/*      */     try {
/*  472 */       int i = 0;
/*      */       do {
/*  474 */         StringTokenizer localStringTokenizer = new StringTokenizer(paramBufferedReader.readLine());
/*  475 */         int j = localStringTokenizer.countTokens();
/*  476 */         for (int k = 0; k < j; k++) arrayOfString[(i++)] = new String(localStringTokenizer.nextToken());
/*  477 */       } while (i < paramInt);
/*      */     }
/*      */     catch (IOException localIOException) {
/*  480 */       HelpPanel.showError("Cannot load " + paramInt + " strings from multi-line!");
/*      */     }
/*  482 */     return arrayOfString;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static String[][] loadStringArray(BufferedReader paramBufferedReader)
/*      */   {
/*  494 */     int i = loadInt(paramBufferedReader);
/*      */     
/*  496 */     String[][] arrayOfString = (String[][])null;
/*  497 */     int j = 0;
/*      */     try {
/*  499 */       for (int k = 0; k < i; k++) {
/*  500 */         StringTokenizer localStringTokenizer = new StringTokenizer(paramBufferedReader.readLine());
/*  501 */         if (k == 0) {
/*  502 */           j = localStringTokenizer.countTokens();
/*  503 */           if (j <= 0) return (String[][])null;
/*  504 */           arrayOfString = new String[i][j];
/*      */         }
/*  506 */         for (int m = 0; m < j; m++) arrayOfString[k][m] = localStringTokenizer.nextToken();
/*      */       }
/*      */     } catch (IOException localIOException) {
/*  509 */       HelpPanel.showError("Error loading strings from file.");
/*      */     }
/*      */     
/*  512 */     return arrayOfString;
/*      */   }
/*      */   
/*      */   public static String[] getStringList(String paramString)
/*      */   {
/*  517 */     if (paramString == null) return null;
/*  518 */     StringTokenizer localStringTokenizer = new StringTokenizer(paramString);
/*  519 */     int i = localStringTokenizer.countTokens();
/*  520 */     String[] arrayOfString = new String[i];
/*  521 */     for (int j = 0; j < i; j++) arrayOfString[j] = new String(localStringTokenizer.nextToken());
/*  522 */     return arrayOfString;
/*      */   }
/*      */   
/*      */   public static void saveString(PrintWriter paramPrintWriter, String paramString)
/*      */   {
/*  527 */     if (paramString == null) {
/*  528 */       HelpPanel.showError("Empty string!");return;
/*      */     }
/*  530 */     paramPrintWriter.println(paramString);
/*      */   }
/*      */   
/*      */   public static void saveStringList(PrintWriter paramPrintWriter, String[] paramArrayOfString)
/*      */   {
/*  535 */     if (paramArrayOfString == null) {
/*  536 */       HelpPanel.showError("Empty string list!");return;
/*      */     }
/*  538 */     for (int i = 0; i < paramArrayOfString.length; i++) saveString(paramPrintWriter, paramArrayOfString[i]);
/*      */   }
/*      */   
/*      */   public static void saveStringListMLine(PrintWriter paramPrintWriter, String[] paramArrayOfString)
/*      */   {
/*  543 */     if (paramArrayOfString == null) {
/*  544 */       HelpPanel.showError("Empty string list!");return;
/*      */     }
/*  546 */     for (int i = 0; i < paramArrayOfString.length; i++) {
/*  547 */       if ((i % 5 == 0) && (i != 0)) paramPrintWriter.println();
/*  548 */       paramPrintWriter.print(paramArrayOfString[i] + " ");
/*      */     }
/*  550 */     paramPrintWriter.println();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public static String toString(float paramFloat)
/*      */   {
/*  557 */     StringBuffer localStringBuffer = new StringBuffer();
/*  558 */     localStringBuffer.append(paramFloat);
/*  559 */     return localStringBuffer.toString();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public static String endWithSpace(String paramString, int paramInt)
/*      */   {
/*  566 */     String str = new String("");
/*  567 */     for (int i = 0; i < paramInt; i++) { str = new String(str + " ");
/*      */     }
/*  569 */     StringBuffer localStringBuffer = new StringBuffer(paramString);
/*  570 */     int j = paramString.length();
/*  571 */     if (j >= paramInt) localStringBuffer.setLength(paramInt - 1);
/*  572 */     localStringBuffer.append(str);
/*  573 */     localStringBuffer.setLength(paramInt);
/*  574 */     return localStringBuffer.toString();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static String openWithZero(String paramString, int paramInt)
/*      */   {
/*  583 */     int i = paramString.length();
/*  584 */     if (i == paramInt) { return new String(paramString);
/*      */     }
/*  586 */     String str = new String("");
/*  587 */     if (i < paramInt) {
/*  588 */       for (int j = 0; j < paramInt - i; j++) str = new String("0" + str);
/*  589 */       return new String(str + paramString);
/*      */     }
/*  591 */     return paramString.substring(i - paramInt);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static String[] evenLength(String[] paramArrayOfString, int paramInt)
/*      */   {
/*  608 */     String str = new String("");
/*  609 */     for (int i = 0; i < paramInt; i++) { str = new String(str + " ");
/*      */     }
/*  611 */     i = paramArrayOfString.length;
/*  612 */     String[] arrayOfString = new String[i];
/*  613 */     for (int j = 0; j < i; j++) {
/*  614 */       StringBuffer localStringBuffer = new StringBuffer(paramArrayOfString[j]);
/*  615 */       int k = paramArrayOfString[j].length();
/*  616 */       if (k >= paramInt) localStringBuffer.setLength(paramInt - 1);
/*  617 */       localStringBuffer.append(str);
/*  618 */       localStringBuffer.setLength(paramInt);
/*  619 */       arrayOfString[j] = localStringBuffer.toString();
/*      */     }
/*  621 */     return arrayOfString;
/*      */   }
/*      */   
/*      */   public static String[] evenLength(String[] paramArrayOfString)
/*      */   {
/*  626 */     int i = paramArrayOfString.length;
/*  627 */     int j = 0;
/*  628 */     for (int k = 0; k < i; k++) {
/*  629 */       int m = paramArrayOfString[k].length();
/*  630 */       if (m > j) j = m;
/*      */     }
/*  632 */     return evenLength(paramArrayOfString, j + 1);
/*      */   }
/*      */   
/*      */   public static String replacePostfix(String paramString1, String paramString2)
/*      */   {
/*  637 */     String str = removePostfix(paramString1);
/*  638 */     return new String(str + "." + paramString2);
/*      */   }
/*      */   
/*      */ 
/*      */   public static String removePostfix(String paramString)
/*      */   {
/*  644 */     int i = paramString.lastIndexOf(".");
/*  645 */     StringBuffer localStringBuffer = new StringBuffer(paramString);
/*  646 */     if (i != -1) localStringBuffer.setLength(i);
/*  647 */     return localStringBuffer.toString();
/*      */   }
/*      */   
/*      */ 
/*      */   public static String removePath(String paramString)
/*      */   {
/*  653 */     int i = paramString.lastIndexOf("/");
/*  654 */     if (i == -1) i = paramString.lastIndexOf("\\");
/*  655 */     return paramString.substring(i + 1);
/*      */   }
/*      */   
/*      */ 
/*      */   public static String removeBrace(String paramString)
/*      */   {
/*  661 */     return paramString.substring(1, paramString.length() - 2);
/*      */   }
/*      */   
/*      */ 
/*      */   public static String getLastToken(String paramString)
/*      */   {
/*  667 */     int i = paramString.length();
/*  668 */     int j = paramString.lastIndexOf("/");
/*  669 */     if (j == -1) j = paramString.lastIndexOf("\\");
/*  670 */     if (i != j + 1) { return null;
/*      */     }
/*  672 */     StringBuffer localStringBuffer = new StringBuffer(paramString);
/*  673 */     localStringBuffer.setLength(j);
/*  674 */     String str = localStringBuffer.toString();
/*  675 */     return removePath(str);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static int[] project(int[] paramArrayOfInt1, int[] paramArrayOfInt2, int[] paramArrayOfInt3)
/*      */   {
/*  684 */     return getSubsetByIndex(paramArrayOfInt2, getSubsetIndex(paramArrayOfInt1, paramArrayOfInt3));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static int[] merge(int[] paramArrayOfInt1, int[] paramArrayOfInt2, int[] paramArrayOfInt3, int[] paramArrayOfInt4)
/*      */   {
/*  693 */     int[] arrayOfInt1 = getSubsetIndex(paramArrayOfInt3, paramArrayOfInt1);
/*  694 */     int[] arrayOfInt2 = getDuplicate(paramArrayOfInt4);
/*  695 */     for (int i = 0; i < arrayOfInt1.length; i++) arrayOfInt2[arrayOfInt1[i]] = paramArrayOfInt2[i];
/*  696 */     return arrayOfInt2;
/*      */   }
/*      */   
/*      */   public static int[] getDuplicate(int[] paramArrayOfInt)
/*      */   {
/*  701 */     if (paramArrayOfInt == null) return null;
/*  702 */     int i = paramArrayOfInt.length;
/*  703 */     int[] arrayOfInt = new int[i];
/*  704 */     System.arraycopy(paramArrayOfInt, 0, arrayOfInt, 0, i);
/*  705 */     return arrayOfInt;
/*      */   }
/*      */   
/*  708 */   public static int[][] getDuplicate(int[][] paramArrayOfInt) { if (paramArrayOfInt == null) return (int[][])null;
/*  709 */     int i = paramArrayOfInt.length;
/*  710 */     int[][] arrayOfInt = new int[i][];
/*  711 */     for (int j = 0; j < i; j++) arrayOfInt[j] = getDuplicate(paramArrayOfInt[j]);
/*  712 */     return arrayOfInt;
/*      */   }
/*      */   
/*  715 */   public static Point[] getDuplicate(Point[] paramArrayOfPoint) { if (paramArrayOfPoint == null) return null;
/*  716 */     int i = paramArrayOfPoint.length;
/*  717 */     Point[] arrayOfPoint = new Point[i];
/*  718 */     System.arraycopy(paramArrayOfPoint, 0, arrayOfPoint, 0, i);
/*  719 */     return arrayOfPoint;
/*      */   }
/*      */   
/*  722 */   public static float[] getDuplicate(float[] paramArrayOfFloat) { if (paramArrayOfFloat == null) return null;
/*  723 */     int i = paramArrayOfFloat.length;
/*  724 */     float[] arrayOfFloat = new float[i];
/*  725 */     System.arraycopy(paramArrayOfFloat, 0, arrayOfFloat, 0, i);
/*  726 */     return arrayOfFloat;
/*      */   }
/*      */   
/*  729 */   public static float[][] getDuplicate(float[][] paramArrayOfFloat) { if (paramArrayOfFloat == null) return (float[][])null;
/*  730 */     int i = paramArrayOfFloat.length;
/*  731 */     float[][] arrayOfFloat = new float[i][];
/*  732 */     for (int j = 0; j < i; j++) arrayOfFloat[j] = getDuplicate(paramArrayOfFloat[j]);
/*  733 */     return arrayOfFloat;
/*      */   }
/*      */   
/*  736 */   public static String[] getDuplicate(String[] paramArrayOfString) { if (paramArrayOfString == null) return null;
/*  737 */     int i = paramArrayOfString.length;
/*  738 */     String[] arrayOfString = new String[i];
/*  739 */     for (int j = 0; j < i; j++) arrayOfString[j] = paramArrayOfString[j];
/*  740 */     return arrayOfString;
/*      */   }
/*      */   
/*  743 */   public static String[][] getDuplicate(String[][] paramArrayOfString) { if (paramArrayOfString == null) return (String[][])null;
/*  744 */     int i = paramArrayOfString.length;
/*  745 */     String[][] arrayOfString = new String[i][];
/*  746 */     for (int j = 0; j < i; j++) arrayOfString[j] = getDuplicate(paramArrayOfString[j]);
/*  747 */     return arrayOfString;
/*      */   }
/*      */   
/*      */ 
/*      */   public static int[] delDuplicate(int[] paramArrayOfInt)
/*      */   {
/*  753 */     if (paramArrayOfInt == null) return null;
/*  754 */     int i = paramArrayOfInt.length;
/*  755 */     boolean[] arrayOfBoolean = new boolean[i];
/*  756 */     int j = 0;
/*  757 */     for (int k = 0; k < i - 1; k++) {
/*  758 */       for (m = k + 1; m < i; m++) {
/*  759 */         if (paramArrayOfInt[k] == paramArrayOfInt[m]) {
/*  760 */           arrayOfBoolean[k] = true;j++; break;
/*      */         }
/*      */       }
/*      */     }
/*  764 */     int[] arrayOfInt = new int[i - j];
/*  765 */     int m = 0;
/*  766 */     for (int n = 0; n < i; n++) if (arrayOfBoolean[n] == 0) arrayOfInt[(m++)] = paramArrayOfInt[n];
/*  767 */     return arrayOfInt;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public static int[][] delDuplicate(int[][] paramArrayOfInt)
/*      */   {
/*  774 */     if (paramArrayOfInt == null) return (int[][])null;
/*  775 */     int i = paramArrayOfInt.length;
/*  776 */     boolean[] arrayOfBoolean = new boolean[i];
/*  777 */     int j = 0;
/*  778 */     for (int k = 0; k < i - 1; k++) {
/*  779 */       for (m = k + 1; m < i; m++) {
/*  780 */         if (MATH.isEqualSet(paramArrayOfInt[k], paramArrayOfInt[m])) {
/*  781 */           arrayOfBoolean[k] = true;j++; break;
/*      */         }
/*      */       }
/*      */     }
/*  785 */     int[][] arrayOfInt = new int[i - j][];
/*  786 */     int m = 0;
/*  787 */     for (int n = 0; n < i; n++)
/*  788 */       if (arrayOfBoolean[n] == 0) arrayOfInt[(m++)] = getDuplicate(paramArrayOfInt[n]);
/*  789 */     return arrayOfInt;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static int getArrayIndex(int paramInt, int[] paramArrayOfInt)
/*      */   {
/*  799 */     if (paramArrayOfInt == null) return -1;
/*  800 */     for (int i = 0; i < paramArrayOfInt.length; i++) if (paramArrayOfInt[i] == paramInt) return i;
/*  801 */     return -1;
/*      */   }
/*      */   
/*      */   public static int getArrayIndex(Object paramObject, Object[] paramArrayOfObject) {
/*  805 */     if (paramArrayOfObject == null) return -1;
/*  806 */     for (int i = 0; i < paramArrayOfObject.length; i++) if (paramObject.equals(paramArrayOfObject[i])) return i;
/*  807 */     return -1;
/*      */   }
/*      */   
/*      */   public static int[] getArrayIndex(int[] paramArrayOfInt, int paramInt) {
/*  811 */     if (paramArrayOfInt == null) return null;
/*  812 */     int i = 0;
/*  813 */     for (int j = 0; j < paramArrayOfInt.length; j++) if (paramArrayOfInt[j] == paramInt) i++;
/*  814 */     if (i == 0) { return null;
/*      */     }
/*  816 */     int[] arrayOfInt = new int[i];
/*  817 */     int k = 0;
/*  818 */     for (int m = 0; m < paramArrayOfInt.length; m++) if (paramArrayOfInt[m] == paramInt) arrayOfInt[(k++)] = m;
/*  819 */     return arrayOfInt;
/*      */   }
/*      */   
/*      */   public static int getMaxMemberIndex(float[] paramArrayOfFloat)
/*      */   {
/*  824 */     if (paramArrayOfFloat == null) return -1;
/*  825 */     float f = paramArrayOfFloat[0];
/*  826 */     int i = 0;
/*  827 */     for (int j = 1; j < paramArrayOfFloat.length; j++) if (paramArrayOfFloat[j] > f) { f = paramArrayOfFloat[j];i = j; }
/*  828 */     return i;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public static int[] getSubsetIndex(int[] paramArrayOfInt1, int[] paramArrayOfInt2)
/*      */   {
/*  835 */     if ((paramArrayOfInt1 == null) || (paramArrayOfInt2 == null)) return null;
/*  836 */     if (!MATH.isSubset(paramArrayOfInt2, paramArrayOfInt1)) { return null;
/*      */     }
/*  838 */     int[] arrayOfInt = new int[paramArrayOfInt2.length];
/*  839 */     for (int i = 0; i < paramArrayOfInt2.length; i++) arrayOfInt[i] = getArrayIndex(paramArrayOfInt2[i], paramArrayOfInt1);
/*  840 */     return arrayOfInt;
/*      */   }
/*      */   
/*  843 */   public static int[] getSubsetIndex(String[] paramArrayOfString1, String[] paramArrayOfString2) { if ((paramArrayOfString1 == null) || (paramArrayOfString2 == null)) return null;
/*  844 */     if (!MATH.isSubset(paramArrayOfString2, paramArrayOfString1)) { return null;
/*      */     }
/*  846 */     int[] arrayOfInt = new int[paramArrayOfString2.length];
/*  847 */     for (int i = 0; i < paramArrayOfString2.length; i++) arrayOfInt[i] = getArrayIndex(paramArrayOfString2[i], paramArrayOfString1);
/*  848 */     return arrayOfInt;
/*      */   }
/*      */   
/*      */ 
/*      */   public static int[] getSubsetByIndex(int[] paramArrayOfInt1, int[] paramArrayOfInt2)
/*      */   {
/*  854 */     if ((paramArrayOfInt1 == null) || (paramArrayOfInt2 == null)) return null;
/*  855 */     int[] arrayOfInt = new int[paramArrayOfInt2.length];
/*  856 */     for (int i = 0; i < paramArrayOfInt2.length; i++) arrayOfInt[i] = paramArrayOfInt1[paramArrayOfInt2[i]];
/*  857 */     return arrayOfInt;
/*      */   }
/*      */   
/*      */   public static int[] appendToArray(int[] paramArrayOfInt, int paramInt)
/*      */   {
/*  862 */     int i = paramArrayOfInt == null ? 1 : paramArrayOfInt.length + 1;
/*  863 */     int[] arrayOfInt = new int[i];
/*  864 */     for (int j = 0; j < i - 1; j++) arrayOfInt[j] = paramArrayOfInt[j];
/*  865 */     arrayOfInt[(i - 1)] = paramInt;
/*  866 */     return arrayOfInt;
/*      */   }
/*      */   
/*  869 */   public static Point[] appendToArray(Point[] paramArrayOfPoint, Point paramPoint) { int i = paramArrayOfPoint == null ? 1 : paramArrayOfPoint.length + 1;
/*  870 */     Point[] arrayOfPoint = new Point[i];
/*  871 */     for (int j = 0; j < i - 1; j++) arrayOfPoint[j] = paramArrayOfPoint[j];
/*  872 */     arrayOfPoint[(i - 1)] = paramPoint;
/*  873 */     return arrayOfPoint;
/*      */   }
/*      */   
/*      */   public static Point[] appendToArray(Point[] paramArrayOfPoint1, Point[] paramArrayOfPoint2) {
/*  877 */     if ((paramArrayOfPoint1 == null) && (paramArrayOfPoint2 == null)) { return null;
/*      */     }
/*      */     Point[] arrayOfPoint;
/*  880 */     if (paramArrayOfPoint1 == null) { arrayOfPoint = getDuplicate(paramArrayOfPoint2);
/*  881 */     } else if (paramArrayOfPoint2 == null) { arrayOfPoint = getDuplicate(paramArrayOfPoint1);
/*      */     } else {
/*  883 */       arrayOfPoint = new Point[paramArrayOfPoint1.length + paramArrayOfPoint2.length];
/*  884 */       System.arraycopy(paramArrayOfPoint1, 0, arrayOfPoint, 0, paramArrayOfPoint1.length);
/*  885 */       System.arraycopy(paramArrayOfPoint2, 0, arrayOfPoint, paramArrayOfPoint1.length, paramArrayOfPoint2.length);
/*      */     }
/*  887 */     return arrayOfPoint;
/*      */   }
/*      */   
/*      */   public static int[] appendToArray(int[] paramArrayOfInt1, int[] paramArrayOfInt2)
/*      */   {
/*  892 */     if ((paramArrayOfInt1 == null) && (paramArrayOfInt2 == null)) return null;
/*      */     int[] arrayOfInt;
/*      */     int i;
/*  895 */     if (paramArrayOfInt1 == null) {
/*  896 */       arrayOfInt = new int[paramArrayOfInt2.length];
/*  897 */       for (i = 0; i < paramArrayOfInt2.length; i++) arrayOfInt[i] = paramArrayOfInt2[i];
/*      */     }
/*  899 */     else if (paramArrayOfInt2 == null) {
/*  900 */       arrayOfInt = new int[paramArrayOfInt1.length];
/*  901 */       for (i = 0; i < paramArrayOfInt1.length; i++) arrayOfInt[i] = paramArrayOfInt1[i];
/*      */     }
/*      */     else {
/*  904 */       i = paramArrayOfInt1.length + paramArrayOfInt2.length;
/*  905 */       arrayOfInt = new int[i];
/*  906 */       for (int j = 0; j < paramArrayOfInt1.length; j++) arrayOfInt[j] = paramArrayOfInt1[j];
/*  907 */       for (j = paramArrayOfInt1.length; j < i; j++) arrayOfInt[j] = paramArrayOfInt2[(j - paramArrayOfInt1.length)];
/*      */     }
/*  909 */     return arrayOfInt;
/*      */   }
/*      */   
/*      */   public static String[][] appendToArray(String[][] paramArrayOfString, String[] paramArrayOfString1)
/*      */   {
/*  914 */     if (paramArrayOfString1 == null) { return getDuplicate(paramArrayOfString);
/*      */     }
/*      */     int i;
/*  917 */     if (paramArrayOfString == null) i = 0; else
/*  918 */       i = paramArrayOfString.length;
/*  919 */     String[][] arrayOfString = new String[i + 1][];
/*  920 */     for (int j = 0; j < i; j++) arrayOfString[j] = getDuplicate(paramArrayOfString[j]);
/*  921 */     arrayOfString[i] = getDuplicate(paramArrayOfString1);
/*  922 */     return arrayOfString;
/*      */   }
/*      */   
/*      */   public static int[][] appendToArray(int[][] paramArrayOfInt, int[] paramArrayOfInt1)
/*      */   {
/*  927 */     if (paramArrayOfInt1 == null) { return getDuplicate(paramArrayOfInt);
/*      */     }
/*      */     int i;
/*  930 */     if (paramArrayOfInt == null) i = 0; else
/*  931 */       i = paramArrayOfInt.length;
/*  932 */     int[][] arrayOfInt = new int[i + 1][];
/*  933 */     for (int j = 0; j < i; j++) arrayOfInt[j] = getDuplicate(paramArrayOfInt[j]);
/*  934 */     arrayOfInt[i] = getDuplicate(paramArrayOfInt1);
/*  935 */     return arrayOfInt;
/*      */   }
/*      */   
/*      */   public static int[][] appendToArray(int[][] paramArrayOfInt1, int[][] paramArrayOfInt2) {
/*  939 */     if (paramArrayOfInt1 == null) return getDuplicate(paramArrayOfInt2);
/*  940 */     if (paramArrayOfInt2 == null) { return getDuplicate(paramArrayOfInt1);
/*      */     }
/*  942 */     int i = paramArrayOfInt1.length;
/*  943 */     int j = paramArrayOfInt2.length;
/*  944 */     int[][] arrayOfInt = new int[i + j][];
/*  945 */     for (int k = 0; k < i; k++) arrayOfInt[k] = getDuplicate(paramArrayOfInt1[k]);
/*  946 */     for (k = 0; k < j; k++) arrayOfInt[(i + k)] = getDuplicate(paramArrayOfInt2[k]);
/*  947 */     return arrayOfInt;
/*      */   }
/*      */   
/*      */   public static int[] delElement(int paramInt, int[] paramArrayOfInt)
/*      */   {
/*  952 */     if (paramArrayOfInt == null) return null;
/*  953 */     int i = paramArrayOfInt.length;
/*  954 */     int[] arrayOfInt = new int[i - 1];
/*  955 */     for (int j = 0; j < paramInt; j++) arrayOfInt[j] = paramArrayOfInt[j];
/*  956 */     for (j = paramInt; j < i - 1; j++) arrayOfInt[j] = paramArrayOfInt[(j + 1)];
/*  957 */     return arrayOfInt;
/*      */   }
/*      */   
/*      */   public static int[] delValue(int paramInt, int[] paramArrayOfInt)
/*      */   {
/*  962 */     if (paramArrayOfInt == null) return null;
/*  963 */     int i = paramArrayOfInt.length;
/*  964 */     int j = 0;
/*  965 */     for (int k = 0; k < i; k++) if (paramArrayOfInt[k] == paramInt) { j++;
/*      */       }
/*  967 */     int[] arrayOfInt = new int[i - j];
/*  968 */     int m = 0;
/*  969 */     for (int n = 0; n < i; n++) if (paramArrayOfInt[n] != paramInt) arrayOfInt[(m++)] = paramArrayOfInt[n];
/*  970 */     return arrayOfInt;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public static int[] moveToSortedAryStart(int paramInt, int[] paramArrayOfInt)
/*      */   {
/*  977 */     if (!MATH.member(paramInt, paramArrayOfInt)) return null;
/*  978 */     int[] arrayOfInt = new int[paramArrayOfInt.length];
/*  979 */     for (int i = 0; i < paramArrayOfInt.length; i++) {
/*  980 */       if (paramArrayOfInt[i] < paramInt) { arrayOfInt[(i + 1)] = paramArrayOfInt[i];
/*  981 */       } else if (paramArrayOfInt[i] > paramInt) arrayOfInt[i] = paramArrayOfInt[i]; else
/*  982 */         arrayOfInt[0] = paramInt;
/*      */     }
/*  984 */     return arrayOfInt;
/*      */   }
/*      */   
/*      */   public static int[][] moveToSortedAryStart(int paramInt, int[][] paramArrayOfInt) {
/*  988 */     if (!MATH.member(paramInt, paramArrayOfInt[0])) return (int[][])null;
/*  989 */     int i = paramArrayOfInt[0].length;
/*  990 */     int[][] arrayOfInt = new int[2][i];
/*  991 */     for (int j = 0; j < i; j++) {
/*  992 */       if (paramArrayOfInt[0][j] < paramInt) {
/*  993 */         arrayOfInt[0][(j + 1)] = paramArrayOfInt[0][j];
/*  994 */         arrayOfInt[1][(j + 1)] = paramArrayOfInt[1][j];
/*      */       }
/*  996 */       else if (paramArrayOfInt[0][j] > paramInt) {
/*  997 */         arrayOfInt[0][j] = paramArrayOfInt[0][j];
/*  998 */         arrayOfInt[1][j] = paramArrayOfInt[1][j];
/*      */       }
/*      */       else {
/* 1001 */         arrayOfInt[0][0] = paramInt;
/* 1002 */         arrayOfInt[1][0] = paramArrayOfInt[1][j];
/*      */       }
/*      */     }
/* 1005 */     return arrayOfInt;
/*      */   }
/*      */   
/*      */ 
/*      */   static int[] getSequence(int paramInt1, int paramInt2)
/*      */   {
/* 1011 */     int[] arrayOfInt = new int[paramInt2];
/* 1012 */     for (int i = 0; i < paramInt2; i++) arrayOfInt[i] = (paramInt1 + i);
/* 1013 */     return arrayOfInt;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public static void busyWait(int paramInt)
/*      */   {
/* 1020 */     long l = System.currentTimeMillis();
/* 1021 */     while (System.currentTimeMillis() < l + paramInt) {}
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public static String listToStr(String paramString, int[] paramArrayOfInt)
/*      */   {
/* 1028 */     StringBuffer localStringBuffer = new StringBuffer(paramString);
/* 1029 */     if (paramArrayOfInt == null) { localStringBuffer.append("[] ");
/*      */     } else {
/* 1031 */       localStringBuffer.append("[");
/* 1032 */       for (int i = 0; i < paramArrayOfInt.length - 1; i++) localStringBuffer.append(paramArrayOfInt[i] + ",");
/* 1033 */       localStringBuffer.append(paramArrayOfInt[(paramArrayOfInt.length - 1)] + "] ");
/*      */     }
/* 1035 */     return localStringBuffer.toString();
/*      */   }
/*      */   
/* 1038 */   public static String[] listToStr(String paramString, int[] paramArrayOfInt, int paramInt) { if (paramArrayOfInt == null) {
/* 1039 */       String[] arrayOfString1 = { paramString + "[]" };return arrayOfString1;
/*      */     }
/* 1041 */     int i = paramArrayOfInt.length;
/* 1042 */     int j = i / paramInt + 2;
/* 1043 */     String[] arrayOfString2 = new String[j];
/*      */     
/* 1045 */     arrayOfString2[0] = paramString;
/* 1046 */     for (int k = 0; k < j - 1; k++) {
/* 1047 */       String str = k == 0 ? "\t" : "\t ";
/* 1048 */       if (k == 0) str = str + "[";
/* 1049 */       for (int m = 0; m < paramInt; m++) {
/* 1050 */         if (k * paramInt + m == i - 1) { str = str + paramArrayOfInt[(k * paramInt + m)] + "]";
/* 1051 */         } else { if (k * paramInt + m >= i) break; str = str + paramArrayOfInt[(k * paramInt + m)] + ",";
/*      */         }
/*      */       }
/* 1054 */       arrayOfString2[(k + 1)] = str.toString();
/*      */     }
/* 1056 */     return arrayOfString2;
/*      */   }
/*      */   
/*      */ 
/*      */   public static String listToStr(String paramString, int[][] paramArrayOfInt)
/*      */   {
/* 1062 */     StringBuffer localStringBuffer = new StringBuffer(paramString);
/* 1063 */     if (paramArrayOfInt == null) { localStringBuffer.append("[] ");
/*      */     } else {
/* 1065 */       int i = paramArrayOfInt.length;
/* 1066 */       localStringBuffer.append("[");
/* 1067 */       for (int j = 0; j < i - 1; j++) {
/* 1068 */         if (paramArrayOfInt[j] == null) {
/* 1069 */           localStringBuffer.append("[],");
/*      */         } else {
/* 1071 */           localStringBuffer.append("[");
/* 1072 */           for (int k = 0; k < paramArrayOfInt[j].length - 1; k++) localStringBuffer.append(paramArrayOfInt[j][k] + ",");
/* 1073 */           localStringBuffer.append(paramArrayOfInt[j][(paramArrayOfInt[j].length - 1)] + "],");
/*      */         }
/*      */       }
/* 1076 */       if (paramArrayOfInt[(i - 1)] == null) { localStringBuffer.append("[]]");
/*      */       } else {
/* 1078 */         localStringBuffer.append("[");
/* 1079 */         for (j = 0; j < paramArrayOfInt[(i - 1)].length - 1; j++) localStringBuffer.append(paramArrayOfInt[(i - 1)][j] + ",");
/* 1080 */         localStringBuffer.append(paramArrayOfInt[(i - 1)][(paramArrayOfInt[(i - 1)].length - 1)] + "]]");
/*      */       }
/*      */     }
/* 1083 */     return localStringBuffer.toString();
/*      */   }
/*      */   
/*      */   public static String listToStr(String paramString, Point[] paramArrayOfPoint)
/*      */   {
/* 1088 */     StringBuffer localStringBuffer = new StringBuffer(paramString);
/* 1089 */     if (paramArrayOfPoint == null) { localStringBuffer.append("[] ");
/*      */     } else {
/* 1091 */       localStringBuffer.append("[");
/* 1092 */       for (int i = 0; i < paramArrayOfPoint.length - 1; i++)
/* 1093 */         localStringBuffer.append("(" + paramArrayOfPoint[i].x + "," + paramArrayOfPoint[i].y + "),");
/* 1094 */       localStringBuffer.append("(" + paramArrayOfPoint[(paramArrayOfPoint.length - 1)].x + "," + paramArrayOfPoint[(paramArrayOfPoint.length - 1)].y + ")] ");
/*      */     }
/* 1096 */     return localStringBuffer.toString();
/*      */   }
/*      */   
/*      */   public static String[] listToStr(String paramString, Point[] paramArrayOfPoint, int paramInt)
/*      */   {
/* 1101 */     if (paramArrayOfPoint == null) {
/* 1102 */       String[] arrayOfString1 = { paramString + "[]" };return arrayOfString1;
/*      */     }
/* 1104 */     int i = paramArrayOfPoint.length;
/* 1105 */     int j = i / paramInt + 2;
/* 1106 */     String[] arrayOfString2 = new String[j];
/*      */     
/* 1108 */     arrayOfString2[0] = paramString;
/* 1109 */     for (int k = 0; k < j - 1; k++) {
/* 1110 */       String str = k == 0 ? "\t" : "\t ";
/* 1111 */       if (k == 0) str = str + "[";
/* 1112 */       for (int m = 0; m < paramInt; m++) {
/* 1113 */         if (k * paramInt + m == i - 1) { str = str + "(" + paramArrayOfPoint[(k * paramInt + m)].x + "," + paramArrayOfPoint[(k * paramInt + m)].y + ")]";
/* 1114 */         } else { if (k * paramInt + m >= i) break; str = str + "(" + paramArrayOfPoint[(k * paramInt + m)].x + "," + paramArrayOfPoint[(k * paramInt + m)].y + "),";
/*      */         }
/*      */       }
/* 1117 */       arrayOfString2[(k + 1)] = str.toString();
/*      */     }
/* 1119 */     return arrayOfString2;
/*      */   }
/*      */   
/*      */   public static String listToStr(String paramString, float[] paramArrayOfFloat)
/*      */   {
/* 1124 */     StringBuffer localStringBuffer = new StringBuffer(paramString);
/* 1125 */     if (paramArrayOfFloat == null) { localStringBuffer.append("[] ");
/*      */     } else {
/* 1127 */       localStringBuffer.append("[");
/* 1128 */       for (int i = 0; i < paramArrayOfFloat.length - 1; i++) localStringBuffer.append(paramArrayOfFloat[i] + ",");
/* 1129 */       localStringBuffer.append(paramArrayOfFloat[(paramArrayOfFloat.length - 1)] + "] ");
/*      */     }
/* 1131 */     return localStringBuffer.toString();
/*      */   }
/*      */   
/*      */   public static String listToStr(String paramString, float[][] paramArrayOfFloat) {
/* 1135 */     StringBuffer localStringBuffer = new StringBuffer(paramString);
/* 1136 */     if (paramArrayOfFloat == null) { localStringBuffer.append("[]");
/*      */     } else {
/* 1138 */       int i = paramArrayOfFloat.length;
/* 1139 */       localStringBuffer.append("[");
/* 1140 */       for (int j = 0; j < i - 1; j++) {
/* 1141 */         if (paramArrayOfFloat[j] == null) {
/* 1142 */           localStringBuffer.append("[],");
/*      */         } else {
/* 1144 */           localStringBuffer.append("[");
/* 1145 */           for (int k = 0; k < paramArrayOfFloat[j].length - 1; k++) localStringBuffer.append(paramArrayOfFloat[j][k] + ",");
/* 1146 */           localStringBuffer.append(paramArrayOfFloat[j][(paramArrayOfFloat[j].length - 1)] + "],");
/*      */         }
/*      */       }
/* 1149 */       if (paramArrayOfFloat[(i - 1)] == null) {
/* 1150 */         localStringBuffer.append("[]]");
/*      */       }
/*      */       else {
/* 1153 */         localStringBuffer.append("[");
/* 1154 */         for (j = 0; j < paramArrayOfFloat[(i - 1)].length - 1; j++) localStringBuffer.append(paramArrayOfFloat[(i - 1)][j] + ",");
/* 1155 */         localStringBuffer.append(paramArrayOfFloat[(i - 1)][(paramArrayOfFloat[(i - 1)].length - 1)] + "]]");
/*      */       }
/*      */     }
/* 1158 */     return localStringBuffer.toString();
/*      */   }
/*      */   
/* 1161 */   public static String[] listToStr(String paramString, float[][] paramArrayOfFloat, int paramInt) { if (paramArrayOfFloat == null) {
/* 1162 */       String[] arrayOfString1 = { paramString + "[]" };return arrayOfString1;
/*      */     }
/* 1164 */     int i = paramArrayOfFloat.length;
/* 1165 */     int j = i / paramInt + 2;
/* 1166 */     String[] arrayOfString2 = new String[j];
/*      */     
/* 1168 */     arrayOfString2[0] = paramString;
/* 1169 */     for (int k = 0; k < j - 1; k++) {
/* 1170 */       String str = k == 0 ? "\t" : "\t ";
/* 1171 */       if (k == 0) str = str + "[";
/* 1172 */       for (int m = 0; m < paramInt; m++) { int n;
/* 1173 */         if (k * paramInt + m == i - 1) {
/* 1174 */           str = str + "[";
/* 1175 */           for (n = 0; n < paramArrayOfFloat[(k * paramInt + m)].length - 1; n++) str = str + paramArrayOfFloat[(k * paramInt + m)][n] + ",";
/* 1176 */           str = str + paramArrayOfFloat[(k * paramInt + m)][(paramArrayOfFloat[(k * paramInt + m)].length - 1)] + "]]";
/*      */         } else {
/* 1178 */           if (k * paramInt + m >= i) break;
/* 1179 */           str = str + "[";
/* 1180 */           for (n = 0; n < paramArrayOfFloat[(k * paramInt + m)].length - 1; n++) str = str + paramArrayOfFloat[(k * paramInt + m)][n] + ",";
/* 1181 */           str = str + paramArrayOfFloat[(k * paramInt + m)][(paramArrayOfFloat[(k * paramInt + m)].length - 1)] + "],";
/*      */         }
/*      */       }
/*      */       
/* 1185 */       arrayOfString2[(k + 1)] = str.toString();
/*      */     }
/* 1187 */     return arrayOfString2;
/*      */   }
/*      */   
/*      */   public static String listToStr(String paramString, double[] paramArrayOfDouble)
/*      */   {
/* 1192 */     StringBuffer localStringBuffer = new StringBuffer(paramString);
/* 1193 */     if (paramArrayOfDouble == null) { localStringBuffer.append("[] ");
/*      */     } else {
/* 1195 */       localStringBuffer.append("[");
/* 1196 */       for (int i = 0; i < paramArrayOfDouble.length - 1; i++) localStringBuffer.append(paramArrayOfDouble[i] + ",");
/* 1197 */       localStringBuffer.append(paramArrayOfDouble[(paramArrayOfDouble.length - 1)] + "] ");
/*      */     }
/* 1199 */     return localStringBuffer.toString();
/*      */   }
/*      */   
/*      */   public static String listToStr(String paramString, String[] paramArrayOfString)
/*      */   {
/* 1204 */     StringBuffer localStringBuffer = new StringBuffer(paramString);
/* 1205 */     if (paramArrayOfString == null) { localStringBuffer.append("[] ");
/*      */     } else {
/* 1207 */       localStringBuffer.append("[");
/* 1208 */       for (int i = 0; i < paramArrayOfString.length - 1; i++) localStringBuffer.append(paramArrayOfString[i] + ",");
/* 1209 */       localStringBuffer.append(paramArrayOfString[(paramArrayOfString.length - 1)] + "] ");
/*      */     }
/* 1211 */     return localStringBuffer.toString();
/*      */   }
/*      */   
/* 1214 */   public static String[] listToStr(String paramString, String[] paramArrayOfString, int paramInt) { if (paramArrayOfString == null) {
/* 1215 */       String[] arrayOfString1 = { paramString + "[]" };return arrayOfString1;
/*      */     }
/* 1217 */     int i = paramArrayOfString.length;
/* 1218 */     int j = i / paramInt + 2;
/* 1219 */     String[] arrayOfString2 = new String[j];
/*      */     
/* 1221 */     arrayOfString2[0] = paramString;
/* 1222 */     for (int k = 0; k < j - 1; k++) {
/* 1223 */       String str = k == 0 ? "\t" : "\t ";
/* 1224 */       if (k == 0) str = str + "[";
/* 1225 */       for (int m = 0; m < paramInt; m++) {
/* 1226 */         if (k * paramInt + m == i - 1) { str = str + paramArrayOfString[(k * paramInt + m)] + "]";
/* 1227 */         } else { if (k * paramInt + m >= i) break; str = str + paramArrayOfString[(k * paramInt + m)] + ",";
/*      */         }
/*      */       }
/* 1230 */       arrayOfString2[(k + 1)] = str.toString();
/*      */     }
/* 1232 */     return arrayOfString2;
/*      */   }
/*      */   
/*      */   public static String listToStr(String paramString, boolean[] paramArrayOfBoolean)
/*      */   {
/* 1237 */     StringBuffer localStringBuffer = new StringBuffer(paramString);
/* 1238 */     if (paramArrayOfBoolean == null) { localStringBuffer.append("[] ");
/*      */     } else {
/* 1240 */       localStringBuffer.append("[");
/* 1241 */       for (int i = 0; i < paramArrayOfBoolean.length - 1; i++) localStringBuffer.append(paramArrayOfBoolean[i] + ",");
/* 1242 */       localStringBuffer.append(paramArrayOfBoolean[(paramArrayOfBoolean.length - 1)] + "] ");
/*      */     }
/* 1244 */     return localStringBuffer.toString();
/*      */   }
/*      */   
/*      */   public static String floatToStr(float paramFloat, int paramInt)
/*      */   {
/* 1249 */     float f = paramFloat;
/* 1250 */     for (int i = 0; i < paramInt - 2; i++) f *= 10.0F;
/* 1251 */     if (f < 1.0F) {
/* 1252 */       String str = "0.0000000000";
/* 1253 */       return str.substring(0, paramInt);
/*      */     }
/* 1255 */     return Float.toString(paramFloat).substring(0, paramInt);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public static String[] pointToStrAry(Point paramPoint)
/*      */   {
/* 1262 */     String[] arrayOfString = new String[2];
/* 1263 */     arrayOfString[0] = new String("" + paramPoint.x);
/* 1264 */     arrayOfString[1] = new String("" + paramPoint.y);
/* 1265 */     return arrayOfString;
/*      */   }
/*      */   
/*      */   public static String[] intToStrAry(int paramInt)
/*      */   {
/* 1270 */     String[] arrayOfString = new String[1];
/* 1271 */     arrayOfString[0] = new String("" + paramInt);
/* 1272 */     return arrayOfString;
/*      */   }
/*      */   
/*      */   public static String[] boolToStrAry(boolean paramBoolean)
/*      */   {
/* 1277 */     String[] arrayOfString = new String[1];
/* 1278 */     if (paramBoolean) arrayOfString[0] = new String("1"); else
/* 1279 */       arrayOfString[0] = new String("0");
/* 1280 */     return arrayOfString;
/*      */   }
/*      */   
/*      */   public static Point strAryToPoint(String[] paramArrayOfString)
/*      */   {
/* 1285 */     return new Point(Integer.parseInt(paramArrayOfString[0]), Integer.parseInt(paramArrayOfString[1]));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1292 */   public static void showList(String paramString, int[] paramArrayOfInt) { System.out.println(listToStr(paramString, paramArrayOfInt)); }
/*      */   
/*      */   public static void showList(String paramString, int[] paramArrayOfInt, int paramInt) {
/* 1295 */     String[] arrayOfString = listToStr(paramString, paramArrayOfInt, paramInt);
/* 1296 */     for (int i = 0; i < arrayOfString.length; i++) System.out.println(arrayOfString[i]);
/*      */   }
/*      */   
/*      */   public static void showList(String paramString, int[][] paramArrayOfInt)
/*      */   {
/* 1301 */     System.out.println(listToStr(paramString, paramArrayOfInt));
/*      */   }
/*      */   
/*      */   public static void showList(String paramString, Point[] paramArrayOfPoint)
/*      */   {
/* 1306 */     System.out.println(listToStr(paramString, paramArrayOfPoint));
/*      */   }
/*      */   
/*      */   public static void showList(String paramString, Point[] paramArrayOfPoint, int paramInt) {
/* 1310 */     String[] arrayOfString = listToStr(paramString, paramArrayOfPoint, paramInt);
/* 1311 */     for (int i = 0; i < arrayOfString.length; i++) { System.out.println(arrayOfString[i]);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static void showList(String paramString, float[] paramArrayOfFloat)
/*      */   {
/* 1322 */     System.out.println(listToStr(paramString, paramArrayOfFloat));
/*      */   }
/*      */   
/* 1325 */   public static void showList(String paramString, float[][] paramArrayOfFloat) { System.out.println(listToStr(paramString, paramArrayOfFloat)); }
/*      */   
/*      */   public static void showList(String paramString, float[][] paramArrayOfFloat, int paramInt) {
/* 1328 */     String[] arrayOfString = listToStr(paramString, paramArrayOfFloat, paramInt);
/* 1329 */     for (int i = 0; i < arrayOfString.length; i++) System.out.println(arrayOfString[i]);
/*      */   }
/*      */   
/*      */   public static void showList(String paramString, double[] paramArrayOfDouble)
/*      */   {
/* 1334 */     System.out.println(listToStr(paramString, paramArrayOfDouble));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/* 1340 */   public static void showList(String paramString, String[] paramArrayOfString) { System.out.println(listToStr(paramString, paramArrayOfString)); }
/*      */   
/*      */   public static void showList(String paramString, String[] paramArrayOfString, int paramInt) {
/* 1343 */     String[] arrayOfString = listToStr(paramString, paramArrayOfString, paramInt);
/* 1344 */     for (int i = 0; i < arrayOfString.length; i++) System.out.println(arrayOfString[i]);
/*      */   }
/*      */   
/*      */   public static void showList(String paramString, String[][] paramArrayOfString)
/*      */   {
/* 1349 */     System.out.println(paramString);
/* 1350 */     for (int i = 0; i < paramArrayOfString.length; i++) System.out.println(listToStr("", paramArrayOfString[i]));
/*      */   }
/*      */   
/*      */   public static void showList(String paramString, boolean[] paramArrayOfBoolean)
/*      */   {
/* 1355 */     System.out.println(listToStr(paramString, paramArrayOfBoolean));
/*      */   }
/*      */   
/*      */   public static void saveList(String paramString, float[] paramArrayOfFloat)
/*      */   {
/*      */     try {
/* 1361 */       PrintWriter localPrintWriter = new PrintWriter(new FileWriter("jk"));
/* 1362 */       localPrintWriter.println(paramString);
/* 1363 */       for (int i = 0; i < paramArrayOfFloat.length; i++) localPrintWriter.println(" " + paramArrayOfFloat[i]);
/* 1364 */       localPrintWriter.close();
/* 1365 */     } catch (IOException localIOException) { HelpPanel.showError("Unable to save file jk.");
/*      */     }
/*      */   }
/*      */   
/* 1369 */   public static void saveList(String paramString, int[] paramArrayOfInt) { try { PrintWriter localPrintWriter = new PrintWriter(new FileWriter("jk"));
/* 1370 */       localPrintWriter.println(paramString);
/* 1371 */       for (int i = 0; i < paramArrayOfInt.length; i++) localPrintWriter.println(" " + paramArrayOfInt[i]);
/* 1372 */       localPrintWriter.close();
/* 1373 */     } catch (IOException localIOException) { HelpPanel.showError("Unable to save file jk.");
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   public static int readInt()
/*      */   {
/* 1380 */     BufferedReader localBufferedReader = new BufferedReader(new InputStreamReader(System.in));
/* 1381 */     return loadInt(localBufferedReader);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static Point readPoint()
/*      */   {
/* 1392 */     BufferedReader localBufferedReader = new BufferedReader(new InputStreamReader(System.in));
/* 1393 */     return loadPoint(localBufferedReader);
/*      */   }
/*      */ }


/* Location:              /home/anali/INAOE/tesis/codigos/topub/exe/ww4.jar!/Network/UTIL.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */