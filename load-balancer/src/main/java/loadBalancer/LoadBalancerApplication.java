package loadBalancer;

import loadBalancer.configuration.Configuration;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

@SpringBootApplication
@RestController
@RibbonClient(name = "load-balancer", configuration = Configuration.class)
public class LoadBalancerApplication {

    @LoadBalanced
    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Autowired
    RestTemplate restTemplate;

    @RequestMapping("/convertXMLToJSON")
    public String convertXMLToJSON(@RequestParam(value = "path") String path) {
        URI uri;
        try {
            uri = new URIBuilder("http://converter/convertXMLToJSON").addParameter("path", path).build();
        } catch (URISyntaxException e) {
            return String.format("Could not build request from given param\n %s", e);
        }
        String response = restTemplate.getForObject(uri, String.class);
        return response;
    }

    public static void main(String[] args) {
        SpringApplication.run(LoadBalancerApplication.class, args);
    }
}
