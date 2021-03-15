package com.portfolio.bugtracker.models;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "roles")
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class Role
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long roleid;
	
	@NonNull
	@NotNull
	private String type;
}
