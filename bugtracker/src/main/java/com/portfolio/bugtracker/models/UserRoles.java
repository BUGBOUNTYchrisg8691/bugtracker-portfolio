package com.portfolio.bugtracker.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "userRoles")
@IdClass(UserRolesId.class)
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode
public class UserRoles implements Serializable
{
	@Id
	@NonNull
	@NotNull
	@ManyToOne
	@JoinColumn(name = "userid")
	@JsonIgnoreProperties(value = {"roles"}, allowSetters = true)
	private User user;
	
	@Id
	@NonNull
	@NotNull
	@ManyToOne
	@JoinColumn(name = "roleid")
	@JsonIgnoreProperties(value = {"users"}, allowSetters = true)
	private Role role;
}
