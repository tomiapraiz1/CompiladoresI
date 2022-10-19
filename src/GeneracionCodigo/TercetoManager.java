package GeneracionCodigo;

import java.util.ArrayList;

public class TercetoManager {
	
	private ArrayList<Terceto> tercetos;
	
	TercetoManager(){
		this.tercetos = new ArrayList<Terceto>();
	}
	
	public void putTerceto(Terceto t) {
		this.tercetos.add(t);
	}
	
	public int getIndexTerceto(Terceto t) {
		return this.tercetos.indexOf(t);
	}

}
