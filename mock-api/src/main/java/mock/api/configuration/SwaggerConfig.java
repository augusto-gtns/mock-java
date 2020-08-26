package mock.api.configuration;

import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.AuthorizationCodeGrantBuilder;
import springfox.documentation.builders.OAuthBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.GrantType;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.service.TokenEndpoint;
import springfox.documentation.service.TokenRequestEndpoint;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.paths.RelativePathProvider;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger.web.SecurityConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	private static final String OAUTH_SCHEME = "spring_oauth";

	@Value("${mock.api.swagger.basepath:}")
	private String basepath;

	private static final String AUTH_SERVER = "AUTH_SERVER";
	private static final String CLIENT_ID = "CLIENT_ID";
	private static final String CLIENT_SECRET = "CLIENT_SECRET";

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.pathProvider(pathProvider()).select()
				.apis(RequestHandlerSelectors.basePackage("mock.api.rest"))
				.paths(PathSelectors.any()).build()
				.securitySchemes(Arrays.asList(securityScheme()))
				.securityContexts(Arrays.asList(securityContext()))
				.apiInfo(apiInfo());
	}

	private RelativePathProvider pathProvider() {
		return new RelativePathProvider(null) {
			@Override
			public String getApplicationBasePath() {
				return StringUtils.isBlank(basepath) ? "" : basepath;
			}
		};
	}

	@Bean
	public SecurityConfiguration security() {
		return SecurityConfigurationBuilder.builder().clientId(CLIENT_ID).clientSecret(CLIENT_SECRET)
				.scopeSeparator(" ").useBasicAuthenticationWithAccessCodeGrant(true).build();
	}

	private SecurityScheme securityScheme() {
		GrantType grantType = new AuthorizationCodeGrantBuilder()
				.tokenEndpoint(new TokenEndpoint(AUTH_SERVER + "/token", "oauthtoken"))
				.tokenRequestEndpoint(new TokenRequestEndpoint(AUTH_SERVER + "/authorize", CLIENT_ID, CLIENT_SECRET))
				.build();

		return new OAuthBuilder().name(OAUTH_SCHEME).grantTypes(Arrays.asList(grantType))
				.scopes(Arrays.asList(scopes())).build();
	}

	private AuthorizationScope[] scopes() {
		return new AuthorizationScope[] { new AuthorizationScope("visitor", "Visitor Access API"),
				new AuthorizationScope("admin", "Admin Access API") };
	}

	private SecurityContext securityContext() {

		Predicate<String> exceptHome = Predicates.not(PathSelectors.regex("/"));
		Predicate<String> exceptError = Predicates.not(PathSelectors.regex("/error"));
		Predicate<String> exceptActuator = Predicates.not(PathSelectors.regex("/actuator/.*"));
		Predicate<String> exceptLogin = Predicates.not(PathSelectors.regex("/login"));
		Predicate<String> exceptOauth = Predicates.not(PathSelectors.regex("/oauth/.*"));
		Predicate<String> exceptPublic = Predicates.not(PathSelectors.regex("/public/.*"));

		return SecurityContext.builder()
				.securityReferences(Arrays.asList(new SecurityReference(OAUTH_SCHEME, scopes())))
				.forPaths(Predicates.and(Arrays.asList(exceptHome, exceptError, exceptActuator, exceptLogin, exceptOauth, exceptPublic))).build();
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("Mock REST API").build();
	}

}