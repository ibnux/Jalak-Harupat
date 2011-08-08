package com.jalak.harupat.capture.ui.Filter;

import net.rim.device.api.system.Bitmap;

public class Brightness {
	
	public static void filter(Bitmap bmp, int brighness, int contrast){
		int w = bmp.getWidth();
        int h = bmp.getHeight();		
		int[] srcPixels = new int[w*h];
        int[] dstPixels = new int[w*h];
        bmp.getARGB(srcPixels, 0,w, 0, 0, w, h);
        for(int i = 0; i < srcPixels.length; i++) {
            int srcRGB = srcPixels[i];
            dstPixels[i] =brightness(srcRGB);
        }



        bmp.setARGB(dstPixels, 0, w, 0, 0, w, h);
		//return bmp;
	}
	
	public static int brightness(int rgb) {
		int r = (rgb >> 16) & 0xff;
		int g = (rgb >> 8) & 0xff;
		int b = rgb & 0xff;
		return (r+g+b)/3;
	}
	
}
