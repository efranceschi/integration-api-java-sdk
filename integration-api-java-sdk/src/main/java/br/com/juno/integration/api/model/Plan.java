package br.com.juno.integration.api.model;

import java.math.BigDecimal;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class Plan extends BaseModel {

	private static final long serialVersionUID = -3166338764312419174L;

	private String createdOn;
	private String name;
	private String frequency;
	private String status; 
	private BigDecimal amount;
	
	protected Plan() {
		// NTD
	}

	public String getCreatedOn() {
		return createdOn;
	}
	
	public String getName() {
		return name;
	}
	
	public String getFrequency() {
		return frequency;
	}
	
	public String getStatus() {
		return status;
	}
	
	public BigDecimal getAmount() {
		return amount;
	}
	
	
	@Override
	public String toString() {
		ToStringBuilder builder = new ToStringBuilder(this, ToStringStyle.JSON_STYLE);
		builder.append("id", getId());
		builder.append("cratedOn", getCreatedOn());
		builder.append("name", getName());
		builder.append("frequency", getFrequency());
		builder.append("status", getStatus());
		builder.append("amount", getAmount());
		return builder.toString();
	}

}
