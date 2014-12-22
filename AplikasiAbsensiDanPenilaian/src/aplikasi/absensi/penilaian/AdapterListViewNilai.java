package aplikasi.absensi.penilaian;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

@SuppressLint("ViewHolder")
public class AdapterListViewNilai extends ArrayAdapter<Nilai>{
	private Context context;
	private ArrayList<Nilai> itemArrayList;

	public AdapterListViewNilai(Context context, ArrayList<Nilai> itemArrayList) {
        super(context, R.layout.listviewnilai,itemArrayList);
        this.context = context;
        this.itemArrayList = itemArrayList;
    }
	
	@Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.listviewnilai, parent, false);
        TextView LVnamaSiswa = (TextView) rowView.findViewById(R.id.LVnamaSiswa);
        TextView LVkodeSiswa = (TextView) rowView.findViewById(R.id.LVkodeSiswa);
        TextView LVjenisKelamin = (TextView) rowView.findViewById(R.id.LVjenisKelamin);
        TextView LVkehadiran = (TextView)rowView.findViewById(R.id.LVkehadiran);
        TextView LVtugas = (TextView)rowView.findViewById(R.id.LVtugas);
        TextView LVuts= (TextView)rowView.findViewById(R.id.LVuts);
        TextView LVuas= (TextView)rowView.findViewById(R.id.LVuas);
        TextView LVnilaiakhir = (TextView)rowView.findViewById(R.id.LVnilaiakhir);
        LVnamaSiswa.setText(itemArrayList.get(position).getNm());
        LVkodeSiswa.setText(itemArrayList.get(position).getKd());
        LVjenisKelamin.setText(itemArrayList.get(position).getJk());
        LVkehadiran.setText("Persentase kehadiran : "+itemArrayList.get(position).getKehadiran()+"%");
        LVtugas.setText("Nilai Tugas : "+itemArrayList.get(position).getTugas());
        LVuts.setText("Nilai UTS : "+itemArrayList.get(position).getUts());
        LVuas.setText("Nilai UAS : "+itemArrayList.get(position).getUas());
        LVnilaiakhir.setText("Nilai Akhir : "+itemArrayList.get(position).getNilaiakhir());
        return rowView;
    }
}
