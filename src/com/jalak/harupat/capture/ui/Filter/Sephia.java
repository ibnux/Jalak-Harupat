package com.jalak.harupat.capture.ui.Filter;

import net.rim.device.api.system.Bitmap;

public class Sephia {
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

            // R*39.3% + G*76.9% + 18.9%B
            int dstR = ((srcR * 100)>>8) + ((srcG * 196)>>8) + ((srcB * 48)>>8);
            dstR = dstR > 255 ? 255 : dstR;
            // R*34.9% + G*68.6% + 16.8%B
            int dstG = ((srcR * 89)>>8) + ((srcG * 175)>>8) + ((srcB * 43)>>8);
            dstG = dstG > 255 ? 255 : dstG;
            // R*27.2% + G*53.4% + 13.1%B
            int dstB = ((srcR * 69)>>8) + ((srcG * 136)>>8) + ((srcB * 33)>>8);
            dstB = dstB > 255 ? 255 : dstB;


            dstPixels[i] = 0xff000000 | (dstR << 16) | (dstG << 8) | dstB;
        }

        bmp.setARGB(dstPixels, 0, w, 0, 0, w, h);
		//return bmp;
	}
}
