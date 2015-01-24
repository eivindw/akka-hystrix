package eivindw.api;

import com.google.common.collect.ImmutableMap;
import eivindw.hystrix.HelloCommand;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.Map;

@Path("/test")
@Produces("application/json")
public class TestResource {

   @GET
   public Map test() {
      final String result = new HelloCommand("test-resource").execute();

      return ImmutableMap.of(
         "message", result
      );
   }
}
