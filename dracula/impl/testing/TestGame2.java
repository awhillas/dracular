package dracula.impl.testing;

import dracula.Dracula;
import dracula.DraculaMove;
import dracula.PlayerFactory;

public class TestGame2 {
	
	
	public static void main(String[] args) {
	
		// Run series of tests.
		testDraculaStartingPosition();
		System.out.println("All tests PASSED");
	}
	
	
	private static void testDraculaStartingPosition() {
		String pastPlays = "GLO.... SVI.... HPA.... MMI....";
		String[] messages = new String[4];
		for (int i = 0; i < messages.length; i++) {
			messages[i] = "Blah " + i;
		}
		
		Dracula d = PlayerFactory.getDracula( pastPlays, messages);
	    assert d != null;
	    
	    DraculaMove result = d.decideMove( );
	    assert result.getPlayAsString().equals("MN");	
	    System.out.println("Test Dracula Starting Position PASSED");
	}
    
}
