package mock.api.configuration;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.embedded.undertow.UndertowServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
public class WebServerConfig {

	@Value("${mock.api.http.port:0}")
	private Integer serverHttpPort;
	
	@Value("${mock.api.http.interface:}")
	private String serverHttpinterface;
	
	@Bean
	public WebServerFactoryCustomizer<UndertowServletWebServerFactory> containerCustomizer() {
		return new WebServerFactoryCustomizer<UndertowServletWebServerFactory>() {
			@Override
			public void customize(UndertowServletWebServerFactory factory) {
				if (serverHttpPort > 0 && StringUtils.isNotBlank(serverHttpinterface)) {
					factory.getBuilderCustomizers().add(builder -> {
						builder.addHttpListener(serverHttpPort, serverHttpinterface) ;
					});
				}
			}
		};
	}

}
