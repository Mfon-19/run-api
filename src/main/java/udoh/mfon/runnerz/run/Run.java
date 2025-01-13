package udoh.mfon.runnerz.run;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import org.springframework.aot.generate.Generated;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;

import java.time.LocalDateTime;

// it is a record so that it is an immutable. no setters
public record Run(@Id
                  Integer id,
                  @NotEmpty // makes sure title is not empty when object is created
                  String title,
                  LocalDateTime startedOn,
                  LocalDateTime completedOn,
                  @Positive // makes sure miles is always positive
                  Integer miles,
                  Location location,
                  @Version
                  Integer version) {

    // this piece of code runs when the object is created
    public Run{
        if(startedOn.isAfter(completedOn)) {
            throw new IllegalArgumentException("The started on is after the completed on");
        }
    }

}
