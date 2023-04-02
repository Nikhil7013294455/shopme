package com.shopme.admin.user;

import java.util.List;
import java.util.NoSuchElementException;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.shopme.common.entity.Role;
import com.shopme.common.entity.User;

@Service
@Transactional
public class UserService {
	public static final int USERS_PER_PAGE = 4;
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private RoleRepository roleRepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public List<User> listAll() {
		return (List<User>) userRepo.findAll();
	}
	
	public Page<User> listByPage(int pageNum,String keyWord) {
		
		
		
		Pageable pageable = PageRequest.of(pageNum-1, USERS_PER_PAGE);
		
		if(keyWord!=null) {
			return userRepo.findAll(keyWord,pageable);
		}
		
		return userRepo.findAll(pageable);
	}
	
	public List<Role> listRoles() {
		return (List<Role>) roleRepo.findAll();
	}

	public User save(User user) {
			boolean isUpdatingUser = (user.getId()!=null);
			
			if(isUpdatingUser) {
				User existingUser = userRepo.findById(user.getId()).get();
				if(user.getPassword().isEmpty()) {
					user.setPassword(existingUser.getPassword());
				}
			} else {
				encodePassword(user);
			}
					
		     encodePassword(user);
		     return userRepo.save(user);
		     
		     
		
	}
	
	private void encodePassword(User user) {
		String encodedPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
		
	}
	
	public boolean isEmailUnique(Integer id,String email) {
		User userByEmail = userRepo.getUserByEmail(email);
		if(userByEmail==null) return true;
		
		boolean isCreatingNew = (id==null);
		
		if(isCreatingNew) {
			if(userByEmail!=null) return false;
		} else {
			if(userByEmail.getId()!=id) {
				return false;
			}
		}
		
		return true;
		
	}

	public User get(Integer id) throws UserNotFoundException {
		
		try {
			return userRepo.findById(id).get();
		}
		catch(NoSuchElementException ex) {
			throw new UserNotFoundException("Could not find any user with id"+id);
		}
	}
	
	public void delete(Integer id) throws UserNotFoundException {
		Long countById = userRepo.countById(id);
		if(countById == null || countById==0) {
			throw new UserNotFoundException("Could not find any user with given id " + id);
		}
		userRepo.deleteById(id);
	}
	
	public void updateUserEnabledStatus(Integer id,boolean enabled) {
		userRepo.updateEnabledStatus(id, enabled);
	}

	public User getByEmail(String email) {
		return userRepo.getUserByEmail(email);
		
	}
	
	public User updateAccount(User userInForm) {
		User userInDB = userRepo.findById(userInForm.getId()).get();
		if(!userInForm.getPassword().isEmpty()) {
			userInDB.setPassword(userInForm.getPassword());
		}
		
		if(!userInForm.getPhotos().isEmpty()) {
			userInDB.setPhotos(userInForm.getPhotos());
		}
		
		userInDB.setFirtsName(userInForm.getFirtsName());
		
		userInDB.setLastName(userInForm.getLastName());
		
		
		
		
		return userRepo.save(userInDB);
	}
	
}