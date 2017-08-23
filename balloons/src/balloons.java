import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
public class balloons extends JFrame{
	public static ArrayList<balloon>ar;
	public balloons(){
		setSize(600,800);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setResizable(false);
		add(new panel());
	}
	public static void main(String[]args){
		EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {                
                JFrame ex = new balloons();
                ex.setVisible(true);                
            }
        });
	}
}

class panel extends JPanel implements ActionListener{
	private ArrayList<balloon>ar;
	private int targetx,targety;
	private static int score=0,time=0,combo=0;
	private static boolean left=false;
	private static boolean right=false;
	private static boolean down=false;
	private static boolean up=false;
	private static boolean space=false;
	private static boolean start=false;
	private static boolean end=false;
	private Timer timer;
	private static  Font small = new Font("Kristen ITC", Font.BOLD, 30);
    private static FontMetrics metr;
	public panel() {
		addKeyListener(new TAdapter());
		setFocusable(true);
		ar=new ArrayList<balloon>();
		metr = getFontMetrics(small);
		this.targetx=250;
		this.targety=250;
		timer = new Timer(9, this);
        timer.start();
	}
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		painty(g);
	}
	public void painty(Graphics g){
		Color[]c={Color.orange,Color.green,Color.blue,Color.yellow,Color.red,Color.pink,Color.magenta};
		g.setColor(Color.CYAN);
		g.fillRect(0, 0, getWidth(), getHeight());
		if(!start){ 
			this.targetx=250;
			this.targety=250;
			g.setColor(Color.black);
			small = new Font("Kristen ITC", Font.BOLD, 60);
			g.setFont(small);
			g.drawString("BALLOON POP",35, 60);
			small = new Font("Kristen ITC", Font.BOLD, 30);
			g.setFont(small);
			g.drawString("(PRESS SPACE TO START)",63, 750 );
			g.setColor(Color.yellow);
			g.fillOval(289,10,53,60);
			g.setColor(Color.blue);
			g.fillOval(233,10,53,60);
			g.setColor(Color.green);
			g.fillOval(468,10,53,60);
			g.setColor(Color.black);
			g.drawLine(315, 70, 315, 140);
			g.drawLine(259, 70, 259, 140);
			g.drawLine(494, 70, 494, 140);
			g.setColor(Color.red);
			g.drawLine(this.targetx-25, this.targety, this.targetx+25, this.targety);
			g.drawLine(this.targetx, this.targety-25, this.targetx, this.targety+25);
			g.drawOval(this.targetx-20, this.targety-20, 40, 40);
		}
		else if(time<6000){
			for(int i=0;i<ar.size();i++){
				g.setColor(c[ar.get(i).color]);
				g.fillOval(ar.get(i).x, ar.get(i).y, ar.get(i).sizex, ar.get(i).sizey);
				g.setColor(Color.black);
				g.drawLine(ar.get(i).x+ar.get(i).sizex/2,ar.get(i).y+ar.get(i).sizey,ar.get(i).x+ar.get(i).sizex/2,ar.get(i).y+ar.get(i).sizey+ar.get(i).string);
			}
			g.setColor(Color.red);
			g.drawLine(this.targetx-25, this.targety, this.targetx+25, this.targety);
			g.drawLine(this.targetx, this.targety-25, this.targetx, this.targety+25);
			g.drawOval(this.targetx-20, this.targety-20, 40, 40);
	        String msg = "Score: "+score;Double ttt=(60-time*1.0/100)/100;
	        String tme = "Time: 00:"+(ttt.toString()+"0").substring(2,4);
	        g.setColor(Color.white);
	        g.fillRect(0, 730, 600, 800);
	        g.setColor(Color.black);
	        g.setFont(small);
	        g.drawString(tme, 0, 760);
	        g.drawString(msg, (getWidth() - metr.stringWidth(msg)),760);
	        g.drawString("COMBO",200,760);
	        g.setColor(c[combo/4]);
	        small = new Font("Kristen ITC", Font.BOLD, 45);
	        g.setFont(small);
	        g.drawString("x"+(combo/4+1),370,760);
	        small = new Font("Kristen ITC", Font.BOLD, 30);
	        g.setFont(small);
			Toolkit.getDefaultToolkit().sync();
			g.dispose();
		}
		else {
			end=true;
			g.setColor(Color.black);
			small = new Font("Kristen ITC", Font.BOLD, 50);
			g.setFont(small);
			String msg="GAME OVER";
			g.drawString(msg,110, getHeight() / 2-20);
			msg="Score: "+score;
			g.drawString(msg,180, getHeight() / 2+55);
			small = new Font("Kristen ITC", Font.BOLD, 30);
			g.setFont(small);
			g.drawString("(PRESS SPACE)",160,750 );
		}
	}
	private void move(){
		if(start){
			time++;
			if (left) {
	            this.targetx -= 30;
	        }
	        if (right) {
	            this.targetx += 30;
	        }
	        if (up) {
	            this.targety -= 30;
	        }
	        if (down) {
	            this.targety += 30;
	        }
	        if(space){
	        	for(int i=0;i<ar.size();i++){
	        		if(targetx>ar.get(i).x&&targetx<ar.get(i).x+ar.get(i).sizex&&targety>ar.get(i).y&&targety<ar.get(i).y+ar.get(i).sizey){
	        			ar.remove(i);combo++;score+=(combo/4+1);
	        		}
	        	}
	        }
			if(ar.get(0).y+ar.get(0).sizey+ar.get(0).string<0){
				ar.remove(0);combo=0;
			}
			if(ar.get(ar.size()-1).y<getHeight()-80){
				ar.add(new balloon(getWidth(),getHeight()));
			}
			for(int i=0;i<ar.size();i++){
				int a=(int) (Math.random()*3-1.3);
				ar.get(i).y--;
				ar.get(i).x=Math.min(550, ar.get(i).x+a);
			}
		}
        right=false;left=false;up=false;down=false;space=false;
	}
	private void gameOver(){
		if(space&&time>6000&&end){space=false;start=false;end=false;}
		}
	private void startGame(){
		if(!start&&space&&!end){
			start=true;time=0;space=false;score=0;combo=0;
			ar.clear();
			ar.add(new balloon(getWidth(),getHeight()));
		}
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		gameOver();
		startGame();
		move();
		repaint();
	}
	private static class TAdapter extends KeyAdapter{
		@Override 
		public void keyPressed(KeyEvent e) {

            int key = e.getKeyCode();
            if ((key == KeyEvent.VK_LEFT) ) {left=true;
            }
            if ((key == KeyEvent.VK_RIGHT) ) {right=true;
            }
            if ((key == KeyEvent.VK_DOWN) ) {down=true;
            }
            if ((key == KeyEvent.VK_UP) ) {up=true;
            }
            if ((key == KeyEvent.VK_SPACE) ) {space=true;
            }
        }
	}
}
class balloon{
	public balloon(int width, int height){
		this.x=(int)(Math.random()*width);
		this.y=height;
		this.color=(int) (Math.random()*7);
		this.sizex=(int) (Math.random()*30)+40;
		this.sizey=6*sizex;this.sizey/=5;
		this.string=(int) (Math.random()*50)+70;
	}
	int x,y,color,sizex,sizey,string;
}