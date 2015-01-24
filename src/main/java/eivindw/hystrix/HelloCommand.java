package eivindw.hystrix;


import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

import java.util.Random;

public class HelloCommand extends HystrixCommand<String> {

   private final String name;

   public HelloCommand(String name) {
      super(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"));
      this.name = name;
   }

   @Override
   protected String run() throws Exception {
      final int sleepMS = new Random().nextInt(2000);

      System.out.printf(
         "%s Sleep-%d Running %s%n",
         Thread.currentThread().getName(), sleepMS, name
      );

      Thread.sleep(sleepMS);

      if (sleepMS % 2 == 0) {
         throw new RuntimeException("failure!");
      } else {
         return "Hello " + name;
      }
   }

   @Override
   protected String getFallback() {
      return "Hello fallback!";
   }
}
