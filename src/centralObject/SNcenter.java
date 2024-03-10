package centralObject;

import circularOrbit.Person;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:社交网络图的中心物体
 * User: xinyu
 * Date: 2023-04-29
 * Time: 10:32
 */
public class SNcenter extends ConcreteCentralObject implements Person {
    private int year;
    private String sex;
    //Abstraction function:
    //人的年龄，人的性别
    // Representation invariant:
    // year正整数，性别取 M|F 之一
    // Safety from rep exposure:
    //属性用private修饰，year是int类型，sex是String类型，所以保证了不可变性
    public SNcenter(String name) {
        this.name=name;
    }

    public SNcenter(List<String> args){
        this.name=args.get(0);
        this.year=Integer.valueOf(args.get(1));
        this.sex=args.get(2);
    }
    @Override
    public int hashCode(){
        return this.name.hashCode();
    }
    @Override
    public boolean equals(Object o){
        if(this.hashCode()==o.hashCode()){
            return true;
        }else{
            return false;
        }
    }
    public SNcenter(String name, int year, String sex) {
        this.name=name;
        this.year = year;
        this.sex = sex;
    }
}
