package kube;
import commons.X;
import commons.Y;
import maths.Maths;
import java.awt.Color;
import java.io.*;

public class Cube
{
	protected Block[][][] cube;
	public int size;
	
	public Cube() {this(3);}
	public Cube(int dim)
	{
		size=dim;
		cube=new Block[size][size][size];
		setupCube();
	}
	public Cube(File f)throws FileNotFoundException
	{
		loadStatus(f);
	}
	
	private void setupCube()
	{
		/*
		 * Start: Front Top Right corner (0,0,0)
		 * 
		 * Right: X+ve
		 * Down: Y+ve
		 * Back: Z+ve
		 */
		cube=Data.buildCube(size);
	}
	
	public void reset() {setupCube();}
	public void schuffle()
	{
		for(int n=0;n<100;n++)
			performRandomMove();
	}
	private void performRandomMove()
	{
		int mC=Maths.randInt(Data.LAST_MOVE_CODE)-1;
		int[] pos=new int[] {Maths.randInt(size)-1,Maths.randInt(size)-1,Maths.randInt(size)-1};
		moveBlocks(mC,pos);
	}
	public Block getBlock(int[] loc) {return cube[loc[0]][loc[1]][loc[2]];}
	public Color getColorAt(int[] loc,int fc) {return Data.getColor(getBlock(loc).getColourCode(fc));}
	
	public void saveStatus(File f)
	{
		BufferedWriter bw=null;
		try
		{
			if(f.exists())
				f.delete();
			f.createNewFile();
			bw=new BufferedWriter(new FileWriter(f));
			bw.write(size+"\n");
			for(int k=0;k<size;k++)
			{
				for(int i=0;i<size;i++)
				{
					for(int j=0;j<size;j++)
						bw.write(cube[i][j][k].toString()+",");
					bw.write("\b|");
				}
				bw.write("\b\n");
			}
			bw.close();
			X.sopln("Saved to: "+f.getName(),"green");
		}
		catch(IOException e) {e.printStackTrace();return;}
	}
	public void loadStatus(File f)throws FileNotFoundException
	{
		String temp;
		BufferedReader br=null;
		try
		{
			if(!f.exists())
				throw new FileNotFoundException();
			br=new BufferedReader(new FileReader(f));
			size=X.ipi(br.readLine());
		String blk;
		cube=new Block[size][size][size];
		int k=0;
		while((temp=br.readLine())!=null)
		{
			for(int i=0;i<size;i++)
			{
				blk=Y.cut(temp,'|',i+1);
				for(int j=0;j<size;j++)
					cube[i][j][k]=new Block(Y.cut(blk,',',j+1));
			}
			k++;
		}
		}
		catch(IOException e) {e.printStackTrace();return;}
		X.sopln("Load Successful","green");
	}
	
	public void moveBlocks(int code,int[] loc)
	{
		int ind;
		int delta=size-1;
		int lim=size/2;
		int i,j,k,l;
		int x,y,z;
		Block temp;
		switch(code)
		{
			case Data.UP_CLOCK:
			case Data.DOWN_CLOCK:
				ind=loc[1];
				y=ind;
				for(int n=0;n<lim;n++)
				{
					k=n;
					for(i=n;i<size-n-1;i++)
					{
						temp=cube[i][ind][k];
						cube[i][ind][k]=cube[delta-k][ind][i];
						cube[i][ind][k].turn(code);
						cube[delta-k][ind][i]=cube[delta-i][ind][delta-k];
						cube[delta-k][ind][i].turn(code);
						cube[delta-i][ind][delta-k]=cube[k][ind][delta-i];
						cube[delta-i][ind][delta-k].turn(code);
						cube[k][ind][delta-i]=temp;
						cube[k][ind][delta-i].turn(code);
					}
				}
				break;
				
			case Data.UP_ACLOCK:
			case Data.DOWN_ACLOCK:
				ind=loc[1];
				y=ind;
				for(int n=0;n<lim;n++)
				{
					k=n;
					for(i=n;i<size-n-1;i++)
					{
						temp=cube[i][ind][k];
						cube[i][ind][k]=cube[k][ind][delta-i];
						cube[i][ind][k].turn(code);
						cube[k][ind][delta-i]=cube[delta-i][ind][delta-k];
						cube[k][ind][delta-i].turn(code);
						cube[delta-i][ind][delta-k]=cube[delta-k][ind][i];
						cube[delta-i][ind][delta-k].turn(code);
						cube[delta-k][ind][i]=temp;
						cube[delta-k][ind][i].turn(code);
					}
				}
				break;
				
			case Data.RIGHT_ACLOCK:
			case Data.LEFT_CLOCK:
				ind=loc[0];
				x=ind;
				for(int n=0;n<lim;n++)
				{
					j=n;
					for(k=n;k<size-n-1;k++)
					{
						temp=cube[ind][j][k];
						cube[ind][j][k]=cube[ind][k][delta-j];
						cube[ind][j][k].turn(code);
						cube[ind][k][delta-j]=cube[ind][delta-j][delta-k];
						cube[ind][k][delta-j].turn(code);
						cube[ind][delta-j][delta-k]=cube[ind][delta-k][j];
						cube[ind][delta-j][delta-k].turn(code);
						cube[ind][delta-k][j]=temp;
						cube[ind][delta-k][j].turn(code);
						//3,0 -> 0,0
					}
				}
				break;
				
			case Data.RIGHT_CLOCK:
			case Data.LEFT_ACLOCK:
				ind=loc[0];
				x=ind;
				for(int n=0;n<lim;n++)
				{
					j=n;
					for(k=n;k<size-n-1;k++)
					{
						temp=cube[ind][j][k];
						cube[ind][j][k]=cube[ind][delta-k][j];
						cube[ind][j][k].turn(code);
						cube[ind][delta-k][j]=cube[ind][delta-j][delta-k];
						cube[ind][delta-k][j].turn(code);
						cube[ind][delta-j][delta-k]=cube[ind][k][delta-j];
						cube[ind][delta-j][delta-k].turn(code);
						cube[ind][k][delta-j]=temp;
						cube[ind][k][delta-j].turn(code);
						//3,0 -> 0,0
					}
				}
				break;
				
			case Data.FACE_CLOCK:
			case Data.BACK_ACLOCK:
				ind=loc[2];
				z=ind;
				for(int n=0;n<lim;n++)
				{
					i=n;
					for(j=n;j<size-n-1;j++)
					{
						temp=cube[i][j][ind];
						cube[i][j][ind]=cube[j][delta-i][ind];
						cube[i][j][ind].turn(code);
						cube[j][delta-i][ind]=cube[delta-i][delta-j][ind];
						cube[j][delta-i][ind].turn(code);
						cube[delta-i][delta-j][ind]=cube[delta-j][i][ind];
						cube[delta-i][delta-j][ind].turn(code);
						cube[delta-j][i][ind]=temp;
						cube[delta-j][i][ind].turn(code);
						//3,0 -> 0,0
					}
				}
				break;
				
			case Data.FACE_ACLOCK:
			case Data.BACK_CLOCK:
				ind=loc[2];
				z=ind;
				for(int n=0;n<lim;n++)
				{
					i=n;
					for(j=n;j<size-n-1;j++)
					{
						temp=cube[i][j][ind];
						cube[i][j][ind]=cube[delta-j][i][ind];
						cube[i][j][ind].turn(code);
						cube[delta-j][i][ind]=cube[delta-i][delta-j][ind];
						cube[delta-j][i][ind].turn(code);
						cube[delta-i][delta-j][ind]=cube[j][delta-i][ind];
						cube[delta-i][delta-j][ind].turn(code);
						cube[j][delta-i][ind]=temp;
						cube[j][delta-i][ind].turn(code);
						//3,0 -> 0,0
					}
				}
				break;
				
			default:
				throw new InvalidMoveException(code);
		}
	}
}
