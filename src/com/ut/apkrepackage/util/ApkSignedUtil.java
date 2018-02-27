package com.ut.apkrepackage.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;

public class ApkSignedUtil {
	public static void createDatFile(File file, String ...codes) throws IOException {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            for(String code : codes) {
                    writer.write(code, 0, code.length());
                writer.newLine();
            }
	    writer.close();
	}
	
	public static void updateAndroidMainifest(String filePath, String appPackage, String versionCode, String versionName) throws DocumentException, IOException {		
            Document doc = XMLUtil.getDocument(filePath);  
            Element root = doc.getRootElement();          
            Attribute packageAttr = root.attribute("package");
            packageAttr.setValue(appPackage);
             
            Attribute versionCodeAttr = root.attribute("android:versionCode");
            if(versionCodeAttr != null) {
                versionCodeAttr.setValue(versionCode);
            } else {
                root.addAttribute("android:versionCode", versionCode); 
            } 
            
            Attribute versionNameAttr = root.attribute("android:versionName");
            if(versionNameAttr != null) {
                versionNameAttr.setValue(versionName);
            } else {
                root.addAttribute("android:versionName", versionName);  
            } 
            

            XMLUtil.writeXml(doc, filePath);
	}
	
	public static void updateAppName(String filePath, String appName) throws DocumentException, IOException {		
            Document doc = XMLUtil.getDocument(filePath);  
            Element root = doc.getRootElement();          
            Iterator<?> it = root.elementIterator();  
            while(it.hasNext()){  
                Element elem = (Element) it.next();  
                if("app_name".equals(elem.attribute("name").getValue())) {
                    elem.setText(appName);
                } 
            }  
            XMLUtil.writeXml(doc, filePath);
	}
	
}
