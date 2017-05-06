package com.holybible.myplayerdemo;

import android.Manifest;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import net.protyposis.android.mediaplayer.MediaPlayer;
import net.protyposis.android.mediaplayer.UriSource;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class MainActivity extends AppCompatActivity {

    @BindView(R.id.edit_speed)
    EditText mSpeedEdt;

    String scarletMp3 = Environment.getExternalStorageDirectory().getAbsolutePath() + "/"
            + "audiowork/Scarlet.mp3";
    String senrenopMp3 = Environment.getExternalStorageDirectory().getAbsolutePath() + "/"
            + "audiowork/senrenbankaop.mp3";
    String readWav = Environment.getExternalStorageDirectory().getAbsolutePath() + "/"
            + "audiowork/read_sentence.wav";
    String readAmr = Environment.getExternalStorageDirectory().getAbsolutePath() + "/"
            + "audiowork/read_sentence_ffmpeg_out.amr";
    String voa = Environment.getExternalStorageDirectory().getAbsolutePath() + "/"
            + "audiowork/voa_4956.mp3";

    MediaPlayer mPlayer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mPlayer = new MediaPlayer();
        mPlayer.setOnPreparedListener(mOnPreparedListener);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPlayer.release();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        MainActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @OnClick(R.id.button_play_file)
    void clickPlayFile() {
        MainActivityPermissionsDispatcher.playFileWithCheck(this);
    }

    @OnClick(R.id.button_change_speed)
    void clickChange() {
        String text = mSpeedEdt.getText().toString();
        float speed = Float.parseFloat(text);
        mPlayer.setPlaybackSpeed(speed);
    }

    @NeedsPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
    void playFile() {
        try {
            mPlayer.setDataSource(new UriSource(this, Uri.parse(senrenopMp3)));
            mPlayer.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    MediaPlayer.OnPreparedListener mOnPreparedListener = new MediaPlayer.OnPreparedListener() {
        @Override
        public void onPrepared(MediaPlayer mp) {
            mp.start();
        }
    };
}
