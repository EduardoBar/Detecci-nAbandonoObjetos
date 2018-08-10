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
public class Gaussiano {
    
    private double peso, varianza;
    private double[] media;
    private double[] matCova;
    private double alfa;
    
    public Gaussiano(){
        this.media = new double[2];
        this.matCova = new double[2];
       
        this.peso = 0.01;
        this.media[0] = 1;
        this.media[1] = 1;
        this.varianza= 1;  
        this.matCova[0] = 1;
         this.matCova[1] = 1;
         this.alfa = 0.2;
    }
    
    public double calcularProbabilidad(int[] RGBvect){
//        double probActual = 0;
//        double exponencial = 0;
        double exponencialIzq = 0;
//        double detMat = 0;
//        double[] nuevoVect = new double[2];
//        double[] nuevoVect2 = new double[2];
        exponencialIzq = (1/((2*Math.PI)*Math.sqrt(this.matCova[0]*this.matCova[1])))*Math.exp((((Math.pow(RGBvect[0], 2))-(2*(RGBvect[0]*this.media[0]))+(Math.pow(this.media[0],2))+((1/this.varianza)*RGBvect[0])-((1/this.varianza)*this.media[0]))+((Math.pow(RGBvect[1], 2))-(2*(RGBvect[1]*this.media[1]))+(Math.pow(this.media[1],2))+((1/this.varianza)*RGBvect[1])-((1/this.varianza)*this.media[1])))/2);
        
    
//        
//        nuevoVect[0] = RGBvect[0] - media[0];
//        nuevoVect[1] = RGBvect[1] - media[1];
//        
//        nuevoVect2[0] = nuevoVect[0] * (1/matCova[0]); 
//        nuevoVect2[0] = nuevoVect[1] *(1/matCova[1]); 
//         
//        exponencial = (((nuevoVect[0]*nuevoVect2[0]) + (nuevoVect[1]*nuevoVect2[1]))/2);
//        detMat = matCova[0] * matCova[1];
//        exponencialIzq = 1/((2*Math.PI)*Math.sqrt(detMat));


        
       
        return exponencialIzq;
    }
    
    
    public void actualizarDatos(boolean coincide, int[]observacionRGB){
        
//        double[] nuevaMedia = new double[2];
        double probInv = 0;
        double probInv2 = 0;
//        double[] nuevovct = new double[2];
//        double[] nuevovct2 = new double[2];
        probInv = calcularProbabilidad(observacionRGB);
        
        
        if(coincide){
            
        this.media[0] = ((1-probInv)*this.media[0]) + probInv*observacionRGB[0];
        this.media[1] = ((1-probInv)*this.media[1]) + probInv*observacionRGB[1];
        probInv2 = calcularProbabilidad(observacionRGB);
        this.varianza = ((1-probInv2)*this.varianza)*probInv2*((Math.pow(observacionRGB[0],2)-Math.pow(this.media[0],2))+(Math.pow(observacionRGB[1],2)-Math.pow(this.media[1],2)));
        this.matCova [0] = this.varianza;
        this.matCova [1] = this.varianza;
            this.peso = ((1-this.alfa)*peso) + this.alfa*1;
            
        }else{
            this.peso = ((1-this.alfa)*peso);
        }
        
    }
    
   
    
    public double getPesos(){
        return peso;
    }
    
    public double getVarianza(){
        return this.varianza;
    }
    
    public void setMedia(int[] obs){
        
      
            this.media[0] = (double)obs[0];
            
            this.media[1] = (double)obs[1];
        
    }
    
    public void setPeso(double peso){
        this.peso = peso;
    }
    
    public void setVarianza(double var){
        this.varianza = var;
    }
}