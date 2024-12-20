package com.study.bookstore.dto.request.auth;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//* 아이디 중복 확인을 위한 요청 dto */

@Getter
@Setter
@NoArgsConstructor
public class IdCheckRequestDto {

	@NotBlank
	@Length(max=20)
	private String userId;
	
}
