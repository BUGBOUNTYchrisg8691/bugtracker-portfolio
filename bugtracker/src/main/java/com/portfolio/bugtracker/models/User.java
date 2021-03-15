package com.portfolio.bugtracker.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.w3c.dom.stylesheets.LinkStyle;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class User
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long userid;
	
	@NonNull
	@NotNull
	@Column(unique = true)
	private String username;
	
	@NonNull
	@NotNull
	@Column
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String password;
	
	@NonNull
	@NotNull
	@Column(unique = true)
	private String email;
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnoreProperties(value = {"user"}, allowSetters = true)
	private Set<UserRoles> roles = new HashSet<>();
	
	public void setPasswordEncrypt(String password)
	{
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		setPassword(encoder.encode(password));
	}
	
	@JsonIgnore
	public List<SimpleGrantedAuthority> getAuthority()
	{
		List<SimpleGrantedAuthority> authList = new ArrayList<>();
		
		for (UserRoles r : getRoles())
		{
			String role = "ROLE_" + r.getRole().getType().toUpperCase();
			authList.add(new SimpleGrantedAuthority(role));
		}
		
		return authList;
	}
}
