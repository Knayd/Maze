package com.example.maze

data class Tile(
    val row: Int,
    val column: Int
) {
    var visited: Boolean = false
        private set

    var hasTopWall: Boolean = true
        private set

    var hasBottomWall: Boolean = true
        private set

    var hasLeftWall: Boolean = true
        private set

    var hasRightWall: Boolean = true
        private set

    var enterDirection: Direction? = null
        private set

    var exitDirection: Direction? = null
        private set

    fun isAtBottomOf(other: Tile): Boolean {
        return column == other.column && row == (other.row + 1)
    }

    fun isLeftOf(other: Tile): Boolean {
        return row == other.row && column == (other.column - 1)
    }

    fun isRightOf(other: Tile): Boolean {
        return row == other.row && column == (other.column + 1)
    }

    fun isOnTopOf(other: Tile): Boolean {
        return column == other.column && row == (other.row - 1)
    }

    fun removeTopWall() {
        hasTopWall = false
    }

    fun removeBottomWall() {
        hasBottomWall = false
    }

    fun removeLeftWall() {
        hasLeftWall = false
    }

    fun removeRightWall() {
        hasRightWall = false
    }

    fun updateVisited(value: Boolean) {
        visited = value
    }

    fun updateEnterDirection(newEnterDirection: Direction?) {
        if (exitDirection == newEnterDirection) {
            exitDirection = null
        } else {
            enterDirection = newEnterDirection
        }
    }

    fun updateExitDirection(newExitDirection: Direction?) {
        if (enterDirection == newExitDirection) {
            enterDirection = null
        } else {
            exitDirection = newExitDirection
        }
    }
}
