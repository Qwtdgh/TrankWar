package com.xv.tankwar;

import java.awt.Color;
import java.awt.Graphics;

import com.sun.prism.shader.DrawCircle_Color_AlphaTest_Loader;

public class Energy {
	int x,y; //�����������λ��
	int width = 20;//����������Ŀ��     �����������λ���� (x+5,y+5)
	boolean tag = true;
	public Energy(int x, int y) {
		// TODO Auto-generated constructor stub
		this.x = x;
		this.y = y;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public void draw(Graphics g) {
		if(this.tag==true) {
			g.setColor(Color.green);
			g.fillOval(x, y, width, width);
		}
	}
	
	public void hit(Tank tank) {
		if((Math.pow(((this.x+5)-(tank.getX()+15)),2)+Math.pow(((this.y+5)-(tank.getY()+15)),2))<700) {
			if(tank.blood<40) {
				tank.blood=40;
				this.tag = false;
			}
		} 


	}
	
}
