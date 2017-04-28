package Network;

public abstract interface IntAgtV
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
  
  public abstract int[] markRootLeaf(int[] paramArrayOfInt);
  
  public abstract void setMark(int paramInt);
  
  public abstract boolean isMarked(int paramInt);
  
  public abstract boolean isMarked();
  
  public abstract boolean hasUnmarkedParent(int paramInt);
  
  public abstract boolean hasUnmarkedChild(int paramInt);
}


/* Location:              /home/anali/INAOE/tesis/codigos/topub/exe/ww4.jar!/Network/IntAgtV.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */