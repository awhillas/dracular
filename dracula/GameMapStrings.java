package dracula;

import java.util.*;

public class GameMapStrings {
	public static List<String> inlandCities() {
		List<String> result = new ArrayList<String>();
		result.add("|| BE || Belgrade ||");
		result.add("|| BR || Berlin ||");
		result.add("|| BU || Brussels ||");
		result.add("|| BC || Bucharest ||");
		result.add("|| BD || Budapest ||");
		result.add("|| CD || Castle Dracula ||");
		result.add("|| CF || Clermont Ferrand ||");
		result.add("|| CO || Cologne ||");
		result.add("|| FL || Florence ||");
		result.add("|| FR || Frankfurt ||");
		result.add("|| GA || Galatz ||");
		result.add("|| GE || Geneva ||");
		result.add("|| GR || Granada ||");
		result.add("|| JM || St Joseph & St Mary's ||");
		result.add("|| KL || Klausenburg ||");
		result.add("|| LI || Leipzig ||");
		result.add("|| MA || Madrid ||");
		result.add("|| MN || Manchester ||");
		result.add("|| MI || Milan ||");
		result.add("|| MU || Munich ||");
		result.add("|| NU || Nuremburg ||");
		result.add("|| PA || Paris ||");
		result.add("|| PR || Prague ||");
		result.add("|| SR || Saragossa ||");
		result.add("|| SJ || Sarajevo ||");
		result.add("|| SO || Sofia ||");
		result.add("|| ST || Strasbourg ||");
		result.add("|| SZ || Szeged ||");
		result.add("|| TO || Toulouse ||");
		result.add("|| VI || Vienna ||");
		result.add("|| ZA || Zagreb ||");
		result.add("|| ZU || Zurich ||");
		return result;
	}
	
	public static List<String> portCities() {
		List<String> result = new ArrayList<String>();
		result.add("|| AL || Alicante ||");
		result.add("|| AM || Amsterdam ||");
		result.add("|| AT || Athens ||");
		result.add("|| BA || Barcelona ||");
		result.add("|| BI || Bari ||");
		result.add("|| BO || Bordeaux ||");
		result.add("|| CA || Cadiz ||");
		result.add("|| CG || Cagliari ||");
		result.add("|| CN || Constanta ||");
		result.add("|| DU || Dublin ||");
		result.add("|| ED || Edinburgh ||");
		result.add("|| GW || Galway ||");
		result.add("|| GO || Genoa ||");
		result.add("|| HA || Hamburg ||");
		result.add("|| LE || Le Havre ||");
		result.add("|| LS || Lisbon ||");
		result.add("|| LV || Liverpool ||");
		result.add("|| LO || London ||");
		result.add("|| MR || Marseilles ||");
		result.add("|| NA || Nantes ||");
		result.add("|| NP || Naples ||");
		result.add("|| PL || Plymouth ||");
		result.add("|| RO || Rome ||");
		result.add("|| SA || Salonica ||");
		result.add("|| SN || Santander ||");
		result.add("|| SW || Swansea ||");
		result.add("|| VA || Valona ||");
		result.add("|| VR || Varna ||");
		result.add("|| VE || Venice ||");
		return result;
	}
	
	public static List<String> seas() {
		List<String> result = new ArrayList<String>();
		result.add("|| NS || North Sea ||");
		result.add("|| EC || English Channel ||");
		result.add("|| IS || Irish Sea ||");
		result.add("|| AO || Atlantic Ocean ||");
		result.add("|| BB || Bay of Biscay ||");
		result.add("|| MS || Mediterranean Sea ||");
		result.add("|| TS || Tyrrhenian Sea ||");
		result.add("|| IO || Ionian Sea ||");
		result.add("|| AS || Adriatic Sea ||");
		result.add("|| BS || Black Sea ||");
		return result;
	}
	
	public static List<String> railMap() {
		List<String> result = new ArrayList<String>();
		result.add("Alicante -- Madrid ");
		result.add("Bari -- Naples");
		result.add("Belgrade -- Szeged ");
		result.add("Belgrade -- Sofia");
		result.add("Berlin -- Leipzig");
		result.add("Bordeaux -- Paris");
		result.add("Brussels -- Paris");
		result.add("Brussels -- Cologne ");
		result.add("Bucharest -- Szeged");
		result.add("Bucharest -- Constanta");
		result.add("Budapest -- Vienna");
		result.add("Cologne -- Frankfurt ");
		result.add("Edinburgh -- Manchester");
		result.add("Frankfurt -- Leipzig");
		result.add("Galatz -- Bucharest  ");
		result.add("Geneva -- Milan");
		result.add("Hamburg -- Berlin");
		result.add("Leipzig -- Nuremburg ");
		result.add("Lisbon -- Madrid ");
		result.add("Liverpool -- Manchester");
		result.add("London -- Manchester");
		result.add("Marseilles -- Paris");
		result.add("Milan -- Genoa");
		result.add("Milan -- Florence");
		result.add("Munich -- Nuremburg ");
		result.add("Naples -- Rome");
		result.add("Paris -- Le Havre");
		result.add("Prague -- Berlin");
		result.add("Prague -- Vienna ");
		result.add("Rome -- Florence ");
		result.add("Salonica -- Sofia");
		result.add("Santander -- Madrid");
		result.add("Saragossa -- Madrid");
		result.add("Barcelona -- Alicante");
		result.add("Saragossa -- Barcelona ");
		result.add("Saragossa -- Bordeaux ");
		result.add("Sofia -- Varna");
		result.add("Strasbourg -- Zurich");
		result.add("Strasbourg -- Frankfurt ");
		result.add("Swansea -- London");
		result.add("Szeged -- Budapest");
		result.add("Venice -- Vienna");
		result.add("Zurich -- Milan");
		return result;
	}
	
	public static List<String> roadMap() {
		List<String> result = new ArrayList<String>();
		result.add("Alicante -- Madrid ");
		result.add("Amsterdam -- Cologne");
		result.add("Athens -- Valona");
		result.add("Barcelona -- Toulouse");
		result.add("Bari -- Naples");
		result.add("Belgrade -- St Joseph & St Mary's");
		result.add("Belgrade -- Szeged ");
		result.add("Belgrade -- Klausenburg ");
		result.add("Belgrade -- Sofia");
		result.add("Berlin -- Leipzig");
		result.add("Bordeaux -- Clermont Ferrand ");
		result.add("Bordeaux -- Nantes");
		result.add("Brussels -- Paris");
		result.add("Brussels -- Strasbourg ");
		result.add("Brussels -- Cologne ");
		result.add("Brussels -- Amsterdam ");
		result.add("Bucharest -- Klausenburg ");
		result.add("Bucharest -- Belgrade");
		result.add("Bucharest -- Sofia");
		result.add("Bucharest -- Constanta");
		result.add("Budapest -- Vienna");
		result.add("Cadiz -- Lisbon");
		result.add("Cadiz -- Granada");
		result.add("Cadiz -- Madrid");
		result.add("Castle Dracula -- Galatz");
		result.add("Clermont Ferrand -- Nantes");
		result.add("Clermont Ferrand -- Paris ");
		result.add("Clermont Ferrand -- Geneva");
		result.add("Cologne -- Hamburg");
		result.add("Cologne -- Strasbourg");
		result.add("Cologne -- Leipzig");
		result.add("Constanta -- Galatz ");
		result.add("Dublin -- Galway");
		result.add("Edinburgh -- Manchester");
		result.add("Florence -- Genoa");
		result.add("Florence -- Venice");
		result.add("Frankfurt --Cologne");
		result.add("Galatz -- Bucharest  ");
		result.add("Galway -- Dublin");
		result.add("Geneva -- Paris");
		result.add("Geneva -- Strasbourg ");
		result.add("Genoa -- Venice");
		result.add("Granada -- Alicante");
		result.add("Hamburg -- Leipzig");
		result.add("Hamburg -- Berlin");
		result.add("Klausenburg -- Szeged");
		result.add("Klausenburg -- Budapest");
		result.add("Klausenburg -- Castle Dracula");
		result.add("Klausenburg -- Galatz");
		result.add("Castle Dracula -- Galatz");
		result.add("Le Havre -- Brussels ");
		result.add("Leipzig -- Frankfurt");
		result.add("Leipzig -- Nuremburg ");
		result.add("Lisbon -- Madrid ");
		result.add("Liverpool --Swansea");
		result.add("London -- Manchester");
		result.add("Madrid -- Granada");
		result.add("Manchester -- Liverpool");
		result.add("Marseilles -- Clermont Ferrand");
		result.add("Marseilles -- Geneva");
		result.add("Marseilles -- Genoa");
		result.add("Marseilles -- Zurich");
		result.add("Marseilles -- Milan");
		result.add("Milan -- Genoa");
		result.add("Milan -- Munich");
		result.add("Munich -- Venice");
		result.add("Munich -- Zurich");
		result.add("Munich -- Strasbourg");
		result.add("Munich -- Nuremburg ");
		result.add("Nantes -- Paris");
		result.add("Nantes -- Le Havre");
		result.add("Naples -- Rome");
		result.add("Nuremburg -- Strasbourg");
		result.add("Nuremburg -- Frankfurt");
		result.add("Nuremburg -- Prague ");
		result.add("Paris -- Le Havre");
		result.add("Plymouth -- London");
		result.add("Prague -- Berlin");
		result.add("Prague -- Vienna ");
		result.add("Rome -- Florence ");
		result.add("Rome -- Bari");
		result.add("Salonica -- Sofia");
		result.add("Santander -- Madrid");
		result.add("Santander -- Lisbon");
		result.add("Saragossa -- Madrid");
		result.add("Santander -- Saragossa ");
		result.add("Saragossa -- Alicante");
		result.add("Saragossa -- Barcelona ");
		result.add("Saragossa -- Bordeaux ");
		result.add("Saragossa -- Toulouse ");
		result.add("Sarajevo -- St Joseph & St Mary's");
		result.add("Sarajevo -- Belgrade");
		result.add("Sarajevo -- Sofia ");
		result.add("Sofia -- Varna");
		result.add("Strasbourg -- Zurich");
		result.add("Strasbourg -- Frankfurt ");
		result.add("Strasbourg -- Paris");
		result.add("Swansea -- London");
		result.add("Szeged -- Budapest");
		result.add("Szeged -- Zagreb ");
		result.add("Szeged -- St Joseph & St Mary's");
		result.add("Zagreb -- St Joseph & St Mary's");
		result.add("Toulouse -- Bordeaux  ");
		result.add("Toulouse -- Marseilles");
		result.add("Toulouse -- Clermont Ferrand ");
		result.add("Valona -- Sofia");
		result.add("Valona -- Sarajevo");
		result.add("Valona -- Salonica ");
		result.add("Varna -- Constanta ");
		result.add("Venice -- Milan");
		result.add("Vienna -- Munich ");
		result.add("Zagreb -- Vienna");
		result.add("Zagreb -- Munich");
		result.add("Zagreb -- Budapest");
		result.add("Zagreb -- Sarajevo");
		result.add("Zurich -- Geneva");
		result.add("Zurich -- Milan");
		return result;
	}
	
	public static List<String> seaMap() {
		List<String> result = new ArrayList<String>();
		result.add("North Sea -- Edinburgh");
		result.add("North Sea -- Amsterdam");
		result.add("North Sea -- Hamburg");
		result.add("North Sea -- English Channel");
		result.add("English Channel -- London");
		result.add("English Channel -- Le Havre");
		result.add("English Channel -- Plymouth");
		result.add("English Channel -- Atlantic Ocean");
		result.add("Irish Sea -- Atlantic Ocean");
		result.add("Irish Sea -- Liverpool");
		result.add("Irish Sea -- Swansea");
		result.add("Irish Sea -- Dublin");
		result.add("Atlantic Ocean -- Galway");
		result.add("Atlantic Ocean -- Bay of Biscay");
		result.add("Atlantic Ocean -- Lisbon");
		result.add("Atlantic Ocean -- Cadiz");
		result.add("Atlantic Ocean -- Mediterranean Sea ");
		result.add("Bay of Biscay -- Nantes");
		result.add("Bay of Biscay -- Bordeaux");
		result.add("Bay of Biscay -- Santander");
		result.add("Mediterranean Sea -- Alicante");
		result.add("Mediterranean Sea -- Barcelona");
		result.add("Mediterranean Sea -- Marseilles");
		result.add("Mediterranean Sea -- Tyrrhenian Sea ");
		result.add("Mediterranean Sea -- Cagliari");
		result.add("Tyrrhenian Sea -- Genoa");
		result.add("Tyrrhenian Sea -- Cagliari");
		result.add("Tyrrhenian Sea -- Rome");
		result.add("Tyrrhenian Sea -- Naples");
		result.add("Tyrrhenian Sea -- Ionian Sea ");
		result.add("Ionian Sea -- Valona");
		result.add("Ionian Sea -- Salonica");
		result.add("Ionian Sea -- Adriatic Sea ");
		result.add("Adriatic Sea -- Venice");
		result.add("Adriatic Sea -- Bari");
		result.add("Black Sea -- Constanta");
		result.add("Black Sea -- Varna");
		return result;
	}
}
