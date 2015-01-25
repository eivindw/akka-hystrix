package eivindw.api;

import com.google.common.collect.ImmutableMap;
import eivindw.hystrix.HelloCommand;
import eivindw.hystrix.RandomCommand;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.Map;
import java.util.concurrent.Future;

@Path("/test")
@Produces("application/json")
public class TestResource {

   @GET
   public Map test() throws Exception {
      final Future<String> hello = new HelloCommand("test-resource").queue();
      final Future<Integer> random = new RandomCommand().queue();

      return ImmutableMap.of(
         "message", hello.get(),
         "random", random.get()
      );
   }
}
