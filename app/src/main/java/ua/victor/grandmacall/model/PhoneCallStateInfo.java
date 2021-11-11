package ua.victor.grandmacall.model;


public class PhoneCallStateInfo {

    private PhoneCallState phoneCallState;
    private String phoneNumber;
    private String address;

    public PhoneCallState getPhoneCallState() {
        return phoneCallState;
    }

    public void setPhoneCallState(PhoneCallState phoneCallState) {
        this.phoneCallState = phoneCallState;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
