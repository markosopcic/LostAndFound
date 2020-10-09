package base.ui.customviews

import android.content.Context
import android.graphics.Outline
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewOutlineProvider
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.res.ResourcesCompat
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_EXPANDED
import com.markosopcic.lostandfound.R
import kotlinx.android.synthetic.main.bottom_sheet_view.view.*

private const val MAX_BACKGROUND_ALPHA = 0.5F

class BottomSheetView : CoordinatorLayout {

    private var bottomSheetBehavior: BottomSheetBehavior<View>? = null

    private var onCloseListener: (() -> Unit)? = null

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context, attrs)
    }

    private fun init(context: Context, attrs: AttributeSet? = null) {
        LayoutInflater.from(context).inflate(R.layout.bottom_sheet_view, this, true)

        bottom_sheet_background.apply {
            setBackgroundColor(ResourcesCompat.getColor(resources, R.color.base_black, null))
            setOnClickListener { dismiss() }
        }

        pulley_container.apply {
            setBackgroundColor(ResourcesCompat.getColor(resources, R.color.base_white, null))
            outlineProvider = object : ViewOutlineProvider() {
                override fun getOutline(view: View, outline: Outline) {
                    resources.getDimension(R.dimen.base_rounded_background_radius).also {
                        outline.setRoundRect(0, 0, view.width, view.height + it.toInt(), it)
                    }
                }
            }
            clipToOutline = true
        }

        post {
            bottomSheetBehavior?.state = STATE_EXPANDED
        }
    }

    override fun addView(child: View, index: Int, params: ViewGroup.LayoutParams) =
        when (child.id) {
            R.id.pulley_container -> {
                super.addView(child, index, params)
            }
            R.id.bottom_sheet_background -> {
                super.addView(child, index, params)
            }

            else -> {
                pulley_container.addView(child, index, params)
            }
        }

    override fun onFinishInflate() {
        super.onFinishInflate()
        bottom_sheet_background.alpha = 0F

        bottomSheetBehavior = BottomSheetBehavior.from(getChildAt(1)).apply {
            bottom_sheet_background.alpha = if (state == STATE_EXPANDED) MAX_BACKGROUND_ALPHA else 0F
            addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
                override fun onSlide(bottomSheet: View, slideOffset: Float) {
                    bottom_sheet_background.alpha = slideOffset * MAX_BACKGROUND_ALPHA
                }

                override fun onStateChanged(bottomSheet: View, newState: Int) {
                    if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
                        visibility = View.GONE
                        onCloseListener?.invoke()
                    }
                }
            })
        }
    }

    fun dismiss(): Boolean = if (bottomSheetBehavior?.state != BottomSheetBehavior.STATE_COLLAPSED) {
        bottomSheetBehavior?.state = BottomSheetBehavior.STATE_COLLAPSED
        true
    } else {
        false
    }

    fun setOnCloseListener(listener: () -> Unit) {
        onCloseListener = listener
    }

}
