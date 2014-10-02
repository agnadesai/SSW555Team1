/**
 * 
 */
package jUnit;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.TreeMap;

import gedcom.GEDCOMELine;
import gedcom.GEDCOMParser;
import gedcom.GEDCOMTag;
import main.Family;
import main.FindErrorAnomaly;
import main.Individual;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class FamilyTest {
	
	final static String ID_FAMILY = "123";

	final static String ID_CHILD = "234";
	final static Individual INDIVIDUAL_CHILD = new Individual(ID_CHILD);
	
	final static String ID_HUSBAND = "999";
	final static Individual INDIVIDUAL_HUSBAND = new Individual(ID_HUSBAND);
	
	final static String ID_WIFE = "543";
	final static Individual INDIVIDUAL_WIFE = new Individual(ID_WIFE);
	
	final static String ID_WIFE1 = "699";
	final static Individual INDIVIDUAL_WIFE1 = new Individual(ID_WIFE1);
	
	final static String DATE_MARRIED_STRING = "July 8, 1902";
	final static String DATE_DIVORCED_STRING = "December 17, 1934";
	
	
	final static Family FAMILY = new Family(ID_FAMILY);
	static
	{
		FAMILY.setHusband(INDIVIDUAL_HUSBAND);
		FAMILY.setWife(INDIVIDUAL_WIFE);
		FAMILY.setChild(INDIVIDUAL_CHILD);
		
		try
		{
			FAMILY.setMarried(new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH).parse(DATE_MARRIED_STRING));
			FAMILY.setDivorced(new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH).parse(DATE_DIVORCED_STRING));
		}
		catch (ParseException e)
		{
			e.printStackTrace();
		}
	}

	@Before
	public void setUp() throws Exception {}

	@After
	public void tearDown() throws Exception {}
	
	@Test
	public void testSetIdWithNoValueThrowsException()
	{
		boolean exceptionThrown = false;
		
		try
		{
			Family f = new Family("");
			fail("Setting the id of a family to an empty value did not throw an exception when it should have");
		}
		catch (IllegalArgumentException e)
		{
			exceptionThrown = true;
		}
		
		assertTrue(exceptionThrown);
	}
	
	
	@Test
	public void testAnomalyWhenIndMarriedToMoreThanOnePerson() {
		Individual i1 = new Individual("1");
		Individual i2 = new Individual("2");
		Individual i3 = new Individual("3");
		Family f1 = new Family("1");
		Family f2 = new Family("2");
		
		TreeMap<String, Individual> indTable = new TreeMap<String, Individual>();
		
		f1.setHusband(i1);
		f1.setWife(i2);
		List<String> SpouseFamilyId = new 	ArrayList<String>();
		
		SpouseFamilyId.add(f1.getId());
		i1.setSpouseOfFamilyIDs(SpouseFamilyId);
		i2.setSpouseOfFamilyIDs(SpouseFamilyId);
		
		f1.setMarried(new Date(2002, 6, 5));
		f1.setMarried(new Date(2012, 12, 12));
		
		f2.setHusband(i1);
		f2.setWife(i3);
		SpouseFamilyId.add(f2.getId());
		i1.setSpouseOfFamilyIDs(SpouseFamilyId);
		i2.setSpouseOfFamilyIDs(SpouseFamilyId);
		f2.setMarried(new Date(2012, 6, 5));
		
		TreeMap<String, Family> famTable = new TreeMap<String, Family>();
		famTable.put(f1.getId(), f1);
		famTable.put(f2.getId(), f2);
		
		indTable.put(i1.getId(), i1);
		indTable.put(i2.getId(), i2);
		indTable.put(i3.getId(), i3);
		
		assertTrue( FindErrorAnomaly.hasmorethanonespouse(indTable, famTable, i1) );
	}
	
	@Test
	public void testSetHusbandAsFemaleThrowsException()
	{
		boolean exceptionThrown = false;
		
		try
		{
			Family f = new Family(ID_FAMILY);
			
			Individual female = new Individual("123");
			female.setSex(Individual.SEX_FEMALE);
			
			f.setWife(female);
			f.setHusband(female);
			
			fail("Setting the husband of a family to a female did not throw an exception when it should have");
		}
		catch (IllegalArgumentException e)
		{
			exceptionThrown = true;
		}
		
		assertTrue(exceptionThrown);
	}
	
	@Test
	public void testSetDeadHusbandInMarriageThrowsException()
	{
		boolean exceptionThrown = false;
		
		try
		{
			Family f = new Family(ID_FAMILY);
			f.setMarried(new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH).parse(DATE_MARRIED_STRING));
			
			Individual male = new Individual("123");
			male.setDeath(new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH).parse(DATE_MARRIED_STRING));
			
			f.setHusband(male);
			
			fail("Setting the husband of a family to a dead person did not throw an exception when it should have");
		}
		catch (IllegalArgumentException e)
		{
			exceptionThrown = true;
		} catch (ParseException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertTrue(exceptionThrown);
	}
	
	@Test
	public void testSetWifeAsMaleThrowsException()
	{
		boolean exceptionThrown = false;
		
		try
		{
			Family f = new Family(ID_FAMILY);
			
			Individual male = new Individual("123");
			male.setSex(Individual.SEX_MALE);
			
			f.setHusband(male);
			f.setWife(male);
			
			fail("Setting the wife of a family to a male did not throw an exception when it should have");
		}
		catch (IllegalArgumentException e)
		{
			exceptionThrown = true;
		}
		
		assertTrue(exceptionThrown);
	}
	
	@Test
	public void testSetDeadWifeInMarriageThrowsException()
	{
		boolean exceptionThrown = false;
		
		try
		{
			Family f = new Family(ID_FAMILY);
			f.setMarried(new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH).parse(DATE_MARRIED_STRING));
			
			Individual female = new Individual("123");
			female.setDeath(new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH).parse(DATE_MARRIED_STRING));
			
			f.setWife(female);
			
			fail("Setting the wife of a family to a dead person did not throw an exception when it should have");
		}
		catch (IllegalArgumentException e)
		{
			exceptionThrown = true;
		} catch (ParseException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertTrue(exceptionThrown);
	}

	@Test
	public void testGetIdEqual()
	{
		assertEquals(ID_FAMILY, FAMILY.getId());
	}
	
	@Test
	public void testGetChildIdEqual()
	{
		assertEquals(ID_CHILD, FAMILY.getChild().getId());
	}
	
	@Test
	public void testGetWifeIdEqual()
	{
		assertEquals(ID_WIFE, FAMILY.getWife().getId());
	}
	
	@Test
	public void testGetHusbandIdEqual()
	{
		assertEquals(ID_HUSBAND, FAMILY.getHusband().getId());
	}
	
	@Test
	public void testGetMarriedEqual()
	{
		try
		{
			assertEquals(new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH).parse(DATE_MARRIED_STRING), FAMILY.getMarried());
		}
		catch (ParseException e)
		{
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGetDivorcedEqual()
	{
		try
		{
			assertEquals(new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH).parse(DATE_DIVORCED_STRING), FAMILY.getDivorced());
		}
		catch (ParseException e)
		{
			e.printStackTrace();
		}
	}

}
