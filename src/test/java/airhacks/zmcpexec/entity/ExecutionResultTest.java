package airhacks.zmcpexec.entity;

import org.junit.jupiter.api.Test;

import airhacks.zmcpexec.entity.ExecutionResult;

import static org.assertj.core.api.Assertions.*;

class ExecutionResultTest {
    
    @Test
    void createSuccessResult() {
        var result = ExecutionResult.success("test output");
        
        assertThat(result.success()).isTrue();
        assertThat(result.output()).isEqualTo("test output");
        assertThat(result.error()).isNull();
    }
    
    @Test
    void createFailureResult() {
        var result = ExecutionResult.failure("test error");
        
        assertThat(result.success()).isFalse();
        assertThat(result.error()).isEqualTo("test error");
        assertThat(result.output()).isNull();
    }
    
    @Test
    void createDirectResult() {
        var result = new ExecutionResult("output", "error", true);
        
        assertThat(result.output()).isEqualTo("output");
        assertThat(result.error()).isEqualTo("error");
        assertThat(result.success()).isTrue();
    }
}