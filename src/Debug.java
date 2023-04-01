import java.util.ArrayList;

public class Debug {
    public static int FPS = 0;
    public static int averageFPS = 0;
    public static int minFPS = 2147483647;
    public static int maxFPS = 0;
    public static ArrayList<Integer> fpsGraph = new ArrayList<>();

    // Records FPS data
    public static void recordFPS() {
        minFPS = 2147483647;
        maxFPS = 0;
        // Record FPS
        fpsGraph.add(FPS);
        if(fpsGraph.size() > 75) {
            fpsGraph.remove(0);
        }
        // Get average FPS
        for(int n : fpsGraph) {
            averageFPS += n;
            if(n < minFPS)
                minFPS = n;
            if(n > maxFPS)
                maxFPS = n;
        }
        averageFPS /= fpsGraph.size();
    }
}
