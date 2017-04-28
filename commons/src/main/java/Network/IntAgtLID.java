package Network;

public abstract interface IntAgtLID
{
  public abstract int getNodeCount();
  
  public abstract String[] getLabel(int[] paramArrayOfInt);
  
  public abstract BayesNet getNet();
  
  public abstract boolean isMarked(int paramInt);
  
  public abstract void clrBufferPoten();
  
  public abstract void clrLkgBufPoten();
  
  public abstract LJoinTreeM getJoinTree1();
  
  public abstract void setStateCount();
  
  public abstract void setPoten(int[] paramArrayOfInt);
  
  public abstract void setMjfPoten(int[] paramArrayOfInt);
  
  public abstract void clrMjfBufPoten();
  
  public abstract void clrMjfLkgBufPoten();
  
  public abstract int[][] getMjfLkg(int paramInt);
  
  public abstract String[][] getMjfLkgStr(int paramInt);
  
  public abstract LazyBelSet[] getMjfBufPoten(int paramInt);
  
  public abstract void collectMJFPotential(int paramInt);
  
  public abstract LJoinTreeM[][] getMsgJF();
  
  public abstract String[] getFullyObserved(int paramInt);
  
  public abstract void enterFullyObserved(String[] paramArrayOfString);
  
  public abstract void showAgGraphLID();
}


/* Location:              /home/anali/INAOE/tesis/codigos/topub/exe/ww4.jar!/Network/IntAgtLID.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */