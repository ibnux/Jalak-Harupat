package com.jalak.harupat.capture.util;

import net.rim.device.api.ui.Color;
import net.rim.device.api.ui.Font;
import net.rim.device.api.util.Persistable;

public class Settings implements Persistable {
	private int size = Font.getDefault().getHeight(), family = 0,
	color=Color.RED, tipe = 0;
	

	public void setSize(int size) {
		this.size = size;
	}

	public int getSize() {
		return size;
	}

	public void setFamily(int family) {
		this.family = family;
	}

	public int getFamily() {
		return family;
	}

	public void setColor(int color) {
		this.color = color;
	}

	public int getColor() {
		return color;
	}

	public void setTipe(int tipe) {
		this.tipe = tipe;
	}

	public int getTipe() {
		return tipe;
	}
	
	
}
