package applications.atomStructureApplication;

import applications.Interaction;
import circularOrbit.AtomStructure;
import physicalObject.Electron;
import track.TrackOfAtom;
import track.Track;
import type.operations.AtomStructureOperation;

public class AtomStructureApplication {
    public static String[] Ops(){
        String[] reS=new String[AtomStructureOperation.values().length];
        for(int i=0;i<AtomStructureOperation.values().length;i++){
            reS[i]=AtomStructureOperation.values()[i].toString();
        }
        return reS;
    }
    public static void doOperation(AtomStructure c, int op){
        String[] args=null;
        Track t=null;
        switch (op){
            case 4:
                System.out.println("请输入轨道编号：");
                args= Interaction.getArgs();
                t=new TrackOfAtom(Integer.valueOf(args[0]));
                if(!c.addTrack(t)){
                    System.out.println("输入的轨道已经存在于系统中！");
                }
                break;
            case 5:
                System.out.println("请输入轨道编号：");
                args= Interaction.getArgs();
                t=new TrackOfAtom(Integer.valueOf(args[0]));
                Electron newE=new Electron(c.getPhysicalObjects(t).size()+1);
                if(c.addPhysicalObject(newE,t)){
                    c.setPosition(newE);
                }else{
                    System.out.println("输入的轨道不存在.");
                }
                break;
            case 6:
                System.out.println("请输入要删除的电子在哪个轨道:");
                args=Interaction.getArgs();
                t=new TrackOfAtom(Integer.valueOf(args[0]));
                if(!c.getTracks().contains(t)){
                    System.out.println("输入的轨道不存在.");
                }
                else if(c.getPhysicalObjects(t).size()==0){
                    System.out.println("轨道上没有电子.");
                }
                else{
                    c.removePhysicalObject(c.getPhysicalObjects(t).get(0),t);
                }
                break;
            case 7:
                System.out.println("请输入电子原来所在轨道和要跃迁到的轨道:");
                args=Interaction.getArgs();
                t=new TrackOfAtom(Integer.valueOf(args[0]));
                Track t2=new TrackOfAtom(Integer.valueOf(args[1]));
                if((!c.getTracks().contains(t))||(!c.getTracks().contains(t2))){
                    System.out.println("输入的轨道不存在.");
                }
                else if(c.getPhysicalObjects(t).size()==0){
                    System.out.println("轨道上没有电子.");
                }
                c.transit(c.getPhysicalObjects(t).get(0), t2);
                break;
        }
    }
}
