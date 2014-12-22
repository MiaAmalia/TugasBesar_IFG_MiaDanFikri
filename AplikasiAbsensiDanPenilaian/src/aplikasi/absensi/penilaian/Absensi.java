package aplikasi.absensi.penilaian;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class Absensi extends Activity{
	DB_Function akses_DB;
	Context konteks;
	DialogBox tampilkan;
	TextView pertemuanke;
	Spinner kehadiran;
	String[] array_spinner;
	TextView Absenke;
	TextView kodeSiswa;
	TextView namaSiswa;
	TextView jKelamin;
	Button simpan;
	String[][] dataAbsensi;
	int pertemuan;
	ContentValues mengedit;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.absensi);
        konteks = this;
        akses_DB = new DB_Function(konteks);
        tampilkan = new DialogBox();
        Absenke = (TextView)findViewById(R.id.textView2);
        kodeSiswa=(TextView)findViewById(R.id.textView9);
        namaSiswa = (TextView)findViewById(R.id.textView8);
        jKelamin=(TextView)findViewById(R.id.textView7);
        
        
        setPertemuanke();
        SetSpinner();
        akses_DB.ambilData("Select * from daftarSiswa order by _id asc");
        dataAbsensi = new String[akses_DB.resultSet.getCount()][2];
        tampilkanIdentitas();
        settombolSimpan();
	}
	
	@Override
	public void onBackPressed(){
		tampilkan.dialogBoxYesorNo(konteks, "Proses absensi belum selesai! Jika anda membatalkan proses absensi, data absensi pada pertemuan kali ini tidak akan tersimpan.\nLanjutkan proses absensi?");
		tampilkan.dialogBox.setOnDismissListener(new DialogInterface.OnDismissListener() {
			
			@Override
			public void onDismiss(DialogInterface dialog) {
				// TODO Auto-generated method stub
				if(!tampilkan.keputusan){
					finish();
				}
			}
		});
	}
	
	private void simpandataAbsensi(){
		mengedit = new ContentValues();
		for(int a=0;a<akses_DB.resultSet.getCount();a++){
			mengedit.put("p"+pertemuan, dataAbsensi[a][1]);
			akses_DB.updateData("absensi", mengedit, "_id='"+dataAbsensi[a][0]+"'");
		}
	}
	
	private void settombolSimpan(){
		simpan=(Button)findViewById(R.id.Button1);
		simpan.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dataAbsensi[akses_DB.resultSet.getPosition()][0]=kodeSiswa.getText().toString();
				dataAbsensi[akses_DB.resultSet.getPosition()][1]=kehadiran.getSelectedItem().toString();
				tampilkanIdentitas();
			}
		});
	}
	
	private void tampilkanIdentitas(){
		if(akses_DB.resultSet.moveToNext()){
			Absenke.setText("Absen ke-"+(akses_DB.resultSet.getPosition()+1));
			kodeSiswa.setText(akses_DB.resultSet.getString(0));
			namaSiswa.setText(akses_DB.resultSet.getString(1));
			jKelamin.setText(akses_DB.resultSet.getString(2));
		}else{
			simpandataAbsensi();
			mengedit = new ContentValues();
			mengedit.put("pertemuanke", pertemuan+1);
			akses_DB.updateData("jumlahPertemuan", mengedit, "_id=1");
			tampilkan.dialogBoxOke(konteks, "Seluruh siswa telah diabsen");
			tampilkan.dialogBox.setOnDismissListener(new DialogInterface.OnDismissListener() {
				
				@Override
				public void onDismiss(DialogInterface dialog) {
					// TODO Auto-generated method stub
					finish();
				}
			});
		}
	}
	
	private void SetSpinner(){
		array_spinner=new String[]{"Hadir","Sakit","Izin","Alpha"};
        kehadiran = (Spinner) findViewById(R.id.spinner1);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item, array_spinner);
        kehadiran.setAdapter(adapter);
	}
	
	private void setPertemuanke(){
		pertemuanke = (TextView)findViewById(R.id.textView1);
		akses_DB.ambilData("select pertemuanKe from jumlahPertemuan");
		akses_DB.resultSet.moveToFirst();
		pertemuan = akses_DB.resultSet.getInt(0);
		if(pertemuan>16){
			tampilkan.dialogBoxOke(konteks, "Anda tidak melakukan absensi lagi karena sudah melakukan 16 pertemuan");
			tampilkan.dialogBox.setOnDismissListener(new DialogInterface.OnDismissListener() {
				
				@Override
				public void onDismiss(DialogInterface dialog) {
					// TODO Auto-generated method stub
			        finish();
				}
			});
		}else{
			pertemuanke.setText(pertemuanke.getText().toString()+" Pertemuan ke-"+pertemuan);
		}		
	}
}
