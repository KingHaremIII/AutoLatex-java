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
    private boolean delete;
    private boolean modify;
    private boolean increment;

    public isSimilarCollection() {
        absoluteSame = false;
        delete = false;
        modify = false;
        increment = false;
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

    public boolean isDelete() {
        return delete;
    }

    public void setDelete(boolean delete) {
        this.delete = delete;
    }

    public boolean isModify() {
        return modify;
    }

    public void setModify(boolean modify) {
        this.modify = modify;
    }

    public boolean isIncrement() {
        return increment;
    }

    public void setIncrement(boolean increment) {
        this.increment = increment;
    }
}
