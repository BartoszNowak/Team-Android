package pl.zgora.uz.pum;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class DevicesDataActivity extends AppCompatActivity {
    private TextView textView;
    private EditText editText;
    private Button button;
    private String string;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_devices_data);
        textView = findViewById(R.id.text_view);
        editText = findViewById(R.id.input_id);
        button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                string = "http://pum-swierzaki-backend.193b.starter-ca-central-1.openshiftapps.com/v1/devices/" + editText.getText().toString() + "/records";
                jsonParse(string);
            }
        });
    }

    private void jsonParse(String url) {

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            String jsonString = response.replaceAll("\\[|\\]", "");
                            JSONObject jsonObj = new JSONObject(jsonString);
                            String idStr = jsonObj.getString("id");
                            String deviceIdStr = jsonObj.getString("deviceId");
                            String temperatureStr = jsonObj.getString("temperature");
                            String humidityStr = jsonObj.getString("humidity");
                            textView.setText("id: " + idStr + "\ndeviceId: " + deviceIdStr + "\ntemperature: " + temperatureStr + "\nhumidity: " + humidityStr);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                textView.setText("unable to get data");
            }
        });
        queue.add(stringRequest);
    }
}
