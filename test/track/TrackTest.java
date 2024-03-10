package track;

import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.*;
/**
* Created with IntelliJ IDEA.
* Description:
* User: xinyu
* Date: 2023-04-30
* Time: 19:21
*/public class TrackTest {
    @Test
    public void getNumberAndRadiusTest(){
        TrackOfGame track=new TrackOfGame(1);
        assertEquals(1,(int)track.getNumber());
        assertEquals(1,track.getRadius(),0);
    }

}