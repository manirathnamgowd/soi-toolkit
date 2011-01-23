package org.soitoolkit.tools.generator.plugin.generator;

import static org.junit.Assert.assertEquals;
import static org.soitoolkit.tools.generator.plugin.util.SystemUtil.BUILD_COMMAND;
import static org.soitoolkit.tools.generator.plugin.model.enums.MuleVersionEnum.MULE_2_2_5;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.soitoolkit.tools.generator.plugin.model.enums.MuleVersionEnum;
import org.soitoolkit.tools.generator.plugin.model.enums.TransportEnum;
import org.soitoolkit.tools.generator.plugin.util.PreferencesUtil;
import org.soitoolkit.tools.generator.plugin.util.SystemUtil;

public class IntegrationComponentGeneratorTest {

	private static final MuleVersionEnum MULE_VERSION = MULE_2_2_5;
	private static final List<TransportEnum> TRANSPORTS = new ArrayList<TransportEnum>();
	private static final String TEST_OUT_FOLDER = PreferencesUtil.getDefaultRootFolder() + "/jUnitTests";
	private static final String PROJECT = "ordermgm";	
	private static final String PROJECT_FOLDER = TEST_OUT_FOLDER + "/" + PROJECT;
	private static final String MAVEN_HOME = PreferencesUtil.getMavenHome();

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		TRANSPORTS.add(TransportEnum.JMS);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGenerateOrderMgm() throws IOException {
		SystemUtil.delDirs(PROJECT_FOLDER);
		assertEquals(0, SystemUtil.countFiles(PROJECT_FOLDER));
				
		new IntegrationComponentGenerator(System.out, "org.soitoolkit.refapps.dealernetwork", PROJECT, "1.0-SNAPSHOT", MULE_VERSION, TRANSPORTS, TEST_OUT_FOLDER).startGenerator();
		assertEquals("Missmatch in expected number of created files and folders", 60, SystemUtil.countFiles(PROJECT_FOLDER));
		
		SystemUtil.executeCommand(MAVEN_HOME + "/bin/" + BUILD_COMMAND, PROJECT_FOLDER + "/trunk");
	}

	@Test
	public void testGenerateShipping() throws IOException {
		SystemUtil.delDirs(TEST_OUT_FOLDER + "/shipping");
		assertEquals(0, SystemUtil.countFiles(TEST_OUT_FOLDER + "/shipping"));

		new IntegrationComponentGenerator(System.out, "org.soitoolkit.refapps.dealernetwork", "shipping", "1.0-SNAPSHOT", MULE_VERSION, TRANSPORTS, TEST_OUT_FOLDER).startGenerator();
		assertEquals("Missmatch in expected number of created files and folders", 60, SystemUtil.countFiles(TEST_OUT_FOLDER + "/shipping"));

		SystemUtil.executeCommand(MAVEN_HOME + "/bin/" + BUILD_COMMAND, TEST_OUT_FOLDER + "/shipping/trunk");
	}

	@Test
	public void testGenerateVgrPicsara2melior() throws IOException {
		String grp = "se.vgregion.pilot";
		String name = "picsara2melior-sas-003";
		
		SystemUtil.delDirs(TEST_OUT_FOLDER + "/" + name);
		assertEquals(0, SystemUtil.countFiles(TEST_OUT_FOLDER + "/" + name));

		new IntegrationComponentGenerator(System.out, grp, name, "1.0-SNAPSHOT", MULE_VERSION, TRANSPORTS, TEST_OUT_FOLDER).startGenerator();
		assertEquals("Missmatch in expected number of created files and folders", 58, SystemUtil.countFiles(TEST_OUT_FOLDER + "/" + name));

		SystemUtil.executeCommand(MAVEN_HOME + "/bin/" + BUILD_COMMAND, TEST_OUT_FOLDER + "/" + name + "/trunk");
	}

	@Test
	public void testGenerateVfSveFasktura() throws IOException {
		String grp = "se.volvofinans.infobus.faktura";
		String name = "faktura3";
		
		SystemUtil.delDirs(TEST_OUT_FOLDER + "/" + name);
		assertEquals(0, SystemUtil.countFiles(TEST_OUT_FOLDER + "/" + name));

		new IntegrationComponentGenerator(System.out, grp, name, "1.0-SNAPSHOT", MULE_VERSION, TRANSPORTS, TEST_OUT_FOLDER).startGenerator();
		assertEquals("Missmatch in expected number of created files and folders", 60, SystemUtil.countFiles(TEST_OUT_FOLDER + "/" + name));

		SystemUtil.executeCommand(MAVEN_HOME + "/bin/" + BUILD_COMMAND, TEST_OUT_FOLDER + "/" + name + "/trunk");
	}
}