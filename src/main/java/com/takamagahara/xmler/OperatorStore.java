package com.takamagahara.xmler;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: kamisama
 * Date: 2020-03-07
 * Time: 上午10:49
 */
public class OperatorStore {
    private static volatile Operator operator;

    private OperatorStore() {}

    public static Operator getInstance() {
        if (operator == null) {
            synchronized (OperatorStore.class) {
                if (operator == null) {
                    operator = new Operator();
                }
            }
        }
        return operator;
    }
}
