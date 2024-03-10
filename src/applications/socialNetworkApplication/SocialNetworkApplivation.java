package applications.socialNetworkApplication;

import applications.Interaction;
import circularOrbit.SocialNetworkCircle;
import physicalObject.Friend;
import relation.Relation;
import relation.SocialRelation;
import type.operations.SocialNetworkOperation;

import java.util.Arrays;

public class SocialNetworkApplivation {
    public static String[] Ops(){
        String[] reS=new String[SocialNetworkOperation.values().length];
        for(int i=0;i<SocialNetworkOperation.values().length;i++){
            reS[i]=SocialNetworkOperation.values()[i].toString();
        }
        return reS;
    }
    public static void doOperation(SocialNetworkCircle c, int op){
        String[] args=null;
        Friend nf=null;
        switch (op){
            case 4:
                System.out.println("请输入新朋友(name year sex):");
                args= Interaction.getArgs();
                nf=new Friend(Arrays.asList(args));
                c.addFriendInG(nf);
                break;
            case 5:
                System.out.println("请输入要删掉的朋友的名字:");
                args=Interaction.getArgs();
                nf=new Friend(args[0]);
                if(!c.removePhysicalObject(nf)){
                    System.out.println("这个人不存在.");
                }
                break;
            case 6:
                System.out.println("请输入两个人的名字和它们之间的亲密度:");
                args=Interaction.getArgs();
                nf=new Friend(args[0]);
                Friend nf2=new Friend(args[1]);
                Relation r=new SocialRelation(Double.valueOf(args[2]));
                if(!c.addRelationPtoP(nf, nf2, r)){
                    System.out.println("这两个人不存在.");
                }
                break;
            case 7:
                System.out.println("请输入两个人的名字和它们之间的亲密度:");
                args=Interaction.getArgs();
                nf=new Friend(args[0]);
                Friend nf1=new Friend(args[1]);
                if(!c.removeRelationship(nf,nf1)){
                    System.out.println("这两个人不存在.");
                }
                break;
        }
    }
}
