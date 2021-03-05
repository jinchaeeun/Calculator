package com.example.calculator;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    private TextView prText; //수식
    private TextView editText; //결과 값

    //private TextView prText;
    private String operator = null; //연산자
    private String strOper = "";
    private String firstValue = ""; //첫번째 값
    private String secondValue = "";
    private boolean isInit = false; //판단

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tablelayout);
        //값
        prText = findViewById(R.id.processTXT);
        editText = findViewById(R.id.resultTXT);
        //prText = findViewById(R.id.prText);
        Log.i("ABCD", "onCreate 실행");

        for (int i = 0; i < 16; i++) {
            String buttonId = "BTN" + i;
            int resID = getResources().getIdentifier(buttonId, "id", getPackageName()); //해당 ID 값을 가지고 옴
            findViewById(resID).setOnClickListener(mClickListener);
            Log.i("ABCD", "버튼 for문 실행");
        }
    }

    /*
     1. 숫자 입력
     2. 연산자 입력
        2.1 첫번째수(firstValue)가 없으면 입력한 숫자를 첫번째수로 지정
        2.1 연산자 보관
     3. 숫자입력
     4. 연산자, = 입력
        4.1 입력한 숫자를 두번째수(secondValue)로 지정
        4.2 연산
        4.3 = 이면 모두 초기화
        4.4 = 이 아니면 계산값은 첫번째수, 연산자 보관
     */
    Button.OnClickListener mClickListener = new View.OnClickListener() {
        public void onClick(View view) {
            Button button = (Button) view;
            String clickValue = button.getText().toString();

            switch (clickValue) {
                case "+":
                case "-":
                case "*":
                case "/":
                    strOper = clickValue;
                case "=":
                    //연산자 변수에 저장

                    if ("".equals(firstValue)) {
                        firstValue = editText.getText().toString();
                        editText.setText("");
                        prText.setText(firstValue+strOper);
                    } else if (!"".equals(operator)) {
                        String secondValue = editText.getText().toString();
                        prText.setText(firstValue+strOper+secondValue);
                        Integer cal = 0;
                        switch (operator) {
                            case "+":
                                cal = Integer.parseInt(firstValue) + Integer.parseInt(secondValue);
                                break;
                            case "-":
                                cal = Integer.parseInt(firstValue) - Integer.parseInt(secondValue);
                                break;
                            case "*":
                                cal = Integer.parseInt(firstValue) * Integer.parseInt(secondValue);
                                break;
                            case "/":
                                cal = Integer.parseInt(firstValue) / Integer.parseInt(secondValue);
                                break;
                        }

                        editText.setText(cal.toString());
                        firstValue = "";
                        isInit = true;

                        if ("=".equals(clickValue)) {
                            operator = "";
                            return;
                        }
                        firstValue = cal.toString();
                    }
                    operator = clickValue;

                    break;
                // DEl 키 한 글자씩 지워져야 함
                // 두번째 값이 없을 때 기호를 지우고
                case "DEL":
                    String str = editText.getText().toString();
                    str = str.substring ( 0, str.length() - 1 );
                    editText.setText ( str );
                    break;
                //(위 조건을 제외한 모두) -> 숫자 입력될 시
                default:
                    if (isInit) {                       //ture이면
                        isInit = false;                 //false로 변경하고
                        editText.setText(clickValue);   //클릭한 값을 editText 수식 칸에 띄움
                    } else {                            //숫자가 하나 이상 입력되어 있는 상태일 때 editText 수식 칸에 추가되어 입력됨
                        editText.setText(editText.getText().toString() + clickValue);
                    }
                    Log.i("ABCD","숫자 입력 중");
                Log.i("ABCD","스위치문 실행");
            }
        }
    };
}
