package strategy;

import javafx.util.Pair;
import physicalObject.Athlete;
import physicalObject.ScoreComparator;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * Description:按成绩排序分组
 * User: xinyu
 * Date: 2023-04-21
 * Time: 18:29
 */
public class OperationSort implements Strategy{
    @Override
    public Map<Athlete, Pair<Integer, Integer>> doOperation(List<Athlete> athletes, int numOfTrack) {
        Map<Athlete,Pair<Integer,Integer>> alreadyGroup=new HashMap<>();
        ScoreComparator scoreComparator=new ScoreComparator();
        Athlete[] array=(Athlete[])athletes.toArray(new Athlete[athletes.size()]);
        Arrays.sort(array, scoreComparator);
        int numOfGroup=athletes.size()/numOfTrack;
        if (athletes.size()%numOfTrack!=0){
            numOfGroup=numOfGroup+1;
        }
        int z=0;
        for (int i = 0; i < numOfGroup; i++) {
            for (int j = 0; j < numOfTrack; j++) {
                if (z==athletes.size())return alreadyGroup;
                alreadyGroup.put(array[z],new Pair<>(i,j));
                z++;
            }
        }
        return alreadyGroup;
    }
}
