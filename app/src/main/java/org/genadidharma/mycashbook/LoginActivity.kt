package org.genadidharma.mycashbook

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.room.Room
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.launch
import org.genadidharma.mycashbook.databinding.ActivityLoginBinding
import org.genadidharma.mycashbook.db.MyCashBookDatabase
import org.genadidharma.mycashbook.db.User

@InternalCoroutinesApi
class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var db: MyCashBookDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = MyCashBookDatabase.getDatabase(applicationContext)

        GlobalScope.launch {
            initData()
        }

        binding.btnLogin.setOnClickListener {
            val username = binding.edtUsername.text.toString()
            val password = binding.edtPassword.text.toString()

            GlobalScope.launch {
                login(username, password)
            }
        }
    }

    private fun initData() {
        val user = User("user", "user")
        if (!db.userDao().checkUser(user.username)) {
            db.userDao().addUser(user)
        }
    }

    private fun login(username: String, password: String) {
        if (db.userDao().login(username, password)) {
            startActivity(Intent(this, MainActivity::class.java))
        } else {
            runOnUiThread {
                Toast.makeText(this, "Username atau Password Salah", Toast.LENGTH_SHORT).show()
            }
        }
    }
}