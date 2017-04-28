/*     */ package Network;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.PrintWriter;
/*     */ 
/*     */ public class AgGraphDes extends AgGraph
/*     */ {
/*     */   DesignNet net;
/*     */   DivisionTree dt;
/*  10 */   static boolean debug = false;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int[] getDesignParSz(int[] paramArrayOfInt)
/*     */   {
/*  20 */     return this.dt.getDesignParSz(paramArrayOfInt);
/*     */   }
/*     */   
/*     */ 
/*     */   public void setDivTree()
/*     */   {
/*  26 */     this.dt = DivisionTree.setDivTree(this.net, 1);
/*     */   }
/*     */   
/*     */ 
/*     */   void setDn(DesignNet paramDesignNet)
/*     */   {
/*  32 */     this.net = paramDesignNet;
/*     */   }
/*     */   
/*     */   public DesignNet getDn() {
/*  36 */     return this.net;
/*     */   }
/*     */   
/*     */   public DivisionTree getDivTree() {
/*  40 */     return this.dt;
/*     */   }
/*     */   
/*     */   public float getWeight() {
/*  44 */     return this.dt.getWeight();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public float[] getDsepsetUtil(int paramInt)
/*     */   {
/*  54 */     int[] arrayOfInt = getDsepsetLocalIndex(paramInt);
/*  55 */     int i = 0;
/*  56 */     if (debug) UTIL.showList("dsep[]=", arrayOfInt);
/*  57 */     return this.dt.getWeud(i, arrayOfInt);
/*     */   }
/*     */   
/*     */ 
/*     */   public void absorbDesignPot(int[] paramArrayOfInt, float[] paramArrayOfFloat)
/*     */   {
/*  63 */     int i = 0;
/*  64 */     this.dt.absorbDesignPot(i, paramArrayOfInt, paramArrayOfFloat);
/*     */   }
/*     */   
/*     */   float getOptUtil(int paramInt)
/*     */   {
/*  69 */     return this.dt.getOptUtil(paramInt);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int[] setOptDesign(int[] paramArrayOfInt1, int[] paramArrayOfInt2)
/*     */   {
/*  81 */     int i = 0;
/*  82 */     return this.dt.setOptDesign(i, paramArrayOfInt1, paramArrayOfInt2);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public int[] getOptDesign(int[] paramArrayOfInt)
/*     */   {
/*  89 */     int i = 0;
/*  90 */     return this.dt.getOptDivDesign(0, paramArrayOfInt);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void loadDn()
/*     */   {
/*  98 */     this.net = DesignNet.loadDn(this.path + ".dn");
/*     */   }
/*     */   
/*     */   public void loadWeight(String paramString) {
/* 102 */     this.dt.loadWeight(this.path + ".wet", paramString);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void saveOptDes(String paramString1, String paramString2)
/*     */   {
/* 111 */     boolean bool = false;
/*     */     try {
/* 113 */       if (paramString2.equals("root")) bool = true;
/* 114 */       PrintWriter localPrintWriter = new PrintWriter(new java.io.FileWriter(paramString1, bool));
/*     */       
/* 116 */       int[] arrayOfInt1 = this.net.getDesignNode();
/* 117 */       int[] arrayOfInt2 = getOptDesign(arrayOfInt1);
/* 118 */       localPrintWriter.println("Globally Optimal Local Design:");
/* 119 */       int i = arrayOfInt1.length;
/* 120 */       localPrintWriter.println(i + "  #_of_Design_Pars");
/* 121 */       for (int j = 0; j < i; j++) {
/* 122 */         String str1 = this.net.getLabel(arrayOfInt1[j]);
/* 123 */         String str2 = this.net.getState(arrayOfInt1[j], arrayOfInt2[j]);
/* 124 */         localPrintWriter.println(str1 + " = " + str2);
/*     */       }
/*     */       
/* 127 */       if (paramString2.equals("root")) {
/* 128 */         localPrintWriter.println(getOptUtil(0) + "  opt_util_@_ColDesign_root_agt");
/*     */       }
/* 130 */       localPrintWriter.println();
/*     */       
/* 132 */       localPrintWriter.close();
/*     */     } catch (IOException localIOException) {
/* 134 */       HelpPanel.showError("Unable to save " + paramString1);
/*     */     }
/* 136 */     HelpPanel.addHelp("Optimal design saved in " + paramString1);
/*     */   }
/*     */   
/*     */   public void saveOptDes(String paramString) {
/* 140 */     saveOptDes(this.path + ".des", paramString);
/*     */   }
/*     */   
/*     */ 
/*     */   public void showDesignPot()
/*     */   {
/* 146 */     this.dt.showDesignPot();
/*     */   }
/*     */   
/*     */   public void showOptDivDesign()
/*     */   {
/* 151 */     this.dt.showOptDivDesign();
/*     */   }
/*     */   
/*     */   public void seeOptDivDesign()
/*     */   {
/* 156 */     int[][][] arrayOfInt = this.dt.getOptDivDesign();
/* 157 */     for (int i = 0; i < arrayOfInt.length; i++) {
/* 158 */       String str1 = new String("Opt Design @ div-" + i + " (");
/* 159 */       for (int j = 0; j < arrayOfInt[i][0].length; j++) {
/* 160 */         String str2 = this.net.getLabel(arrayOfInt[i][0][j]);
/* 161 */         String str3 = this.net.getState(arrayOfInt[i][0][j], arrayOfInt[i][1][j]);
/* 162 */         str1 = str1 + str2 + "=" + str3 + " ";
/*     */       }
/* 164 */       str1 = str1 + ")";
/* 165 */       HelpPanel.addHelp(str1);
/*     */     }
/*     */   }
/*     */ }


/* Location:              /home/anali/INAOE/tesis/codigos/topub/exe/ww4.jar!/Network/AgGraphDes.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */