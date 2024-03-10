package track;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: xinyu
 * Date: 2023-04-20
 * Time: 9:50
 */
public class TrackOfGame extends Track{

    /**
     * 轨道构造函数，这里先规定编号要和半径同增长
     *
     * @param number 轨道编号
     */
    public TrackOfGame(int number) {
        super(number);
    }
//    @Override
//    public int hashCode(){
//        int reInt=0;
//        reInt+=Number;
//        return reInt;
//    }
//    @Override
//    public boolean equals(Object o){
//        if(o.hashCode()==this.hashCode()){
//            return true;
//        }
//        else{
//            return false;
//        }
//    }
//    @Override
//    public TrackOfGame clone(){
//        TrackOfGame tmpShell = new TrackOfGame(Number);
//        return tmpShell;
//    }
}
