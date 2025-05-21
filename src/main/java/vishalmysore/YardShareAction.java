package vishalmysore;



import com.t4a.annotations.Action;
import com.t4a.annotations.Agent;
import com.t4a.detect.ActionCallback;
import com.t4a.detect.ActionState;
import lombok.extern.java.Log;
import org.checkerframework.checker.units.qual.A;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ai.document.Document;
import java.util.List;
import java.util.stream.Collectors;

/**
 * YardShareAction is a service that allows users to share their backyards for vegetable growing,
 * record harvests, and get quotes for yard sharing.
 * It uses A2ARagService to manage vector storage and retrieval.
 * This is a spring bean and only ONE instance will be created
 */
@Log
@Service
@Agent(groupName = "yardshare", groupDescription = "actions related to backyard sharing and harvesting")
public class YardShareAction {


    private final A2ARagService vectorService;
    private ActionCallback callback;

    @Autowired
    public YardShareAction(A2ARagService vectorService) {
        this.vectorService = vectorService;
        log.info("YardShareAction initialized with vectorService");
    }

    private boolean isAvailable = true;
    private double yardSize = 0.0;
    private String cropsGrowing = "";
    private String location = "";

    @Action(description = "Share your backyard for vegetable growing")
    public void shareMyYard(double size, String address, String availableDates) {
        this.yardSize = size;
        this.location = address;
        this.isAvailable = true;
        log.info("Yard shared: " + size + " sq ft at " + address);
        if (callback != null) {
            callback.sendtStatus("Yard successfully listed for sharing", ActionState.COMPLETED);
        }
    }

    @Action(description = "Record harvest from shared yard")
    public void harvestFromMyYard(String cropType, double amount, String date) {
        log.info("Harvest recorded: " + amount + " kg of " + cropType);
        if (callback != null) {
            callback.sendtStatus("Harvest of " + cropType + " recorded successfully", ActionState.COMPLETED);
        }
    }

    @Action(description = "Get quote for yard sharing")
    public Integer giveAQuote(double yardSize, String duration, String cropType) {
        // Simple pricing model: $2 per sq ft per month
        int baseRate = 2;
        int months = Integer.parseInt(duration);
        int quote = (int) (yardSize * baseRate * months);

        log.info("Quote generated: $" + quote);
        if (callback != null) {
            callback.sendtStatus("Quote for " + yardSize + " sq ft for " + duration + " months: $" + quote, ActionState.COMPLETED);
        }
        return quote;
    }

    @Action(description = "Get yard status")
    public String getYardStatus() {
        String status = String.format("Yard is %s, Size: %.2f sq ft, Location: %s, Currently growing: %s",
                isAvailable ? "available" : "occupied", yardSize, location, cropsGrowing);
        log.info("Status checked: " + status);
        return status;
    }

    @Action(description = "Submit Harvest quote Details")
    public String submitHarvestQuote(HarvestDetails harvestDetails){
        vectorService.addTask(harvestDetails);
        return "Harvest quote details submitted successfully";
    }
    @Action(description = "retrieve Harvest quote Details")
    public List<String> getHarvestQuote(String harversterNameOrDateOrNumber) {
        List<Document> documents = vectorService.getSimilarDocuments(harversterNameOrDateOrNumber);
        return documents.stream()
                .map(Document::getFormattedContent)
                .collect(Collectors.toList());
    }

}