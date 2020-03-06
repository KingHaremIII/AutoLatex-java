package com.takamagahara.converter.envNodesUtils;

/**
 * Created with IntelliJ IDEA.
 * Description: This interface is used to mark the nodes which can be processed by converter.envNodesUtils.NodesChainManager.
 *     Container has no text (getText() should return empty and it is useless whatever it' set
 *         due to the reality that the getText() of container will never be used. )
 * User: kamisama
 * Date: 2020-03-05
 * Time: 上午10:18
 */
public interface Containable {

}
