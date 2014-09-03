package com.android.tugasakhir;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.EditText;
import android.widget.Toast;
import android.webkit.*;
//import android.view.Menu;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.UUID;

public class MainActivity extends Activity{

	private static final String TAG = "Aplikasi Bluetooth";
	
	ImageButton btnMaju,btnMundur,btnKiri,btnKanan,btnStop;
	Button btnAtas,btnBawah,btnBuka,btnTutup,btnPutarPlus,btnPutarMinus;
	EditText txtCmd;
	WebView myWV;
	
	
	public Timer autoUpdate;
	
	private BluetoothAdapter btAdapter = null;
	private BluetoothSocket btSocket = null;
	private OutputStream outStream = null;
	    
	// SPP UUID service 
	private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"); 
	private static String address = "00:12:10:09:05:06";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		btnMaju = (ImageButton)findViewById(R.id.btnMaju);
		btnMundur = (ImageButton)findViewById(R.id.btnMundur);
		btnKiri = (ImageButton)findViewById(R.id.btnKiri);
		btnKanan = (ImageButton)findViewById(R.id.btnKanan);
		btnStop = (ImageButton)findViewById(R.id.btnStop);
		btnAtas= (Button)findViewById(R.id.btnAtas);
		btnBawah = (Button)findViewById(R.id.btnBawah);
		btnPutarPlus = (Button)findViewById(R.id.btnPutarPlus);
		btnPutarMinus = (Button)findViewById(R.id.btnPutarMinus);
		btnBuka = (Button)findViewById(R.id.btnBuka);
		btnTutup = (Button)findViewById(R.id.btnTutup);
		
		
		myWV =(WebView)findViewById(R.id.myWV);
		
		
		myWV.getSettings().getJavaScriptEnabled();
		myWV.getSettings().getPluginsEnabled();
		myWV.loadUrl("http://192.168.43.1:8080/photo.jpg");
		timerSetup();
		
		btAdapter = BluetoothAdapter.getDefaultAdapter();
	    checkBTState();
	    
	    
	    btnMaju.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if(event.getAction()==MotionEvent.ACTION_DOWN){
					sendData("1");
				}else
				if(event.getAction()==MotionEvent.ACTION_UP){
					sendData("5");
				}
				return true;
			}
		});
	    
	    btnKanan.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if(event.getAction()==MotionEvent.ACTION_DOWN){
					sendData("2");
				}else
				if(event.getAction()==MotionEvent.ACTION_UP){
					sendData("5");
				}
				return true;
			}
		});
	    
	    btnMundur.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if(event.getAction()==MotionEvent.ACTION_DOWN){
					sendData("3");
				}else
				if(event.getAction()==MotionEvent.ACTION_UP){
					sendData("5");
				}
				return true;
			}
		});
	    
	    btnKiri.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if(event.getAction()==MotionEvent.ACTION_DOWN){
					sendData("4");
				}else
				if(event.getAction()==MotionEvent.ACTION_UP){
					sendData("5");
				}
				return true;
			}
		});
	    
	    btnStop.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if(event.getAction()==MotionEvent.ACTION_DOWN){
					sendData("5");
				}else
				if(event.getAction()==MotionEvent.ACTION_UP){
					sendData("5");
				}
				return true;
			}
		});
	    
	    btnAtas.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if(event.getAction()==MotionEvent.ACTION_DOWN){
					sendData("u");
				}else
				if(event.getAction()==MotionEvent.ACTION_UP){
					sendData("s");
				}
				return true;
			}
		});
	    
	    btnBawah.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if(event.getAction()==MotionEvent.ACTION_DOWN){
					sendData("d");
				}else
				if(event.getAction()==MotionEvent.ACTION_UP){
					sendData("s");
				}
				return true;
			}
		});
	    
	    btnBuka.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if(event.getAction()==MotionEvent.ACTION_DOWN){
					sendData("o");
				}else
				if(event.getAction()==MotionEvent.ACTION_UP){
					sendData("s");
				}
				return true;
			}
		});
	    
	    btnTutup.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if(event.getAction()==MotionEvent.ACTION_DOWN){
					sendData("c");
				}else
				if(event.getAction()==MotionEvent.ACTION_UP){
					sendData("s");
				}
				return true;
			}
		});
	    
	    btnPutarPlus.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if(event.getAction()==MotionEvent.ACTION_DOWN){
					sendData("p");
				}else
				if(event.getAction()==MotionEvent.ACTION_UP){
					sendData("s");
				}
				return true;
			}
		});
	    
	    btnPutarMinus.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if(event.getAction()==MotionEvent.ACTION_DOWN){
					sendData("m");
				}else
				if(event.getAction()==MotionEvent.ACTION_UP){
					sendData("s");
				}
				return true;
			}
		});
	}
	
	private BluetoothSocket createBluetoothSocket(BluetoothDevice device) throws IOException {
	      if(Build.VERSION.SDK_INT >= 10){
	          try {
	              final Method  m = device.getClass().getMethod("createInsecureRfcommSocketToServiceRecord", new Class[] { UUID.class });
	              return (BluetoothSocket) m.invoke(device, MY_UUID);
	          } catch (Exception e) {
	              Log.e(TAG, "Could not create Insecure RFComm Connection",e);
	          }
	      }
	      return  device.createRfcommSocketToServiceRecord(MY_UUID);
	  }
	
	
	
	public void timerSetup(){
	     autoUpdate = new Timer();
	     autoUpdate.schedule(new TimerTask() {
	      @Override
	      public void run() {
	       runOnUiThread(new Runnable() {
	        @Override
	     public void run() {
	         //Actions goes here
	         myWV.loadUrl("http://192.168.43.1:8080/photo.jpg");
	        }
	       });
	      }
	     }, 0, 500);//refresh rate time interval (ms)
	    }
	
	//Mengecek kondisi Bluetooth
	private void checkBTState() {
	    if(btAdapter==null) { 
	      errorExit("Fatal Error", "Bluetooth not support");
	    } else {
	      if (btAdapter.isEnabled()) {
	        Log.d(TAG, "...Bluetooth ON...");
	      } else {
	        //Muncul Request untuk menyalakan Bluetooth
	        Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
	        startActivityForResult(enableBtIntent, 1);
	      }
	    }
	  }
	
	
	private void errorExit(String title, String message){
	    Toast.makeText(getBaseContext(), title + " - " + message, Toast.LENGTH_LONG).show();
	    finish();
	}
	
	private void sendData(String message) {
	    byte[] msgBuffer = message.getBytes();
	  
	    Log.d(TAG, "...Send data: " + message + "...");
	  
	    try {
	      outStream.write(msgBuffer);
	    } catch (IOException e) {
	      String msg = "In onResume() and an exception occurred during write: " + e.getMessage();
	      if (address.equals("00:00:00:00:00:00")) 
	        msg = msg + ".\n\nUpdate your server address from 00:00:00:00:00:00 to the correct address on line 35 in the java code";
	        msg = msg +  ".\n\nCheck that the SPP UUID: " + MY_UUID.toString() + " exists on server.\n\n";
	        
	        errorExit("Fatal Error", msg);       
	    }
	  }
	
	@Override
	  public void onResume() {
	    super.onResume();
	  
	    Log.d(TAG, "...onResume - try connect...");
	    
	    // Set up a pointer to the remote node using it's address.
	    BluetoothDevice device = btAdapter.getRemoteDevice(address);
	    
	    // Two things are needed to make a connection:
	    //   A MAC address, which we got above.
	    //   A Service ID or UUID.  In this case we are using the
	    //     UUID for SPP.
	    
	    try {
	        btSocket = createBluetoothSocket(device);
	    } catch (IOException e1) {
	        errorExit("Fatal Error", "In onResume() and socket create failed: " + e1.getMessage() + ".");
	    }
	        
	    // Discovery is resource intensive.  Make sure it isn't going on
	    // when you attempt to connect and pass your message.
	    btAdapter.cancelDiscovery();
	    
	    // Establish the connection.  This will block until it connects.
	    Log.d(TAG, "...Connecting...");
	    try {
	      btSocket.connect();
	      Log.d(TAG, "...Connection ok...");
	    } catch (IOException e) {
	      try {
	        btSocket.close();
	      } catch (IOException e2) {
	        errorExit("Fatal Error", "In onResume() and unable to close socket during connection failure" + e2.getMessage() + ".");
	      }
	    }
	      
	    // Create a data stream so we can talk to server.
	    Log.d(TAG, "...Create Socket...");
	  
	    try {
	      outStream = btSocket.getOutputStream();
	    } catch (IOException e) {
	      errorExit("Fatal Error", "In onResume() and output stream creation failed:" + e.getMessage() + ".");
	    }
	  }
	  
	  @Override
	  public void onPause() {
	    super.onPause();
	  
	    Log.d(TAG, "...In onPause()...");
	  
	    if (outStream != null) {
	      try {
	        outStream.flush();
	      } catch (IOException e) {
	        errorExit("Fatal Error", "In onPause() and failed to flush output stream: " + e.getMessage() + ".");
	      }
	    }
	  
	    try {
	      btSocket.close();
	    } catch (IOException e2) {
	      errorExit("Fatal Error", "In onPause() and failed to close socket." + e2.getMessage() + ".");
	    }
	  }
}
