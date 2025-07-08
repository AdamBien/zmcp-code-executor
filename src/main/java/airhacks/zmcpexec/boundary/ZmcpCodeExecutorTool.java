package airhacks.zmcpexec.boundary;

import java.util.List;
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
    
    public static final Map<String, Object> TOOL_SPEC = Map.of(
        "name", "execute_java_code",
        "description", "Executes Java code using JShell and returns the result",
        "inputSchema", Map.of(
            "type", "object",
            "properties", Map.of(
                "code", Map.of(
                    "type", "string",
                    "description", "Java code to execute"
                )
            ),
            "required", List.of("code")
        )
    );
    
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