package br.univille.dsiodontosys.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Procedimento {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@NotNull
	@Length(min = 2, max = 100, message = "O tamanho do nome deve ser entre {min} e {max}")
	private String nome;
	private String descricao;
	@Temporal(value = TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "HH:mm")
	private Date duracaoAproximada;
	private String valor;
	private String desconto;

	@ManyToMany(cascade = { CascadeType.MERGE, CascadeType.REFRESH })
	@JoinColumn(name = "procedimento_id")
	private List<Dentista> listaDentistasAutorizados = new ArrayList<Dentista>();

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Date getDuracaoAproximada() {
		return duracaoAproximada;
	}

	public void setDuracaoAproximada(Date duracaoAproximada) {
		this.duracaoAproximada = duracaoAproximada;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public String getDesconto() {
		return desconto;
	}

	public void setDesconto(String desconto) {
		this.desconto = desconto;
	}

	public List<Dentista> getListaDentistasAutorizados() {
		return listaDentistasAutorizados;
	}

	public void setListaDentistasAutorizados(List<Dentista> listaDentistasAutorizados) {
		this.listaDentistasAutorizados = listaDentistasAutorizados;
	}

}
