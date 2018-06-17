package pl.zgora.uz.pum;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class DevicesDataActivity extends AppCompatActivity {
    private EditText editText;
    private Button button;
    private String url;
    private JsonArrayRequest ArrayRequest;
    private RequestQueue requestQueue;
    private LineChart lineChart;
    private BarChart barChart;
    float[] temperatureArr;
    float[] humidityArr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_devices_data);
        editText = findViewById(R.id.input_id);
        editText.setSelection(editText.getText().length());

        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                url = "http://pum-swierzaki-backend.193b.starter-ca-central-1.openshiftapps.com/v1/devices/" + editText.getText().toString() + "/records";
                jsonParse(url);
            }
        });
    }

    private void drawLineChart(float temperatureValues[], float humidityValues[]) {
        ArrayList<Entry> yData = new ArrayList<>();
        for (int i = 0; i < temperatureValues.length; i++) {
            yData.add(new Entry(i, temperatureValues[i]));
        }
        ArrayList<Entry> yData2 = new ArrayList<>();
        for (int i = 0; i < humidityValues.length; i++) {
            yData2.add(new Entry(i, humidityValues[i]));
        }

        lineChart = (LineChart) findViewById(R.id.line_chart);
        lineChart.getDescription().setEnabled(false);
        lineChart.setDragEnabled(true);
        lineChart.setScaleEnabled(true);

        LineDataSet lineDataSet = new LineDataSet(yData, "temperature");
        lineDataSet.setFillAlpha(110);
        lineDataSet.setColor(Color.RED);
        lineDataSet.setLineWidth(3f);

        LineDataSet lineDataSet2 = new LineDataSet(yData2, "humidity");
        lineDataSet2.setFillAlpha(110);
        lineDataSet2.setColor(Color.BLUE);
        lineDataSet2.setLineWidth(3f);

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(lineDataSet);
        dataSets.add(lineDataSet2);
        LineData data = new LineData(dataSets);
        lineChart.setData(data);
        lineChart.invalidate();
    }

    private void drawBarChart(float temperatureValues[], float humidityValues[]) {
        ArrayList<BarEntry> yData = new ArrayList<>();
        for (int i = 0; i < temperatureValues.length; i++) {
            yData.add(new BarEntry(i, temperatureValues[i]));
        }
        ArrayList<BarEntry> yData2 = new ArrayList<>();
        for (int i = 0; i < humidityValues.length; i++) {
            yData2.add(new BarEntry(i, humidityValues[i]));
        }

        barChart = (BarChart) findViewById(R.id.bar_chart);
        barChart.setDrawBarShadow(false);
        barChart.setDrawValueAboveBar(true);
        barChart.setMaxVisibleValueCount(50);
        barChart.setPinchZoom(false);
        barChart.setDrawGridBackground(true);
        barChart.setDragEnabled(true);
        barChart.setScaleEnabled(true);
        barChart.getDescription().setEnabled(false);

        BarDataSet barDataSet = new BarDataSet(yData, "temperature");
        barDataSet.setColor(Color.RED);
        BarData barData = new BarData(barDataSet);
        barData.setBarWidth(0.9f);

        BarDataSet barDataSet2 = new BarDataSet(yData2, "humidity");
        barDataSet2.setColor(Color.BLUE);
        BarData barData2 = new BarData(barDataSet2);
        barData2.setBarWidth(0.9f);

        ArrayList<IBarDataSet> iBarDataSet = new ArrayList<>();
        iBarDataSet.add(barDataSet);
        iBarDataSet.add(barDataSet2);
        BarData data = new BarData(iBarDataSet);
        barChart.setData(data);
        barChart.invalidate();
    }


    private void jsonParse(String url) {

        ArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                JSONObject jsonObject = null;
                temperatureArr = new float[response.length()];
                humidityArr = new float[response.length()];
                for (int i = 0; i < response.length(); i++) {

                    try {

                        jsonObject = response.getJSONObject(i);
                        temperatureArr[i] = Float.parseFloat(jsonObject.getString("temperature"));
                        humidityArr[i] = Float.parseFloat(jsonObject.getString("humidity"));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                if (temperatureArr.length != 0 && humidityArr.length != 0) {
                    drawLineChart(temperatureArr, humidityArr);
                    drawBarChart(temperatureArr, humidityArr);
                }
                if (jsonObject == null) {
                    Toast.makeText(DevicesDataActivity.this, "Device with the given id does not exist.", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(DevicesDataActivity.this, "Unable to get data. Check internet connection.", Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue = Volley.newRequestQueue(DevicesDataActivity.this);
        requestQueue.add(ArrayRequest);
    }

}
