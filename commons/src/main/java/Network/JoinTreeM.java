/*     */ package Network;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.PrintWriter;
/*     */ 
/*     */ public class JoinTreeM
/*     */   extends JoinForest
/*     */ {
/*     */   static final int SYSTEM = -1;
/*     */   
/*     */   JoinTreeM() {}
/*     */   
/*     */   JoinTreeM(HyperGraph paramHyperGraph)
/*     */   {
/*  15 */     super(paramHyperGraph);
/*     */   }
/*     */   
/*  18 */   JoinTreeM(JoinForest paramJoinForest) { super(paramJoinForest); }
/*     */   
/*     */   JoinTreeM(JoinTreeM paramJoinTreeM) {
/*  21 */     super(paramJoinTreeM);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setBelief(BayesNet paramBayesNet, int[] paramArrayOfInt)
/*     */   {
/*  30 */     for (int i = 0; i < getNodeCount(); i++) {
/*  31 */       setStateCount(i, paramBayesNet.getStateCount(getCqMember(i)));
/*     */     }
/*  33 */     this.varCount = paramBayesNet.getNodeCount();
/*     */     
/*  35 */     this.varToCq = new int[this.varCount];
/*  36 */     for (i = 0; i < this.varCount; i++) {
/*  37 */       this.varToCq[i] = getCqHome(paramBayesNet.getFamily(i));
/*     */     }
/*  39 */     setDumbBelief();
/*  40 */     for (i = 0; i < this.varCount; i++) {
/*  41 */       if (MATH.member(i, paramArrayOfInt)) {
/*  42 */         int[] arrayOfInt = paramBayesNet.getFamily(i);
/*  43 */         float[] arrayOfFloat = paramBayesNet.getCondProb(i);
/*  44 */         int j = this.varToCq[i];
/*  45 */         setBeliefProduct(j, arrayOfInt, arrayOfFloat);
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
/*     */   void setLinkageBelief(JoinTreeM paramJoinTreeM)
/*     */   {
/*  64 */     for (int i = 0; i < getNodeCount(); i++) {
/*  65 */       int[] arrayOfInt1 = getCqMember(i);
/*  66 */       int j = paramJoinTreeM.getCqByLabel(getLabel(i));
/*     */       
/*  68 */       int[] arrayOfInt2 = paramJoinTreeM.getCqMember(j);
/*  69 */       int[] arrayOfInt3 = paramJoinTreeM.getStateCount(j);
/*  70 */       int[] arrayOfInt4 = UTIL.getSubsetIndex(arrayOfInt2, arrayOfInt1);
/*  71 */       float[] arrayOfFloat1 = MATH.margin(paramJoinTreeM.getBelief(j), arrayOfInt3, arrayOfInt4);
/*     */       
/*  73 */       if (!hasPeer(i)) {
/*  74 */         setBelief(i, arrayOfFloat1);
/*     */       } else {
/*  76 */         int[] arrayOfInt5 = getSepsetToPeer(i);
/*  77 */         int[] arrayOfInt6 = MATH.getSubsetDimen(arrayOfInt2, arrayOfInt3, arrayOfInt1);
/*  78 */         arrayOfInt4 = UTIL.getSubsetIndex(arrayOfInt1, arrayOfInt5);
/*  79 */         float[] arrayOfFloat2 = MATH.margin(arrayOfFloat1, arrayOfInt6, arrayOfInt4);
/*  80 */         arrayOfFloat1 = MATH.division(arrayOfInt1, arrayOfFloat1, arrayOfInt5, arrayOfFloat2, arrayOfInt1, arrayOfInt6);
/*  81 */         setBelief(i, arrayOfFloat1);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   void setDumbLinkageBelief(JoinTreeM paramJoinTreeM)
/*     */   {
/*  88 */     for (int i = 0; i < getNodeCount(); i++) {
/*  89 */       int[] arrayOfInt1 = getCqMember(i);
/*  90 */       int j = paramJoinTreeM.getCqByLabel(getLabel(i));
/*  91 */       int[] arrayOfInt2 = paramJoinTreeM.getStateCount(j, arrayOfInt1);
/*  92 */       setStateCount(i, arrayOfInt2);
/*     */     }
/*     */     
/*  95 */     setDumbBelief();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   static JoinTreeM loadLinkageTree(BufferedReader paramBufferedReader)
/*     */   {
/* 102 */     int i = UTIL.loadInt(paramBufferedReader);
/* 103 */     JoinTreeM localJoinTreeM = new JoinTreeM();
/* 104 */     localJoinTreeM.setDumbNetPlus(i);
/*     */     
/* 106 */     for (int j = 0; j < i; j++) {
/* 107 */       UTIL.skipLine(paramBufferedReader);
/* 108 */       ((CNode)localJoinTreeM.cq[j]).loadLinkage(paramBufferedReader);
/*     */     }
/* 110 */     return localJoinTreeM;
/*     */   }
/*     */   
/*     */   void saveLinkageTree(PrintWriter paramPrintWriter, int paramInt)
/*     */   {
/* 115 */     int i = getNodeCount();
/* 116 */     paramPrintWriter.println(i + "  #_of_lkgs_in_tree_" + paramInt);
/* 117 */     for (int j = 0; j < i; j++) {
/* 118 */       paramPrintWriter.println();
/* 119 */       ((CNode)this.cq[j]).saveLinkage(paramPrintWriter, j);
/*     */     }
/*     */   }
/*     */ }


/* Location:              /home/anali/INAOE/tesis/codigos/topub/exe/ww4.jar!/Network/JoinTreeM.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */