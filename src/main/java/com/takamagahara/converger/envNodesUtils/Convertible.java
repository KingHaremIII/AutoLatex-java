package com.takamagahara.converger.envNodesUtils;

/**
 * Created with IntelliJ IDEA.
 * Description: Labels in documents root label should all implement this interface,
 *     so that they can generate text of tex file.
 * User: kamisama
 * Date: 2020-03-04
 * Time: 下午10:13
 */
public interface Convertible {
    String getText();
}
