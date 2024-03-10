package circularOrbit;

import centralObject.SNcenter;
import centralObject.TGcenter;
import graph.ConcreteVerticesGraph;
import javafx.util.Pair;
import physicalObject.Athlete;
import physicalObject.Friend;
import position.Position;
import relation.SocialRelation;
import track.Track;
import track.TrackOfSocial;
import type.ApplicationType;
import type.SocialNetworkCircle.InSocialType;

import relation.Relation;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: xinyu
 * Date: 2023-04-29
 * Time: 10:32
 */
public class SocialNetworkCircle extends ConcreteCircularOrbit<SNcenter, Friend>{
    private ConcreteVerticesGraph<Person> G=new ConcreteVerticesGraph<>();//要将社交网络图转化成图结构
    //Abstraction function:
//根据文件中的数据转化成的图结构
    // Representation invariant:
//   对应图结构的RI
    // Safety from rep exposure:
//  可变，注意防御性拷贝
    /**
     * 从文本文件中读取建立轨道系统的数据信息，通过正则匹配处理并将信息交给socialNetworkCircleInit处理，最终返回构造好的系统
     * @param file 文本文件
     * @return 社交网络系统
     */
    public static SocialNetworkCircle newNetWork(String file){
        SocialNetworkCircle socialNetworkCircle=new SocialNetworkCircle();
        socialNetworkCircle.socialNetworkCircleInit(socialNetworkCircle.readFile(file));
        return socialNetworkCircle;
    }
    /**
     * 用处理好的文本文件信息给给社交网络系统初始化
     * @param ObjectList 处理好的文本文件信息
     */
    public void socialNetworkCircleInit(List<Pair<String, List<String>>> ObjectList){
        type= ApplicationType.SocialNetworkCircle;

        List<List<String>> tieList=new ArrayList<>();//关系列表的列表
        for(Pair<String, List<String>> eachPair:ObjectList){
            if(eachPair.getKey().equals(InSocialType.CentralUser.toString())){
                //用文件的数据构造一个中心物体
                this.addCentralObject(new SNcenter(eachPair.getValue()));
                G.add(CentralObject);
            }else if(eachPair.getKey().equals(InSocialType.Friend.toString())){
                //用文件的数据构造一个轨道物体
                Friend newFriend=new Friend(eachPair.getValue());
                this.PhysicalObjects.add(newFriend);
                G.add(newFriend);
            }else if(eachPair.getKey().equals(InSocialType.SocialTie.toString())){
                tieList.add(eachPair.getValue());
            }
        }
        for(List<String> eachList:tieList) {//分别找关系列表中第一位和第二位在轨道系统中对应的物体
            Person p1 = null;
            Person p2 = null;
            /////      文件中数据             输入的数据
            if (eachList.get(0).equals(this.CentralObject.getName())) {
                p1 = this.CentralObject;;
            }
            if (eachList.get(1).equals(this.CentralObject.getName())) {
                p2 = this.CentralObject;
            }
            for (Friend eachFriend : PhysicalObjects) {
                if (eachList.get(0).equals(eachFriend.getName())) {
                    p1 = eachFriend;//.clone();
                }
                if (eachList.get(1).equals(eachFriend.getName())) {
                    p2 = eachFriend;//.clone();
                }
            }
            G.set(p1, p2, Double.valueOf(eachList.get(2)));//在图中添加关系

            //***下面在图中添加关系，ConcreteCircularOrbit.java
        }
        PhysicalObjects.clear();
        this.makeGraph();
    }

    /**
     * 每一次对图进行修改，都要把之前的轨道系统信息清空，然后根据图结构重新建立轨道系统
     */
    public void clearAll(){
        PhysicalObjects.clear();
        tracks.clear();
        TrackAndObjects.clear();
        ObjectToTrack.clear();
        positionOfPhysicalObjects.clear();
        straightLine.clear();
    }
    /**
     * 通过图结构中的BFS算法来构造轨道系统
     * @return true
     */
    public boolean makeGraph(){
        //  物体           对应轨道编号   物体可达的物体集合
        Map<Person, Pair<Integer, Set<Person>>> buildMap=G.BFS(CentralObject);//中心的人对应着一个深度以及他可以到达的人的集合
        Map<Integer, Track> toTrack=new HashMap<>();
        for(Person eachP:buildMap.keySet()){
            if(eachP!=CentralObject){
                Pair<Integer, Set<Person>> tmpP=buildMap.get(eachP);//eachP对应的深度以及它可以到达的集合
                if(toTrack.containsKey(tmpP.getKey())){
                    //通过轨道编号获得对应的轨道
                    this.addPhysicalObject( (Friend)eachP,toTrack.get(tmpP.getKey()));//轨道物体集合里面加入eachP
                }else{
                    Track newTrack=new TrackOfSocial(tmpP.getKey());//eachP对应的深度即轨道编号
                    toTrack.put(tmpP.getKey(), newTrack);//轨道集合里面加入”新建立“的轨道
                    this.addTrack(newTrack);
                    this.addPhysicalObject( (Friend)eachP,toTrack.get(tmpP.getKey()));
                }
            }
        }
        for(Friend eachP:PhysicalObjects){//要清楚坐标是为了计算具体位置，在社交网络图中，这个功能很鸡肋，所以不写
            setPosition(eachP);//为每个轨道物体设立极坐标位置
        }
        //有连线的两个人之间的坐标关系添加到straightLine，证明有连线
        for(Person eachP:buildMap.keySet()){//物体1
            for(Person toP:buildMap.get(eachP).getValue()){//和物体1有连线的
                //看到了这里，刚才把Position类搞明白了，一会看ConcreteCircularOrbit.java里面的相关属性来理解下面代码
                //对于一个物体来说，有坐标值的带进去，没有坐标值的直接原点表示
                if(positionOfPhysicalObjects.get(eachP)!=null&&positionOfPhysicalObjects.get(toP)!=null)
                    straightLine.add(new Pair<>(positionOfPhysicalObjects.get(eachP), positionOfPhysicalObjects.get(toP)));
                else if(positionOfPhysicalObjects.get(eachP)==null&&positionOfPhysicalObjects.get(toP)!=null){
                    straightLine.add(new Pair<>(new Position((double)0,(double)0,1), positionOfPhysicalObjects.get(toP)));
                }else if(positionOfPhysicalObjects.get(eachP)==null&&positionOfPhysicalObjects.get(toP)!=null){
                    straightLine.add(new Pair<>(positionOfPhysicalObjects.get(eachP), new Position((double)0,(double)0,1)));
                }
            }
        }
        return true;
    }

    /**
     * 从图中把轨道物体删除
     * @param Object 要删除的轨道物体
     * @return 是否删除成功
     */
    public boolean removePhysicalObject(Friend Object) {
        if(!PhysicalObjects.contains(Object)){
            return false;
        }else{
            clearAll();
            G.remove(Object);//对图进行改变了
            this.makeGraph();//就要重建图
            return true;
        }
    }

    @Override
    public boolean removeTrack(Track track) {
        if(!tracks.contains(track)){
            return false;
        }else{
            for(Person eachP:TrackAndObjects.get(track)){
                G.remove(eachP);
            }
           clearAll();
            this.makeGraph();
            return true;
        }
    }
    //计算两个物体之间的逻辑距离：如果有一个物体是中心物体，那么距离就是轨道编号
//                      如果都是轨道物体，那么距离就是轨道编号的差值
    public int getLogicalDistance(String a,String b){
        if(CentralObject.getName().equals(a)){
            Friend bf=new Friend(b);
            return ObjectToTrack.get(bf).getNumber();
        }else if(CentralObject.getName().equals(b)){
            Friend af=new Friend(a);
            return ObjectToTrack.get(af).getNumber();
        }else{
            Friend af=new Friend(a);
            Friend bf=new Friend(b);
            return Math.abs(ObjectToTrack.get(af).getNumber()-ObjectToTrack.get(bf).getNumber());
        }
    }
    @Override
    public boolean addRelationCtoP(SNcenter Object1, Friend Object2, Relation relation) {
        G.add(Object2);                                                      //里面只有一个属性，即亲密度
        G.set(CentralObject,Object2,((SocialRelation)relation).getIntimacy());
        G.set(Object2,CentralObject,((SocialRelation)relation).getIntimacy());
        clearAll();
        makeGraph();
        return true;
    }

    @Override
    public boolean addRelationPtoP(Friend Object1, Friend Object2, Relation relation) {
        G.set(Object1,Object2,((SocialRelation)relation).getIntimacy());
        G.set(Object2,Object1,((SocialRelation)relation).getIntimacy());
        clearAll();
        makeGraph();
        return true;
    }
    //删除关系，这里只考虑了轨道物体和中心物体的关系，事实上
    public boolean removeRelationship(Friend f1, Friend f2){
            G.set(f1, f2, (double) 0);
            G.set(f2, f1, (double) 0);
            clearAll();
            makeGraph();
            return true;
    }

    //这个函数不用写的原因是每次增加关系，删除关系都会重新建图
    @Override
    public boolean transit(Friend Object, Track track) {
        return false;
    }
    public boolean addFriendInG(Friend f){
        if(PhysicalObjects.contains(f)){
            G.add(f);
            return true;
        }else {
            return false;
        }
    }
    @Override
    public void move(Friend Object, double sitha) {

    }
}
