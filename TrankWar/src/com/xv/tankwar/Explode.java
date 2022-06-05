package com.xv.tankwar;
import java.awt.Color;
import java.awt.Graphics;


public class Explode {    
    public static final int DIAMETER = 25;
    public static final int LIMIT = 50;
    
    private int x;
    private int y;
    private Color color;
    private TankClient tc;
    private int step;
	private Tank tf;
    
    /**
     * default constructor
     * 
     * @param x coordinate x
     * @param y coordinate y
     * @param tc the reference of controller
     */
    public Explode(int x, int y, TankClient tc, Tank tf, Color color) {
        this.x = x;
        this.y = y;
        this.tc = tc;
        this.tf = tf;
        this.color = color;
        this.step = 1;
    }

    /**
     * draw the explosion effect
     * 
     * @param g Graphics class
     */
    public void draw(Graphics g) {
        if ((this.step * DIAMETER)/3 > LIMIT) {
        	
            tc.explodes.remove(this);
            
            return;
        }
        
        Color c = g.getColor();
        g.setColor(this.color);
        g.fillOval(x - (DIAMETER * this.step)/6, y - (DIAMETER * this.step)/6, (DIAMETER * this.step)/3, (DIAMETER * this.step)/3);
        g.setColor(c);
        this.step ++;
    }
}
