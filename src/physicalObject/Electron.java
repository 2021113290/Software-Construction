package physicalObject;

/**
 * Created with IntelliJ IDEA.
 * Description:原子轨道系统的轨道物体--电子
 * User: xinyu
 * Date: 2023-04-19
 * Time: 8:41
 */
public class Electron extends ConcretePhysicalObject{
    /**
     * 电子的构造函数，名称用编号表示
     * @param number 电子的编号
     */
    public Electron(int number) {
        this.name= String.valueOf(number);
    }
    @Override
    public int hashCode(){
        return name.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if(this.hashCode()==o.hashCode()){
            return true;
        }else{
            return false;
        }
    }
}
