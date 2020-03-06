package com.takamagahara.inier;

import org.ini4j.Ini;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: kamisama
 * Date: 2020-03-06
 * Time: 上午8:20
 */
public class IniInstanceStore {
    private volatile static Ini loader;

    private IniInstanceStore() {}

    public static Ini getInstance() {
        if (loader == null) {
            synchronized (IniInstanceStore.class) {
                if (loader == null) {
                    loader = new Ini();
                }
            }
        }
        return loader;
    }
}
