/*     */ package Network;
/*     */ 
/*     */ import java.awt.Point;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.PrintWriter;
/*     */ 
/*     */ class DNode extends Node
/*     */ {
/*   9 */   int[] pls = null;
/*  10 */   int[] cls = null;
/*     */   
/*     */   DNode() {}
/*     */   
/*     */   DNode(XNode paramXNode)
/*     */   {
/*  16 */     super(paramXNode);
/*  17 */     this.pls = paramXNode.getParent();
/*  18 */     this.cls = paramXNode.getChild();
/*     */   }
/*     */   
/*  21 */   DNode(DNode paramDNode) { super(paramDNode);
/*  22 */     this.pls = UTIL.getDuplicate(paramDNode.getParent());
/*  23 */     this.cls = UTIL.getDuplicate(paramDNode.getChild());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   void setParent(int[] paramArrayOfInt)
/*     */   {
/*  30 */     this.pls = paramArrayOfInt;
/*     */   }
/*     */   
/*     */   boolean isParent(int paramInt)
/*     */   {
/*  35 */     if (this.pls == null) return false;
/*  36 */     if (MATH.member(paramInt, this.pls)) return true;
/*  37 */     return false;
/*     */   }
/*     */   
/*     */   int getParentCount()
/*     */   {
/*  42 */     if (this.pls == null) return 0;
/*  43 */     return this.pls.length;
/*     */   }
/*     */   
/*     */   int getParent(int paramInt)
/*     */   {
/*  48 */     if (this.pls == null) return -1;
/*  49 */     return this.pls[paramInt];
/*     */   }
/*     */   
/*     */   int[] getParent() {
/*  53 */     return UTIL.getDuplicate(this.pls);
/*     */   }
/*     */   
/*     */   void addParent(int paramInt)
/*     */   {
/*  58 */     this.pls = MATH.addMember(paramInt, this.pls);
/*     */   }
/*     */   
/*     */   void delParent(int paramInt)
/*     */   {
/*  63 */     this.pls = MATH.delMember(paramInt, this.pls);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   void setChild(int[] paramArrayOfInt)
/*     */   {
/*  70 */     this.cls = paramArrayOfInt;
/*     */   }
/*     */   
/*     */   boolean hasChild()
/*     */   {
/*  75 */     return this.cls != null;
/*     */   }
/*     */   
/*     */   void replaceChild(int paramInt1, int paramInt2)
/*     */   {
/*  80 */     delChild(paramInt1);addChild(paramInt2);
/*     */   }
/*     */   
/*     */   int getChildCount()
/*     */   {
/*  85 */     if (this.cls == null) return 0;
/*  86 */     return this.cls.length;
/*     */   }
/*     */   
/*     */   int[] getChild()
/*     */   {
/*  91 */     return UTIL.getDuplicate(this.cls);
/*     */   }
/*     */   
/*     */   void addChild(int paramInt)
/*     */   {
/*  96 */     this.cls = MATH.addMember(paramInt, this.cls);
/*     */   }
/*     */   
/*     */   void delChild(int paramInt)
/*     */   {
/* 101 */     this.cls = MATH.delMember(paramInt, this.cls);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   void modifyNodeIndex(int paramInt1, int paramInt2, int paramInt3)
/*     */   {
/* 110 */     int i = this.pls == null ? 0 : this.pls.length;
/* 111 */     for (int j = 0; j < i; j++) if ((this.pls[j] >= paramInt1) && (this.pls[j] <= paramInt2)) this.pls[j] += paramInt3;
/* 112 */     MATH.qsort(this.pls);
/* 113 */     i = this.cls == null ? 0 : this.cls.length;
/* 114 */     for (j = 0; j < i; j++) if ((this.cls[j] >= paramInt1) && (this.cls[j] <= paramInt2)) this.cls[j] += paramInt3;
/* 115 */     MATH.qsort(this.cls);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   void loadCP(BufferedReader paramBufferedReader)
/*     */   {
/* 122 */     setChild(UTIL.loadIntList(paramBufferedReader));
/* 123 */     setParent(UTIL.loadIntList(paramBufferedReader));
/*     */   }
/*     */   
/*     */   void saveCP(PrintWriter paramPrintWriter)
/*     */   {
/* 128 */     UTIL.saveIntList(paramPrintWriter, this.cls, "children");
/* 129 */     UTIL.saveIntList(paramPrintWriter, this.pls, "parents");
/*     */   }
/*     */   
/*     */ 
/*     */   void showDNode()
/*     */   {
/* 135 */     System.out.println("label=" + getLabel() + " Pos=(" + getPos().x + "," + getPos().y + ")");
/*     */     
/* 137 */     UTIL.showList("pls[]=", this.pls);
/* 138 */     UTIL.showList("cls[]=", this.cls);
/*     */   }
/*     */ }


/* Location:              /home/anali/INAOE/tesis/codigos/topub/exe/ww4.jar!/Network/DNode.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */