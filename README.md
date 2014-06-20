Proyect Search Web based in Java + Apache Tomcat with Lucene and Mallet
=====
This project is based on the indexed data search, which was created by the IDE Netbeans 8 and riding on a webserver Apache Tomcat, where an index is used invert generated through Lucene 4.3.1 library, and for a classifier data through 2.0.7 Mallet library.

Apache Tomcat 7 Installation
------------------
To run this project, you need to have installed `Apache Tomcat` websever in your `Ubuntu`. If you do not have, you can install it with the following command.

   	$ sudo apt-get install tomcat7
  
Then you must add the file `Bajavista.war` in the folder `dist` administrator applications Apache Tomcat, which can be located in `http://localhost:8080/manager/html`  


Datebase configuration
------------------
In this program is used with `MySQL` database, so you must configure the file `ConexionSQL` in the folder `src/java/conexionsql`, where the table should indicate use of the data analyzed in search of data. To put data into the database, you can verify and analyze `bd` folder `Example` file which brings references to create a new one.

Mallet configuration
------------------
In order to classify data with Mallet, you must run the `mallet` folder `mallet.sh` file. What made ​​that file, is that the text that is in the text folder, it will sort by objectivity, subjectivities, with positive and negative trends of the text.

Bajavista Search Web Running
------------------
Enter the URL `http://localhost:8080/Bajavista` and you'll get your home search indexed data !
