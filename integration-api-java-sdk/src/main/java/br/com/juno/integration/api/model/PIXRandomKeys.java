package br.com.juno.integration.api.model;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class PIXRandomKeys extends BaseModel {

	private static final long serialVersionUID = 7060693572283711869L;

	private String key;
	private String creationDateTime;
	private String ownershipDateTime;
	private String claimRequestDateTime;
	
	protected PIXRandomKeys() {
		// NTD	
	}
	
	public String getKey() {
		return key;
	}
	
	public String getCreationDateTime() {
		return creationDateTime;
	}
	
	public String getOwnershipDateTime() {
		return ownershipDateTime;
	}
	
	public String getClaimRequestDateTime() {
		return claimRequestDateTime;
	}
	
	@Override
	public String toString() {
		ToStringBuilder builder = new ToStringBuilder(this, ToStringStyle.JSON_STYLE);
		
		builder.append("id", getId());
		builder.append("key", getKey());
		builder.append("creationDateTime", getCreationDateTime());
		builder.append("ownershipDateTime", getOwnershipDateTime());
		builder.append("claimRequestDateTime", getClaimRequestDateTime());
		
		return builder.toString();
	}
}
