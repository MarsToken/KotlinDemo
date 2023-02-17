package com.example.administrator.kotlindemo.tools

/**
 * 2.类委托，类似静态代理，区别：委托强调结果，静态代理强调过程，
 * 实战，AudioHelper,录音模块交给AudioRecorder,播放模块交给AudioPlayer
 *
 * @author WangMaoBo
 * @since 2023/2/17
 */

fun main() {
    val audioPlayer = AudioPlayer()
    val audioRecorder = AudioRecorder()
    val audioHelper = AudioHelper(audioRecorder, audioPlayer)
    audioHelper.recordAudio()
    audioHelper.playAudio()
}

class AudioHelper(private val audioRecorder: IAudioRecorder, private val audioPlayer: IAudioPlayer) :
    IAudioRecorder by audioRecorder, IAudioPlayer by audioPlayer{
        // do something
    }

class AudioPlayer : IAudioPlayer {
    override fun playAudio() {
        println("audio play")
    }

}

class AudioRecorder : IAudioRecorder {
    override fun recordAudio() {
        println("record video")
    }
}

interface IAudioRecorder {
    fun recordAudio()
}

interface IAudioPlayer {
    fun playAudio()
}