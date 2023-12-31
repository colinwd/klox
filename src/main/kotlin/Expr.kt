abstract class Expr {
    abstract fun <R> accept(visitor: Visitor<R>): R

    interface Visitor<R> {
        fun visitAssignExpr(expr: Assign): R
        fun visitBinaryExpr(expr: Binary): R
        fun visitCallExpr(expr: Call): R
        fun visitGetExpr(expr: Get): R
        fun visitGroupingExpr(expr: Grouping): R
        fun visitLiteralExpr(expr: Literal): R
        fun visitLogicalExpr(expr: Logical): R
        fun visitSetExpr(expr: Set): R
        fun visitUnaryExpr(expr: Unary): R
        fun visitVariableExpr(expr: Variable): R
    }

    data class Assign(
        val name: Token,
        val value: Expr,
    ) : Expr() {
        override fun <R> accept(visitor: Visitor<R>): R {
            return visitor.visitAssignExpr(this)
        }
    }

    data class Binary(
        val left: Expr,
        val operator: Token,
        val right: Expr,
    ) : Expr() {
        override fun <R> accept(visitor: Visitor<R>): R {
            return visitor.visitBinaryExpr(this)
        }
    }

    data class Call(
        val callee: Expr,
        val paren: Token,
        val arguments: List<Expr>,
    ) : Expr() {
        override fun <R> accept(visitor: Visitor<R>): R {
            return visitor.visitCallExpr(this)
        }
    }

    data class Get(
        val target: Expr,
        val name: Token,
    ) : Expr() {
        override fun <R> accept(visitor: Visitor<R>): R {
            return visitor.visitGetExpr(this)
        }
    }

    data class Grouping(
        val expression: Expr,
    ) : Expr() {
        override fun <R> accept(visitor: Visitor<R>): R {
            return visitor.visitGroupingExpr(this)
        }
    }

    data class Literal(
        val value: Any?,
    ) : Expr() {
        override fun <R> accept(visitor: Visitor<R>): R {
            return visitor.visitLiteralExpr(this)
        }
    }

    data class Logical(
        val left: Expr,
        val operator: Token,
        val right: Expr,
    ) : Expr() {
        override fun <R> accept(visitor: Visitor<R>): R {
            return visitor.visitLogicalExpr(this)
        }
    }

    data class Set(
        val target: Expr,
        val name: Token,
        val value: Expr,
    ) : Expr() {
        override fun <R> accept(visitor: Visitor<R>): R {
            return visitor.visitSetExpr(this)
        }
    }

    data class Unary(
        val operator: Token,
        val right: Expr,
    ) : Expr() {
        override fun <R> accept(visitor: Visitor<R>): R {
            return visitor.visitUnaryExpr(this)
        }
    }

    data class Variable(
        val name: Token,
    ) : Expr() {
        override fun <R> accept(visitor: Visitor<R>): R {
            return visitor.visitVariableExpr(this)
        }
    }
}
