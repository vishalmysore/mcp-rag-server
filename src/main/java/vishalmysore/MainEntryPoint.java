package vishalmysore;


import com.t4a.processor.AIProcessor;
import com.t4a.processor.spring.SpringGeminiProcessor;
import io.github.vishalmysore.a2a.domain.JsonRpcRequest;

import io.github.vishalmysore.a2a.server.DyanamicTaskContoller;
import io.github.vishalmysore.common.server.JsonRpcController;
import io.github.vishalmysore.common.server.SpringAwareJSONRpcController;
import io.github.vishalmysore.mcp.server.MCPToolsController;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** Expose the Json-RPC endpoint for the tasks
 *  This will handle all the JSON RPC Requests for a2a such as
 *  tasks/send
 *  tasks/get
 *  tasks/sendSubscribe
 *  tasks/cancel
 *  tasks/setPushNotification etc
 * */
@RestController
@RequestMapping("/")
@Log
public class MainEntryPoint extends SpringAwareJSONRpcController {

    @Autowired
    public MainEntryPoint(ApplicationContext applicationContext) {
        super(applicationContext);
    }

    @Autowired
    private A2ARagService vectorService;

    @Override
    public void preProcessing(String method, Object params) {
        vectorService.ragStorage(method,params);
    }

    @Override
    public void postProcessing(String method, Object params) {
        super.postProcessing(method, params);
    }



    @PostMapping
    public Object handleRpc(@RequestBody JsonRpcRequest request) {
        log.info(request.toString());
        return super.handleRpc(request);
    }



}
