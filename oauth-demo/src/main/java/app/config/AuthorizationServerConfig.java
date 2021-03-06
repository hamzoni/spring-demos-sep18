package app.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;

import app.CustomUserDetails;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

	static final String CLIENT_ID = "lms";
	static final String SECRET = "lms";

	static final String GRANT_TYPE_PASSWORD = "password";
	static final String CLIENT_CREDENTIALS = "client_credentials";

	@Autowired
	AuthenticationManager authenticationManager;

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		 endpoints.authenticationManager(authenticationManager);
	}

	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		security
			.checkTokenAccess("isAuthenticated()");
	}

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.inMemory().withClient(CLIENT_ID)
				.authorizedGrantTypes(CLIENT_CREDENTIALS, GRANT_TYPE_PASSWORD)
				.authorities("ROLE_CLIENT", "ROLE_TRUSTED_CLIENT")
				.scopes("read", "write", "trust")
				.resourceIds("oauth2-resource")
				.accessTokenValiditySeconds(5000).secret(SECRET);
	}
}
