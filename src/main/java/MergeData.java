import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.*;

public class MergeData {
    public static Integer[][] HashMapToIntegerArray(Map<Integer, Integer> outputMap, String sortMode) {
        if(sortMode.equals("-d")){
            return outputMap.entrySet().stream()
                    .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                    .map(e -> new Integer[]{e.getKey(), e.getValue()})
                    .toArray(Integer[][]::new);
        }

        else{
            return outputMap.entrySet().stream()
                    .map(e -> new Integer[] { e.getKey(), e.getValue() })
                    .toArray(Integer[][]::new);
        }
    }
    public static void mergeSortStrings(List<String> inputFiles, String sortMode, String outputFileName) throws IOException {
        int chunkSize = 100000;
        int chunkIndex = 0;
        List<String> chunk = new ArrayList<>();

        for (String inputFile : inputFiles) {
            try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    chunk.add(line);
                    if (chunk.size() >= chunkSize) {
                        // sort chunk before writing to temp file
                        mergeSort(chunk, sortMode);
                        String chunkFileName = "chunk" + chunkIndex + ".tmp";
                        try (BufferedWriter writer = new BufferedWriter(new FileWriter(chunkFileName))) {
                            for (String str : chunk) {
                                writer.write(str);
                                writer.newLine();
                            }
                        }
                        chunk.clear();
                        chunkIndex++;
                    }
                }
            }
        }

        if (!chunk.isEmpty()) {
            // sort last temp-chunk
            mergeSort(chunk, sortMode);
            String chunkFileName = "chunk" + chunkIndex + ".tmp";
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(chunkFileName))) {
                for (String str : chunk) {
                    writer.write(str);
                    writer.newLine();
                }
            }
            chunkIndex++;
        }

        // merge sorted temp files
        mergeChunks(chunkIndex, outputFileName);

        // delete temp files
        cleanup(chunkIndex);
    }

    private static void mergeSort(List<String> array, String sortMode) {
        if (array.size() <= 1) {
            return;
        }

        int middle = array.size() / 2;
        List<String> left = new ArrayList<>(array.subList(0, middle));
        List<String> right = new ArrayList<>(array.subList(middle, array.size()));

        mergeSort(left, sortMode);
        mergeSort(right, sortMode);

        merge(array, left, right, sortMode);
    }

    private static void merge(List<String> array, List<String> left, List<String> right, String sortMode) {
        int leftIndex = 0;
        int rightIndex = 0;
        int arrayIndex = 0;

        while (leftIndex < left.size() && rightIndex < right.size()) {
            String leftElement = left.get(leftIndex);
            String rightElement = right.get(rightIndex);

            int comparisonResult = compareElements(leftElement, rightElement, sortMode);
            if (comparisonResult <= 0) {
                array.set(arrayIndex, leftElement);
                leftIndex++;
            } else {
                array.set(arrayIndex, rightElement);
                rightIndex++;
            }
            arrayIndex++;
        }

        while (leftIndex < left.size()) {
            array.set(arrayIndex, left.get(leftIndex));
            leftIndex++;
            arrayIndex++;
        }

        while (rightIndex < right.size()) {
            array.set(arrayIndex, right.get(rightIndex));
            rightIndex++;
            arrayIndex++;
        }
    }

    private static int compareElements(String element1, String element2, String sortMode) {
        if (sortMode.equalsIgnoreCase("-d")) return element2.compareTo(element1);
        else return element1.compareTo(element2);
    }

    private static void mergeChunks(int chunkIndex, String outputFileName) throws IOException {
        List<BufferedReader> readers = new ArrayList<>();
        for (int i = 0; i < chunkIndex; i++) {
            String chunkFileName = "chunk" + i + ".tmp";
            readers.add(new BufferedReader(new FileReader(chunkFileName)));
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFileName))) {
            List<String> currentLines = new ArrayList<>();
            for (int i = 0; i < chunkIndex; i++) {
                String line = readers.get(i).readLine();
                if (line != null) {
                    currentLines.add(line);
                }
            }

            while (!currentLines.isEmpty()) {
                String minLine = Collections.min(currentLines);
                writer.write(minLine);
                writer.newLine();

                for (int i = 0; i < currentLines.size(); i++) {
                    if (currentLines.get(i).equals(minLine)) {
                        String nextLine = readers.get(i).readLine();
                        if (nextLine != null) {
                            currentLines.set(i, nextLine);
                        } else {
                            currentLines.remove(i);
                            readers.get(i).close();
                            readers.remove(i);
                            i--;
                        }
                    }
                }
            }
        }
        // close temp-file readers
        for (BufferedReader reader : readers) {
            reader.close();
        }
    }
    private static void cleanup(int chunkIndex) {
        for (int i = 0; i < chunkIndex; i++) {
            String chunkFileName = "chunk" + i + ".tmp";
            File file = new File(chunkFileName);
            if (file.exists()) {
                file.delete();
            }
        }
    }
}
