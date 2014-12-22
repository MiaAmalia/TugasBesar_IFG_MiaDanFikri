package aplikasi.absensi.penilaian;

import java.util.ArrayList;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;

@SuppressLint("ClickableViewAccessibility")
public class DaftarSiswa extends Activity{
	private EditText kdsiswa;
	private EditText nmsiswa;
	private RadioButton laki;
	private RadioButton perempuan;
	private ListView ListViewdaftarSiswa;
	private DB_Function akses_DB;
	private Button simpan;
	private Context konteks;
	private DialogBox tampilkan;
	private String jKelamin;
	private ArrayList<Siswa> daftarsiswa;
	private EditText cari;
	private int smpnoredit;
	private String kodeSiswa;
	private Button hapus;
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.daftarsiswa);
        konteks = this;
        akses_DB = new DB_Function(konteks);
        ListViewdaftarSiswa = (ListView) findViewById(R.id.listView1);
        daftarsiswa = new ArrayList<Siswa>();
        tampilkan = new DialogBox();
        radioButtonGroup();
        akses_DB.close();
        setButtonSimpan();
        setdaftarSiswaOnLongClick();
        cari = (EditText)findViewById(R.id.editText3);
        setETcari();
        hapus = (Button)findViewById(R.id.Button3);
        setTombolHapus();
        clear();
        setListViewdaftarSiswaOnTouch();
	}
	
	private void setListViewdaftarSiswaOnTouch(){
		ListViewdaftarSiswa.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				int action = event.getAction();
		        switch (action) 
		        {
		        case MotionEvent.ACTION_DOWN:
		            // Disallow ScrollView to intercept touch events.
		            v.getParent().requestDisallowInterceptTouchEvent(true);
		            break;

		        case MotionEvent.ACTION_UP:
		            // Allow ScrollView to intercept touch events.
		            v.getParent().requestDisallowInterceptTouchEvent(false);
		            break;
		        }

		        // Handle ListView touch events.
		        v.onTouchEvent(event);
		        return true;
			}
		});
	}
	
	private void clear(){
		kdsiswa.setText("");
		nmsiswa.setText("");
		setListViewDaftarSiswa("select * from daftarSiswa ORDER BY _id ASC");
		smpnoredit=0;
		kodeSiswa="";
		jKelamin = "";
		laki.setChecked(false);
		perempuan.setChecked(false);
	}
	
	private void setTombolHapus(){
		hapus.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(kodeSiswa.equalsIgnoreCase("")){
					tampilkan.dialogBoxOke(konteks, "Pilih siswa yang akan dihapus!");
				}else{
					tampilkan.dialogBoxYesorNo(konteks, "Apakah anda ingin mehapus siswa ini dari daftar siswa?");
					tampilkan.dialogBox.setOnDismissListener(new DialogInterface.OnDismissListener() {
						
						@Override
						public void onDismiss(DialogInterface arg0) {
							// TODO Auto-generated method stub
							if(tampilkan.keputusan){
								akses_DB.hapusData("daftarSiswa", "_id = '"+kodeSiswa+"'");
								tampilkan.keputusan = false;
								if(akses_DB.noerror){
									clear();
									Toast.makeText(konteks, "Data telah terhapus", Toast.LENGTH_SHORT).show();
								}
							}
						}
					});
					
				}
			}
		});
	}
	
	private void setETcari(){
		cari.setOnKeyListener(new View.OnKeyListener() {
			
			@Override
			public boolean onKey(View arg0, int arg1, KeyEvent arg2) {
				// TODO Auto-generated method stub
				if(cari.getText().toString().equalsIgnoreCase("")){
			        setListViewDaftarSiswa("select * from daftarSiswa ORDER BY _id ASC");
				}else{
					setListViewDaftarSiswa("select * from daftarSiswa where _id like '%"+cari.getText().toString()+"%' or namaSiswa like '%"+cari.getText().toString()+"%'");
				}
				return false;
			}
		});
	}
	
	private void setButtonSimpan(){
		simpan = (Button)findViewById(R.id.Button2);
		kdsiswa = (EditText)findViewById(R.id.editText1);
		nmsiswa = (EditText)findViewById(R.id.editText2);
		simpan.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(kdsiswa.getText().toString().equalsIgnoreCase("")|| nmsiswa.getText().toString().equalsIgnoreCase("")||(laki.isChecked()==false && perempuan.isChecked()==false)){
					tampilkan.dialogBoxOke(konteks, "Isi semua data terlebih dahulu");
				}else{
					
					switch(smpnoredit){
					case 0:
						akses_DB.simpanData("insert into daftarSiswa(_id,namaSiswa,jKelamin) values('"+kdsiswa.getText().toString()+"','"+nmsiswa.getText().toString()+"','"+jKelamin+"')");
						break;
					case 1:
						ContentValues mengedit = new ContentValues();
						mengedit.put("_id", kdsiswa.getText().toString());
						mengedit.put("namaSiswa", nmsiswa.getText().toString());
						mengedit.put("jKelamin", jKelamin);
						akses_DB.updateData("daftarSiswa", mengedit, "_id='"+kodeSiswa+"'");
						break;
					}
					if(akses_DB.noerror){
						clear();
						Toast.makeText(konteks, "Data telah tersimpan", Toast.LENGTH_SHORT).show();
					}
				}
			}
		});
	}
	
	private ArrayList<Siswa> getDaftarSiswa(){
		daftarsiswa.clear();
		while(akses_DB.resultSet.moveToNext()){
				daftarsiswa.add(new Siswa(akses_DB.resultSet.getString(1),akses_DB.resultSet.getString(0),akses_DB.resultSet.getString(2)));
		}
		return daftarsiswa;
	}
	
	private void setdaftarSiswaOnLongClick(){
		ListViewdaftarSiswa.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				smpnoredit = 1;
				nmsiswa.setText(daftarsiswa.get(arg2).getNM().toString());
				kdsiswa.setText(daftarsiswa.get(arg2).getKD().toString());
				if(daftarsiswa.get(arg2).getJK().toString().equalsIgnoreCase("Laki-laki")){
					laki.setChecked(true);
					jKelamin = laki.getText().toString();
				}else{
					perempuan.setChecked(true);
					jKelamin = perempuan.getText().toString();
				}
				kodeSiswa = kdsiswa.getText().toString();
				return false;
			}
		});
	}
	
	private void setListViewDaftarSiswa(String perintahSQL){
        akses_DB.ambilData(perintahSQL);
        ListViewdaftarSiswa.setAdapter(new AdapterListViewDaftarSiswa(konteks, getDaftarSiswa()));
	}
	
	private void radioButtonGroup() {
	    laki = (RadioButton)findViewById(R.id.radioButton1);
	    perempuan = (RadioButton)findViewById(R.id.radioButton2);
	    laki.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				jKelamin = laki.getText().toString();
				perempuan.setChecked(false);
			}
		});
	    perempuan.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				jKelamin = perempuan.getText().toString();
				laki.setChecked(false);
			}
		});
	}
}
