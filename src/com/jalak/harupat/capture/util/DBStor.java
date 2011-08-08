package com.jalak.harupat.capture.util;



public class DBStor {
	private static String _setting ="com.jalak.harupat.capture.util";

	public static void setSettings(Settings settings) {
		Persistentstore ps = new Persistentstore();
		ps.setObject(_setting, settings);
	}

	public static Settings getSettings() {
		Persistentstore ps = new Persistentstore();
		Settings profil = (Settings)ps.getObject(_setting);
		return profil;
	}

}
