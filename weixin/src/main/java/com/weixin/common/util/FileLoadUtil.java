package com.weixin.common.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class FileLoadUtil {
	private static String lineSeparator = System.getProperty("line.separator", "\n");  
	public static String loadAFileToString(File f) throws IOException {
        long beginTime = System.currentTimeMillis();
        BufferedReader br = null;
        String ret = null;
        try {
            br =  new BufferedReader(new FileReader(f));
            String line = null;
            StringBuffer sb = new StringBuffer((int)f.length());
            while( (line = br.readLine() ) != null ) {
                sb.append(line).append(lineSeparator);
            }
            ret = sb.toString();
        } finally {
            if(br!=null) {try{br.close();} catch(Exception e){} }
        }
        long endTime = System.currentTimeMillis();
        return ret;        
    }
}
