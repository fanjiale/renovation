package com.andy.common.spring;

import com.andy.common.utils.AESUtil;
import com.andy.common.utils.StringUtil;
import com.andy.common.utils.AESUtil;
import com.andy.common.utils.FileUtils;
import com.andy.common.utils.StringUtil;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.core.io.Resource;

import java.io.*;
import java.net.URI;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 两种方式处理加密
 * 	  1.如果 properties中值为 password={ENC}1123333333 格式的，表示为加密数据，直接解密
 *    2.如果 properties中值为 password={PWD}123456 格式的，表示需要加密后写入，先加密再解密
 *    						  写入后也为 ENC:{}
 * @author root
 */
public class EncPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {
	private String key; 
	
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	private static final String ENC = "{ENC}";
	private static final String TO_ENC = "{PWD}";
//	// 加密后的
//	private static final Pattern encryptedPattern = Pattern.compile(ENC + ":\\{(\\w+)\\}");
//	// 需要加密的
//	private static final Pattern toEncryptedPattern = Pattern.compile(TO_ENC + ":\\{(\\w+)\\}");


	@Override
	protected String convertProperty(String propertyName, String propertyValue) {
		if(StringUtil.isEmpty(propertyValue)) return propertyValue;
		// 1.加密过的
		if(propertyValue.startsWith(ENC)){
			System.out.println("propertyName = " + propertyName + ", " + propertyValue);
			String encrypted = propertyValue.substring(ENC.length());
			if(encrypted != null){
				propertyValue = AESUtil.decrypt(getKey(), encrypted);
			}
		} else if(propertyValue.startsWith(TO_ENC)){
			// 如果是未加密的，也未经过转换的,取明文
			propertyValue = propertyValue.substring(TO_ENC.length());
		}
		// 使用spring自带的转换
		return super.convertProperty(propertyName, propertyValue); 
	}
	
	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		// 正常执行属性文件加载
		super.postProcessBeanFactory(beanFactory); 
		// 加载完后，遍历location，对properties进行加密
		for (Resource location : locations) {
			// 只能读的,就不管了
			encryptProperties(location);
		}
	}

	/**
	 * 属性文件加密方法
	 * @param resource
	 */
	private void encryptProperties(Resource resource) {
		// 存放加密后的,如可文件可写,需要回写到文件
		boolean doEncrypt = false;
		List<String> outputLine = new ArrayList<String>();
		BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(resource.getInputStream()));
            String line = null;
            do {
                line = bufferedReader.readLine(); //按行读取属性文件
                if (line != null) { //判断是否文件结束
                    if (line.trim().length() != 0) {  //是否为空行
                        line = line.trim();
                        if (!line.startsWith("#")) {
                            String[] lineParts = line.split("=");  //将属性名与值分离
                            String name = lineParts[0];       // 属性名
                            String value = lineParts[1];      //属性值
                            if (name != null && value != null) {
//                            	Matcher matcher = toEncryptedPattern.matcher(value);
                    		    if(value.startsWith(TO_ENC)){ // 需要加密
                    		    	value = AESUtil.encrypt(getKey(), value.substring(TO_ENC.length()));
                    		    	value = ENC + value;
                    		    	line = name + "=" + value;
                    		    	doEncrypt = true;
                    		    }
                            }
                        }
                    }
                    outputLine.add(line);
                }
            } while (line != null);
        }catch (Exception e) {
        	e.printStackTrace();
		}
		if(doEncrypt){
			saveToFile(resource, outputLine);
		}
	}

	/**
	 * 回写到属性文件
	 * @param resource 
	 * @param outputLine
	 */
	private void saveToFile(Resource resource, List<String> outputLine) {
		URI uri = null;
		boolean canWrite = false;
		File file = null;
		try {
			uri = resource.getURI();
			file = new File(uri);
			canWrite = file.canWrite();
		} catch (Exception e){
			System.err.println("无法更新properties文件" + resource);
		}
		if (canWrite) {
			File backupFile = new File(file.getAbsoluteFile() + "_" + System.currentTimeMillis());
			FileUtils.copyFile(file, backupFile.getAbsolutePath());// 备份一下
			 //创建临时文件
			BufferedWriter bufferedWriter = null;
			try {
				bufferedWriter = new BufferedWriter(new FileWriter(file));
				final Iterator<String> iterator = outputLine.iterator();
				while (iterator.hasNext()) { // 将加密后内容写入临时文件
					bufferedWriter.write(iterator.next());
					if (iterator.hasNext()) {
						bufferedWriter.newLine();
					}
				}
				bufferedWriter.flush();
			} catch (IOException e) {
				// 有异常的话就恢复原来的文件
				logger.error(e.getMessage(), e);
				// 恢复一下
				FileUtils.copyFile(backupFile, file.getAbsolutePath());
			} finally {
				if (bufferedWriter != null) {
					try {
						bufferedWriter.close();
					} catch (IOException e) {
						logger.error(e.getMessage(), e);
					}
				}
				backupFile.delete();
			}
		}
	}
	
	protected Resource[] locations;

	@Override
	public void setLocations(Resource[] locations) { // 由于location是父类私有，所以需要记录到本类的locations中
		super.setLocations(locations);
		this.locations = locations;
	}

	@Override
	public void setLocation(Resource location) { // 由于location是父类私有，所以需要记录到本类的locations中
		super.setLocation(location);
		this.locations = new Resource[] { location };
	}
}