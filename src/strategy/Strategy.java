package strategy;

import javafx.util.Pair;
import physicalObject.Athlete;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description:策略模式的接口//仿照菜鸟教程里的框架来写
 * User: xinyu
 * Date: 2023-04-21
 * Time: 8:58
 */
public interface Strategy {
    public Map<Athlete, Pair<Integer,Integer>> doOperation(List<Athlete> athletes,int numOfTrack);
}
