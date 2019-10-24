package com.wessam.chooseforme

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.thekhaeng.pushdownanim.PushDown
import com.thekhaeng.pushdownanim.PushDownAnim
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dialog_help.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var viewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        PushDownAnim.setPushDownAnimTo(btn_clear, btn_choose)

        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)

        viewModel.getChoice()?.observe(this, Observer { tv_result.text = it })

        viewModel.getUserInput()?.observe(this, Observer { et_choices.setText(it) })

        viewModel.getImageVisibility()?.observe(this, Observer {
            if (it == true) iv_wheel.visibility = View.VISIBLE
            else iv_wheel.visibility = View.GONE
        })

        viewModel.getResultVisibility()?.observe(this, Observer {
            if (it == true) layout_result.visibility = View.VISIBLE
            else layout_result.visibility = View.GONE
        })

        btn_choose.setOnClickListener(this)
        btn_clear.setOnClickListener(this)

    }

    override fun onClick(view: View) {
        when (view) {
            btn_choose -> onChooseButtonClicked()
            btn_clear -> onClearButtonClicked()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.menu_help -> showHelpDialog()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun onChooseButtonClicked() {
        if (et_choices.text.toString().isEmpty()) et_choices.error =
            resources.getString(R.string.empty)
        else if (!et_choices.text.toString().contains(".")) et_choices.error =
            resources.getString(R.string.no_dot)
        else {
            if (layout_result.isVisible) {
                viewModel.setResultVisibility(false)
                viewModel.setImageVisibility(true)
            }
            Glide.with(this).load(R.mipmap.wheel).into(iv_wheel)
            Handler().postDelayed(
                {
                    viewModel.setResultVisibility(true)
                    viewModel.setImageVisibility(false)
                    viewModel.setChoice(et_choices.text.toString())
                },
                3000
            )
        }
    }

    private fun onClearButtonClicked() {
        viewModel.clear()
        iv_wheel.setImageResource(R.mipmap.wheel)
    }

    private fun showHelpDialog() {
        val helpDialog =
            LayoutInflater.from(this).inflate(R.layout.dialog_help, null)
        val alertBuilder = AlertDialog.Builder(this).setView(helpDialog).show()
        alertBuilder.window?.setBackgroundDrawableResource(android.R.color.transparent)
        alertBuilder.btn_dialog_ok.setOnClickListener {
            alertBuilder.dismiss()
        }
    }

}