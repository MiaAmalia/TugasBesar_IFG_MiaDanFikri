package aplikasi.absensi.penilaian;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.ListView;

public class LihatNilai extends Activity{
	private ListView ListViewNilai;
	private Context konteks;
	private DB_Function akses_DB;
	private ArrayList<Nilai> daftarnilai;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nilai);
        ListViewNilai = (ListView)findViewById(R.id.ListViewNilai);
        konteks = this;
        akses_DB = new DB_Function(konteks);
        daftarnilai = new ArrayList<Nilai>();
        setListViewNilai();
	}
	
	private ArrayList<Nilai> getNilai(){
		daftarnilai.clear();
		while(akses_DB.resultSet.moveToNext()){
				daftarnilai.add(new Nilai(akses_DB.resultSet.getString(1),akses_DB.resultSet.getString(0),akses_DB.resultSet.getString(2),akses_DB.resultSet.getString(3),akses_DB.resultSet.getString(4),akses_DB.resultSet.getString(5),akses_DB.resultSet.getString(6),akses_DB.resultSet.getString(7)));
		}
		return daftarnilai;
	}
	
	private void setListViewNilai(){
        akses_DB.ambilData("select daftarSiswa._id,daftarSiswa.namaSiswa,daftarSiswa.jKelamin,penilaian.kehadiran,penilaian.tugas,penilaian.uts,penilaian.uas,((penilaian.kehadiran/10)+((penilaian.tugas*1.5)/10)+(penilaian.uts/4)+(penilaian.uas/2)) as 'nilaiAkhir' from daftarSiswa join penilaian on daftarSiswa._id=penilaian._id order by daftarSiswa._id");
        ListViewNilai.setAdapter(new AdapterListViewNilai(konteks, getNilai()));
	}
}
