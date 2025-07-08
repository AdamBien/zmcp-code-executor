package airhacks.zmcpexec.control;

import airhacks.zmcpexec.entity.ExecutionResult;
import jdk.jshell.JShell;
import jdk.jshell.SnippetEvent;

/**
 * Service for executing Java code using JShell.
 */
public interface CodeExecutor {
    
    /**
     * Executes Java code and returns the result.
     * 
     * @param code Java code to execute
     * @return execution result containing output or error
     */
    ExecutionResult execute(String code);
    
    static CodeExecutor create() {
        return new JShellCodeExecutor();
    }
}

class JShellCodeExecutor implements CodeExecutor {
    
    @Override
    public ExecutionResult execute(String code) {
        try (var jshell = JShell.create()) {
            var events = jshell.eval(code);
            
            var result = new StringBuilder();
            var hasError = false;
            String lastValue = null;
            
            for (SnippetEvent event : events) {
                if (event.exception() != null) {
                    result.append("Exception: ").append(event.exception().getMessage());
                    hasError = true;
                    break;
                } else if (event.status() == jdk.jshell.Snippet.Status.REJECTED) {
                    result.append("Compilation error: ").append(jshell.diagnostics(event.snippet()).iterator().next().getMessage(java.util.Locale.getDefault()));
                    hasError = true;
                    break;
                } else if (event.value() != null && !event.value().isEmpty()) {
                    lastValue = event.value();
                }
            }
            
            if (hasError) {
                return ExecutionResult.failure(result.toString().trim());
            }
            
            if (lastValue != null) {
                return ExecutionResult.success(lastValue);
            }
            
            return ExecutionResult.success("Code executed successfully");
                
        } catch (Exception e) {
            return ExecutionResult.failure("Execution failed: " + e.getMessage());
        }
    }
}