package edu.ubb.cwmdEjb.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "configuredFlow")
public class ConfiguredFlow extends BaseEntity{
	
	private static final long serialVersionUID = -2502548668979454934L;

	@JoinColumn(name = "flowId")
	@OneToOne()
	private Flow flow;
	
	@Column(name = "P1", nullable = false)
	private String p1;

	@Column(name = "P2", nullable = false)
	private String p2;
	
	@Column(name = "P3", nullable = true)
	private String p3;
	
	@Column(name = "P4", nullable = true)
	private String p4;
	
	@Column(name = "P5", nullable = true)
	private String p5;
	
	@Column(name = "P6", nullable = true)
	private String p6;
	
	@Column(name = "P7", nullable = true)
	private String p7;

	public Flow getFlow() {
		return flow;
	}

	public void setFlow(Flow flow) {
		this.flow = flow;
	}

	public String getP1() {
		return p1;
	}

	public void setP1(String p1) {
		this.p1 = p1;
	}

	public String getP2() {
		return p2;
	}

	public void setP2(String p2) {
		this.p2 = p2;
	}

	public String getP3() {
		return p3;
	}

	public void setP3(String p3) {
		this.p3 = p3;
	}

	public String getP4() {
		return p4;
	}

	public void setP4(String p4) {
		this.p4 = p4;
	}

	public String getP5() {
		return p5;
	}

	public void setP5(String p5) {
		this.p5 = p5;
	}

	public String getP6() {
		return p6;
	}

	public void setP6(String p6) {
		this.p6 = p6;
	}

	public String getP7() {
		return p7;
	}

	public void setP7(String p7) {
		this.p7 = p7;
	}

}
