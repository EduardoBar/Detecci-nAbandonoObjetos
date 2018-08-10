/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gausstt;

/**
 *
 * @author Eduardo
 */
public class MezclarKgauss {
    
    private int K;
    public Gaussiano[] gaussMuestra;
    private double pesos[];
    private boolean match = false;
    public MezclarKgauss(int k){
        this.K =k;
        this.gaussMuestra = new Gaussiano[k];
        for(int i = 0; i < k; i++){
            this.gaussMuestra[i] = new Gaussiano();
        }
        this.pesos = new double[k];
    }
    
    public boolean actualizarGaussianos(int[] observacionRGB){
    
        this.match = true;int index = 0;
        
      
        for(int i = 0; i < this.K; i++){
           
            if(this.gaussMuestra[i].calcularProbabilidad(observacionRGB) <= (this.gaussMuestra[i].getVarianza()*2.5)){
                this.gaussMuestra[i].actualizarDatos(true,observacionRGB);
                match = false;
              //  return match;
            }else{
                this.gaussMuestra[i].actualizarDatos(false,observacionRGB);
               // match = false;
            }
            
            if(i < (this.K -1)){
                if(gaussMuestra[i+1].calcularProbabilidad(observacionRGB) < gaussMuestra[i].calcularProbabilidad(observacionRGB)){
                    index = i;
                }
            }
           // System.out.println("Var "+this.gaussMuestra[i].getVarianza());
        }
        
        
        if(match == true){
//         int index = 0;
//            for(int x = 0; x < this.K-1; x++){
//                
//                if(gaussMuestra[x+1].calcularProbabilidad(observacionRGB) < gaussMuestra[x].calcularProbabilidad(observacionRGB)){
//                    index = x;
//                }
//            }
            
            this.gaussMuestra[index].setMedia(observacionRGB);
            this.gaussMuestra[index].setVarianza(gaussMuestra[index].getVarianza()*10);
            this.gaussMuestra[index].setPeso(0.01);
                    
            
        }
        
        return this.match;
    }
    
    public boolean organizarParametros(){
       
          double acum = 0;
        for (int i = 0; i < this.K-1; i++)
            for (int j = 0; j < this.K-i-1; j++)
                if ((this.gaussMuestra[j].getPesos()/Math.sqrt(this.gaussMuestra[j].getVarianza())) > (this.gaussMuestra[j+1].getPesos()/Math.sqrt(this.gaussMuestra[j+1].getVarianza())))
                {
                    // swap temp and arr[i]
                    Gaussiano gaussTemp = this.gaussMuestra[j];
                    this.gaussMuestra[j] = this.gaussMuestra[j+1];
                    this.gaussMuestra[j+1] = gaussTemp;
                }
        
        for(int x = 0; x < this.K; x++){
            acum+=this.gaussMuestra[x].getPesos();
            if(acum >= 0.5 && x < this.K -1){
               
                return true;
                
            }
        }
        
        
        return false;
    }
    
    public double[] getPesos(){
        
        for(int i = 0; i < this.K; i++){
            pesos[i] = gaussMuestra[i].getPesos();
        }
        return this.pesos;
    }
    
}
