package Network;

public abstract interface IntAgtI
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
  
  public abstract int getNodeCount();
  
  public abstract int[] getIndex(String[] paramArrayOfString);
  
  public abstract JoinTreeM getLinkageTree1(int paramInt);
  
  public abstract JoinTreeM getJoinTree();
  
  public abstract boolean isMarked(int paramInt);
  
  public abstract void setBelief(int[] paramArrayOfInt);
  
  public abstract void unifyBelief();
  
  public abstract void setDumbLinkageBelief(int paramInt);
  
  public abstract int getCqByLabel(String paramString);
  
  public abstract void absorbThroughLinkage(int paramInt, int[] paramArrayOfInt, float[] paramArrayOfFloat1, float[] paramArrayOfFloat2);
}


/* Location:              /home/anali/INAOE/tesis/codigos/topub/exe/ww4.jar!/Network/IntAgtI.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */