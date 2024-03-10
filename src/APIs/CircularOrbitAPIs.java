package APIs;

import circularOrbit.CircularOrbit;
import track.Track;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: xinyu
 * Date: 2023-04-30
 * Time: 14:11
 */
public class CircularOrbitAPIs {
    /**
     *计算多轨道系统中各轨道上物体分布的熵值
     * @param c 轨道系统
     * @return 多轨道系统中各轨道上物体分布的熵值
     */
    public static double getObjectDistributionEntropy(CircularOrbit c){
        Map<Track, List> tmpTrackAndObjects=c.getTrackAndObjects();
        double entropy = 0; // 熵值
        double sum = 0; // 轨道物体总数
        double p = 0;
        for(Track eachT:tmpTrackAndObjects.keySet()) {
            sum += tmpTrackAndObjects.get(eachT).size();//计算轨道物体总数
        }
        for(Track eachT:tmpTrackAndObjects.keySet()) {
            p = tmpTrackAndObjects.get(eachT).size() / sum;//第i条轨道上的物体数/轨道物体总数
            entropy -= p * Math.log(p);
        }
        return entropy;
    }

    /**计算任意两个物体之间的物理距离。若物体有具体位置，则可在直角坐标系
     里计算出它们之间的物理距离。
     *
     * @param c 轨道系统
     * @param arg 两个物体参数
     * @return 100000 因为所选的三个轨道系统都没有具体位置，赛跑，原子结构，社交网络图，这三个系统根据要开发的功能没有必要加具体位置
     */
    public static double getPhysicalDistance (CircularOrbit c, String[] arg) {
        return 100000;
    }

    /**
     * 计算任意两个物体之间的最短逻辑距离。这里的逻辑距离是指：e1 和 e2 之
     * 间通过最少多少条边（relation）即可连接在一起。两个物体之间若无关系，
     * 则距离无穷大。
     * @param c 轨道系统
     * @param arg 两个物体参数
     * @return 两个物体之间的最短逻辑距离
     */
    public static int getLogicalDistance (CircularOrbit c,String[] arg) {

        return c.getLogicalDistance(arg[0],arg[1]);
    }
    public static void deleteTrack(CircularOrbit c, String[] arg){
        int number=Integer.valueOf(arg[0]);
        for(Object eachT:c.getTracks()){
            if(((Track)eachT).getNumber().equals(number)){
                c.removeTrack((Track)eachT);
            }
        }
    }
}
