/**
 * 
 */
package dracula.impl.testing;

import java.util.ArrayList;
import java.util.List;

import dracula.Encounter;
import dracula.impl.*;

/**
 * testing suite for DracularTrial class
 */
public class TestDraculaTrail {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// Run me!
		
		// Test constructor
		
		System.out.println("Testing DraculaTrail constructor...");
		DraculaTrail dt  = new DraculaTrail();
		assert dt != null: "Problem instansiating DracularTrail class with constructor.";
		assert dt.getLength() == 0: "Trail should be of length 0, returns "+dt.getLength();
		assert !dt.hasDoubledBack();
		assert !dt.hasHidden();
		assert dt.getDoubleBackMoves().isEmpty();
		assert dt.getMoveAt(0) == null;
		System.out.println("PASSED!");
		
		// Test copy constructor
		
		System.out.println("Testing DraculaTrail copy constructor on empty object...");
		DraculaTrail dt2 = dt.clone();
		assert dt2 != null;
		assert dt != dt2;
		assert dt.getLength() == 0;
		assert !dt.hasDoubledBack();
		assert !dt.hasHidden();
		assert dt.getDoubleBackMoves().isEmpty();
		assert dt.getMoveAt(0) == null;
		System.out.println("PASSED!");
		
		// Test adding a move
		
		// data
		System.out.println("Testing adding 1st move...");
		String loc1 = "CD";
		Move move1 = new Move(loc1);
		Encounter trap1 = new Vampire(loc1);
		//tests
		dt.addMove(move1, trap1);
		assert dt.getLength() == 1;
		assert !dt.hasDoubledBack();
		assert !dt.hasHidden();
		assert dt.getDoubleBackMoves().size() == 1;
		assert dt.getMoveAt(0).equals(move1);
		assert dt.getEncountersAt(loc1)[0] == trap1;
		System.out.println("PASSED!");

		// data
		System.out.println("Testing adding 2nd move...");
		String loc2 = "KL";
		Move move2 = new Move(loc2);
		Encounter trap2 = new Trap(loc2);
		//tests
		dt.addMove(move2, trap2);
		assert dt.getLength() == 2;
		assert !dt.hasDoubledBack();
		assert !dt.hasHidden();
		assert dt.getDoubleBackMoves().size() == 2;
		assert dt.getMoveAt(0).equals(move2);
		assert dt.getMoveAt(1).equals(move1);
		assert dt.getEncountersAt(loc1)[0] == trap1;
		assert dt.getEncountersAt(loc2)[0] == trap2;
		System.out.println("PASSED!");
		
		// Clone list again and testing
		
		System.out.println("Testing DraculaTrail copy constructor on 2 item trail...");
		dt2 = dt.clone();
		//tests
		assert dt != dt2;	// Its a clone not object point copy.
		assert dt2.getLength() == 2;
		assert !dt2.hasHidden();
		assert dt2.getDoubleBackMoves().size() == 2;
		assert dt2.getMoveAt(0).equals(move2);
		assert dt2.getMoveAt(1).equals(move1);
		assert dt2.getEncountersAt(loc1)[0] == trap1;
		assert dt2.getEncountersAt(loc2)[0] == trap2;
		System.out.println("PASSED!");
		
		// Test encounters tracking
		
		System.out.println("Testing Encounter disarming...");
		assert dt2.disarm(trap1);
		assert dt2.getEncountersAt(loc1).length == 0: "ERROR: traps at "+loc1+" should be zero, found: "+dt2.getEncountersAt(loc1).length + dt2.getEncountersAt(loc1);
		assert dt2.getEncountersAt(loc2)[0] == trap2;
		assert dt2.disarm(trap2);
		assert dt2.getEncountersAt(loc1).length == 0;
		assert dt2.getEncountersAt(loc2).length == 0;
		System.out.println("PASSED!");
		
		// Test Double-Back moves
		
		System.out.println("Testing Double-Back move listing...");
		
		System.out.println("... on an empty list...");
		dt2 = new  DraculaTrail();
		assert dt2.getDoubleBackMoves().size() == 0;
		System.out.println("PASSED!");
		
		System.out.println("... on 2 move list...");		
		List<Move> dbmoves = dt.getDoubleBackMoves();
		System.out.println("Double-back moves: "+dbmoves);
		assert dbmoves.size() == 2;
		assert dbmoves.get(0).getLocation() == loc2;
		assert dbmoves.get(1).getLocation() == loc1;
		System.out.println("PASSED!");
		
		System.out.println("... adding a Double-Back move...");
		Encounter trap3 = new Trap(loc2);
		dt.addMove(dbmoves.get(1), trap3);
		assert dt.getLength() == 3;
		assert dt.hasDoubledBack();
		assert dt.getDoubleBackMoves().size() == 0;
		assert dt.getMoveAt(0).equals(dbmoves.get(1));
		assert dt.getMoveAt(1).equals(move2);
		assert dt.getMoveAt(2).equals(move1);
		System.out.println("PASSED!");
		
		// Test Hidden moves in Trail.
		
		// data
		System.out.println("Testing adding a Hide move...");
		Move move4 = new Move("HI", loc2);
		Encounter trap4 = new Trap(loc2);
		dt.addMove(move4, trap4);
		// testing
		assert dt.getLength() == 4;
		assert dt.hasHidden();
		assert dt.getMoveAt(0).equals(move4);
		assert dt.getMoveAt(1).equals(dbmoves.get(1));
		assert dt.getMoveAt(2).equals(move2);
		assert dt.getMoveAt(3).equals(move1);		
		System.out.println("PASSED!");
		
		// Test adding more than 6 locations
		// data
		System.out.println("Testing adding 7 moves...");
		String[] locs = {"BE", "BR", "BU", "BC", "BD", "CD", "KR"};
		for(String l : locs)
			dt.addMove(new Move(l), new Trap(l));
		// Tests, 
		// should be in reverse order to which they were added.
		assert dt.getMoveAt(0).getLocation() == "KR";
		assert dt.getMoveAt(1).getLocation() == "CD";
		assert dt.getMoveAt(2).getLocation() == "BD";
		assert dt.getMoveAt(3).getLocation() == "BC";
		assert dt.getMoveAt(4).getLocation() == "BU";
		assert dt.getMoveAt(5).getLocation() == "BR";
		System.out.println("PASSED!");
		
		System.out.println("...testing double-back moves on full trail...");
		// data 
		dbmoves = dt.getDoubleBackMoves();
		// tests
		System.out.println("Size: "+dbmoves.size());
		assert !dt.hasDoubledBack();
		assert dbmoves.size() == 6;
		assert dt.getMoveAt(0).getLocation() == dbmoves.get(0).getLocation();
		assert dt.getMoveAt(1).getLocation() == dbmoves.get(1).getLocation();
		assert dt.getMoveAt(2).getLocation() == dbmoves.get(2).getLocation();
		assert dt.getMoveAt(3).getLocation() == dbmoves.get(3).getLocation();
		assert dt.getMoveAt(4).getLocation() == dbmoves.get(4).getLocation();
		assert dt.getMoveAt(5).getLocation() == dbmoves.get(5).getLocation();
		System.out.println("PASSED!");
		
		
		System.out.println("Final trail looked like:");
		System.out.println(dt);
		System.out.println("ALL TEST PASSED!!");
	}

}
