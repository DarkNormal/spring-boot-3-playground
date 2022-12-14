package com.in28minutes.springboot.myfirstwebapp.todo;

import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
@SessionAttributes("name")
public class TodoControllerJpa {

	public TodoControllerJpa(TodoRepository todoRepository) {
		super();
		this.todoRepository = todoRepository;
	}

	private final TodoRepository todoRepository;

	@RequestMapping("list-todos")
	public String listAllTodos(ModelMap model) {
		String username = getLoggedInUsername();
		List<Todo> todos = todoRepository.findByUsername(username);
		model.addAttribute("todos", todos);

		return "listTodos";
	}

	//GET, POST
	@GetMapping(value = "add-todo")
	public String showNewTodoPage(ModelMap model) {
		String username = getLoggedInUsername();
		Todo todo = new Todo(0, username, "", LocalDate.now().plusYears(1), false);
		model.put("todo", todo);
		return "todo";
	}

	@PostMapping(value = "add-todo")
	public String addNewTodo(ModelMap model, @Valid Todo todo, BindingResult result) {
		if (result.hasErrors()) {
			return "todo";
		}
		String username = getLoggedInUsername();
		todo.setUsername(username);
		todoRepository.save(todo);
		return "redirect:list-todos";
	}

	@RequestMapping("delete-todo")
	public String deleteTodo(@RequestParam int id) {
		todoRepository.deleteById(id);
		return "redirect:list-todos";

	}

	@GetMapping(value = "update-todo")
	public String showUpdateTodoPage(@RequestParam int id, ModelMap model) {
		Optional<Todo> todo = todoRepository.findById(id);
		if (todo.isPresent()) {
			model.addAttribute("todo", todo);
		}
		return "todo";
	}

	@PostMapping(value = "update-todo")
	public String updateTodo(ModelMap model, @Valid Todo todo, BindingResult result) {
		if (result.hasErrors()) {
			return "todo";
		}
		String username = getLoggedInUsername();
		todo.setUsername(username);
		todoRepository.save(todo);
		return "redirect:list-todos";
	}

	private static String getLoggedInUsername() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return authentication.getName();
	}

}