package circularOrbit;

import centralObject.AScenter;
import javafx.util.Pair;
import physicalObject.Electron;
import position.Position;
import track.Track;
import track.TrackOfAtom;
import type.ApplicationType;
import type.AtomStructure.InAtomType;

import relation.Relation;
import java.util.List;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * Description:原子结构模型
 * 轨道是圆形，每个轨道上有多个轨道物体（电子），中心物体是原子核，电子彼此不存在关系，电子能从一个轨道移动到另一个轨道
 * User: xinyu
 * Date: 2023-04-19
 * Time: 8:36
 */
public class AtomStructure extends ConcreteCircularOrbit<AScenter, Electron>{
     /**
     * 从文本文件中读取建立轨道系统的数据信息，通过正则匹配处理并将信息交给atomStructureInit处理，最终返回构造好的系统
     * @param file 文本文件
     * @return 原子结构系统
     */
    public static AtomStructure newStructure(String file){
        AtomStructure atomStructure=new AtomStructure();
        atomStructure.atomStructureInit(atomStructure.readFile(file));
        return atomStructure;
    }
    /**
     * 用处理好的文本文件信息给给原子结构系统初始化
     * @param ObjectList 处理好的文本文件信息
     */
    public void atomStructureInit(List<Pair<String, List<String>>> ObjectList){
        for (Pair<String,List<String>> pair:ObjectList) {
            type= ApplicationType.AtomStructure;

            if (pair.getKey().equals(InAtomType.ElementName.toString())){
                this.addCentralObject(new AScenter(pair.getValue().get(0)));
            }else if (pair.getKey().equals(InAtomType.NumberOfTracks.toString())){
                int numOfTracks= Integer.parseInt(pair.getValue().get(0));
                for (int i = 1; i <=numOfTracks; i++) {
                    this.addTrack(new TrackOfAtom(i));
                }
            } else if (pair.getKey().equals(InAtomType.NumberOfElectron.toString())) {
                /**对每一条轨道，都要对该轨道上的电子编号；到下一条轨道时，不重新编号
                 * 轨道号 /该轨道上的电子数量
                 */
                int Electronicbianhao=0;
                Random random=new Random();
                for(String eachString:pair.getValue()){
                    String fenshu[]=eachString.split("/");
                    for(int i=1;i<=Integer.valueOf(fenshu[1]);i++){
                        Integer orbitbianhao=Integer.valueOf(fenshu[0])-1;//拿到轨道编号
                        Electron addedElectronic=new Electron(++Electronicbianhao);
                        this.addPhysicalObject(addedElectronic,tracks.get(orbitbianhao));
                        setPosition(addedElectronic);
                        positionOfPhysicalObjects.put(addedElectronic, new Position(((TrackOfAtom)tracks.get(orbitbianhao)).getRadius(), random.nextDouble()*2*Math.PI, 2));

                    }
                }
            }
        }
    }
    @Override
    public boolean addRelationCtoP(AScenter Object1, Electron Object2, Relation relation) {
        return false;
    }

    @Override
    public boolean addRelationPtoP(Electron Object1, Electron Object2, Relation relation) {
        return false;
    }

    //电子跃迁
    @Override
    public boolean transit(Electron Object, Track track) {
        if (!PhysicalObjects.contains(Object)||!tracks.contains(track)){ //如果轨道物体列表中不包含要跃迁的电子或是参数轨道不存在
           return false;
        }else {
            Track beforeTrack=ObjectToTrack.get(Object);//首先获得物体之前属于的轨道
            TrackAndObjects.get(beforeTrack).remove(Object);//将之前轨道中的该物体删去
            TrackAndObjects.get(track).add(Object);//将要跃迁到的轨道中添加该物体
            ObjectToTrack.remove(Object);//删去之前轨道和该物体的映射关系
            ObjectToTrack.put(Object,track);//放入跃迁后电子和轨道的映射关系
            return true;
        }
    }

    @Override
    public int getLogicalDistance(String a, String b) {
        return 100000;
    }

    @Override
    public void move(Electron Object, double sitha) {

    }
}
