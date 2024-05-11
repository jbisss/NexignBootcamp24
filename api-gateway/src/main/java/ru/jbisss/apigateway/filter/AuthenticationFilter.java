package ru.jbisss.apigateway.filter;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.DiscoveryManager;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    private final RestTemplate restTemplate;
    private final RouteValidator validator;

    public AuthenticationFilter(RestTemplate restTemplate, RouteValidator validator) {
        super(Config.class);
        this.restTemplate = restTemplate;
        this.validator = validator;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            if (validator.isSecured.test(exchange.getRequest())) {
                //header contains token or not
                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    throw new RuntimeException("missing authorization header");
                }

                String authHeader = Objects.requireNonNull(exchange.getRequest()
                                .getHeaders()
                                .get(HttpHeaders.AUTHORIZATION))
                                .get(0);
                if (authHeader != null && authHeader.startsWith("Bearer ")) {
                    authHeader = authHeader.substring(7);
                }
                try {
                    restTemplate.getForObject(String.format("http://%s/auth/validate?token=%s&request=%s",
                            getHostAndPortOfService("security-service"), authHeader, getLastPathElementFromRequest(exchange.getRequest().getPath().toString())), String.class);
                } catch (Exception e) {
                    System.out.println("invalid access...!");
                    throw new RuntimeException("un authorized access to application");
                }
            }
            return chain.filter(exchange);
        });
    }

    private String getLastPathElementFromRequest(String requestPath) {
        String[] split = requestPath.split("/");
        return split[split.length - 1];
    }

    private String getHostAndPortOfService(String serviceName) {
        InstanceInfo instanceInfo = DiscoveryManager.getInstance().getDiscoveryClient().getNextServerFromEureka(serviceName, false);
        if (instanceInfo != null) {
            return instanceInfo.getHostName() + ":" + instanceInfo.getPort();
        } else {
            return null;
        }
    }

    public static class Config {

    }
}
