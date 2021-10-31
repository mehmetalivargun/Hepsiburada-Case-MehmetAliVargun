package com.mehmetalivargun.hepsiburadacase.ui.detail

import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.util.TypedValue
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
import com.mehmetalivargun.hepsiburadacase.ui.adapter.ScreenShotAdapter
import com.mehmetalivargun.hepsiburadacase.util.constants.DetailState
import com.mehmetalivargun.hepsiburadacase.util.constants.KindType
import com.wnafee.vector.MorphButton
import dagger.hilt.android.AndroidEntryPoint
import android.app.Activity
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.mehmetalivargun.hepsiburadacase.R


@AndroidEntryPoint
class DetailFragment : BaseFragment<FragmentDetailBinding>(FragmentDetailBinding::inflate) {
    private val args: DetailFragmentArgs by navArgs()
    private val viewModel: DetailViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        hideKeyboard(requireActivity())
        stateObserver()
        bindForKind()
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    private fun stateObserver() {
        viewModel.state.observe(viewLifecycleOwner, {
            when (it) {
                DetailState.Loading -> {
                    binding.apply {
                        allViewLayout.visibility = View.GONE
                        errorLayout.visibility = View.GONE
                        loading.visibility = View.VISIBLE
                    }
                }
                DetailState.Failure -> {
                    binding.allViewLayout.visibility = View.GONE
                    binding.errorLayout.visibility = View.VISIBLE
                    bindingForError()
                }
                DetailState.Success -> {
                    binding.apply {
                        allViewLayout.visibility = View.VISIBLE
                        errorLayout.visibility = View.GONE
                        loading.visibility = View.GONE
                    }
                }
                null -> {
                }
            }
        })
    }

    //hide keyboard if its necessary
    fun hideKeyboard(activity: Activity) {
        val imm: InputMethodManager =
            activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        var view = activity.currentFocus
        if (view == null) {
            view = View(activity)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }


    private fun bindingForError() {
        binding.retryButton.setOnClickListener {
            viewModel.initialData()
        }
    }


    private fun bindForKind() {
        when (args.kind) {
            KindType.APPS.kind -> bindForSoftware()
            KindType.MOVIES.kind -> bindForMovies()
            KindType.MUSIC.kind -> bindForMusic()
            KindType.BOOKS.kind -> bindForBooks()
        }
    }

    private fun openTrackviewUrl(url: String?) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = url?.toUri()
        startActivity(intent)

    }

    private fun bindForMusic() {
        viewModel.result.observe(viewLifecycleOwner, { data ->
            binding.apply {
                data.artworkUrl100?.let { artWork.load(it) }
                songName.text = data.trackName
                collectionName.text = data.collectionName
                releaseDate.text = data.releaseDate.getDate()
                openTrackviewUrl.text = getString(R.string.button_buy_music)
                trailerTitleTV.visibility = View.GONE
                descriptionTitleTV.visibility = View.GONE
                dividerDescription.visibility = View.GONE
                dividerTrailer.visibility = View.GONE
                openTrackviewUrl.setOnClickListener {
                    openTrackviewUrl(data.trackViewUrl)
                }
                playPreview.setOnStateChangedListener { changedTo, _ ->
                    when (changedTo) {
                        MorphButton.MorphState.END -> {
                            Toast.makeText(
                                requireContext(),
                                "Playing Audio Preview",
                                Toast.LENGTH_SHORT
                            ).show()
                            data.previewUrl?.let { viewModel.playAudio(it) }
                        }
                        MorphButton.MorphState.START -> {
                            Toast.makeText(requireContext(), "Stopped", Toast.LENGTH_SHORT).show()
                            viewModel.pauseAudio()
                        }
                        else -> {
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
                description.maxLines = 6
                videoView.visibility = View.VISIBLE
                openTrackviewUrl.text = getString(R.string.button_buy_movie)

                videoView.seekTo(1)
                openTrackviewUrl.setOnClickListener {
                    openTrackviewUrl(data.trackViewUrl)
                }
                var startPosition = videoView.currentPosition
                playPreview.setOnStateChangedListener { changedTo, isAnimating ->
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
                        null->{}
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
                description.maxLines = 10
                description.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18F)
                trailerTitleTV.visibility = View.GONE
                description.text = Html.fromHtml(data.description, 1)
                openTrackviewUrl.text = getString(R.string.button_read_book)
                playPreview.visibility = View.GONE
                openTrackviewUrl.setOnClickListener {
                    openTrackviewUrl(data.trackViewUrl)
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
                trailerTitleTV.text = getString(R.string.screenshots)
                playPreview.visibility = View.GONE
                openTrackviewUrl.setOnClickListener {
                    openTrackviewUrl(data.trackViewUrl)
                }
                screenShotRV.adapter = data.screenshotUrls?.let { it1 ->
                    ScreenShotAdapter(
                        it1
                    )
                }
            }
        })
    }

    override fun onDestroyView() {
        viewModel.releaseMediaPlayer()
        super.onDestroyView()
    }

}