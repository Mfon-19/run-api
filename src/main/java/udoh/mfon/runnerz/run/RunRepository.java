package udoh.mfon.runnerz.run;

import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

// we add the data jdbc dependency to our pom.xml then extend which ever crud repository we need
// list crud repository contains inbuilt methods to findAll, save, saveAll, etc.
// this way we don't have to write all the methods on our own
public interface RunRepository extends ListCrudRepository<Run, Integer> {

    List<Run> findAllByLocation(String location);
}
