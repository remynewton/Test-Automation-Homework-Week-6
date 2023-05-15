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

