package com.takamagahara;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: kamisama
 * Date: 2020-03-05
 * Time: 上午10:52
 */
public class A extends BaseTest implements InterfaceTest {
    @Override
    public String toString() {
        return "A";
    }

    @Override
    public String show() {
        return "A show";
    }

    @Override
    public void display() {
        System.out.println("A display");
    }
}
