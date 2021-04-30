package org.app.peorig;

import java.util.ArrayList;
import java.util.List;

import org.cloudbus.cloudsim.Cloudlet;
import org.cloudbus.cloudsim.DatacenterBroker;
import org.cloudbus.cloudsim.Log;
import org.cloudbus.cloudsim.ResCloudlet;
import org.cloudbus.cloudsim.Vm;
import org.cloudbus.cloudsim.core.CloudSim;
import org.cloudbus.cloudsim.core.CloudSimTags;
import org.cloudbus.cloudsim.lists.VmList;

public class ResDatacenterBroker extends DatacenterBroker{

	protected List<? extends ResCloudlet> resCloudletList;
	
	public ResDatacenterBroker(String name) throws Exception {
		super(name);
		setResCloudletList(new ArrayList<ResCloudlet>());
	}

	protected <T extends ResCloudlet> void setResCloudletList(List<T> resCloudletList) {
		this.resCloudletList = resCloudletList;
	}
	
	@SuppressWarnings("unchecked")
	public <T extends ResCloudlet> List<T> getResCloudletList() {
		return (List<T>) resCloudletList;
	}
	
	public void submitResCloudletList(List<? extends ResCloudlet> list) {
		getResCloudletList().addAll(list);
	}
	
	protected void submitCloudlets() {
//		System.out.println("------------------------------mydatacenterbroker submitcloudlets----------------------------");
//		System.out.println(Integer.MAX_VALUE);
		int vmIndex = 0;
		List<Cloudlet> successfullySubmitted = new ArrayList<Cloudlet>();
		for (ResCloudlet rescloudlet : getResCloudletList()) {
			Vm vm;
			// if user didn't bind this cloudlet and it has not been executed yet
			if (rescloudlet.getCloudlet().getVmId() == -1) {
				vm = getVmsCreatedList().get(vmIndex);
			} else { // submit to the specific vm
				vm = VmList.getById(getVmsCreatedList(), rescloudlet.getCloudlet().getVmId());
				if (vm == null) { // vm was not created
					if(!Log.isDisabled()) {	
						Log.print(CloudSim.clock());
						Log.print(getName());
						Log.print("Postponing execution of cloudlet");
						Log.print(rescloudlet.getCloudlet().getCloudletId());
						Log.print(": bount VM not available");
				
					}
					continue;
				}
			}

			if (!Log.isDisabled()) {
				Log.print(CloudSim.clock());
				Log.print(getName());
				Log.print(": Sending cloudlet ");
				Log.print(rescloudlet.getCloudlet().getCloudletId());
				Log.print(" to VM #");
				Log.print(vm.getId());

			}
			
			rescloudlet.getCloudlet().setVmId(vm.getId());
			int delay = (int)rescloudlet.getStartTime();
//			sendNow(getVmsToDatacentersMap().get(vm.getId()), CloudSimTags.CLOUDLET_SUBMIT, cloudlet);
			schedule(getVmsToDatacentersMap().get(vm.getId()),delay ,CloudSimTags.CLOUDLET_SUBMIT, rescloudlet.getCloudlet());
			cloudletsSubmitted++;
			vmIndex = (vmIndex + 1) % getVmsCreatedList().size();
			getCloudletSubmittedList().add(rescloudlet.getCloudlet());
			successfullySubmitted.add(rescloudlet.getCloudlet());
		}

		// remove submitted cloudlets from waiting list
		getCloudletList().removeAll(successfullySubmitted);
	}
}
