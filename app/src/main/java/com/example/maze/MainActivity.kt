package com.example.maze

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import com.example.maze.ui.theme.MazeTheme
import com.example.maze.ui.theme.MazeTile
import kotlin.math.absoluteValue

class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MazeTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colorScheme.background) {
                    val maze by viewModel.mazeFlow.collectAsState()
                    val player by maze.player.collectAsState()
                    val exit = maze.exit

                    BoxWithConstraints {
                        val width = maxWidth
                        val height = maxHeight
                        val rows = maze.rows
                        val columns = maze.columns

                        val tileSize = minOf(height / rows, width / columns)

                        Column(
                            modifier = Modifier
                                .pointerInput(Unit) {
                                    detectDragGestures { change, _ ->
                                        player?.let {
                                            val x = change.position.x
                                            val y = change.position.y
                                            val tileSizePx = tileSize.toPx()

                                            val playerCenterX = (it.column + 0.5f) * tileSizePx
                                            val playerCenterY = (it.row + 0.5f) * tileSizePx

                                            val dx = x - playerCenterX
                                            val dy = y - playerCenterY

                                            val absDx = dx.absoluteValue
                                            val absDy = dy.absoluteValue

                                            if (absDx > tileSizePx || absDy > tileSizePx) {
                                                if (absDx > absDy) {
                                                    if (dx > 0) {
                                                        maze.movePlayer(Direction.RIGHT)
                                                    } else {
                                                        maze.movePlayer(Direction.LEFT)
                                                    }
                                                } else {
                                                    if (dy > 0) {
                                                        maze.movePlayer(Direction.DOWN)
                                                    } else {
                                                        maze.movePlayer(Direction.UP)
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                        ) {
                            maze.grid.forEach { row ->
                                Row {
                                    row.forEach { tile ->
                                        val type = when (tile) {
                                            player -> TileType.PLAYER
                                            exit -> TileType.EXIT
                                            else -> TileType.DEFAULT
                                        }
                                        MazeTile(modifier = Modifier.size(tileSize), tile = tile, type = type)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MazeTheme {
        Greeting("Android")
    }
}