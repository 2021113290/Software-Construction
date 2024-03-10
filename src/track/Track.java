package track;

import type.TrackType;

/**
 * Created with IntelliJ IDEA.
 * Description:表示轨道，不可变
 * User: xinyu
 * Date: 2023-04-16
 * Time: 14:33
 */
public abstract class Track {
    protected Integer Number;//轨道标号
    protected Double radius;//轨道半径
    //Abstraction function:
    //轨道标号，轨道半径
    // Representation invariant:
//    轨道标号是大于0的正整数，轨道半径数值上等于轨道标号，只是类型不一样
    // Safety from rep exposure:
    //属性用protected修饰，Number是Integer类型，radius是Double类型，所以保证了不可变性

    /**
     * 获得轨道标号
     * @return 轨道标号
     */
    public Integer getNumber() {
        return Number;
    }

    /**
     * 获得轨道半径
     * @return 轨道半径
     */
    public double getRadius() {
        return radius;
    }

    /**
     * 轨道构造函数，这里先规定编号要和半径同增长,为了避免客户端乱输入，故输入编号的同时就已经把半径确定了
     * @param number 轨道编号
     */
    public Track(int number) {
        this.Number = number;
        this.radius = Double.valueOf(number);
    }
}
