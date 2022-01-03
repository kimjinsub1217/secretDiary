package com.example.toyproject003_secretdiary

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.ColorFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.NumberPicker
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.edit

class MainActivity : AppCompatActivity() {
    //뷰를 lazy하게 초기화 해주는 이유는 MainActivity 생성된 시점에는 뷰가 그려지기 않아서.
//뷰가 그려졌다고 알려주는 시점은 override fun onCreate 부분
    private val numberPicker1: NumberPicker by lazy {
        findViewById<NumberPicker>(R.id.numberPicker1)
            .apply {
                minValue = 0
                maxValue = 9
            }

    }

    private val numberPicker2: NumberPicker by lazy {
        findViewById<NumberPicker>(R.id.numberPicker2)
            .apply {
                minValue = 0
                maxValue = 9
            }
    }

    private val numberPicker3: NumberPicker by lazy {
        findViewById<NumberPicker>(R.id.numberPicker3)
            .apply {
                minValue = 0
                maxValue = 9
            }


    }

    private val openButton: AppCompatButton by lazy {
        findViewById<AppCompatButton>(R.id.openButton)
    }

    private val changePasswordButton: AppCompatButton by lazy {
        findViewById<AppCompatButton>(R.id.changePasswordButton)
    }

    //    버튼을 눌렀을때 동작을 하는 동안에는 다른 작업을 할 수 없게 예외처리
    private var changePasswordMode = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        numberPicker1
        numberPicker2
        numberPicker3

//        password를 기기에 저장하는 방법
//        1.LocalDB
//        2.파일에 직접 담기(SharedPreferences)

        openButton.setOnClickListener {

            if (changePasswordMode) {
                Toast.makeText(this, "비밀번호 변경 중입니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val passwordPreferences = getSharedPreferences("password", Context.MODE_PRIVATE)
//            다른 app과 공유하지 않기 위해 MODE_PRIVATE

            val passwordFromUser =
                "${numberPicker1.value}${numberPicker2.value}${numberPicker3.value}"

            if (passwordPreferences.getString("password", "000").equals(passwordFromUser)) {
                //패스워드 성공


               startActivity(Intent(this,DiaryActivity::class.java))
            } else {
                showErrorAlertDialog()
            }

        }

        changePasswordButton.setOnClickListener {
            val passwordPreferences = getSharedPreferences("password", Context.MODE_PRIVATE)
            val passwordFromUser =
                "${numberPicker1.value}${numberPicker2.value}${numberPicker3.value}"
            if (changePasswordMode) {
//                번호를 저장하는 기능
                passwordPreferences.edit(true) {
                    putString("password", passwordFromUser)
                }
                changePasswordMode = false
                changePasswordButton.setBackgroundColor(Color.parseColor("#B963C8"))


            } else {
//                changePasswordMode 가 활성화 비밀번호가 맞는지 체크
//            다른 app과 공유하지 않기 위해 MODE_PRIVATE
                if (passwordPreferences.getString("password", "000").equals(passwordFromUser)) {
                    //패스워드 성공
                    changePasswordMode = true
                    Toast.makeText(this, "변경할 패스워드를 입력해주세요", Toast.LENGTH_SHORT).show()
                    changePasswordButton.setBackgroundColor(Color.BLUE)

                } else {
                    showErrorAlertDialog()
                }


            }

        }
    }

    private fun showErrorAlertDialog() {
        //실패
        AlertDialog.Builder(this)
            .setTitle("실패!!")
            .setMessage("비밀번호가 잘못되었습니다.")
            .setPositiveButton("확인") { _, _ -> }
            .create()
            .show()
    }
}

