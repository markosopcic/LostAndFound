package mapoptions.ui

import base.ui.BaseFragment
import com.markosopcic.lostandfound.R
import kotlinx.android.synthetic.main.fragment_map_options.*
import org.koin.android.ext.android.inject

class MapOptionsFragment : BaseFragment<MapOptionsViewState>(R.layout.fragment_map_options) {
    override val viewModel: MapOptionsViewModel by inject()

    override fun initialiseView() {
        map_options_bottom_sheet.setOnCloseListener {
            viewModel.closeMapOptions()
        }
        map_options_radiusSlider.addOnChangeListener { _, value, _ ->
            map_options_radiusSliderText.text = getString(R.string.map_options_radius_text, value.toInt())
            updateMapOptions()
        }

        map_options_dayNumberSlider.addOnChangeListener { slider, _, _ ->
            map_options_numberOfDays.text =
                getString(R.string.map_options_days_text, slider.values[0].toInt(), slider.values[1].toInt())
            updateMapOptions()
        }
    }

    private fun updateMapOptions() {
        viewModel.updateMapOptions(
            map_options_radiusSlider.value.toLong(),
            map_options_dayNumberSlider.values[0].toLong(),
            map_options_dayNumberSlider.values[1].toLong()
        )
    }

    override fun render(viewState: MapOptionsViewState) = when (viewState) {
        is MapOptionsViewState.Options -> {
            map_options_radiusSlider.value = viewState.radius.toFloat()
            map_options_dayNumberSlider.values = mutableListOf(viewState.minDaysAgo.toFloat(), viewState.maxDaysAgo.toFloat())
        }
    }

}
