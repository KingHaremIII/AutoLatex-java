package com.takamagahara.reflector;

import com.takamagahara.xmler.XMLer;
import com.takamagahara.xmler.isSimilarCollection;
import org.dom4j.DocumentException;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.lang.reflect.InvocationTargetException;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: kamisama
 * Date: 2020-03-01
 * Time: 下午2:33
 */
public class ReflectorTest {
    private String pProject = "/home/kamisama/IdeaProjects/AutoLatex/src/main/resources/IEEEtranTest";

    @Test
    public void Test() throws DocumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Reflector reflector = new Reflector("/home/kamisama/IdeaProjects/AutoLatex/src/main/resources/IEEEtranTest/Structure.xml");
        reflector.ReflectSafe("/home/kamisama/IdeaProjects/AutoLatex/src/main/resources/IEEEtranTest");
    }

    @Test
    public void BackupForBackup() {
        try{
            FileInputStream fis = new FileInputStream(pProject+"/"+".StructureBackup.xml");
            FileOutputStream fos = new FileOutputStream(pProject+"/"+"StructureBackup.xml");

            //创建搬运工具
            byte datas[] = new byte[1024*8];
            //创建长度
            int len = 0;
            //循环读取数据
            while((len = fis.read(datas))!=-1){
                fos.write(datas,0,len);
            }
            //3.释放资源
            fis.close();
            fis.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
