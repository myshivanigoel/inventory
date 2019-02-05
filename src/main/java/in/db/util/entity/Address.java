package in.db.util.entity;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Embeddable
public class Address implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
        @NotBlank@Size(min=1,max=44,message="Please provide Country Name")
	private String addressCountry;
        @NotBlank@Size(min=1,max=44,message="Please provide State Name")
	private String addressState;
	@NotBlank@Size(min=1,max=44,message="Please provide City Name")
        private String addressCity;
	@NotBlank@Size(min=1,max=44,message="Please provide Address")
        private String addressLine1;
	private String addressLine2;
	@NotBlank
        @Size(min=4,max=10,message="Please provide minimum 4 digit code")
	@Pattern(regexp = "[\\s]*[0-9]*[1-9]+",message="Numbers are allowed only")
        private String pincode;
	
	
	public Address(String addressCountry, String addressState, String addressCity, String addressLine1,
			String addressLine2, String pincode) {
		super();
		this.addressCountry = addressCountry;
		this.addressState = addressState;
		this.addressCity = addressCity;
		this.addressLine1 = addressLine1;
		this.addressLine2 = addressLine2;
		this.pincode = pincode;
	}


	public Address() {
		// TODO Auto-generated constructor stub
	}


	public String getAddressCountry() {
		return addressCountry;
	}


	public void setAddressCountry(String addressCountry) {
		this.addressCountry = addressCountry;
	}


	public String getAddressState() {
		return addressState;
	}


	public void setAddressState(String addressState) {
		this.addressState = addressState;
	}


	public String getAddressCity() {
		return addressCity;
	}


	public void setAddressCity(String addressCity) {
		this.addressCity = addressCity;
	}


	public String getAddressLine1() {
		return addressLine1;
	}


	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}


	public String getAddressLine2() {
		return addressLine2;
	}


	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}


	public String getPincode() {
		return pincode;
	}


	public void setPincode(String pincode) {
		this.pincode = pincode;
	}

    @Override
    public String toString() {
        return "Address{" + "addressCountry=" + addressCountry + ", addressState=" + addressState + ", addressCity=" + addressCity + ", addressLine1=" + addressLine1 + ", addressLine2=" + addressLine2 + ", pincode=" + pincode + '}';
    }
	
	
	

}
