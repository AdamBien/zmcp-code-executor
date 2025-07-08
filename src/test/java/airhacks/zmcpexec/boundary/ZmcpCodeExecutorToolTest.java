package airhacks.zmcpexec.boundary;

import org.junit.jupiter.api.Test;

import airhacks.zmcpexec.boundary.ZmcpCodeExecutorTool;

import java.util.Map;
import static org.assertj.core.api.Assertions.*;

class ZmcpCodeExecutorToolTest {
    
    private final ZmcpCodeExecutorTool tool = new ZmcpCodeExecutorTool();
    
    @Test
    void applyWithValidCode() {
        var input = Map.<String, Object>of("code", "5 + 3");
        var result = tool.apply(input);
        
        assertThat(result.get("success")).isEqualTo("true");
        assertThat(result.get("output")).isEqualTo("8");
        assertThat(result.get("error")).isNull();
    }
    
    @Test
    void applyWithInvalidCode() {
        var input = Map.<String, Object>of("code", "invalid syntax");
        var result = tool.apply(input);
        
        assertThat(result.get("success")).isEqualTo("false");
        assertThat(result.get("error")).contains("Compilation error");
        assertThat(result.get("output")).isNull();
    }
    
    @Test
    void applyWithMissingCode() {
        var input = Map.<String, Object>of();
        var result = tool.apply(input);
        
        assertThat(result.get("success")).isEqualTo("false");
        assertThat(result.get("error")).isEqualTo("Code parameter is required and cannot be empty");
        assertThat(result.get("output")).isNull();
    }
    
    @Test
    void applyWithEmptyCode() {
        var input = Map.<String, Object>of("code", "");
        var result = tool.apply(input);
        
        assertThat(result.get("success")).isEqualTo("false");
        assertThat(result.get("error")).isEqualTo("Code parameter is required and cannot be empty");
        assertThat(result.get("output")).isNull();
    }
    
    @Test
    void applyWithBlankCode() {
        var input = Map.<String, Object>of("code", "   ");
        var result = tool.apply(input);
        
        assertThat(result.get("success")).isEqualTo("false");
        assertThat(result.get("error")).isEqualTo("Code parameter is required and cannot be empty");
        assertThat(result.get("output")).isNull();
    }
    
    @Test
    void getMetadata() {
        var metadata = tool.getMetadata();
        
        assertThat(metadata.name()).isEqualTo("execute_java_code");
        assertThat(metadata.description()).isEqualTo("Executes Java code using JShell and returns the result");
        assertThat(metadata.inputSchema()).isNotNull();
        assertThat(metadata.inputSchema().get("type")).isEqualTo("object");
    }
    
    @Test
    void applyWithComplexCode() {
        var code = "java.util.stream.IntStream.range(1, 6).sum()";
        var input = Map.<String, Object>of("code", code);
        var result = tool.apply(input);
        
        assertThat(result.get("success")).isEqualTo("true");
        assertThat(result.get("output")).isEqualTo("15");
        assertThat(result.get("error")).isNull();
    }
}