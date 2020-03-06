package com.takamagahara.inier;

import org.ini4j.Ini;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: kamisama
 * Date: 2020-03-05
 * Time: 下午10:11
 */
public class iniReader {
    /**
     * read *.ini
     * @param iniFile ini file
     * @return maps
     * @throws IOException
     */
    public static Map<String, Map<String, String>> ReadIniFile(File iniFile) throws IOException {
        Map<String, Map<String, String>> fileContent = new HashMap<>();

        Ini ini = IniInstanceStore.getInstance();
        ini.load(iniFile);

        for (String sectionName : ini.keySet()) {
            Map<String, String> sectionMap = new HashMap<>();
            for (String key : ini.get(sectionName).keySet()) {
                sectionMap.put(key, ini.get(sectionName).get(key));
            }
            fileContent.put(sectionName, sectionMap);
        }
        return fileContent;
    }
}
