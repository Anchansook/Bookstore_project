package com.study.bookstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.study.bookstore.entity.UsersEntity;

//* users 리포지토리 */

@Repository
public interface UsersRepository extends JpaRepository<UsersEntity, String> {

	// 아이디 중복 확인
	boolean existsByUserId(String userId);
	
}
