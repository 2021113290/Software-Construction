package circularOrbit;

import centralObject.TGcenter;
import javafx.util.Pair;
import physicalObject.Athlete;
import position.Position;
import strategy.OperationRandom;
import strategy.OperationSort;
import track.Track;
import track.TrackOfGame;
import type.ApplicationType;
import type.TrackGame.GameType;
import type.TrackGame.InGameType;

import relation.Relation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description:赛跑
 * 轨道是圆形，每个轨道上有一个轨道物体，没有中心物体，运动员彼此不存在关系，比赛过程中运动员不能从一个轨道移动到另一个轨道
 * 给定一组运动员，通过比赛方案编排算法，将他们分为 n 组，为每组内的每个运动员分配具体的跑道。
 * 由于是比赛，很容易想到设计模式里面的策略模式：体现给每组运动员分配赛道上，随机分配或者是最好记录分配
 * Game ::= 100|200|400 表明比赛项目，只能这三个选择
 * 之一
 * NumOfTracks ::= 跑道数目，4 到 10 之间的整数
 * Athlete ::= <姓名,号码,国籍,年龄,本年度最好成绩>
 * 代表一个运动员，其中姓名是 word，号码和年龄是正整数，
 * 国籍是三位大写字母，成绩是最多两位整数和必须两位小数
 * 构成（例如 9.10、10.05）
 * User: xinyu
 * Date: 2023-04-17
 * Time: 23:19
 */
public class TrackGame extends ConcreteCircularOrbit<TGcenter, Athlete>{
    private int typeOfGame;
    private int numOfTracks;
    private int numOfGroups;
    private Map<Athlete,Integer> AthleteToGroup =new HashMap<>();
    private Map<Integer,List<Athlete>> GroupWithAthlete=new HashMap<>();
    private double angle;

//`Abstract Function:
//    映射为整个赛跑系统
// Representation Invariant
//   typeOfGame取值为 100|200|400 表明比赛项目，只能这三个选择
//    NumOfTracks ::= 跑道数目，4 到 10 之间的整数
// Safety from rep exposure:
//    属性用private修饰
    private void checkRep(){
        assert typeOfGame==100||typeOfGame==200||typeOfGame==400;
        assert numOfTracks>=4&&numOfTracks<=10;
    }
    /**
     * 从文本文件中读取建立轨道系统的数据信息，通过正则匹配处理并将信息交给trackGameInit处理，最终返回构造好的系统
     * @param file 文本文件
     * @return 赛跑轨道系统
     */
    public static TrackGame newGame(String file){
        TrackGame trackGame=new TrackGame();
        trackGame.trackGameInit(trackGame.readFile(file));
        return trackGame;
    }
    /**
     * 用处理好的文本文件信息给给赛跑轨道系统初始化
     * @param ObjectList 处理好的文本文件信息
     */
    public void trackGameInit(List<Pair<String,List<String>>> ObjectList){
        type= ApplicationType.TrackGame;

        for (Pair<String,List<String>> pair:ObjectList) {
            if (pair.getKey().equals(InGameType.Game.toString())){
                this.typeOfGame= Integer.parseInt(pair.getValue().get(0));
            }else if (pair.getKey().equals(InGameType.NumOfTracks.toString())){
                this.numOfTracks= Integer.parseInt(pair.getValue().get(0));
                for (int i = 1; i <=numOfTracks; i++) {
                    this.tracks.add(new TrackOfGame(i));
                }
            } else if (pair.getKey().equals(InGameType.Athlete.toString())) {
                PhysicalObjects.add(new Athlete(pair.getValue()));
            }
        }
    }

    private Map<Athlete,Pair<Integer,Integer>> divideGroup(List<Athlete> athletes, int numOfTracks, GameType type){
        switch (type){
            case Random:
                return new OperationRandom().doOperation(athletes, numOfTracks);
            case Score:
                return new OperationSort().doOperation(athletes, numOfTracks);
        }
        throw new IllegalArgumentException();
    }
    /**
     * 对分好的组添加运动员信息
     * @param type 策略模式的类型
     */
    public void addAthlete(GameType type){
        Map<Athlete, Pair<Integer, Integer>> operation=divideGroup(this.PhysicalObjects, numOfTracks, type);
        int numOfGroup=0;
        for(Athlete eachA:PhysicalObjects){
            Pair<Integer, Integer> eachPair=operation.get(eachA);
            AthleteToGroup.put(eachA, eachPair.getKey());//远动员和他所属的组号
            if(GroupWithAthlete.get(eachPair.getKey())==null){
                GroupWithAthlete.put(eachPair.getKey(), new ArrayList<>());//增加新的组号和运动员列表的关系
            }
            GroupWithAthlete.get(eachPair.getKey()).add(eachA);//向组号对应的运动员列表添加运动员
            numOfGroup=Math.max(numOfGroup, eachPair.getKey());

            ObjectToTrack.put(eachA, tracks.get(eachPair.getValue()));//给父类同样执行上述操作
            if(TrackAndObjects.get(tracks.get(eachPair.getValue()))==null){
                TrackAndObjects.put(tracks.get(eachPair.getValue()), new ArrayList<>());
            }
            TrackAndObjects.get(tracks.get(eachPair.getValue())).add(eachA);
        }
        this.angle=2*Math.PI/(this.numOfGroups);
        this.numOfGroups=GroupWithAthlete.keySet().size();
        for(Athlete eachA:PhysicalObjects){
            setPosition(eachA);
        }
    }

    /**
     *
     * 在轨道上加一名运动员
     * @param Object 要添加的运动员
     * @param track 要添加进的赛道
     * @return 添加成功返回true，否则返回假
     */
    @Override
    public boolean addPhysicalObject(Athlete Object, Track track) {
//        if(TrackAndObjects.get(track).size()==this.numOfGroups){
//            return false;//轨道上物体的数量等于组数，返回假？？？
//        }else{
            int flag=1;
            PhysicalObjects.add(Object);//添加轨道物体的操作
            for(int i=0;i<numOfGroups;i++){
                //如果一组中的运动员数量不等于轨道数，这个是文档的要求
                if(GroupWithAthlete.get(i).size()!=numOfTracks){
                    for(int j=0;j<GroupWithAthlete.get(i).size();j++){
                        //如果第i组的第j个运动员所属的轨道等于要添加进的轨道
                        if(ObjectToTrack.get(GroupWithAthlete.get(i).get(j)).equals(track)){
                            flag=0;
                        }
                    }
                    if(flag==1){
                        GroupWithAthlete.get(i).add(Object);
                        AthleteToGroup.put(Object, i);
                       TrackAndObjects.get(track).add(Object);
                        ObjectToTrack.put(Object, track);
                        break;
                    }
                }
            }
            setPosition(Object);
            return true;
        }
//    }

    /**
     * 删除赛道
     * @param track 要删除的赛道
     * @return 删除成功返回true，否则返回假
     */
    @Override
    public boolean removeTrack(Track track) {
        for(Athlete eachAthlete:PhysicalObjects) {
            if(ObjectToTrack.get(eachAthlete).equals(track)) {
                GroupWithAthlete.get(AthleteToGroup.get(eachAthlete)).remove(eachAthlete);
                AthleteToGroup.remove(eachAthlete);
            }
        }
        return super.removeTrack(track);
    }

    /**
     * 删除轨道上的运动员
     * @param object 轨道上的运动员
     * @return 删除成功返回true，否则返回假
     */
    public boolean removePhysicalObject(Athlete object){
        if(!PhysicalObjects.contains(object)){
            return false;
        }
        AthleteToGroup.remove(object);
        for(Integer eachGroup:GroupWithAthlete.keySet()) {
            if(GroupWithAthlete.get(eachGroup).contains(object)) {
                GroupWithAthlete.get(eachGroup).remove(object);
            }
        }
        numOfTracks--;///？？？？
        return true;
    }

    @Override
    public void setPosition(Athlete ob){
        positionOfPhysicalObjects.put(ob, new Position((ObjectToTrack.get(ob)).getRadius(), (AthleteToGroup.get(ob))*angle, 2));
    }
    @Override
    public int getLogicalDistance(String a, String b) {
        return 100000;
    }

    @Override
    public boolean addRelationCtoP(TGcenter Object1, Athlete Object2, Relation relation) {
        return false;
    }

    @Override
    public boolean addRelationPtoP(Athlete Object1, Athlete Object2, Relation relation) {
        return false;
    }

    @Override
    public boolean transit(Athlete Object, Track track) {
return false;
    }

    @Override
    public void move(Athlete Object, double sitha) {

    }
}
