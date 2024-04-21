BANKING APPLICATION
========================
### OOP Project Using JAVA with MYSQL Database


#### How to Run:
1)  The ER Diagram of the database is present in the lib folder.  Make the same database as mentioned in your local MySQL server. Add at least one record in the admin table. Note that all tables with primary key have auto increment and default values.  
2) Use the command `java -jar Project_Banking Application.jar` to run the commandline app. The .jar file is present in the out folder.  
#### Note:  The .jar file is  compiled by Oracle  JDK 19. If your system has lower version of JDK,  update it or rebuild the .jar  file  with your JDK version.
The default local MySQL server details are: 
* Username - root
* Password - Root@123
* Database Name - banking_database

To change this, head to **src/Connection** and modify the class **Connection_establish** method **dbms_connect()** with appropriate details and rebuild the .jar file  present in the lib folder.
