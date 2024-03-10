package track;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: xinyu
 * Date: 2023-04-29
 * Time: 10:28
 */
public class TrackOfAtom extends Track{
    /**
     * 轨道构造函数，这里先规定编号要和半径同增长,为了避免客户端乱输入，故输入编号的同时就已经把半径确定了
     *
     * @param number 轨道编号
     */
    public TrackOfAtom(int number) {
        super(number);
    }

    @Override
    public int hashCode() {
        int re=0;
        re+=Number.hashCode();
        re+=7*radius.hashCode();
        return re;
    }

    @Override
    public boolean equals(Object o){
        if(o.hashCode()==this.hashCode()){
            return true;
        }
        else{
            return false;
        }
    }

    @Override
    public TrackOfAtom clone(){
        TrackOfAtom tmpShell = new TrackOfAtom(Number);
        return tmpShell;
    }
}
