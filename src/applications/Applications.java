package applications;

import APIs.CircularOrbitAPIs;
import APIs.CircularOrbitHelper;
import applications.atomStructureApplication.AtomStructureApplication;
import applications.socialNetworkApplication.SocialNetworkApplivation;
import applications.trackGameApplication.TrackGameApplication;
import circularOrbit.AtomStructure;
import circularOrbit.CircularOrbit;
import circularOrbit.SocialNetworkCircle;
import circularOrbit.TrackGame;
import type.ApplicationType;
import type.operations.BaseOperationType;
import type.TrackGame.GameType;
import visual.Visual;


import java.util.ArrayList;
import java.util.List;

public class Applications {
    public static void doOperation(CircularOrbit c){
        Visual v = CircularOrbitHelper.visualize(c);
        v.show();
        List<String> args=new ArrayList<String>();
        for(BaseOperationType eachOp:BaseOperationType.values()){
            args.add(eachOp.toString());
        }
        String[] ops=null;
        switch (c.getType()){
            case TrackGame:
                ops=TrackGameApplication.Ops();
                break;
            case SocialNetworkCircle:
                ops= SocialNetworkApplivation.Ops();
                break;
            case AtomStructure:
                ops= AtomStructureApplication.Ops();
                break;
        }
        for(int i=0;i<ops.length;i++){
            args.add(ops[i]);
        }
        while(true){
            v.dispose();
            v.show();
            int op=Interaction.chooseOp(args);
            if(op==-1){
                v.dispose();
                break;
            }
            switch (op) {
                case 0:
                    System.out.println("请输入轨道编号：");
                    break;
                case 1:
                    System.out.println(CircularOrbitAPIs.getObjectDistributionEntropy(c));
                    break;
                case 2:
                    System.out.println("请输入两个轨道物体的名字:");
                    break;
                case 3:
                    System.out.println("请输入两个轨道物体的名字:");
                    break;
            }
            String[] arg=null;
            if(op==0||op==2||op==3)
                arg=Interaction.getArgs();
            switch (op){
                case 0:
                    CircularOrbitAPIs.deleteTrack(c, arg);
                    break;
                case 2:
                    System.out.println(CircularOrbitAPIs.getPhysicalDistance(c, arg));
                    break;
                case 3:
                    System.out.println(CircularOrbitAPIs.getLogicalDistance(c, arg)-1);
                    break;
            }
            if(op>=4){
                switch (c.getType()){
                    case TrackGame:
                        TrackGameApplication.doOperation((TrackGame)c, op);
                        break;
                    case SocialNetworkCircle:
                        SocialNetworkApplivation.doOperation((SocialNetworkCircle)c, op);
                        break;
                    case AtomStructure:
                        AtomStructureApplication.doOperation((AtomStructure) c, op);
                        break;
                }
            }
        }
    }
    public static CircularOrbit newApplication(){
        String path="src/file";
        ApplicationType newType=Interaction.chooseApp();
        String fileName=path+"/"+Interaction.chooseFile("src\\file");
        CircularOrbit c=CircularOrbit.empty(newType.toString(),fileName);
        if(newType.equals(ApplicationType.TrackGame)){
            GameType strategy=Interaction.chooseGameType();
            ((TrackGame)c).addAthlete(strategy);
        }
        return c;
    }
}
