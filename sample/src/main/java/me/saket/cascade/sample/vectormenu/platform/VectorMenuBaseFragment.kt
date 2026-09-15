package me.saket.cascade.sample.vectormenu.platform

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.annotation.LayoutRes
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import me.saket.cascade.sample.vectormenu.contract.VectorMenuProvider

abstract class VectorMenuBaseFragment(
  @LayoutRes contentLayoutId: Int
) : Fragment(contentLayoutId) {

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    setupMenu()
  }

  private fun setupMenu() {
    if (this !is VectorMenuProvider) return
    if (getMenuRes() == -1) return

    val menuHost: MenuHost = requireActivity()
    menuHost.addMenuProvider(
      object : MenuProvider {
        override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
          menuInflater.inflate(getMenuRes(), menu)
          handlePostCreateMenu(menu)
        }

        override fun onPrepareMenu(menu: Menu) {
          handlePrepareMenu(menu)
        }

        override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
          return handleMenuItemSelected(menuItem)
        }
      },
      viewLifecycleOwner,
      Lifecycle.State.RESUMED
    )
  }

  protected fun invalidateMenu() {
    val menuHost: MenuHost = requireActivity()
    menuHost.invalidateMenu()
  }
}
