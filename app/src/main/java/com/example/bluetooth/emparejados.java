package com.example.bluetooth;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Set;


public class emparejados extends Activity {
    BluetoothAdapter adaptador= BluetoothAdapter.getDefaultAdapter();

    ListView lista;

    ArrayList<String> stringArrayList = new ArrayList<String>(); //creacion de array list para guardar busqueda de dispositivos
    ArrayAdapter<String> arrayAdapter; //creacion de adaptador


    ArrayAdapter<String> itemsAdapter;
    ArrayList<String> sincronizados = new ArrayList<String>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.emparejados);


    }



    @RequiresApi(api = Build.VERSION_CODES.M)

    public void scan(View view){ // boton para busqueda de dispositivo

    Button scan = (Button)findViewById(R.id.scan);
    lista=(ListView)findViewById(R.id.lista);
    checkBTPermissions(); //permisos
    adaptador.startDiscovery(); //iniciar busqueda

    IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND); //registrar un filter para llamar al broadcastrecever
    registerReceiver(buscar,filter);

    arrayAdapter= new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,stringArrayList); //guardar el array
    lista.setAdapter(arrayAdapter); // set el array list a array adapter

    Toast.makeText(this,"Buscandoo..", Toast.LENGTH_LONG).show();

    }

    BroadcastReceiver buscar = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) { //llamada
            String action = intent.getAction(); //accion
            if(BluetoothDevice.ACTION_FOUND.equals(action)){ //busqueda
             BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE); //para recoleccion de datos
             stringArrayList.add("Nombre : "+device.getName()+"\nMac : "+device.getAddress()); //guarda
                arrayAdapter.notifyDataSetChanged(); //  notifica la informacion guardada actualizar lista
            }
        }
    };

    public void sincronizados(View vista){  //mostrar dispositivos ya sincronizados
        lista = (ListView)findViewById(R.id.lista);
        Button boton= (Button)findViewById(R.id.devices);

            Set<BluetoothDevice> items =adaptador.getBondedDevices();
            for (BluetoothDevice device  : items) {
                String deviceName = device.getName();
                String deviceHardwareAddress = device.getAddress(); // MAC address
                sincronizados.add("Nombre : "+device.getName()+"\nMac : "+device.getAddress());
            }
            itemsAdapter= new ArrayAdapter(this,android.R.layout.simple_list_item_1,sincronizados);   //ArrayAdapter---------------------
            lista.setAdapter(itemsAdapter);
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    public void checkBTPermissions(){
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP){
            int permissionCheck = this.checkSelfPermission("Manifest.permission.ACCESS_FINE_LOCATION");
            permissionCheck += this.checkSelfPermission("Manifest.permission.ACCESS_COARSE_LOCATION");
            if (permissionCheck != 0) {

                this.requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1001); //Any number
            }
        }
    }



}
