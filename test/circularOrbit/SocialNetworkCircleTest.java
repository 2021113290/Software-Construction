package circularOrbit;

import centralObject.CentralObject;
import centralObject.SNcenter;
import javafx.util.Pair;
import org.junit.Test;
import physicalObject.Friend;
import relation.SocialRelation;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: xinyu
 * Date: 2023-04-30
 * Time: 19:29
 */
public class SocialNetworkCircleTest {
    CircularOrbit<SNcenter, Friend> c=CircularOrbit.empty("SocialNetworkCircle", "src/file/SocialNetworkCircle.txt");
    public SNcenter TommyWong=new SNcenter(Arrays.asList("TommyWong","30","M"));
    public Friend LisaWong=new Friend(Arrays.asList("LisaWong","25","F"));
    public Friend TomWong=new Friend(Arrays.asList("TomWong","61","M"));
    public Friend FrankLee=new Friend(Arrays.asList("FrankLee","42","M"));
    public Friend DavidChen=new Friend(Arrays.asList("DavidChen","55","M"));
//    test strategy
//    测试能否根据输入数据建图
    @Test
    public void makeGraph() {
        CircularOrbit<SNcenter, Friend> c=CircularOrbit.empty("SocialNetworkCircle", "src/file/SocialNetworkCircle.txt");
        HashMap<Person, Pair<Integer, HashSet<Person>>> ansMap=new HashMap<Person, Pair<Integer, HashSet<Person>>>();
        ansMap.put((Person)TommyWong, new Pair<Integer, HashSet<Person>>(0, new HashSet<Person>(Arrays.asList(LisaWong, TomWong, DavidChen))));
        ansMap.put((Person)LisaWong, new Pair<Integer, HashSet<Person>>(1, new HashSet<Person>(Arrays.asList(TommyWong))));
        ansMap.put((Person)TomWong, new Pair<Integer, HashSet<Person>>(1, new HashSet<Person>(Arrays.asList(TommyWong,FrankLee))));
        ansMap.put((Person)DavidChen, new Pair<Integer, HashSet<Person>>(1, new HashSet<Person>(Arrays.asList(TommyWong,FrankLee))));
        ansMap.put((Person)FrankLee, new Pair<Integer, HashSet<Person>>(1, new HashSet<Person>(Arrays.asList(TomWong,DavidChen))));
        assertEquals(true, ((SocialNetworkCircle)c).makeGraph());
    }
    //test strategy
//    测试得到了两物体的逻辑距离是否和输入数据一样
    @Test
    public void getLogicalDistance() {
        CircularOrbit<SNcenter, Friend> c=CircularOrbit.empty("SocialNetworkCircle", "src/file/SocialNetworkCircle.txt");
        assertEquals(1,c.getLogicalDistance("TommyWong","LisaWong"));
        assertEquals(0,c.getLogicalDistance("TomWong","LisaWong"));
    }
    //test strategy
//    测试能否成功添加中心物体和轨道物体的关系
    @Test
    public void addRelationCtoP() {
        CircularOrbit<SNcenter, Friend> c=CircularOrbit.empty("SocialNetworkCircle", "src/file/SocialNetworkCircle.txt");
        assertEquals(true,c.addRelationCtoP(TommyWong,LisaWong,new SocialRelation(0.99)));
    }
    //test strategy
//    测试能否成功添加中轨道物体和轨道物体的关系
    @Test
    public void addRelationPtoP() {
        CircularOrbit<SNcenter, Friend> c=CircularOrbit.empty("SocialNetworkCircle", "src/file/SocialNetworkCircle.txt");
        assertEquals(true,c.addRelationPtoP(TomWong,LisaWong,new SocialRelation(0.99)));

    }
    //test strategy
//    测试能否成功删除轨道物体和轨道物体的关系
    @Test
    public void removeRelationship() {
        CircularOrbit<SNcenter, Friend> c=CircularOrbit.empty("SocialNetworkCircle", "src/file/SocialNetworkCircle.txt");
        assertEquals(true,((SocialNetworkCircle)c).removeRelationship(TomWong,LisaWong));

    }

}