import java.io.*;
import java.util.Map;
public class TextReader {
    public static void ReadIntFile(String fileName, Map<Integer, Integer> outMap) throws IOException {
        try (BufferedReader firstReader = new BufferedReader(new FileReader(fileName))) {
            String line = "";
            boolean isFileEmpty = true;
            while ((line = firstReader.readLine()) != null) {
                isFileEmpty = false;
                Integer number = Integer.parseInt(line);
                Integer curCount = outMap.get(number);
                outMap.put(number, curCount == null ? 1 : ++curCount);
            }
            if(isFileEmpty) System.out.println("One of file is empty! Choose different file!");
        } catch (IOException e) {

            e.printStackTrace();
        }
        catch(NumberFormatException ex){
            System.out.println("File contains string! Restart program and select -s mode!");
        }
    }
    public static void CreateOutputIntFile(Integer[][] sortedData, String filePath) {
        try{
            BufferedWriter bw = new BufferedWriter(new FileWriter(filePath));
            for (Integer[] integers : sortedData) {
                String value = integers[0].toString();
                for (int k = 0; k < integers[1]; k++) {
                    bw.write(value);
                    bw.newLine();
                }
            }
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
