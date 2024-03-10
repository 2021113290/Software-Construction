package relation;

/**
 * Created with IntelliJ IDEA.
 * Description:只有一个属性即亲密度
 * User: xinyu
 * Date: 2023-04-30
 * Time: 13:17
 */
public class SocialRelation implements Relation {
    private double intimacy;

    public SocialRelation(double intimacy) {
        this.intimacy = intimacy;
    }

    public double getIntimacy() {
        return intimacy;
    }
}
