package centralObject;

/**
 * Created with IntelliJ IDEA.
 * Description:原子结构系统的中心--即原子核
 * User: xinyu
 * Date: 2023-04-19
 * Time: 8:40
 */
public class AScenter extends ConcreteCentralObject{


    //Abstraction function:
    //原子核
    // Representation invariant:
    // Safety from rep exposure:
    //不可变性

    /**
     * 构造函数，构造只有原子核名字为中心（即忽略质子和中子）的原子结构
     * @param name 原子核名称
     */
    public AScenter(String name) {
        this.name=name;
    }


}
