/**
 * 
 */
package dracula;

/**
 * "Dracula leaves nasty surprises behind him however: Traps and immature Vampires"
 * @author alex
 */
public interface Encounter {
	/**
	 * Cost to the Hunter for encountering a Trap
	 */
	public final static int TRAP_COST = 2;
	/**
	 * Cost to the Hunter for encountering Dracular
	 */
	public final static int DRACULA_COST = 4;
	/**
	 * Cost to Dracula for encountering a Hunter
	 */
	public final static int HUNTER_COST = 10;
	
	public String getLocation();
	public boolean isTrap();
}
