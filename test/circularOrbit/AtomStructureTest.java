package circularOrbit;

import centralObject.AScenter;
import centralObject.CentralObject;
import org.junit.Test;
import physicalObject.Electron;
import physicalObject.PhysicalObject;
import relation.SocialRelation;
import track.TrackOfAtom;

import static org.junit.Assert.*;
/**
* Created with IntelliJ IDEA.
* Description:
* User: xinyu
* Date: 2023-04-30
* Time: 19:23
*/public class AtomStructureTest {
//test strategy
//    如果物体不在系统里，或者系统里没有要跃迁到的轨道，返回false
//    physicalObjects:在系统里的轨道物体，不在系统里的轨道物体
//    tracks：在系统里的轨道，不在系统里的轨道

    @Test
    public void transit() {
        CircularOrbit<CentralObject, Electron> c=CircularOrbit.empty("AtomStructure", "src/file/AtomicStructure.txt");
        Electron e1=new Electron(1);//根据文件可以知道,编号为1的电子在第一层轨道上
        TrackOfAtom shell5=new TrackOfAtom(5);
        TrackOfAtom shell1=new TrackOfAtom(1);
        Electron e2=new Electron(1000);//不在轨道上的物体
        TrackOfAtom shell6=new TrackOfAtom(6);//不在系统的轨道
        c.addPhysicalObject(e1,shell1);
//        c.addPhysicalObject(e2,shell5);
        assertEquals(false,c.transit(e2,shell1));
        assertEquals(true, c.transit(e1, shell5));
        assertEquals(false, c.transit(e1, shell6));
    }
//    test strategy
//
    @Test
    public void someTest(){
        CircularOrbit<AScenter, Electron> c=CircularOrbit.empty("AtomStructure", "src/file/AtomicStructure.txt");
        Electron e1=new Electron(1);
        Electron e2=new Electron(2);
        CentralObject cen=new AScenter("Rb");
        assertEquals(100000,c.getLogicalDistance("a","b"));
        assertEquals(false, c.addRelationCtoP((AScenter) cen,e1,new SocialRelation(0.99)));
        assertEquals(false,c.addRelationPtoP(e1,e2,new SocialRelation(0.99)));
    }
    }