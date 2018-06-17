package pl.zgora.uz.pum;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;
import java.util.UUID;


public class BluetoothCommActivity extends AppCompatActivity {


     private static final String TAG = "appTag";
     private static final int REQUEST_ENABLE_BT = 1;
     //Wspaniała autoryzacja
     private static final UUID appUuid = UUID.fromString("0e6114d0-8a2e-477a-8502-298d1ff4b4ba");
     //TODO hardcoded MACaddr, need to obtain it from QR code
     private String macAddress = "30:3A:64:65:DC:DA";
     ConnectThread connectThread;
     Context context;
      Handler handler = new Handler(){
         @Override
         public void handleMessage(final Message msg){
             if(null!=msg.obj){
             final byte[] byteArray = (byte[])msg.obj;
             final String dataObject = new String(byteArray).trim();
             Log.d(TAG,dataObject);
                 Toast.makeText(getApplicationContext(), dataObject,
                         Toast.LENGTH_LONG).show();
             }
         }
     };
     BluetoothCommService communicationService = new BluetoothCommService(handler);
     private BluetoothAdapter mBluetoothAdapter;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        Bundle b = getIntent().getExtras();
        String value = ""; // or other values
        if(b != null)
            value = b.getString("macAddr");
        macAddress = value;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth_comm);
        context = getApplicationContext();

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            // Device doesn't support Bluetooth
        }

        if (!mBluetoothAdapter.isEnabled()) {
            final Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }

        final Button buttonD1On = findViewById(R.id.button_d1on);
        buttonD1On.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                final BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(macAddress);

                connectThread = new ConnectThread(device, Command.DIODA1_ON);
                connectThread.start();
            }
        });

        final Button buttonD1Off = findViewById(R.id.button_d1off);
        buttonD1Off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                final BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(macAddress);

                connectThread = new ConnectThread(device, Command.DIODA1_OFF);
                connectThread.start();
            }
        });

        final Button buttonD2On = findViewById(R.id.button_d2on);
        buttonD2On.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                final BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(macAddress);

                connectThread = new ConnectThread(device, Command.DIODA2_ON);
                connectThread.start();
            }
        });

        final Button buttonD3On = findViewById(R.id.button_d3on);
        buttonD3On.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                final BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(macAddress);

                connectThread = new ConnectThread(device, Command.DIODA3_ON);
                connectThread.start();
            }
        });

        final Button buttonD4On = findViewById(R.id.button_d4on);
        buttonD4On.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                final BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(macAddress);

                connectThread = new ConnectThread(device, Command.DIODA4_ON);
                connectThread.start();
            }
        });

        final Button buttonD5On = findViewById(R.id.button_d5on);
        buttonD5On.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                final BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(macAddress);

                connectThread = new ConnectThread(device, Command.DIODA5_ON);
                connectThread.start();
            }
        });

        final Button buttonD6On = findViewById(R.id.button_d6on);
        buttonD6On.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                final BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(macAddress);

                connectThread = new ConnectThread(device, Command.DIODA6_ON);
                connectThread.start();
            }
        });

        final Button buttonD7On = findViewById(R.id.button_d7on);
        buttonD7On.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                final BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(macAddress);

                connectThread = new ConnectThread(device, Command.DIODA7_ON);
                connectThread.start();
            }
        });

        final Button buttonD8On = findViewById(R.id.button_d8on);
        buttonD8On.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                final BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(macAddress);

                connectThread = new ConnectThread(device, Command.DIODA8_ON);
                connectThread.start();
            }
        });

        final Button buttonD2Off = findViewById(R.id.button_d20ff);
        buttonD2Off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                final BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(macAddress);

                connectThread = new ConnectThread(device, Command.DIODA2_OFF);
                connectThread.start();
            }
        });

        final Button buttonD3Off = findViewById(R.id.button_d3off);
        buttonD3Off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                final BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(macAddress);

                connectThread = new ConnectThread(device, Command.DIODA3_OFF);
                connectThread.start();
            }
        });

        final Button buttonD4Off = findViewById(R.id.button_d4off);
        buttonD4Off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                final BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(macAddress);

                connectThread = new ConnectThread(device, Command.DIODA4_OFF);
                connectThread.start();
            }
        });

        final Button buttonD5Off = findViewById(R.id.button_d5off);
        buttonD5Off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                final BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(macAddress);

                connectThread = new ConnectThread(device, Command.DIODA1_OFF);
                connectThread.start();
            }
        });

        final Button buttonD6Off = findViewById(R.id.button_d6off);
        buttonD6Off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                final BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(macAddress);

                connectThread = new ConnectThread(device, Command.DIODA6_OFF);
                connectThread.start();
            }
        });

        final Button buttonD7Off = findViewById(R.id.button_d7off);
        buttonD7Off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                final BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(macAddress);

                connectThread = new ConnectThread(device, Command.DIODA7_OFF);
                connectThread.start();
            }
        });

        final Button buttonD8Off = findViewById(R.id.button_d8off);
        buttonD8Off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                final BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(macAddress);

                connectThread = new ConnectThread(device, Command.DIODA8_OFF);
                connectThread.start();
            }
        });

        final Button buttonGetTemperature = findViewById(R.id.button_temp);
        buttonGetTemperature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                final BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(macAddress);

                connectThread = new ConnectThread(device, Command.GET_TEMPERATURE);
                connectThread.start();
            }
        });

        final Button buttonGetHumidity = findViewById(R.id.button_humidity);
        buttonGetHumidity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                final BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(macAddress);

                connectThread = new ConnectThread(device, Command.GET_HUMIDITY);
                connectThread.start();
            }
        });

        final Button buttonGetBatteryStatus = findViewById(R.id.button_battery);
        buttonGetBatteryStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                final BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(macAddress);

                connectThread = new ConnectThread(device, Command.GET_BATTERY_STATUS);
                connectThread.start();
            }
        });

        final Button buttonGetLocalization = findViewById(R.id.button_localization);
        buttonGetLocalization.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                final BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(macAddress);

                connectThread = new ConnectThread(device, Command.GET_LOCALIZATION);
                connectThread.start();
            }
        });

        final Button buttonTerminal = findViewById(R.id.button_terminal);
        buttonTerminal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Intent intent = new Intent(BluetoothCommActivity.this, TerminalActivity.class);
                Bundle b = new Bundle();
                b.putString("macAddr", macAddress);
                intent.putExtras(b);
                startActivity(intent);
                finish();
            }
        });

    }

    private class ConnectThread extends Thread {
        private final BluetoothSocket mmSocket;
        private final BluetoothDevice mmDevice;
        private final Command command;

        public ConnectThread(final BluetoothDevice device, Command command) {
            // Use a temporary object that is later assigned to mmSocket
            // because mmSocket is final.
            this.command = command;
            BluetoothSocket tmp = null;
            mmDevice = device;

            try {
                // Get a BluetoothSocket to connect with the given BluetoothDevice.
                // MY_UUID is the app's UUID string, also used in the server code.
                tmp = device.createInsecureRfcommSocketToServiceRecord(appUuid);
            } catch (final IOException e) {
                Log.e(TAG, "Socket's create() method failed", e);
            }
            mmSocket = tmp;
        }

        @Override
        public void run() {
            // Cancel discovery because it otherwise slows down the connection.
            mBluetoothAdapter.cancelDiscovery();

            try {
                // Connect to the remote device through the socket. This call blocks
                // until it succeeds or throws an exception.
                mmSocket.connect();
            } catch (final IOException connectException) {
                try {
                    mmSocket.close();
                } catch (final IOException closeException) {
                    Log.e(TAG, "Could not close the client socket", closeException);
                }
                return;
            }

            // The connection attempt succeeded. Perform work associated with
            // the connection in a separate thread.
            communicationService.sendCommand(command, mmSocket);

        }

        // Closes the client socket and causes the thread to finish.
        public void cancel() {
            try {
                mmSocket.close();
            } catch (final IOException e) {
                Log.e(TAG, "Could not close the client socket", e);
            }
        }
    }
}
