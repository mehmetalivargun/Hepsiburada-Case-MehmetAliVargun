package com.mehmetalivargun.hepsiburadacase.ui.detail

import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.mehmetalivargun.hepsiburadacase.base.BaseFragment
import com.mehmetalivargun.hepsiburadacase.databinding.FragmentDetailBinding
import com.mehmetalivargun.hepsiburadacase.extentions.getDate
import com.mehmetalivargun.hepsiburadacase.extentions.load
import com.mehmetalivargun.hepsiburadacase.util.constants.KindType
import com.wnafee.vector.MorphButton
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : BaseFragment<FragmentDetailBinding>(FragmentDetailBinding::inflate) {
    private val args: DetailFragmentArgs by navArgs()
    private val viewModel: DetailViewModel by viewModels()
    override fun FragmentDetailBinding.initialize() {
        bindForKind()
    }

    private fun bindForKind() {
        when (args.kind) {
            KindType.APPS.kind -> bindForSoftware()
            KindType.MOVIES.kind -> bindForMovies()
            KindType.MUSIC.kind -> bindForMusic()
            KindType.BOOKS.kind -> bindForBooks()
        }
    }

    private fun bindForMusic() {
        viewModel.result.observe(viewLifecycleOwner, { data ->
            binding.apply {
                data.artworkUrl100?.let { artWork.load(it) }
                songName.text = data.trackName
                collectionName.text = data.collectionName
                releaseDate.text = data.releaseDate.getDate()
                playPreview.setOnStateChangedListener { changedTo, isAnimating ->
                    when (changedTo) {
                        MorphButton.MorphState.END -> {
                            videoView.visibility = View.VISIBLE
                            videoView.setVideoPath(data.previewUrl)
                            videoView.start()
                        }
                        MorphButton.MorphState.START -> {
                        }
                    }
                }
            }

        })
    }

    private fun bindForMovies() {
        viewModel.result.observe(viewLifecycleOwner, { data ->
            binding.apply {
                data.artworkUrl100?.let { artWork.load(it) }
                songName.text = data.trackName
                collectionName.text = data.primaryGenreName
                releaseDate.text = data.releaseDate.getDate()
                description.text = data.longDescription
                openTrackviewUrl.text = "Buy Movie"

                videoView.seekTo(1)
                openTrackviewUrl.setOnClickListener {
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.data = data.trackViewUrl?.toUri()
                    startActivity(intent)
                }
                playPreview.setOnStateChangedListener { changedTo, isAnimating ->
                    var startPosition = videoView.currentPosition
                    when (changedTo) {
                        MorphButton.MorphState.END -> {
                            videoView.visibility = View.VISIBLE
                            videoView.setVideoPath(data.previewUrl)
                            videoView.seekTo(startPosition)
                            when (videoView.isPlaying) {
                                true -> videoView.resume()
                                false -> videoView.start()
                            }


                        }
                        MorphButton.MorphState.START -> {
                            startPosition = videoView.currentPosition
                            videoView.pause()


                        }
                    }
                }
            }
        })
    }

    private fun bindForBooks() {
        viewModel.result.observe(viewLifecycleOwner, { data ->
            data.artworkUrl100?.let { binding.artWork.load(it) }
            binding.apply {
                songName.text = data.trackName
                collectionName.text = data.artistName
                releaseDate.text = data.releaseDate.getDate()
                description.text = Html.fromHtml(data.description, 1)
                openTrackviewUrl.text = "Read Book"
                openTrackviewUrl.setOnClickListener {
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.data = data.trackViewUrl?.toUri()
                    startActivity(intent)
                }
            }
        })
    }

    private fun bindForSoftware() {
        viewModel.softwareResult.observe(viewLifecycleOwner, { data ->
            data.artworkUrl512?.let { it1 -> binding.artWork.load(it1) }
            binding.apply {
                screenShotRV.visibility = View.VISIBLE
                songName.text = data.trackName
                collectionName.text = data.artistName
                releaseDate.text = data.releaseDate.getDate()
                description.text = data.description
                screenShotRV.visibility = View.VISIBLE
                openTrackviewUrl.setOnClickListener {
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.data = data.trackViewUrl?.toUri()
                    startActivity(intent)
                }
                screenShotRV.adapter = data.screenshotUrls?.let { it1 ->
                    ScreenShotAdapter(
                        it1
                    )
                }
            }
        })
    }


}