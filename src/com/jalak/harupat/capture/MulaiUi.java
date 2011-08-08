package com.jalak.harupat.capture;

import java.io.InputStream;

import javax.microedition.media.Manager;
import javax.microedition.media.Player;
import javax.microedition.media.control.VolumeControl;

import net.rim.device.api.i18n.SimpleDateFormat;
import net.rim.device.api.system.Bitmap;
import net.rim.device.api.system.Display;
import net.rim.device.api.system.EncodedImage;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.component.Dialog;

import com.jalak.harupat.capture.ui.Capturee;
import com.jalak.harupat.capture.ui.EditorScreen;
import com.jalak.harupat.capture.util.ImageUtil;
import com.jalak.harupat.capture.util.Util;

public class MulaiUi extends UiApplication{
	int w = Display.getWidth(), h=Display.getHeight();

	public MulaiUi() {
		Bitmap gbr = new Bitmap(w,h);
		Display.screenshot(gbr, 0, 0, w,h);
		try {
			 Class classs = Class.forName(getClass().getName());
			 InputStream is = classs.getResourceAsStream("/CameraShutter.mid");
			 Player p = Manager.createPlayer(is, "audio/midi");
		     p.realize();
		     VolumeControl vc = (VolumeControl)p.getControl("VolumeControl");
		     if(vc!=null)
		    	 vc.setLevel(100);
		     p.start();
		 } catch (Exception pe) { }
		 pushScreen(new Capturee(gbr));
		 gbr = null;
	}

	public MulaiUi(String file) {
		if (Util.cekDir(file, false)) {
			if(Util.getSize(file)<500001){
				String data = Util.bacaFile(file);
				EncodedImage img = EncodedImage.createEncodedImage(data.getBytes(), 0, data.getBytes().length);
				img = ImageUtil.bestFit2(img, Display.getWidth(), Display.getHeight());
				SimpleDateFormat dFormatter = new SimpleDateFormat("yyyy-MM-dd HH;mm;ss");
				pushScreen(new EditorScreen(img.getBitmap(),System.getProperty("fileconn.dir.memorycard.photos")+"CaptureNux/",
						"CaptureNux "+dFormatter.formatLocal(System.currentTimeMillis())+".jpg"));
			}else{
				invokeLater(new Runnable() {
					public void run() {
						Dialog.alert("File size must under 500kb");
						System.exit(0);
					}
				});
			}
			// notify the server that we are handling the invocation
		}
		else{
			invokeLater(new Runnable() {
				public void run() {
					Dialog.alert("File not found");
					System.exit(0);
				}
			});
		}	
	}

	public MulaiUi(int i) {
		requestBackground();
		Thread tr = new Thread(){
			public void run() {
				try {
					sleep(3*1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.exit(0);
			}
		};
		tr.start();
	}

}
