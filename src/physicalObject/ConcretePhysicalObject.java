package physicalObject;

/**
 * Created with IntelliJ IDEA.
 * Description:具体的轨道物体
 * User: xinyu
 * Date: 2023-04-17
 * Time: 23:35
 */
public abstract class ConcretePhysicalObject implements PhysicalObject{
    protected String name;
    //Abstraction function:
    //轨道物体的名字:Athlete，Electorn，Friend
    // Representation invariant:
//    Athlete:由大小写字母构成，不含有空格
//     Electorn:编号是大于0的正整数，电子在从内到外的轨道上的分布数目应满足 2、8、18、…的合理分布
//    Friend：由大小写字母或数字构成，不含有空格和其他符号
    // Safety from rep exposure:
    //属性用protected修饰，name是String类型，所以保证了不可变性
    @Override
    public String getName() {
        return name;
    }
}
