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
import com.fasterxml.jackson.databind.ObjectMapper;

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

	@Test
	public void getUser() throws Exception {
		User mockUser = createMockUsers(1).get(0);
		Mockito.when(mockUserRepo.getUserById(mockUser.getId())).thenReturn(mockUser);

		ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.get("/api/user/" + mockUser.getId()))
		    .andExpect(MockMvcResultMatchers.status().isOk())
		    .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE));

		assertMockUser(actions, mockUser, true);
	}

	@Test
	public void createUser() throws Exception {
		User mockUser = createMockUsers(1).get(0);
		Mockito.when(mockUserRepo.create(Mockito.any(User.class))).thenReturn(mockUser);
		String locHeader = String.format("/api/user/%d", mockUser.getId());

		mockMvc.perform(MockMvcRequestBuilders.post("/api/user").contentType(MediaType.APPLICATION_JSON)
		    .content(asJsonString(mockUser)))
		    .andExpect(MockMvcResultMatchers.status().isCreated())
		    .andExpect(MockMvcResultMatchers.header().string("location", Matchers.containsString(locHeader)));
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
		for (User mockUser : mockUsers) {
			assertMockUser(actions, mockUser, false);
		}
	}

	private void assertMockUser(ResultActions actions, User mockUser, boolean isSingle) throws Exception {
		double salary = mockUser.getId() * 40;
		String path = null;
		if (isSingle) {
			path = "$";
		} else {
			path = String.format("$[%d]", (mockUser.getId() - 1));
		}
		actions.andExpect(MockMvcResultMatchers.jsonPath(path + ".id", Matchers.is(mockUser.getId())));
		actions.andExpect(MockMvcResultMatchers.jsonPath(path + ".job", Matchers.is("job-" + mockUser.getId())));
		actions.andExpect(MockMvcResultMatchers.jsonPath(path + ".salary", Matchers.is(salary)));

	}

	public static String asJsonString(final Object obj) {
		try {
			final ObjectMapper mapper = new ObjectMapper();
			return mapper.writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
