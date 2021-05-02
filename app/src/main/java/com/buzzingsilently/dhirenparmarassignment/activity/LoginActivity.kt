package com.buzzingsilently.dhirenparmarassignment.activity

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.buzzingsilently.dhirenparmarassignment.R
import com.buzzingsilently.dhirenparmarassignment.base.BaseActivity
import com.buzzingsilently.dhirenparmarassignment.utility.Prefs
import com.buzzingsilently.dhirenparmarassignment.utility.Validator
import com.buzzingsilently.dhirenparmarassignment.utility.getTextValue
import com.buzzingsilently.dhirenparmarassignment.utility.isValidationNotSatisfied
import com.buzzingsilently.dhirenparmarassignment.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.include_toolbar.*

class LoginActivity : BaseActivity(), TextView.OnEditorActionListener {

    companion object {
        fun getIntent(context: Context): Intent {
            return Intent(context, LoginActivity::class.java)
        }
    }

    private lateinit var viewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        tvToolbar.setText(R.string.title_login)
        ibToolbar.visibility = View.GONE
        init()
    }

    override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            onClickedPrimary(btnPrimary)
            return true
        }
        return false;
    }

    fun onClickedPrimary(view: View) {
        super.onClick(view)

        if (view is TextView) {
            if (hasNetwork()) {
                if (view.text == getString(R.string.btn_sign_up)) {
                    if (isValidSignUp()) {
                        viewModel.signUp(
                            tietFullName.getTextValue(),
                            tietEmail.getTextValue(),
                            tietPassword.getTextValue()
                        )
                    }
                } else {
                    if (isValidSignIn()) {
                        viewModel.signIn(tietEmail.getTextValue(), tietPassword.getTextValue())
                    }
                }
            }
        }
    }

    fun onClickedSecondary(view: View) {
        super.onClick(view)

        if (view is TextView) {
            if (view.text == getString(R.string.btn_sign_up)) {
                tilFullName.visibility = View.VISIBLE
                tilConfirmPassword.visibility = View.VISIBLE
                tietPassword.imeOptions = EditorInfo.IME_ACTION_NEXT

                btnPrimary.text = getString(R.string.btn_sign_up)
                tvSecondary.text = getString(R.string.btn_sign_in)
            } else {
                tilFullName.visibility = View.GONE
                tilConfirmPassword.visibility = View.GONE
                tietPassword.imeOptions = EditorInfo.IME_ACTION_DONE

                btnPrimary.text = getString(R.string.btn_sign_in)
                tvSecondary.text = getString(R.string.btn_sign_up)
            }
        }
    }

    private fun init() {
        initView()
        setObserver()
    }

    private fun initView() {
        tietPassword.setOnEditorActionListener(this)
        tietConfirmPassword.setOnEditorActionListener(this)
    }

    private fun setObserver() {
        viewModel = ViewModelProviders.of(this).get(UserViewModel::class.java)

        viewModel.observeMessage().observe(this, Observer {
            if (it.isNullOrEmpty()) {
                tvMessage.visibility = View.INVISIBLE
                pbLoader.visibility = View.INVISIBLE
            } else {
                tvMessage.text = it
                tvMessage.visibility = View.VISIBLE
                pbLoader.visibility = View.VISIBLE
            }
        })

        viewModel.observeSignUp().observe(this, Observer {
            if (it) {
                Prefs.getInstance(application)!!.isSignedIn = true
                showSuccessDialog()
            } else {
                showSnackBar(getString(R.string.error_sign_up_failure))
            }
        })

        viewModel.observeSignIn().observe(this, Observer {
            if (it) {
                showSnackBar(getString(R.string.message_sign_in_success))
                Prefs.getInstance(application)!!.isSignedIn = true
                startActivityWithFinish(RepoListActivity.getIntent(this))
            } else {
                showSnackBar(getString(R.string.error_sign_in_failure))
            }
        })
    }

    private fun isValidSignUp(): Boolean {
        var isValid = true

        if (tilFullName.isValidationNotSatisfied(Validator(tietFullName).isValidFullName())) {
            isValid = false
        }

        if (tilEmail.isValidationNotSatisfied(Validator(tietEmail).isValidEmail())) {
            isValid = false
        }

        if (tilPassword.isValidationNotSatisfied(Validator(tietPassword).isValidPassword(true))) {
            isValid = false
        } else if (tilConfirmPassword.isValidationNotSatisfied(
                Validator(tietConfirmPassword).isValidConfirmPassword(
                    tietPassword.getTextValue()
                )
            )
        ) {
            isValid = false
        }

        return isValid
    }

    private fun isValidSignIn(): Boolean {
        var isValid = true

        if (tilEmail.isValidationNotSatisfied(Validator(tietEmail).isValidEmail())) {
            isValid = false
        }

        if (tilPassword.isValidationNotSatisfied(Validator(tietPassword).isValidPassword(false))) {
            isValid = false
        }

        return isValid
    }

    private fun showSuccessDialog() {
        val dialogClickListener = DialogInterface.OnClickListener { dialog, which ->
            when (which) {
                DialogInterface.BUTTON_POSITIVE -> {
                    startActivityWithFinish(RepoListActivity.getIntent(this))
                    dialog.dismiss()
                }
            }
        }

        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder
            .setTitle(R.string.title_sign_up_success)
            .setMessage(R.string.message_sign_up_success)
            .setCancelable(false)
            .setPositiveButton(R.string.btn_sign_up_positive, dialogClickListener)
            .show()
    }
}