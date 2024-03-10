package centralObject;

/**
 * Created with IntelliJ IDEA.
 * Description:表示TrackGame的中心物体
 * User: xinyu
 * Date: 2023-04-17
 * Time: 23:28
 */
public class TGcenter extends ConcreteCentralObject {
    /**
     * 由于赛跑系统无中心物体，故中心物体名称为null
     * @return null
     */
    @Override
    public String getName() {
        String name = "null";
        return name;
    }
}
