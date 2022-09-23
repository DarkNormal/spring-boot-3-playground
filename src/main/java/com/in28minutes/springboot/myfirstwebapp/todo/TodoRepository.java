package com.in28minutes.springboot.myfirstwebapp.todo;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * TodoRepository handles ...
 * <p>
 * $LastChangedDate$<br>
 * $LastChangedBy$<br>
 * $Revision$<br>
 * </p>
 *
 * @author Mark Lordan
 */
public interface TodoRepository extends JpaRepository<Todo, Integer> {

	List<Todo> findByUsername(final String username);
}
