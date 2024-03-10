package circularOrbit;

import javafx.util.Pair;
import position.Position;
import track.Track;

import relation.Relation;
import type.ApplicationType;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: xinyu
 * Date: 2023-04-16
 * Time: 14:16
 */
public interface CircularOrbit <L,E>{
    /**
     *
     * @param SystemName 要建的系统名字
     * @param file 系统数据文件
     * @return 根据选择和文件数据建好的轨道系统
     */
    public static CircularOrbit empty(String SystemName,String file){
        if(SystemName.equals("AtomStructure")){
            return AtomStructure.newStructure(file);
        }else if (SystemName.equals("TrackGame")){
            return TrackGame.newGame(file);
        }else if (SystemName.equals("SocialNetworkCircle")){
            return SocialNetworkCircle.newNetWork(file);
        }
        throw new IllegalArgumentException();
    }
    //下面根据报告要求中给出了提示编写方法

    /**
     * 向轨道系统中添加一条轨道
     * @param track 要添加的轨道
     * @return 如果添加成功则返回真，否则返回假
     */
    public boolean addTrack(Track track);

    /**
     * 删除轨道系统中的一条轨道，该轨道上的所有轨道物体都会被删除
     * @param track 要删除的轨道
     * @return 如果删除成功则返回真，否则返回假
     */
    public boolean removeTrack(Track track);


    /**
     * 增加中心点物体
     * @param Object 要增加的中心点物体
     * @return 如果增加成功则返回真，否则返回假
     */
    public boolean addCentralObject(L Object);

    /**
     * 向特定的轨道上添加一个物体
     * @param Object 要添加的物体
     * @param track 要添加进的轨道
     * @return 如果增加成功则返回真，否则返回假
     */
    public boolean addPhysicalObject(E Object,Track track);

    /**
     * 从特定的轨道上删除一个物体
     * @param Object 要删除的物体
     * @param track 要删除的物体所在的轨道
     * @return 如果删除成功则返回真，否则返回假
     */
    public boolean removePhysicalObject(E Object,Track track);
    /**
     *增加中心点物体和一个轨道物体之间的关系
     * @param Object1 中心点物体
     * @param Object2 一个轨道物体
     * @param relation 中心点物体和一个轨道物体之间的关系
     * @return 如果增加成功则返回真，否则返回假
     */
    public boolean addRelationCtoP(L Object1, E Object2, Relation relation);

    /**
     * 增加两个轨道物体之间的关系
     * @param Object1 轨道物体1
     * @param Object2 轨道物体2
     * @param relation 轨道物体之间的关系
     * @return 如果增加成功则返回真，否则返回假
     */
    public boolean addRelationPtoP(E Object1, E Object2, Relation relation);

    /**List<Pair<String, List<String>>>可以重复key的map
     * 从外部文件读取数据构造轨道系统对象
     * @param fileName 外部文件名
     * @return 轨道数据
     */
    public List<Pair<String, List<String>>> readFile(String fileName);

    /**
     * 将 object 从当前所在轨道迁移到轨道 track
     *
     * @param Object 要迁移的物体
     * @param track  要迁移到的轨道
     * @return 迁移成功返回真，否则返回假
     */
    public boolean transit(E Object, Track track);

    /**
     * 将 object 从当前位置移动到新的 sitha 角度所对应的位置
     * @param Object 要移动的物体
     * @param sitha 移动的角度
     */
    public void move(E Object,double sitha);

    /**
     * 客户端可在遍历 CircularOrbit 对象中的各 PhysicalObject 对象时使用，遍历次序为：从内部轨道逐步向外、同一轨道上的物体按照其角度从小到大的次序（若不考虑绝对位置，则随机遍历）
     * @return 一个遍历器
     */
    public Iterator<E> iterator();
    /**
     * 获得轨道和物体的映射关系
     * @return 轨道和物体的映射关系
     */
    public Map<Track,List<E>> getTrackAndObjects();

    /**
     * 计算任意两个物体之间的最短逻辑距离
     * @param a 物体a
     * @param b 物体b
     * @return 返回最短逻辑距离
     */
    public int getLogicalDistance(String a,String b);
    public ApplicationType getType();
    public L getCentralObject();
    public List<Track> getTracks();
    public List<E> getPhysicalObjects(Track track);
    public Position getPhysicalObjectPosition(E object);
    public List<Pair<Position, Position>> getStraight();
    public void setPosition(E object);
}
