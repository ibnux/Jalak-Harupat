package com.jalak.harupat.capture.ui;

import net.rim.blackberry.api.browser.Browser;
import net.rim.blackberry.api.browser.BrowserSession;
import net.rim.device.api.system.Bitmap;
import net.rim.device.api.system.Characters;
import net.rim.device.api.system.Display;
import net.rim.device.api.system.EncodedImage;
import net.rim.device.api.system.JPEGEncodedImage;
import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.Keypad;
import net.rim.device.api.ui.MenuItem;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.component.Dialog;
import net.rim.device.api.ui.container.MainScreen;
import net.rim.device.api.ui.container.VerticalFieldManager;
import net.rim.device.api.util.MathUtilities;

import com.jalak.harupat.capture.Mulai;
import com.jalak.harupat.capture.ui.Filter.FilterImg;
import com.jalak.harupat.capture.ui.field.gbrField;
import com.jalak.harupat.capture.util.DBStor;
import com.jalak.harupat.capture.util.ImageUtil;
import com.jalak.harupat.capture.util.Settings;
import com.jalak.harupat.capture.util.Util;

public class EditorScreen extends MainScreen {
	gbrField gbrfield;
	String path = "",filename="";
	Bitmap gbr;
	Graphics g;
	VerticalFieldManager latar;
	int[] undo;
	
	public EditorScreen(final Bitmap gbr,String path,String filename) {
		super(NO_VERTICAL_SCROLL);
		this.path = path;
		this.filename = filename;
		this.gbr = gbr;
		this.g = Graphics.create(gbr);

		UiApplication.getUiApplication().requestForeground();

		latar = new VerticalFieldManager(VERTICAL_SCROLL|HORIZONTAL_SCROLL|USE_ALL_WIDTH|USE_ALL_HEIGHT){
			protected void paint(Graphics graphics) {
				graphics.drawBitmap(0,0, gbr.getWidth(), gbr.getHeight(), gbr, 0, 0);
				graphics.drawRect(0, 0, gbr.getWidth(), gbr.getHeight());
				super.paint(graphics);
			}
		};
		
		this.gbrfield = new gbrField(gbr.getWidth(),gbr.getHeight());
		latar.add(gbrfield);
		gbrfield.setText("Jalak_Harupat");
		add(latar);
		
		addMenuItem(addText);
		addMenuItem(addArrow);
		addMenuItem(addimg);
		addMenuItem(addFilter);
		addMenuItem(addhelp);
		addMenuItem(saveclose);
		addMenuItem(about);
		if(DBStor.getSettings() == null){
			UiApplication.getUiApplication().invokeLater(new Runnable(){
				public void run() {
					Dialog.alert("this is editor Screen, Click menu to do something.");
				}
			});
			DBStor.setSettings(new Settings());
		}
		setDirty(true);
	}
	
	MenuItem addFilter = new MenuItem("Add Filter",0x0001000,3) {
		public void run() {
			int ask = -1;
			if(undo!=null)
				ask = Dialog.ask("What Filter?", new String[]{"GrayScale","Negative","Sephia","Termal","Undo Filter"}, 0);
			else
				ask = Dialog.ask("What Filter?", new String[]{"GrayScale","Negative","Sephia","Termal"}, 0);
			if(ask<4){
				undo = new int[gbr.getWidth()*gbr.getHeight()];
				gbr.getARGB(undo, 0,gbr.getWidth(), 0, 0, gbr.getWidth(), gbr.getHeight());
				FilterImg.doit(gbr,ask);
			}else if( ask == 4){
				if(undo!=null){
					gbr.setARGB(undo, 0,gbr.getWidth(), 0, 0, gbr.getWidth(), gbr.getHeight());
					undo = null;
				}
			}
			latar.invalidate();
		}
	};
	
	MenuItem about = new MenuItem("About",0x0001000,10) {
		public void run() {
			int ask = Dialog.ask("Jalak_Harupat by BBDevID", new String[]{"Go to Web"}, 0);
			if(ask==0){
				openWeb("http://bbdevid.com/");
			}
		}
		
		private void openWeb(String url){
			BrowserSession bs = Browser.getDefaultSession();
			bs.displayPage(url);
			bs.showBrowser();
		}
	};
	
	MenuItem addimg = new MenuItem("Add Image",0x0001000,2) {
		
		public void run() {
			FileSelectorPopupScreen fs = new FileSelectorPopupScreen(System.getProperty("fileconn.dir.memorycard.photos").substring(8), 
					new String[]{"png","jpg","gif","jpeg"});
			fs.pickFile();
			if(Util.cekDir("file:///" +fs.getFile(), false)){
				int apa = Dialog.ask("Resize if bigger than Screen", new String[]{"100%","80%","50%","30%","10%"}, 0);
				String data = Util.bacaFile("file:///" +fs.getFile());
				EncodedImage img = EncodedImage.createEncodedImage(data.getBytes(), 0, data.getBytes().length);
				int sw = 0, sh = 0;
				if(apa==0){
					sw = Display.getWidth();
					sh = Display.getHeight();
				}else if(apa==1){
					sw = (int)(0.8*Display.getWidth());
					sh = (int)(0.8*Display.getHeight());
				}else if(apa==2){
					sw = (int)(0.5*Display.getWidth());
					sh = (int)(0.5*Display.getHeight());
				}else if(apa==3){
					sw = (int)(0.3*Display.getWidth());
					sh = (int)(0.3*Display.getHeight());
				}else if(apa==4){
					sw = (int)(0.1*Display.getWidth());
					sh = (int)(0.1*Display.getHeight());
				}else{
					return;
				}
				gbrfield.setGbr(ImageUtil.bestFit2(img, sw, sh).getBitmap());
				img = null;
			}
		}
	};
	
	MenuItem addText = new MenuItem("Add Text",0x0001000,0) {
		public void run() {
			inputBox apa = new inputBox("Your Text?","");
			String txt = apa.getNama();
			if(txt.length()>0){
				gbrfield.setText(txt);
				gbrfield.setColor(apa.getColor());
				gbrfield.setFont(apa.getFont());
				apa = null;
			}
		}
	};

	MenuItem addArrow = new MenuItem("Add Arrow",0x0001000,0) {
		public void run() {
			int apa = Dialog.ask("Direction?",new String[]{"left","up","right","down"},0);
			Bitmap bmp = null;
			if(apa==0){
				bmp = rotateImage(Bitmap.getBitmapResource("right.png"),180);
			}else if(apa==1){
				bmp = Bitmap.getBitmapResource("up.png");
			}else if(apa==2){
				bmp = Bitmap.getBitmapResource("right.png");
			}else if(apa==3){
				bmp = rotateImage(Bitmap.getBitmapResource("up.png"),180);
			}
			gbrfield.setGbr(bmp);
		}
	};

	MenuItem addhelp = new MenuItem("Add Help Me!",0x0001000,0) {
		public void run() {
			gbrfield.setGbr(Bitmap.getBitmapResource("helpme.png"));			
		}
	};

	MenuItem saveclose = new MenuItem("Save & Close", 0x0001000,5) {
		public void run() {
			if(saveIt())
				System.exit(0);
		}
	};
	
	protected boolean onSave() {
		return saveIt();
	};
	
	boolean saveIt(){
		if(Util.cekDir(path, true)){
			JPEGEncodedImage img = JPEGEncodedImage.encode(this.gbr,90); 
			if(Util.tulisFile(path+filename, new String(img.getData()))){
				Dialog.alert("Saved");
				img = null;
				return true;
			}else{
				Dialog.alert("Failed to create File\r\n "+path+filename);
				img = null;
				return false;
			}
		}else{
			UiApplication.getUiApplication().invokeLater(new Runnable(){
				public void run() {
					Dialog.alert("Failed to create Folder\r\n"+path);
				}
			});
			return false;
		} 
	}
	
	private void askWhat(){
		int apa = Dialog.ask("What to do?", new String[]{"Draw to image","Add Filter","Transparent","Remove"}, 0);
		if(apa==0){
			this.g.setGlobalAlpha(gbrfield.getTransparent());
			if(gbrfield.getText() != null){
				this.g.setFont(gbrfield.getFont());
				int c = g.getColor();
				this.g.setFont(gbrfield.getFont());
				this.g.setColor(gbrfield.getColor());
				this.g.drawText(gbrfield.getText(), gbrfield.getX(), gbrfield.getY());
				this.g.setColor(c);
				gbrfield.setText(null);
				gbrfield.setGbr(null);
			}else if(gbrfield.getGbr() != null){
				this.g.drawBitmap(gbrfield.getX(), gbrfield.getY(), gbrfield.getGbr().getWidth(),
						gbrfield.getGbr().getHeight(), gbrfield.getGbr(),0,0);
				gbrfield.setText(null);
				gbrfield.setGbr(null);
			}
			gbrfield.setTransparent(255);
			latar.invalidate();
			Mulai.playTune();
		}else if(apa==1){
			if(gbrfield.getGbr()!=null){
				int ask = Dialog.ask("What Filter?", new String[]{"GrayScale","Negative","Sephia","Thermal"}, 0);
				FilterImg.doit(gbrfield.getGbr(),ask);
				gbrfield.setGbr(gbrfield.getGbr());
			}
		}else if(apa == 2){
			int ask = Dialog.ask("Transparent Value?", new String[]{"255","200","150","100","50"}, 0);
			switch (ask) {
			case 0:
				gbrfield.setTransparent(255);
				break;
			case 1:
				gbrfield.setTransparent(200);
				break;
			case 2:
				gbrfield.setTransparent(150);
				break;
			case 3:
				gbrfield.setTransparent(100);
				break;
			case 4:
				gbrfield.setTransparent(50);
				break;

			default:
				break;
			}
		}else if(apa == 3){
			gbrfield.setText(null);
			gbrfield.setGbr(null);
			gbrfield.setTransparent(255);
		}
		
	}
	
	protected boolean keyChar(char c, int status, int time) {
		if(c==Characters.ESCAPE){
			if(gbrfield.getText()!=null || gbrfield.getGbr()!=null){
	  			askWhat();
	  			return true;
	  		}
		}
		return super.keyChar(c, status, time);
	}
	
	protected boolean keyDown(int in_nKeyCode, int in_nTime)
	{
	      int nKeyPressed = Keypad.key(in_nKeyCode);

	      if(nKeyPressed == Keypad.KEY_MENU){
	    	if(gbrfield.getText()!=null || gbrfield.getGbr()!=null){
	  			askWhat();
	  			return true;
	  		}
	       }

	       return super.keyDown(in_nKeyCode, in_nTime);
	}
	
	protected boolean navigationClick(int status, int time) {
		if(gbrfield.getText()!=null || gbrfield.getGbr()!=null){
			askWhat();
			return true;
		}
		return super.navigationClick(status, time);
	};

	public static Bitmap rotateImage(Bitmap oldB, int angle)
    {
		try{
	        int w = oldB.getWidth();
	        int h = oldB.getHeight();
	        double angRad = (angle%360)*(Math.PI/180);
	        Bitmap newB = new Bitmap(w,h);
	        int[] oldD = new int[w*h];
	        int[] newD = new int[w*h];
	        oldB.getARGB(oldD, 0, w, 0, 0, w, h);
	        
	        int axisX = w/2;
	        int axisY = h/2;
	        
	        for(int x = 0; x < oldD.length; x++){
	            int oldX = x%w;
	            int oldY = x/w;
	            int op = oldX-axisX;
	            int adj = oldY-axisY;
	            double oldT = MathUtilities.atan2(op, adj);
	            double rad = Math.sqrt((op*op)+(adj*adj));
	            double newT = oldT+angRad;
	            int newX = (int)MathUtilities.round((rad*Math.sin(newT))+(double)axisX);
	            int newY = (int)MathUtilities.round((rad*Math.cos(newT))+(double)axisY);
	            if(newX<0||newY<0||newX>=w||newY>=h){
	                newD[x] = 0x00000000;
	            }else{
	                newD[x] = oldD[(newY*w)+newX];
	            }
	        }
	        
	        newB.setARGB(newD, 0, w, 0, 0, w, h);
	        return newB;
		}catch(Exception e){
			
		}
		return null;
    }
	
}
