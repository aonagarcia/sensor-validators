/*     */ package Network;
/*     */ 
/*     */ import java.awt.Point;
/*     */ import java.awt.Rectangle;
/*     */ import java.io.FileWriter;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintWriter;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AgGraphT
/*     */   extends AgGraph
/*     */   implements IntAgtT
/*     */ {
/*     */   public void setMark(int paramInt)
/*     */   {
/*  17 */     this.net.setMark(paramInt);
/*     */   }
/*     */   
/*     */ 
/*     */   public void makeSkeleton()
/*     */   {
/*  23 */     this.cdg = new ChordalGraph(UndirectGraph.makeSkeleton(this.net));
/*     */   }
/*     */   
/*     */   public Point[] getMfillIn()
/*     */   {
/*  28 */     return this.cdg.getMfillIn();
/*     */   }
/*     */   
/*     */   public void addMfillIn(Point paramPoint)
/*     */   {
/*  33 */     this.cdg.addMfillIn(paramPoint);
/*     */   }
/*     */   
/*     */   public Point[] getIntFillIn()
/*     */   {
/*  38 */     return this.cdg.getIntFillIn();
/*     */   }
/*     */   
/*     */   public void setFillIn(Point[] paramArrayOfPoint)
/*     */   {
/*  43 */     this.cdg.setFillIn(paramArrayOfPoint);
/*     */   }
/*     */   
/*     */   public void delFillIn() {
/*  47 */     this.cdg.delFillIn();
/*     */   }
/*     */   
/*     */   public boolean isMfillIn(Point paramPoint)
/*     */   {
/*  52 */     return this.cdg.isMfillIn(paramPoint);
/*     */   }
/*     */   
/*     */   public void pickAddFillIn(String[][] paramArrayOfString)
/*     */   {
/*  57 */     this.cdg.pickAddFillIn(paramArrayOfString);
/*     */   }
/*     */   
/*     */   public void setChordalGraph(int[] paramArrayOfInt)
/*     */   {
/*  62 */     this.cdg.setChordalGraph(paramArrayOfInt);
/*     */   }
/*     */   
/*     */   public String[][] nodesToLinksLabel(int[] paramArrayOfInt)
/*     */   {
/*  67 */     return this.cdg.nodesToLinksLabel(paramArrayOfInt);
/*     */   }
/*     */   
/*     */   public void localMoralize()
/*     */   {
/*  72 */     this.cdg = ChordalGraph.makeMoralGraph(this.net);
/*     */   }
/*     */   
/*     */ 
/*     */   public void localMoralize(Point[] paramArrayOfPoint)
/*     */   {
/*  78 */     this.cdg = ChordalGraph.makeMoralGraph(this.net, paramArrayOfPoint);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setJoinTree(ChordalGraph paramChordalGraph, Rectangle paramRectangle)
/*     */   {
/*  85 */     HyperGraph localHyperGraph = HyperGraph.setJoinForest(paramChordalGraph, paramRectangle);
/*  86 */     this.jt = new HyperGrafM(localHyperGraph);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setHostTree(int paramInt, int[] paramArrayOfInt, Rectangle paramRectangle)
/*     */   {
/*  93 */     this.lt[paramInt] = HyperGrafM.setHostTree(this.jt, paramArrayOfInt, paramRectangle);
/*     */   }
/*     */   
/*     */   public void setDumbLinkageTree(int paramInt)
/*     */   {
/*  98 */     this.lt = new HyperGrafM[paramInt];
/*     */   }
/*     */   
/*     */   public void setLinkageTree(int paramInt, int[] paramArrayOfInt, Rectangle paramRectangle)
/*     */   {
/* 103 */     this.lt[paramInt] = HyperGrafM.setLinkageTree(this.lt[paramInt], paramArrayOfInt, paramRectangle);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   int[] getNodeAssigned()
/*     */   {
/* 110 */     int[] arrayOfInt1 = getDsepsetUnion();
/* 111 */     int[] arrayOfInt2 = null;
/* 112 */     for (int i = 0; i < this.net.getNodeCount(); i++) {
/* 113 */       if (!MATH.member(i, arrayOfInt1)) { arrayOfInt2 = MATH.addMember(i, arrayOfInt2);
/* 114 */       } else if (this.net.isMarked(i)) arrayOfInt2 = MATH.addMember(i, arrayOfInt2);
/*     */     }
/* 116 */     return arrayOfInt2;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void saveJoinTree()
/*     */   {
/* 124 */     this.jt.save(new String(getPath() + ".jtt"));
/*     */   }
/*     */   
/*     */   void saveLinkageTree(PrintWriter paramPrintWriter)
/*     */   {
/* 129 */     int i = getNeighborCount();
/* 130 */     paramPrintWriter.println(i + "  #_of_lkg_trees");
/* 131 */     for (int j = 0; j < i; j++) {
/* 132 */       paramPrintWriter.println();
/* 133 */       this.lt[j].saveLinkageTree(paramPrintWriter, j);
/*     */     }
/*     */   }
/*     */   
/*     */   public void saveLinkageTree()
/*     */   {
/* 139 */     String str = new String(getPath() + ".ltt");
/*     */     try {
/* 141 */       PrintWriter localPrintWriter = new PrintWriter(new FileWriter(str));
/* 142 */       HelpPanel.addHelp("Saving " + str);
/* 143 */       saveLinkageTree(localPrintWriter);
/* 144 */       localPrintWriter.close();
/*     */     } catch (IOException localIOException) {
/* 146 */       HelpPanel.showError("Unable to save " + str);
/*     */     }
/*     */   }
/*     */   
/*     */   void saveNodeAssigned(String paramString)
/*     */   {
/*     */     try {
/* 153 */       PrintWriter localPrintWriter = new PrintWriter(new FileWriter(paramString));
/* 154 */       HelpPanel.addHelp("Saving " + paramString);
/* 155 */       int[] arrayOfInt = getNodeAssigned();
/* 156 */       UTIL.saveIntList(localPrintWriter, arrayOfInt, new String("var_assigned"));
/* 157 */       localPrintWriter.close();
/*     */     } catch (IOException localIOException) {
/* 159 */       HelpPanel.showError("Unable to save " + paramString);
/*     */     }
/*     */   }
/*     */   
/*     */   public void saveNodeAssigned() {
/* 164 */     saveNodeAssigned(new String(getPath() + ".nas"));
/*     */   }
/*     */ }


/* Location:              /home/anali/INAOE/tesis/codigos/topub/exe/ww4.jar!/Network/AgGraphT.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */