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
    private boolean match;
    public MezclarKgauss(int k){
        this.K =k;
        this.gaussMuestra = new Gaussiano[k];
        for(int i = 0; i < k; i++){
            this.gaussMuestra[i] = new Gaussiano();
        }
        this.pesos = new double[k];
    }
    
    public boolean actualizarGaussianos(int[] observacionRGB){
        boolean matchedAny = false;
        this.match= true;
        int index = 0;
            
        organizarParametros();
        
        for(int i = 0; i < this.K; i++){
           
            if(this.gaussMuestra[i].calcularProbabilidad(observacionRGB) <= (Math.sqrt(this.gaussMuestra[i].getVarianza()))*2.5){
                this.gaussMuestra[i].actualizarDatos(true,observacionRGB);
               this. match = this.gaussMuestra[i].isForeground();
                //matchedAny = true;
              //  return match;
            }else{
                this.gaussMuestra[i].actualizarDatos(false,observacionRGB);
               // match = false;
            }
//            
//            if(i < (this.K -1)){
//                if(gaussMuestra[i+1].calcularProbabilidad(observacionRGB) < gaussMuestra[i].calcularProbabilidad(observacionRGB)){
//                    index = i;
//                }
//            }
           // System.out.println("Var "+this.gaussMuestra[i].getVarianza());
        }
        
////        
//       if(matchedAny == false){
////         int index = 0;
////            for(int x = 0; x < this.K-1; x++){
////                
////                if(gaussMuestra[x+1].calcularProbabilidad(observacionRGB) < gaussMuestra[x].calcularProbabilidad(observacionRGB)){
////                    index = x;
////                }
////            }
////                      this.gaussMuestra[i].actualizarDatos(false,observacionRGB);
//            this.gaussMuestra[index].setMedia(observacionRGB);
//            this.gaussMuestra[index].setVarianza(gaussMuestra[index].getVarianza()*2);
//            this.gaussMuestra[index].setPeso(0.1);
////                    
////            
//        }
        
        return this.match;
    }
    
    public void organizarParametros(){
       
          double acum = 0;
          Gaussiano gaussTemp;
        for (int i = 0; i < this.K; i++)
            for (int j = 1; j < this.K-i-1; j++)
                if ((Math.pow(this.gaussMuestra[j-1].getPesos(), 2)/this.gaussMuestra[j-1].getVarianza()) < (Math.pow(this.gaussMuestra[j].getPesos(), 2)/this.gaussMuestra[j].getVarianza()))
                {
                
                    gaussTemp = this.gaussMuestra[j-1];
                    this.gaussMuestra[j-1] = this.gaussMuestra[j];
                    this.gaussMuestra[j] = gaussTemp;
                }
        
        for(int x = 0; x < this.K; x++){
        
            if(acum >= 0.7){
               this.gaussMuestra[x].setIsForeground(true);
               // return true;
                
            }
            
               acum+=this.gaussMuestra[x].getPesos();
                
            this.gaussMuestra[x].setIsForeground(false);
        }
    
        
      
    }
    
    public double[] getPesos(){
        
        for(int i = 0; i < this.K; i++){
            pesos[i] = gaussMuestra[i].getPesos();
        }
        return this.pesos;
    }
    
}
