package com.portfolio.bugtracker.controllers;

import com.portfolio.bugtracker.models.LoginCreds;
import com.portfolio.bugtracker.models.User;
import com.portfolio.bugtracker.models.UserMinimum;
import com.portfolio.bugtracker.models.UserRoles;
import com.portfolio.bugtracker.services.RoleService;
import com.portfolio.bugtracker.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * The class allows access to endpoints that are open to all users regardless of authentication status.
 * Its most important function is to allow a person to create their own username
 */
@RestController
public class AuthController
{
	/**
	 * A method in this controller adds a new user to the application so needs access to User Services to do this.
	 */
	@Autowired
	private UserService userService;
	
	/**
	 * A method in this controller adds a new user to the application with the role User so needs access to Role Services to do this.
	 */
	@Autowired
	private RoleService roleService;
	
	/**
	 * Connect to the Token store so the application can remove the token
	 */
	@Autowired
	private TokenStore tokenStore;
	
	/**
	 * This endpoint always anyone to create an account with the default role of USER. That role is hardcoded in this method.
	 *
	 * @param httpServletRequest the request that comes in for creating the new user
	 * @param newminuser         A special minimum set of data that is needed to create a new user
	 * @return The token access and other relevent data to token access. Status of CREATED. The location header to look up the new user.
	 * @throws URISyntaxException we create some URIs during this method. If anything goes wrong with that creation, an exception is thrown.
	 */
	@PostMapping(value = "/register",
	             consumes = {"application/json"},
	             produces = {"application/json"})
	public ResponseEntity<?> registerSelf(HttpServletRequest httpServletRequest,
			@Valid @RequestBody UserMinimum newminuser) throws URISyntaxException
	{
		// Create the user
		User newuser = new User();
		
		newuser.setUsername(newminuser.getUsername());
		newuser.setPassword(newminuser.getPassword());
		
		// add the default role of user
		Set<UserRoles> newRoles = new HashSet<>();
		newRoles.add(new UserRoles(newuser, roleService.findRoleByType("USER")));
		newuser.setRoles(newRoles);
		
		newuser = userService.save(newuser);
		
		// set the location header for the newly created resource
		// The location comes from a different controller!
		HttpHeaders responseHeaders = new HttpHeaders();
		URI newUserURI = ServletUriComponentsBuilder.fromUriString(
				httpServletRequest.getServerName() + ":" + httpServletRequest.getLocalPort() +
						"/users/user/{userId}").buildAndExpand(newuser.getUserid()).toUri();
		responseHeaders.setLocation(newUserURI);
		
		// return the access token
		// To get the access token, surf to the endpoint /login (which is always on the server where this is running)
		// just as if a client had done this.
		RestTemplate restTemplate = new RestTemplate();
		String requestURI = "http://localhost" + ":" + httpServletRequest.getLocalPort() + "/oauth-token";
		
		List<MediaType> acceptableMediaTypes = new ArrayList<>();
		acceptableMediaTypes.add(MediaType.APPLICATION_JSON);
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		headers.setAccept(acceptableMediaTypes);
		headers.setBasicAuth(System.getenv("OAUTHCLIENTID"),
				System.getenv("OAUTHCLIENTSECRET"));
		
		MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
		map.add("grant_type", "password");
		map.add("scope", "read write trust");
		map.add("username", newminuser.getUsername());
		map.add("password", newminuser.getPassword());
		
		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
		
		String theToken = restTemplate.postForObject(requestURI, request, String.class);
		
		return new ResponseEntity<>(theToken, responseHeaders, HttpStatus.CREATED);
	}
	
	/**
	 * This endpoint always anyone to create an account with the default role of USER. That role is hardcoded in this method.
	 *
	 * @param httpServletRequest the request that comes in for creating the new user
	 * @param loginCreds the login credentials of the user trying to login
	 * @return The token access and other relevent data to token access. Status of CREATED. The location header to look up the new user.
	 * @throws URISyntaxException we create some URIs during this method. If anything goes wrong with that creation, an exception is thrown.
	 */
	@PostMapping(value = "/login",
	             consumes = {"application/json"},
	             produces = {"application/json"})
	public ResponseEntity<?> loginSelf(HttpServletRequest httpServletRequest,
			@Valid @RequestBody LoginCreds loginCreds) throws URISyntaxException
	{
		// find user in database
		User user = userService.findUserByUsername(loginCreds.getUsername());
		
		// set the location header for the newly created resource
		// The location comes from a different controller!
		HttpHeaders responseHeaders = new HttpHeaders();
		URI newUserURI = ServletUriComponentsBuilder.fromUriString(
				httpServletRequest.getServerName() + ":" + httpServletRequest.getLocalPort() +
						"/users/user/{userId}").buildAndExpand(user.getUserid()).toUri();
		responseHeaders.setLocation(newUserURI);
		
		// return the access token
		// To get the access token, surf to the endpoint /login (which is always on the server where this is running)
		// just as if a client had done this.
		RestTemplate restTemplate = new RestTemplate();
		String requestURI = "http://localhost" + ":" + httpServletRequest.getLocalPort() + "/oauth-token";
		
		List<MediaType> acceptableMediaTypes = new ArrayList<>();
		acceptableMediaTypes.add(MediaType.APPLICATION_JSON);
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		headers.setAccept(acceptableMediaTypes);
		headers.setBasicAuth(System.getenv("OAUTHCLIENTID"), System.getenv("OAUTHCLIENTSECRET"));
		
		MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
		map.add("grant_type", "password");
		map.add("scope", "read write trust");
		map.add("username", loginCreds.getUsername());
		map.add("password", loginCreds.getPassword());
		
		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
		
		String theToken = restTemplate.postForObject(requestURI, request, String.class);
		
		return new ResponseEntity<>(theToken, responseHeaders, HttpStatus.OK);
	}
	
	/**
	 * Removes the token for the signed on user. The signed user will lose access to the application. They would have to sign on again.
	 *
	 * <br>Example: <a href="http://localhost:2019/logout">http://localhost:2019/logout</a>
	 *
	 * @param request the Http request from which we find the authorization header which includes the token to be removed
	 */
	// yes, both endpoints are mapped to the same Java method! So, either one will work.
	@GetMapping(value = {"/oauth/revoke-token", "/logout"},
	            produces = "application/json")
	public ResponseEntity<?> logoutSelf(HttpServletRequest request)
	{
		String authHeader = request.getHeader("Authorization");
		if (authHeader != null)
		{
			// find the token
			String tokenValue = authHeader.replace("Bearer",
					"")
					.trim();
			// and remove it!
			OAuth2AccessToken accessToken = tokenStore.readAccessToken(tokenValue);
			tokenStore.removeAccessToken(accessToken);
		}
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
}