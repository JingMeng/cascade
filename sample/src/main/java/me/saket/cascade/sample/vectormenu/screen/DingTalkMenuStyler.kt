package me.saket.cascade.sample.vectormenu.screen

import android.content.res.ColorStateList
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.RippleDrawable
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.updateLayoutParams
import me.saket.cascade.CascadePopupMenu
import me.saket.cascade.sample.R

/**
 * A DingTalk-inspired menu: white rounded card with a soft shadow, no group
 * dividers, comfortable 44dp rows and 20dp icons.
 */
fun dingTalkMenuStyler(context: Context): CascadePopupMenu.Styler {
  val res = context.resources

  val backgroundColor = ContextCompat.getColor(context, R.color.dingtalk_menu_background)
  val textColor = ContextCompat.getColor(context, R.color.dingtalk_menu_text)
  val iconColor = ContextCompat.getColor(context, R.color.dingtalk_menu_icon)
  val rippleColor = ContextCompat.getColor(context, R.color.dingtalk_menu_ripple)
  val arrowColor = ContextCompat.getColor(context, R.color.dingtalk_menu_arrow)

  val cardCornerRadius = res.getDimensionPixelSize(R.dimen.vector_menu_card_corner_radius)
  val itemCornerRadius = res.getDimensionPixelSize(R.dimen.vector_menu_item_corner_radius)
  val itemHorizontalMargin = res.getDimensionPixelSize(R.dimen.vector_menu_item_horizontal_margin)
  val itemVerticalMargin = res.getDimensionPixelSize(R.dimen.vector_menu_item_vertical_margin)
  val itemHeight = res.getDimensionPixelSize(R.dimen.vector_menu_item_height)
  val listVerticalPadding = res.getDimensionPixelSize(R.dimen.vector_menu_list_vertical_padding)
  val contentStartPadding = res.getDimensionPixelSize(R.dimen.vector_menu_content_start_padding)
  val contentEndPadding = res.getDimensionPixelSize(R.dimen.vector_menu_content_end_padding)
  val iconTextSpacing = res.getDimensionPixelSize(R.dimen.vector_menu_icon_text_spacing)
  val iconSize = res.getDimensionPixelSize(R.dimen.vector_menu_icon_size)
  val itemTextSize = res.getDimensionPixelSize(R.dimen.vector_menu_item_text_size)

  fun cardBackground(): GradientDrawable {
    return GradientDrawable().apply {
      shape = GradientDrawable.RECTANGLE
      setColor(backgroundColor)
      cornerRadius = cardCornerRadius.toFloat()
    }
  }

  fun itemBackground(): RippleDrawable {
    val mask = GradientDrawable().apply {
      shape = GradientDrawable.RECTANGLE
      setColor(Color.WHITE)
      cornerRadius = itemCornerRadius.toFloat()
    }
    return RippleDrawable(ColorStateList.valueOf(rippleColor), null, mask)
  }

  return CascadePopupMenu.Styler(
    background = { cardBackground() },
    menuList = { recyclerView ->
      recyclerView.setPadding(0, listVerticalPadding, 0, listVerticalPadding)
      recyclerView.clipToPadding = false
    },
    menuItem = { holder ->
      holder.setItemMargins(
        start = itemHorizontalMargin,
        top = itemVerticalMargin,
        end = itemHorizontalMargin,
        bottom = itemVerticalMargin
      )
      holder.setItemHeight(itemHeight)
      holder.setContentSpacing(
        start = contentStartPadding,
        end = contentEndPadding,
        iconSpacing = iconTextSpacing
      )
      holder.titleView.setTextSize(TypedValue.COMPLEX_UNIT_PX, itemTextSize.toFloat())
      holder.titleView.setTextColor(textColor)
      holder.iconView.updateLayoutParams<ViewGroup.LayoutParams> {
        width = iconSize
        height = iconSize
      }
      holder.iconView.drawable?.mutate()?.setTint(iconColor)
      holder.subMenuArrowView.setColorFilter(arrowColor)
      holder.groupDividerView.visibility = View.GONE
      holder.setBackground(itemBackground())
    }
  )
}
