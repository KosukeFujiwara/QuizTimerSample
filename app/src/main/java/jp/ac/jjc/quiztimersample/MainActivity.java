package jp.ac.jjc.quiztimersample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    Timer timer;
    CountTask task;
    Handler handler;
    int count = 20;
    final String TimeOver = "時間切れです・・・。";
    final String CorrectAns = "正解！";
    final String WrongAns = "不正解・・・";
    final int TimeCountOK = 1;
    final int TimeCountNG = 0;
    int checkedID;
    TextView Res;
    RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        radioGroup = (RadioGroup)findViewById(R.id.Rgroup);
        RadioListener listener = new RadioListener();
        radioGroup.setOnCheckedChangeListener(listener);

        Res = findViewById(R.id.responseText);
        checkedID = radioGroup.getCheckedRadioButtonId();
        timer = new Timer();
        task = new CountTask();
        timer.schedule(task, 1000, 1000);

        handler = new Handler(){
            @Override
            public void handleMessage(Message msg){
                TextView remainingTime = findViewById(R.id.timerText);
                switch (msg.what){
                    case 1:
                        remainingTime.setText(Integer.toString(count));
                        break;
                    case 0:
                        remainingTime.setText(Integer.toString(count));
                        Res.setText(TimeOver);
                        setSelectedFalse();
                        break;
                }
            }
        };
    }

    private class RadioListener implements RadioGroup.OnCheckedChangeListener{
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int checkedID){
            if(checkedID==R.id.rbtn18) Res.setText(CorrectAns);
            else Res.setText(WrongAns);
            timer.cancel();
            setSelectedFalse();
        }
    }

    class CountTask extends TimerTask{
        @Override
        public void run(){
            count--;
            Message msg = Message.obtain();

            if(count>0){
                msg.what = TimeCountOK;
                handler.sendMessage(msg);
            }else if(count<=0){
                msg.what = TimeCountNG;
                handler.sendMessage(msg);
                timer.cancel();
            }
        }
    }

    private void setSelectedFalse(){ //ラジオボタン操作無効化
        radioGroup.setEnabled(false);
    }
}