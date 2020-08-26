package mock.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.approval.TokenApprovalStore;
import org.springframework.security.oauth2.provider.approval.TokenStoreUserApprovalHandler;
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestFactory;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

import lombok.extern.log4j.Log4j2;
import mock.api.configuration.OAuth2SecurityConfiguration;

@SpringBootApplication
@EntityScan("mock.api.model*")
@EnableJpaRepositories("mock.api.repository*")
@ComponentScan({ "mock.api.rest*", "mock.api.service*", "mock.api.configuration*", "mock.api.util*"})
@Log4j2
public class MockAPIApplication {

	public static void main(String[] args) {
		SpringApplication.run(MockAPIApplication.class, args);
		log.info("................................................................................");
	}
	
	@Autowired
	ClientDetailsService clientDetailsService;
	
	@Autowired
	OAuth2SecurityConfiguration oAuth2SecurityConfiguration;
	
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return oAuth2SecurityConfiguration.authenticationManagerBean();
	}
	
	@Bean
	public TokenStore tokenStore() {
		return new InMemoryTokenStore();
	}

	@Bean
	public TokenStoreUserApprovalHandler userApprovalHandler(TokenStore tokenStore) {
		TokenStoreUserApprovalHandler handler = new TokenStoreUserApprovalHandler();
		handler.setTokenStore(tokenStore);
		handler.setRequestFactory(new DefaultOAuth2RequestFactory(clientDetailsService));
		handler.setClientDetailsService(clientDetailsService);
		return handler;
	}

	@Bean
	public ApprovalStore approvalStore(TokenStore tokenStore) throws Exception {
		TokenApprovalStore store = new TokenApprovalStore();
		store.setTokenStore(tokenStore);
		return store;
	}
	
}
