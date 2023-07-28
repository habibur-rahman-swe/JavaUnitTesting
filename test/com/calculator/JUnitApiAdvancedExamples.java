package com.calculator;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.condition.DisabledForJreRange;
import org.junit.jupiter.api.condition.DisabledIf;
import org.junit.jupiter.api.condition.DisabledIfEnvironmentVariable;
import org.junit.jupiter.api.condition.DisabledIfSystemProperty;
import org.junit.jupiter.api.condition.DisabledOnOs;
import org.junit.jupiter.api.condition.EnabledForJreRange;
import org.junit.jupiter.api.condition.EnabledIf;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.junit.jupiter.api.condition.EnabledOnJre;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.JRE;
import org.junit.jupiter.api.condition.OS;
import org.junit.jupiter.api.io.TempDir;

@Tag("production")

@TestMethodOrder(OrderAnnotation.class)
class JUnitApiAdvancedExamples {

	private  JRE JAVA_8 = JRE.JAVA_8;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void test() {
		fail("Not yet implemented");
	}
	
	@Test
	@Tag("production")
	void someTestForProdEnv() {
		
	}
	
	@Test
	@Order(1)
	void order1() {
		
	}
	
	@Test
	@Order(2)
	void order2() {
		
	}
	
	@Test
	@Order(3)
	void order3() {
		
	}
	
	@Test
	@EnabledOnOs(OS.MAC)
	void onlyOnMacOs() {
		
	}
	
	@Test
	@EnabledOnOs({OS.LINUX, OS.MAC})
	void onLinuxOrMac() {
		
	}
	
	@Test
	@DisabledOnOs(OS.WINDOWS)
	void notOnWindows() {
		
	}
	
	@Test
	@EnabledOnJre(JRE.JAVA_8)
	void onlyeOnJava8() {
		
	}
	
	@Test
	@EnabledOnJre({JRE.JAVA_9, JRE.JAVA_10})
	void onJava9Or10() {
		
	}
	
	@Test
	@EnabledForJreRange(min = JRE.JAVA_9, max = JRE.JAVA_11)
	void fromJava9to11() {
		
	}
	
	@Test
	@EnabledForJreRange(min = JRE.JAVA_11)
	void fromJava11ToCurrentJavaFeatureNumber() {
		
	}
	
	@Test
	@EnabledForJreRange(max = JRE.JAVA_12) 
	void fromJava12() {
		
	}
	
	@Test
	@DisabledForJreRange(min = JRE.JAVA_9, max = JRE.JAVA_11)
	void notFromJava9to11() {
		
	}
	
	@Test
	@DisabledForJreRange(min = JRE.JAVA_8)
	void onlyFromJava9() {
		
	}
	
	@Test
	@DisabledForJreRange(max = JRE.JAVA_11)
	void notFromJava8to11() {
		
	}
	
//	system properties conditions
	
	@Test
	@EnabledIfSystemProperty(named = "os.arch", matches = ".*64.")
	void onlyOn64BitArchitectures() {
		
	}
	
	@Test
	@DisabledIfSystemProperty(named = "ci-server", matches = "true")
	void notOnCiServer() {
		
	}
	
//	environment variables conditions
	
	@Test
	@EnabledIfEnvironmentVariable(named = "ENV", matches = "staging-server")
	void onlyOnStagingServer() {
		
	}
	
	@Test
	@DisabledIfEnvironmentVariable(named = "ENV", matches = ".*development.*")
	void notOnDeveloperWorkstation() {
		
	}
	
	// custom conditions
	
	@Test
	@EnabledIf("customCondition")
	void enabled() {
		
	}
	
	@Test
	@DisabledIf("customCondition")
	void disabled() {
		
	}
	
	boolean customCondition() {
		return true;
	}
	
//	repeated test
	
	@RepeatedTest(value = 5, name = "{displayName} {currentRepetition} / {totalRepetitions}")
	void repeatedTest() {
		
	}
	
//	extension - tempDir
	
	@Test
	void tempDirExample(@TempDir Path tempDir) throws IOException {
		Path path = tempDir.resolve("test");
		Files.write(path, "some text".getBytes());
		
		assertEquals("some text", Files.readAllLines(path).stream().collect(Collectors.joining()));
		
	}
	
}
