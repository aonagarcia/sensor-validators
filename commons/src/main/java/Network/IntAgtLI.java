package Network;

public abstract interface IntAgtLI
{
  public abstract void loadDag();
  
  public abstract int getIndex(String paramString);
  
  public abstract int[] getDsepsetLocalIndex(int paramInt);
  
  public abstract int[] getDsepsetUnion();
  
  public abstract String getLabel(int paramInt);
  
  public abstract String[] getLabel(int[] paramArrayOfInt);
  
  public abstract BayesNet getNet();
  
  public abstract boolean hasOutParent(String paramString, String[] paramArrayOfString);
  
  public abstract String[][] getInParentLabel(String[] paramArrayOfString);
  
  public abstract String[] getInParentLabel(String paramString, String[] paramArrayOfString);
  
  public abstract Dsepset getDsepset(int paramInt);
  
  public abstract ChordalGraph getSkeleton();
  
  public abstract int getNodeCount();
  
  public abstract int[] getIndex(String[] paramArrayOfString);
  
  public abstract boolean isMarked(int paramInt);
  
  public abstract void setPoten(int[] paramArrayOfInt);
  
  public abstract void clrBufferPoten();
  
  public abstract void clrLkgBufPoten();
  
  public abstract void clrLkgPoten();
  
  public abstract LJoinTreeM getJoinTree1();
  
  public abstract LJoinTreeM getLinkageTree2(int paramInt);
  
  public abstract void unifyPotential();
  
  public abstract void unifyPotential1();
  
  public abstract void addLkgBufPoten(int paramInt);
  
  public abstract String[] getFullyObserved(int paramInt);
  
  public abstract void enterFullyObserved(String[] paramArrayOfString);
  
  public abstract void showAgGraphLI();
}


/* Location:              /home/anali/INAOE/tesis/codigos/topub/exe/ww4.jar!/Network/IntAgtLI.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */