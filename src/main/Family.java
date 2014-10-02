package main;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Family
{
	private String Id;
	private Date Married;
	private Date Divorced;
	private Individual Husband;
	private Individual Wife;
	private Individual Child;

	public Family(String identifier)
	{
		this.setId(identifier);
	}
	
	public String getId() {
		return Id;
	}

	public void setId(String id)
	{
		Id = Individual.ParseIdFromString(id);
		
		if(id.isEmpty())
		{
			throw new IllegalArgumentException("A family must have an id");
		}
	}

	public Date getMarried()
	{
		return Married;
	}

	public void setMarried(Date married)
	{
		Married = married;
		
				
	}

	public Individual getHusband()
	{
		return Husband;
	}

	public void setHusband(Individual husband)
	{
		Husband = husband;
		
		if(husband.getSex() != Individual.SEX_MALE && husband.getSex() != '\u0000')
		{
			throw new IllegalArgumentException("Husband with id " + husband.getId() + " may only be of male sex type for family with id " + this.getId());
		}
		else if(husband.getDeath() != null) //should perhaps thrown a different exception as to catch the exact error type instead of determine from message
		{
			throw new IllegalArgumentException("Husband with id " + husband.getId() + " is dead and may not be married in family with id " + this.getId());
		}
		
		
		System.out.println("husband more than once married = " +husband.getSpouseOfFamilyIDs());
		
	}

	public Individual getWife()
	{
		return Wife;
	}

	public void setWife(Individual wife)
	{
		Wife = wife;
		
		if(wife.getSex() != Individual.SEX_FEMALE && wife.getSex() != '\u0000')
		{
			throw new IllegalArgumentException("Wife with id " + wife.getId() + " may only be of female sex type for family with id " + this.getId());
		}
		else if(wife.getDeath() != null) //should perhaps thrown a different exception as to catch the exact error type instead of determine from message
		{
			throw new IllegalArgumentException("Wife with id " + wife.getId() + " is dead and may not be married in family with id " + this.getId());
		}
	}

	public Individual getChild()
	{
		return Child;
	}

	public void setChild(Individual child) 
	{
		Child = child;
	}

	public Date getDivorced() 
	{
		return Divorced;
	}

	public void setDivorced(Date divorced) 
	{
		Divorced = divorced;
	}
}
