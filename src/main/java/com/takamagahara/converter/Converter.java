package com.takamagahara.converter;

import com.takamagahara.converter.envNodesUtils.NodesChainManager;
import com.takamagahara.xmler.SAXReaderStore;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description: Convert the xml to tex. Proxy object of envNodesUtils.NodesChainManager.
 * User: kamisama
 * Date: 2020-03-02
 * Time: 上午10:08
 */
public class Converter {
    String tex;
    private Element root;
    private String pathProject;
    private String nameProject;

    public Converter(String pathProject) {
        this.pathProject = pathProject;
        String[] tmp = pathProject.split("/");
        nameProject = tmp[tmp.length-1];
        SAXReader reader = SAXReaderStore.getInstance();
        Document document = null;
        try {
            document = reader.read(new File(pathProject+"/Target/"+nameProject+".xml"));
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        root = document.getRootElement();
    }

    public void Convert() {
        NodesChainManager manager = new NodesChainManager(root, pathProject);
        tex = manager.Process();
        writeTex();
    }

    public void writeTex() {
        FileWriter writer;
        try {
            writer = new FileWriter(pathProject+"/Target/"+nameProject+".tex");
            writer.write("");
            writer.write(tex);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
