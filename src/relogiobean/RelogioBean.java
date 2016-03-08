/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package relogiobean;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;
import java.beans.VetoableChangeSupport;
import java.time.LocalTime;

/**
 *
 * @author ccalifi
 */
public class RelogioBean extends Thread {

    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);
    private LocalTime tempo;

    private int hora, minuto, segundo;

    public RelogioBean() {
        tempo = LocalTime.now();
        
        
    }

    private final VetoableChangeSupport vcs = new VetoableChangeSupport(this);

    public void run() {
        try {
            iniciaRelogio();
        } catch (PropertyVetoException ex) {
            Logger.getLogger(RelogioBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(RelogioBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

//    public void iniciaRelogio() throws PropertyVetoException {
//        try {
//            while (true) {
//                LocalTime old = tempo;
//                this.vcs.fireVetoableChange("tempo", old, tempo);
//                tempo = LocalTime.now();
//                this.pcs.firePropertyChange("tempo", old, tempo);
//                Thread.sleep(1000);
//                System.out.println(tempo);
//            }
//        } catch (InterruptedException ex) {
//            Logger.getLogger(RelogioBean.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
    
    
    public void iniciaRelogio() throws PropertyVetoException, InterruptedException {

        hora = tempo.getHour();
        minuto = tempo.getMinute();
        segundo = tempo.getSecond();
        
        while (true){
            Thread.sleep(1000);
            setSegundo(segundo+1);            
            if(segundo == 59){
                setMinuto(minuto+1);
                setSegundo(0);
                if(minuto == 60){
                    setHora(hora+1);
                    setMinuto(0);
                    if(hora == 24){
                        setHora(0);
                    }
                }
            }
            System.out.println(hora + ":" + minuto + ":" + segundo);
        }
        
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        this.pcs.addPropertyChangeListener(listener);
    }

    public void
            removePropertyChangeListener(PropertyChangeListener listener) {
        this.pcs.removePropertyChangeListener(listener);
    }

    public void addVetoableChangeListener(VetoableChangeListener listener) {
        this.vcs.addVetoableChangeListener(listener);
    }

    public void
            removeVetoableChangeListener(VetoableChangeListener listener) {
        this.vcs.removeVetoableChangeListener(listener);
    }

    public int getHora() {
        return hora;
    }

    public void setHora(int hora) {
        int oldHora = hora;
        this.hora = hora;
        this.pcs.firePropertyChange("hora", oldHora, hora);
    }

    public int getMinuto() {
        return minuto;
    }

    public void setMinuto(int minuto) {
        int oldMinuto = minuto;
        this.minuto = minuto;
        this.pcs.firePropertyChange("minuto", oldMinuto, minuto);
    }

    public int getSegundo() {
        return segundo;
    }

    public void setSegundo(int segundo) {
        int oldSegundo = segundo;
        this.segundo = segundo;
        this.pcs.firePropertyChange("segundo", oldSegundo, segundo);
    }

}
