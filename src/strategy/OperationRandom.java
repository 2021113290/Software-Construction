package strategy;

import javafx.util.Pair;
import physicalObject.Athlete;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * Description:随机分组
 * User: xinyu
 * Date: 2023-04-21
 * Time: 18:30
 */
public class OperationRandom implements Strategy{
    @Override
    public Map<Athlete, Pair<Integer, Integer>> doOperation(List<Athlete> athletes, int numOfTrack) {
       Map<Athlete,Pair<Integer,Integer>> alreadyGroup=new HashMap<>();
        Random random=new Random();
        Set<Integer> allAthletes=new HashSet<>();//所有运动员集合，一会从这里面选运动员来分组
        for (int i = 0; i < athletes.size(); i++) {
            allAthletes.add(i);
        }//初始化所有运动员的集合
        List<Integer> randomAthletes=new ArrayList<>(athletes.size());//用于存放打乱序号后的运动员集合
        while (!allAthletes.isEmpty()){
            int randomAthlete=random.nextInt(athletes.size());//相当于从运动员大本营里面随机选一名运动员
            if (allAthletes.contains(randomAthlete)){
                randomAthletes.add(randomAthlete);//第一位赋成随机到的运动员，接下来给randomAthletes数组顺位赋值
                allAthletes.remove(randomAthlete);//运动员大本营里删掉选中的运动员
            }
        }
        int numOfGroup=athletes.size()/numOfTrack;
        if (athletes.size()%numOfTrack!=0){
            numOfGroup=numOfGroup+1;
        }
        int z=0;
        for (int i = 0; i < numOfGroup; i++) {
            for (int j = 0; j < numOfTrack; j++) {
                if (z==athletes.size())return alreadyGroup;
                alreadyGroup.put(athletes.get(randomAthletes.get(z)),new Pair<>(i,j));
                z++;
            }
        }
        return alreadyGroup;
    }
}
