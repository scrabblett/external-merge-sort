import java.io.IOException;
import java.util.*;

public class UserInterface {
    private String dataType;
    private String sortMode = "-a";
    private String outputPath;
    List<String> srcFiles = new LinkedList<>();

    public void ProcessFiles(String[] args) throws IOException {
        setCommandArgs(args);
        System.out.println("Sorting mode:" + sortMode);
        System.out.println("Data type:" + dataType);
        System.out.println("Output path:" + outputPath);
        System.out.println("Files to merge:");
        for(String srcFileName:srcFiles){
            System.out.println(srcFileName);
        }
        if(Objects.equals(dataType, "-i")) {
            Map<Integer, Integer> outputMap = new HashMap<>();
            for (String filePath: srcFiles) {
                TextReader.ReadIntFile(filePath, outputMap);
            }
            Integer[][] sortedMap = Sorter.mergeSort(MergeData.HashMapToIntegerArray(outputMap, sortMode), sortMode);
            TextReader.CreateOutputIntFile(sortedMap, outputPath);
        }
        else{
            MergeData.mergeSortStrings(srcFiles, sortMode, outputPath);
        }
    }
    private void setCommandArgs(String[] args){
        if (args.length < 3){
            System.out.println("Invalid number of arguments!\nRestart the program and enter all required arguments");
            System.exit(0);
        }
        if(args.length == 3){
            if(Objects.equals(args[0], "-a") ||(Objects.equals(args[0], "-d"))) {
                System.out.println("You have not entered files to sort!\nRestart the program and enter all required arguments");
                System.exit(0);
            }
            else if(Objects.equals(args[0], "-i")){
                dataType = "-i";
            }
            else{
                dataType = "-s";
            }
            outputPath = args[1];
            srcFiles.add(args[2]);

        }
        if(args.length > 3){
            if(args[0].equals("-i")){
                dataType = "-i";
                outputPath = args[1];
                srcFiles.addAll(Arrays.asList(args).subList(2, args.length));
            }
            else if(args[0].equals("-s")) {
                dataType = "-s";
                outputPath = args[1];
                srcFiles.addAll(Arrays.asList(args).subList(2, args.length));
            }
            else if (args[0].equals("-d")){
                sortMode = "-d";
                if(args[1].equals("-i")){
                    dataType = "-i";
                    outputPath = args[2];
                    srcFiles.addAll(Arrays.asList(args).subList(3, args.length));
                }
                else{
                    dataType = "-s";
                    outputPath = args[2];
                    srcFiles.addAll(Arrays.asList(args).subList(3, args.length));
                }
            }
            else if(args[1].equals("-i")){
                    dataType = "-i";
                    outputPath = args[2];
                    srcFiles.addAll(Arrays.asList(args).subList(3, args.length));
                }
                else{
                    dataType = "-s";
                    outputPath = args[2];
                    srcFiles.addAll(Arrays.asList(args).subList(3, args.length));
                }
            }
            if(dataType == null || outputPath == null || srcFiles == null) {
                System.out.println("Invalid number of arguments!\nRestart the program and enter all required arguments");
                System.exit(0);
        }
    }
}
