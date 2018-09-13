package app.service;

import java.util.Arrays;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.AccessTokenRequest;
import org.springframework.security.oauth2.client.token.DefaultAccessTokenRequest;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
import org.springframework.stereotype.Service;

import app.dto.LoginDto;
import app.dto.UserDto;
import app.entity.Role;
import app.entity.User;
import app.exception.ExceptionDuplicateRecord;
import app.repository.RoleRepository;
import app.repository.UserRepository;

@Service
public class AuthServiceImpl implements AuthService {
	
	private boolean usernameExist(String username) {
		User user = userRepo.findByUsername(username);
		if (user != null)
			return true;
		return false;
	}

	private boolean emailExist(String email) {
		User user = userRepo.findByEmail(email);
		if (user != null)
			return true;
		return false;
	}

	@Autowired
	UserRepository userRepo;

	@Autowired
	RoleRepository roleRepo;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Transactional
	@Override
	public User register(UserDto accountDto) throws ExceptionDuplicateRecord {

		if (emailExist(accountDto.getEmail())) {
			throw new ExceptionDuplicateRecord("There is an account with that email adress: " + accountDto.getEmail());
		}

		if (usernameExist(accountDto.getUsername())) {
			throw new ExceptionDuplicateRecord("There is an account with that username: " + accountDto.getUsername());
		}

		User user = new User();
		user.setEmail(accountDto.getEmail());
		user.setUsername(accountDto.getUsername());
		user.setPassword(passwordEncoder.encode(accountDto.getPassword()));

		Role defaultRole = roleRepo.findOrCreate(Role.BORROWER);
		user.setRoles(Arrays.asList(defaultRole));

		return userRepo.save(user);
	}

	@Value("${spring.data.rest.base-path}")
	private String BASE_URL;
	
	@Value("${oauth.lms.client-id}")
	private String CLIENT_ID;
	
	@Value("${oauth.lms.client-secret}")
	private String SECRET;
	
	@Override
	public Object login(LoginDto loginDto) {
		
		ResourceOwnerPasswordResourceDetails details = getOAuthResourceDetails(loginDto.getUsername(), loginDto.getPassword());
		
		OAuth2RestOperations operations = getRestTemplate(details);
//		OAuth2AccessToken accessToken = operations.getAccessToken();
		return operations.getAccessToken()
				;
	}
	
	// Access token request configuration
	private ResourceOwnerPasswordResourceDetails getOAuthResourceDetails(String username, String password) {

		ResourceOwnerPasswordResourceDetails resource = new ResourceOwnerPasswordResourceDetails();

		List<String> scopes = Arrays.asList("read", "write", "trust");

		resource.setAccessTokenUri(BASE_URL + "/oauth/token");
		resource.setClientId(CLIENT_ID);
		resource.setClientSecret(SECRET);
		resource.setGrantType("password");
		resource.setScope(scopes);

		resource.setUsername(username);
		resource.setPassword(password);

		return resource;
	}

	/*
	 * Usage: restTemplate.getAccessToken();
	 */
	public OAuth2RestOperations getRestTemplate(ResourceOwnerPasswordResourceDetails details) {
		AccessTokenRequest atr = new DefaultAccessTokenRequest();
		DefaultOAuth2ClientContext ctx = new DefaultOAuth2ClientContext(atr);
		
		return new OAuth2RestTemplate(details, ctx);
	}

}
