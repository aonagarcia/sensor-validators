package Network;

public abstract interface Bridge
{
  public abstract void setVector(String[] paramArrayOfString);
  
  public abstract void setVector2(String[] paramArrayOfString);
  
  public abstract void setVector(boolean[] paramArrayOfBoolean);
  
  public abstract void setVector(float[] paramArrayOfFloat);
  
  public abstract void setVector(int[] paramArrayOfInt);
  
  public abstract void setVector2(int[] paramArrayOfInt);
  
  public abstract void setTable(float[] paramArrayOfFloat);
  
  public abstract void showNet();
  
  public abstract float[] getVector(int paramInt);
  
  public abstract String[] getVector2(int paramInt);
  
  public abstract String[] getVector3(int paramInt);
}


/* Location:              /home/anali/INAOE/tesis/codigos/topub/exe/ww4.jar!/Network/Bridge.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */