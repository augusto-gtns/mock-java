package mock.api.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.approval.UserApprovalHandler;
import org.springframework.security.oauth2.provider.token.TokenStore;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

	@Autowired
	private TokenStore tokenStore;

	@Autowired
	private UserApprovalHandler userApprovalHandler;

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	UserDetailsService userDetailsService;
	
	public static final String CLIENT_ID = "CLIENT_ID";
	public static final String CLIENT_SECRET = "{noop}CLIENT_SECRET";
	private static final int TOKEN_EXPIRATION = 30;
	private static final int REFRESH_TOKEN_EXPIRATION = 600;
	
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {

		clients.inMemory().withClient(CLIENT_ID)
				.authorizedGrantTypes("password", "authorization_code", "refresh_token", "implicit")
				.authorities("ADMIN")
				.scopes("web", "mobile")
				.secret(CLIENT_SECRET)
				.accessTokenValiditySeconds(TOKEN_EXPIRATION). // Access token is only valid for 2 minutes.
				refreshTokenValiditySeconds(REFRESH_TOKEN_EXPIRATION); // Refresh token is only valid for 10 minutes.
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints.tokenStore(tokenStore).userApprovalHandler(userApprovalHandler).userDetailsService(userDetailsService)
				.authenticationManager(authenticationManager);
	}

}
