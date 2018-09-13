package app.dto;

import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

public class AccessCardDto {
	private OAuth2AccessToken oauth;
	private OAuth2Authentication principal;

	public AccessCardDto(OAuth2AccessToken oauth, OAuth2Authentication principal) {
		super();
		this.oauth = oauth;
		this.principal = principal;
	}

	public AccessCardDto() {
		super();
	}

	public OAuth2AccessToken getOauth() {
		return oauth;
	}

	public void setOauth(OAuth2AccessToken oauth) {
		this.oauth = oauth;
	}

	public OAuth2Authentication getPrincipal() {
		return principal;
	}

	public void setPrincipal(OAuth2Authentication principal) {
		this.principal = principal;
	}

}
