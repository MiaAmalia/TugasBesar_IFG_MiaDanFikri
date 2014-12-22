package aplikasi.absensi.penilaian;

public class Siswa {
	private String nm,kd,jk;
	
	public Siswa(String nm, String kd, String jk) {
        super();
        this.nm = nm;
        this.kd = kd;
        this.jk = jk;
    }
	
	public String getNM(){
		return nm;
	}
	
	public void setNM(String nm){
		this.nm = nm;
	}
	
	public String getKD(){
		return kd;
	}
	
	public void setKD(String kd){
		this.kd = kd;
	}
	
	
	
	public String getJK(){
		return jk;
	}
	
	public void setJK(String jk){
		this.jk = jk;
	}
}
