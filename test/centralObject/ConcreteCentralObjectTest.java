package centralObject;

import static org.junit.Assert.assertEquals;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: xinyu
 * Date: 2023-04-30
 * Time: 19:18
 */
public class ConcreteCentralObjectTest {

    @org.junit.Test
    public void getNameTest() {
        CentralObject AScenter=new AScenter("原子核1");
        assertEquals("原子核1",AScenter.getName());
        CentralObject SNcenter=new SNcenter("中心人");
        assertEquals("中心人",SNcenter.getName());
        CentralObject TGcenter=new TGcenter();
        assertEquals("null",TGcenter.getName());

    }
}