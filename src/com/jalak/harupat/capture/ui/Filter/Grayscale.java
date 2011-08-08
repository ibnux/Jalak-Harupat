package com.jalak.harupat.capture.ui.Filter;

import net.rim.device.api.system.Bitmap;

public class Grayscale {
	
	public static void filter(Bitmap bmp){
		int w = bmp.getWidth();
        int h = bmp.getHeight();		
		int[] srcPixels = new int[w*h];
        int[] dstPixels = new int[w*h];
        bmp.getARGB(srcPixels, 0,w, 0, 0, w, h);
        for(int i = 0; i < srcPixels.length; i++) {
            int srcRGB = srcPixels[i];

            int srcR = (srcRGB >> 16) & 0xff;
            int srcG = (srcRGB >> 8) & 0xff;
            int srcB = srcRGB & 0xff;

            // add together 30% of red value, 59% of green, 11% of blue
            int dstGray = ((srcR * 77) >> 8) + ((srcG * 150) >>8) + ((srcB  * 28) >> 8);

            dstPixels[i] = 0xff000000 | (dstGray << 16) | (dstGray << 8) | dstGray;
        }



        bmp.setARGB(dstPixels, 0, w, 0, 0, w, h);
		//return bmp;
	}
	
}
