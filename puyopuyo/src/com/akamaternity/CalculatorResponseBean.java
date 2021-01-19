package com.akamaternity;

import com.akamaternity.puyopuyo.Tsumo;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CalculatorResponseBean {
	@JsonProperty("ax")
	private String ax;

	@JsonProperty("ay")
	private String ay;

	@JsonProperty("ac")
	private String ac;

	@JsonProperty("cx")
	private String cx;

	@JsonProperty("cy")
	private String cy;

	@JsonProperty("cc")
	private String cc;

	public String getAx() {
		return ax;
	}
	public void setAx(String ax) {
		this.ax = ax;
	}
	public String getAy() {
		return ay;
	}
	public void setAy(String ay) {
		this.ay = ay;
	}
	public String getAc() {
		return ac;
	}
	public void setAc(String ac) {
		this.ac = ac;
	}
	public String getCx() {
		return cx;
	}
	public void setCx(String cx) {
		this.cx = cx;
	}
	public String getCy() {
		return cy;
	}
	public void setCy(String cy) {
		this.cy = cy;
	}
	public String getCc() {
		return cc;
	}
	public void setCc(String cc) {
		this.cc = cc;
	}

	public static CalculatorResponseBean createBeanFromTsumo(Tsumo tsumo) {
		CalculatorResponseBean bean = new CalculatorResponseBean();
		bean.setAx(tsumo.getAxisX() + "");
		bean.setAy(1 + "");
		bean.setAc(tsumo.getAxisPuyo().getColor() + "");
		bean.setCx(tsumo.getChildX() + "");
		bean.setCy((1 + tsumo.getChildPosition().getChildRelativeY()) + "");
		bean.setCc(tsumo.getChildPuyo().getColor() + "");
		return bean;
	}
}
