package com.jalak.harupat.capture.ui.Filter;

import net.rim.device.api.system.Bitmap;

public class Thermal {
	public static void filter(Bitmap bmp){
		int w = bmp.getWidth();
        int h = bmp.getHeight();		
		int[] srcPixels = new int[w*h];
        int[] dstPixels = new int[w*h];
        bmp.getARGB(srcPixels, 0, w, 0, 0, w, h);
        for(int i = 0; i < srcPixels.length; i++) {
        	if(srcPixels[i]!= 0x00){
	            int srcRGB = srcPixels[i];
	
	            int srcR = (srcRGB >> 16) & 0xff;
	            int srcG = (srcRGB >> 8) & 0xff;
	            int srcB = srcRGB & 0xff;
	
	            // add together 30% of red value, 59% of green, 11% of blue
	            int dstGray = ((srcR * 77) >> 8) + ((srcG * 150) >>8) + ((srcB  * 28) >> 8);
	
	            // rearrange the grayscale colors to obtain different values from white to red to green to blue to black
	            // real thermal filter have 10 values. See examples here : http://en.wikipedia.org/wiki/Thermal_imaging
	            // but this "fake thermal filter" have only 4 ranges to facilitate calculation.
	            if (dstGray >= 0 && dstGray <= 63) {
	                // from white to red  0xffffffff -> 0xffff0000
	                int val = 0xff - (dstGray << 2);
	                dstPixels[i] = 0xffff0000 | (val << 8) | val;
	            }
	            else if (dstGray >= 64 && dstGray <= 127) {
	                // from red to green 0xffff0000 -> 0xff00ff00
	                int val1 = 0xff - ((dstGray-64) << 2);
	                int val2 = (dstGray-64) << 2;
	                dstPixels[i] = 0xff000000 | (val1 << 16) | (val2 << 8 );
	            }
	            else if (dstGray >= 128 && dstGray <= 191) {
	                // from green to blue 0xff00ff00 -> 0xff0000ff
	                int val1 = 0xff - ((dstGray-128) << 2);
	                int val2 = (dstGray-128) << 2;
	                dstPixels[i] = 0xff000000 | (val1 << 8) | val2;
	            }
	            else {
	                // from blue to black 0xff0000ff -> 0xff000000
	                int val = 0xff - ((dstGray-192) << 2);
	                dstPixels[i] = 0xff000000 | val;
	            }
        	}else{
        		dstPixels[i] = srcPixels[i];
        	}
        }
        bmp.setARGB(dstPixels, 0, w, 0, 0, w, h);
		//return bmp;
	}
}
