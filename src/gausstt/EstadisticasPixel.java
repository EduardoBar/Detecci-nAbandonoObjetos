/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gausstt;

import java.util.TimerTask;
import javax.swing.Timer;

/**
 *
 * @author Eduardo
 */
public class EstadisticasPixel {
    
    private MezclarKgauss mezclaPixel1,mezclaPixel2;
    private boolean pPlano = false;
    int B;
    private long startTime,refTime;
    

    public EstadisticasPixel() {
        
        this.mezclaPixel1 = new MezclarKgauss(3);
      
   
    
    }
    
    public void actualizarObservacion(int[] observacionRgb){
       
       this.pPlano =  mezclaPixel1.actualizarGaussianos(observacionRgb);
   //     this.pPlano = mezclaPixel1.organizarParametros();
        
     
       
    }
    
    public boolean getpPlano(){
        return this.pPlano;
    }
    
    
    
    
}
