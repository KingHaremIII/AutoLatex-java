package com.takamagahara.converter.envNodes.document;

import com.takamagahara.converter.envNodes.EnvNode;
import com.takamagahara.converter.envNodesUtils.Containable;
import com.takamagahara.converter.envNodesUtils.Convertible;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: kamisama
 * Date: 2020-03-05
 * Time: 上午10:20
 */
public class Document extends EnvNode implements Convertible, Containable {
    @Override
    public String getPrefix() {
        return null;
    }

    @Override
    public String getText() {
        return null;
    }

    @Override
    public String getPostfix() {
        return null;
    }
}
