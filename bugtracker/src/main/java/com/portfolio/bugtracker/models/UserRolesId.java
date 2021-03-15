package com.portfolio.bugtracker.models;

import lombok.*;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode
@Embeddable
public class UserRolesId implements Serializable
{
	@NonNull
	private long user;
	
	@NonNull
	private long role;
}
