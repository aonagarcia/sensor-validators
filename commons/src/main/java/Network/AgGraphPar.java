/*     */ package Network;
/*     */ 
/*     */ import java.io.FileWriter;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintWriter;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AgGraphPar
/*     */   extends AgGraph
/*     */ {
/*     */   DesignNet net;
/*     */   DecNetPar dp;
/*  19 */   static boolean debug = false;
/*     */   
/*     */ 
/*  22 */   int PeUcKey = 0;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setPeUcKey(int paramInt)
/*     */   {
/*  31 */     this.PeUcKey = paramInt;
/*     */   }
/*     */   
/*     */   void setDn(DesignNet paramDesignNet) {
/*  35 */     this.net = paramDesignNet;
/*     */   }
/*     */   
/*     */   public DesignNet getDn() {
/*  39 */     return this.net;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setDp(int paramInt)
/*     */   {
/*  46 */     this.dp = new DecNetPar(this.net);
/*  47 */     this.dp.pivot = paramInt;
/*  48 */     this.dp.setPath(getPath());
/*     */   }
/*     */   
/*     */   public DecNetPar getDp() {
/*  52 */     return this.dp;
/*     */   }
/*     */   
/*     */   public float getAgtWeight() {
/*  56 */     return this.dp.getAgtWeight();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public float[] getDsepsetUtil(int paramInt)
/*     */   {
/*  67 */     int[] arrayOfInt = getDsepsetLocalIndex(paramInt);
/*  68 */     this.dp.setLocalVar();
/*  69 */     if (debug) { UTIL.showList("Run getDsepsetUtil() & call getMEU(dsep), where dsep[]=", arrayOfInt);
/*     */     }
/*  71 */     float[] arrayOfFloat = null;
/*  72 */     if (this.PeUcKey == 0) { arrayOfFloat = this.dp.getMeuPeUc(arrayOfInt);
/*  73 */     } else if (this.PeUcKey == 1) { arrayOfFloat = this.dp.getMeuPeNoUc(arrayOfInt);
/*  74 */     } else if (this.PeUcKey == 2) arrayOfFloat = this.dp.getMeuFeUc(arrayOfInt); else
/*  75 */       arrayOfFloat = this.dp.getMeuFeNoUc(arrayOfInt);
/*  76 */     if (debug) UTIL.showList("util mes =", arrayOfFloat);
/*  77 */     return arrayOfFloat;
/*     */   }
/*     */   
/*     */   public void absorbDesignPot(int[] paramArrayOfInt, float[] paramArrayOfFloat)
/*     */   {
/*  82 */     this.dp = DecNetPar.addUtilEffe(this.dp, paramArrayOfInt, paramArrayOfFloat);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int[] getOptDec()
/*     */   {
/*  94 */     this.dp.getOptDecFe();
/*  95 */     return this.dp.od;
/*     */   }
/*     */   
/*     */ 
/*     */   public float getOptUtil()
/*     */   {
/* 101 */     return this.dp.ou;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void getOptDec(int[] paramArrayOfInt1, int[] paramArrayOfInt2)
/*     */   {
/* 108 */     this.dp.getOptDec(paramArrayOfInt1, paramArrayOfInt2);
/*     */   }
/*     */   
/*     */ 
/*     */   public int[] getOptDec(int[] paramArrayOfInt)
/*     */   {
/* 114 */     return UTIL.project(this.dp.ad, this.dp.od, paramArrayOfInt);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void loadDn()
/*     */   {
/* 121 */     this.net = DesignNet.loadDn(this.path + ".dn");
/*     */   }
/*     */   
/*     */   public void loadWeight(String paramString) {
/* 125 */     this.dp.loadWeight(this.path + ".wet", paramString);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void saveOptDes(String paramString1, String paramString2)
/*     */   {
/* 134 */     boolean bool = false;
/*     */     try {
/* 136 */       if (paramString2.equals("root")) bool = true;
/* 137 */       PrintWriter localPrintWriter = new PrintWriter(new FileWriter(paramString1, bool));
/*     */       
/* 139 */       int[] arrayOfInt1 = this.dp.ad;
/* 140 */       int[] arrayOfInt2 = this.dp.od;
/* 141 */       localPrintWriter.println("Globally Optimal Local Design:");
/* 142 */       int i = arrayOfInt1.length;
/* 143 */       localPrintWriter.println(i + "  #_of_Design_Pars");
/* 144 */       for (int j = 0; j < i; j++) {
/* 145 */         String str1 = this.net.getLabel(arrayOfInt1[j]);
/* 146 */         String str2 = this.net.getState(arrayOfInt1[j], arrayOfInt2[j]);
/* 147 */         localPrintWriter.println(str1 + " = " + str2);
/*     */       }
/*     */       
/* 150 */       if (paramString2.equals("root")) {
/* 151 */         localPrintWriter.println(this.dp.ou + "  opt_util_@_CoDecPar_root_agt");
/*     */       }
/* 153 */       localPrintWriter.println();
/*     */       
/* 155 */       localPrintWriter.close();
/*     */     } catch (IOException localIOException) {
/* 157 */       HelpPanel.showError("Unable to save " + paramString1);
/*     */     }
/* 159 */     HelpPanel.addHelp("Optimal design saved in " + paramString1);
/*     */   }
/*     */   
/*     */   public void saveOptDes(String paramString) {
/* 163 */     saveOptDes(this.path + ".des", paramString);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void seeOptDec()
/*     */   {
/* 170 */     int[] arrayOfInt1 = this.dp.ad;
/* 171 */     int[] arrayOfInt2 = this.dp.od;
/* 172 */     String str1 = new String("Opt plan = (");
/* 173 */     for (int i = 0; i < arrayOfInt2.length; i++) {
/* 174 */       String str2 = this.dp.getLabel(arrayOfInt1[i]);
/* 175 */       String str3 = this.dp.getState(arrayOfInt1[i], arrayOfInt2[i]);
/* 176 */       str1 = str1 + str2 + "=" + str3 + " ";
/*     */     }
/* 178 */     str1 = str1 + ")";
/* 179 */     HelpPanel.addHelp(str1);
/*     */   }
/*     */ }


/* Location:              /home/anali/INAOE/tesis/codigos/topub/exe/ww4.jar!/Network/AgGraphPar.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */