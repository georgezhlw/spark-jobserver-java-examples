package com.sparkjobserver.examples;

import java.util.Arrays;
import java.util.List;

import com.typesafe.config.Config;
import org.apache.spark.api.java.JavaSparkContext;
import scala.Tuple2;
import spark.jobserver.api.JobEnvironment;
import spark.jobserver.japi.JSparkJob;

public class SparkWordCountJob implements JSparkJob<List<Tuple2<String, Integer>>> {

    public List<Tuple2<String, Integer>> run(JavaSparkContext jsc, JobEnvironment runtime, Config config) {
        List<Tuple2<String, Integer>> results = jsc
                .parallelize(Arrays.asList(config.getString("input.string").split(" ")))
                .mapToPair(x -> new Tuple2<>(x, 1))
                .reduceByKey((a, b) -> a + b)
                .collect();
        return results;
    }

    public Config verify(JavaSparkContext sc, JobEnvironment runtime, Config config) {
        try {
            config.getString("input.string");
            return config;
        } catch (Exception e) {
            throw new RuntimeException("Required config input.string is missing");
        }
    }
}
