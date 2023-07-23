import java.io.*;
import java.util.Random;

public class DataGenerator {
    public static void CreateTestFile(double wantedSize, String type, String filename) throws FileNotFoundException, UnsupportedEncodingException{
        wantedSize = Double.parseDouble(System.getProperty("size", Double.toString(wantedSize)));
        Random random = new Random();
        File file = new File(filename);
        long start = System.currentTimeMillis();
        PrintWriter writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8")), false);
        int counter = 0;
        while (true) {
            String sep = "";
            switch (type) {
                case "-i" -> {
                    for (int i = 0; i < 100; i++) {
                        int number = random.nextInt(1000) + 1;
                        writer.print(sep);
                        writer.print(number);
                        sep = "\n";
                    }
                }
                case "-s" -> {
                    for (int i = 0; i < 100; i++) {
                        int leftLimit = 97; // letter 'a'
                        int rightLimit = 122; // letter 'z'
                        int targetStringLength = 5;
                        StringBuilder buffer = new StringBuilder(targetStringLength);
                        for (int j = 0; j < targetStringLength; j++) {
                            int randomLimitedInt = leftLimit + (int)
                                    (random.nextFloat() * (rightLimit - leftLimit + 1));
                            buffer.append((char) randomLimitedInt);
                        }
                        String generatedString = buffer.toString();
                        writer.print(sep);
                        writer.print(generatedString);
                        sep = "\n";
                    }
                }
            }
            writer.println();
            //Check to see if the current size is what we want it to be
            if (++counter == 20000) {
                System.out.printf("Size: %.3f GB%n", file.length() / 1e9);
                if (file.length() >= wantedSize * 1e9) {
                    writer.close();
                    break;
                } else {
                    counter = 0;
                }
            }
        }
    }
}
