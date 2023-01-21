class LoxClass(
    val name: String
) : LoxCallable {
    override fun call(interpreter: Interpreter, arguments: List<Any?>): Any? {
        val instance = LoxInstance(this)
        return instance
    }

    override fun arity() = 0

    override fun toString(): String {
        return name
    }
}