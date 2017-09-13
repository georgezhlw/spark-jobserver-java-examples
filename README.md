# spark-jobserver-java-examples
Example Spark jobs of spark-jobserver in Java, works with lastest 0.8 version.  

Instructions

Get spark-jobserver up and running following the instructions at https://github.com/spark-jobserver/spark-jobserver.

Clone this project

git clone https://github.com/georgezhlw/spark-jobserver-java-examples

Build with gradle

gradle build

Upload and run the word count example

curl --data-binary @build/libs/spark-jobserver-java-examples-1.0.jar localhost:8090/jars/myapp1  

Use the following to create a Java spark context, otherwise the program will get error "SparkWordCountJob cannot be cast to spark.jobserver.api.SparkJobBase":  
curl -d "" 'localhost:8090/contexts/jcontext1?context-factory=spark.jobserver.context.JavaSparkContextFactory'  

curl -d "input.string = a b c a b see" 'localhost:8090/jobs?appName=myapp1&classPath=com.sparkjobserver.examples.SparkWordCountJob&context=jcontext1&sync=true'  
 
