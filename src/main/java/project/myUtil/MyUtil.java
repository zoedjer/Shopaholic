package project.myUtil;

public class MyUtil {
    
    public static String reformatDateString(String time) {
	return time.replaceAll("(\\d+)/(\\d+)/(\\d+)", "$3-$1-$2");
    }

}
