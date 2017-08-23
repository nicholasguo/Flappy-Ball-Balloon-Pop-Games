import java.util.*;
import java.io.*;

import javax.swing.*;
import javax.swing.Timer;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
public class flappyBird extends JFrame{
	public flappyBird(){
		setSize(400,550);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setResizable(false);
		add(new window());
	}
	public static void main(String[]args){
		EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {                
                JFrame ex = new flappyBird();
                ex.setVisible(true);                
            }
        });
	}
}
class window extends JPanel implements ActionListener{
	private static boolean space=false;
	private static boolean start=false;
	private static boolean end=false;
	private static boolean stop=false;
	private static boolean pass=false;
	private static boolean tpass=false;
	private static Integer score=0,nscore;
	private static Font f= new Font("Impact", Font.BOLD, 50);
	private Timer timer;
	public window(){
		addKeyListener(new TAdapter());
		setFocusable(true);
		timer = new Timer(30, this);
        timer.start();
		init();
	}
	public static ArrayList<Integer>height;
	public static ArrayList<Integer>location;
	public static int dot;
	public static int velocity;
	public static void init(){
		location=new ArrayList<Integer>();
		height=new ArrayList<Integer>();//left/right, up down
		dot=200;score=0;velocity=-20;stop=false;pass=false;tpass=false;
	}
	public static void hs(){
		BufferedReader ff=null;StringTokenizer st=null;
		try {
			ff = new BufferedReader(new FileReader("hs.txt"));
		} catch (FileNotFoundException e) {
		}
	    try {
			st = new StringTokenizer(ff.readLine());
		} catch (IOException e) {
		}
	    nscore=Math.max(score,Integer.parseInt(st.nextToken()));
	    try {
			ff.close();
		} catch (IOException e) {
		}
	    try {
			PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("hs.txt")));
			out.println(nscore);out.close();
		} catch (IOException e) {
		}
	}
	public void paint(Graphics g){
		g.setColor(Color.cyan);
		g.fillRect(0, 0, getWidth(), getHeight());
		if(!start&&!end){
			g.setFont(f);
			g.setColor(Color.white);
			g.drawString("FLAPPY BALL",60,50);
			g.setColor(Color.red);
			g.fillOval(60,dot,20,20);
		}
		if(start&&!end){
			g.setColor(Color.green);
			for(int i=0;i<location.size();i++){
				int b=location.get(i)-60;
				int c=height.get(i);
				g.fillRect(b,c,60,452);
				g.fillRect(b, 0, 60, c-110);	
			}
			g.setColor(Color.red);
			g.fillOval(60,dot,20,20);
			g.setFont(f);
			g.setColor(Color.white);
			g.drawString(score.toString(), 190,50);
		for(int i=0;i<location.size();i++){
			if(location.get(i)>60&&location.get(i)<140){
				pass=true;tpass=true;
				if(height.get(i)-110>dot||height.get(i)<dot)stop=true;
			}
		}}
		if(start&&end){
			hs();
			g.setFont(f);
			g.setColor(Color.white);
			g.drawString("GAME OVER",80,50);
			g.drawString("YOUR SCORE: "+score, 25, 250);
			g.drawString("HIGH SCORE: "+nscore, 25, 300);
		}
		if(!tpass&&pass&&start&&!stop){
			score++;pass=false;
		}
		tpass=false;
		if(dot>=540){
			end=true;
			dot=200;
		}
	}
	public void move(){
		if(!start)return;
		if(space&&!stop){
			velocity=-20;
		}
		velocity=Math.min(13,velocity+2);
		dot+=velocity;
		space=false;
		if(!stop){for(int i=0;i<location.size();i++){
			location.set(i, location.get(i)-5);
		}}
		if(location.isEmpty()||location.get(location.size()-1)<=300){
			int x=150+(int)(Math.random()*250);
			height.add(x);
			location.add(490);
		}
		if(location.get(0)<=0){
			location.remove(0);
			height.remove(0);
		}
	}
	private void gameOver(){
		if(space&&end){space=false;start=false;end=false;
		init();}
		}
	private void startGame(){
		if(!start&&space&&!end){
			start=true;space=false;
			init();
		}
	}
	public void actionPerformed(ActionEvent e) {
		gameOver();
		move();
		startGame();
		repaint();
	}
	private static class TAdapter extends KeyAdapter{
		@Override 
		public void keyPressed(KeyEvent e) {

            int key = e.getKeyCode();
            if ((key == KeyEvent.VK_SPACE) ) {space=true;
            }
        }
	}
}

