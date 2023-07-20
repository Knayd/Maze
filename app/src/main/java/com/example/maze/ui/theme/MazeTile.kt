package com.example.maze.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.maze.Direction
import com.example.maze.Tile
import com.example.maze.TileType

@Composable
fun MazeTile(
    modifier: Modifier = Modifier,
    tile: Tile,
    type: TileType,
) {
    val color = when (type) {
        TileType.DEFAULT -> Color.Transparent
        TileType.PLAYER -> Color.Red
        TileType.EXIT -> Color.Blue
    }

    Box(modifier = modifier
        .background(color)
        .drawBehind {
            val strokeWidth = 5f
            val x = size.width
            val y = size.height
            val centerX = x / 2
            val centerY = y / 2
            val trailColor = Color.Green
            val borderColor = Color.Black

            val trailFromCenterToTop: () -> Unit = {
                drawLine(
                    color = trailColor,
                    start = Offset(centerX, centerY),
                    end = Offset(centerX, 0f),
                    strokeWidth = strokeWidth
                )
            }

            val trailFromCenterToBottom: () -> Unit = {
                drawLine(
                    color = trailColor,
                    start = Offset(centerX, centerY),
                    end = Offset(centerX, y),
                    strokeWidth = strokeWidth
                )
            }

            val trailFromCenterToLeft: () -> Unit = {
                drawLine(
                    color = trailColor,
                    start = Offset(centerX, centerY),
                    end = Offset(0f, centerY),
                    strokeWidth = strokeWidth
                )
            }

            val trailFromCenterToRight: () -> Unit = {
                drawLine(
                    color = trailColor,
                    start = Offset(centerX, centerY),
                    end = Offset(x, centerY),
                    strokeWidth = strokeWidth
                )
            }

            if (tile.hasTopWall) {
                drawLine(
                    color = borderColor,
                    start = Offset.Zero,
                    end = Offset(x, 0f),
                    strokeWidth = strokeWidth
                )
            }

            if (tile.hasLeftWall) {
                drawLine(
                    color = borderColor,
                    start = Offset.Zero,
                    end = Offset(0f, y),
                    strokeWidth = strokeWidth
                )
            }

            if (tile.hasRightWall) {
                drawLine(
                    color = borderColor,
                    start = Offset(x, 0f),
                    end = Offset(x, y),
                    strokeWidth = strokeWidth
                )
            }

            if (tile.hasBottomWall) {
                drawLine(
                    color = borderColor,
                    start = Offset(0f, y),
                    end = Offset(x, y),
                    strokeWidth = strokeWidth
                )
            }

            when (tile.enterDirection) {
                Direction.UP -> trailFromCenterToTop()
                Direction.DOWN -> trailFromCenterToBottom()
                Direction.LEFT -> trailFromCenterToLeft()
                Direction.RIGHT -> trailFromCenterToRight()
                null -> Unit
            }

            when (tile.exitDirection) {
                Direction.UP -> trailFromCenterToTop()
                Direction.DOWN -> trailFromCenterToBottom()
                Direction.LEFT -> trailFromCenterToLeft()
                Direction.RIGHT -> trailFromCenterToRight()
                null -> Unit
            }
        }
    )
}

@Preview
@Composable
private fun MazeTilePreview() {
    MazeTheme {
        Column {
            val tile = Tile(row = 0, column = 0).apply {
                updateEnterDirection(Direction.UP)
                updateExitDirection(Direction.RIGHT)
            }
            MazeTile(modifier = Modifier.size(50.dp), tile = tile, type = TileType.DEFAULT)
            MazeTile(modifier = Modifier.size(50.dp), tile = tile, type = TileType.PLAYER)
            MazeTile(modifier = Modifier.size(50.dp), tile = tile, type = TileType.EXIT)
        }
    }
}