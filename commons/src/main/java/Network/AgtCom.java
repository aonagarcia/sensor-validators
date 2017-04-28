/*     */ package Network;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.FileReader;
/*     */ import java.io.IOException;
/*     */ import java.util.StringTokenizer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AgtCom
/*     */ {
/*  13 */   String path = null;
/*  14 */   String host = null;
/*  15 */   int port = 0;
/*  16 */   String agentnm = null;
/*  17 */   String[] nbhs = null;
/*  18 */   int[] nbpt = null;
/*  19 */   String[] nbanm = null;
/*     */   
/*  21 */   MsgBox[] msgb = null;
/*  22 */   BearerMgr bmgr = null;
/*     */   Bridge panel;
/*     */   
/*     */   public AgtCom(String paramString, Bridge paramBridge)
/*     */   {
/*  27 */     this.path = paramString;
/*  28 */     this.panel = paramBridge;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public String getPath()
/*     */   {
/*  35 */     if (this.path == null) return null;
/*  36 */     return new String(this.path);
/*     */   }
/*     */   
/*     */ 
/*     */   public int getPort()
/*     */   {
/*  42 */     return this.port;
/*     */   }
/*     */   
/*     */   public String getHost() {
/*  46 */     return new String(this.host);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void loadHostPort()
/*     */   {
/*     */     try
/*     */     {
/*  60 */       BufferedReader localBufferedReader = new BufferedReader(new FileReader(this.path + ".adr"));
/*  61 */       HelpPanel.addHelp("Loading address file from " + this.path + ".adr.");
/*     */       
/*  63 */       int i = UTIL.loadInt(localBufferedReader);
/*  64 */       StringTokenizer localStringTokenizer = new StringTokenizer(localBufferedReader.readLine());
/*  65 */       this.host = localStringTokenizer.nextToken();
/*  66 */       this.port = Integer.parseInt(localStringTokenizer.nextToken());
/*  67 */       this.agentnm = localStringTokenizer.nextToken();
/*     */       
/*  69 */       this.nbhs = new String[i];
/*  70 */       this.nbpt = new int[i];
/*  71 */       this.nbanm = new String[i];
/*  72 */       for (int j = 0; j < i; j++) {
/*  73 */         localStringTokenizer = new StringTokenizer(localBufferedReader.readLine());
/*  74 */         this.nbhs[j] = localStringTokenizer.nextToken();
/*  75 */         this.nbpt[j] = Integer.parseInt(localStringTokenizer.nextToken());
/*  76 */         this.nbanm[j] = localStringTokenizer.nextToken();
/*     */       }
/*     */     } catch (IOException localIOException) {
/*  79 */       HelpPanel.showError("Unable to load " + this.path + ".adr");
/*     */     }
/*  81 */     HelpPanel.addHelp("Host=" + this.host + " Port=" + this.port);
/*     */   }
/*     */   
/*     */   public void showHostPort()
/*     */   {
/*  86 */     HelpPanel.addHelp("local: " + this.host + " " + this.port);
/*  87 */     HelpPanel.showList("nbhs: ", this.nbhs);
/*  88 */     HelpPanel.showList("nbpt: ", this.nbpt);
/*     */   }
/*     */   
/*     */   public String getName()
/*     */   {
/*  93 */     return new String(this.agentnm);
/*     */   }
/*     */   
/*     */   public String getNbrName(int paramInt)
/*     */   {
/*  98 */     return new String(this.nbanm[paramInt]);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public int getNeighborCount()
/*     */   {
/* 105 */     return this.nbhs.length;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setMsgBox()
/*     */   {
/* 112 */     int i = this.nbhs.length;
/* 113 */     this.msgb = new MsgBox[i];
/* 114 */     for (int j = 0; j < i; j++) {
/* 115 */       this.msgb[j] = new MsgBox(this.host, this.port, this.nbhs[j], this.nbpt[j]);
/*     */     }
/*     */   }
/*     */   
/*     */   void setListOutBody(int paramInt, int[] paramArrayOfInt) {
/* 120 */     this.msgb[paramInt].setListOutBody(paramArrayOfInt);
/*     */   }
/*     */   
/* 123 */   void setListOutBody(int paramInt, float[] paramArrayOfFloat) { this.msgb[paramInt].setListOutBody(paramArrayOfFloat); }
/*     */   
/*     */   void setListOutBody(int paramInt, float[][] paramArrayOfFloat) {
/* 126 */     this.msgb[paramInt].setListOutBody(paramArrayOfFloat);
/*     */   }
/*     */   
/*     */   int[] getInBodyIntList(int paramInt)
/*     */   {
/* 131 */     return this.msgb[paramInt].getInBodyIntList();
/*     */   }
/*     */   
/*     */   float[] getInBodyRealList(int paramInt)
/*     */   {
/* 136 */     return this.msgb[paramInt].getInBodyRealList();
/*     */   }
/*     */   
/*     */   void showMsgBox(int paramInt)
/*     */   {
/* 141 */     this.msgb[paramInt].showMsgBox();
/*     */   }
/*     */   
/*     */   void showMsgBox() {
/* 145 */     for (int i = 0; i < this.msgb.length; i++) { this.msgb[i].showMsgBox();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public void stopBearerMgr()
/*     */   {
/* 152 */     this.bmgr.stopSock();
/* 153 */     this.bmgr.setStop(true);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setMsgFlag(int paramInt)
/*     */   {
/* 160 */     int[] arrayOfInt = new int[1];
/* 161 */     arrayOfInt[0] = paramInt;
/* 162 */     this.panel.setVector2(arrayOfInt);
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
/*     */   public void setCtrlFlag(String paramString1, String paramString2)
/*     */   {
/* 175 */     String[] arrayOfString = new String[2];
/* 176 */     arrayOfString[0] = new String(paramString1);
/* 177 */     arrayOfString[1] = new String(paramString2);
/* 178 */     this.panel.setVector2(arrayOfString);
/*     */   }
/*     */ }


/* Location:              /home/anali/INAOE/tesis/codigos/topub/exe/ww4.jar!/Network/AgtCom.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */