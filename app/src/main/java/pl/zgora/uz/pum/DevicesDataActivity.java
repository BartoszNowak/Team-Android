package pl.zgora.uz.pum;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DevicesDataActivity extends AppCompatActivity {
    private EditText editText;
    private Button button;
    private String string;
    private JsonArrayRequest ArrayRequest;
    private RequestQueue requestQueue;
    private List<Data> dataList = new ArrayList<>();
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_devices_data);
        editText = findViewById(R.id.input_id);
        editText.setSelection(editText.getText().length());
        recyclerView = findViewById(R.id.recyclerviewid);
        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                string = "http://pum-swierzaki-backend.193b.starter-ca-central-1.openshiftapps.com/v1/devices/" + editText.getText().toString() + "/records";
                dataList.clear();
                jsonParse(string);
            }
        });
    }

    private void jsonParse(String url) {

        ArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                JSONObject jsonObject = null;

                for (int i = 0; i < response.length(); i++) {

                    try {
                        jsonObject = response.getJSONObject(i);
                        Data data = new Data();

                        data.setTemperature(jsonObject.getString("temperature"));
                        data.setHumidity(jsonObject.getString("humidity"));

                        dataList.add(data);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }


                setRvadapter(dataList);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(DevicesDataActivity.this, "Unable to get data.", Toast.LENGTH_SHORT).show();
            }
        });


        requestQueue = Volley.newRequestQueue(DevicesDataActivity.this);
        requestQueue.add(ArrayRequest);
    }

    public void setRvadapter(List<Data> lst) {

        RecyclerViewAdapter myAdapter = new RecyclerViewAdapter(this, lst);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(myAdapter);
    }
}
