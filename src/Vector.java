import java.util.ArrayList;


public class Vector {

    private ArrayList<Double> components;
    private double mag;
    Angle angle;

    public Vector() {
        components = new ArrayList<Double>();
    }

    public Vector(double[] d) {
        components = new ArrayList<Double>();
        for(Double comp : d) {
            components.add(comp);
        }
        setMag();
        if(d.length == 2) {
            cart();
        }
    }

    public Vector(double m, Angle a) {
        mag = m;
        angle = a;
        rec();
    }

    private void rec() {
        components.clear();
        components.add(mag * Math.cos(angle.getAngleDeg()));
        components.add(mag * Math.sin(angle.getAngleDeg()));
    }

    private void cart() {
        angle = new Angle (Math.atan(components.get(1)/components.get(0)));
    }

    private void setMag() {
        double t = 0;
        double tt = this.getRawComponents();
        for(int i = 0; i < tt.length; i++) {
            t += Math.pow(tt[i], 2);
        }
        this.mag = Math.sqrt(t);
    }

    public double[] getRawComponents() {
        double[] f = new double[components.size()];
        for(int i = 0; i < components.size(); i++) {
            f[i] = components.get(i);
        }
        return f;
    }

    public void setComponents(double[] f) {
        if(!components.isEmpty()) throw new RuntimeException("Vector already has components");
        for(Double d: f) {
            components.add(d);
        }
        setMag();
    }

    public void overrideComponents(double[] f) {
        components.clear();
        for(Double d: f) {
            components.add(d);
        }
        setMag();
    }

    private void setComp(double[] f) {
        components.clear();
        for(Double d: f) {
            components.add(d);
        }
        setMag();
    }

    public static Vector subtract(Vector f, Vector s) {
        Vector o = Vector.opposite(s);
        Vector t = Vector.add(f, o);
        return t;
    }


    public static Vector add(Vector f, Vector s) {
        double[] ff = f.getRawComponents();
        double[] ss = s.getRawComponents();
        double[] tt = new double[ff.length];
        for(int i = 0; i < tt.length; i++) {
            tt[i] = ss[i] + ff[i];
        }
        Vector t = new Vector(tt);
        return t;
    }

    public static Vector opposite(Vector f) {
        Vector t = new Vector();
        double[] ff = f.getRawComponents();
        double[] tt = new double[ff.length];
        for(int i = 0; i < ff.length; i++) {
            tt[i] = ff[i] * -1;
        }
        t.setComponents(tt);
        return t;
    }

    public String toString() {
        String t = "< ";
        double[] tt = this.getRawComponents();
        for(int i = 0; i < tt.length;i++) {
            t += tt[i] + " ";
        }
        t += ">";
        return t;
    }

    public double magnitude() {
        return mag;
    }

    public static double dot(Vector f, Vector s) {
        double[] ff = f.getRawComponents();
        double[] ss = s.getRawComponents();
        if(ff.length != ss.length) throw new RuntimeException("Vectors are in different dimensions.");
        double t = 0;
        for(int i = 0; i < ff.length; i++) {
            t += ss[i] * ff[i];
        }
        return t;
    }

    public void multiplyByScalar(double d) {
        double[] t = this.getRawComponents();
        for(int i = 0; i < t.length; i++) {
            t[i] *= d;
        }
        setComp(t);
    }

    public void divideByScalar(double d) {
        double[] t = this.getRawComponents();
        for(int i = 0; i < t.length; i++) {
            t[i] /= d;
        }
        setComp(t);
    }

    public void addScalar(double d) {
        double[] t = this.getRawComponents();
        for(int i = 0; i < t.length; i++) {
            t[i] += d;
        }
        setComp(t);
    }

    public void subtractScalar(double d) {
        double[] t = this.getRawComponents();
        for(int i = 0; i < t.length; i++) {
            t[i] -= d;
        }
        setComp(t);
    }
    
    /***
       Not sure if this should be a void method or not. On one hand, it allows the user to make a separate vector
       as opposed to directly modifying the original one. I just decided to make both and comment one out.
    ***/
    public Vector norm() {
        double[] tt = this.getRawComponents();
        double[] uu = new double[tt.length];
        double m = this.magnitude();
        for (int i = 0; i < tt.length; i++) {
            uu[i] = tt[i] / m;
        }
        Vector u = new Vector(uu);
        return u;
    }
    
    /*
    public void norm() {
        double m = this.magnitude();
        this.divideByScalar(m);
    }
    */
    
    public static Vector cross(Vector f, Vector s) {
        double[] ff = f.getRawComponents();
        double[] ss = s.getRawComponents();
        if (ff.length != ss.length) throw new RuntimeException("Vectors are in different dimensions.");
        // because we checked to see if the lengths were different, we don't need to check if both are of dimensions 3 or 7
        if (ff.length != 3 || ff.length != 7) throw new RuntimeException("Cross Product only works in 3d and 7d.");
        
        // init new double array to hold vector components
        double[] tt = new double[ff.length];
        if (ff.length == 3) {
            for (int i = 0; i < 3; i++) {
                tt[i] = ff[(i + 1) % 3]*ss[(i + 2) % 3] - ff[(i + 2) % 3]*ss[(i + 1) % 3];
            }
        } else {
            for (int i = 0; i < 7; i++) {
                tt[i] = ff[(i + 1) % 7]*ss[(i + 3) % 7] - ff[(i + 3) % 7]*ss[(i + 1) % 7] +
                        ff[(i + 2) % 7]*ss[(i + 6) % 7] - ff[(i + 6) % 7]*ss[(i + 2) % 7] +
                        ff[(i + 4) % 7]*ss[(i + 5) % 7] - ff[(i + 5) % 7]*ss[(i + 5) % 7];
            }
        }
        Vector t = new Vector(tt);
        return t;
    }
    
    public void clear() {
        components.clear();
    }

}
