package kube;
import commons.*;
import maths.Maths;
import java.awt.*;
import java.awt.event.*;
import draw.*;
import draw.fullscreen.*;

public class CubeFrame extends FSWindow implements ActionListener,MouseMotionListener
{
	private final Cube cube;
	private final int size;
	private boolean start=false,savemode=false,rotatemode=false;
	public static final Color fgCol=Color.magenta;
	public static final int xC=250,yC=250,LSHIFTK=170,USHIFTK=325;
	public static int SIDE=400,USHIFT=230,LSHIFT=120,UTWIST=45,RTWIST=45;
	public static Rectangle fFace;
	public static Polygon uFace,rFace,lFace,dFace;
	
	//Debug: Start
	public static final java.io.File file=new java.io.File("op.txt");
	//Debug: end
	
	TextField tf1=new TextField("001-F0"); // xyz-<turn><clockwise=0/anti=1>
	TextField saveTF=new TextField("File Location/name");
	Button resetB=new Button("Reset"),execB=new Button("Execute Move"),schB=new Button("Schuffle"),rotateB=new Button("Rotate"),saveB=new Button("Save"),loadB=new Button("Load"),cancB=new Button("Cancel");
	
	private Point cP1=null,cP2=null;
	int[] lc;
	private int fC=-1,trt,tut;
	
	private int drawn=1;
	public Rectangle[] fCols,fRows;
	public Polygon[] uCols,uRows;
	public Polygon[] rCols,rRows;
	public Polygon[] lCols,lRows;
	public Polygon[] dCols,dRows;
	
	public CubeFrame() {this(3);}
	public CubeFrame(int cubesize)
	{
		super();
		cube=new Cube(cubesize);
		size=cubesize;
		setPolygons();
		addComponents();
		start=true;
		
	}
	private void addComponents()
	{
		this.addMouseMotionListener(this);
		tf1.setBounds(xC+SIDE+Math.abs(LSHIFT)+260,yC-100,100,40);
		tf1.addActionListener(this);
		tf1.setVisible(true);
		saveTF.setBounds(xC+SIDE+Math.abs(LSHIFT)+275,yC+250,120,30);
		saveTF.addActionListener(this);
		saveTF.setVisible(false);
		
		execB.setBounds(xC+SIDE+Math.abs(LSHIFT)+100,yC-100,150,40);
		execB.addActionListener(this);
		execB.setVisible(true);
		schB.setBounds(xC+SIDE+Math.abs(LSHIFT)+100,100,100,40);
		schB.addActionListener(this);
		schB.setVisible(true);
		
		resetB.setBounds(xC+SIDE+Math.abs(LSHIFT)+200,yC+50,70,30);
		resetB.addActionListener(this);
		resetB.setVisible(true);
		
		saveB.setBounds(xC+SIDE+Math.abs(LSHIFT)+200,yC+250,70,30);
		saveB.addActionListener(this);
		saveB.setVisible(true);
		loadB.setBounds(xC+SIDE+Math.abs(LSHIFT)+200,yC+290,70,30);
		loadB.addActionListener(this);
		loadB.setVisible(true);
		cancB.setBounds(xC+SIDE+Math.abs(LSHIFT)+275,yC+270,60,30);
		cancB.addActionListener(this);
		cancB.setVisible(false);
		
		rotateB.setBounds(xC+SIDE+Math.abs(LSHIFT)+300,yC+150,80,50);
		rotateB.addActionListener(this);
		rotateB.setVisible(true);
		
		this.add(tf1);
		this.add(saveTF);
		this.add(resetB);
		this.add(execB);
		this.add(schB);
		this.add(saveB);
		this.add(loadB);
		this.add(cancB);
		this.add(rotateB);
	}
	/*public void assign(CubeFrame f)
	{
		cube=f.cube;
		size=f.size;
		fCols=f.fCols; fRows=f.fRows;
		uCols=f.uCols; uRows=f.uRows;
		rCols=f.rCols; rRows=f.rRows;
	}*/
	private void setPolygons()
	{
		LSHIFT=(int)(LSHIFTK*Maths.sin(UTWIST));
		USHIFT=(int)(USHIFTK*Maths.sin(RTWIST));
		fFace=new Rectangle(xC,yC,SIDE,SIDE);
		uFace=new Polygon(new int[] {xC,xC+LSHIFT,xC+LSHIFT+SIDE,xC+SIDE},new int[] {yC,yC-USHIFT,yC-USHIFT,yC},4);
		rFace=new Polygon(new int[] {xC+LSHIFT+SIDE,xC+SIDE,xC+SIDE,xC+LSHIFT+SIDE},new int[] {yC-USHIFT,yC,yC+SIDE,yC-USHIFT+SIDE},4);
		lFace=new Polygon(new int[] {xC+LSHIFT,xC,xC,xC+LSHIFT},new int[] {yC-USHIFT,yC,yC+SIDE,yC-USHIFT+SIDE},4);
		dFace=new Polygon(new int[] {xC,xC+LSHIFT,xC+LSHIFT+SIDE,xC+SIDE},new int[] {yC+SIDE,yC-USHIFT+SIDE,yC-USHIFT+SIDE,yC+SIDE},4);
		
		fRows=new Rectangle[size];
		fCols=new Rectangle[size];
		uCols=new Polygon[size];
		uRows=new Polygon[size];
		dCols=new Polygon[size];
		dRows=new Polygon[size];
		rCols=new Polygon[size];
		rRows=new Polygon[size];
		lRows=new Polygon[size];
		lCols=new Polygon[size];
		
		int cW=SIDE/size;
		int hW=LSHIFT/size;
		int uW=USHIFT/size;
		for(int i=0;i<size;i++)
		{
			fCols[i]=new Rectangle(xC+cW*i,yC,cW,SIDE);
			fRows[i]=new Rectangle(xC,yC+cW*i,SIDE,cW);
			uCols[i]=new Polygon(new int[] {xC+cW*i,xC+LSHIFT+cW*i,xC+LSHIFT+cW*(i+1),xC+cW*(i+1)},new int[] {yC,yC-USHIFT,yC-USHIFT,yC},4);
			uRows[i]=new Polygon(new int[] {xC+hW*i,xC+hW*(i+1),xC+hW*(i+1)+SIDE,xC+hW*i+SIDE},new int[] {yC-uW*i,yC-uW*(i+1),yC-uW*(i+1),yC-uW*i},4);
			dCols[i]=new Polygon(new int[] {xC+cW*i,xC+LSHIFT+cW*i,xC+LSHIFT+cW*(i+1),xC+cW*(i+1)},new int[] {yC+SIDE,yC-USHIFT+SIDE,yC-USHIFT+SIDE,yC+SIDE},4);
			dRows[i]=new Polygon(new int[] {xC+hW*i,xC+hW*(i+1),xC+hW*(i+1)+SIDE,xC+hW*i+SIDE},new int[] {yC-uW*i+SIDE,yC-uW*(i+1)+SIDE,yC-uW*(i+1)+SIDE,yC-uW*i+SIDE},4);
			rRows[i]=new Polygon(new int[] {xC+SIDE,xC+SIDE,xC+SIDE+LSHIFT,xC+SIDE+LSHIFT},new int[] {yC+cW*i,yC+cW*(i+1),yC+cW*(i+1)-USHIFT,yC+cW*i-USHIFT},4);
			rCols[i]=new Polygon(new int[] {xC+SIDE+hW*i,xC+SIDE+hW*(i+1),xC+SIDE+hW*(i+1),xC+SIDE+hW*i},new int[] {yC-uW*i,yC-uW*(i+1),yC-uW*(i+1)+SIDE,yC-uW*i+SIDE},4);
			lRows[i]=new Polygon(new int[] {xC,xC,xC+LSHIFT,xC+LSHIFT},new int[] {yC+cW*i,yC+cW*(i+1),yC+cW*(i+1)-USHIFT,yC+cW*i-USHIFT},4);
			lCols[i]=new Polygon(new int[] {xC+hW*i,xC+hW*(i+1),xC+hW*(i+1),xC+hW*i},new int[] {yC-uW*i,yC-uW*(i+1),yC-uW*(i+1)+SIDE,yC-uW*i+SIDE},4);
		}
	}
	public void draw(Graphics g)
	{
		if(!start)
			return;
		drawCube((Graphics2D)g);
	}
	
	private void drawCube(Graphics2D g)
	{
		//cube.getBlock(new int[] {2,0,0}).turn(Data.RIGHT_CLOCK);
		//setPolygons();
		drawCubeColours(g);
		g.setColor(fgCol);
		g.drawRect(xC,yC,SIDE,SIDE);
		if(LSHIFT>0)
			g.drawPolygon(rFace);
		else
			g.drawPolygon(lFace);
		if(USHIFT>0)
			g.drawPolygon(uFace);
		else
			g.drawPolygon(dFace);
		for(int i=0;i<size;i++)
		{
			g.draw(fCols[i]);
			g.draw(fRows[i]);
			if(USHIFT>0)
			{
				g.draw(uCols[i]);
				g.draw(uRows[i]);
			}
			else
			{
				g.draw(dRows[i]);
				g.draw(dCols[i]);
			}
			
			if(LSHIFT>0)
			{
				g.draw(rRows[i]);
				g.draw(rCols[i]);
			}
			else
			{
				g.draw(lRows[i]);
				g.draw(lCols[i]);
			}
		}
		/*g.setColor(Color.blue);*/
	}
	
	private void drawCubeColours(Graphics2D g)
	{
		Shape tF=null;
		Color clr=null;
		int[] loc;
		int fcA;
		for(int i=0;i<size;i++)
		{
			for(int j=0;j<size;j++)
			{
				for(int k=0;k<size;k++)
				{
					for(fcA=0;fcA<6;fcA++)
					{
						loc=new int[] {i,j,k};
						tF=getAreaOf(loc,fcA,false);
						if(tF==null)
							continue;
						clr=cube.getColorAt(loc,fcA);
						if(clr==null)
							continue;
						g.setColor(clr);
						g.fill(tF);
					}
				}
			}
		}
	}
	public Shape getAreaOf(int[] loc,int fc) {return getAreaOf(loc,fc,true);}
	public Shape getAreaOf(int[] loc,int fc,boolean warn)
	{
		if(size<=0 || fc<0)
			return null;
		if(loc.length<3)
			throw new InvalidBlockLocationException(loc);
		if((fc==3 && LSHIFT<0) || (fc==1 && LSHIFT>0) || (fc==0 && USHIFT<0) || (fc==5 && USHIFT>0) || (fc==4))
		{
			if(warn)
				X.sopln("Warning: Face requested="+fc+".\tNot present on screen!!","yellow");
			return null;
		}
		if(loc[0]!=size-1 && loc[1]!=0 && loc[2]!=0 && loc[0]!=0 && loc[1]!=size-1)
		{
			if(warn)
				X.sopln("Warning: Block requested=("+loc[0]+","+loc[1]+","+loc[2]+").\tNot present on screen!!","yellow");
			return null;
		}
		if((loc[0]!=size-1 && fc==3) || (loc[1]!=0 && fc==0) || (loc[2]!=0 && fc==2) || (loc[0]!=0 && fc==1) || (loc[1]!=size-1 && fc==5))
		{
			if(warn)
				X.sopln("Warning: Block requested=("+loc[0]+","+loc[1]+","+loc[2]+"). Face requested="+fc+"\tNot present on screen!!","yellow");
			return null;
		}
		Polygon r,c;
		Polygon ret=new Polygon();
		int xSR,xSC,ySR,ySC;
		if(fc==3 || fc==1) 
		{
			if(fc==3)
			{
				r=rRows[loc[1]];
				c=rCols[loc[2]];
			}
			else
			{
				r=lRows[loc[1]];
				c=lCols[loc[2]];
			}
			xSR=(r.xpoints[2]-r.xpoints[0])/size; //slant width
			ySR=(c.ypoints[1]-c.ypoints[0]); //
			xSC=(r.xpoints[1]-r.xpoints[0])/size;
			ySC=(c.ypoints[2]-c.ypoints[1])/size;
			
			
			// tl,bl,br,tr
			ret.addPoint(r.xpoints[0]+xSR*loc[2],c.ypoints[0]+ySC*loc[1]);
			ret.addPoint(ret.xpoints[0]+xSC,ret.ypoints[0]+ySC);
			ret.addPoint(ret.xpoints[1]+xSR,ret.ypoints[1]+ySR);
			ret.addPoint(ret.xpoints[2]-xSC,ret.ypoints[2]-ySC);
			return ret;
		}
		if(fc==0 || fc==5)
		{
			if(fc==0)
			{
				r=uRows[loc[2]];
				c=uCols[loc[0]];
			}
			else
			{
				r=dRows[loc[2]];
				c=dCols[loc[0]];
			}
			//xSR=(r.xpoints[3]-r.xpoints[0])/size;
			xSR=SIDE/size;
			ySC=USHIFT/size;
			xSC=LSHIFT/size;
			
			//ret.addPoint(c.xpoints[0]+xSR*loc[0]+xSC*loc[2],yC-ySC*loc[2]);
			ret.addPoint(r.xpoints[0]+xSR*loc[0],c.ypoints[3]-ySC*loc[2]);
			ret.addPoint(ret.xpoints[0]+xSR,ret.ypoints[0]);
			ret.addPoint(ret.xpoints[1]-LSHIFT/size,ret.ypoints[1]+ySC);
			ret.addPoint(ret.xpoints[2]-xSR,ret.ypoints[2]);
			ret.translate(xSC,-ySC);
			return ret;
		}
		if(fc==2)
		{
			xSR=SIDE/size;
			ySR=SIDE/size;
			return new Rectangle(xC+loc[0]*xSR,yC+loc[1]*ySR,xSR,ySR);
		}
		return null;
	}
	public void mousePressed(MouseEvent e) 
	{
		if(savemode)
			return;
		cP1=e.getPoint();
		lc=getLocFrom(cP1);
		trt=RTWIST;
		tut=UTWIST;
	}
	public void mouseReleased(MouseEvent e) 
	{
		if(savemode)
			return;
		cP2=e.getPoint();
		
		if(lc[0]<0 || lc[1]<0 || lc[2] <0)
		{
			if(!rotatemode) {}
			else
				moveEntireCube(cP2.x-cP1.x,cP2.y-cP1.y);
			update();
			return;
		}
		int mC=getMoveCode(lc,cP2.x-cP1.x,cP2.y-cP1.y);
		cube.moveBlocks(mC,lc);
		cP1=cP2=null;
		update();
	}
	public void mouseClicked(MouseEvent e)
	{
		X.sopln("Clicked: "+e.getPoint());
		update();
	}
	public void mouseMoved(MouseEvent e) {}
	public void mouseDragged(MouseEvent e)
	{
		if(rotatemode || savemode)
			return;
		int dy,dx;
		cP2=e.getPoint();
		if(lc[0]<0 || lc[1]<0 || lc[2] <0)
		{
			dy=cP2.y-cP1.y; dx=cP2.x-cP1.x;
			/*if(Math.abs(dy)>Math.abs(dx))
			{
				UTWIST=tut+dx/10;
				
			}*/
			UTWIST=tut+dx;
			RTWIST=trt-dy;
			if(RTWIST>45)
				RTWIST=45;
			if(UTWIST>90)
				UTWIST=90;
			if(UTWIST<-90)
				UTWIST=-90;
			if(RTWIST<-45)
				RTWIST=-45;
			setPolygons();
			update();
			return;
		}
	}
	
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource()==tf1 || e.getSource()==execB)
		{
			X.sopln("Command: "+tf1.getText());
			Data.performMove(tf1.getText(),cube);
		}
		else if(e.getSource()==resetB)
			cube.reset();
		else if(e.getSource()==schB)
			cube.schuffle();
		else if(e.getSource()==rotateB)
		{
			rotatemode=!rotatemode;
			if(rotatemode)
				rotateB.setLabel("Twist");
			else
				rotateB.setLabel("Rotate");
		}
		else if(e.getSource()==saveB || e.getSource()==saveTF)
		{
			savemode=!savemode;
			saveTF.setVisible(savemode);
			cancB.setVisible(savemode);
			if(!savemode)
			{
				if(saveB.isVisible())
				{
					cube.saveStatus(new java.io.File(saveTF.getText()));
					javax.swing.JOptionPane.showMessageDialog(this,"Save Successful to file: "+saveTF.getText());
				}
				else
				{
					try {cube.loadStatus(new java.io.File(saveTF.getText()));} catch(java.io.FileNotFoundException ex) {ex.printStackTrace(); return;}
					saveB.setVisible(true);
				}
			}
		}
		else if(e.getSource()==loadB)
		{
			savemode=!savemode;
			cancB.setVisible(savemode);
			saveTF.setVisible(savemode);
			saveB.setVisible(!savemode);
			if(!savemode)
				try {cube.loadStatus(new java.io.File(saveTF.getText()));} catch(java.io.FileNotFoundException ex) {ex.printStackTrace(); return;}
		}
		else if(e.getSource()==cancB)
		{
			saveB.setVisible(true);
			saveTF.setVisible(false);
			savemode=false;
			cancB.setVisible(false);
		}
		update();
	}
	
	
	public int[] getLocFrom(Point pt)
	{
		fC=-1;
		int[] ret=new int[] {-1,-1,-1};
		for(int i=0;i<size;i++)
		{
			if(rRows[i].contains(pt) && LSHIFT>0)
			{
				ret[0]=size-1;
				ret[1]=i;
				fC=3;
				break;
			}
			if(uRows[i].contains(pt) && USHIFT>0)
			{
				ret[1]=0;
				ret[2]=i; //Check
				fC=0;
				break;
			}
			if(fRows[i].contains(pt))
			{
				ret[2]=0;
				ret[1]=i;
				fC=2;
				break;
			}
			if(lRows[i].contains(pt) && LSHIFT<0)
			{
				ret[0]=0;
				ret[1]=i;
				fC=1;
				break;
			}
			if(dRows[i].contains(pt) && USHIFT<0)
			{
				ret[1]=size-1;
				ret[2]=i;
				fC=5;
				break;
			}
		}
		for(int i=0;i<size;i++)
		{
			if(rCols[i].contains(pt) && LSHIFT>0)
			{
				ret[2]=i;
				break;
			}
			if(uCols[i].contains(pt) && USHIFT>0)
			{
				ret[0]=i;
				break;
			}
			if(fCols[i].contains(pt))
			{
				ret[0]=i;
				break;
			}
			if(lCols[i].contains(pt) && LSHIFT<0)
			{
				ret[2]=i;
				break;
			}
			if(dCols[i].contains(pt) && USHIFT<0)
			{
				ret[0]=i;
				break;
			}
		}
		return ret;
	}
	
	public int getMoveCode(int[] pos,double dx,double dy)
	{
		if(fC==3 || fC==1)
		{
			dy=dy/(double)USHIFT;
			dx=dx/(double)LSHIFT;
		}
		if(Math.abs(dy)>Math.abs(dx))
		{
			switch(fC)
			{
				case 5:
				case 0:
					if(dy<0)
						return Data.RIGHT_CLOCK;
					else
						return Data.RIGHT_ACLOCK;
				case 2:
					if(dy<0)
						return Data.RIGHT_CLOCK;
					else
						return Data.RIGHT_ACLOCK;
				case 1:
					dy=-dy;
				case 3:
					if(USHIFT<0)
						dy=-dy;
					if(dy<0)
						return Data.FACE_ACLOCK;
					else
						return Data.FACE_CLOCK;
				default:
					return -1;
			}
		}
		else
		{
			switch(fC)
			{
				case 5:
					dx=-dx;
				case 0:
					if(dx<0)
						return Data.FACE_ACLOCK;
					else
						return Data.FACE_CLOCK;
				case 2:
					if(dx<0)
						return Data.UP_CLOCK;
					else
						return Data.UP_ACLOCK;
				case 1:
					dx=-dx;
				case 3:
					if(dx<0)
						return Data.UP_CLOCK;
					else
						return Data.UP_ACLOCK;
				default:
					return -1;
			}
		}
	}
	
	public void moveEntireCube(int dx,int dy)
	{
		if(Math.abs(Math.abs(dy)-Math.abs(dx))<15)
		{
			X.sopln("Neglected","cyan");
			return;
		}
		if(Math.abs(dy)>Math.abs(dx))
		{
			if(cP1.x>xC && cP1.x<xC+SIDE+LSHIFT)
				turnRAxis(dy); // No Prob
			else
			{
				if(cP1.x<xC)
					turnFAxis(-dy);
				else
					turnFAxis(dy);
			}
		}
		else
		{
			if(cP1.y<yC-USHIFT)
				turnFAxis(dx);
			else if(cP1.y>yC+SIDE)
				turnFAxis(-dx);
			else
				turnUAxis(dx); // No Prob
		}
	}
	
	public void turnFAxis(int d)
	{
		int[] tlc;
		int mC=(d>0)?Data.FACE_CLOCK:Data.FACE_ACLOCK;
		for(int k=0;k<size;k++)
		{
			tlc=new int[] {0,0,k};
			cube.moveBlocks(mC,tlc);
		}
		//X.sopln("Turned Face-Axis\n");
	}
	public void turnRAxis(int d)
	{
		int[] tlc;
		int mC=(d>0)?Data.RIGHT_ACLOCK:Data.RIGHT_CLOCK;
		for(int i=0;i<size;i++)
		{
			tlc=new int[] {i,0,0};
			cube.moveBlocks(mC,tlc);
		}
		//X.sopln("Turned Right-Axis\n");
	}
	public void turnUAxis(int d)
	{
		int[] tlc;
		int mC=(d>0)?Data.UP_ACLOCK:Data.UP_CLOCK;
		for(int j=0;j<size;j++)
		{
			tlc=new int[] {0,j,0};
			cube.moveBlocks(mC,tlc);
		}
		//X.sopln("Turned Up-Axis\n");
	}
}
