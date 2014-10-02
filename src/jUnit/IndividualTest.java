/**
 * 
 */
package jUnit;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import gedcom.GEDCOMELine;
import gedcom.GEDCOMParser;
import gedcom.GEDCOMTag;
import main.Family;
import main.Individual;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class IndividualTest {
	
	final static String INDIVIDUAL_ID = "I3";
	final static String INDIVIDUAL_NAME = "John Smith";
	final static char INDIVIDUAL_SEX = Individual.SEX_MALE;
	final static String INDIVIDUAL_BIRTHDAY_DATE = "July 8, 1902";
	final static String INDIVIDUAL_DEATH_DATE = "December 17, 1934";
	
	final static Individual INDIVIDUAL = new Individual(INDIVIDUAL_ID);
	static
	{
		INDIVIDUAL.setName(INDIVIDUAL_NAME);
		INDIVIDUAL.setSex(INDIVIDUAL_SEX);
		
		try
		{
			INDIVIDUAL.setBirthday(new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH).parse(INDIVIDUAL_BIRTHDAY_DATE));
			INDIVIDUAL.setDeath(new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH).parse(INDIVIDUAL_DEATH_DATE));
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
			Individual i = new Individual("");
			fail("Setting the id of an individual to an empty value did not throw an exception when it should have");
		}
		catch (IllegalArgumentException e)
		{
			exceptionThrown = true;
		}
		
		assertTrue(exceptionThrown);
	}
	
	@Test
	public void testSetSexWithInvalidValueThrowsException()
	{
		boolean exceptionThrown = false;
		
		try
		{
			Individual i = new Individual(INDIVIDUAL_ID);
			i.setSex('X');
			fail("Setting the sex of an individual to an invalid value did not throw an exception when it should have");
		}
		catch (IllegalArgumentException e)
		{
			exceptionThrown = true;
		}
		
		assertTrue(exceptionThrown);
	}

	@Test
	public void testGetIdEqual()
	{
		assertEquals(INDIVIDUAL_ID, INDIVIDUAL.getId());
	}
	
	@Test
	public void testGetNameEqual()
	{
		assertEquals(INDIVIDUAL_NAME, INDIVIDUAL.getName());
	}
	
	@Test
	public void testGetBirthdayEqual()
	{
		try
		{
			assertEquals(new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH).parse(INDIVIDUAL_BIRTHDAY_DATE), INDIVIDUAL.getBirthday());
		}
		catch (ParseException e)
		{
			e.printStackTrace();
		}
	}
	
	@Test
	public void testSetBirtdayToDateAfterDeathThrowsException()
	{
		boolean exceptionThrown = false;
		
		try
		{
			Date d = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH).parse(INDIVIDUAL_DEATH_DATE);
			
			Calendar cal = Calendar.getInstance();
			cal.setTime(d);
			cal.add(Calendar.DATE, +20); //20 days after death date
			
			Individual i = new Individual(INDIVIDUAL_ID);
			i.setDeath(d);
			i.setBirthday(cal.getTime());

			fail("Setting the birthday of an individual to a date after death did not throw an exception when it should have");
		}
		catch (ParseException e)
		{
			e.printStackTrace();
		}
		catch (IllegalArgumentException e)
		{
			exceptionThrown = true;
		}
		
		assertTrue(exceptionThrown);
	}
	
	@Test
	public void testSetDeathToDateBeforeBirthdayThrowsException()
	{
		boolean exceptionThrown = false;
		
		try
		{
			Date d = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH).parse(INDIVIDUAL_BIRTHDAY_DATE);
			
			Calendar cal = Calendar.getInstance();
			cal.setTime(d);
			cal.add(Calendar.DATE, -20); //20 days before birth date
			
			Individual i = new Individual(INDIVIDUAL_ID);
			i.setBirthday(d);
			i.setDeath(cal.getTime());
			

			fail("Setting the date of an individual to a date before birthday did not throw an exception when it should have");
		}
		catch (ParseException e)
		{
			e.printStackTrace();
		}
		catch (IllegalArgumentException e)
		{
			exceptionThrown = true;
		}
		
		assertTrue(exceptionThrown);
	}
	
	@Test
	public void testGetDeathEqual()
	{
		try
		{
			assertEquals(new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH).parse(INDIVIDUAL_DEATH_DATE), INDIVIDUAL.getDeath());
		}
		catch (ParseException e)
		{
			e.printStackTrace();
		}
	}

}
