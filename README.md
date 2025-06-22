# SentenceExtractor

A command-line tool that extracts sentences and words from text files, outputting results in XML format.


# How to run
To use it first you need to build a project. Execute this command inside downloaded project.
```
mvn clean test install
```

then execute jar file with parameter ```--file```, for example ```/Users/user/example_file.txt```
```
java -jar target/SentenceExtractor-1.0-SNAPSHOT.jar --file <path_to_file>
```
