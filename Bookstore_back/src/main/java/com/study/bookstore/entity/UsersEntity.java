package com.study.bookstore.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//* users 테이블 엔터티 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name="users")
@Table(name="users")
public class UsersEntity {

	@Id
	private String userId;
	private String userName;
	private String userEmail;
	private String userPassword;
	
	// 데이터 삽입을 위한 생성자 (회원가입)
}