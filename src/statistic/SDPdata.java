package statistic;

public class SDPdata {
    private static int[] LacIgaps = {1,2,3,4,5,6,7,8, 35,36,37, 82,83,84,85,86,87, 171,172, 196,197, 214,215,216,217,218, 261,264,265, 291,292,293, 346,347,348,349,350,351,352,353, 370,371,372,373,374,375,376,377,378,379,380,381,382,383};
    private static int[] LacISDPs = {38,33,	140,24,180,178,164,28,362,161,66,30,333,23,284,138,127,315,162,13,113,108,64,90,325,163,306,79,95,98,61,51,139,177,183,12,68,124,102};
    public static int[] getSDPs(){
    	return LacISDPs;
    }
    public static int[] getGaps(){
    	return LacIgaps;
    }
}
