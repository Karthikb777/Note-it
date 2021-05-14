package com.karthik.blissv2alpha10

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.browser.customtabs.CustomTabsIntent
import kotlinx.android.synthetic.main.activity_about.*

class AboutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        goBackBtn.setOnClickListener {
            finish()
        }

        githubLinkBtn.setOnClickListener {
            val webpage: Uri = Uri.parse("https://github.com/Karthikb777")

            CustomTabsIntent.Builder().build()
                    .launchUrl(this, webpage);
        }
    }
}