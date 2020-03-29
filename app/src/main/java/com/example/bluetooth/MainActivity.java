package com.example.bluetooth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private static final int ACTION_CONNECTION_STATE_CHANGED = 1;
    BluetoothAdapter adaptador = BluetoothAdapter.getDefaultAdapter();
    private static final int REQUEST_ENABLE_BT = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



    }


   private int presiono;

    public void encender(View vista){ // para encender el bluetooth

        if(adaptador==null){
          Toast.makeText(this,"EL dispositivo no soporta bluetooth", Toast.LENGTH_LONG).show();
        }else{
           // Toast.makeText(this,"EL dispositivo soporta bluetooth", Toast.LENGTH_LONG).show();
        }

        if(!adaptador.isEnabled()){
            Intent prender= new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(prender,REQUEST_ENABLE_BT); //
                presiono=1;
               // Toast.makeText(this,"NO permitio prender el bluetooth!", Toast.LENGTH_LONG).show();


        }else{
            Toast.makeText(this,"Bluetooth Ya esta encendido!", Toast.LENGTH_LONG).show();
                presiono=0;
        }


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) { //para mostar mensajes

        if(requestCode== REQUEST_ENABLE_BT){
            if (resultCode == RESULT_OK){
                Toast.makeText(this,"Bluetooth Ahora esta activado!", Toast.LENGTH_LONG).show();
            }else if(resultCode == RESULT_CANCELED){
                Toast.makeText(this,"Error al Ativar el Bluetooth!", Toast.LENGTH_LONG).show();
                presiono=0;
            }



        }


    }

    public void apagar( View vista) {

    if(adaptador.disable()){
        Toast.makeText(this,"Bluetooth ha sido apagado!", Toast.LENGTH_LONG).show();
        presiono=0;
    }


        //                IntentFilter filter = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
        //              registerReceiver(mReceiver, filter);

    }
// DETECTAR CAMBIOS BLUETOOTH
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();

            if (BluetoothAdapter.ACTION_STATE_CHANGED.equals(action)) {
                final int estado = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE,
                        BluetoothAdapter.ERROR);

                switch (estado) {
                    case BluetoothAdapter.STATE_OFF:
                        // Bluetooth has been turned off;
                      // resultado.setText("Ha sido apagado");


                        break;
                    case BluetoothAdapter.STATE_TURNING_OFF:
                        //resultado.setText("Se esta aapagando");

                        break;

                }
            }
        }
    };



    public void emparejados(View view){
        IntentFilter filtro = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
        this.registerReceiver(mReceiver, filtro);

    if(presiono==0){
        Toast.makeText(this,"Enciende el bluetooth!", Toast.LENGTH_LONG).show();
    }else {

        Intent i = new Intent(this,emparejados.class);
        startActivity(i);
    }


    }

    


    public void salir(View vista){
        finish();
    }


}

