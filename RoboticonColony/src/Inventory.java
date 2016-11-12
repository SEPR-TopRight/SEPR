/**
 * @author jcn509
 * 
 */
public class Inventory {
	private int oreQuantity;
	private int energyQuantity;
	
	public Inventory(int oreQuantity,int energyQuantity){
		setOreQuantity(oreQuantity);
		setEnergyQuantity(energyQuantity);
	}
	
	public Inventory(){
		oreQuantity = 0;
		energyQuantity = 0;
	}
	
	public int getOreQuantity(){
		return oreQuantity;
	}
	public int getEnergyQuantity(){
		return energyQuantity;
	}
	public void setOreQuantity(int oreQuantity){
		if (oreQuantity < 0)
			throw new IllegalArgumentException("oreQuantity must be >=0");
		this.oreQuantity = oreQuantity;
	}
	public void setEnergyQuantity(int energyQuantity){
		if (energyQuantity < 0)
			throw new IllegalArgumentException("energyQuantity must be >=0");
		this.energyQuantity = energyQuantity;
	}
	public void increaseEnergyQuantity(int energyQuantityIncrease){
		if(energyQuantityIncrease < 0){
			throw new IllegalArgumentException("energyQuantityIncrease must be positive");
		}
		energyQuantity += energyQuantityIncrease;
	}
	public void decreaseEnergyQuantity(int energyQuantityDecrease){
		if(energyQuantityDecrease < 0){
			throw new IllegalArgumentException("energyQuantityDecrease must be positive");
		}
		energyQuantity -= energyQuantityDecrease;
	}
	public void increaseOreQuantity(int oreQuantityIncrease){
		if(oreQuantityIncrease < 0){
			throw new IllegalArgumentException("preQuantityIncrease must be positive");
		}
		oreQuantity += oreQuantityIncrease;
	}
	public void decreaseOreQuantity(int oreQuantityDecrease){
		if(oreQuantityDecrease < 0){
			throw new IllegalArgumentException("oreQuantityDecrease must be positive");
		}
		oreQuantity -= oreQuantityDecrease;
	}
}


