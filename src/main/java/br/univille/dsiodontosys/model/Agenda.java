package br.univille.dsiodontosys.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

@Entity
public class Agenda {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@NotNull
	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.REFRESH })
	private Paciente paciente;

	@NotNull
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date data;

	@NotNull
	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.REFRESH })
	private Procedimento procedimento;

	@NotNull
	@Column(length = 50)
	private String status;

	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.REFRESH })
	private Dentista dentistaResponsavel;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Paciente getPaciente() {
		return paciente;
	}

	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Procedimento getProcedimento() {
		return procedimento;
	}

	public void setProcedimento(Procedimento procedimento) {
		this.procedimento = procedimento;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Dentista getDentistaResponsavel() {
		return dentistaResponsavel;
	}

	public void setDentistaResponsavel(Dentista dentistaResponsavel) {
		this.dentistaResponsavel = dentistaResponsavel;
	}

}
