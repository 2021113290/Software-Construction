package APIs;

import circularOrbit.CircularOrbit;
import physicalObject.Electron;
import track.Track;
import visual.Visual;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: xinyu
 * Date: 2023-04-30
 * Time: 18:31
 */
public class CircularOrbitHelper{

    public static Visual visualize(CircularOrbit c){
        Visual v=new Visual(c);
        return v;
    }
}

