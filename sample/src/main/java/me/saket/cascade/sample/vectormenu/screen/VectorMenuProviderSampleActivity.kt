package me.saket.cascade.sample.vectormenu.screen

import android.os.Bundle
import android.view.Gravity
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import me.saket.cascade.CascadePopupMenu
import me.saket.cascade.overrideAllPopupMenus
import me.saket.cascade.sample.R

class VectorMenuProviderSampleActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_vector_menu_provider)

    val toolbar = findViewById<Toolbar>(R.id.vector_menu_toolbar)
    setSupportActionBar(toolbar)
    supportActionBar?.setTitle(R.string.vector_menu_activity_title)

    val menuStyler = dingTalkMenuStyler(this)
    val popupElevation = resources.getDimension(R.dimen.vector_menu_popup_elevation)
    val shadowMargin = resources.getDimensionPixelSize(R.dimen.vector_menu_popup_shadow_margin)

    val topOffset = resources.getDimensionPixelSize(R.dimen.vector_menu_popup_top_offset)
    val endOffset = resources.getDimensionPixelSize(R.dimen.vector_menu_popup_end_offset)

    toolbar.overrideAllPopupMenus(
      with = { context, anchor ->
        CascadePopupMenu(
          context = context,
          anchor = anchor,
          gravity = Gravity.END,
          styler = menuStyler
        ).apply {
          setMenuWidth(
            context.resources.getDimensionPixelSize(R.dimen.vector_menu_popup_width)
          )
          setPopupElevation(popupElevation)
          setPopupMargins(
            start = shadowMargin,
            end = shadowMargin,
            bottom = shadowMargin
          )
        }
      }
    ) {
      showWithOffsets(
        // Gravity.END aligns the popup's right edge with the anchor's right edge.
        // A negative x offset moves it left, leaving a right-side gap.
        xOffset = -endOffset,
        yOffset = topOffset,
        overlapAnchor = false
      )
    }

    if (savedInstanceState == null) {
      supportFragmentManager.beginTransaction()
        .replace(R.id.vector_menu_fragment_container, TimelineMenuDemoFragment())
        .commit()
    }
  }
}
