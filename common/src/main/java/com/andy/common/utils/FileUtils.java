package com.andy.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * 文件操作工具类
 * 
 * @author root
 * 
 */
public class FileUtils {

	private static Logger logger = LoggerFactory.getLogger(FileUtils.class);

	/**
	 * 将文件复制到目标路径
	 * 
	 * @throws IOException
	 * 
	 * @throws IOException
	 */
	public static boolean copyFile(File file, String dest) {
		FileInputStream in = null;
		FileOutputStream out = null;
		try {
			logger.debug("File copy: dest = " + dest);
			if (in == null) {
				in = new FileInputStream(file);
			}
			File destFile = new File(dest);
			if (!destFile.exists()) {
				destFile.createNewFile();
			} else {
				logger.debug("复制文件 : 复制前文件已存在,更新替换");
				destFile.delete();
				destFile.createNewFile();
			}
			if (out == null) {
				out = new FileOutputStream(destFile);
			}
			int c;
			byte buffer[] = new byte[1024];
			while ((c = in.read(buffer)) != -1) {
				for (int i = 0; i < c; i++)
					out.write(buffer[i]);
			}
			return true;
		} catch (IOException e) {
			logger.error("复制文件: 将文件复制到目标路径出错" + e.getMessage());
		} finally {
			try {
				if (in != null) {
					in.close();
				}
				if (out != null) {
					out.close();
				}
			} catch (IOException e) {
				logger.error("复制文件: 关闭出错:" + e.getMessage());
			}
		}
		return false;
	}

	/**
	 * 创建多层目录
	 * 
	 * @param path
	 */
	public static void createDir(String path) {
		File file = new File(path);
		if (file.getParentFile() == null)
			return;
		if (file.getParentFile().exists()) {
			File dir = new File(path);
			if (!dir.exists()) {
				dir.mkdir();
				logger.debug("Create Dir : path = " + path + " is created!");
			}
		} else {
			File tmpFile = file.getParentFile();
			while (tmpFile != null) {
				createDir(tmpFile.getAbsolutePath());
				tmpFile = tmpFile.getParentFile();
			}
			createDir(path);
		}
	}

	/**
	 * 读取文件
	 * 
	 * @param file
	 * @return
	 */
	public static String readFile(File file, String charSet) {
		if (file == null)
			return null;
		BufferedReader br = null;
		InputStreamReader isr = null;
		StringBuffer sb = new StringBuffer();
		try {
			isr = new InputStreamReader(new FileInputStream(file), charSet);  
			br = new BufferedReader(isr);
			String temp = null;
			temp = br.readLine();
			while (temp != null) {
				sb.append(temp + "\n");
				temp = br.readLine();
			}
		} catch (FileNotFoundException e) {
			logger.error("文件读取++++ : 文件读取失败:" + e.getMessage());
		} catch (IOException e) {
			logger.error("文件读取++++ :" + e.getMessage());
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (isr != null) {
				try {
					isr.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return sb.toString();
	}

	/**
	 * 写入文件
	 * 
	 * @param file
	 * @param text
	 */
	public static void wirteFile(File file, String text, String charSet) {
		if (text == null || text.length() == 0) {
			return;
		}
		FileOutputStream out = null;
		BufferedWriter bufferedWriter = null;
		try {
			if (out == null) {
				out = new FileOutputStream(file);
			}
			bufferedWriter = new BufferedWriter(new OutputStreamWriter(out, charSet));
			bufferedWriter.write(text);
			text = null;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (bufferedWriter != null){
				try {
					bufferedWriter.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
