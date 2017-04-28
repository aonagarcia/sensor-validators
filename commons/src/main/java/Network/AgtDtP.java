/*     */ package Network;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.PrintStream;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AgtDtP
/*     */   extends AgtDt
/*     */ {
/*  11 */   int round = 0;
/*     */   
/*     */   public AgtDtP(AgGraphDt paramAgGraphDt, String paramString, Bridge paramBridge) {
/*  14 */     super(paramAgGraphDt, paramString, paramBridge);
/*     */   }
/*     */   
/*     */ 
/*     */   public String[] actAll(int paramInt1, int paramInt2)
/*     */   {
/*     */     String str1;
/*     */     
/*     */     String str2;
/*     */     String str3;
/*  24 */     if (paramInt2 == 1)
/*     */     {
/*  26 */       HelpPanel.addHelp("*** Col-Design No. " + this.round + " ***");
/*     */       
/*  28 */       str1 = new String("" + this.round);
/*  29 */       str2 = UTIL.openWithZero(str1, 3);
/*  30 */       System.out.println("path retrieved = " + this.agnet.getPath());
/*  31 */       str3 = new String(this.agnet.getPath() + "-" + str2);
/*  32 */       String str4 = new String(str3 + ".wrd");
/*     */       
/*  34 */       File localFile = new File(str4);
/*  35 */       HelpPanel.addHelp("Wait for " + str4 + " ...");
/*  36 */       while (!localFile.exists()) {
/*     */         try {
/*  38 */           Thread.sleep(1000L);
/*     */         }
/*     */         catch (InterruptedException localInterruptedException) {}
/*     */       }
/*  42 */       DesNetPers localDesNetPers = new DesNetPers(this.agnet.getDn());
/*  43 */       localDesNetPers.update(str3, false);
/*  44 */       this.agnet.setDn(localDesNetPers);
/*  45 */       HelpPanel.addHelp("Subnet updated using " + str4);
/*     */       
/*  47 */       this.agnet.d = null;this.agnet.setDesignPar();
/*  48 */       HelpPanel.addHelp("Design par list d[] updated.");
/*  49 */       this.agnet.dsz = null;this.agnet.setDesignParSz();
/*  50 */       HelpPanel.addHelp("Design par dimen list dsz[] updated.");
/*     */       
/*  52 */       this.agnet.initEnvset();
/*  53 */       String str5 = new String(str3 + ".efv");
/*  54 */       localFile = new File(str5);
/*  55 */       if (localFile.exists()) { this.agnet.loadEnvFacVal(str5);
/*     */       }
/*     */       
/*  58 */       if (paramInt1 == -1) { HelpPanel.addHelp("Set one div tree ...");
/*     */       } else {
/*  60 */         int i = this.agnet.getLkgCount(paramInt1);
/*  61 */         HelpPanel.addHelp("Set " + i + " div tree(s) ...");
/*     */       }
/*  63 */       setDivTree(paramInt1, paramInt2);
/*  64 */       if (paramInt1 == -1) {
/*  65 */         HelpPanel.addHelp("Root: setDivTree completed.");
/*     */       } else {
/*  67 */         HelpPanel.addHelp("NonRoot: setDivTree completed.");
/*     */       }
/*  69 */       this.agnet.enterEnvSetupToCq();
/*  70 */       this.agnet.designByDiv();
/*  71 */       HelpPanel.addHelp("Local design by division completed.");
/*     */       
/*  73 */       return null;
/*     */     }
/*  75 */     if (paramInt2 == 2) {
/*  76 */       HelpPanel.addHelp("Start collectUtil ...");
/*  77 */       collectUtil(paramInt1, paramInt2);
/*  78 */       if (paramInt1 == -1) {
/*  79 */         HelpPanel.addHelp("Root: collectUtil completed.");
/*     */       } else
/*  81 */         HelpPanel.addHelp("NonRoot: collectUtil completed.");
/*  82 */       return null;
/*     */     }
/*  84 */     if (paramInt2 == 3) {
/*  85 */       HelpPanel.addHelp("Start distrOptDes ...");
/*  86 */       distrOptDes(paramInt1, paramInt2);
/*  87 */       if (paramInt1 == -1) {
/*  88 */         HelpPanel.addHelp("Root: distrOptDes completed.");
/*     */       } else {
/*  90 */         HelpPanel.addHelp("NonRoot: distrOptDes completed.");
/*     */       }
/*     */       
/*  93 */       str1 = new String("" + this.round);
/*  94 */       str2 = UTIL.openWithZero(str1, 3);
/*  95 */       str3 = new String(this.agnet.getPath() + "-" + str2 + ".des");
/*     */       
/*  97 */       this.agnet.saveOptDes(str3);
/*  98 */       this.round += 1;
/*     */       
/*     */ 
/* 101 */       setCtrlFlag("operation_stage", "designed");
/* 102 */       return null;
/*     */     }
/*     */     
/* 105 */     return null;
/*     */   }
/*     */ }


/* Location:              /home/anali/INAOE/tesis/codigos/topub/exe/ww4.jar!/Network/AgtDtP.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */