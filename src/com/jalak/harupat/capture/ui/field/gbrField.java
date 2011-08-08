package com.jalak.harupat.capture.ui.field;

import net.rim.device.api.system.Bitmap;
import net.rim.device.api.ui.Color;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.Font;
import net.rim.device.api.ui.FontFamily;
import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.TouchEvent;

public class gbrField extends Field {
	private Bitmap gbr = null;
	private String text = null;
	private int w = 0, h = 0,
		color = Color.RED,
		trans = 255;
	private int x = w/2, y = h/2;
	
	public gbrField(int w,int h) {
		super(FOCUSABLE);
		this.w = w;
		this.h = h;
		try{
			FontFamily _fontFamily = FontFamily.forName("BBAlpha Serif");
			Font font = _fontFamily.getFont(FontFamily.SCALABLE_FONT, Font.getDefault().getHeight()).derive(Font.BOLD);
			setFont(font);
		}catch(Exception e){}
	}
	
	protected void layout(int arg0, int arg1) {
		setExtent(w, h);
	}
	
	public void setGbr(Bitmap gbr){
		this.gbr = gbr;
		this.text = null;
		this.x = w/2;
		this.y = h/2;
		invalidate();
	}
	
	public void setText(String text){
		this.gbr = null;
		this.text = text;
		this.x = w/2;
		this.y = h/2;
		invalidate();
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setColor(int color) {
		this.color = color;
		invalidate();
	}
	
	public int getColor() {
		return color;
	}
	
	
	
	protected void drawFocus(Graphics graphics, boolean on) {}

	protected void paint(Graphics g) {
		//g.clear();
		g.setGlobalAlpha(trans);
		g.setColor(color);
		if(gbr != null){
			g.drawBitmap(x, y, gbr.getWidth(), gbr.getHeight(), gbr, 0, 0);
		}else if(text != null){
			g.drawText(text, x, y);
		}
	}
	
	protected boolean touchEvent(TouchEvent message)
    {
        this.x = message.getX(1);
        this.y = message.getY(1);
        invalidate();
		return true;
    }
	
	protected boolean navigationMovement(int dx, int dy, int status, int time) {
		int s = 4;
		//if((x+(dx*s))>-1 && (x+(dx*s))<w)
			this.x += dx*s;
		//if((y+(dy*s))>-1 && (y+(dy*s))<h)
			this.y += dy*s;
		invalidate();
		return true;
	}
	
	public Bitmap getGbr() {
		return gbr;
	}
	
	public String getText() {
		return text;
	}

	public void setTransparent(int trans) {
		this.trans = trans;
		invalidate();
	}
	
	public int getTransparent() {
		return trans;
	}

}
