package vishalmysore;

import com.t4a.annotations.Action;
import com.t4a.annotations.Agent;
import com.t4a.annotations.Prompt;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.util.Date;

@Log

@Agent(groupName = "lawnshare", groupDescription = "actions related to frontyard sharing for party or instagram pictures")
public class LawnShareAction {

    @Action(description = "Share your frontyard with your friends for party")
    public String lawnShareForParty(@Prompt(dateFormat = "ddMMYYYY") Date startTime, int hours, String name){
        log.info("LawnShareAction.lawnShareForInstagram()");
        return "Party Booked for "+hours+" hours from "+startTime+" by "+name;
    }
}
