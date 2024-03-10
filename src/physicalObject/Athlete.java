package physicalObject;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

/**
 * Created with IntelliJ IDEA.
 * Description:赛跑的轨道物体
 * User: xinyu
 * Date: 2023-04-17
 * Time: 23:31
 */
public class Athlete extends ConcretePhysicalObject {
   // protected String name;名字已经从ConcretePhysicalObject继承过来了
    protected int number;//号码
    protected String nation;//国籍
    protected int age;//年龄
    protected double score;//最好成绩
    //Abstraction function:
    //跑道上的一名运动员
    // Representation invariant:
    //姓名是 word：由大小写字母构成，不含有空格
    //号码和年龄是正整数，
    //国籍是三位大写字母，
    //成绩是最多两位整数和必须两位小数构成（例如 9.10、10.05）
    //Safety from rep exposure:
    //属性用protected修饰，类型不可变

    private void checkRep(){
        assert number>0;
        assert age>0;
        assert name.matches("^[a-zA-Z]+$");
        assert String.valueOf(score).matches("/^([1-9]\\d?)(\\.\\d{2})$");
    }
    public Athlete(List<String> relist){
        this.name=relist.get(0);
        this.number= Integer.parseInt(relist.get(1));
        this.nation=relist.get(2);
        this.age= Integer.parseInt(relist.get(3));
        this.score= Double.parseDouble(relist.get(4));
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Athlete athlete = (Athlete) o;
        return number == athlete.number && age == athlete.age && Double.compare(athlete.score, score) == 0 && Objects.equals(nation, athlete.nation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, nation, age, score);
    }

    /**
     * 在sort方法中会自动调用compareTo方法，compareTo方法的参数是Object，其实传入的就是Athlete类型的对象
     * @param o the object to be compared.
     * @return 如果当前对象应排在参数对象之前，返回小于0的数字；
     *          如果当前对象应排在参数对象之后，返回大于0的数字
     *          如果当前对象和参数对象不分先后，返回0
     */

}
