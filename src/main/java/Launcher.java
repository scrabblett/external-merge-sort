import java.io.IOException;

public class Launcher {
    public static void main(String[] args) throws IOException {
       String [] test = new String[] {"-d","-s", "testSort.txt", "aboba.txt", "aboba2.txt", "aboba3.txt"};
       UserInterface ui = new UserInterface();
       ui.ProcessFiles(test);
       Runtime runtime = Runtime.getRuntime();
       long memory = runtime.totalMemory() - runtime.freeMemory();
       System.out.println("Used memory is bytes: " + memory);
       System.out.println("Used memory is megabytes: "
               + bytesToMegabytes(memory));
    }
    private static final long MEGABYTE = 1024L * 1024L;

    public static long bytesToMegabytes(long bytes) {
        return bytes / MEGABYTE;
    }
}
