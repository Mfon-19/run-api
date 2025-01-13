package udoh.mfon.runnerz.run;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

// this class simply manages our database (for now we're using an arraylist)
@Repository
public class JdbcClientRunRepository {

    private static final Logger log = Logger.getLogger(JdbcClientRunRepository.class.getName());

    // the jdbc client provides a connection to our h2 database
    // it provides an easy interface to query our database using sql and retrieve stuff
    private final JdbcClient jdbcClient;

    @Autowired
    public JdbcClientRunRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }


    public List<Run> findAll() {
        //we pass a sql command to jdbc client sql method as a string
        return jdbcClient.sql("SELECT * FROM run")
                .query(Run.class)
                .list(); // specifies that jdbc client returns a list of items in our database
    }

    public Optional<Run> findById(Integer id){
        return jdbcClient.sql("SELECT id, title, started_on, completed_on, miles, location FROM run WHERE id = :id") // we specify what
                // :id is down below
                .param("id", id) // id is the id in the method header
                .query(Run.class) // the data we get back gets mapped to a Run object which is wrapped as an optional and returned
                .optional();
    }

    public void create(Run run){
        var updated = jdbcClient.sql("INSERT INTO Run (id, title, started_on, completed_on, miles, location) values (?, ?, ?, ?, ?, ?)")
                .params(List.of(run.id(), run.title(), run.startedOn(), run.completedOn(), run.miles(), run.location().toString()))
                .update(); // to update something from the database. returns how many rows are affected

        // we assert that only 1 row was affected. if not, message is shown
        Assert.state(updated == 1, "Failed to create run " + run.title());
    }

    public void update(@Valid Run run, Integer id){
        var updated = jdbcClient.sql("update run set title = ?, started_on = ?, completed_on = ?, miles = ?, location = ? where id = ?")
                .params(List.of(run.title(), run.startedOn(), run.completedOn(), run.miles(), run.location().toString(), id))
                .update();

        Assert.state(updated == 1, "Failed to update run " + run.title());
    }

    void delete(Integer id){
        var deleted = jdbcClient.sql("delete from run where id = :id")
                .param("id", id)
                .update();

        Assert.state(deleted == 1, "Failed to delete run " + id);
    }

    public int count(){
        return jdbcClient.sql("select * from run")
                .query()
                .listOfRows()
                .size();
    }

    public void saveAll(List<Run> runs){
        runs.stream().forEach(this::create);
    }

    public List<Run> findByLocation(String location){
        return jdbcClient.sql("select * from run where location = :location")
                .param("location", location)
                .query(Run.class)
                .list();
    }
}
