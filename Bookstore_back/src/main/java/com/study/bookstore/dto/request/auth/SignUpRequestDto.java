package com.study.bookstore.dto.request.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//* 회원가입 요청을 하기 위한 dto */

@Getter
@Setter
@NoArgsConstructor
public class SignUpRequestDto {

	@NotBlank
	private String userName;
	@NotBlank
	private String userId;
	@NotBlank
	@Pattern(regexp="^(?=.*[a-zA-Z])(?=.*[0-9]).{8,13}$")
	private String userPassword;
	@NotBlank
	@Email
	private String userEmail;
	
}
