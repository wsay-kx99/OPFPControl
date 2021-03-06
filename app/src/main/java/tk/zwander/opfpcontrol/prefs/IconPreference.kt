package tk.zwander.opfpcontrol.prefs

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.preference.Preference
import androidx.preference.PreferenceViewHolder
import tk.zwander.opfpcontrol.R
import tk.zwander.opfpcontrol.util.getProperIcon
import tk.zwander.opfpcontrol.util.setProperIcon

class IconPreference : Preference {
    var resetListener: (() -> Boolean)? = null
    var defDrawable: Drawable? = null

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes) {
        initAttrs(attrs)
    }
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initAttrs(attrs)
    }
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initAttrs(attrs)
    }
    constructor(context: Context) : super(context)

    init {
        widgetLayoutResource = R.layout.icon_pref_widget

        updateIcon()
    }

    private fun initAttrs(attrs: AttributeSet) {
        val array = context.theme.obtainStyledAttributes(attrs, R.styleable.IconPreference, 0, 0)

        for (i in 0 until array.length()) {
            val a = array.getIndex(i)

            when (a) {
                R.styleable.IconPreference_def_icon -> {
                    defDrawable = array.getDrawable(a)
                }
            }
        }
    }

    override fun onBindViewHolder(holder: PreferenceViewHolder?) {
        super.onBindViewHolder(holder)

        val view = holder?.itemView

        val widgetView = view?.findViewById<LinearLayout>(android.R.id.widget_frame)

        val widgetButton = widgetView?.findViewById<ImageView>(R.id.reset)
        widgetButton?.setOnClickListener {
            onReset()
        }
    }

    fun updateIcon() {
        icon = try {
            context.getProperIcon(key)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        } ?: defDrawable
    }

    private fun onReset() {
        context.setProperIcon(key, null)
        resetListener?.invoke()
    }
}