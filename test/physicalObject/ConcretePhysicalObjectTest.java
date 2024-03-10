package physicalObject;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
/**
* Created with IntelliJ IDEA.
* Description:
* User: xinyu
* Date: 2023-04-30
* Time: 19:19
*/public class ConcretePhysicalObjectTest {
    @Test
    public void getNameTest() {
        List<String> relist=new ArrayList<>();
        relist.add("Bolt");
        relist.add("1");
        relist.add("JAM");
        relist.add("38");
        relist.add("9.94");
        Athlete athlete=new Athlete(relist);
        assertEquals("Bolt",athlete.getName());
        Electron electron=new Electron(1);
        assertEquals("1",electron.getName());
        Friend friend=new Friend("LisaWong");
        assertEquals("LisaWong",friend.getName());
    }
}