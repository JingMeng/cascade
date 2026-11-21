package me.saket.cascade.sample.vectormenu.contract

import android.view.Menu
import android.view.MenuItem
import androidx.annotation.MenuRes

interface VectorMenuProvider {
  @MenuRes
  fun getMenuRes(): Int

  fun handlePostCreateMenu(menu: Menu) = Unit

  fun handlePrepareMenu(menu: Menu) = Unit

  fun handleMenuItemSelected(item: MenuItem): Boolean
}
