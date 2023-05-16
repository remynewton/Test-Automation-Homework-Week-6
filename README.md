# Test-Automation-Homework-Week-6

My mentor gave me the following assessment of how much my code has completed all of the homeworks prior and current:

hw 2 (hierarchy) - done
hw 3 (abstract) - add logic for working with 5 protected fields
hw 4 (interface) -Create final class, method, variable.
Create a static block, method, variable.
hw 5 (exceprions)
Use try-catch with resources.
hw 6 (collections) - done
hw 7 (maven) - done
hw 8 (utils) - done
hw 9 (lambda, enums)  - enums
Use at least 5 lambda functions from the java.util.function package.
Create 3 custom Lambda functions with generics.
Create 5 complex Enums(with fields, methods, blocks).
hw 10 (streaming collections)
Add 7 collection streaming in the hierarchy with terminal and non-terminal operations.
Using reflection extract information(modifiers, return types, parameters, etc) about fields, constructors, methods. Create object and call method using the only reflection.

# For Homework 3 & 4

HoldingCell is a final class and has a static object variable and a static method. There are two final variables in Crime.java called description and severity. In terms of protected variables, I have 1 in Jail called totalJails and 1 in Beast called furry.

I decided to change the name and DOB variables in the Beast class to protected as well as the age variable in the Person class.

I implemented a static block in the HoldingCell class, so that the holdingCell object would be instantiated at the moment of class creation. I read about it here: https://www.java2novice.com/java-fundamentals/static/singleton/. I actuallly think this implementation is better than my prior one.

I also changed some of the getters and setters in the abstract class Person to final, because I do not want polymorphism.

# For Homework 5

I changed the LogReader class so that it implemented a try-catch with resources.

```
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
```

# For Homeowork 9

I’d like to note that I made 4 complex enums already and 2 custom lambda functions with generics. I don’t recall how many lambda functions from utils I used, so I’ll make some.
