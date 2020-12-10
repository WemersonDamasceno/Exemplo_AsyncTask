package com.example.exemplo_asynctask;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.AsyncTaskLoader;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
Button btnRun;
TextView tv_tempo;
TextView tv_result;
EditText in_tempo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btnRun = findViewById(R.id.btn_run);
        tv_tempo = findViewById(R.id.tv_tempo);
        tv_result = findViewById(R.id.tv_result);
        in_tempo = findViewById(R.id.in_tempo);


        btnRun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AsyncTaskRunner runner = new AsyncTaskRunner();
                String tempoAguardado = in_tempo.getText().toString();
                runner.execute(tempoAguardado);
            }
        });


    }

    private class AsyncTaskRunner extends AsyncTask<String,String,String>{
        private String resp;
        ProgressDialog progressDialog;
        @Override
        protected String doInBackground(String... strings) {
            publishProgress("Aguardando....");
            try {
                int tempo = Integer.parseInt(strings[0]) * 1000;
                Thread.sleep(tempo);
                resp = "Aguardando por "+strings[0] + " segundos...";
            }catch (InterruptedException e){
                e.printStackTrace();
                resp = e.getMessage();
            }catch (Exception e){
                e.printStackTrace();
                resp = e.getMessage();
            }
            return resp;
        }


        @Override
        protected void onPostExecute(String s) {
            progressDialog.dismiss();
            tv_result.setText(s);
        }

        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(getApplicationContext());
            progressDialog.setTitle("ProgressDialog");
            progressDialog.setMessage("Esperar por "+tv_tempo.getText().toString()+" segundos...");
            progressDialog.show();
        }


        @Override
        protected void onProgressUpdate(String... values) {
            tv_result.setText(values[0]);
        }
    }
}