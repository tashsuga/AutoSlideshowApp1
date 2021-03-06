package com.onespan.takeshisugai.autoslideshowapp1

import android.Manifest
import android.content.ContentUris
import android.content.pm.PackageManager
import android.database.Cursor
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.provider.MediaStore
import kotlinx.android.synthetic.main.activity_main.*
//import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import android.util.Log


class MainActivity : AppCompatActivity() {


    // private val PERMISSIONS_REQUEST_CODΩE = 100
    private val PERMISSIONS_REQUEST_CODE = 100

    private var mTimer: Timer? = null

    // タイマー用の時間のための変数
    private var mTimerSec = 0.0

    private var mHandler = Handler()


    private var cursor: Cursor? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        // Android 6.0以降の場合
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // パーミッションの許可状態を確認する
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                // 許可されている
                getContentsInfo()
            } else {
                // 許可されていないので許可ダイアログを表示する
                requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), PERMISSIONS_REQUEST_CODE)
            }
            // Android 5系以下の場合
        } else {
            getContentsInfo()
        }



        start_button.setOnClickListener {

            /*
            val requestCode = 0
            val grantResults: IntArray

            when (requestCode) {
                PERMISSIONS_REQUEST_CODE -> {
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        Log.d("ANDROID", "許可された")
                    } else {
                        Log.d("ANDROID", "許可されなかった")

                        // 25th/ Jab.
                        // super.onDestroy()
                        finish()

                    }
                }
            }
   */

            if (cursor!!.moveToNext()) {
            } else {
                cursor!!.moveToFirst()
            }
            // indexからIDを取得し、そのIDから画像のURIを取得する
            val fieldIndex = cursor!!.getColumnIndex(MediaStore.Images.Media._ID)
            val id = cursor!!.getLong(fieldIndex)
            val imageUri = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id)
            imageView.setImageURI(imageUri)
        }

        pause_button.setOnClickListener {

            if (cursor!!.moveToPrevious()) {
            } else {
                cursor!!.moveToLast()
            }

            // indexからIDを取得し、そのIDから画像のURIを取得する
            val fieldIndex = cursor!!.getColumnIndex(MediaStore.Images.Media._ID)
            val id = cursor!!.getLong(fieldIndex)
            val imageUri = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id)
            imageView.setImageURI(imageUri)
        }

        reset_button.setOnClickListener {


            if (mTimer == null) {
                mTimer = Timer()
                mTimer!!.schedule(object : TimerTask() {
                    override fun run() {
                       // mTimerSec += 0.1
                        mHandler.post {

                            // timer.text = String.format("%.1f", mTimerSec)
                            // ここにProcedureを記載。

                           if (cursor!!.moveToNext()) {
                            } else {
                                cursor!!.moveToFirst()
                            }


                            // indexからIDを取得し、そのIDから画像のURIを取得する
                            val fieldIndex = cursor!!.getColumnIndex(MediaStore.Images.Media._ID)
                            val id = cursor!!.getLong(fieldIndex)
                            val imageUri = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id)
                            imageView.setImageURI(imageUri)

                            // ここまで
                        }
                    }
                }, 2000, 2000) // 最初に始動させるまで 100ミリ秒、ループの間隔を 100ミリ秒 に設定
            } else {
                mTimer!!.cancel()
                mTimer = null
            }
        }
    }


// 毛猛

        override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
            when (requestCode) {
                PERMISSIONS_REQUEST_CODE ->
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        Log.d("ANDROID", "許可された")
                        getContentsInfo()
                    } else {
                        Log.d("ANDROID", "許可されなかった")

                        // 25th/ Jab.
                        // super.onDestroy()
                        finish()

                    }
            }
        }

    private fun getContentsInfo() {
        val resolver = this.contentResolver
        this.cursor = resolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI, // データの種類
            null, // 項目(null = 全項目)
            null, // フィルタ条件(null = フィルタなし)
            null, // フィルタ用パラメータ
            null // ソート (null ソートなし)
        )

        if (cursor!!.moveToFirst()) {
            // indexからIDを取得し、そのIDから画像のURIを取得する
            val fieldIndex = cursor!!.getColumnIndex(MediaStore.Images.Media._ID)
            val id = cursor!!.getLong(fieldIndex)
            val imageUri = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id)

            imageView.setImageURI(imageUri)
        }

         fun onDestroy() {
            super.onDestroy()

            cursor?.close()
        }
    }

}


