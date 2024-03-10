package applications.trackGameApplication;

import applications.Interaction;
import circularOrbit.TrackGame;
import physicalObject.Athlete;
import track.TrackOfGame;
import track.Track;
import type.operations.TrackGameOperation;

import java.util.Arrays;
import java.util.Collections;

public class TrackGameApplication {
    public static String[] Ops(){
        String[] reS=new String[TrackGameOperation.values().length];
        for(int i=0;i<TrackGameOperation.values().length;i++){
            reS[i]=TrackGameOperation.values()[i].toString();
        }
        return reS;
    }
    public static boolean deleteTrack(String trackName){
        return false;
    }
    public static void doOperation(TrackGame c, int op){
        String[] args=null;
        Track t=null;
        Athlete newA=null;
        switch (op){
                //add track
            case 4:
                System.out.println("请输入轨道编号");
                args= Interaction.getArgs();
                t=new TrackOfGame(Integer.valueOf(args[0]));
                if(!c.addTrack(t)){
                    System.out.println("输入的轨道编号不存在");
                }
                break;
                //add ob
            case 5:
                System.out.println("请输入要增加的运动员信息(Name Number Country Year bestRecord) 和他所属的轨道:");
                args=Interaction.getArgs();
                newA =new Athlete(Arrays.asList(args));
                t=new TrackOfGame(Integer.valueOf(args[5]));
                if(c.addPhysicalObject( newA,t)){
                    c.setPosition(newA);
                }else{
                    System.out.println("输入的轨道不存在.");
                }
                break;
                //dele ob
            case 6:
                System.out.println("请输入运动员的姓名");
                args=Interaction.getArgs();
                newA=new Athlete(Collections.singletonList(args[0]));
                if(!c.removePhysicalObject(newA)){
                    System.out.println("输入的运动员不存在.");
                }
                break;
        }
    }
}
