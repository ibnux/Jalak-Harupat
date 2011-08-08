package com.jalak.harupat.capture.ui.Filter;

import net.rim.device.api.system.Bitmap;

public class Negative {
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

            srcR = 0xff - srcR;
            srcG = 0xff - srcG;
            srcB = 0xff - srcB;

            dstPixels[i] = 0xff000000 | (srcR << 16) | (srcG << 8) | srcB;
        }


        bmp.setARGB(dstPixels, 0, w, 0, 0, w, h);
		//return bmp;
	}
}
