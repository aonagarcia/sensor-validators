/*     */ package Network;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintWriter;
/*     */ import java.util.StringTokenizer;
/*     */ 
/*     */ public class LnEnv
/*     */ {
/*     */   String iFile;
/*     */   String iDir;
/*     */   int maxLk;
/*     */   int lk;
/*     */   int mCq;
/*     */   int pblen;
/*     */   float coeff;
/*     */   boolean stop;
/*     */   boolean usePat;
/*     */   boolean patSv;
/*     */   boolean loadAmi;
/*     */   
/*     */   public LnEnv()
/*     */   {
/*  24 */     this.iFile = new String("");
/*  25 */     this.iDir = new String("");
/*  26 */     this.maxLk = 1;
/*  27 */     this.lk = 1;
/*  28 */     this.mCq = 7;
/*  29 */     this.pblen = 16;
/*  30 */     this.coeff = 1.44F;
/*  31 */     this.stop = true;
/*  32 */     this.usePat = false;
/*  33 */     this.patSv = true;
/*  34 */     this.loadAmi = false;
/*     */   }
/*     */   
/*     */ 
/*     */   public String[] getLabels()
/*     */   {
/*  40 */     String[] arrayOfString = new String[9];
/*  41 */     arrayOfString[0] = new String("Max\t# of lookahead links");
/*  42 */     arrayOfString[1] = new String("Current # of lookahead links");
/*  43 */     arrayOfString[2] = new String("Max\tclique size");
/*  44 */     arrayOfString[3] = new String("Bits per probability value");
/*  45 */     arrayOfString[4] = new String("Model DL coefficient");
/*  46 */     arrayOfString[5] = new String("Stop at each pass");
/*  47 */     arrayOfString[6] = new String("Continue from saved\tstructure");
/*  48 */     arrayOfString[7] = new String("Save structure after each pass");
/*  49 */     arrayOfString[8] = new String("Load avg mutual info from file");
/*  50 */     return arrayOfString;
/*     */   }
/*     */   
/*     */   public String[] getValues() {
/*  54 */     String[] arrayOfString = new String[9];
/*  55 */     arrayOfString[0] = new String(this.maxLk + "");
/*  56 */     arrayOfString[1] = new String(this.lk + "");
/*  57 */     arrayOfString[2] = new String(this.mCq + "");
/*  58 */     arrayOfString[3] = new String(this.pblen + "");
/*  59 */     arrayOfString[4] = new String(this.coeff + "");
/*  60 */     arrayOfString[5] = new String((this.stop ? 1 : 0) + "");
/*  61 */     arrayOfString[6] = new String((this.usePat ? 1 : 0) + "");
/*  62 */     arrayOfString[7] = new String((this.patSv ? 1 : 0) + "");
/*  63 */     arrayOfString[8] = new String((this.loadAmi ? 1 : 0) + "");
/*  64 */     return arrayOfString;
/*     */   }
/*     */   
/*  67 */   public void setValues(String[] paramArrayOfString) { setmaxLk(paramArrayOfString[0]);
/*  68 */     setlk(paramArrayOfString[1]);
/*  69 */     setmCq(paramArrayOfString[2]);
/*  70 */     setpblen(paramArrayOfString[3]);
/*  71 */     setcoeff(paramArrayOfString[4]);
/*  72 */     setstop(paramArrayOfString[5]);
/*  73 */     setusePat(paramArrayOfString[6]);
/*  74 */     setpatSv(paramArrayOfString[7]);
/*  75 */     setloadAmi(paramArrayOfString[8]);
/*     */   }
/*     */   
/*     */ 
/*     */   public void setiFile(String paramString)
/*     */   {
/*  81 */     this.iFile = new String(paramString);
/*     */   }
/*     */   
/*  84 */   public String getiFile() { return new String(this.iFile); }
/*     */   
/*     */   public void setiDir(String paramString)
/*     */   {
/*  88 */     this.iDir = new String(paramString);
/*     */   }
/*     */   
/*  91 */   public String getiDir() { return new String(this.iDir); }
/*     */   
/*     */   public String getPath()
/*     */   {
/*  95 */     return new String(this.iDir + this.iFile);
/*     */   }
/*     */   
/*     */   void setmaxLk(String paramString) {
/*  99 */     this.maxLk = Integer.parseInt(paramString);
/*     */   }
/*     */   
/* 102 */   int getmaxLk() { return this.maxLk; }
/*     */   
/*     */   void setlk(String paramString)
/*     */   {
/* 106 */     this.lk = Integer.parseInt(paramString);
/*     */   }
/*     */   
/* 109 */   public void setlk(int paramInt) { this.lk = paramInt; }
/*     */   
/*     */   public int getlk() {
/* 112 */     return this.lk;
/*     */   }
/*     */   
/*     */   void setmCq(String paramString) {
/* 116 */     this.mCq = Integer.parseInt(paramString);
/*     */   }
/*     */   
/* 119 */   int getmCq() { return this.mCq; }
/*     */   
/*     */   void setpblen(String paramString)
/*     */   {
/* 123 */     this.pblen = Integer.parseInt(paramString);
/*     */   }
/*     */   
/* 126 */   int getpblen() { return this.pblen; }
/*     */   
/*     */   void setcoeff(String paramString)
/*     */   {
/* 130 */     this.coeff = Float.parseFloat(paramString);
/*     */   }
/*     */   
/* 133 */   float getcoeff() { return this.coeff; }
/*     */   
/*     */   void setstop(boolean paramBoolean)
/*     */   {
/* 137 */     this.stop = paramBoolean;
/*     */   }
/*     */   
/* 140 */   void setstop(String paramString) { this.stop = (Integer.parseInt(paramString) == 1); }
/*     */   
/*     */   boolean getstop() {
/* 143 */     return this.stop;
/*     */   }
/*     */   
/*     */   void setusePat(boolean paramBoolean) {
/* 147 */     this.usePat = paramBoolean;
/*     */   }
/*     */   
/* 150 */   void setusePat(String paramString) { this.usePat = (Integer.parseInt(paramString) == 1); }
/*     */   
/*     */   public boolean getusePat() {
/* 153 */     return this.usePat;
/*     */   }
/*     */   
/*     */   void setpatSv(boolean paramBoolean) {
/* 157 */     this.patSv = paramBoolean;
/*     */   }
/*     */   
/* 160 */   void setpatSv(String paramString) { this.patSv = (Integer.parseInt(paramString) == 1); }
/*     */   
/*     */   boolean getpatSv() {
/* 163 */     return this.patSv;
/*     */   }
/*     */   
/*     */   void setloadAmi(boolean paramBoolean) {
/* 167 */     this.loadAmi = paramBoolean;
/*     */   }
/*     */   
/* 170 */   void setloadAmi(String paramString) { this.loadAmi = (Integer.parseInt(paramString) == 1); }
/*     */   
/*     */   boolean getloadAmi() {
/* 173 */     return this.loadAmi;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void save(PrintWriter paramPrintWriter)
/*     */   {
/* 180 */     paramPrintWriter.println("iFile=\t" + this.iFile);
/* 181 */     paramPrintWriter.println("maxLk=\t" + this.maxLk);
/* 182 */     paramPrintWriter.println("lk= " + this.lk);
/* 183 */     paramPrintWriter.println("mCq= " + this.mCq);
/* 184 */     paramPrintWriter.println("pblen=\t" + this.pblen);
/* 185 */     paramPrintWriter.println("coeff=\t" + this.coeff);
/* 186 */     paramPrintWriter.println("stop= " + this.stop);
/* 187 */     paramPrintWriter.println("usePat= " + this.usePat);
/* 188 */     paramPrintWriter.println("patSv=\t" + this.patSv);
/* 189 */     paramPrintWriter.println("loadAmi= " + this.loadAmi);
/*     */   }
/*     */   
/*     */   public void load(BufferedReader paramBufferedReader)
/*     */   {
/*     */     try {
/* 195 */       StringTokenizer localStringTokenizer = new StringTokenizer(paramBufferedReader.readLine());
/* 196 */       localStringTokenizer.nextToken();
/* 197 */       this.iFile = new String(localStringTokenizer.nextToken());
/*     */       
/* 199 */       localStringTokenizer = new StringTokenizer(paramBufferedReader.readLine());
/* 200 */       localStringTokenizer.nextToken();
/* 201 */       this.maxLk = Integer.parseInt(localStringTokenizer.nextToken());
/*     */       
/* 203 */       localStringTokenizer = new StringTokenizer(paramBufferedReader.readLine());
/* 204 */       localStringTokenizer.nextToken();
/* 205 */       this.lk = Integer.parseInt(localStringTokenizer.nextToken());
/*     */       
/* 207 */       localStringTokenizer = new StringTokenizer(paramBufferedReader.readLine());
/* 208 */       localStringTokenizer.nextToken();
/* 209 */       this.mCq = Integer.parseInt(localStringTokenizer.nextToken());
/*     */       
/* 211 */       localStringTokenizer = new StringTokenizer(paramBufferedReader.readLine());
/* 212 */       localStringTokenizer.nextToken();
/* 213 */       this.pblen = Integer.parseInt(localStringTokenizer.nextToken());
/*     */       
/* 215 */       localStringTokenizer = new StringTokenizer(paramBufferedReader.readLine());
/* 216 */       localStringTokenizer.nextToken();
/* 217 */       this.coeff = Float.parseFloat(localStringTokenizer.nextToken());
/*     */       
/* 219 */       localStringTokenizer = new StringTokenizer(paramBufferedReader.readLine());
/* 220 */       localStringTokenizer.nextToken();
/* 221 */       this.stop = (localStringTokenizer.nextToken().equals("true"));
/*     */       
/* 223 */       localStringTokenizer = new StringTokenizer(paramBufferedReader.readLine());
/* 224 */       localStringTokenizer.nextToken();
/* 225 */       this.usePat = (localStringTokenizer.nextToken().equals("true"));
/*     */       
/* 227 */       localStringTokenizer = new StringTokenizer(paramBufferedReader.readLine());
/* 228 */       localStringTokenizer.nextToken();
/* 229 */       this.patSv = (localStringTokenizer.nextToken().equals("true"));
/*     */       
/* 231 */       localStringTokenizer = new StringTokenizer(paramBufferedReader.readLine());
/* 232 */       localStringTokenizer.nextToken();
/* 233 */       this.loadAmi = (localStringTokenizer.nextToken().equals("true"));
/*     */     }
/*     */     catch (IOException localIOException) {
/* 236 */       HelpPanel.showError("Unable to read env file!");
/*     */     }
/*     */   }
/*     */ }


/* Location:              /home/anali/INAOE/tesis/codigos/topub/exe/ww4.jar!/Network/LnEnv.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */