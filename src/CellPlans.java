//Dalveer Dosanjh
public enum CellPlans {
	FIDOS("Fido Student","iPhone XR",670,12),
	FIDOB("Fido BYOB","NA",0,24),
	BELLF("Bell Family","Galaxy S10+",600,36),
	ROGERSB("Rogers Business","Pixel 3",800,20),
	TELUSG("Telus Gigabytes","Huawei Pro30",200,30),
	BELLS("Bell Share","NA",0,40),
	KOODOB("Koodo Business","BB Key2",0,12),
	KOODOT("Koodo Tab","iPhone XR",0,24),
	FREEDOMH("Freedom Home","Xperia XZ1",100,36),
	BELLP("Bell Perks","Moto",50,12);
	
	private String value;
	private String phone;
	private double cost;
	private int months;
	
	private CellPlans(String name,String phone,double cost,int months)
	{
		this.value = name;
		this.phone = phone;
		this.cost = cost;
		this.months = months;
	}
		
	public String getName() 
	{
		return value;
	}
	
	public String getPhone() {//see if NA or no NA
		if(phone.equals("NA")) {
			return "***";
		}
		return phone;
	}
	
	public double getCost() {
		return cost;
	}
	
	public int getMonths() {
		return months;
	}

}
