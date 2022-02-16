public class GraphicAVLTreeRunner {

    public static void main(String[] args) {
        new GraphicAVLTreeWindow();
    }

    public static String fmt(double d)
    {
        if (d == (long) d)
            return String.format("%d",(long)d);
        else
            return String.format("%s",d);
    }
}
