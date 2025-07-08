package airhacks.zmcpexec.control;

import org.junit.jupiter.api.Test;

import airhacks.zmcpexec.control.CodeExecutor;

import static org.assertj.core.api.Assertions.*;

class CodeExecutorTest {
    
    private final CodeExecutor codeExecutor = CodeExecutor.create();
    
    @Test
    void executeSimpleExpression() {
        var result = codeExecutor.execute("2 + 3");
        
        assertThat(result.success()).isTrue();
        assertThat(result.output()).isEqualTo("5");
        assertThat(result.error()).isNull();
    }
    
    @Test
    void executeVariableDeclaration() {
        var result = codeExecutor.execute("var x = 42;");
        
        assertThat(result.success()).isTrue();
        assertThat(result.output()).isEqualTo("42");
        assertThat(result.error()).isNull();
    }
    
    @Test
    void executeMethodCall() {
        var result = codeExecutor.execute("Math.sqrt(16)");
        
        assertThat(result.success()).isTrue();
        assertThat(result.output()).isEqualTo("4.0");
        assertThat(result.error()).isNull();
    }
    
    @Test
    void executeStringOperation() {
        var result = codeExecutor.execute("\"Hello\" + \" World\"");
        
        assertThat(result.success()).isTrue();
        assertThat(result.output()).isEqualTo("\"Hello World\"");
        assertThat(result.error()).isNull();
    }
    
    @Test
    void executeInvalidCode() {
        var result = codeExecutor.execute("invalid syntax here");
        
        assertThat(result.success()).isFalse();
        assertThat(result.error()).contains("Compilation error");
        assertThat(result.output()).isNull();
    }
    
    @Test
    void executeEmptyCode() {
        var result = codeExecutor.execute("");
        
        assertThat(result.success()).isTrue();
        assertThat(result.output()).isEqualTo("Code executed successfully");
        assertThat(result.error()).isNull();
    }
    
    @Test
    void executeCodeWithException() {
        var result = codeExecutor.execute("throw new RuntimeException(\"test error\");");
        
        assertThat(result.success()).isFalse();
        assertThat(result.error()).contains("Exception: test error");
        assertThat(result.output()).isNull();
    }
    
    @Test
    void executeMultilineCode() {
        var code = "java.util.List.of(1, 2, 3).stream().mapToInt(i -> i * 2).sum()";
        
        var result = codeExecutor.execute(code);
        
        assertThat(result.success()).isTrue();
        assertThat(result.output()).isEqualTo("12");
        assertThat(result.error()).isNull();
    }
}