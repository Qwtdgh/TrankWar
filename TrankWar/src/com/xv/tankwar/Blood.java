package com.xv.tankwar;
import java.awt.*;
import java.util.Random;

public class Blood {
    public static final int WIDTH = 15, HEIGHT = 15;

    private int x, y;
    private Color color = new Color(0x41, 0x14, 0x45);
    private int[][] path = { {350, 300}, {350, 500}, {750, 310}, {810, 115}, {650, 260},
            {470, 60}, {395, 50}};
    private boolean live = true;
    public static Random br = new Random();

    /**
     * default constructor
     */
    public Blood() {
        int k = br.nextInt(path.length);
        this.x = this.path[k][0];
        this.y = this.path[k][1];
    }


    /**
     * draw blood block
     * 
     * @param g
     */
    public void draw(Graphics g) {
        if (!this.live) {
            return;
        }

        Color c = g.getColor();
        g.setColor(this.color);
        g.fillRect(x, y, WIDTH, HEIGHT);
        g.setColor(c);
    }

    /**
     * get blood rectangle
     * 
     * @return rectangle
     */
    public Rectangle getRect() {
        return new Rectangle(this.x, this.y, WIDTH, HEIGHT);
    }

    /**
     * make blood block die
     */
    public void setLive() {
        this.live = false;
    }
    
    /**
     * get blood block live
     * @return live
     */
    public void freshLive() {
    	this.live = true;
    }
    public boolean getLive() {
        return this.live;
    }
}
