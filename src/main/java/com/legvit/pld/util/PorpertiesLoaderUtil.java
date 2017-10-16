package com.legvit.pld.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PorpertiesLoaderUtil {

	private Properties prop;
	
	private static PorpertiesLoaderUtil propertiesInstance = null;
	
	private PorpertiesLoaderUtil () {
		prop = new Properties();
		InputStream input = null;
		try {
			String filename = "config.properties";
			input = getClass().getClassLoader().getResourceAsStream(filename);
			if (input == null) {
				System.out.println("No se encontro el archivo: " + filename);
				return;
			}

			prop.load(input);
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static PorpertiesLoaderUtil getInstance() {
		if (propertiesInstance == null) {
			propertiesInstance = new PorpertiesLoaderUtil();
		} 
		return propertiesInstance;
	}
	
	public String getValue(String property) {
		return prop.getProperty(property);
	}
}
