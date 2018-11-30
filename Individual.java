package org.cloudbus.cloudsim.examples;

import java.util.List;
import java.util.Random;

import org.cloudbus.cloudsim.Cloudlet;
import org.cloudbus.cloudsim.Datacenter;
import org.cloudbus.cloudsim.DatacenterBroker;
import org.cloudbus.cloudsim.Vm;

public class Individual
{
	
	 public static final int SIZE = 10;
	    int[] genes = new int[SIZE];
	    String gene;
	    int fitnessValue;

	    public Individual() {
	    	
	    	for(int i=0;i<SIZE;i++)
	    	{
	    		genes[i]=0;
	    	}
	    	gene=null;
	    	fitnessValue=0;
	    	
	    }

	    public int getFitnessValue() 
	    {
	        return fitnessValue;
	    }

	    public void setFitnessValue(int fitnessValue) 
	    {
	        this.fitnessValue = fitnessValue;
	    }

	    public int getGenes(int index) 
	    {
	        return genes[index];
	    }
	    
	    public String getGene()
	    {
	    	return gene;
	    }

	    public void setGene(int index, int gene) 
	    {
	        this.genes[index] = gene;
	    }

	    public void randGenes() 
	    {
	        Random rand = new Random();
	        for(int i=1; i<SIZE; ++i) 
	        {
	            this.setGene(i, rand.nextInt(10));
	        }
	    }
        
	    public void mutate() 
	    {
	        Random rand = new Random();
	        int index = rand.nextInt(SIZE);
	        this.setGene(index, 10-this.getGenes(index));    // flip
	    }

	/*   public int evaluate() 
	    {
	        int fitness = 0;
	        for(int i=0; i<SIZE; ++i) 
	        {
	            fitness += this.getGene(i); //include power function of how much power will be consumed if a vm runs on a datacentre
	        }
	        this.setFitnessValue(fitness);

	        return fitness;
	    }*/

	public float evaluate(String str, List<? extends Vm> vmlist, List<? extends Cloudlet> cloudletList,
			Datacenter datacenter2) throws Exception {
		// TODO Auto-generated method stub
		DatacenterBroker db=new DatacenterBroker("Db");
		float power;
		
		power=db.evaluatePower(str, vmlist, cloudletList, datacenter2);
		
		return power;
	}

}

