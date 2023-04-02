package com.shopme.admin.category;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.shopme.common.entity.Category;

@DataJpaTest(showSql = false)
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class CategoryRepositoryTests {
	
	@Autowired
	private CategoryRepository repo;
	
	@Test
	public void testCreateRootCategory() {
		Category ct = new Category("Electronics");
		Category savedCategory = repo.save(ct);
		
		assertThat(savedCategory.getId()).isGreaterThan(0);
	}
	
	@Test
	public void testCreateSubCategory() {
		Category parent = new Category(7);
		Category ct = new Category("iPhone",parent);
		Category savedCategory = repo.save(ct);
		
		assertThat(savedCategory.getId()).isGreaterThan(0);
	}
	
	@Test
	public void testGetCategory() {
		Category cat = repo.findById(1).get();
		System.out.println(cat.getName());
		
		Set<Category> children = cat.getChildren();
		
		for(Category c:children) {
			System.out.println(c.getName());
		}
		
		assertThat(children.size()).isGreaterThan(0);

		
	}
	
	@Test
	public void testListRootCategories() {
		List<Category> rc = repo.findRootCategories();
		rc.forEach(cat -> System.out.println(cat.getName()));
	}
	
	@Test 
	public void testFindByName() {
		Category cat = repo.findByName("Computers");
		assertThat(cat).isNotNull();
		assertThat(cat.getName()).isEqualTo("Computers");
	}
	
	
	
}
