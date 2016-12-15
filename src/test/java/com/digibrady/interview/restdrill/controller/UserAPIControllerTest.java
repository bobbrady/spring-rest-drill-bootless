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
import org.springframework.test.web.servlet.ResultActions;
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

		ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.get("/api/user"))
		    .andExpect(MockMvcResultMatchers.status().isOk())
		    .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
		    .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(10)));

		assertMockUsers(actions, mockUsers);
	}

	private List<User> createMockUsers(int count) {
		List<User> users = new ArrayList<>();
		for (int idx = 1; idx <= count; idx++) {
			double salary = idx * 40;
			User user = new User(idx, "name-" + idx, "job-" + idx, salary);
			users.add(user);
		}
		return users;
	}

	private void assertMockUsers(ResultActions actions, List<User> mockUsers) throws Exception {
		for (int idx = 0; idx < mockUsers.size(); idx++) {
			double salary = (idx + 1) * 40;
			actions.andExpect(MockMvcResultMatchers.jsonPath("$[" + idx + "].id", Matchers.is(idx + 1)));
			actions.andExpect(MockMvcResultMatchers.jsonPath("$[" + idx + "].job", Matchers.is("job-" + (idx + 1))));
			actions.andExpect(MockMvcResultMatchers.jsonPath("$[" + idx + "].salary", Matchers.is(salary)));
		}
	}

}
