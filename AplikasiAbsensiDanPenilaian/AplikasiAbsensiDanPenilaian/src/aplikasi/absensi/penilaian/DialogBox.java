package aplikasi.absensi.penilaian;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class DialogBox extends Activity{
	public AlertDialog dialogBox;
	public boolean keputusan = false;
	
	public void dialogBoxOke(Context konteks, String pesan){
		dialogBox = new AlertDialog.Builder(konteks).setMessage(pesan).setNeutralButton("Oke", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
			}
		}).show();
	}
	
	public void dialogBoxYesorNo(Context konteks, String pesan){
		dialogBox = new AlertDialog.Builder(konteks).setMessage(pesan).setPositiveButton("Ya", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub
				keputusan = true;
			}
		}).setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub
				keputusan = false;
			}
		}).show();
	}
}
