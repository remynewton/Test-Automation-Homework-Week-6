package com.laba.solvd.hw;

import com.laba.solvd.hw.Exception.LogReaderException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class LogReader {
    public void countUniqueWords(String logFilePath) throws LogReaderException {
        File file = new File(logFilePath);
        InputStream inputStream = null;
        try {
            inputStream = FileUtils.openInputStream(file);
            String text = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
            String[] words = StringUtils.split(text);
            Set<String> uniqueWords = new HashSet<>();
            Collections.addAll(uniqueWords, words);
            int numUniqueWords = uniqueWords.size();
            FileUtils.writeStringToFile(file, "Number of unique words: " + numUniqueWords, StandardCharsets.UTF_8, true);
            System.out.println("Result written to file.");
        } catch (IOException e) {
            throw new LogReaderException("Failed to read or write to file.", e);
        } finally {
            IOUtils.closeQuietly(inputStream);
        }
    }
}