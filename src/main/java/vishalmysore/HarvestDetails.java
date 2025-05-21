package vishalmysore;

import com.t4a.annotations.Prompt;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class HarvestDetails {
    private String harvesterName;
    @Prompt(dateFormat = "ddMMyyyy", describe = "convert to actual date")
    private Date harvestDate;
    private String harvestLocation;
    private String harvestType;
    private String harvestQuantity;

}
