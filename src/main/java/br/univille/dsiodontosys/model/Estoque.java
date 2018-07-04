package br.univille.dsiodontosys.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Estoque {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@NotNull
	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.REFRESH })
	private Material materialMovimentado;
	@Temporal(value = TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy H:mm")
	private Date data;
	private int quantidade;
	private String movimento;

	private int estoqueTotal;

	public int getEstoqueTotal() {
		return estoqueTotal;
	}

	public void setEstoqueTotal(int estoqueTotal) {
		this.estoqueTotal = estoqueTotal;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Material getMaterialMovimentado() {
		return materialMovimentado;
	}

	public void setMaterialMovimentado(Material materialMovimentado) {
		this.materialMovimentado = materialMovimentado;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	public String getMovimento() {
		return movimento;
	}

	public void setMovimento(String movimento) {
		this.movimento = movimento;
	}

}
