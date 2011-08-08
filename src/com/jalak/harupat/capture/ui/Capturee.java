package com.jalak.harupat.capture.ui;

import net.rim.device.api.i18n.SimpleDateFormat;
import net.rim.device.api.system.Alert;
import net.rim.device.api.system.Bitmap;
import net.rim.device.api.system.Characters;
import net.rim.device.api.system.EncodedImage;
import net.rim.device.api.system.JPEGEncodedImage;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.Keypad;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.component.BitmapField;
import net.rim.device.api.ui.component.ButtonField;
import net.rim.device.api.ui.component.Menu;
import net.rim.device.api.ui.container.HorizontalFieldManager;
import net.rim.device.api.ui.container.MainScreen;
import net.rim.device.api.ui.container.PopupScreen;
import net.rim.device.api.ui.container.VerticalFieldManager;

import com.jalak.harupat.capture.util.ImageUtil;
import com.jalak.harupat.capture.util.Util;

public class Capturee extends MainScreen {
	Capturee app;
	VerticalFieldManager latar;
	public Capturee(final Bitmap gbr) {
		super(NO_VERTICAL_SCROLL);
		this.latar = new VerticalFieldManager(VERTICAL_SCROLL|HORIZONTAL_SCROLL|USE_ALL_WIDTH|USE_ALL_HEIGHT){
			protected void paint(Graphics graphics) {
				graphics.drawBitmap(0,0, gbr.getWidth(), gbr.getHeight(), gbr, 0, 0);
				super.paint(graphics);
			}
		};
		this.app = this;
		UiApplication.getUiApplication().invokeLater(new Runnable(){
			public void run() {
				UiApplication.getUiApplication().pushScreen(new Captures(gbr));
			}
		});
	}
	
	protected boolean onSavePrompt() {
		return true;
	}
	
	public class Captures extends PopupScreen implements FieldChangeListener{
		private String Filename;
		private String path = System.getProperty("fileconn.dir.memorycard.photos")+"JalakHarupat/";
		private byte [] imgData;
	
		ButtonField Save = new ButtonField("Save",FIELD_HCENTER|ButtonField.CONSUME_CLICK);
		ButtonField edit = new ButtonField("Edit",FIELD_HCENTER|ButtonField.CONSUME_CLICK);
		Bitmap gbr;
		
		public Captures(final Bitmap gbr) {
			super(new VerticalFieldManager(VERTICAL_SCROLL));
			VerticalFieldManager vfm = (VerticalFieldManager)getDelegate();
			
			this.gbr = gbr;
			JPEGEncodedImage img = JPEGEncodedImage.encode(gbr,90);
			this.imgData = img.getData();
			long saveTime = System.currentTimeMillis();
	        SimpleDateFormat dFormatter = new SimpleDateFormat("yyyy-MM-dd HH;mm;ss");
	        this.Filename = "JalakHarupat "+dFormatter.formatLocal(saveTime)+".jpg";
			
	        //EncodedImage enc = EncodedImage.createEncodedImage(img.getData(), 0, img.getData().length,"image/jpeg");
	        EncodedImage enc = ImageUtil.bestFit2(img, img.getWidth()-100, img.getHeight()-100);
	        vfm.add(new BitmapField(enc.getBitmap()));
			HorizontalFieldManager hfm = new HorizontalFieldManager(FIELD_HCENTER);
			hfm.add(Save);
			hfm.add(edit);
			Save.setChangeListener(this);
			edit.setChangeListener(this);
			vfm.add(hfm);
			Util.cekDir(path, true);
			app.add(latar);
		}
	
		protected boolean keyChar(char c, int status, int time) {
			if(c==Characters.ESCAPE)
				System.exit(0);
			return super.keyChar(c, status, time);
		}
	
		public void fieldChanged(Field field, int context) {
			if(field == Save){
				saveBitmap();
			}else{
				UiApplication.getUiApplication().pushScreen(new EditorScreen(gbr,path,Filename));
				close();
				app.close();
			}
		}
		
		protected boolean keyDown(int in_nKeyCode, int in_nTime)
		{
			int nKeyPressed = Keypad.key(in_nKeyCode);
			if(nKeyPressed == Keypad.KEY_MENU)
			{
				Menu apa = this.getMenu(Menu.INSTANCE_DEFAULT);
				apa.show();
				return true;
			}
			return false;
		}
	
		private void saveBitmap() 
		{
			
			if(Util.cekDir(path, true)){
				if(!Util.tulisFile(path+Filename, new String(imgData))){
					Alert.startVibrate(3000);
				}else
					System.exit(0);
			}else{
				Alert.startVibrate(3000);
			} 
		}	
	}
}