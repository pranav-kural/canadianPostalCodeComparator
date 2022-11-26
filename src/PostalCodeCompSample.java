import java.util.*;

/**
 * Display a sample usage of the PostalCodeComparator
 */
public class PostalCodeCompSample {

    public static void main(String[] args) {

        String origin = "K1N 9N3"; // uOttawa CRX building

        // key: postal code, value: name of a restaurant (and its actual closeness to the origin)
        // NOTE: 'closeness' is based on postal code, not real physical geographical closeness
        Map<String, String> postalCodes = new HashMap<>(){{
            put("K1V 0W3", "closest 3: Ocho Rios Caribbean Eatery");
            put("K7M 2X6", "closest 6: Naan Stop Express");
            put("M8Y 1K7", "closest 7: Daiko Indian and Nepali Food");
            put("K1Z 7Z4", "closest 5: Run2Patty");
            put("K1N 9K4", "closest 1: Island Fava");
            put("H4P 2H4", "closest 8: Gibeau Orange Julep");
            put("K1C 6V3", "closest 4: Tipikliz");
            put("K1N 6N5", "closest 2: Quesada Burritos & Tacos");
            put("V5T 3K5", "closest 9: Osteria Savio Volpe");
        }};

        // instantiate PostalCodeComparator - setting origin
        PostalCodeComparator postalCodeComparator = new PostalCodeComparator(origin);

        // get an unsorted list of postal codes
        List<String> postalCodeResult = new ArrayList<>(postalCodes.keySet());

        // print initial unsorted values
        System.out.println("List of postal codes before sorting: ");
        postalCodeResult.forEach(el -> {
            System.out.println(el + " - " + postalCodes.get(el));
        });

        // sort the list of postal codes using our comparator utility
        postalCodeResult.sort(postalCodeComparator);

        // print the result: sorted list of pincodes
        System.out.println("\n--------------------------------------------");
        System.out.println("List of postal codes in order of closeness to " + origin + ": ");
        postalCodeResult.forEach(el -> {
            System.out.println(el + " - " + postalCodes.get(el));
        });
    }


}
