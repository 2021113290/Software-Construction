package physicalObject;

import circularOrbit.Person;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:社交网络图的轨道物体
 * User: xinyu
 * Date: 2023-04-29
 * Time: 10:33
 */
public class Friend extends ConcretePhysicalObject implements Person {
    private Integer year;
    private String sex;
    //Abstraction function:
    //人的年龄，人的性别
    // Representation invariant:
    // year正整数，性别取 M|F 之一
    // Safety from rep exposure:
    //属性用private修饰，year是int类型，sex是String类型，所以保证了不可变性

    public Friend(String name){
        this.name=name;
    }

    public Friend(String name, int year, String sex) {
        this.name=name;
        this.year = year;
        this.sex = sex;
    }
    public Friend(List<String> args){
        this.name=args.get(0);
        this.year=Integer.parseInt(args.get(1));
        this.sex=args.get(2);
    }
    @Override
    public Friend clone(){
        List<String> args=new ArrayList<>();
        args.add(this.name);
        args.add(this.year.toString());
        args.add(this.sex);
        return new Friend(args);
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
}
