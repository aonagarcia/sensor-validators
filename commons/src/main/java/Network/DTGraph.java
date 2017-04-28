/*     */ package Network;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DTGraph
/*     */   extends DirectGraph
/*     */ {
/*     */   public DTGraph() {}
/*     */   
/*     */ 
/*     */ 
/*     */   public DTGraph(DTGraph paramDTGraph)
/*     */   {
/*  14 */     this();
/*  15 */     if ((paramDTGraph != null) && (paramDTGraph.nd != null)) setDTGraph(paramDTGraph);
/*     */   }
/*     */   
/*     */   public void reinit()
/*     */   {
/*  20 */     this.nd = null;
/*     */   }
/*     */   
/*     */   public void setDumbNet(int paramInt)
/*     */   {
/*  25 */     this.nd = new TNode[paramInt];
/*     */   }
/*     */   
/*     */   public void setDumbNetPlus(int paramInt)
/*     */   {
/*  30 */     setDumbNet(paramInt);
/*  31 */     for (int i = 0; i < paramInt; i++) this.nd[i] = new TNode();
/*     */   }
/*     */   
/*     */   void setDTGraph(DTGraph paramDTGraph) {
/*  35 */     int i = paramDTGraph.getNodeCount();
/*  36 */     setDumbNetPlus(i);
/*  37 */     for (int j = 0; j < i; j++) this.nd[j] = new TNode(paramDTGraph.getTNode(j));
/*     */   }
/*     */   
/*     */   TNode getTNode(int paramInt) {
/*  41 */     return new TNode((TNode)this.nd[paramInt]);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   boolean isOnBdr(int paramInt1, int paramInt2, int paramInt3)
/*     */   {
/*  48 */     return ((TNode)this.nd[paramInt1]).isOnBdr(paramInt2, paramInt3);
/*     */   }
/*     */   
/*     */   public void setOnBdr(int paramInt1, int paramInt2, int paramInt3)
/*     */   {
/*  53 */     ((TNode)this.nd[paramInt1]).setOnBdr(paramInt2, paramInt3);
/*     */   }
/*     */   
/*     */   int getBuddy(int paramInt)
/*     */   {
/*  58 */     for (int i = 0; i < this.nd.length - 1; i++)
/*  59 */       if ((i != paramInt) && (getLabel(i).equals(getLabel(paramInt)))) return i;
/*  60 */     return -1;
/*     */   }
/*     */   
/*     */   public void setNativeBorder()
/*     */   {
/*  65 */     for (int i = 0; i < this.nd.length - 1; i++) {
/*  66 */       for (int j = i + 1; j < this.nd.length; j++)
/*  67 */         if (getLabel(i).equals(getLabel(j))) {
/*  68 */           setOnBdr(i, 0, 0);
/*  69 */           setOnBdr(j, 0, 1);
/*     */         }
/*     */     }
/*     */   }
/*     */   
/*     */   public void setRunTimeBorder(int paramInt) {
/*  75 */     ((TNode)this.nd[paramInt]).setOnBdr(1, 2);
/*     */   }
/*     */   
/*     */ 
/*     */   int[] getBorder(int paramInt1, int paramInt2)
/*     */   {
/*  81 */     int[] arrayOfInt = null;
/*  82 */     for (int i = 0; i < this.nd.length; i++)
/*  83 */       if (((TNode)this.nd[i]).isOnBdr(paramInt1, paramInt2))
/*  84 */         arrayOfInt = MATH.addMember(i, arrayOfInt);
/*  85 */     return arrayOfInt;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   void setRunTimeSliceByFwdBdr()
/*     */   {
/*  93 */     int[] arrayOfInt1 = getBorder(0, 0);
/*  94 */     for (int i = 0; i < arrayOfInt1.length; i++) {
/*  95 */       setOnBdr(arrayOfInt1[i], 2, 3);
/*     */     }
/*  97 */     int[] arrayOfInt2 = getBorder(0, 1);
/*  98 */     for (int j = 0; j < arrayOfInt2.length; j++) {
/*  99 */       setOnBdr(arrayOfInt2[j], 2, 4);
/*     */     }
/* 101 */     for (j = 0; j < this.nd.length; j++) {
/* 102 */       setOnBdr(j, 0, -1);
/*     */     }
/*     */   }
/*     */ }


/* Location:              /home/anali/INAOE/tesis/codigos/topub/exe/ww4.jar!/Network/DTGraph.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */