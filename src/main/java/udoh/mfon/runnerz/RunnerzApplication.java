package udoh.mfon.runnerz;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import udoh.mfon.runnerz.run.Location;
import udoh.mfon.runnerz.run.Run;
import udoh.mfon.runnerz.user.User;
import udoh.mfon.runnerz.user.UserHttpClient;
import udoh.mfon.runnerz.user.UserRestClient;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@SpringBootApplication
public class RunnerzApplication {

	public static void main(String[] args) {
		SpringApplication.run(RunnerzApplication.class, args);

	}

	@Bean
	UserHttpClient userHttpClient(){
		RestClient restClient = RestClient.create("https://jsonplaceholder.typicode.com/");
		HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(RestClientAdapter.create(restClient)).build();
		return factory.createClient(UserHttpClient.class);
	}

	@Bean
	CommandLineRunner runner(UserHttpClient client){
		return (args) -> {
			User user = client.findById(3);
			System.out.println(user);
		};
	}

}
