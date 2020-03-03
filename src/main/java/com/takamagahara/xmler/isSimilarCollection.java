package com.takamagahara.xmler;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description: contains the result of judgement of similar and both the path list of origin and previous one.
 * User: kamisama
 * Date: 2020-03-03
 * Time: 上午10:32
 */
public class isSimilarCollection {
    private boolean result;
    private List<String> origin;
    private List<String> current;
    private boolean absoluteSame;

    public isSimilarCollection() {
        absoluteSame = false;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public List<String> getOrigin() {
        return origin;
    }

    public void setOrigin(List<String> origin) {
        this.origin = origin;
    }

    public List<String> getCurrent() {
        return current;
    }

    public void setCurrent(List<String> current) {
        this.current = current;
    }

    public boolean isAbsoluteSame() {
        return absoluteSame;
    }

    public void setAbsoluteSame(boolean absoluteSame) {
        this.absoluteSame = absoluteSame;
    }
}
