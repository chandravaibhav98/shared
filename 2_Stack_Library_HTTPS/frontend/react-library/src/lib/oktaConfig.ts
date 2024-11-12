export const oktaConfig = {
	clientId: "0oak525tvpFoPt7B35d7",
	issuer: "https://dev-37260923.okta.com/oauth2/default",
	redirectUri: "https://localhost:3000/login/callback",
	scopes: ["openid", "profile", "email"],
	pkce: true,
	disableHttpsCheck: true,
};
