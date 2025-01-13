package udoh.mfon.runnerz.run;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

// response body will be JSON
@RestController
// this is the base mapping where all requests hit
// in the class body, we specify mappings for specific endpoints
@RequestMapping("/api/runs")
public class RunController {

    private final RunRepository runRepository;

    // we don't instantiate a new RunRepository object each time we create a RunController object
    // instead, we use the instance of RunRepository managed by spring ioc as a "bean"
    // spring "injects" the object into our RunController constructor
    // that is what is meant by Dependency Injection
    @Autowired
    public RunController(RunRepository runRepository) {
        this.runRepository = runRepository;
    }

//     this is same as the base mapping aka the default method mapped to the endpoint
//     since there is no path variable, we simply return all data in our repository
    @GetMapping("")
    List<Run> findAll(){
        return runRepository.findAll();
    }

    // the user passes an id in the path. we wrap that id as {id}
    // and put it in the method by using annotation @PathVariable
    @GetMapping("/{id}")
    Run findById(@PathVariable Integer id){
        // the optional class is very useful to check if some item exists or not

        Optional<Run> optionalRun = runRepository.findById(id);
        if(optionalRun.isPresent()){
            return optionalRun.get();
        }
        else
            throw new RunNotFoundException();
    }

    // creates a new entry in our database according to the run object passed
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    // the @Valid annotation validated the run object based on validation annotations
    // we provided in the Run record
    void create(@Valid @RequestBody Run run){
        runRepository.save(run);
    }

    // updates an entry in our database of id == id according to the run object passed
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}")
    void update(@RequestBody Run run, @PathVariable Integer id){
        runRepository.save(run);
    }

    // deletes an entry in our database of id == id
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    void delete(@PathVariable Integer id){
        runRepository.delete(runRepository.findById(id).get());
    }

    @GetMapping("/location/{location}")
    List<Run> findByLocation(@PathVariable String location){
        return runRepository.findAllByLocation(location);
    }
}
