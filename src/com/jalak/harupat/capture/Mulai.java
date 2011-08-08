package com.jalak.harupat.capture;

import java.util.Timer;
import java.util.TimerTask;

import net.rim.blackberry.api.menuitem.ApplicationMenuItem;
import net.rim.blackberry.api.menuitem.ApplicationMenuItemRepository;
import net.rim.device.api.applicationcontrol.ApplicationPermissions;
import net.rim.device.api.applicationcontrol.ApplicationPermissionsManager;
import net.rim.device.api.system.Alert;
import net.rim.device.api.system.ApplicationDescriptor;
import net.rim.device.api.system.ApplicationManager;
import net.rim.device.api.system.ApplicationManagerException;
import net.rim.device.api.system.RuntimeStore;


public class Mulai {
	 public static final long MY_CLOSE_KEY = 0x11e0face19055a68L;
	 
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		_assertHasPermissions();
		
		if(args.length>0){
			for(int a=0;a<args.length;a++)
				System.out.println(args[a]);
			if(args[0].equals("startup")){
				Object o = RuntimeStore.getRuntimeStore().get(MY_CLOSE_KEY);
				if(o == null) {
					long locationToAddMenuItem = ApplicationMenuItemRepository.MENUITEM_FILE_EXPLORER;
			        ApplicationMenuItemRepository amir = ApplicationMenuItemRepository.getInstance();
					amir.addMenuItem(ApplicationMenuItemRepository.MENUITEM_SYSTEM,new ambil());
					
					ApplicationDescriptor apd = ApplicationDescriptor.currentApplicationDescriptor();
			        amir.addMenuItem(locationToAddMenuItem, new ngedit(), apd, "image/png");
			        amir.addMenuItem(locationToAddMenuItem, new ngedit(), apd, "image/jpg");
			        amir.addMenuItem(locationToAddMenuItem, new ngedit(), apd, "image/gif");
					
					//amir.addMenuItem(ApplicationMenuItemRepository.MENUITEM_EMAIL_EDIT, new attach());
					RuntimeStore.getRuntimeStore().put(MY_CLOSE_KEY, "hahahaha");
					System.exit(0);
				}else{
					MulaiUi mu = new MulaiUi(12);
					mu.enterEventDispatcher();
				}
			}else if(args.length>0 && args[0].startsWith("file")){
				MulaiUi mu = new MulaiUi(args[0]);
				mu.enterEventDispatcher();
			}else{
				MulaiUi mu = new MulaiUi();
				mu.enterEventDispatcher();
			}
		}else{
			System.exit(0);
		}
	}
		
	// ASK FOR PERMISSIONS
	private static void _assertHasPermissions() {

		// Capture the current state of permissions and check against the requirements.
		ApplicationPermissionsManager apm = ApplicationPermissionsManager.getInstance();
		ApplicationPermissions original = apm.getApplicationPermissions();
		boolean permissionsOk = false;

		if (original.getPermission(ApplicationPermissions.PERMISSION_EMAIL) ==
		    ApplicationPermissions.VALUE_ALLOW
		    &&
		    original.getPermission(ApplicationPermissions.PERMISSION_FILE_API) ==
		    ApplicationPermissions.VALUE_ALLOW
		    &&
		    original.getPermission(ApplicationPermissions.PERMISSION_RECORDING) ==
		    ApplicationPermissions.VALUE_ALLOW)
		{
			permissionsOk = true;
		}else{
		  ApplicationPermissions permRequest = new ApplicationPermissions();
		  permRequest.addPermission(ApplicationPermissions.PERMISSION_EMAIL);
		  permRequest.addPermission(ApplicationPermissions.PERMISSION_FILE_API);
		  permRequest.addPermission(ApplicationPermissions.PERMISSION_RECORDING);

		  permissionsOk = apm.invokePermissionsRequest(permRequest);

		}

		if (!permissionsOk) {
			_assertHasPermissions();
		}
	}
	
	private static class ngedit extends ApplicationMenuItem {
		ngedit() {
			super(0x00020000);
		}
		public String toString() {
			return "Edit with Jalak Harupat";
		}
		public Object run(Object context) {
			if (context != null && context instanceof String) {
				String inputFile = (String) context;
				try {
					ApplicationManager.getApplicationManager().launch("Jalak_Harupat?"+inputFile.trim());
				} catch (ApplicationManagerException e) {
					System.out.println(e.getMessage());
				}
				// excecution control is automatically passed to Mp3Player.invocationRequestNotify() after this
				//invokeUi.invoke(getClass().getName(), inputFile, null);
			}else{
				System.out.println("apa: "+context);
			}
			return context;
		}
	}
	
	private static class ambil extends ApplicationMenuItem {
		ambil() {
			super(0x00020000);
		}
		public String toString() {
			return "Capture Screen";
		}
		public Object run(Object context) {
			launch();
			return context;
		}
		
	    static TimerTask loadingTask;
	    static int count = 0;
		private void launch() {
			
			count=0;
			final Timer loadingTimer = new Timer();
			loadingTask = new TimerTask() {
	            public void run() {
	                count++;
	                if (count == 3 * 10) {
	                	loadingTimer.cancel();
	                	loadingTask.cancel();
	                	try {
	        				ApplicationManager.getApplicationManager().launch("Jalak_Harupat?"+System.currentTimeMillis());
	        			} catch (ApplicationManagerException e) {
	        				e.printStackTrace();
	        			}
	        			//System.exit(0);
	                }else if(count == 1*10 || count == 2*10 || count == 3*10){
	                	playTune();
	                }
	                

	            }
	        };
	        loadingTimer.scheduleAtFixedRate(loadingTask, 100, 100);
		}
	}
	
	private static final short[] tune = { 2349, 115, 0 };
	public static final boolean playTune() {
        if ( Alert.isAudioSupported() ) {
            Alert.startAudio(tune, 100);
            return true;
        }
        if ( Alert.isBuzzerSupported() ) {
            Alert.startBuzzer(tune, 100 );
            return true;
        }
        return false;
    }

}
