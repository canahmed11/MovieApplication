package com.ahmeturunveren.movieapp.ui.login

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.navigation.fragment.findNavController
import com.ahmeturunveren.movieapp.R
import com.ahmeturunveren.movieapp.databinding.FragmentLoginWebViewBinding
import com.ahmeturunveren.movieapp.util.constants.Constants
import com.ahmeturunveren.movieapp.util.extensions.gone
import com.ahmeturunveren.movieapp.util.extensions.toastMessage
import com.ahmeturunveren.movieapp.util.extensions.visible

class LoginWebView : Fragment() {

    private lateinit var binding: FragmentLoginWebViewBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginWebViewBinding.inflate(inflater,container,false)
        return binding.root
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding){
            webviewProgressbar.visible()
            webview.webViewClient = LoginWebViewClient()
            webview.settings.javaScriptEnabled = true
            webview.loadUrl(Constants.WEBVIEW_LOGIN)
        }
    }

    private inner class LoginWebViewClient : WebViewClient() {

        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
            binding.webviewProgressbar.gone()
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            if (url == Constants.WEBVIEW_LOGIN) {
                binding.webview.evaluateJavascript(
                    "document.getElementById('username').value = '';" +
                            "document.getElementById('password').value = '';" +
                            "document.getElementById('login-form').submit();"
                ) {}
            }
            if(url?.startsWith(Constants.WEBVIEW_DASHBOARD) == true){
                val action = LoginWebViewDirections.actionLoginWebViewToPopularMovieScreen()
                findNavController().navigate(action)
                requireContext().toastMessage(getString(R.string.login_successful_message))
            }
        }
    }
}