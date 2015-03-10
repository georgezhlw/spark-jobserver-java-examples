# spark-jobserver-java-examples
Example Spark jobs of spark-jobserver in Java

Instructions

Get spark-jobserver up and running following the instructions at https://github.com/spark-jobserver/spark-jobserver.

Clone this project

git clone https://github.com/ducnm0/spark-jobserver-java-examples

Build with gradle

gradle build

Upload and run the word count example

curl --data-binary @build/libs/spark-jobserver-java-examples-1.0.jar localhost:8090/jars/test

curl -d "input.string = a b c a b see" 'localhost:8090/jobs?appName=test&classPath=com.sparkjobserver.examples.SparkWordCountJob&sync=true'
 