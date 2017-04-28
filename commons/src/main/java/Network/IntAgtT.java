package Network;

import java.awt.Point;
import java.awt.Rectangle;

public abstract interface IntAgtT
{
  public abstract void loadDag();
  
  public abstract int getIndex(String paramString);
  
  public abstract int[] getDsepsetLocalIndex(int paramInt);
  
  public abstract int[] getDsepsetUnion();
  
  public abstract String getLabel(int paramInt);
  
  public abstract String[] getLabel(int[] paramArrayOfInt);
  
  public abstract boolean hasOutParent(String paramString, String[] paramArrayOfString);
  
  public abstract String[][] getInParentLabel(String[] paramArrayOfString);
  
  public abstract String[] getInParentLabel(String paramString, String[] paramArrayOfString);
  
  public abstract Dsepset getDsepset(int paramInt);
  
  public abstract ChordalGraph getSkeleton();
  
  public abstract void setMark(int paramInt);
  
  public abstract Point[] getMfillIn();
  
  public abstract void addMfillIn(Point paramPoint);
  
  public abstract Point[] getIntFillIn();
  
  public abstract void setFillIn(Point[] paramArrayOfPoint);
  
  public abstract void delFillIn();
  
  public abstract boolean isMfillIn(Point paramPoint);
  
  public abstract void pickAddFillIn(String[][] paramArrayOfString);
  
  public abstract void setChordalGraph(int[] paramArrayOfInt);
  
  public abstract String[][] nodesToLinksLabel(int[] paramArrayOfInt);
  
  public abstract void localMoralize();
  
  public abstract void localMoralize(Point[] paramArrayOfPoint);
  
  public abstract void setJoinTree(ChordalGraph paramChordalGraph, Rectangle paramRectangle);
  
  public abstract void setHostTree(int paramInt, int[] paramArrayOfInt, Rectangle paramRectangle);
  
  public abstract void setDumbLinkageTree(int paramInt);
  
  public abstract void setLinkageTree(int paramInt, int[] paramArrayOfInt, Rectangle paramRectangle);
}


/* Location:              /home/anali/INAOE/tesis/codigos/topub/exe/ww4.jar!/Network/IntAgtT.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */