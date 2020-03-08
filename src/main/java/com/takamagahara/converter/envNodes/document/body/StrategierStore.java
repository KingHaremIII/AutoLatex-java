package com.takamagahara.converter.envNodes.document.body;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: kamisama
 * Date: 2020-03-07
 * Time: 下午6:40
 */
public class StrategierStore {
    private static volatile Strategier strategier;

    private StrategierStore() {}

    public static Strategier getInstance() {
        if (strategier == null) {
            synchronized (StrategierStore.class) {
                if (strategier == null) {
                    strategier = new Strategier();
                }
            }
        }
        return strategier;
    }
}
