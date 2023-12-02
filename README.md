# SQE_JTspringBoot_Project

# Introduction : This is an ecommerce application.

# Instructions :
1. Clone the repository in your intellij locally
2. Integrate Database connectivity - using mySQL workbench or dbeaver 
3. Run the database schema (basedata.sql) to make the tables
4. Make sure you have database navigator plugins and spring boot enabled
5. Open pom.xml as a Project in your intellij

# Changes made:
1. change line 240 to executeQuery() to run the application - admincontroller.java
2. The password for the DB was changed according to the root password - application.properties
3. Line 234 username class was changed to remove errors -admincontroller.java
4. Removed "NO_AUTO_CREATE_USER" from the first line in basedata.sql

# How to run the tests using command:
1. Install mvn if it is not installed in your device (to check this you can open command prompt and run "mvn -v")
2. If mvn is istalled in your device, then open the terminal/command prompt in your project directory
3. Write "mvn test" and run this on terminal/command prompt
4. All the test cases that are present in test folder in project will be executed and result will be displayed on the console.

# Group Members:
1. Aiza Khurram  21L-5771
2. Minaal Khurram 21L-5828
4. Aaon Raza  21L-6055
3. Tayyab Nadeem  21L-5830
