package airhacks.zmcpexec.entity;

import java.util.Map;

/**
 * Metadata for the zmcp code executor tool.
 * 
 * @param name tool name
 * @param description tool description
 * @param inputSchema schema describing expected input parameters
 */
public record ToolMetadata(String name, String description, Map<String, Object> inputSchema) {
    
    public static ToolMetadata codeExecutor() {
        var schema = Map.of(
            "type", "object",
            "properties", Map.of(
                "code", Map.of(
                    "type", "string",
                    "description", "Java code to execute"
                )
            ),
            "required", new String[]{"code"}
        );
        
        return new ToolMetadata(
            "execute_java_code",
            "Executes Java code using JShell and returns the result",
            schema
        );
    }
}