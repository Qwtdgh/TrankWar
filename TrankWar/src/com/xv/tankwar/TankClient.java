package com.xv.tankwar;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import java.util.Random;
import java.util.ArrayList;

public class TankClient extends Frame {


	public static final int GAME_X = 0, GAME_Y = 0;
    public static final int HEIGHT = 1000, WIDTH = 1500, G_END = 25;
    public static final int GOODTIME = 3*1000;
    public int G_TIME ;
    
    public int GOOD_flag = 1;  

	public int ENEMY_SIZE = 2;
    public int CNT = 3;
    public static Random r = new Random();
    long startTime = System.currentTimeMillis();
    long endTime = System.currentTimeMillis();
    private Color backColor = new Color(0x65, 0xc2, 0x94);
    private Image offScreenImage = null;
    
    public Tank heroTank1 = new Tank((GAME_X + Tank.WIDTH-15), GAME_Y + HEIGHT - Tank.HEIGHT, ENEMY_SIZE, true, true, Direction.STOP, this);
    public Tank heroTank2 = new Tank(GAME_X + WIDTH - Tank.WIDTH -20, GAME_Y + HEIGHT - Tank.HEIGHT, ENEMY_SIZE, true, false, Direction.STOP, this);
    



    public List<Missile> missiles = new ArrayList<Missile>();
    public List<Explode> explodes = new ArrayList<Explode>();
    public List<Tank> enemyTanks = new ArrayList<Tank>();
    public List<Wall> walls = new ArrayList<Wall>();
//    public List<Wall> wallss = new ArrayList<Wall>();
    public Blood blood = new Blood();

    /**
     * default constructor, generate walls
     */
    public TankClient() {
    	
        Wall w1 = new Wall(100, 200, 15, 100);
        Wall w2 = new Wall(200, 250, 15, 100);
        Wall w3 = new Wall(300, 270, 15, 100);
        Wall w4 = new Wall(400, 300, 100, 15);
        Wall w5 = new Wall(700, 400, 100, 15);
        Wall w6 = new Wall(800, 500, 100, 15);
        Wall w7 = new Wall(100, 200, 15, 100);
        Wall w8 = new Wall(200, 250, 15, 100);
        Wall w9 = new Wall(300, 270, 15, 100);
        Wall w10 = new Wall(400, 300, 100, 15);
        Wall w11 = new Wall(700, 400, 100, 15);
        Wall w12 = new Wall(800, 500, 100, 15);
  
        this.walls.add(w1);
        this.walls.add(w2);
        this.walls.add(w3);
        this.walls.add(w4);
        this.walls.add(w5);
        this.walls.add(w6);
        this.walls.add(w7);
        this.walls.add(w8);
        this.walls.add(w9);
        this.walls.add(w10);
        this.walls.add(w11);
        this.walls.add(w12);
        /*this.wallss.add(w1);
        this.wallss.add(w2);
        this.wallss.add(w3);
        this.wallss.add(w4);
        this.wallss.add(w5);
        this.wallss.add(w6);*/
        
        																			//建墙
        
    }

    /**
     * generate enemy tanks
     */
    public int getGOOD_flag() {
		return GOOD_flag;
	}

	public void setGOOD_flag(int gOOD_flag) {
		GOOD_flag = gOOD_flag;
	}
    public void generateEnemys() {
    	if(((r.nextInt(10) >= 7 &&  ENEMY_SIZE - 1 <= 10) || (r.nextInt(10) >= 5 && ENEMY_SIZE - 1 >= 11)) ) {
    		blood.freshLive();
    	}						
    					//重新加载血包
    	
    	
    	GOOD_flag = 1;
    	startTime = System.currentTimeMillis();			//开始无敌时间
    	
    	if(ENEMY_SIZE - 1 <= 5) {
    		G_TIME = 25 * 1000;
        for (int i = 0; i < ENEMY_SIZE/2 ; i++) {
            int x, y;
            y = 50; 
            x = -(i - ENEMY_SIZE/4) * Tank.WIDTH + WIDTH/2;      
            Tank enemyTank = new Tank(x, y, ENEMY_SIZE, false, false, Direction.D, this);
            enemyTanks.add(enemyTank);
        }
    	}
    	else if (ENEMY_SIZE - 1 >= 6 && ENEMY_SIZE - 1 <= 10) {
    		G_TIME = 35 * 1000;
    		for (int i = 0; i < ENEMY_SIZE/3 ; i++) {
                int x, y;
                y = 50; 
                x = -(i - ENEMY_SIZE/6) * Tank.WIDTH + WIDTH/2;      
                Tank enemyTank = new Tank(x, y, ENEMY_SIZE, false, false, Direction.D, this);
                enemyTanks.add(enemyTank);
            }
    		for (int i = 0; i < ENEMY_SIZE/3 ; i++) {
                int x, y;
                y = HEIGHT - 50; 
                x = -(i - ENEMY_SIZE/6) * Tank.WIDTH + WIDTH/2;      
                Tank enemyTank = new Tank(x, y, ENEMY_SIZE, false, false, Direction.U, this);
                enemyTanks.add(enemyTank);
            }
    	}
    	else {
    		G_TIME = (25 + (ENEMY_SIZE - 1) * 2) * 1000;
    		for (int i = 0; i <= CNT/2 ; i++) {
                int x, y;
                y = 50; 
                x = -(i-CNT/4) * Tank.WIDTH + WIDTH/2;      
                Tank enemyTank = new Tank(x, y, ENEMY_SIZE, false, false, Direction.D, this);
                enemyTanks.add(enemyTank);
            }
    		for (int i = 0; i <= CNT/2 ; i++) {
                int x, y;
                y = HEIGHT - 50; 
                x = -(i-CNT/4) * Tank.WIDTH + WIDTH/2;      
                Tank enemyTank = new Tank(x, y, ENEMY_SIZE, false, false, Direction.U, this);
                enemyTanks.add(enemyTank);
            }
    		for (int i = 0; i <= CNT/2 ; i++) {
                int x, y;
                y = -(i-CNT/4) * Tank.WIDTH + HEIGHT/2; 
                x = 50;      
                Tank enemyTank = new Tank(x, y, ENEMY_SIZE, false, false, Direction.R, this);
                enemyTanks.add(enemyTank);
            }
    		for (int i = 0; i <= CNT/2 ; i++) {
                int x, y;
                y = -(i-CNT/4) * Tank.WIDTH + HEIGHT/2;  
                x = WIDTH - 50;      
                Tank enemyTank = new Tank(x, y, ENEMY_SIZE, false, false, Direction.L, this);
                enemyTanks.add(enemyTank);
            }
    		CNT++;
    	}
         if(ENEMY_SIZE != 2) {
        	Wall w1 = new Wall(r.nextInt(10)*30 + WIDTH/4, r.nextInt(10)*40 + HEIGHT/4, r.nextInt(5)*50 + 150, 15);
            this.walls.add(w1);
            Wall w2 = new Wall(r.nextInt(10)*50 + WIDTH/3, r.nextInt(10)*30 + HEIGHT/3, r.nextInt(5)*50 + 150, 15);
            this.walls.add(w2);
            Wall w3 = new Wall(r.nextInt(10)*30 + WIDTH/2, -r.nextInt(10)*50 + HEIGHT/3, r.nextInt(5)*50 + 150, 15);
            this.walls.add(w3);
            Wall w4 = new Wall(-r.nextInt(10)*50 + WIDTH/5, r.nextInt(10)*50 + HEIGHT/5, 15, r.nextInt(5)*50 + 150);
            this.walls.add(w4);
            Wall w5 = new Wall(r.nextInt(10)*40 + WIDTH/6, -r.nextInt(10)*30 + HEIGHT/2, 15, r.nextInt(5)*50 + 150);
            this.walls.add(w5);
            Wall w6 = new Wall(r.nextInt(10)*40 + WIDTH/8, r.nextInt(10)*40 + HEIGHT/3, 15, r.nextInt(5)*50 + 150);		
            this.walls.add(w6);
        	Wall w7 = new Wall(r.nextInt(10)*30 + WIDTH/4, r.nextInt(10)*40 + HEIGHT/4, r.nextInt(5)*50 + 150, 15);
            this.walls.add(w7);
            Wall w8 = new Wall(-r.nextInt(10)*50 + WIDTH/2, -r.nextInt(10)*30 + HEIGHT, r.nextInt(5)*50 + 150, 15);
            this.walls.add(w8);
            Wall w9 = new Wall(-r.nextInt(10)*30 + WIDTH, -r.nextInt(10)*50 + HEIGHT/2, r.nextInt(5)*50 + 150, 15);
            this.walls.add(w9);
            Wall w10 = new Wall(-r.nextInt(10)*50 + WIDTH, -r.nextInt(10)*50 + HEIGHT, 15, r.nextInt(5)*50 + 150);
            this.walls.add(w10);
            Wall w11 = new Wall(-r.nextInt(10)*40 + WIDTH, r.nextInt(10)*30 + HEIGHT/2, 15, r.nextInt(5)*50 + 150);
            this.walls.add(w11);
            Wall w12 = new Wall(r.nextInt(10)*40 + WIDTH, -r.nextInt(10)*40 + HEIGHT, 15, r.nextInt(5)*50 + 150);		
            this.walls.add(w12);
         }
           /* this.wallss.add(w1);
            this.wallss.add(w2);
            this.wallss.add(w3);
            this.wallss.add(w4);
            this.wallss.add(w5);
            this.wallss.add(w6);*/

            													//更新地图：产生新的地图
        
    }
    
    /**
     * double-buffer applied to avert screen flicker and flash
     */
    public void update(Graphics g) {
        if (this.offScreenImage == null) {
            this.offScreenImage = this.createImage(WIDTH, HEIGHT);
        }

        Graphics goff = offScreenImage.getGraphics();
        Color c = goff.getColor();
        goff.setColor(this.backColor);
        goff.fillRect(0, 0, WIDTH, HEIGHT);
        goff.setColor(c);
        paint(goff);
        g.drawImage(this.offScreenImage, 0, 0, null);
    }

  
    public void paint(Graphics g) {
        // record
    	endTime = System.currentTimeMillis();
    	if(startTime < endTime - GOODTIME ) {
    		
    		GOOD_flag = 0;		//无敌时间消失
    	}
    	
    	
    	if(startTime < endTime - G_TIME) {						
    		heroTank1.setDie();		//超时直接死亡
    		heroTank2.setDie();
    	}
        Color c = g.getColor();
        if((heroTank1.isLive() || heroTank2.isLive()) && (ENEMY_SIZE - 1) <= G_END) {
        g.setColor(Color.WHITE);
        g.drawString("Missiles Count: " + this.missiles.size(), 10, 30);
     // g.drawString("Explodes Count: " + this.explodes.size(), 10, 50);
        g.drawString("Tanks Count: " + this.enemyTanks.size(), 10, 50);
        g.drawString("HeroTank1 Life: " + this.heroTank1.getLife(), 10, 70);
        g.drawString("HeroTank2 Life: " + this.heroTank2.getLife(), 10, 90);
        g.drawString("The number of round: " + (ENEMY_SIZE-1), 10, 110); //增加局数、血量
        g.drawString("Score of HeroTank1 : " + this.heroTank1.Score, 10, 130);
        g.drawString("Score of HeroTank2 : " + this.heroTank2.Score, 10, 150);
        
        //g.drawString("The reset of Invincible time : " + endTime, 10, 130);
        if(((GOODTIME-(endTime - startTime))/1000) >= 0) {
        	g.setColor(Color.BLACK);
        g.drawString("The reset of Invincible time : " + ((GOODTIME-(endTime - startTime))/1000), WIDTH/2-100, 50);//新增无敌时间
        }
        if(((G_TIME-(endTime - startTime))/1000) >= 0 && (heroTank1.isLive() || heroTank2.isLive())) {
        	g.setColor(Color.BLACK);
        g.drawString("The reset of this Game : " + ((G_TIME-(endTime - startTime))/1000), WIDTH-200, 50);
        }
        //新增每局时间限制
        }
        else 
        	{
        	if(!heroTank1.isLive() && !heroTank2.isLive()) {
            g.setColor(Color.MAGENTA);
            g.drawString("游戏结束，大侠请重新来过！", TankClient.WIDTH/2 - 50, TankClient.HEIGHT/2);
            }
        	else {
        	g.setColor(Color.MAGENTA);
            g.drawString("恭喜大侠获胜！", TankClient.WIDTH/2 - 50, TankClient.HEIGHT/2);
        	}
        	}
        
        	g.setColor(c);

        // walls
        if((heroTank1.isLive() || heroTank2.isLive()) && (ENEMY_SIZE - 1) <= G_END) {
        for (int i = 0; i < walls.size(); i++) {
            Wall w = this.walls.get(i);
            w.draw(g);
        }
        }
        // my tank
        if((heroTank1.isLive() || heroTank2.isLive()) && (ENEMY_SIZE - 1) <= G_END) {
        heroTank1.draw(g);
        heroTank1.eatBlood(blood);
        heroTank2.draw(g);
        heroTank2.eatBlood(blood);
        }
        // enemy Tanks
        if (this.enemyTanks.size() <= 0) {
        	
        	ENEMY_SIZE = ENEMY_SIZE + 1; //过关坦克数量增加
        	
        	this.walls.clear();		//清空墙链表
        	
            this.generateEnemys();
            
        }
        if((heroTank1.isLive() || heroTank2.isLive()) && (ENEMY_SIZE - 1) <= G_END) {
        for (int i = 0; i < this.enemyTanks.size(); i++) {
            Tank enemyTank = this.enemyTanks.get(i);
            enemyTank.againstTanks(this.enemyTanks);
            for (int j = 0; j < this.walls.size(); j++) {
                enemyTank.againstWall(this.walls.get(j));
            }
            enemyTank.draw(g);
        }

        // missiles
        for (int i = 0; i < this.missiles.size(); i++) {
            Missile m = this.missiles.get(i);
            m.hitTanks(this.enemyTanks);
            m.hitTank(heroTank1);
            m.hitTank(heroTank2);
            for (int j = 0; j < this.walls.size(); j++) {
                m.againstWall(this.walls.get(j));
            }
            m.draw(g);
        }

        // explodes
        for (int i = 0; i < this.explodes.size(); i++) {
            Explode ex = this.explodes.get(i);
            ex.draw(g);
        }
        
        // blood
   
        blood.draw(g);
        }
    }

    /**
     * run frame
     */
    public void launchFrame() {
        // generate enemy enemyTanks
        this.generateEnemys();

        // set screen
        this.setTitle("TankWar");
        this.setBackground(this.backColor);
        this.setLocation(GAME_X, GAME_Y);
        this.setSize(WIDTH, HEIGHT);
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        this.addKeyListener(new KeyMonitor());
        this.setResizable(false);
        this.setVisible(true);

        // slave start
        Thread tankthread = new Thread(new PaintThread());
        tankthread.start();
    }

    /**
     * repaint slave class
     * 
     * @author wzy
     * 
     */
    private class PaintThread implements Runnable {
        public void run() {
            while (true) {
                repaint();
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * keyboard monitor class
     * 
     * @author wzy
     * 
     */
    private class KeyMonitor extends KeyAdapter {
        public void keyReleased(KeyEvent e) {
            heroTank1.keyReleased(e);
            heroTank2.keyReleased(e);
        }

        public void keyPressed(KeyEvent e) {
            heroTank1.keyPressed(e);
            heroTank2.keyPressed(e);
        }
        
    }

    public static void main(String[] args) {
        TankClient tc = new TankClient();
        tc.launchFrame();
    }
}
