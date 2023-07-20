package com.example.maze

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.Stack

/**
 * A maze that's generated using a recursive backtracking algorithm.
 */

class Maze(val rows: Int, val columns: Int) {

    val grid = List(rows) { row ->
        List(columns) { column ->
            Tile(row, column)
        }
    }

    private val _player = MutableStateFlow<Tile?>(null)
    val player: StateFlow<Tile?> = _player

    var exit: Tile? = null
        private set

    init {
        val tilesStack = Stack<Tile>()
        var current: Tile?
        var next: Tile?
        val start = grid.firstOrNull()?.firstOrNull()

        _player.value = start
        exit = grid.lastOrNull()?.lastOrNull()
        current = start
        current?.updateVisited(true)
        do {
            next = getRandomAdjacentTileFor(current)
            next?.let {
                removeWallsBetween(current, next)
                tilesStack.push(current)
                current = next
                current?.updateVisited(true)
            } ?: run {
                current = tilesStack.pop()
            }
        } while (tilesStack.isNotEmpty())
    }

    private fun getRandomAdjacentTileFor(tile: Tile?): Tile? {
        val adjacentTiles = mutableListOf<Tile>()

        val addIfNotVisited: (Tile) -> Unit = {
            if (!it.visited) adjacentTiles.add(it)
        }

        leftTileOf(tile)?.let { addIfNotVisited(it) }

        rightTileOf(tile)?.let { addIfNotVisited(it) }

        topTileOf(tile)?.let { addIfNotVisited(it) }

        bottomTileOf(tile)?.let { addIfNotVisited(it) }

        return if (adjacentTiles.isNotEmpty()) adjacentTiles.random() else null
    }

    private fun removeWallsBetween(current: Tile?, next: Tile) {
        if (current == null) return
        when {
            current.isAtBottomOf(next) -> {
                current.removeTopWall()
                next.removeBottomWall()
            }

            current.isLeftOf(next) -> {
                current.removeRightWall()
                next.removeLeftWall()
            }

            current.isRightOf(next) -> {
                current.removeLeftWall()
                next.removeRightWall()
            }

            current.isOnTopOf(next) -> {
                current.removeBottomWall()
                next.removeTopWall()
            }
        }
    }

    fun movePlayer(direction: Direction) {
        _player.value?.let { player ->
            _player.value = when {
                direction == Direction.UP && !player.hasTopWall -> {
                    player.updateExitDirection(direction)
                    topTileOf(player)?.apply { updateEnterDirection(Direction.DOWN) }
                }

                direction == Direction.DOWN && !player.hasBottomWall -> {
                    player.updateExitDirection(direction)
                    bottomTileOf(player)?.apply { updateEnterDirection(Direction.UP) }
                }

                direction == Direction.LEFT && !player.hasLeftWall -> {
                    player.updateExitDirection(direction)
                    leftTileOf(player)?.apply { updateEnterDirection(Direction.RIGHT) }
                }

                direction == Direction.RIGHT && !player.hasRightWall -> {
                    player.updateExitDirection(direction)
                    rightTileOf(player)?.apply { updateEnterDirection(Direction.LEFT) }
                }

                else -> player
            }
        }
    }

    private fun leftTileOf(tile: Tile?): Tile? = tile?.let { grid.getOrNull(tile.row)?.getOrNull(tile.column - 1) }

    private fun rightTileOf(tile: Tile?): Tile? = tile?.let { grid.getOrNull(tile.row)?.getOrNull(tile.column + 1) }

    private fun topTileOf(tile: Tile?): Tile? = tile?.let { grid.getOrNull(tile.row - 1)?.getOrNull(tile.column) }

    private fun bottomTileOf(tile: Tile?): Tile? = tile?.let { grid.getOrNull(tile.row + 1)?.getOrNull(tile.column) }
}