import java.io.BufferedReader
import java.io.InputStreamReader
import java.nio.charset.Charset
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.system.exitProcess

class Lox {
    companion object {
        private val interpreter = Interpreter()

        private var hadError = false
        private var hadRuntimeError = false

        fun runFile(path: String) {
            val bytes = Files.readAllBytes(Paths.get(path))
            runCode(String(bytes, Charset.defaultCharset()))

            if (hadError) exitProcess(65)
            if (hadRuntimeError) exitProcess(70)
        }

        fun runPrompt() {
            val input = InputStreamReader(System.`in`)
            val reader = BufferedReader(input)

            while (true) {
                print("> ")
                val line = reader.readLine() ?: break
                runCode(line)
                hadError = false
            }
        }

        fun runCode(source: String) {
            val scanner = Scanner(source)
            val tokens = scanner.scanTokens()
            val parser = Parser(tokens)
            val statements = parser.parse()

            if (hadError) return

            interpreter.interpret(statements)
        }

        fun report(line: Int, where: String, message: String) {
            println("[line $line] Error $where: $message")
            hadError = true
        }

        fun error(line: Int, message: String) {
            report(line, "", message)
        }

        fun error(token: Token, message: String) {
            if (token.type == TokenType.EOF) {
                report(token.line, " at end", message)
            } else {
                report(token.line, " at '${token.lexeme}'", message)
            }
        }

        fun runtimeError(error: RuntimeError) {
            System.err.println("${error.message} \n[line ${error.token.line}]")
            hadRuntimeError = true
        }
    }
}

fun main(args: Array<String>) {
    if (args.size > 1) {
        println("Usage: jlox [script]")
        exitProcess(64)
    } else if (args.size == 1) {
        Lox.runFile(args[0])
    } else {
        Lox.runPrompt()
    }
}

