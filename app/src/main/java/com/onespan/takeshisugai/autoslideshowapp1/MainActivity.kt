package com.onespan.takeshisugai.autoslideshowapp1

import android.Manifest
import android.content.ContentUris
import android.content.pm.PackageManager
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.provider.MediaStore
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


class MainActivity : AppCompatActivity() {


    // private val PERMISSIONS_REQUEST_CODΩE = 100
    private val PERMISSIONS_REQUEST_CODE = 100

    private var mTimer: Timer? = null

    // タイマー用の時間のための変数
    private var mTimerSec = 0.0

    private var mHandler = Handler()


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

            //getContentsInfo()

            // 画像の情報を取得する
            val resolver = contentResolver
            val cursor = resolver.query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, // データの種類
                null, // 項目(null = 全項目)
                null, // フィルタ条件(null = フィルタなし)
                null, // フィルタ用パラメータ
                null // ソート (null ソートなし)
            )

            if (cursor.moveToNext()) {
                // indexからIDを取得し、そのIDから画像のURIを取得する
                val fieldIndex = cursor.getColumnIndex(MediaStore.Images.Media._ID)
                val id = cursor.getLong(fieldIndex)
                val imageUri = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id)

                imageView.setImageURI(imageUri)
            }
            cursor.close()

        }


    }





    /*
      start_button.setOnClickListener {
          if (mTimer == null){
              mTimer = Timer()
              mTimer!!.schedule(object : TimerTask() {
                  override fun run() {
                      mTimerSec += 0.1
                      mHandler.post {
                          timer.text = String.format("%.1f", mTimerSec)
                      }
                  }
              }, 100, 100) // 最初に始動させるまで 100ミリ秒、ループの間隔を 100ミリ秒 に設定
          }
      }



      pause_button.setOnClickListener{
          if (mTimer != null) {
              mTimer!!.cancel()
              mTimer = null
          }
      }

      reset_button.setOnClickListener{
          mTimerSec = 0.0
          timer.text = String.format("%.1f", mTimerSec)
      }



      }
      */

    private fun getContentsInfo() {

        // 画像の情報を取得する
        val resolver = contentResolver
        val cursor = resolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI, // データの種類
            null, // 項目(null = 全項目)
            null, // フィルタ条件(null = フィルタなし)
            null, // フィルタ用パラメータ
            null // ソート (null ソートなし)
        )

        // added
       // cursor.moveToNext()

        /*
        if (cursor!!.moveToFirst()) {
            do {
                // indexからIDを取得し、そのIDから画像のURIを取得する
                val fieldIndex = cursor.getColumnIndex(MediaStore.Images.Media._ID)
                val id = cursor.getLong(fieldIndex)
                val imageUri = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id)

                Log.d("ANDROID", "URI : " + imageUri.toString())
            } while (cursor.moveToNext())
        }

        */


        if (cursor.moveToFirst()) {
            // indexからIDを取得し、そのIDから画像のURIを取得する
            val fieldIndex = cursor.getColumnIndex(MediaStore.Images.Media._ID)


            val id = cursor.getLong(fieldIndex)
            val imageUri = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id)


            /* imageView.setImageURI(imageUri) */
            // ImageView() imageView = new ImageView(this)
            imageView.setImageURI(imageUri)

        }
        cursor.close()
    }

}
