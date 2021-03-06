import org.junit.Rule;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.sql2o.Connection;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SightingTest {

    @Rule
    public DatabaseRule database = new DatabaseRule();

    @AfterEach
    protected void after() {
        //clear sightings table after each test
        try(Connection con = DB.sql2o.open()) {
            String deleteAnimalsQuery = "DELETE FROM sightings;";
            con.createQuery(deleteAnimalsQuery).executeUpdate();
        }
    }

    @Test
    @DisplayName("Sighting Instantiates Correctly.")
    public void testInstanceOfSighting_true(){
        Sighting testSighting = setUpNewSighting();
        assertEquals(true, testSighting instanceof Sighting);
    }

    @Test
    @DisplayName("Compare Sighting objects")
    public void returnTrueIfSightingIsTheSame_true() {
        Sighting testSighting = setUpNewSighting();
        Sighting testSighting2 = setUpNewSighting();
        assertTrue(testSighting.equals(testSighting2));
    }

    @Test
    @DisplayName("Sighting Instantiates Correctly the animalId 1.")
    public void getAnimalId_sightingInstantiatesWithAnimalId_1() {
        Sighting testSighting = setUpNewSighting();
        assertEquals(1, testSighting.getAnimalId());
    }

    @Test
    @DisplayName("Sighting Instantiates Correctly the rangerId 1.")
    public void getRangerId_sightingInstantiatesWithRanger_1() {
        Sighting testSighting = setUpNewSighting();
        assertEquals(1, testSighting.getRangerId());
    }

    @Test
    @DisplayName("Sighting Instantiates Correctly the locationId 1.")
    public void getLocationId_sightingInstantiatesWithLocationId_1() {
        Sighting testSighting = setUpNewSighting();
        assertEquals(1, testSighting.getLocationId());
    }

    @Test
    @DisplayName("Sighting saves objects")
    public void save_insertsObjectIntoDatabase_Sighting() {
        Sighting testSighting = setUpNewSighting();
        testSighting.save();
        assertEquals(1, Sighting.all().size());
    }



    //helper method.
    private Sighting setUpNewSighting() {
        return new Sighting (1,1, 1);
    }
}
