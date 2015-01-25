package eivindw.hystrix;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

import java.util.Random;

public class RandomCommand extends HystrixCommand<Integer> {

   private static final Random RANDOM = new Random();

   public RandomCommand() {
      super(HystrixCommandGroupKey.Factory.asKey("RandomGroup"));
   }

   @Override
   protected Integer run() throws Exception {
      return RANDOM.nextInt();
   }
}
