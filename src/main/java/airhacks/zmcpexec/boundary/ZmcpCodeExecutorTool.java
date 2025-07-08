package airhacks.zmcpexec.boundary;

import java.util.Map;
import java.util.function.Function;

import airhacks.zmcpexec.control.CodeExecutor;
import airhacks.zmcpexec.entity.ToolMetadata;

/**
 * zmcp tool for executing Java code.
 * 
 * Implements the Function interface required by the zmcp protocol
 * for dynamic tool discovery and execution.
 */
public class ZmcpCodeExecutorTool implements Function<Map<String, Object>, Map<String, String>> {
    
    private final CodeExecutor codeExecutor;
    
    public ZmcpCodeExecutorTool() {
        this.codeExecutor = CodeExecutor.create();
    }
    
    @Override
    public Map<String, String> apply(Map<String, Object> input) {
        var code = (String) input.get("code");
        
        if (code == null || code.isBlank()) {
            return Map.of(
                "error", "Code parameter is required and cannot be empty",
                "success", "false"
            );
        }
        
        var result = codeExecutor.execute(code);
        
        if (result.success()) {
            return Map.of(
                "output", result.output(),
                "success", "true"
            );
        } else {
            return Map.of(
                "error", result.error(),
                "success", "false"
            );
        }
    }
    
    /**
     * Returns tool metadata for zmcp discovery.
     */
    public ToolMetadata getMetadata() {
        return ToolMetadata.codeExecutor();
    }
}