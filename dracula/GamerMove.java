/**
 * 
 */
package dracula;

/**
 * @author arbw870
 */
public class GamerMove implements DraculaMove {

	private String nextCity;
	
	public GamerMove(String city) {
		this.nextCity = city;
	}

	@Override
	public String getPlayAsString() {
		return this.nextCity;
	}

	@Override
	public String getMessage() {
		return "Blah blah blah-blaaah!";
	}
}
