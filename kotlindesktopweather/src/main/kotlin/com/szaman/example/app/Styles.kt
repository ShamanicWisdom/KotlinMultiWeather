package com.szaman.example.app

import javafx.geometry.Pos
import javafx.scene.text.FontWeight
import tornadofx.*

class Styles : Stylesheet() {
    companion object {
        val heading by cssclass()
        val headerLabel by cssclass()
        val label by cssclass()
        val textfield by cssclass()
        val headerHbox by cssclass()
        val leftHbox by cssclass()
        val emptySpaceHBox by cssclass()
        val centerLabel by cssclass()
        val pingButton by cssclass()
        val spacingHbox by cssclass()
    }

    init {
        heading {
            padding = box(10.px)
            fontSize = 20.px
            fontWeight = FontWeight.BOLD
        }

        headerHbox {
            alignment = Pos.CENTER
        }

        leftHbox {
            alignment = Pos.CENTER_LEFT
        }

        emptySpaceHBox{
            padding = box(35.px)
        }

        spacingHbox{
            alignment = Pos.CENTER_LEFT
            spacing = 5.px
        }

        label {
            padding = box(10.px)
            fontSize = 15.px
            fontWeight = FontWeight.BOLD
        }

        headerLabel {
            padding = box(10.px)
            fontSize = 20.px
            fontWeight = FontWeight.BOLD
        }

        centerLabel {
            padding = box(10.px)
            fontSize = 15.px
            fontWeight = FontWeight.BOLD
        }

        pingButton{
            spacing = 5.px
        }

        root {
            prefHeight = 600.px
            prefWidth = 550.px
        }
    }
}