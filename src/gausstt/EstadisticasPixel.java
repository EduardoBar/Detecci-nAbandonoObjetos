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
    private int IDregion;
    private int regionColor;
    private int RGB[];

    public EstadisticasPixel() {
        
        this.mezclaPixel1 = new MezclarKgauss(3);
        regionColor =0;
        IDregion =0;
        RGB = new int[2];
      
   
    
    }
    
    public void actualizarObservacion(int[] observacionRgb){
       
       this.pPlano =  mezclaPixel1.actualizarGaussianos(observacionRgb);
   //     this.pPlano = mezclaPixel1.organizarParametros();
        
     
       
    }
    
    public boolean getpPlano(){
        return this.pPlano;
    }
    
    
    public MezclarKgauss getMezclaPix(){
        return this.mezclaPixel1;
    }
    
    public int getRegion(){
        return this.IDregion;
    }
    
    public void setRegion(int region){
        this.IDregion = region;
    }
    
    public int getRegioncolor(){
        return this.regionColor;
    }
    
    public void setRegioncolor(int col){
        
        this.regionColor = col;
        
    }
    
    public boolean isRegion(){
        if(this.IDregion == 0){
            return false;
        }
            return true;  
        
        
    }

    /**
     * @return the RGB
     */
    public int[] getRGB() {
        return RGB;
    }

    /**
     * @param RGB the RGB to set
     */
    public void setRGB(int[] RGB) {
        this.RGB = RGB;
    }
    
}
