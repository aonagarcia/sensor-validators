/*     */ package Network;
/*     */ 
/*     */ import java.io.PrintStream;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class MsgBox
/*     */ {
/*  11 */   private String thisHost = null; private String destHost = null;
/*  12 */   private int thisPort = 0; private int destPort = 0;
/*  13 */   private int outMsgType = 0;
/*  14 */   private String[] inBody = null; private String[] outBody = null;
/*  15 */   private boolean recved = false;
/*     */   
/*     */ 
/*     */   MsgBox(String paramString1, int paramInt1, String paramString2, int paramInt2)
/*     */   {
/*  20 */     setHost(paramString1);setPort(paramInt1);
/*  21 */     setDestHost(paramString2);setDestPort(paramInt2);
/*     */   }
/*     */   
/*     */   void reset()
/*     */   {
/*  26 */     this.outMsgType = 0;this.recved = false;
/*  27 */     this.inBody = null;this.outBody = null;
/*     */   }
/*     */   
/*     */   void setHost(String paramString) {
/*  31 */     this.thisHost = new String(paramString);
/*     */   }
/*     */   
/*     */   void setDestHost(String paramString) {
/*  35 */     this.destHost = new String(paramString);
/*     */   }
/*     */   
/*     */   void setPort(int paramInt) {
/*  39 */     this.thisPort = paramInt;
/*     */   }
/*     */   
/*     */   void setDestPort(int paramInt) {
/*  43 */     this.destPort = paramInt;
/*     */   }
/*     */   
/*     */   void setDumbInBody(int paramInt)
/*     */   {
/*  48 */     if (paramInt == 0) {
/*  49 */       setEmptyInBody();return;
/*     */     }
/*  51 */     this.inBody = new String[paramInt];
/*     */   }
/*     */   
/*     */   void setDumbOutBody(int paramInt)
/*     */   {
/*  56 */     if (paramInt == 0) {
/*  57 */       setEmptyOutBody();return;
/*     */     }
/*  59 */     this.outBody = new String[paramInt];
/*     */   }
/*     */   
/*     */   void setInBody(String paramString, int paramInt)
/*     */   {
/*  64 */     this.inBody[paramInt] = new String(paramString);
/*     */   }
/*     */   
/*     */   void setOutBody(String paramString, int paramInt)
/*     */   {
/*  69 */     this.outBody[paramInt] = new String(paramString);
/*     */   }
/*     */   
/*     */   void setOutBody(String[] paramArrayOfString) {
/*  73 */     if (paramArrayOfString == null) {
/*  74 */       setEmptyOutBody();return;
/*     */     }
/*  76 */     this.outBody = new String[paramArrayOfString.length];
/*  77 */     for (int i = 0; i < paramArrayOfString.length; i++) setOutBody(paramArrayOfString[i], i);
/*     */   }
/*     */   
/*     */   void setEmptyInBody()
/*     */   {
/*  82 */     this.inBody = null;
/*     */   }
/*     */   
/*     */   void setEmptyOutBody()
/*     */   {
/*  87 */     this.outBody = null;
/*     */   }
/*     */   
/*     */   void setOutMsgType(int paramInt) {
/*  91 */     this.outMsgType = paramInt;
/*     */   }
/*     */   
/*     */   void setRecved(boolean paramBoolean) {
/*  95 */     this.recved = paramBoolean;
/*     */   }
/*     */   
/*     */   String getHost() {
/*  99 */     return this.thisHost;
/*     */   }
/*     */   
/*     */   String getDestHost() {
/* 103 */     return this.destHost;
/*     */   }
/*     */   
/*     */   int getPort() {
/* 107 */     return this.thisPort;
/*     */   }
/*     */   
/*     */   int getDestPort() {
/* 111 */     return this.destPort;
/*     */   }
/*     */   
/*     */   String[] getInBody()
/*     */   {
/* 116 */     if (this.inBody == null) return null;
/* 117 */     int i = this.inBody.length;
/* 118 */     String[] arrayOfString = new String[i];
/* 119 */     for (int j = 0; j < i; j++) arrayOfString[j] = new String(this.inBody[j]);
/* 120 */     return arrayOfString;
/*     */   }
/*     */   
/*     */   String getInBody(int paramInt) {
/* 124 */     return new String(this.inBody[paramInt]);
/*     */   }
/*     */   
/*     */   String[] getOutBody() {
/* 128 */     if (this.outBody == null) return null;
/* 129 */     int i = this.outBody.length;
/* 130 */     String[] arrayOfString = new String[i];
/* 131 */     for (int j = 0; j < i; j++) arrayOfString[j] = new String(this.outBody[j]);
/* 132 */     return arrayOfString;
/*     */   }
/*     */   
/*     */   String getOutBody(int paramInt) {
/* 136 */     return new String(this.outBody[paramInt]);
/*     */   }
/*     */   
/*     */   int getInBodyCount() {
/* 140 */     if (this.inBody == null) return 0;
/* 141 */     return this.inBody.length;
/*     */   }
/*     */   
/*     */   int getOutBodyCount() {
/* 145 */     if (this.outBody == null) return 0;
/* 146 */     return this.outBody.length;
/*     */   }
/*     */   
/*     */   int getOutMsgType() {
/* 150 */     return this.outMsgType;
/*     */   }
/*     */   
/*     */   boolean isRecved() {
/* 154 */     return this.recved;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   void setListOutBody(int[] paramArrayOfInt)
/*     */   {
/* 161 */     int i = paramArrayOfInt.length;
/* 162 */     int j = (i + 4) / 5;
/* 163 */     setDumbOutBody(j);
/*     */     
/* 165 */     int k = 0;
/* 166 */     for (int m = 0; m < j; m++) {
/* 167 */       String str = new String("");
/* 168 */       for (int n = 0; n < 5; n++) {
/* 169 */         str = new String(str + " " + paramArrayOfInt[(k++)]);
/* 170 */         if (k == i) break;
/*     */       }
/* 172 */       setOutBody(str, m);
/*     */     }
/*     */   }
/*     */   
/* 176 */   void setListOutBody(float[] paramArrayOfFloat) { int i = paramArrayOfFloat.length;
/* 177 */     int j = (i + 4) / 5;
/* 178 */     setDumbOutBody(j);
/*     */     
/* 180 */     int k = 0;
/* 181 */     for (int m = 0; m < j; m++) {
/* 182 */       String str = new String("");
/* 183 */       for (int n = 0; n < 5; n++) {
/* 184 */         str = new String(str + " " + paramArrayOfFloat[(k++)]);
/* 185 */         if (k == i) break;
/*     */       }
/* 187 */       setOutBody(str, m);
/*     */     }
/*     */   }
/*     */   
/*     */   void setListOutBody(float[][] paramArrayOfFloat) {
/* 192 */     int i = 0;
/* 193 */     for (int j = 0; j < paramArrayOfFloat.length; j++) {
/* 194 */       k = paramArrayOfFloat[j].length;
/* 195 */       i += (k + 4) / 5;
/*     */     }
/* 197 */     setDumbOutBody(i);
/*     */     
/* 199 */     j = 0;
/* 200 */     for (int k = 0; k < paramArrayOfFloat.length; k++) {
/* 201 */       int m = paramArrayOfFloat[k].length;
/* 202 */       int n = (m + 4) / 5;
/* 203 */       int i1 = 0;
/* 204 */       for (int i2 = 0; i2 < n; i2++) {
/* 205 */         String str = new String("");
/* 206 */         for (int i3 = 0; i3 < 5; i3++) {
/* 207 */           str = new String(str + " " + paramArrayOfFloat[k][(i1++)]);
/* 208 */           if (i1 == m) break;
/*     */         }
/* 210 */         setOutBody(str, j + i2);
/*     */       }
/*     */       
/* 213 */       j += n;
/*     */     }
/*     */   }
/*     */   
/*     */   int[] getInBodyIntList()
/*     */   {
/* 219 */     int i = getInBodyCount();
/* 220 */     if (i == 0) { return null;
/*     */     }
/* 222 */     int[] arrayOfInt1 = new int[i * 5];
/* 223 */     int j = 0;
/* 224 */     for (int k = 0; k < i; k++) {
/* 225 */       int[] arrayOfInt3 = UTIL.getIntListPure(getInBody(k));
/* 226 */       for (int n = 0; n < arrayOfInt3.length; n++) { arrayOfInt1[(j++)] = arrayOfInt3[n];
/*     */       }
/*     */     }
/* 229 */     int[] arrayOfInt2 = new int[j];
/* 230 */     for (int m = 0; m < j; m++) arrayOfInt2[m] = arrayOfInt1[m];
/* 231 */     return arrayOfInt2;
/*     */   }
/*     */   
/*     */   float[] getInBodyRealList()
/*     */   {
/* 236 */     int i = getInBodyCount();
/* 237 */     if (i == 0) { return null;
/*     */     }
/* 239 */     float[] arrayOfFloat1 = new float[i * 5];
/* 240 */     int j = 0;
/* 241 */     for (int k = 0; k < i; k++) {
/* 242 */       float[] arrayOfFloat3 = UTIL.getRealList(getInBody(k));
/* 243 */       for (int n = 0; n < arrayOfFloat3.length; n++) { arrayOfFloat1[(j++)] = arrayOfFloat3[n];
/*     */       }
/*     */     }
/* 246 */     float[] arrayOfFloat2 = new float[j];
/* 247 */     for (int m = 0; m < j; m++) arrayOfFloat2[m] = arrayOfFloat1[m];
/* 248 */     return arrayOfFloat2;
/*     */   }
/*     */   
/*     */ 
/*     */   void showMsgBox()
/*     */   {
/* 254 */     System.out.println("host=" + this.thisHost + " hport=" + this.thisPort + " dhost=" + this.destHost + " dport=" + this.destPort);
/*     */     
/* 256 */     System.out.println("outmsgType=" + this.outMsgType + " recved=" + this.recved);
/* 257 */     UTIL.showList("inBody=", this.inBody);
/* 258 */     UTIL.showList("outBody=", this.outBody);
/*     */   }
/*     */ }


/* Location:              /home/anali/INAOE/tesis/codigos/topub/exe/ww4.jar!/Network/MsgBox.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */