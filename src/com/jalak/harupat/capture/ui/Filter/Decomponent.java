package com.jalak.harupat.capture.ui.Filter;

import net.rim.device.api.system.Bitmap;

public class Decomponent {
	
	 public final static int RED_COMPONENT = 1;
     public final static int BLUE_COMPONENT = 2;
     public final static int GREEN_COMPONENT = 3;
     public final static int ALPHA_COMPONENT = 0;
     
     private final int component;
     
     public Decomponent(int component) {
             this.component = component;
     }
	
	public void filter(Bitmap bmp){
		int w = bmp.getWidth();
        int h = bmp.getHeight();		
		int[] srcPixels = new int[w*h];
        int[] dstPixels = new int[w*h];
        bmp.getARGB(srcPixels, 0,w, 0, 0, w, h);
        for (int i = 0; i < srcPixels.length; i++) {
            int srcRGB = srcPixels[i];

            if (component == RED_COMPONENT) {
                dstPixels[i] = 0xffff0000 & srcRGB;
            }
            else if (component == GREEN_COMPONENT) {
                dstPixels[i] = 0xff00ff00 & srcRGB;
            }
            else if (component == BLUE_COMPONENT) {
                dstPixels[i] = 0xff0000ff & srcRGB;
            }
            else {
                dstPixels[i] = 0xff000000 & srcRGB;
            }
        }




        bmp.setARGB(dstPixels, 0, w, 0, 0, w, h);
		//return bmp;
	}

}
