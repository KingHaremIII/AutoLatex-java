package com.takamagahara.converter.envNodes.document.body;

/**
 * Created with IntelliJ IDEA.
 * Description: Text in sections. Maintaining a String due to the fact that java copies value of basic type,
 *     at the same time XMLer.reader cannot return the value of Method, however.
 * User: kamisama
 * Date: 2020-03-05
 * Time: 下午1:11
 */
public class Text {
    private String content;

    public Text() {
        content = "";
    }

    public void  add(String s) {
        content += s;
    }

    public String getContent() {
        return content;
    }
}
