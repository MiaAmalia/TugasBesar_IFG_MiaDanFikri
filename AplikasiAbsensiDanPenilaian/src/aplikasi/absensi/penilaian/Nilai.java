package aplikasi.absensi.penilaian;

public class Nilai {
	private String kd,nm,jk,kehadiran,tugas,uts,uas,nilaiakhir;
	
	public Nilai(String nm, String kd, String jk, String kehadiran, String tugas, String uts, String uas, String nilaiakhir) {
        super();
        this.nm = nm;
        this.kd = kd;
        this.jk = jk;
        this.kehadiran = kehadiran;
        this.tugas = tugas;
        this.uts = uts;
        this.uas = uas;
        this.nilaiakhir = nilaiakhir;
    }

	public String getUts() {
		return uts;
	}

	public void setUts(String uts) {
		this.uts = uts;
	}

	public String getJk() {
		return jk;
	}

	public void setJk(String jk) {
		this.jk = jk;
	}

	public String getKd() {
		return kd;
	}

	public void setKd(String kd) {
		this.kd = kd;
	}

	public String getNm() {
		return nm;
	}

	public void setNm(String nm) {
		this.nm = nm;
	}

	public String getTugas() {
		return tugas;
	}

	public void setTugas(String tugas) {
		this.tugas = tugas;
	}

	public String getNilaiakhir() {
		return nilaiakhir;
	}

	public void setNilaiakhir(String nilaiakhir) {
		this.nilaiakhir = nilaiakhir;
	}

	public String getUas() {
		return uas;
	}

	public void setUas(String uas) {
		this.uas = uas;
	}

	public String getKehadiran() {
		return kehadiran;
	}

	public void setKehadiran(String kehadiran) {
		this.kehadiran = kehadiran;
	}
}
