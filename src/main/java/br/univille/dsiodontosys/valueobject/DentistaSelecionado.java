package br.univille.dsiodontosys.valueobject;

import br.univille.dsiodontosys.model.Dentista;

public class DentistaSelecionado {
	private Dentista dentistaSelecionado = new Dentista();

	public Dentista getDentistaSelecionado() {
		return dentistaSelecionado;
	}

	public void setDentistaSelecionado(Dentista dentistaSelecionado) {
		this.dentistaSelecionado = dentistaSelecionado;
	}
}
