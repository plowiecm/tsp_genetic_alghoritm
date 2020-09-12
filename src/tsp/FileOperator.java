package tsp;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;

public class FileOperator {

    public static int[][] readFileToArray(String fileName) throws IOException, InvalidInputException, InvalidStructureException {
        int[][] distances = new int[][]{};
        int lineCounter = 0;
        int wordCounter = 0;

        File f = new File(fileName);
        if (f.exists() && f.isFile()) {
            BufferedReader bfReader = null;
            try {
                bfReader = new BufferedReader(new java.io.FileReader(fileName));

                String line = bfReader.readLine().trim();
                distances = new int[Integer.parseInt(line)][Integer.parseInt(line)];

                line = bfReader.readLine();
                while (line != null) {
                    String[] words = line.split("\\s+");

                    if (words.length > lineCounter + 1) {
                        throw new InvalidStructureException();
                    }

                    for (String word : words) {
                        distances[lineCounter][wordCounter] = (int) Double.parseDouble(words[wordCounter]);
                        distances[wordCounter][lineCounter] = (int) Double.parseDouble(words[wordCounter]);
                        wordCounter++;
                    }

                    line = bfReader.readLine();
                    lineCounter++;
                    wordCounter = 0;
                }
            } catch (NumberFormatException e) {
                throw new InvalidInputException(e.getMessage());
            } finally {
                if (bfReader != null) {
                    bfReader.close();
                }
            }

        }
        return distances;
    }
}
