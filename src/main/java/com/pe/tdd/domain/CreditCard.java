package com.pe.tdd.domain;

public class CreditCard {
	private int NIP;
	private int number;
	private double balance;
	private User owner;
	
	public int getNIP() {
		return NIP;
	}
	
	public void setNIP(int _NIP) {
		NIP = _NIP;
	}
	
	public int getNumber() {
		return number;
	}
	
	public void setNumber(int number) {
		this.number = number;
	}
	
	public double getBalance() {
		return balance;
	}
	
	public void setBalance(double balance) {
		this.balance = balance;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}
}
