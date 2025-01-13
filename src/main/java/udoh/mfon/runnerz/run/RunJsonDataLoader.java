package udoh.mfon.runnerz.run;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

@Component
public class RunJsonDataLoader implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(RunJsonDataLoader.class);

    // the object mapper will be used to map json to an object
    private final ObjectMapper objectMapper;
    private final RunRepository runRepository;

    public RunJsonDataLoader(ObjectMapper objectMapper, RunRepository runRepository) {
        this.objectMapper = objectMapper;
        this.runRepository = runRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if(runRepository.count() == 0) {
            // use inputstream to store json in a way object mapper can read
            try (InputStream inputStream = TypeReference.class.getResourceAsStream("/data/runs.json")) {
                // object mapper stores all run objects in Runs (a list of runs)
                Runs allRuns = objectMapper.readValue(inputStream, Runs.class);
                log.info("Reading {} runs from JSON data and saving to in-memory collection.", allRuns.runs().size());
                // use run repository to save all run objects into our h2 in mem database
                runRepository.saveAll(allRuns.runs());
            } catch (IOException e) {
                throw new RuntimeException("Failed to read JSON data", e);
            }
        } else {
            log.info("Not loading Runs from JSON data because the collection contains data.");
        }
    }

}