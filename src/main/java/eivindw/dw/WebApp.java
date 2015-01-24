package eivindw.dw;

import com.netflix.hystrix.contrib.metrics.eventstream.HystrixMetricsStreamServlet;
import eivindw.api.TestResource;
import io.dropwizard.Application;
import io.dropwizard.Configuration;
import io.dropwizard.setup.Environment;

public class WebApp extends Application<Configuration> {

   public static void main(String[] args) throws Exception {
      if (args.length == 0) {
         args = new String[]{"server"};
      }
      new WebApp().run(args);
   }

   @Override
   public void run(Configuration conf, Environment env) throws Exception {
      env.jersey().register(TestResource.class);

      env.servlets()
         .addServlet("hystrix-metrics-stream-servlet", HystrixMetricsStreamServlet.class)
         .addMapping("/hystrix.stream");
   }
}
