package com.takamagahara.converter.envNodes;

import com.takamagahara.converter.envNodesUtils.Convertible;
import org.dom4j.Element;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: kamisama
 * Date: 2020-03-05
 * Time: 上午8:12
 */
public abstract class EnvNode implements Convertible {
    @Override
    public String toString() {
        return getPrefix()+getText()+getPostfix();
    }
}
