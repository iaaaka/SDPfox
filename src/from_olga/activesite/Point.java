package from_olga.activesite;

/**
 * <p>Title: activeSite</p>
 *
 * <p>Description: Active site finder that utilizes 3D structure and SDPs</p>
 *
 * <p>Copyright: Copyright (c) 2004</p>
 *
 * <p>Company: </p>
 *
 * @author Olga V. Kalinina
 * @version 1.0
 */
public class Point {
    public double x;
    public double y;
    public double z;

    public Point() {
        x = 0;
        y = 0;
        z = 0;
    }

    public Point(double xx, double yy, double zz) {
        x = xx;
        y = yy;
        z = zz;
    }

    public void copy(Point p) {
        x = p.x;
        y = p.y;
        z = p.z;
    }
}
