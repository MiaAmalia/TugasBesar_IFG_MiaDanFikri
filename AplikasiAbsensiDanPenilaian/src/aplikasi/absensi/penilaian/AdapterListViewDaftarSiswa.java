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
public class AdapterListViewDaftarSiswa extends ArrayAdapter<Siswa> {
	private Context context;
	private ArrayList<Siswa> itemArrayList;

	public AdapterListViewDaftarSiswa(Context context, ArrayList<Siswa> itemArrayList) {
        super(context, R.layout.listviewsiswa,itemArrayList);
        this.context = context;
        this.itemArrayList = itemArrayList;
    }
	
	@Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.listviewsiswa, parent, false);
        TextView LVnamaSiswa = (TextView) rowView.findViewById(R.id.LVnamaSiswa);
        TextView LVkodeSiswa = (TextView) rowView.findViewById(R.id.LVkodeSiswa);
        TextView LVjenisKelamin = (TextView) rowView.findViewById(R.id.LVjenisKelamin);
        LVnamaSiswa.setText(itemArrayList.get(position).getNM());
        LVkodeSiswa.setText(itemArrayList.get(position).getKD());
        LVjenisKelamin.setText(itemArrayList.get(position).getJK());
        return rowView;
    }
}
