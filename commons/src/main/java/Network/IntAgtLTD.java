package Network;

import java.awt.Point;
import java.awt.Rectangle;

public abstract interface IntAgtLTD
  extends IntAgtT
{
  public abstract void pickAddFillIn(String[][] paramArrayOfString, int paramInt);
  
  public abstract void setMsgGraph();
  
  public abstract void setChordalGraph(int paramInt, int[] paramArrayOfInt);
  
  public abstract void setChordalGraph();
  
  public abstract String[][] nodesToLinksLabel(int paramInt, int[] paramArrayOfInt);
  
  public abstract void setJoinTree(Rectangle paramRectangle);
  
  public abstract void setMessageJF();
  
  public abstract void setMessageJF(int paramInt);
  
  public abstract void showAgGraphLTD();
  
  public abstract void addFillinToMsgGraph(int paramInt, Point paramPoint);
  
  public abstract void addFillinToLocalGraph(Point paramPoint);
}


/* Location:              /home/anali/INAOE/tesis/codigos/topub/exe/ww4.jar!/Network/IntAgtLTD.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */