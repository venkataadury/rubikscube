package kube;
import commons.*;
import java.awt.Color;

public class Data
{
	public static final char[] cnames=new char[] {'W','G','R','B','O','Y'};
	public static final Color[] colours=new Color[] {Color.white,Color.green,Color.red,Color.blue,new Color(255,70,0),Color.yellow};
	public static final Color NOCOLOUR=null;
	public static final char EMPTY=(char)0;
	
	//Cycles for Block
	public static final int FACE_CLOCK=0,FACE_ACLOCK=1,RIGHT_CLOCK=2,RIGHT_ACLOCK=3,UP_CLOCK=4,UP_ACLOCK=5;
	public static final int BACK_CLOCK=6,BACK_ACLOCK=7,LEFT_CLOCK=8,LEFT_ACLOCK=9,DOWN_CLOCK=10,DOWN_ACLOCK=11;
	public static final int LAST_MOVE_CODE=11;
	
	public static Color getColor(char ch)
	{
		for(int i=0;i<cnames.length;i++)
		{
			if(cnames[i]==ch)
				return colours[i];
		}
		return NOCOLOUR;
			
	}
	
	public static Block[][][] buildCube(int sz)
	{
		Block[][][] ret=new Block[sz][sz][sz];
		for(int i=0;i<sz;i++)
		{
			for(int j=0;j<sz;j++)
			{
				for(int k=0;k<sz;k++)
					ret[i][j][k]=new Block(sz,new int[] {i,j,k});
			}
		}
		return ret;
	}
	
	public static void performMove(String move,Cube c)
	{
		String lc=Y.cut(move,'-',1);
		String mv=Y.cut(move,'-',2);
		int[] loc=new int[3];
		loc[0]=X.ipi(lc.charAt(0)+"");
		loc[1]=X.ipi(lc.charAt(1)+"");
		loc[2]=X.ipi(lc.charAt(2)+"");
		int mC=getMoveCode(mv);
		if(mC==-1)
			throw new InvalidMoveStringException(mv);
		c.moveBlocks(mC,loc);
	}
	
	public static int getMoveCode(String mv)
	{
		char c1=mv.charAt(0);
		boolean b=X.toBoolean(X.ipi(mv.charAt(1)+""));
		switch(c1)
		{
			case 'F':
				if(b)
					return Data.FACE_CLOCK;
				else
					return Data.FACE_ACLOCK;
				
			case 'B':
				if(b)
					return Data.BACK_CLOCK;
				else
					return Data.BACK_ACLOCK;
				
			case 'U':
				if(b)
					return Data.UP_CLOCK;
				else
					return Data.UP_ACLOCK;
				
			case 'D':
				if(b)
					return Data.DOWN_CLOCK;
				else
					return Data.DOWN_ACLOCK;
				
			case 'R':
				if(b)
					return Data.RIGHT_CLOCK;
				else
					return Data.RIGHT_ACLOCK;
				
			case 'L':
				if(b)
					return Data.LEFT_CLOCK;
				else
					return Data.LEFT_ACLOCK;
				
			default:
				return -1;
		}
	}
}

class InvalidMoveException extends RuntimeException
{
	public InvalidMoveException()
	{
		X.sopln("Invalid Move","red");
	}
	public InvalidMoveException(int cycle)
	{
		this();
		X.sopln("Cycle code was: "+cycle,"yellow");
	}
	public InvalidMoveException(int cycle,Block obj)
	{
		this(cycle);
		X.sop("Called on block: ");
		X.sopln(obj,"red");
	}
}
class InvalidBlockLocationException extends RuntimeException
{
	public InvalidBlockLocationException()
	{
		X.sopln("Invalid block location passed.","red");
	}
	public InvalidBlockLocationException(int[] loc)
	{
		this();
		X.sop("Location","cyan");
		X.sopln(loc,"red");
	}
}

class InvalidMoveStringException extends RuntimeException
{
	public InvalidMoveStringException()
	{
		X.sopln("Invalid move string.","red");
	}
	public InvalidMoveStringException(String str)
	{
		this();
		X.sopln("Move string: "+str,"yellow");
	}
}
