package com.shopme.admin.user;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;

import com.shopme.common.entity.Role;
import com.shopme.common.entity.User;

@DataJpaTest(showSql = false)
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class UserRepositoryTests {
	
	@Autowired
	private UserRepository repo;
	
	@Autowired
	private TestEntityManager entityManager;
	
	@Test
	public void testCreateUser() {
		Role roleAdmin = entityManager.find(Role.class,1);
		User suppy = new User("supradeepch@gmail.com","flipkart2819","suppy","ch");
		suppy.addRole(roleAdmin);
		User savedUser = repo.save(suppy);
		assertThat(savedUser.getId()).isGreaterThan(0);
		
	}
	
	@Test	
	public void testCreateNewUserWithTwoRoles() {
		/*User userRavi = new User("ravi@gmail.com","ravi2022","ravi","kumar");
		
		Role editor = new Role(3);
		Role assistant = new Role(5);
		
		userRavi.addRole(editor);
		userRavi.addRole(assistant);
		
		User savedUser = repo.save(userRavi);
		assertThat(savedUser.getId()).isGreaterThan(0);*/
		
		User userKunjan = new User("kunujan@gmail.com","kunujan2022","ravi","kumar");
		
		
		Role roleEditor = new Role(3);
		Role roleAssistant1 = new Role(5);
		Role roleAssistant2 = new Role(5);
		 
		userKunjan.addRole(roleEditor);
		userKunjan.addRole(roleAssistant1);
		userKunjan.addRole(roleAssistant2);
		
		User savedUser = repo.save(userKunjan);
		assertThat(savedUser.getId()).isGreaterThan(0);
		
	}
	
	@Test
	public void testListAllTestUsers() {
		Iterable<User>listUsers = repo.findAll();
		listUsers.forEach(user -> System.out.println(user));
		
	}
	
	@Test 
	public void testGetUserByEmail() {
		String email = "supradeepch@gmail.com";
		User user = repo.getUserByEmail(email);
		assertThat(user).isNotNull();
		
	}
	
	
	@Test
	public void testCountById() {
		Integer id = 1;
		Long countById =  repo.countById(id);
		assertThat(countById).isNotNull().isGreaterThan(0);
	}
	
	@Test
	public void testDisableUser() {
		Integer id  = 4;
		repo.updateEnabledStatus(id, false);
	}
	
	@Test
	public void testEnableUser() {
		Integer id  = 4;
		repo.updateEnabledStatus(id, true);
	}
	
	@Test
	public void testListFirstPage() {
		int pn = 0;
		int ps = 4;
		Pageable pageable =  PageRequest.of(pn, ps);
		Page<User>page = repo.findAll(pageable);
		List<User> listUsers = page.getContent();
		
		listUsers.forEach(user -> System.out.println(user));
		
		assertThat(listUsers.size()).isEqualTo(ps);
		
	}
	
	@Test 
	public void testSearchUsers() {
		String keyWord = "bruce";
		int pn = 0;
		int ps = 4;
		Pageable pageable =  PageRequest.of(pn, ps);
		Page<User>page = repo.findAll(keyWord,pageable);
		List<User> listUsers = page.getContent();
		
		listUsers.forEach(user -> System.out.println(user));
		
		assertThat(listUsers.size()).isGreaterThan(0);
	}
	
}
