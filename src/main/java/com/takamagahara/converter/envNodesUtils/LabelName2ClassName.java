package com.takamagahara.converter.envNodesUtils;

import com.takamagahara.inier.iniReader;

import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description: read LabelConfig.ini to gain the key-value pair of xml label and class name. 
 * User: kamisama
 * Date: 2020-03-06
 * Time: 下午2:11
 */
public class LabelName2ClassName {
    private Map<String, Map<String, String>> config;

    public LabelName2ClassName(String path) throws IOException {
        config = iniReader.ReadIniFile(new File(path+"/LabelConfig.ini"));
    }

    public Map<String, Map<String, String>> getConfig() {
        return config;
    }
}
