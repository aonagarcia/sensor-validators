/*     */ package Network;
/*     */ 
/*     */ import java.util.StringTokenizer;
/*     */ 
/*     */ public class BnLnEnv
/*     */ {
/*     */   String iFile;
/*     */   String iDir;
/*     */   float dhFlr;
/*     */   boolean stop;
/*     */   boolean patSv;
/*     */   int mOrder;
/*     */   
/*     */   public BnLnEnv()
/*     */   {
/*  16 */     this.iFile = new String("");
/*  17 */     this.iDir = new String("");
/*  18 */     this.dhFlr = 0.003F;
/*  19 */     this.stop = false;
/*  20 */     this.patSv = false;
/*  21 */     this.mOrder = 1;
/*     */   }
/*     */   
/*     */ 
/*     */   public String[] getLabels()
/*     */   {
/*  27 */     String[] arrayOfString = new String[4];
/*  28 */     arrayOfString[0] = new String("Entropy decrement threshold");
/*  29 */     arrayOfString[1] = new String("Stop at each pass");
/*  30 */     arrayOfString[2] = new String("Save structure after each pass");
/*  31 */     arrayOfString[3] = new String("Max\torder of cond. independency");
/*  32 */     return arrayOfString;
/*     */   }
/*     */   
/*     */   public String[] getValues() {
/*  36 */     String[] arrayOfString = new String[4];
/*  37 */     arrayOfString[0] = new String(this.dhFlr + "");
/*  38 */     arrayOfString[1] = new String((this.stop ? 1 : 0) + "");
/*  39 */     arrayOfString[2] = new String((this.patSv ? 1 : 0) + "");
/*  40 */     arrayOfString[3] = new String(this.mOrder + "");
/*  41 */     return arrayOfString;
/*     */   }
/*     */   
/*  44 */   public void setValues(String[] paramArrayOfString) { setdhFlr(paramArrayOfString[0]);
/*  45 */     setstop(paramArrayOfString[1]);
/*  46 */     setpatSv(paramArrayOfString[2]);
/*  47 */     setmOrder(paramArrayOfString[3]);
/*     */   }
/*     */   
/*     */ 
/*     */   public void setiFile(String paramString)
/*     */   {
/*  53 */     this.iFile = new String(paramString);
/*     */   }
/*     */   
/*  56 */   public String getiFile() { return new String(this.iFile); }
/*     */   
/*     */   public void setiDir(String paramString)
/*     */   {
/*  60 */     this.iDir = new String(paramString);
/*     */   }
/*     */   
/*  63 */   public String getiDir() { return new String(this.iDir); }
/*     */   
/*     */   public String getPath()
/*     */   {
/*  67 */     return new String(this.iDir + this.iFile);
/*     */   }
/*     */   
/*     */   void setdhFlr(String paramString) {
/*  71 */     Float localFloat = Float.valueOf(paramString);
/*  72 */     this.dhFlr = localFloat.floatValue();
/*     */   }
/*     */   
/*  75 */   float getdhFlr() { return this.dhFlr; }
/*     */   
/*     */   void setstop(boolean paramBoolean)
/*     */   {
/*  79 */     this.stop = paramBoolean;
/*     */   }
/*     */   
/*  82 */   void setstop(String paramString) { this.stop = (Integer.parseInt(paramString) == 1); }
/*     */   
/*     */   boolean getstop() {
/*  85 */     return this.stop;
/*     */   }
/*     */   
/*     */   void setpatSv(boolean paramBoolean) {
/*  89 */     this.patSv = paramBoolean;
/*     */   }
/*     */   
/*  92 */   void setpatSv(String paramString) { this.patSv = (Integer.parseInt(paramString) == 1); }
/*     */   
/*     */   boolean getpatSv() {
/*  95 */     return this.patSv;
/*     */   }
/*     */   
/*     */   void setmOrder(String paramString) {
/*  99 */     this.mOrder = Integer.parseInt(paramString);
/*     */   }
/*     */   
/* 102 */   int getmOrder() { return this.mOrder; }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void save(java.io.PrintWriter paramPrintWriter)
/*     */   {
/* 109 */     paramPrintWriter.println("iFile=\t" + this.iFile);
/* 110 */     paramPrintWriter.println("dhFlr=\t" + this.dhFlr);
/* 111 */     paramPrintWriter.println("stop= " + this.stop);
/* 112 */     paramPrintWriter.println("patSv=\t" + this.patSv);
/* 113 */     paramPrintWriter.println("mOrder= " + this.mOrder);
/*     */   }
/*     */   
/*     */   public void load(java.io.BufferedReader paramBufferedReader)
/*     */   {
/*     */     try {
/* 119 */       StringTokenizer localStringTokenizer = new StringTokenizer(paramBufferedReader.readLine());
/* 120 */       localStringTokenizer.nextToken();
/* 121 */       this.iFile = new String(localStringTokenizer.nextToken());
/*     */       
/* 123 */       localStringTokenizer = new StringTokenizer(paramBufferedReader.readLine());
/* 124 */       localStringTokenizer.nextToken();
/* 125 */       Float localFloat = Float.valueOf(localStringTokenizer.nextToken());
/* 126 */       this.dhFlr = localFloat.floatValue();
/*     */       
/* 128 */       localStringTokenizer = new StringTokenizer(paramBufferedReader.readLine());
/* 129 */       localStringTokenizer.nextToken();
/* 130 */       this.stop = (localStringTokenizer.nextToken().equals("true"));
/*     */       
/* 132 */       localStringTokenizer = new StringTokenizer(paramBufferedReader.readLine());
/* 133 */       localStringTokenizer.nextToken();
/* 134 */       this.patSv = (localStringTokenizer.nextToken().equals("true"));
/*     */       
/* 136 */       localStringTokenizer = new StringTokenizer(paramBufferedReader.readLine());
/* 137 */       localStringTokenizer.nextToken();
/* 138 */       this.mOrder = Integer.parseInt(localStringTokenizer.nextToken());
/*     */     }
/*     */     catch (java.io.IOException localIOException) {
/* 141 */       HelpPanel.showError("Unable to read env file!");
/*     */     }
/*     */   }
/*     */ }


/* Location:              /home/anali/INAOE/tesis/codigos/topub/exe/ww4.jar!/Network/BnLnEnv.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */