package app.controller;

import java.util.Base64;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import app.dto.AccessCardDto;
import app.dto.LoginDto;
import app.dto.UserDto;
import app.service.AuthService;
import app.util.Notification;

@RestController
@RequestMapping("auth")
public class AuthController {

	@Value("${oauth.lms.client-id}")
	private String CLIENT_ID;
	
	@Value("${oauth.lms.client-secret}")
	private String SECRET;
	
	@Value("${spring.data.rest.base-path}")
	private String BASE_URL;
	
	@Autowired
	private TokenStore tokenStore;
	
	@Autowired
	private AuthService authService;

	@PostMapping(value = "/register")
	public Notification signUp(@Valid @RequestBody UserDto accountDto) {
		authService.register(accountDto);
		return new Notification(true, "Created");
	}

	@PostMapping(value = "/login")
	public Object signIn(@RequestBody LoginDto loginDto) {
		
		// SET REQUEST HEADER
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		String plainCreds = CLIENT_ID + ":" + SECRET;
		String base64Creds = new String(Base64.getEncoder().encode(plainCreds.getBytes()));
		headers.add("Authorization", "Basic " + base64Creds);

		// SET REQUEST BODY
		MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
		map.add("grant_type", "password");
		map.add("username", loginDto.getUsername());
		map.add("password", loginDto.getPassword());

		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);

		RestTemplate  restTemplate = new RestTemplate();
		
		// MAKE REQUEST FOR ACCESS TOKEN
		String authServerUri = BASE_URL + "/oauth/token";
		ResponseEntity<OAuth2AccessToken> response = restTemplate.postForEntity(authServerUri, request , OAuth2AccessToken.class);
		OAuth2AccessToken tokenData = response.getBody();
		OAuth2Authentication oauthData = tokenStore.readAuthentication(tokenData); 
		
		return new AccessCardDto(tokenData, oauthData);
	
	}

}
