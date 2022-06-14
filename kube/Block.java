package kube;
import commons.X;
import maths.Maths;
import java.awt.Color;

public class Block
{
	public char[] colours=new char[] {(char)0,(char)0,(char)0,(char)0,(char)0,(char)0};
	//private int[] pos=new int[] {0,0,0};
	private char temp;
	
	public Block() {}
	public Block(char[] dat) {colours=dat;}
	public Block(int size,int[] pos)
	{
		int mInd=size-1;
		if((pos[0]*pos[1]*pos[2])!=0 && (pos[0]!=mInd && pos[1]!=mInd && pos[2]!=mInd))
			return;
		if(pos[0]==0)
			colours[1]=Data.cnames[1];
		else if(pos[0]==mInd)
			colours[3]=Data.cnames[3];
		if(pos[1]==0)
			colours[0]=Data.cnames[0];
		else if(pos[1]==mInd)
			colours[5]=Data.cnames[5];
		if(pos[2]==0)
			colours[2]=Data.cnames[2];
		else if(pos[2]==mInd)
			colours[4]=Data.cnames[4];
	}
	public Block(String in)
	{
		colours=new char[6];
		colours[0]=in.charAt(0);
		colours[1]=in.charAt(1);
		colours[2]=in.charAt(2);
		colours[3]=in.charAt(3);
		colours[4]=in.charAt(4);
		colours[5]=in.charAt(5);
	}
	
	public String toString() {return new String(colours);}
	
	public void turn(int cycle)
	{
		switch(cycle)
		{
			case Data.LEFT_CLOCK:
				turnBlock(Data.RIGHT_ACLOCK);
				return;
			case Data.LEFT_ACLOCK:
				turnBlock(Data.RIGHT_CLOCK);
				return;
			case Data.BACK_CLOCK:
				turnBlock(Data.FACE_ACLOCK);
				return;
			case Data.BACK_ACLOCK:
				turnBlock(Data.FACE_CLOCK);
				return;
			default:
				turnBlock(cycle);
		}
	}
	private void turnBlock(int cycle)
	{
		switch(cycle)
		{
			case Data.FACE_CLOCK:
				temp=colours[0];
				colours[0]=colours[1];
				colours[1]=colours[5];
				colours[5]=colours[3];
				colours[3]=temp;
				break;
			case Data.FACE_ACLOCK:
				temp=colours[0];
				colours[0]=colours[3];
				colours[3]=colours[5];
				colours[5]=colours[1];
				colours[1]=temp;
				break;
			case Data.RIGHT_CLOCK:
				temp=colours[0];
				colours[0]=colours[2];
				colours[2]=colours[5];
				colours[5]=colours[4];
				colours[4]=temp;
				break;
			case Data.RIGHT_ACLOCK:
				temp=colours[0];
				colours[0]=colours[4];
				colours[4]=colours[5];
				colours[5]=colours[2];
				colours[2]=temp;
				break;
			case Data.DOWN_CLOCK:
			case Data.UP_CLOCK:
				temp=colours[2];
				colours[2]=colours[3];
				colours[3]=colours[4];
				colours[4]=colours[1];
				colours[1]=temp;
				break;
			case Data.DOWN_ACLOCK:
			case Data.UP_ACLOCK:
				temp=colours[2];
				colours[2]=colours[1];
				colours[1]=colours[4];
				colours[4]=colours[3];
				colours[3]=temp;
				break;
			default:
				throw new InvalidMoveException(cycle,this);
		}
	}
	/*public void setPos(int[] loc) {pos=loc;}
	public int[] getPos() {return pos;}*/
	public char getColourCode(int fc) {return colours[fc];}
	
	public int countColouredFaces()
	{
		int sum=0;
		for(int i=0;i<colours.length;i++)
		{
			if(colours[i]!=(char)0)
				sum++;
		}
		return sum;
	}
	
	//Identification
	public boolean isEmpty() {return (countColouredFaces()==0);}
	public boolean isCenter() {return (countColouredFaces()==1);}
	public boolean isEdge() {return (countColouredFaces()==2);}
	public boolean isCorner() {return (countColouredFaces()==3);}
}
