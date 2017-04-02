package com.example.leidong.pattern;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.eftimoff.patternview.PatternView;

public class MainActivity extends AppCompatActivity {
    private PatternView patternView;
    private String patternString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initPattern();

        final SharedPreferences sp = getSharedPreferences("MyPattern", 0);

        patternView.setOnPatternDetectedListener(new PatternView.OnPatternDetectedListener() {
            @Override
            public void onPatternDetected() {
                String myPattern = sp.getString("MyPattern", null);
                if (myPattern == null) {
                    patternString = patternView.getPatternString();
                    Toast.makeText(MainActivity.this, patternString, Toast.LENGTH_LONG).show();
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("MyPattern", patternString);
                    editor.commit();
                    patternView.clearPattern();
                    return;
                }
                else{
                    if(myPattern.equals(patternView.getPatternString())) {
                        Intent intent = new Intent(MainActivity.this, WelcomeActivity.class);
                        patternView.clearPattern();
                        startActivity(intent);
                        return;
                    }
                    else{
                        Toast.makeText(MainActivity.this, "锁屏密码错误！", Toast.LENGTH_LONG).show();
                        patternView.clearPattern();
                    }
                }
            }
        });

    }

    private void initPattern() {
        patternView = (PatternView) findViewById(R.id.patternView);
        patternView.setTactileFeedbackEnabled(false);
        patternView.setPathColor(Color.BLACK);
        patternView.setDotColor(Color.BLACK);
        patternView.setCircleColor(Color.WHITE);
    }
}
