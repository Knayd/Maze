package com.example.maze.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.maze.Tile
import com.example.maze.TileType

@Composable
fun MazeTile(
    modifier: Modifier = Modifier,
    tile: Tile,
    type: TileType
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
            val x = size.width - strokeWidth
            val y = size.height - strokeWidth
            val borderColor = Color.Black

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
        }
    ) {
        Text(modifier = Modifier.align(Alignment.Center), text = "${tile.row}, ${tile.column}")
    }
}

@Preview
@Composable
private fun MazeTilePreview() {
    MazeTheme {
        MazeTile(modifier = Modifier.size(50.dp), tile = Tile(row = 0, column = 0), type = TileType.DEFAULT)
        MazeTile(modifier = Modifier.size(50.dp), tile = Tile(row = 0, column = 0), type = TileType.PLAYER)
        MazeTile(modifier = Modifier.size(50.dp), tile = Tile(row = 0, column = 0), type = TileType.EXIT)
    }
}