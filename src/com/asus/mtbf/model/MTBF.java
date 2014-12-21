package com.asus.mtbf.model;

import java.util.Date;

import com.example.beffect.R;

import android.content.Context;

public class MTBF {

	private Date date;
	private String TotalSuccessRate = "0%";
	private String MTBFNumber;
	private String MTBFName;
	private String MTBFSuccessRate;

	public MTBF(String number, String Name, String SuccessRate) {
		this.MTBFNumber = number;
		this.MTBFName = Name;
		this.MTBFSuccessRate = SuccessRate;
		this.TotalSuccessRate = "95%";

	}

	public String getMTBFNumber() {
		return MTBFNumber;
	}

	public String getMTBFName() {
		return MTBFName;
	}
	
	public String getMTBFSuccessRate(){
		return MTBFSuccessRate;
	}

}
