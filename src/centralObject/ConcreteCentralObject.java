package centralObject;

import java.util.Objects;

/**
 * Created with IntelliJ IDEA.
 * Description:具体的中心物体
 * User: xinyu
 * Date: 2023-04-17
 * Time: 23:32
 */
public abstract class ConcreteCentralObject implements CentralObject{
    protected String name;
    //Abstraction function:
    //中心物体的名字:ElementName,NULL,CentralUser
    //
    // Representation invariant:
//    ElementName:元素名称，例如 Na、C、O、Ca，最多两位字母，第一位大写，第二位小写（若有）
//     CentralUser:由大小写字母或数字构成，不含有空格和其他符号
    // Safety from rep exposure:
    //属性用protected修饰，name是String类型，所以保证了不可变性

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConcreteCentralObject that = (ConcreteCentralObject) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
