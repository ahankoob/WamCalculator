package com.ahankoob.wamcalculator.models;

/**
 * Created by abolfazl on 4/29/2020.
 */
public class ghest {
	public int ghestNumber;
	public Double ghes,asl,sood;
	public ghest(){}
	public ghest(int ghestNumber, Double ghes, Double asl, Double sood) {
		this.ghestNumber = ghestNumber;
		this.ghes = ghes;
		this.asl = asl;
		this.sood = sood;
	}

	public int getGhestNumber() {
		return ghestNumber;
	}

	public void setGhestNumber(int ghestNumber) {
		this.ghestNumber = ghestNumber;
	}

	public Double getGhest() {
		return ghes;
	}

	public void setGhest(Double ghes) {
		this.ghes = ghes;
	}

	public Double getAsl() {
		return asl;
	}

	public void setAsl(Double asl) {
		this.asl = asl;
	}

	public Double getSood() {
		return sood;
	}

	public void setSood(Double sood) {
		this.sood = sood;
	}
}
