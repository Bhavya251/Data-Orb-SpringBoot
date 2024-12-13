package com.dataorb;

import com.dataorb.api.DataOrbController;
import com.dataorb.api.impl.DataImplementation;
import com.dataorb.api.impl.DataService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
class DataOrbAssignmentApplicationTests {


	private static DataOrbController controller;
	private Object response;

	@BeforeAll
	static void setUp() throws IOException {

		controller = new DataOrbController();

		MultipartFile file = createMockFile("sample1.txt", """
						1,emp101,Bill,Gates,Software Engineer,ONBOARD,01-01-2022,01-01-2022,"ONboarding"
						2,emp102,Steve,Jobs,Architect,ONBOARD,01-02-2022,01-02-2022,"ONboarding"
						3,emp101,SALARY,3000,01-01-2022,"Salary"
						4,emp102,SALARY,4000,01-02-2022,"Salary"
						"""
				);
		List<MultipartFile> files = new ArrayList<>();
		files.add(file);

		controller.sendRecords(files);
	}

	@BeforeEach
	void init() {
		DataService dataService = new DataImplementation();
		response = dataService.getRecords();
	}


	@Test
	void testTotalEmployees() {

		// Assert
		assertTrue(response.toString().contains("Total number of employees"));
	}

	@Test
	void testMonthlyJoins() {
		assertTrue(response.toString().contains("Monthly Joins"));
	}

	@Test
	void testMonthlyExits() {
		assertTrue(response.toString().contains("Monthly Exits"));
	}

	@Test
	void testMonthlySalary() {
		assertTrue(response.toString().contains("Monthly Salary"));
	}

	@Test
	void testEmployeeFinancialReport() {
		assertTrue(response.toString().contains("Employee Financial Report"));
	}

	@Test
	void testYearlyFinancialReport() {
		assertTrue(response.toString().contains("Yearly Financial Report"));
	}

	private static MultipartFile createMockFile(String fileName, String content) throws IOException {
		File file = new File(fileName);
		try (FileOutputStream fos = new FileOutputStream(file)) {
			fos.write(content.getBytes());
		}

		FileInputStream fis = new FileInputStream(file);
		MultipartFile multipartFile = mock(MultipartFile.class);
		when(multipartFile.getOriginalFilename()).thenReturn(fileName);
		when(multipartFile.getInputStream()).thenReturn(fis);
		when(multipartFile.getBytes()).thenReturn(content.getBytes());
		return multipartFile;
	}

}
