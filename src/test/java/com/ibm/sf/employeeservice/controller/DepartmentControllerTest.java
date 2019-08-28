package com.ibm.sf.employeeservice.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.eq;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibm.sf.employeeservice.exception.DepartmentAlreadyExistsException;
import com.ibm.sf.employeeservice.exception.DepartmentNotFoundException;
import com.ibm.sf.employeeservice.model.Department;
import com.ibm.sf.employeeservice.service.DepartmentService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class DepartmentControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private DepartmentService deptService;
	
	@InjectMocks
	private DepartmentController deptController;
	
	private Department dept;
	
	@Before
	public void setUp() {

		MockitoAnnotations.initMocks(this);

		mockMvc = MockMvcBuilders.standaloneSetup(deptController).build();
		dept = new Department();

		dept.setId("DEPT1");
		dept.setName("Admin");
	}

	@Test
	public void testcreateDepartment() throws Exception { //
		
		when(deptService.createDepartment(any())).thenReturn(dept);
		mockMvc.perform(
				post("/department/create/department",dept)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonToString(dept)))
				.andExpect(MockMvcResultMatchers.status().isCreated()).andDo(print());
		
		verify(deptService, times(1)).createDepartment(any());

	}
	
	@Test
	public void testcreateDepartmentFailed() throws Exception  { //
		
		when(deptService.createDepartment(any())).thenThrow(DepartmentAlreadyExistsException.class);
		mockMvc.perform(
				post("/department/create/department",dept)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonToString(dept)))
				.andExpect(MockMvcResultMatchers.status().isConflict()).andDo(print());

		verify(deptService, times(1)).createDepartment(any());

	}
	
	@Test
    public void testupdateDepartment() throws Exception {
        dept.setName("Finance");
        when(deptService.updateDepartment(eq(dept.getId()), any())).thenReturn(dept);
        mockMvc.perform(
        		put("/department/update/department/DEPT1")
                .contentType(MediaType.APPLICATION_JSON).content(jsonToString(dept)))
        		.andExpect(MockMvcResultMatchers.status().isOk()).andDo(print());
      }
	
	@Test
    public void testupdateDepartmentFailure() throws Exception {
		dept.setName("Finance");
            when(deptService.updateDepartment(eq(dept.getId()), any())).thenThrow(DepartmentNotFoundException.class);
            mockMvc.perform(put("/department/update/department/DEPT1")
                    .contentType(MediaType.APPLICATION_JSON).content(jsonToString(dept)))
            .andExpect(MockMvcResultMatchers.status().isNotFound()).andDo(print());
                    
    }
	
	@Test
    public void testdeleteDepartment() throws Exception {
        when(deptService.deleteDepartment("DEPT1")).thenReturn(true);
        mockMvc.perform(delete("/department/delete/DEPT1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());

    }
	
	@Test
    public void testdeleteDepartmentFailure() throws Exception {
        when(deptService.deleteDepartment("DEPT1")).thenThrow(DepartmentNotFoundException.class);
        mockMvc.perform(delete("/department/delete/DEPT1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andDo(MockMvcResultHandlers.print());

    }
	
	@Test
    public void getDepartmentSuccess() throws Exception {

        when(deptService.getDepartment("DEPT1")).thenReturn(dept);
        mockMvc.perform(get("/department/getdepartment/DEPT1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }
	
	@Test
    public void getDepartmentFAilure() throws Exception {

        when(deptService.getDepartment("DEPT1")).thenThrow(DepartmentNotFoundException.class);
        mockMvc.perform(get("/department/getdepartment/DEPT1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andDo(MockMvcResultHandlers.print());
    }
	
	private static String jsonToString(final Object obj) throws Exception {
		String result;

		try {
			final ObjectMapper mapper = new ObjectMapper();
			final String jsonContent = mapper.writeValueAsString(obj);
			result = jsonContent;
		} catch (Exception e) {
			result = "error in Json processing";
		}
		return result;
	}
	
}
