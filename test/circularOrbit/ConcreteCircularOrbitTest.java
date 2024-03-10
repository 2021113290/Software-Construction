package circularOrbit;

import centralObject.AScenter;
import centralObject.CentralObject;
import org.junit.Test;
import physicalObject.Electron;
import track.Track;
import track.TrackOfAtom;

import static org.junit.Assert.*;
/**
* Created with IntelliJ IDEA.
* Description:
* User: xinyu
* Date: 2023-04-30
* Time: 19:22
*/public class ConcreteCircularOrbitTest {
//    Test strategy
//    划分addTrack()的输入如下：
//    已经在系统中的轨道，不在系统中的轨道
//    覆盖了在系统中的轨道和不在系统中的轨道
    @Test
    public void addTrack() {
        CircularOrbit<CentralObject, Electron> c=CircularOrbit.empty("AtomStructure","src/file/AtomicStructure.txt");
        Track track1=new TrackOfAtom(1);
        Track track2=new TrackOfAtom(6);
        assertFalse(c.addTrack(track1));//添加的轨道已经在系统中存在了，返回false
        assertTrue(c.addTrack(track2));//添加轨道成功
    }
//  Test strategy
//    划分removeTrack()的输入如下：
//    已经在系统中的轨道，不在系统中的轨道
//    覆盖了在系统中的轨道和不在系统中的轨道
    @Test
    public void removeTrack() {
        CircularOrbit<CentralObject, Electron> c=CircularOrbit.empty("AtomStructure","src/file/AtomicStructure.txt");
        Track track1=new TrackOfAtom(1);
        Track track2=new TrackOfAtom(6);
        assertTrue(c.removeTrack(track1));//轨道系统中有要删的轨道，则返回true
        assertFalse(c.removeTrack(track2));//轨道系统中没有要删的轨道，则返回false
    }
    //  Test strategy
//    划分addCentralObject()的输入如下：
//    当系统没有中心物体时,添加中心物体;当系统有中心物体时,添加中心物体
//    覆盖了在添加中心物体时系统中有没有中心物体
    @Test
    public void addCentralObject() {
//只要从文件中读取数据创建了图,那么中心物体就不为空
        CircularOrbit<CentralObject, Electron> c=CircularOrbit.empty("AtomStructure","src/file/AtomicStructure.txt");
        assertEquals(false,c.addCentralObject(new AScenter("2")));
    }
    //  Test strategy
//    划分addPhysicalObject()的输入如下：
//    已经在系统中的轨道，不在系统中的轨道
//    覆盖了在系统中的轨道和不在系统中的轨道
    @Test
    public void addPhysicalObject() {
        CircularOrbit<CentralObject, Electron> c=CircularOrbit.empty("AtomStructure","src/file/AtomicStructure.txt");
        Electron e1=new Electron(1);
        Track track=new TrackOfAtom(1);
        Track track1=new TrackOfAtom(6);
        assertEquals(true,c.addPhysicalObject(e1,track));
        assertEquals(false,c.addPhysicalObject(e1,track1));
    }
    //  Test strategy
//    划分addPhysicalObject()的输入如下：
//    track:已经在系统中的轨道，不在系统中的轨道
//    PhysicalObjects:存在，不存在
//    覆盖了在系统中的轨道和不在系统中的轨道
    @Test
    public void removePhysicalObject() {
        CircularOrbit<CentralObject, Electron> c=CircularOrbit.empty("AtomStructure","src/file/AtomicStructure.txt");
        Electron e1=new Electron(1);
        Track track=new TrackOfAtom(1);
        Track track1=new TrackOfAtom(6);
        Electron e2=new Electron(100);
        assertEquals(true,c.removePhysicalObject(e1,track));
        assertEquals(false,c.removePhysicalObject(e1,track1));
        assertEquals(false,c.removePhysicalObject(e2,track));
    }

}