package com.sparkjobserver.examples;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import com.typesafe.config.Config;
import org.apache.spark.SparkContext;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import scala.Tuple2;
import spark.jobserver.SparkJob;
import spark.jobserver.SparkJobInvalid;
import spark.jobserver.SparkJobValid$;
import spark.jobserver.SparkJobValidation;

public class SparkWordCountJob implements SparkJob, Serializable {
  /**
   * 
   */
  private static final long serialVersionUID = 2336840751384066490L;

  public Object runJob(Object sparkContext, Config config) {
    JavaSparkContext sc = new JavaSparkContext((SparkContext) sparkContext);
    List<Tuple2<String, Integer>> results =
        sc.parallelize(Arrays.asList(config.getString("input.string").split(" "))).
            mapToPair(new PairFunction<String, String, Integer>() {
              public Tuple2<String, Integer> call(String s) throws Exception {
                return new Tuple2<String, Integer>(s, 1);
              }
            }).reduceByKey(new Function2<Integer, Integer, Integer>() {
              public Integer call(Integer v1, Integer v2) throws Exception {
                return v1 + v2;
              }
            }).collect();
    sc.close();
    return results;
  }

  public SparkJobValidation validate(Object sparkContext, Config config) {
    try {
      config.getString("input.string");
      return SparkJobValid$.MODULE$;
    } catch (Exception e) {
      return new SparkJobInvalid("No input.string config param");
    }
  }
}
