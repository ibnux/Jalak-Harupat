package com.jalak.harupat.capture.util;

/*
 *  Code by iBNuX.net April 2010
 *  +6285624644268
 *  me@ibnux.net
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.microedition.io.Connector;
import javax.microedition.io.file.FileConnection;

public class Util {

	public static int getSize(String path){
		try{
			FileConnection fconn = (FileConnection)Connector.open(path,Connector.READ);
	        int nn = 0;
			if (fconn.exists()){
	        	 nn = (int)fconn.fileSize();
	        }
	        fconn.close();
	        return nn;
		}catch (IOException ioe) {
	    }
		return 0;
	}


    public static String bacaFile(String path){
        return baca(path, false);
    }
    public static String bacaFile(String path, boolean utf){
        return baca(path, utf);
    }

    private static String baca(String path, boolean utf){
    	try {
            InputStream inputStream = null;
            String resultString = null;
            FileConnection fileConnection = (FileConnection) Connector.open(path);
            if (fileConnection.exists()) {
                inputStream = fileConnection.openInputStream();
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                while (true) {
                    int bytesRead = inputStream.read( buffer, 0, 1024 );
                    if (bytesRead == -1)
                        break;
                    byteArrayOutputStream.write( buffer, 0, bytesRead );
                }
                byteArrayOutputStream.flush();
                byte[] result = byteArrayOutputStream.toByteArray();
                fileConnection.close();
                inputStream.close();
                byteArrayOutputStream.close();
                if(utf){
                	resultString = new String(result,"UTF-8");
                }else{
                	resultString = new String(result);
                }
                return resultString;
            }else{
                //fileConnection.create();
                return null;
            }

        }catch (IOException ioe) {
            System.out.println(ioe.toString());
            return null;

        }
    }

	public static boolean cekDir(String path, boolean mkdir){
		//if mkdir true, will make new folder
		boolean hasil = false;
		try{
			FileConnection fc1 = (FileConnection) Connector.open(path, Connector.READ_WRITE);
			if (!fc1.exists()) {
				if(mkdir){
					fc1.mkdir();
					hasil = true;
				}else{
					hasil = false;
				}
			}else{
				hasil = true;
			}
			fc1.close();
			return hasil;
		}catch(Exception e){
		}
		return false;
	}

    public static boolean tulisFile(String path,String txt){
        try {
            FileConnection fconn = (FileConnection)Connector.open(path,Connector.READ_WRITE);
            if (!fconn.exists()){
                fconn.create();  // create the file if it doesn't exist
        	}else{
        		fconn.delete();
        		fconn.create();
        	}
            OutputStream os =fconn.openOutputStream();
            os.write(txt.getBytes());
            os.close();
            fconn.close();
            return true;
        }
        catch (IOException ioe) {
            return false;
        }
    }

}
