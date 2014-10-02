package gedcom;

public class GEDCOMAnomaly {
	private String message;

	public GEDCOMAnomaly(String m)
	{
		this.setMessage(m);
	}
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String m) {
		message = m;
	}
}
