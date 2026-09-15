package me.saket.cascade.sample.vectormenu.screen

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.core.view.MenuCompat
import me.saket.cascade.sample.R
import me.saket.cascade.sample.vectormenu.contract.VectorMenuProvider
import me.saket.cascade.sample.vectormenu.platform.VectorMenuBaseFragment

class TimelineMenuDemoFragment :
  VectorMenuBaseFragment(R.layout.fragment_timeline_menu_demo),
  VectorMenuProvider {

  private var invitationsEnabled = true
  private var notificationsMuted = false
  private var statusText: TextView? = null

  override fun getMenuRes(): Int = R.menu.menu_timeline_demo

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    invitationsEnabled = savedInstanceState?.getBoolean(KEY_INVITATIONS_ENABLED) ?: true
    notificationsMuted = savedInstanceState?.getBoolean(KEY_NOTIFICATIONS_MUTED) ?: false
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    statusText = view.findViewById(R.id.vector_menu_status)
    view.findViewById<Button>(R.id.vector_menu_toggle_invite).setOnClickListener {
      invitationsEnabled = !invitationsEnabled
      val status = if (invitationsEnabled) {
        R.string.vector_menu_status_invite_enabled
      } else {
        R.string.vector_menu_status_invite_disabled
      }
      showStatus(status)
      invalidateMenu()
    }
  }

  override fun onSaveInstanceState(outState: Bundle) {
    outState.putBoolean(KEY_INVITATIONS_ENABLED, invitationsEnabled)
    outState.putBoolean(KEY_NOTIFICATIONS_MUTED, notificationsMuted)
    super.onSaveInstanceState(outState)
  }

  override fun onDestroyView() {
    statusText = null
    super.onDestroyView()
  }

  override fun handlePostCreateMenu(menu: Menu) {
    MenuCompat.setGroupDividerEnabled(menu, true)
  }

  override fun handlePrepareMenu(menu: Menu) {
    menu.findItem(R.id.menu_vector_invite).isEnabled = invitationsEnabled
    menu.findItem(R.id.menu_vector_notifications).title = getString(
      if (notificationsMuted) {
        R.string.vector_menu_unmute_notifications
      } else {
        R.string.vector_menu_mute_notifications
      }
    )
  }

  override fun handleMenuItemSelected(item: MenuItem): Boolean {
    return when (item.itemId) {
      R.id.menu_vector_invite -> showStatus(R.string.vector_menu_action_invite)
      R.id.menu_vector_search -> showStatus(R.string.vector_menu_action_search)
      R.id.menu_vector_notifications -> {
        notificationsMuted = !notificationsMuted
        showStatus(
          if (notificationsMuted) {
            R.string.vector_menu_action_notifications_muted
          } else {
            R.string.vector_menu_action_notifications_unmuted
          }
        )
        invalidateMenu()
        true
      }
      R.id.menu_vector_room_details -> showStatus(R.string.vector_menu_action_room_details)
      R.id.menu_vector_permissions -> showStatus(R.string.vector_menu_action_permissions)
      R.id.menu_vector_leave -> showStatus(R.string.vector_menu_action_leave)
      else -> false
    }
  }

  private fun showStatus(messageRes: Int): Boolean {
    statusText?.setText(messageRes)
    return true
  }

  private companion object {
    const val KEY_INVITATIONS_ENABLED = "invitations_enabled"
    const val KEY_NOTIFICATIONS_MUTED = "notifications_muted"
  }
}
