package aplikasi.absensi.penilaian;

import android.support.v7.app.ActionBarActivity;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends ActionBarActivity {
	private DB_Function akses_DB;
	private DialogBox tampilkan;
	private Context konteks;
	Button daftarSiswa;
	Button absensi;
	Button penilaian;
	ContentValues mengedit;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		konteks = this;
		tampilkan = new DialogBox();
		setTombolDaftarSiswa();
		setTombolAbsensi();
		setTombolPenilaian();
		akses_DB = new DB_Function(konteks);
		try {
            akses_DB.createDataBase();
    	} 
        catch (Exception ioe) {
        	tampilkan.dialogBoxOke(konteks, ioe.toString());
        }
    	try {
    		akses_DB.openDataBase();
    	}
    	catch(SQLException sqle){
    		tampilkan.dialogBoxOke(konteks, sqle.toString());
    	}
    	cekdaftarSiswa();
	}
	
	@Override
	protected void onStart(){
	      super.onStart();
	      cekdaftarSiswa();
	}
	
	@Override
	protected void onResume(){
		super.onResume();
		cekdaftarSiswa();
	}
	
	@SuppressLint("NewApi")
	private void cekdaftarSiswa(){
		akses_DB.ambilData("select _id from daftarSiswa");
		if(akses_DB.resultSet.moveToFirst()){
			absensi.setEnabled(true);
			penilaian.setEnabled(true);
		}else{
			absensi.setEnabled(false);
			penilaian.setEnabled(false);
			mengedit = new ContentValues();
			mengedit.put("pertemuanke", 1);
			akses_DB.updateData("jumlahPertemuan", mengedit, "_id=1");
		}
	}
	
	private void actionButton(View v, Class<?> namaKelas){
		Intent myIntent = new Intent(v.getContext(), namaKelas);
        startActivityForResult(myIntent, 0);
	}
	
	private void setTombolDaftarSiswa(){
		daftarSiswa = (Button) findViewById(R.id.Button1);
		daftarSiswa.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				actionButton(v, DaftarSiswa.class);
			}
		});
	}
	
	private void setTombolAbsensi(){
		absensi = (Button) findViewById(R.id.Button2);
		absensi.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(final View v) {
				// TODO Auto-generated method stub
				tampilkan.dialogBoxYesorNo(konteks, "Absensi akan dilakukan secara berurutan sesuai dengan jumlah siswa.\nLakukan absensi?");
				tampilkan.dialogBox.setOnDismissListener(new DialogInterface.OnDismissListener() {
					
					@Override
					public void onDismiss(DialogInterface dialog) {
						// TODO Auto-generated method stub
						if(tampilkan.keputusan){
							actionButton(v, Absensi.class);
						}
					}
				});
			}
		});
	}
	
	private void setTombolPenilaian(){
		penilaian = (Button)findViewById(R.id.Button3);
		penilaian.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				actionButton(v, Penilaian.class);
			}
		});
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
