package com.smartestidea.houseofthedargonquiz.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import com.smartestidea.houseofthedargonquiz.databinding.ActivityAboutBinding
import android.content.Intent
import android.net.Uri


class AboutActivity : AppCompatActivity() {
    private val binding:ActivityAboutBinding by lazy {ActivityAboutBinding.inflate(layoutInflater)}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN,
            WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
        )
        binding.btnApklis.setOnClickListener {
            openLink("https://www.apklis.cu/developer/JanselLB")
        }
        binding.btnGithub.setOnClickListener {
            openLink("https://github.com/JanselLopez/HouseOfTheDargonQuiz")
        }
    }
    private fun openLink(url:String){
        val uri: Uri = Uri.parse(url) // missing 'http://' will cause crashed
        val intent = Intent(Intent.ACTION_VIEW, uri)
        startActivity(intent)
    }
}