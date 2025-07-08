package airhacks.zmcpexec.entity;

/**
 * Represents the result of Java code execution.
 * 
 * @param output the execution output or result value
 * @param error optional error message if execution failed
 * @param success indicates if execution was successful
 */
public record ExecutionResult(String output, String error, boolean success) {
    
    public static ExecutionResult success(String output) {
        return new ExecutionResult(output, null, true);
    }
    
    public static ExecutionResult failure(String error) {
        return new ExecutionResult(null, error, false);
    }
}