# zmcp Java code executor tool

This [zmcp](https://github.com/AdamBien/zmcp) tool receives Java code which is executed via the JShell API.

Uses jdk.jshell.JShell to run (evaluate) Java code on-the-fly.

## ⚠️ Security Warning

**This tool executes arbitrary Java code on the host system.** Use only in trusted environments with proper security measures:

- Code execution happens with the same permissions as the JVM process
- No sandboxing or security restrictions are applied
- Malicious code can access system resources, files, and network
- Suitable for development/testing environments only
- Not recommended for production or untrusted input

For more secure code execution, consider [GraalVM Espresso](https://www.graalvm.org/jdk/reference-manual/java-on-truffle/) which provides sandboxed Java execution.

## Related Projects

- [zmcp-linkinfo-tool](https://github.com/AdamBien/zmcp-linkinfo-tool)