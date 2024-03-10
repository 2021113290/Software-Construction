package circularOrbit;

import centralObject.TGcenter;
import javafx.util.Pair;
import physicalObject.Athlete;
import position.Position;
import track.Track;

import relation.Relation;
import type.ApplicationType;

import java.io.Reader;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * Description:实现CircularOrbit接口的类
 * User: xinyu
 * Date: 2023-04-17
 * Time: 20:25
 */

/**
 * rep要表达的：
 * (1) 中心点物体；
 * (2) 一组轨道；
 * (3) 每条轨道上的一组物体；
 * (4) 中心点物体与轨道物体之间的关系；
 * (5) 轨道物体之间关系。
 * 选择不同的数据结构存储这些信息，即产生不同的 rep 和不同的 ADT实现方案
 */
public abstract class ConcreteCircularOrbit<L,E> implements CircularOrbit<L,E>{
    protected L CentralObject=null;//中心点物体(1)
    protected List<Track> tracks=new ArrayList<>();//一组轨道(2)
    protected List<E> PhysicalObjects=new ArrayList<>();//轨道上的一组物体(3)
    protected Map<E, Track> ObjectToTrack = new HashMap<>();//轨道上物体属于哪条轨道
    protected Map<Track,List<E>> TrackAndObjects=new HashMap<>();//每条轨道和轨道上的物体的映射关系(3)
    protected ApplicationType type;
    protected Map<E, Position> positionOfPhysicalObjects = new HashMap<>();
    protected List<Pair<Position, Position>> straightLine=new ArrayList<>();
    protected Random random=new Random();


    /**待补充
     * (4) 中心点物体与轨道物体之间的关系；
     * (5) 轨道物体之间关系
     */
    //Abstraction function:
    //由多个轨道，多个轨道物体和中心物体组成的轨道系统
    // Representation invariant:
    //   轨道不能重复编号：一个系统里面不能有两个编号为1的轨道
    // Safety from rep exposure:
    //属性用protected修饰，必要时进行防御式拷贝


    public Map<Track,List<E>> getTrackAndObjects(){
        Map<Track,List<E>> map=new HashMap<>(TrackAndObjects);
        return map;
    }
    /**
     * 获得轨道的中心物体
     * @return 轨道的中心物体
     */
    public L getCentralObject() {
        return CentralObject;
    }

    /**
     * 获得一组轨道
     * @return 一组轨道
     */
    public List<Track> getTracks() {//防御式拷贝
        return new ArrayList<>(tracks);
    }

    /**
     * 获得轨道上的一组物体
     * @return 轨道上的一组物体
     */

    public ApplicationType getType(){
        return type;
    }

    /**
     * 获得指定的轨道上的一组物体
     * @param track 指定的轨道
     * @return 指定的轨道上的一组物体
     */
    public List<E> getPhysicalObjects(Track track) {//防御式拷贝
        return TrackAndObjects.get(track);
    }


    @Override
    public boolean addTrack(Track track) {
        if (tracks.contains(track)){//如果一组轨道中已经含有参数轨道了，就添加失败
            return false;
        }else {
            tracks.add(track);//一组轨道中添加一条轨道
            TrackAndObjects.put(track,new ArrayList<>());//增加一条新的轨道和轨道物体的映射关系
            return true;//添加成功
        }
    }
    @Override
    public boolean removeTrack(Track track) {
        //删除轨道的话，那么与所删除轨道关联的一切东西都得删掉：
        //所删除的轨道上面的物体得删掉，从哪里删？从轨道系统中删，
        //所删除的轨道的相关的关系得删掉，
        if (!tracks.contains(track)){//如果一组轨道中不包含要删除的轨道，就删除失败
            return false;
        }else {
            /**
             * 这里可能还要删，待补充
             */
            for(E eachObject:TrackAndObjects.get(track)){
                PhysicalObjects.remove(eachObject);
                ObjectToTrack.remove(eachObject);
            }
            tracks.remove(track);//一组轨道中删掉参数轨道
            TrackAndObjects.remove(track);//轨道和轨道物体之间的映射关系中删掉参数轨道
            return true;
        }

    }

    @Override
    public boolean addCentralObject(L Object) {
        if (this.CentralObject==null){
            this.CentralObject=Object;
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public boolean addPhysicalObject(E Object, Track track) {
        if (!tracks.contains(track)){
            return false;
        }else {
            PhysicalObjects.add(Object);
            ObjectToTrack.put(Object,track);
            TrackAndObjects.get(track).add(Object);
            return true;
        }
    }

    @Override
    public boolean removePhysicalObject(E Object, Track track) {
        if (!PhysicalObjects.contains(Object)){
            return false;
        }else {
            PhysicalObjects.remove(Object);
            TrackAndObjects.get(track).remove(Object);
            ObjectToTrack.remove(Object);
            return true;
        }
    }
    @Override
    public List<Pair<String, List<String>>> readFile(String fileName) {
        /**
         * 应该是要读文件中的正则，现在正则还没有写
         */
        return read.read.handleByRegular(fileName);
    }

    public Iterator<E> iterator(){
        return new itr();
    }

    private class itr implements Iterator<E>{
        int cur=0;
        @Override
        public boolean hasNext() {
            return cur!=PhysicalObjects.size();
        }

        @Override
        public E next() {
            if(cur!=PhysicalObjects.size()){
                cur++;
                return PhysicalObjects.get(cur);
            }else{
                return null;
            }
        }

        @Override
        public boolean equals(Object obj) {
            return super.equals(obj);
        }
    }
    @Override
    public Position getPhysicalObjectPosition(E object){
        if(positionOfPhysicalObjects.get(object)==null){
            throw new IllegalArgumentException();
        }
        else{
            return positionOfPhysicalObjects.get(object);
        }
    }
    @Override
    public List<Pair<Position, Position>> getStraight(){
        return straightLine;
    }
    @Override
    public void setPosition(E object){
        //貌似只能用极坐标表示                                                              //实质上是轨道物体所在轨道编号，在轨道系统中体现的是中心物体到轨道物体半径；Random.NextDouble()返回一个大于或等于 0.0 且小于 1.0 的随机浮点数。
        positionOfPhysicalObjects.put(object, new Position(ObjectToTrack.get(object).getRadius(), random.nextDouble()*2*Math.PI, 2));
    }
//
//    @Override
//    public void transit(Object Object, Track track) {
//
//    }
//
//    @Override
//    public void move(Object Object, double sitha) {
//
//    }
}
