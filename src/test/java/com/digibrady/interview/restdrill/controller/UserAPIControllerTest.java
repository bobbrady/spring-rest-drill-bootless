package com.digibrady.interview.restdrill.controller;

import java.util.ArrayList;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.digibrady.interview.restdrill.data.IUserRepository;
import com.digibrady.interview.restdrill.model.User;

public class UserAPIControllerTest {

	private MockMvc mockMvc;

	@Mock
	private IUserRepository mockUserRepo;

	@InjectMocks
	private UserAPIController controller;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
	}

	@Test
	public void getAllUsers() throws Exception {
		List<User> mockUsers = createMockUsers(10);
		Mockito.when(mockUserRepo.getUsers()).thenReturn(mockUsers);
		mockMvc.perform(MockMvcRequestBuilders.get("/api/user"))
		    .andExpect(MockMvcResultMatchers.status().isOk())
		    .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
		    .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(10)))
		    .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.is(1)));
	}

	private List<User> createMockUsers(int count) {
		List<User> users = new ArrayList<>();
		for (int idx = 1; idx <= count; idx++) {
			User user = new User(idx, "name-" + idx, "job", idx * 40);
			users.add(user);
		}
		return users;
	}

}
