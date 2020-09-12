package com.company;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class FileOperatorTest {

    @Test
    public void testReadToDistances() throws IOException, InvalidInputException, InvalidStructureException {
        int[][] distances = FileOperator.readFileToArray("resources\\berlin52.txt");

        Assert.assertEquals(52, distances.length);
    }

    @Test(expected = InvalidInputException.class)
    public void wrongDistanceInFile() throws IOException, InvalidInputException, InvalidStructureException {
        FileOperator.readFileToArray("resources\\wrong_file.txt");
    }

    @Test(expected = InvalidStructureException.class)
    public void wrongFileAsym() throws IOException, InvalidInputException, InvalidStructureException {
        FileOperator.readFileToArray("resources\\wrong_file2.txt");
    }
}
