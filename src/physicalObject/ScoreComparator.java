package physicalObject;

import java.util.Comparator;

/**
 * Created with IntelliJ IDEA.
 * Description:成绩比较器
 * User: xinyu
 * Date: 2023-04-21
 * Time: 19:08
 */
public class ScoreComparator implements Comparator<Athlete> {

    /**
     * 成绩比较器（降序）
     *
     * @param o1 the first object to be compared.
     * @param o2 the second object to be compared.
     * @return o1.score>o2.score,返回小于0的数
     * o1.score<o2.score,返回大于0的数
     * o1.score=o2.score,返回0
     */
    @Override
    public int compare(Athlete o1, Athlete o2) {
        return (int)(o2.score- o1.score) ;
    }
}
