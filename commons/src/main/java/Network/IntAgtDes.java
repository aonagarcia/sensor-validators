package Network;

public abstract interface IntAgtDes
{
  public abstract int[] getDsepsetLocalIndex(int paramInt);
  
  public abstract int[] getDesignParSz(int[] paramArrayOfInt);
  
  public abstract float[] getDsepsetUtil(int paramInt);
  
  public abstract void absorbDesignPot(int[] paramArrayOfInt, float[] paramArrayOfFloat);
  
  public abstract int[] setOptDesign(int[] paramArrayOfInt1, int[] paramArrayOfInt2);
  
  public abstract int[] getOptDesign(int[] paramArrayOfInt);
  
  public abstract void showDesignPot();
  
  public abstract void showOptDivDesign();
  
  public abstract void seeOptDivDesign();
}


/* Location:              /home/anali/INAOE/tesis/codigos/topub/exe/ww4.jar!/Network/IntAgtDes.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */