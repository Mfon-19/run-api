package udoh.mfon.runnerz.user;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;

// a  REST (REpresentational State Transfer) client initiates a request to the api
// In this class, we use the RestClient provided by spring to call another api provided in the .baseUrl

@Component
public class UserRestClient {

    private final RestClient restClient;

    public UserRestClient(RestClient.Builder restClientBuilder) {
        this.restClient = restClientBuilder
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .build();
    }

    // for our custom findAll function to GET some json from the baseUrl, we specify a uri, in this case /users
    public List<User> findAll(){
        return restClient.get()
                .uri("/users")
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});
    }

    public User findById(Integer id){
        return restClient.get()
                .uri("/users/{id}", id)
                .retrieve()
                .body(User.class);
    }
}
