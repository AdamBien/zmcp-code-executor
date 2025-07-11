package airhacks.zmcpexec.control;

import java.util.Map;

/**
 * Returns the response for "tools/list" request 
 * as specified by the MCP protocol: https://modelcontextprotocol.io/specification/2025-03-26/server/tools#protocol-messages
 */
public interface ToolSpec {

    static Map<String, String> singleRequiredParameter(String name,String description) {
        return Map.of(
                "name", name,
                "description", description,
                "inputSchema", """
                                {
                                    "type": "object",
                                    "properties": {
                                        "code": {
                                            "type": "string"
                                        }
                                    },
                                    "required": ["code"]
                                }
                        """);
    }


}
