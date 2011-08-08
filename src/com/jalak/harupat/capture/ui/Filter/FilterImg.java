package com.jalak.harupat.capture.ui.Filter;

import net.rim.device.api.system.Bitmap;

public class FilterImg {
	final int GRAYSCALE = 0;
	final int NEGATIVE = 1;
	final int SEPHIA = 2;
	final int THERMAL = 3;
	
	/**
	 * 
	 * @param bmp
	 * @param tipe Filter.UPCASE
	 */
	public static void doit(Bitmap bmp, int tipe){
		switch (tipe) {
		case 0:
			Grayscale.filter(bmp);
			break;
		case 1:
			Negative.filter(bmp);
			break;
		case 2:
			Sephia.filter(bmp);
			break;
		case 3:
			Thermal.filter(bmp);
			break;

		default:
			break;
		}
	}
}
