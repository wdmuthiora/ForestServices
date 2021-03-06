import org.sql2o.Connection;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Location {

    private String name;
    private int id;

    public static Location find(int id) {
        try(Connection con = DB.sql2o.open()){
            String sql = "SELECT * FROM locations WHERE id=:id";
            Location location = con.createQuery(sql).addParameter("id",id).executeAndFetchFirst(Location.class);
            return location;
        }
    }

    public static void deleteAll() {
        String sql = "DELETE FROM locations";
            try(Connection con = DB.sql2o.open()) {
                con.createQuery(sql).executeUpdate();
        }

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Location)) return false;
        Location location = (Location) o;
        return Objects.equals(getName(), location.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }

    public Location(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static List<Location> all() {
        String sql = "SELECT * FROM locations";
        try(Connection con = DB.sql2o.open()) {
            return con.createQuery(sql).executeAndFetch(Location.class);
        }
    }

    public void save() {
        try (Connection con=DB.sql2o.open()){
            String sql="INSERT INTO locations (name) VALUES (:name)";
            if(name.equals("")){
                throw new IllegalArgumentException("All fields must be filled");
            }
            this.id = (int) con.createQuery(sql,true)
                    .addParameter("name", this.name)
                    .executeUpdate()
                    .getKey();
        }
    }

    public int getId() {
        return id;
    }

    public void deleteById(int id) {
        String sql = "DELETE FROM locations WHERE id= :id";
        try(Connection con = DB.sql2o.open()) {
            con.createQuery(sql).addParameter("id", this.id).executeUpdate();
        }
    }
    public List<Sighting> getLocationSightings() {
        try (Connection con = DB.sql2o.open()) {
            String sql = "SELECT sighting_id FROM locations_sightings WHERE location_id=:location_id";
            List<Integer> sightings_ids = con.createQuery(sql)
                    .addParameter("location_id", this.getId())
                    .executeAndFetch(Integer.class);
            List<Sighting> sightings = new ArrayList<Sighting>();

            for (Integer sighting_id : sightings_ids) {
                String sightingsQuery = "SELECT * FROM sightings WHERE id=:sighting_id";
                Sighting sighting = con.createQuery(sightingsQuery)
                        .addParameter("sighting_id", sighting_id)
                        .executeAndFetchFirst(Sighting.class);
                sightings.add(sighting);

            }
            if (sightings.size() == 0) {
                throw new IllegalArgumentException("Location has no sighting");
            } else {
                return sightings;
            }


        }
    }


}
