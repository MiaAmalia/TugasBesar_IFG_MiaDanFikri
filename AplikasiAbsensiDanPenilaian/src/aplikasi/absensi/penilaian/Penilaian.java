package aplikasi.absensi.penilaian;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Penilaian extends Activity {
	private EditText cari,tugas,uts,uas;
	private DB_Function akses_DB;
	private Context konteks;
	private TextView kodeSiswa,namaSiswa,kehadiran;
	private int jumlahKehadiran;
	private Button simpan,lihatnilai;
	DialogBox tampilkan;
	private ContentValues mengedit;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.penilaian);
        konteks = this;
        akses_DB = new DB_Function(konteks);
        kodeSiswa = (TextView)findViewById(R.id.textView3);
        namaSiswa = (TextView)findViewById(R.id.textView5);
        kehadiran = (TextView)findViewById(R.id.textView7);
        tugas = (EditText)findViewById(R.id.editText2);
        uts = (EditText)findViewById(R.id.editText3);
        uas=(EditText)findViewById(R.id.editText4);
        tampilkan = new DialogBox();
        setETcari();
        setTombolSimpan();
        setLihatNilai();
	}
	
	private void clear(){
		cari.setText("");
		kodeSiswa.setText("");
        namaSiswa.setText("");
        kehadiran.setText("");
        tugas.setText("");
        uts.setText("");
        uas.setText("");
	}
	
	private void setLihatNilai(){
		lihatnilai = (Button)findViewById(R.id.Button3);
		lihatnilai.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent myIntent = new Intent(v.getContext(), LihatNilai.class);
		        startActivityForResult(myIntent, 0);
			}
		});
	}
	
	private void setETcari(){
		cari = (EditText)findViewById(R.id.editText1);
		cari.setOnKeyListener(new View.OnKeyListener() {
			
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				// TODO Auto-generated method stub
				if(cari.getText().toString().equalsIgnoreCase("")){
			        clear();
				}else{
					akses_DB.ambilData("select _id,namaSiswa from daftarSiswa where _id like '%"+cari.getText().toString()+"%' or namaSiswa like '%"+cari.getText().toString()+"%'");
					if(akses_DB.resultSet.moveToFirst()){
						kodeSiswa.setText(akses_DB.resultSet.getString(0));
						namaSiswa.setText(akses_DB.resultSet.getString(1));
						akses_DB.ambilData("select * from absensi where _id = '"+kodeSiswa.getText().toString()+"'");
						if(akses_DB.resultSet.moveToFirst()){
							jumlahKehadiran = 0;
							for(int a=1;a<17;a++){
								if(!akses_DB.resultSet.isNull(a)){
									if(akses_DB.resultSet.getString(a).toString().equalsIgnoreCase("hadir")){
									jumlahKehadiran++;
									}
								}
							}
							kehadiran.setText(((jumlahKehadiran*25)/4)+"%");
						}
						akses_DB.ambilData("select tugas,uts,uas from penilaian where _id ='"+kodeSiswa.getText().toString()+"'");
						if(akses_DB.resultSet.moveToFirst()){
							if(!akses_DB.resultSet.isNull(0)){
								tugas.setText(akses_DB.resultSet.getString(0));
							}
							if(!akses_DB.resultSet.isNull(1)){
								uts.setText(akses_DB.resultSet.getString(1));
							}
							if(!akses_DB.resultSet.isNull(2)){
								uas.setText(akses_DB.resultSet.getString(2));
							}
						}
					}else{
						clear();
					}
				}
				return false;
			}
		});
	}
	private void setTombolSimpan(){
		simpan = (Button)findViewById(R.id.Button2);
		simpan.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(kodeSiswa.getText().toString().equalsIgnoreCase("")||
						namaSiswa.getText().toString().equalsIgnoreCase("")||
						kehadiran.getText().toString().equalsIgnoreCase("")||
						tugas.getText().toString().equalsIgnoreCase("")||
						uts.getText().toString().equalsIgnoreCase("")||
						uas.getText().toString().equalsIgnoreCase("")){
					tampilkan.dialogBoxOke(konteks, "Cari data siswa kemudian inputkan nilai-nilainya dengan lengkap!");
				}else{
					mengedit = new ContentValues();
					mengedit.put("kehadiran", (jumlahKehadiran*25)/4);
					mengedit.put("tugas", tugas.getText().toString());
					mengedit.put("uts", uts.getText().toString());
					mengedit.put("uas", uas.getText().toString());
					akses_DB.updateData("penilaian", mengedit, "_id='"+kodeSiswa.getText().toString()+"'");
					if(akses_DB.noerror){
						clear();
						Toast.makeText(konteks, "Nilai sudah tersimpan", Toast.LENGTH_SHORT).show();
					}
				}
			}
		});
	}
}
