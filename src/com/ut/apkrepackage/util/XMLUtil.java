package com.ut.apkrepackage.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.Map;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

public class XMLUtil {

    public static Document getDocument(String xmlFile) {
        SAXReader reader = new SAXReader();
        reader.setEncoding("UTF-8");
        File file = new File(xmlFile);
        try {
            if (!file.exists()) {
                return null;
            } else {
                return reader.read(file);
            }
        } catch (DocumentException e) {
            throw new RuntimeException(e + "->ָ���ļ���" + xmlFile + "����ȡ����");
        }
    }

    public static Document getDocument_gb2312(String xmlFile) {
        SAXReader reader = new SAXReader();
        reader.setEncoding("gb2312");
        File file = new File(xmlFile);
        try {
            if (!file.exists()) {
                return null;
            } else {
                return reader.read(file);
            }
        } catch (DocumentException e) {
            throw new RuntimeException(e + "->ָ���ļ���" + xmlFile + "����ȡ����");
        }
    }

    public static String getText(Element element) {
        try {
            return element.getTextTrim();
        } catch (Exception e) {
            throw new RuntimeException(e + "->ָ����" + element.getName() + "���ڵ��ȡ����");
        }

    }

    public static Document addElementByName(Document document, String elementName, Map<String, String> attrs, String cdata) {
        try {
            Element root = document.getRootElement();
            Element subElement = root.addElement(elementName);
            for (Map.Entry<String, String> attr : attrs.entrySet()) {
                subElement.addAttribute(attr.getKey(), attr.getValue());
            }
            subElement.addCDATA(cdata);
        } catch (Exception e) {
            throw new RuntimeException(e + "->ָ���ġ�" + elementName + "���ڵ����ӳ��ִ���");
        }
        return document;
    }


    @SuppressWarnings("unchecked")
    public static Document deleteElementByName(Document document, String elementName) {
        Element root = document.getRootElement();
        Iterator<Object> iterator = root.elementIterator("file");
        while (iterator.hasNext()) {
            Element element = (Element) iterator.next();
            // ������������ȡ����ֵ
            Attribute attribute = element.attribute("name");
            if (attribute.getValue().equals(elementName)) {
                root.remove(element);
                document.setRootElement(root);
                break;
            }
        }
        return document;
    }

    public static void writeXml(Document document, String filePath) throws IOException {
        File xmlFile = new File(filePath);
        XMLWriter writer = null;
        try {
            if (xmlFile.exists())
                xmlFile.delete();
            writer = new XMLWriter(new FileOutputStream(xmlFile), OutputFormat.createPrettyPrint());
            writer.write(document);
            writer.close();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null)
                writer.close();
        }
    }


    public static Document createDocument(String rootName, String attributeName, String attributeVaule) {
        Document document = null;
        try {
            document = DocumentHelper.createDocument();
            Element root = document.addElement(rootName);
            root.addAttribute(attributeName, attributeVaule);
        } catch (Exception e) {
            throw new RuntimeException(e + "->�����ġ�" + rootName + "�����ڵ���ִ���");
        }
        return document;
    }


    @SuppressWarnings("unchecked")
    public static Document deleteElementAddressByName(Document document, String elementName) {
        Element root = document.getRootElement();
        Iterator<Object> iterator = root.elementIterator("address");
        while (iterator.hasNext()) {
            Element element = (Element) iterator.next();
            // ������������ȡ����ֵ
            Attribute attribute = element.attribute("name");
            if (attribute.getValue().equals(elementName)) {
                root.remove(element);
                document.setRootElement(root);
                break;
            }
        }
        return document;
    }
    

    @SuppressWarnings("unchecked")
    public static Document deleteElementByAttribute(Document document, String xpath, String attrName, String attrValue) {
        Iterator<Object> iterator = document.selectNodes(xpath).iterator();
        while (iterator.hasNext()) {
            Element element = (Element) iterator.next();
            Element parentElement = element.getParent();
            // ������������ȡ����ֵ
            Attribute attribute = element.attribute(attrName);
            if (attribute.getValue().equals(attrValue)) {
                parentElement.remove(element);
            }
        }
        return document;
    }
}
