package com.example.colorescape

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.GDX
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Rectangle
import com.badlogic.gdx.graphics.Vector2
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.Texture.TextureFilter.Linear
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import ktx.app.KtxGame
import ktx.app.KtxScreen
import ktx.app.clearScreen
import ktx.assets.disposeSafely
import ktx.assets.toInternalFile
import ktx.async.KtxAsync
import ktx.graphics.use

class Main : ApplicationAdapter() {
    private lateinit var batch: SpriteBatch
    private lateinit var playerTexture: Texture
    private lateinit var redTileTexture: Texture
    private lateinit var blueTiletexture: Texture
    private lateinit var player: Rectangle

    private var velocity = Vector2()
    private var gravity = -0.5f
    private var currentColor = "red"
    private val redPlatforms = mutableListOf<Rectangle>()
    private val bluePlatforms = mutableListOf<Rectangle>()


    override fun create() {
        batch = SpriteBatch()
        playerTexture = Texture("player.png")
        redTileTexture = Texture("redTileTexture.png")
        blueTiletexture = Texture("blueTiletexture.png")
        player = Rectangle(50f, 150f, 32f, 32f)

        redPlatforms.add(Rectangle(100f, 100f, 64f, 16f))
        bluePlatforms.add(Rectangle(100f, 100f, 64f, 16f))
    }

    override fun render() {
        handleInput()
        updatePlayer()

        Gdx.gl.glClearColor(0f, 0f, 0f,1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        batch.begin()
        if (currentColor == "red") {
            redPlatforms.forEach {batch.draw(redTileTexture, it.x, it.y)}
        } else{
            bluePlatforms.forEach {batch.draw(blueTileTexture, it.x, it.y)}
        }
        batch.draw(playerTexture, player.x, player.y)
        batch.end()
    }

    private fun handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            currentColor = if (currentColor == "red") "blue" else "red"
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
            player.x -= 3
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
            player.x += 3
        }

    }

    private fun updatePlayer() {
        velocity.y += gravity
        player.y += velocity.y

        val platforms = if (currentColor == "red") redPlatforms else bluePlatforms

        for (platform in platforms) {
            if (player.overlaps(platform)) {
                player.y = platform.y + platform.height
                velocity.y = Of
            }
        }

        if (player.y < 0) {
            player.y = Of
            velocity.y = Of
        }
    }
    override fun dispose() {
        batch.dispose()
        playerTexture.dispose()
        redTileTexture.dispose()
        blueTiletexture.dispose()
    }
}


