package circularOrbit;

import centralObject.TGcenter;
import org.junit.Test;
import physicalObject.Athlete;
import relation.SocialRelation;
import type.TrackGame.GameType;
import relation.*;


import static org.junit.Assert.assertEquals;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: xinyu
 * Date: 2023-04-30
 * Time: 19:30
 */
public class TrackGameTest {
//    Test strategy
//    因为trackgame的功能在ConcreteCircularOrbitTest都有测试，所以只在这里测假

@Test
    public void gameTest(){
        CircularOrbit<TGcenter, Athlete> c=CircularOrbit.empty("TrackGame","src/file/TrackGame.txt");
    ((TrackGame)c).addAthlete(GameType.Score);
    assertEquals(100000,c.getLogicalDistance("1","2"));
    assertEquals(false,c.addRelationCtoP(((TrackGame) c).CentralObject,((TrackGame) c).PhysicalObjects.get(0), (Relation) new SocialRelation(0.98)));
    assertEquals(false, c.addRelationPtoP(((TrackGame) c).PhysicalObjects.get(0),((TrackGame) c).PhysicalObjects.get(1),new SocialRelation(0.01)));

}
}