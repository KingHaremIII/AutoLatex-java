package com.takamagahara.converter;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description: Convert the xml to tex. Proxy object of envNodesUtils.NodesChainManager.
 * User: kamisama
 * Date: 2020-03-02
 * Time: 上午10:08
 */
public class Converter {
    private List<Element> elementList;
    private Element root;

    public Converter(String path) {
        SAXReader reader = new SAXReader();
        Document document = null;
        try {
            document = reader.read(new File(path));
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        root = document.getRootElement();
        elementList = root.elements();
    }

    public void Convert() {
        for (Element e : elementList) {
            
        }
    }
}
