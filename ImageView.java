
import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

@SuppressWarnings("serial")
class Window extends JFrame implements ActionListener, ComponentListener
{
	JPanel p=new JPanel();
	JPanel p1=new JPanel();
	JButton[] b=new JButton[10];
	Icon[] ic=new Icon[10];
	BufferedImage buff;
	File f,dir;
	File[] files=new File[100];
	int index=0,current=-1,qty=0,WD,HT,count=0;
	public int WIDTH=1024,HEIGHT=1024;
	public Window()
	{	
		p.setSize(1280, 950);
		p.setIgnoreRepaint(true);
		JScrollPane jp=new JScrollPane();
		p.add(jp);
		p.setAutoscrolls(true);
		p1.setSize(1280, 50);
		p1.setLocation(300, 775);
		p1.setBorder(BorderFactory.createLineBorder(Color.black));
		String name[]={"open","first","last","prev","next","plus","minus","flip","delete","off"};
		for(int i=0;i<10;i++)
		{
		b[i]=new JButton();
		}
		ic[0]=new ImageIcon("img/open.png");
		ic[1]=new ImageIcon("img/first.png");
		ic[2]=new ImageIcon("img/last.png");
		ic[3]=new ImageIcon("img/prev.png");
		ic[4]=new ImageIcon("img/next.png");
		ic[5]=new ImageIcon("img/plus.png");
		ic[6]=new ImageIcon("img/minus.png");
		ic[7]=new ImageIcon("img/flip.png");
		ic[8]=new ImageIcon("img/delete.png");
		ic[9]=new ImageIcon("img/on-off.png");
		for(int i=0;i<10;i++)
		{
			b[i].setIcon(ic[i]);
			p1.add(b[i]);
			b[i].setActionCommand(name[i]);
			b[i].addActionListener(this);
			b[i].setEnabled(false);
		}
		b[0].setEnabled(true);
		b[9].setEnabled(true);
		JMenuBar m=new JMenuBar();
		JMenu file=new JMenu();
		JMenu edit =new JMenu();
		JMenu go=new JMenu();
		JMenu help=new JMenu();
		
		JMenuItem open=new JMenuItem();
		open.setText("Open");
		open.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
		open.setName("open");
		file.add(open);
		
		JMenuItem exit=new JMenuItem();
		exit.setText("Exit");
		exit.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F4, java.awt.event.InputEvent.ALT_MASK));
		exit.setName("exit");
		file.add(exit);	
		
		JMenuItem flip=new JMenuItem();
		flip.setText("Flip");
		flip.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_RIGHT, java.awt.event.InputEvent.SHIFT_DOWN_MASK));
		flip.setName("flip");
		edit.add(flip);
		
		JMenuItem plus=new JMenuItem();
		plus.setText("plus");
		plus.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_UP, java.awt.event.InputEvent.CTRL_MASK));
		plus.setName("plus");
		edit.add(plus);
		
		JMenuItem minus=new JMenuItem();
		minus.setText("minus");
		minus.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_DOWN, java.awt.event.InputEvent.CTRL_MASK));
		minus.setName("minus");
		edit.add(minus);
		
		JMenuItem next=new JMenuItem();
		next.setText("Next");
		next.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_RIGHT, 0));
		next.setName("next");
		go.add(next);
		
		JMenuItem prev=new JMenuItem();
		prev.setText("prev");
		prev.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_LEFT, 0));
		prev.setName("prev");
		go.add(prev);
		
		JMenuItem first=new JMenuItem();
		first.setText("first");
		first.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_HOME, 0));
		first.setName("first");
		go.add(first);
		
		JMenuItem last=new JMenuItem();
		last.setText("last");
		last.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_END, 0));
		last.setName("last");
		go.add(last);
		
		JMenuItem delete=new JMenuItem();
		delete.setText("Delete");
		delete.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_DELETE, 0));
		delete.setName("delete");
		edit.add(delete);
		
		first.setActionCommand("first");
		delete.setActionCommand("delete");
		open.setActionCommand("open");
		exit.setActionCommand("off");
		flip.setActionCommand("flip");
		next.setActionCommand("next");
		last.setActionCommand("last");
		prev.setActionCommand("prev");
		plus.setActionCommand("plus");
		minus.setActionCommand("minus");
		
		open.addActionListener(this);
		exit.addActionListener(this);
		flip.addActionListener(this);
		next.addActionListener(this);
		first.addActionListener(this);
		prev.addActionListener(this);
		last.addActionListener(this);
		plus.addActionListener(this);
		minus.addActionListener(this);
		delete.addActionListener(this);
		
		file.setText("File");
		edit.setText("Edit");
		go.setText("Go");
		help.setText("Help");
		m.add(file);
		m.add(edit);
		m.add(go);
		m.add(help);
		m.setLocation(0, 0);
		this.setJMenuBar(m);
		this.add(p);
		this.add(p1,BorderLayout.PAGE_END);
		this.addComponentListener(this);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setTitle("ImageViewer");
	}

	public void repaint(Graphics g)
	{
	}
	
	public void paintComponent(Graphics g)
	{
	}
	
	public void paint(Graphics g)
	{
		if(current>=0)
		open(files[current]);
	}
	

	public void componentResized(ComponentEvent e) {          
    }

	public void resize(BufferedImage img, int newW, int newH) {  
		int w = img.getWidth();  
		int h = img.getHeight();  
		BufferedImage dimg = new BufferedImage(newW, newH, img.getType());  
		Graphics2D g = dimg.createGraphics();  
		g.drawImage(img, 0, 0, newW, newH, 0, 0, w, h, null);  
		g.dispose();  
		buff=dimg;  
      }  
	public void open(File f)
	{
		try {
			 buff=ImageIO.read(f);
			Graphics g=this.p.getGraphics();
			g.setColor(Color.WHITE);
			g.fillRect(30, 0, 1280, 755);
			if(buff.getWidth()>1240)
			{
				if(buff.getHeight()>755)
					g.drawImage(buff, 30, 0, (WD=1240), (HT=755), null);

				else
					g.drawImage(buff, 30, 0, (WD=1240), (HT=buff.getHeight()-((buff.getWidth()-1240)/3)), null);
			}
			else if(buff.getHeight()>755)
			{
				if(buff.getWidth()>1240)
				g.drawImage(buff, 30, 0, (WD=1240), (HT=755), null);
				else
					g.drawImage(buff, 30, 0, (WD=buff.getWidth()-((buff.getHeight()-755)/3)), (HT=755), null);
			}
			else
			{
				WD=buff.getWidth();
				HT=buff.getHeight();
				g.drawImage(buff, 30, 0,null);
			}
			resize(buff,WD,HT);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	public void enableAll()
	{
		for(int i=0;i<10;i++)
		{
			if(!b[i].isEnabled())
				b[i].setEnabled(true);
		}
	}	

	public void zoom(File f,int q)
	{
		
		try {
			 buff=ImageIO.read(f);
			 int w=WD+q;
			int h=HT+q;
			Graphics g=this.p.getGraphics();
				g.setColor(Color.WHITE);
				g.fillRect(0, 0, 1280,775);
				g.drawImage(buff, 30, 0, w, h, null);
				resize(buff,w,h);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	public void actionPerformed(ActionEvent e) 
	{
		String cmd=e.getActionCommand();
		if(cmd!="open")	enableAll();
		if(cmd=="open")
		{
			JFileChooser chooser=new JFileChooser();
			chooser.showDialog(this, "Open a picture");
			f=chooser.getSelectedFile();
			qty=0; 
			open(f);
			enableAll();
			dir=f.getParentFile();
			File[] ls=dir.listFiles();
			for(File s: ls)
			{
				if(s.isFile())
				{
					if(s.equals(f))
						current=index;
					if((s.toString().endsWith("jpg"))||(s.toString().endsWith("png"))||(s.toString().endsWith("jpeg"))||(s.toString().endsWith("JPG")))
								files[index++]=s;
				}
			}
		}
		else if(cmd=="first")
		{
			current=0;
			open(files[0]);
			b[1].setEnabled(false);
			b[3].setEnabled(false);
		}
		else if(cmd=="last")
		{
			current=index-1;
			open(files[index-1]);
			b[2].setEnabled(false);
			b[4].setEnabled(false);
		}
		else if(cmd=="next")
		{
			open(files[++current]);
			if(current==index-1)
			{
				b[4].setEnabled(false);
				b[2].setEnabled(false);
			}
		}
		else if(cmd=="prev")
		{
			open(files[--current]);
			if(current==0)
			{
				b[3].setEnabled(false);
				b[1].setEnabled(false);
			}
		}
		else if(cmd=="plus")
		{
			if(qty>70)
				b[5].setEnabled(false);
			else
			{
			qty+=10;
			zoom(files[current],qty);
			}
			
		}
		else if(cmd=="minus")
		{
			if(qty<-70)
				b[6].setEnabled(false);
			else
			{
			qty-=10;
			zoom(files[current],qty);
			}
			
		}
		else if(cmd=="delete")
		{
			int more = JOptionPane.YES_OPTION;
			more = JOptionPane.showConfirmDialog(null, "Are you sure that you wanna delete?", "Confirmation", JOptionPane.YES_NO_OPTION);
			if(more==0)
				{
				files[current].delete();
				open(files[++current]);
				}
		}
		else if(cmd=="flip")
		{
			count++;
			if(count%2==1)
			{
			BufferedImage bi=new BufferedImage(WD,HT,buff.getColorModel().getTransparency());
			Graphics g=p.getGraphics();
			g.setColor(Color.white);
			g.fillRect(30, 0, 1280, 775);
			g.setColor(Color.black);
			g.drawRect(30, 0, WD, HT);
			g.drawImage(buff, 30, 0, WD+30, HT, WD, 0, 0, HT, null);
			g.dispose();  
			}
			else
			{
				Graphics g=p.getGraphics();
				g.setColor(Color.white);
				g.fillRect(30, 0, 1280, 775);
				g.setColor(Color.black);
				g.drawRect(30, 0, WD, HT);
				g.drawImage(buff,30, 0, WD, HT,null);
			}
			//b[7].setEnabled(false);
		}
		else if(cmd=="off")
			System.exit(NORMAL);
	}

	public void componentMoved(ComponentEvent e) {		
	}

	public void componentShown(ComponentEvent e) {
		//paint(getGraphics());
		
	}

	public void componentHidden(ComponentEvent e) {	
	}
}

public class ImageView
{
	public static void main(String[] arg)
	{
	Window w=new Window();
	w.setSize(1280,775);
	w.setLocation(0,25);
	w.setVisible(true);
	}
}
