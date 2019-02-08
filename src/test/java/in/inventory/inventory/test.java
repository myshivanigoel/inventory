/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.inventory.inventory;

/**
 *
 * @author shivani
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author shivani
 */
public class test {
    
    public static void main(String args[])
    {
        
        derived d=new derived();
        
        System.out.println("d"+d.x);
        
        
        
        int i=0;
        
        for(;;)
        {
            for(i=0;i<=5;i++)
            {
                System.out.println(i);
            }
            if(i>5)
                break;
        }
    }
}



class base
{
    
    static int x=100;
    
    static {
        x=++x + --x;
    }
}


class derived extends base
{
    
    static{
        
        x=x-- + --x;
    }
    
    {
        
        x=x++ - ++x;
    }
}