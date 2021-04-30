package org.app.peorig;

public class DataCenterDetails {
	private String id;
	private String broker;
	private double capacity[];
	private double greenEnergy;

	public DataCenterDetails() {
		capacity = new double[60];
	}

	@Override
	public String toString() {
		return "DataCenterDetails:: [id=" + id + ", broker=" + broker + ", capacity=" + capacity + ", greenEnergy="
				+ greenEnergy + "]";
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public double[] getCapacity() {
		return capacity;
	}

	public void setCapacity(double capacity[]) {
		this.capacity = capacity;
	}

	public double getGreenEnergy() {
		return greenEnergy;
	}

	public void setGreenEnergy(double greenEnergy) {
		this.greenEnergy = greenEnergy;
	}

	public String getBroker() {
		return broker;
	}
	
	public void setBroker(String broker) {
		this.broker = broker;
	}
// 	@Override
//	public boolean equals(Object obj) {
//		if(obj==null)
//			return false;
//		
//		if (!DataCenterDetails.class.isAssignableFrom(obj.getClass())) {
//	        return false;
//	    }
//		
//	    final DataCenterDetails other = (DataCenterDetails) obj;
//	    if ((this.id == null) ? (other.id != null) : !this.id.equals(other.id)) {
//	    	return false;
//	    }
//	    if (this.broker != other.broker) {
//	        return false;
//	    }
//	    return true;
//		
//	}

	public int compareTo(DataCenterDetails dd) {
		// TODO Auto-generated method stub
		if(this.getId().equals(dd.getId())) {
			System.out.println("not here-----------------------------------");
			return 0;
		}
//			return 0;
		if(dd.greenEnergy - this.greenEnergy >= 0)
			return 1;
		else
			return -1;
	}

//	@Override
//	public int compareTo(Object obj) {
//		DataCenterDetails other = (DataCenterDetails) obj;
//		if(this.getId() == other.getId())
//			return 0;
//		if(other.getGreenEnergy()-this.getGreenEnergy() == 0)
//			return -1;
//		return -(int)(other.getGreenEnergy()+this.getGreenEnergy());
//	}
//	
}

/*

*/
