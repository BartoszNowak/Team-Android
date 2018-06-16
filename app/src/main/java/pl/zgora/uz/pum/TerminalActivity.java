package pl.zgora.uz.pum;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.util.UUID;

public class TerminalActivity extends AppCompatActivity {

    private static final String TAG = "appTag";
    private static final int REQUEST_ENABLE_BT = 1;
    //Wspaniała autoryzacja
    private static final UUID appUuid = UUID.fromString("0e6114d0-8a2e-477a-8502-298d1ff4b4ba");
    private String macAddress = "";
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terminal);

        context = this;

        Bundle b = getIntent().getExtras();
        String value = ""; // or other values
        if(b != null)
            value = b.getString("macAddr");
        macAddress = value;

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            // Device doesn't support Bluetooth
        }

        if (!mBluetoothAdapter.isEnabled()) {
            final Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }

        final Button button = findViewById(R.id.buttonTerminalSend);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                final BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(macAddress);
                EditText et = findViewById(R.id.textView2);
                String text = et.getText().toString();

                try{
                    //TODO removeme
                    Toast.makeText(context, "TEST", Toast.LENGTH_LONG).show();
                Command parsedCommand = Command.parse(text);
                connectThread = new ConnectThread(device, parsedCommand);
                connectThread.start();}catch (IllegalArgumentException ex){
                    Toast.makeText(context, "Wrong input, try other command", Toast.LENGTH_LONG).show();
                }
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
