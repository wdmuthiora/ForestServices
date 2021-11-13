import org.junit.Rule;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.sql2o.Connection;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RangerTest {
    @Rule
    public DatabaseRule database = new DatabaseRule();

    @AfterEach
    protected void after() {

        //clear rangers table after each test
        try(Connection con = DB.sql2o.open()) {
            String deleteAnimalsQuery = "DELETE FROM rangers;";
            con.createQuery(deleteAnimalsQuery).executeUpdate();
        }
    }

    @Test
    @DisplayName("Ranger Instantiates Correctly.")
    public void testInstanceOfRanger_true(){
        Ranger testRanger = setUpNewRanger();
        assertEquals(true, testRanger instanceof Ranger);
    }

    @Test
    @DisplayName("Ranger Instantiates Correctly the name Chuck.")
    public void getName_rangerInstantiatesWithName_Chuck() {
        Ranger testRanger = setUpNewRanger();
        assertEquals("Chuck", testRanger.getName());
    }
















    //helper method.
    private Ranger setUpNewRanger() {
        return new Ranger ("Chuck", 1);
    }
    private Ranger setUpNewRanger2() { return new Ranger ("Norris", 2); }
}
